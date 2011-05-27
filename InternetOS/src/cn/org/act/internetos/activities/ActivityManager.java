package cn.org.act.internetos.activities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.org.act.internetos.ModuleConstructor;
import cn.org.act.internetos.UserSpace;
import cn.org.act.internetos.persist.Application;
import cn.org.act.internetos.persist.Pair;
import cn.org.act.internetos.signal.Signal;
import cn.org.act.internetos.signal.SignalListener;
import cn.org.act.tools.ObjectChangedListener;

public class ActivityManager {
	private Map<String,Activity> activities = new HashMap<String,Activity>();

	private UserSpace userspace;
	public ActivityManager(UserSpace userspace){
		this.userspace = userspace;
		
		//TODO complex smell:UserSpace change is clientTick now
		userspace.addListener(new ObjectChangedListener(){

			@Override
			public void onChanged(Object obj, Object oldstate) {
				applicationStateChanged();
				
			}
			
		});
	}
	
	private Map<String,Activity> getActivities(){
		for(Application app : userspace.getApps()){
			if(!app.isInited())
				app.init(userspace);
			
			if(!activities.containsKey(app.getName()))
				activities.put(app.getName(), new Activity(app.getName(),"installed",Activity.Killed));
		}
		return activities;
	}
	
	public void addActivity(String name, String type) {
		if(getActivities().containsKey(name))
			getActivities().get(name).increaseCount();
		else
			setActivity( new Activity(name,type,Activity.Actived));
	}
	
	public Activity getActivity(String name){
		return getActivities().get(name);
	}
	
	public Collection<Activity> getAllActivities(){
		return getActivities().values();
	}
	
	public void stopActivity(String name){
		if(getActivities().containsKey(name))
			getActivity(name).decreaseCount();
	}
	
	public void killActivity(String name){
		getActivity(name).setState(Activity.Killed);
	}

	public void setActivity(Activity activity) {
		getActivities().put(activity.getName(), activity);
		activity.addListener(new ObjectChangedListener(){
			@Override
			public void onChanged(Object obj, Object oldstate) {
				applicationStateChanged();
			}
		});
		applicationStateChanged();
	}
	
	private void applicationStateChanged(){
		//Check all waiting routings, cope the ready ones
		List<Pair<SignalListener,Signal>> restRoutings = 
			new ArrayList<Pair<SignalListener,Signal>>();
		
		for(Pair<SignalListener,Signal> route: waitingRoutings){
			SignalListener listener = route.getItem1();
			Signal signal = route.getItem2();
			if(listener.isEventRecieveReady(userspace)){
				try {
					listener.accept(signal, null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else
				restRoutings.add(route);
		}
		
		//remove the done pairs
		waitingRoutings = restRoutings;
	}

	private List<Pair<SignalListener,Signal>> waitingRoutings = 
		new ArrayList<Pair<SignalListener,Signal>>();
	
	public void registerDelaySignal(Signal signal,
			List<SignalListener> notready) {
		for(SignalListener listener:notready){
			waitingRoutings.add(new Pair<SignalListener,Signal>(
					listener, signal
					));
		}
	}
}

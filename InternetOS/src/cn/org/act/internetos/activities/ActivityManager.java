package cn.org.act.internetos.activities;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import cn.org.act.internetos.ModuleConstructor;
import cn.org.act.internetos.UserSpace;
import cn.org.act.internetos.persist.Application;

public class ActivityManager {
	private Map<String,Activity> activities = new HashMap<String,Activity>();

	private UserSpace userspace;
	public ActivityManager(UserSpace userspace){
		this.userspace = userspace;
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
	
	public void createActivity(String name, String type) {
		if(getActivities().containsKey(name))
			getActivities().get(name).setState(Activity.Actived);
		else
			getActivities().put(name, new Activity(name,type,Activity.Actived));
	}
	
	public Activity getActivity(String name){
		return getActivities().get(name);
	}
	
	public Collection<Activity> getAllActivities(){
		return getActivities().values();
	}
	
	public void stopActivity(String name){
		getActivity(name).setState(Activity.Stopped);
	}
	
	public void killActivity(String name){
		getActivity(name).setState(Activity.Killed);
	}
}

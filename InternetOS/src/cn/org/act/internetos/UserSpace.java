package cn.org.act.internetos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import cn.org.act.internetos.activities.ActivityManager;
import cn.org.act.internetos.activities.ClientPageListener;
import cn.org.act.internetos.persist.Application;
import cn.org.act.internetos.persist.Pair;
import cn.org.act.internetos.signal.ClientSignalListener;
import cn.org.act.internetos.signal.HttpSignalListener;
import cn.org.act.internetos.signal.MatchRule;
import cn.org.act.internetos.signal.Signal;
import cn.org.act.internetos.signal.SignalListener;

public class UserSpace {
	private static HashMap<String, UserSpace> toeknSpaceMap = new HashMap<String, UserSpace>();

	public static UserSpace getUserSpace(String token) {
		if (!toeknSpaceMap.containsKey(token))
			toeknSpaceMap.put(token, new UserSpace(token));
		return toeknSpaceMap.get(token);
	}

	private String usertoken;
	private ActivityManager activityManager;
	private BlockingQueue<String> messageQueue;

	public BlockingQueue<String> getMessageQueue() {
		return messageQueue;
	}

	public void notify(String cMessage) throws IOException {
		try {
			messageQueue.put(cMessage);
		} catch (Exception ex) {
			IOException t = new IOException();
			t.initCause(ex);
			throw t;
		}
	}

	public UserSpace(String token) {
		usertoken = token;
		activityManager = new ActivityManager(this);
		messageQueue = new LinkedBlockingQueue<String>();
	}

	public List<Application> getApps(){
		 return ModuleConstructor.getAppDAO().getApps(usertoken);			
	}
	
	//TODO this function did to irrelevant work
	public List<SignalListener> getMatchedSignalListener(Signal signal) {
		//TODO  system signal
		List<SignalListener> ans = getSystemSignalListener();

		List<Application> apps = getApps();
		for (Application app : apps) {
			
			if(!app.isInited())
				app.init(this); //TODO  init a app
			
			//TODO activeListeners
			this.activityManager.createActivity(app.getName(), "listener");
			
			for (SignalListener listener : app
					.getListeners()) {
				if (listener.match(signal))
					ans.add(listener);
			}
		}
		
		
		return ans;
	}
	
	private List<SignalListener> getSystemSignalListener(){
		List<SignalListener> ans = new ArrayList<SignalListener>();
		ans.add(new ClientPageListener(this));
		
		return ans;
	}
	

	public ActivityManager getActivityManager() {

		return activityManager;
		
	}
	
}

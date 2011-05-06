package cn.org.act.internetos.activities;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ActivityManager {
	private Map<String,Activity> activities = new HashMap<String,Activity>();
	

	public void createActivity(String name, String type) {
		activities.put(name, new Activity(name,type,Activity.Actived));
	}
	
	public Activity getActivity(String name){
		return activities.get(name);
	}
	
	public Collection<Activity> getAllActivities(){
		return activities.values();
	}
}

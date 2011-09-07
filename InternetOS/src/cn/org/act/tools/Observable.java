package cn.org.act.tools;

import java.util.ArrayList;


public class Observable {
	private ArrayList<ObjectChangedListener> handlers = 
		new ArrayList<ObjectChangedListener>();
	
	public void addListener(ObjectChangedListener listener){
		handlers.add(listener);
	}
	
	public boolean removeListener(ObjectChangedListener listener){
		return handlers.remove(listener);
	}
	
	protected void onChanged(Object oldstate){
		for(ObjectChangedListener listener : handlers){
			listener.onChanged(this, oldstate);
		}
	}
}

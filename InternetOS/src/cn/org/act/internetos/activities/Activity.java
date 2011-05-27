package cn.org.act.internetos.activities;

import java.util.ArrayList;

import cn.org.act.tools.Observable;


public class Activity extends Observable{
	private String name;
	private String type;
	private String state;
	private int count;
	
	public static final String Actived = "actived";
	public static final String Stopped = "stopped"; 
	public static final String Killed = "killed";
	

	public Activity(String name,String type,String state)
	{
		this.name = name;
		this.type = type;
		this.setState(state);
		this.count = 1;
	}
	
	public void increaseCount(){
		this.count++;
		if(this.count > 0)
			setState(Actived);
	}
	
	public void decreaseCount(){
		this.count--;
		if(this.count <= 0)
			setState(Stopped);
	}
	
	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		String oldstate = state;
		this.state = state;
		super.onChanged(oldstate);
	}
}

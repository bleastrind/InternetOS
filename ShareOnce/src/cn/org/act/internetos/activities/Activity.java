package cn.org.act.internetos.activities;


public class Activity {
	private String name;
	private String type;
	private String state;
	
	public static final String Actived = "actived";
	public static final String Stopped = "stopped"; 
	public static final String Killed = "killed";
	
	public Activity(String name,String type,String state)
	{
		this.setName(name);
		this.setType(type);
		this.setState(state);
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getState() {
		return state;
	}
	

}

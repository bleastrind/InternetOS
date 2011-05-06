package cn.org.act.internetos.activities;


public class Activity {
	private String name;
	private String type;
	private String state;
	
	public static final String Actived = "actived"; 
	public Activity(String name,String type,String state)
	{
		this.name = name;
		this.type = type;
		this.state = state;
	}
	

}

package cn.org.act.internetos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import cn.org.act.internetos.activities.Activity;
import cn.org.act.internetos.activities.ActivityManager;
import cn.org.act.internetos.activities.ClientPageListener;
import cn.org.act.internetos.persist.Application;
import cn.org.act.internetos.persist.Pair;
import cn.org.act.internetos.signal.ClientSignalListener;
import cn.org.act.internetos.signal.HttpSignalListener;
import cn.org.act.internetos.signal.MatchRule;
import cn.org.act.internetos.signal.Signal;
import cn.org.act.internetos.signal.SignalListener;
import cn.org.act.tools.Observable;

public class UserSpace extends Observable{
	/**
	 * @tag group
	 * name = "私有对象"
	 */
	private static HashMap<String, UserSpace> toeknSpaceMap = new HashMap<String, UserSpace>();

	public static UserSpace getUserSpace(String token) {
		if (!toeknSpaceMap.containsKey(token))
			toeknSpaceMap.put(token, new UserSpace(token));
		return toeknSpaceMap.get(token);
	}

	/**
	 * @tag group
	 * name = "私有对象"
	 */
	private String usertoken;
	/**
	 * @tag group
	 * name = "私有对象"
	 */
	private ActivityManager activityManager;
	/**
	 * @tag group
	 * name = "私有对象"
	 */
	private BlockingQueue<String> messageQueue;

	/**
	 * @tag group
	 * name = "客户端相关"
	 */
	public BlockingQueue<String> getMessageQueue() {
		return messageQueue;
	}

	/**
	 * @tag group
	 * name = "客户端相关"
	 */
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
	/**
	 * @tag group
	 * name = "应用管理相关"
	 */
	public List<Application> getApps(){
		 return ModuleConstructor.getAppDAO().getApps(usertoken);			
	}
	/**
	 * @tag group
	 * name = "应用管理相关"
	 */
	private void tes(){
		
	}
	/**
	 * @tag group
	 * name = "活动维护相关"
	 */
	private void t(){
		
	}
	
	//TODO this function did to irrelevant work
	/**
	 * @tag group
	 * name = "信号转发相关"
	 */
	public List<SignalListener> getMatchedSignalListener(Signal signal) {
		//TODO  system signal
		List<SignalListener> ans = getSystemSignalListener();

		List<Application> apps = getApps();
		for (Application app : apps) {
			
			if(!app.isInited())
				app.init(this); //TODO  init a app
			
			//TODO activeListeners
			Activity activity = new Activity(app.getName(),"listener",Activity.Stopped);
			this.activityManager.setActivity(activity);
			
			for (SignalListener listener : app
					.getListeners()) {
				if (listener.match(signal))
					ans.add(listener);
			}
		}
		
		
		return ans;
	}
	
	/**
	 * @tag group
	 * name = "信号转发相关"
	 */
	private List<SignalListener> getSystemSignalListener(){
		List<SignalListener> ans = new ArrayList<SignalListener>();
		ans.add(new ClientPageListener(this));
		
		return ans;
	}
	

	/**
	 * @tag group
	 * name = "活动维护相关"
	 */
	public ActivityManager getActivityManager() {
		return activityManager;		
	}

	/**
	 * @tag group
	 * name = "私有对象"
	 */
	private boolean clientAlive = false;
	
	/**
	 * @tag group
	 * name = "客户端相关"
	 */
	public boolean isClientActived() {
		return clientAlive;
	}

	/**
	 * @tag group
	 * name = "客户端相关"
	 */
	public void clientTick() {
		clientAlive = true;
		super.onChanged("clientAlive");
	}

	/**
	 * @tag group
	 * name = "客户端相关"
	 */
	public void waitingClient() {
		clientAlive = true;
		new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				clientAlive = false;
			}}).run();
	}
	
}

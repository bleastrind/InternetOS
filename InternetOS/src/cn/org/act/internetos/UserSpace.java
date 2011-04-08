package cn.org.act.internetos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.org.act.internetos.signal.Signal;
import cn.org.act.internetos.signal.SignalListener;

public class UserSpace {
	private static HashMap<String,UserSpace> toeknSpaceMap = new HashMap<String,UserSpace>();
	

	public static UserSpace getUserSpace(String token){
		if(!toeknSpaceMap.containsKey(token))
			toeknSpaceMap.put(token, new UserSpace(token));
		return toeknSpaceMap.get(token);
	}

	private String usertoken;
	private List<SignalListener> listeners;
	
	public UserSpace(String token) {
		usertoken = token;
	}

	
	public List<SignalListener> getMatchedSignalListener(Signal signal){
		List<SignalListener> ans = new ArrayList<SignalListener>();
		for(SignalListener listener:listeners){
			if(listener.match(signal))
				ans.add(listener);
		}
		return ans;
	}
}

package cn.org.act.internetos.persist;

import java.util.ArrayList;
import java.util.List;

import cn.org.act.internetos.signal.ConfigParser;
import cn.org.act.internetos.signal.MatchRule;
import cn.org.act.internetos.signal.SignalListener;

public class Application {
	private String user;
	private String name;
	private String config;
	private ArrayList<Pair<MatchRule,SignalListener>> matchListeners;
	
	public Application(String user,String name, String config) {
		this.setUser(user);
		this.setName(name);
		this.setConfig(config);
		matchListeners = new ArrayList<Pair<MatchRule,SignalListener>>();
		ConfigParser.Parse(matchListeners, config);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public String getConfig() {
		return config;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getUser() {
		return user;
	}

	public List<Pair<MatchRule,SignalListener>> getMatchListeners() {
		return matchListeners;
	}
	
}

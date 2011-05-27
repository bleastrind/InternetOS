package cn.org.act.internetos.signal;

import java.io.IOException;
import java.io.OutputStream;

import cn.org.act.internetos.UserSpace;

public abstract class SignalListener {

	private MatchRule rule;
	public SignalListener(MatchRule rule){
		this.rule = rule;
	}
	public abstract void accept(Signal signal,OutputStream resultStream) throws IOException;
	
	public abstract boolean isEventRecieveReady(UserSpace userspace);
	
	public boolean match(Signal signal) {
		
		return rule.match(signal);
	}
}

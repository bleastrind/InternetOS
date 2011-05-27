package cn.org.act.internetos.signal;

import java.io.IOException;
import java.io.OutputStream;

import cn.org.act.internetos.Settings;
import cn.org.act.internetos.UserSpace;
import cn.org.act.internetos.api.client.EventComet;

public class ClientSignalListener extends SignalListener{

	private UserSpace userSpace;
	private String signaltype;
	public ClientSignalListener(UserSpace space,String signaltype, MatchRule rule)
	{
		super(rule);
		this.userSpace =  space;
		this.signaltype = signaltype;
	}
	@Override
	public void accept(Signal signal, OutputStream resultStream)
			throws IOException {
		signal.getHeaders().put(Settings.HEADERS_SIGNAL_CLIENT, this.signaltype);
		userSpace.notify(signal.toString());
		
	}
	@Override
	public boolean isEventRecieveReady(UserSpace userspace) {
		// TODO Auto-generated method stub
		return userspace.isClientActived();
	}

}

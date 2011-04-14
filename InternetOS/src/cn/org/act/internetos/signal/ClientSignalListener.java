package cn.org.act.internetos.signal;

import java.io.IOException;
import java.io.OutputStream;

import cn.org.act.internetos.UserSpace;
import cn.org.act.internetos.api.client.EventComet;

public abstract class ClientSignalListener extends SignalListener{

	private UserSpace userSpace;
	
	public ClientSignalListener(UserSpace space)
	{
		this.userSpace =  space;
	}
	@Override
	public void accept(Signal signal, OutputStream resultStream)
			throws IOException {
		userSpace.notify(signal.toString());
		
	}

}

package cn.org.act.internetos.signal;

import java.io.IOException;
import java.io.OutputStream;

import cn.org.act.internetos.api.client.EventComet;

public class ClientSignalListener extends SignalListener{

	@Override
	public void accept(Signal signal, OutputStream resultStream)
			throws IOException {
		EventComet.notify(signal.toString());
		
	}

}

package cn.org.act.internetos.activities;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import cn.org.act.internetos.UserSpace;
import cn.org.act.internetos.signal.Signal;
import cn.org.act.internetos.signal.SignalListener;
import cn.org.act.internetos.signal.UrlRegexMatchRule;
import cn.org.act.tools.HttpHelper;
import cn.org.act.tools.StreamHelper;

public class ClientPageListener extends SignalListener{

	private UserSpace userspace;
	
	public ClientPageListener(UserSpace userspace) {
		super(new ClientPageMatchRule());
		this.userspace = userspace;
	}

	@Override
	public void accept(Signal signal, OutputStream resultStream)
			throws IOException {
		String url = StreamHelper.readStream(signal.getData());
		String name = HttpHelper.getHostApplication(url);
		
		userspace.getActivityManager().addActivity(name, "clientpage");
	}

	@Override
	public boolean isEventRecieveReady(UserSpace userspace) {
		return true;
	}

}


class ClientPageMatchRule extends UrlRegexMatchRule
{

	public ClientPageMatchRule() {
		super("/signal/clientsignal/pageload");
	}
	
	
}
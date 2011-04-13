package cn.org.act.internetos.signal;

import java.io.IOException;
import java.io.OutputStream;

public abstract class SignalListener {
	public boolean match(Signal signal)
	{
		return true;
		
	}
	public abstract void accept(Signal signal,OutputStream resultStream) throws IOException;
}

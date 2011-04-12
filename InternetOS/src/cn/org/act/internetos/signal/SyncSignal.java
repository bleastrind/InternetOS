package cn.org.act.internetos.signal;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class SyncSignal extends Signal{

	@Override
	public void sendTo(List<SignalListener> listeners, OutputStream result) throws IOException {

		for(SignalListener listener:listeners){
			//TODO combine with frames
			listener.accept(this, result);
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "SyncSignal";
	}

}

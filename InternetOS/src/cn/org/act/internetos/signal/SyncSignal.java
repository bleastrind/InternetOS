package cn.org.act.internetos.signal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import cn.org.act.tools.StreamHelper;

public class SyncSignal extends Signal{

	private boolean hasMultiListeners = false;
	@Override
	public void sendTo(List<SignalListener> listeners, OutputStream result) throws IOException {

		if(listeners.size() > 1){
			makeDataRereadable();
		}
		for(SignalListener listener:listeners){
			listener.accept(this, result);
			
			if(hasMultiListeners)
				this.getData().reset();
		}
	}
	
	@Override
	public InputStream getData() throws IOException{
		InputStream data = super.getData();
		if(hasMultiListeners)
			data.reset();
		return data;
		
	}
	public void makeDataRereadable(){
		hasMultiListeners = true;
		
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		
		try {
			StreamHelper.copyStream(this.getData(), bout);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.setData(new ByteArrayInputStream(bout.toByteArray()));
	}
}

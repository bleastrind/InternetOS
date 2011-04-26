package cn.org.act.internetos.signal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import cn.org.act.internetos.Settings;
import cn.org.act.tools.StreamHelper;

public class AsyncSignal extends Signal {

	private String callback;
	private String usertoken;

	public AsyncSignal(String callbackurl,String usertoken){
		this.callback = callbackurl;
		this.usertoken = usertoken;
	}
	
	@Override
	public void sendTo(List<SignalListener> listeners, OutputStream result)
			throws IOException {
		final List<SignalListener> flisteners = listeners;
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (SignalListener listener : flisteners) {
					try {
						sendDataToCallback(listener, AsyncSignal.this);

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}).start();

		new PrintWriter(result).write("success");
	}

	protected void sendDataToCallback(SignalListener listener,
			AsyncSignal asyncSignal) throws IOException {

		OutputStream output = null;
		try {

			HttpURLConnection conn = createHttpConnection(callback);
			conn.setRequestProperty(Settings.TOKEN, usertoken);

			// Method
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// get result
			conn.connect();

			output = conn.getOutputStream();
			
			// request data
			listener.accept(this, output);
			
			//actual send data
			conn.getResponseCode();
			
			conn.disconnect();
			
		} catch (Exception e) {
			listener.accept(this, output);
			
			System.out.println("callback failed");
			e.printStackTrace();
		}
	}

	private HttpURLConnection createHttpConnection(String urlString)
			throws IOException {
		urlString = (urlString.startsWith("http://") || urlString
				.startsWith("https://")) ? urlString : ("http://" + urlString)
				.intern();
		URL url = new URL(urlString);
		return (HttpURLConnection) url.openConnection();
	}

	/**
	 * AsyncSignal has to store the inputstream as it may expire
	 */
	@Override
	public void setData(InputStream stream) throws IOException{
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		StreamHelper.copyStream(stream, bout);
		super.setData(new ByteArrayInputStream(bout.toByteArray()));
	}

}

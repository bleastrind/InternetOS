package cn.org.act.internetos.signal;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.org.act.internetos.UserSpace;
import cn.org.act.internetos.activities.Activity;
import cn.org.act.tools.HttpHelper;
import cn.org.act.tools.WebClient;

public class HttpSignalListener extends SignalListener {

	private String baseUrlString;
	public HttpSignalListener(String url, MatchRule rule) {
		super(rule);
		
		baseUrlString = url;
	}

	@Override
	public void accept(Signal signal, OutputStream resultStream)
			throws IOException {

		String urlString = baseUrlString;
		
		try {
			URI uri = new URI(signal.getUrl());
			urlString += uri.getQuery() == null ? "" : "?"+uri.getQuery();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HttpURLConnection conn = createHttpConnection(urlString);

		// Method
		setMethod(conn, signal.getMethod());

		// header
		Map<String, String> headers = signal.getHeaders();
		for (Entry<String, String> entry : headers.entrySet()) {
			conn.addRequestProperty(entry.getKey(), entry.getValue());
		}

		// get result
		conn.connect();

		// request data
		setRequestData(conn, signal);


		// write response
		if (resultStream != null)
			copyStream(conn.getInputStream(), resultStream);

		conn.disconnect();
	}

	private void setRequestData(HttpURLConnection conn, Signal signal)
			throws IOException {
		if (signal.getMethod() == "POST" || signal.getMethod() == "PUT")
			copyStream(signal.getData(), conn.getOutputStream());
	}

	private void setMethod(HttpURLConnection conn, String method) {
		try {
			conn.setRequestMethod(method);

			if (method == "POST") {
				conn.setDoOutput(true);
				conn.setDoInput(true);
				conn.setUseCaches(false);
				conn.setInstanceFollowRedirects(true);
			} else if (method == "GET") {

			} else if (method == "DELETE") {

			} else if (method == "PUT") {

			}
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
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

	private String readStream(InputStream stream, String charset)
			throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				stream, charset));
		String line;
		StringBuffer sb = new StringBuffer();
		while ((line = reader.readLine()) != null) {
			sb.append(line).append("\r\n");
		}
		if (reader != null) {
			reader.close();
		}
		return sb.toString();
	}

	private void copyStream(InputStream srcStream, OutputStream dstStream) {
		byte[] buf = new byte[100];
		int i = -1;
		try {
			while ((i = srcStream.read(buf)) != -1) {
				dstStream.write(buf, 0, i);
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("The stream is not avaliable!");
		}
	}

	@Override
	public boolean isEventRecieveReady(UserSpace userspace) {
		String appname = HttpHelper.getHostApplication(baseUrlString);
		Activity activity = userspace.getActivityManager().getActivity(appname);
		if(null != activity)
			return activity.getState() == Activity.Actived;
		else
			return false;
	}

}

package cn.org.act.internetos.signal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

public abstract class Signal {
	private int id;
	
	private String url;
	private HashMap<String,String> headers;
	private String method;
	private InputStream data;
	
	public void setHeaders(HashMap<String,String> headers) {
		this.headers = headers;
	}
	public HashMap<String,String> getHeaders() {
		return headers;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getMethod() {
		return method;
	}
	public void setData(InputStream data) throws IOException {
		this.data = data;
	}
	public InputStream getData() {
		return data;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrl() {
		return url;
	}
	public abstract void sendTo(List<SignalListener> listener,OutputStream result) throws IOException;
	public abstract String toString();
	

}



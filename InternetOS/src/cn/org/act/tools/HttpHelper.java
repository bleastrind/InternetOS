package cn.org.act.tools;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

public class HttpHelper {
	public static String getContextPath(HttpServletRequest req){
		return req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+req.getContextPath();
	}
	
	public static String getHostApplication(String url){
		String name = null;
		try {
			URI i = new URI(url);
			name = i.getHost();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return name;
	}
}

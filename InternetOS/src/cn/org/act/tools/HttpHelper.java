package cn.org.act.tools;

import javax.servlet.http.HttpServletRequest;

public class HttpHelper {
	public static String getContextPath(HttpServletRequest req){
		return req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+req.getContextPath();
	}
}

package cn.org.act.internetos.api.identifyservice;

import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;

import cn.org.act.internetos.Settings;
import cn.org.act.internetos.UserSpace;

/**
 * Servlet Filter implementation class UserIdentifier
 */
//@WebFilter(dispatcherTypes = {
//				DispatcherType.REQUEST, 
//		}
//					, urlPatterns = { "/*" })
public class UserIdentifiedServlet extends HttpServlet {

	public String getAcessToken(ServletRequest request){
		String token = request.getParameter(Settings.TOKEN);
		
		return token;
	}
}

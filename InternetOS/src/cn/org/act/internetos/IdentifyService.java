package cn.org.act.internetos;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IdentifyService{
    /**
     * Default constructor. 
     */
    public IdentifyService() {
        // TODO Auto-generated constructor stub
    }

	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String callback = request.getParameter("callback");
		response.sendRedirect(callback + "?"+
				Settings.AUTHTOKEN +
				"=" + "authtoken");
	}

	public void getToken(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.getWriter().write("token");
	}
}
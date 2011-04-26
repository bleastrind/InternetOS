package cn.org.act.internetos.api.client;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.org.act.internetos.ModuleConstructor;
import cn.org.act.internetos.Settings;

/**
 * Servlet implementation class AccessTokenMaker
 */
@WebServlet("/loginfortoken")
public class AccessTokenMaker extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccessTokenMaker() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String authtoken = ModuleConstructor.getIdentifyService().getAuthToken(
				request.getParameter("username"),request.getParameter("password")
		);
		
		response.getWriter().write(authtoken);
	}


}

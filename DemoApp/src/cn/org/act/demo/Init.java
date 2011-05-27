package cn.org.act.demo;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.org.act.tools.WebClient;

/**
 * Servlet implementation class Init
 */
@WebServlet("/Init")
public class Init extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Init() {
		super();
		// TODO Auto-generated constructor stub
	}
	private String checkUser(HttpServletRequest request) {
		String userToken = request.getHeader("token");
		userToken = userToken == null ? request.getParameter("token")
				: userToken;
		return userToken;
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (null != checkUser(request)){

			request.getSession().setAttribute(Setting.TOKEN, checkUser(request));
			
			response.sendRedirect("control.html");
		}else if(null != request.getParameter(Setting.AUTHTOKEN)){
			WebClient client = new WebClient();
			String accesstoken = client
					.getWebContentByGet(Setting.INTERNETOS + "/identifyservice/token?authtoken="+request.getParameter(Setting.AUTHTOKEN));
			System.out.println("Done "+accesstoken);
			
		}
		else {

			response.sendRedirect(Setting.INTERNETOS +"/identifyservice/login?callback=http://localhost:8080/DemoApp/Init");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

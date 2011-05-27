package cn.org.act.demo;

import java.io.IOException;
import java.net.HttpURLConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.org.act.tools.IHttpModify;
import cn.org.act.tools.WebClient;

/**
 * Servlet implementation class signaltest
 */
@WebServlet("/signaltest")
public class signaltest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public signaltest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String accesstoken = (String)request.getSession().getAttribute(Setting.TOKEN);
		if(accesstoken == null){
			response.sendRedirect("Init");
			return;
		}
		System.out.println(accesstoken);
		//test synchronous
		//response.getWriter().write(new WebClient().getWebContentByGet(Setting.INTERNETOS+"/signal/send?token="+accesstoken));
		
		//test asynchronous
		String cases = request.getParameter("case");
		if(cases.equals( "asyncalert")){
			new WebClient(new IHttpModify(){
	
				@Override
				public void handle(HttpURLConnection conn) {
					conn.addRequestProperty("client-signal", "");
					conn.addRequestProperty("clienttype","cn.org.act.internetos.clientsignal.alert");
					conn.addRequestProperty("async", "true");
					//conn.addRequestProperty("token", accesstoken);
					conn.addRequestProperty("callback", "http://localhost:8080/DemoApp/callback");
				}}).getWebContentByPost(Setting.INTERNETOS+"/signal/send?callback=http://localhost:8080/DemoApp/callback&token="+accesstoken,"This is an alert sent by DemoApp");
		}else if(cases.equals( "delayalert")){
			new WebClient(new IHttpModify(){
				
				@Override
				public void handle(HttpURLConnection conn) {
					conn.addRequestProperty("client-signal", "");
					conn.addRequestProperty("clienttype","cn.org.act.internetos.clientsignal.alert");
					conn.addRequestProperty("async", "true");
					conn.addRequestProperty("delay", "true");
					//conn.addRequestProperty("token", accesstoken);
					conn.addRequestProperty("callback", "http://localhost:8080/DemoApp/callback");
				}}).getWebContentByPost(Setting.INTERNETOS+"/signal/send?callback=http://localhost:8080/DemoApp/callback&token="+accesstoken,"This is an alert sent by DemoApp");
		
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

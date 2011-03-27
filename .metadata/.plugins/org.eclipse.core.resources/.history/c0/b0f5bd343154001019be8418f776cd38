package cn.org.act.internetos;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignalDispatcher {

	public void sendSignal(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String token = request.getParameter(Settings.TOKEN);
		String data = request.getReader().readLine();
		
		response.sendRedirect("http://localhost:8080/DemoApp/Init");
	}
}

package cn.org.act.internetos;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.org.act.internetos.activities.DelaySignal;
import cn.org.act.internetos.api.client.EventComet;
import cn.org.act.internetos.api.identifyservice.UserIdentifiedServlet;
import cn.org.act.internetos.signal.AsyncSignal;
import cn.org.act.internetos.signal.HttpSignalListener;
import cn.org.act.internetos.signal.Signal;
import cn.org.act.internetos.signal.SignalListener;
import cn.org.act.internetos.signal.SyncSignal;
import cn.org.act.tools.IHttpModify;
import cn.org.act.tools.WebClient;

public class SignalDispatcher extends UserIdentifiedServlet {

	public void sendSignal(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String token = request.getParameter(Settings.TOKEN);
		// final String data = request.getReader().readLine();

		UserSpace space = getUserSpace(request);
		Signal signal = createSignal(space, request, token);

		// signal.getData().reset();
		//System.out.println(signal);

		List<SignalListener> list = space.getMatchedSignalListener(signal);

		signal.sendTo(list, response.getOutputStream());

	}

	private Signal createSignal(UserSpace userspace,
			HttpServletRequest request, String userToken) throws IOException {
		Signal res = null;

		// construct signal
		if (request.getHeader("async") != null) {
			res = new AsyncSignal(request.getParameter("callback"), userToken);
		} else {
			res = new SyncSignal();
		}

		fillData(request, res);
		
		// wrap
		if (request.getHeader("delay") != null) {
			res = new DelaySignal(userspace, res);
		}
		
		return res;
	}

	private void fillData(HttpServletRequest request, Signal res)
			throws IOException {
		// make the query string
		String query = "";
		Enumeration<String> names = request.getParameterNames();

		while (names.hasMoreElements()) {
			String key = names.nextElement();
			query += key + "=" + request.getParameter(key) + "&";
		}
		String url = request.getRequestURL().toString();
		if (!"".equals(query))
			url += "?" + query;
		res.setUrl(url);

		// data
		res.setData(request.getInputStream());

		// headers
		HashMap<String, String> headers = new HashMap<String, String>();
		Enumeration<String> enumeration = request.getHeaderNames();
		while (enumeration.hasMoreElements()) {
			String k = enumeration.nextElement();
			headers.put(k, request.getHeader(k));
		}
		res.setHeaders(headers);
		// method
		res.setMethod(request.getMethod());
	}

}

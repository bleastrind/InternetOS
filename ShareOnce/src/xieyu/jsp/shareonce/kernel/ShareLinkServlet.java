package xieyu.jsp.shareonce.kernel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.org.act.internetos.activities.Activity;
import cn.org.act.internetos.toolkit.QueryInterface;

/**
 * Servlet implementation class ShareLinkServlet
 */
@WebServlet("/ShareLinkServlet")
public class ShareLinkServlet extends HttpServlet {
	private static int max_app = 50; // The maximum number of apps
	private static final String baseUrl = "http://localhost:8080/InternetOS"; // The
																							// url
	private QueryInterface queryInterface;																						// for
																						// at
																							// init()
	// private String userID; // The user who requests
	// private String urlRef; // The url for sharing

	// private ArrayList<String> appNamesArray; // Store the app names get from
	// the query url
	// private ArrayList<String> redirectUrlArray;

	private ArrayList<ShareLink> shareLinkArray;

	private static String sina = "http://v.t.sina.com.cn/share/share.php?url=";
	private static String renren = "http://share.renren.com/share/buttonshare.do?link=";
	private static String qq = "http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?url=";
	private static String pengyou = "http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?to=pengyou&url=";
	private static String qzone = "http://v.t.qq.com/share/share.php?url=";
	private static String mop = "http://tt.mop.com/share/shareV.jsp?pageUrl=";
	private static String msn = "http://profile.live.com/badge/?url=";
	private static String douban = "http://www.douban.com/recommend/?url=";
	private static String kaixin = "http://www.kaixin001.com/repaste/share.php?rurl%=";
	private static String fetion = "http://space.fetion.com.cn/api/share?url=";
	private static String w163 = "http://t.163.com/article/user/checkLogin.do?link=";
	private static String tianya = "http://www.tianya.cn/new/share/compose.asp?itemtype=tech&item=665&strFlashPageURL=";
	private static String ibaidu = "http://tieba.baidu.com/i/app/open_share_api?link=";

	/**
	 * Constructor of the object.
	 */
	public ShareLinkServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String userToken = checkUser(request);

		String urlRef = request.getParameter("shareUrl");
		System.out.println("User Token: " + userToken);
		System.out.println("Share URL: " + urlRef);
		// Prepare the data
		ArrayList<String> appNamesArray = getAppNamesArray(userToken);
		ArrayList<String> redirectUrlArray = getRedirectUrlArray(appNamesArray,
				urlRef);
		// Then make the return page
		PrintWriter out = response.getWriter();
		render(redirectUrlArray, out);
		// Just for debugging
		System.out.println();
		System.out.println("Application Names: ");

		for (int i = 0; i < appNamesArray.size(); i++) {
			System.out.println(i + ": " + appNamesArray.get(i));
		}
		System.out.println();
		System.out.println("Redirect Urls: ");
		for (int i = 0; i < redirectUrlArray.size(); i++) {
			System.out.println(i + ": " + redirectUrlArray.get(i));
		}
	}

	private void render(ArrayList<String> redirectUrlArray, PrintWriter out) {
		out.println("<html>");
		out.println("<head>");
		out.println("\t<script type=\"text/javascript\">");

		for (int i = 1; i < redirectUrlArray.size(); i++) {
			out.println("\t\twindow.open(\"" + redirectUrlArray.get(i) + "\")");
		}
		if (redirectUrlArray.size() > 0) {
			out.println("\t\twindow.location.replace(\""
					+ redirectUrlArray.get(0) + "\")");
		} else {
			out.println("\t\talert(\"You haven't registered any web sites jet!\")");
		}
		out.println("\t</script>");
		out.println("</head>");
		out.println("</html>");
	}

	private ArrayList<String> getAppNamesArray(String userToken) {
		ArrayList<String> res = new ArrayList<String>();
		for (Activity activity : queryInterface.queryAllActivities(userToken)) {
			res.add(activity.getName());
		}
		return res;
	}

	private String checkUser(HttpServletRequest request) {
		String userToken = request.getHeader("token");
		userToken = userToken == null ? request.getParameter("token")
				: userToken;
		return userToken;
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

	/**
	 * Initialisation of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {

		this.queryInterface = new QueryInterface(baseUrl);
		// this.urlRef = "";
		// this.userID = "";
		this.shareLinkArray = new ArrayList<ShareLink>();
		// Now initiate the share link array
		this.shareLinkArray.add(new ShareLink("sina", sina));
		this.shareLinkArray.add(new ShareLink("renren", renren));
		this.shareLinkArray.add(new ShareLink("qq", qq));
		this.shareLinkArray.add(new ShareLink("pengyou", pengyou));
		this.shareLinkArray.add(new ShareLink("qzone", qzone));
		this.shareLinkArray.add(new ShareLink("mop", mop));
		this.shareLinkArray.add(new ShareLink("msn", msn));
		this.shareLinkArray.add(new ShareLink("douban", douban));
		this.shareLinkArray.add(new ShareLink("kaixin", kaixin));
		this.shareLinkArray.add(new ShareLink("fetion", fetion));
		this.shareLinkArray.add(new ShareLink("163", w163));
		this.shareLinkArray.add(new ShareLink("tianya", tianya));
		this.shareLinkArray.add(new ShareLink("baidu", ibaidu));
	}



	/*
	 * Given the app names, get the redirect url from the data structures The
	 * given app names might be like www.renren.com Thus we need to compare
	 */
	private ArrayList<String> getRedirectUrlArray(
			ArrayList<String> appNamesArray, String urlRef) {
		ArrayList<String> redirectUrlArray = new ArrayList<String>();
		for (String appName : appNamesArray) {
			// System.out.println(appName);
			for (ShareLink link : this.shareLinkArray) {
				// System.out.println("\t" + link.getName());
				/*
				 * if(appName.equals(link.getName())) {
				 * this.redirectUrlArray.add(link.getFullLink(this.urlRef));
				 * break; }
				 */
				if (appName.contains(link.getName())) {
					redirectUrlArray.add(link.getFullLink(urlRef));
					break;
				}
			}
		}

		return redirectUrlArray;
	}

	// /* Given a string consist of several app names separated by '|', get the
	// app names */
	// private void getAppNamesByString(String s) {
	// int head = 0, tail = 0;
	// System.out.print(s);
	// while(true) {
	// int separate = s.indexOf('|', head);
	// if(separate == -1)
	// break;
	// head = separate;
	// String appName =s.substring(tail, head);
	// this.appNamesArray.add(appName);
	// tail = ++head;
	// }
	// /* Remember to get the last( maybe the first ) app name */
	// this.appNamesArray.add(s.substring(tail, s.length()));
	// }


}

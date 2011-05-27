package xieyu.jsp.shareonce.kernel;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.org.act.internetos.activities.Activity;
import cn.org.act.internetos.toolkit.QueryInterface;

/**
 * Servlet implementation class ShareLinkServlet
 */
@WebServlet("/ShareLinkServlet")
public class ShareLinkServlet extends HttpServlet {
	private static final String baseUrl = "http://localhost:8080/InternetOS"; // The
	
	private static final String sina = "http://v.t.sina.com.cn/share/share.php?url=";
	private static final String renren = "http://share.renren.com/share/buttonshare.do?link=";
	private static final String qq = "http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?url=";
	private static final String pengyou = "http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?to=pengyou&url=";
	private static final String qzone = "http://v.t.qq.com/share/share.php?url=";
	private static final String mop = "http://tt.mop.com/share/shareV.jsp?pageUrl=";
	private static final String msn = "http://profile.live.com/badge/?url=";
	private static final String douban = "http://www.douban.com/recommend/?url=";
	private static final String kaixin = "http://www.kaixin001.com/repaste/share.php?rurl%=";
	private static final String fetion = "http://space.fetion.com.cn/api/share?url=";
	private static final String w163 = "http://t.163.com/article/user/checkLogin.do?link=";
	private static final String tianya = "http://www.tianya.cn/new/share/compose.asp?itemtype=tech&item=665&strFlashPageURL=";
	private static final String ibaidu = "http://tieba.baidu.com/i/app/open_share_api?link=";

	// The toolkit of interface to access internetOS
	private QueryInterface queryInterface;																						
	
	// All the share links												
	private ArrayList<ShareLink> shareLinkArray;

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
		// Prepare the data
		ArrayList<String> appNamesArray = getAppNamesArray(userToken);
		ArrayList<String> redirectUrlArray = getRedirectUrlArray(appNamesArray,
				urlRef);
		// Then make the return page
		render(redirectUrlArray, response.getWriter());
		
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


	/*
	 * Given the app names, get the redirect url from the data structures The
	 * given app names might be like www.renren.com Thus we need to compare
	 */
	private ArrayList<String> getRedirectUrlArray(
			ArrayList<String> appNamesArray, String urlRef) {
		ArrayList<String> redirectUrlArray = new ArrayList<String>();
		for (String appName : appNamesArray) {
			for (ShareLink link : this.shareLinkArray) {
				if (appName.contains(link.getName())) {
					redirectUrlArray.add(link.getFullLink(urlRef));
					break;
				}
			}
		}
		return redirectUrlArray;
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
}

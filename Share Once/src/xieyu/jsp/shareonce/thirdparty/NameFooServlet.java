package xieyu.jsp.shareonce.thirdparty;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NameFooServlet extends HttpServlet {
	private String[] appNames = {
			"www.sina.com",
			"www.renren.com",
			"www.qq.com",
			"www.pengyou.com",
			"www.qzone.com",
			"www.mop.com",
			"www.msn.com",
			"www.douban.com",
			"www.kaixin.com",
			"www.fetion.com",
			"www.163.com",
			"www.tianya.com",
			"www.ibaidu.com"
	};
	private String finalString = "";
	
	/**
	 * Constructor of the object.
	 */
	public NameFooServlet() {
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
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		makeString();
		OutputStream os = response.getOutputStream();
		os.write(finalString.getBytes("utf-8"));
		os.flush();
		os.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}
	
	private void makeString() {
		finalString = "";
		long currentTime = System.currentTimeMillis();
		int appNum = (int)(currentTime % (long)(this.appNames.length + 1));
		int appGet[] = new int[this.appNames.length];
		for(int i = 0; i < this.appNames.length; i++)
			appGet[i] = 0;
		while(appNum > 0) {
			currentTime = System.currentTimeMillis();
			int trial = (int)(currentTime % (long)this.appNames.length);
			if(appGet[trial] == 0) {
				appGet[trial] = 1;
				if(finalString == "")
					finalString += appNames[trial];
				else finalString += "|" + appNames[trial];
				appNum--;
			} else {
				continue;
			}
		}
	}
}

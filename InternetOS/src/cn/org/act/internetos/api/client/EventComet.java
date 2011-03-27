package cn.org.act.internetos.api.client;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EventComet
 */
@WebServlet(urlPatterns={"/clientevent"},asyncSupported=true)
public class EventComet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final AsyncContext aCtx = request.startAsync(request, response);
		
		aCtx.addListener(new AsyncListener(){

			@Override
			public void onComplete(AsyncEvent arg0) throws IOException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onError(AsyncEvent arg0) throws IOException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStartAsync(AsyncEvent arg0) throws IOException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTimeout(AsyncEvent arg0) throws IOException {
				arg0.getAsyncContext().getResponse().getWriter().write("time out");
				arg0.getAsyncContext().complete();
				
			}});
		
		new Thread( new Runnable(){
			
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
					PrintWriter out = aCtx.getResponse().getWriter();
					out.write("1");
					Thread.sleep(1000);
					out.write("2");
					aCtx.complete();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}).start();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

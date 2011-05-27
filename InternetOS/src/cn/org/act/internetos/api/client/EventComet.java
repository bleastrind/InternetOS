package cn.org.act.internetos.api.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.org.act.internetos.Settings;
import cn.org.act.internetos.UserSpace;
import cn.org.act.internetos.api.identifyservice.UserIdentifiedServlet;

/**
 * Servlet implementation class EventComet
 */
@WebServlet(urlPatterns={"/clientevent"},asyncSupported=true)
public class EventComet extends UserIdentifiedServlet {
	private static final long serialVersionUID = 1L;
     
	private ExecutorService executorService = Executors.newFixedThreadPool(10);
//	private static final Queue<AsyncContext> asyncContextQueue = new ConcurrentLinkedQueue<AsyncContext>();

	
//	private Thread notifierThread = null;  
	
	public void init(){
//		Runnable notifierRunnable = new Runnable() {  
//            public void run() {  
//                boolean done = false;  
//                while (!done) {  
//                    String cMessage = null;  
//                    try {  
//                        cMessage = messageQueue.take();  
//                        for (AsyncContext ac : asyncContextQueue) {  
//                            try {  
//                                PrintWriter acWriter = ac.getResponse().getWriter();  
//                                acWriter.println(cMessage);  
//                                acWriter.flush();  
//                            } catch(IOException ex) {  
//                                System.out.println(ex);  
//                                asyncContextQueue.remove(ac);  
//                            }  
//                        }  
//                    } catch(InterruptedException iex) {  
//                        done = true;  
//                        System.out.println(iex);  
//                    }  
//                }  
//            }  
//        };  
//        notifierThread = new Thread(notifierRunnable);  
//        notifierThread.start();  
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserSpace space = getUserSpace(request);
		
		final AsyncContext aCtx = request.startAsync(request, response);

		final Future<?> task = executorService.submit(new AsyncRequest(aCtx,space));
		aCtx.addListener(new AsyncListener(){

			@Override
			public void onComplete(AsyncEvent arg0) throws IOException {
//				asyncContextQueue.remove(aCtx);				
			}

			@Override
			public void onError(AsyncEvent arg0) throws IOException {
//				asyncContextQueue.remove(aCtx);
			}

			@Override
			public void onStartAsync(AsyncEvent arg0) throws IOException {

			}

			@Override
			public void onTimeout(AsyncEvent arg0) throws IOException {
				arg0.getAsyncContext().getResponse().getWriter().write("time out");
				arg0.getAsyncContext().complete();
				task.cancel(true);
//				asyncContextQueue.remove(aCtx);
			}
			});
		space.clientTick();
		
	}
	
	@Override  
    public void destroy() {  
	//	asyncContextQueue.clear();  
    //    notifierThread.interrupt();  
    }  
}

class AsyncRequest implements Runnable{

	private AsyncContext aCtx;
	private BlockingQueue<String> messageQueue;
	private UserSpace userspace;
	public AsyncRequest(AsyncContext aCtx,UserSpace space) {
		this.aCtx = aCtx;
		this.messageQueue = space.getMessageQueue();
		this.userspace = space;
	}

	@Override
	public void run() {
		
		String cMessage = null;  
        try {  
            cMessage = messageQueue.take();  

            try {  
                PrintWriter acWriter = aCtx.getResponse().getWriter();  
                acWriter.println(cMessage);  
                acWriter.flush();  
                aCtx.complete();
            } catch(Exception ex) {  
                System.out.println(ex);  
            }  

        } catch(InterruptedException iex) {  
        	if(cMessage != null)
        		while(true){
				try {
					messageQueue.put(cMessage); // Warning: may failed that missing a signal
					break;
				} catch (InterruptedException e) {
					
				}
        		}
            System.out.println(iex);  
        }  
        
        userspace.waitingClient();
		
	}
	
}

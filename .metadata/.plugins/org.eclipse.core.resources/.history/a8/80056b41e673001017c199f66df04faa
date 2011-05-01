package cn.org.act.internetos.identify;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

import cn.org.act.internetos.Settings;
import cn.org.act.internetos.UserSpace;

/**
 * Application Lifecycle Listener implementation class User
 *
 */
@WebListener
public class User implements ServletRequestListener {

    /**
     * Default constructor. 
     */
    public User() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletRequestListener#requestDestroyed(ServletRequestEvent)
     */
    public void requestDestroyed(ServletRequestEvent arg0) {
        // TODO Auto-generated method stub
    }

	/**
     * @see ServletRequestListener#requestInitialized(ServletRequestEvent)
     */
    public void requestInitialized(ServletRequestEvent arg0) {
        String token = arg0.getServletRequest().getParameter(Settings.TOKEN);
        
        UserSpace space = UserSpace.getUserSpace(token);
        
        arg0.getServletRequest().setAttribute(Settings.USERPACE,space);
    }
	
}

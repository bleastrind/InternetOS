package cn.org.act.internetos.api.activities;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

import cn.org.act.internetos.UserSpace;
import cn.org.act.internetos.api.identifyservice.UserIdentifiedServlet;

/**
 * Servlet implementation class QueryActivities
 */
@WebServlet("/QueryActivities")
public class QueryActivities extends UserIdentifiedServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryActivities() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserSpace space = getUserSpace(request);
		String name = request.getParameter("activityName");
		
		Object result = null;
		if(name != null){
			result = space.getActivityManager().getActivity(name);
		}else
		{
			result = space.getActivityManager().getAllActivities();
		}
		
			JsonHierarchicalStreamDriver jsondriver = new JsonHierarchicalStreamDriver(){
				public HierarchicalStreamWriter createWriter(Writer writer) {
			        return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
			    }
			};
			
			XStream xstream = new XStream(jsondriver);
			xstream.setMode(XStream.NO_REFERENCES);
		String output = xstream.toXML(result);
		
		response.getWriter().print(output);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

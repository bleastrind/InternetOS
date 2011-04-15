package cn.org.act.internetos.persist.memory;

import java.util.ArrayList;
import java.util.List;

import cn.org.act.internetos.persist.Application;
import cn.org.act.internetos.persist.IAppDAO;
import cn.org.act.tools.collection.Extension;
import cn.org.act.tools.collection.Predicate;

public class AppMemoryDAO implements IAppDAO{

	private static List<Application> data = new ArrayList<Application>();
	@Override
	public String addApp(Application app) {
		// TODO Auto-generated method stub
		
		data.add(app);
		return "";
	}
	@Override
	public List<Application> getApps(String user) {
		final String curuser = user;
		// TODO Auto-generated method stub
		return Extension.filter(data, new Predicate<Application>(){

			@Override
			public boolean apply(Application type) {
				// TODO Auto-generated method stub
				String appuser = type.getUser();
				boolean same = appuser.equals(curuser);
				return same;
			}});
	}

}

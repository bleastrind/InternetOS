package cn.org.act.internetos.persist;

import java.util.List;

public interface IAppDAO {
	public List<Application> getApps(String user);
	public int addApp(Application app);
}

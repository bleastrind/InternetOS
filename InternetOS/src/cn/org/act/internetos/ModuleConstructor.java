package cn.org.act.internetos;

import cn.org.act.internetos.identify.IdentifyService;
import cn.org.act.internetos.persist.IAppDAO;
import cn.org.act.internetos.persist.cassandra.AppCassandraDAO;
import cn.org.act.internetos.persist.memory.AppMemoryDAO;

public class ModuleConstructor {
	
	public static IdentifyService getIdentifyService(){
		return new IdentifyService();
	}
	public static ProcessManager getProcessManager(){
		return new ProcessManager();
	}
	public static SignalDispatcher getSignalDispather(){
		return new SignalDispatcher();
	}
	public static IAppDAO getAppDAO(){
		//return new AppMemoryDAO();
		return new AppCassandraDAO("localhost:9160");
	}
}

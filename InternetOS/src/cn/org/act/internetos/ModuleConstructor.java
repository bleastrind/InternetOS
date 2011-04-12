package cn.org.act.internetos;

import cn.org.act.internetos.identify.IdentifyService;

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
}

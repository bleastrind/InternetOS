package cn.org.act.internetos.manage.app;

import org.dom4j.Element;

import cn.org.act.internetos.signal.HttpSignalListener;
import cn.org.act.internetos.signal.MatchRule;
import cn.org.act.internetos.signal.SignalListener;

public class ListenerFactory {

	public static SignalListener createListener(Element listener) {
		String listenertype = listener.getName();
		if (listener.getName() == "HttpListener")
			return createHttpListenr(listener);
		else
			return null;
	}

	private static SignalListener createHttpListenr(Element listenerElement) {
		String url = listenerElement.elementText("URL");
		MatchRule rule = MatchRuleFactory.createMatchRule(listenerElement
				.element("MatchRule"));
		return new HttpSignalListener(url,rule);
	}

}

package cn.org.act.internetos.signal;

import java.util.List;

import cn.org.act.internetos.persist.Pair;

public class ConfigParser {
	public static void Parse(
			List<Pair<MatchRule, SignalListener>> matchListeners, String config) {
		matchListeners.add(new Pair<MatchRule, SignalListener>(new MatchRule() {

			@Override
			public boolean match(Signal signal) {
				// TODO Auto-generated method stub
				return true;
			}

		}, new HttpSignalListener("http://localhost:8080/DemoApp/listener")));

	}
}

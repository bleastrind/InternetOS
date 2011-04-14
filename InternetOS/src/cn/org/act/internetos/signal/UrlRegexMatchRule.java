package cn.org.act.internetos.signal;

import java.util.regex.Pattern;

public class UrlRegexMatchRule extends MatchRule {

	private Pattern pattern;
	public UrlRegexMatchRule(String regex){
		pattern = Pattern.compile(regex);
	}
	@Override
	public boolean match(Signal signal) {
		
		return pattern.matcher(signal.getUrl()).matches();
	}

}

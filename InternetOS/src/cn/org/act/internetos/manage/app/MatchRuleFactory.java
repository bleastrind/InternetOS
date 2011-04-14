package cn.org.act.internetos.manage.app;

import org.dom4j.Element;

import cn.org.act.internetos.signal.MatchRule;
import cn.org.act.internetos.signal.UrlRegexMatchRule;

public class MatchRuleFactory {
	public static MatchRule createMatchRule(Element matchrule)
	{
		if(matchrule.attributeValue("type")== "urlregex")
			return new UrlRegexMatchRule(matchrule.getTextTrim());
		else
			return MatchRule.MatchAll;
	}
}

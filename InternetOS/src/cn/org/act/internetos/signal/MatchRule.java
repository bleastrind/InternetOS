package cn.org.act.internetos.signal;

public abstract class MatchRule {
	public static MatchRule MatchAll = new MatchRule(){

		@Override
		public boolean match(Signal signal) {
			return true;
		}
		
	};
	public abstract boolean match(Signal signal);
}

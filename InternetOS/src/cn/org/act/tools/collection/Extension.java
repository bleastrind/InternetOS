package cn.org.act.tools.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Extension {
	public static <T> List<T> filter(Collection<T> target, Predicate<T> predicate) {
		List<T> result = new ArrayList<T>();
	    for (T element: target) {
	        if (predicate.apply(element)) {
	            result.add(element);
	        }
	    }
	    return result;
	}
	
	public static <T1,T2> List<T2> map(Collection<T1> source,Function<T1,T2> func){
		List<T2> result = new ArrayList<T2>();
	    for (T1 element: source) {
	        result.add(func.apply(element));
	    }
	    return result;
	}
}

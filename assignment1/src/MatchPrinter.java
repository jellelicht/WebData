import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class MatchPrinter {

	public static List<Map<PatternNode, String>> generateRoutes(Match m, Map<PatternNode, String> cache){
		Map<PatternNode, List<Match>> children = m.getChildren();
		PatternNode node = m.getStack().getNode();
		
		String pre = "";
		if(node.isFull()){
			pre = String.valueOf(m.getValue());
		}
		else {
			pre = String.valueOf(m.getStart());
		}
		
		cache.put(node, pre);
		List<Map<PatternNode, String>> routes = new ArrayList<Map<PatternNode, String>>();
		if (children.isEmpty()) {
			routes.add(cache);
			return routes;
		}
		for(Entry<PatternNode, List<Match>> listChild : children.entrySet()){
			
			for(Match child : listChild.getValue()) {
				Map<PatternNode, String> placeholder = new HashMap<PatternNode, String>();
				placeholder.putAll(cache);
				routes.addAll(generateRoutes(child, placeholder));
			}
		}
		return routes;
	}
	
	public static String printRoute(Map<PatternNode, String> route){
		String acc = "";
		for(Entry<PatternNode, String> e : route.entrySet()){
			acc += "\t" + e.getKey().getName() + " = " + e.getValue();
		}
		return acc;
	}
	
	public static String printFilteredRoute(Map<PatternNode, String> route){
		String acc = "";
		for(Entry<PatternNode, String> e : route.entrySet()){
			if(e.getKey().isResult()){
				acc += "\t" + e.getKey().getName() + " = " + e.getValue();
			}
		}
		return acc;
	}
	
	public static String printFilteredRouteComplete(Map<PatternNode, String> route){
		String acc = "";
		for(Entry<PatternNode, String> e : route.entrySet()){
			if(e.getKey().isResult()){
				acc += "\t<" + e.getKey().getName() + ">" + e.getValue() + "</" + e.getKey().getName() + "> ";
			}
		}
		return acc;
	}
	
	public static Map<PatternNode, String> mergeRoute(Map<PatternNode, String> route1, Map<PatternNode, String> route2){
		Map<PatternNode, String> retval = new HashMap<PatternNode, String>();
		for(PatternNode n : route1.keySet()){
			if(route2.containsKey(n)){
				if(!route1.get(n).equals(route2.get(n))){
					return null;
				}
			}
		}		
		retval.putAll(route1);
		retval.putAll(route2);
		return retval;
	}
	
	public static List<Map<PatternNode, String>> generateTuples(List<Map<PatternNode, String>> routes){
		List<Map<PatternNode, String>> tuples = new ArrayList<Map<PatternNode, String>>();
		for(int i=0; i<routes.size(); i++){
			for(int j=0; j<i; j++){
				Map<PatternNode, String> placeholder = mergeRoute(routes.get(i), routes.get(j));
				if(placeholder != null){
					tuples.add(placeholder);
				}
			}
		}
		return tuples;
	}
	

	
	
	
}

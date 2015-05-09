import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class MatchPrinter {

	public static List<Map<PatternNode, Integer>> generateRoutes(Match m, Map<PatternNode, Integer> cache){
		Map<PatternNode, List<Match>> children = m.getChildren();
		PatternNode node = m.getStack().getNode();
		Integer pre = m.getStart();
		cache.put(node, pre);
		List<Map<PatternNode, Integer>> routes = new ArrayList<Map<PatternNode, Integer>>();
		if (children.isEmpty()) {
			routes.add(cache);
			return routes;
		}
		for(Entry<PatternNode, List<Match>> listChild : children.entrySet()){
			
			for(Match child : listChild.getValue()) {
				Map<PatternNode, Integer> placeholder = new HashMap<PatternNode, Integer>();
				placeholder.putAll(cache);
				routes.addAll(generateRoutes(child, placeholder));
			}
		}
		return routes;
	}
	
	public static String printRoute(Map<PatternNode, Integer> route){
		String acc = "";
		for(Entry<PatternNode, Integer> e : route.entrySet()){
			acc += "\t" + e.getKey().getName() + " = " + e.getValue();
		}
		return acc;
	}
	public static String printFilteredRoute(Map<PatternNode, Integer> route){
		String acc = "";
		for(Entry<PatternNode, Integer> e : route.entrySet()){
			if(e.getKey().isResult()){
				acc += "\t" + e.getKey().getName() + " = " + e.getValue();
			}
		}
		return acc;
	}
	
	public static Map<PatternNode, Integer> mergeRoute(Map<PatternNode, Integer> route1, Map<PatternNode, Integer> route2){
		Map<PatternNode, Integer> retval = new HashMap<PatternNode, Integer>();
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
	
	public static List<Map<PatternNode, Integer>> generateTuples(List<Map<PatternNode, Integer>> routes){
		List<Map<PatternNode, Integer>> tuples = new ArrayList<Map<PatternNode, Integer>>();
		for(int i=0; i<routes.size(); i++){
			for(int j=0; j<i; j++){
				Map<PatternNode, Integer> placeholder = mergeRoute(routes.get(i), routes.get(j));
				if(placeholder != null){
					tuples.add(placeholder);
				}
			}
		}
		return tuples;
	}
	

	
	
	
}

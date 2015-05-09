import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class MatchPrinter {

	private static List<Map<PatternNode, Integer>> generateRoutes(Match m, Map<PatternNode, Integer> cache){
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
	public static List<Map<PatternNode, Integer>> extractTuples(Match m){
		List<Map<PatternNode, Integer>> routes = generateRoutes(m, new HashMap<PatternNode, Integer>());
		return generateTuples(routes);
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
	public static String printTupleTable(List<Map<PatternNode, Integer>> tuples, PatternNode root){
		String heading = printTupleHeading(root);
		String tuplesString = "";
		for(Map<PatternNode, Integer> tuple: tuples){
			tuplesString += "\n" + printTuple(tuple, root);
		}
		return heading + tuplesString;
	}
	private static String printTupleHeading(PatternNode root){
		String acc = "";
		for (PatternNode p : root.getOrderedSubTree()){
			acc += "\t\t" + p.getName();
		}
		return acc;
			
	}
	private static String printTuple(Map<PatternNode, Integer> tuple, PatternNode root){
		String acc = "";
		for (PatternNode p : root.getOrderedSubTree()){
			//acc += "\t" + p.getName();
			Integer placeholder = tuple.get(p);
			acc += "\t\t";
			if(placeholder == null){
				acc += "null";
			} else if(p.isResult()) {
				acc += "[" + placeholder +"]";
			} else {
				acc += placeholder;
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
		Map<Integer, Boolean> merged = new HashMap<Integer, Boolean>();
		for(int i=0; i<routes.size(); i++){
			for(int j=0; j<i; j++){
				Map<PatternNode, Integer> placeholder = mergeRoute(routes.get(i), routes.get(j));
				if(placeholder != null){
					merged.put(i, true);
					merged.put(j, true);
					tuples.add(placeholder);
				}
			}
		}
		// For routes that are not complete, yet can't be merged
		for(int i=0; i<routes.size(); i++){
			if(merged.get(i) == null){
				tuples.add(routes.get(i));
			}
		}
		return tuples;
	}	
}

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Match {
	private int start; // refers to node id
	
	private MatchState state;

	Match parent;
	
	Map <PatternNode, List<Match> > children;
	
	TPEStack stack;

	public Match(int start, Match parent, TPEStack stack) {
		super();
		this.start = start;
		this.parent = parent;
		this.stack = stack;
		this.state = MatchState.OPEN;
		this.children = new HashMap<PatternNode, List<Match>>();
	}

	public Map<PatternNode, List<Match>> getChildren() {
		return children;
	}

	public void addChild(PatternNode childNode, Match childMatch) {
		List<Match> ms = children.get(childNode);
		if(ms== null){
			ms = new ArrayList<Match>();
		}
		ms.add(childMatch);
		
		this.children.put(childNode, ms);
	}
	
	public void removeChild(PatternNode childNode, Match childMatch) {
		List<Match> ms = children.get(childNode);
		if (ms != null){
			ms.remove(childMatch);
		}
	}


	public int getStart() {
		return start;
	}

	public MatchState getState() {
		return state;
	}

	public Match getParent() {
		return parent;
	}

	public TPEStack getStack() {
		return stack;
	}

	public void setState(MatchState state) {
		this.state = state;
	}
	
	@Override
	public String toString() {
		return "";
	}
}

/*
 * class Match {
int start;
int state;
Match parent;
Map <PatternNode, Array<Match> > children;
TPEStack st;
int getStatus() {...}
}
 */

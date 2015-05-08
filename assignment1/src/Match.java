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
	}

	public Map<PatternNode, List<Match>> getChildren() {
		return children;
	}

	public void addChild(PatternNode childNode, List<Match> childMatches) {
		this.children.put(childNode, childMatches);
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

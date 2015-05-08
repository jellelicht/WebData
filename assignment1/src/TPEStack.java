import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class TPEStack {
	private PatternNode node;
	private Stack < Match> matches;
	private TPEStack parentStack; // Null if no parent == root of tree
	private List<TPEStack> childrenStacks;
	
	public PatternNode getNode() {
		return node;
	}
	public Stack<Match> getMatches() {
		return matches;
	}
	public TPEStack getParentStack() {
		return parentStack;
	}
	public void push(Match m) { matches.push(m); }
	public Match top() { return (matches.size() > 0)? matches.peek(): null; }
	public Match pop() { return matches.pop(); }
	public void remove(Match m) { matches.remove(m); }
	
	public TPEStack(PatternNode node, TPEStack parentStack) {
		super();
		this.node = node;
		this.parentStack = parentStack;
		this.matches = new Stack <Match>();
		this.childrenStacks = new ArrayList<TPEStack>();
	}
	
	//alias
	public List<TPEStack> getDescendantStacks() {
		return this.getChildrenStacks();
	}
	
	public List<TPEStack> getChildrenStacks() {
		return childrenStacks;
	}
	public void addChildStack(TPEStack childStack) {
		this.childrenStacks.add(childStack);
	}	
}

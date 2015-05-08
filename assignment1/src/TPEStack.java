import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class TPEStack {
	PatternNode node;
	Stack < Match> matches;
	TPEStack parentStack; // Null if no parent == root of tree
	List<TPEStack> childrenStacks;
	
	public void push(Match m) { matches.push(m); }
	public Match top() { return matches.peek(); }
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

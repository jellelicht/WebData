import java.util.ArrayList;
import java.util.List;


public class PatternNode {
	
	private String name;
	private NodeType type;
	private boolean required;
	private List<PatternNode> children;

	private boolean result;
	private boolean wildcard;
	private boolean fullRepresentation;
	
	private Predicate pred;

	public PatternNode(String name, NodeType type,
			boolean required, boolean result, boolean wildcard, boolean fullRepresentation, Predicate pred) {
		super();
		this.name = name;
		this.type = type;
		this.required = required;
		this.children = new ArrayList<PatternNode>();
		this.result = result;
		this.wildcard = wildcard;
		this.fullRepresentation = fullRepresentation;
		this.pred = pred;
	}

	public boolean isResult() {
		return result;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isfullRepresentation() {
		return fullRepresentation;
	}

	public void setfullRepresentation(boolean fullRepresentation) {
		this.fullRepresentation = fullRepresentation;
	}
	
	public NodeType getType() {
		return type;
	}

	public void setType(NodeType type) {
		this.type = type;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public boolean isWildcard() {
		return wildcard;
	}

	public void setWildcard(boolean wildcard) {
		this.wildcard = wildcard;
	}
	
	public boolean fullfillsPredicate(String s){
		
		return pred.isMatch(s);
	}
	
	public List<PatternNode> getChildren() {
		// TODO Auto-generated method stub
		return children;
	}
	
	public PatternNode addChild(PatternNode child){
		children.add(child);
		return this;
	}
}

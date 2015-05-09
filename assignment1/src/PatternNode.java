import java.util.ArrayList;
import java.util.List;


public class PatternNode {
	
	private String name;
	private NodeType type;
	private boolean required;
	private List<PatternNode> children;

	private boolean result;
	private boolean wildcard;
	
	public PatternNode(String name, NodeType type,
			boolean required, boolean result, boolean wildcard) {
		super();
		this.name = name;
		this.type = type;
		this.required = required;
		this.children = new ArrayList<PatternNode>();
		this.result = result;
		this.wildcard = wildcard;
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
	
	public List<PatternNode> getChildren() {
		// TODO Auto-generated method stub
		return children;
	}
	
	public PatternNode addChild(PatternNode child){
		children.add(child);
		return this;
	}
}

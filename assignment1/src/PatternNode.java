import java.util.ArrayList;
import java.util.List;


public class PatternNode {
	
	private String name;
	private NodeType type;
	private boolean required;
	private List<PatternNode> children;
	
	private boolean result;
	
	public PatternNode(String name, NodeType type,
			boolean required, boolean result) {
		super();
		this.name = name;
		this.type = type;
		this.required = required;
		this.children = new ArrayList<PatternNode>();
		this.result = result;
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

	public List<PatternNode> getChildren() {
		// TODO Auto-generated method stub
		return children;
	}
	
	public List<PatternNode> getOrderedSubTree(){
		List<PatternNode> retval = new ArrayList<PatternNode>();
		retval.add(this);
		for(PatternNode child: this.getChildren()){
			retval.addAll(child.getOrderedSubTree());
		}
		return retval;
	}
	
	public PatternNode addChild(PatternNode child){
		children.add(child);
		return this;
	}
}

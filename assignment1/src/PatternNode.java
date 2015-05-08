import java.util.List;


public class PatternNode {
	
	private String name;
	private NodeType type;
	private boolean required;
	
	public PatternNode(String name, NodeType type,
			boolean required) {
		super();
		this.name = name;
		this.type = type;
		this.required = required;
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
		return null;
	}
}

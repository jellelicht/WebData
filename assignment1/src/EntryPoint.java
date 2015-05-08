
public class EntryPoint {

	public static void main(String[] args) {
		PatternNode root = new PatternNode("people", NodeType.ELEMENT, false).addChild( 
				new PatternNode("person", NodeType.ELEMENT, false)
				.addChild(new PatternNode("email", NodeType.ELEMENT, true))
				.addChild(new PatternNode("name", NodeType.ELEMENT, false)
								.addChild(new PatternNode("last",
										NodeType.ELEMENT, true))));
		System.out.println("hello");
	}
}

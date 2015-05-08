import java.util.List;

import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.SAXException;



public class EntryPoint {

	public static void main(String[] args) throws SAXException {
		PatternNode root = new PatternNode("people", NodeType.ELEMENT, false).addChild( 
				new PatternNode("person", NodeType.ELEMENT, false)
				.addChild(new PatternNode("email", NodeType.ELEMENT, true))
				.addChild(new PatternNode("name", NodeType.ELEMENT, false)
								.addChild(new PatternNode("last",
										NodeType.ELEMENT, true))));
		System.out.println("hello");
		
		XMLReader saxReader = 
			    XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
		saxReader.setContentHandler(new StackEval(generate(root, null)));
	}
	
	public static TPEStack generate (PatternNode root, TPEStack parent){
		if(root == null) {
			throw new Error("Root cannot be null");
		}
		List<PatternNode> children = root.getChildren();
		TPEStack ts = new TPEStack(root, parent);
		for (PatternNode child : children){
			ts.addChildStack(generate(child, ts));
		}
		return ts;
	}
}

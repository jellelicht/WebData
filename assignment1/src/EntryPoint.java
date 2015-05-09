import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.SAXException;



public class EntryPoint {

	public static void main(String[] args) throws SAXException, IOException {

		PatternNode root = new PatternNode("people", NodeType.ELEMENT, false, false,false,false, AnyPredicate.getInstance()).addChild( 
				new PatternNode("person", NodeType.ELEMENT, false, false,false,false, AnyPredicate.getInstance())
				.addChild(new PatternNode("email", NodeType.ELEMENT, true, true,false,true, AnyPredicate.getInstance()))
				.addChild(new PatternNode("name", NodeType.ELEMENT, false, false,false,false, AnyPredicate.getInstance())
								.addChild(new PatternNode("last",
										NodeType.ELEMENT, true, true,false,true, new StringPredicate("Hart")))));
		System.out.println("hello");
		
		XMLReader saxReader = 
			    XMLReaderFactory.createXMLReader();
		TPEStack stack = generate(root, null);
		saxReader.setContentHandler(new StackEval(stack));
		saxReader.parse("sample/sampleXML.xml");
		List<Map<PatternNode, String>> tuples = MatchPrinter.extractTuples(stack.top());
		System.out.println(MatchPrinter.printFilteredTupleTable(tuples, root));

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
	
	
	public static void cMatchPrinter(Match m, String acc) {
		 Map<PatternNode, List<Match>> children = m.getChildren();
	 	 acc = acc + " " + m.getStart();
		
		 if (children.isEmpty()) {
			 System.out.println(acc);
			 return;
		 }
		 String markedAcc;
		 for(Entry<PatternNode, List<Match>> listChild : children.entrySet()){
			 markedAcc = acc + " [" + listChild.getKey().getName() + "]->";
			 for(Match child : listChild.getValue()) {
 				 cMatchPrinter(child, markedAcc); 
			 }
		 }
	}
}

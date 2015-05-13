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

		/*
		 * This case should only return 
		 * 		<email>a@home</email>		<last>Hart</last>
				<email>a@work</email>		<last>Hart</last>
				But this also returns
				<email>m@home</email>		null
				
				I just noticed that this wrong answer occurs in this branch, while it was correct on jelle-awesome branch :|
		 */
		PatternNode root = new PatternNode("people", NodeType.ELEMENT, false, false,false,false, AnyPredicate.getInstance()).addChild( 
				new PatternNode("person", NodeType.ELEMENT, false, false,false,false, AnyPredicate.getInstance())
				.addChild(new PatternNode("email", NodeType.ELEMENT, true, true,false,true, AnyPredicate.getInstance())
					.addChild(new PatternNode("tel", NodeType.ATTRIBUTE, true,true,false,true, new StringPredicate("1234"))))
				.addChild(new PatternNode("name", NodeType.ELEMENT, false, false,false,false, AnyPredicate.getInstance())
					.addChild(new PatternNode("last",NodeType.ELEMENT, true, true,false,true, AnyPredicate.getInstance()))));


		/*
		 * This case should only return 
		 * <first>Mary</first>		<last>Jones</last>
		 * but it returns wrong answer
		 * Note: this is a query from the book p.132 Query q4.
		*/
		/*
		PatternNode root = new PatternNode("people", NodeType.ELEMENT, false, false,false,false, AnyPredicate.getInstance()).addChild( 
				new PatternNode("person", NodeType.ELEMENT, false, false,false,false, AnyPredicate.getInstance())
				.addChild(new PatternNode("email", NodeType.ELEMENT, true, false,false,false, new StringPredicate("m@home")))
				.addChild(new PatternNode("name", NodeType.ELEMENT, false, false,false,false, AnyPredicate.getInstance())
								.addChild(new PatternNode("first",NodeType.ELEMENT, true, true,false,false, AnyPredicate.getInstance()))
								.addChild(new PatternNode("last",NodeType.ELEMENT, true, true,false,false, AnyPredicate.getInstance()))));
		*/


		System.out.println("hello");
		XMLReader qsaxReader = 
			    XMLReaderFactory.createXMLReader();
		QueryEval qe = new QueryEval();
		qsaxReader.setContentHandler(qe);
		qsaxReader.parse("sample/q5.xml");
		root = qe.getRoot();
		
		XMLReader saxReader = 
			    XMLReaderFactory.createXMLReader();
		TPEStack stack = generate(root, null);
		saxReader.setContentHandler(new StackEval(stack));
		saxReader.parse("sample/sampleXML.xml");
		List<Map<PatternNode, String>> tuples = MatchPrinter.extractTuples(stack.top());
		List<Map<PatternNode, String>> routes = MatchPrinter.generateRoutes( stack.top(), new HashMap<PatternNode, String>());
		//System.out.println(MatchPrinter.printFilteredTupleTable(tuples, root));
		for(Map<PatternNode, String> route : routes){
			System.out.println(MatchPrinter.printRoute(route));
		}
		System.out.println(MatchPrinter.printTupleTable(tuples, root));

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

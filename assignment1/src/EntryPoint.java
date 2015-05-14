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
		String xmlDoc = "sample/sampleXML2.xml";
		String xmlInput = "sample/q5.xml";
		XMLReader qsaxReader = 
			    XMLReaderFactory.createXMLReader();
		QueryEval qe = new QueryEval();
		qsaxReader.setContentHandler(qe);
		qsaxReader.parse(xmlInput);
		PatternNode root = qe.getRoot();
		XMLReader saxReader = 
			    XMLReaderFactory.createXMLReader();
		TPEStack stack = generate(root, null);
		saxReader.setContentHandler(new StackEval(stack));
		saxReader.parse(xmlDoc);
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

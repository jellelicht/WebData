import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;


public class QueryEval implements ContentHandler{
	private PatternNode root;
	private Stack<PatternNode> created;
	
	public PatternNode getRoot(){
		return root;
	}
	
	private class Placeholder {
		public String fullName;
		public Boolean required;
		public Boolean resolve;
		public Boolean fullRepresentation;
		public String pred;
		
		public PatternNode generateNode(){
			boolean isAttr = false;
			String scratchName = fullName == null? "": fullName;
			if(scratchName.startsWith("@")){
				isAttr = true;
				scratchName = scratchName.substring(1, scratchName.length());				
			}
			
			boolean wildcard = false;
			if(scratchName.equals("*")){
				wildcard = true;
			}
			
			Predicate pr;
			if(pred == null || pred.equals("")){
				pr = AnyPredicate.getInstance();
			} else {
				pr = new StringPredicate(pred);
			}
			
			boolean rRequired = required == null? false: required;
			boolean rResolve = resolve == null? false: resolve;
			boolean rFullRepresentation = fullRepresentation == null? false: fullRepresentation;
			NodeType t = isAttr? NodeType.ATTRIBUTE : NodeType.ELEMENT;
			return new PatternNode(scratchName, t, rRequired, rResolve, wildcard, rFullRepresentation, pr);
			
		}
	}
	private Placeholder placeholder;
	
	public QueryEval(){
		created = new Stack<PatternNode>();
		placeholder = new Placeholder();		
	}
	
	public PatternNode parent(){
		return created.isEmpty() ? null: created.peek();
	}
	
	public void push(PatternNode n){
		created.push(n);
	}
	
	public void pop(){
		created.pop();
	}
	
	@Override
	public void startElement(String arg0, String arg1, String arg2, Attributes atts)
			throws SAXException {
		// We already know everything (except for children that might be added)
		// fill placeholder
		PatternNode parent = parent();
		placeholder = new Placeholder();
		for(int i=0; i<atts.getLength(); i++){
			String name = atts.getLocalName(i);
			String val = atts.getValue(i);
			switch (name) {
			case "name":
				placeholder.fullName = val;
				break;
			case "required":
				placeholder.required = val.equals("true");
				break;
			case "resolve":
				placeholder.resolve = val.equals("true");
				break;
			case "fullRepresentation":
				placeholder.fullRepresentation = val.equals("true");
				break;
			case "pred":
				placeholder.pred = val;
				break;
			default:
				break;
			}
		}
		PatternNode curr = placeholder.generateNode();
		if(parent == null){ // root node
			root = curr;
		} else {
			parent.addChild(curr);
		}
		created.push(curr);
	}

	@Override
	public void endElement(String arg0, String arg1, String arg2)
			throws SAXException {
		created.pop();
		// TODO Auto-generated method stub

	}

	@Override
	public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
		// TODO Auto-generated method stub

	}

	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub

	}



	@Override
	public void endPrefixMapping(String arg0) throws SAXException {
		// TODO Auto-generated method stub

	}

	@Override
	public void ignorableWhitespace(char[] arg0, int arg1, int arg2)
			throws SAXException {
		// TODO Auto-generated method stub

	}

	@Override
	public void processingInstruction(String arg0, String arg1) throws SAXException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDocumentLocator(Locator arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void skippedEntity(String arg0) throws SAXException {
		// TODO Auto-generated method stub

	}

	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub

	}



	@Override
	public void startPrefixMapping(String arg0, String arg1) throws SAXException {
		// TODO Auto-generated method stub

	}
}
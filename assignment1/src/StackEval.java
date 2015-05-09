import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/*
 * TreePattern q;
TPEStack rootStack; // stack for the root of q
// pre number of the last element which has started:
int currentPre = 0 ;
// pre numbers for all elements having started but not ended yet:
Stack < Integer > preOfOpenNodes;

 */

public class StackEval implements ContentHandler{

	private TPEStack rootStack;
	private int currentPre = 1; // pre number of the last element which has started
	private Stack <Integer> preOfOpenNodes; // pre numbers for all elements having started but not ended yet
	private String value;
	
	public StackEval(TPEStack rootStack) {
		super();
		this.rootStack = rootStack;
		this.preOfOpenNodes = new Stack<Integer>();
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		value = new String(ch,start,length).trim();
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		System.out.println("End parsing of document");
	}
	
	private static boolean ancestorCondition(TPEStack s,String  localName){
		TPEStack parentStack = s.getParentStack();
		
		boolean localNameEq = localName.equals(s.getNode().getName()); 
		// if s is the root
		if (parentStack == null) {
			return localNameEq;
		} else {
			boolean ancestorOpen = parentStack.top() != null && parentStack.top().getState() == MatchState.OPEN;
			return (s.getNode().isWildcard() ||localNameEq) && ancestorOpen;
		}
	}
	
	private static boolean descendantCondition(TPEStack s, String localName, int preOfLastOpen){
		boolean localNameEq = s.getNode().getName().equals(localName);
		boolean openMatch = s.top() != null && 
				s.top().getState() == MatchState.OPEN &&
				s.top().getStart() == preOfLastOpen;
		
		return (s.getNode().isWildcard() || localNameEq) && openMatch;
								
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {
		for(TPEStack s : rootStack.getDescendantStacks()){
			TPEStack ps = s.getParentStack();
			Match parentMatch = ps == null? null : ps.top();
			if(ancestorCondition(s, localName)) {
				Match m = new Match(currentPre, parentMatch , s);
				// create a match satisfying the ancestor conditions
				// of query node s.p
				//System.out.println("PUSH match " + m.getStack().getNode().getName() +" with id: "+ m.getStart());
				s.push(m); 
				if(parentMatch != null ){
					//System.out.println("register");

					parentMatch.addChild(s.getNode(),m);
				}
				break;
			}
		}
		preOfOpenNodes.push(currentPre);

		//System.out.println("Incing currentPre from " + currentPre + " to " + (currentPre+1) + " with name " + localName);
		currentPre++; // incease counter for each single startElement invocation
		for(int i=0; i<atts.getLength(); i++){
			for (TPEStack s : rootStack.getDescendantStacks()){
				if(ancestorCondition(s, "@" + atts.getLocalName(i))) {
					Match m = new Match(currentPre, s.getParentStack().top(), s);
					//System.out.println("PUSH match " + m.getStack().getNode().getName() +" with id: "+ m.getStart());
					s.push(m); 
					break;
				}
				
			}
			currentPre++; // Also increase counter for each attribute in element
		}	
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// we need to find out if the element ending now corresponded
		// to matches in some stacks
		// first, get the pre number of the element that ends now:
		//if(preOfOpenNodes.isEmpty()) return;
		//System.out.println("Popping node " + preOfOpenNodes.peek());
		int preOfLastOpen = preOfOpenNodes.pop();
		// now look for Match objects having this pre number:
		for (TPEStack s : rootStack.getDescendantStacks()){
			if(descendantCondition(s, localName, preOfLastOpen)){
				// all descendants of this Match have been traversed by now.
				Match m = s.top();
				if(value.length() > 0) {
					m.setValue(value);
					System.out.println("POP match " + m.getStack().getNode().getName() + " with id " + m.getStart() + " with value " + m.getValue());
				}
				m.setState(MatchState.CLOSED);
				//System.out.println("POP match " + m.getStack().getNode().getName() + " with id " + m.getStart() + " with value " + value);
				// check if m has child matches for all children
				// of its pattern node
				for (PatternNode child : s.getNode().getChildren()){
					if(m.getChildren().get(child) == null && child.isRequired()){
						System.out.println("Required: " + child.getName() + " "+ child.isRequired());
						// m lacks a child Match for the pattern node pChild
						// we remove m from its Stack, detach it from its parent etc.
						s.remove(m);
						//System.out.println("Something happened");
						if(m.getParent() != null) {
							//System.out.println("unregistra");
							m.getParent().removeChild(s.getNode(),m);
						}
						break;
					}
				}
			}
		}
	}

	@Override
	public void endPrefixMapping(String prefix) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processingInstruction(String target, String data)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDocumentLocator(Locator locator) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void skippedEntity(String name) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		System.out.println("Start the parsing of document");
		
	}

	@Override
	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

}

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
	private int currentPre = 0; // pre number of the last element which has started
	private Stack <Integer> preOfOpenNodes; // pre numbers for all elements having started but not ended yet
	
	
	public StackEval(TPEStack rootStack) {
		super();
		this.rootStack = rootStack;
		this.preOfOpenNodes = new Stack<Integer>();
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		System.out.println("End parsing of document");
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// we need to find out if the element ending now corresponded
		// to matches in some stacks
		// first, get the pre number of the element that ends now:
		if(preOfOpenNodes.isEmpty()) return;
		int preOfLastOpen = preOfOpenNodes.pop();
		// now look for Match objects having this pre number:
		for (TPEStack s : rootStack.getDescendantStacks()){
			if(s.getNode().getName().equals(localName) && 
					((s.top() == null) || 
					(s.top().getState() == MatchState.OPEN && 
					s.top().getStart() == preOfLastOpen))){
				// all descendants of this Match have been traversed by now.
				if(s.top() == null)
					continue;						
				
				Match m = s.pop();
				System.out.println("POP match " + m.getStack().getNode().getName() + " with id " + m.getStart());
				// check if m has child matches for all children
				// of its pattern node
				for (PatternNode child : s.getNode().getChildren()){
					if(m.getChildren().get(child) == null){
						// m lacks a child Match for the pattern node pChild
						// we remove m from its Stack, detach it from its parent etc.
						s.remove(m);
					}
				}
				m.setState(MatchState.CLOSED);
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
	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {
		for(TPEStack s : rootStack.getDescendantStacks()){
			TPEStack ps = s.getParentStack();
			if((localName.equals(s.getNode().getName())) && ((ps == null) || (ps.top() != null) && (ps.top().getState() == MatchState.OPEN))){ // TODO check for null parentstack
				Match m = new Match(currentPre, (ps == null)? null : ps.top() , s);
				// create a match satisfying the ancestor conditions
				// of query node s.p
				System.out.println("PUSH match " + m.getStack().getNode().getName() +" with id: "+ m.getStart());
				s.push(m); 
				preOfOpenNodes.push(currentPre);
				break;
			}
			currentPre++;
		}
		for(int i=0; i<atts.getLength(); i++){
			for (TPEStack s : rootStack.getDescendantStacks()){
				if((atts.getLocalName(i).equals(s.getNode().getName())) && s.getParentStack().top().getState() == MatchState.OPEN) {
					Match m = new Match(currentPre, s.getParentStack().top(), s);
					s.push(m);
				}
			}
			currentPre++;
		}	
	}

	@Override
	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

}

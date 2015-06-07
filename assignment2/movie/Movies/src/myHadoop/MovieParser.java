package myHadoop;

import java.io.StringBufferInputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MovieParser extends DefaultHandler {
	private String content;
	private Movie movie;
	private String firstName = "";
	private String lastName ="";
	private String birthDate ="";
	private String role ="";
	private String value;
	
    public MovieParser(String content) {
        this.content = content;
     
    }
    
    public Movie parseXML() {
        // parse
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(new StringBufferInputStream(content), this);
	    } catch (Throwable err) {
	        err.printStackTrace ();
	    }
		return movie;
    }

	@Override
	public void startElement(String arg0, String arg1, String arg2,
			Attributes arg3) throws SAXException {
        value=null;
        if (arg2.equalsIgnoreCase("movie")) {
        	movie = new Movie();
        }
	}
	
	@Override
	public void endElement(String arg0, String arg1, String arg2)
			throws SAXException {
		// TODO Auto-generated method stub
        if (arg2.equalsIgnoreCase("title")) {
        	movie.setTitle(value);
        }
        if (arg2.equalsIgnoreCase("year")) {
        	movie.setYear(value);
        }
        if (arg2.equalsIgnoreCase("role")) {
        	role = value;
        }        
        if (arg2.equalsIgnoreCase("birth_date")) {
        	birthDate = value;
        }        
        if(arg2.equalsIgnoreCase("first_name")){
        	firstName = value;
        }
        if(arg2.equalsIgnoreCase("last_name")){
    		lastName = value;
        }
        if(arg2.equalsIgnoreCase("director")){
        	String directorName = firstName + " " + lastName;
        	movie.setDirector(directorName);
        }
        if(arg2.equalsIgnoreCase("actor")){
        	String actorName = firstName + " " + lastName;
        	Actor actor = new Actor(actorName, birthDate, role);
        	movie.addActors(actor);
        }
	}

	@Override
	public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
		// TODO Auto-generated method stub
		value = new String(arg0, arg1, arg2);
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
	public void processingInstruction(String arg0, String arg1)
			throws SAXException {
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
	public void startPrefixMapping(String arg0, String arg1)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}

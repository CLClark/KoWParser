package pureBuild;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class DBuilder extends DocumentBuilderFactory{

	
	DocumentBuilderFactory Hey = new DocumentBuilderFactory.newInstance();

	@Override
	public Object getAttribute(String name) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getFeature(String name) throws ParserConfigurationException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DocumentBuilder newDocumentBuilder() throws ParserConfigurationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAttribute(String name, Object value) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFeature(String name, boolean value) throws ParserConfigurationException {
		// TODO Auto-generated method stub
		
	}
	
	
//	try {
//		DocumentBuilderFactory d2 = DocumentBuilderFactory.newInstance(); 
//		DocumentBuilder dB = d2.newDocumentBuilder();
//	} 
//	
//	catch (ParserConfigurationException e) {
//		throw new RuntimeException("Cannot construct or configure document builder", e);
//	}

}




package pureBuild;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class KoWPMain {

	public static void main(String[] args) throws XPathExpressionException {
		
		String inputPath = System.getProperty("user.dir");
		System.out.println(inputPath);
		inputPath = inputPath.concat("\\data\\TheHerd.xhtml");
		System.out.println(inputPath);
		
		File z = new File(inputPath);
		System.out.println(z.getAbsolutePath());
		Document emptyDoc;
		emptyDoc = createDocument(z);
		System.out.println(emptyDoc.getDocumentURI());
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//*[@class=\"colUS\"]/parent::*/child::*/text() | //*[@class=\"colUS\"]/parent::*/child::*/@class";
		
		NodeList nodes; 
//		nodes = (NodeList) xpath.evaluate(expression, emptyDoc, XPathConstants.NODESET);
		XPathExpression statsExp = xpath.compile(expression);
		
		NodeList printy = (NodeList) statsExp.evaluate(emptyDoc, XPathConstants.NODESET);
//		System.out.println((printy).item(9).getNodeValue());
//		System.out.println(printy.getLength());
		
		for (int i = 0; i < printy.getLength(); i++){
//			if(printy.item(i).getNodeType() == Node.TEXT_NODE){
//			System.out.println(nodes.item(i).getNodeValue());
//			System.out.println(printy.item(i).getParentNode().getNodeName());
			System.out.println((printy).item(i).getNodeValue().replace("col","").replace("LB", ""));
			if (i%2 == 0) System.out.println("");
			}
		}
//	}
//			System.out.println(nL.item(i).);
			
			
//			//table[@class="cssTroopsTable"]//*[@class="cssTroopsRow"]/td
//			(:first is troop name elem, second is troop type elem:)
//		
//			//*[@class="colUS"]/ancestor::div[1]/@id
//			(:selects the id attribute of the first 'div' ancestor node of the stat:)
//		
//			//*[@class="colUS"]/parent::*/child::*
//			(: can be run inside div node[or alone] to get every individual troop stat:)
//			
//			//table[@class="cssTroopsTable"]//tr/td/div
//			(:div node:)
//			
//		colUS troop size
//		colSP speed
//		colME melee
//		colRA ranged
//		colDE defense
//		colAT attacks
//		colNE nerve
//		colPTS points
//		colSpecial special info
//		colLB *do not need*
//		parent::* to get parent	
		
				
	

	
public static Document createDocument(File f){
		
		try {
			DocumentBuilderFactory d2 = DocumentBuilderFactory.newInstance(); 
			d2.setNamespaceAware(true);
			d2.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			DocumentBuilder dB = d2.newDocumentBuilder();			
			
			Document result = dB.parse(f);
			return result;
		} 
		
		catch (ParserConfigurationException | SAXException | IOException e) {
			throw new RuntimeException("Cannot construct or configure document builder", e);
		}
		
	}	
	
}

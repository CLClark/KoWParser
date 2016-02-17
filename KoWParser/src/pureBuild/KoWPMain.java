package pureBuild;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class KoWPMain {

	public static void main(String[] args) {
		String inputPath = System.getProperty("user.dir");
		System.out.println(inputPath);
		inputPath = inputPath.concat("\\data\\TheHerd.xhtml");
		System.out.println(inputPath);
		
		File z = new File(inputPath);
		System.out.println(z.getAbsolutePath());
		Document emptyDoc;
		emptyDoc = createDocument(z);
//		Element eZ = emptyDoc.getDocumentElement();
		NodeList nL = emptyDoc.getElementsByTagName("*");
		
//		System.out.println(eZ.getTagName());
		
//		for (int i = 0; i < nL.getLength(); i++){
////			boolean texty = nL.item(i).hasChildNodes();
//			if (nL.item(i).hasChildNodes() == true) {
//				for(int j = 0; j < nL.item(i).getChildNodes().getLength(); j++){
//						System.out.println(nL.item(i).getChildNodes().item(j).getNodeValue());
//						System.out.println(j + "-" + i);
//								
//				}
//			}
//		}
	}
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

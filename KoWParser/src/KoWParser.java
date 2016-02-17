//coded by C L Clark

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQSequence;

import javax.xml.*;




public class KoWParser {
	
	private String rawContent = "";
	private String neededContent = "";
	private String filePath = "";
	private String companyName = "";
	static int errors = 0;
    static int noCompanyNameCount = 0;
    
    //constructor invoked in main method (represents each xhtml file parsed)
	public KoWParser(String filePath) {
		
		this.filePath = filePath;
		this.read();
		this.extractCompanyName();
		this.extractNeededContent();
		
	}
	
	
	
	
	
	
	
	

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
	
	
	
	// reads the text (raw content) inside the file specified in the file path
	private void read() {
		byte[] encoded;
		try {
			encoded = Files.readAllBytes(Paths.get(filePath));
			rawContent = new String(encoded);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// removes RTF tags and fields from the input string and returns the cleaned, human-readable version of that string
	private String clean(String content) {
//		content = content.replace("\\~", " ");
//		content = content.replaceAll("\\{\\\\\\*\\\\blipuid[a-zA-Z0-9|\\s|\\n]+\\}[a-zA-Z0-9|\\s|\\n]+\\}\\}", "");
//		content = content.replaceAll("\\{\\\\\\*\\\\(bkmkstart|bkmkend)[A-Za-z0-9._\\*-|\\s]+\\}", "");
//		content = content.replaceAll("(\\\\[A-Za-z0-9._\\*-]+)+", "");
//		content = content.replaceAll("\\{\\{ HYPERLINK \\\\ \"\\w*\" \\}\\{ Table of Contents\\}\\}", "");
//		content = content.replaceAll("(?i)HYPERLINK \\\\ \"\\w*\"", " ");
//		content = content.replaceAll("\\{ gmnh1page\\w*\\}", "");
//		content = content.replace("{", "");
//		content = content.replace("}", "");
		return content;
	}

	// extract Items 1 and 1A from the attribute "rawContent" and stores the extracted content in the attribute "neededContent".
	// This is achieved by getting the starting index of Item 1 first. If it cannot be found, then the program gets the starting
	// index of Part I,1 or One (whichever of the three is found). This will be the start index. The end index will be determined
	// by getting the starting index of the section after Item 1A (This could be Item 1B, Part 2, etc.). The text between the
	// start and end index will be the extracted content.
	// Note that the extraction method is not perfect and can still be enhanced to lessen incorrect extractions
	// (~96% success rate based on 17800+ documents)
	private void extractNeededContent() {

		String content = rawContent;
		String contentLowerCase = content.toLowerCase();

		ArrayList<Integer> s = new ArrayList<Integer>();

		contentLowerCase = content.toLowerCase();

		s.add(new Integer(getSectionIndex("item 1", contentLowerCase)));
		s.add(new Integer(getSectionIndex("part i", contentLowerCase)));
		s.add(new Integer(getSectionIndex("part 1", contentLowerCase)));
		s.add(new Integer(getSectionIndex("part one", contentLowerCase)));

		// among the four possible indexes, store the one with the highest index (most of the time, the other indexes would be -1)
		Collections.sort(s, Collections.reverseOrder());
		int start = s.get(0);

		ArrayList<Integer> e = new ArrayList<Integer>();
		e.add(new Integer(getSectionIndex("item 1b", contentLowerCase)));

		// if item 1b is not found, try to get index for item 2
		if (e.get(0) == -1 || e.get(0) <= start) {
			e = new ArrayList<Integer>();
			e.add(new Integer(getSectionIndex("item 2", contentLowerCase)));

		}

		// if item 2 is not found, try to get index for item 3
		if (e.get(0) == -1 || e.get(0) <= start) {
			e = new ArrayList<Integer>();
			e.add(new Integer(getSectionIndex("item 3", contentLowerCase)));
		}

		// if item 3 is not found, try to get index for item 4
		if (e.get(0) == -1 || e.get(0) <= start) {
			e = new ArrayList<Integer>();
			e.add(new Integer(getSectionIndex("item 4", contentLowerCase)));
		}

		// if item 4 is not found, try to get index for part ii, 2 or two (whichever of the three is found)
		if (e.get(0) == -1 || e.get(0) <= start) {
			e = new ArrayList<Integer>();
			e.add(new Integer(getSectionIndex("part ii", contentLowerCase)));
			e.add(new Integer(getSectionIndex("part 2", contentLowerCase)));
			e.add(new Integer(getSectionIndex("part two", contentLowerCase)));
			Collections.sort(e, Collections.reverseOrder());
		}

		int end = e.get(0);

		//
		if (start != -1 && end != -1 && (start < end)) {
			System.out.println("start index: "+start);
			System.out.println("end index: "+end);
			neededContent = clean(content.substring(start, end));
			
		// part ii,2 or two is not found (end index will be -1).
		// The needed content is not found.
		} else {
			System.out.println("Needed content not found "+start);
			errors++;
			neededContent = "";
		}

	}

	public String getNeededContent() {
		return neededContent;
	}

	public String getRawContent() {
		return rawContent;
	}

	public String getFilePath() {
		return filePath;
	}

	// returns the starting index or location of a specific section (e.g. Part 1, Item 2) from the content string.
	// If section is not found on the content string, -1 is returned.
	// Note that there are different cases to handle the different ways in which a section is labelled
	// (section names sometimes end with '.' or ':').
	private static int getSectionIndex(String sectionName, String content) {
		String bookmark = "";
		if (content.lastIndexOf("\\*\\fldrslt " + sectionName + "}") != -1) {
			bookmark = getBookmark(sectionName + "}", content);
		} else if (content.lastIndexOf("\\*\\fldrslt " + sectionName + ".}") != -1) {
			bookmark = getBookmark(sectionName + ".}", content);
		} else if (content.lastIndexOf("\\*\\fldrslt " + sectionName + ":}") != -1) {
			bookmark = getBookmark(sectionName + ":}", content);
		} else if (content.lastIndexOf("\\*\\fldrslt " + sectionName.replace(" ", "\\~") + "}") != -1) {
			bookmark = getBookmark(sectionName.replace(" ", "\\~") + "}", content);
		} else if (content.lastIndexOf("\\*\\fldrslt " + sectionName.replace(" ", "\\~") + ".}") != -1) {
			bookmark = getBookmark(sectionName.replace(" ", "\\~") + ".}", content);
		} else if (content.lastIndexOf("\\*\\fldrslt " + sectionName.replace(" ", "\\~") + ":}") != -1) {
			bookmark = getBookmark(sectionName.replace(" ", "\\~") + ":}", content);
		}

		if (content.lastIndexOf("{\\*\\bkmkstart " + bookmark + "}") != -1) {
			if (content.lastIndexOf("{\\*\\bkmkstart " + bookmark + "}") > Math.floor(content.length() / 2))
				return 0;
			else
				return content.lastIndexOf("{\\*\\bkmkstart " + bookmark + "}");
		}

		return -1;
	}

	// get bookmark or hyperlink associated with a specific section (e.g. Part 1, Item 2).
	//
	// Example for section PART 1:
	// This field will be located: {\field{\*\fldinst HYPERLINK \\l "part_1_2_1"}{\*\fldrslt PART I}}
	// The bookmark would be part_1_2_1.
	private static String getBookmark(String sectionName, String content) {
//		return content.substring(content.lastIndexOf("\\*\\fldrslt " + sectionName) - 18, content.lastIndexOf("\\*\\fldrslt " + sectionName) + 30)
//				.split("\"")[1];
	}
	
	private void extractCompanyName() {
//		int endIndex = 30000;
//		if(rawContent.length()<30000)
//			endIndex = rawContent.length();
//		Pattern pattern = Pattern.compile("(.*?)Form 10-K(.*?)Filed:(.*?)Annual report with a comprehensive overview of the company",Pattern.DOTALL);
//		Matcher matcher = pattern.matcher(rawContent.substring(0, endIndex));
//		if (matcher.find()) {
//			String temp = clean(matcher.group(2));
//			String temp2 = new StringBuilder(temp).reverse().toString().split("-",2)[1];
//			companyName = new StringBuilder(temp2).reverse().toString().trim();
//			noCompanyNameCount++;
		companyName = "define it from xml data";
		
		}

	}

	public String getCompanyName() {
		return companyName;
	}
}

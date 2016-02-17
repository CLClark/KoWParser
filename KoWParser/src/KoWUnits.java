//developed by Chris Clark based on code from Luigi Vamenta 

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class KoWUnits {
	public static void main(String[] args) {
		
		String inputPath = System.getProperty("user.dir");
		String outputPath = inputPath+"/output/";
		
		// a user can also specify the input directory by passing a command line argument
		// when running the program
		if(args.length>0) {
			inputPath = args[0];
			if(args[0].endsWith("/")||args[0].endsWith("\\")) {
				outputPath = args[0]+"output/";
			} else {
				outputPath = args[0]+"/output/";
			}
		}
		else {
			//String inputPath = new File()
			//String outputPath = args[0]+"output/";
		}
		String outputFile = "";
		//initialize the file path?
		
		if (Files.notExists(Paths.get(outputPath)))
			new File(outputPath).mkdir();
		//checks if the output folder exists, creates folder if nonexistent

		File inputFolder = new File(inputPath);
		//creates the folder as an object in java based on input path - so that it can interact with the file system
	

		try {
			File[] inputFiles = inputFolder.listFiles();
			//creates array of Files based on contents of directory (what is a file? an address?)

			for (File inputFile : inputFiles) {
				String inputFileName = inputFile.getName();
				//gets the last name in the file's path (ie file name)
				
				// Check if the file is an .html file and then execute the Parser class
				
				if (inputFileName.substring(inputFileName.length() - 4).equals("html")) {

					inputFileName = inputFileName.substring(0, inputFileName.length() - 5);
					System.out.println(inputFileName);

					System.out.println("Input File: " + inputFile);

					System.out.println("Reading and parsing file...");
					KoWParser form = new KoWParser(inputFile.getAbsolutePath()); //KoWParser

					// output file containing (more or less) only Item 1 and 1A of the document
//					outputFile = outputPath + inputFileName + ".csv";
//
//					BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
//					bw.write(form.getCompanyName());
//					bw.newLine();
//					bw.write("~~~~~~~~~~~~~~~~~~~~");
//					bw.newLine();
//					bw.write(form.getNeededContent());
//					bw.close();

				}
			}
			System.out.println("FINISHED");
			System.out.println("Unsuccessful company name extractions: " + KoWParser.noCompanyNameCount); //KoWParser
			System.out.println("Unsuccessful body text extractions: " + KoWParser.errors); //KoWParser

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

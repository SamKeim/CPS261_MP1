package reading_with_exceptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ReadingWithExceptions {
	public static void process(String inputFileName) {
		// Declare Variables
		Scanner fileIn = null;
		PrintWriter fileOut = null;
		String outputFileName = null;
		int numberToRead = Integer.MAX_VALUE;
		
		try {
			// Setup file to be read
			fileIn = new Scanner(new FileInputStream(inputFileName));

			// Get instructions from file and validate
			outputFileName = fileIn.next();
			if (fileIn.hasNextInt()) {
				numberToRead = fileIn.nextInt();
				if (numberToRead <= 0) {
					System.out.println("Bad input number, unable to read " + numberToRead + " numbers from file.");
					return;
				}
			} else {
				// if next() does not hold int
				System.out.println("Bad input number, unable to read " + fileIn.next() + " numbers from file.");
			}

			fileIn.nextLine(); // Clear /r from Scanner

			// Setup file to write to
			fileOut = new PrintWriter(new FileOutputStream(outputFileName));

			// Loop for numbers to be read
			
			for (int i = 1; i <= numberToRead;) {
				// Check validity
				if (!fileIn.hasNextInt()) {
					System.out.println("Bad number, skipping.");
					fileIn.next();
					continue;
				} else {
					fileOut.print(fileIn.nextInt());
					 i++; // Only increment i if number is valid
				}
				
				// If 10 numbers have been printed, add line break, otherwise add formatting
				if (i > 1 && i % 10 == 0) {
					fileOut.println();
				} else {
					fileOut.print(" ");
				}
				
				// If EOF has been reached
				if (!fileIn.hasNext()) {
					System.out.println("Ran out of numbers to parse.");
					break;
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found : " + inputFileName);
			return;
		} finally {
			// Close files
			if (fileIn != null) {
				fileIn.close();
				fileIn = null;
			}
			if (fileOut != null) {
				fileOut.close();
			}
		}

		// Confirm file creation
		System.out.println("File " + outputFileName + " Created.");
		
		// Try to open file to print processed numbers
		try {
			fileIn = new Scanner(new FileInputStream(outputFileName));
			while (fileIn.hasNextLine()) {
				System.out.println(fileIn.nextLine());
			}
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found : " + e);
		} finally {
			// Close file
			if (fileIn != null) {
				fileIn.close();
			}
		}

	}

	public static void main(String[] args) {
		for (String arg : args) {
			process(arg);
		}
	}
}

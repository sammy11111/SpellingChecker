package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * class used for generic helper methods
 * 
 * @author Sammy Ibrahim
 *
 */
public class Utils {

	/**
	 * method used to check if 2 words are equals - the case is ignored
	 * 
	 * @param word1
	 *            used to check similarity
	 * @param word2
	 *            used to check similarity
	 * @return true if the words are equals and false otherwise
	 */
	public static boolean similarity(String word1, String word2) {
		return word1.equalsIgnoreCase(word2);
	}

	/**
	 * generic method used to read a text file and append every line of the file
	 * to string builder
	 * 
	 * @param fileName
	 *            the name of the given file
	 * @return the string with the content of the file lines and line separators
	 * @throws IOException
	 */
	public static String readTextFile(String fileName) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		try {

			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
		} finally {
			br.close();
		}
		return sb.toString();
	}

	/**
	 * generic method used to save string content to a text file
	 * 
	 * @param fileName
	 *            the name of the file used to save the string content
	 * @param content
	 *            the contet wished to be saved
	 */
	public static void saveTextFile(String fileName, String content) {
		System.out.println("Save text started.....");
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(fileName));
			writer.write(content);

		} catch (IOException e) {
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (IOException e) {
			}
		}
		System.out.println("Save text finished.....");
	}

	/**
	 * method used to parse the lines that are read from file
	 * 
	 * @param suggestionsLine
	 *            that are read from file
	 * @return the list with parsed lines
	 */
	public static List<String> parseSuggestions(String suggestionsLine) {
		List<String> suggestionList = new ArrayList<>();
		for (String token : suggestionsLine.trim().split(",")) {
			if (!token.isEmpty()) {
				suggestionList.add(token);
			}
		}
		Collections.sort(suggestionList);
		return suggestionList;
	}

	/**
	 * method used to replace a word in a text
	 * 
	 * @param newWord
	 *            to replace
	 * @param oldWord
	 *            to be replaced
	 * @param text
	 *            where the words are replaced
	 * @return the text modified
	 */
	public static String replaceWordInText(String newWord, String oldWord, String text) {
		return text.replaceAll("\\b" + oldWord + "\\b", newWord);
	}
}

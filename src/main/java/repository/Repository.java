package repository;

import java.io.IOException;
import java.util.List;

import model.SpellDictionary;
import model.SpellText;

public interface Repository {
	/**
	 * method used to read dictionary from configuration file
	 * 
	 * @param fileName
	 *            the name of the file where the dictionary is stored
	 * @param spellDictionary
	 *            the object used to store the dictionary data
	 * @throws IOException
	 *             the exception that can be thrown in case that the file is not
	 *             found
	 */
	public void readDictionaryFile(String fileName, SpellDictionary spellDictionary) throws IOException;

	/**
	 * method used to save the text that is spelled into a file
	 * 
	 * @param content
	 *            the text to be saved
	 * @param fileName
	 *            the name of the file where the text is saved
	 */
	public void saveTextToFile(String content, String fileName);

	/**
	 * method used to save the dictionary in file
	 * 
	 * @param fileName
	 *            the name of the file where the dictionary is saved
	 * @param spellDictionary
	 */
	public void saveDictionaryFile(String fileName, SpellDictionary spellDictionary);

	/**
	 * method used to read the dictionary from file
	 * 
	 * @param fileName
	 *            the name of the file where the dictionary is stored
	 * @throws IOException
	 *             the exception that can be thrown in case that the dictionary
	 *             is not found
	 */
	public void loadDictionary(String fileName) throws IOException;

	/**
	 * method used to load text from file
	 * 
	 * @param fileName
	 *            the name of the file
	 * @throws IOException
	 *             the exception that can be thrown when the file is not found
	 */
	public void loadTextFile(String fileName) throws IOException;

	/**
	 * method used to check that a word exist as key in a dictionary
	 * 
	 * @param word
	 * @return true if the word is in dictionary false otherwise
	 */
	public boolean checkWord(String word);

	/**
	 * method used to get the list of suggestions for a dictionary word that is
	 * key
	 * 
	 * @param word
	 *            that is used to get suggestions
	 * @return the list of suggestions for a word that is key , in case of no
	 *         result return an empty list
	 */
	public List<String> suggestionsForWord(String word);

	/**
	 * method used to find all the similar words for a given word even the word
	 * is key or not
	 * 
	 * @param word
	 *            used to get all the similar words
	 * @return the list with similar words
	 */
	public List<String> findSimilarWords(String word);

	/**
	 * method used to to save new word in dictionary
	 * 
	 * @param word
	 *            proposed as key
	 * @param suggestions
	 *            list of words proposed as similar words for a key
	 */
	public void proposeNewWordDictionary(String word, List<String> suggestions);

	/**
	 * method used to proposed new suggestions for a given word ( key)
	 * 
	 * @param word
	 *            used as key
	 * @param suggestions
	 *            the list of suggestions
	 */
	public void proposeNewSuggestions(String word, List<String> suggestions);

	/**
	 * getter method to get the spell dictionary object
	 * 
	 * @return the spell dictionary object
	 */
	public SpellDictionary getSpellDictionary();

	/**
	 * setter method to set a new dictionary object
	 * 
	 * @param spellDictionary
	 *            used to update the object
	 */
	public void setSpellDictionary(SpellDictionary spellDictionary);

	/**
	 * getter to get the spell text object
	 * 
	 * @return the spell text object
	 */
	public SpellText getSpellText();

	/**
	 * setter to set the object spell text
	 * 
	 * @param spellText
	 *            used to update the spell text object
	 */
	public void setSpellText(SpellText spellText);

}

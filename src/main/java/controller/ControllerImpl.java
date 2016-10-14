package controller;

import java.io.IOException;
import java.util.List;

import model.SpellDictionary;
import repository.Repository;

/**
 * class that implements the Controller interface
 * 
 * @author Sammy Ibrahim
 *
 */
public class ControllerImpl implements Controller {
	/* repo object */
	private Repository repo;

	/**
	 * no arg constructor
	 */
	public ControllerImpl() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.Controller#loadDictionary(java.lang.String)
	 */
	public void loadDictionary(String fileName) throws IOException {
		repo.loadDictionary(fileName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.Controller#loadTextFile(java.lang.String)
	 */
	public void loadTextFile(String fileName) throws IOException {
		repo.loadTextFile(fileName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.Controller#checkWord(java.lang.String)
	 */
	public boolean checkWord(String word) {
		return repo.checkWord(word);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.Controller#suggestionsForWord(java.lang.String)
	 */
	public List<String> suggestionsForWord(String word) {
		return repo.suggestionsForWord(word);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.Controller#findSimilarWords(java.lang.String)
	 */
	public List<String> findSimilarWords(String word) {
		return repo.findSimilarWords(word);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.Controller#proposeNewWordDictionary(java.lang.String,
	 * java.util.List)
	 */
	public void proposeNewWordDictionary(String word, List<String> suggestions) {
		repo.proposeNewWordDictionary(word, suggestions);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.Controller#proposeNewSuggestions(java.lang.String,
	 * java.util.List)
	 */
	public void proposeNewSuggestions(String word, List<String> suggestions) {
		repo.proposeNewSuggestions(word, suggestions);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.Controller#getRepo()
	 */
	public Repository getRepo() {
		return repo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.Controller#setRepo(repository.Repository)
	 */
	public void setRepo(Repository repo) {
		this.repo = repo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.Controller#readDictionaryFile(java.lang.String,
	 * model.SpellDictionary)
	 */
	@Override
	public void readDictionaryFile(String fileName, SpellDictionary spellDictionary) throws IOException {
		this.repo.readDictionaryFile(fileName, spellDictionary);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.Controller#saveTextToFile(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void saveTextToFile(String content, String fileName) {
		this.repo.saveTextToFile(content, fileName);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.Controller#saveDictionaryFile(java.lang.String,
	 * model.SpellDictionary)
	 */
	@Override
	public void saveDictionaryFile(String fileName, SpellDictionary spellDictionary) {
		this.repo.saveDictionaryFile(fileName, spellDictionary);

	}

}

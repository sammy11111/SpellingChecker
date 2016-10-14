package repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import model.SpellDictionary;
import model.SpellText;
import util.Utils;

public class RepositoryImpl implements Repository {
	/* spell dictionary object */
	private SpellDictionary spellDictionary;
	/* spell text object */
	private SpellText spellText;
	/* read write lock used to can update and read from the same resource */
	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	/* read lock */
	protected final Lock readLock = readWriteLock.readLock();
	/* write lock */
	protected final Lock writeLock = readWriteLock.writeLock();

	/**
	 * constructor with no arguments
	 */
	public RepositoryImpl() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see repository.Repository#readDictionaryFile(java.lang.String,
	 * model.SpellDictionary)
	 */
	public void readDictionaryFile(String fileName, SpellDictionary spellDictionary) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		try {

			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				parsePropertiesFile(line, spellDictionary);
			}
		} finally {
			br.close();
		}
		System.out.println(spellDictionary.getDictionary());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see repository.Repository#saveTextToFile(java.lang.String,
	 * java.lang.String)
	 */
	public void saveTextToFile(String content, String fileName) {
		Utils.saveTextFile(fileName, content);
	}

	/**
	 * generic method used to save string content to a text file
	 * 
	 * @param fileName
	 *            the name of the file used to save the string content
	 * @param content
	 *            the contet wished to be saved
	 */
	public void saveDictionaryFile(String fileName, SpellDictionary spellDictionary) {
		Map<String, List<String>> dictionary = spellDictionary.getDictionary();

		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(fileName));
			for (Map.Entry<String, List<String>> entry : dictionary.entrySet()) {
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append(entry.getKey());
				stringBuilder.append("=");
				for (String element : entry.getValue()) {
					stringBuilder.append(element);
					stringBuilder.append(",");
				}
				stringBuilder.append("\n");
				writer.write(stringBuilder.toString());
			}

		} catch (IOException e) {
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * method used to parse the content of dictionary after is read from file
	 * 
	 * @param line
	 *            every line from the file is parsed
	 * @param spellDictionary
	 *            object is used to be updated with data from file
	 */
	private void parsePropertiesFile(String line, SpellDictionary spellDictionary) {
		if (line != null) {
			String[] tokens = line.split("=");
			spellDictionary.getDictionary().put(tokens[0], Utils.parseSuggestions(tokens[1]));
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see repository.Repository#loadDictionary(java.lang.String)
	 */
	public void loadDictionary(String fileName) throws IOException {
		readDictionaryFile(fileName, spellDictionary);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see repository.Repository#loadTextFile(java.lang.String)
	 */
	public void loadTextFile(String fileName) throws IOException {
		spellText.setContent(Utils.readTextFile(fileName));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see repository.Repository#checkWord(java.lang.String)
	 */
	public boolean checkWord(String word) {
		return spellDictionary.getDictionary().get(word) != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see repository.Repository#suggestionsForWord(java.lang.String)
	 */
	public List<String> suggestionsForWord(String word) {
		List<String> list = spellDictionary.getDictionary().get(word);
		if (list == null) {
			return new ArrayList<>();
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see repository.Repository#findSimilarWords(java.lang.String)
	 */
	public List<String> findSimilarWords(String word) {

		List<String> similarityList = new ArrayList<>();

		this.readLock.lock();

		try {

			if (word != null) {
				Map<String, List<String>> dictionary = spellDictionary.getDictionary();
				for (Map.Entry<String, List<String>> entry : dictionary.entrySet()) {
					if (Utils.similarity(word, entry.getKey())) {
						similarityList.addAll(entry.getValue());
						break;
					} else {
						for (String element : entry.getValue()) {
							if (Utils.similarity(word, element)) {
								similarityList.addAll(entry.getValue());
								break;
							}
						}
					}
				}
			}
		} finally {
			// Unlock the reader lock
			this.readLock.unlock();
		}
		System.out.print("The similar words for: " + word + " are ");
		System.out.println(similarityList);
		return similarityList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see repository.Repository#proposeNewSuggestions(java.lang.String,
	 * java.util.List)
	 */
	public void proposeNewSuggestions(String word, List<String> suggestions) {
		// Lock the list of listeners for writing
		this.writeLock.lock();
		try {
			Map<String, List<String>> dictionary = spellDictionary.getDictionary();
			dictionary.put(word, suggestions);
			spellDictionary.setDictionary(dictionary);
			// update the file with dictionary
			saveDictionaryFile("dictionary.txt", spellDictionary);

		} finally {
			// Unlock the writer lock
			this.writeLock.unlock();
		}
		System.out.println(spellDictionary.getDictionary());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see repository.Repository#proposeNewWordDictionary(java.lang.String,
	 * java.util.List)
	 */
	public void proposeNewWordDictionary(String word, List<String> suggestions) {
		// Lock the list of listeners for writing
		Map<String, List<String>> dictionary = spellDictionary.getDictionary();
		this.writeLock.lock();
		try {
			if (!dictionary.containsKey(word)) {
				dictionary.put(word, suggestions);
				spellDictionary.setDictionary(dictionary);
				saveDictionaryFile("dictionary.txt", spellDictionary);
			}
		} finally {
			// Unlock the writer lock
			this.writeLock.unlock();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see repository.Repository#getSpellDictionary()
	 */
	public SpellDictionary getSpellDictionary() {
		return spellDictionary;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see repository.Repository#setSpellDictionary(model.SpellDictionary)
	 */
	public void setSpellDictionary(SpellDictionary spellDictionary) {
		this.spellDictionary = spellDictionary;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see repository.Repository#getSpellText()
	 */
	public SpellText getSpellText() {
		return spellText;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see repository.Repository#setSpellText(model.SpellText)
	 */
	public void setSpellText(SpellText spellText) {
		this.spellText = spellText;
	}
}

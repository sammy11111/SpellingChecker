package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpellDictionary {
	private Map<String, List<String>> dictionary = null;

	public SpellDictionary() {
		this.setDictionary(new HashMap<String, List<String>>());
	}

	public Map<String, List<String>> getDictionary() {
		return dictionary;
	}

	public void setDictionary(Map<String, List<String>> dictionary) {
		this.dictionary = dictionary;
	}
}

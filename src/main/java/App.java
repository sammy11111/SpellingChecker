import java.io.IOException;

import controller.Controller;
import controller.ControllerImpl;
import model.SpellDictionary;
import model.SpellText;
import repository.Repository;
import repository.RepositoryImpl;
import runners.SaverTimer;
import runners.SaverTimerTask;
import ui.UI;
import ui.UIImpl;

/**
 * 
 * main class used to run the application
 * 
 * @author Sammy Ibrahim
 *
 */
public class App {
	/**
	 * main method to start the application
	 * 
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException, InterruptedException {

		UI ui = new UIImpl();
		Controller controller = new ControllerImpl();
		ui.setController(controller);
		Repository repo = new RepositoryImpl();
		controller.setRepo(repo);
		SpellText spellText = new SpellText();
		repo.setSpellText(spellText);
		SpellDictionary spellDictionary = new SpellDictionary();
		repo.setSpellDictionary(spellDictionary);

		// files are load within a thread
		Thread t = new Thread() {
			public void run() {
				// load files with a thread
				try {
					ui.init("dictionary.txt", "text.txt");
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		};
		t.start();

		// the UI is loaded by other thread
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				ui.initUI();
			}
		});

		// start tasks to save the text from UI
		SaverTimerTask task = new SaverTimerTask();
		task.setController(controller);
		SaverTimer.setTimer(task);

		// some tests
		testFindSimilarities(repo, "Ade");
		testFindSimilarities(repo, "Adele");
		testFindSimilarities(repo, "Ala");
		testFindSimilarities(repo, "Alabama");
	}

	public static void testFindSimilarities(Repository repo, String word) {
		repo.findSimilarWords(word);
	}
}

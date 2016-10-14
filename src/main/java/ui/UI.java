package ui;

import java.io.IOException;

import controller.Controller;

/**
 * interface used for UI operations
 * 
 * @author Sammy Ibrahim
 *
 */
public interface UI {

	/**
	 * getter for the controller
	 * 
	 * @return the controller object
	 */
	public Controller getController();

	/**
	 * setter for the controller
	 * 
	 * @param controller
	 *            that is updated
	 */
	public void setController(Controller controller);

	/**
	 * method used to initialize the collections by reading text and dictionary
	 * from file
	 * 
	 * @param dictionaryFileName
	 *            name for the file there the dictionary is saved
	 * @param textFileName
	 *            name for the file where the text is saved
	 * @throws IOException
	 *             the exception that is thrown when a file is not found
	 */
	public void init(String dictionaryFileName, String textFileName) throws IOException;

	/**
	 * method used to init the main parts of the GUI
	 */
	public void initUI();

}

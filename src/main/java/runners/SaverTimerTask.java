package runners;

import java.util.Date;
import java.util.TimerTask;

import controller.Controller;

/**
 * class saver used to save task into file
 * 
 * @author Sammy Ibrahim
 *
 */
public class SaverTimerTask extends TimerTask {
	private Controller controller;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.TimerTask#run()
	 */
	@Override
	public void run() {
		System.out.println("Timer task started at:" + new Date());
		completeTask();
		System.out.println("Timer task finished at:" + new Date());
	}

	/**
	 * method used to process command ( saving ) and expect 10 seconds for doing
	 * this task
	 */
	private void completeTask() {
		try {
			// assuming it takes 10 secs to complete the task
			processCommand();
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * method used to save text into file with the controller
	 */
	private void processCommand() {
		controller.saveTextToFile(controller.getRepo().getSpellText().getContent(), "text.txt");

	}

	/**
	 * getter for controller
	 * 
	 * @return the controller object
	 */
	public Controller getController() {
		return controller;
	}

	/**
	 * method setter for controller
	 * 
	 * @param controller
	 *            that is updated
	 */
	public void setController(Controller controller) {
		this.controller = controller;
	}
}
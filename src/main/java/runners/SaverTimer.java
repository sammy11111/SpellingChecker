package runners;

import java.util.Timer;

/**
 * timer class used to set a timer for saving text into file
 * 
 * @author Sammy Ibrahim
 *
 */
public class SaverTimer {

	/**
	 * method used to schedule an operation(task) at some interval of time , in
	 * our case the saving is schedule to be done at every 2 seconds so that to
	 * have the most recent text updates from the UI
	 * 
	 * @param task
	 *            that is schedule to be done at some time interval
	 */
	public static void setTimer(SaverTimerTask task) {

		// running timer task as daemon thread
		Timer timer = new Timer(true);

		timer.scheduleAtFixedRate(task, 0, 2 * 1000);// at 2 seconds

		System.out.println("TimerTask started");
		// cancel after sometime
		try {
			Thread.sleep(120000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		timer.cancel();

		System.out.println("TimerTask cancelled");
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

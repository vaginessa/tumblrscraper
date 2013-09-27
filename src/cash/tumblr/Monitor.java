package cash.tumblr;

public interface Monitor {

	/**
	 * Returns a string description of what the current state is
	 */
	public String getStatus();
	/**
	 * Returns a number between 0 and 100 representing completion
	 * @return
	 */
	public int getCompletionPrecentage();
	/**
	 * Returns true or false depending if the action(s) being monitored are completed
	 * @return
	 */
	public boolean isDone();

	public void update();
	
}

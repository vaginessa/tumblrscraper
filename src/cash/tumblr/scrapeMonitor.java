package cash.tumblr;

public class scrapeMonitor implements Monitor{

	private int completion;
	private boolean completed;
	private String desc;
	private BlogScraper bs;
	public scrapeMonitor(BlogScraper bs){
		completion = 0;
		completed = false;
		desc = "Scanning - ";
		this.bs = bs;
	}
	@Override
	public String getStatus() {
		update();
		return desc + bs.getDir();
	}
	@Override
	public int getCompletionPrecentage() {
		update();
		return completion;
	}
	@Override
	public boolean isDone() {
		return completed;
	}
	public synchronized void beginScrape(){
		completion = 0;
		desc = "Scanning - ";
	}
	public synchronized void beginDownload(){
		completion = 35;
		desc = "Downloading - ";
	}
	public synchronized void doneDownload(){
		completion = 100;
		desc = "Done - ";
		completed = true;
	}
	/**
	 * Forces an update of state, usually should be unnecessary if Monitor is implemented correctly
	 * GetStatus etc should call this method
	 */
	@Override
	public synchronized void update() {
		if(bs.isDone()){
			doneDownload();
		}else if(bs.beganDownloading()){
			beginDownload();
		}else{
			beginScrape();
		}
	}
}

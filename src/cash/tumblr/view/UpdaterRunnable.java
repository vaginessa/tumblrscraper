package cash.tumblr.view;

import java.util.ArrayList;
import java.util.List;

import cash.tumblr.controller.ScrapeController;

public class UpdaterRunnable implements Runnable{
	private List<String> targets;
	private int threads;
	public UpdaterRunnable(List<String> targets,int threads){
		this.targets = targets;
		this.threads = threads;
	}

	@Override
	public void run(){

		ArrayList<String> fixed = new ArrayList<>();
		for(String target : targets)
		{
			fixed.add(target.replace("http://", "").replace("https://", "").replace("/", ""));
		}
		ScrapeController.INSTANCE.scrape(fixed, threads);
	}
}

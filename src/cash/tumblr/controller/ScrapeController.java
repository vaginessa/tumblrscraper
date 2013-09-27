package cash.tumblr.controller;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.UIManager;

import cash.tumblr.BlogScraper;
import cash.tumblr.Monitor;
import cash.tumblr.ScraperUtils;
import cash.tumblr.view.ScraperView;
import cash.tumblr.view.UpdaterRunnable;

public class ScrapeController implements Runnable {
	
	public final static ScrapeController INSTANCE = new ScrapeController();
	private String status;
	private ScrapeController() {
		status = "Idle";
	}

	@Override
	public void run() {
		// Create View //////////////////////////
		// Set View UI
		try {
			  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch(Exception e) {
			  System.out.println("Error setting native LAF: " + e);
			}
		// Create actual view
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {	
				try {
					ScraperView frame = ScraperView.INSTANCE;
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		// End Create View //////////////////////
	}
	
	public synchronized void scrape(List<String> targets,int threads){
		String[] splits;
		ScraperView.INSTANCE.getBtnStart().setEnabled(false);
		ScraperView.INSTANCE.getAddToList().setEnabled(false);
		for(String str:targets){
			splits = str.split(":");
			Monitor mon = ScraperUtils.scrapeBlog(splits[0], Integer.parseInt(splits[1]), threads);
			while(!mon.isDone()){
				this.status = mon.getStatus();
				try {
					Thread.currentThread().sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.status = mon.getStatus();
				ScraperView.INSTANCE.update();
			}
			
		
			
		}
		this.status = "Idle";
		ScraperView.INSTANCE.update();

		ScraperView.INSTANCE.getBtnStart().setEnabled(true);
		ScraperView.INSTANCE.getAddToList().setEnabled(true);
		
		
	}
	public synchronized boolean isIdle(){
		if(this.status.toLowerCase().trim().equals("idle")){
			return true;
		}
		return false;
	}
	public synchronized String status(){
		return status;
	}

}

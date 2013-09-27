package cash.tumblr;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import cash.utils.DownloadFile;
import cash.utils.SaveHelper;


public class Worker extends Thread{

	CountDownLatch cd;
	Queue<String> blogPages,targets;
	String dir;
	public Worker(Queue<String> blogPages,Queue<String> targets, CountDownLatch cd,String dir) {
		this.blogPages = blogPages;
		this.targets   = targets;
		this.cd = cd;
		this.dir = dir;
	}
	
	public void run() {
		while(!blogPages.isEmpty()){
			String page = blogPages.poll();
			if(page != null){
				List<String> results = scrapePage(page);
				for(String rez : results){
					targets.add(rez);
				}
			} 
		}
		
		cd.countDown();
		try {
			cd.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		while(!targets.isEmpty()){
			String res = targets.poll();
			if(res != null){
				downloadTarget(res,dir);
			}
		}
		
	}
	private List<String> scrapePage(String target){
		return Scraper.getImageURLs(target);
	}
	
	private void downloadTarget(String target,String dir){
		File file = SaveHelper.INSTANCE.getFile(target,dir);
		URL u = null;
		try {
			u = new URL(target);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DownloadFile dl = new DownloadFile(u, file);
		try {
			dl.call();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

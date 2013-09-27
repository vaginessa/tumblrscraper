package cash.tumblr;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class BlogScraper extends Thread{

	private int threads;
	private List<String> blogPages;
	private String dir;
	private ExecutorService exec;
	private CountDownLatch cd;
	public BlogScraper(String address, int pages,int threads) {
		if(!address.contains("http://") && !address.contains("https://")){		
			address = "http://" + address;
		}
		this.dir = address.replace("http://", "").replace("https://", "").replaceAll("/", "").trim();
		blogPages = Scraper.getBlogPages(address, pages);
		this.threads = threads;
		exec = Executors.newFixedThreadPool(threads);
		cd = new CountDownLatch(threads);
	}
	

	public String getDir(){
		return dir;
	}
	@Override
	public void run() {	
		ConcurrentLinkedQueue<String> pq = new ConcurrentLinkedQueue<>();
		ConcurrentLinkedQueue<String> rq = new ConcurrentLinkedQueue<>();
		
		for(String page:blogPages){
			pq.add(page);
		}
	
		for(int i = 0;i < threads;i++){
			Worker w = new Worker(pq, rq, cd,dir);
			exec.execute(w);
		}
		exec.shutdown();
	}
	public synchronized boolean isDone(){
		return exec.isTerminated();
	}
	public synchronized boolean beganDownloading(){
		try {
			if(cd.await(1, TimeUnit.MICROSECONDS)){
				return true;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}

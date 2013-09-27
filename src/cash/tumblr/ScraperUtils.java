package cash.tumblr;

public class ScraperUtils {

	public static Monitor scrapeBlog(String URL,int pages,int threads){
		BlogScraper bs = new BlogScraper(URL, pages, threads);
		scrapeMonitor mon = new scrapeMonitor(bs);
		bs.run();
		return mon;
	}
}

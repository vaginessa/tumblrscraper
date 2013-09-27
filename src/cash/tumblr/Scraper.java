package cash.tumblr;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraper{
	public static final int FETCH_RETRY = 3;
	
	/**
	 * Returns a List of strings which are the URLs to the amount of pages that are
	 * requested to scrape
	 * 
	 * TODO: Actually check if the pages has images
	 * @param address
	 * @param pages
	 */
	public static List<String> getBlogPages(String address,int pages){
		List<String> ret = new ArrayList<>();
		String base = "/page/";
		if(address.charAt(address.length()-1) == '/'){
			base = "page/";
		}
		for(int i = 1;i <= pages;i++){
			ret.add(address + base + Integer.toString(i));
		}
		return ret;
	}
	public static List<String> getImageURLs(String url){
		ArrayList<String> results = new ArrayList<>();
		HttpClient client = new DefaultHttpClient();
		HttpResponse resp = null;
		try{
		resp = client.execute(new HttpGet(url));
		}catch(Exception e){
			return getImages(url,1);
		}
		resp.toString();
		HttpEntity entity = resp.getEntity();
		String html = "";
		try {
			Scanner sc = new Scanner(entity.getContent());
			while(sc.hasNextLine()){
				html+=sc.nextLine()+'\n';
			}
		} catch(Exception e){
			return getImages(url, 1);
		}
		
		Document doc = Jsoup.parse(html);
		Elements ems = doc.select("img");
		for(Element e: ems){
			if(e.hasAttr("src") && e.attr("src").contains("media.tumblr.com") && !e.attr("src").contains("/avatar")){
				results.add(e.attr("src"));
			}
		}
		return results;
	}
	private static List<String> getImages(String url,int retries){
			if(retries > FETCH_RETRY){
				System.err.println("Failed to fetch page:" + url + " After " + FETCH_RETRY + "Retries," +
						"Please check your connection and that the target site is online");
				return new ArrayList<>();
			}
			
			ArrayList<String> results = new ArrayList<>();
			HttpClient client = new DefaultHttpClient();
			HttpResponse resp = null;
			try{
			resp = client.execute(new HttpGet(url));
			}catch(Exception e){
				return getImages(url, retries+1);
			}
			resp.toString();
			HttpEntity entity = resp.getEntity();
			String html = "";
			try {
				Scanner sc = new Scanner(entity.getContent());
			while(sc.hasNextLine()){
				html+=sc.nextLine()+'\n';
			}
			} catch(Exception e){
				return getImages(url, retries+1);
			}
			
			Document doc = Jsoup.parse(html);
			Elements ems = doc.select("img");
			for(Element e: ems){
				if(e.hasAttr("src") && e.attr("src").contains("media.tumblr.com") && !e.attr("src").contains("/avatar")){
					results.add(e.attr("src"));
				}
			}
			return results;
			
			
	}
}

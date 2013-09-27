package cash.tumblr;

import java.util.Scanner;


public class CLIMain {
	
	/**public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String in = "";
		System.out.println("Enter <URL> <Pages to scrape> or 'exit' to quit the program");
		in = sc.nextLine();
		String[] input = in.split(" ");
		Monitor m = ScraperUtils.scrapeBlog(input[0], Integer.parseInt(input[1]), 30);
		sc.close();
		
		while(!m.isDone()){
			System.out.println(m.getStatus());
			try {
				Thread.currentThread().sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(m.getStatus());
		
	}
	**/
}

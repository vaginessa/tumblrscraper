package cash.utils;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;


public class DownloadFile implements Callable<Boolean> {
	private URL url;
	private File file;
	
	public final int MAX_RETRIES = 3;
	public DownloadFile(URL url,File file){
		this.url = url;
		this.file = file;
	}

	@Override
	public Boolean call() throws Exception {
		try{
		    ReadableByteChannel rbc = Channels.newChannel(url.openStream());
		    FileOutputStream fos = new FileOutputStream(file);
		    fos.getChannel().transferFrom(rbc, 0, 1 << 24);
		    fos.close();
			}catch(Exception e){
				recurse(1);
			}
		
			return true;
	}
	private Boolean recurse(int depth) throws Exception {
		if(depth > MAX_RETRIES){
			System.err.println("Failed downloading file:" + url + " After " + MAX_RETRIES);
			return false;
		}
		
		try{
		    ReadableByteChannel rbc = Channels.newChannel(url.openStream());
		    FileOutputStream fos = new FileOutputStream(file);
		    fos.getChannel().transferFrom(rbc, 0, 1 << 24);
		    fos.close();
			}catch(Exception e){
				recurse(depth + 1);
			}
		
			return true;
	}
}

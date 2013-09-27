package cash.utils;

import java.io.File;
import java.net.URL;

/**
 * @author Matan
 * 
 * A web resource that can be downloaded (URL Resolvable), this object encapsulates the 
 * URL,Download state (Not downloaded, downloaded), Retry attempts, and local File.
 */
public class Resource {
	private URL target;
	private int attempts;
	private boolean downloaded;
	private File file;
	
	public Resource(URL target){
		this.target = target;
		attempts = 0;
		downloaded = false;
		file = null;
	}
	
	public URL getTarget() {
		return target;
	}
	public void setTarget(URL target) {
		this.target = target;
	}
	public int getAttempts() {
		return attempts;
	}
	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}
	public boolean isDownloaded() {
		return downloaded;
	}
	public void setDownloaded(boolean downloaded) {
		this.downloaded = downloaded;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}

	
}

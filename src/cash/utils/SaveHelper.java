package cash.utils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;

import org.apache.commons.io.FileUtils;

public class SaveHelper {
	public static final SaveHelper INSTANCE = new SaveHelper();
	
	private Map<String,Integer> domainFileIndex;
	private final String WORKING_DIR = System.getProperty("user.dir");
	private SaveHelper(){
		domainFileIndex = new HashMap<>();
	}
	public File getFile(String address,String dir){
		System.out.println("BUT UHM");
		try{
		FileUtils.forceMkdir(new File(WORKING_DIR + '\\' + "downloads" + '\\' + dir));
		int dot = address.lastIndexOf(".");
		String extension = address.substring(dot+1);
		return new File(WORKING_DIR + '\\' + "downloads" + '\\' + dir + '\\'+ getFileNumber(dir) + '.' + extension);
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("Error Getting File Location");
		}
		return null;
	}
	private synchronized int getFileNumber(String address){
		if(domainFileIndex.containsKey(address)){
			int ret = domainFileIndex.get(address);
			domainFileIndex.put(address, domainFileIndex.get(address)+1);
			return ret;
		}else{
			domainFileIndex.put(address, 2);
			return 1;
		}
	}
}

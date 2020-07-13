package com.hbm.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;

public class VersionChecker {
	
	public static boolean newVersion = false;
	public static String versionNumber = "";
	
	public static void checkVersion() {
		
		try {
			
			URL github = new URL("https://raw.githubusercontent.com/HbmMods/Hbm-s-Nuclear-Tech-GIT/master/src/main/java/com/hbm/lib/RefStrings.java");
	        BufferedReader in = new BufferedReader(new InputStreamReader(github.openStream()));
			
	        MainRegistry.logger.info("Searching for new versions...");
	        String line;
	        
	        while ((line = in.readLine()) != null) {
	        	
	            if(line.contains("String VERSION")) {

	            	int begin = line.indexOf('"');
	            	int end = line.lastIndexOf('"');
	            	
	            	String sub = line.substring(begin + 1, end);
	            	
	            	newVersion = !RefStrings.VERSION.equals(sub);
	            	versionNumber = sub;
	    	        MainRegistry.logger.info("Found version " + sub);
	    	        break;
	            }
	        }

	        MainRegistry.logger.info("Version checker ended.");
	        in.close();
	        
		} catch (IOException e) {
			MainRegistry.logger.warn("Version checker failed!");
		}
	}

}

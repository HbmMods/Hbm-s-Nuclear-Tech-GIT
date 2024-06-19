package com.hbm.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;

public class HTTPHandler {

	public static List<String> capsule = new ArrayList();
	public static boolean newVersion = false;
	public static String versionNumber = "";

	public static void loadStats() {
		
		Thread versionChecker = new Thread("NTM Version Checker") {
			
			@Override
			public void run() {
				try {
					loadVersion();
					loadSoyuz();
				} catch(IOException e) {
					MainRegistry.logger.warn("Version checker failed!");
				}
			}
			
		};
		
		versionChecker.start();
	}

	private static void loadVersion() throws IOException {

		URL github = new URL("https://raw.githubusercontent.com/JameH2/Hbm-s-Nuclear-Tech-GIT/space-travel-twopointfive/src/main/java/com/hbm/lib/RefStrings.java");
		BufferedReader in = new BufferedReader(new InputStreamReader(github.openStream()));

		MainRegistry.logger.info("Searching for new versions...");
		String line;

		while((line = in.readLine()) != null) {

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
	}

	private static void loadSoyuz() throws IOException {

		URL github = new URL("https://gist.githubusercontent.com/HbmMods/a1cad71d00b6915945a43961d0037a43/raw/soyuz_holo");
		BufferedReader in = new BufferedReader(new InputStreamReader(github.openStream()));

		String line;

		while((line = in.readLine()) != null) {
			capsule.add(line);
		}

		in.close();
	}

}

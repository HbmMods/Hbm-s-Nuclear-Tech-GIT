package com.hbm.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.hbm.util.Tuple.Pair;

/** A more lightweight, punctual version of the dreadfully slow vanilla profiler. */
public class TimeAnalyzer {

	/* instead of writing to the hashmap outright, we write it to a list since during analysis using a hashmap would add unnecessary load */
	private static List<Pair<String, Long>> deltas = new ArrayList();
	private static String currentSection = "";
	private static long sectionStartTime = 0;
	
	public static void startCount(String section) {
		currentSection = section;
		sectionStartTime = System.nanoTime();
	}
	
	public static void endCount() {
		long delta = System.nanoTime() - sectionStartTime;
		deltas.add(new Pair(currentSection, delta));
	}
	
	public static void startEndCount(String section) {
		endCount();
		startCount(section);
	}
	
	public static void dump() {
		HashMap<String, Long> milliTime = new HashMap();
		
		for(Pair<String, Long> delta : deltas) {
			Long total = milliTime.get(delta.getKey());
			if(total == null) total = new Long(0);
			total += delta.getValue();
			milliTime.put(delta.getKey(), total);
		}
		
		for(Entry<String, Long> entry : milliTime.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue() + "ns");
		}
		
		currentSection = "";
		sectionStartTime = 0;
		deltas.clear();
	}
}

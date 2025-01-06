package com.hbm.util;

import com.hbm.util.Tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;

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

		long total = 0;

		for(Entry<String, Long> entry : milliTime.entrySet()) {
			total += entry.getValue();
			String time = String.format(Locale.US, "%,d", entry.getValue());
			System.out.println(entry.getKey() + ": " + time + "ns");
		}

		System.out.println("Total time passed: " + String.format(Locale.US, "%,d", total) + "ns (" + (total / 1_000_000_000) + "s)");

		currentSection = "";
		sectionStartTime = 0;
		deltas.clear();
	}
}

package com.hbm.hrist;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PathNode {
	
	// i suppose that's a tree now
	public Map<PathInfo, List<PathInfo>> inToOut = new HashMap();

	public static class PathInfo {
		
		public PathNode leadsTo;
		public double distance;
	}
}

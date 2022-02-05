package com.hbm.animloader;

import java.util.HashMap;
import java.util.Map;

public class Animation {
	
	public static final Animation EMPTY = createBlankAnimation();
	
	public int length;
	public int numKeyFrames;
	public Map<String, Transform[]> objectTransforms = new HashMap<String, Transform[]>();
	
	private static Animation createBlankAnimation(){
		Animation anim = new Animation();
		anim.numKeyFrames = 0;
		anim.length = 0;
		anim.objectTransforms = new HashMap<String, Transform[]>();
		return anim;
	}

}
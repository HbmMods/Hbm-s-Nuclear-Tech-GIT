package com.hbm.render.block;

import java.util.ArrayList;
import java.util.List;

//the actual bus, a sequence of keyframes with their own behavior and such
public class BusAnimationSequence {
	
	//not actually useful for anything since the renderer usually handles
	//this part, but it's nice to have for distinction just in case
	public static enum EnumTransformation {
		UNDEFINED,
		ROTATION,
		TRANSLATION,
		SCALE
	}

	private List<BusAnimationKeyframe> keyframes = new ArrayList();
	public EnumTransformation transformationType;
	
	public double[] getTransformation(int millis) {
		return null;
	}
}

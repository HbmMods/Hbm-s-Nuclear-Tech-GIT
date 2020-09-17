package com.hbm.render.anim;

import java.util.ArrayList;
import java.util.List;

import com.hbm.render.anim.BusAnimationKeyframe.InterpolationType;

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
	
	//appends a keyframe at the end of the sequence
	public BusAnimationSequence addKeyframe(BusAnimationKeyframe keyframe) {
		
		keyframes.add(keyframe);
		
		return this;
	}
	
	//all transformation data is absolute, additive transformations have not yet been implemented
	public double[] getTransformation(int millis) {
		
		BusAnimationKeyframe frame = getFrameAtTime(millis);
				
		if(frame == null)
			return null;
		
		//if no interpolation type is set, just return the new coords
		if(frame.interpolationType == InterpolationType.NONE)
			return frame.toArray();
		
		//if this is the first frame, the "previous" values are 0
		double[] previous = new double[] {0, 0, 0};
		
		BusAnimationKeyframe lastFrame = getPreviousFrame(frame);
		
		if(lastFrame != null)
			previous = lastFrame.toArray();
		
		//the time elapsed during the frame is the total current time minus the starting timie of the current frame
		int frameTime = millis - getStartingTime(frame);
		double interpolation = (double)frameTime / (double)frame.duration;

		double interX = (frame.x - previous[0]) * interpolation + previous[0];
		double interY = (frame.y - previous[1]) * interpolation + previous[1];
		double interZ = (frame.z - previous[2]) * interpolation + previous[2];
		
		return new double[] {interX, interY, interZ};
	}
	
	public BusAnimationKeyframe getFrameAtTime(int millis) {

		int time = 0;
		
		for(BusAnimationKeyframe frame : keyframes) {
			time += frame.duration;
			
			if(millis < time)
				return frame;
		}
		
		return null;
	}
	
	public BusAnimationKeyframe getPreviousFrame(BusAnimationKeyframe frame) {

		int index = keyframes.indexOf(frame);
		
		if(index == 0)
			return null;
		
		return keyframes.get(index - 1);
	}
	
	public int getStartingTime(BusAnimationKeyframe start) {
		
		int time = 0;
		
		for(BusAnimationKeyframe frame : keyframes) {
			
			if(frame == start)
				break;
			
			time += frame.duration;
		}
		
		return time;
	}
	
	public int getTotalTime() {
		
		int time = 0;
		
		for(BusAnimationKeyframe frame : keyframes) {
			time += frame.duration;
		}
		
		return time;
	}
}

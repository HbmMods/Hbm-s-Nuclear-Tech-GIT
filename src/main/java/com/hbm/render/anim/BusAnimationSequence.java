package com.hbm.render.anim;

import java.util.ArrayList;
import java.util.List;

import com.hbm.render.anim.BusAnimationKeyframe.IType;

//the actual bus, a sequence of keyframes with their own behavior and such
public class BusAnimationSequence {

	public static enum Dimension {
		TX,
		TY,
		TZ,
		RX,
		RY,
		RZ,
		SX,
		SY,
		SZ
	}


	// Storing a matrix of keyframe data, each keyframe stores a SINGLE dimension, so we can stagger frames over each parameter
	private List<List<BusAnimationKeyframe>> transformKeyframes = new ArrayList<List<BusAnimationKeyframe>>(9);

	public double[] offset = new double[3];

	// swizzle me timbers
	public double[] rotMode = new double[] { 0, 1, 2 };

	public BusAnimationSequence() {
		// Initialise our keyframe storage, since it's multidimensional
		for(int i = 0; i < 9; i++) {
			transformKeyframes.add(new ArrayList<BusAnimationKeyframe>());
		}
	}
	
	// Adds a keyframe to the given dimension
	public BusAnimationSequence addKeyframe(Dimension dimension, BusAnimationKeyframe keyframe) {
		transformKeyframes.get(dimension.ordinal()).add(keyframe);
		return this;
	}

	public BusAnimationSequence addKeyframe(Dimension dimension, double value, int duration) {
		return addKeyframe(dimension, new BusAnimationKeyframe(value, duration));
	}

	/** Adds a position with a duration of 0 */
	public BusAnimationSequence setPos(double x, double y, double z) {
		return addPos(x, y, z, 0, IType.LINEAR);
	}

	/** Adds a position with the desired duration and lininterp */
	public BusAnimationSequence addPos(double x, double y, double z, int duration) {
		return addPos(x, y, z, duration, IType.LINEAR);
	}

	/** Adds a position with the desired duration and interpolation type */
	public BusAnimationSequence addPos(double x, double y, double z, int duration, IType type) {
		addKeyframe(Dimension.TX, new BusAnimationKeyframe(x, duration, type));
		addKeyframe(Dimension.TY, new BusAnimationKeyframe(y, duration, type));
		addKeyframe(Dimension.TZ, new BusAnimationKeyframe(z, duration, type));
		return this;
	}

	public BusAnimationSequence addRot(double x, double y, double z, int duration) {
		addKeyframe(Dimension.RX, new BusAnimationKeyframe(x, duration));
		addKeyframe(Dimension.RY, new BusAnimationKeyframe(y, duration));
		addKeyframe(Dimension.RZ, new BusAnimationKeyframe(z, duration));
		return this;
	}
	
	/** Repeats the previous keyframe with the same values using lininterp. Effectively makes the animation frame pause for the desired amount of milliseconds. */
	public BusAnimationSequence hold(int duration) {
		addKeyframe(Dimension.TX, new BusAnimationKeyframe(getLast(Dimension.TX), duration));
		addKeyframe(Dimension.TY, new BusAnimationKeyframe(getLast(Dimension.TY), duration));
		addKeyframe(Dimension.TZ, new BusAnimationKeyframe(getLast(Dimension.TZ), duration));
		return this;
	}
	
	/** Repeats the previous keyframe for a duration depending on the previous keyframes. Useful for getting different buses to sync up. */
	public BusAnimationSequence holdUntil(int end) {
		int duration = end - getTotalTime();
		//FIXME: holdUntil breaks as soon as the animation speed is not 1
		return hold(duration);
	}
	
	/** Grabs the numerical value for the most recent keyframe on the given dimension */
	private double getLast(Dimension dim) {
		List<BusAnimationKeyframe> keyframes = transformKeyframes.get(dim.ordinal());
		if(keyframes.isEmpty()) return 0D;
		return keyframes.get(keyframes.size() - 1).value;
	}
	
	//all transformation data is absolute, additive transformations have not yet been implemented
	public double[] getTransformation(int millis) {
		double[] transform = new double[15];

		for(int i = 0; i < 9; i++) {
			List<BusAnimationKeyframe> keyframes = transformKeyframes.get(i);

			BusAnimationKeyframe currentFrame = null;
			BusAnimationKeyframe previousFrame = null;

			int startTime = 0;
			int endTime = 0;
			for(BusAnimationKeyframe keyframe : keyframes) {
				startTime = endTime;
				endTime += keyframe.duration;
				previousFrame = currentFrame;
				currentFrame = keyframe;
				if(millis < endTime) break;
			}

			if(currentFrame == null) {
				// Scale defaults to 1, others are 0
				transform[i] = i >= 6 ? 1 : 0;
				continue;
			}

			if(millis >= endTime) {
				transform[i] = currentFrame.value;
				continue;
			}

			if(previousFrame != null && previousFrame.interpolationType == IType.CONSTANT) {
				transform[i] = previousFrame.value;
				continue;
			}

			transform[i] = currentFrame.interpolate(startTime, millis, previousFrame);
		}

		transform[9] = offset[0];
		transform[10] = offset[1];
		transform[11] = offset[2];

		transform[12] = rotMode[0];
		transform[13] = rotMode[1];
		transform[14] = rotMode[2];

		return transform;
	}
	
	public int getTotalTime() {
		int highestTime = 0;
		
		for(List<BusAnimationKeyframe> keyframes : transformKeyframes) {
			int time = 0;
			for(BusAnimationKeyframe frame : keyframes) {
				time += frame.duration;
			}

			highestTime = Math.max(time, highestTime);
		}
		
		return highestTime;
	}
}

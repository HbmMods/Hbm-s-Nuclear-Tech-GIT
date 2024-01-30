package com.hbm.render.anim;

//"pieces" that make up a bus
public class BusAnimationKeyframe {
	
	//whether the next frame "snaps" to the intended value or has interpolation
	//it's an enum so stuff like accelerated animations between just
	//two frames could be implemented
	public static enum InterpolationType {
		NONE,
		LINEAR
	}

	public double value;
	public InterpolationType interpolationType;
	public int duration;
	
	//this one can be used for "reset" type keyframes
	public BusAnimationKeyframe() {
		this.value = 0;
		this.duration = 1;
		this.interpolationType = InterpolationType.LINEAR;
	}

	public BusAnimationKeyframe(double value, int duration) {
		this();
		this.value = value;
		this.duration = duration;
	}

	public BusAnimationKeyframe(double value, int duration, InterpolationType interpolation) {
		this(value, duration);
		this.interpolationType = interpolation;
	}

}

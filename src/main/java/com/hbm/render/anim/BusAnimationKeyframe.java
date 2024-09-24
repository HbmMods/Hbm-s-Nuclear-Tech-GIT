package com.hbm.render.anim;

//"pieces" that make up a bus
public class BusAnimationKeyframe {
	
	//whether the next frame "snaps" to the intended value or has interpolation
	//it's an enum so stuff like accelerated animations between just
	//two frames could be implemented
	public static enum IType {
		CONSTANT,
		LINEAR,
		SIN_UP,
		SIN_DOWN,
		SIN_FULL,
	}

	public double value;
	public IType interpolationType;
	public int duration;
	
	//this one can be used for "reset" type keyframes
	public BusAnimationKeyframe() {
		this.value = 0;
		this.duration = 1;
		this.interpolationType = IType.LINEAR;
	}

	public BusAnimationKeyframe(double value, int duration) {
		this();
		this.value = value;
		this.duration = duration;
	}

	public BusAnimationKeyframe(double value, int duration, IType interpolation) {
		this(value, duration);
		this.interpolationType = interpolation;
	}

}

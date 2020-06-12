package com.hbm.render.anim;

//"pieces" that make up a bus
public class BusAnimationKeyframe {
	
	//whether the next frame "snaps" to the intended value or has interpolation
	//it's an enum so stuff like accelerated animations between just
	//two frames could be implemented
	public static enum InterpolationType {
		NONE,
		SMOOTH
	}
	
	//unimplemented, all current animations are absolute
	//whether the transformation happens on its own or relative to the last state
	//i.e. 5 with 15 being the previous would be 20 additive and 5 absolute, simple enough
	public static enum StateType {
		ADDITIVE,
		ABSOLTE
	}
	
	public double x;
	public double y;
	public double z;
	public InterpolationType interpolationType;
	public int duration;
	
	public double[] toArray() {
		return new double[] {x, y, z};
	}
	
	//this one can be used for "reset" type keyframes
	public BusAnimationKeyframe() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.duration = 1;
		this.interpolationType = InterpolationType.SMOOTH;
	}
	
	public BusAnimationKeyframe(double x, double y, double z, int duration) {
		this();
		this.x = x;
		this.y = y;
		this.z = z;
		this.duration = duration;
	}
	
	public BusAnimationKeyframe(double x, double y, double z, int duration, InterpolationType interpolation) {
		this(x, y, z, duration);
		this.interpolationType = interpolation;
	}
}

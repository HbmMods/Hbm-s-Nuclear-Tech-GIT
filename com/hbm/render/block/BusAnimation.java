package com.hbm.render.block;

import java.util.HashMap;

//a """simple""" implementation of an animation system
//it's the first thing i came up with and i suppose it's relatively simple but
//it's probably not since i suck at everything
//i could have jut used collada XML animations but where's the fun in that?
public class BusAnimation {
	
	//"buses" with one S since it's not a vehicle
	private final HashMap<String, BusAnimationSequence> animationBuses= new HashMap();
	//multiples buses exist simultaneously and start with 0.
	//a bus has one authority, i.e. the translation of a single part of a model or the rotation of the entire thing.
	//imagine the busses being film strips that hang from the ceiling, with the tape player
	//rolling down, picking up images from all tapes and combining them into a movie.
	
	/**
	 * Adds a bus to the animation
	 * If an object has several moving parts, each transformation type of each seperat bus should have its own bus
	 * Unless you use one bus for several things because the animation is identical, that's ok too
	 * @param name of the bus being added
	 * @param bus the bus in question
	 * @return
	 */
	public BusAnimation addBus(String name, BusAnimationSequence bus) {
		
		animationBuses.put(name, bus);
		return this;
	}
	
	/**
	 * Gets a bus from the specified name. Usually not something you want to do
	 * @param name
	 * @param bus
	 * @return
	 */
	public BusAnimationSequence getBus(String name) {
		return animationBuses.get(name);
	}
	
	/**
	 * Gets the state of a bus at a specified time
	 * @param name the name of the bus in question
	 * @param millis the elapsed time since the animation started in milliseconds
	 * @return
	 */
	public double[] getTimedTransformation(String name, int millis) {
		
		if(this.animationBuses.containsKey(name))
			return animationBuses.get(name).getTransformation(millis);
		
		return null;
	}
	
	/**
	 * reads all buses and checks if inbetween the last invocation and this one, a sound was scheduled
	 * @param lastMillis the last time the bus was checked
	 * @param millis the current time
	 */
	public void playPendingSounds(int lastMillis, int millis) {
		//TODO: pending
	}

}

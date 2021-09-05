package com.hbm.render.anim;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.entity.passive.IAnimals;

/**
 * Unfinished
 * @author Drillgon200
 *
 */
public class AnimatedModel 
{
	public static FloatBuffer auxGLMatrix = GLAllocation.createDirectFloatBuffer(16);
	
	public AnimatedModel controller;
	
	public String name = new String();
	
	public float[] transform;
	boolean hasGeometry = true;
	boolean hasTransform = false;
	
	public String geoName = new String();
	public AnimatedModel parent;
	public ArrayList<AnimatedModel> children = new ArrayList<AnimatedModel>();
	private int callList;
	
	public AnimatedModel() {
		// TODO Auto-generated constructor stub
	}
	
	public void renderAnimated(long sysTime)
	{
		renderAnimated(sysTime, null);
	}
	
	public void renderAnimated(long sysTime, IAnimatedModelCallback c)
	{
		
	}

}

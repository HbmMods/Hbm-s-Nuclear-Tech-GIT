package com.hbm.render.anim;

import java.util.HashMap;

/**
 * Unfinished
 * @author Drillgon200
 *
 */
public class Animation
{
	public static final Animation EMPTY = createBlankAnimation();
	public int length;
	public int numKeyFrames;
	public HashMap<String, Transform[]> objectTransforms = new HashMap<String, Transform[]>();
	public Animation(int l, int num)
	{
	}
	private static Animation createBlankAnimation()
	{
		Animation anim = new Animation(0, 0);
		anim.objectTransforms = new HashMap<String, Transform[]>();
		return anim;
	}
}

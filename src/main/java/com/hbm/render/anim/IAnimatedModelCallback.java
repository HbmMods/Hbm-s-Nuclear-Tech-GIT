package com.hbm.render.anim;
/**
 * Unfinished
 * @author Drillgon200
 *
 */
public interface IAnimatedModelCallback
{
	public boolean onRender(int prevFrame, int currFrame, int model, float diff, String modelName);
	public default void postRender(int prevFrame, int currFrame, int model, float diff, String modelName)
	{
		// hm
	}
}

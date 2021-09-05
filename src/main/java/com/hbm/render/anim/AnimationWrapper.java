package com.hbm.render.anim;

import javax.annotation.Nullable;

/**
 * Unfinished
 * @author Drillgon200
 *
 */
public class AnimationWrapper
{
	public static final AnimationWrapper EMPTY = new AnimationWrapper(Animation.EMPTY)
			{
		public AnimationWrapper onEnd(EndResult end)
		{
			return this;
		};
			};
	public Animation anim;
	public long startTime;
	public float speedScale = 1F;
	public boolean reverse = false;
	public EndResult endResult = EndResult.END;
	public int prevFrame = 0;
	public AnimationWrapper(Animation a)
	{
		anim = a;
		startTime = System.currentTimeMillis();
	}
	
	public AnimationWrapper(Animation a, long start)
	{
		anim = a;
		startTime = start;
	}
	
	public AnimationWrapper(Animation a, long start, float scale)
	{
		anim = a;
		startTime = start;
		speedScale = scale;
	}
	
	public AnimationWrapper onEnd(EndResult end)
	{
		endResult = end;
		return this;
	}
	
	public AnimationWrapper reverse()
	{
		reverse = !reverse;
		return this;
	}
	
	public AnimationWrapper cloneStats(AnimationWrapper toClone)
	{
		anim = toClone.anim;
		startTime = toClone.startTime;
		reverse = toClone.reverse;
		endResult = toClone.endResult;
		return this;
	}
	
	public AnimationWrapper cloneStatsWithoutTime(AnimationWrapper toClone)
	{
		anim = toClone.anim;
		reverse = toClone.reverse;
		endResult = toClone.endResult;
		return this;
	}
	
	public enum EndType
	{
		END,
		REPEAT,
		REPEAT_REVERSE,
		START_NEW,
		STAY;
	}

	public static class EndResult
	{
		public static final EndResult END = new EndResult(EndType.END, null);
		EndType type;
		AnimationWrapper next;
		public EndResult(EndType type)
		{
			this(type, null);
		}
		public EndResult(EndType type, @Nullable AnimationWrapper next)
		{
			this.type = type;
			this.next = next;
		}
	}

}

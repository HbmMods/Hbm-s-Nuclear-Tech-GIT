package com.hbm.entity.mob;

public interface IFlyingCreature {

	public static final int STATE_WALKING = 0;
	public static final int STATE_FLYING = 1;
	
	public int getFlyingState();
	public void setFlyingState(int state);
}

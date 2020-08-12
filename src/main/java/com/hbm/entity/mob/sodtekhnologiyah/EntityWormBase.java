package com.hbm.entity.mob.sodtekhnologiyah;

public abstract class EntityWormBase extends EntityBurrowing {

	public abstract int getHeadID();

	public abstract int getPartID();

	public abstract boolean getIsHead();

	public abstract void setPartID(int id);

	public abstract void setHeadID(int id);
}

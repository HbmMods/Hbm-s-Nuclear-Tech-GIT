package com.hbm.entity.mob.sodtekhnologiyah;

import net.minecraft.world.World;

public class EntityBallsOTronBase extends EntityWormBase {

	public EntityBallsOTronBase(World world) {
		super(world);
	}

	@Override
	public int getHeadID() {
		return 0;
	}

	@Override
	public int getPartID() {
		return 0;
	}

	@Override
	public boolean getIsHead() {
		return false;
	}

	@Override
	public void setPartID(int id) {
		
	}

	@Override
	public void setHeadID(int id) {
		
	}

}

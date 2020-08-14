package com.hbm.entity.mob.sodtekhnologiyah;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class EntityBallsOTronSegment extends EntityBallsOTronBase {
	
	private WormMovementBody movement = new WormMovementBody(this);

	public EntityBallsOTronSegment(World world) {
		super(world);
	}

	@Override
	public float getAttackStrength(Entity target) {
		return 0;
	}

}

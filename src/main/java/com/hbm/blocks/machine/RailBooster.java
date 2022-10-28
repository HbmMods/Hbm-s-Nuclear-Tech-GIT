package com.hbm.blocks.machine;

import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.world.World;

public class RailBooster extends RailGeneric {
	
	public RailBooster() {
		super();
		this.setMaxSpeed(1.0F);
		this.setFlexible(false);
	}

	@Override
	public void onMinecartPass(World world, EntityMinecart cart, int y, int x, int z) {
		cart.motionX *= 1.15F;
		cart.motionY *= 1.15F;
		cart.motionZ *= 1.15F;
	}
}

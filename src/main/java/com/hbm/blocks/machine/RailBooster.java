package com.hbm.blocks.machine;

import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.world.World;

public class RailBooster extends RailHighspeed {

	@Override
	public void onMinecartPass(World world, EntityMinecart cart, int y, int x, int z) {
		cart.motionX *= 1.15F;
		cart.motionY *= 1.15F;
		cart.motionZ *= 1.15F;
	}
}

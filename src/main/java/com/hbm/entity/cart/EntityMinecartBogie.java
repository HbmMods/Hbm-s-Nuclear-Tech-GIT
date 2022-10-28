package com.hbm.entity.cart;

import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.world.World;

public class EntityMinecartBogie extends EntityMinecart {

	public EntityMinecartBogie(World world) {
		super(world);
	}

	public EntityMinecartBogie(World world, double x, double y, double z) {
		super(world, x, y ,z);
	}

	@Override
	public int getMinecartType() {
		return -1;
	}
}

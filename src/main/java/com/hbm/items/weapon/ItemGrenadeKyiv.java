package com.hbm.items.weapon;

import net.minecraft.world.World;

public class ItemGrenadeKyiv extends ItemGenericGrenade {

	public ItemGrenadeKyiv(int fuse) {
		super(fuse);
	}

	public void explode(World world, double x, double y, double z) {
		world.newExplosion(null, x, y, z, 5F, true, true);
	}
}

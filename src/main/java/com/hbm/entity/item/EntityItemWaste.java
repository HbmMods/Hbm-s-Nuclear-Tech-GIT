package com.hbm.entity.item;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityItemWaste extends EntityItem {

	public EntityItemWaste(World world) {
		super(world);
	}

	public EntityItemWaste(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	public EntityItemWaste(World world, double x, double y, double z, ItemStack stack) {
		super(world, x, y, z, stack);
	}

	@Override
	public boolean isEntityInvulnerable() {
		return true;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		return false;
	}
}

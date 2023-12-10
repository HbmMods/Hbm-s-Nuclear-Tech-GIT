package com.hbm.entity.item;

import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityItemBuoyant extends EntityItem {

	public EntityItemBuoyant(World world) {
		super(world);
	}

	public EntityItemBuoyant(World world, double x, double y, double z, ItemStack stack) {
		super(world, x, y, z, stack);
	}
	
	@Override
	public void onUpdate() {
		
		if(worldObj.getBlock((int) Math.floor(posX), (int) Math.floor(posY - 0.0625), (int) Math.floor(posZ)).getMaterial() == Material.water) {
			this.motionY += 0.045D;
		}
		
		super.onUpdate();
	}
}

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

		int x = (int) Math.floor(posX);
		int y = (int) Math.floor(posY - 0.0625);
		int z = (int) Math.floor(posZ);

		if(worldObj.getBlock(x, y, z).getMaterial() == Material.water && worldObj.getBlockMetadata(x, y, z) < 8) {
			this.motionY += 0.045D;
		}
		
		super.onUpdate();
	}
}

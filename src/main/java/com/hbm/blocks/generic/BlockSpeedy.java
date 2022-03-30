package com.hbm.blocks.generic;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class BlockSpeedy extends Block {

	double speed;
	
	public BlockSpeedy(Material mat, double speed) {
		super(mat);
		this.speed = speed;
	}

	@Override
	public void onEntityWalking(World world, int x, int y, int z, Entity entity) {

		if(entity instanceof EntityLivingBase) { //prevents vehicles from going mach 5
			double tan = Math.atan2(entity.motionX, entity.motionZ);
			entity.motionX += Math.sin(tan) * speed;
			entity.motionZ += Math.cos(tan) * speed;
		}
	}
}

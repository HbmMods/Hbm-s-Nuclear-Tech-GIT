package com.hbm.blocks.generic;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class BlockGoldSand extends BlockFalling {

	public BlockGoldSand(Material mat) {
		super(mat);
	}

	@Override
	public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
		
		if(entity instanceof EntityLivingBase) {
			entity.attackEntityFrom(DamageSource.inFire, 2F);
		}
	}
}

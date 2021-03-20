package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.util.ContaminationUtil;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockGasRadonTomb extends BlockGasBase {
	
	/*
	 * You should not have come here.
	 * 
	 * This is not a place of honor. No great deed is commemorated here.
	 * 
	 * Nothing of value is here.
	 * 
	 * What is here is dangerous and repulsive.
	 * 
	 * We considered ourselves a powerful culture. We harnessed the hidden fire,
	 * and used it for our own purposes.
	 * 
	 * Then we saw the fire could burn within living things, unnoticed until it
	 * destroyed them.
	 * 
	 * And we were afraid.
	 * 
	 * We built great tombs to hold the fire for one hundred thousand years,
	 * after which it would no longer kill.
	 * 
	 * If this place is opened, the fire will not be isolated from the world,
	 * and we will have failed to protect you.
	 * 
	 * Leave this place and never come back.
	 */

	@Override
	public void onEntityCollidedWithBlock(World world, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity entity) {
		
		if(entity instanceof EntityLivingBase) {
			ContaminationUtil.applyRadDirect(entity, 0.5F);
		}
	}

	@Override
	public ForgeDirection getFirstDirection(World world, int x, int y, int z) {
		
		if(world.rand.nextInt(3) == 0)
			return ForgeDirection.UP;
		
		return ForgeDirection.DOWN;
	}

	@Override
	public ForgeDirection getSecondDirection(World world, int x, int y, int z) {
		return this.randomHorizontal(world);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		
		if(!world.isRemote) {
	
			if(rand.nextInt(10) == 0) {
				if(world.getBlock(x, y - 1, z) == Blocks.grass)
					world.setBlock(x, y - 1, z, ModBlocks.waste_earth);
			}
	
			if(rand.nextInt(600) == 0) {
				world.setBlockToAir(x, y, z);
				return;
			}
		}
		
		super.updateTick(world, x, y, z, rand);
	}
}

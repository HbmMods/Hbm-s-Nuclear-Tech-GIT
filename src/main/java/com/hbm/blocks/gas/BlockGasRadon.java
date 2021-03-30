package com.hbm.blocks.gas;

import java.util.Random;

import com.hbm.util.ArmorUtil;
import com.hbm.util.ContaminationUtil;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockGasRadon extends BlockGasBase {

	@Override
	public void onEntityCollidedWithBlock(World world, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity entity) {
		
		if(entity instanceof EntityLivingBase) {
			ContaminationUtil.applyRadDirect(entity, 0.05F);
		}
	}

	@Override
	public ForgeDirection getFirstDirection(World world, int x, int y, int z) {
		
		if(world.rand.nextInt(5) == 0)
			return ForgeDirection.UP;
		
		return ForgeDirection.DOWN;
	}

	@Override
	public ForgeDirection getSecondDirection(World world, int x, int y, int z) {
		return this.randomHorizontal(world);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {

		if(!world.isRemote && rand.nextInt(50) == 0) {
			world.setBlockToAir(x, y, z);
			return;
		}
		
		super.updateTick(world, x, y, z, rand);
	}
}

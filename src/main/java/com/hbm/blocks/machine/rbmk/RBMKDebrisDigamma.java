package com.hbm.blocks.machine.rbmk;

import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class RBMKDebrisDigamma extends RBMKDebris {


	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		
		if(!world.isRemote) {
			
			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				Block b = world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
				if((b instanceof RBMKDebris  && b != this) || b == ModBlocks.corium_block || b == ModBlocks.block_corium)
					world.setBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, this);
			}
		}
	}

	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		world.scheduleBlockUpdate(x, y, z, this, 2);
	}
}

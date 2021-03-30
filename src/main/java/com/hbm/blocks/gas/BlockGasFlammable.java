package com.hbm.blocks.gas;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockGasFlammable extends BlockGasBase {

	@Override
	public ForgeDirection getFirstDirection(World world, int x, int y, int z) {
		
		if(world.rand.nextInt(3) == 0)
			return ForgeDirection.getOrientation(world.rand.nextInt(2));
		
		return this.randomHorizontal(world);
	}

	@Override
	public ForgeDirection getSecondDirection(World world, int x, int y, int z) {
		return this.randomHorizontal(world);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {

		if(!world.isRemote) {
			
			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				Block b = world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
				
				if(b == Blocks.fire || b.getMaterial() == Material.lava || b == Blocks.torch) {
					world.setBlock(x, y, z, Blocks.fire);
					return;
				}
			}

			if(rand.nextInt(20) == 0 && world.getBlock(x, y - 1, z) == Blocks.air) {
				world.setBlockToAir(x, y, z);
				return;
			}
		}

		super.updateTick(world, x, y, z, rand);
	}

	public boolean isFlammable(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
		return true;
	}

	public int getDelay(World world) {
		return world.rand.nextInt(5) + 16;
	}
}

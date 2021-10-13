package com.hbm.blocks.bomb;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class DigammaMatter extends Block {
	
	private static Random rand = new Random(); 

	public DigammaMatter() {
		super(Material.portal);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
		return null;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		float pixel = 0.0625F;
		this.setBlockBounds(rand.nextInt(9) * pixel, rand.nextInt(9) * pixel, rand.nextInt(9) * pixel, 1.0F - rand.nextInt(9) * pixel, 1.0F - rand.nextInt(9) * pixel, 1.0F - rand.nextInt(9) * pixel);
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		
		if(!world.isRemote)
			world.scheduleBlockUpdate(x, y, z, this, 10 + rand.nextInt(40));
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {

		if(!world.isRemote) {
			
			int meta = world.getBlockMetadata(x, y, z);
			
			if(meta >= 7) {
				world.setBlock(x, y, z, Blocks.air);
			} else {

				world.setBlock(x, y, z, Blocks.air);
				//world.setBlock(x, y, z, this, meta + 1, 3);
				
				for(int i = -1; i <= 1; i++) {
					for(int j = -1; j <= 1; j++) {
						for(int k = -1; k <= 1; k++) {
							
							int dist = Math.abs(i) + Math.abs(j) + Math.abs(k);
							
							if(dist > 0 && dist < 3) {
								if(world.getBlock(x + i, y + j, z + k) != this)
									world.setBlock(x + i, y + j, z + k, this, meta + 1, 3);
							}
						}
					}
				}
			}
		}
	}
}

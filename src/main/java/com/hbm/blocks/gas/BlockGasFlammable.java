package com.hbm.blocks.gas;

import java.util.Random;

import com.hbm.interfaces.Untested;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockGasFlammable extends BlockGasBase {

	public BlockGasFlammable() {
		super(0.8F, 0.8F, 0.2F);
	}

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
				
				if(isFireSource(b)) {
					combust(world, x, y, z);
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

	@Untested
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			Block b = world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
			
			if(isFireSource(b)) {
				world.scheduleBlockUpdate(x, y, z, this, 2);
			}
		}
	}
	
	protected void combust(World world, int x, int y, int z) {
		world.setBlock(x, y, z, Blocks.fire);
	}
	
	public boolean isFireSource(Block b) {
		return b.getMaterial() == Material.fire || b.getMaterial() == Material.lava || b == Blocks.torch;
	}

	@Override
	public boolean isFlammable(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
		return true;
	}

	@Override
	public int getDelay(World world) {
		return world.rand.nextInt(5) + 16;
	}
}

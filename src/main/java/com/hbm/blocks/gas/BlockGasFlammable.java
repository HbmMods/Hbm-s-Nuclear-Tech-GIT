package com.hbm.blocks.gas;

import java.util.HashSet;
import java.util.Random;

import com.hbm.interfaces.Untested;
import com.hbm.util.Compat;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockGasFlammable extends BlockGasBase {
	
	public static HashSet<Block> fireSources = new HashSet();
	
	public BlockGasFlammable() {
		super(0.8F, 0.8F, 0.2F);
		
		if(fireSources.isEmpty()) {
			fireSources.add(Blocks.fire);
			fireSources.add(Blocks.lava);
			fireSources.add(Blocks.torch);
			fireSources.add(Blocks.lit_pumpkin);
			
			if(Compat.isModLoaded(Compat.MOD_TIC)) {
				Block stoneTorch = Compat.tryLoadBlock(Compat.MOD_TIC, "decoration.stonetorch");
				if(stoneTorch != null) {
					fireSources.add(stoneTorch);
				}
			}
		}
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

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		
		/* causes burning entities to set off the gas */
		if(!world.isRemote && entity.isBurning()) {
			this.combust(world, x, y, z);
		}
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
		return this.fireSources.contains(b);
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

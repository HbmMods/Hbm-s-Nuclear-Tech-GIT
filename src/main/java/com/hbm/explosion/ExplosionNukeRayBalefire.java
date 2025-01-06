package com.hbm.explosion;

import com.hbm.blocks.ModBlocks;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ExplosionNukeRayBalefire extends ExplosionNukeRayBatched {

	public ExplosionNukeRayBalefire(World world, int x, int y, int z, int strength, int speed, int length) {
		super(world, x, y, z, strength, speed, length);
	}
	
	protected void handleTip(int x, int y, int z) {
		
		if(world.rand.nextInt(5) == 0 && world.getBlock(x, y - 1, z).isSideSolid(world, x, y - 1, z, ForgeDirection.UP)) {
			world.setBlock(x, y, z, ModBlocks.balefire, 0, 3);
		} else {
			world.setBlock(x, y, z, Blocks.air, 0, 3);
		}
	}
}

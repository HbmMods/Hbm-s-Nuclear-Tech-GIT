package com.hbm.blocks.bomb;

import com.hbm.explosion.ExplosionChaos;
import com.hbm.interfaces.IBomb;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class BombFlameWar extends Block implements IBomb {

	public BombFlameWar(Material p_i45394_1_) {
		super(p_i45394_1_);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		
		if(world.isBlockIndirectlyGettingPowered(x, y, z)) {
			ExplosionChaos.explode(world, x, y, z, 15);
			ExplosionChaos.spawnExplosion(world, x, y, z, 75);
			ExplosionChaos.flameDeath(world, x, y, z, 100);
		}
	}

	@Override
	public BombReturnCode explode(World world, int x, int y, int z) {

		if(!world.isRemote) {
			ExplosionChaos.explode(world, x, y, z, 15);
			ExplosionChaos.spawnExplosion(world, x, y, z, 75);
			ExplosionChaos.flameDeath(world, x, y, z, 100);
		}

		return BombReturnCode.DETONATED;
	}
}

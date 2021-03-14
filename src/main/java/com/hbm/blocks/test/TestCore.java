package com.hbm.blocks.test;

import java.util.Random;

import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.entity.logic.EntityNukeExplosionMK4;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class TestCore extends Block {

	public TestCore(Material mat) {
		super(mat);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		
		if(!world.isRemote) {
			
			int meta = world.getBlockMetadata(x, y, z);
			
			if(meta >= 6) {

				world.setBlockToAir(x, y, z);
				world.spawnEntityInWorld(EntityNukeExplosionMK4.statFac(world, BombConfig.missileRadius, x + 0.5, y + 0.5, z + 0.5));

				EntityNukeCloudSmall entity2 = new EntityNukeCloudSmall(world, 1000, BombConfig.missileRadius * 0.005F);
				entity2.posX = x;
				entity2.posY = y;
				entity2.posZ = z;
				world.spawnEntityInWorld(entity2);
				
			} else if(meta > 0) {
				
				world.newExplosion(null, x + 0.5, y + 0.5, z + 0.5, 5.0F, false, true);
			}
		}
	}
}

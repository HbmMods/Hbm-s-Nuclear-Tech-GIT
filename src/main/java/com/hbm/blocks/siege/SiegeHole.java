package com.hbm.blocks.siege;

import java.util.List;
import java.util.Random;

import com.hbm.blocks.BlockBase;

import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class SiegeHole extends BlockBase {
	
	public SiegeHole(Material material) {
		super(material);
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
	}

	@Override
	public int tickRate(World world) {
		return 90 + world.rand.nextInt(20);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
		
//		List<EntitySiegeZombie> list = world.getEntitiesWithinAABB(EntitySiegeZombie.class, AxisAlignedBB.getBoundingBox(x - 5, y - 2, z - 5, x + 6, y + 3, z + 6));
//		
//		if(list.size() < 2) {
//			EntitySiegeZombie zomb = new EntitySiegeZombie(world);
//			zomb.setPositionAndRotation(x + 0.5, y + 1, z + 0.5, 0.0F, 0.0F);
//			zomb.onSpawnWithEgg(null);
//			world.spawnEntityInWorld(zomb);
//		}
	}
}

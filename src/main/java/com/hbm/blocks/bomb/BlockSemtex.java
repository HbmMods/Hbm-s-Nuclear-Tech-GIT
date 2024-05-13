package com.hbm.blocks.bomb;

import com.hbm.entity.item.EntityTNTPrimedBase;

import net.minecraft.world.World;

public class BlockSemtex extends BlockTNTBase {

	@Override
	public void explodeEntity(World world, double x, double y, double z, EntityTNTPrimedBase entity) {
		world.createExplosion(entity, x, y, z, 12F, true);
	}
}

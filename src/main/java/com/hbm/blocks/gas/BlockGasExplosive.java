package com.hbm.blocks.gas;

import net.minecraft.world.World;

public class BlockGasExplosive extends BlockGasFlammable {
	
	protected void combust(World world, int x, int y, int z) {
		super.combust(world, x, y, z);
		world.newExplosion(null, x + 0.5, y + 0.5, z + 0.5, 3F, true, false);
	}
}

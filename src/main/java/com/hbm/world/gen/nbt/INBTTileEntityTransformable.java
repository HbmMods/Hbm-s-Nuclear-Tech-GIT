package com.hbm.world.gen.nbt;

import net.minecraft.world.World;

public interface INBTTileEntityTransformable {

	/**
	 * Like INBTTransformable but for TileEntities, like for randomizing bobbleheads
	 */

	// Allows for the TE to modify itself when spawned in an NBT structure
	public void transformTE(World world, int coordBaseMode);

}
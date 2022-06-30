package com.hbm.blocks;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.world.IBlockAccess;

public interface IBlockSideRotation {

	public int getRotationFromSide(IBlockAccess world, int x, int y, int z, int side);

	public static int renderID = RenderingRegistry.getNextAvailableRenderId();
	public static int getRenderType() {
		return renderID;
	}
}

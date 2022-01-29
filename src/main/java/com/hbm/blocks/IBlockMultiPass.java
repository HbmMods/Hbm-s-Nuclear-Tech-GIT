package com.hbm.blocks;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public interface IBlockMultiPass {

	public int getPasses();

	public static int renderID = RenderingRegistry.getNextAvailableRenderId();
	public static int getRenderType() {
		return renderID;
	}
	
}

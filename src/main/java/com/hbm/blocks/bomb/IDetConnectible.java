package com.hbm.blocks.bomb;

import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public interface IDetConnectible {

	public default boolean canConnectToDetCord(IBlockAccess world, int x, int y, int z, ForgeDirection dir) {
		return true;
	}
	
	public static boolean isConnectible(IBlockAccess world, int x, int y, int z, ForgeDirection dir) {
		Block b = world.getBlock(x, y, z);
		if(b instanceof IDetConnectible) {
			return ((IDetConnectible)b).canConnectToDetCord(world, x, y, z, dir);
		}
		return false;
	}
}

package com.hbm.render.block.ct;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public interface IBlockCT {

	public IIcon[] getFragments(IBlockAccess world, int x, int y, int z);
	
	public default boolean canConnect(IBlockAccess world, int x, int y, int z, Block block) {
		return this == block;
	}
	
	public static CTStitchReceiver primeReceiver(IIconRegister reg, String textureName, IIcon blockIcon) {
		IIcon ct = reg.registerIcon(textureName + "_ct");
		return new CTStitchReceiver(blockIcon, ct);
	}
}

package com.hbm.render.block.ct;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public interface IBlockCT {

	public IIcon[] getFragments();
	
	public default boolean canConnect(IBlockAccess world, int x, int y, int z, IBlockCT block) {
		return this == block;
	}
	
	public static CTStitchReceiver primeReceiver(IIconRegister reg, String textureName, IIcon blockIcon) {
		IIcon ct = reg.registerIcon(textureName + "_ct");
		return new CTStitchReceiver(blockIcon, ct);
	}
}

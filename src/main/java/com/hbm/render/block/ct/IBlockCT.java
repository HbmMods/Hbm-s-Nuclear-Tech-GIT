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
	
	public static IIcon[] registerIcons(IIconRegister reg, String textureName, IIcon blockIcon) {
		IIcon[] frags = new IIcon[20];
		
		IIcon ct = new IconGeneric(textureName + "_ct");
		reg.registerIcon(textureName + "_ct");
		
		for(int i = 0; i < frags.length; i++) {
			frags[i] = new IconCT(i < 4 ? blockIcon : ct, i);
		}
		
		return frags;
	}
}

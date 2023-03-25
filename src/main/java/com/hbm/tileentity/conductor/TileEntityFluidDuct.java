package com.hbm.tileentity.conductor;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityFluidDuct extends TileEntityFluidDuctSimple {
	
	public TileEntityFluidDuct() { }
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}

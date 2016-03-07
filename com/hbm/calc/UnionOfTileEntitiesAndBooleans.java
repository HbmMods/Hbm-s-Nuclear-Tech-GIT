package com.hbm.calc;

import net.minecraft.tileentity.TileEntity;

public class UnionOfTileEntitiesAndBooleans {
	
	public UnionOfTileEntitiesAndBooleans(TileEntity tileentity, boolean bool)
	{
		source = tileentity;
		ticked = bool;
	}
	
	public TileEntity source;
	public boolean ticked = false;
}

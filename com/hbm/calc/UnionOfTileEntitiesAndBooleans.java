package com.hbm.calc;

import com.hbm.interfaces.ISource;

import net.minecraft.tileentity.TileEntity;

public class UnionOfTileEntitiesAndBooleans {
	
	public UnionOfTileEntitiesAndBooleans(ISource tileentity, boolean bool)
	{
		source = tileentity;
		ticked = bool;
	}

	public ISource source;
	public boolean ticked = false;
}

package com.hbm.calc;

import com.hbm.interfaces.IOilSource;

public class UnionOfTileEntitiesAndBooleansForOil {
	
	public UnionOfTileEntitiesAndBooleansForOil(IOilSource tileentity, boolean bool)
	{
		source = tileentity;
		ticked = bool;
	}

	public IOilSource source;
	public boolean ticked = false;
}

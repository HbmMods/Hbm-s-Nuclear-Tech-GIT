package com.hbm.calc;

import com.hbm.interfaces.IGasSource;

public class UnionOfTileEntitiesAndBooleansForGas {
	
	public UnionOfTileEntitiesAndBooleansForGas(IGasSource tileentity, boolean bool)
	{
		source = tileentity;
		ticked = bool;
	}

	public IGasSource source;
	public boolean ticked = false;

}

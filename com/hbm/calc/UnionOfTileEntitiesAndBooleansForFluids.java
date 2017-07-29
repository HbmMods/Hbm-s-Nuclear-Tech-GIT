package com.hbm.calc;

import com.hbm.interfaces.IFluidSource;
import com.hbm.interfaces.IOilSource;

public class UnionOfTileEntitiesAndBooleansForFluids {
	
	public UnionOfTileEntitiesAndBooleansForFluids(IFluidSource tileentity, boolean bool)
	{
		source = tileentity;
		ticked = bool;
	}

	public IFluidSource source;
	public boolean ticked = false;
}

package com.hbm.calc;

import com.hbm.interfaces.ISource;
import com.hbm.interfaces.Spaghetti;

@Spaghetti("i deserve to be shot for this one")
public class UnionOfTileEntitiesAndBooleans {
	
	public UnionOfTileEntitiesAndBooleans(ISource tileentity, boolean bool)
	{
		source = tileentity;
		ticked = bool;
	}

	public ISource source;
	public boolean ticked = false;
}

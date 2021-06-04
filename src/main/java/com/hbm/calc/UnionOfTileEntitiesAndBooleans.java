package com.hbm.calc;

import api.hbm.energy.IEnergySource;
import com.hbm.interfaces.Spaghetti;

@Spaghetti("i deserve to be shot for this one")
public class UnionOfTileEntitiesAndBooleans {
	
	public UnionOfTileEntitiesAndBooleans(IEnergySource tileentity, boolean bool)
	{
		source = tileentity;
		ticked = bool;
	}

	public IEnergySource source;
	public boolean ticked = false;
}

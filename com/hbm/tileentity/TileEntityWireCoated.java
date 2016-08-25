package com.hbm.tileentity;

import java.util.ArrayList;
import java.util.List;

import com.hbm.calc.UnionOfTileEntitiesAndBooleans;
import com.hbm.interfaces.IConductor;
import com.hbm.lib.Library;

import net.minecraft.tileentity.TileEntity;

public class TileEntityWireCoated extends TileEntity implements IConductor {
	
	public List<UnionOfTileEntitiesAndBooleans> uoteab = new ArrayList<UnionOfTileEntitiesAndBooleans>();

}

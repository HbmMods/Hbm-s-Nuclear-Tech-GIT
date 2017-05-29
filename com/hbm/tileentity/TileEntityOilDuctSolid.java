package com.hbm.tileentity;

import java.util.ArrayList;
import java.util.List;

import com.hbm.calc.UnionOfTileEntitiesAndBooleansForOil;
import com.hbm.interfaces.IOilDuct;

import net.minecraft.tileentity.TileEntity;

public class TileEntityOilDuctSolid extends TileEntity implements IOilDuct {
	
	public List<UnionOfTileEntitiesAndBooleansForOil> uoteab = new ArrayList<UnionOfTileEntitiesAndBooleansForOil>();

}

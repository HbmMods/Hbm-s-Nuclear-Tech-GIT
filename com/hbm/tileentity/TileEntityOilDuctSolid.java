package com.hbm.tileentity;

import java.util.ArrayList;
import java.util.List;

import com.hbm.calc.UnionOfTileEntitiesAndBooleansForOil;
import com.hbm.interfaces.IDuct;

import net.minecraft.tileentity.TileEntity;

public class TileEntityOilDuctSolid extends TileEntity implements IDuct {
	
	public List<UnionOfTileEntitiesAndBooleansForOil> uoteab = new ArrayList<UnionOfTileEntitiesAndBooleansForOil>();

}

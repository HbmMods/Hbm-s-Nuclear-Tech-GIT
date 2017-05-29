package com.hbm.tileentity;

import java.util.ArrayList;
import java.util.List;

import com.hbm.calc.UnionOfTileEntitiesAndBooleansForGas;
import com.hbm.calc.UnionOfTileEntitiesAndBooleansForOil;
import com.hbm.interfaces.IGasDuct;
import com.hbm.interfaces.IOilDuct;

import net.minecraft.tileentity.TileEntity;

public class TileEntityGasDuctSolid extends TileEntity implements IGasDuct {

	public List<UnionOfTileEntitiesAndBooleansForGas> uoteab = new ArrayList<UnionOfTileEntitiesAndBooleansForGas>();

}

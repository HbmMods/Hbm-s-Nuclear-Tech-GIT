package com.hbm.tileentity;

import java.util.ArrayList;
import java.util.List;

import com.hbm.calc.UnionOfTileEntitiesAndBooleansForFluids;
import com.hbm.calc.UnionOfTileEntitiesAndBooleansForOil;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IFluidDuct;
import com.hbm.interfaces.IOilDuct;

import net.minecraft.tileentity.TileEntity;

public class TileEntityOilDuctSolid extends TileEntity implements IFluidDuct {

	public FluidType type = FluidType.OIL;
	public List<UnionOfTileEntitiesAndBooleansForFluids> uoteab = new ArrayList<UnionOfTileEntitiesAndBooleansForFluids>();

}

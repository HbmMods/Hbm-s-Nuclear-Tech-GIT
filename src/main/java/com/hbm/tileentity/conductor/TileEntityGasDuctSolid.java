package com.hbm.tileentity.conductor;

import java.util.ArrayList;
import java.util.List;

import com.hbm.calc.UnionOfTileEntitiesAndBooleansForFluids;
import com.hbm.calc.UnionOfTileEntitiesAndBooleansForGas;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IFluidDuct;
import com.hbm.interfaces.IGasDuct;
import net.minecraft.tileentity.TileEntity;

public class TileEntityGasDuctSolid extends TileEntity implements IFluidDuct {

	public FluidType type = FluidType.GAS;
	public List<UnionOfTileEntitiesAndBooleansForFluids> uoteab = new ArrayList<UnionOfTileEntitiesAndBooleansForFluids>();

}

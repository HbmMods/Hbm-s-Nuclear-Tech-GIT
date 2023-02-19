package com.hbm.blocks.machine.rbmk;

import com.hbm.blocks.generic.BlockGeneric;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.trait.FT_Coolable;
import com.hbm.inventory.fluid.trait.FT_Heatable;

import api.hbm.fluid.IFluidConnectorBlock;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public class RBMKLoader extends BlockGeneric implements IFluidConnectorBlock {

	public RBMKLoader(Material material) {
		super(material);
	}

	@Override
	public boolean canConnect(FluidType type, IBlockAccess world, int x, int y, int z, ForgeDirection dir) {
		if(dir == ForgeDirection.UP) return type.hasTrait(FT_Heatable.class);
		return type.hasTrait(FT_Coolable.class);
	}

}

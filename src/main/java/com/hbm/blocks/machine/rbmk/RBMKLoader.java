package com.hbm.blocks.machine.rbmk;

import java.util.List;

import com.hbm.blocks.ITooltipProvider;
import com.hbm.blocks.generic.BlockGeneric;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.trait.FT_Coolable;
import com.hbm.inventory.fluid.trait.FT_Heatable;

import api.hbm.fluid.IFluidConnectorBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public class RBMKLoader extends BlockGeneric implements IFluidConnectorBlock, ITooltipProvider {

	public RBMKLoader(Material material) {
		super(material);
	}

	@Override
	public boolean canConnect(FluidType type, IBlockAccess world, int x, int y, int z, ForgeDirection dir) {
		if(dir == ForgeDirection.UP) return type.hasTrait(FT_Heatable.class);
		return type.hasTrait(FT_Coolable.class);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		this.addStandardInfo(stack, player, list, ext);
	}

}

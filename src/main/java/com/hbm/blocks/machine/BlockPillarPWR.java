package com.hbm.blocks.machine;

import java.util.List;

import com.hbm.blocks.ITooltipProvider;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class BlockPillarPWR extends BlockPillar implements ITooltipProvider {

	public BlockPillarPWR(Material mat, String top) {
		super(mat, top);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		this.addStandardInfo(stack, player, list, ext);
	}
}

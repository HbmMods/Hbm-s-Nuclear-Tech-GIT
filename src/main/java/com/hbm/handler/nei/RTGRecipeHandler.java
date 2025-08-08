package com.hbm.handler.nei;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.machine.ItemRTGPellet;

import net.minecraft.item.ItemStack;

public class RTGRecipeHandler extends NEIUniversalHandler {

	public RTGRecipeHandler() {
		super("RTG", new ItemStack[] {
				new ItemStack(ModBlocks.machine_rtg_grey),
				new ItemStack(ModBlocks.machine_difurnace_rtg_off)
			}, ItemRTGPellet.getRecipeMap());
	}

	@Override
	public String getKey() {
		return "ntmRTG";
	}
}

package com.hbm.handler.nei;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.MixerRecipes;

public class MixerHandler extends NEIUniversalHandler {

	public MixerHandler() {
		super("Mixer", ModBlocks.machine_mixer, MixerRecipes.getRecipes());
	}

	@Override
	public String getKey() {
		return "ntmMixer";
	}
}

package com.hbm.handler.nei;

import com.hbm.blocks.ModBlocks;
import com.hbm.tileentity.machine.TileEntitySawmill;

public class SawmillHandler extends NEIUniversalHandler {

	public SawmillHandler() {
		super("Sawmill", ModBlocks.machine_sawmill, TileEntitySawmill.getRecipes());
	}

	@Override
	public String getKey() {
		return "ntmSawmill";
	}
}

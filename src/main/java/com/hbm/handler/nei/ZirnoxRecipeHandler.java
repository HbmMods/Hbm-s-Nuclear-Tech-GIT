package com.hbm.handler.nei;

import com.hbm.blocks.ModBlocks;
import com.hbm.tileentity.machine.TileEntityReactorZirnox;

public class ZirnoxRecipeHandler extends NEIUniversalHandler {

	public ZirnoxRecipeHandler() {
		super("ZIRNOX", ModBlocks.reactor_zirnox, TileEntityReactorZirnox.fuelMap);
	}

	@Override
	public String getKey() {
		return "ntmZirnox";
	}
}

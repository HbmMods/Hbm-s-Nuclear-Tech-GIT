package com.hbm.handler.nei;

import com.hbm.blocks.generic.BlockToolConversion;

public class ToolingHandler extends NEIUniversalHandler {

	public ToolingHandler() {
		super("Tooling", BlockToolConversion.getRecipes(true), BlockToolConversion.getRecipes(false));
	}

	@Override
	public String getKey() {
		return "ntmTooling";
	}
}

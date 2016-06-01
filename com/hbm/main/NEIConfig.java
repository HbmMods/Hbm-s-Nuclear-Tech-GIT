package com.hbm.main;

import com.hbm.gui.GUITestDiFurnace;
import com.hbm.handler.AlloyFurnaceRecipeHandler;
import com.hbm.handler.CentrifugeRecipeHandler;
import com.hbm.handler.ReactorRecipeHandler;
import com.hbm.lib.RefStrings;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

public class NEIConfig implements IConfigureNEI {

	@Override
	public void loadConfig() {
		API.registerRecipeHandler(new AlloyFurnaceRecipeHandler());
		API.registerUsageHandler(new AlloyFurnaceRecipeHandler());
		API.registerRecipeHandler(new CentrifugeRecipeHandler());
		API.registerUsageHandler(new CentrifugeRecipeHandler());
		API.registerRecipeHandler(new ReactorRecipeHandler());
		API.registerUsageHandler(new ReactorRecipeHandler());
		
	}

	@Override
	public String getName() {
		return "Nuclear Tech NEI Plugin";
	}

	@Override
	public String getVersion() {
		return RefStrings.VERSION;
	}

}

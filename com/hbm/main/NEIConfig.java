package com.hbm.main;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.AlloyFurnaceRecipeHandler;
import com.hbm.handler.CMBFurnaceRecipeHandler;
import com.hbm.handler.CentrifugeRecipeHandler;
import com.hbm.handler.CyclotronRecipeHandler;
import com.hbm.handler.ReactorRecipeHandler;
import com.hbm.handler.ShredderRecipeHandler;
import com.hbm.inventory.gui.GUITestDiFurnace;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class NEIConfig implements IConfigureNEI {

	@Override
	public void loadConfig() {
		API.registerRecipeHandler(new AlloyFurnaceRecipeHandler());
		API.registerUsageHandler(new AlloyFurnaceRecipeHandler());
		API.registerRecipeHandler(new CentrifugeRecipeHandler());
		API.registerUsageHandler(new CentrifugeRecipeHandler());
		API.registerRecipeHandler(new ReactorRecipeHandler());
		API.registerUsageHandler(new ReactorRecipeHandler());
		API.registerRecipeHandler(new ShredderRecipeHandler());
		API.registerUsageHandler(new ShredderRecipeHandler());
		API.registerRecipeHandler(new CMBFurnaceRecipeHandler());
		API.registerUsageHandler(new CMBFurnaceRecipeHandler());
		API.registerRecipeHandler(new CyclotronRecipeHandler());
		API.registerUsageHandler(new CyclotronRecipeHandler());

		//Some things are even beyond my control...or are they?
		API.hideItem(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_coal_on)));
		API.hideItem(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_electric_furnace_on)));
		API.hideItem(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_difurnace_on)));
		API.hideItem(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_nuke_furnace_on)));
		API.hideItem(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_rtg_furnace_on)));
		API.hideItem(new ItemStack(Item.getItemFromBlock(ModBlocks.reinforced_lamp_on)));
		API.hideItem(new ItemStack(Item.getItemFromBlock(ModBlocks.statue_elb)));
		API.hideItem(new ItemStack(Item.getItemFromBlock(ModBlocks.statue_elb_g)));
		API.hideItem(new ItemStack(Item.getItemFromBlock(ModBlocks.statue_elb_w)));
		API.hideItem(new ItemStack(Item.getItemFromBlock(ModBlocks.statue_elb_f)));
		API.hideItem(new ItemStack(Item.getItemFromBlock(ModBlocks.cheater_virus)));
		API.hideItem(new ItemStack(Item.getItemFromBlock(ModBlocks.cheater_virus_seed)));
		API.hideItem(new ItemStack(ModItems.euphemium_helmet));
		API.hideItem(new ItemStack(ModItems.euphemium_plate));
		API.hideItem(new ItemStack(ModItems.euphemium_legs));
		API.hideItem(new ItemStack(ModItems.euphemium_boots));
		API.hideItem(new ItemStack(ModItems.apple_euphemium));
		API.hideItem(new ItemStack(ModItems.ingot_euphemium));
		API.hideItem(new ItemStack(ModItems.nugget_euphemium));
		API.hideItem(new ItemStack(ModItems.euphemium_kit));
		API.hideItem(new ItemStack(ModItems.euphemium_stopper));
		API.hideItem(new ItemStack(ModItems.watch));
		API.hideItem(new ItemStack(ModItems.rod_quad_euphemium));
		API.hideItem(new ItemStack(ModItems.rod_euphemium));
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

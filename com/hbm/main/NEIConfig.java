package com.hbm.main;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.nei.*;
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
		API.registerUsageHandler(new GasCentrifugeRecipeHandler());
		API.registerRecipeHandler(new GasCentrifugeRecipeHandler());
		API.registerUsageHandler(new CentrifugeRecipeHandler());
		API.registerRecipeHandler(new ReactorRecipeHandler());
		API.registerUsageHandler(new ReactorRecipeHandler());
		API.registerRecipeHandler(new ShredderRecipeHandler());
		API.registerUsageHandler(new ShredderRecipeHandler());
		API.registerRecipeHandler(new CMBFurnaceRecipeHandler());
		API.registerUsageHandler(new CMBFurnaceRecipeHandler());
		API.registerRecipeHandler(new CyclotronRecipeHandler());
		API.registerUsageHandler(new CyclotronRecipeHandler());
		API.registerRecipeHandler(new AssemblerRecipeHandler());
		API.registerUsageHandler(new AssemblerRecipeHandler());
		API.registerRecipeHandler(new RefineryRecipeHandler());
		API.registerUsageHandler(new RefineryRecipeHandler());
		API.registerRecipeHandler(new BoilerRecipeHandler());
		API.registerUsageHandler(new BoilerRecipeHandler());
		//API.registerRecipeHandler(new ModInfoHandler());
		//API.registerUsageHandler(new ModInfoHandler());
		API.registerRecipeHandler(new ChemplantRecipeHandler());
		API.registerUsageHandler(new ChemplantRecipeHandler());
		API.registerRecipeHandler(new FluidRecipeHandler());
		API.registerUsageHandler(new FluidRecipeHandler());
		API.registerRecipeHandler(new PressRecipeHandler());
		API.registerUsageHandler(new PressRecipeHandler());

		//Some things are even beyond my control...or are they?
		API.hideItem(new ItemStack(ModItems.memory));
		
		API.hideItem(new ItemStack(ModBlocks.machine_coal_on));
		API.hideItem(new ItemStack(ModBlocks.machine_electric_furnace_on));
		API.hideItem(new ItemStack(ModBlocks.machine_difurnace_on));
		API.hideItem(new ItemStack(ModBlocks.machine_nuke_furnace_on));
		API.hideItem(new ItemStack(ModBlocks.machine_rtg_furnace_on));
		API.hideItem(new ItemStack(ModBlocks.reinforced_lamp_on));
		API.hideItem(new ItemStack(ModBlocks.statue_elb));
		API.hideItem(new ItemStack(ModBlocks.statue_elb_g));
		API.hideItem(new ItemStack(ModBlocks.statue_elb_w));
		API.hideItem(new ItemStack(ModBlocks.statue_elb_f));
		API.hideItem(new ItemStack(ModBlocks.cheater_virus));
		API.hideItem(new ItemStack(ModBlocks.cheater_virus_seed));
		//API.hideItem(new ItemStack(ModItems.euphemium_helmet));
		//API.hideItem(new ItemStack(ModItems.euphemium_plate));
		//API.hideItem(new ItemStack(ModItems.euphemium_legs));
		//API.hideItem(new ItemStack(ModItems.euphemium_boots));
		//API.hideItem(new ItemStack(ModItems.apple_euphemium));
		//API.hideItem(new ItemStack(ModItems.ingot_euphemium));
		//API.hideItem(new ItemStack(ModItems.nugget_euphemium));
		API.hideItem(new ItemStack(ModItems.euphemium_kit));
		//API.hideItem(new ItemStack(ModItems.euphemium_stopper));
		//API.hideItem(new ItemStack(ModItems.watch));
		//API.hideItem(new ItemStack(ModItems.rod_quad_euphemium));
		//API.hideItem(new ItemStack(ModItems.rod_euphemium));
		if(MainRegistry.polaroidID != 11) {
			API.hideItem(new ItemStack(ModItems.book_secret));
			API.hideItem(new ItemStack(ModItems.book_of_));
			API.hideItem(new ItemStack(ModItems.burnt_bark));
			API.hideItem(new ItemStack(ModItems.ams_core_thingy));
		}
		API.hideItem(new ItemStack(ModBlocks.dummy_block_assembler));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_centrifuge));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_chemplant));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_cyclotron));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_drill));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_flare));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_fluidtank));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_igenerator));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_pumpjack));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_refinery));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_turbofan));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_well));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_ams_base));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_ams_emitter));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_ams_limiter));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_reactor_small));
		API.hideItem(new ItemStack(ModBlocks.dummy_port_assembler));
		API.hideItem(new ItemStack(ModBlocks.dummy_port_chemplant));
		API.hideItem(new ItemStack(ModBlocks.dummy_port_cyclotron));
		API.hideItem(new ItemStack(ModBlocks.dummy_port_drill));
		API.hideItem(new ItemStack(ModBlocks.dummy_port_flare));
		API.hideItem(new ItemStack(ModBlocks.dummy_port_fluidtank));
		API.hideItem(new ItemStack(ModBlocks.dummy_port_igenerator));
		API.hideItem(new ItemStack(ModBlocks.dummy_port_pumpjack));
		API.hideItem(new ItemStack(ModBlocks.dummy_port_refinery));
		API.hideItem(new ItemStack(ModBlocks.dummy_port_turbofan));
		API.hideItem(new ItemStack(ModBlocks.dummy_port_well));
		API.hideItem(new ItemStack(ModBlocks.dummy_port_ams_base));
		API.hideItem(new ItemStack(ModBlocks.dummy_port_ams_emitter));
		API.hideItem(new ItemStack(ModBlocks.dummy_port_ams_limiter));
		API.hideItem(new ItemStack(ModBlocks.dummy_port_reactor_small));
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

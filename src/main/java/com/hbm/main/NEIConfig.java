package com.hbm.main;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.nei.*;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemBattery;
import com.hbm.lib.RefStrings;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import net.minecraft.item.ItemStack;

public class NEIConfig implements IConfigureNEI {

	@Override
	public void loadConfig() {
		API.registerRecipeHandler(new AlloyFurnaceRecipeHandler());
		API.registerUsageHandler(new AlloyFurnaceRecipeHandler());
		API.registerRecipeHandler(new CentrifugeRecipeHandler());
		API.registerUsageHandler(new CentrifugeRecipeHandler());
		API.registerRecipeHandler(new GasCentrifugeRecipeHandler());
		API.registerUsageHandler(new GasCentrifugeRecipeHandler());
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
		API.registerRecipeHandler(new ChemplantRecipeHandler());
		API.registerUsageHandler(new ChemplantRecipeHandler());
		API.registerRecipeHandler(new FluidRecipeHandler());
		API.registerUsageHandler(new FluidRecipeHandler());
		API.registerRecipeHandler(new PressRecipeHandler());
		API.registerUsageHandler(new PressRecipeHandler());
		API.registerRecipeHandler(new CrystallizerRecipeHandler());
		API.registerUsageHandler(new CrystallizerRecipeHandler());
		API.registerRecipeHandler(new BookRecipeHandler());
		API.registerUsageHandler(new BookRecipeHandler());
		API.registerRecipeHandler(new FusionRecipeHandler());
		API.registerUsageHandler(new FusionRecipeHandler());
		API.registerRecipeHandler(new HadronRecipeHandler());
		API.registerUsageHandler(new HadronRecipeHandler());
		API.registerRecipeHandler(new SILEXRecipeHandler());
		API.registerUsageHandler(new SILEXRecipeHandler());

		//Some things are even beyond my control...or are they?
		API.hideItem(ItemBattery.getEmptyBattery(ModItems.memory));
		API.hideItem(ItemBattery.getFullBattery(ModItems.memory));
		
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
		API.hideItem(new ItemStack(ModItems.euphemium_kit));
		API.hideItem(new ItemStack(ModItems.bobmazon_hidden));
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
		API.hideItem(new ItemStack(ModBlocks.dummy_block_radgen));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_vault));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_blast));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_gascent));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_uf6));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_puf6));
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
		API.hideItem(new ItemStack(ModBlocks.dummy_port_radgen));
		API.hideItem(new ItemStack(ModBlocks.dummy_port_compact_launcher));
		API.hideItem(new ItemStack(ModBlocks.dummy_port_launch_table));
		API.hideItem(new ItemStack(ModBlocks.dummy_plate_compact_launcher));
		API.hideItem(new ItemStack(ModBlocks.dummy_plate_launch_table));
		API.hideItem(new ItemStack(ModBlocks.dummy_plate_cargo));

		API.hideItem(new ItemStack(ModBlocks.pink_log));
		API.hideItem(new ItemStack(ModBlocks.pink_planks));
		API.hideItem(new ItemStack(ModBlocks.pink_slab));
		API.hideItem(new ItemStack(ModBlocks.pink_double_slab));
		API.hideItem(new ItemStack(ModBlocks.pink_stairs));
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

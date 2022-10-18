package com.hbm.main;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockMotherOfAllOres.TileEntityRandomOre;
import com.hbm.config.VersatileConfig;
import com.hbm.handler.nei.*;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemBattery;
import com.hbm.lib.RefStrings;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.api.IHighlightHandler;
import codechicken.nei.api.ItemInfo.Layout;
import codechicken.nei.recipe.ICraftingHandler;
import codechicken.nei.recipe.IUsageHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class NEIConfig implements IConfigureNEI {

	@Override
	public void loadConfig() {
		registerHandler(new AlloyFurnaceRecipeHandler());
		registerHandler(new CentrifugeRecipeHandler());
		registerHandler(new GasCentrifugeRecipeHandler());
		registerHandler(new BreederRecipeHandler());
		registerHandler(new ShredderRecipeHandler());
		registerHandler(new CMBFurnaceRecipeHandler());
		registerHandler(new CyclotronRecipeHandler());
		registerHandler(new AssemblerRecipeHandler());
		registerHandler(new RefineryRecipeHandler());
		registerHandler(new BoilerRecipeHandler());
		registerHandler(new ChemplantRecipeHandler());
		registerHandler(new FluidRecipeHandler());
		registerHandler(new PressRecipeHandler());
		registerHandler(new CrystallizerRecipeHandler());
		registerHandler(new BookRecipeHandler());
		registerHandler(new FusionRecipeHandler());
		registerHandler(new HadronRecipeHandler());
		registerHandler(new SILEXRecipeHandler());
		registerHandler(new SmithingRecipeHandler());
		registerHandler(new AnvilRecipeHandler());
		registerHandler(new FuelPoolHandler());
		registerHandler(new RadiolysisRecipeHandler());
		registerHandler(new CrucibleSmeltingHandler());
		registerHandler(new CrucibleAlloyingHandler());
		registerHandler(new CrucibleCastingHandler());
		registerHandler(new ChunkyHandler());
		
		//universal boyes
		registerHandler(new ZirnoxRecipeHandler());
		if(VersatileConfig.rtgDecay()) {
			registerHandler(new RTGRecipeHandler());
		}
		registerHandler(new LiquefactionHandler());
		registerHandler(new SolidificationHandler());
		registerHandler(new CrackingHandler());
		registerHandler(new FractioningHandler());

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
		API.hideItem(new ItemStack(ModBlocks.transission_hatch));
		API.hideItem(new ItemStack(ModItems.euphemium_kit));
		API.hideItem(new ItemStack(ModItems.bobmazon_hidden));
		API.hideItem(new ItemStack(ModItems.coin_siege));
		API.hideItem(new ItemStack(ModItems.source));
		if(MainRegistry.polaroidID != 11) {
			API.hideItem(new ItemStack(ModItems.book_secret));
			API.hideItem(new ItemStack(ModItems.book_of_));
			API.hideItem(new ItemStack(ModItems.burnt_bark));
			API.hideItem(new ItemStack(ModItems.ams_core_thingy));
		}
		API.hideItem(new ItemStack(ModBlocks.dummy_block_assembler));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_drill));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_fluidtank));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_refinery));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_turbofan));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_ams_base));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_ams_emitter));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_ams_limiter));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_vault));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_blast));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_uf6));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_puf6));
		API.hideItem(new ItemStack(ModBlocks.dummy_port_assembler));
		API.hideItem(new ItemStack(ModBlocks.dummy_port_drill));
		API.hideItem(new ItemStack(ModBlocks.dummy_port_fluidtank));
		API.hideItem(new ItemStack(ModBlocks.dummy_port_refinery));
		API.hideItem(new ItemStack(ModBlocks.dummy_port_turbofan));
		API.hideItem(new ItemStack(ModBlocks.dummy_port_ams_base));
		API.hideItem(new ItemStack(ModBlocks.dummy_port_ams_emitter));
		API.hideItem(new ItemStack(ModBlocks.dummy_port_ams_limiter));
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
		
		API.registerHighlightIdentifier(ModBlocks.ore_random, new IHighlightHandler() {

			@Override
			public ItemStack identifyHighlight(World world, EntityPlayer player, MovingObjectPosition mop) {
				int x = mop.blockX;
				int y = mop.blockY;
				int z = mop.blockZ;
				
				TileEntity te = world.getTileEntity(x, y, z);
				
				if(te instanceof TileEntityRandomOre) {
					TileEntityRandomOre ore = (TileEntityRandomOre) te;
					return new ItemStack(ModBlocks.ore_random, 1, ore.getStackId());
				}
				
				return null;
			}

			@Override
			public List<String> handleTextData(ItemStack itemStack, World world, EntityPlayer player, MovingObjectPosition mop, List<String> currenttip, Layout layout) {
				return currenttip;
			}
			
		});
	}
	
	public static void registerHandler(Object o) {
		API.registerRecipeHandler((ICraftingHandler) o);
		API.registerUsageHandler((IUsageHandler) o);
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

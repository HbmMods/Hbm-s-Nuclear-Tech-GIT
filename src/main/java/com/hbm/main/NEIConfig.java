package com.hbm.main;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockMotherOfAllOres.TileEntityRandomOre;
import com.hbm.config.VersatileConfig;
import com.hbm.handler.nei.*;
import com.hbm.inventory.recipes.MachineRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemBattery;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityReactorResearch;
import com.hbm.tileentity.machine.TileEntityStorageDrum;
import com.hbm.util.I18nUtil;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.api.IHighlightHandler;
import codechicken.nei.api.ItemInfo.Layout;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class NEIConfig implements IConfigureNEI {

	public static final class ArcFurnaceRecipeHandler extends NEIUniversalHandler
	{
		public ArcFurnaceRecipeHandler()
		{
			super(I18nUtil.resolveKey("container.arcFurnace"), ModBlocks.machine_arc_furnace_off, MachineRecipes.getArcFurnaceRecipes());
		}

		@Override
		public String getKey()
		{
			return "ntmArcFurnace";
		}
	}

	public static final class ResearchReactorRecipeHandler extends NEIUniversalHandler
	{
		public ResearchReactorRecipeHandler()
		{
			super(I18nUtil.resolveKey("container.reactorResearch"), ModBlocks.reactor_research, TileEntityReactorResearch.getFuelRecipes());
		}

		@Override
		public String getKey()
		{
			return "ntmResearchReactor";
		}
	}
	
	public static final class DecayRecipeHandler extends NEIUniversalHandler
	{
		public DecayRecipeHandler()
		{
			super("Decay", new ItemStack[] {new ItemStack(ModBlocks.machine_storage_drum), new ItemStack(ModBlocks.machine_radgen)}, TileEntityStorageDrum.getRecipesForNEI());
		}

		@Override
		public String getKey()
		{
			return "ntmDecay";
		}
	}
	
	@Override
	public void loadConfig() {
		API.registerRecipeHandler(new AlloyFurnaceRecipeHandler());
		API.registerUsageHandler(new AlloyFurnaceRecipeHandler());
		API.registerRecipeHandler(new CentrifugeRecipeHandler());
		API.registerUsageHandler(new CentrifugeRecipeHandler());
		API.registerRecipeHandler(new GasCentrifugeRecipeHandler());
		API.registerUsageHandler(new GasCentrifugeRecipeHandler());
		API.registerRecipeHandler(new BreederRecipeHandler());
		API.registerUsageHandler(new BreederRecipeHandler());
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
		API.registerRecipeHandler(new SmithingRecipeHandler());
		API.registerUsageHandler(new SmithingRecipeHandler());
		API.registerRecipeHandler(new AnvilRecipeHandler());
		API.registerUsageHandler(new AnvilRecipeHandler());
		API.registerRecipeHandler(new FuelPoolHandler());
		API.registerUsageHandler(new FuelPoolHandler());
		API.registerRecipeHandler(new RadiolysisRecipeHandler());
		API.registerUsageHandler(new RadiolysisRecipeHandler());
		
		//universal boyes
		API.registerRecipeHandler(new ZirnoxRecipeHandler());
		API.registerUsageHandler(new ZirnoxRecipeHandler());
		if(VersatileConfig.rtgDecay()) {
			API.registerRecipeHandler(new RTGRecipeHandler());
			API.registerUsageHandler(new RTGRecipeHandler());
		}
		API.registerRecipeHandler(new LiquefactionHandler());
		API.registerUsageHandler(new LiquefactionHandler());
		API.registerRecipeHandler(new SolidificationHandler());
		API.registerUsageHandler(new SolidificationHandler());
		API.registerRecipeHandler(new CrackingHandler());
		API.registerUsageHandler(new CrackingHandler());
		API.registerRecipeHandler(new FractioningHandler());
		API.registerUsageHandler(new FractioningHandler());

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
		API.hideItem(new ItemStack(ModBlocks.dummy_block_chemplant));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_drill));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_flare));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_fluidtank));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_pumpjack));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_refinery));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_turbofan));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_ams_base));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_ams_emitter));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_ams_limiter));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_radgen));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_vault));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_blast));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_uf6));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_puf6));
		API.hideItem(new ItemStack(ModBlocks.dummy_port_assembler));
		API.hideItem(new ItemStack(ModBlocks.dummy_port_chemplant));
		API.hideItem(new ItemStack(ModBlocks.dummy_port_drill));
		API.hideItem(new ItemStack(ModBlocks.dummy_port_flare));
		API.hideItem(new ItemStack(ModBlocks.dummy_port_fluidtank));
		API.hideItem(new ItemStack(ModBlocks.dummy_port_pumpjack));
		API.hideItem(new ItemStack(ModBlocks.dummy_port_refinery));
		API.hideItem(new ItemStack(ModBlocks.dummy_port_turbofan));
		API.hideItem(new ItemStack(ModBlocks.dummy_port_ams_base));
		API.hideItem(new ItemStack(ModBlocks.dummy_port_ams_emitter));
		API.hideItem(new ItemStack(ModBlocks.dummy_port_ams_limiter));
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

	@Override
	public String getName() {
		return "Nuclear Tech NEI Plugin";
	}

	@Override
	public String getVersion() {
		return RefStrings.VERSION;
	}

}

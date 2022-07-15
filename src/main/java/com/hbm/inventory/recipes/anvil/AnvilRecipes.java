package com.hbm.inventory.recipes.anvil;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.hazard.transformer.HazardTransformerRadiationNBT;
import com.hbm.inventory.OreDictManager;
import static com.hbm.inventory.OreDictManager.*;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.recipes.AssemblerRecipes;
import com.hbm.items.ItemAmmoEnums.*;
import com.hbm.items.ItemEnums.Orichalcum;
import com.hbm.items.ModItems;

import api.hbm.energy.IBatteryItem;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;


public class AnvilRecipes {

	private static final List<AnvilSmithingRecipe> smithingRecipes = new ArrayList<AnvilSmithingRecipe>();
	private static final List<AnvilConstructionRecipe> constructionRecipes = new ArrayList<AnvilConstructionRecipe>();
	
	public static void register() {
		registerSmithing();
		registerConstruction();
	}
	
	/*
	 *      //////  //      //  //  //////  //  //  //  //    //  //////
	 *     //      ////  ////  //    //    //  //  //  ////  //  //
	 *    //////  //  //  //  //    //    //////  //  //  ////  //  //
	 *       //  //      //  //    //    //  //  //  //    //  //  //
	 *  //////  //      //  //    //    //  //  //  //    //  //////
	 */
	public static void registerSmithing() {
		
		final Block[] anvils = new Block[]{ModBlocks.anvil_iron, ModBlocks.anvil_lead};
		
		for(Block anvil : anvils) {
			smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.anvil_bismuth, 1), new ComparableStack(anvil), new ComparableStack(ModItems.ingot_bismuth, 10)));
			smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.anvil_dnt, 1), new ComparableStack(anvil), new OreDictStack(DNT.ingot(), 10)));
			smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.anvil_ferrouranium, 1), new ComparableStack(anvil), new OreDictStack(FERROURANIUM.ingot(), 10)));
			smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.anvil_meteorite, 1), new ComparableStack(anvil), new ComparableStack(ModItems.ingot_meteorite, 10)));
			smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.anvil_schrabidate, 1), new ComparableStack(anvil), new OreDictStack(SBD.ingot(), 10)));
			smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.anvil_starmetal, 1), new ComparableStack(anvil), new OreDictStack(STAR.ingot(), 10)));
			smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.anvil_steel, 1), new ComparableStack(anvil), new OreDictStack(STEEL.ingot(), 10)));
			smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.anvil_osmiridium, 1), new ComparableStack(anvil), new OreDictStack(OSMIRIDIUM.ingot(), 10)));
		}
		
		for(int i = 0; i < 9; i++)
			smithingRecipes.add(new AnvilSmithingHotRecipe(3, new ItemStack(ModItems.ingot_steel_dusted, 1, i + 1),
					new ComparableStack(ModItems.ingot_steel_dusted, 1, i), new ComparableStack(ModItems.ingot_steel_dusted, 1, i)));
		for (byte i = 0; i < 5; i++)
			smithingRecipes.add(new AnvilSmithingHotRecipe(7, new ItemStack(ModItems.ingot_dineutronium_forged, 1, i + 1),
					new ComparableStack(ModItems.ingot_dineutronium_forged, 1, i), new ComparableStack(ModItems.ingot_dineutronium_forged, 1, i)));
		smithingRecipes.add(new AnvilSmithingHotRecipe(7, ModItems.orichalcum.stackFromEnum(Orichalcum.INGOT),
				new ComparableStack(ModItems.ingot_dineutronium_forged, 1, 5), new ComparableStack(ModItems.ingot_dineutronium_forged, 1, 5)));
		
		smithingRecipes.add(new AnvilSmithingHotRecipe(3, new ItemStack(ModItems.ingot_chainsteel, 1),
				new ComparableStack(ModItems.ingot_steel_dusted, 1, 9), new ComparableStack(ModItems.ingot_steel_dusted, 1, 9)));
		
		smithingRecipes.add(new AnvilSmithingHotRecipe(3, new ItemStack(ModItems.ingot_meteorite_forged, 1), new ComparableStack(ModItems.ingot_meteorite), new ComparableStack(ModItems.ingot_meteorite)));
		smithingRecipes.add(new AnvilSmithingHotRecipe(3, new ItemStack(ModItems.blade_meteorite, 1), new ComparableStack(ModItems.ingot_meteorite_forged), new ComparableStack(ModItems.ingot_meteorite_forged)));
		smithingRecipes.add(new AnvilSmithingHotRecipe(3, new ItemStack(ModItems.meteorite_sword_reforged, 1), new ComparableStack(ModItems.meteorite_sword_seared), new ComparableStack(ModItems.ingot_meteorite_forged)));
		smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModItems.gun_ar15, 1), new ComparableStack(ModItems.gun_thompson), new ComparableStack(ModItems.pipe_lead)));
//		smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModItems.gun_benelli_mod, 1), new ComparableStack(ModItems.gun_benelli), new ComparableStack(ModItems.hull_big_aluminium)));
		smithingRecipes.add(new AnvilSmithingRecipe(1916169, new ItemStack(ModItems.wings_murk, 1), new ComparableStack(ModItems.wings_limp), new ComparableStack(ModItems.particle_tachyon)));

		smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModItems.bolt_dura_steel, 4), new ComparableStack(ModItems.ingot_dura_steel), new ComparableStack(ModItems.ingot_dura_steel)));
		smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModItems.bolt_tungsten, 4), new ComparableStack(ModItems.ingot_tungsten), new ComparableStack(ModItems.ingot_tungsten)));
		smithingRecipes.add(new AnvilSmithingRecipe(3, new ItemStack(ModItems.bolt_staballoy, 4), new ComparableStack(ModItems.ingot_staballoy), new ComparableStack(ModItems.ingot_staballoy)));
		
//		smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.concrete), new ComparableStack(ModBlocks.concrete_slab), new ComparableStack(ModBlocks.concrete_slab)));
//		smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.concrete_smooth), new ComparableStack(ModBlocks.concrete_smooth_slab), new ComparableStack(ModBlocks.concrete_smooth_slab)));
//		smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.brick_concrete), new ComparableStack(ModBlocks.brick_concrete_slab), new ComparableStack(ModBlocks.brick_concrete_slab)));
		
		smithingRecipes.add(new AnvilSmithingCyanideRecipe());
		smithingRecipes.add(new AnvilSmithingRenameRecipe());
//		smithingRecipes.add(new AnvilSmithingPoloniumRecipe());
	}
	
	/*
	 *      //////  //////  //    //  //////  //////  ////    //  //  //////  //////  //  //////  //    //
	 *     //      //  //  ////  //  //        //    //  //  //  //  //        //    //  //  //  ////  //
	 *    //      //  //  //  ////  //////    //    ////    //  //  //        //    //  //  //  //  ////
	 *   //      //  //  //    //      //    //    //  //  //  //  //        //    //  //  //  //    //
	 *  //////  //////  //    //  //////    //    //  //  //////  //////    //    //  //////  //    //
	 */
	public static void registerConstruction() {
		registerConstructionRecipes();
		
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(FE.ingot()), new AnvilOutput(new ItemStack(ModItems.plate_iron))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(AU.ingot()), new AnvilOutput(new ItemStack(ModItems.plate_gold))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(TI.ingot()), new AnvilOutput(new ItemStack(ModItems.plate_titanium))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(AL.ingot()), new AnvilOutput(new ItemStack(ModItems.plate_aluminium))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(STEEL.ingot()), new AnvilOutput(new ItemStack(ModItems.plate_steel))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(PB.ingot()), new AnvilOutput(new ItemStack(ModItems.plate_lead))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(CU.ingot()), new AnvilOutput(new ItemStack(ModItems.plate_copper))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(ALLOY.ingot()), new AnvilOutput(new ItemStack(ModItems.plate_advanced_alloy))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(SA326.ingot()), new AnvilOutput(new ItemStack(ModItems.plate_schrabidium))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(CMB.ingot()), new AnvilOutput(new ItemStack(ModItems.plate_combine_steel))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(BIGMT.ingot()), new AnvilOutput(new ItemStack(ModItems.plate_saturnite))).setTier(3));

		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(AL.ingot()), new AnvilOutput(new ItemStack(ModItems.wire_aluminium, 8))).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(CU.ingot()), new AnvilOutput(new ItemStack(ModItems.wire_copper, 8))).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(W.ingot()), new AnvilOutput(new ItemStack(ModItems.wire_tungsten, 8))).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(MINGRADE.ingot()), new AnvilOutput(new ItemStack(ModItems.wire_red_copper, 8))).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(ALLOY.ingot()), new AnvilOutput(new ItemStack(ModItems.wire_advanced_alloy, 8))).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(AU.ingot()), new AnvilOutput(new ItemStack(ModItems.wire_gold, 8))).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(SA326.ingot()), new AnvilOutput(new ItemStack(ModItems.wire_schrabidium, 8))).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(MAGTUNG.ingot()), new AnvilOutput(new ItemStack(ModItems.wire_magnetized_tungsten, 8))).setTier(4));

		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(COAL.dust()), new AnvilOutput(new ItemStack(Items.coal))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(NETHERQUARTZ.dust()), new AnvilOutput(new ItemStack(Items.quartz))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(LAPIS.dust()), new AnvilOutput(new ItemStack(Items.dye, 1, 4))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(DIAMOND.dust()), new AnvilOutput(new ItemStack(Items.diamond))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(EMERALD.dust()), new AnvilOutput(new ItemStack(Items.emerald))).setTier(3));

		constructionRecipes.add(new AnvilConstructionRecipe(new ComparableStack(ModItems.orichalcum, 1, Orichalcum.INGOT_FINISHED.ordinal()), new AnvilOutput(ModItems.orichalcum.stackFromEnum(Orichalcum.PLATE_ARMOR))).setTier(7));
		
		constructionRecipes.add(new AnvilConstructionRecipe(new AStack[]
				{
						new ComparableStack(ModItems.gun_benelli),
						new ComparableStack(ModItems.hull_big_aluminium),
						new OreDictStack(AL.plate(), 2),
						new OreDictStack(ANY_PLASTIC.ingot()),
						new ComparableStack(ModItems.mechanism_revolver_2)
				}, new AnvilOutput(new ItemStack(ModItems.gun_benelli_mod))).setTier(3));
		
//		constructionRecipes.add(new AnvilConstructionRecipe(new AStack[]
//				{
//						new OreDictStack(SA326.plate(), 4),
//						new ComparableStack(ModItems.wire_magnetized_tungsten)
//				}, new AnvilOutput(new ItemStack(ModItems.battery_fast_2_raw, 4))).setTier(4));
		
		final Item[] miningWarheads = {ModItems.warhead_nuclear, Item.getItemFromBlock(ModBlocks.det_nuke), ModItems.mp_warhead_10_nuclear_large};

		for (Item warhead : miningWarheads)
		{
			constructionRecipes.add(new AnvilConstructionRecipe(new AStack[]
					{
							new ComparableStack(ModItems.drill_titanium),
							new ComparableStack(ModItems.circuit_targeting_tier4),
							new ComparableStack(ModItems.hull_big_titanium, 2),
							new ComparableStack(ModItems.hull_big_aluminium),
							new ComparableStack(ModItems.motor_desh, 4),
							new ComparableStack(ModItems.wire_gold, 8),
							new OreDictStack(ALLOY.plate(), 16),
							new ComparableStack(warhead)
					}, new AnvilOutput(new ItemStack(ModItems.drill_nuclear))).setTier(5));
		}
		
		registerConstructionAmmo();
		registerConstructionUpgrades();
		registerConstructionRecycling();
	}
	
	public static void registerConstructionRecipes() {

		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(AL.ingot(), 1), new AnvilOutput(new ItemStack(ModBlocks.deco_aluminium))).setTier(1).setOverlay(OverlayType.CONSTRUCTION));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(BE.ingot(), 1), new AnvilOutput(new ItemStack(ModBlocks.deco_beryllium))).setTier(1).setOverlay(OverlayType.CONSTRUCTION));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(PB.ingot(), 1), new AnvilOutput(new ItemStack(ModBlocks.deco_lead))).setTier(1).setOverlay(OverlayType.CONSTRUCTION));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(MINGRADE.ingot(), 1), new AnvilOutput(new ItemStack(ModBlocks.deco_red_copper))).setTier(1).setOverlay(OverlayType.CONSTRUCTION));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(STEEL.ingot(), 1), new AnvilOutput(new ItemStack(ModBlocks.deco_steel))).setTier(1).setOverlay(OverlayType.CONSTRUCTION));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(TI.ingot(), 1), new AnvilOutput(new ItemStack(ModBlocks.deco_titanium))).setTier(1).setOverlay(OverlayType.CONSTRUCTION));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(W.ingot(), 1), new AnvilOutput(new ItemStack(ModBlocks.deco_tungsten))).setTier(1).setOverlay(OverlayType.CONSTRUCTION));

//		constructionRecipes.add(new AnvilConstructionRecipe(new ComparableStack(ModBlocks.concrete), new AnvilOutput(new ItemStack(ModBlocks.concrete_slab, 2))).setTier(1));
//		constructionRecipes.add(new AnvilConstructionRecipe(new ComparableStack(ModBlocks.concrete_smooth), new AnvilOutput(new ItemStack(ModBlocks.concrete_smooth_slab, 2))).setTier(1));
//		constructionRecipes.add(new AnvilConstructionRecipe(new ComparableStack(ModBlocks.brick_concrete), new AnvilOutput(new ItemStack(ModBlocks.brick_concrete_slab, 2))).setTier(1));

		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {new OreDictStack(DNT.ingot(), 4), new ComparableStack(ModBlocks.depth_brick)},
				new AnvilOutput(new ItemStack(ModBlocks.depth_dnt))).setTier(1916169));

		constructionRecipes.add(new AnvilConstructionRecipe(
				new OreDictStack(CU.plate(), 4),
				new AnvilOutput(new ItemStack(ModItems.board_copper))).setTier(1));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new OreDictStack(STEEL.plate(), 2),
				new AnvilOutput(new ItemStack(ModItems.hull_small_steel))).setTier(1));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new OreDictStack(AL.plate(), 2),
				new AnvilOutput(new ItemStack(ModItems.hull_small_aluminium))).setTier(1));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new ComparableStack(ModItems.coil_copper, 2),
				new AnvilOutput(new ItemStack(ModItems.coil_copper_torus))).setTier(1).setOverlay(OverlayType.CONSTRUCTION));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new ComparableStack(ModItems.coil_advanced_alloy, 2),
				new AnvilOutput(new ItemStack(ModItems.coil_advanced_torus))).setTier(1).setOverlay(OverlayType.CONSTRUCTION));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new ComparableStack(ModItems.coil_gold, 2),
				new AnvilOutput(new ItemStack(ModItems.coil_gold_torus))).setTier(1).setOverlay(OverlayType.CONSTRUCTION));

		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {new OreDictStack(FE.plate(), 2), new ComparableStack(ModItems.coil_copper), new ComparableStack(ModItems.coil_copper_torus)},
				new AnvilOutput(new ItemStack(ModItems.motor, 2))).setTier(1));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {new ComparableStack(ModItems.motor), new OreDictStack(ANY_PLASTIC.ingot(), 2), new OreDictStack(DESH.ingot(), 2), new ComparableStack(ModItems.coil_gold_torus)},
				new AnvilOutput(new ItemStack(ModItems.motor_desh, 1))).setTier(3));
		
		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {
						new ComparableStack(Blocks.stonebrick, 4),
						new OreDictStack(FE.ingot(), 2),
						new OreDictStack(W.ingot(), 4),
						new ComparableStack(ModItems.board_copper, 2)
				},
				new AnvilOutput(new ItemStack(ModBlocks.machine_difurnace_off))).setTier(1));
		
		int ukModifier = 1;
		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {
						new OreDictStack(KEY_CLEARGLASS, 4 * ukModifier),
						new OreDictStack(STEEL.ingot(), 8 * ukModifier),
						new OreDictStack(CU.ingot(), 8 * ukModifier),
						new ComparableStack(ModItems.motor, 2 * ukModifier),
						new ComparableStack(ModItems.circuit_aluminium, 1 * ukModifier)
				}, new AnvilOutput(new ItemStack(ModBlocks.machine_assembler))).setTier(2));

		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {
						new OreDictStack(STEEL.ingot(), 6),
						new OreDictStack(FE.plate(), 8),
						new OreDictStack(ANY_PLASTIC.ingot(), 4),
						new ComparableStack(ModItems.generator_steel, 2),
						new ComparableStack(ModItems.turbine_titanium, 1),
						new ComparableStack(ModItems.thermo_element, 3),
						new ComparableStack(ModItems.crt_display, 1)
				}, new AnvilOutput(new ItemStack(ModBlocks.machine_industrial_generator))).setTier(2));

		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {
						new ComparableStack(ModBlocks.brick_concrete, 64),
						new ComparableStack(Blocks.iron_bars, 128),
						new ComparableStack(ModBlocks.machine_condenser, 5),
				}, new AnvilOutput(new ItemStack(ModBlocks.machine_tower_small))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {
						new ComparableStack(ModBlocks.concrete_smooth, 128),
						new ComparableStack(ModBlocks.steel_scaffold, 32),
						new ComparableStack(ModBlocks.machine_condenser, 25),
						new ComparableStack(ModItems.pipes_steel, 2)
				}, new AnvilOutput(new ItemStack(ModBlocks.machine_tower_large))).setTier(4));
		
		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {
						new ComparableStack(Items.bone, 16),
						new ComparableStack(Items.leather, 4),
						new ComparableStack(Items.feather, 24)
				}, new AnvilOutput(new ItemStack(ModItems.wings_limp))).setTier(2));
		
		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {
						new ComparableStack(ModItems.deuterium_filter, 2),
						new ComparableStack(ModItems.hull_big_steel, 5),
						new ComparableStack(ModBlocks.concrete_smooth, 8),
						new ComparableStack(ModBlocks.concrete_asbestos, 4),
						new ComparableStack(ModBlocks.steel_scaffold, 16),
						new ComparableStack(ModBlocks.deco_pipe_quad, 12),
						new OreDictStack(S.dust(), 32),
				},
				new AnvilOutput(new ItemStack(ModBlocks.machine_deuterium_tower))).setTier(4));
		
		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {
						new OreDictStack(ANY_CONCRETE.any(), 2),
						new ComparableStack(ModBlocks.steel_scaffold, 8),
						new ComparableStack(ModItems.plate_polymer, 8),
						new ComparableStack(ModItems.coil_copper, 4)
				},
				new AnvilOutput(new ItemStack(ModBlocks.red_pylon_large))).setTier(2));
		
		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {
						new OreDictStack(ANY_CONCRETE.any(), 6),
						new OreDictStack(STEEL.ingot(), 4),
						new ComparableStack(ModBlocks.steel_scaffold, 2),
						new ComparableStack(ModItems.plate_polymer, 8),
						new ComparableStack(ModItems.coil_copper, 2),
						new ComparableStack(ModItems.coil_copper_torus, 2)
				},
				new AnvilOutput(new ItemStack(ModBlocks.substation))).setTier(2));
		
		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {
						new ComparableStack(ModItems.tank_steel, 1),
						new OreDictStack(PB.plate(), 2),
						new ComparableStack(ModItems.nuclear_waste, 10)
				}, new AnvilOutput(new ItemStack(ModBlocks.yellow_barrel))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {
						new ComparableStack(ModItems.tank_steel, 1),
						new OreDictStack(PB.plate(), 2),
						new ComparableStack(ModItems.nuclear_waste_vitrified, 10)
				}, new AnvilOutput(new ItemStack(ModBlocks.vitrified_barrel))).setTier(3));
		
		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {
						new ComparableStack(ModItems.man_core, 1),
						new OreDictStack(BE.ingot(), 4),
						new ComparableStack(ModItems.screwdriver, 1)
				}, new AnvilOutput(new ItemStack(ModItems.demon_core_open))).setTier(3));
		
		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {new OreDictStack(DESH.ingot(), 4), new OreDictStack(ANY_PLASTIC.dust(), 2), new OreDictStack(DURA.ingot(), 1)},
				new AnvilOutput(new ItemStack(ModItems.plate_desh, 4))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {new ComparableStack(ModItems.nugget_bismuth, 2), new OreDictStack(U238.billet(), 2), new OreDictStack(NB.dust(), 1)},
				new AnvilOutput(new ItemStack(ModItems.plate_bismuth, 1))).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {new OreDictStack(EUPH.ingot(), 4), new OreDictStack(AT.dust(), 2), new OreDictStack(VOLCANIC.gem(), 1)},
				new AnvilOutput(new ItemStack(ModItems.plate_euphemium))).setTier(6));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {new OreDictStack(DNT.ingot(), 4), new ComparableStack(ModItems.powder_spark_mix, 2), new OreDictStack(DESH.ingot(), 1)},
				new AnvilOutput(new ItemStack(ModItems.plate_dineutronium, 4))).setTier(7));

		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {new OreDictStack(TI.plate(), 2), new OreDictStack(STEEL.ingot(), 1), new ComparableStack(ModItems.bolt_tungsten, 2)},
				new AnvilOutput(new ItemStack(ModItems.plate_armor_titanium))).setTier(2));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {new OreDictStack(FE.plate(), 4), new OreDictStack(BIGMT.plate(), 2), new ComparableStack(ModItems.plate_armor_titanium, 1)},
				new AnvilOutput(new ItemStack(ModItems.plate_armor_ajr))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {new OreDictStack(ALLOY.plate(), 4), new ComparableStack(ModItems.plate_armor_titanium, 1), new ComparableStack(ModItems.wire_tungsten, 6)},
				new AnvilOutput(new ItemStack(ModItems.plate_armor_hev))).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {new OreDictStack(OreDictManager.getReflector(), 4), new OreDictStack(STAR.ingot(), 1), new ComparableStack(ModItems.wire_magnetized_tungsten, 6)},
				new AnvilOutput(new ItemStack(ModItems.plate_armor_lunar))).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {new ComparableStack(ModItems.ingot_meteorite_forged, 4), new OreDictStack(DESH.ingot(), 1), new ComparableStack(ModItems.billet_yharonite, 1)},
				new AnvilOutput(new ItemStack(ModItems.plate_armor_fau))).setTier(6));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {new ComparableStack(ModItems.plate_dineutronium, 4), new ComparableStack(ModItems.particle_sparkticle, 1), new ComparableStack(ModItems.plate_armor_fau, 6)},
				new AnvilOutput(new ItemStack(ModItems.plate_armor_dnt))).setTier(7));

		constructionRecipes.add(new AnvilConstructionRecipe(new AStack[]
				{
					new ComparableStack(ModItems.mechanism_rifle_2),
					new ComparableStack(ModItems.hull_small_steel, 2),
					new OreDictStack(STEEL.ingot(), 3),
					new OreDictStack(KEY_PLANKS, 2)
				}, new AnvilOutput(new ItemStack(ModItems.gun_thompson_redux))).setTier(2));
		constructionRecipes.add(new AnvilConstructionRecipe(new AStack[]
				{
					new ComparableStack(ModItems.mechanism_rifle_2),
					new ComparableStack(ModItems.hull_small_steel),
					new OreDictStack(STEEL.ingot()),
					new OreDictStack(AL.ingot()),
					new OreDictStack(ANY_PLASTIC.ingot())
				}, new AnvilOutput(new ItemStack(ModItems.gun_uac_pistol))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(new AStack[]
				{
					new ComparableStack(ModItems.mechanism_rifle_2, 2),
					new ComparableStack(ModItems.hull_small_steel, 3),
					new OreDictStack(STEEL.plate(), 4),
					new OreDictStack(STEEL.ingot()),
					new OreDictStack(DURA.ingot(), 2)
				}, new AnvilOutput(new ItemStack(ModItems.gun_m2))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(new AStack[]
				{
					new ComparableStack(ModItems.mechanism_rifle_2),
					new ComparableStack(ModItems.mechanism_special),
					new OreDictStack(BIGMT.plate(), 20),
					new OreDictStack(BIGMT.ingot(), 16),
					new ComparableStack(ModItems.plate_combine_steel, 12),
					new ComparableStack(ModItems.ingot_combine_steel, 4),
					new ComparableStack(ModItems.plate_desh, 8)
				}, new AnvilOutput(new ItemStack(ModItems.gun_lunatic_marksman))).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(new AStack[]
				{
					new ComparableStack(ModItems.mechanism_rifle_2),
					new ComparableStack(ModItems.hull_small_steel, 2),
					new OreDictStack(ALLOY.plate(), 6),
					new ComparableStack(ModItems.plate_paa, 4),
					new OreDictStack(ANY_PLASTIC.ingot(), 2)
				}, new AnvilOutput(new ItemStack(ModItems.gun_llr))).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(new AStack[]
				{
					new ComparableStack(ModItems.mechanism_rifle_2),
					new ComparableStack(ModItems.hull_small_steel, 3),
					new OreDictStack(ALLOY.plate(), 12),
					new ComparableStack(ModItems.plate_paa, 8),
					new OreDictStack(ANY_PLASTIC.ingot(), 4)
				}, new AnvilOutput(new ItemStack(ModItems.gun_mlr))).setTier(4));
		
		constructionRecipes.add(new AnvilConstructionRecipe(new AStack[]
				{
					new OreDictStack(AU.plate(), 6),
					new OreDictStack(LAPIS.gem(), 4),
					new OreDictStack(EMERALD.gem(), 4),
					new OreDictStack(DIAMOND.gem(), 4),
					new ComparableStack(ModItems.gem_alexandrite)
				}, new AnvilOutput(new ItemStack(ModItems.pagoda))).setTier(5));
		
//		constructionRecipes.add(new AnvilConstructionRecipe(new AStack[]
//				{
//					new OreDictStack(KEY_ANYPANE),
//					new OreDictStack(NETHERQUARTZ.gem()),
//					new ComparableStack(ModItems.magnetron),
//					new ComparableStack(ModItems.circuit_aluminium)
//				}, new AnvilOutput(new ItemStack(ModItems.watch_digital))).setTier(2));
		
//		constructionRecipes.add(new AnvilConstructionRecipe(new AStack[]
//				{
//					new ComparableStack(ModItems.particle_graviton, 6),
//					new ComparableStack(ModBlocks.block_u238, 8),
//					new ComparableStack(ModItems.singularity_micro),
//					new ComparableStack(ModItems.coil_magnetized_tungsten, 8),
//					new ComparableStack(ModItems.plate_combine_steel, 20),
//					new ComparableStack(ModItems.circuit_targeting_tier5)
//				}, new AnvilOutput(CustomWarheadWrapper.gravimetricBase.getStack())).setTier(6));
//		constructionRecipes.add(new AnvilConstructionRecipe(new AStack[]
//				{
//					new ComparableStack(ModItems.tritium_deuterium_cake),
//					new OreDictStack("plateTitanium", 12),
//					new ComparableStack(ModItems.hull_big_titanium),
//					new ComparableStack(ModItems.battery_gun_basic),
//					new ComparableStack(ModItems.powder_power, 8),
//					new OreDictStack("ingotNeodymium", 6),
//					new ComparableStack(ModItems.circuit_targeting_tier5)
//				}, new AnvilOutput(CustomWarheadWrapper.pureFusionBase.getStack())).setTier(5));
//		constructionRecipes.add(new AnvilConstructionRecipe(new AStack[]
//				{
//					new ComparableStack(ModItems.gas_sarin, 24),
//					new OreDictStack("plateAluminum", 8),
//					new ComparableStack(ModItems.hull_big_aluminium),
//					new ComparableStack(ModBlocks.det_cord, 2),
//					new ComparableStack(ModItems.circuit_targeting_tier4)
//				}, new AnvilOutput(CustomWarheadWrapper.chemicalBase.getStack())).setTier(4));
//		constructionRecipes.add(new AnvilConstructionRecipe(new AStack[]
//				{
//					new ComparableStack(ModItems.neutron_reflector, 36),
//					new ComparableStack(ModBlocks.det_charge, 6),
//					new OreDictStack("ingotUranium235", 6),
//					new OreDictStack("ingotPlutonium239"),
//					new ComparableStack(ModItems.tritium_deuterium_cake),
//					new OreDictStack("ingotCobalt", 12),
//					new ComparableStack(ModItems.wire_gold, 16),
//					new ComparableStack(ModItems.circuit_targeting_tier4, 3)
//				}, new AnvilOutput(CustomWarheadWrapper.saltedBase.getStack())).setTier(4));
		
		pullFromAssembler(new ComparableStack(ModItems.plate_mixed, 4), 3);

		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {new OreDictStack(AS.ingot()), new OreDictStack(REDSTONE.dust(), 4), new OreDictStack(ASBESTOS.ingot(), 2)},
				new AnvilOutput(new ItemStack(ModItems.circuit_arsenic_raw))).setTier(5));
		
		constructionRecipes.add(new AnvilConstructionRecipe(new ComparableStack(ModItems.ingot_u233, 1), new AnvilOutput(new ItemStack(ModItems.plate_fuel_u233))).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(new ComparableStack(ModItems.ingot_u235, 1), new AnvilOutput(new ItemStack(ModItems.plate_fuel_u235))).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(new ComparableStack(ModItems.ingot_mox_fuel, 1), new AnvilOutput(new ItemStack(ModItems.plate_fuel_mox))).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(new ComparableStack(ModItems.ingot_pu239, 1), new AnvilOutput(new ItemStack(ModItems.plate_fuel_pu239))).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(new ComparableStack(ModItems.ingot_schrabidium, 1), new AnvilOutput(new ItemStack(ModItems.plate_fuel_sa326))).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(new ComparableStack(ModItems.billet_ra226be, 1), new AnvilOutput(new ItemStack(ModItems.plate_fuel_ra226be))).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(new ComparableStack(ModItems.billet_pu238be, 1), new AnvilOutput(new ItemStack(ModItems.plate_fuel_pu238be))).setTier(4));
	
		registerBatteryRecipes();
	}
	
	public static void registerBatteryRecipes()
	{
		// Redstone: redstone electrodes, Carbon electrolyte, Iron casing, Aluminium connectors
		constructionRecipes.add(new AnvilConstructionRecipe(new AStack[]
				{
					new OreDictStack(REDSTONE.dust()),
					new OreDictStack(COAL.gem()),
					new OreDictStack(FE.plate()),
					new OreDictStack(AL.plate())
				}, new AnvilOutput[]
				{
					new AnvilOutput(IBatteryItem.emptyBattery(ModItems.battery_generic)),
					new AnvilOutput(IBatteryItem.emptyBattery(ModItems.battery_generic))
				}).setTier(1));
		constructionRecipes.add(new AnvilConstructionRecipe(new AStack[]
				{
					new ComparableStack(ModItems.battery_generic, 3),
					new OreDictStack(FE.plate()),
					new ComparableStack(ModItems.wire_aluminium, 2)
				}, new AnvilOutput(IBatteryItem.emptyBattery(ModItems.battery_red_cell))).setTier(1).setBatteryType());
		constructionRecipes.add(new AnvilConstructionRecipe(new AStack[]
				{
					new ComparableStack(ModItems.battery_red_cell, 6),
					new OreDictStack(FE.plate(), 2),
					new ComparableStack(ModItems.wire_aluminium, 2)
				}, new AnvilOutput(IBatteryItem.emptyBattery(ModItems.battery_red_cell_6))).setTier(1).setBatteryType());
		constructionRecipes.add(new AnvilConstructionRecipe(new AStack[]
				{
					new ComparableStack(ModItems.battery_red_cell_6, 4),
					new OreDictStack(FE.plate(), 4),
					new ComparableStack(ModItems.wire_aluminium, 4)
				}, new AnvilOutput(IBatteryItem.emptyBattery(ModItems.battery_red_cell_24))).setTier(1).setBatteryType());
		// Lead-acid: Lead electrodes, Sulfur electrolye, steel casing, Copper connectors
		constructionRecipes.add(new AnvilConstructionRecipe(new AStack[]
				{
					new OreDictStack(PB.plate()),
					new OreDictStack(S.dust()),
					new OreDictStack(NI.plate()),
					new OreDictStack(CU.plate()),
				}, new AnvilOutput[]
				{
					new AnvilOutput(IBatteryItem.emptyBattery(ModItems.battery_advanced)),
					new AnvilOutput(IBatteryItem.emptyBattery(ModItems.battery_advanced))
				}).setTier(2));
		constructionRecipes.add(new AnvilConstructionRecipe(new AStack[]
				{
					new ComparableStack(ModItems.battery_advanced, 3),
					new OreDictStack(STEEL.plate()),
					new ComparableStack(ModItems.wire_copper, 2)
				}, new AnvilOutput(IBatteryItem.emptyBattery(ModItems.battery_advanced_cell))).setTier(2).setBatteryType());
		constructionRecipes.add(new AnvilConstructionRecipe(new AStack[]
				{
					new ComparableStack(ModItems.battery_advanced_cell, 4),
					new OreDictStack(STEEL.plate(), 2),
					new ComparableStack(ModItems.wire_copper, 2)
				}, new AnvilOutput(IBatteryItem.emptyBattery(ModItems.battery_advanced_cell_4))).setTier(2).setBatteryType());
		constructionRecipes.add(new AnvilConstructionRecipe(new AStack[]
				{
					new ComparableStack(ModItems.battery_advanced_cell_4, 3),
					new OreDictStack(STEEL.plate(), 4),
					new ComparableStack(ModItems.wire_copper, 4)
				}, new AnvilOutput(IBatteryItem.emptyBattery(ModItems.battery_advanced_cell_12))).setTier(2).setBatteryType());
		// Lithium-ion: Cobalt & graphite electrodes, Lithium electrolyte, Aluminium & plastic casing, red Copper connectors
		constructionRecipes.add(new AnvilConstructionRecipe(new AStack[]
				{
					new OreDictStack(LI.dust()),
					new OreDictStack(CO.ingot()),
					new OreDictStack(GRAPHITE.ingot()),
					new OreDictStack(AL.plate()),
					new ComparableStack(ModItems.plate_polymer),
					new OreDictStack(MINGRADE.ingot())
				}, new AnvilOutput[]
				{
					new AnvilOutput(IBatteryItem.emptyBattery(ModItems.battery_lithium)),
					new AnvilOutput(IBatteryItem.emptyBattery(ModItems.battery_lithium))
				}).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(new AStack[]
				{
					new ComparableStack(ModItems.battery_lithium, 3),
					new OreDictStack(AL.plate()),
					new ComparableStack(ModItems.wire_red_copper, 2)
				}, new AnvilOutput(IBatteryItem.emptyBattery(ModItems.battery_lithium_cell))).setTier(3).setBatteryType());
		constructionRecipes.add(new AnvilConstructionRecipe(new AStack[]
				{
					new ComparableStack(ModItems.battery_lithium_cell, 3),
					new OreDictStack(AL.plate(), 2),
					new ComparableStack(ModItems.wire_red_copper, 2)
				}, new AnvilOutput(IBatteryItem.emptyBattery(ModItems.battery_lithium_cell_3))).setTier(3).setBatteryType());
		constructionRecipes.add(new AnvilConstructionRecipe(new AStack[]
				{
					new ComparableStack(ModItems.battery_lithium_cell_3, 2),
					new OreDictStack(AL.plate(), 4),
					new ComparableStack(ModItems.wire_red_copper, 4)
				}, new AnvilOutput(IBatteryItem.emptyBattery(ModItems.battery_lithium_cell_6))).setTier(3).setBatteryType());
		// Schrabidium: Schrabidium & Neptunium electrodes, desh casing, 4000k superconductor connectors
		constructionRecipes.add(new AnvilConstructionRecipe(new AStack[]
				{
					new OreDictStack(SA326.plate()),
					new OreDictStack(NP237.ingot()),
					new OreDictStack(DESH.ingot()),
					new ComparableStack(ModItems.ingot_magnetized_tungsten)
				}, new AnvilOutput[]
				{
					new AnvilOutput(IBatteryItem.emptyBattery(ModItems.battery_schrabidium)),
					new AnvilOutput(IBatteryItem.emptyBattery(ModItems.battery_schrabidium))
				}).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(new AStack[]
				{
					new ComparableStack(ModItems.battery_schrabidium, 3),
					new OreDictStack(DESH.ingot()),
					new ComparableStack(ModItems.wire_magnetized_tungsten, 2)
				}, new AnvilOutput(IBatteryItem.emptyBattery(ModItems.battery_schrabidium_cell))).setTier(4).setBatteryType());
		constructionRecipes.add(new AnvilConstructionRecipe(new AStack[]
				{
					new ComparableStack(ModItems.battery_schrabidium_cell, 2),
					new OreDictStack(DESH.ingot()),
					new ComparableStack(ModItems.wire_magnetized_tungsten, 2)
				}, new AnvilOutput(IBatteryItem.emptyBattery(ModItems.battery_schrabidium_cell_2))).setTier(4).setBatteryType());
		constructionRecipes.add(new AnvilConstructionRecipe(new AStack[]
				{
					new ComparableStack(ModItems.battery_schrabidium_cell_2, 2),
					new OreDictStack(DESH.ingot(), 2),
					new ComparableStack(ModItems.wire_magnetized_tungsten, 4)
				}, new AnvilOutput(IBatteryItem.emptyBattery(ModItems.battery_schrabidium_cell_4))).setTier(4).setBatteryType());
	}
	
	public static void registerConstructionAmmo() {
		
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(CU.plate()), new AnvilOutput(new ItemStack(ModItems.casing_357))).setTier(1));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(CU.plate()), new AnvilOutput(new ItemStack(ModItems.casing_44))).setTier(1));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(CU.plate()), new AnvilOutput(new ItemStack(ModItems.casing_9))).setTier(1));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(CU.plate()), new AnvilOutput(new ItemStack(ModItems.casing_50))).setTier(1));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(CU.plate()), new AnvilOutput(new ItemStack(ModItems.casing_buckshot))).setTier(1));
		constructionRecipes.add(new AnvilConstructionRecipe(new AStack[] {new OreDictStack(AL.plate()), new ComparableStack(Items.redstone)}, new AnvilOutput(new ItemStack(ModItems.primer_357))).setTier(1));
		constructionRecipes.add(new AnvilConstructionRecipe(new AStack[] {new OreDictStack(AL.plate()), new ComparableStack(Items.redstone)}, new AnvilOutput(new ItemStack(ModItems.primer_44))).setTier(1));
		constructionRecipes.add(new AnvilConstructionRecipe(new AStack[] {new OreDictStack(AL.plate()), new ComparableStack(Items.redstone)}, new AnvilOutput(new ItemStack(ModItems.primer_9))).setTier(1));
		constructionRecipes.add(new AnvilConstructionRecipe(new AStack[] {new OreDictStack(AL.plate()), new ComparableStack(Items.redstone)}, new AnvilOutput(new ItemStack(ModItems.primer_50))).setTier(1));
		constructionRecipes.add(new AnvilConstructionRecipe(new AStack[] {new OreDictStack(AL.plate()), new ComparableStack(Items.redstone)}, new AnvilOutput(new ItemStack(ModItems.primer_buckshot))).setTier(1));
		
		Object[][] recs = new Object[][] {
			{ModItems.ammo_12gauge.stackFromEnum(20, Ammo12Gauge.STOCK),	P_RED.dust(),										ModItems.ammo_12gauge.stackFromEnum(20, Ammo12Gauge.INCENDIARY),	2},
			{ModItems.ammo_12gauge.stackFromEnum(20, Ammo12Gauge.STOCK),	Item.getItemFromBlock(ModBlocks.gravel_obsidian),	ModItems.ammo_12gauge.stackFromEnum(20, Ammo12Gauge.SHRAPNEL),		2},
			{ModItems.ammo_12gauge.stackFromEnum(20, Ammo12Gauge.STOCK),	STABALLOY.ingot(),									ModItems.ammo_12gauge.stackFromEnum(20, Ammo12Gauge.DU),			3},
			{ModItems.ammo_12gauge.stackFromEnum(100, Ammo12Gauge.STOCK),	ModItems.coin_maskman,								ModItems.ammo_12gauge.stackFromEnum(100, Ammo12Gauge.SLEEK),		4},
			
			{ModItems.ammo_20gauge.stackFromEnum(20, Ammo20Gauge.STOCK),	P_RED.dust(),										ModItems.ammo_20gauge.stackFromEnum(20, Ammo20Gauge.INCENDIARY),	2},
			{ModItems.ammo_20gauge.stackFromEnum(20, Ammo20Gauge.STOCK),	Item.getItemFromBlock(ModBlocks.gravel_obsidian),	ModItems.ammo_20gauge.stackFromEnum(20, Ammo20Gauge.SHRAPNEL),		2},
			{ModItems.ammo_20gauge.stackFromEnum(20, Ammo20Gauge.STOCK),	ModItems.powder_poison,								ModItems.ammo_20gauge.stackFromEnum(20, Ammo20Gauge.CAUSTIC),		2},
			{ModItems.ammo_20gauge.stackFromEnum(20, Ammo20Gauge.STOCK),	DIAMOND.dust(),										ModItems.ammo_20gauge.stackFromEnum(20, Ammo20Gauge.SHOCK),			2},
			{ModItems.ammo_20gauge.stackFromEnum(10, Ammo20Gauge.STOCK),	Item.getItemFromBlock(Blocks.soul_sand),			ModItems.ammo_20gauge.stackFromEnum(10, Ammo20Gauge.WITHER),		3},
			{ModItems.ammo_20gauge.stackFromEnum(100, Ammo20Gauge.STOCK),	ModItems.coin_maskman,								ModItems.ammo_20gauge.stackFromEnum(100, Ammo20Gauge.SLEEK),		4},

			{ModItems.ammo_4gauge.stackFromEnum(20, Ammo4Gauge.FLECHETTE),	P_WHITE.ingot(),				ModItems.ammo_4gauge.stackFromEnum(20, Ammo4Gauge.FLECHETTE_PHOSPHORUS),	2},
			{ModItems.ammo_4gauge.stackFromEnum(10, Ammo4Gauge.EXPLOSIVE),	ModItems.egg_balefire_shard,	ModItems.ammo_4gauge.stackFromEnum(10, Ammo4Gauge.BALEFIRE),				4},
			{ModItems.ammo_4gauge.stackFromEnum(4, Ammo4Gauge.EXPLOSIVE),	ModItems.ammo_rocket,			ModItems.ammo_4gauge.stackFromEnum(4, Ammo4Gauge.KAMPF),					2},
			{ModItems.ammo_4gauge.stackFromEnum(10, Ammo4Gauge.KAMPF),		ModItems.pellet_canister,		ModItems.ammo_4gauge.stackFromEnum(10, Ammo4Gauge.CANISTER),				3},
			{ModItems.ammo_4gauge.stackFromEnum(4, Ammo4Gauge.STOCK),		ModItems.pellet_claws,			ModItems.ammo_4gauge.stackFromEnum(4, Ammo4Gauge.CLAW),						5},
			{ModItems.ammo_4gauge.stackFromEnum(4, Ammo4Gauge.STOCK),		ModItems.toothpicks,			ModItems.ammo_4gauge.stackFromEnum(4, Ammo4Gauge.VAMPIRE),					5},
			{ModItems.ammo_4gauge.stackFromEnum(Ammo4Gauge.STOCK),			ModItems.pellet_charged,		ModItems.ammo_4gauge.stackFromEnum(Ammo4Gauge.VOID),						5},
			{ModItems.ammo_4gauge.stackFromEnum(100, Ammo4Gauge.STOCK),		ModItems.coin_maskman,			ModItems.ammo_4gauge.stackFromEnum(100, Ammo4Gauge.SLEEK),					4},

			{ModItems.ammo_44.stackFromEnum(20, Ammo44Magnum.STOCK),		DURA.ingot(),					ModItems.ammo_44.stackFromEnum(20, Ammo44Magnum.AP),			2},
			{ModItems.ammo_44.stackFromEnum(20, Ammo44Magnum.STOCK),		STABALLOY.ingot(),				ModItems.ammo_44.stackFromEnum(20, Ammo44Magnum.DU),			2},
			{ModItems.ammo_44.stackFromEnum(20, Ammo44Magnum.STOCK),		P_WHITE.ingot(),				ModItems.ammo_44.stackFromEnum(20, Ammo44Magnum.PHOSPHORUS),	2},
			{ModItems.ammo_44.stackFromEnum(10, Ammo44Magnum.DU),			STAR.ingot(),					ModItems.ammo_44.stackFromEnum(10, Ammo44Magnum.STAR),			3},
			{ModItems.ammo_44.stackFromEnum(10, Ammo44Magnum.STOCK),		ModItems.pellet_chlorophyte,	ModItems.ammo_44.stackFromEnum(10, Ammo44Magnum.CHLOROPHYTE),	3},

			{ModItems.ammo_5mm.stackFromEnum(20, Ammo5mm.STOCK),	ModItems.ingot_semtex,			ModItems.ammo_5mm.stackFromEnum(20, Ammo5mm.EXPLOSIVE),		2},
			{ModItems.ammo_5mm.stackFromEnum(20, Ammo5mm.STOCK),	STABALLOY.ingot(),				ModItems.ammo_5mm.stackFromEnum(20, Ammo5mm.DU),			2},
			{ModItems.ammo_5mm.stackFromEnum(10, Ammo5mm.STOCK),	STAR.ingot(),					ModItems.ammo_5mm.stackFromEnum(10, Ammo5mm.STAR),			3},
			{ModItems.ammo_5mm.stackFromEnum(10, Ammo5mm.STOCK),	ModItems.pellet_chlorophyte,	ModItems.ammo_5mm.stackFromEnum(10, Ammo5mm.CHLOROPHYTE),	3},

			{ModItems.ammo_9mm.stackFromEnum(20, Ammo9mm.STOCK),	DURA.ingot(),					ModItems.ammo_9mm.stackFromEnum(20, Ammo9mm.AP),			2},
			{ModItems.ammo_9mm.stackFromEnum(20, Ammo9mm.STOCK),	STABALLOY.ingot(),				ModItems.ammo_9mm.stackFromEnum(20, Ammo9mm.DU),			2},
			{ModItems.ammo_9mm.stackFromEnum(10, Ammo9mm.STOCK),	ModItems.pellet_chlorophyte,	ModItems.ammo_9mm.stackFromEnum(10, Ammo9mm.CHLOROPHYTE),	3},
			
			{ModItems.ammo_22lr.stackFromEnum(20, Ammo22LR.STOCK),	DURA.ingot(),					ModItems.ammo_22lr.stackFromEnum(20, Ammo22LR.AP),			2},
			{ModItems.ammo_22lr.stackFromEnum(10, Ammo22LR.STOCK),	ModItems.pellet_chlorophyte,	ModItems.ammo_22lr.stackFromEnum(10, Ammo22LR.CHLOROPHYTE),	3},

			{ModItems.ammo_50bmg.stackFromEnum(20, Ammo50BMG.STOCK),		P_RED.dust(),					ModItems.ammo_50bmg.stackFromEnum(20, Ammo50BMG.INCENDIARY),	2},
			{ModItems.ammo_50bmg.stackFromEnum(20, Ammo50BMG.STOCK),		P_WHITE.ingot(),				ModItems.ammo_50bmg.stackFromEnum(20, Ammo50BMG.PHOSPHORUS),	2},
			{ModItems.ammo_50bmg.stackFromEnum(20, Ammo50BMG.STOCK),		ModItems.ingot_semtex,			ModItems.ammo_50bmg.stackFromEnum(20, Ammo50BMG.EXPLOSIVE),		2},
			{ModItems.ammo_50bmg.stackFromEnum(20, Ammo50BMG.STOCK),		DURA.ingot(),					ModItems.ammo_50bmg.stackFromEnum(20, Ammo50BMG.AP),			2},
			{ModItems.ammo_50bmg.stackFromEnum(20, Ammo50BMG.STOCK),		STABALLOY.ingot(),				ModItems.ammo_50bmg.stackFromEnum(20, Ammo50BMG.DU),			2},
			{ModItems.ammo_50bmg.stackFromEnum(10, Ammo50BMG.DU),			STAR.ingot(),					ModItems.ammo_50bmg.stackFromEnum(10, Ammo50BMG.STAR),			3},
			{ModItems.ammo_50bmg.stackFromEnum(10, Ammo50BMG.STOCK),		ModItems.pellet_chlorophyte,	ModItems.ammo_50bmg.stackFromEnum(10, Ammo50BMG.CHLOROPHYTE),	3},
			{ModItems.ammo_50bmg.stackFromEnum(100, Ammo50BMG.STOCK),		ModItems.coin_maskman,			ModItems.ammo_50bmg.stackFromEnum(100, Ammo50BMG.SLEEK),		4},
			{ModItems.ammo_50bmg.stackFromEnum(20, Ammo50BMG.STOCK),		ModItems.pellet_flechette,		ModItems.ammo_50bmg.stackFromEnum(20, Ammo50BMG.FLECHETTE),		2},
			{ModItems.ammo_50bmg.stackFromEnum(10, Ammo50BMG.FLECHETTE),	ModItems.nugget_am_mix,			ModItems.ammo_50bmg.stackFromEnum(10, Ammo50BMG.FLECHETTE_AM),	3},
			{ModItems.ammo_50bmg.stackFromEnum(20, Ammo50BMG.FLECHETTE),	ModItems.powder_polonium,		ModItems.ammo_50bmg.stackFromEnum(20, Ammo50BMG.FLECHETTE_PO),	3},

			{ModItems.ammo_50ae.stackFromEnum(20, Ammo50AE.STOCK),	DURA.ingot(),					ModItems.ammo_50ae.stackFromEnum(20, Ammo50AE.AP),			2},
			{ModItems.ammo_50ae.stackFromEnum(20, Ammo50AE.STOCK),	STABALLOY.ingot(),				ModItems.ammo_50ae.stackFromEnum(20, Ammo50AE.DU),			2},
			{ModItems.ammo_50ae.stackFromEnum(10, Ammo50AE.DU),		STAR.ingot(),					ModItems.ammo_50ae.stackFromEnum(10, Ammo50AE.STAR),		3},
			{ModItems.ammo_50ae.stackFromEnum(10, Ammo50AE.STOCK),	ModItems.pellet_chlorophyte,	ModItems.ammo_50ae.stackFromEnum(10, Ammo50AE.CHLOROPHYTE),	3},

			{ModItems.ammo_556.stackFromEnum(20, Ammo556mm.STOCK),		P_WHITE.ingot(),				ModItems.ammo_556.stackFromEnum(20, Ammo556mm.PHOSPHORUS),				2},
			{ModItems.ammo_556.stackFromEnum(20, Ammo556mm.STOCK),		DURA.ingot(),					ModItems.ammo_556.stackFromEnum(20, Ammo556mm.AP),						2},
			{ModItems.ammo_556.stackFromEnum(20, Ammo556mm.STOCK),		STABALLOY.ingot(),				ModItems.ammo_556.stackFromEnum(20, Ammo556mm.DU),						2},
			{ModItems.ammo_556.stackFromEnum(10, Ammo556mm.DU),			STAR.ingot(),					ModItems.ammo_556.stackFromEnum(10, Ammo556mm.STAR),					3},
			{ModItems.ammo_556.stackFromEnum(10, Ammo556mm.STOCK),		ModItems.pellet_chlorophyte,	ModItems.ammo_556.stackFromEnum(10, Ammo556mm.CHLOROPHYTE),				3},
			{ModItems.ammo_556.stackFromEnum(100, Ammo556mm.STOCK),		ModItems.coin_maskman,			ModItems.ammo_556.stackFromEnum(100, Ammo556mm.SLEEK),					4},
			{ModItems.ammo_556.stackFromEnum(20, Ammo556mm.STOCK),		Items.redstone,					ModItems.ammo_556.stackFromEnum(20, Ammo556mm.TRACER),					2},
			{ModItems.ammo_556.stackFromEnum(20, Ammo556mm.STOCK),		ModItems.pellet_flechette,		ModItems.ammo_556.stackFromEnum(20, Ammo556mm.FLECHETTE),				2},
			{ModItems.ammo_556.stackFromEnum(20, Ammo556mm.FLECHETTE),	P_RED.dust(),					ModItems.ammo_556.stackFromEnum(20, Ammo556mm.FLECHETTE_INCENDIARY),	2},
			{ModItems.ammo_556.stackFromEnum(20, Ammo556mm.FLECHETTE),	P_WHITE.ingot(),				ModItems.ammo_556.stackFromEnum(20, Ammo556mm.FLECHETTE_PHOSPHORUS),	2},
			{ModItems.ammo_556.stackFromEnum(20, Ammo556mm.FLECHETTE),	STABALLOY.ingot(),				ModItems.ammo_556.stackFromEnum(20, Ammo556mm.FLECHETTE_DU),			2},
			{ModItems.ammo_556.stackFromEnum(100, Ammo556mm.FLECHETTE),	ModItems.coin_maskman,			ModItems.ammo_556.stackFromEnum(100, Ammo556mm.FLECHETTE_SLEEK),		4},
			{ModItems.ammo_556.stackFromEnum(10, Ammo556mm.FLECHETTE),	ModItems.pellet_chlorophyte,	ModItems.ammo_556.stackFromEnum(10, Ammo556mm.FLECHETTE_CHLOROPHYTE),	3},
		};
		
		for(Object[] objs : recs) {
			
			if(objs[1] instanceof Item) {
				constructionRecipes.add(new AnvilConstructionRecipe(new AStack[] { new ComparableStack((ItemStack) objs[0]), new ComparableStack((Item)objs[1], 1) },
						new AnvilOutput((ItemStack) objs[2])).setTier((int)objs[3]));
				
			} else if(objs[1] instanceof String) {
				constructionRecipes.add(new AnvilConstructionRecipe(new AStack[] { new ComparableStack((ItemStack)objs[0]), new OreDictStack((String)objs[1], 1) },
						new AnvilOutput((ItemStack) objs[2])).setTier((int)objs[3]));
			}
		}
	}
	
	public static void registerConstructionUpgrades() {
		pullFromAssembler(new ComparableStack(ModItems.upgrade_template), 2);
		pullFromAssembler(new ComparableStack(ModItems.upgrade_speed_1), 2);
		pullFromAssembler(new ComparableStack(ModItems.upgrade_speed_2), 3);
		pullFromAssembler(new ComparableStack(ModItems.upgrade_speed_3), 4);
		pullFromAssembler(new ComparableStack(ModItems.upgrade_power_1), 2);
		pullFromAssembler(new ComparableStack(ModItems.upgrade_power_2), 3);
		pullFromAssembler(new ComparableStack(ModItems.upgrade_power_3), 4);
		pullFromAssembler(new ComparableStack(ModItems.upgrade_effect_1), 2);
		pullFromAssembler(new ComparableStack(ModItems.upgrade_effect_2), 3);
		pullFromAssembler(new ComparableStack(ModItems.upgrade_effect_3), 4);
		pullFromAssembler(new ComparableStack(ModItems.upgrade_fortune_1), 2);
		pullFromAssembler(new ComparableStack(ModItems.upgrade_fortune_2), 3);
		pullFromAssembler(new ComparableStack(ModItems.upgrade_fortune_3), 4);
		pullFromAssembler(new ComparableStack(ModItems.upgrade_afterburn_1), 2);
		pullFromAssembler(new ComparableStack(ModItems.upgrade_afterburn_2), 3);
		pullFromAssembler(new ComparableStack(ModItems.upgrade_afterburn_3), 4);

		pullFromAssembler(new ComparableStack(ModItems.upgrade_radius), 4);
		pullFromAssembler(new ComparableStack(ModItems.upgrade_health), 4);
		pullFromAssembler(new ComparableStack(ModItems.upgrade_smelter), 4);
		pullFromAssembler(new ComparableStack(ModItems.upgrade_shredder), 4);
		pullFromAssembler(new ComparableStack(ModItems.upgrade_centrifuge), 4);
		pullFromAssembler(new ComparableStack(ModItems.upgrade_crystallizer), 4);
		pullFromAssembler(new ComparableStack(ModItems.upgrade_nullifier), 4);
		pullFromAssembler(new ComparableStack(ModItems.upgrade_screm), 4);
	}
	
	public static void registerConstructionRecycling() {
		constructionRecipes.add(new AnvilConstructionRecipe(
				new ComparableStack(ModBlocks.barrel_tcalloy),
				new AnvilOutput[] {
						new AnvilOutput(new ItemStack(ModItems.ingot_nickel, 2)),
						new AnvilOutput(new ItemStack(ModItems.ingot_tcalloy, 4)),
						new AnvilOutput(new ItemStack(ModItems.ingot_tcalloy, 1), 0.50F),
						new AnvilOutput(new ItemStack(ModItems.ingot_tcalloy, 1), 0.25F)
				}
		).setTier(3));

		constructionRecipes.add(new AnvilConstructionRecipe(
				new ComparableStack(ModItems.circuit_raw),
				new AnvilOutput[] {
						new AnvilOutput(new ItemStack(ModItems.plate_steel, 1)),
						new AnvilOutput(new ItemStack(ModItems.wire_aluminium, 1)),
						new AnvilOutput(new ItemStack(Items.redstone, 1))
				}
		).setTier(1));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new ComparableStack(ModItems.circuit_aluminium),
				new AnvilOutput[] {
						new AnvilOutput(new ItemStack(ModItems.plate_steel, 1)),
						new AnvilOutput(new ItemStack(ModItems.wire_aluminium, 1), 0.5F),
						new AnvilOutput(new ItemStack(Items.redstone, 1), 0.25F)
				}
		).setTier(1));
//		constructionRecipes.add(new AnvilConstructionRecipe(
//				new ComparableStack(ModItems.circuit_copper),
//				new AnvilOutput[] {
//						new AnvilOutput(new ItemStack(ModItems.circuit_aluminium, 1)),
//						new AnvilOutput(new ItemStack(ModItems.wire_copper, 2)),
//						new AnvilOutput(new ItemStack(ModItems.wire_copper, 1), 0.5F),
//						new AnvilOutput(new ItemStack(ModItems.wire_copper, 1), 0.25F),
//						new AnvilOutput(new ItemStack(ModItems.wafer, 1, EnumWaferType.SILICON.ordinal()), 0.25F),
//						new AnvilOutput(new ItemStack(ModItems.plate_copper, 1), 0.5F)
//				}
//		).setTier(2));
//		constructionRecipes.add(new AnvilConstructionRecipe(
//				new ComparableStack(ModItems.circuit_red_copper),
//				new AnvilOutput[] {
//						new AnvilOutput(new ItemStack(ModItems.circuit_copper, 1)),
//						new AnvilOutput(new ItemStack(ModItems.wire_red_copper, 2)),
//						new AnvilOutput(new ItemStack(ModItems.wire_red_copper, 1), 0.5F),
//						new AnvilOutput(new ItemStack(ModItems.wire_red_copper, 1), 0.25F),
//						new AnvilOutput(new ItemStack(ModItems.wafer, 1, GeneralConfig.enable528 ? EnumWaferType.GOLD.ordinal() : EnumWaferType.NICKEL.ordinal()), 0.25F),
//						new AnvilOutput(new ItemStack(ModItems.plate_polymer, 1), 0.5F)
//				}
//		).setTier(3));
//		constructionRecipes.add(new AnvilConstructionRecipe(
//				new ComparableStack(ModItems.circuit_gold),
//				new AnvilOutput[] {
//						new AnvilOutput(new ItemStack(ModItems.circuit_red_copper, 1)),
//						new AnvilOutput(new ItemStack(ModItems.wire_gold, 2)),
//						new AnvilOutput(new ItemStack(ModItems.wire_gold, 1), 0.5F),
//						new AnvilOutput(new ItemStack(ModItems.wire_gold, 1), 0.25F),
//						new AnvilOutput(new ItemStack(ModItems.wafer, 1, EnumWaferType.LAPIS.ordinal()), 0.25F),
//						new AnvilOutput(new ItemStack(ModItems.ingot_polymer, 1), 0.5F)
//				}
//		).setTier(4));
//		constructionRecipes.add(new AnvilConstructionRecipe(
//				new ComparableStack(ModItems.circuit_schrabidium),
//				new AnvilOutput[] {
//						new AnvilOutput(new ItemStack(ModItems.circuit_gold, 1)),
//						new AnvilOutput(new ItemStack(ModItems.wire_schrabidium, 2)),
//						new AnvilOutput(new ItemStack(ModItems.wire_schrabidium, 1), 0.5F),
//						new AnvilOutput(new ItemStack(ModItems.wire_schrabidium, 1), 0.25F),
//						new AnvilOutput(new ItemStack(ModItems.wafer, 1, GeneralConfig.enable528 ? EnumWaferType.CAESIUM.ordinal() : EnumWaferType.DIAMOND.ordinal()), 0.25F),
//						new AnvilOutput(new ItemStack(ModItems.ingot_desh, 1), 0.5F)
//				}
//		).setTier(6));

		constructionRecipes.add(new AnvilConstructionRecipe(
				new ComparableStack(ModItems.circuit_tantalium_raw),
				new AnvilOutput[] {
						new AnvilOutput(new ItemStack(Items.redstone, 4)),
						new AnvilOutput(new ItemStack(ModItems.wire_gold, 2)),
						new AnvilOutput(new ItemStack(ModItems.plate_copper, 2)),
						new AnvilOutput(new ItemStack(ModItems.nugget_tantalium, 1))
				}
		).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new ComparableStack(ModItems.circuit_tantalium),
				new AnvilOutput[] {
						new AnvilOutput(new ItemStack(Items.redstone, 2)),
						new AnvilOutput(new ItemStack(ModItems.wire_gold, 1)),
						new AnvilOutput(new ItemStack(ModItems.wire_gold, 1), 0.5F),
						new AnvilOutput(new ItemStack(ModItems.plate_copper, 1)),
						new AnvilOutput(new ItemStack(ModItems.nugget_tantalium, 1), 0.75F)
				}
		).setTier(4));
		
		constructionRecipes.add(new AnvilConstructionRecipe(
				new ComparableStack(ModItems.circuit_bismuth_raw),
				new AnvilOutput[] {
						new AnvilOutput(new ItemStack(Items.redstone, 4)),
						new AnvilOutput(new ItemStack(ModItems.ingot_polymer, 2)),
						new AnvilOutput(new ItemStack(GeneralConfig.enable528 ? ModItems.circuit_tantalium : ModItems.ingot_asbestos, 2)),
						new AnvilOutput(new ItemStack(ModItems.ingot_bismuth, 1))
				}
		).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new ComparableStack(ModItems.circuit_bismuth),
				new AnvilOutput[] {
						new AnvilOutput(new ItemStack(Items.redstone, 2)),
						new AnvilOutput(new ItemStack(ModItems.ingot_polymer, 1)),
						new AnvilOutput(new ItemStack(ModItems.ingot_polymer, 1), 0.5F),
						new AnvilOutput(new ItemStack(GeneralConfig.enable528 ? ModItems.circuit_tantalium : ModItems.ingot_asbestos, 1)),
						new AnvilOutput(new ItemStack(ModItems.ingot_bismuth, 1), 0.75F)
				}
		).setTier(4));

		constructionRecipes.add(new AnvilConstructionRecipe(
				new ComparableStack(ModItems.pile_rod_uranium), new AnvilOutput[] {
						new AnvilOutput(new ItemStack(ModItems.billet_uranium, 3)),
						new AnvilOutput(new ItemStack(ModItems.plate_iron, 2))
						}).setTier(2));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new ComparableStack(ModItems.pile_rod_source), new AnvilOutput[] {
						new AnvilOutput(new ItemStack(ModItems.billet_ra226be, 3)),
						new AnvilOutput(new ItemStack(ModItems.plate_iron, 2))
						}).setTier(2));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new ComparableStack(ModItems.pile_rod_boron), new AnvilOutput[] {
						new AnvilOutput(new ItemStack(ModItems.ingot_boron, 2)),
						new AnvilOutput(new ItemStack(Items.stick, 2))
						}).setTier(2));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new ComparableStack(ModItems.pile_rod_detector), new AnvilOutput[] {
						new AnvilOutput(new ItemStack(ModItems.ingot_boron, 2)),
						new AnvilOutput(new ItemStack(ModItems.motor, 1)),
						new AnvilOutput(new ItemStack(ModItems.circuit_aluminium, 1))
						}).setTier(2));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new ComparableStack(ModItems.pile_rod_lithium), new AnvilOutput[] {
						new AnvilOutput(new ItemStack(ModItems.lithium, 1)),
						new AnvilOutput(new ItemStack(ModItems.cell_empty, 1))
						}).setTier(2));
		
		//RBMK
		constructionRecipes.add(new AnvilConstructionRecipe(
				new ComparableStack(ModBlocks.rbmk_moderator), new AnvilOutput[] {
						new AnvilOutput(new ItemStack(ModBlocks.rbmk_blank, 1)),
						new AnvilOutput(new ItemStack(ModBlocks.block_graphite, 4))
				}).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new ComparableStack(ModBlocks.rbmk_absorber), new AnvilOutput[] {
						new AnvilOutput(new ItemStack(ModBlocks.rbmk_blank, 1)),
						new AnvilOutput(new ItemStack(ModItems.ingot_boron, 8))
				}).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new ComparableStack(ModBlocks.rbmk_reflector), new AnvilOutput[] {
						new AnvilOutput(new ItemStack(ModBlocks.rbmk_blank, 1)),
						new AnvilOutput(new ItemStack(ModItems.neutron_reflector, 8))
				}).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new ComparableStack(ModBlocks.rbmk_control), new AnvilOutput[] {
						new AnvilOutput(new ItemStack(ModBlocks.rbmk_absorber, 1)),
						new AnvilOutput(new ItemStack(ModItems.ingot_graphite, 2)),
						new AnvilOutput(new ItemStack(ModItems.motor, 2))
				}).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new ComparableStack(ModBlocks.rbmk_control_mod), new AnvilOutput[] {
						new AnvilOutput(new ItemStack(ModBlocks.rbmk_control, 1)),
						new AnvilOutput(new ItemStack(ModBlocks.block_graphite, 4)),
						new AnvilOutput(new ItemStack(ModItems.nugget_bismuth, 4))
				}).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new ComparableStack(ModBlocks.rbmk_control_auto), new AnvilOutput[] {
						new AnvilOutput(new ItemStack(ModBlocks.rbmk_control, 1)),
						new AnvilOutput(new ItemStack(ModItems.circuit_targeting_tier1, 2))
				}).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new ComparableStack(ModBlocks.rbmk_rod_reasim), new AnvilOutput[] {
						new AnvilOutput(new ItemStack(ModBlocks.rbmk_blank, 1)),
						new AnvilOutput(new ItemStack(ModItems.ingot_zirconium, 4)),
						new AnvilOutput(new ItemStack(ModItems.hull_small_steel, 2))
				}).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new ComparableStack(ModBlocks.rbmk_rod_reasim_mod), new AnvilOutput[] {
						new AnvilOutput(new ItemStack(ModBlocks.rbmk_rod_reasim, 1)),
						new AnvilOutput(new ItemStack(ModBlocks.block_graphite, 4)),
						new AnvilOutput(new ItemStack(ModItems.ingot_tcalloy, 4))
				}).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new ComparableStack(ModBlocks.rbmk_outgasser), new AnvilOutput[] {
						new AnvilOutput(new ItemStack(ModBlocks.rbmk_blank, 1)),
						new AnvilOutput(new ItemStack(ModBlocks.steel_grate, 6)),
						new AnvilOutput(new ItemStack(ModItems.tank_steel, 1)),
						new AnvilOutput(new ItemStack(Blocks.hopper, 1))
				}).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new ComparableStack(ModBlocks.rbmk_storage), new AnvilOutput[] {
						new AnvilOutput(new ItemStack(ModBlocks.rbmk_blank, 1)),
						new AnvilOutput(new ItemStack(ModBlocks.crate_steel, 2))
				}).setTier(4));
		
		if(!GeneralConfig.enable528) {
			
			constructionRecipes.add(new AnvilConstructionRecipe(
					new ComparableStack(ModBlocks.rbmk_rod), new AnvilOutput[] {
							new AnvilOutput(new ItemStack(ModBlocks.rbmk_blank, 1)),
							new AnvilOutput(new ItemStack(ModItems.hull_small_steel, 2))
					}).setTier(4));
			constructionRecipes.add(new AnvilConstructionRecipe(
					new ComparableStack(ModBlocks.rbmk_rod_mod), new AnvilOutput[] {
							new AnvilOutput(new ItemStack(ModBlocks.rbmk_rod, 1)),
							new AnvilOutput(new ItemStack(ModBlocks.block_graphite, 4)),
							new AnvilOutput(new ItemStack(ModItems.nugget_bismuth, 4))
					}).setTier(4));
			constructionRecipes.add(new AnvilConstructionRecipe(
					new ComparableStack(ModBlocks.rbmk_boiler), new AnvilOutput[] {
							new AnvilOutput(new ItemStack(ModBlocks.rbmk_blank, 1)),
							new AnvilOutput(new ItemStack(ModItems.board_copper, 6)),
							new AnvilOutput(new ItemStack(ModItems.pipes_steel, 2))
					}).setTier(4));
			constructionRecipes.add(new AnvilConstructionRecipe(
					new ComparableStack(ModBlocks.rbmk_cooler), new AnvilOutput[] {
							new AnvilOutput(new ItemStack(ModBlocks.rbmk_blank, 1)),
							new AnvilOutput(new ItemStack(ModBlocks.steel_grate, 4)),
							new AnvilOutput(new ItemStack(ModItems.plate_polymer, 4))
					}).setTier(4));
			constructionRecipes.add(new AnvilConstructionRecipe(
					new ComparableStack(ModBlocks.reactor_research), new AnvilOutput[] {
							new AnvilOutput(new ItemStack(ModItems.ingot_steel, 8)),
							new AnvilOutput(new ItemStack(ModItems.ingot_tcalloy, 4)),
							new AnvilOutput(new ItemStack(ModItems.motor_desh, 2)),
							new AnvilOutput(new ItemStack(ModItems.ingot_boron, 5)),
							new AnvilOutput(new ItemStack(ModItems.ingot_lead, 12)),
							new AnvilOutput(new ItemStack(ModItems.plate_lead, 2)),
							new AnvilOutput(new ItemStack(ModItems.plate_aluminium, 3)),
							new AnvilOutput(new ItemStack(ModItems.plate_aluminium, 1), 0.5F),
							new AnvilOutput(new ItemStack(ModItems.crt_display, 2)),
							new AnvilOutput(new ItemStack(ModItems.crt_display, 1), 0.5F),
							new AnvilOutput(new ItemStack(ModItems.circuit_copper, 1)),
							new AnvilOutput(new ItemStack(ModItems.circuit_copper, 1), 0.5F),
					}).setTier(4));
			constructionRecipes.add(new AnvilConstructionRecipe(
					new ComparableStack(ModItems.pile_rod_plutonium), new AnvilOutput[] {
							new AnvilOutput(new ItemStack(ModItems.billet_pu_mix, 2)),
							new AnvilOutput(new ItemStack(ModItems.billet_uranium, 1)),
							new AnvilOutput(new ItemStack(ModItems.plate_iron, 2))
					}).setTier(2));
			constructionRecipes.add(new AnvilConstructionRecipe(
					new ComparableStack(ModItems.pile_rod_pu239), new AnvilOutput[] {
							new AnvilOutput(new ItemStack(ModItems.billet_pu239, 1)), //Might need to be cut to 3 nuggets, but a full billet is nice and round
							new AnvilOutput(new ItemStack(ModItems.billet_pu_mix, 1)),
							new AnvilOutput(new ItemStack(ModItems.billet_uranium, 1)),
							new AnvilOutput(new ItemStack(ModItems.plate_iron, 2))
					}).setTier(2));
			ItemStack funnyLeadPlate = HazardTransformerRadiationNBT.addRadNBT(new ItemStack(ModItems.plate_lead), 0.5f);
			ItemStack funnyLeadIngot = HazardTransformerRadiationNBT.addRadNBT(new ItemStack(ModItems.ingot_lead), 2.5f);
			ItemStack funnyTung = HazardTransformerRadiationNBT.addRadNBT(new ItemStack(ModItems.neutron_reflector), 0.2f);
			ItemStack funnySteel = HazardTransformerRadiationNBT.addRadNBT(new ItemStack(ModItems.ingot_steel), 0.25f);
			constructionRecipes.add(new AnvilConstructionRecipe(new ComparableStack(ModBlocks.reactor_element), new AnvilOutput[]
					{
							new AnvilOutput(new ItemStack(ModItems.ingot_steel, 2)),
							new AnvilOutput(new ItemStack(ModItems.neutron_reflector, 3)),
							new AnvilOutput(funnyTung),
							new AnvilOutput(new ItemStack(ModItems.plate_lead)),
							new AnvilOutput(funnyLeadPlate, 0.75f),
							new AnvilOutput(new ItemStack(ModItems.ingot_zirconium, 2))
					}).setTier(4));
			constructionRecipes.add(new AnvilConstructionRecipe(new ComparableStack(ModBlocks.reactor_hatch), new AnvilOutput[]
					{
							new AnvilOutput(new ItemStack(ModBlocks.brick_concrete)),
							new AnvilOutput(new ItemStack(ModItems.plate_steel, 6))
					}).setTier(4));
			constructionRecipes.add(new AnvilConstructionRecipe(new ComparableStack(ModBlocks.reactor_control), new AnvilOutput[]
					{
							new AnvilOutput(new ItemStack(ModItems.ingot_steel, 2)),
							new AnvilOutput(funnySteel, 0.8f),
							new AnvilOutput(funnySteel, 0.65f),
							new AnvilOutput(new ItemStack(ModItems.ingot_lead)),
							new AnvilOutput(funnyLeadIngot, 0.9f),
							new AnvilOutput(funnyLeadIngot, 0.9f),
							new AnvilOutput(funnyLeadIngot, 0.75f),
							new AnvilOutput(new ItemStack(ModItems.bolt_tungsten, 6)),
							new AnvilOutput(new ItemStack(ModItems.motor))
					}).setTier(4));
			constructionRecipes.add(new AnvilConstructionRecipe(new ComparableStack(ModBlocks.reactor_conductor), new AnvilOutput[]
					{
							new AnvilOutput(new ItemStack(ModItems.ingot_steel, 6)),
							new AnvilOutput(new ItemStack(ModItems.plate_copper, 12)),
							new AnvilOutput(new ItemStack(ModItems.wire_tungsten, 4))
					}).setTier(4));
			constructionRecipes.add(new AnvilConstructionRecipe(new ComparableStack(ModBlocks.reactor_computer), new AnvilOutput[]
					{
							new AnvilOutput(new ItemStack(ModBlocks.reactor_conductor, 2)),
							new AnvilOutput(new ItemStack(ModItems.circuit_targeting_tier3, 4)),
							new AnvilOutput(new ItemStack(ModItems.circuit_gold))
					}).setTier(4));
			constructionRecipes.add(new AnvilConstructionRecipe(new ComparableStack(ModItems.reactor_sensor), new AnvilOutput[]
					{
							new AnvilOutput(new ItemStack(ModItems.plate_lead, 4)),
							new AnvilOutput(new ItemStack(ModItems.wire_tungsten, 2)),
							new AnvilOutput(new ItemStack(ModItems.magnetron)),
							new AnvilOutput(new ItemStack(ModItems.circuit_targeting_tier3, 2))
					}).setTier(4));
			constructionRecipes.add(new AnvilConstructionRecipe(new ComparableStack(ModBlocks.reactor_ejector), new AnvilOutput[]
					{
							new AnvilOutput(new ItemStack(ModBlocks.brick_concrete, 3)),
							new AnvilOutput(HazardTransformerRadiationNBT.addRadNBT(new ItemStack(ModBlocks.brick_concrete), 1.5f)),
							new AnvilOutput(funnyLeadPlate),
							new AnvilOutput(funnyLeadPlate, 0.9f),
							new AnvilOutput(new ItemStack(ModItems.motor, 2)),
							new AnvilOutput(new ItemStack(ModBlocks.reactor_hatch))
					}).setTier(4));
			constructionRecipes.add(new AnvilConstructionRecipe(new ComparableStack(ModBlocks.reactor_inserter), new AnvilOutput[]
					{
							new AnvilOutput(new ItemStack(ModBlocks.brick_concrete, 4)),
							new AnvilOutput(new ItemStack(ModItems.plate_copper, 2)),
							new AnvilOutput(new ItemStack(ModItems.motor, 2)),
							new AnvilOutput(new ItemStack(ModBlocks.reactor_hatch))
					}).setTier(4));

		} else {
			constructionRecipes.add(new AnvilConstructionRecipe(
					new ComparableStack(ModItems.pile_rod_plutonium), new AnvilOutput[] {
							new AnvilOutput(new ItemStack(ModItems.billet_pu_mix, 2)),
							new AnvilOutput(new ItemStack(ModItems.billet_nuclear_waste, 1)),
							new AnvilOutput(new ItemStack(ModItems.plate_iron, 1))
					}).setTier(2));
			constructionRecipes.add(new AnvilConstructionRecipe(
					new ComparableStack(ModItems.pile_rod_pu239), new AnvilOutput[] {
							new AnvilOutput(new ItemStack(ModItems.billet_pu239, 1)),
							new AnvilOutput(new ItemStack(ModItems.billet_pu_mix, 1)),
							new AnvilOutput(new ItemStack(ModItems.billet_nuclear_waste, 1)),
							new AnvilOutput(new ItemStack(ModItems.plate_iron, 2))
					}).setTier(2));
		}

		constructionRecipes.add(new AnvilConstructionRecipe(
				new ComparableStack(ModBlocks.machine_turbine), new AnvilOutput[] {
						new AnvilOutput(new ItemStack(ModItems.turbine_titanium, 1)),
						new AnvilOutput(new ItemStack(ModItems.coil_copper, 2)),
						new AnvilOutput(new ItemStack(ModItems.ingot_steel, 6))
						}).setTier(3));
		
		constructionRecipes.add(new AnvilConstructionRecipe(
				new ComparableStack(ModBlocks.yellow_barrel), new AnvilOutput[] {
						new AnvilOutput(new ItemStack(ModItems.tank_steel, 1)),
						new AnvilOutput(new ItemStack(ModItems.plate_lead, 2)),
						new AnvilOutput(new ItemStack(ModItems.nuclear_waste, 10))
				}).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new ComparableStack(ModBlocks.vitrified_barrel), new AnvilOutput[] {
						new AnvilOutput(new ItemStack(ModItems.tank_steel, 1)),
						new AnvilOutput(new ItemStack(ModItems.plate_lead, 2)),
						new AnvilOutput(new ItemStack(ModItems.nuclear_waste_vitrified, 10))
				}).setTier(3));
		
		constructionRecipes.add(new AnvilConstructionRecipe(new ComparableStack(ModItems.mike_deut), new AnvilOutput[]
				{
						new AnvilOutput(new ItemStack(ModItems.cell_deuterium, 10)),
						new AnvilOutput(new ItemStack(ModItems.plate_iron, 12)),
						new AnvilOutput(new ItemStack(ModItems.plate_steel, 16))
				}).setTier(3));
	}
	
	public static void pullFromAssembler(ComparableStack result, int tier) {
		
		AStack[] ingredients = AssemblerRecipes.recipes.get(result);
		
		if(ingredients != null) {
			constructionRecipes.add(new AnvilConstructionRecipe(ingredients, new AnvilOutput(result.toStack())).setTier(tier));
		}
	}
	
	public static List<AnvilSmithingRecipe> getSmithing() {
		return smithingRecipes;
	}
	
	public static List<AnvilConstructionRecipe> getConstruction() {
		return constructionRecipes;
	}
	
	public static class AnvilConstructionRecipe {
		public List<AStack> input = new ArrayList<AStack>();
		public List<AnvilOutput> output = new ArrayList<AnvilOutput>();
		public int tierLower = 0;
		public int tierUpper = -1;
		public boolean batteryType, storageType;
		OverlayType overlay = OverlayType.NONE;
		
		public AnvilConstructionRecipe(AStack input, AnvilOutput output) {
			this.input.add(input);
			this.output.add(output);
			this.setOverlay(OverlayType.SMITHING); //preferred overlay for 1:1 conversions is smithing
		}
		
		public AnvilConstructionRecipe(AStack[] input, AnvilOutput output) {
			for(AStack stack : input) this.input.add(stack);
			this.output.add(output);
			this.setOverlay(OverlayType.CONSTRUCTION); //preferred overlay for many:1 conversions is construction
		}
		
		public AnvilConstructionRecipe(AStack input, AnvilOutput[] output) {
			this.input.add(input);
			for(AnvilOutput out : output) this.output.add(out);
			this.setOverlay(OverlayType.RECYCLING); //preferred overlay for 1:many conversions is recycling
		}
		
		public AnvilConstructionRecipe(AStack[] input, AnvilOutput[] output) {
			for(AStack stack : input) this.input.add(stack);
			for(AnvilOutput out : output) this.output.add(out);
			this.setOverlay(OverlayType.NONE); //no preferred overlay for many:many conversions
		}
		
		public AnvilConstructionRecipe setTier(int tier) {
			this.tierLower = tier;
			if(GeneralConfig.enableLBSM && GeneralConfig.enableLBSMUnlockAnvil) this.tierLower = 1;
			return this;
		}
		
		public AnvilConstructionRecipe setBatteryType()
		{
			batteryType = true;
			return this;
		}
		
		public AnvilConstructionRecipe setTierRange(int lower, int upper) {
			this.tierLower = lower;
			this.tierUpper = upper;
			if(GeneralConfig.enableLBSM && GeneralConfig.enableLBSMUnlockAnvil) this.tierLower = this.tierUpper = 1;
			return this;
		}
		
		public boolean isTierValid(int tier) {
			
			if(this.tierUpper == -1)
				return tier >= this.tierLower;
			
			return tier >= this.tierLower && tier <= this.tierUpper;
		}
		
		public AnvilConstructionRecipe setOverlay(OverlayType overlay) {
			this.overlay = overlay;
			return this;
		}
		
		public OverlayType getOverlay() {
			return this.overlay;
		}
		
		public ItemStack getDisplay() {
			switch(this.overlay) {
			case RECYCLING:
				for(AStack stack : this.input) {
					if(stack instanceof ComparableStack)
						return ((ComparableStack)stack).toStack();
				}
			case NONE:
			case CONSTRUCTION:
			case SMITHING:
				return this.output.get(0).stack.copy();
			default: return new ItemStack(Items.iron_pickaxe);
			}
		}
	}
	
	public static class AnvilOutput {
		public ItemStack stack;
		public float chance;
		
		public AnvilOutput(ItemStack stack) {
			this(stack, 1F);
		}
		
		public AnvilOutput(ItemStack stack, float chance) {
			this.stack = stack;
			this.chance = chance;
		}
	}
	
	public static enum OverlayType {
		NONE,
		CONSTRUCTION,
		RECYCLING,
		SMITHING;
	}
}

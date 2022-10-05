package com.hbm.inventory.recipes.anvil;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.inventory.OreDictManager;
import static com.hbm.inventory.OreDictManager.*;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.recipes.AssemblerRecipes;
import com.hbm.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;


public class AnvilRecipes {

	private static List<AnvilSmithingRecipe> smithingRecipes = new ArrayList();
	private static List<AnvilConstructionRecipe> constructionRecipes = new ArrayList();
	
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
		
		Block[] anvils = new Block[]{ModBlocks.anvil_iron, ModBlocks.anvil_lead};
		
		for(Block anvil : anvils) {
			smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.anvil_bismuth, 1), new ComparableStack(anvil), new ComparableStack(ModItems.ingot_bismuth, 10)));
			smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.anvil_dnt, 1), new ComparableStack(anvil), new OreDictStack(DNT.ingot(), 10)));
			smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.anvil_ferrouranium, 1), new ComparableStack(anvil), new OreDictStack(U238.ingot(), 10)));
			smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.anvil_meteorite, 1), new ComparableStack(anvil), new ComparableStack(ModItems.ingot_meteorite, 10)));
			smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.anvil_schrabidate, 1), new ComparableStack(anvil), new OreDictStack(SBD.ingot(), 10)));
			smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.anvil_starmetal, 1), new ComparableStack(anvil), new OreDictStack(STAR.ingot(), 10)));
			smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.anvil_steel, 1), new ComparableStack(anvil), new OreDictStack(STEEL.ingot(), 10)));
			smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.anvil_osmiridium, 1), new ComparableStack(anvil), new OreDictStack(OSMIRIDIUM.ingot(), 10)));
		}
		
		for(int i = 0; i < 9; i++)
			smithingRecipes.add(new AnvilSmithingHotRecipe(3, new ItemStack(ModItems.ingot_steel_dusted, 1, i + 1),
					new ComparableStack(ModItems.ingot_steel_dusted, 1, i), new ComparableStack(ModItems.ingot_steel_dusted, 1, i)));
		
		smithingRecipes.add(new AnvilSmithingHotRecipe(3, new ItemStack(ModItems.ingot_chainsteel, 1),
				new ComparableStack(ModItems.ingot_steel_dusted, 1, 9), new ComparableStack(ModItems.ingot_steel_dusted, 1, 9)));
		
		smithingRecipes.add(new AnvilSmithingHotRecipe(3, new ItemStack(ModItems.ingot_meteorite_forged, 1), new ComparableStack(ModItems.ingot_meteorite), new ComparableStack(ModItems.ingot_meteorite)));
		smithingRecipes.add(new AnvilSmithingHotRecipe(3, new ItemStack(ModItems.blade_meteorite, 1), new ComparableStack(ModItems.ingot_meteorite_forged), new ComparableStack(ModItems.ingot_meteorite_forged)));
		smithingRecipes.add(new AnvilSmithingHotRecipe(3, new ItemStack(ModItems.meteorite_sword_reforged, 1), new ComparableStack(ModItems.meteorite_sword_seared), new ComparableStack(ModItems.ingot_meteorite_forged)));
		smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModItems.gun_ar15, 1), new ComparableStack(ModItems.gun_thompson), new ComparableStack(ModItems.pipe_lead)));
		smithingRecipes.add(new AnvilSmithingRecipe(1916169, new ItemStack(ModItems.wings_murk, 1), new ComparableStack(ModItems.wings_limp), new ComparableStack(ModItems.particle_tachyon)));

		smithingRecipes.add(new AnvilSmithingCyanideRecipe());
		smithingRecipes.add(new AnvilSmithingRenameRecipe());
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
		
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(IRON.ingot()), new AnvilOutput(new ItemStack(ModItems.plate_iron))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(GOLD.ingot()), new AnvilOutput(new ItemStack(ModItems.plate_gold))).setTier(3));
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
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(GOLD.ingot()), new AnvilOutput(new ItemStack(ModItems.wire_gold, 8))).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(SA326.ingot()), new AnvilOutput(new ItemStack(ModItems.wire_schrabidium, 8))).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(MAGTUNG.ingot()), new AnvilOutput(new ItemStack(ModItems.wire_magnetized_tungsten, 8))).setTier(4));

		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(COAL.dust()), new AnvilOutput(new ItemStack(Items.coal))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(NETHERQUARTZ.dust()), new AnvilOutput(new ItemStack(Items.quartz))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(LAPIS.dust()), new AnvilOutput(new ItemStack(Items.dye, 1, 4))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(DIAMOND.dust()), new AnvilOutput(new ItemStack(Items.diamond))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(EMERALD.dust()), new AnvilOutput(new ItemStack(Items.emerald))).setTier(3));

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

		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {new OreDictStack(DNT.ingot(), 4), new ComparableStack(ModBlocks.depth_brick)},
				new AnvilOutput(new ItemStack(ModBlocks.depth_dnt))).setTier(1916169));

		constructionRecipes.add(new AnvilConstructionRecipe(
				new OreDictStack(CU.plate(), 4),
				new AnvilOutput(new ItemStack(ModItems.board_copper))).setTier(1));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new ComparableStack(ModItems.plate_steel, 2),
				new AnvilOutput(new ItemStack(ModItems.hull_small_steel))).setTier(1));
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
				new AStack[] {new OreDictStack(IRON.plate(), 2), new ComparableStack(ModItems.coil_copper), new ComparableStack(ModItems.coil_copper_torus)},
				new AnvilOutput(new ItemStack(ModItems.motor, 2))).setTier(1));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {new ComparableStack(ModItems.motor), new OreDictStack(ANY_PLASTIC.ingot(), 2), new OreDictStack(DESH.ingot(), 2), new ComparableStack(ModItems.coil_gold_torus)},
				new AnvilOutput(new ItemStack(ModItems.motor_desh, 1))).setTier(3));
		
		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {
						new ComparableStack(Blocks.stonebrick, 4),
						new OreDictStack(IRON.ingot(), 2),
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
						new OreDictStack(IRON.plate(), 8),
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
						new ComparableStack(ModItems.deuterium_filter, 3),
						new ComparableStack(ModItems.hull_big_steel, 5),
						new ComparableStack(ModBlocks.concrete_smooth, 8),
						new ComparableStack(ModBlocks.concrete_asbestos, 4),
						new ComparableStack(ModBlocks.steel_scaffold, 16),
						new ComparableStack(ModBlocks.deco_pipe_quad, 12),
						new OreDictStack("dustSulfur", 32),
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
				new AnvilOutput(new ItemStack(ModItems.plate_euphemium, 4))).setTier(6));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {new OreDictStack(DNT.ingot(), 4), new ComparableStack(ModItems.powder_spark_mix, 2), new OreDictStack(DESH.ingot(), 1)},
				new AnvilOutput(new ItemStack(ModItems.plate_dineutronium, 4))).setTier(7));

		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {new OreDictStack(TI.plate(), 2), new OreDictStack(STEEL.ingot(), 1), new ComparableStack(ModItems.bolt_tungsten, 2)},
				new AnvilOutput(new ItemStack(ModItems.plate_armor_titanium))).setTier(2));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {new OreDictStack(IRON.plate(), 4), new OreDictStack(BIGMT.plate(), 2), new ComparableStack(ModItems.plate_armor_titanium, 1)},
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
		
		pullFromAssembler(new ComparableStack(ModItems.plate_mixed, 4), 3);

		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {new ComparableStack(ModItems.ingot_arsenic), new OreDictStack(REDSTONE.dust(), 4), new OreDictStack(ASBESTOS.ingot(), 2)},
				new AnvilOutput(new ItemStack(ModItems.circuit_arsenic_raw))).setTier(5));
		
		constructionRecipes.add(new AnvilConstructionRecipe(new ComparableStack(ModItems.ingot_u233, 1), new AnvilOutput(new ItemStack(ModItems.plate_fuel_u233))).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(new ComparableStack(ModItems.ingot_u235, 1), new AnvilOutput(new ItemStack(ModItems.plate_fuel_u235))).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(new ComparableStack(ModItems.ingot_mox_fuel, 1), new AnvilOutput(new ItemStack(ModItems.plate_fuel_mox))).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(new ComparableStack(ModItems.ingot_pu239, 1), new AnvilOutput(new ItemStack(ModItems.plate_fuel_pu239))).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(new ComparableStack(ModItems.ingot_schrabidium, 1), new AnvilOutput(new ItemStack(ModItems.plate_fuel_sa326))).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(new ComparableStack(ModItems.billet_ra226be, 1), new AnvilOutput(new ItemStack(ModItems.plate_fuel_ra226be))).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(new ComparableStack(ModItems.billet_pu238be, 1), new AnvilOutput(new ItemStack(ModItems.plate_fuel_pu238be))).setTier(4));
	}
	
	public static void registerConstructionAmmo() {
		
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(CU.plate()), new AnvilOutput(new ItemStack(ModItems.casing_357))).setTier(1));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(CU.plate()), new AnvilOutput(new ItemStack(ModItems.casing_44))).setTier(1));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(CU.plate()), new AnvilOutput(new ItemStack(ModItems.casing_9))).setTier(1));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(CU.plate()), new AnvilOutput(new ItemStack(ModItems.casing_50))).setTier(1));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack(CU.plate()), new AnvilOutput(new ItemStack(ModItems.casing_buckshot))).setTier(1));
		constructionRecipes.add(new AnvilConstructionRecipe(new AStack[] {new OreDictStack(IRON.plate()), new ComparableStack(Items.redstone)}, new AnvilOutput(new ItemStack(ModItems.primer_357))).setTier(1));
		constructionRecipes.add(new AnvilConstructionRecipe(new AStack[] {new OreDictStack(IRON.plate()), new ComparableStack(Items.redstone)}, new AnvilOutput(new ItemStack(ModItems.primer_44))).setTier(1));
		constructionRecipes.add(new AnvilConstructionRecipe(new AStack[] {new OreDictStack(IRON.plate()), new ComparableStack(Items.redstone)}, new AnvilOutput(new ItemStack(ModItems.primer_9))).setTier(1));
		constructionRecipes.add(new AnvilConstructionRecipe(new AStack[] {new OreDictStack(IRON.plate()), new ComparableStack(Items.redstone)}, new AnvilOutput(new ItemStack(ModItems.primer_50))).setTier(1));
		constructionRecipes.add(new AnvilConstructionRecipe(new AStack[] {new OreDictStack(IRON.plate()), new ComparableStack(Items.redstone)}, new AnvilOutput(new ItemStack(ModItems.primer_buckshot))).setTier(1));
		
		Object[][] recs = new Object[][] {
			{ModItems.ammo_12gauge,	P_RED.dust(),										ModItems.ammo_12gauge_incendiary,	20,		2},
			{ModItems.ammo_12gauge,	Item.getItemFromBlock(ModBlocks.gravel_obsidian),	ModItems.ammo_12gauge_shrapnel,		20,		2},
			{ModItems.ammo_12gauge,	U238.ingot(),										ModItems.ammo_12gauge_du,			20,		3},
			{ModItems.ammo_12gauge,	ModItems.coin_maskman,								ModItems.ammo_12gauge_sleek,		100,	4},
			
			{ModItems.ammo_20gauge,	P_RED.dust(),										ModItems.ammo_20gauge_incendiary,	20,		2},
			{ModItems.ammo_20gauge,	Item.getItemFromBlock(ModBlocks.gravel_obsidian),	ModItems.ammo_20gauge_shrapnel,		20,		2},
			{ModItems.ammo_20gauge,	ModItems.powder_poison,								ModItems.ammo_20gauge_caustic,		20,		2},
			{ModItems.ammo_20gauge,	DIAMOND.dust(),										ModItems.ammo_20gauge_shock,		20,		2},
			{ModItems.ammo_20gauge,	Item.getItemFromBlock(Blocks.soul_sand),			ModItems.ammo_20gauge_wither,		10,		3},
			{ModItems.ammo_20gauge,	ModItems.coin_maskman,								ModItems.ammo_20gauge_sleek,		100,	4},

			{ModItems.ammo_4gauge_flechette,	P_WHITE.ingot(),				ModItems.ammo_4gauge_flechette_phosphorus,	20,		2},
			{ModItems.ammo_4gauge_explosive,	ModItems.egg_balefire_shard,	ModItems.ammo_4gauge_balefire,				10,		4},
			{ModItems.ammo_4gauge_explosive,	ModItems.ammo_rocket,			ModItems.ammo_4gauge_kampf,					4,		2},
			{ModItems.ammo_4gauge_kampf,		ModItems.pellet_canister,		ModItems.ammo_4gauge_canister,				10,		3},
			{ModItems.ammo_4gauge,				ModItems.pellet_claws,			ModItems.ammo_4gauge_claw,					4,		5},
			{ModItems.ammo_4gauge,				ModItems.toothpicks,			ModItems.ammo_4gauge_vampire,				4,		5},
			{ModItems.ammo_4gauge,				ModItems.pellet_charged,		ModItems.ammo_4gauge_void,					1,		5},
			{ModItems.ammo_4gauge,				ModItems.coin_maskman,			ModItems.ammo_4gauge_sleek,					100,	4},

			{ModItems.ammo_44,		DURA.ingot(),					ModItems.ammo_44_ap,			20,	2},
			{ModItems.ammo_44,		U238.ingot(),					ModItems.ammo_44_du,			20,	2},
			{ModItems.ammo_44,		P_WHITE.ingot(),				ModItems.ammo_44_phosphorus,	20,	2},
			{ModItems.ammo_44_du,	STAR.ingot(),					ModItems.ammo_44_star,			10,	3},
			{ModItems.ammo_44,		ModItems.pellet_chlorophyte,	ModItems.ammo_44_chlorophyte,	10,	3},

			{ModItems.ammo_5mm,	ANY_HIGHEXPLOSIVE.ingot(),		ModItems.ammo_5mm_explosive,	20,	2},
			{ModItems.ammo_5mm,	U238.ingot(),					ModItems.ammo_5mm_du,			20,	2},
			{ModItems.ammo_5mm,	STAR.ingot(),					ModItems.ammo_5mm_star,			10,	3},
			{ModItems.ammo_5mm,	ModItems.pellet_chlorophyte,	ModItems.ammo_5mm_chlorophyte,	10,	3},

			{ModItems.ammo_9mm,	DURA.ingot(),					ModItems.ammo_9mm_ap,			20,	2},
			{ModItems.ammo_9mm,	U238.ingot(),					ModItems.ammo_9mm_du,			20,	2},
			{ModItems.ammo_9mm,	ModItems.pellet_chlorophyte,	ModItems.ammo_9mm_chlorophyte,	10,	3},
			
			{ModItems.ammo_22lr,	DURA.ingot(),					ModItems.ammo_22lr_ap,			20,	2},
			{ModItems.ammo_22lr,	ModItems.pellet_chlorophyte,	ModItems.ammo_22lr_chlorophyte,	10,	3},

			{ModItems.ammo_50bmg,			P_RED.dust(),					ModItems.ammo_50bmg_incendiary,		20,		2},
			{ModItems.ammo_50bmg,			P_WHITE.ingot(),				ModItems.ammo_50bmg_phosphorus,		20,		2},
			{ModItems.ammo_50bmg,			ANY_HIGHEXPLOSIVE.ingot(),		ModItems.ammo_50bmg_explosive,		20,		2},
			{ModItems.ammo_50bmg,			DURA.ingot(),					ModItems.ammo_50bmg_ap,				20,		2},
			{ModItems.ammo_50bmg,			U238.ingot(),					ModItems.ammo_50bmg_du,				20,		2},
			{ModItems.ammo_50bmg_du,		STAR.ingot(),					ModItems.ammo_50bmg_star,			10,		3},
			{ModItems.ammo_50bmg,			ModItems.pellet_chlorophyte,	ModItems.ammo_50bmg_chlorophyte,	10,		3},
			{ModItems.ammo_50bmg,			ModItems.coin_maskman,			ModItems.ammo_50bmg_sleek,			100,	4},
			{ModItems.ammo_50bmg,			ModItems.pellet_flechette,		ModItems.ammo_50bmg_flechette,		20,		2},
			{ModItems.ammo_50bmg_flechette,	ModItems.nugget_am_mix,			ModItems.ammo_50bmg_flechette_am,	10,		3},
			{ModItems.ammo_50bmg_flechette,	ModItems.powder_polonium,		ModItems.ammo_50bmg_flechette_po,	20,		3},

			{ModItems.ammo_50ae,		DURA.ingot(),					ModItems.ammo_50ae_ap,			20,	2},
			{ModItems.ammo_50ae,		U238.ingot(),					ModItems.ammo_50ae_du,			20,	2},
			{ModItems.ammo_50ae_du,		STAR.ingot(),					ModItems.ammo_50ae_star,		10,	3},
			{ModItems.ammo_50ae,		ModItems.pellet_chlorophyte,	ModItems.ammo_50ae_chlorophyte,	10,	3},

			{ModItems.ammo_556,				P_WHITE.ingot(),				ModItems.ammo_556_phosphorus,				20,		2},
			{ModItems.ammo_556,				DURA.ingot(),					ModItems.ammo_556_ap,						20,		2},
			{ModItems.ammo_556,				U238.ingot(),					ModItems.ammo_556_du,						20,		2},
			{ModItems.ammo_556_du,			STAR.ingot(),					ModItems.ammo_556_star,						10,		3},
			{ModItems.ammo_556,				ModItems.pellet_chlorophyte,	ModItems.ammo_556_chlorophyte,				10,		3},
			{ModItems.ammo_556,				ModItems.coin_maskman,			ModItems.ammo_556_sleek,					100,	4},
			{ModItems.ammo_556,				Items.redstone,					ModItems.ammo_556_tracer,					20,		2},
			{ModItems.ammo_556,				ModItems.pellet_flechette,		ModItems.ammo_556_flechette,				20,		2},
			{ModItems.ammo_556_flechette,	P_RED.dust(),					ModItems.ammo_556_flechette_incendiary,		20,		2},
			{ModItems.ammo_556_flechette,	P_WHITE.ingot(),				ModItems.ammo_556_flechette_phosphorus,		20,		2},
			{ModItems.ammo_556_flechette,	U238.ingot(),					ModItems.ammo_556_flechette_du,				20,		2},
			{ModItems.ammo_556_flechette,	ModItems.coin_maskman,			ModItems.ammo_556_flechette_sleek,			100,	4},
			{ModItems.ammo_556_flechette,	ModItems.pellet_chlorophyte,	ModItems.ammo_556_flechette_chlorophyte,	10,		3},
		};
		
		for(Object[] objs : recs) {
			
			if(objs[1] instanceof Item) {
				constructionRecipes.add(new AnvilConstructionRecipe(new AStack[] { new ComparableStack((Item)objs[0], (int)objs[3]), new ComparableStack((Item)objs[1], 1) },
						new AnvilOutput(new ItemStack((Item)objs[2], (int)objs[3]))).setTier((int)objs[4]));
				
			} else if(objs[1] instanceof String) {
				constructionRecipes.add(new AnvilConstructionRecipe(new AStack[] { new ComparableStack((Item)objs[0], (int)objs[3]), new OreDictStack((String)objs[1], 1) },
						new AnvilOutput(new ItemStack((Item)objs[2], (int)objs[3]))).setTier((int)objs[4]));
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
						new AnvilOutput(new ItemStack(ModItems.ingot_titanium, 2)),
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
		constructionRecipes.add(new AnvilConstructionRecipe(
				new ComparableStack(ModItems.circuit_copper),
				new AnvilOutput[] {
						new AnvilOutput(new ItemStack(ModItems.circuit_aluminium, 1)),
						new AnvilOutput(new ItemStack(ModItems.wire_copper, 2)),
						new AnvilOutput(new ItemStack(ModItems.wire_copper, 1), 0.5F),
						new AnvilOutput(new ItemStack(ModItems.wire_copper, 1), 0.25F),
						new AnvilOutput(new ItemStack(ModItems.powder_quartz, 1), 0.25F),
						new AnvilOutput(new ItemStack(ModItems.plate_copper, 1), 0.5F)
				}
		).setTier(2));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new ComparableStack(ModItems.circuit_red_copper),
				new AnvilOutput[] {
						new AnvilOutput(new ItemStack(ModItems.circuit_copper, 1)),
						new AnvilOutput(new ItemStack(ModItems.wire_red_copper, 2)),
						new AnvilOutput(new ItemStack(ModItems.wire_red_copper, 1), 0.5F),
						new AnvilOutput(new ItemStack(ModItems.wire_red_copper, 1), 0.25F),
						new AnvilOutput(new ItemStack(ModItems.powder_gold, 1), 0.25F),
						new AnvilOutput(new ItemStack(ModItems.plate_polymer, 1), 0.5F)
				}
		).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new ComparableStack(ModItems.circuit_gold),
				new AnvilOutput[] {
						new AnvilOutput(new ItemStack(ModItems.circuit_red_copper, 1)),
						new AnvilOutput(new ItemStack(ModItems.wire_gold, 2)),
						new AnvilOutput(new ItemStack(ModItems.wire_gold, 1), 0.5F),
						new AnvilOutput(new ItemStack(ModItems.wire_gold, 1), 0.25F),
						new AnvilOutput(new ItemStack(ModItems.powder_lapis, 1), 0.25F),
						new AnvilOutput(new ItemStack(ModItems.ingot_polymer, 1), 0.5F)
				}
		).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new ComparableStack(ModItems.circuit_schrabidium),
				new AnvilOutput[] {
						new AnvilOutput(new ItemStack(ModItems.circuit_gold, 1)),
						new AnvilOutput(new ItemStack(ModItems.wire_schrabidium, 2)),
						new AnvilOutput(new ItemStack(ModItems.wire_schrabidium, 1), 0.5F),
						new AnvilOutput(new ItemStack(ModItems.wire_schrabidium, 1), 0.25F),
						new AnvilOutput(new ItemStack(ModItems.powder_diamond, 1), 0.25F),
						new AnvilOutput(new ItemStack(ModItems.ingot_desh, 1), 0.5F)
				}
		).setTier(6));

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
							new AnvilOutput(new ItemStack(ModItems.billet_pu_mix, 3)),
							new AnvilOutput(new ItemStack(ModItems.plate_iron, 2))
					}).setTier(2));

		} else {
			constructionRecipes.add(new AnvilConstructionRecipe(
					new ComparableStack(ModItems.pile_rod_plutonium), new AnvilOutput[] {
							new AnvilOutput(new ItemStack(ModItems.billet_pu_mix, 2)),
							new AnvilOutput(new ItemStack(ModItems.billet_nuclear_waste, 1)),
							new AnvilOutput(new ItemStack(ModItems.plate_iron, 1))
					}).setTier(2));
		}

		constructionRecipes.add(new AnvilConstructionRecipe(
				new ComparableStack(ModBlocks.machine_turbine), new AnvilOutput[] {
						new AnvilOutput(new ItemStack(ModItems.turbine_titanium, 2)),
						new AnvilOutput(new ItemStack(ModItems.motor, 1)),
						new AnvilOutput(new ItemStack(ModItems.tank_steel, 2)),
						new AnvilOutput(new ItemStack(ModItems.plate_titanium, 4))
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
		public List<AStack> input = new ArrayList();
		public List<AnvilOutput> output = new ArrayList();
		public int tierLower = 0;
		public int tierUpper = -1;
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
			if(GeneralConfig.enableBabyMode) this.tierLower = 1;
			return this;
		}
		
		public AnvilConstructionRecipe setTierRange(int lower, int upper) {
			this.tierLower = lower;
			this.tierUpper = upper;
			if(GeneralConfig.enableBabyMode) this.tierLower = this.tierUpper = 1;
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
			case NONE: return this.output.get(0).stack.copy();
			case CONSTRUCTION: return this.output.get(0).stack.copy();
			case SMITHING: return this.output.get(0).stack.copy();
			case RECYCLING:
				for(AStack stack : this.input) {
					if(stack instanceof ComparableStack)
						return ((ComparableStack)stack).toStack();
				}
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

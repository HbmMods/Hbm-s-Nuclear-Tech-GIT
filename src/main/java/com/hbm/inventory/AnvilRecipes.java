package com.hbm.inventory;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
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
			smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.anvil_dnt, 1), new ComparableStack(anvil), new ComparableStack(ModItems.ingot_dineutronium, 10)));
			smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.anvil_ferrouranium, 1), new ComparableStack(anvil), new ComparableStack(ModItems.ingot_u238, 10)));
			smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.anvil_meteorite, 1), new ComparableStack(anvil), new ComparableStack(ModItems.ingot_meteorite, 10)));
			smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.anvil_schrabidate, 1), new ComparableStack(anvil), new ComparableStack(ModItems.ingot_schrabidate, 10)));
			smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.anvil_starmetal, 1), new ComparableStack(anvil), new ComparableStack(ModItems.ingot_starmetal, 10)));
			smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.anvil_steel, 1), new ComparableStack(anvil), new OreDictStack("ingotSteel", 10)));
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
		
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack("ingotIron"), new AnvilOutput(new ItemStack(ModItems.plate_iron))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack("ingotGold"), new AnvilOutput(new ItemStack(ModItems.plate_gold))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack("ingotTitanium"), new AnvilOutput(new ItemStack(ModItems.plate_titanium))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack("ingotAluminum"), new AnvilOutput(new ItemStack(ModItems.plate_aluminium))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack("ingotSteel"), new AnvilOutput(new ItemStack(ModItems.plate_steel))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack("ingotLead"), new AnvilOutput(new ItemStack(ModItems.plate_lead))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack("ingotCopper"), new AnvilOutput(new ItemStack(ModItems.plate_copper))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack("ingotAdvancedAlloy"), new AnvilOutput(new ItemStack(ModItems.plate_advanced_alloy))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack("ingotSchrabidium"), new AnvilOutput(new ItemStack(ModItems.plate_schrabidium))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack("ingotCMBSteel"), new AnvilOutput(new ItemStack(ModItems.plate_combine_steel))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack("ingotSaturnite"), new AnvilOutput(new ItemStack(ModItems.plate_saturnite))).setTier(3));

		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack("ingotAluminum"), new AnvilOutput(new ItemStack(ModItems.wire_aluminium, 8))).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack("ingotCopper"), new AnvilOutput(new ItemStack(ModItems.wire_copper, 8))).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack("ingotTungsten"), new AnvilOutput(new ItemStack(ModItems.wire_tungsten, 8))).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack("ingotRedCopperAlloy"), new AnvilOutput(new ItemStack(ModItems.wire_red_copper, 8))).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack("ingotAdvancedAlloy"), new AnvilOutput(new ItemStack(ModItems.wire_advanced_alloy, 8))).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack("ingotGold"), new AnvilOutput(new ItemStack(ModItems.wire_gold, 8))).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack("ingotSchrabidium"), new AnvilOutput(new ItemStack(ModItems.wire_schrabidium, 8))).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack("ingotMagnetizedTungsten"), new AnvilOutput(new ItemStack(ModItems.wire_magnetized_tungsten, 8))).setTier(4));

		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack("dustCoal"), new AnvilOutput(new ItemStack(Items.coal))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack("dustNetherQuartz"), new AnvilOutput(new ItemStack(Items.quartz))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack("dustLapis"), new AnvilOutput(new ItemStack(Items.dye, 1, 4))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack("dustDiamond"), new AnvilOutput(new ItemStack(Items.diamond))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack("dustEmerald"), new AnvilOutput(new ItemStack(Items.emerald))).setTier(3));

		registerConstructionAmmo();
		registerConstructionRecycling();
	}
	
	public static void registerConstructionRecipes() {

		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack("ingotAluminum", 1), new AnvilOutput(new ItemStack(ModBlocks.deco_aluminium))).setTier(1).setOverlay(OverlayType.CONSTRUCTION));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack("ingotBeryllium", 1), new AnvilOutput(new ItemStack(ModBlocks.deco_beryllium))).setTier(1).setOverlay(OverlayType.CONSTRUCTION));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack("ingotLead", 1), new AnvilOutput(new ItemStack(ModBlocks.deco_lead))).setTier(1).setOverlay(OverlayType.CONSTRUCTION));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack("ingotRedCopperAlloy", 1), new AnvilOutput(new ItemStack(ModBlocks.deco_red_copper))).setTier(1).setOverlay(OverlayType.CONSTRUCTION));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack("ingotSteel", 1), new AnvilOutput(new ItemStack(ModBlocks.deco_steel))).setTier(1).setOverlay(OverlayType.CONSTRUCTION));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack("ingotTitanium", 1), new AnvilOutput(new ItemStack(ModBlocks.deco_titanium))).setTier(1).setOverlay(OverlayType.CONSTRUCTION));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack("ingotTungsten", 1), new AnvilOutput(new ItemStack(ModBlocks.deco_tungsten))).setTier(1).setOverlay(OverlayType.CONSTRUCTION));

		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {new OreDictStack("ingotDineutronium", 4), new ComparableStack(ModBlocks.depth_brick)},
				new AnvilOutput(new ItemStack(ModBlocks.depth_dnt))).setTier(1916169));

		constructionRecipes.add(new AnvilConstructionRecipe(
				new OreDictStack("plateCopper", 4),
				new AnvilOutput(new ItemStack(ModItems.board_copper))).setTier(1));
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
				new AStack[] {new OreDictStack("plateIron", 2), new ComparableStack(ModItems.coil_copper), new ComparableStack(ModItems.coil_copper_torus)},
				new AnvilOutput(new ItemStack(ModItems.motor, 2))).setTier(1));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {new ComparableStack(ModItems.motor), new OreDictStack("ingotPolymer", 2), new OreDictStack("ingotDesh", 2), new ComparableStack(ModItems.coil_gold_torus)},
				new AnvilOutput(new ItemStack(ModItems.motor_desh, 1))).setTier(3));
		
		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {
						new ComparableStack(Blocks.stonebrick, 4),
						new OreDictStack("ingotIron", 2),
						new OreDictStack("ingotTungsten", 4),
						new ComparableStack(ModItems.board_copper, 2)
				},
				new AnvilOutput(new ItemStack(ModBlocks.machine_difurnace_off))).setTier(1));
		
		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {
						new OreDictStack("blockGlassColorless", 4),
						new OreDictStack("ingotSteel", 8),
						new OreDictStack("ingotCopper", 8),
						new ComparableStack(ModItems.motor, 2),
						new ComparableStack(ModItems.circuit_aluminium, 1)
				},
				new AnvilOutput(new ItemStack(ModBlocks.machine_assembler))).setTier(2));
		
		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {
						new ComparableStack(Items.bone, 16),
						new ComparableStack(Items.leather, 4),
						new ComparableStack(Items.feather, 24)
				},
				new AnvilOutput(new ItemStack(ModItems.wings_limp))).setTier(2));

		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {new OreDictStack("ingotDesh", 2), new OreDictStack("dustPolymer", 2), new ComparableStack(ModItems.ingot_dura_steel, 1)},
				new AnvilOutput(new ItemStack(ModItems.plate_desh, 4))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {new OreDictStack("ingotEuphemium", 2), new ComparableStack(ModItems.powder_astatine, 2), new ComparableStack(Items.nether_star, 1)},
				new AnvilOutput(new ItemStack(ModItems.plate_euphemium, 4))).setTier(6));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {new OreDictStack("ingotDineutronium", 2), new ComparableStack(ModItems.powder_spark_mix, 2), new OreDictStack("ingotDesh", 1)},
				new AnvilOutput(new ItemStack(ModItems.plate_dineutronium, 4))).setTier(7));

		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {new OreDictStack("plateTitanium", 4), new OreDictStack("ingotSteel", 1), new ComparableStack(ModItems.bolt_tungsten, 2)},
				new AnvilOutput(new ItemStack(ModItems.plate_armor_titanium))).setTier(2));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {new OreDictStack("plateIron", 4), new ComparableStack(ModItems.plate_saturnite, 4), new ComparableStack(ModItems.plate_armor_titanium, 1)},
				new AnvilOutput(new ItemStack(ModItems.plate_armor_ajr))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {new ComparableStack(ModItems.plate_advanced_alloy, 4), new ComparableStack(ModItems.plate_armor_titanium, 1), new ComparableStack(ModItems.wire_tungsten, 6)},
				new AnvilOutput(new ItemStack(ModItems.plate_armor_hev))).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {new OreDictStack("plateDenseLead", 4), new ComparableStack(ModItems.ingot_starmetal, 1), new ComparableStack(ModItems.wire_magnetized_tungsten, 6)},
				new AnvilOutput(new ItemStack(ModItems.plate_armor_lunar))).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {new ComparableStack(ModItems.ingot_meteorite_forged, 4), new OreDictStack("ingotDesh", 1), new ComparableStack(ModItems.billet_yharonite, 1)},
				new AnvilOutput(new ItemStack(ModItems.plate_armor_fau))).setTier(6));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {new ComparableStack(ModItems.plate_dineutronium, 4), new ComparableStack(ModItems.particle_sparkticle, 1), new ComparableStack(ModItems.plate_armor_fau, 1)},
				new AnvilOutput(new ItemStack(ModItems.plate_armor_dnt))).setTier(7));
		
	}
	
	public static void registerConstructionAmmo() {
		
		Object[][] recs = new Object[][] {
			new Object[] {ModItems.ammo_12gauge, ModItems.powder_fire, ModItems.ammo_12gauge_incendiary, 20, 2},
			new Object[] {ModItems.ammo_12gauge, Item.getItemFromBlock(ModBlocks.gravel_obsidian), ModItems.ammo_12gauge_shrapnel, 20, 2},
			new Object[] {ModItems.ammo_12gauge, ModItems.ingot_u238, ModItems.ammo_12gauge_du, 20, 3},
			new Object[] {ModItems.ammo_12gauge, ModItems.coin_maskman, ModItems.ammo_12gauge_sleek, 100, 4},
			
			new Object[] {ModItems.ammo_20gauge, ModItems.powder_fire, ModItems.ammo_20gauge_incendiary, 20, 2},
			new Object[] {ModItems.ammo_20gauge, Item.getItemFromBlock(ModBlocks.gravel_obsidian), ModItems.ammo_20gauge_shrapnel, 20, 2},
			new Object[] {ModItems.ammo_20gauge, ModItems.powder_poison, ModItems.ammo_20gauge_caustic, 20, 2},
			new Object[] {ModItems.ammo_20gauge, "dustDiamond", ModItems.ammo_20gauge_shock, 20, 2},
			new Object[] {ModItems.ammo_20gauge, Item.getItemFromBlock(Blocks.soul_sand), ModItems.ammo_20gauge_wither, 10, 3},
			new Object[] {ModItems.ammo_20gauge, ModItems.coin_maskman, ModItems.ammo_20gauge_sleek, 100, 4},

			new Object[] {ModItems.ammo_4gauge_flechette, ModItems.ingot_phosphorus, ModItems.ammo_4gauge_flechette_phosphorus, 20, 2},
			new Object[] {ModItems.ammo_4gauge_explosive, ModItems.egg_balefire_shard, ModItems.ammo_4gauge_balefire, 10, 4},
			new Object[] {ModItems.ammo_4gauge_explosive, ModItems.ammo_rocket, ModItems.ammo_4gauge_kampf, 4, 2},
			new Object[] {ModItems.ammo_4gauge_kampf, ModItems.pellet_canister, ModItems.ammo_4gauge_canister, 10, 3},
			new Object[] {ModItems.ammo_4gauge, ModItems.pellet_claws, ModItems.ammo_4gauge_claw, 4, 5},
			new Object[] {ModItems.ammo_4gauge, ModItems.toothpicks, ModItems.ammo_4gauge_vampire, 4, 5},
			new Object[] {ModItems.ammo_4gauge, ModItems.pellet_charged, ModItems.ammo_4gauge_void, 1, 5},
			new Object[] {ModItems.ammo_4gauge, ModItems.coin_maskman, ModItems.ammo_4gauge_sleek, 100, 4},

			new Object[] {ModItems.ammo_44, ModItems.ingot_dura_steel, ModItems.ammo_44_ap, 20, 2},
			new Object[] {ModItems.ammo_44, ModItems.ingot_u238, ModItems.ammo_44_du, 20, 2},
			new Object[] {ModItems.ammo_44, ModItems.ingot_phosphorus, ModItems.ammo_44_phosphorus, 20, 2},
			new Object[] {ModItems.ammo_44_du, ModItems.ingot_starmetal, ModItems.ammo_44_star, 10, 3},
			new Object[] {ModItems.ammo_44, ModItems.pellet_chlorophyte, ModItems.ammo_44_chlorophyte, 10, 3},

			new Object[] {ModItems.ammo_5mm, ModItems.ingot_semtex, ModItems.ammo_5mm_explosive, 20, 2},
			new Object[] {ModItems.ammo_5mm, ModItems.ingot_u238, ModItems.ammo_5mm_du, 20, 2},
			new Object[] {ModItems.ammo_5mm, ModItems.ingot_starmetal, ModItems.ammo_5mm_star, 10, 3},
			new Object[] {ModItems.ammo_5mm, ModItems.pellet_chlorophyte, ModItems.ammo_5mm_chlorophyte, 10, 3},

			new Object[] {ModItems.ammo_9mm, ModItems.ingot_dura_steel, ModItems.ammo_9mm_ap, 20, 2},
			new Object[] {ModItems.ammo_9mm, ModItems.ingot_u238, ModItems.ammo_9mm_du, 20, 2},
			new Object[] {ModItems.ammo_9mm, ModItems.pellet_chlorophyte, ModItems.ammo_9mm_chlorophyte, 10, 3},
			
			new Object[] {ModItems.ammo_22lr, ModItems.ingot_dura_steel, ModItems.ammo_22lr_ap, 20, 2},
			new Object[] {ModItems.ammo_22lr, ModItems.pellet_chlorophyte, ModItems.ammo_22lr_chlorophyte, 10, 3},

			new Object[] {ModItems.ammo_50bmg, ModItems.powder_fire, ModItems.ammo_50bmg_incendiary, 20, 2},
			new Object[] {ModItems.ammo_50bmg, ModItems.ingot_phosphorus, ModItems.ammo_50bmg_phosphorus, 20, 2},
			new Object[] {ModItems.ammo_50bmg, ModItems.ingot_semtex, ModItems.ammo_50bmg_explosive, 20, 2},
			new Object[] {ModItems.ammo_50bmg, ModItems.ingot_dura_steel, ModItems.ammo_50bmg_ap, 20, 2},
			new Object[] {ModItems.ammo_50bmg, ModItems.ingot_u238, ModItems.ammo_50bmg_du, 20, 2},
			new Object[] {ModItems.ammo_50bmg_du, ModItems.ingot_starmetal, ModItems.ammo_50bmg_star, 10, 3},
			new Object[] {ModItems.ammo_50bmg, ModItems.pellet_chlorophyte, ModItems.ammo_50bmg_chlorophyte, 10, 3},
			new Object[] {ModItems.ammo_50bmg, ModItems.coin_maskman, ModItems.ammo_50bmg_sleek, 100, 4},
			new Object[] {ModItems.ammo_50bmg_flechette, ModItems.nugget_am_mix, ModItems.ammo_50bmg_flechette_am, 10, 3},
			new Object[] {ModItems.ammo_50bmg_flechette, ModItems.powder_polonium, ModItems.ammo_50bmg_flechette_po, 20, 3},

			new Object[] {ModItems.ammo_50ae, ModItems.ingot_dura_steel, ModItems.ammo_50ae_ap, 20, 2},
			new Object[] {ModItems.ammo_50ae, ModItems.ingot_u238, ModItems.ammo_50ae_du, 20, 2},
			new Object[] {ModItems.ammo_50ae_du, ModItems.ingot_starmetal, ModItems.ammo_50ae_star, 10, 3},
			new Object[] {ModItems.ammo_50ae, ModItems.pellet_chlorophyte, ModItems.ammo_50ae_chlorophyte, 10, 3},

			new Object[] {ModItems.ammo_556, ModItems.ingot_phosphorus, ModItems.ammo_556_phosphorus, 20, 2},
			new Object[] {ModItems.ammo_556, ModItems.ingot_dura_steel, ModItems.ammo_556_ap, 20, 2},
			new Object[] {ModItems.ammo_556, ModItems.ingot_u238, ModItems.ammo_556_du, 20, 2},
			new Object[] {ModItems.ammo_556_du, ModItems.ingot_starmetal, ModItems.ammo_556_star, 10, 3},
			new Object[] {ModItems.ammo_556, ModItems.pellet_chlorophyte, ModItems.ammo_556_chlorophyte, 10, 3},
			new Object[] {ModItems.ammo_556, ModItems.coin_maskman, ModItems.ammo_556_sleek, 100, 4},
			new Object[] {ModItems.ammo_556, Items.redstone, ModItems.ammo_556_tracer, 20, 2},
			new Object[] {ModItems.ammo_556, ModItems.pellet_flechette, ModItems.ammo_556_flechette, 20, 2},
			new Object[] {ModItems.ammo_556_flechette, ModItems.powder_fire, ModItems.ammo_556_flechette_incendiary, 20, 2},
			new Object[] {ModItems.ammo_556_flechette, ModItems.ingot_phosphorus, ModItems.ammo_556_flechette_phosphorus, 20, 2},
			new Object[] {ModItems.ammo_556_flechette, ModItems.ingot_u238, ModItems.ammo_556_flechette_du, 20, 2},
			new Object[] {ModItems.ammo_556_flechette, ModItems.coin_maskman, ModItems.ammo_556_flechette_sleek, 100, 4},
			new Object[] {ModItems.ammo_556_flechette, ModItems.pellet_chlorophyte, ModItems.ammo_556_flechette_chlorophyte, 10, 3},
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
						new AnvilOutput(new ItemStack(GeneralConfig.enable528 ? ModItems.circuit_bismuth : ModItems.ingot_asbestos, 2)),
						new AnvilOutput(new ItemStack(ModItems.ingot_bismuth, 1))
				}
		).setTier(4));
		constructionRecipes.add(new AnvilConstructionRecipe(
				new ComparableStack(ModItems.circuit_bismuth),
				new AnvilOutput[] {
						new AnvilOutput(new ItemStack(Items.redstone, 2)),
						new AnvilOutput(new ItemStack(ModItems.ingot_polymer, 1)),
						new AnvilOutput(new ItemStack(ModItems.ingot_polymer, 1), 0.5F),
						new AnvilOutput(new ItemStack(GeneralConfig.enable528 ? ModItems.circuit_bismuth : ModItems.ingot_asbestos, 1)),
						new AnvilOutput(new ItemStack(ModItems.ingot_bismuth, 1), 0.75F)
				}
		).setTier(4));
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
			return this;
		}
		
		public AnvilConstructionRecipe setTierRange(int lower, int upper) {
			this.tierLower = lower;
			this.tierUpper = upper;
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

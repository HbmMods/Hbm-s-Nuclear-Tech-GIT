package com.hbm.main;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockNTMFlower.EnumFlowerType;
import com.hbm.config.GeneralConfig;
import com.hbm.crafting.*;
import com.hbm.crafting.handlers.*;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.fluid.Fluids;

import static com.hbm.inventory.OreDictManager.*;

import com.hbm.items.ModItems;
import com.hbm.items.ItemEnums.EnumLegendaryType;
import com.hbm.items.ItemEnums.EnumPlantType;
import com.hbm.items.ItemGenericPart.EnumPartType;
import com.hbm.items.machine.ItemBattery;
import com.hbm.items.special.ItemCircuitStarComponent.CircuitComponentType;
import com.hbm.items.special.ItemHolotapeImage.EnumHoloImage;
import com.hbm.items.special.ItemPlasticScrap.ScrapType;
import com.hbm.items.tool.ItemGuideBook.BookType;
import com.hbm.util.EnchantmentUtil;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

public class CraftingManager {
	
	public static void mainRegistry() {
		
		AddCraftingRec();
		SmeltingRecipes.AddSmeltingRec();
		
		MineralRecipes.register();
		RodRecipes.register();
		ToolRecipes.register();
		ArmorRecipes.register();
		WeaponRecipes.register();
		ConsumableRecipes.register();
		PowderRecipes.register();
		
		GameRegistry.addRecipe(new RBMKFuelCraftingHandler());
		GameRegistry.addRecipe(new MKUCraftingHandler());
		GameRegistry.addRecipe(new ToolboxCraftingHandler());
		
		//TODO: find out what this actually did
		RecipeSorter.register("hbm:rbmk", RBMKFuelCraftingHandler.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
		RecipeSorter.register("hbm:toolbox", ToolboxCraftingHandler.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
		RecipeSorter.register("hbm:mku", MKUCraftingHandler.class, RecipeSorter.Category.SHAPED, "after:minecraft:shaped before:minecraft:shapeless");
	}

	public static void AddCraftingRec() {
		
		addRecipeAuto(new ItemStack(ModItems.redstone_sword, 1), new Object[] { "R", "R", "S", 'R', REDSTONE.block(), 'S', KEY_STICK });
		addRecipeAuto(new ItemStack(ModItems.big_sword, 1), new Object[] { "QIQ", "QIQ", "GSG", 'G', Items.gold_ingot, 'S', KEY_STICK, 'I', Items.iron_ingot, 'Q', Items.quartz});
		
		addRecipeAuto(new ItemStack(ModItems.board_copper, 1), new Object[] { "TTT", "TTT", 'T', CU.plate() });
		addRecipeAuto(new ItemStack(ModItems.rubber_gloves, 1), new Object[] { "   ", "RRR", "RAR", 'R', ModItems.plate_polymer, 'A', ASBESTOS.ingot() });
		addRecipeAuto(new ItemStack(ModItems.hazmat_cloth_red, 1), new Object[] { "C", "R", "C", 'C', ModItems.hazmat_cloth, 'R', REDSTONE.dust() });
		addRecipeAuto(new ItemStack(ModItems.hazmat_cloth_grey, 1), new Object[] { " P ", "ICI", " L ", 'C', ModItems.hazmat_cloth_red, 'P', IRON.plate(), 'L', PB.plate(), 'I', ModItems.plate_polymer });
		addRecipeAuto(new ItemStack(ModItems.asbestos_cloth, 8), new Object[] { "SCS", "CPC", "SCS", 'S', Items.string, 'P', BR.dust(), 'C', Blocks.wool });
		addRecipeAuto(new ItemStack(ModItems.bolt_dura_steel, 4), new Object[] { "D", "D", 'D', DURA.ingot()});
		addRecipeAuto(new ItemStack(ModItems.pipes_steel, 1), new Object[] { "B", "B", "B", 'B', STEEL.block() });
		addRecipeAuto(new ItemStack(ModItems.bolt_tungsten, 4), new Object[] { "D", "D", 'D', W.ingot() });
		addRecipeAuto(new ItemStack(ModItems.bolt_compound, 1), new Object[] { "PDP", "PTP", "PDP", 'D', ModItems.bolt_dura_steel, 'T', ModItems.bolt_tungsten, 'P', TI.plate() });
		addRecipeAuto(new ItemStack(ModItems.pellet_coal, 1), new Object[] { "PFP", "FOF", "PFP", 'P', COAL.dust(), 'F', Items.flint, 'O', ModBlocks.gravel_obsidian });
		addRecipeAuto(new ItemStack(ModItems.plate_polymer, 8), new Object[] { "DD", 'D', ANY_PLASTIC.ingot() });
		addRecipeAuto(new ItemStack(ModItems.plate_polymer, 16), new Object[] { "DD", 'D', FIBER.ingot()});
		addRecipeAuto(new ItemStack(ModItems.plate_polymer, 16), new Object[] { "DD", 'D', ASBESTOS.ingot()});
		addRecipeAuto(new ItemStack(ModItems.plate_polymer, 4), new Object[] { "SWS", 'S', Items.string, 'W', Blocks.wool });
		addRecipeAuto(new ItemStack(ModItems.plate_polymer, 4), new Object[] { "BB", 'B', "ingotBrick" });
		addRecipeAuto(new ItemStack(ModItems.plate_polymer, 4), new Object[] { "BB", 'B', "ingotNetherBrick" });

		addRecipeAuto(new ItemStack(ModBlocks.marker_structure, 1), new Object[] { "L", "G", "R", 'L', LAPIS.dust(), 'G', Items.glowstone_dust, 'R', Blocks.redstone_torch });

		addRecipeAuto(new ItemStack(ModItems.circuit_raw, 1), new Object[] { "A", "R", "S", 'S', STEEL.plate(), 'R', REDSTONE.dust(), 'A', ModItems.wire_aluminium });
		addRecipeAuto(new ItemStack(ModItems.circuit_bismuth_raw, 1), new Object[] { "RPR", "ABA", "RPR", 'R', REDSTONE.dust(), 'P', ANY_PLASTIC.ingot(), 'A', (GeneralConfig.enable528 ? ModItems.circuit_tantalium : ASBESTOS.ingot()), 'B', ModItems.ingot_bismuth });
		addRecipeAuto(new ItemStack(ModItems.circuit_tantalium_raw, 1), new Object[] { "RWR", "PTP", "RWR", 'R', REDSTONE.dust(), 'W', ModItems.wire_gold, 'P', CU.plate(), 'T', TA.nugget() });
		addRecipeAuto(new ItemStack(ModItems.circuit_targeting_tier1, 1), new Object[] { "CPC", 'C', ModItems.circuit_aluminium, 'P', REDSTONE.dust() });
		addRecipeAuto(new ItemStack(ModItems.circuit_targeting_tier2, 1), new Object[] { "CPC", 'C', ModItems.circuit_copper, 'P', NETHERQUARTZ.dust() });
		addRecipeAuto(new ItemStack(ModItems.circuit_targeting_tier3, 1), new Object[] { "CPC", 'C', ModItems.circuit_red_copper, 'P', GOLD.dust() });
		addRecipeAuto(new ItemStack(ModItems.circuit_targeting_tier4, 1), new Object[] { "CPC", 'C', ModItems.circuit_gold, 'P', LAPIS.dust() });
		addRecipeAuto(new ItemStack(ModItems.circuit_targeting_tier5, 1), new Object[] { "CPC", 'C', ModItems.circuit_schrabidium, 'P', DIAMOND.dust() });
		addRecipeAuto(new ItemStack(ModItems.circuit_targeting_tier6, 1), new Object[] { "P", "D", "C", 'C', ModItems.circuit_targeting_tier5, 'D', ModItems.battery_potatos, 'P', ModItems.powder_spark_mix });
		addShapelessAuto(new ItemStack(ModItems.circuit_aluminium, 2), new Object[] { ModItems.circuit_targeting_tier1 });
		addShapelessAuto(new ItemStack(ModItems.circuit_copper, 2), new Object[] { ModItems.circuit_targeting_tier2 });
		addShapelessAuto(new ItemStack(ModItems.circuit_red_copper, 2), new Object[] { ModItems.circuit_targeting_tier3 });
		addShapelessAuto(new ItemStack(ModItems.circuit_gold, 2), new Object[] { ModItems.circuit_targeting_tier4 });
		addShapelessAuto(new ItemStack(ModItems.circuit_schrabidium, 2), new Object[] { ModItems.circuit_targeting_tier5 });

		addRecipeAuto(new ItemStack(ModItems.cell_empty, 6), new Object[] { " S ", "G G", " S ", 'S', STEEL.plate(), 'G', KEY_ANYPANE });
		addRecipeAuto(new ItemStack(ModItems.cell_deuterium, 8), new Object[] { "DDD", "DTD", "DDD", 'D', ModItems.cell_empty, 'T', ModItems.mike_deut });
		addRecipeAuto(new ItemStack(ModItems.particle_empty, 2), new Object[] { "STS", "G G", "STS", 'S', STEEL.plate(), 'T', W.ingot(), 'G', KEY_ANYPANE });
		addShapelessAuto(new ItemStack(ModItems.particle_copper, 1), new Object[] { ModItems.particle_empty, CU.dust(), ModItems.pellet_charged });
		addShapelessAuto(new ItemStack(ModItems.particle_lead, 1), new Object[] { ModItems.particle_empty, PB.dust(), ModItems.pellet_charged });
		addShapelessAuto(new ItemStack(ModItems.cell_antimatter, 1), new Object[] { ModItems.particle_aproton, ModItems.particle_aelectron, ModItems.cell_empty });
		addShapelessAuto(new ItemStack(ModItems.particle_amat, 1), new Object[] { ModItems.particle_aproton, ModItems.particle_aelectron, ModItems.particle_empty });

		addRecipeAuto(new ItemStack(ModItems.canister_empty, 2), new Object[] { "S ", "AA", "AA", 'S', STEEL.plate(), 'A', AL.plate() });
		addRecipeAuto(new ItemStack(ModItems.gas_empty, 2), new Object[] { "S ", "AA", "AA", 'A', STEEL.plate(), 'S', CU.plate() });
		addShapelessAuto(new ItemStack(ModBlocks.block_waste_painted, 1), new Object[] { KEY_YELLOW, ModBlocks.block_waste });


		addRecipeAuto(new ItemStack(ModItems.ingot_aluminium, 1), new Object[] { "###", "###", "###", '#', ModItems.wire_aluminium });
		addRecipeAuto(new ItemStack(ModItems.ingot_copper, 1), new Object[] { "###", "###", "###", '#', ModItems.wire_copper });
		addRecipeAuto(new ItemStack(ModItems.ingot_tungsten, 1), new Object[] { "###", "###", "###", '#', ModItems.wire_tungsten });
		addRecipeAuto(new ItemStack(ModItems.ingot_red_copper, 1), new Object[] { "###", "###", "###", '#', ModItems.wire_red_copper });
		addRecipeAuto(new ItemStack(ModItems.ingot_advanced_alloy, 1), new Object[] { "###", "###", "###", '#', ModItems.wire_advanced_alloy });
		addRecipeAuto(new ItemStack(Items.gold_ingot, 1), new Object[] { "###", "###", "###", '#', ModItems.wire_gold });
		addRecipeAuto(new ItemStack(ModItems.ingot_schrabidium, 1), new Object[] { "###", "###", "###", '#', ModItems.wire_schrabidium });
		addRecipeAuto(new ItemStack(ModItems.ingot_magnetized_tungsten, 1), new Object[] { "###", "###", "###", '#', ModItems.wire_magnetized_tungsten });

		addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { Items.melon, Items.melon, Items.melon, Items.melon, Items.melon, Items.melon, Items.melon });
		addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { Items.apple, Items.apple, Items.apple, Items.apple, Items.apple, Items.apple, Items.apple, Items.apple, Items.apple });
		addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { Items.reeds, Items.reeds, Items.reeds, Items.reeds, Items.reeds, Items.reeds, Items.reeds, Items.reeds, Items.reeds });
		addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { Items.rotten_flesh, Items.rotten_flesh, Items.rotten_flesh, Items.rotten_flesh, Items.rotten_flesh, Items.rotten_flesh, Items.rotten_flesh });
		addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { Items.carrot, Items.carrot, Items.carrot, Items.carrot, Items.carrot, Items.carrot, Items.carrot, Items.carrot, Items.carrot });
		addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { Items.potato, Items.potato, Items.potato, Items.potato, Items.potato, Items.potato, Items.potato, Items.potato, Items.potato });
		addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { Items.poisonous_potato, Items.poisonous_potato, Items.poisonous_potato, Items.poisonous_potato, Items.poisonous_potato, Items.poisonous_potato, Items.poisonous_potato, Items.poisonous_potato, Items.poisonous_potato });
		addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { Items.potato, Items.potato, Items.potato, Items.potato, Items.potato, Items.potato, Items.potato, Items.potato, Items.potato });
		addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { KEY_SAPLING, KEY_SAPLING, KEY_SAPLING, KEY_SAPLING, KEY_SAPLING, KEY_SAPLING, KEY_SAPLING });
		addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { KEY_LEAVES, KEY_LEAVES, KEY_LEAVES, KEY_LEAVES, KEY_LEAVES });
		addShapelessAuto(new ItemStack(ModItems.biomass, 8), new Object[] { Blocks.pumpkin, Blocks.pumpkin, Blocks.pumpkin, Blocks.pumpkin, Blocks.pumpkin, Blocks.pumpkin });
		addShapelessAuto(new ItemStack(ModItems.biomass, 6), new Object[] { KEY_LOG, KEY_LOG, KEY_LOG });
		addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { KEY_PLANKS, KEY_PLANKS, KEY_PLANKS, KEY_PLANKS, KEY_PLANKS, KEY_PLANKS, KEY_PLANKS, KEY_PLANKS, KEY_PLANKS });
		addShapelessAuto(new ItemStack(ModItems.biomass, 8), new Object[] { Blocks.hay_block, Blocks.hay_block });
		addShapelessAuto(new ItemStack(ModItems.biomass, 1), new Object[] { Items.wheat_seeds, Items.wheat_seeds, Items.wheat_seeds, Items.wheat_seeds, Items.wheat_seeds, Items.wheat_seeds, Items.wheat_seeds, Items.wheat_seeds, Items.wheat_seeds });
		addShapelessAuto(new ItemStack(ModItems.biomass, 2), new Object[] { Items.pumpkin_seeds, Items.pumpkin_seeds, Items.pumpkin_seeds, Items.pumpkin_seeds, Items.pumpkin_seeds, Items.pumpkin_seeds, Items.pumpkin_seeds, Items.pumpkin_seeds, Items.pumpkin_seeds });
		addShapelessAuto(new ItemStack(ModItems.biomass, 2), new Object[] { Items.melon_seeds, Items.melon_seeds, Items.melon_seeds, Items.melon_seeds, Items.melon_seeds, Items.melon_seeds, Items.melon_seeds, Items.melon_seeds, Items.melon_seeds });
		addShapelessAuto(new ItemStack(ModItems.biomass, 3), new Object[] { Items.rotten_flesh, Items.rotten_flesh, Items.rotten_flesh, Items.rotten_flesh });

		//addRecipeAuto(new ItemStack(ModItems.part_lithium), new Object[] { "P", "D", "P", 'P', STEEL.plate(), 'D', LI.dust() });
		//addRecipeAuto(new ItemStack(ModItems.part_beryllium), new Object[] { "P", "D", "P", 'P', STEEL.plate(), 'D', BE.dust() });
		//addRecipeAuto(new ItemStack(ModItems.part_carbon), new Object[] { "P", "D", "P", 'P', STEEL.plate(), 'D', COAL.dust() });
		//addRecipeAuto(new ItemStack(ModItems.part_copper), new Object[] { "P", "D", "P", 'P', STEEL.plate(), 'D', CU.dust() });
		//addRecipeAuto(new ItemStack(ModItems.part_plutonium), new Object[] { "P", "D", "P", 'P', STEEL.plate(), 'D', "dustPlutonium" });

		//addRecipeAuto(new ItemStack(ModItems.pellet_rtg, 1), new Object[] { "IPI", "PPP", "IPI", 'I', IRON.plate(), 'P', "tinyPu238" });
		//addRecipeAuto(new ItemStack(ModItems.pellet_rtg_weak, 1), new Object[] { "IUI", "UPU", "IUI", 'I', IRON.plate(), 'P', "tinyPu238", 'U', "tinyU238" });
		//addRecipeAuto(new ItemStack(ModItems.tritium_deuterium_cake, 1), new Object[] { "DLD", "LTL", "DLD", 'L', "ingotLithium", 'D', ModItems.cell_deuterium, 'T', ModItems.cell_tritium });

		//addRecipeAuto(new ItemStack(ModItems.pellet_schrabidium, 1), new Object[] { "IPI", "PPP", "IPI", 'I', IRON.plate(), 'P', SA326.ingot() });
		//addRecipeAuto(new ItemStack(ModItems.pellet_hes, 1), new Object[] { "IPI", "PPP", "IPI", 'I', IRON.plate(), 'P', ModItems.ingot_hes });
		//addRecipeAuto(new ItemStack(ModItems.pellet_mes, 1), new Object[] { "IPI", "PPP", "IPI", 'I', IRON.plate(), 'P', ModItems.ingot_schrabidium_fuel });
		//addRecipeAuto(new ItemStack(ModItems.pellet_les, 1), new Object[] { "IPI", "PPP", "IPI", 'I', IRON.plate(), 'P', ModItems.ingot_les });
		//addRecipeAuto(new ItemStack(ModItems.pellet_beryllium, 1), new Object[] { "IPI", "PPP", "IPI", 'I', IRON.plate(), 'P', BE.ingot() });
		//addRecipeAuto(new ItemStack(ModItems.pellet_neptunium, 1), new Object[] { "IPI", "PPP", "IPI", 'I', IRON.plate(), 'P', "ingotNeptunium" });
		//addRecipeAuto(new ItemStack(ModItems.pellet_lead, 1), new Object[] { "IPI", "PPP", "IPI", 'I', IRON.plate(), 'P', PB.ingot() });
		//addRecipeAuto(new ItemStack(ModItems.pellet_advanced, 1), new Object[] { "IPI", "PPP", "IPI", 'I', IRON.plate(), 'P', ModItems.ingot_advanced_alloy });

		addRecipeAuto(new ItemStack(ModItems.coil_copper, 1), new Object[] { "WWW", "WIW", "WWW", 'W', ModItems.wire_red_copper, 'I', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModItems.coil_advanced_alloy, 1), new Object[] { "WWW", "WIW", "WWW", 'W', ModItems.wire_advanced_alloy, 'I', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModItems.coil_gold, 1), new Object[] { "WWW", "WIW", "WWW", 'W', ModItems.wire_gold, 'I', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModItems.coil_copper_torus, 2), new Object[] { " C ", "CPC", " C ", 'P', IRON.plate(), 'C', ModItems.coil_copper });
		addRecipeAuto(new ItemStack(ModItems.coil_advanced_torus, 2), new Object[] { " C ", "CPC", " C ", 'P', IRON.plate(), 'C', ModItems.coil_advanced_alloy });
		addRecipeAuto(new ItemStack(ModItems.coil_gold_torus, 2), new Object[] { " C ", "CPC", " C ", 'P', IRON.plate(), 'C', ModItems.coil_gold });
		addRecipeAuto(new ItemStack(ModItems.coil_copper_torus, 2), new Object[] { " C ", "CPC", " C ", 'P', STEEL.plate(), 'C', ModItems.coil_copper });
		addRecipeAuto(new ItemStack(ModItems.coil_advanced_torus, 2), new Object[] { " C ", "CPC", " C ", 'P', STEEL.plate(), 'C', ModItems.coil_advanced_alloy });
		addRecipeAuto(new ItemStack(ModItems.coil_gold_torus, 2), new Object[] { " C ", "CPC", " C ", 'P', STEEL.plate(), 'C', ModItems.coil_gold });
		addRecipeAuto(new ItemStack(ModItems.coil_tungsten, 1), new Object[] { "WWW", "WIW", "WWW", 'W', ModItems.wire_tungsten, 'I', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModItems.coil_magnetized_tungsten, 1), new Object[] { "WWW", "WIW", "WWW", 'W', ModItems.wire_magnetized_tungsten, 'I', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModItems.tank_steel, 2), new Object[] { "STS", "S S", "STS", 'S', STEEL.plate(), 'T', TI.plate() });
		addRecipeAuto(new ItemStack(ModItems.motor, 2), new Object[] { " R ", "ICI", "ITI", 'R', ModItems.wire_red_copper, 'T', ModItems.coil_copper_torus, 'I', IRON.plate(), 'C', ModItems.coil_copper });
		addRecipeAuto(new ItemStack(ModItems.motor, 2), new Object[] { " R ", "ICI", " T ", 'R', ModItems.wire_red_copper, 'T', ModItems.coil_copper_torus, 'I', STEEL.plate(), 'C', ModItems.coil_copper });
		addRecipeAuto(new ItemStack(ModItems.motor_desh, 1), new Object[] { "PCP", "DMD", "PCP", 'P', ANY_PLASTIC.ingot(), 'C', ModItems.coil_gold_torus, 'D', DESH.ingot(), 'M', ModItems.motor });
		//addRecipeAuto(new ItemStack(ModItems.centrifuge_element, 1), new Object[] { " T ", "WTW", "RMR", 'R', ModItems.wire_red_copper, 'T', ModItems.tank_steel, 'M', ModItems.motor, 'W', ModItems.coil_tungsten });
		//addRecipeAuto(new ItemStack(ModItems.centrifuge_tower, 1), new Object[] { "LL", "EE", "EE", 'E', ModItems.centrifuge_element, 'L', KEY_BLUE });
		//addRecipeAuto(new ItemStack(ModItems.reactor_core, 1), new Object[] { "LNL", "N N", "LNL", 'N', getReflector(), 'L', PB.plate() });
		//addRecipeAuto(new ItemStack(ModItems.rtg_unit, 1), new Object[] { "TIT", "PCP", "TIT", 'T', ModItems.thermo_element, 'I', PB.ingot(), 'P', ModItems.board_copper, 'C', ModItems.circuit_copper });
		//addRecipeAuto(new ItemStack(ModItems.thermo_unit_empty, 1), new Object[] { "TTT", " S ", "P P", 'S', STEEL.ingot(), 'P', TI.plate(), 'T', ModItems.coil_copper_torus });
		//addRecipeAuto(new ItemStack(ModItems.levitation_unit, 1), new Object[] { "CSC", "TAT", "PSP", 'C', ModItems.coil_copper, 'S', ModItems.nugget_schrabidium, 'T', ModItems.coil_tungsten, 'P', TI.plate(), 'A', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.deuterium_filter, 1), new Object[] { "TST", "SCS", "TST", 'T', TCALLOY.ingot(), 'S', S.dust(), 'C', ModItems.catalyst_clay });

		addRecipeAuto(new ItemStack(ModItems.cap_aluminium, 1), new Object[] { "PIP", 'P', AL.plate(), 'I', AL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.hull_small_steel, 3), new Object[] { "PPP", "   ", "PPP", 'P', STEEL.plate(), 'I', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.hull_small_aluminium, 3), new Object[] { "PPP", "   ", "PPP", 'P', AL.plate(), 'I', AL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.hull_big_steel, 1), new Object[] { "III", "   ", "III", 'P', STEEL.plate(), 'I', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.hull_big_aluminium, 1), new Object[] { "III", "   ", "III", 'P', AL.plate(), 'I', AL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.hull_big_titanium, 1), new Object[] { "III", "   ", "III", 'P', TI.plate(), 'I', TI.ingot() });
		addRecipeAuto(new ItemStack(ModItems.fins_flat, 1), new Object[] { "IP", "PP", "IP", 'P', STEEL.plate(), 'I', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.fins_small_steel, 1), new Object[] { " PP", "PII", " PP", 'P', STEEL.plate(), 'I', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.fins_big_steel, 1), new Object[] { " PI", "III", " PI", 'P', STEEL.plate(), 'I', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.fins_tri_steel, 1), new Object[] { " PI", "IIB", " PI", 'P', STEEL.plate(), 'I', STEEL.ingot(), 'B', STEEL.block() });
		addRecipeAuto(new ItemStack(ModItems.fins_quad_titanium, 1), new Object[] { " PP", "III", " PP", 'P', TI.plate(), 'I', TI.ingot() });
		addRecipeAuto(new ItemStack(ModItems.sphere_steel, 1), new Object[] { "PIP", "I I", "PIP", 'P', STEEL.plate(), 'I', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.pedestal_steel, 1), new Object[] { "P P", "P P", "III", 'P', STEEL.plate(), 'I', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.lemon, 1), new Object[] { " D ", "DSD", " D ", 'D', KEY_YELLOW, 'S', "stone" });
		addRecipeAuto(new ItemStack(ModItems.blade_titanium, 4), new Object[] { "TP", "TP", "TT", 'P', TI.plate(), 'T', TI.ingot() });
		addRecipeAuto(new ItemStack(ModItems.turbine_titanium, 1), new Object[] { "BBB", "BSB", "BBB", 'B', ModItems.blade_titanium, 'S', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.rotor_steel, 3), new Object[] { "CCC", "SSS", "CCC", 'C', ModItems.coil_gold, 'S', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.generator_steel, 1), new Object[] { "RRR", "CCC", "SSS", 'C', ModItems.coil_gold_torus, 'S', STEEL.ingot(), 'R', ModItems.rotor_steel });
		addRecipeAuto(new ItemStack(ModItems.shimmer_head, 1), new Object[] { "SSS", "DTD", "SSS", 'S', STEEL.ingot(), 'D', DESH.block(), 'T', W.block() });
		addRecipeAuto(new ItemStack(ModItems.shimmer_axe_head, 1), new Object[] { "PII", "PBB", "PII", 'P', STEEL.plate(), 'B', DESH.block(), 'I', W.ingot() });
		addRecipeAuto(new ItemStack(ModItems.shimmer_handle, 1), new Object[] { "GP", "GP", "GP", 'G', GOLD.plate(), 'P', ANY_PLASTIC.ingot() });
		addRecipeAuto(new ItemStack(ModItems.shimmer_sledge, 1), new Object[] { "H", "G", "G", 'G', ModItems.shimmer_handle, 'H', ModItems.shimmer_head });
		addRecipeAuto(new ItemStack(ModItems.shimmer_axe, 1), new Object[] { "H", "G", "G", 'G', ModItems.shimmer_handle, 'H', ModItems.shimmer_axe_head });
		addRecipeAuto(new ItemStack(ModItems.definitelyfood, 1), new Object[] { "DDD", "SDS", "DDD", 'D', Blocks.dirt, 'S', STEEL.plate() });
		addRecipeAuto(new ItemStack(ModItems.blade_tungsten, 2), new Object[] { "IP", "TP", "TI", 'P', TI.plate(), 'T', TI.ingot(), 'I', W.ingot() });
		addRecipeAuto(new ItemStack(ModItems.turbine_tungsten, 1), new Object[] { "BBB", "BSB", "BBB", 'B', ModItems.blade_tungsten, 'S', DURA.ingot() });
		addRecipeAuto(new ItemStack(ModItems.ring_starmetal, 1), new Object[] { " S ", "S S", " S ", 'S', STAR.ingot() });
		addRecipeAuto(new ItemStack(ModItems.flywheel_beryllium, 1), new Object[] { "BBB", "BTB", "BBB", 'B', BE.block(), 'T', ModItems.bolt_compound });
		
		addShapelessAuto(DictFrame.fromOne(ModItems.plant_item, EnumPlantType.ROPE, 1), new Object[] { Items.string, Items.string, Items.string });
		addRecipeAuto(DictFrame.fromOne(ModItems.plant_item, EnumPlantType.ROPE, 4), new Object[] { "W", "W", "W", 'W', DictFrame.fromOne(ModBlocks.plant_flower, EnumFlowerType.WEED) });
		addRecipeAuto(new ItemStack(ModItems.rag, 16), new Object[] { "WW", "WW", 'W', DictFrame.fromOne(ModBlocks.plant_flower, EnumFlowerType.WEED) });

		ItemStack infinity = new ItemStack(Items.enchanted_book);
		EnchantmentUtil.addEnchantment(infinity, Enchantment.infinity, 1);
		addRecipeAuto(infinity, new Object[] { "SBS", "BDB", "SBS", 'S', ModItems.ammo_50bmg_star, 'B', ModItems.ammo_5mm_star, 'D', ModItems.powder_magic });
		ItemStack unbreaking = new ItemStack(Items.enchanted_book);
		EnchantmentUtil.addEnchantment(unbreaking, Enchantment.unbreaking, 3);
		addRecipeAuto(unbreaking, new Object[] { "SBS", "BDB", "SBS", 'S', BIGMT.ingot(), 'B', ModItems.plate_armor_lunar, 'D', ModItems.powder_magic });
		ItemStack thorns = new ItemStack(Items.enchanted_book);
		EnchantmentUtil.addEnchantment(thorns, Enchantment.thorns, 3);
		addRecipeAuto(thorns, new Object[] { "SBS", "BDB", "SBS", 'S', ModBlocks.barbed_wire, 'B', ModBlocks.spikes, 'D', ModItems.powder_magic });

		addRecipeAuto(new ItemStack(ModItems.wrench, 1), new Object[] { " S ", " IS", "I  ", 'S', STEEL.ingot(), 'I', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModItems.wrench_flipped, 1), new Object[] { "S", "D", "W", 'S', Items.iron_sword, 'D', ModItems.ducttape, 'W', ModItems.wrench });
		addRecipeAuto(new ItemStack(ModItems.memespoon, 1), new Object[] { "CGC", "PSP", "IAI", 'C', ModItems.powder_cloud, 'G', TH232.block(), 'P', ModItems.photo_panel, 'S', ModItems.steel_shovel, 'I', ModItems.plate_polymer, 'A', "ingotAustralium" });
		addShapelessAuto(new ItemStack(ModItems.cbt_device, 1), new Object[] { ModItems.bolt_tungsten, ModItems.wrench });

		addShapelessAuto(new ItemStack(ModItems.toothpicks, 3), new Object[] { KEY_STICK, KEY_STICK, KEY_STICK });
		addRecipeAuto(new ItemStack(ModItems.ducttape, 6), new Object[] { "FSF", "SPS", "FSF", 'F', Items.string, 'S', KEY_SLIME, 'P', Items.paper });

		addRecipeAuto(new ItemStack(ModBlocks.conveyor, 16), new Object[] { "LLL", "I I", "LLL", 'L', Items.leather, 'I', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.conveyor, 16), new Object[] { "RSR", "I I", "RSR", 'I', IRON.ingot(), 'R', DictFrame.fromOne(ModItems.plant_item, EnumPlantType.ROPE), 'S', IRON.plate() });
		addRecipeAuto(new ItemStack(ModBlocks.conveyor, 64), new Object[] { "LLL", "I I", "LLL", 'L', RUBBER.ingot(), 'I', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.conveyor_double, 3), new Object[] { "CPC", "CPC", "CPC", 'C', ModBlocks.conveyor, 'P', IRON.plate() });
		addRecipeAuto(new ItemStack(ModBlocks.conveyor_triple, 3), new Object[] { "CPC", "CPC", "CPC", 'C', ModBlocks.conveyor_double, 'P', STEEL.plate() });
		addRecipeAuto(new ItemStack(ModBlocks.conveyor_chute, 3), new Object[] { "IGI", "IGI", "ICI" , 'I', IRON.ingot(), 'G', ModBlocks.steel_grate, 'C', ModBlocks.conveyor });
		addRecipeAuto(new ItemStack(ModBlocks.conveyor_lift, 3), new Object[] { "IGI", "IGI", "ICI" , 'I', IRON.ingot(), 'G', ModBlocks.chain, 'C', ModBlocks.conveyor });

		//addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_difurnace_off), 1), new Object[] { "T T", "PHP", "TFT", 'T', W.ingot(), 'P', ModItems.board_copper, 'H', Blocks.hopper, 'F', Blocks.furnace });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_uf6_tank), 1), new Object[] { "WTW", "WTW", "SRS", 'S', IRON.plate(), 'W', ModItems.coil_tungsten, 'T', ModItems.tank_steel, 'W', ModItems.coil_tungsten,'R', MINGRADE.ingot() });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_puf6_tank), 1), new Object[] { "WTW", "WTW", "SRS", 'S', STEEL.plate(), 'W', ModItems.coil_tungsten, 'T', ModItems.tank_steel, 'W', ModItems.coil_tungsten,'R', MINGRADE.ingot() });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_nuke_furnace_off), 1), new Object[] { "SSS", "LFL", "CCC", 'S', STEEL.plate(), 'C', ModItems.board_copper, 'L', PB.plate(), 'F', Item.getItemFromBlock(Blocks.furnace) });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_electric_furnace_off), 1), new Object[] { "BBB", "WFW", "RRR", 'B', BE.ingot(), 'R', ModItems.coil_tungsten, 'W', ModItems.board_copper, 'F', Item.getItemFromBlock(Blocks.furnace) });
		addRecipeAuto(new ItemStack(ModBlocks.machine_arc_furnace_off, 1), new Object[] { "ITI", "PFP", "ITI", 'I', W.ingot(), 'T', ModBlocks.machine_transformer, 'P', ModItems.board_copper, 'F', Blocks.furnace });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.red_wire_coated), 16), new Object[] { "WRW", "RIR", "WRW", 'W', ModItems.plate_polymer, 'I', MINGRADE.ingot(), 'R', ModItems.wire_red_copper });
		addRecipeAuto(new ItemStack(ModBlocks.cable_switch, 1), new Object[] { "S", "W", 'S', Blocks.lever, 'W', ModBlocks.red_wire_coated });
		addRecipeAuto(new ItemStack(ModBlocks.cable_detector, 1), new Object[] { "S", "W", 'S', REDSTONE.dust(), 'W', ModBlocks.red_wire_coated });
		addRecipeAuto(new ItemStack(ModBlocks.cable_diode, 1), new Object[] { " Q ", "CAC", " Q ", 'Q', NETHERQUARTZ.gem(), 'C', ModBlocks.red_cable, 'A', AL.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.machine_detector, 1), new Object[] { "IRI", "CTC", "IRI", 'I', ModItems.plate_polymer, 'R', REDSTONE.dust(), 'C', ModItems.wire_red_copper, 'T', ModItems.coil_tungsten });
		addRecipeAuto(new ItemStack(ModBlocks.red_cable, 16), new Object[] { " W ", "RRR", " W ", 'W', ModItems.plate_polymer, 'R', ModItems.wire_red_copper });
		addShapelessAuto(new ItemStack(ModBlocks.red_cable_classic, 1), new Object[] { ModBlocks.red_cable });
		addShapelessAuto(new ItemStack(ModBlocks.red_cable, 1), new Object[] { ModBlocks.red_cable_classic });
		addRecipeAuto(new ItemStack(ModBlocks.red_connector, 4), new Object[] { "C", "I", "S", 'C', ModItems.coil_copper, 'I', ModItems.plate_polymer, 'S', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.red_pylon, 4), new Object[] { "CWC", "PWP", " T ", 'C', ModItems.coil_copper, 'W', KEY_PLANKS, 'P', ModItems.plate_polymer, 'T', ModBlocks.red_wire_coated });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.oil_duct_solid), 16), new Object[] { "SPS", "P P", "SPS", 'S', STEEL.ingot(), 'P', IRON.plate() });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.oil_duct), 16), new Object[] { "SIS", "   ", "SIS", 'S', STEEL.plate(), 'I', IRON.plate() });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.gas_duct_solid), 16), new Object[] { "SPS", "P P", "SPS", 'S', STEEL.ingot(), 'P', CU.plate() });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.gas_duct), 16), new Object[] { "SIS", "   ", "SIS", 'S', STEEL.plate(), 'I', CU.plate() });
		addRecipeAuto(new ItemStack(ModBlocks.machine_battery_potato, 1), new Object[] { "PCP", "WRW", "PCP", 'P', ItemBattery.getEmptyBattery(ModItems.battery_potato), 'C', CU.ingot(), 'R', REDSTONE.block(), 'W', KEY_PLANKS });
		addRecipeAuto(new ItemStack(ModBlocks.machine_coal_off, 1), new Object[] { "STS", "SCS", "SFS", 'S', STEEL.ingot(), 'T', ModItems.tank_steel, 'C', MINGRADE.ingot(), 'F', Blocks.furnace });
		addRecipeAuto(new ItemStack(ModBlocks.machine_boiler_off, 1), new Object[] { "SPS", "TFT", "SPS", 'S', STEEL.ingot(), 'P', ModItems.board_copper, 'T', ModItems.tank_steel, 'F', Blocks.furnace });
		addRecipeAuto(new ItemStack(ModBlocks.machine_boiler_electric_off, 1), new Object[] { "SPS", "TFT", "SPS", 'S', DESH.ingot(), 'P', ModItems.board_copper, 'T', ModItems.tank_steel, 'F', ModBlocks.machine_electric_furnace_off });
		addRecipeAuto(new ItemStack(ModBlocks.machine_turbine, 1), new Object[] { "PMP", "PTP", "PMP", 'P', STEEL.ingot(), 'T', ModItems.turbine_titanium, 'M', ModItems.coil_copper });
		addRecipeAuto(new ItemStack(ModBlocks.machine_converter_he_rf, 1), new Object[] { "SSS", "CRB", "SSS", 'S', STEEL.ingot(), 'C', ModItems.coil_copper, 'R', ModItems.coil_copper_torus, 'B', REDSTONE.block() });
		addRecipeAuto(new ItemStack(ModBlocks.machine_converter_rf_he, 1), new Object[] { "SSS", "BRC", "SSS", 'S', BE.ingot(), 'C', ModItems.coil_copper, 'R', ModItems.coil_copper_torus, 'B', REDSTONE.block() });
		addRecipeAuto(new ItemStack(ModBlocks.crate_iron, 1), new Object[] { "PPP", "I I", "III", 'P', IRON.plate(), 'I', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.crate_steel, 1), new Object[] { "PPP", "I I", "III", 'P', STEEL.plate(), 'I', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.crate_desh, 1), new Object[] { " D ", "DSD", " D ", 'D', ModItems.plate_desh, 'S', ModBlocks.crate_steel });
		addRecipeAuto(new ItemStack(ModBlocks.crate_tungsten, 1), new Object[] { "BPB", "PCP", "BPB", 'B', W.block(), 'P', ModItems.board_copper, 'C', ModBlocks.crate_steel });
		addRecipeAuto(new ItemStack(ModBlocks.safe, 1), new Object[] { "LAL", "ACA", "LAL", 'L', PB.plate(), 'A', ALLOY.plate(), 'C', ModBlocks.crate_steel });
		addRecipeAuto(new ItemStack(ModBlocks.mass_storage, 1, 0), new Object[] { "ICI", "CLC", "ICI", 'I', TI.ingot(), 'C', ModBlocks.crate_steel, 'L', ModItems.circuit_copper });
		addRecipeAuto(new ItemStack(ModBlocks.mass_storage, 1, 1), new Object[] { "PCP", "PMP", "PPP", 'P', DESH.ingot(), 'C', ModItems.circuit_red_copper, 'M', new ItemStack(ModBlocks.mass_storage, 1, 0) });
		addRecipeAuto(new ItemStack(ModBlocks.mass_storage, 1, 2), new Object[] { "PCP", "PMP", "PPP", 'P', TCALLOY.ingot(), 'C', ModItems.circuit_gold, 'M', new ItemStack(ModBlocks.mass_storage, 1, 1) });
		addRecipeAuto(new ItemStack(ModBlocks.machine_autocrafter, 1), new Object[] { "SCS", "MWM", "SCS", 'S', STEEL.plate(), 'C', ModItems.circuit_copper, 'M', ModItems.motor, 'W', Blocks.crafting_table });
		addRecipeAuto(new ItemStack(ModBlocks.machine_waste_drum, 1), new Object[] { "LRL", "BRB", "LRL", 'L', PB.ingot(), 'B', Blocks.iron_bars, 'R', ModItems.rod_quad_empty });
		addRecipeAuto(new ItemStack(ModBlocks.machine_press, 1), new Object[] { "IRI", "IPI", "IBI", 'I', IRON.ingot(), 'R', Blocks.furnace, 'B', IRON.block(), 'P', Blocks.piston });
		addRecipeAuto(new ItemStack(ModBlocks.machine_siren, 1), new Object[] { "SIS", "ICI", "SRS", 'S', STEEL.plate(), 'I', ModItems.plate_polymer, 'C', ModItems.circuit_copper, 'R', REDSTONE.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.machine_microwave, 1), new Object[] { "III", "SGM", "IDI", 'I', ModItems.plate_polymer, 'S', STEEL.plate(), 'G', KEY_ANYPANE, 'M', ModItems.magnetron, 'D', ModItems.motor });
		addRecipeAuto(new ItemStack(ModBlocks.machine_solar_boiler), new Object[] { "SHS", "DHD", "SHS", 'S', STEEL.ingot(), 'H', ModItems.hull_big_steel, 'D', KEY_BLACK });
		addRecipeAuto(new ItemStack(ModBlocks.solar_mirror, 3), new Object[] { "AAA", " B ", "SSS", 'A', AL.plate(), 'B', ModBlocks.steel_beam, 'S', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.anvil_iron, 1), new Object[] { "III", " B ", "III", 'I', IRON.ingot(), 'B', IRON.block() });
		addRecipeAuto(new ItemStack(ModBlocks.anvil_lead, 1), new Object[] { "III", " B ", "III", 'I', PB.ingot(), 'B', PB.block() });
		addRecipeAuto(new ItemStack(ModBlocks.anvil_murky, 1), new Object[] { "UUU", "UAU", "UUU", 'U', ModItems.undefined, 'A', ModBlocks.anvil_steel });
		addRecipeAuto(new ItemStack(ModBlocks.machine_fraction_tower), new Object[] { "SHS", "SGS", "SHS", 'S', STEEL.plate(), 'H', ModItems.hull_big_steel, 'G', ModBlocks.steel_grate });
		addRecipeAuto(new ItemStack(ModBlocks.fraction_spacer), new Object[] { "BHB", 'H', ModItems.hull_big_steel, 'B', Blocks.iron_bars });
		addRecipeAuto(new ItemStack(ModBlocks.furnace_iron), new Object[] { "III", "IFI", "BBB", 'I', IRON.ingot(), 'F', Blocks.furnace, 'B', Blocks.stonebrick });

		addRecipeAuto(new ItemStack(ModBlocks.muffler, 1), new Object[] { "III", "IWI", "III", 'I', ModItems.plate_polymer, 'W', Blocks.wool });

		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.factory_titanium_hull), 1), new Object[] { "PIP", "I I", "PIP", 'P', TI.plate(), 'I', TI.ingot() });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.factory_advanced_hull), 1), new Object[] { "PIP", "I I", "PIP", 'P', ALLOY.plate(), 'I', ALLOY.ingot() });

		addRecipeAuto(new ItemStack(ModItems.arc_electrode, 1), new Object[] { "C", "T", "C", 'C', GRAPHITE.ingot(), 'T', ModItems.bolt_tungsten });
		addRecipeAuto(new ItemStack(ModItems.arc_electrode_desh, 1), new Object[] { "C", "T", "C", 'C', DESH.dust(), 'T', ModItems.arc_electrode });
		addRecipeAuto(new ItemStack(ModItems.detonator, 1), new Object[] { " W", "SC", "CE", 'S', STEEL.plate(), 'W', ModItems.wire_red_copper, 'C', ModItems.circuit_red_copper, 'E', STEEL.ingot() });
		addShapelessAuto(new ItemStack(ModItems.detonator_multi, 1), new Object[] { ModItems.detonator, ModItems.circuit_targeting_tier3 });
		addRecipeAuto(new ItemStack(ModItems.detonator_laser, 1), new Object[] { "RRD", "PIC", "  P", 'P', STEEL.plate(), 'R', REDSTONE.dust(), 'C', ModItems.circuit_targeting_tier3, 'D', DIAMOND.gem(), 'I', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.detonator_laser, 1), new Object[] { "RRD", "PIC", "  P", 'P', STEEL.plate(), 'R', REDSTONE.dust(), 'C', ModItems.circuit_targeting_tier3, 'D', EMERALD.gem(), 'I', STEEL.ingot() });
		addShapelessAuto(new ItemStack(ModItems.detonator_deadman, 1), new Object[] { ModItems.detonator, ModItems.defuser, ModItems.ducttape });
		addRecipeAuto(new ItemStack(ModItems.detonator_de, 1), new Object[] { "T", "D", "T", 'T', Blocks.tnt, 'D', ModItems.detonator_deadman });

		addRecipeAuto(new ItemStack(ModItems.singularity, 1), new Object[] { "ESE", "SBS", "ESE", 'E', EUPH.nugget(), 'S', ModItems.cell_anti_schrabidium, 'B', SA326.block() });
		addRecipeAuto(new ItemStack(ModItems.singularity_counter_resonant, 1), new Object[] { "CTC", "TST", "CTC", 'C', CMB.plate(), 'T', MAGTUNG.ingot(), 'S', ModItems.singularity });
		addRecipeAuto(new ItemStack(ModItems.singularity_super_heated, 1), new Object[] { "CTC", "TST", "CTC", 'C', ALLOY.plate(), 'T', ModItems.powder_power, 'S', ModItems.singularity });
		addRecipeAuto(new ItemStack(ModItems.black_hole, 1), new Object[] { "SSS", "SCS", "SSS", 'C', ModItems.singularity, 'S', ModItems.crystal_xen });
		addRecipeAuto(new ItemStack(ModItems.crystal_xen, 1), new Object[] { "EEE", "EIE", "EEE", 'E', ModItems.powder_power, 'I', EUPH.ingot() });

		addShapelessAuto(new ItemStack(ModItems.overfuse, 1), new Object[] { ModItems.bolt_tungsten, NP237.dust(), I.dust(), TH232.dust(), AT.dust(), ND.dust(), ModItems.board_copper, ModItems.black_hole, CS.dust() });
		addShapelessAuto(new ItemStack(ModItems.overfuse, 1), new Object[] { ModItems.bolt_tungsten, ST.dust(), BR.dust(), CO.dust(), TS.dust(), NB.dust(), ModItems.board_copper, ModItems.black_hole, CE.dust() });

		addRecipeAuto(new ItemStack(ModItems.blades_aluminium, 1), new Object[] { " P ", "PIP", " P ", 'P', AL.plate(), 'I', AL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.blades_gold, 1), new Object[] { " P ", "PIP", " P ", 'P', GOLD.plate(), 'I', GOLD.ingot() });
		addRecipeAuto(new ItemStack(ModItems.blades_iron, 1), new Object[] { " P ", "PIP", " P ", 'P', IRON.plate(), 'I', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModItems.blades_steel, 1), new Object[] { " P ", "PIP", " P ", 'P', STEEL.plate(), 'I', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.blades_titanium, 1), new Object[] { " P ", "PIP", " P ", 'P', TI.plate(), 'I', TI.ingot() });
		addRecipeAuto(new ItemStack(ModItems.blades_advanced_alloy, 1), new Object[] { " P ", "PIP", " P ", 'P', ALLOY.plate(), 'I', ALLOY.ingot() });
		addRecipeAuto(new ItemStack(ModItems.blades_combine_steel, 1), new Object[] { " P ", "PIP", " P ", 'P', CMB.plate(), 'I', CMB.ingot() });
		addRecipeAuto(new ItemStack(ModItems.blades_schrabidium, 1), new Object[] { " P ", "PIP", " P ", 'P', SA326.plate(), 'I', SA326.ingot() });
		addRecipeAuto(new ItemStack(ModItems.blades_desh, 1), new Object[] { "NPN", "PBP", "NPN", 'N', SA326.nugget(), 'P', ModItems.plate_desh, 'B', ModItems.blades_combine_steel });
		
		addRecipeAuto(new ItemStack(ModItems.blades_aluminium, 1), new Object[] { "PIP", 'P', AL.plate(), 'I', new ItemStack(ModItems.blades_aluminium, 1, OreDictionary.WILDCARD_VALUE) });
		addRecipeAuto(new ItemStack(ModItems.blades_gold, 1), new Object[] { "PIP", 'P', GOLD.plate(), 'I', new ItemStack(ModItems.blades_gold, 1, OreDictionary.WILDCARD_VALUE) });
		addRecipeAuto(new ItemStack(ModItems.blades_iron, 1), new Object[] { "PIP", 'P', IRON.plate(), 'I', new ItemStack(ModItems.blades_iron, 1, OreDictionary.WILDCARD_VALUE) });
		addRecipeAuto(new ItemStack(ModItems.blades_steel, 1), new Object[] { "PIP", 'P', STEEL.plate(), 'I', new ItemStack(ModItems.blades_steel, 1, OreDictionary.WILDCARD_VALUE) });
		addRecipeAuto(new ItemStack(ModItems.blades_titanium, 1), new Object[] { "PIP", 'P', TI.plate(), 'I', new ItemStack(ModItems.blades_titanium, 1, OreDictionary.WILDCARD_VALUE) });
		addRecipeAuto(new ItemStack(ModItems.blades_advanced_alloy, 1), new Object[] { "PIP", 'P', ALLOY.plate(), 'I', new ItemStack(ModItems.blades_advanced_alloy, 1, OreDictionary.WILDCARD_VALUE) });
		addRecipeAuto(new ItemStack(ModItems.blades_combine_steel, 1), new Object[] { "PIP", 'P', CMB.plate(), 'I', new ItemStack(ModItems.blades_combine_steel, 1, OreDictionary.WILDCARD_VALUE) });
		addRecipeAuto(new ItemStack(ModItems.blades_schrabidium, 1), new Object[] { "PIP", 'P', SA326.plate(), 'I', new ItemStack(ModItems.blades_schrabidium, 1, OreDictionary.WILDCARD_VALUE) });
		
		addRecipeAuto(new ItemStack(ModItems.laser_crystal_co2, 1), new Object[] { "QDQ", "NCN", "QDQ", 'Q', ModBlocks.glass_quartz, 'D', DESH.ingot(), 'N', NB.ingot(), 'C', new ItemStack(ModItems.fluid_tank_full, 1, Fluids.CARBONDIOXIDE.getID()) });
		addRecipeAuto(new ItemStack(ModItems.laser_crystal_bismuth, 1), new Object[] {"QUQ", "BCB", "QTQ", 'Q', ModBlocks.glass_quartz, 'U', U.ingot(), 'T', TH232.ingot(), 'B', ModItems.nugget_bismuth, 'C', ModItems.crystal_rare });
		addRecipeAuto(new ItemStack(ModItems.laser_crystal_cmb, 1), new Object[] {"QBQ", "CSC", "QBQ", 'Q', ModBlocks.glass_quartz, 'B', CMB.ingot(), 'C', SBD.ingot(), 'S', ModItems.cell_anti_schrabidium });
		addRecipeAuto(new ItemStack(ModItems.laser_crystal_dnt, 1), new Object[] {"QDQ", "SBS", "QDQ", 'Q', ModBlocks.glass_quartz, 'D', DNT.ingot(), 'B', ModItems.egg_balefire, 'S', ModItems.powder_spark_mix });
		addRecipeAuto(new ItemStack(ModItems.laser_crystal_digamma, 1), new Object[] {"QUQ", "UEU", "QUQ", 'Q', ModBlocks.glass_quartz, 'U', ModItems.undefined, 'E', ModItems.ingot_electronium } );

		Item[] bricks = new Item[] {Items.brick, Items.netherbrick};
		
		for(Item brick : bricks) {
			addRecipeAuto(new ItemStack(ModItems.stamp_stone_flat, 1), new Object[] { " R ", "III", "SSS", 'R', REDSTONE.dust(), 'I', brick, 'S', "stone" });
			addRecipeAuto(new ItemStack(ModItems.stamp_iron_flat, 1), new Object[] { " R ", "III", "SSS", 'R', REDSTONE.dust(), 'I', brick, 'S', IRON.ingot() });
			addRecipeAuto(new ItemStack(ModItems.stamp_steel_flat, 1), new Object[] { " R ", "III", "SSS", 'R', REDSTONE.dust(), 'I', brick, 'S', STEEL.ingot() });
			addRecipeAuto(new ItemStack(ModItems.stamp_titanium_flat, 1), new Object[] { " R ", "III", "SSS", 'R', REDSTONE.dust(), 'I', brick, 'S', TI.ingot() });
			addRecipeAuto(new ItemStack(ModItems.stamp_obsidian_flat, 1), new Object[] { " R ", "III", "SSS", 'R', REDSTONE.dust(), 'I', brick, 'S', Blocks.obsidian });
			addRecipeAuto(new ItemStack(ModItems.stamp_schrabidium_flat, 1), new Object[] { " R ", "III", "SSS", 'R', REDSTONE.dust(), 'I', brick, 'S', SA326.ingot() });
		}

		addRecipeAuto(new ItemStack(ModItems.stamp_desh_flat), "DSD", "III", "DSD", 'D', DESH.block(), 'I', BIGMT.ingot(), 'S', ModItems.stamp_schrabidium_flat);
		addRecipeAuto(new ItemStack(ModItems.stamp_desh_plate), "DSD", "III", "DSD", 'D', DESH.block(), 'I', BIGMT.ingot(), 'S', ModItems.stamp_schrabidium_plate);
		addRecipeAuto(new ItemStack(ModItems.stamp_desh_wire), "DSD", "III", "DSD", 'D', DESH.block(), 'I', BIGMT.ingot(), 'S', ModItems.stamp_schrabidium_wire);
		addRecipeAuto(new ItemStack(ModItems.stamp_desh_circuit), "DSD", "III", "DSD", 'D', DESH.block(), 'I', BIGMT.ingot(), 'S', ModItems.stamp_schrabidium_circuit);
		
		addRecipeAuto(new ItemStack(ModItems.mechanism_revolver_1, 1), new Object[] { " II", "ICA", "IKW", 'I', IRON.plate(), 'C', CU.ingot(), 'A', AL.ingot(), 'K', ModItems.wire_copper, 'W', ModItems.wire_aluminium });
		addRecipeAuto(new ItemStack(ModItems.mechanism_revolver_2, 1), new Object[] { " II", "ICA", "IKW", 'I', ALLOY.plate(), 'C', DURA.ingot(), 'A', W.ingot(), 'K', ModItems.bolt_dura_steel, 'W', ModItems.bolt_tungsten });
		addRecipeAuto(new ItemStack(ModItems.mechanism_rifle_1, 1), new Object[] { "ICI", "CMA", "IAM", 'I', IRON.plate(), 'C', CU.ingot(), 'A', AL.ingot(), 'M', ModItems.mechanism_revolver_1 });
		addRecipeAuto(new ItemStack(ModItems.mechanism_rifle_2, 1), new Object[] { "ICI", "CMA", "IAM", 'I', ALLOY.plate(), 'C', DURA.ingot(), 'A', W.ingot(), 'M', ModItems.mechanism_revolver_2 });
		addRecipeAuto(new ItemStack(ModItems.mechanism_launcher_1, 1), new Object[] { "TTT", "SSS", "BBI", 'T', TI.plate(), 'S', STEEL.ingot(), 'B', ModItems.bolt_tungsten, 'I', MINGRADE.ingot() });
		addRecipeAuto(new ItemStack(ModItems.mechanism_launcher_2, 1), new Object[] { "TTT", "SSS", "BBI", 'T', ALLOY.plate(), 'S', ANY_PLASTIC.ingot(), 'B', ModItems.bolt_dura_steel, 'I', DESH.ingot() });
		addRecipeAuto(new ItemStack(ModItems.mechanism_special, 1), new Object[] { "PCI", "ISS", "PCI", 'P', ModItems.plate_desh, 'C', ModItems.coil_advanced_alloy, 'I', STAR.ingot(), 'S', ModItems.circuit_targeting_tier3 });
		
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.fwatz_cooler), 1), new Object[] { "IPI", "IPI", "IPI", 'I', TI.ingot(), 'P', TI.plate() });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.fwatz_tank), 1), new Object[] { "CGC", "GGG", "CGC", 'C', CMB.plate(), 'G', KEY_ANYPANE });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.fwatz_scaffold), 1), new Object[] { "IPI", "P P", "IPI", 'I', W.ingot(), 'P', getReflector() });

		addRecipeAuto(new ItemStack(ModBlocks.reinforced_stone, 4), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.cobblestone, 'B', Blocks.stone });
		addRecipeAuto(new ItemStack(ModBlocks.brick_light, 4), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.fence, 'B', Blocks.brick_block });
		addRecipeAuto(new ItemStack(ModBlocks.brick_asbestos, 2), new Object[] { " A ", "ABA", " A ", 'B', ModBlocks.brick_light, 'A', ASBESTOS.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.concrete, 4), new Object[] { "CC", "CC", 'C', ModBlocks.concrete_smooth });
		addRecipeAuto(new ItemStack(ModBlocks.concrete_pillar, 8), new Object[] { "CBC", "CBC", "CBC", 'C', ModBlocks.concrete_smooth, 'B', Blocks.iron_bars });
		addRecipeAuto(new ItemStack(ModBlocks.brick_concrete, 4), new Object[] { " C ", "CBC", " C ", 'C', ModBlocks.concrete_smooth, 'B', Items.clay_ball });
		addRecipeAuto(new ItemStack(ModBlocks.brick_concrete, 4), new Object[] { " C ", "CBC", " C ", 'C', ModBlocks.concrete, 'B', Items.clay_ball });
		addRecipeAuto(new ItemStack(ModBlocks.brick_concrete_mossy, 8), new Object[] { "CCC", "CVC", "CCC", 'C', ModBlocks.brick_concrete, 'V', Blocks.vine });
		addRecipeAuto(new ItemStack(ModBlocks.brick_concrete_cracked, 6), new Object[] { " C " , "C C", " C ", 'C', ModBlocks.brick_concrete });
		addRecipeAuto(new ItemStack(ModBlocks.brick_concrete_broken, 6), new Object[] { " C " , "C C", " C ", 'C', ModBlocks.brick_concrete_cracked });
		addRecipeAuto(new ItemStack(ModBlocks.ducrete, 4), new Object[] { "DD", "DD", 'D', ModBlocks.ducrete_smooth });
		addRecipeAuto(new ItemStack(ModBlocks.brick_ducrete, 4), new Object[] {"CDC", "DLD", "CDC", 'D', ModBlocks.ducrete_smooth, 'C', Items.clay_ball, 'L', ModItems.plate_lead });
		addRecipeAuto(new ItemStack(ModBlocks.brick_ducrete, 4), new Object[] {"CDC", "DLD", "CDC", 'D', ModBlocks.ducrete, 'C', Items.clay_ball, 'L', ModItems.plate_lead });
		addRecipeAuto(new ItemStack(ModBlocks.reinforced_ducrete, 4), new Object[] {"DSD", "SUS", "DSD", 'D', ModBlocks.brick_ducrete, 'S', ModItems.plate_steel, 'U', U238.billet() });
		addRecipeAuto(new ItemStack(ModBlocks.brick_obsidian, 4), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.iron_bars, 'B', Blocks.obsidian });
		addRecipeAuto(new ItemStack(ModBlocks.meteor_polished, 4), new Object[] { "CC", "CC", 'C', ModBlocks.block_meteor_broken });
		addRecipeAuto(new ItemStack(ModBlocks.meteor_pillar, 2), new Object[] { "C", "C", 'C', ModBlocks.meteor_polished });
		addRecipeAuto(new ItemStack(ModBlocks.meteor_brick, 4), new Object[] { "CC", "CC", 'C', ModBlocks.meteor_polished });
		addRecipeAuto(new ItemStack(ModBlocks.meteor_brick_mossy, 8), new Object[] { "CCC", "CVC", "CCC", 'C', ModBlocks.meteor_brick, 'V', Blocks.vine });
		addRecipeAuto(new ItemStack(ModBlocks.meteor_brick_cracked, 6), new Object[] { " C " , "C C", " C ", 'C', ModBlocks.meteor_brick });
		addRecipeAuto(new ItemStack(ModBlocks.meteor_battery, 1), new Object[] { "MSM", "MWM", "MSM", 'M', ModBlocks.meteor_polished, 'S', STAR.block(), 'W', ModItems.wire_schrabidium });
		addRecipeAuto(new ItemStack(ModBlocks.tile_lab, 4), new Object[] { "CBC", "CBC", "CBC", 'C', Items.brick, 'B', ASBESTOS.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.tile_lab_cracked, 6), new Object[] { " C " , "C C", " C ", 'C', ModBlocks.tile_lab });
		addRecipeAuto(new ItemStack(ModBlocks.tile_lab_broken, 6), new Object[] { " C " , "C C", " C ", 'C', ModBlocks.tile_lab_cracked });
		addShapelessAuto(new ItemStack(ModBlocks.asphalt_light, 1), new Object[] { ModBlocks.asphalt, Items.glowstone_dust });
		addShapelessAuto(new ItemStack(ModBlocks.asphalt, 1), new Object[] { ModBlocks.asphalt_light });
		addRecipeAuto(new ItemStack(ModBlocks.block_niter_reinforced, 1), new Object[] { "TCT", "CNC", "TCT", 'T', TCALLOY.ingot(), 'C', ModBlocks.concrete, 'N', KNO.block() });
		
		String[] dyes = { "Black", "Red", "Green", "Brown", "Blue", "Purple", "Cyan", "LightGray", "Gray", "Pink", "Lime", "Yellow", "LightBlue", "Magenta", "Orange", "White" };
		
		for(int i = 0; i < 16; i++) {
			String dyeName = "dye" + dyes[15 - i];
			addRecipeAuto(new ItemStack(ModBlocks.concrete_colored, 8, i), new Object[] { "CCC", "CDC", "CCC", 'C', ModBlocks.concrete_smooth, 'D', dyeName });
			addRecipeAuto(new ItemStack(ModBlocks.concrete_colored, 8, i), new Object[] { "CCC", "CDC", "CCC", 'C', ModBlocks.concrete_colored, 'D', dyeName });
		}
		addShapelessAuto(new ItemStack(ModBlocks.concrete_smooth, 1), new Object[] { ModBlocks.concrete_colored });

		addRecipeAuto(new ItemStack(ModBlocks.gneiss_tile, 4), new Object[] { "CC", "CC", 'C', ModBlocks.stone_gneiss });
		addRecipeAuto(new ItemStack(ModBlocks.gneiss_brick, 4), new Object[] { "CC", "CC", 'C', ModBlocks.gneiss_tile });
		addShapelessAuto(new ItemStack(ModBlocks.gneiss_chiseled, 1), new Object[] { ModBlocks.gneiss_tile });
		addRecipeAuto(new ItemStack(ModBlocks.depth_brick, 4), new Object[] { "CC", "CC", 'C', ModBlocks.stone_depth });
		addRecipeAuto(new ItemStack(ModBlocks.depth_tiles, 4), new Object[] { "CC", "CC", 'C', ModBlocks.depth_brick });
		addRecipeAuto(new ItemStack(ModBlocks.depth_nether_brick, 4), new Object[] { "CC", "CC", 'C', ModBlocks.stone_depth_nether });
		addRecipeAuto(new ItemStack(ModBlocks.depth_nether_tiles, 4), new Object[] { "CC", "CC", 'C', ModBlocks.depth_nether_brick });
		addRecipeAuto(new ItemStack(ModBlocks.basalt_polished, 4), new Object[] { "CC", "CC", 'C', ModBlocks.basalt_smooth });
		addRecipeAuto(new ItemStack(ModBlocks.basalt_brick, 4), new Object[] { "CC", "CC", 'C', ModBlocks.basalt_polished });
		addRecipeAuto(new ItemStack(ModBlocks.basalt_tiles, 4), new Object[] { "CC", "CC", 'C', ModBlocks.basalt_brick });


		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.reinforced_brick), 4), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.iron_bars, 'B', ModBlocks.brick_concrete });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.brick_compound), 4), new Object[] { "FBF", "BTB", "FBF", 'F', ModItems.bolt_tungsten, 'B', ModBlocks.reinforced_brick, 'T', ANY_TAR.any() });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.reinforced_glass), 4), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.iron_bars, 'B', Blocks.glass });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.reinforced_light), 1), new Object[] { "FFF", "FBF", "FFF", 'F', Blocks.iron_bars, 'B', Blocks.glowstone });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.reinforced_lamp_off), 1), new Object[] { "FFF", "FBF", "FFF", 'F', Blocks.iron_bars, 'B', Blocks.redstone_lamp });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.reinforced_sand), 4), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.iron_bars, 'B', Blocks.sandstone });

		addRecipeAuto(new ItemStack(ModBlocks.lamp_tritium_green_off, 1), new Object[] { "GPG", "1T2", "GPG", 'G', KEY_ANYGLASS, 'P', P_RED.dust(), 'T', ModItems.cell_tritium, '1', "dustSulfur", '2', CU.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.lamp_tritium_blue_off, 1), new Object[] { "GPG", "1T2", "GPG", 'G', KEY_ANYGLASS, 'P',P_RED.dust(), 'T', ModItems.cell_tritium, '1', AL.dust(), '2', ST.dust() });
		
		addRecipeAuto(new ItemStack(ModBlocks.barbed_wire, 16), new Object[] { "AIA", "I I", "AIA", 'A', ModItems.wire_aluminium, 'I', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.barbed_wire_fire, 8), new Object[] { "BBB", "BIB", "BBB", 'B', ModBlocks.barbed_wire, 'I', P_RED.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.barbed_wire_poison, 8), new Object[] { "BBB", "BIB", "BBB", 'B', ModBlocks.barbed_wire, 'I', ModItems.powder_poison });
		addRecipeAuto(new ItemStack(ModBlocks.barbed_wire_acid, 8), new Object[] { "BBB", "BIB", "BBB", 'B', ModBlocks.barbed_wire, 'I', new ItemStack(ModItems.fluid_tank_full, 1, Fluids.ACID.getID()) });
		addRecipeAuto(new ItemStack(ModBlocks.barbed_wire_wither, 8), new Object[] { "BBB", "BIB", "BBB", 'B', ModBlocks.barbed_wire, 'I', new ItemStack(Items.skull, 1, 1) });
		addRecipeAuto(new ItemStack(ModBlocks.barbed_wire_ultradeath, 4), new Object[] { "BCB", "CIC", "BCB", 'B', ModBlocks.barbed_wire, 'C', ModItems.powder_cloud, 'I', ModItems.nuclear_waste });

		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.tape_recorder), 4), new Object[] { "TST", "SSS", 'T', W.ingot(), 'S', STEEL.ingot() });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_poles), 16), new Object[] { "S S", "SSS", "S S", 'S', STEEL.ingot() });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.pole_top), 1), new Object[] { "T T", "TRT", "BBB", 'T', W.ingot(), 'B', BE.ingot(), 'R', MINGRADE.ingot() });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.pole_satellite_receiver), 1), new Object[] { "SS ", "SCR", "SS ", 'S', STEEL.ingot(), 'C', ModItems.circuit_red_copper, 'R', ModItems.wire_red_copper });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_beam), 8), new Object[] { "S", "S", "S", 'S', STEEL.ingot() });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_wall), 4), new Object[] { "SSS", "SSS", 'S', STEEL.ingot() });
		addShapelessAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_corner)), new Object[] { ModBlocks.steel_wall, ModBlocks.steel_wall });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_roof), 2), new Object[] { "SSS", 'S', STEEL.ingot() });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_scaffold), 8), new Object[] { "SSS", " S ", "SSS", 'S', STEEL.ingot() });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_beam), 8), new Object[] { "S", "S", "S", 'S', ModBlocks.steel_scaffold });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.chain), 8), new Object[] { "S", "S", "S", 'S', ModBlocks.steel_beam });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_grate), 4), new Object[] { "SS", "SS", 'S', ModBlocks.steel_beam });
		
		reg2();
	}
	
	public static void reg2() {

		addRecipeAuto(new ItemStack(ModItems.stamp_357, 1), new Object[] { "RSR", "III", " C ", 'R', REDSTONE.dust(), 'S', ModItems.stamp_iron_flat, 'I', ModItems.plate_polymer, 'C', ModItems.casing_357 });
		addRecipeAuto(new ItemStack(ModItems.stamp_44, 1), new Object[] { "RSR", "III", " C ", 'R', REDSTONE.dust(), 'S', ModItems.stamp_iron_flat, 'I', ModItems.plate_polymer, 'C', ModItems.casing_44 });
		addRecipeAuto(new ItemStack(ModItems.stamp_9, 1), new Object[] { "RSR", "III", " C ", 'R', REDSTONE.dust(), 'S', ModItems.stamp_iron_flat, 'I', ModItems.plate_polymer, 'C', ModItems.casing_9 });
		addRecipeAuto(new ItemStack(ModItems.stamp_50, 1), new Object[] { "RSR", "III", " C ", 'R', REDSTONE.dust(), 'S', ModItems.stamp_iron_flat, 'I', ModItems.plate_polymer, 'C', ModItems.casing_50 });

		addRecipeAuto(new ItemStack(ModBlocks.sat_dock, 1), new Object[] { "SSS", "PCP", 'S', STEEL.ingot(), 'P', ANY_PLASTIC.ingot(), 'C', ModBlocks.crate_iron });
		addRecipeAuto(new ItemStack(ModBlocks.book_guide, 1), new Object[] { "IBI", "LBL", "IBI", 'B', Items.book, 'I', KEY_BLACK, 'L', KEY_BLUE });

		addRecipeAuto(new ItemStack(ModBlocks.rail_wood, 16), new Object[] { "S S", "SRS", "S S", 'S', Items.stick, 'R', DictFrame.fromOne(ModItems.plant_item, EnumPlantType.ROPE) });
		addRecipeAuto(new ItemStack(ModBlocks.rail_narrow, 64), new Object[] { "S S", "S S", "S S", 'S', ModBlocks.steel_beam });
		addRecipeAuto(new ItemStack(ModBlocks.rail_highspeed, 16), new Object[] { "S S", "SIS", "S S", 'S', STEEL.ingot(), 'I', IRON.plate() });
		addRecipeAuto(new ItemStack(ModBlocks.rail_booster, 6), new Object[] { "S S", "CIC", "SRS", 'S', STEEL.ingot(), 'I', IRON.plate(), 'R', MINGRADE.ingot(), 'C', ModItems.coil_copper });

		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.bomb_multi), 1), new Object[] { "AAD", "CHF", "AAD", 'A', ModItems.wire_aluminium, 'C', ModItems.circuit_aluminium, 'H', ModItems.hull_small_aluminium, 'F', ModItems.fins_quad_titanium, 'D', KEY_WHITE });
		addShapelessAuto(new ItemStack(ModItems.powder_ice, 4), new Object[] { Items.snowball, KNO.dust(), REDSTONE.dust() });
		addShapelessAuto(new ItemStack(ModItems.powder_poison, 4), new Object[] { Items.spider_eye, REDSTONE.dust(), NETHERQUARTZ.gem() });
		addShapelessAuto(new ItemStack(ModItems.pellet_gas, 2), new Object[] { Items.water_bucket, "dustGlowstone", STEEL.plate() });

		addRecipeAuto(new ItemStack(ModItems.flame_pony, 1), new Object[] { " O ", "DPD", " O ", 'D', "dyePink", 'O', KEY_YELLOW, 'P', Items.paper });
		addRecipeAuto(new ItemStack(ModItems.flame_conspiracy, 1), new Object[] { " S ", "STS", " S ", 'S', Fluids.KEROSENE.getDict(1000), 'T', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.flame_politics, 1), new Object[] { " I ", "IPI", " I ", 'P', Items.paper, 'I', KEY_BLACK });
		addRecipeAuto(new ItemStack(ModItems.flame_opinion, 1), new Object[] { " R ", "RPR", " R ", 'P', Items.paper, 'R', KEY_RED });
		

		addRecipeAuto(new ItemStack(ModItems.solid_fuel_presto, 1), new Object[] { " P ", "SRS", " P ", 'P', Items.paper, 'S', ModItems.solid_fuel, 'R', REDSTONE.dust() });
		addShapelessAuto(new ItemStack(ModItems.solid_fuel_presto_triplet, 1), new Object[] { ModItems.solid_fuel_presto, ModItems.solid_fuel_presto, ModItems.solid_fuel_presto, ModItems.ball_dynamite });
		
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.flame_war), 1), new Object[] { "WHW", "CTP", "WOW", 'W', Item.getItemFromBlock(Blocks.planks), 'T', Item.getItemFromBlock(Blocks.tnt), 'H', ModItems.flame_pony, 'C', ModItems.flame_conspiracy, 'P', ModItems.flame_politics, 'O', ModItems.flame_opinion });
		addRecipeAuto(new ItemStack(ModBlocks.det_cord, 4), new Object[] { " P ", "PGP", " P ", 'P', Items.paper, 'G', Items.gunpowder });
		addRecipeAuto(new ItemStack(ModBlocks.det_charge, 1), new Object[] { "PDP", "DTD", "PDP", 'P', STEEL.plate(), 'D', ModBlocks.det_cord, 'T', ANY_PLASTICEXPLOSIVE.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.det_nuke, 1), new Object[] { "PDP", "DCD", "PDP", 'P', ModItems.plate_desh, 'D', ModBlocks.det_charge, 'C', ModItems.man_core });
		addRecipeAuto(new ItemStack(ModBlocks.det_miner, 4), new Object[] { "FFF", "ITI", "ITI", 'F', Items.flint, 'I', IRON.plate(), 'T', ModItems.ball_dynamite });
		addRecipeAuto(new ItemStack(ModBlocks.det_miner, 12), new Object[] { "FFF", "ITI", "ITI", 'F', Items.flint, 'I', STEEL.plate(), 'T', ANY_PLASTICEXPLOSIVE.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.emp_bomb, 1), new Object[] { "LML", "LCL", "LML", 'L', PB.plate(), 'M', ModItems.magnetron, 'C', ModItems.circuit_gold });
		addShapelessAuto(new ItemStack(ModBlocks.charge_dynamite, 1), new Object[] { ModItems.stick_dynamite, ModItems.stick_dynamite, ModItems.stick_dynamite, ModItems.ducttape });
		addRecipeAuto(new ItemStack(ModBlocks.charge_miner, 1), new Object[] { " F ", "FCF", " F ", 'F', Items.flint, 'C', ModBlocks.charge_dynamite });
		addShapelessAuto(new ItemStack(ModBlocks.charge_semtex, 1), new Object[] { ModItems.stick_semtex, ModItems.stick_semtex, ModItems.stick_semtex, ModItems.ducttape });
		addShapelessAuto(new ItemStack(ModBlocks.charge_c4, 1), new Object[] { ModItems.stick_c4, ModItems.stick_c4, ModItems.stick_c4, ModItems.ducttape });

		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_generic), new Object[] { " A ", "PRP", "PRP", 'A', ModItems.wire_aluminium, 'P', AL.plate(), 'R', REDSTONE.dust() });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_advanced), new Object[] { " A ", "PSP", "PLP", 'A', ModItems.wire_red_copper, 'P', CU.plate(), 'S', "sulfur", 'L', PB.dust() });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_advanced), new Object[] { " A ", "PLP", "PSP", 'A', ModItems.wire_red_copper, 'P', CU.plate(), 'S', "sulfur", 'L', PB.dust() });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_advanced), new Object[] { " A ", "PSP", "PLP", 'A', ModItems.wire_red_copper, 'P', CU.plate(), 'S', "dustSulfur", 'L', PB.dust() });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_advanced), new Object[] { " A ", "PLP", "PSP", 'A', ModItems.wire_red_copper, 'P', CU.plate(), 'S', "dustSulfur", 'L', PB.dust() });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_lithium), new Object[] { "A A", "PSP", "PLP", 'A', ModItems.wire_gold, 'P', TI.plate(), 'S', LI.dust(), 'L', CO.dust() });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_lithium), new Object[] { "A A", "PLP", "PSP", 'A', ModItems.wire_gold, 'P', TI.plate(), 'S', LI.dust(), 'L', CO.dust() });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_schrabidium), new Object[] { " A ", "PNP", "PSP", 'A', ModItems.wire_schrabidium, 'P', SA326.plate(), 'S', SA326.dust(), 'N', NP237.dust() });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_schrabidium), new Object[] { " A ", "PSP", "PNP", 'A', ModItems.wire_schrabidium, 'P', SA326.plate(), 'S', SA326.dust(), 'N', NP237.dust() });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_spark), new Object[] { " A ", "PSP", "PSP", 'A', ModItems.wire_magnetized_tungsten, 'P', ModItems.plate_dineutronium, 'S', ModItems.powder_spark_mix });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_trixite), new Object[] { " A ", "PSP", "PTP", 'A', ModItems.wire_aluminium, 'P', AL.plate(), 'S', ModItems.powder_power, 'T', ModItems.crystal_trixite });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_trixite), new Object[] { " A ", "PTP", "PSP", 'A', ModItems.wire_aluminium, 'P', AL.plate(), 'S', ModItems.powder_power, 'T', ModItems.crystal_trixite });
		addRecipeAuto(ItemBattery.getFullBattery(ModItems.energy_core), new Object[] { "PCW", "TRD", "PCW", 'P', ALLOY.plate(), 'C', ModItems.coil_advanced_alloy, 'W', ModItems.wire_advanced_alloy, 'R', ModItems.cell_tritium, 'D', ModItems.cell_deuterium, 'T', W.ingot() });
		addRecipeAuto(ItemBattery.getFullBattery(ModItems.energy_core), new Object[] { "PCW", "TDR", "PCW", 'P', ALLOY.plate(), 'C', ModItems.coil_advanced_alloy, 'W', ModItems.wire_advanced_alloy, 'R', ModItems.cell_tritium, 'D', ModItems.cell_deuterium, 'T', W.ingot() });
		addRecipeAuto(new ItemStack(ModItems.hev_battery, 4), new Object[] { " W ", "IEI", "ICI", 'W', ModItems.wire_gold, 'I', ModItems.plate_polymer, 'E', REDSTONE.dust(), 'C', CO.dust() });
		addRecipeAuto(new ItemStack(ModItems.hev_battery, 4), new Object[] { " W ", "ICI", "IEI", 'W', ModItems.wire_gold, 'I', ModItems.plate_polymer, 'E', REDSTONE.dust(), 'C', CO.dust() });
		addShapelessAuto(new ItemStack(ModItems.hev_battery, 1), new Object[] { ModBlocks.hev_battery });
		addShapelessAuto(new ItemStack(ModBlocks.hev_battery, 1), new Object[] { ModItems.hev_battery });

		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_red_cell), new Object[] { "WBW", "PBP", "WBW", 'W', ModItems.wire_aluminium, 'P', AL.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_generic) });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_advanced_cell), new Object[] { "WBW", "PBP", "WBW", 'W', ModItems.wire_red_copper, 'P', CU.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_advanced) });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell), new Object[] { "WBW", "PBP", "WBW", 'W', ModItems.wire_gold, 'P', TI.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_lithium) });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_schrabidium_cell), new Object[] { "WBW", "PBP", "WBW", 'W', ModItems.wire_schrabidium, 'P', SA326.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_schrabidium) });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_red_cell_6), new Object[] { "BBB", "WPW", "BBB", 'W', ModItems.wire_aluminium, 'P', AL.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_red_cell) });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_advanced_cell_4), new Object[] { "BWB", "WPW", "BWB", 'W', ModItems.wire_red_copper, 'P', CU.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_advanced_cell) });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell_3), new Object[] { "WPW", "BBB", "WPW", 'W', ModItems.wire_gold, 'P', TI.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell) });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_schrabidium_cell_2), new Object[] { "WPW", "BWB", "WPW", 'W', ModItems.wire_schrabidium, 'P', SA326.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_schrabidium_cell) });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_red_cell_24), new Object[] { "BWB", "WPW", "BWB", 'W', ModItems.wire_aluminium, 'P', AL.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_red_cell_6) });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_advanced_cell_12), new Object[] { "WPW", "BBB", "WPW", 'W', ModItems.wire_red_copper, 'P', CU.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_advanced_cell_4) });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell_6), new Object[] { "WPW", "BWB", "WPW", 'W', ModItems.wire_gold, 'P', TI.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell_3) });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_schrabidium_cell_4), new Object[] { "WPW", "BWB", "WPW", 'W', ModItems.wire_schrabidium, 'P', SA326.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_schrabidium_cell_2) });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_6), new Object[] { "BBW", "BBP", "BBW", 'W', ModItems.wire_magnetized_tungsten, 'P', ModItems.plate_dineutronium, 'B', ItemBattery.getEmptyBattery(ModItems.battery_spark) });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_25), new Object[] { " WW", "PCC", "BCC", 'W', ModItems.wire_magnetized_tungsten, 'P', ModItems.plate_dineutronium, 'B', ItemBattery.getEmptyBattery(ModItems.battery_spark), 'C', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_6) });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_100), new Object[] { "W W", "BPB", "BPB", 'W', ModItems.wire_magnetized_tungsten, 'P', ModItems.plate_dineutronium, 'B', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_25) });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_1000), new Object[] { "CCC", "CSC", "CCC", 'S', ModItems.singularity_spark, 'C', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_100) });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_2500), new Object[] { "CVC", "PSP", "CVC", 'S', ModItems.singularity_spark, 'C', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_100), 'V', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_1000), 'P', ModItems.plate_dineutronium });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_10000), new Object[] { "PVP", "VSV", "PVP", 'S', ModItems.singularity_spark, 'V', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_2500), 'P', ModItems.plate_dineutronium });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_power), new Object[] { "CCC", "CSC", "CCC", 'S', ModItems.singularity_spark, 'C', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_10000) });

		addRecipeAuto(ItemBattery.getFullBattery(ModItems.battery_su), new Object[] { "P", "R", "C", 'P', Items.paper, 'R', REDSTONE.dust(), 'C', COAL.dust() });
		addRecipeAuto(ItemBattery.getFullBattery(ModItems.battery_su), new Object[] { "P", "C", "R", 'P', Items.paper, 'R', REDSTONE.dust(), 'C', COAL.dust() });
		addRecipeAuto(ItemBattery.getFullBattery(ModItems.battery_su_l), new Object[] { " W ", "CPC", "RPR", 'W', ModItems.wire_aluminium, 'P', Items.paper, 'R', REDSTONE.dust(), 'C', COAL.dust() });
		addRecipeAuto(ItemBattery.getFullBattery(ModItems.battery_su_l), new Object[] { " W ", "RPR", "CPC", 'W', ModItems.wire_aluminium, 'P', Items.paper, 'R', REDSTONE.dust(), 'C', COAL.dust() });
		addRecipeAuto(ItemBattery.getFullBattery(ModItems.battery_su_l), new Object[] { " W ", "CPC", "RPR", 'W', ModItems.wire_copper, 'P', Items.paper, 'R', REDSTONE.dust(), 'C', COAL.dust() });
		addRecipeAuto(ItemBattery.getFullBattery(ModItems.battery_su_l), new Object[] { " W ", "RPR", "CPC", 'W', ModItems.wire_copper, 'P', Items.paper, 'R', REDSTONE.dust(), 'C', COAL.dust() });
		addShapelessAuto(ItemBattery.getFullBattery(ModItems.battery_potato), new Object[] { Items.potato, ModItems.wire_aluminium, ModItems.wire_copper });
		addShapelessAuto(ItemBattery.getFullBattery(ModItems.battery_potatos), new Object[] { ItemBattery.getFullBattery(ModItems.battery_potato), ModItems.turret_chip, REDSTONE.dust() });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_steam), new Object[] { "PMP", "ISI", "PCP", 'P', CU.plate(), 'M', ModItems.motor, 'C', ModItems.coil_tungsten, 'S', new ItemStack(ModItems.fluid_tank_full, 1, Fluids.WATER.getID()), 'I', ModItems.plate_polymer });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_steam_large), new Object[] { "MPM", "ISI", "CPC", 'P', ModItems.board_copper, 'M', ModItems.motor, 'C', ModItems.coil_tungsten, 'S', new ItemStack(ModItems.fluid_barrel_full, 1, Fluids.WATER.getID()), 'I', ANY_PLASTIC.ingot() });

		addRecipeAuto(new ItemStack(ModItems.battery_sc_uranium), new Object[] { "NBN", "PCP", "NBN", 'N', GOLD.nugget(), 'B', U238.billet(), 'P', PB.plate(), 'C', ModItems.thermo_element });
		addRecipeAuto(new ItemStack(ModItems.battery_sc_technetium), new Object[] { "NBN", "PCP", "NBN", 'N', GOLD.nugget(), 'B', TC99.billet(), 'P', PB.plate(), 'C', ModItems.battery_sc_uranium });
		addRecipeAuto(new ItemStack(ModItems.battery_sc_plutonium), new Object[] { "NBN", "PCP", "NBN", 'N', TC99.nugget(), 'B', PU238.billet(), 'P', PB.plate(), 'C', ModItems.battery_sc_technetium });
		addRecipeAuto(new ItemStack(ModItems.battery_sc_polonium), new Object[] { "NBN", "PCP", "NBN", 'N', TC99.nugget(), 'B', PO210.billet(), 'P', ANY_PLASTIC.ingot(), 'C', ModItems.battery_sc_plutonium });
		addRecipeAuto(new ItemStack(ModItems.battery_sc_gold), new Object[] { "NBN", "PCP", "NBN", 'N', TA.nugget(), 'B', AU198.billet(), 'P', ANY_PLASTIC.ingot(), 'C', ModItems.battery_sc_polonium });
		addRecipeAuto(new ItemStack(ModItems.battery_sc_lead), new Object[] { "NBN", "PCP", "NBN", 'N', TA.nugget(), 'B', PB209.billet(), 'P', ANY_PLASTIC.ingot(), 'C', ModItems.battery_sc_gold });
		addRecipeAuto(new ItemStack(ModItems.battery_sc_americium), new Object[] { "NBN", "PCP", "NBN", 'N', TA.nugget(), 'B', AM241.billet(), 'P', ANY_PLASTIC.ingot(), 'C', ModItems.battery_sc_lead });
		
		addRecipeAuto(new ItemStack(ModItems.wiring_red_copper, 1), new Object[] { "PPP", "PIP", "PPP", 'P', STEEL.plate(), 'I', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.tank_waste, 1), new Object[] { "PTP", "PTP", "PTP", 'T', ModItems.tank_steel, 'P', STEEL.plate() });
		
		addRecipeAuto(new ItemStack(ModItems.jetpack_tank, 1), new Object[] { " S ", "BKB", " S ", 'S', STEEL.plate(), 'B', ModItems.bolt_tungsten, 'K', Fluids.KEROSENE.getDict(1000) });
		addRecipeAuto(new ItemStack(ModItems.gun_kit_1, 4), new Object[] { "I ", "LB", "P ", 'I', ModItems.plate_polymer, 'L', Fluids.LUBRICANT.getDict(1000), 'B', ModItems.bolt_tungsten, 'P', IRON.plate() });
		addRecipeAuto(new ItemStack(ModItems.gun_kit_2, 1), new Object[] { "III", "GLG", "PPP", 'I', ModItems.plate_polymer, 'L', ModItems.ducttape, 'G', ModItems.gun_kit_1, 'P', IRON.plate() });

		addRecipeAuto(new ItemStack(ModItems.igniter, 1), new Object[] { " W", "SC", "CE", 'S', STEEL.plate(), 'W', ModItems.wire_schrabidium, 'C', ModItems.circuit_schrabidium, 'E', EUPH.ingot() });
		addRecipeAuto(new ItemStack(ModItems.watch, 1), new Object[] { "LEL", "EWE", "LEL", 'E', EUPH.ingot(), 'L', KEY_BLUE, 'W', Items.clock });

		addRecipeAuto(new ItemStack(ModItems.key, 1), new Object[] { "  B", " B ", "P  ", 'P', STEEL.plate(), 'B', ModItems.bolt_tungsten });
		addRecipeAuto(new ItemStack(ModItems.key_kit, 1), new Object[] { "PKP", "DTD", "PKP", 'P', GOLD.plate(), 'K', ModItems.key, 'D', DESH.dust(), 'T', ModItems.screwdriver });
		addRecipeAuto(new ItemStack(ModItems.key_red, 1), new Object[] { "DSC", "SMS", "KSD", 'C', ModItems.circuit_targeting_tier4, 'M', Items.nether_star, 'K', ModItems.key, 'D', DESH.dust(), 'S', BIGMT.plate() });
		addRecipeAuto(new ItemStack(ModItems.pin, 1), new Object[] { "W ", " W", " W", 'W', ModItems.wire_copper });
		addRecipeAuto(new ItemStack(ModItems.padlock_rusty, 1), new Object[] { "I", "B", "I", 'I', IRON.ingot(), 'B', ModItems.bolt_tungsten });
		addRecipeAuto(new ItemStack(ModItems.padlock, 1), new Object[] { " P ", "PBP", "PPP", 'P', STEEL.plate(), 'B', ModItems.bolt_tungsten });
		addRecipeAuto(new ItemStack(ModItems.padlock_reinforced, 1), new Object[] { " P ", "PBP", "PDP", 'P', ALLOY.plate(), 'D', ModItems.plate_desh, 'B', ModItems.bolt_dura_steel });
		addRecipeAuto(new ItemStack(ModItems.padlock_unbreakable, 1), new Object[] { " P ", "PBP", "PDP", 'P', BIGMT.plate(), 'D', DIAMOND.gem(), 'B', ModItems.bolt_dura_steel });
		
		addRecipeAuto(new ItemStack(ModItems.record_lc, 1), new Object[] { " S ", "SDS", " S ", 'S', ANY_PLASTIC.ingot(), 'D', LAPIS.dust() });
		addRecipeAuto(new ItemStack(ModItems.record_ss, 1), new Object[] { " S ", "SDS", " S ", 'S', ANY_PLASTIC.ingot(), 'D', ALLOY.dust() });
		addRecipeAuto(new ItemStack(ModItems.record_vc, 1), new Object[] { " S ", "SDS", " S ", 'S', ANY_PLASTIC.ingot(), 'D', CMB.dust() });

		addRecipeAuto(new ItemStack(ModItems.polaroid, 1), new Object[] { " C ", "RPY", " B ", 'B', LAPIS.dust(), 'C', COAL.dust(), 'R', ALLOY.dust(), 'Y', GOLD.dust(), 'P', Items.paper });

		addShapelessAuto(new ItemStack(ModItems.crystal_horn, 1), new Object[] { NP237.dust(), I.dust(), TH232.dust(), AT.dust(), ND.dust(), CS.dust(), ModBlocks.block_meteor, ModBlocks.gravel_obsidian, Items.water_bucket });
		addShapelessAuto(new ItemStack(ModItems.crystal_charred, 1), new Object[] { ST.dust(), CO.dust(), BR.dust(), NB.dust(), TS.dust(), CE.dust(), ModBlocks.block_meteor, AL.block(), Items.water_bucket });
		addRecipeAuto(new ItemStack(ModBlocks.crystal_virus, 1), new Object[] { "STS", "THT", "STS", 'S', ModItems.particle_strange, 'T', W.dust(), 'H', ModItems.crystal_horn });
		addRecipeAuto(new ItemStack(ModBlocks.crystal_pulsar, 32), new Object[] { "STS", "THT", "STS", 'S', ModItems.cell_uf6, 'T', AL.dust(), 'H', ModItems.crystal_charred });

		addRecipeAuto(new ItemStack(ModBlocks.fluid_duct, 8), new Object[] { "SAS", "   ", "SAS", 'S', STEEL.plate(), 'A', AL.plate() });
		addRecipeAuto(new ItemStack(ModBlocks.fluid_duct_solid, 8), new Object[] { "SAS", "A A", "SAS", 'S', STEEL.ingot(), 'A', AL.plate() });
		//addRecipeAuto(new ItemStack(ModBlocks.machine_assembler, 1), new Object[] { "WWW", "MCM", "ISI", 'W', KEY_ANYPANE, 'M', ModItems.motor, 'C', ModItems.circuit_aluminium, 'I', "blockCopper", 'S', STEEL.block() });
		addRecipeAuto(new ItemStack(ModItems.template_folder, 1), new Object[] { "LPL", "BPB", "LPL", 'P', Items.paper, 'L', KEY_BLUE, 'B', KEY_WHITE });
		//addRecipeAuto(new ItemStack(ModItems.turret_control, 1), new Object[] { "R12", "PPI", "  I", 'R', REDSTONE.dust(), '1', ModItems.circuit_aluminium, '2', ModItems.circuit_red_copper, 'P', STEEL.plate(), 'I', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.pellet_antimatter, 1), new Object[] { "###", "###", "###", '#', ModItems.cell_antimatter });
		addRecipeAuto(new ItemStack(ModItems.fluid_tank_empty, 8), new Object[] { "121", "1G1", "121", '1', AL.plate(), '2', IRON.plate(), 'G', KEY_ANYPANE });
		addRecipeAuto(new ItemStack(ModItems.fluid_tank_lead_empty, 4), new Object[] { "LUL", "LTL", "LUL", 'L', PB.plate(), 'U', U238.billet(), 'T', ModItems.fluid_tank_empty });
		addRecipeAuto(new ItemStack(ModItems.fluid_barrel_empty, 2), new Object[] { "121", "1G1", "121", '1', STEEL.plate(), '2', AL.plate(), 'G', KEY_ANYPANE });
		addRecipeAuto(new ItemStack(ModItems.inf_water, 1), new Object[] { "222", "131", "222", '1', Items.water_bucket, '2', AL.plate(), '3', DIAMOND.gem() });
		addRecipeAuto(new ItemStack(ModItems.inf_water_mk2, 1), new Object[] { "BPB", "PTP", "BPB", 'B', ModItems.inf_water, 'P', ModBlocks.fluid_duct, 'T', ModItems.tank_steel });
		

		addRecipeAuto(new ItemStack(ModItems.dynosphere_base), new Object[] { "RPR", "PBP", "RPR", 'R', MINGRADE.dust(), 'P', STEEL.plate(), 'B', REDSTONE.block() });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.dynosphere_desh), new Object[] { "RPR", "PBP", "RPR", 'R', ModItems.powder_desh_mix, 'P', DESH.ingot(), 'B', ModItems.dynosphere_base });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.dynosphere_schrabidium), new Object[] { "RPR", "PBP", "RPR", 'R', ModItems.powder_power, 'P', SA326.ingot(), 'B', ModItems.dynosphere_desh_charged });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.dynosphere_euphemium), new Object[] { "RPR", "PBP", "RPR", 'R', ModItems.powder_nitan_mix, 'P', EUPH.ingot(), 'B', ModItems.dynosphere_schrabidium_charged });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.dynosphere_dineutronium), new Object[] { "RPR", "PBP", "RPR", 'R', ModItems.powder_spark_mix, 'P', DNT.ingot(), 'B', ModItems.dynosphere_euphemium_charged });
		
		//not so Temporary Crappy Recipes
		addRecipeAuto(new ItemStack(ModItems.piston_selenium, 1), new Object[] { "SSS", "STS", " D ", 'S', STEEL.plate(), 'T', W.ingot(), 'D', ModItems.bolt_dura_steel });
		addShapelessAuto(new ItemStack(ModItems.catalyst_clay), new Object[] { IRON.dust(), Items.clay_ball });
		addRecipeAuto(new ItemStack(ModItems.singularity_spark, 1), new Object[] { "XAX", "BCB", "XAX", 'X', ModItems.plate_dineutronium, 'A', ModItems.singularity_counter_resonant, 'B', ModItems.singularity_super_heated, 'C', ModItems.black_hole });
		addRecipeAuto(new ItemStack(ModItems.singularity_spark, 1), new Object[] { "XBX", "ACA", "XBX", 'X', ModItems.plate_dineutronium, 'A', ModItems.singularity_counter_resonant, 'B', ModItems.singularity_super_heated, 'C', ModItems.black_hole });
		addRecipeAuto(new ItemStack(ModItems.ams_core_sing, 1), new Object[] { "EAE", "ASA", "EAE", 'E', ModItems.plate_euphemium, 'A', ModItems.cell_anti_schrabidium, 'S', ModItems.singularity });
		addRecipeAuto(new ItemStack(ModItems.ams_core_wormhole, 1), new Object[] { "DPD", "PSP", "DPD", 'D', ModItems.plate_dineutronium, 'P', ModItems.powder_spark_mix, 'S', ModItems.singularity });
		addRecipeAuto(new ItemStack(ModItems.ams_core_eyeofharmony, 1), new Object[] { "ALA", "LSL", "ALA", 'A', ModItems.plate_dalekanium, 'L', new ItemStack(ModItems.fluid_barrel_full, 1, Fluids.LAVA.getID()), 'S', ModItems.black_hole });
		addRecipeAuto(new ItemStack(ModItems.ams_core_thingy), new Object[] { "NSN", "NGN", "G G", 'N', GOLD.nugget(), 'G', GOLD.ingot(), 'S', ModItems.battery_spark_cell_10000 });
		addRecipeAuto(new ItemStack(ModItems.photo_panel), new Object[] { " G ", "IPI", " C ", 'G', KEY_ANYPANE, 'I', ModItems.plate_polymer, 'P', NETHERQUARTZ.dust(), 'C', ModItems.circuit_aluminium });
		addRecipeAuto(new ItemStack(ModBlocks.machine_satlinker), new Object[] { "PSP", "SCS", "PSP", 'P', STEEL.plate(), 'S', STAR.ingot(), 'C', ModItems.sat_chip });
		addRecipeAuto(new ItemStack(ModBlocks.machine_telelinker), new Object[] { "PSP", "SCS", "PSP", 'P', STEEL.plate(), 'S', ALLOY.ingot(), 'C', ModItems.circuit_red_copper });
		addRecipeAuto(new ItemStack(ModBlocks.machine_keyforge), new Object[] { "PCP", "WSW", "WSW", 'P', STEEL.plate(), 'S', W.ingot(), 'C', ModItems.padlock, 'W', KEY_PLANKS });
		addRecipeAuto(new ItemStack(ModItems.sat_chip), new Object[] { "WWW", "CIC", "WWW", 'W', ModItems.wire_red_copper, 'C', ModItems.circuit_red_copper, 'I', ANY_PLASTIC.ingot() });
		addRecipeAuto(new ItemStack(ModItems.sat_mapper), new Object[] { "H", "B", 'H', ModItems.sat_head_mapper, 'B', ModItems.sat_base });
		addRecipeAuto(new ItemStack(ModItems.sat_scanner), new Object[] { "H", "B", 'H', ModItems.sat_head_scanner, 'B', ModItems.sat_base });
		addRecipeAuto(new ItemStack(ModItems.sat_radar), new Object[] { "H", "B", 'H', ModItems.sat_head_radar, 'B', ModItems.sat_base });
		addRecipeAuto(new ItemStack(ModItems.sat_laser), new Object[] { "H", "B", 'H', ModItems.sat_head_laser, 'B', ModItems.sat_base });
		addRecipeAuto(new ItemStack(ModItems.sat_resonator), new Object[] { "H", "B", 'H', ModItems.sat_head_resonator, 'B', ModItems.sat_base });
		addShapelessAuto(new ItemStack(ModItems.sat_mapper), new Object[] { ModBlocks.sat_mapper });
		addShapelessAuto(new ItemStack(ModItems.sat_scanner), new Object[] { ModBlocks.sat_scanner });
		addShapelessAuto(new ItemStack(ModItems.sat_radar), new Object[] { ModBlocks.sat_radar });
		addShapelessAuto(new ItemStack(ModItems.sat_laser), new Object[] { ModBlocks.sat_laser });
		addShapelessAuto(new ItemStack(ModItems.sat_resonator), new Object[] { ModBlocks.sat_resonator });
		addShapelessAuto(new ItemStack(ModItems.sat_foeq), new Object[] { ModBlocks.sat_foeq });
		addShapelessAuto(new ItemStack(ModItems.geiger_counter), new Object[] { ModBlocks.geiger });
		addShapelessAuto(new ItemStack(ModBlocks.sat_mapper), new Object[] { ModItems.sat_mapper });
		addShapelessAuto(new ItemStack(ModBlocks.sat_scanner), new Object[] { ModItems.sat_scanner });
		addShapelessAuto(new ItemStack(ModBlocks.sat_radar), new Object[] { ModItems.sat_radar });
		addShapelessAuto(new ItemStack(ModBlocks.sat_laser), new Object[] { ModItems.sat_laser });
		addShapelessAuto(new ItemStack(ModBlocks.sat_resonator), new Object[] { ModItems.sat_resonator });
		addShapelessAuto(new ItemStack(ModBlocks.sat_foeq), new Object[] { ModItems.sat_foeq });
		addRecipeAuto(new ItemStack(ModItems.sat_interface), new Object[] { "ISI", "PCP", "PAP", 'I', STEEL.ingot(), 'S', STAR.ingot(), 'P', ModItems.plate_polymer, 'C', ModItems.sat_chip, 'A', ModItems.circuit_gold });
		addRecipeAuto(new ItemStack(ModItems.sat_coord), new Object[] { "SII", "SCA", "SPP", 'I', STEEL.ingot(), 'S', STAR.ingot(), 'P', ModItems.plate_polymer, 'C', ModItems.sat_chip, 'A', ModItems.circuit_red_copper });
		addRecipeAuto(new ItemStack(ModBlocks.machine_spp_bottom), new Object[] { "MDM", "LCL", "LWL", 'M', MAGTUNG.ingot(), 'D', ModItems.plate_desh, 'L', PB.plate(), 'C', ModItems.circuit_gold, 'W', ModItems.coil_magnetized_tungsten });
		addRecipeAuto(new ItemStack(ModBlocks.machine_spp_top), new Object[] { "LWL", "LCL", "MDM", 'M', MAGTUNG.ingot(), 'D', ModItems.plate_desh, 'L', PB.plate(), 'C', ModItems.circuit_gold, 'W', ModItems.coil_magnetized_tungsten });
		addShapelessAuto(new ItemStack(ModBlocks.machine_spp_bottom), new Object[] { ModBlocks.machine_spp_top });
		addShapelessAuto(new ItemStack(ModBlocks.machine_spp_top), new Object[] { ModBlocks.machine_spp_bottom });
		addRecipeAuto(new ItemStack(ModBlocks.machine_transformer), new Object[] { "SDS", "MCM", "MCM", 'S', IRON.ingot(), 'D', MINGRADE.ingot(), 'M',ModItems.coil_advanced_alloy, 'C', ModItems.circuit_copper });
		addRecipeAuto(new ItemStack(ModBlocks.machine_transformer_20), new Object[] { "SDS", "MCM", "MCM", 'S', IRON.ingot(), 'D', MINGRADE.ingot(), 'M', ModItems.coil_copper, 'C', ModItems.circuit_copper });
		addRecipeAuto(new ItemStack(ModBlocks.machine_transformer_dnt), new Object[] { "SDS", "MCM", "MCM", 'S', STAR.ingot(), 'D', DESH.ingot(), 'M', ModBlocks.fwatz_conductor, 'C', ModItems.circuit_targeting_tier6 });
		addRecipeAuto(new ItemStack(ModBlocks.machine_transformer_dnt_20), new Object[] { "SDS", "MCM", "MCM", 'S', STAR.ingot(), 'D', DESH.ingot(), 'M', ModBlocks.fusion_conductor, 'C', ModItems.circuit_targeting_tier6 });
		addRecipeAuto(new ItemStack(ModBlocks.radiobox), new Object[] { "PLP", "PSP", "PLP", 'P', STEEL.plate(), 'S', ModItems.ring_starmetal, 'C', ModItems.fusion_core, 'L', getReflector() });
		addRecipeAuto(new ItemStack(ModBlocks.radiorec), new Object[] { "  W", "PCP", "PIP", 'W', ModItems.wire_copper, 'P', STEEL.plate(), 'C', ModItems.circuit_red_copper, 'I', ANY_PLASTIC.ingot() });
		addRecipeAuto(new ItemStack(ModItems.jackt), new Object[] { "S S", "LIL", "LIL", 'S', STEEL.plate(), 'L', Items.leather, 'I', ModItems.plate_polymer });
		addRecipeAuto(new ItemStack(ModItems.jackt2), new Object[] { "S S", "LIL", "III", 'S', STEEL.plate(), 'L', Items.leather, 'I', ModItems.plate_polymer });
		addRecipeAuto(new ItemStack(ModBlocks.vent_chlorine), new Object[] { "IGI", "ICI", "IDI", 'I', IRON.plate(), 'G', Blocks.iron_bars, 'C', ModItems.pellet_gas, 'D', Blocks.dispenser });
		addRecipeAuto(new ItemStack(ModBlocks.vent_chlorine_seal), new Object[] { "ISI", "SCS", "ISI", 'I', BIGMT.ingot(), 'S', STAR.ingot(), 'C', ModItems.chlorine_pinwheel });
		addRecipeAuto(new ItemStack(ModBlocks.vent_cloud), new Object[] { "IGI", "ICI", "IDI", 'I', IRON.plate(), 'G', Blocks.iron_bars, 'C', ModItems.grenade_cloud, 'D', Blocks.dispenser });
		addRecipeAuto(new ItemStack(ModBlocks.vent_pink_cloud), new Object[] { "IGI", "ICI", "IDI", 'I', IRON.plate(), 'G', Blocks.iron_bars, 'C', ModItems.grenade_pink_cloud, 'D', Blocks.dispenser });
		addRecipeAuto(new ItemStack(ModBlocks.spikes, 4), new Object[] { "FFF", "BBB", "TTT", 'F', Items.flint, 'B', ModItems.bolt_tungsten, 'T', W.ingot() });
		addRecipeAuto(new ItemStack(ModItems.custom_fall, 1), new Object[] { "IIP", "CHW", "IIP", 'I', ModItems.plate_polymer, 'P', BIGMT.plate(), 'C', ModItems.circuit_red_copper, 'H', ModItems.hull_small_steel, 'W', ModItems.coil_copper });
		addRecipeAuto(new ItemStack(ModBlocks.machine_controller, 1), new Object[] { "TDT", "DCD", "TDT", 'T', TCALLOY.ingot(), 'D', ModItems.crt_display, 'C', ModItems.circuit_targeting_tier3 });
		addRecipeAuto(new ItemStack(ModItems.containment_box, 1), new Object[] { "LLL", "LCL", "LLL", 'L', PB.plate(), 'C', Blocks.chest });

		addRecipeAuto(new ItemStack(ModBlocks.absorber, 1), new Object[] { "ICI", "CPC", "ICI", 'I', CU.ingot(), 'C', COAL.dust(), 'P', PB.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.absorber_red, 1), new Object[] { "ICI", "CPC", "ICI", 'I', TI.ingot(), 'C', COAL.dust(), 'P', ModBlocks.absorber });
		addRecipeAuto(new ItemStack(ModBlocks.absorber_green, 1), new Object[] { "ICI", "CPC", "ICI", 'I', ANY_PLASTIC.ingot(), 'C', ModItems.powder_desh_mix, 'P', ModBlocks.absorber_red });
		addRecipeAuto(new ItemStack(ModBlocks.absorber_pink, 1), new Object[] { "ICI", "CPC", "ICI", 'I', BIGMT.ingot(), 'C', ModItems.powder_nitan_mix, 'P', ModBlocks.absorber_green });
		addRecipeAuto(new ItemStack(ModBlocks.decon, 1), new Object[] { "BGB", "SAS", "BSB", 'B', BE.ingot(), 'G', Blocks.iron_bars, 'S', STEEL.ingot(), 'A', ModBlocks.absorber });
		addRecipeAuto(new ItemStack(ModBlocks.machine_amgen, 1), new Object[] { "ITI", "TAT", "ITI", 'I', ALLOY.ingot(), 'T', ModItems.thermo_element, 'A', ModBlocks.absorber });
		addRecipeAuto(new ItemStack(ModBlocks.machine_geo, 1), new Object[] { "ITI", "PCP", "ITI", 'I', DURA.ingot(), 'T', ModItems.thermo_element, 'P', ModItems.board_copper, 'C', ModBlocks.red_wire_coated });
		addRecipeAuto(new ItemStack(ModBlocks.machine_minirtg, 1), new Object[] { "LLL", "PPP", "TRT", 'L', PB.plate(), 'P', PU238.billet(), 'T', ModItems.thermo_element, 'R', ModItems.rtg_unit });
		addRecipeAuto(new ItemStack(ModBlocks.machine_powerrtg, 1), new Object[] { "SRS", "PTP", "SRS", 'S', STAR.ingot(), 'R', ModItems.rtg_unit, 'P', PO210.billet(), 'T', TS.dust() });

		addRecipeAuto(new ItemStack(ModBlocks.pink_planks, 4), new Object[] { "W", 'W', ModBlocks.pink_log });
		addRecipeAuto(new ItemStack(ModBlocks.pink_slab, 6), new Object[] { "WWW", 'W', ModBlocks.pink_planks });
		addRecipeAuto(new ItemStack(ModBlocks.pink_stairs, 6), new Object[] { "W  ", "WW ", "WWW", 'W', ModBlocks.pink_planks });

		addRecipeAuto(new ItemStack(ModItems.door_metal, 1), new Object[] { "II", "SS", "II", 'I', IRON.plate(), 'S', STEEL.plate() });
		addRecipeAuto(new ItemStack(ModItems.door_office, 1), new Object[] { "II", "SS", "II", 'I', KEY_PLANKS, 'S', IRON.plate() });
		addRecipeAuto(new ItemStack(ModItems.door_bunker, 1), new Object[] { "II", "SS", "II", 'I', STEEL.plate(), 'S', PB.plate() });

		addShapelessAuto(new ItemStack(Items.paper, 1), new Object[] { new ItemStack(ModItems.assembly_template, 1, OreDictionary.WILDCARD_VALUE) });
		addShapelessAuto(new ItemStack(Items.paper, 1), new Object[] { new ItemStack(ModItems.chemistry_template, 1, OreDictionary.WILDCARD_VALUE) });
		addShapelessAuto(new ItemStack(Items.slime_ball, 16), new Object[] { new ItemStack(Items.dye, 1, 15), new ItemStack(Items.dye, 1, 15), new ItemStack(Items.dye, 1, 15), new ItemStack(Items.dye, 1, 15), Fluids.SULFURIC_ACID.getDict(1000) });

		for(int i = 1; i < Fluids.getAll().length; ++i) {
    		addShapelessAuto(new ItemStack(ModItems.fluid_duct, 1, i), new Object[] { new ItemStack(ModBlocks.fluid_duct, 1), new ItemStack(ModItems.fluid_identifier, 1, i) });
    		
    		addShapelessAuto(new ItemStack(ModItems.fluid_duct, 8, i), new Object[] { new ItemStack(ModBlocks.fluid_duct, 8), new ItemStack(ModBlocks.fluid_duct, 8), 
    				new ItemStack(ModBlocks.fluid_duct, 8), new ItemStack(ModBlocks.fluid_duct, 8), new ItemStack(ModBlocks.fluid_duct, 8), 
    				new ItemStack(ModBlocks.fluid_duct, 8), new ItemStack(ModBlocks.fluid_duct, 8), new ItemStack(ModBlocks.fluid_duct, 8), new ItemStack(ModItems.fluid_identifier, 1, i) });
    		
    		addShapelessAuto(new ItemStack(ModItems.fluid_duct, 1, i), new Object[] { new ItemStack(ModItems.fluid_duct, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.fluid_identifier, 1, i) });
    		
    		addShapelessAuto(new ItemStack(ModItems.fluid_duct, 8, i), new Object[] { new ItemStack(ModItems.fluid_duct, 8, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.fluid_duct, 8, OreDictionary.WILDCARD_VALUE), 
    				new ItemStack(ModItems.fluid_duct, 8, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.fluid_duct, 8, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.fluid_duct, 8, OreDictionary.WILDCARD_VALUE), 
    				new ItemStack(ModItems.fluid_duct, 8, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.fluid_duct, 8, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.fluid_duct, 8, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.fluid_identifier, 1, i) });
		}

		addShapelessAuto(new ItemStack(ModBlocks.fluid_duct, 1), new Object[] { new ItemStack(ModItems.fluid_duct, 1, OreDictionary.WILDCARD_VALUE) });

		addShapelessAuto(new ItemStack(ModItems.redstone_depleted, 1), new Object[] { new ItemStack(ModItems.battery_su, 1, OreDictionary.WILDCARD_VALUE) });
		addShapelessAuto(new ItemStack(ModItems.redstone_depleted, 2), new Object[] { new ItemStack(ModItems.battery_su_l, 1, OreDictionary.WILDCARD_VALUE) });
		addShapelessAuto(new ItemStack(Items.redstone, 1), new Object[] { ModItems.redstone_depleted, ModItems.redstone_depleted });

		addRecipeAuto(new ItemStack(Blocks.torch, 3), new Object[] { "L", "S", 'L', LIGNITE.gem(), 'S', KEY_STICK });
		addRecipeAuto(new ItemStack(Blocks.torch, 6), new Object[] { "L", "S", 'L', ModItems.briquette_lignite, 'S', KEY_STICK });
		addRecipeAuto(new ItemStack(Blocks.torch, 8), new Object[] { "L", "S", 'L', ANY_COKE.gem(), 'S', KEY_STICK });

		addRecipeAuto(new ItemStack(ModBlocks.machine_missile_assembly, 1), new Object[] { "PWP", "SSS", "CCC", 'P', ModItems.pedestal_steel, 'W', ModItems.wrench, 'S', STEEL.plate(), 'C', ModBlocks.steel_scaffold });
		addRecipeAuto(new ItemStack(ModBlocks.struct_launcher, 4), new Object[] { "PPP", "SDS", "CCC", 'P', STEEL.plate(), 'S', ModBlocks.steel_scaffold, 'D', ModBlocks.deco_pipe_quad, 'C', ModBlocks.concrete_smooth });
		addRecipeAuto(new ItemStack(ModBlocks.struct_launcher, 4), new Object[] { "PPP", "SDS", "CCC", 'P', STEEL.plate(), 'S', ModBlocks.steel_scaffold, 'D', ModBlocks.deco_pipe_quad, 'C', ModBlocks.concrete });
		addRecipeAuto(new ItemStack(ModBlocks.struct_scaffold, 4), new Object[] { "SSS", "DCD", "SSS", 'S', ModBlocks.steel_scaffold, 'D', ModBlocks.fluid_duct, 'C', ModBlocks.red_cable });

		addRecipeAuto(new ItemStack(ModItems.seg_10, 1), new Object[] { "P", "S", "B", 'P', AL.plate(), 'S', ModBlocks.steel_scaffold, 'B', ModBlocks.steel_beam });
		addRecipeAuto(new ItemStack(ModItems.seg_15, 1), new Object[] { "PP", "SS", "BB", 'P', TI.plate(), 'S', ModBlocks.steel_scaffold, 'B', ModBlocks.steel_beam });
		addRecipeAuto(new ItemStack(ModItems.seg_20, 1), new Object[] { "PGP", "SSS", "BBB", 'P', STEEL.plate(), 'G', GOLD.plate(), 'S', ModBlocks.steel_scaffold, 'B', ModBlocks.steel_beam });

		addRecipeAuto(new ItemStack(ModBlocks.obj_tester, 1), new Object[] { "P", "I", "S", 'P', ModItems.polaroid, 'I', ModItems.flame_pony, 'S', STEEL.plate() });

		addRecipeAuto(new ItemStack(ModBlocks.fence_metal, 6), new Object[] { "BIB", "BIB", 'B', Blocks.iron_bars, 'I', Items.iron_ingot });

		addShapelessAuto(new ItemStack(ModBlocks.waste_trinitite), new Object[] { new ItemStack(Blocks.sand, 1, 0), ModItems.trinitite });
		addShapelessAuto(new ItemStack(ModBlocks.waste_trinitite_red), new Object[] { new ItemStack(Blocks.sand, 1, 1), ModItems.trinitite });
		addShapelessAuto(new ItemStack(ModBlocks.sand_uranium, 8), new Object[] { "sand", "sand", "sand", "sand", "sand", "sand", "sand", "sand", U.dust() });
		addShapelessAuto(new ItemStack(ModBlocks.sand_polonium, 8), new Object[] { "sand", "sand", "sand", "sand", "sand", "sand", "sand", "sand", PO210.dust() });
		addShapelessAuto(new ItemStack(ModBlocks.sand_boron, 8), new Object[] { "sand", "sand", "sand", "sand", "sand", "sand", "sand", "sand", B.dust() });
		addShapelessAuto(new ItemStack(ModBlocks.sand_lead, 8), new Object[] { "sand", "sand", "sand", "sand", "sand", "sand", "sand", "sand", PB.dust() });
		addShapelessAuto(new ItemStack(ModBlocks.sand_quartz, 1), new Object[] { "sand", "sand", NETHERQUARTZ.dust(), NETHERQUARTZ.dust() });
		
		addRecipeAuto(new ItemStack(ModItems.rune_blank, 1), new Object[] { "PSP", "SDS", "PSP", 'P', ModItems.powder_magic, 'S', STAR.ingot(), 'D', ModItems.dynosphere_dineutronium_charged });
		addShapelessAuto(new ItemStack(ModItems.rune_isa, 1), new Object[] { ModItems.rune_blank, ModItems.powder_spark_mix, ModItems.singularity_counter_resonant });
		addShapelessAuto(new ItemStack(ModItems.rune_dagaz, 1), new Object[] { ModItems.rune_blank, ModItems.powder_spark_mix, ModItems.singularity });
		addShapelessAuto(new ItemStack(ModItems.rune_hagalaz, 1), new Object[] { ModItems.rune_blank, ModItems.powder_spark_mix, ModItems.singularity_super_heated });
		addShapelessAuto(new ItemStack(ModItems.rune_jera, 1), new Object[] { ModItems.rune_blank, ModItems.powder_spark_mix, ModItems.singularity_spark });
		addShapelessAuto(new ItemStack(ModItems.rune_thurisaz, 1), new Object[] { ModItems.rune_blank, ModItems.powder_spark_mix, ModItems.black_hole });
		addRecipeAuto(new ItemStack(ModItems.ams_lens, 1), new Object[] { "PDP", "GDG", "PDP", 'P', ModItems.plate_dineutronium, 'G', ModBlocks.reinforced_glass, 'D', Blocks.diamond_block });
		addRecipeAuto(new ItemStack(ModItems.ams_catalyst_blank, 1), new Object[] { "TET", "ETE", "TET", 'T', TS.dust(), 'E', EUPH.ingot()});
		addRecipeAuto(new ItemStack(ModItems.ams_focus_limiter, 1), new Object[] { "PDP", "GDG", "PDP", 'P', BIGMT.plate(), 'G', ModBlocks.reinforced_glass, 'D', Blocks.diamond_block });
		addRecipeAuto(new ItemStack(ModItems.ams_muzzle, 1), new Object[] { "GDG", "GDG", "PGP", 'P', BIGMT.plate(), 'G', ModBlocks.reinforced_glass, 'D', Blocks.diamond_block });
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_lithium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_isa, ModItems.rune_isa, ModItems.rune_jera, ModItems.rune_jera, LI.dust(), LI.dust(), LI.dust(), LI.dust() });
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_beryllium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_isa, ModItems.rune_dagaz, ModItems.rune_jera, ModItems.rune_jera, BE.dust(), BE.dust(), BE.dust(), BE.dust() });
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_copper, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_dagaz, ModItems.rune_dagaz, ModItems.rune_jera, ModItems.rune_jera, CU.dust(), CU.dust(), CU.dust(), CU.dust() });
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_cobalt, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_dagaz, ModItems.rune_hagalaz, ModItems.rune_jera, ModItems.rune_jera, CO.dust(), CO.dust(), CO.dust(), CO.dust() });
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_tungsten, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_hagalaz, ModItems.rune_hagalaz, ModItems.rune_jera, ModItems.rune_jera, W.dust(), W.dust(), W.dust(), W.dust() });
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_aluminium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_isa, ModItems.rune_isa, ModItems.rune_jera, ModItems.rune_thurisaz, AL.dust(), AL.dust(), AL.dust(), AL.dust() });
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_iron, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_isa, ModItems.rune_dagaz, ModItems.rune_jera, ModItems.rune_thurisaz, IRON.dust(), IRON.dust(), IRON.dust(), IRON.dust() });
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_strontium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_dagaz, ModItems.rune_dagaz, ModItems.rune_jera, ModItems.rune_thurisaz, ST.dust(), ST.dust(), ST.dust(), ST.dust() });
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_niobium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_dagaz, ModItems.rune_hagalaz, ModItems.rune_jera, ModItems.rune_thurisaz, NB.dust(), NB.dust(), NB.dust(), NB.dust() });
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_cerium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_hagalaz, ModItems.rune_hagalaz, ModItems.rune_jera, ModItems.rune_thurisaz, CE.dust(), CE.dust(), CE.dust(), CE.dust() });
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_caesium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_isa, ModItems.rune_isa, ModItems.rune_thurisaz, ModItems.rune_thurisaz, CS.dust(), CS.dust(), CS.dust(), CS.dust() });
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_thorium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_isa, ModItems.rune_dagaz, ModItems.rune_thurisaz, ModItems.rune_thurisaz, TH232.dust(), TH232.dust(), TH232.dust(), TH232.dust() });
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_euphemium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_dagaz, ModItems.rune_dagaz, ModItems.rune_thurisaz, ModItems.rune_thurisaz, EUPH.dust(), EUPH.dust(), EUPH.dust(), EUPH.dust() });
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_schrabidium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_dagaz, ModItems.rune_hagalaz, ModItems.rune_thurisaz, ModItems.rune_thurisaz, SA326.dust(), SA326.dust(), SA326.dust(), SA326.dust() });
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_dineutronium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_hagalaz, ModItems.rune_hagalaz, ModItems.rune_thurisaz, ModItems.rune_thurisaz, DNT.dust(), DNT.dust(), DNT.dust(), DNT.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.dfc_core, 1), new Object[] { "DLD", "LML", "DLD", 'D', ModItems.ingot_bismuth, 'L', DNT.block(), 'M', KEY_CIRCUIT_BISMUTH });
		addRecipeAuto(new ItemStack(ModBlocks.dfc_emitter, 1), new Object[] { "SDS", "TXL", "SDS", 'S', OSMIRIDIUM.ingot(), 'D', ModItems.plate_desh, 'T', ModBlocks.machine_transformer_dnt, 'X', ModItems.crystal_xen, 'L', ModItems.sat_head_laser });
		addRecipeAuto(new ItemStack(ModBlocks.dfc_receiver, 1), new Object[] { "SDS", "TXL", "SDS", 'S', OSMIRIDIUM.ingot(), 'D', ModItems.plate_desh, 'T', ModBlocks.machine_transformer_dnt, 'X', ModBlocks.sellafield_core, 'L', ModItems.hull_small_steel });
		addRecipeAuto(new ItemStack(ModBlocks.dfc_injector, 1), new Object[] { "SDS", "TXL", "SDS", 'S', OSMIRIDIUM.ingot(), 'D', CMB.plate(), 'T', ModBlocks.machine_fluidtank, 'X', ModItems.motor, 'L', ModItems.pipes_steel });
		addRecipeAuto(new ItemStack(ModBlocks.dfc_stabilizer, 1), new Object[] { "SDS", "TXL", "SDS", 'S', OSMIRIDIUM.ingot(), 'D', ModItems.plate_desh, 'T', ModItems.singularity_spark, 'X', ModItems.magnet_circular, 'L', ModItems.crystal_xen });
		addRecipeAuto(new ItemStack(ModBlocks.barrel_plastic, 1), new Object[] { "IPI", "I I", "IPI", 'I', ModItems.plate_polymer, 'P', AL.plate() });
		addRecipeAuto(new ItemStack(ModBlocks.barrel_iron, 1), new Object[] { "IPI", "I I", "IPI", 'I', IRON.plate(), 'P', IRON.ingot() });
		addShapelessAuto(new ItemStack(ModBlocks.barrel_iron, 1), new Object[] { ModBlocks.barrel_corroded, ANY_TAR.any() });
		addRecipeAuto(new ItemStack(ModBlocks.barrel_steel, 1), new Object[] { "IPI", "ITI", "IPI", 'I', STEEL.plate(), 'P', STEEL.ingot(), 'T', ANY_TAR.any() });
		addRecipeAuto(new ItemStack(ModBlocks.barrel_tcalloy, 1), new Object[] { "IPI", "I I", "IPI", 'I', "ingotTcAlloy", 'P', TI.plate() });
		addRecipeAuto(new ItemStack(ModBlocks.barrel_antimatter, 1), new Object[] { "IPI", "IBI", "IPI", 'I', BIGMT.plate(), 'P', ModItems.coil_advanced_torus, 'B', ModItems.battery_sc_technetium });
		addRecipeAuto(new ItemStack(ModBlocks.tesla, 1), new Object[] { "CCC", "PIP", "WTW", 'C', ModItems.coil_copper, 'I', IRON.ingot(), 'P', ANY_PLASTIC.ingot(), 'T', ModBlocks.machine_transformer, 'W', KEY_PLANKS });
		addRecipeAuto(new ItemStack(ModBlocks.struct_plasma_core, 1), new Object[] { "CBC", "BHB", "CBC", 'C', ModItems.circuit_gold, 'B', ModBlocks.machine_lithium_battery, 'H', ModBlocks.fusion_heater });
		addShapelessAuto(new ItemStack(ModItems.circuit_red_copper, 48), new Object[] { ModBlocks.fusion_core });
		addShapelessAuto(new ItemStack(ModBlocks.fusion_heater), new Object[] { ModBlocks.fusion_hatch });
		addShapelessAuto(new ItemStack(ModItems.energy_core), new Object[] { ModItems.fusion_core, ModItems.fuse });

		addRecipeAuto(new ItemStack(ModItems.upgrade_nullifier, 1), new Object[] { "SPS", "PUP", "SPS", 'S', STEEL.plate(), 'P', ModItems.powder_fire, 'U', ModItems.upgrade_template });
		addRecipeAuto(new ItemStack(ModItems.upgrade_smelter, 1), new Object[] { "PHP", "CUC", "DTD", 'P', CU.plate(), 'H', Blocks.hopper, 'C', ModItems.coil_tungsten, 'U', ModItems.upgrade_template, 'D', ModItems.coil_copper, 'T', ModBlocks.machine_transformer });
		addRecipeAuto(new ItemStack(ModItems.upgrade_shredder, 1), new Object[] { "PHP", "CUC", "DTD", 'P', ModItems.motor, 'H', Blocks.hopper, 'C', ModItems.blades_advanced_alloy, 'U', ModItems.upgrade_smelter, 'D', TI.plate(), 'T', ModBlocks.machine_transformer });
		addRecipeAuto(new ItemStack(ModItems.upgrade_centrifuge, 1), new Object[] { "PHP", "PUP", "DTD", 'P', ModItems.centrifuge_element, 'H', Blocks.hopper, 'U', ModItems.upgrade_shredder, 'D', ANY_PLASTIC.ingot(), 'T', ModBlocks.machine_transformer });
		addRecipeAuto(new ItemStack(ModItems.upgrade_crystallizer, 1), new Object[] { "PHP", "CUC", "DTD", 'P', new ItemStack(ModItems.fluid_barrel_full, 1, Fluids.ACID.getID()), 'H', ModItems.circuit_targeting_tier4, 'C', ModBlocks.barrel_steel, 'U', ModItems.upgrade_centrifuge, 'D', ModItems.motor, 'T', ModBlocks.machine_transformer });
		addRecipeAuto(new ItemStack(ModItems.upgrade_screm, 1), new Object[] { "SUS", "SCS", "SUS", 'S', STEEL.plate(), 'U', ModItems.upgrade_template, 'C', ModItems.crystal_xen });
		addRecipeAuto(new ItemStack(ModItems.upgrade_gc_speed, 1), new Object[] {"GNG", "RUR", "GMG", 'R', RUBBER.ingot(), 'M', ModItems.motor, 'G', ModItems.coil_gold, 'N', TCALLOY.ingot(), 'U', ModItems.upgrade_template}); //TODO: gate this behind the upwards gate of the oil chain when it exists
		
    addRecipeAuto(new ItemStack(ModItems.upgrade_stack, 1, 0), new Object[] { " C ", "PUP", " C ", 'C', ModItems.circuit_aluminium, 'P', DictFrame.fromOne(ModItems.part_generic, EnumPartType.PISTON_PNEUMATIC), 'U', ModItems.upgrade_template });
		addRecipeAuto(new ItemStack(ModItems.upgrade_stack, 1, 1), new Object[] { " C ", "PUP", " C ", 'C', ModItems.circuit_copper, 'P', DictFrame.fromOne(ModItems.part_generic, EnumPartType.PISTON_HYDRAULIC), 'U', new ItemStack(ModItems.upgrade_stack, 1, 0) });
		addRecipeAuto(new ItemStack(ModItems.upgrade_stack, 1, 2), new Object[] { " C ", "PUP", " C ", 'C', ModItems.circuit_red_copper, 'P', DictFrame.fromOne(ModItems.part_generic, EnumPartType.PISTON_ELECTRIC), 'U', new ItemStack(ModItems.upgrade_stack, 1, 1) });
		addRecipeAuto(new ItemStack(ModItems.upgrade_ejector, 1, 0), new Object[] { " C ", "PUP", " C ", 'C', ModItems.plate_copper, 'P', ModItems.motor, 'U', ModItems.upgrade_template });
		addRecipeAuto(new ItemStack(ModItems.upgrade_ejector, 1, 1), new Object[] { " C ", "PUP", " C ", 'C', ModItems.plate_gold, 'P', ModItems.motor, 'U', new ItemStack(ModItems.upgrade_ejector, 1, 0) });
		addRecipeAuto(new ItemStack(ModItems.upgrade_ejector, 1, 2), new Object[] { " C ", "PUP", " C ", 'C', ModItems.plate_saturnite, 'P', ModItems.motor, 'U', new ItemStack(ModItems.upgrade_ejector, 1, 1) });

		addRecipeAuto(new ItemStack(ModItems.mech_key, 1), new Object[] { "MCM", "MKM", "MMM", 'M', ModItems.ingot_meteorite_forged, 'C', ModItems.coin_maskman, 'K', ModItems.key });
		addRecipeAuto(new ItemStack(ModItems.spawn_ufo, 1), new Object[] { "MMM", "DCD", "MMM", 'M', ModItems.ingot_meteorite, 'D', DNT.ingot(), 'C', ModItems.coin_worm });

		addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_alloy, 1), new Object[] { "WWW", "WCW", "WWW", 'W', ModItems.wire_advanced_alloy, 'C', ModBlocks.fusion_conductor });
		addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_gold, 1), new Object[] { "PGP", "PCP", "PGP", 'G', GOLD.dust(), 'C', ModBlocks.hadron_coil_alloy, 'P', IRON.plate() });
		addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_neodymium, 1), new Object[] { "G", "C", "G", 'G', ND.dust(), 'C', ModBlocks.hadron_coil_gold });
		addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_magtung, 1), new Object[] { "WWW", "WCW", "WWW", 'W', ModItems.wire_magnetized_tungsten, 'C', ModBlocks.fwatz_conductor });
		addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_schrabidium, 1), new Object[] { "WWW", "WCW", "WWW", 'W', ModItems.wire_schrabidium, 'C', ModBlocks.hadron_coil_magtung });
		addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_schrabidate, 1), new Object[] { " S ", "SCS", " S ", 'S', SBD.dust(), 'C', ModBlocks.hadron_coil_schrabidium });
		addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_starmetal, 1), new Object[] { "SNS", "SCS", "SNS", 'S', STAR.ingot(), 'N', ModBlocks.hadron_coil_neodymium, 'C', ModBlocks.hadron_coil_schrabidate });
		addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_chlorophyte, 1), new Object[] { "TCT", "TST", "TCT", 'T', ModItems.coil_tungsten, 'C', ModItems.powder_chlorophyte, 'S', ModBlocks.hadron_coil_starmetal });
		addRecipeAuto(new ItemStack(ModBlocks.hadron_diode, 1), new Object[] { "CIC", "ISI", "CIC", 'C', ModBlocks.hadron_coil_alloy, 'I', STEEL.ingot(), 'S', ModItems.circuit_gold });
		addRecipeAuto(new ItemStack(ModBlocks.hadron_plating, 8), new Object[] { "IPI", "P P", "IPI", 'I', STEEL.ingot(), 'P', STEEL.plate() });
		addShapelessAuto(new ItemStack(ModBlocks.hadron_plating_blue, 1), new Object[] { ModBlocks.hadron_plating, KEY_BLUE });
		addShapelessAuto(new ItemStack(ModBlocks.hadron_plating_black, 1), new Object[] { ModBlocks.hadron_plating, KEY_BLACK });
		addShapelessAuto(new ItemStack(ModBlocks.hadron_plating_yellow, 1), new Object[] { ModBlocks.hadron_plating, KEY_YELLOW });
		addShapelessAuto(new ItemStack(ModBlocks.hadron_plating_striped, 1), new Object[] { ModBlocks.hadron_plating, KEY_BLACK, KEY_YELLOW });
		addShapelessAuto(new ItemStack(ModBlocks.hadron_plating_glass, 1), new Object[] { ModBlocks.hadron_plating, KEY_ANYGLASS });
		addShapelessAuto(new ItemStack(ModBlocks.hadron_plating_voltz, 1), new Object[] { ModBlocks.hadron_plating, KEY_RED });
		addRecipeAuto(new ItemStack(ModBlocks.hadron_power, 1), new Object[] { "SFS", "FTF", "SFS", 'S', BIGMT.ingot(), 'T', ModBlocks.machine_transformer, 'F', ModItems.fuse });
		addRecipeAuto(new ItemStack(ModBlocks.hadron_power_10m, 1), new Object[] { "HF", 'H', ModBlocks.hadron_power, 'F', ModItems.fuse });
		addRecipeAuto(new ItemStack(ModBlocks.hadron_power_100m, 1), new Object[] { "HF", 'H', ModBlocks.hadron_power_10m, 'F', ModItems.fuse });
		addRecipeAuto(new ItemStack(ModBlocks.hadron_power_1g, 1), new Object[] { "HF", 'H', ModBlocks.hadron_power_100m, 'F', ModItems.fuse });
		addRecipeAuto(new ItemStack(ModBlocks.hadron_power_10g, 1), new Object[] { "HF", 'H', ModBlocks.hadron_power_1g, 'F', ModItems.fuse });
		addRecipeAuto(new ItemStack(ModBlocks.hadron_analysis, 1), new Object[] { "IPI", "PCP", "IPI", 'I', TI.ingot(), 'P', getReflector(), 'C', ModItems.circuit_gold });
		addShapelessAuto(new ItemStack(ModBlocks.hadron_analysis_glass, 1), new Object[] { ModBlocks.hadron_analysis, KEY_ANYGLASS });
		addRecipeAuto(new ItemStack(ModBlocks.hadron_access, 1), new Object[] { "IGI", "CRC", "IPI", 'I', ModItems.plate_polymer, 'G', KEY_ANYPANE, 'C', ModItems.circuit_aluminium, 'R', REDSTONE.block(), 'P', ModBlocks.hadron_plating_blue });

		addRecipeAuto(new ItemStack(ModItems.ingot_schrabidium, 8), new Object[] { "UUU", "UPU", "UUU", 'U', U.ingot(), 'P', new ItemStack(ModItems.particle_higgs).setStackDisplayName("Higgs Boson (Temporary Recipe)") });
		addRecipeAuto(new ItemStack(ModItems.ingot_euphemium, 8), new Object[] { "UUU", "UPU", "UUU", 'U', PU.ingot(), 'P', new ItemStack(ModItems.particle_dark).setStackDisplayName("Dark Matter (Temporary Recipe)") });
		addRecipeAuto(new ItemStack(ModItems.ingot_dineutronium, 8), new Object[] { "UUU", "UPU", "UUU", 'U', SBD.ingot(), 'P', new ItemStack(ModItems.particle_sparkticle).setStackDisplayName("Sparkticle (Temporary Recipe)") });

		addRecipeAuto(new ItemStack(ModBlocks.fireworks, 1), new Object[] { "PPP", "PPP", "WIW", 'P', Items.paper, 'W', KEY_PLANKS, 'I', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModItems.safety_fuse, 8), new Object[] { "SSS", "SGS", "SSS", 'S', Items.string, 'G', Items.gunpowder });
		
		addRecipeAuto(new ItemStack(ModItems.rbmk_lid, 4), new Object[] { "PPP", "CCC", "PPP", 'P', STEEL.plate(), 'C', ModBlocks.concrete_asbestos });
		addRecipeAuto(new ItemStack(ModItems.rbmk_lid_glass, 4), new Object[] { "LLL", "BBB", "P P", 'P', STEEL.plate(), 'L', ModBlocks.glass_lead, 'B', ModBlocks.glass_boron });
		addRecipeAuto(new ItemStack(ModItems.rbmk_lid_glass, 4), new Object[] { "BBB", "LLL", "P P", 'P', STEEL.plate(), 'L', ModBlocks.glass_lead, 'B', ModBlocks.glass_boron });

		addRecipeAuto(new ItemStack(ModBlocks.rbmk_moderator, 1), new Object[] { " G ", "GRG", " G ", 'G', GRAPHITE.block(), 'R', ModBlocks.rbmk_blank });
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_absorber, 1), new Object[] { "GGG", "GRG", "GGG", 'G', B.ingot(), 'R', ModBlocks.rbmk_blank });
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_reflector, 1), new Object[] { "GGG", "GRG", "GGG", 'G', OreDictManager.getReflector(), 'R', ModBlocks.rbmk_blank });
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_control, 1), new Object[] { " B ", "GRG", " B ", 'G', GRAPHITE.ingot(), 'B', ModItems.motor, 'R', ModBlocks.rbmk_absorber });
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_control_mod, 1), new Object[] { "BGB", "GRG", "BGB", 'G', GRAPHITE.block(), 'R', ModBlocks.rbmk_control, 'B', ModItems.nugget_bismuth });
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_control_auto, 1), new Object[] { "C", "R", "D", 'C', ModItems.circuit_targeting_tier1, 'R', ModBlocks.rbmk_control, 'D', ModItems.crt_display });
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_rod_reasim, 1), new Object[] { "ZCZ", "ZRZ", "ZCZ", 'C', ModItems.hull_small_steel, 'R', ModBlocks.rbmk_blank, 'Z', ZR.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_rod_reasim_mod, 1), new Object[] { "BGB", "GRG", "BGB", 'G', GRAPHITE.block(), 'R', ModBlocks.rbmk_rod_reasim, 'B', TCALLOY.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_outgasser, 1), new Object[] { "GHG", "GRG", "GTG", 'G', ModBlocks.steel_grate, 'H', Blocks.hopper, 'T', ModItems.tank_steel, 'R', ModBlocks.rbmk_blank });
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_storage, 1), new Object[] { "C", "R", "C", 'C', ModBlocks.crate_steel, 'R', ModBlocks.rbmk_blank });
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_loader, 1), new Object[] { "SCS", "CBC", "SCS", 'S', STEEL.plate(), 'C', CU.ingot(), 'B', ModItems.tank_steel });
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_steam_inlet, 1), new Object[] { "SCS", "CBC", "SCS", 'S', STEEL.ingot(), 'C', IRON.plate(), 'B', ModItems.tank_steel });
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_steam_outlet, 1), new Object[] { "SCS", "CBC", "SCS", 'S', STEEL.ingot(), 'C', CU.plate(), 'B', ModItems.tank_steel });
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_heatex, 1), new Object[] { "SCS", "CBC", "SCS", 'S', STEEL.ingot(), 'C', CU.plate(), 'B', ModItems.pipes_steel });

		addRecipeAuto(new ItemStack(ModBlocks.deco_rbmk, 8), new Object[] { "R", 'R', ModBlocks.rbmk_blank });
		addRecipeAuto(new ItemStack(ModBlocks.deco_rbmk_smooth, 1), new Object[] { "R", 'R', ModBlocks.deco_rbmk });
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_blank, 1), new Object[] { "RRR", "R R", "RRR", 'R', ModBlocks.deco_rbmk });
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_blank, 1), new Object[] { "RRR", "R R", "RRR", 'R', ModBlocks.deco_rbmk_smooth });

		addRecipeAuto(new ItemStack(ModBlocks.ladder_sturdy, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.ladder, '#', KEY_PLANKS });
		addRecipeAuto(new ItemStack(ModBlocks.ladder_iron, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.ladder, '#', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.ladder_gold, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.ladder, '#', GOLD.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.ladder_aluminium, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.ladder, '#', AL.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.ladder_copper, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.ladder, '#', CU.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.ladder_titanium, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.ladder, '#', TI.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.ladder_lead, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.ladder, '#', PB.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.ladder_cobalt, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.ladder, '#', CO.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.ladder_steel, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.ladder, '#', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.ladder_tungsten, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.ladder, '#', W.ingot() });

		addRecipeAuto(new ItemStack(ModBlocks.machine_storage_drum), new Object[] { "LLL", "L#L", "LLL", 'L', PB.plate(), '#', ModItems.tank_steel });

		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe, 6), new Object[] { "PPP", 'P', ModItems.hull_small_steel });
		addShapelessAuto(new ItemStack(ModBlocks.deco_pipe, 1), new Object[] { ModBlocks.deco_pipe_rim });
		addShapelessAuto(new ItemStack(ModBlocks.deco_pipe, 1), new Object[] { ModBlocks.deco_pipe_framed });
		addShapelessAuto(new ItemStack(ModBlocks.deco_pipe, 1), new Object[] { ModBlocks.deco_pipe_quad });

		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_rim, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe, 'C', STEEL.plate() });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_quad, 4), new Object[] { "PP", "PP", 'P', ModBlocks.deco_pipe });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_framed, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe, 'C', Blocks.iron_bars });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_framed, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_rim, 'C', Blocks.iron_bars });

		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_rusted, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe, 'C', IRON.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_rim_rusted, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_rim, 'C', IRON.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_quad_rusted, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_quad, 'C', IRON.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_framed_rusted, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_framed, 'C', IRON.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_green, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe, 'C', KEY_GREEN });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_rim_green, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_rim, 'C', KEY_GREEN });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_quad_green, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_quad, 'C', KEY_GREEN });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_framed_green, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_framed, 'C', KEY_GREEN });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_green_rusted, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_green, 'C', IRON.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_rim_green_rusted, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_rim_green, 'C', IRON.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_quad_green_rusted, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_quad_green, 'C', IRON.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_framed_green_rusted, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_framed_green, 'C', IRON.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_red, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe, 'C', KEY_RED });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_rim_red, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_rim, 'C', KEY_RED });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_quad_red, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_quad, 'C', KEY_RED });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_framed_red, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_framed, 'C', KEY_RED });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_marked, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_green, 'C', KEY_GREEN });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_rim_marked, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_rim_green, 'C', KEY_GREEN });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_quad_marked, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_quad_green, 'C', KEY_GREEN });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_framed_marked, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_framed_green, 'C', KEY_GREEN });
		
		addRecipeAuto(new ItemStack(ModBlocks.deco_emitter), new Object[] { "IDI", "DRD", "IDI", 'I', IRON.ingot(), 'D', DIAMOND.gem(), 'R', REDSTONE.block() });

		addRecipeAuto(new ItemStack(Items.name_tag), new Object[] { "SB ", "BPB", " BP", 'S', Items.string, 'B', KEY_SLIME, 'P', Items.paper });
		addRecipeAuto(new ItemStack(Items.name_tag), new Object[] { "SB ", "BPB", " BP", 'S', Items.string, 'B', ANY_TAR.any(), 'P', Items.paper });
		addRecipeAuto(new ItemStack(Items.lead, 4), new Object[] { "RSR", 'R', DictFrame.fromOne(ModItems.plant_item, EnumPlantType.ROPE), 'S', KEY_SLIME });
		addRecipeAuto(new ItemStack(ModItems.rag, 4), new Object[] { "SW", "WS", 'S', Items.string, 'W', Blocks.wool });

		addShapelessAuto(new ItemStack(ModItems.solid_fuel, 10), new Object[] { Fluids.HEATINGOIL.getDict(1000), KEY_TOOL_CHEMISTRYSET });
		addShapelessAuto(new ItemStack(ModItems.canister_full, 2, Fluids.LUBRICANT.getID()), new Object[] { Fluids.HEATINGOIL.getDict(1000), Fluids.UNSATURATEDS.getDict(1000), ModItems.canister_empty, ModItems.canister_empty, KEY_TOOL_CHEMISTRYSET });

		addRecipeAuto(new ItemStack(ModBlocks.machine_condenser), new Object[] { "SIS", "ICI", "SIS", 'S', STEEL.ingot(), 'I', IRON.plate(), 'C', ModItems.board_copper });

		addShapelessAuto(new ItemStack(ModItems.book_guide, 1, BookType.TEST.ordinal()), new Object[] { Items.book, ModItems.canned_jizz });
		addShapelessAuto(new ItemStack(ModItems.book_guide, 1, BookType.RBMK.ordinal()), new Object[] { Items.book, Items.potato });
		addShapelessAuto(new ItemStack(ModItems.book_guide, 1, BookType.HADRON.ordinal()), new Object[] { Items.book, ModItems.fuse });
		addShapelessAuto(new ItemStack(ModItems.book_guide, 1, BookType.STARTER.ordinal()), new Object[] { Items.book, Items.iron_ingot });

		addRecipeAuto(new ItemStack(ModBlocks.charger), new Object[] { "G", "S", "C", 'G', Items.glowstone_dust, 'S', STEEL.ingot(), 'C', ModItems.coil_copper });
		addRecipeAuto(new ItemStack(ModBlocks.charger, 16), new Object[] { "G", "S", "C", 'G', Blocks.glowstone, 'S', STEEL.block(), 'C', ModItems.coil_copper_torus });
		addRecipeAuto(new ItemStack(ModBlocks.press_preheater), new Object[] { "CCC", "SLS", "TST", 'C', ModItems.board_copper, 'S', Blocks.stone, 'L', Fluids.LAVA.getDict(1000), 'T', W.ingot() });
		addRecipeAuto(new ItemStack(ModItems.fluid_identifier_multi), new Object[] { "D", "C", "P", 'D', "dye", 'C', ModItems.circuit_aluminium, 'P', ANY_PLASTIC.ingot() });
		
		addShapelessAuto(new ItemStack(ModItems.holotape_image, 1, EnumHoloImage.HOLO_RESTORED.ordinal()), new Object[] { new ItemStack(ModItems.holotape_image, 1, EnumHoloImage.HOLO_DIGAMMA.ordinal()), KEY_TOOL_SCREWDRIVER, ModItems.ducttape, ModItems.armor_polish });
		addShapelessAuto(new ItemStack(ModItems.holotape_damaged), new Object[] { DictFrame.fromOne(ModItems.holotape_image, EnumHoloImage.HOLO_RESTORED), ModBlocks.muffler, ModItems.crt_display, ModItems.gem_alexandrite /* placeholder for amplifier */ });

		addShapelessAuto(new ItemStack(ModItems.canister_full, 1, Fluids.SMEAR.getID()), new Object[] { ModItems.canister_smear });
		addShapelessAuto(new ItemStack(ModItems.canister_full, 1, Fluids.LUBRICANT.getID()), new Object[] { ModItems.canister_canola });
		addShapelessAuto(new ItemStack(ModItems.canister_full, 1, Fluids.OIL.getID()), new Object[] { ModItems.canister_oil });
		addShapelessAuto(new ItemStack(ModItems.canister_full, 1, Fluids.DIESEL.getID()), new Object[] { ModItems.canister_fuel });
		addShapelessAuto(new ItemStack(ModItems.canister_full, 1, Fluids.KEROSENE.getID()), new Object[] { ModItems.canister_kerosene });
		addShapelessAuto(new ItemStack(ModItems.canister_full, 1, Fluids.RECLAIMED.getID()), new Object[] { ModItems.canister_reoil });
		addShapelessAuto(new ItemStack(ModItems.canister_full, 1, Fluids.PETROIL.getID()), new Object[] { ModItems.canister_petroil });
		addShapelessAuto(new ItemStack(ModItems.canister_full, 1, Fluids.GASOLINE_LEADED.getID()), new Object[] { ModItems.canister_gasoline });
		addShapelessAuto(new ItemStack(ModItems.canister_full, 1, Fluids.FRACKSOL.getID()), new Object[] { ModItems.canister_fracksol });
		addShapelessAuto(new ItemStack(ModItems.canister_full, 1, Fluids.NITAN.getID()), new Object[] { ModItems.canister_NITAN });
		addShapelessAuto(new ItemStack(ModItems.canister_full, 1, Fluids.HEAVYOIL.getID()), new Object[] { ModItems.canister_heavyoil });
		addShapelessAuto(new ItemStack(ModItems.canister_full, 1, Fluids.BITUMEN.getID()), new Object[] { ModItems.canister_bitumen });
		addShapelessAuto(new ItemStack(ModItems.canister_full, 1, Fluids.HEATINGOIL.getID()), new Object[] { ModItems.canister_heatingoil });
		addShapelessAuto(new ItemStack(ModItems.canister_full, 1, Fluids.NAPHTHA.getID()), new Object[] { ModItems.canister_naphtha });
		addShapelessAuto(new ItemStack(ModItems.canister_full, 1, Fluids.LIGHTOIL.getID()), new Object[] { ModItems.canister_lightoil });
		addShapelessAuto(new ItemStack(ModItems.canister_full, 1, Fluids.BIOFUEL.getID()), new Object[] { ModItems.canister_biofuel });
		addShapelessAuto(new ItemStack(ModItems.canister_full, 1, Fluids.ETHANOL.getID()), new Object[] { ModItems.canister_ethanol });

		addRecipeAuto(DictFrame.fromOne(ModItems.part_generic, EnumPartType.PISTON_PNEUMATIC, 4), new Object[] { " I ", "CPC", " I ", 'I', IRON.ingot(), 'C', CU.ingot(), 'P', IRON.plate() });
		addRecipeAuto(DictFrame.fromOne(ModItems.part_generic, EnumPartType.PISTON_HYDRAULIC, 4), new Object[] { " I ", "CPC", " I ", 'I', STEEL.ingot(), 'C', TI.ingot(), 'P', Fluids.LUBRICANT.getDict(1000) });
		addRecipeAuto(DictFrame.fromOne(ModItems.part_generic, EnumPartType.PISTON_ELECTRIC, 4), new Object[] { " I ", "CPC", " I ", 'I', TCALLOY.ingot(), 'C', ANY_PLASTIC.ingot(), 'P', ModItems.motor });
		
		Object[] craneCasing = new Object[] {
				Blocks.stonebrick, 1,
				IRON.ingot(), 2,
				STEEL.ingot(), 4
		};
		
		for(int i = 0; i < craneCasing.length / 2; i++) {
			Object casing = craneCasing[i * 2];
			int amount = (int) craneCasing[i * 2 + 1];
			addRecipeAuto(new ItemStack(ModBlocks.crane_inserter, amount), new Object[] { "CCC", "C C", "CBC", 'C', casing, 'B', ModBlocks.conveyor });
			addRecipeAuto(new ItemStack(ModBlocks.crane_extractor, amount), new Object[] { "CCC", "CPC", "CBC", 'C', casing, 'B', ModBlocks.conveyor, 'P', DictFrame.fromOne(ModItems.part_generic, EnumPartType.PISTON_PNEUMATIC) });
		}
		
		addShapelessAuto(DictFrame.fromOne(ModItems.parts_legendary, EnumLegendaryType.TIER1), new Object[] { ModItems.ingot_chainsteel, ASBESTOS.ingot(), ModItems.gem_alexandrite });
		addShapelessAuto(DictFrame.fromOne(ModItems.parts_legendary, EnumLegendaryType.TIER1, 3), new Object[] { DictFrame.fromOne(ModItems.parts_legendary, EnumLegendaryType.TIER2) });
		addShapelessAuto(DictFrame.fromOne(ModItems.parts_legendary, EnumLegendaryType.TIER2), new Object[] { ModItems.ingot_chainsteel, ModItems.ingot_bismuth, ModItems.gem_alexandrite, ModItems.gem_alexandrite });
		addShapelessAuto(DictFrame.fromOne(ModItems.parts_legendary, EnumLegendaryType.TIER2, 3), new Object[] { DictFrame.fromOne(ModItems.parts_legendary, EnumLegendaryType.TIER3) });
		addShapelessAuto(DictFrame.fromOne(ModItems.parts_legendary, EnumLegendaryType.TIER3), new Object[] { ModItems.ingot_chainsteel, ModItems.ingot_smore, ModItems.gem_alexandrite, ModItems.gem_alexandrite, ModItems.gem_alexandrite });
		
		addShapelessAuto(new ItemStack(ModItems.upgrade_5g), new Object[] { ModItems.upgrade_template, ModItems.gem_alexandrite });
		
		if(GeneralConfig.enableLBSM && GeneralConfig.enableLBSMSimpleCrafting) {
			addShapelessAuto(new ItemStack(ModItems.cordite, 3), new Object[] { ModItems.ballistite, Items.gunpowder, new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE) });
			addShapelessAuto(new ItemStack(ModItems.ingot_semtex, 3), new Object[] { Items.slime_ball, Blocks.tnt, KNO.dust() });
			addShapelessAuto(new ItemStack(ModItems.canister_full, 1, Fluids.DIESEL.getID()), new Object[] { new ItemStack(ModItems.canister_full, 1, Fluids.OIL.getID()), REDSTONE.dust(), ModItems.canister_empty });

			addShapelessAuto(new ItemStack(ModBlocks.ore_uranium, 1), new Object[] { ModBlocks.ore_uranium_scorched, Items.water_bucket });
			addRecipeAuto(new ItemStack(ModBlocks.ore_uranium, 8), new Object[] { "OOO", "OBO", "OOO", 'O', ModBlocks.ore_uranium_scorched, 'B', Items.water_bucket });
			addShapelessAuto(new ItemStack(ModBlocks.ore_nether_uranium, 1), new Object[] { ModBlocks.ore_nether_uranium_scorched, Items.water_bucket });
			addRecipeAuto(new ItemStack(ModBlocks.ore_nether_uranium, 8), new Object[] { "OOO", "OBO", "OOO", 'O', ModBlocks.ore_nether_uranium_scorched, 'B', Items.water_bucket });
			addShapelessAuto(new ItemStack(ModBlocks.ore_gneiss_uranium, 1), new Object[] { ModBlocks.ore_gneiss_uranium_scorched, Items.water_bucket });
			addRecipeAuto(new ItemStack(ModBlocks.ore_gneiss_uranium, 8), new Object[] { "OOO", "OBO", "OOO", 'O', ModBlocks.ore_gneiss_uranium_scorched, 'B', Items.water_bucket });

			addRecipeAuto(new ItemStack(ModItems.plate_iron, 4), new Object[] { "##", "##", '#', IRON.ingot() });
			addRecipeAuto(new ItemStack(ModItems.plate_gold, 4), new Object[] { "##", "##", '#', GOLD.ingot() });
			addRecipeAuto(new ItemStack(ModItems.plate_aluminium, 4), new Object[] { "##", "##", '#', AL.ingot() });
			addRecipeAuto(new ItemStack(ModItems.plate_titanium, 4), new Object[] { "##", "##", '#', TI.ingot() });
			addRecipeAuto(new ItemStack(ModItems.plate_copper, 4), new Object[] { "##", "##", '#', CU.ingot() });
			addRecipeAuto(new ItemStack(ModItems.plate_lead, 4), new Object[] { "##", "##", '#', PB.ingot() });
			addRecipeAuto(new ItemStack(ModItems.plate_steel, 4), new Object[] { "##", "##", '#', STEEL.ingot() });
			addRecipeAuto(new ItemStack(ModItems.plate_schrabidium, 4), new Object[] { "##", "##", '#', SA326.ingot() });
			addRecipeAuto(new ItemStack(ModItems.plate_advanced_alloy, 4), new Object[] { "##", "##", '#', ALLOY.ingot() });
			addRecipeAuto(new ItemStack(ModItems.plate_saturnite, 4), new Object[] { "##", "##", '#', BIGMT.ingot() });
			addRecipeAuto(new ItemStack(ModItems.plate_combine_steel, 4), new Object[] { "##", "##", '#', CMB.ingot() });
			addRecipeAuto(new ItemStack(ModItems.neutron_reflector, 4), new Object[] { "##", "##", '#', W.ingot() });

			addRecipeAuto(new ItemStack(ModItems.wire_aluminium, 16), new Object[] { "###", '#', AL.ingot() });
			addRecipeAuto(new ItemStack(ModItems.wire_copper, 16), new Object[] { "###", '#', CU.ingot() });
			addRecipeAuto(new ItemStack(ModItems.wire_tungsten, 16), new Object[] { "###", '#', W.ingot() });
			addRecipeAuto(new ItemStack(ModItems.wire_red_copper, 16), new Object[] { "###", '#', MINGRADE.ingot() });
			addRecipeAuto(new ItemStack(ModItems.wire_advanced_alloy, 16), new Object[] { "###", '#', ALLOY.ingot() });
			addRecipeAuto(new ItemStack(ModItems.wire_gold, 16), new Object[] { "###", '#', GOLD.ingot() });
			addRecipeAuto(new ItemStack(ModItems.wire_schrabidium, 16), new Object[] { "###", '#', SA326.ingot() });
			
			addRecipeAuto(new ItemStack(ModItems.book_of_), new Object[] { "BGB", "GAG", "BGB", 'B', ModItems.egg_balefire_shard, 'G', GOLD.ingot(), 'A', Items.book });
		}

		if(!GeneralConfig.enable528) {
			addRecipeAuto(new ItemStack(ModBlocks.struct_launcher_core, 1), new Object[] { "SCS", "SIS", "BEB", 'S', ModBlocks.steel_scaffold, 'I', Blocks.iron_bars, 'C', ModItems.circuit_targeting_tier3, 'B', ModBlocks.struct_launcher, 'E', ModBlocks.machine_battery });
			addRecipeAuto(new ItemStack(ModBlocks.struct_launcher_core_large, 1), new Object[] { "SIS", "ICI", "BEB", 'S', ModItems.circuit_red_copper, 'I', Blocks.iron_bars, 'C', ModItems.circuit_targeting_tier4, 'B', ModBlocks.struct_launcher, 'E', ModBlocks.machine_battery });
			addRecipeAuto(new ItemStack(ModBlocks.struct_soyuz_core, 1), new Object[] { "CUC", "TST", "TBT", 'C', ModItems.circuit_targeting_tier4, 'U', ModItems.upgrade_power_3, 'T', ModBlocks.barrel_steel, 'S', ModBlocks.steel_scaffold, 'B', ModBlocks.machine_lithium_battery });
			addRecipeAuto(new ItemStack(ModItems.reactor_sensor, 1), new Object[] { "WPW", "CMC", "PPP", 'W', ModItems.wire_tungsten, 'P', PB.plate(), 'C', ModItems.circuit_targeting_tier3, 'M', ModItems.magnetron });
			addRecipeAuto(new ItemStack(ModBlocks.reactor_ejector, 1), new Object[] { "CLC", "MHM", "CLC", 'C', ModBlocks.brick_concrete, 'L', PB.plate(), 'M', ModItems.motor, 'H', ModBlocks.reactor_hatch });
			addRecipeAuto(new ItemStack(ModBlocks.reactor_inserter, 1), new Object[] { "CLC", "MHM", "CLC", 'C', ModBlocks.brick_concrete, 'L', CU.plate(), 'M', ModItems.motor, 'H', ModBlocks.reactor_hatch });
			addRecipeAuto(new ItemStack(ModBlocks.rbmk_console, 1), new Object[] { "BBB", "DGD", "DCD", 'B', B.ingot(), 'D', ModBlocks.deco_rbmk, 'G', KEY_ANYPANE, 'C', ModItems.circuit_targeting_tier3 });
			addRecipeAuto(new ItemStack(ModBlocks.rbmk_crane_console, 1), new Object[] { "BCD", "DDD", 'B', B.ingot(), 'D', ModBlocks.deco_rbmk, 'C', ModItems.circuit_targeting_tier3 });
			addRecipeAuto(new ItemStack(ModBlocks.hadron_core, 1), new Object[] { "CCC", "DSD", "CCC", 'C', ModBlocks.hadron_coil_alloy, 'D', ModBlocks.hadron_diode, 'S', ModItems.circuit_schrabidium });
			addRecipeAuto(new ItemStack(ModBlocks.rbmk_rod, 1), new Object[] { "C", "R", "C", 'C', ModItems.hull_small_steel, 'R', ModBlocks.rbmk_blank });
			addRecipeAuto(new ItemStack(ModBlocks.rbmk_rod_mod, 1), new Object[] { "BGB", "GRG", "BGB", 'G', GRAPHITE.block(), 'R', ModBlocks.rbmk_rod, 'B', ModItems.nugget_bismuth });
			addRecipeAuto(new ItemStack(ModBlocks.rbmk_boiler, 1), new Object[] { "CPC", "CRC", "CPC", 'C', ModItems.board_copper, 'P', ModItems.pipes_steel, 'R', ModBlocks.rbmk_blank });
			addRecipeAuto(new ItemStack(ModBlocks.rbmk_heater, 1), new Object[] { "CIC", "PRP", "CIC", 'C', ModItems.board_copper, 'P', ModItems.pipes_steel, 'R', ModBlocks.rbmk_blank, 'I', ANY_PLASTIC.ingot() });
			addRecipeAuto(new ItemStack(ModBlocks.rbmk_cooler, 1), new Object[] { "IGI", "GCG", "IGI", 'C', ModBlocks.rbmk_blank, 'I', ModItems.plate_polymer, 'G', ModBlocks.steel_grate });
		}
		
		addShapelessAuto(ModItems.circuit_star_component.stackFromEnum(CircuitComponentType.CHIPSET), new Object[] {
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.BRIDGE_BIOS),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.BRIDGE_BUS),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.BRIDGE_CHIPSET),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.BRIDGE_CMOS),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.BRIDGE_IO),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.BRIDGE_NORTH),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.BRIDGE_SOUTH)
		});
		
		addShapelessAuto(ModItems.circuit_star_component.stackFromEnum(CircuitComponentType.CPU), new Object[] {
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.CPU_CACHE),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.CPU_CLOCK),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.CPU_EXT),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.CPU_LOGIC),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.CPU_REGISTER),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.CPU_SOCKET)
		});
		
		addShapelessAuto(ModItems.circuit_star_component.stackFromEnum(CircuitComponentType.RAM), new Object[] {
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.MEM_SOCKET),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.MEM_16K_A),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.MEM_16K_B),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.MEM_16K_C),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.MEM_16K_D)
		});
		
		addShapelessAuto(new ItemStack(ModItems.circuit_star), new Object[] {
				ModItems.circuit_star_component.stackFromEnum(CircuitComponentType.CHIPSET),
				ModItems.circuit_star_component.stackFromEnum(CircuitComponentType.CPU),
				ModItems.circuit_star_component.stackFromEnum(CircuitComponentType.RAM),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.BOARD_TRANSISTOR),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.BOARD_BLANK)
		});
	}
	
	public static void crumple() {
		
		if(Loader.isModLoaded("Mekanism")) {
			
			List<IRecipe> toDestroy = new ArrayList();
			
			Block mb = (Block) Block.blockRegistry.getObject("Mekanism:MachineBlock");
			ItemStack digiminer = new ItemStack(mb, 1, 4);
			
			for(Object o : net.minecraft.item.crafting.CraftingManager.getInstance().getRecipeList()) {
				
				if(o instanceof IRecipe) {
					IRecipe rec = (IRecipe)o;
					ItemStack stack = rec.getRecipeOutput();
					
					if(stack != null && stack.getItem() == digiminer.getItem() && stack.getItemDamage() == digiminer.getItemDamage()) {
						toDestroy.add(rec);
					}
				}
			}
			
			if(toDestroy.size() > 0) {
				net.minecraft.item.crafting.CraftingManager.getInstance().getRecipeList().removeAll(toDestroy);
			}
		}
	}
	
	//option 1: find every entry that needs to be ore dicted and change the recipe method by hand and commit to doing it right in the future
	//option 2: just make the computer do all the stupid work for us
	public static void addRecipeAuto(ItemStack result, Object... ins) {
		
		boolean shouldUseOD = false;
		boolean engage = false;
		
		for(int i = 0; i < ins.length; i++) {
			Object ingredient = ins[i];
			
			if(ingredient instanceof String) {
				
				if(engage) {
					shouldUseOD = true;
					break;
				}
			} else {
				engage = true;
			}
		}
		
		if(shouldUseOD)
			GameRegistry.addRecipe(new ShapedOreRecipe(result, ins));
		else
			GameRegistry.addRecipe(result, ins);
	}
	
	public static void addShapelessAuto(ItemStack result, Object... ins) {
		
		boolean shouldUseOD = false;
		
		for(int i = 0; i < ins.length; i ++) {
			Object ingredient = ins[i];
			
			if(ingredient instanceof String) {
				shouldUseOD = true;
				break;
			}
		}
		
		if(shouldUseOD)
			GameRegistry.addRecipe(new ShapelessOreRecipe(result, ins));
		else
			GameRegistry.addShapelessRecipe(result, ins);
	}
}

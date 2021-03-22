package com.hbm.main;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.crafting.ArmorRecipes;
import com.hbm.crafting.ConsumableRecipes;
import com.hbm.crafting.MineralRecipes;
import com.hbm.crafting.PowderRecipes;
import com.hbm.crafting.RodRecipes;
import com.hbm.crafting.ToolRecipes;
import com.hbm.crafting.WeaponRecipes;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemBattery;
import com.hbm.items.special.ItemHot;
import com.hbm.util.EnchantmentUtil;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

public class CraftingManager {
	
	public static void mainRegistry()
	{
		AddCraftingRec();
		AddSmeltingRec();
		
		MineralRecipes.register();
		RodRecipes.register();
		ToolRecipes.register();
		ArmorRecipes.register();
		WeaponRecipes.register();
		ConsumableRecipes.register();
		PowderRecipes.register();
	}

	public static void AddCraftingRec() {
		
		GameRegistry.addRecipe(new ItemStack(ModItems.redstone_sword, 1), new Object[] { "R", "R", "S", 'R', Blocks.redstone_block, 'S', Items.stick });
		GameRegistry.addRecipe(new ItemStack(ModItems.big_sword, 1), new Object[] { "QIQ", "QIQ", "GSG", 'G', Items.gold_ingot, 'S', Items.stick, 'I', Items.iron_ingot, 'Q', Items.quartz});
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.board_copper, 1), new Object[] { "TTT", "TTT", 'T', "plateCopper" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.hazmat_cloth_red, 1), new Object[] { "C", "R", "C", 'C', ModItems.hazmat_cloth, 'R', "dustRedstone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.hazmat_cloth_grey, 1), new Object[] { " P ", "ICI", " L ", 'C', ModItems.hazmat_cloth_red, 'P', "plateIron", 'L', "plateLead", 'I', ModItems.plate_polymer }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.asbestos_cloth, 8), new Object[] { "SCS", "CPC", "SCS", 'S', Items.string, 'P', ModItems.powder_bromine, 'C', Blocks.wool }));
		GameRegistry.addRecipe(new ItemStack(ModItems.bolt_dura_steel, 4), new Object[] { "D", "D", 'D', ModItems.ingot_dura_steel});
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.pipes_steel, 1), new Object[] { "B", "B", "B", 'B', "blockSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.bolt_tungsten, 4), new Object[] { "D", "D", 'D', "ingotTungsten" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.bolt_compound, 1), new Object[] { "PDP", "PTP", "PDP", 'D', ModItems.bolt_dura_steel, 'T', ModItems.bolt_tungsten, 'P', "plateTitanium" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.pellet_coal, 1), new Object[] { "PFP", "FOF", "PFP", 'P', "dustCoal", 'F', Items.flint, 'O', ModBlocks.gravel_obsidian }));
		GameRegistry.addRecipe(new ItemStack(ModItems.plate_polymer, 8), new Object[] { "DD", 'D', ModItems.ingot_polymer});
		GameRegistry.addRecipe(new ItemStack(ModItems.plate_polymer, 16), new Object[] { "DD", 'D', ModItems.ingot_fiberglass});
		GameRegistry.addRecipe(new ItemStack(ModItems.plate_polymer, 16), new Object[] { "DD", 'D', ModItems.ingot_asbestos});
		GameRegistry.addRecipe(new ItemStack(ModItems.plate_polymer, 4), new Object[] { "SWS", 'S', Items.string, 'W', Blocks.wool });
		GameRegistry.addRecipe(new ItemStack(ModItems.plate_polymer, 4), new Object[] { "BB", 'B', Items.brick });

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.marker_structure, 1), new Object[] { "L", "G", "R", 'L', "dustLapis", 'G', Items.glowstone_dust, 'R', Blocks.redstone_torch }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.circuit_raw, 1), new Object[] { "A", "R", "S", 'S', "plateSteel", 'R', "dustRedstone", 'A', ModItems.wire_aluminium }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.circuit_targeting_tier1, 1), new Object[] { "CPC", 'C', ModItems.circuit_aluminium, 'P', "dustRedstone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.circuit_targeting_tier2, 1), new Object[] { "CPC", 'C', ModItems.circuit_copper, 'P', "dustNetherQuartz" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.circuit_targeting_tier3, 1), new Object[] { "CPC", 'C', ModItems.circuit_red_copper, 'P', "dustGold" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.circuit_targeting_tier4, 1), new Object[] { "CPC", 'C', ModItems.circuit_gold, 'P', "dustLapis" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.circuit_targeting_tier5, 1), new Object[] { "CPC", 'C', ModItems.circuit_schrabidium, 'P', "dustDiamond" }));
		GameRegistry.addRecipe(new ItemStack(ModItems.circuit_targeting_tier6, 1), new Object[] { "P", "D", "C", 'C', ModItems.circuit_targeting_tier5, 'D', ModItems.battery_potatos, 'P', ModItems.powder_spark_mix });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.circuit_aluminium, 2), new Object[] { ModItems.circuit_targeting_tier1 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.circuit_copper, 2), new Object[] { ModItems.circuit_targeting_tier2 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.circuit_red_copper, 2), new Object[] { ModItems.circuit_targeting_tier3 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.circuit_gold, 2), new Object[] { ModItems.circuit_targeting_tier4 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.circuit_schrabidium, 2), new Object[] { ModItems.circuit_targeting_tier5 });

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.cell_empty, 6), new Object[] { "SSS", "G G", "SSS", 'S', "plateSteel", 'G', "paneGlass" }));
		GameRegistry.addRecipe(new ItemStack(ModItems.cell_deuterium, 8), new Object[] { "DDD", "DTD", "DDD", 'D', ModItems.cell_empty, 'T', ModItems.mike_deut });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.particle_empty, 2), new Object[] { "STS", "G G", "STS", 'S', "plateSteel", 'T', "ingotTungsten", 'G', "paneGlass" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.particle_copper, 1), new Object[] { ModItems.particle_empty, "dustCopper", ModItems.pellet_charged }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.particle_lead, 1), new Object[] { ModItems.particle_empty, "dustLead", ModItems.pellet_charged }));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.cell_antimatter, 1), new Object[] { ModItems.particle_aproton, ModItems.particle_aelectron, ModItems.cell_empty });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.particle_amat, 1), new Object[] { ModItems.particle_aproton, ModItems.particle_aelectron, ModItems.particle_empty });

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.canister_empty, 2), new Object[] { "S ", "AA", "AA", 'S', "plateSteel", 'A', "plateAluminum" }));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.yellow_barrel, 1), new Object[] { "DDD", "DTD", "DDD", 'D', ModItems.nuclear_waste, 'T', ModItems.tank_steel });
		GameRegistry.addRecipe(new ItemStack(ModItems.nuclear_waste, 8), new Object[] { "B", 'B', ModBlocks.yellow_barrel });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gas_empty, 2), new Object[] { "S ", "AA", "AA", 'A', "plateSteel", 'S', "plateCopper" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.vitrified_barrel, 1), new Object[] { "LSL", "PWP", "LSL", 'L', "plateLead", 'S', Blocks.sand, 'P', "ingotPolymer", 'W', ModBlocks.block_waste }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModBlocks.block_waste_painted, 1), new Object[] { "dyeYellow", ModBlocks.block_waste }));


		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_aluminium, 1), new Object[] { "###", "###", "###", '#', ModItems.wire_aluminium });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_copper, 1), new Object[] { "###", "###", "###", '#', ModItems.wire_copper });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_tungsten, 1), new Object[] { "###", "###", "###", '#', ModItems.wire_tungsten });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_red_copper, 1), new Object[] { "###", "###", "###", '#', ModItems.wire_red_copper });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_advanced_alloy, 1), new Object[] { "###", "###", "###", '#', ModItems.wire_advanced_alloy });
		GameRegistry.addRecipe(new ItemStack(Items.gold_ingot, 1), new Object[] { "###", "###", "###", '#', ModItems.wire_gold });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_schrabidium, 1), new Object[] { "###", "###", "###", '#', ModItems.wire_schrabidium });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_magnetized_tungsten, 1), new Object[] { "###", "###", "###", '#', ModItems.wire_magnetized_tungsten });

		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.biomass, 4), new Object[] { Items.melon, Items.melon, Items.melon, Items.melon, Items.melon, Items.melon, Items.melon });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.biomass, 4), new Object[] { Items.apple, Items.apple, Items.apple, Items.apple, Items.apple, Items.apple, Items.apple, Items.apple, Items.apple });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.biomass, 4), new Object[] { Items.reeds, Items.reeds, Items.reeds, Items.reeds, Items.reeds, Items.reeds, Items.reeds, Items.reeds, Items.reeds });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.biomass, 4), new Object[] { Items.rotten_flesh, Items.rotten_flesh, Items.rotten_flesh, Items.rotten_flesh, Items.rotten_flesh, Items.rotten_flesh, Items.rotten_flesh });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.biomass, 4), new Object[] { Items.carrot, Items.carrot, Items.carrot, Items.carrot, Items.carrot, Items.carrot, Items.carrot, Items.carrot, Items.carrot });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.biomass, 4), new Object[] { Items.potato, Items.potato, Items.potato, Items.potato, Items.potato, Items.potato, Items.potato, Items.potato, Items.potato });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.biomass, 4), new Object[] { Items.poisonous_potato, Items.poisonous_potato, Items.poisonous_potato, Items.poisonous_potato, Items.poisonous_potato, Items.poisonous_potato, Items.poisonous_potato, Items.poisonous_potato, Items.poisonous_potato });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.biomass, 4), new Object[] { Items.potato, Items.potato, Items.potato, Items.potato, Items.potato, Items.potato, Items.potato, Items.potato, Items.potato });
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.biomass, 4), new Object[] { "treeSapling", "treeSapling", "treeSapling", "treeSapling", "treeSapling", "treeSapling", "treeSapling" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.biomass, 4), new Object[] { "treeLeaves", "treeLeaves", "treeLeaves", "treeLeaves", "treeLeaves" }));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.biomass, 8), new Object[] { Blocks.pumpkin, Blocks.pumpkin, Blocks.pumpkin, Blocks.pumpkin, Blocks.pumpkin, Blocks.pumpkin });
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.biomass, 6), new Object[] { "logWood", "logWood", "logWood" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.biomass, 4), new Object[] { "plankWood", "plankWood", "plankWood", "plankWood", "plankWood", "plankWood", "plankWood", "plankWood", "plankWood" }));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.biomass, 8), new Object[] { Blocks.hay_block, Blocks.hay_block });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.biomass, 1), new Object[] { Items.wheat_seeds, Items.wheat_seeds, Items.wheat_seeds, Items.wheat_seeds, Items.wheat_seeds, Items.wheat_seeds, Items.wheat_seeds, Items.wheat_seeds, Items.wheat_seeds });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.biomass, 2), new Object[] { Items.pumpkin_seeds, Items.pumpkin_seeds, Items.pumpkin_seeds, Items.pumpkin_seeds, Items.pumpkin_seeds, Items.pumpkin_seeds, Items.pumpkin_seeds, Items.pumpkin_seeds, Items.pumpkin_seeds });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.biomass, 2), new Object[] { Items.melon_seeds, Items.melon_seeds, Items.melon_seeds, Items.melon_seeds, Items.melon_seeds, Items.melon_seeds, Items.melon_seeds, Items.melon_seeds, Items.melon_seeds });

		//GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.part_lithium), new Object[] { "P", "D", "P", 'P', "plateSteel", 'D', "dustLithium" }));
		//GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.part_beryllium), new Object[] { "P", "D", "P", 'P', "plateSteel", 'D', "dustBeryllium" }));
		//GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.part_carbon), new Object[] { "P", "D", "P", 'P', "plateSteel", 'D', "dustCoal" }));
		//GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.part_copper), new Object[] { "P", "D", "P", 'P', "plateSteel", 'D', "dustCopper" }));
		//GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.part_plutonium), new Object[] { "P", "D", "P", 'P', "plateSteel", 'D', "dustPlutonium" }));

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModBlocks.deco_aluminium), new Object[] { "ingotAluminum", ModBlocks.steel_scaffold }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModBlocks.deco_beryllium), new Object[] { "ingotBeryllium", ModBlocks.steel_scaffold }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModBlocks.deco_lead), new Object[] { "ingotLead", ModBlocks.steel_scaffold }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModBlocks.deco_red_copper), new Object[] { "ingotRedCopperAlloy", ModBlocks.steel_scaffold }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModBlocks.deco_steel), new Object[] { "ingotSteel", ModBlocks.steel_scaffold }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModBlocks.deco_titanium), new Object[] { "ingotTitanium", ModBlocks.steel_scaffold }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModBlocks.deco_tungsten), new Object[] { "ingotTungsten", ModBlocks.steel_scaffold }));

		//GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.pellet_rtg, 1), new Object[] { "IPI", "PPP", "IPI", 'I', "plateIron", 'P', "tinyPu238" }));
		//GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.pellet_rtg_weak, 1), new Object[] { "IUI", "UPU", "IUI", 'I', "plateIron", 'P', "tinyPu238", 'U', "tinyU238" }));
		//GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.tritium_deuterium_cake, 1), new Object[] { "DLD", "LTL", "DLD", 'L', "ingotLithium", 'D', ModItems.cell_deuterium, 'T', ModItems.cell_tritium }));

		//GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.pellet_schrabidium, 1), new Object[] { "IPI", "PPP", "IPI", 'I', "plateIron", 'P', "ingotSchrabidium" }));
		//GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.pellet_hes, 1), new Object[] { "IPI", "PPP", "IPI", 'I', "plateIron", 'P', ModItems.ingot_hes }));
		//GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.pellet_mes, 1), new Object[] { "IPI", "PPP", "IPI", 'I', "plateIron", 'P', ModItems.ingot_schrabidium_fuel }));
		//GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.pellet_les, 1), new Object[] { "IPI", "PPP", "IPI", 'I', "plateIron", 'P', ModItems.ingot_les }));
		//GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.pellet_beryllium, 1), new Object[] { "IPI", "PPP", "IPI", 'I', "plateIron", 'P', ModItems.ingot_beryllium }));
		//GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.pellet_neptunium, 1), new Object[] { "IPI", "PPP", "IPI", 'I', "plateIron", 'P', "ingotNeptunium" }));
		//GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.pellet_lead, 1), new Object[] { "IPI", "PPP", "IPI", 'I', "plateIron", 'P', "ingotLead" }));
		//GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.pellet_advanced, 1), new Object[] { "IPI", "PPP", "IPI", 'I', "plateIron", 'P', ModItems.ingot_advanced_alloy }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.coil_copper, 1), new Object[] { "WWW", "WIW", "WWW", 'W', ModItems.wire_red_copper, 'I', "ingotIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.coil_advanced_alloy, 1), new Object[] { "WWW", "WIW", "WWW", 'W', ModItems.wire_advanced_alloy, 'I', "ingotIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.coil_gold, 1), new Object[] { "WWW", "WIW", "WWW", 'W', ModItems.wire_gold, 'I', "ingotIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.coil_copper_torus, 2), new Object[] { " C ", "CPC", " C ", 'P', "plateIron", 'C', ModItems.coil_copper }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.coil_advanced_torus, 2), new Object[] { " C ", "CPC", " C ", 'P', "plateIron", 'C', ModItems.coil_advanced_alloy }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.coil_gold_torus, 2), new Object[] { " C ", "CPC", " C ", 'P', "plateIron", 'C', ModItems.coil_gold }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.coil_copper_torus, 2), new Object[] { " C ", "CPC", " C ", 'P', "plateSteel", 'C', ModItems.coil_copper }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.coil_advanced_torus, 2), new Object[] { " C ", "CPC", " C ", 'P', "plateSteel", 'C', ModItems.coil_advanced_alloy }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.coil_gold_torus, 2), new Object[] { " C ", "CPC", " C ", 'P', "plateSteel", 'C', ModItems.coil_gold }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.coil_tungsten, 1), new Object[] { "WWW", "WIW", "WWW", 'W', ModItems.wire_tungsten, 'I', "ingotIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.coil_magnetized_tungsten, 1), new Object[] { "WWW", "WIW", "WWW", 'W', ModItems.wire_magnetized_tungsten, 'I', "ingotIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.tank_steel, 2), new Object[] { "STS", "S S", "STS", 'S', "plateSteel", 'T', "plateTitanium" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.motor, 2), new Object[] { " R ", "ICI", "ITI", 'R', ModItems.wire_red_copper, 'T', ModItems.coil_copper_torus, 'I', "plateIron", 'C', ModItems.coil_copper }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.motor, 2), new Object[] { " R ", "ICI", " T ", 'R', ModItems.wire_red_copper, 'T', ModItems.coil_copper_torus, 'I', "plateSteel", 'C', ModItems.coil_copper }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.motor_desh, 1), new Object[] { "PCP", "DMD", "PCP", 'P', ModItems.ingot_polymer, 'C', ModItems.coil_gold_torus, 'D', "ingotDesh", 'M', ModItems.motor }));
		//GameRegistry.addRecipe(new ItemStack(ModItems.centrifuge_element, 1), new Object[] { " T ", "WTW", "RMR", 'R', ModItems.wire_red_copper, 'T', ModItems.tank_steel, 'M', ModItems.motor, 'W', ModItems.coil_tungsten });
		//GameRegistry.addRecipe(new ItemStack(ModItems.centrifuge_tower, 1), new Object[] { "LL", "EE", "EE", 'E', ModItems.centrifuge_element, 'L', new ItemStack(Items.dye, 1, 4) });
		//GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.reactor_core, 1), new Object[] { "LNL", "N N", "LNL", 'N', "plateDenseLead", 'L', "plateLead" }));
		//GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.rtg_unit, 1), new Object[] { "TIT", "PCP", "TIT", 'T', ModItems.thermo_element, 'I', "ingotLead", 'P', ModItems.board_copper, 'C', ModItems.circuit_copper }));
		//GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.thermo_unit_empty, 1), new Object[] { "TTT", " S ", "P P", 'S', "ingotSteel", 'P', "plateTitanium", 'T', ModItems.coil_copper_torus }));
		GameRegistry.addRecipe(new ItemStack(ModItems.thermo_unit_endo, 1), new Object[] { "EEE", "ETE", "EEE", 'E', Item.getItemFromBlock(Blocks.ice), 'T', ModItems.thermo_unit_empty });
		GameRegistry.addRecipe(new ItemStack(ModItems.thermo_unit_exo, 1), new Object[] { "LLL", "LTL", "LLL", 'L', Items.lava_bucket, 'T', ModItems.thermo_unit_empty });
		//GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.levitation_unit, 1), new Object[] { "CSC", "TAT", "PSP", 'C', ModItems.coil_copper, 'S', ModItems.nugget_schrabidium, 'T', ModItems.coil_tungsten, 'P', "plateTitanium", 'A', "ingotSteel" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.cap_aluminium, 1), new Object[] { "PIP", 'P', "plateAluminum", 'I', "ingotAluminum" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.hull_small_steel, 1), new Object[] { "PPP", "   ", "PPP", 'P', "plateSteel", 'I', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.hull_small_aluminium, 1), new Object[] { "PPP", "   ", "PPP", 'P', "plateAluminum", 'I', "ingotAluminum" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.hull_big_steel, 1), new Object[] { "III", "   ", "III", 'P', "plateSteel", 'I', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.hull_big_aluminium, 1), new Object[] { "III", "   ", "III", 'P', "plateAluminum", 'I', "ingotAluminum" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.hull_big_titanium, 1), new Object[] { "III", "   ", "III", 'P', "plateTitanium", 'I', "ingotTitanium" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.fins_flat, 1), new Object[] { "IP", "PP", "IP", 'P', "plateSteel", 'I', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.fins_small_steel, 1), new Object[] { " PP", "PII", " PP", 'P', "plateSteel", 'I', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.fins_big_steel, 1), new Object[] { " PI", "III", " PI", 'P', "plateSteel", 'I', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.fins_tri_steel, 1), new Object[] { " PI", "IIB", " PI", 'P', "plateSteel", 'I', "ingotSteel", 'B', "blockSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.fins_quad_titanium, 1), new Object[] { " PP", "III", " PP", 'P', "plateTitanium", 'I', "ingotTitanium" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.sphere_steel, 1), new Object[] { "PIP", "I I", "PIP", 'P', "plateSteel", 'I', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.pedestal_steel, 1), new Object[] { "P P", "P P", "III", 'P', "plateSteel", 'I', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.lemon, 1), new Object[] { " D ", "DSD", " D ", 'D', new ItemStack(Items.dye, 1, 11), 'S', "stone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.blade_titanium, 2), new Object[] { "TP", "TP", "TT", 'P', "plateTitanium", 'T', "ingotTitanium" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.turbine_titanium, 1), new Object[] { "BBB", "BSB", "BBB", 'B', ModItems.blade_titanium, 'S', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.rotor_steel, 3), new Object[] { "CCC", "SSS", "CCC", 'C', ModItems.coil_gold, 'S', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.generator_steel, 1), new Object[] { "RRR", "CCC", "SSS", 'C', ModItems.coil_gold_torus, 'S', "ingotSteel", 'R', ModItems.rotor_steel }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.shimmer_head, 1), new Object[] { "SSS", "DTD", "SSS", 'S', "ingotSteel", 'D', "blockDesh", 'T', "blockTungsten" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.shimmer_axe_head, 1), new Object[] { "PII", "PBB", "PII", 'P', "plateSteel", 'B', "blockDesh", 'I', "ingotTungsten" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.shimmer_handle, 1), new Object[] { "GP", "GP", "GP", 'G', "plateGold", 'P', "ingotPolymer" }));
		GameRegistry.addRecipe(new ItemStack(ModItems.shimmer_sledge, 1), new Object[] { "H", "G", "G", 'G', ModItems.shimmer_handle, 'H', ModItems.shimmer_head });
		GameRegistry.addRecipe(new ItemStack(ModItems.shimmer_axe, 1), new Object[] { "H", "G", "G", 'G', ModItems.shimmer_handle, 'H', ModItems.shimmer_axe_head });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.definitelyfood, 1), new Object[] { "DDD", "SDS", "DDD", 'D', Blocks.dirt, 'S', "plateSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.blade_tungsten, 2), new Object[] { "IP", "TP", "TI", 'P', "plateTitanium", 'T', "ingotTitanium", 'I', "ingotTungsten" }));
		GameRegistry.addRecipe(new ItemStack(ModItems.turbine_tungsten, 1), new Object[] { "BBB", "BSB", "BBB", 'B', ModItems.blade_tungsten, 'S', ModItems.ingot_dura_steel });
		GameRegistry.addRecipe(new ItemStack(ModItems.ring_starmetal, 1), new Object[] { " S ", "S S", " S ", 'S', ModItems.ingot_starmetal });

		ItemStack infinity = new ItemStack(Items.enchanted_book);
		EnchantmentUtil.addEnchantment(infinity, Enchantment.infinity, 1);
		GameRegistry.addRecipe(infinity, new Object[] { "SBS", "BDB", "SBS", 'S', ModItems.ammo_50bmg_star, 'B', ModItems.ammo_5mm_star, 'D', ModItems.powder_magic });
		ItemStack unbreaking = new ItemStack(Items.enchanted_book);
		EnchantmentUtil.addEnchantment(unbreaking, Enchantment.unbreaking, 3);
		GameRegistry.addRecipe(unbreaking, new Object[] { "SBS", "BDB", "SBS", 'S', ModItems.ingot_saturnite, 'B', ModItems.plate_armor_lunar, 'D', ModItems.powder_magic });
		ItemStack thorns = new ItemStack(Items.enchanted_book);
		EnchantmentUtil.addEnchantment(thorns, Enchantment.thorns, 3);
		GameRegistry.addRecipe(thorns, new Object[] { "SBS", "BDB", "SBS", 'S', ModBlocks.barbed_wire, 'B', ModBlocks.spikes, 'D', ModItems.powder_magic });

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.wrench, 1), new Object[] { " S ", " IS", "I  ", 'S', "ingotSteel", 'I', "ingotIron" }));
		GameRegistry.addRecipe(new ItemStack(ModItems.wrench_flipped, 1), new Object[] { "S", "D", "W", 'S', Items.iron_sword, 'D', ModItems.ducttape, 'W', ModItems.wrench });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.memespoon, 1), new Object[] { "CGC", "PSP", "IAI", 'C', ModItems.powder_cloud, 'G', ModBlocks.block_thorium, 'P', ModItems.photo_panel, 'S', ModItems.steel_shovel, 'I', ModItems.plate_polymer, 'A', "ingotAustralium" }));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.cbt_device, 1), new Object[] { ModItems.bolt_tungsten, ModItems.wrench });

		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.toothpicks, 3), new Object[] { Items.stick, Items.stick, Items.stick });
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.ducttape, 6), new Object[] { "FSF", "SPS", "FSF", 'F', Items.string, 'S', Items.slime_ball, 'P', Items.paper });

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_difurnace_off), 1), new Object[] { "T T", "PHP", "TFT", 'T', "ingotTungsten", 'P', ModItems.board_copper, 'H', Blocks.hopper, 'F', Blocks.furnace }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_uf6_tank), 1), new Object[] { "WTW", "WTW", "SRS", 'S', "plateIron", 'W', ModItems.coil_tungsten, 'T', ModItems.tank_steel, 'W', ModItems.coil_tungsten,'R', "ingotRedCopperAlloy" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_puf6_tank), 1), new Object[] { "WTW", "WTW", "SRS", 'S', "plateSteel", 'W', ModItems.coil_tungsten, 'T', ModItems.tank_steel, 'W', ModItems.coil_tungsten,'R', "ingotRedCopperAlloy" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_nuke_furnace_off), 1), new Object[] { "SSS", "LFL", "CCC", 'S', "plateSteel", 'C', ModItems.board_copper, 'L', "plateLead", 'F', Item.getItemFromBlock(Blocks.furnace) }));
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_electric_furnace_off), 1), new Object[] { "BBB", "WFW", "RRR", 'B', ModItems.ingot_beryllium, 'R', ModItems.coil_tungsten, 'W', ModItems.board_copper, 'F', Item.getItemFromBlock(Blocks.furnace) });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.machine_arc_furnace_off, 1), new Object[] { "ITI", "PFP", "ITI", 'I', "ingotTungsten", 'T', ModBlocks.machine_transformer, 'P', ModItems.board_copper, 'F', Blocks.furnace }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.red_wire_coated), 16), new Object[] { "WRW", "RIR", "WRW", 'W', ModItems.plate_polymer, 'I', "ingotRedCopperAlloy", 'R', ModItems.wire_red_copper }));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.cable_switch, 1), new Object[] { "S", "W", 'S', Blocks.lever, 'W', ModBlocks.red_wire_coated });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.machine_detector, 1), new Object[] { "IRI", "CTC", "IRI", 'I', ModItems.plate_polymer, 'R', Items.redstone, 'C', ModItems.wire_red_copper, 'T', ModItems.coil_tungsten });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.red_cable), 16), new Object[] { " W ", "RRR", " W ", 'W', ModItems.plate_polymer, 'R', ModItems.wire_red_copper });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.red_pylon), 4), new Object[] { "CWC", "PWP", " T ", 'C', ModItems.coil_copper_torus, 'W', "plankWood", 'P', ModItems.plate_polymer, 'T', ModBlocks.red_wire_coated }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.oil_duct_solid), 16), new Object[] { "SPS", "P P", "SPS", 'S', "ingotSteel", 'P', "plateIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.oil_duct), 16), new Object[] { "SIS", "   ", "SIS", 'S', "plateSteel", 'I', "plateIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.gas_duct_solid), 16), new Object[] { "SPS", "P P", "SPS", 'S', "ingotSteel", 'P', "plateCopper" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.gas_duct), 16), new Object[] { "SIS", "   ", "SIS", 'S', "plateSteel", 'I', "plateCopper" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.machine_battery_potato, 1), new Object[] { "PCP", "WRW", "PCP", 'P', ItemBattery.getEmptyBattery(ModItems.battery_potato), 'C', "ingotCopper", 'R', Blocks.redstone_block, 'W', "plankWood" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.machine_coal_off, 1), new Object[] { "STS", "SCS", "SFS", 'S', "ingotSteel", 'T', ModItems.tank_steel, 'C', "ingotRedCopperAlloy", 'F', Blocks.furnace }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.machine_boiler_off, 1), new Object[] { "SPS", "TFT", "SPS", 'S', "ingotSteel", 'P', ModItems.board_copper, 'T', ModItems.tank_steel, 'F', Blocks.furnace }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.machine_boiler_electric_off, 1), new Object[] { "SPS", "TFT", "SPS", 'S', "ingotDesh", 'P', ModItems.board_copper, 'T', ModItems.tank_steel, 'F', ModBlocks.machine_electric_furnace_off }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.machine_turbine, 1), new Object[] { "PTP", "BMB", "PTP", 'P', "plateTitanium", 'T', ModItems.turbine_titanium, 'B', ModItems.tank_steel, 'M', ModItems.motor }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.machine_converter_he_rf, 1), new Object[] { "SSS", "CRB", "SSS", 'S', "ingotSteel", 'C', ModItems.coil_copper, 'R', ModItems.coil_copper_torus, 'B', "blockRedstone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.machine_converter_rf_he, 1), new Object[] { "SSS", "BRC", "SSS", 'S', ModItems.ingot_beryllium, 'C', ModItems.coil_copper, 'R', ModItems.coil_copper_torus, 'B', "blockRedstone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.crate_iron, 1), new Object[] { "PPP", "I I", "III", 'P', "plateIron", 'I', "ingotIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.crate_steel, 1), new Object[] { "PPP", "I I", "III", 'P', "plateSteel", 'I', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.crate_tungsten, 1), new Object[] { "BPB", "PCP", "BPB", 'B', "blockTungsten", 'P', ModItems.board_copper, 'C', ModBlocks.crate_steel }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.safe, 1), new Object[] { "LAL", "ACA", "LAL", 'L', "plateLead", 'A', "plateAdvanced", 'C', ModBlocks.crate_steel }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.machine_waste_drum, 1), new Object[] { "LRL", "BRB", "LRL", 'L', "ingotLead", 'B', Blocks.iron_bars, 'R', ModItems.rod_quad_empty }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.machine_press, 1), new Object[] { "IRI", "IPI", "IBI", 'I', "ingotIron", 'R', Blocks.furnace, 'B', "blockIron", 'P', Blocks.piston }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.machine_siren, 1), new Object[] { "SIS", "ICI", "SRS", 'S', "plateSteel", 'I', ModItems.plate_polymer, 'C', ModItems.circuit_copper, 'R', "dustRedstone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.machine_microwave, 1), new Object[] { "III", "SGM", "IDI", 'I', ModItems.plate_polymer, 'S', "plateSteel", 'G', "paneGlass", 'M', ModItems.magnetron, 'D', ModItems.motor }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.machine_solar_boiler), new Object[] { "SHS", "DHD", "SHS", 'S', "ingotSteel", 'H', ModItems.hull_big_steel, 'D', "dyeBlack" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.solar_mirror, 3), new Object[] { "AAA", " B ", "SSS", 'A', "plateAluminum", 'B', ModBlocks.steel_beam, 'S', "ingotSteel" }));

		GameRegistry.addRecipe(new ItemStack(ModBlocks.muffler, 1), new Object[] { "III", "IWI", "III", 'I', ModItems.plate_polymer, 'W', Blocks.wool });

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.factory_titanium_hull), 1), new Object[] { "PIP", "I I", "PIP", 'P', "plateTitanium", 'I', "ingotTitanium" }));
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.factory_titanium_furnace), 1), new Object[] { "HMH", "MFM", "HMH", 'H', Item.getItemFromBlock(ModBlocks.factory_titanium_hull), 'M', ModItems.motor, 'F', Item.getItemFromBlock(Blocks.furnace) });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.factory_titanium_conductor), 1), new Object[] { "SWS", "FFF", "SWS", 'S', "ingotTitanium", 'W', Item.getItemFromBlock(ModBlocks.red_wire_coated), 'F', ModItems.fuse }));
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.factory_titanium_core), 1), new Object[] { "HPH", "PCP", "HPH", 'H', Item.getItemFromBlock(ModBlocks.factory_titanium_hull), 'C', ModItems.circuit_aluminium, 'P', Item.getItemFromBlock(Blocks.piston) });
		GameRegistry.addRecipe(ItemBattery.getEmptyBattery(ModItems.factory_core_titanium), new Object[] { "BRB", "RHR", "BRB", 'B', ItemBattery.getEmptyBattery(ModItems.battery_generic), 'R', Item.getItemFromBlock(Blocks.redstone_block), 'H', Item.getItemFromBlock(ModBlocks.factory_titanium_hull) });
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.factory_core_advanced), new Object[] { "BLB", "SHS", "BLB", 'B', ItemBattery.getEmptyBattery(ModItems.battery_advanced), 'S', "blockSulfur", 'L', "blockLead", 'H', Item.getItemFromBlock(ModBlocks.factory_advanced_hull) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.factory_core_advanced), new Object[] { "BSB", "LHL", "BSB", 'B', ItemBattery.getEmptyBattery(ModItems.battery_advanced), 'S', "blockSulfur", 'L', "blockLead", 'H', Item.getItemFromBlock(ModBlocks.factory_advanced_hull) }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.arc_electrode, 1), new Object[] { "C", "T", "C", 'C', "dustCoal", 'T', ModItems.bolt_tungsten }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.arc_electrode_desh, 1), new Object[] { "C", "T", "C", 'C', "dustDesh", 'T', ModItems.arc_electrode }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.detonator, 1), new Object[] { " W", "SC", "CE", 'S', "plateSteel", 'W', ModItems.wire_red_copper, 'C', ModItems.circuit_red_copper, 'E', "ingotSteel" }));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.detonator_multi, 1), new Object[] { ModItems.detonator, ModItems.circuit_targeting_tier3 });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.detonator_laser, 1), new Object[] { "RRD", "PIC", "  P", 'P', "plateSteel", 'R', Items.redstone, 'C', ModItems.circuit_targeting_tier3, 'D', "gemDiamond", 'I', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.detonator_laser, 1), new Object[] { "RRD", "PIC", "  P", 'P', "plateSteel", 'R', Items.redstone, 'C', ModItems.circuit_targeting_tier3, 'D', "gemEmerald", 'I', "ingotSteel" }));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.detonator_deadman, 1), new Object[] { ModItems.detonator, ModItems.defuser, ModItems.ducttape });
		GameRegistry.addRecipe(new ItemStack(ModItems.detonator_de, 1), new Object[] { "T", "D", "T", 'T', Blocks.tnt, 'D', ModItems.detonator_deadman });

		GameRegistry.addRecipe(new ItemStack(ModItems.singularity, 1), new Object[] { "ESE", "SBS", "ESE", 'E', ModItems.nugget_euphemium, 'S', ModItems.cell_anti_schrabidium, 'B', Item.getItemFromBlock(ModBlocks.block_schrabidium) });
		GameRegistry.addRecipe(new ItemStack(ModItems.singularity_counter_resonant, 1), new Object[] { "CTC", "TST", "CTC", 'C', ModItems.plate_combine_steel, 'T', ModItems.ingot_magnetized_tungsten, 'S', ModItems.singularity });
		GameRegistry.addRecipe(new ItemStack(ModItems.singularity_super_heated, 1), new Object[] { "CTC", "TST", "CTC", 'C', ModItems.plate_advanced_alloy, 'T', ModItems.powder_power, 'S', ModItems.singularity });
		GameRegistry.addRecipe(new ItemStack(ModItems.black_hole, 1), new Object[] { "SSS", "SCS", "SSS", 'C', ModItems.singularity, 'S', ModItems.crystal_xen });
		GameRegistry.addRecipe(new ItemStack(ModItems.crystal_xen, 1), new Object[] { "EEE", "EIE", "EEE", 'E', ModItems.powder_power, 'I', ModItems.ingot_euphemium });

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.screwdriver, 1), new Object[] { "  I", " I ", "S  ", 'S', "ingotSteel", 'I', "ingotIron" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.overfuse, 1), new Object[] { ModItems.screwdriver, "dustNeptunium", ModItems.powder_iodine, ModItems.powder_thorium, ModItems.powder_astatine, ModItems.powder_neodymium, ModItems.board_copper, ModItems.black_hole, ModItems.powder_caesium }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.overfuse, 1), new Object[] { ModItems.screwdriver, ModItems.powder_strontium, ModItems.powder_bromine, ModItems.powder_cobalt, ModItems.powder_tennessine, ModItems.powder_niobium, ModItems.board_copper, ModItems.black_hole, ModItems.powder_cerium }));
		GameRegistry.addRecipe(new ItemStack(ModItems.crystal_energy, 1), new Object[] { "EEE", "EGE", "EEE", 'E', ModItems.powder_power, 'G', Items.glowstone_dust });
		GameRegistry.addRecipe(new ItemStack(ModItems.pellet_coolant, 1), new Object[] { "CRC", "RBR", "CRC", 'C', ModItems.powder_ice, 'R', ModItems.rod_quad_coolant, 'B', ModBlocks.block_niter });

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.blades_aluminium, 1), new Object[] { " P ", "PIP", " P ", 'P', "plateAluminum", 'I', "ingotAluminum" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.blades_gold, 1), new Object[] { " P ", "PIP", " P ", 'P', "plateGold", 'I', "ingotGold" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.blades_iron, 1), new Object[] { " P ", "PIP", " P ", 'P', "plateIron", 'I', "ingotIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.blades_steel, 1), new Object[] { " P ", "PIP", " P ", 'P', "plateSteel", 'I', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.blades_titanium, 1), new Object[] { " P ", "PIP", " P ", 'P', "plateTitanium", 'I', "ingotTitanium" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.blades_advanced_alloy, 1), new Object[] { " P ", "PIP", " P ", 'P', "plateAdvanced", 'I', "ingotAdvanced" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.blades_combine_steel, 1), new Object[] { " P ", "PIP", " P ", 'P', "plateCMBSteel", 'I', "ingotCMBSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.blades_schrabidium, 1), new Object[] { " P ", "PIP", " P ", 'P', "plateSchrabidium", 'I', "ingotSchrabidium" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.blades_desh, 1), new Object[] { "NPN", "PBP", "NPN", 'N', "nuggetSchrabidium", 'P', ModItems.plate_desh, 'B', ModItems.blades_combine_steel }));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.blades_aluminium, 1), new Object[] { "PIP", 'P', "plateAluminum", 'I', new ItemStack(ModItems.blades_aluminium, 1, OreDictionary.WILDCARD_VALUE) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.blades_gold, 1), new Object[] { "PIP", 'P', "plateGold", 'I', new ItemStack(ModItems.blades_gold, 1, OreDictionary.WILDCARD_VALUE) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.blades_iron, 1), new Object[] { "PIP", 'P', "plateIron", 'I', new ItemStack(ModItems.blades_iron, 1, OreDictionary.WILDCARD_VALUE) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.blades_steel, 1), new Object[] { "PIP", 'P', "plateSteel", 'I', new ItemStack(ModItems.blades_steel, 1, OreDictionary.WILDCARD_VALUE) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.blades_titanium, 1), new Object[] { "PIP", 'P', "plateTitanium", 'I', new ItemStack(ModItems.blades_titanium, 1, OreDictionary.WILDCARD_VALUE) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.blades_advanced_alloy, 1), new Object[] { "PIP", 'P', "plateAdvanced", 'I', new ItemStack(ModItems.blades_advanced_alloy, 1, OreDictionary.WILDCARD_VALUE) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.blades_combine_steel, 1), new Object[] { "PIP", 'P', "plateCMBSteel", 'I', new ItemStack(ModItems.blades_combine_steel, 1, OreDictionary.WILDCARD_VALUE) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.blades_schrabidium, 1), new Object[] { "PIP", 'P', "plateSchrabidium", 'I', new ItemStack(ModItems.blades_schrabidium, 1, OreDictionary.WILDCARD_VALUE) }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.stamp_stone_flat, 1), new Object[] { " R ", "III", "SSS", 'R', "dustRedstone", 'I', "ingotBrick", 'S', "stone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.stamp_iron_flat, 1), new Object[] { " R ", "III", "SSS", 'R', "dustRedstone", 'I', "ingotBrick", 'S', "ingotIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.stamp_steel_flat, 1), new Object[] { " R ", "III", "SSS", 'R', "dustRedstone", 'I', "ingotBrick", 'S', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.stamp_titanium_flat, 1), new Object[] { " R ", "III", "SSS", 'R', "dustRedstone", 'I', "ingotBrick", 'S', "ingotTitanium" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.stamp_obsidian_flat, 1), new Object[] { " R ", "III", "SSS", 'R', "dustRedstone", 'I', "ingotBrick", 'S', Blocks.obsidian }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.stamp_schrabidium_flat, 1), new Object[] { " R ", "III", "SSS", 'R', "dustRedstone", 'I', "ingotBrick", 'S', "ingotSchrabidium" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.mechanism_revolver_1, 1), new Object[] { " II", "ICA", "IKW", 'I', "plateIron", 'C', "ingotCopper", 'A', "ingotAluminum", 'K', ModItems.wire_copper, 'W', ModItems.wire_aluminium }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.mechanism_revolver_2, 1), new Object[] { " II", "ICA", "IKW", 'I', "plateAdvanced", 'C', "ingotDuraSteel", 'A', "ingotTungsten", 'K', ModItems.bolt_dura_steel, 'W', ModItems.bolt_tungsten }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.mechanism_rifle_1, 1), new Object[] { "ICI", "CMA", "IAM", 'I', "plateIron", 'C', "ingotCopper", 'A', "ingotAluminum", 'M', ModItems.mechanism_revolver_1 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.mechanism_rifle_2, 1), new Object[] { "ICI", "CMA", "IAM", 'I', "plateAdvanced", 'C', "ingotDuraSteel", 'A', "ingotTungsten", 'M', ModItems.mechanism_revolver_2 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.mechanism_launcher_1, 1), new Object[] { "TTT", "SSS", "BBI", 'T', "plateTitanium", 'S', "ingotSteel", 'B', ModItems.bolt_tungsten, 'I', "ingotRedCopperAlloy" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.mechanism_launcher_2, 1), new Object[] { "TTT", "SSS", "BBI", 'T', "plateAdvanced", 'S', "ingotPolymer", 'B', ModItems.bolt_dura_steel, 'I', "ingotDesh" }));
		GameRegistry.addRecipe(new ItemStack(ModItems.mechanism_special, 1), new Object[] { "PCI", "ISS", "PCI", 'P', ModItems.plate_desh, 'C', ModItems.coil_advanced_alloy, 'I', ModItems.ingot_starmetal, 'S', ModItems.circuit_targeting_tier3 });

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.reactor_ejector, 1), new Object[] { "CLC", "MHM", "CLC", 'C', ModBlocks.brick_concrete, 'L', "plateLead", 'M', ModItems.motor, 'H', ModBlocks.reactor_hatch }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.reactor_inserter, 1), new Object[] { "CLC", "MHM", "CLC", 'C', ModBlocks.brick_concrete, 'L', "plateCopper", 'M', ModItems.motor, 'H', ModBlocks.reactor_hatch }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.fwatz_cooler), 1), new Object[] { "IPI", "IPI", "IPI", 'I', "ingotTitanium", 'P', "plateTitanium" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.fwatz_tank), 1), new Object[] { "CGC", "GGG", "CGC", 'C', ModItems.plate_combine_steel, 'G', "paneGlass" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.fwatz_scaffold), 1), new Object[] { "IPI", "P P", "IPI", 'I', "ingotTungsten", 'P', "plateDenseLead" }));

		GameRegistry.addRecipe(new ItemStack(ModBlocks.reinforced_stone, 4), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.cobblestone, 'B', Blocks.stone });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.brick_light, 4), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.fence, 'B', Blocks.brick_block });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.brick_asbestos, 2), new Object[] { " A ", "ABA", " A ", 'B', ModBlocks.brick_light, 'A', ModItems.ingot_asbestos });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.concrete, 4), new Object[] { "CC", "CC", 'C', ModBlocks.concrete_smooth });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.concrete_pillar, 8), new Object[] { "CBC", "CBC", "CBC", 'C', ModBlocks.concrete_smooth, 'B', Blocks.iron_bars });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.brick_concrete, 4), new Object[] { " C ", "CBC", " C ", 'C', ModBlocks.concrete_smooth, 'B', Items.clay_ball });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.brick_concrete, 4), new Object[] { " C ", "CBC", " C ", 'C', ModBlocks.concrete, 'B', Items.clay_ball });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.brick_concrete_mossy, 8), new Object[] { "CCC", "CVC", "CCC", 'C', ModBlocks.brick_concrete, 'V', Blocks.vine });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.brick_concrete_cracked, 6), new Object[] { " C " , "C C", " C ", 'C', ModBlocks.brick_concrete });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.brick_concrete_broken, 6), new Object[] { " C " , "C C", " C ", 'C', ModBlocks.brick_concrete_cracked });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.brick_obsidian, 4), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.iron_bars, 'B', Blocks.obsidian });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.meteor_polished, 4), new Object[] { "CC", "CC", 'C', ModBlocks.block_meteor_broken });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.meteor_pillar, 2), new Object[] { "C", "C", 'C', ModBlocks.meteor_polished });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.meteor_brick, 4), new Object[] { "CC", "CC", 'C', ModBlocks.meteor_polished });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.meteor_brick_mossy, 8), new Object[] { "CCC", "CVC", "CCC", 'C', ModBlocks.meteor_brick, 'V', Blocks.vine });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.meteor_brick_cracked, 6), new Object[] { " C " , "C C", " C ", 'C', ModBlocks.meteor_brick });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.meteor_battery, 1), new Object[] { "MSM", "MWM", "MSM", 'M', ModBlocks.meteor_polished, 'S', ModBlocks.block_starmetal, 'W', ModItems.wire_schrabidium });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.tile_lab, 4), new Object[] { "CBC", "CBC", "CBC", 'C', Items.brick, 'B', ModItems.ingot_asbestos });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.tile_lab_cracked, 6), new Object[] { " C " , "C C", " C ", 'C', ModBlocks.tile_lab });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.tile_lab_broken, 6), new Object[] { " C " , "C C", " C ", 'C', ModBlocks.tile_lab_cracked });

		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.reinforced_brick), 8), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.iron_bars, 'B', ModBlocks.brick_concrete });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.brick_compound), 8), new Object[] { "FBF", "BFB", "FBF", 'F', ModItems.bolt_tungsten, 'B', ModBlocks.reinforced_brick });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.reinforced_glass), 8), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.iron_bars, 'B', Blocks.glass });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.reinforced_light), 1), new Object[] { "FFF", "FBF", "FFF", 'F', Blocks.iron_bars, 'B', Blocks.glowstone });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.reinforced_lamp_off), 1), new Object[] { "FFF", "FBF", "FFF", 'F', Blocks.iron_bars, 'B', Blocks.redstone_lamp });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.reinforced_sand), 8), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.iron_bars, 'B', Blocks.sandstone });

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.lamp_tritium_green_off, 1), new Object[] { "GPG", "1T2", "GPG", 'G', "blockGlass", 'P', ModItems.powder_fire, 'T', ModItems.cell_tritium, '1', "dustSulfur", '2', "dustCopper" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.lamp_tritium_blue_off, 1), new Object[] { "GPG", "1T2", "GPG", 'G', "blockGlass", 'P', ModItems.powder_fire, 'T', ModItems.cell_tritium, '1', "dustAluminum", '2', ModItems.powder_strontium }));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.barbed_wire, 16), new Object[] { "AIA", "I I", "AIA", 'A', ModItems.wire_aluminium, 'I', "ingotIron" }));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.barbed_wire_fire, 8), new Object[] { "BBB", "BIB", "BBB", 'B', ModBlocks.barbed_wire, 'I', ModItems.powder_fire });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.barbed_wire_poison, 8), new Object[] { "BBB", "BIB", "BBB", 'B', ModBlocks.barbed_wire, 'I', ModItems.powder_poison });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.barbed_wire_acid, 8), new Object[] { "BBB", "BIB", "BBB", 'B', ModBlocks.barbed_wire, 'I', new ItemStack(ModItems.fluid_tank_full, 1, FluidType.ACID.getID()) });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.barbed_wire_wither, 8), new Object[] { "BBB", "BIB", "BBB", 'B', ModBlocks.barbed_wire, 'I', new ItemStack(Items.skull, 1, 1) });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.barbed_wire_ultradeath, 4), new Object[] { "BCB", "CIC", "BCB", 'B', ModBlocks.barbed_wire, 'C', ModItems.powder_cloud, 'I', ModItems.nuclear_waste });

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.tape_recorder), 4), new Object[] { "TST", "SSS", 'T', "ingotTungsten", 'S', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_poles), 16), new Object[] { "S S", "SSS", "S S", 'S', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.pole_top), 1), new Object[] { "T T", "TRT", "BBB", 'T', "ingotTungsten", 'B', ModItems.ingot_beryllium, 'R', "ingotRedCopperAlloy" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.pole_satellite_receiver), 1), new Object[] { "SS ", "SCR", "SS ", 'S', "ingotSteel", 'C', ModItems.circuit_red_copper, 'R', ModItems.wire_red_copper }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_beam), 8), new Object[] { "S", "S", "S", 'S', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_wall), 4), new Object[] { "SSS", "SSS", 'S', "ingotSteel" }));
		GameRegistry.addShapelessRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_corner)), new Object[] { Item.getItemFromBlock(ModBlocks.steel_wall), Item.getItemFromBlock(ModBlocks.steel_wall) });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_roof), 2), new Object[] { "SSS", 'S', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_scaffold), 8), new Object[] { "SSS", " S ", "SSS", 'S', "ingotSteel" }));
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_beam), 8), new Object[] { "S", "S", "S", 'S', ModBlocks.steel_scaffold });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.chain), 8), new Object[] { "S", "S", "S", 'S', ModBlocks.steel_beam });
		
		reg2();
	}
	
	public static void reg2() {

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.stamp_357, 1), new Object[] { "RSR", "III", " C ", 'R', "dustRedstone", 'S', ModItems.stamp_iron_flat, 'I', ModItems.plate_polymer, 'C', ModItems.casing_357 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.stamp_44, 1), new Object[] { "RSR", "III", " C ", 'R', "dustRedstone", 'S', ModItems.stamp_iron_flat, 'I', ModItems.plate_polymer, 'C', ModItems.casing_44 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.stamp_9, 1), new Object[] { "RSR", "III", " C ", 'R', "dustRedstone", 'S', ModItems.stamp_iron_flat, 'I', ModItems.plate_polymer, 'C', ModItems.casing_9 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.stamp_50, 1), new Object[] { "RSR", "III", " C ", 'R', "dustRedstone", 'S', ModItems.stamp_iron_flat, 'I', ModItems.plate_polymer, 'C', ModItems.casing_50 }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.sat_dock, 1), new Object[] { "SSS", "PCP", 'S', "ingotSteel", 'P', "ingotPolymer", 'C', ModBlocks.crate_iron }));
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.book_guide), 1), new Object[] { "IBI", "LBL", "IBI", 'B', Items.book, 'I', new ItemStack(Items.dye, 1, 0), 'L', new ItemStack(Items.dye, 1, 4) });

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.rail_highspeed), 16), new Object[] { "S S", "SIS", "S S", 'S', "ingotSteel", 'I', "plateIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.rail_booster), 6), new Object[] { "S S", "CIC", "SRS", 'S', "ingotSteel", 'I', "plateIron", 'R', "ingotRedCopperAlloy", 'C', ModItems.coil_copper }));

		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.bomb_multi), 1), new Object[] { "AAD", "CHF", "AAD", 'A', ModItems.wire_aluminium, 'C', ModItems.circuit_aluminium, 'H', ModItems.hull_small_aluminium, 'F', ModItems.fins_quad_titanium, 'D', new ItemStack(Items.dye, 1, 15) });
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.powder_ice, 4), new Object[] { Items.snowball, "dustNiter", "dustRedstone" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.powder_poison, 4), new Object[] { Items.spider_eye, "dustRedstone", "gemQuartz" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.pellet_gas, 2), new Object[] { Items.water_bucket, "dustGlowstone", "plateSteel" }));

		GameRegistry.addRecipe(new ItemStack(ModItems.flame_pony, 1), new Object[] { " O ", "DPD", " O ", 'D', new ItemStack(Items.dye, 1, 11), 'O', new ItemStack(Items.dye, 1, 9), 'P', Items.paper });
		GameRegistry.addRecipe(new ItemStack(ModItems.flame_conspiracy, 1), new Object[] { " S ", "STS", " S ", 'S', ModItems.canister_fuel, 'T', ModItems.ingot_steel });
		GameRegistry.addRecipe(new ItemStack(ModItems.flame_politics, 1), new Object[] { " I ", "IPI", " I ", 'P', Items.paper, 'I', new ItemStack(Items.dye, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(ModItems.flame_opinion, 1), new Object[] { " R ", "RPR", " R ", 'P', Items.paper, 'R', new ItemStack(Items.dye, 1, 1) });
		
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.flame_war), 1), new Object[] { "WHW", "CTP", "WOW", 'W', Item.getItemFromBlock(Blocks.planks), 'T', Item.getItemFromBlock(Blocks.tnt), 'H', ModItems.flame_pony, 'C', ModItems.flame_conspiracy, 'P', ModItems.flame_politics, 'O', ModItems.flame_opinion });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.det_cord, 8), new Object[] { "TNT", "NGN", "TNT", 'T', "plateIron", 'N', "dustNiter", 'G', Items.gunpowder }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.det_charge, 1), new Object[] { "PDP", "DTD", "PDP", 'P', "plateSteel", 'D', ModBlocks.det_cord, 'T', ModItems.ingot_semtex }));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.det_nuke, 1), new Object[] { "PDP", "DCD", "PDP", 'P', ModItems.plate_desh, 'D', ModBlocks.det_charge, 'C', ModItems.man_core });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.det_miner, 3), new Object[] { "FFF", "ITI", "ITI", 'F', Items.flint, 'I', "plateIron", 'T', Blocks.tnt }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.det_miner, 12), new Object[] { "FFF", "ITI", "ITI", 'F', Items.flint, 'I', "plateSteel", 'T', ModItems.ingot_semtex }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.emp_bomb), 1), new Object[] { "LML", "LCL", "LML", 'L', "plateLead", 'M', ModItems.magnetron, 'C', ModItems.circuit_gold }));

		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_generic), new Object[] { " A ", "PRP", "PRP", 'A', ModItems.wire_aluminium, 'P', "plateAluminum", 'R', "dustRedstone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_advanced), new Object[] { " A ", "PSP", "PLP", 'A', ModItems.wire_red_copper, 'P', "plateCopper", 'S', "sulfur", 'L', "dustLead" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_advanced), new Object[] { " A ", "PLP", "PSP", 'A', ModItems.wire_red_copper, 'P', "plateCopper", 'S', "sulfur", 'L', "dustLead" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_advanced), new Object[] { " A ", "PSP", "PLP", 'A', ModItems.wire_red_copper, 'P', "plateCopper", 'S', "dustSulfur", 'L', "dustLead" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_advanced), new Object[] { " A ", "PLP", "PSP", 'A', ModItems.wire_red_copper, 'P', "plateCopper", 'S', "dustSulfur", 'L', "dustLead" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_lithium), new Object[] { "A A", "PSP", "PLP", 'A', ModItems.wire_gold, 'P', "plateTitanium", 'S', "dustLithium", 'L', ModItems.powder_cobalt }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_lithium), new Object[] { "A A", "PLP", "PSP", 'A', ModItems.wire_gold, 'P', "plateTitanium", 'S', "dustLithium", 'L', ModItems.powder_cobalt }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_schrabidium), new Object[] { " A ", "PNP", "PSP", 'A', ModItems.wire_schrabidium, 'P', "plateSchrabidium", 'S', "dustSchrabidium", 'N', ModItems.powder_neptunium }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_schrabidium), new Object[] { " A ", "PSP", "PNP", 'A', ModItems.wire_schrabidium, 'P', "plateSchrabidium", 'S', "dustSchrabidium", 'N', ModItems.powder_neptunium }));
		GameRegistry.addRecipe(ItemBattery.getEmptyBattery(ModItems.battery_spark), new Object[] { " A ", "PSP", "PSP", 'A', ModItems.wire_magnetized_tungsten, 'P', ModItems.plate_dineutronium, 'S', ModItems.powder_spark_mix });
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_trixite), new Object[] { " A ", "PSP", "PTP", 'A', ModItems.wire_aluminium, 'P', "plateAluminum", 'S', ModItems.powder_power, 'T', ModItems.crystal_trixite }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_trixite), new Object[] { " A ", "PTP", "PSP", 'A', ModItems.wire_aluminium, 'P', "plateAluminum", 'S', ModItems.powder_power, 'T', ModItems.crystal_trixite }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getFullBattery(ModItems.energy_core), new Object[] { "PCW", "TRD", "PCW", 'P', ModItems.plate_advanced_alloy, 'C', ModItems.coil_advanced_alloy, 'W', ModItems.wire_advanced_alloy, 'R', ModItems.cell_tritium, 'D', ModItems.cell_deuterium, 'T', "ingotTungsten" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getFullBattery(ModItems.energy_core), new Object[] { "PCW", "TDR", "PCW", 'P', ModItems.plate_advanced_alloy, 'C', ModItems.coil_advanced_alloy, 'W', ModItems.wire_advanced_alloy, 'R', ModItems.cell_tritium, 'D', ModItems.cell_deuterium, 'T', "ingotTungsten" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.hev_battery, 4), new Object[] { " W ", "IEI", "ICI", 'W', ModItems.wire_gold, 'I', ModItems.plate_polymer, 'E', Items.redstone, 'C', "dustCobalt" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.hev_battery, 4), new Object[] { " W ", "ICI", "IEI", 'W', ModItems.wire_gold, 'I', ModItems.plate_polymer, 'E', Items.redstone, 'C', "dustCobalt" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_red_cell), new Object[] { "WBW", "PBP", "WBW", 'W', ModItems.wire_aluminium, 'P', "plateAluminum", 'B', ItemBattery.getEmptyBattery(ModItems.battery_generic) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_advanced_cell), new Object[] { "WBW", "PBP", "WBW", 'W', ModItems.wire_red_copper, 'P', "plateCopper", 'B', ItemBattery.getEmptyBattery(ModItems.battery_advanced) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell), new Object[] { "WBW", "PBP", "WBW", 'W', ModItems.wire_gold, 'P', "plateTitanium", 'B', ItemBattery.getEmptyBattery(ModItems.battery_lithium) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_schrabidium_cell), new Object[] { "WBW", "PBP", "WBW", 'W', ModItems.wire_schrabidium, 'P', "plateSchrabidium", 'B', ItemBattery.getEmptyBattery(ModItems.battery_schrabidium) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_red_cell_6), new Object[] { "BBB", "WPW", "BBB", 'W', ModItems.wire_aluminium, 'P', "plateAluminum", 'B', ItemBattery.getEmptyBattery(ModItems.battery_red_cell) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_advanced_cell_4), new Object[] { "BWB", "WPW", "BWB", 'W', ModItems.wire_red_copper, 'P', "plateCopper", 'B', ItemBattery.getEmptyBattery(ModItems.battery_advanced_cell) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell_3), new Object[] { "WPW", "BBB", "WPW", 'W', ModItems.wire_gold, 'P', "plateTitanium", 'B', ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_schrabidium_cell_2), new Object[] { "WPW", "BWB", "WPW", 'W', ModItems.wire_schrabidium, 'P', "plateSchrabidium", 'B', ItemBattery.getEmptyBattery(ModItems.battery_schrabidium_cell) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_red_cell_24), new Object[] { "BWB", "WPW", "BWB", 'W', ModItems.wire_aluminium, 'P', "plateAluminum", 'B', ItemBattery.getEmptyBattery(ModItems.battery_red_cell_6) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_advanced_cell_12), new Object[] { "WPW", "BBB", "WPW", 'W', ModItems.wire_red_copper, 'P', "plateCopper", 'B', ItemBattery.getEmptyBattery(ModItems.battery_advanced_cell_4) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell_6), new Object[] { "WPW", "BWB", "WPW", 'W', ModItems.wire_gold, 'P', "plateTitanium", 'B', ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell_3) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_schrabidium_cell_4), new Object[] { "WPW", "BWB", "WPW", 'W', ModItems.wire_schrabidium, 'P', "plateSchrabidium", 'B', ItemBattery.getEmptyBattery(ModItems.battery_schrabidium_cell_2) }));
		GameRegistry.addRecipe(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_6), new Object[] { "BBW", "BBP", "BBW", 'W', ModItems.wire_magnetized_tungsten, 'P', ModItems.plate_dineutronium, 'B', ItemBattery.getEmptyBattery(ModItems.battery_spark) });
		GameRegistry.addRecipe(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_25), new Object[] { " WW", "PCC", "BCC", 'W', ModItems.wire_magnetized_tungsten, 'P', ModItems.plate_dineutronium, 'B', ItemBattery.getEmptyBattery(ModItems.battery_spark), 'C', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_6) });
		GameRegistry.addRecipe(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_100), new Object[] { "W W", "BPB", "BPB", 'W', ModItems.wire_magnetized_tungsten, 'P', ModItems.plate_dineutronium, 'B', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_25) });
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_1000), new Object[] { "CCC", "CSC", "CCC", 'S', ModItems.singularity_spark, 'C', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_100) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_2500), new Object[] { "CVC", "PSP", "CVC", 'S', ModItems.singularity_spark, 'C', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_100), 'V', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_1000), 'P', ModItems.plate_dineutronium }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_10000), new Object[] { "PVP", "VSV", "PVP", 'S', ModItems.singularity_spark, 'V', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_2500), 'P', ModItems.plate_dineutronium }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_power), new Object[] { "CCC", "CSC", "CCC", 'S', ModItems.singularity_spark, 'C', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_10000) }));

		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getFullBattery(ModItems.battery_su), new Object[] { "P", "R", "C", 'P', Items.paper, 'R', "dustRedstone", 'C', "dustCoal" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getFullBattery(ModItems.battery_su), new Object[] { "P", "C", "R", 'P', Items.paper, 'R', "dustRedstone", 'C', "dustCoal" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getFullBattery(ModItems.battery_su_l), new Object[] { " W ", "CPC", "RPR", 'W', ModItems.wire_aluminium, 'P', Items.paper, 'R', "dustRedstone", 'C', "dustCoal" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getFullBattery(ModItems.battery_su_l), new Object[] { " W ", "RPR", "CPC", 'W', ModItems.wire_aluminium, 'P', Items.paper, 'R', "dustRedstone", 'C', "dustCoal" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getFullBattery(ModItems.battery_su_l), new Object[] { " W ", "CPC", "RPR", 'W', ModItems.wire_copper, 'P', Items.paper, 'R', "dustRedstone", 'C', "dustCoal" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getFullBattery(ModItems.battery_su_l), new Object[] { " W ", "RPR", "CPC", 'W', ModItems.wire_copper, 'P', Items.paper, 'R', "dustRedstone", 'C', "dustCoal" }));
		GameRegistry.addShapelessRecipe(ItemBattery.getFullBattery(ModItems.battery_potato), new Object[] { Items.potato, ModItems.wire_aluminium, ModItems.wire_copper });
		GameRegistry.addRecipe(new ShapelessOreRecipe(ItemBattery.getFullBattery(ModItems.battery_potatos), new Object[] { ItemBattery.getFullBattery(ModItems.battery_potato), ModItems.turret_chip, "dustRedstone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_steam), new Object[] { "PMP", "ISI", "PCP", 'P', "plateCopper", 'M', ModItems.motor, 'C', ModItems.coil_tungsten, 'S', new ItemStack(ModItems.fluid_tank_full, 1, FluidType.WATER.getID()), 'I', ModItems.plate_polymer }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.battery_steam_large), new Object[] { "MPM", "ISI", "CPC", 'P', ModItems.board_copper, 'M', ModItems.motor, 'C', ModItems.coil_tungsten, 'S', new ItemStack(ModItems.fluid_barrel_full, 1, FluidType.WATER.getID()), 'I', "ingotPolymer" }));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.wiring_red_copper, 1), new Object[] { "PPP", "PIP", "PPP", 'P', "plateSteel", 'I', "ingotSteel" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.tank_waste, 1), new Object[] { "PTP", "PTP", "PTP", 'T', ModItems.tank_steel, 'P', "plateSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gas_mask_filter, 1), new Object[] { "F", "I", "F", 'F', ModItems.filter_coal, 'I', "plateIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.jetpack_tank, 1), new Object[] { " S ", "BKB", " S ", 'S', "plateSteel", 'B', ModItems.bolt_tungsten, 'K', ModItems.canister_kerosene }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_kit_1, 4), new Object[] { "I ", "LB", "P ", 'I', ModItems.plate_polymer, 'L', ModItems.canister_canola, 'B', ModItems.bolt_tungsten, 'P', "plateIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_kit_2, 1), new Object[] { "III", "GLG", "PPP", 'I', ModItems.plate_polymer, 'L', ModItems.ducttape, 'G', ModItems.gun_kit_1, 'P', "plateIron" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.igniter, 1), new Object[] { " W", "SC", "CE", 'S', "plateSteel", 'W', ModItems.wire_schrabidium, 'C', ModItems.circuit_schrabidium, 'E', ModItems.ingot_euphemium }));
		GameRegistry.addRecipe(new ItemStack(ModItems.watch, 1), new Object[] { "LEL", "EWE", "LEL", 'E', ModItems.ingot_euphemium, 'L', new ItemStack(Items.dye, 1, 4), 'W', Items.clock });
		GameRegistry.addRecipe(new ItemStack(ModItems.plate_euphemium, 1), new Object[] { "AEA", "ENE", "AEA", 'E', ModItems.ingot_euphemium, 'N', Items.nether_star, 'A', ModItems.powder_astatine });

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.key, 1), new Object[] { "  B", " B ", "P  ", 'P', "plateSteel", 'B', ModItems.bolt_tungsten }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.key_kit, 1), new Object[] { "PKP", "DTD", "PKP", 'P', "plateGold", 'K', ModItems.key, 'D', "dustDiamond", 'T', ModItems.screwdriver }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.key_red, 1), new Object[] { "DSC", "SMS", "KSD", 'C', ModItems.circuit_targeting_tier4, 'M', Items.nether_star, 'K', ModItems.key, 'D', "dustDesh", 'S', "plateSaturnite" }));
		GameRegistry.addRecipe(new ItemStack(ModItems.pin, 1), new Object[] { "W ", " W", " W", 'W', ModItems.wire_copper });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.padlock_rusty, 1), new Object[] { "I", "B", "I", 'I', "ingotIron", 'B', ModItems.bolt_tungsten }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.padlock, 1), new Object[] { " P ", "PBP", "PPP", 'P', "plateSteel", 'B', ModItems.bolt_tungsten }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.padlock_reinforced, 1), new Object[] { " P ", "PBP", "PDP", 'P', "plateAdvanced", 'D', ModItems.plate_desh, 'B', ModItems.bolt_dura_steel }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.padlock_unbreakable, 1), new Object[] { " P ", "PBP", "PDP", 'P', "plateSaturnite", 'D', "gemDiamond", 'B', ModItems.bolt_dura_steel }));
		
		GameRegistry.addRecipe(new ItemStack(ModItems.canister_petroil, 9), new Object[] { "RRR", "RLR", "RRR", 'R', ModItems.canister_reoil, 'L', ModItems.canister_canola });
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.record_lc, 1), new Object[] { " S ", "SDS", " S ", 'S', "ingotPolymer", 'D', "dustLapis" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.record_ss, 1), new Object[] { " S ", "SDS", " S ", 'S', "ingotPolymer", 'D', "dustAdvanced" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.record_vc, 1), new Object[] { " S ", "SDS", " S ", 'S', "ingotPolymer", 'D', "dustCMBSteel" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.polaroid, 1), new Object[] { " C ", "RPY", " B ", 'B', "dustLapis", 'C', "dustCoal", 'R', "dustAdvanced", 'Y', "dustGold", 'P', Items.paper }));

		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.crystal_horn, 1), new Object[] { ModItems.powder_neptunium, ModItems.powder_iodine, ModItems.powder_thorium, ModItems.powder_astatine, ModItems.powder_neodymium, ModItems.powder_caesium, ModBlocks.block_meteor, ModBlocks.gravel_obsidian, Items.water_bucket });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.crystal_charred, 1), new Object[] { ModItems.powder_strontium, ModItems.powder_cobalt, ModItems.powder_bromine, ModItems.powder_niobium, ModItems.powder_tennessine, ModItems.powder_cerium, ModBlocks.block_meteor, ModBlocks.block_aluminium, Items.water_bucket });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.crystal_virus, 1), new Object[] { "STS", "THT", "STS", 'S', ModItems.particle_strange, 'T', "dustTungsten", 'H', ModItems.crystal_horn }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.crystal_pulsar, 32), new Object[] { "STS", "THT", "STS", 'S', ModItems.cell_uf6, 'T', "dustAluminum", 'H', ModItems.crystal_charred }));
	
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.fluid_duct, 8), new Object[] { "SAS", "   ", "SAS", 'S', "plateSteel", 'A', "plateAluminum" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.machine_assembler, 1), new Object[] { "WWW", "MCM", "ISI", 'W', "paneGlass", 'M', ModItems.motor, 'C', ModItems.circuit_aluminium, 'I', "blockCopper", 'S', "blockSteel" }));
		GameRegistry.addRecipe(new ItemStack(ModItems.template_folder, 1), new Object[] { "LPL", "BPB", "LPL", 'P', Items.paper, 'L', new ItemStack(Items.dye, 1, 4), 'B', new ItemStack(Items.dye, 1, 15) });
		//GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.turret_control, 1), new Object[] { "R12", "PPI", "  I", 'R', Items.redstone, '1', ModItems.circuit_aluminium, '2', ModItems.circuit_red_copper, 'P', "plateSteel", 'I', "ingotSteel" }));
		GameRegistry.addRecipe(new ItemStack(ModItems.pellet_antimatter, 1), new Object[] { "###", "###", "###", '#', ModItems.cell_antimatter });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.fluid_tank_empty, 8), new Object[] { "121", "1 1", "121", '1', "plateAluminum", '2', "plateIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.fluid_barrel_empty, 2), new Object[] { "121", "1 1", "121", '1', "plateSteel", '2', "plateAluminum" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.inf_water, 1), new Object[] { "222", "131", "222", '1', Items.water_bucket, '2', "plateAluminum", '3', Items.diamond }));
		

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.dynosphere_base), new Object[] { "RPR", "PBP", "RPR", 'R', "dustRedCopperAlloy", 'P', "plateSteel", 'B', "blockRedstone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.dynosphere_desh), new Object[] { "RPR", "PBP", "RPR", 'R', ModItems.powder_desh_mix, 'P', "ingotDesh", 'B', ModItems.dynosphere_base }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.dynosphere_schrabidium), new Object[] { "RPR", "PBP", "RPR", 'R', ModItems.powder_power, 'P', "ingotSchrabidium", 'B', ModItems.dynosphere_desh_charged }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.dynosphere_euphemium), new Object[] { "RPR", "PBP", "RPR", 'R', ModItems.powder_nitan_mix, 'P', "ingotEuphemium", 'B', ModItems.dynosphere_schrabidium_charged }));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemBattery.getEmptyBattery(ModItems.dynosphere_dineutronium), new Object[] { "RPR", "PBP", "RPR", 'R', ModItems.powder_spark_mix, 'P', "ingotDineutronium", 'B', ModItems.dynosphere_euphemium_charged }));
		
		//not so Temporary Crappy Recipes
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.plate_dineutronium, 4), new Object[] { "PIP", "IDI", "PIP", 'P', ModItems.powder_spark_mix, 'I', "ingotDineutronium", 'D', "ingotDesh" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.plate_desh, 4), new Object[] { "PIP", "IDI", "PIP", 'P', ModItems.powder_polymer, 'I', "ingotDesh", 'D', "ingotDuraSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.piston_selenium, 1), new Object[] { "SSS", "STS", " D ", 'S', "plateSteel", 'T', "ingotTungsten", 'D', ModItems.bolt_dura_steel }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.catalyst_clay), new Object[] { "dustIron", Items.clay_ball }));
		GameRegistry.addRecipe(new ItemStack(ModItems.singularity_spark, 1), new Object[] { "XAX", "BCB", "XAX", 'X', ModItems.plate_dineutronium, 'A', ModItems.singularity_counter_resonant, 'B', ModItems.singularity_super_heated, 'C', ModItems.black_hole });
		GameRegistry.addRecipe(new ItemStack(ModItems.singularity_spark, 1), new Object[] { "XBX", "ACA", "XBX", 'X', ModItems.plate_dineutronium, 'A', ModItems.singularity_counter_resonant, 'B', ModItems.singularity_super_heated, 'C', ModItems.black_hole });
		GameRegistry.addRecipe(new ItemStack(ModItems.ams_core_sing, 1), new Object[] { "EAE", "ASA", "EAE", 'E', ModItems.plate_euphemium, 'A', ModItems.cell_anti_schrabidium, 'S', ModItems.singularity });
		GameRegistry.addRecipe(new ItemStack(ModItems.ams_core_wormhole, 1), new Object[] { "DPD", "PSP", "DPD", 'D', ModItems.plate_dineutronium, 'P', ModItems.powder_spark_mix, 'S', ModItems.singularity });
		GameRegistry.addRecipe(new ItemStack(ModItems.ams_core_eyeofharmony, 1), new Object[] { "ALA", "LSL", "ALA", 'A', ModItems.plate_dalekanium, 'L', new ItemStack(ModItems.fluid_barrel_full, 1, FluidType.LAVA.getID()), 'S', ModItems.black_hole });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ams_core_thingy), new Object[] { "NSN", "NGN", "G G", 'N', "nuggetGold", 'G', "ingotGold", 'S', ModItems.battery_spark_cell_10000 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.photo_panel), new Object[] { " G ", "IPI", " C ", 'G', "paneGlass", 'I', ModItems.plate_polymer, 'P', "dustNetherQuartz", 'C', ModItems.circuit_aluminium }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.machine_satlinker), new Object[] { "PSP", "SCS", "PSP", 'P', "plateSteel", 'S', ModItems.ingot_starmetal, 'C', ModItems.sat_chip }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.machine_telelinker), new Object[] { "PSP", "SCS", "PSP", 'P', "plateSteel", 'S', "ingotAdvanced", 'C', ModItems.turret_biometry }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.machine_keyforge), new Object[] { "PCP", "WSW", "WSW", 'P', "plateSteel", 'S', "ingotTungsten", 'C', ModItems.padlock, 'W', "plankWood" }));
		GameRegistry.addRecipe(new ItemStack(ModItems.sat_chip), new Object[] { "WWW", "CIC", "WWW", 'W', ModItems.wire_red_copper, 'C', ModItems.circuit_red_copper, 'I', ModItems.ingot_polymer });
		GameRegistry.addRecipe(new ItemStack(ModItems.sat_mapper), new Object[] { "H", "B", 'H', ModItems.sat_head_mapper, 'B', ModItems.sat_base });
		GameRegistry.addRecipe(new ItemStack(ModItems.sat_scanner), new Object[] { "H", "B", 'H', ModItems.sat_head_scanner, 'B', ModItems.sat_base });
		GameRegistry.addRecipe(new ItemStack(ModItems.sat_radar), new Object[] { "H", "B", 'H', ModItems.sat_head_radar, 'B', ModItems.sat_base });
		GameRegistry.addRecipe(new ItemStack(ModItems.sat_laser), new Object[] { "H", "B", 'H', ModItems.sat_head_laser, 'B', ModItems.sat_base });
		GameRegistry.addRecipe(new ItemStack(ModItems.sat_resonator), new Object[] { "H", "B", 'H', ModItems.sat_head_resonator, 'B', ModItems.sat_base });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.sat_mapper), new Object[] { ModBlocks.sat_mapper });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.sat_scanner), new Object[] { ModBlocks.sat_scanner });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.sat_radar), new Object[] { ModBlocks.sat_radar });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.sat_laser), new Object[] { ModBlocks.sat_laser });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.sat_resonator), new Object[] { ModBlocks.sat_resonator });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.sat_foeq), new Object[] { ModBlocks.sat_foeq });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.geiger_counter), new Object[] { ModBlocks.geiger });
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.sat_mapper), new Object[] { ModItems.sat_mapper });
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.sat_scanner), new Object[] { ModItems.sat_scanner });
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.sat_radar), new Object[] { ModItems.sat_radar });
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.sat_laser), new Object[] { ModItems.sat_laser });
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.sat_resonator), new Object[] { ModItems.sat_resonator });
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.sat_foeq), new Object[] { ModItems.sat_foeq });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.sat_interface), new Object[] { "ISI", "PCP", "PAP", 'I', "ingotSteel", 'S', ModItems.ingot_starmetal, 'P', ModItems.plate_polymer, 'C', ModItems.sat_chip, 'A', ModItems.circuit_gold }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.sat_coord), new Object[] { "SII", "SCA", "SPP", 'I', "ingotSteel", 'S', ModItems.ingot_starmetal, 'P', ModItems.plate_polymer, 'C', ModItems.sat_chip, 'A', ModItems.circuit_red_copper }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.machine_spp_bottom), new Object[] { "MDM", "LCL", "LWL", 'M', "ingotMagnetizedTungsten", 'D', ModItems.plate_desh, 'L', "plateLead", 'C', ModItems.circuit_gold, 'W', ModItems.coil_magnetized_tungsten }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.machine_spp_top), new Object[] { "LWL", "LCL", "MDM", 'M', "ingotMagnetizedTungsten", 'D', ModItems.plate_desh, 'L', "plateLead", 'C', ModItems.circuit_gold, 'W', ModItems.coil_magnetized_tungsten }));
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.machine_spp_bottom), new Object[] { ModBlocks.machine_spp_top });
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.machine_spp_top), new Object[] { ModBlocks.machine_spp_bottom });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.machine_transformer), new Object[] { "SDS", "MCM", "MCM", 'S', "ingotIron", 'D', "ingotRedCopperAlloy", 'M',ModItems.coil_advanced_alloy, 'C', ModItems.circuit_copper }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.machine_transformer_20), new Object[] { "SDS", "MCM", "MCM", 'S', "ingotIron", 'D', "ingotRedCopperAlloy", 'M', ModItems.coil_copper, 'C', ModItems.circuit_copper }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.machine_transformer_dnt), new Object[] { "SDS", "MCM", "MCM", 'S', ModItems.ingot_starmetal, 'D', "ingotDesh", 'M', ModBlocks.fwatz_conductor, 'C', ModItems.circuit_targeting_tier6 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.machine_transformer_dnt_20), new Object[] { "SDS", "MCM", "MCM", 'S', ModItems.ingot_starmetal, 'D', "ingotDesh", 'M', ModBlocks.fusion_conductor, 'C', ModItems.circuit_targeting_tier6 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.radiobox), new Object[] { "PLP", "PSP", "PLP", 'P', "plateSteel", 'S', ModItems.ring_starmetal, 'C', ModItems.fusion_core, 'L', "plateDenseLead" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.radiorec), new Object[] { "  W", "PCP", "PIP", 'W', ModItems.wire_copper, 'P', "plateSteel", 'C', ModItems.circuit_red_copper, 'I', "ingotPolymer" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.jackt), new Object[] { "S S", "LIL", "LIL", 'S', "plateSteel", 'L', Items.leather, 'I', ModItems.plate_polymer }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.jackt2), new Object[] { "S S", "LIL", "III", 'S', "plateSteel", 'L', Items.leather, 'I', ModItems.plate_polymer }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.vent_chlorine), new Object[] { "IGI", "ICI", "IDI", 'I', "plateIron", 'G', Blocks.iron_bars, 'C', ModItems.pellet_gas, 'D', Blocks.dispenser }));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.vent_chlorine_seal), new Object[] { "ISI", "SCS", "ISI", 'I', ModItems.ingot_saturnite, 'S', ModItems.ingot_starmetal, 'C', ModItems.chlorine_pinwheel });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.vent_cloud), new Object[] { "IGI", "ICI", "IDI", 'I', "plateIron", 'G', Blocks.iron_bars, 'C', ModItems.grenade_cloud, 'D', Blocks.dispenser }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.vent_pink_cloud), new Object[] { "IGI", "ICI", "IDI", 'I', "plateIron", 'G', Blocks.iron_bars, 'C', ModItems.grenade_pink_cloud, 'D', Blocks.dispenser }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.spikes, 4), new Object[] { "FFF", "BBB", "TTT", 'F', Items.flint, 'B', ModItems.bolt_tungsten, 'T', "ingotTungsten" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.custom_fall, 1), new Object[] { "IIP", "CHW", "IIP", 'I', ModItems.plate_polymer, 'P', "plateSaturnite", 'C', ModItems.circuit_red_copper, 'H', ModItems.hull_small_steel, 'W', ModItems.coil_copper }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.reactor_sensor, 1), new Object[] { "WPW", "CMC", "PPP", 'W', ModItems.wire_tungsten, 'P', "plateLead", 'C', ModItems.circuit_targeting_tier3, 'M', ModItems.magnetron }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.machine_controller, 1), new Object[] { "PGP", "ICI", "PRP", 'P', ModItems.plate_desh, 'G', "paneGlass", 'I', "ingotPolymer", 'R', "blockRedstone", 'C', ModItems.circuit_targeting_tier4 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.containment_box, 1), new Object[] { "LLL", "LCL", "LLL", 'L', "plateLead", 'C', Blocks.chest }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.absorber, 1), new Object[] { "ICI", "CPC", "ICI", 'I', "ingotCopper", 'C', "dustCoal", 'P', "dustLead" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.absorber_red, 1), new Object[] { "ICI", "CPC", "ICI", 'I', "ingotTitanium", 'C', "dustCoal", 'P', ModBlocks.absorber }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.absorber_green, 1), new Object[] { "ICI", "CPC", "ICI", 'I', "ingotPolymer", 'C', ModItems.powder_desh_mix, 'P', ModBlocks.absorber_red }));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.absorber_pink, 1), new Object[] { "ICI", "CPC", "ICI", 'I', ModItems.ingot_saturnite, 'C', ModItems.powder_nitan_mix, 'P', ModBlocks.absorber_green });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.decon, 1), new Object[] { "BGB", "SAS", "BSB", 'B', "ingotBeryllium", 'G', Blocks.iron_bars, 'S', "ingotSteel", 'A', ModBlocks.absorber }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.machine_amgen, 1), new Object[] { "ITI", "TAT", "ITI", 'I', "ingotAdvanced", 'T', ModItems.thermo_element, 'A', ModBlocks.absorber }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.machine_geo, 1), new Object[] { "ITI", "PCP", "ITI", 'I', "ingotDuraSteel", 'T', ModItems.thermo_element, 'P', ModItems.board_copper, 'C', ModBlocks.red_wire_coated }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.machine_minirtg, 1), new Object[] { "LLL", "PPP", "TRT", 'L', "plateLead", 'P', ModItems.billet_pu238, 'T', ModItems.thermo_element, 'R', ModItems.rtg_unit }));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.machine_powerrtg, 1), new Object[] { "SRS", "PTP", "SRS", 'S', ModItems.ingot_starmetal, 'R', ModItems.rtg_unit, 'P', ModItems.billet_polonium, 'T', ModItems.powder_tennessine });

		GameRegistry.addRecipe(new ItemStack(ModBlocks.pink_planks, 4), new Object[] { "W", 'W', ModBlocks.pink_log });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.pink_slab, 6), new Object[] { "WWW", 'W', ModBlocks.pink_planks });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.pink_stairs, 6), new Object[] { "W  ", "WW ", "WWW", 'W', ModBlocks.pink_planks });

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.door_metal, 1), new Object[] { "II", "SS", "II", 'I', "plateIron", 'S', "plateSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.door_office, 1), new Object[] { "II", "SS", "II", 'I', "plankWood", 'S', "plateIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.door_bunker, 1), new Object[] { "II", "SS", "II", 'I', "plateSteel", 'S', "plateLead" }));

		GameRegistry.addShapelessRecipe(new ItemStack(Items.paper, 1), new Object[] { new ItemStack(ModItems.assembly_template, 1, OreDictionary.WILDCARD_VALUE) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.paper, 1), new Object[] { new ItemStack(ModItems.chemistry_template, 1, OreDictionary.WILDCARD_VALUE) });

        for (int i = 1; i < FluidType.values().length; ++i)
        {
    		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.fluid_duct, 1, i), new Object[] { new ItemStack(ModBlocks.fluid_duct, 1), new ItemStack(ModItems.fluid_identifier, 1, i) });
    		
    		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.fluid_duct, 8, i), new Object[] { new ItemStack(ModBlocks.fluid_duct, 8), new ItemStack(ModBlocks.fluid_duct, 8), 
    				new ItemStack(ModBlocks.fluid_duct, 8), new ItemStack(ModBlocks.fluid_duct, 8), new ItemStack(ModBlocks.fluid_duct, 8), 
    				new ItemStack(ModBlocks.fluid_duct, 8), new ItemStack(ModBlocks.fluid_duct, 8), new ItemStack(ModBlocks.fluid_duct, 8), new ItemStack(ModItems.fluid_identifier, 1, i) });
    		
    		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.fluid_duct, 1, i), new Object[] { new ItemStack(ModItems.fluid_duct, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.fluid_identifier, 1, i) });
    		
    		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.fluid_duct, 8, i), new Object[] { new ItemStack(ModItems.fluid_duct, 8, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.fluid_duct, 8, OreDictionary.WILDCARD_VALUE), 
    				new ItemStack(ModItems.fluid_duct, 8, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.fluid_duct, 8, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.fluid_duct, 8, OreDictionary.WILDCARD_VALUE), 
    				new ItemStack(ModItems.fluid_duct, 8, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.fluid_duct, 8, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.fluid_duct, 8, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.fluid_identifier, 1, i) });
        }
        
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.fluid_duct, 1), new Object[] { new ItemStack(ModItems.fluid_duct, 1, OreDictionary.WILDCARD_VALUE) });

		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.redstone_depleted, 1), new Object[] { new ItemStack(ModItems.battery_su, 1, OreDictionary.WILDCARD_VALUE) });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.redstone_depleted, 2), new Object[] { new ItemStack(ModItems.battery_su_l, 1, OreDictionary.WILDCARD_VALUE) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.redstone, 1), new Object[] { ModItems.redstone_depleted, ModItems.redstone_depleted });

		GameRegistry.addRecipe(new ItemStack(Blocks.torch, 3), new Object[] { "L", "S", 'L', ModItems.lignite, 'S', Items.stick });
		GameRegistry.addRecipe(new ItemStack(Blocks.torch, 6), new Object[] { "L", "S", 'L', ModItems.briquette_lignite, 'S', Items.stick });
		GameRegistry.addRecipe(new ItemStack(Blocks.torch, 8), new Object[] { "L", "S", 'L', ModItems.coke, 'S', Items.stick });

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.machine_missile_assembly, 1), new Object[] { "PWP", "SSS", "CCC", 'P', ModItems.pedestal_steel, 'W', ModItems.wrench, 'S', "plateSteel", 'C', ModBlocks.steel_scaffold }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.struct_launcher, 1), new Object[] { "PPP", "SDS", "CCC", 'P', "plateSteel", 'S', ModBlocks.steel_scaffold, 'D', ModItems.pipes_steel, 'C', ModBlocks.concrete_smooth }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.struct_launcher, 1), new Object[] { "PPP", "SDS", "CCC", 'P', "plateSteel", 'S', ModBlocks.steel_scaffold, 'D', ModItems.pipes_steel, 'C', ModBlocks.concrete }));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.struct_scaffold, 1), new Object[] { "SSS", "DCD", "SSS", 'S', ModBlocks.steel_scaffold, 'D', ModBlocks.fluid_duct, 'C', ModBlocks.red_cable });

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.seg_10, 1), new Object[] { "P", "S", "B", 'P', "plateAluminum", 'S', ModBlocks.steel_scaffold, 'B', ModBlocks.steel_beam }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.seg_15, 1), new Object[] { "PP", "SS", "BB", 'P', "plateTitanium", 'S', ModBlocks.steel_scaffold, 'B', ModBlocks.steel_beam }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.seg_20, 1), new Object[] { "PGP", "SSS", "BBB", 'P', "plateSteel", 'G', "plateGold", 'S', ModBlocks.steel_scaffold, 'B', ModBlocks.steel_beam }));

		GameRegistry.addRecipe(new ItemStack(ModBlocks.struct_launcher_core, 1), new Object[] { "SCS", "SIS", "BEB", 'S', ModBlocks.steel_scaffold, 'I', Blocks.iron_bars, 'C', ModItems.circuit_targeting_tier3, 'B', ModBlocks.struct_launcher, 'E', ModBlocks.machine_battery });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.struct_launcher_core_large, 1), new Object[] { "SIS", "ICI", "BEB", 'S', ModItems.circuit_red_copper, 'I', Blocks.iron_bars, 'C', ModItems.circuit_targeting_tier4, 'B', ModBlocks.struct_launcher, 'E', ModBlocks.machine_battery });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.struct_soyuz_core, 1), new Object[] { "CUC", "TST", "TBT", 'C', ModItems.circuit_targeting_tier4, 'U', ModItems.upgrade_power_3, 'T', ModBlocks.barrel_steel, 'S', ModBlocks.steel_scaffold, 'B', ModBlocks.machine_lithium_battery });

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.obj_tester, 1), new Object[] { "P", "I", "S", 'P', ModItems.polaroid, 'I', ModItems.flame_pony, 'S', "plateSteel" }));

		GameRegistry.addRecipe(new ItemStack(ModBlocks.fence_metal, 6), new Object[] { "BIB", "BIB", 'B', Blocks.iron_bars, 'I', Items.iron_ingot });

		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.waste_trinitite), new Object[] { new ItemStack(Blocks.sand, 1, 0), ModItems.trinitite });
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.waste_trinitite_red), new Object[] { new ItemStack(Blocks.sand, 1, 1), ModItems.trinitite });
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModBlocks.sand_uranium, 8), new Object[] { "sand", "sand", "sand", "sand", "sand", "sand", "sand", "sand", "dustUranium" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModBlocks.sand_polonium, 8), new Object[] { "sand", "sand", "sand", "sand", "sand", "sand", "sand", "sand", "dustPolonium" }));

		GameRegistry.addRecipe(new ItemStack(ModItems.rune_blank, 1), new Object[] { "PSP", "SDS", "PSP", 'P', ModItems.powder_magic, 'S', ModItems.ingot_starmetal, 'D', ModItems.dynosphere_dineutronium_charged });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rune_isa, 1), new Object[] { ModItems.rune_blank, ModItems.powder_spark_mix, ModItems.singularity_counter_resonant });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rune_dagaz, 1), new Object[] { ModItems.rune_blank, ModItems.powder_spark_mix, ModItems.singularity });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rune_hagalaz, 1), new Object[] { ModItems.rune_blank, ModItems.powder_spark_mix, ModItems.singularity_super_heated });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rune_jera, 1), new Object[] { ModItems.rune_blank, ModItems.powder_spark_mix, ModItems.singularity_spark });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rune_thurisaz, 1), new Object[] { ModItems.rune_blank, ModItems.powder_spark_mix, ModItems.black_hole });
		GameRegistry.addRecipe(new ItemStack(ModItems.ams_lens, 1), new Object[] { "PDP", "GDG", "PDP", 'P', ModItems.plate_dineutronium, 'G', ModBlocks.reinforced_glass, 'D', Blocks.diamond_block });
		GameRegistry.addRecipe(new ItemStack(ModItems.ams_catalyst_blank, 1), new Object[] { "TET", "ETE", "TET", 'T', ModItems.powder_tennessine, 'E', ModItems.ingot_euphemium});
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ams_catalyst_lithium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_isa, ModItems.rune_isa, ModItems.rune_jera, ModItems.rune_jera, "dustLithium", "dustLithium", "dustLithium", "dustLithium" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ams_catalyst_beryllium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_isa, ModItems.rune_dagaz, ModItems.rune_jera, ModItems.rune_jera, "dustBeryllium", "dustBeryllium", "dustBeryllium", "dustBeryllium" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ams_catalyst_copper, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_dagaz, ModItems.rune_dagaz, ModItems.rune_jera, ModItems.rune_jera, "dustCopper", "dustCopper", "dustCopper", "dustCopper" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ams_catalyst_cobalt, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_dagaz, ModItems.rune_hagalaz, ModItems.rune_jera, ModItems.rune_jera, "dustCobalt", "dustCobalt", "dustCobalt", "dustCobalt" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ams_catalyst_tungsten, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_hagalaz, ModItems.rune_hagalaz, ModItems.rune_jera, ModItems.rune_jera, "dustTungsten", "dustTungsten", "dustTungsten", "dustTungsten" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ams_catalyst_aluminium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_isa, ModItems.rune_isa, ModItems.rune_jera, ModItems.rune_thurisaz, "dustAluminum", "dustAluminum", "dustAluminum", "dustAluminum" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ams_catalyst_iron, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_isa, ModItems.rune_dagaz, ModItems.rune_jera, ModItems.rune_thurisaz, "dustIron", "dustIron", "dustIron", "dustIron" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ams_catalyst_strontium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_dagaz, ModItems.rune_dagaz, ModItems.rune_jera, ModItems.rune_thurisaz, "dustStrontium", "dustStrontium", "dustStrontium", "dustStrontium" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ams_catalyst_niobium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_dagaz, ModItems.rune_hagalaz, ModItems.rune_jera, ModItems.rune_thurisaz, "dustNiobium", "dustNiobium", "dustNiobium", "dustNiobium" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ams_catalyst_cerium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_hagalaz, ModItems.rune_hagalaz, ModItems.rune_jera, ModItems.rune_thurisaz, "dustCerium", "dustCerium", "dustCerium", "dustCerium" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ams_catalyst_caesium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_isa, ModItems.rune_isa, ModItems.rune_thurisaz, ModItems.rune_thurisaz, "dustCaesium", "dustCaesium", "dustCaesium", "dustCaesium" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ams_catalyst_thorium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_isa, ModItems.rune_dagaz, ModItems.rune_thurisaz, ModItems.rune_thurisaz, "dustThorium", "dustThorium", "dustThorium", "dustThorium" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ams_catalyst_euphemium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_dagaz, ModItems.rune_dagaz, ModItems.rune_thurisaz, ModItems.rune_thurisaz, "dustEuphemium", "dustEuphemium", "dustEuphemium", "dustEuphemium" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ams_catalyst_schrabidium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_dagaz, ModItems.rune_hagalaz, ModItems.rune_thurisaz, ModItems.rune_thurisaz, "dustSchrabidium", "dustSchrabidium", "dustSchrabidium", "dustSchrabidium" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ams_catalyst_dineutronium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_hagalaz, ModItems.rune_hagalaz, ModItems.rune_thurisaz, ModItems.rune_thurisaz, "dustDineutronium", "dustDineutronium", "dustDineutronium", "dustDineutronium" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.dfc_core, 1), new Object[] { "DLD", "LML", "DLD", 'D', ModItems.plate_dineutronium, 'L', "blockLead", 'M', ModBlocks.block_meteor }));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.dfc_emitter, 1), new Object[] { "SDS", "TXL", "SDS", 'S', ModItems.ingot_starmetal, 'D', ModItems.plate_desh, 'T', ModBlocks.machine_transformer_dnt, 'X', ModItems.crystal_xen, 'L', ModItems.sat_head_laser });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.dfc_receiver, 1), new Object[] { "SDS", "TXL", "SDS", 'S', ModItems.ingot_starmetal, 'D', ModItems.plate_desh, 'T', ModBlocks.machine_transformer_dnt, 'X', ModBlocks.sellafield_core, 'L', ModItems.hull_small_steel });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.dfc_injector, 1), new Object[] { "SDS", "TXL", "SDS", 'S', ModItems.ingot_starmetal, 'D', ModItems.plate_combine_steel, 'T', ModBlocks.machine_fluidtank, 'X', ModItems.motor, 'L', ModItems.pipes_steel }));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.dfc_stabilizer, 1), new Object[] { "SDS", "TXL", "SDS", 'S', ModItems.ingot_starmetal, 'D', ModItems.plate_desh, 'T', ModItems.singularity_spark, 'X', ModItems.magnet_circular, 'L', ModItems.crystal_xen });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.barrel_plastic, 1), new Object[] { "IPI", "I I", "IPI", 'I', ModItems.plate_polymer, 'P', "plateAluminum" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.barrel_iron, 1), new Object[] { "IPI", "I I", "IPI", 'I', "plateIron", 'P', "ingotIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.barrel_steel, 1), new Object[] { "IPI", "I I", "IPI", 'I', "plateSteel", 'P', "ingotSteel" }));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.barrel_antimatter, 1), new Object[] { "IPI", "IPI", "IPI", 'I', ModItems.plate_saturnite, 'P', ModItems.coil_advanced_torus });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.tesla, 1), new Object[] { "CCC", "PIP", "WTW", 'C', ModItems.coil_copper, 'I', "ingotIron", 'P', "ingotPolymer", 'T', ModBlocks.machine_transformer, 'W', "plankWood" }));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.struct_plasma_core, 1), new Object[] { "CBC", "BHB", "CBC", 'C', ModItems.circuit_gold, 'B', ModBlocks.machine_lithium_battery, 'H', ModBlocks.fusion_heater });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.circuit_red_copper, 48), new Object[] { ModBlocks.fusion_core });
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.fusion_heater), new Object[] { ModBlocks.fusion_hatch });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.energy_core), new Object[] { ModItems.fusion_core, ModItems.fuse });

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.upgrade_nullifier, 1), new Object[] { "SPS", "PUP", "SPS", 'S', "plateSteel", 'P', ModItems.powder_fire, 'U', ModItems.upgrade_template }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.upgrade_smelter, 1), new Object[] { "PHP", "CUC", "DTD", 'P', "plateCopper", 'H', Blocks.hopper, 'C', ModItems.coil_tungsten, 'U', ModItems.upgrade_template, 'D', ModItems.coil_copper, 'T', ModBlocks.machine_transformer }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.upgrade_shredder, 1), new Object[] { "PHP", "CUC", "DTD", 'P', ModItems.motor, 'H', Blocks.hopper, 'C', ModItems.blades_advanced_alloy, 'U', ModItems.upgrade_smelter, 'D', "plateTitanium", 'T', ModBlocks.machine_transformer }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.upgrade_centrifuge, 1), new Object[] { "PHP", "PUP", "DTD", 'P', ModItems.centrifuge_element, 'H', Blocks.hopper, 'U', ModItems.upgrade_shredder, 'D', "ingotPolymer", 'T', ModBlocks.machine_transformer }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.upgrade_crystallizer, 1), new Object[] { "PHP", "CUC", "DTD", 'P', new ItemStack(ModItems.fluid_barrel_full, 1, FluidType.ACID.ordinal()), 'H', ModItems.circuit_targeting_tier4, 'C', ModBlocks.barrel_steel, 'U', ModItems.upgrade_centrifuge, 'D', ModItems.motor, 'T', ModBlocks.machine_transformer }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.plate_armor_titanium, 1), new Object[] { "NPN", "PIP", "NPN", 'N', ModItems.bolt_tungsten, 'P', "plateTitanium", 'I', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.plate_armor_ajr, 1), new Object[] { "NPN", "PIP", "NPN", 'N', "plateIron", 'P', ModItems.plate_saturnite, 'I', ModItems.plate_armor_titanium }));
		GameRegistry.addRecipe(new ItemStack(ModItems.plate_armor_hev, 1), new Object[] { "NPN", "PIP", "NPN", 'N', ModItems.wire_tungsten, 'P', ModItems.plate_advanced_alloy, 'I', ModItems.plate_armor_titanium });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.plate_armor_lunar, 1), new Object[] { "NPN", "PIP", "NPN", 'N', ModItems.wire_magnetized_tungsten, 'P', "plateDenseLead", 'I', ModItems.ingot_starmetal }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.plate_armor_fau, 1), new Object[] { "MDM", "DYD", "MDM", 'M', ModItems.ingot_meteorite_forged, 'D', "ingotDesh", 'Y', ModItems.billet_yharonite }));
		
		GameRegistry.addRecipe(new ItemStack(ModItems.mech_key, 1), new Object[] { "MCM", "MKM", "MMM", 'M', ModItems.ingot_meteorite_forged, 'C', ModItems.coin_maskman, 'K', ModItems.key });

		GameRegistry.addRecipe(new ItemStack(ModBlocks.hadron_coil_alloy, 1), new Object[] { "WWW", "WCW", "WWW", 'W', ModItems.wire_advanced_alloy, 'C', ModBlocks.fusion_conductor });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.hadron_coil_gold, 1), new Object[] { "GGG", "GCG", "GGG", 'G', ModItems.coil_gold, 'C', ModBlocks.hadron_coil_alloy });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.hadron_coil_neodymium, 1), new Object[] { " G ", "GCG", " G ", 'G', ModItems.powder_neodymium, 'C', ModBlocks.hadron_coil_gold });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.hadron_coil_magtung, 1), new Object[] { "WWW", "WCW", "WWW", 'W', ModItems.wire_magnetized_tungsten, 'C', ModBlocks.fwatz_conductor });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.hadron_coil_schrabidium, 1), new Object[] { "WSW", "SCS", "WSW", 'W', ModItems.wire_schrabidium, 'S', ModItems.powder_schrabidium, 'C', ModBlocks.hadron_coil_magtung });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.hadron_coil_schrabidate, 1), new Object[] { "WSW", "SCS", "WSW", 'W', ModItems.wire_schrabidium, 'S', ModItems.powder_schrabidate, 'C', ModBlocks.hadron_coil_schrabidium });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.hadron_coil_starmetal, 1), new Object[] { "NSN", "SCS", "NSN", 'S', ModItems.ring_starmetal, 'N', ModBlocks.hadron_coil_neodymium, 'C', ModBlocks.hadron_coil_schrabidate });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.hadron_coil_chlorophyte, 1), new Object[] { "TCT", "CSC", "TCT", 'T', "dustTungsten", 'C', ModItems.powder_chlorophyte, 'S', ModBlocks.hadron_coil_starmetal }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.hadron_diode, 1), new Object[] { "CIC", "ISI", "CIC", 'C', ModBlocks.hadron_coil_alloy, 'I', "ingotSteel", 'S', ModItems.circuit_gold }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.hadron_plating, 1), new Object[] { "IPI", "P P", "IPI", 'I', "ingotSteel", 'P', "plateSteel" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModBlocks.hadron_plating_blue, 1), new Object[] { ModBlocks.hadron_plating, "dyeBlue" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModBlocks.hadron_plating_black, 1), new Object[] { ModBlocks.hadron_plating, "dyeBlack" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModBlocks.hadron_plating_yellow, 1), new Object[] { ModBlocks.hadron_plating, "dyeYellow" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModBlocks.hadron_plating_striped, 1), new Object[] { ModBlocks.hadron_plating, "dyeBlack", "dyeYellow" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModBlocks.hadron_plating_glass, 1), new Object[] { ModBlocks.hadron_plating, "blockGlass" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModBlocks.hadron_plating_voltz, 1), new Object[] { ModBlocks.hadron_plating, "dyeRed" }));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.hadron_power, 1), new Object[] { "STS", "CPC", "STS", 'S', ModItems.ingot_saturnite, 'T', ModBlocks.machine_transformer, 'C', ModItems.circuit_targeting_tier3, 'P', ModBlocks.hadron_plating_blue });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.hadron_power_10m, 1), new Object[] { "HF", 'H', ModBlocks.hadron_power, 'F', ModItems.fuse });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.hadron_power_100m, 1), new Object[] { "HF", 'H', ModBlocks.hadron_power_10m, 'F', ModItems.fuse });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.hadron_power_1g, 1), new Object[] { "HF", 'H', ModBlocks.hadron_power_100m, 'F', ModItems.fuse });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.hadron_power_10g, 1), new Object[] { "HF", 'H', ModBlocks.hadron_power_1g, 'F', ModItems.fuse });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.hadron_analysis, 1), new Object[] { "IPI", "PCP", "IPI", 'I', "ingotTitanium", 'P', "plateDenseLead", 'C', ModItems.circuit_gold }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModBlocks.hadron_analysis_glass, 1), new Object[] { ModBlocks.hadron_analysis, "blockGlass" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.hadron_access, 1), new Object[] { "IGI", "CRC", "IPI", 'I', ModItems.plate_polymer, 'G', "paneGlass", 'C', ModItems.circuit_aluminium, 'R', "blockRedstone", 'P', ModBlocks.hadron_plating_blue }));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.hadron_core, 1), new Object[] { "CCC", "DSD", "CCC", 'C', ModBlocks.hadron_coil_alloy, 'D', ModBlocks.hadron_diode, 'S', ModItems.circuit_schrabidium });

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ingot_schrabidium, 8), new Object[] { "UUU", "UPU", "UUU", 'U', "ingotUranium", 'P', new ItemStack(ModItems.particle_higgs).setStackDisplayName("Higgs Boson (Temporary Recipe)") }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ingot_euphemium, 8), new Object[] { "UUU", "UPU", "UUU", 'U', "ingotPlutonium", 'P', new ItemStack(ModItems.particle_dark).setStackDisplayName("Dark Matter (Temporary Recipe)") }));
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_dineutronium, 8), new Object[] { "UUU", "UPU", "UUU", 'U', ModItems.ingot_schrabidate, 'P', new ItemStack(ModItems.particle_sparkticle).setStackDisplayName("Sparkticle (Temporary Recipe)") });

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.fireworks, 1), new Object[] { "PPP", "PPP", "WIW", 'P', Items.paper, 'W', "plankWood", 'I', "ingotIron" }));

		if(GeneralConfig.enableBabyMode) {
			GameRegistry.addShapelessRecipe(new ItemStack(ModItems.cordite, 3), new Object[] { ModItems.ballistite, Items.gunpowder, new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE) });
			GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ingot_semtex, 3), new Object[] { Items.slime_ball, Blocks.tnt, ModItems.niter });
			GameRegistry.addShapelessRecipe(new ItemStack(ModItems.canister_fuel, 1), new Object[] { ModItems.canister_oil, Items.redstone });

			GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.ore_uranium, 1), new Object[] { ModBlocks.ore_uranium_scorched, Items.water_bucket });
			GameRegistry.addRecipe(new ItemStack(ModBlocks.ore_uranium, 8), new Object[] { "OOO", "OBO", "OOO", 'O', ModBlocks.ore_uranium_scorched, 'B', Items.water_bucket });
			GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.ore_nether_uranium, 1), new Object[] { ModBlocks.ore_nether_uranium_scorched, Items.water_bucket });
			GameRegistry.addRecipe(new ItemStack(ModBlocks.ore_nether_uranium, 8), new Object[] { "OOO", "OBO", "OOO", 'O', ModBlocks.ore_nether_uranium_scorched, 'B', Items.water_bucket });
			GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.ore_gneiss_uranium, 1), new Object[] { ModBlocks.ore_gneiss_uranium_scorched, Items.water_bucket });
			GameRegistry.addRecipe(new ItemStack(ModBlocks.ore_gneiss_uranium, 8), new Object[] { "OOO", "OBO", "OOO", 'O', ModBlocks.ore_gneiss_uranium_scorched, 'B', Items.water_bucket });

			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.plate_iron, 4), new Object[] { "##", "##", '#', "ingotIron" }));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.plate_gold, 4), new Object[] { "##", "##", '#', "ingotGold" }));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.plate_aluminium, 4), new Object[] { "##", "##", '#', "ingotAluminum" }));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.plate_titanium, 4), new Object[] { "##", "##", '#', "ingotTitanium" }));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.plate_copper, 4), new Object[] { "##", "##", '#', "ingotCopper" }));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.plate_lead, 4), new Object[] { "##", "##", '#', "ingotLead" }));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.plate_steel, 4), new Object[] { "##", "##", '#', "ingotSteel" }));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.plate_schrabidium, 4), new Object[] { "##", "##", '#', "ingotSchrabidium" }));
			GameRegistry.addRecipe(new ItemStack(ModItems.plate_advanced_alloy, 4), new Object[] { "##", "##", '#', ModItems.ingot_advanced_alloy });
			GameRegistry.addRecipe(new ItemStack(ModItems.plate_saturnite, 4), new Object[] { "##", "##", '#', ModItems.ingot_saturnite });
			GameRegistry.addRecipe(new ItemStack(ModItems.plate_combine_steel, 4), new Object[] { "##", "##", '#', ModItems.ingot_combine_steel });
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.neutron_reflector, 4), new Object[] { "##", "##", '#', "ingotTungsten" }));

			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.wire_aluminium, 16), new Object[] { "###", '#', "ingotAluminum" }));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.wire_copper, 16), new Object[] { "###", '#', "ingotCopper" }));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.wire_tungsten, 16), new Object[] { "###", '#', "ingotTungsten" }));
			GameRegistry.addRecipe(new ItemStack(ModItems.wire_red_copper, 16), new Object[] { "###", '#', ModItems.ingot_red_copper });
			GameRegistry.addRecipe(new ItemStack(ModItems.wire_advanced_alloy, 16), new Object[] { "###", '#', ModItems.ingot_advanced_alloy });
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.wire_gold, 16), new Object[] { "###", '#', "ingotGold" }));
			GameRegistry.addRecipe(new ItemStack(ModItems.wire_schrabidium, 16), new Object[] { "###", '#', ModItems.ingot_schrabidium });
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.book_of_), new Object[] { "BGB", "GAG", "BGB", 'B', ModItems.egg_balefire_shard, 'G', "ingotGold", 'A', Items.book }));
		}
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
	
	public static void AddSmeltingRec()
	{
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_thorium), new ItemStack(ModItems.ingot_th232), 3.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_uranium), new ItemStack(ModItems.ingot_uranium), 6.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_uranium_scorched), new ItemStack(ModItems.ingot_uranium), 6.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_nether_uranium), new ItemStack(ModItems.ingot_uranium), 12.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_nether_uranium_scorched), new ItemStack(ModItems.ingot_uranium), 12.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_nether_plutonium), new ItemStack(ModItems.ingot_plutonium), 24.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_titanium), new ItemStack(ModItems.ingot_titanium), 3.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_copper), new ItemStack(ModItems.ingot_copper), 2.5F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_tungsten), new ItemStack(ModItems.ingot_tungsten), 6.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_nether_tungsten), new ItemStack(ModItems.ingot_tungsten), 12.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_aluminium), new ItemStack(ModItems.ingot_aluminium), 2.5F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_lead), new ItemStack(ModItems.ingot_lead), 3.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_beryllium), new ItemStack(ModItems.ingot_beryllium), 2.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_schrabidium), new ItemStack(ModItems.ingot_schrabidium), 128.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_nether_schrabidium), new ItemStack(ModItems.ingot_schrabidium), 256.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_meteor_uranium), new ItemStack(ModItems.ingot_uranium, 2), 12.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_meteor_thorium), new ItemStack(ModItems.ingot_th232, 2), 6.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_meteor_titanium), new ItemStack(ModItems.ingot_titanium, 3), 6.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_meteor_copper), new ItemStack(ModItems.ingot_copper, 3), 5.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_meteor_tungsten), new ItemStack(ModItems.ingot_tungsten, 3), 12.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_meteor_aluminium), new ItemStack(ModItems.ingot_aluminium, 3), 5.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_meteor_lead), new ItemStack(ModItems.ingot_lead, 3), 6.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_meteor_lithium), new ItemStack(ModItems.lithium), 20.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_meteor_starmetal), new ItemStack(ModItems.ingot_starmetal), 50.0F);

		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_gneiss_iron), new ItemStack(Items.iron_ingot), 5.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_gneiss_gold), new ItemStack(Items.gold_ingot), 5.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_gneiss_uranium), new ItemStack(ModItems.ingot_uranium), 12.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_gneiss_uranium_scorched), new ItemStack(ModItems.ingot_uranium), 12.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_gneiss_copper), new ItemStack(ModItems.ingot_copper), 5F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_gneiss_lithium), new ItemStack(ModItems.lithium), 10F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_gneiss_schrabidium), new ItemStack(ModItems.ingot_schrabidium), 256.0F);

		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_australium), new ItemStack(ModItems.nugget_australium), 2.5F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_weidanium), new ItemStack(ModItems.nugget_weidanium, 6), 16.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_reiium), new ItemStack(ModItems.ingot_reiium), 24.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_unobtainium), new ItemStack(ModItems.nugget_unobtainium, 4), 10.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_daffergon), new ItemStack(ModItems.nugget_daffergon, 3), 8.0F);
		GameRegistry.addSmelting(Item.getItemFromBlock(ModBlocks.ore_verticium), new ItemStack(ModItems.ingot_verticium), 24.0F);
		GameRegistry.addSmelting(ModItems.powder_australium, new ItemStack(ModItems.ingot_australium), 5.0F);
		GameRegistry.addSmelting(ModItems.powder_weidanium, new ItemStack(ModItems.ingot_weidanium), 5.0F);
		GameRegistry.addSmelting(ModItems.powder_reiium, new ItemStack(ModItems.ingot_reiium), 5.0F);
		GameRegistry.addSmelting(ModItems.powder_unobtainium, new ItemStack(ModItems.ingot_unobtainium), 5.0F);
		GameRegistry.addSmelting(ModItems.powder_daffergon, new ItemStack(ModItems.ingot_daffergon), 5.0F);
		GameRegistry.addSmelting(ModItems.powder_verticium, new ItemStack(ModItems.ingot_verticium), 5.0F);

		GameRegistry.addSmelting(ModItems.powder_lead, new ItemStack(ModItems.ingot_lead), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_neptunium, new ItemStack(ModItems.ingot_neptunium), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_polonium, new ItemStack(ModItems.ingot_polonium), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_schrabidium, new ItemStack(ModItems.ingot_schrabidium), 5.0F);
		GameRegistry.addSmelting(ModItems.powder_schrabidate, new ItemStack(ModItems.ingot_schrabidate), 5.0F);
		GameRegistry.addSmelting(ModItems.powder_euphemium, new ItemStack(ModItems.ingot_euphemium), 10.0F);
		GameRegistry.addSmelting(ModItems.powder_aluminium, new ItemStack(ModItems.ingot_aluminium), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_beryllium, new ItemStack(ModItems.ingot_beryllium), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_copper, new ItemStack(ModItems.ingot_copper), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_gold, new ItemStack(Items.gold_ingot), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_iron, new ItemStack(Items.iron_ingot), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_titanium, new ItemStack(ModItems.ingot_titanium), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_cobalt, new ItemStack(ModItems.ingot_cobalt), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_tungsten, new ItemStack(ModItems.ingot_tungsten), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_uranium, new ItemStack(ModItems.ingot_uranium), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_thorium, new ItemStack(ModItems.ingot_th232), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_plutonium, new ItemStack(ModItems.ingot_plutonium), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_advanced_alloy, new ItemStack(ModItems.ingot_advanced_alloy), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_combine_steel, new ItemStack(ModItems.ingot_combine_steel), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_magnetized_tungsten, new ItemStack(ModItems.ingot_magnetized_tungsten), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_red_copper, new ItemStack(ModItems.ingot_red_copper), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_steel, new ItemStack(ModItems.ingot_steel), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_lithium, new ItemStack(ModItems.lithium), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_dura_steel, new ItemStack(ModItems.ingot_dura_steel), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_polymer, new ItemStack(ModItems.ingot_polymer), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_lanthanium, new ItemStack(ModItems.ingot_lanthanium), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_actinium, new ItemStack(ModItems.ingot_actinium), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_desh, new ItemStack(ModItems.ingot_desh), 1.0F);
		GameRegistry.addSmelting(ModItems.powder_dineutronium, new ItemStack(ModItems.ingot_dineutronium), 5.0F);
		GameRegistry.addSmelting(ModItems.powder_asbestos, new ItemStack(ModItems.ingot_asbestos), 1.0F);

		GameRegistry.addSmelting(ModItems.powder_coal, new ItemStack(ModItems.coke), 1.0F);
		GameRegistry.addSmelting(ModItems.briquette_lignite, new ItemStack(ModItems.coke), 1.0F);

		GameRegistry.addSmelting(ModItems.combine_scrap, new ItemStack(ModItems.ingot_combine_steel), 1.0F);
		GameRegistry.addSmelting(ModItems.tank_waste, new ItemStack(ModItems.tank_waste), 0.0F);
		
		GameRegistry.addSmelting(Items.bone, new ItemStack(Items.slime_ball, 3), 0.0F);
		GameRegistry.addSmelting(new ItemStack(Items.dye, 1, 15), new ItemStack(Items.slime_ball, 1), 0.0F);
		GameRegistry.addSmelting(new ItemStack(Blocks.gravel, 1), new ItemStack(Blocks.cobblestone, 1), 0.0F);
		GameRegistry.addSmelting(new ItemStack(ModBlocks.gravel_obsidian), new ItemStack(Blocks.obsidian), 0.0F);
		GameRegistry.addSmelting(new ItemStack(ModBlocks.gravel_diamond), new ItemStack(Items.diamond), 3.0F);
		GameRegistry.addSmelting(new ItemStack(ModBlocks.sand_uranium), new ItemStack(ModBlocks.glass_uranium), 0.25F);
		GameRegistry.addSmelting(new ItemStack(ModBlocks.sand_polonium), new ItemStack(ModBlocks.glass_polonium), 0.75F);
		GameRegistry.addSmelting(new ItemStack(ModBlocks.waste_trinitite), new ItemStack(ModBlocks.glass_trinitite), 0.25F);
		GameRegistry.addSmelting(new ItemStack(ModBlocks.waste_trinitite_red), new ItemStack(ModBlocks.glass_trinitite), 0.25F);
		
		GameRegistry.addSmelting(ModItems.ingot_schraranium, new ItemStack(ModItems.nugget_schrabidium, 1), 2.0F);

		GameRegistry.addSmelting(ModItems.lodestone, new ItemStack(ModItems.crystal_iron, 1), 5.0F);
		GameRegistry.addSmelting(ModItems.crystal_iron, new ItemStack(Items.iron_ingot, 2), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_gold, new ItemStack(Items.gold_ingot, 2), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_redstone, new ItemStack(Items.redstone, 6), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_diamond, new ItemStack(Items.diamond, 2), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_uranium, new ItemStack(ModItems.ingot_uranium, 2), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_thorium, new ItemStack(ModItems.ingot_th232, 2), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_plutonium, new ItemStack(ModItems.ingot_plutonium, 2), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_titanium, new ItemStack(ModItems.ingot_titanium, 2), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_sulfur, new ItemStack(ModItems.sulfur, 6), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_niter, new ItemStack(ModItems.niter, 6), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_copper, new ItemStack(ModItems.ingot_copper, 2), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_tungsten, new ItemStack(ModItems.ingot_tungsten, 2), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_aluminium, new ItemStack(ModItems.ingot_aluminium, 2), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_fluorite, new ItemStack(ModItems.fluorite, 6), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_beryllium, new ItemStack(ModItems.ingot_beryllium, 2), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_lead, new ItemStack(ModItems.ingot_lead, 2), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_schraranium, new ItemStack(ModItems.nugget_schrabidium, 2), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_schrabidium, new ItemStack(ModItems.ingot_schrabidium, 2), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_rare, new ItemStack(ModItems.powder_desh_mix, 1), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_phosphorus, new ItemStack(ModItems.powder_fire, 6), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_lithium, new ItemStack(ModItems.lithium, 2), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_starmetal, new ItemStack(ModItems.ingot_starmetal, 2), 2.0F);
		GameRegistry.addSmelting(ModItems.crystal_trixite, new ItemStack(ModItems.ingot_plutonium, 4), 2.0F);

		GameRegistry.addSmelting(ModItems.circuit_schrabidium, new ItemStack(ModItems.circuit_gold, 1), 1.0F);
		GameRegistry.addSmelting(ModItems.circuit_gold, new ItemStack(ModItems.circuit_red_copper, 1), 1.0F);
		GameRegistry.addSmelting(ModItems.circuit_red_copper, new ItemStack(ModItems.circuit_copper, 1), 1.0F);
		GameRegistry.addSmelting(ModItems.circuit_copper, new ItemStack(ModItems.circuit_aluminium, 1), 1.0F);

		GameRegistry.addSmelting(ModItems.ingot_chainsteel, ItemHot.heatUp(new ItemStack(ModItems.ingot_chainsteel)), 1.0F);
		GameRegistry.addSmelting(ModItems.ingot_meteorite, ItemHot.heatUp(new ItemStack(ModItems.ingot_meteorite)), 1.0F);
		GameRegistry.addSmelting(ModItems.ingot_meteorite_forged, ItemHot.heatUp(new ItemStack(ModItems.ingot_meteorite_forged)), 1.0F);
		GameRegistry.addSmelting(ModItems.blade_meteorite, ItemHot.heatUp(new ItemStack(ModItems.blade_meteorite)), 1.0F);
		GameRegistry.addSmelting(ModItems.meteorite_sword, ItemHot.heatUp(new ItemStack(ModItems.meteorite_sword_seared)), 1.0F);
		
		for(int i = 0; i < 10; i++)
			GameRegistry.addSmelting(new ItemStack(ModItems.ingot_steel_dusted, 1, i), ItemHot.heatUp(new ItemStack(ModItems.ingot_steel_dusted, 1, i)), 1.0F);
	}
}

package com.hbm.crafting;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemBattery;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

/**
 * For mining and utility tools
 * @author hbm
 */
public class ToolRecipes {
	
	public static void register() {
		
		//Regular tools
		addSword(	"ingotSteel", ModItems.steel_sword);
		addPickaxe(	"ingotSteel", ModItems.steel_pickaxe);
		addAxe(		"ingotSteel", ModItems.steel_axe);
		addShovel(	"ingotSteel", ModItems.steel_shovel);
		addHoe(		"ingotSteel", ModItems.steel_hoe);
		addSword(	"ingotTitanium", ModItems.titanium_sword);
		addPickaxe(	"ingotTitanium", ModItems.titanium_pickaxe);
		addAxe(		"ingotTitanium", ModItems.titanium_axe);
		addShovel(	"ingotTitanium", ModItems.titanium_shovel);
		addHoe(		"ingotTitanium", ModItems.titanium_hoe);
		addSword(	"ingotCobalt", ModItems.cobalt_sword);
		addPickaxe(	"ingotCobalt", ModItems.cobalt_pickaxe);
		addAxe(		"ingotCobalt", ModItems.cobalt_axe);
		addShovel(	"ingotCobalt", ModItems.cobalt_shovel);
		addHoe(		"ingotCobalt", ModItems.cobalt_hoe);
		addSword(	ModItems.ingot_advanced_alloy, ModItems.alloy_sword);
		addPickaxe(	ModItems.ingot_advanced_alloy, ModItems.alloy_pickaxe);
		addAxe(		ModItems.ingot_advanced_alloy, ModItems.alloy_axe);
		addShovel(	ModItems.ingot_advanced_alloy, ModItems.alloy_shovel);
		addHoe(		ModItems.ingot_advanced_alloy, ModItems.alloy_hoe);
		addSword(	ModItems.ingot_combine_steel, ModItems.cmb_sword);
		addPickaxe(	ModItems.ingot_combine_steel, ModItems.cmb_pickaxe);
		addAxe(		ModItems.ingot_combine_steel, ModItems.cmb_axe);
		addShovel(	ModItems.ingot_combine_steel, ModItems.cmb_shovel);
		addHoe(		ModItems.ingot_combine_steel, ModItems.cmb_hoe);
		addSword(	"ingotDesh", ModItems.desh_sword);
		addPickaxe(	"ingotDesh", ModItems.desh_pickaxe);
		addAxe(		"ingotDesh", ModItems.desh_axe);
		addShovel(	"ingotDesh", ModItems.desh_shovel);
		addHoe(		"ingotDesh", ModItems.desh_hoe);
		
		GameRegistry.addRecipe(new ItemStack(ModItems.elec_sword, 1), new Object[] { "RPR", "RPR", " B ", 'P', ModItems.ingot_polymer, 'D', ModItems.ingot_dura_steel, 'R', ModItems.bolt_dura_steel, 'M', ModItems.motor, 'B', ModItems.battery_lithium });
		GameRegistry.addRecipe(new ItemStack(ModItems.elec_pickaxe, 1), new Object[] { "RDM", " PB", " P ", 'P', ModItems.ingot_polymer, 'D', ModItems.ingot_dura_steel, 'R', ModItems.bolt_dura_steel, 'M', ModItems.motor, 'B', ModItems.battery_lithium });
		GameRegistry.addRecipe(new ItemStack(ModItems.elec_axe, 1), new Object[] { " DP", "RRM", " PB", 'P', ModItems.ingot_polymer, 'D', ModItems.ingot_dura_steel, 'R', ModItems.bolt_dura_steel, 'M', ModItems.motor, 'B', ModItems.battery_lithium });
		GameRegistry.addRecipe(new ItemStack(ModItems.elec_shovel, 1), new Object[] { "  P", "RRM", "  B", 'P', ModItems.ingot_polymer, 'D', ModItems.ingot_dura_steel, 'R', ModItems.bolt_dura_steel, 'M', ModItems.motor, 'B', ModItems.battery_lithium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.centri_stick, 1), new Object[] { ModItems.centrifuge_element, ModItems.energy_core, Items.stick });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.smashing_hammer, 1), new Object[] { "STS", "SPS", " P ", 'S', "blockSteel", 'T', "blockTungsten", 'P', "ingotPolymer" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.meteorite_sword, 1), new Object[] { "  B", "GB ", "SG ", 'B', ModItems.blade_meteorite, 'G', "plateGold", 'S', Items.stick }));

		GameRegistry.addRecipe(new ItemStack(ModItems.cobalt_decorated_sword, 1), new Object[] { " I ", " I ", "SBS", 'I', ModItems.ingot_cobalt, 'S', ModItems.ingot_meteorite_forged, 'B', ModItems.cobalt_sword });
		GameRegistry.addRecipe(new ItemStack(ModItems.cobalt_decorated_pickaxe, 1), new Object[] { "III", " B ", " S ", 'I', ModItems.ingot_cobalt, 'S', ModItems.ingot_meteorite_forged, 'B', ModItems.cobalt_pickaxe });
		GameRegistry.addRecipe(new ItemStack(ModItems.cobalt_decorated_axe, 1), new Object[] { "II", "IB", " S", 'I', ModItems.ingot_cobalt, 'S', ModItems.ingot_meteorite_forged, 'B', ModItems.cobalt_axe });
		GameRegistry.addRecipe(new ItemStack(ModItems.cobalt_decorated_shovel, 1), new Object[] { "I", "B", "S", 'I', ModItems.ingot_cobalt, 'S', ModItems.ingot_meteorite_forged, 'B', ModItems.cobalt_shovel });
		GameRegistry.addRecipe(new ItemStack(ModItems.cobalt_decorated_hoe, 1), new Object[] { "II", " B", " S", 'I', ModItems.ingot_cobalt, 'S', ModItems.ingot_meteorite_forged, 'B', ModItems.cobalt_hoe });
		GameRegistry.addRecipe(new ItemStack(ModItems.starmetal_sword, 1), new Object[] { " I ", " I ", "SBS", 'I', ModItems.ingot_starmetal, 'S', ModItems.ingot_cobalt, 'B', ModItems.cobalt_decorated_sword });
		GameRegistry.addRecipe(new ItemStack(ModItems.starmetal_pickaxe, 1), new Object[] { "III", " B ", " S ", 'I', ModItems.ingot_starmetal, 'S', ModItems.ingot_cobalt, 'B', ModItems.cobalt_decorated_pickaxe });
		GameRegistry.addRecipe(new ItemStack(ModItems.starmetal_axe, 1), new Object[] { "II", "IB", " S", 'I', ModItems.ingot_starmetal, 'S', ModItems.ingot_cobalt, 'B', ModItems.cobalt_decorated_axe });
		GameRegistry.addRecipe(new ItemStack(ModItems.starmetal_shovel, 1), new Object[] { "I", "B", "S", 'I', ModItems.ingot_starmetal, 'S', ModItems.ingot_cobalt, 'B', ModItems.cobalt_decorated_shovel });
		GameRegistry.addRecipe(new ItemStack(ModItems.starmetal_hoe, 1), new Object[] { "II", " B", " S", 'I', ModItems.ingot_starmetal, 'S', ModItems.ingot_cobalt, 'B', ModItems.cobalt_decorated_hoe });
		
		//Drax
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.drax, 1), new Object[] { "BDS", "CDC", "FMF", 'B', ModItems.starmetal_pickaxe, 'S', ModItems.starmetal_shovel, 'C', "ingotCobalt", 'F', ModItems.fusion_core, 'D', "ingotDesh", 'M', ModItems.motor_desh }));
		GameRegistry.addRecipe(new ItemStack(ModItems.drax_mk2, 1), new Object[] { "SCS", "IDI", "FEF", 'S', ModItems.ingot_starmetal, 'C', ModItems.crystal_trixite, 'I', ModItems.ingot_saturnite, 'D', ModItems.drax, 'F', ModItems.fusion_core, 'E', ModItems.circuit_targeting_tier5 });
		GameRegistry.addRecipe(new ItemStack(ModItems.drax_mk3, 1), new Object[] { "ECE", "CDC", "SBS", 'E', ModBlocks.block_euphemium_cluster, 'C', ModItems.crystal_schrabidium, 'D', ModItems.drax_mk2, 'S', ModItems.circuit_targeting_tier6, 'B', ItemBattery.getFullBattery(ModItems.battery_spark) });

		//Super pickaxes
		GameRegistry.addRecipe(new ItemStack(ModItems.bismuth_pickaxe, 1), new Object[] { " BM", "BPB", "TB ", 'B', ModItems.ingot_bismuth, 'M', ModItems.ingot_meteorite, 'P', ModItems.starmetal_pickaxe, 'T', ModItems.bolt_tungsten });
		GameRegistry.addRecipe(new ItemStack(ModItems.volcanic_pickaxe, 1), new Object[] { " BM", "BPB", "TB ", 'B', ModItems.gem_volcanic, 'M', ModItems.ingot_meteorite, 'P', ModItems.starmetal_pickaxe, 'T', ModItems.bolt_tungsten });
		GameRegistry.addRecipe(new ItemStack(ModItems.chlorophyte_pickaxe, 1), new Object[] { " SD", "APS", "FA ", 'S', ModItems.blades_steel, 'D', ModItems.powder_chlorophyte, 'A', ModItems.ingot_fiberglass, 'P', ModItems.bismuth_pickaxe, 'F', ModItems.bolt_dura_steel });
		GameRegistry.addRecipe(new ItemStack(ModItems.chlorophyte_pickaxe, 1), new Object[] { " SD", "APS", "FA ", 'S', ModItems.blades_steel, 'D', ModItems.powder_chlorophyte, 'A', ModItems.ingot_fiberglass, 'P', ModItems.volcanic_pickaxe, 'F', ModItems.bolt_dura_steel });
		GameRegistry.addRecipe(new ItemStack(ModItems.mese_pickaxe, 1), new Object[] { " SD", "APS", "FA ", 'S', ModItems.blades_desh, 'D', ModItems.powder_dineutronium, 'A', ModItems.plate_paa, 'P', ModItems.chlorophyte_pickaxe, 'F', ModItems.shimmer_handle });

		//Chainsaws
		GameRegistry.addRecipe(new ItemStack(ModItems.chainsaw, 1), new Object[] { "  H", "BBP", "  C", 'H', ModItems.hull_small_steel, 'B', ModItems.blades_steel, 'P', ModItems.piston_selenium, 'C', ModItems.canister_fuel });
		GameRegistry.addRecipe(new ItemStack(ModItems.chainsaw, 1), new Object[] { "  H", "BBP", "  C", 'H', ModItems.hull_small_steel, 'B', ModItems.blades_steel, 'P', ModItems.piston_selenium, 'C', ModItems.canister_petroil });
		GameRegistry.addRecipe(new ItemStack(ModItems.chainsaw, 1), new Object[] { "  H", "BBP", "  C", 'H', ModItems.hull_small_steel, 'B', ModItems.blades_steel, 'P', ModItems.piston_selenium, 'C', ModItems.canister_biofuel });

		//Misc
		GameRegistry.addRecipe(new ItemStack(ModItems.euphemium_stopper, 1), new Object[] { "I", "S", "S", 'I', ModItems.ingot_euphemium, 'S', Items.stick });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.crowbar, 1), new Object[] { "II", " I", " I", 'I', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.bottle_opener, 1), new Object[] { "S", "P", 'S', "plateSteel", 'P', "plankWood" }));

		//Matches
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.matchstick, 16), new Object[] { "I", "S", 'I', "sulfur", 'S', Items.stick }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.matchstick, 16), new Object[] { "I", "S", 'I', "dustSulfur", 'S', Items.stick }));
		GameRegistry.addRecipe(new ItemStack(ModItems.matchstick, 24), new Object[] { "I", "S", 'I', ModItems.powder_fire, 'S', Items.stick });

		//Gavels
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.wood_gavel, 1), new Object[] { "SWS", " R ", " R ", 'S', "slabWood", 'W', "logWood", 'R', "stickWood" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.lead_gavel, 1), new Object[] { "PIP", "IGI", "PIP", 'P', ModItems.pellet_buckshot, 'I', "ingotLead", 'G', ModItems.wood_gavel }));

		//Misc weapons
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.saw, 1), new Object[] { "IIL", "PP ", 'P', "plateSteel", 'I', "ingotSteel", 'L', Items.leather }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.bat, 1), new Object[] { "P", "P", "S", 'S', "plateSteel", 'P', "plankWood" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.bat_nail, 1), new Object[] { ModItems.bat, "plateSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.golf_club, 1), new Object[] { "IP", " P", " P", 'P', "plateSteel", 'I', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.pipe_rusty, 1), new Object[] { "II", " I", " I", 'I', "ingotIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.pipe_lead, 1), new Object[] { "II", " I", " I", 'I', "ingotLead" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ullapool_caber, 1), new Object[] { "ITI", " S ", " S ", 'I', "plateIron", 'T', Blocks.tnt, 'S', Items.stick }));

		//Utility
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.designator, 1), new Object[] { "  A", "#B#", "#B#", '#', "plateIron", 'A', "plateSteel", 'B', ModItems.circuit_red_copper }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.designator_range, 1), new Object[] { "RRD", "PIC", "  P", 'P', "plateSteel", 'R', Items.redstone, 'C', ModItems.circuit_gold, 'D', ModItems.designator, 'I', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.designator_manual, 1), new Object[] { "  A", "#C#", "#B#", '#', "ingotPolymer", 'A', "plateLead", 'B', ModItems.circuit_gold, 'C', ModItems.designator }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.linker, 1), new Object[] { "I I", "ICI", "GGG", 'I', "plateIron", 'G', "plateGold", 'C', ModItems.circuit_gold }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.oil_detector, 1), new Object[] { "W I", "WCI", "PPP", 'W', ModItems.wire_gold, 'I', "ingotCopper", 'C', ModItems.circuit_red_copper, 'P', "plateSteel" }));
		GameRegistry.addRecipe(new ItemStack(ModItems.turret_chip, 1), new Object[] { "WWW", "CPC", "WWW", 'W', ModItems.wire_gold, 'P', ModItems.ingot_polymer, 'C', ModItems.circuit_gold, });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.turret_biometry, 1), new Object[] { "CC ", "GGS", "SSS", 'C', ModItems.circuit_copper, 'S', "plateSteel", 'G', "plateGold", 'I', "plateLead" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.survey_scanner, 1), new Object[] { "SWS", " G ", "PCP", 'W', ModItems.wire_gold, 'P', ModItems.ingot_polymer, 'C', ModItems.circuit_gold, 'S', "plateSteel", 'G', "ingotGold" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.geiger_counter, 1), new Object[] { "GPP", "WCS", "WBB", 'W', ModItems.wire_gold, 'P', ModItems.plate_polymer, 'C', ModItems.circuit_aluminium, 'G', "ingotGold", 'S', "plateSteel", 'B', ModItems.ingot_beryllium }));
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.geiger), new Object[] { ModItems.geiger_counter });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.digamma_diagnostic), new Object[] { ModItems.geiger_counter, ModItems.billet_polonium, ModItems.ingot_asbestos });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.defuser, 1), new Object[] { " PS", "P P", " P ", 'P', "ingotPolymer", 'S', "plateSteel" }));
		GameRegistry.addRecipe(new ItemStack(ModItems.coltan_tool, 1), new Object[] { "ACA", "CXC", "ACA", 'A', ModItems.ingot_advanced_alloy, 'C', ModItems.cinnebar, 'X', Items.compass });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.reacher, 1), new Object[] { "BIB", "P P", "B B", 'B', ModItems.bolt_tungsten, 'I', "ingotTungsten", 'P', ModItems.plate_polymer }));
		GameRegistry.addRecipe(new ItemStack(ModItems.bismuth_tool, 1), new Object[] { "TBT", "SRS", "SCS", 'T', ModItems.nugget_tantalium, 'B', ModItems.nugget_bismuth, 'S', ModItems.ingot_tcalloy, 'R', ModItems.reacher, 'C', ModItems.circuit_aluminium });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.sat_designator, 1), new Object[] { "RRD", "PIC", "  P", 'P', "plateGold", 'R', Items.redstone, 'C', ModItems.circuit_gold, 'D', ModItems.sat_chip, 'I', "ingotGold" }));
		GameRegistry.addRecipe(new ItemStack(ModItems.radiation_film, 1), new Object[] { "FFF", "RRR", "PPP", 'F', ModItems.fluorite, 'R', Items.redstone, 'P', Items.paper});

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.mirror_tool), new Object[] { " A ", " IA", "I  ", 'A', "ingotAluminum", 'I', "ingotIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.rbmk_tool), new Object[] { " A ", " IA", "I  ", 'A', "ingotLead", 'I', "ingotIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.hand_drill), new Object[] { " D", "S ", " S", 'D', "ingotDuraSteel", 'S', Items.stick }));
		
		//Instruments
		GameRegistry.addRecipe(new ItemStack(ModItems.gabriel_horn, 1), new Object[] { "PIB", "  S", "PIB", 'P', ModItems.plate_gold, 'I', Items.gold_ingot, 'B', Item.getItemFromBlock(Blocks.gold_block), 'S', ModItems.singularity });
		
		//Bobmazon
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.bobmazon_materials), new Object[] { Items.book, Items.gold_nugget, Items.string });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.bobmazon_machines), new Object[] { Items.book, Items.gold_nugget, new ItemStack(Items.dye, 1, 1) });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.bobmazon_weapons), new Object[] { Items.book, Items.gold_nugget, new ItemStack(Items.dye, 1, 8) });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.bobmazon_tools), new Object[] { Items.book, Items.gold_nugget, new ItemStack(Items.dye, 1, 2) });
		
		//Configged
		if(GeneralConfig.enableBabyMode) {
			addSword(	"ingotSchrabidium", ModItems.schrabidium_sword);
			addPickaxe(	"ingotSchrabidium", ModItems.schrabidium_pickaxe);
			addAxe(		"ingotSchrabidium", ModItems.schrabidium_axe);
			addShovel(	"ingotSchrabidium", ModItems.schrabidium_shovel);
			addHoe(		"ingotSchrabidium", ModItems.schrabidium_hoe);
		} else {
			GameRegistry.addRecipe(new ItemStack(ModItems.schrabidium_sword, 1), new Object[] { "I", "W", "S", 'I', ModBlocks.block_schrabidium, 'W', ModItems.desh_sword, 'S', ModItems.ingot_polymer });
			GameRegistry.addRecipe(new ItemStack(ModItems.schrabidium_pickaxe, 1), new Object[] { "SWS", " P ", " P ", 'S', ModItems.blades_schrabidium, 'W', ModItems.desh_pickaxe, 'P', ModItems.ingot_polymer });
			GameRegistry.addRecipe(new ItemStack(ModItems.schrabidium_axe, 1), new Object[] { "SW", "SP", " P", 'S', ModItems.blades_schrabidium, 'W', ModItems.desh_axe, 'P', ModItems.ingot_polymer });
			GameRegistry.addRecipe(new ItemStack(ModItems.schrabidium_shovel, 1), new Object[] { "S", "W", "P", 'S', ModItems.blades_schrabidium, 'W', ModItems.desh_shovel, 'P', ModItems.ingot_polymer });
			GameRegistry.addRecipe(new ItemStack(ModItems.schrabidium_hoe, 1), new Object[] { "IW", " S", " S", 'I', ModItems.ingot_schrabidium, 'W', ModItems.desh_hoe, 'S', ModItems.ingot_polymer });
		}
	}

	//Common wrappers
	public static void addSword(Object ingot, Item sword) {
		addTool(ingot, sword, patternSword);
	}
	public static void addPickaxe(Object ingot, Item pick) {
		addTool(ingot, pick, patternPick);
	}
	public static void addAxe(Object ingot, Item axe) {
		addTool(ingot, axe, patternAxe);
	}
	public static void addShovel(Object ingot, Item shovel) {
		addTool(ingot, shovel, patternShovel);
	}
	public static void addHoe(Object ingot, Item hoe) {
		addTool(ingot, hoe, patternHoe);
	}
	
	//Generic, pattern-driven
	public static void addTool(Object ingot, Item tool, String[] pattern) {
		if(ingot instanceof Item)		addToolRec(ingot, tool, pattern);
		if(ingot instanceof ItemStack)	addToolRec(ingot, tool, pattern);
		if(ingot instanceof String)		addToolDict(ingot, tool, pattern);
	}
	public static void addToolRec(Object ingot, Item tool, String[] pattern) {
		GameRegistry.addRecipe(new ItemStack(tool), new Object[] { pattern, 'X', ingot, '#', Items.stick });
	}
	public static void addToolDict(Object ingot, Item tool, String[] pattern) {
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(tool), new Object[] { pattern, 'X', ingot, '#', Items.stick }));
	}
	
	public static final String[] patternSword = new String[] {"X", "X", "#"};
	public static final String[] patternPick = new String[] {"XXX", " # ", " # "};
	public static final String[] patternAxe = new String[] {"XX", "X#", " #"};
	public static final String[] patternShovel = new String[] {"X", "#", "#"};
	public static final String[] patternHoe = new String[] {"XX", " #", " #"};
}

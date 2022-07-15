package com.hbm.crafting;

import static com.hbm.inventory.OreDictManager.*;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.inventory.fluid.Fluids;

import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemBattery;
import com.hbm.items.tool.ItemModMinecart;
import com.hbm.items.tool.ItemModMinecart.EnumCartBase;
import com.hbm.items.tool.ItemModMinecart.EnumMinecart;
import com.hbm.main.CraftingManager;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * For mining and utility tools
 * @author hbm
 */
public class ToolRecipes {
	
	public static void register() {
		
		//Regular tools
		addSword(	STEEL.ingot(), ModItems.steel_sword);
		addPickaxe(	STEEL.ingot(), ModItems.steel_pickaxe);
		addAxe(		STEEL.ingot(), ModItems.steel_axe);
		addShovel(	STEEL.ingot(), ModItems.steel_shovel);
		addHoe(		STEEL.ingot(), ModItems.steel_hoe);
		addSword(	TI.ingot(), ModItems.titanium_sword);
		addPickaxe(	TI.ingot(), ModItems.titanium_pickaxe);
		addAxe(		TI.ingot(), ModItems.titanium_axe);
		addShovel(	TI.ingot(), ModItems.titanium_shovel);
		addHoe(		TI.ingot(), ModItems.titanium_hoe);
		addSword(	CO.ingot(), ModItems.cobalt_sword);
		addPickaxe(	CO.ingot(), ModItems.cobalt_pickaxe);
		addAxe(		CO.ingot(), ModItems.cobalt_axe);
		addShovel(	CO.ingot(), ModItems.cobalt_shovel);
		addHoe(		CO.ingot(), ModItems.cobalt_hoe);
		addSword(	ALLOY.ingot(), ModItems.alloy_sword);
		addPickaxe(	ALLOY.ingot(), ModItems.alloy_pickaxe);
		addAxe(		ALLOY.ingot(), ModItems.alloy_axe);
		addShovel(	ALLOY.ingot(), ModItems.alloy_shovel);
		addHoe(		ALLOY.ingot(), ModItems.alloy_hoe);
//		final Item[] cmbSet = {ModItems.cmb_sword, ModItems.cmb_pickaxe, ModItems.cmb_axe, ModItems.cmb_shovel, ModItems.cmb_hoe};
//		final Item[] deshSet = {ModItems.desh_sword, ModItems.desh_pickaxe, ModItems.desh_axe, ModItems.desh_shovel, ModItems.desh_hoe};
//		final Item[] ferrouraniumSet = {ModItems.ferrouranium_sword, ModItems.ferrouranium_pickaxe, ModItems.ferrouranium_axe, ModItems.ferrouranium_shovel, ModItems.ferrouranium_hoe};
//		addFullToolUpgrade(CMB.ingot(), ModItems.bolt_staballoy, cmbSet, deshSet);
//		addFullToolSet(FERROURANIUM.ingot(), ModItems.bolt_dura_steel, ferrouraniumSet);
//		addFullToolUpgrade(DESH.ingot(), ModItems.bolt_staballoy, deshSet, ferrouraniumSet);
//		addSword(	CMB.ingot(), ModItems.cmb_sword);
//		addPickaxe(	CMB.ingot(), ModItems.cmb_pickaxe);
//		addAxe(		CMB.ingot(), ModItems.cmb_axe);
//		addShovel(	CMB.ingot(), ModItems.cmb_shovel);
//		addHoe(		CMB.ingot(), ModItems.cmb_hoe);
//		addSword(	DESH.ingot(), ModItems.desh_sword);
//		addPickaxe(	DESH.ingot(), ModItems.desh_pickaxe);
//		addAxe(		DESH.ingot(), ModItems.desh_axe);
//		addShovel(	DESH.ingot(), ModItems.desh_shovel);
//		addHoe(		DESH.ingot(), ModItems.desh_hoe);
		
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.elec_sword, 1), new Object[] { "RPR", "RPR", " B ", 'P', POLYMER.ingot(), 'D', DURA.ingot(), 'R', ModItems.bolt_dura_steel, 'M', ModItems.motor, 'B', ModItems.battery_lithium });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.elec_pickaxe, 1), new Object[] { "RDM", " PB", " P ", 'P', POLYMER.ingot(), 'D', DURA.ingot(), 'R', ModItems.bolt_dura_steel, 'M', ModItems.motor, 'B', ModItems.battery_lithium });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.elec_axe, 1), new Object[] { " DP", "RRM", " PB", 'P', POLYMER.ingot(), 'D', DURA.ingot(), 'R', ModItems.bolt_dura_steel, 'M', ModItems.motor, 'B', ModItems.battery_lithium });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.elec_shovel, 1), new Object[] { "  P", "RRM", "  B", 'P', POLYMER.ingot(), 'D', DURA.ingot(), 'R', ModItems.bolt_dura_steel, 'M', ModItems.motor, 'B', ModItems.battery_lithium });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.centri_stick, 1), new Object[] { ModItems.centrifuge_element, ModItems.energy_core, KEY_STICK });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.smashing_hammer, 1), new Object[] { "STS", "SPS", " P ", 'S', STEEL.block(), 'T', W.block(), 'P', POLYMER.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.meteorite_sword, 1), new Object[] { "  B", "GB ", "SG ", 'B', ModItems.blade_meteorite, 'G', AU.plate(), 'S', KEY_STICK });

		//Drax
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.drax, 1), new Object[] { "BDS", "CDC", "FMF", 'B', ModItems.starmetal_pickaxe, 'S', ModItems.starmetal_shovel, 'C', CO.ingot(), 'F', ModItems.fusion_core, 'D', DESH.ingot(), 'M', ModItems.motor_desh });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.drax_mk2, 1), new Object[] { "SCS", "IDI", "FEF", 'S', STAR.ingot(), 'C', ModItems.crystal_trixite, 'I', BIGMT.ingot(), 'D', ModItems.drax, 'F', ModItems.fusion_core, 'E', ModItems.circuit_targeting_tier5 });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.drax_mk3, 1), new Object[] { "ECE", "CDC", "SBS", 'E', ModBlocks.block_euphemium_cluster, 'C', ModItems.crystal_schrabidium, 'D', ModItems.drax_mk2, 'S', ModItems.circuit_targeting_tier6, 'B', ItemBattery.getFullBattery(ModItems.battery_spark) });

		//Super pickaxes
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.bismuth_pickaxe, 1), new Object[] { " BM", "BPB", "TB ", 'B', ModItems.ingot_bismuth, 'M', ModItems.ingot_meteorite, 'P', ModItems.starmetal_pickaxe, 'T', ModItems.bolt_tungsten });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.volcanic_pickaxe, 1), new Object[] { " BM", "BPB", "TB ", 'B', ModItems.gem_volcanic, 'M', ModItems.ingot_meteorite, 'P', ModItems.starmetal_pickaxe, 'T', ModItems.bolt_tungsten });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.chlorophyte_pickaxe, 1), new Object[] { " SD", "APS", "FA ", 'S', ModItems.blades_steel, 'D', ModItems.powder_chlorophyte, 'A', FIBER.ingot(), 'P', ModItems.bismuth_pickaxe, 'F', ModItems.bolt_dura_steel });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.chlorophyte_pickaxe, 1), new Object[] { " SD", "APS", "FA ", 'S', ModItems.blades_steel, 'D', ModItems.powder_chlorophyte, 'A', FIBER.ingot(), 'P', ModItems.volcanic_pickaxe, 'F', ModItems.bolt_dura_steel });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.mese_pickaxe, 1), new Object[] { " SD", "APS", "FA ", 'S', ModItems.blades_desh, 'D', ModItems.powder_dineutronium, 'A', ModItems.plate_paa, 'P', ModItems.chlorophyte_pickaxe, 'F', ModItems.shimmer_handle });

		//Chainsaws
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.chainsaw, 1), new Object[] { "  H", "BBP", "  C", 'H', ModItems.hull_small_steel, 'B', ModItems.blades_steel, 'P', ModItems.piston_selenium, 'C', Fluids.DIESEL.getDict(1000) });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.chainsaw, 1), new Object[] { "  H", "BBP", "  C", 'H', ModItems.hull_small_steel, 'B', ModItems.blades_steel, 'P', ModItems.piston_selenium, 'C', Fluids.DIESEL_CRACK.getDict(1000) });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.chainsaw, 1), new Object[] { "  H", "BBP", "  C", 'H', ModItems.hull_small_steel, 'B', ModItems.blades_steel, 'P', ModItems.piston_selenium, 'C', Fluids.PETROIL.getDict(1000) });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.chainsaw, 1), new Object[] { "  H", "BBP", "  C", 'H', ModItems.hull_small_steel, 'B', ModItems.blades_steel, 'P', ModItems.piston_selenium, 'C', Fluids.PETROIL_LEADED.getDict(1000) });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.chainsaw, 1), new Object[] { "  H", "BBP", "  C", 'H', ModItems.hull_small_steel, 'B', ModItems.blades_steel, 'P', ModItems.piston_selenium, 'C', Fluids.GASOLINE.getDict(1000) });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.chainsaw, 1), new Object[] { "  H", "BBP", "  C", 'H', ModItems.hull_small_steel, 'B', ModItems.blades_steel, 'P', ModItems.piston_selenium, 'C', Fluids.GASOLINE_LEADED.getDict(1000) });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.chainsaw, 1), new Object[] { "  H", "BBP", "  C", 'H', ModItems.hull_small_steel, 'B', ModItems.blades_steel, 'P', ModItems.piston_selenium, 'C', Fluids.BIOFUEL.getDict(1000) });

		//Misc
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.euphemium_stopper, 1), new Object[] { "I", "S", "S", 'I', EUPH.ingot(), 'S', KEY_STICK });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.crowbar, 1), new Object[] { "II", " I", " I", 'I', STEEL.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.bottle_opener, 1), new Object[] { "S", "P", 'S', STEEL.plate(), 'P', KEY_PLANKS });

		//Matches
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.matchstick, 16), new Object[] { "I", "S", 'I', S.dust(), 'S', KEY_STICK });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.matchstick, 24), new Object[] { "I", "S", 'I', P_RED.dust(), 'S', KEY_STICK });

		//Gavels
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.wood_gavel, 1), new Object[] { "SWS", " R ", " R ", 'S', KEY_SLAB, 'W', KEY_LOG, 'R', KEY_STICK });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.lead_gavel, 1), new Object[] { "PIP", "IGI", "PIP", 'P', ModItems.pellet_buckshot, 'I', PB.ingot(), 'G', ModItems.wood_gavel });

		//Misc weapons
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.saw, 1), new Object[] { "IIL", "PP ", 'P', STEEL.plate(), 'I', STEEL.ingot(), 'L', Items.leather });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.bat, 1), new Object[] { "P", "P", "S", 'S', STEEL.plate(), 'P', KEY_PLANKS });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.bat_nail, 1), new Object[] { ModItems.bat, STEEL.plate() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.golf_club, 1), new Object[] { "IP", " P", " P", 'P', STEEL.plate(), 'I', STEEL.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.pipe_rusty, 1), new Object[] { "II", " I", " I", 'I', FE.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.pipe_lead, 1), new Object[] { "II", " I", " I", 'I', PB.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ullapool_caber, 1), new Object[] { "ITI", " S ", " S ", 'I', FE.plate(), 'T', ModBlocks.tnt, 'S', KEY_STICK });

		//Utility
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.designator, 1), new Object[] { "  A", "#B#", "#B#", '#', FE.plate(), 'A', STEEL.plate(), 'B', ModItems.circuit_red_copper });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.designator_range, 1), new Object[] { "RRD", "PIC", "  P", 'P', STEEL.plate(), 'R', Items.redstone, 'C', ModItems.circuit_gold, 'D', ModItems.designator, 'I', STEEL.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.designator_manual, 1), new Object[] { "  A", "#C#", "#B#", '#', POLYMER.ingot(), 'A', PB.plate(), 'B', ModItems.circuit_gold, 'C', ModItems.designator });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.linker, 1), new Object[] { "I I", "ICI", "GGG", 'I', FE.plate(), 'G', AU.plate(), 'C', ModItems.circuit_gold });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.oil_detector, 1), new Object[] { "W I", "WCI", "PPP", 'W', ModItems.wire_gold, 'I', CU.ingot(), 'C', ModItems.circuit_red_copper, 'P', STEEL.plate() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.turret_chip, 1), new Object[] { "WWW", "CPC", "WWW", 'W', ModItems.wire_gold, 'P', POLYMER.ingot(), 'C', ModItems.circuit_gold, });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.survey_scanner, 1), new Object[] { "SWS", " G ", "PCP", 'W', ModItems.wire_gold, 'P', POLYMER.ingot(), 'C', ModItems.circuit_gold, 'S', STEEL.plate(), 'G', AU.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.geiger_counter, 1), new Object[] { "GPP", "WCS", "WBB", 'W', ModItems.wire_gold, 'P', ModItems.plate_polymer, 'C', ModItems.circuit_red_copper, 'G', AU.ingot(), 'S', STEEL.plate(), 'B', ModItems.ingot_beryllium });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.dosimeter, 1), new Object[] { "WGW", "WCW", "WBW", 'W', KEY_PLANKS, 'G', KEY_ANYPANE, 'C', ModItems.circuit_aluminium, 'B', BE.ingot() });
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.geiger), new Object[] { ModItems.geiger_counter });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.digamma_diagnostic), new Object[] { ModItems.geiger_counter, PO210.billet(), ASBESTOS.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.defuser, 1), new Object[] { " PS", "P P", " P ", 'P', POLYMER.ingot(), 'S', STEEL.plate() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.coltan_tool, 1), new Object[] { "ACA", "CXC", "ACA", 'A', ALLOY.ingot(), 'C', CINNABAR.crystal(), 'X', Items.compass });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.reacher, 1), new Object[] { "BIB", "P P", "B B", 'B', ModItems.bolt_tungsten, 'I', W.ingot(), 'P', ModItems.plate_polymer });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.bismuth_tool, 1), new Object[] { "TBT", "SRS", "SCS", 'T', TA.nugget(), 'B', ModItems.nugget_bismuth, 'S', TCALLOY.ingot(), 'R', ModItems.reacher, 'C', ModItems.circuit_aluminium });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.sat_designator, 1), new Object[] { "RRD", "PIC", "  P", 'P', AU.plate(), 'R', Items.redstone, 'C', ModItems.circuit_gold, 'D', ModItems.sat_chip, 'I', AU.ingot() });

		CraftingManager.addRecipeAuto(new ItemStack(ModItems.mirror_tool), new Object[] { " A ", " IA", "I  ", 'A', AL.ingot(), 'I', FE.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.rbmk_tool), new Object[] { " A ", " IA", "I  ", 'A', PB.ingot(), 'I', FE.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.power_net_tool), new Object[] { "WRW", " I ", " B ", 'W', ModItems.wire_red_copper, 'R', REDSTONE.dust(), 'I', FE.ingot(), 'B', ModItems.battery_su });

		CraftingManager.addRecipeAuto(new ItemStack(ModItems.kit_toolbox_empty), new Object[] { "CCC", "CIC", 'C', CU.plate(), 'I', FE.ingot() });

		CraftingManager.addRecipeAuto(new ItemStack(ModItems.screwdriver, 1), new Object[] { "  I", " I ", "S  ", 'S', STEEL.ingot(), 'I', FE.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.screwdriver_desh, 1), new Object[] { "  I", " I ", "S  ", 'S', ANY_PLASTIC.ingot(), 'I', DESH.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.hand_drill), new Object[] { " D", "S ", " S", 'D', DURA.ingot(), 'S', KEY_STICK });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.hand_drill_desh), new Object[] { " D", "S ", " S", 'D', DESH.ingot(), 'S', ANY_PLASTIC.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.chemistry_set), new Object[] { "GIG", "GCG", 'G', KEY_ANYGLASS, 'I', FE.ingot(), 'C', CU.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.chemistry_set_boron), new Object[] { "GIG", "GCG", 'G', ModBlocks.glass_boron, 'I', STEEL.ingot(), 'C', CO.ingot() });
		
		//Bobmazon
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.bobmazon_materials), new Object[] { Items.book, Items.gold_nugget, Items.string });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.bobmazon_machines), new Object[] { Items.book, Items.gold_nugget, KEY_RED });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.bobmazon_weapons), new Object[] { Items.book, Items.gold_nugget, KEY_GRAY });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.bobmazon_tools), new Object[] { Items.book, Items.gold_nugget, KEY_GREEN });
		
//		final Item[] deshBulletStamps = {ModItems.stamp_357_desh, ModItems.stamp_44_desh, ModItems.stamp_50_desh, ModItems.stamp_9_desh};
//		final Item[] casings = {ModItems.casing_357, ModItems.casing_44, ModItems.casing_50, ModItems.casing_9};
//		for (byte i = 0; i < 4; i++)
//			CraftingManager.addRecipeAuto(new ItemStack(deshBulletStamps[i]), "GSG", "III", " C ", 'G', DIAMOND.block(), 'S', ModItems.stamp_desh_flat, 'I', W.ingot(), 'C', casings[i]);
		
		//Carts
		CraftingManager.addRecipeAuto(ItemModMinecart.createCartItem(EnumCartBase.WOOD, EnumMinecart.EMPTY), new Object[] { "P P", "WPW", 'P',KEY_SLAB, 'W', KEY_PLANKS });
		CraftingManager.addRecipeAuto(ItemModMinecart.createCartItem(EnumCartBase.STEEL, EnumMinecart.EMPTY), new Object[] { "P P", "IPI", 'P', STEEL.plate(), 'I', STEEL.ingot() });
		CraftingManager.addShapelessAuto(ItemModMinecart.createCartItem(EnumCartBase.PAINTED, EnumMinecart.EMPTY), new Object[] { ItemModMinecart.createCartItem(EnumCartBase.STEEL, EnumMinecart.EMPTY), KEY_RED });
		
		for(EnumCartBase base : EnumCartBase.values()) {
			
			if(EnumMinecart.DESTROYER.supportsBase(base))	CraftingManager.addRecipeAuto(ItemModMinecart.createCartItem(base, EnumMinecart.DESTROYER), new Object[] { "S S", "BLB", "SCS", 'S', STEEL.ingot(), 'B', ModItems.blades_steel, 'L', Fluids.LAVA.getDict(1000), 'C', ItemModMinecart.createCartItem(base, EnumMinecart.EMPTY) });
			if(EnumMinecart.POWDER.supportsBase(base))		CraftingManager.addRecipeAuto(ItemModMinecart.createCartItem(base, EnumMinecart.POWDER), new Object[] { "PPP", "PCP", "PPP", 'P', Items.gunpowder, 'C', ItemModMinecart.createCartItem(base, EnumMinecart.EMPTY) });
			if(EnumMinecart.SEMTEX.supportsBase(base))		CraftingManager.addRecipeAuto(ItemModMinecart.createCartItem(base, EnumMinecart.SEMTEX), new Object[] { "S", "C", 'S', ModBlocks.semtex, 'C', ItemModMinecart.createCartItem(base, EnumMinecart.EMPTY) });
		}
		net.minecraft.item.crafting.CraftingManager.getInstance().addRecipe(DictFrame.fromOne(ModItems.cart, EnumMinecart.CRATE), new Object[] { "C", "S", 'C', ModBlocks.crate_steel, 'S', Items.minecart }).func_92100_c();
		
		//Configged
		if(GeneralConfig.enableLBSM && GeneralConfig.enableLBSMSimpleToolRecipes) {
			addSword(	CO.block(), ModItems.cobalt_decorated_sword);
			addPickaxe(	CO.block(), ModItems.cobalt_decorated_pickaxe);
			addAxe(		CO.block(), ModItems.cobalt_decorated_axe);
			addShovel(	CO.block(), ModItems.cobalt_decorated_shovel);
			addHoe(		CO.block(), ModItems.cobalt_decorated_hoe);
			addSword(	STAR.ingot(), ModItems.starmetal_sword);
			addPickaxe(	STAR.ingot(), ModItems.starmetal_pickaxe);
			addAxe(		STAR.ingot(), ModItems.starmetal_axe);
			addShovel(	STAR.ingot(), ModItems.starmetal_shovel);
			addHoe(		STAR.ingot(), ModItems.starmetal_hoe);
			addSword(	SA326.ingot(), ModItems.schrabidium_sword);
			addPickaxe(	SA326.ingot(), ModItems.schrabidium_pickaxe);
			addAxe(		SA326.ingot(), ModItems.schrabidium_axe);
			addShovel(	SA326.ingot(), ModItems.schrabidium_shovel);
			addHoe(		SA326.ingot(), ModItems.schrabidium_hoe);
		} else {
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.schrabidium_sword, 1), new Object[] { "I", "W", "S", 'I', SA326.block(), 'W', ModItems.cmb_sword, 'S', POLYMER.ingot() });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.schrabidium_pickaxe, 1), new Object[] { "SWS", " P ", " P ", 'S', ModItems.blades_schrabidium, 'W', ModItems.cmb_pickaxe, 'P', POLYMER.ingot() });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.schrabidium_axe, 1), new Object[] { "SW", "SP", " P", 'S', ModItems.blades_schrabidium, 'W', ModItems.cmb_axe, 'P', POLYMER.ingot() });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.schrabidium_shovel, 1), new Object[] { "S", "W", "P", 'S', ModItems.blades_schrabidium, 'W', ModItems.cmb_shovel, 'P', POLYMER.ingot() });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.schrabidium_hoe, 1), new Object[] { "IW", " S", " S", 'I', SA326.ingot(), 'W', ModItems.cmb_hoe, 'S', POLYMER.ingot() });
		}
	}

	public static void newStamp(Item stamp, Object material, boolean diamond)
	{
		if (diamond)
			CraftingManager.addRecipeAuto(new ItemStack(stamp), patternStamp, 'R', Items.redstone, 'B', Items.diamond, 'M', material);
		else
		{
			CraftingManager.addRecipeAuto(new ItemStack(stamp), patternStamp, 'R', Items.redstone, 'B', KEY_BRICK, 'M', material);
			CraftingManager.addRecipeAuto(new ItemStack(stamp), patternStamp, 'R', Items.redstone, 'B', KEY_NETHERBRICK, 'M', material);
		}
	}

	
	public static void addFullToolSet(Object headMaterial, Object handleMaterial, Item...tool)
	{
		assert tool.length == 5;
		addTool(headMaterial, tool[0], handleMaterial, patternSword);
		addTool(headMaterial, tool[1], handleMaterial, patternPick);
		addTool(headMaterial, tool[2], handleMaterial, patternAxe);
		addTool(headMaterial, tool[3], handleMaterial, patternShovel);
		addTool(headMaterial, tool[4], handleMaterial, patternHoe);
	}
	
	public static void addFullToolUpgrade(Object headMaterial, Object handleMaterial, Item[] newTool, Item[] oldTool)
	{
		assert newTool.length == 5;
		assert oldTool.length == 5;
		addToolUpgrade(headMaterial, handleMaterial, newTool[0], oldTool[0], patternSwordUpgrade);
		addToolUpgrade(headMaterial, handleMaterial, newTool[1], oldTool[1], patternPickUpgrade);
		addToolUpgrade(headMaterial, handleMaterial, newTool[2], oldTool[2], patternAxeUpgrade);
		addToolUpgrade(headMaterial, handleMaterial, newTool[3], oldTool[3], patternShovelUpgrade);
		addToolUpgrade(headMaterial, handleMaterial, newTool[4], oldTool[4], patternHoeUpgrade);
	}
	
	public static void addFullToolSet(Object headMaterial, Item...tool)
	{
		addFullToolSet(headMaterial, KEY_STICK, tool);
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
	public static void addTool(Object ingot, Item tool, String[] pattern) {
		CraftingManager.addRecipeAuto(new ItemStack(tool), new Object[] { pattern, 'X', ingot, '#', KEY_STICK });
	}
	public static void addTool(Object ingot, Item tool, Object handle, String[] pattern)
	{
		CraftingManager.addRecipeAuto(new ItemStack(tool), new Object[] { pattern, 'X', ingot, '#', handle });
	}
	public static void addToolUpgrade(Object headMaterial, Object handleMaterial, Item newTool, Item oldTool, String[] pattern)
	{
		CraftingManager.addRecipeAuto(new ItemStack(newTool), pattern, 'X', headMaterial, '#', handleMaterial, 'P', oldTool);
	}
	public static final String[] patternPick = new String[] {"XXX", " # ", " # "};
	public static final String[] patternSword = new String[] {"X", "X", "#"};
	public static final String[] patternAxe = new String[] {"XX", "X#", " #"};
	public static final String[] patternShovel = new String[] {"X", "#", "#"};
	public static final String[] patternHoe = new String[] {"XX", " #", " #"};
	
	public static final String[] patternPickUpgrade = {"XXX", "#P#", " # "};
	public static final String[] patternSwordUpgrade = {" X ", " X ", "#P#"};
	public static final String[] patternAxeUpgrade = {"XX ", "XP#", " # "};
	public static final String[] patternShovelUpgrade = {" X ", "#P#", " # "};
	public static final String[] patternHoeUpgrade = {"XX", "#P", " #"};
	
	public static final String[] patternStamp = {" R ", "BBB", "MMM"};
	
}
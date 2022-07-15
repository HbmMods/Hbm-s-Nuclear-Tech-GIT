package com.hbm.crafting;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.fluid.Fluids;

import static com.hbm.inventory.OreDictManager.*;
import com.hbm.items.ModItems;
import com.hbm.items.ItemEnums.EnumLegendaryType;
import com.hbm.main.CraftingManager;

import api.hbm.energy.IBatteryItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * For player armor
 * @author hbm
 */
public class ArmorRecipes {
	
	public static void register() {
		
		//Armor mod table
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.machine_armor_table, 1), new Object[] { "PPP", "TCT", "TST", 'P', STEEL.plate(), 'T', W.ingot(), 'C', Blocks.crafting_table, 'S', STEEL.block() });
		
		//Regular armor
		addHelmet(	STEEL.ingot(), ModItems.steel_helmet);
		addChest(	STEEL.ingot(), ModItems.steel_plate);
		addLegs(	STEEL.ingot(), ModItems.steel_legs);
		addBoots(	STEEL.ingot(), ModItems.steel_boots);
		addHelmet(	TI.ingot(), ModItems.titanium_helmet);
		addChest(	TI.ingot(), ModItems.titanium_plate);
		addLegs(	TI.ingot(), ModItems.titanium_legs);
		addBoots(	TI.ingot(), ModItems.titanium_boots);
		addHelmet(	ALLOY.ingot(), ModItems.alloy_helmet);
		addChest(	ALLOY.ingot(), ModItems.alloy_plate);
		addLegs(	ALLOY.ingot(), ModItems.alloy_legs);
		addBoots(	ALLOY.ingot(), ModItems.alloy_boots);
		addHelmet(	CMB.ingot(), ModItems.cmb_helmet);
		addChest(	CMB.ingot(), ModItems.cmb_plate);
		addLegs(	CMB.ingot(), ModItems.cmb_legs);
		addBoots(	CMB.ingot(), ModItems.cmb_boots);
		addHelmet(	CO.ingot(), ModItems.cobalt_helmet);
		addChest(	CO.ingot(), ModItems.cobalt_plate);
		addLegs(	CO.ingot(), ModItems.cobalt_legs);
		addBoots(	CO.ingot(), ModItems.cobalt_boots);
		addHelmet(	ModItems.rag, ModItems.robes_helmet);
		addChest(	ModItems.rag, ModItems.robes_plate);
		addLegs(	ModItems.rag, ModItems.robes_legs);
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.robes_boots, 1), new Object[] { "R R", "P P", 'R', ModItems.rag, 'P', ModItems.plate_polymer });
		
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.security_helmet, 1), new Object[] { "SSS", "IGI", 'S', STEEL.plate(), 'I', ModItems.plate_polymer, 'G', KEY_ANYPANE });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.security_plate, 1), new Object[] { "KWK", "IKI", "WKW", 'K', ModItems.plate_kevlar, 'I', POLYMER.ingot(), 'W', new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE) });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.security_legs, 1), new Object[] { "IWI", "K K", "W W", 'K', ModItems.plate_kevlar, 'I', POLYMER.ingot(), 'W', new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE) });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.security_boots, 1), new Object[] { "P P", "I I", 'P', STEEL.plate(), 'I', ModItems.plate_polymer });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.dnt_helmet, 1), new Object[] { "EEE", "EE ", 'E', DNT.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.dnt_plate, 1), new Object[] { "EE ", "EEE", "EEE", 'E', DNT.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.dnt_legs, 1), new Object[] { "EE ", "EEE", "E E", 'E', DNT.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.dnt_boots, 1), new Object[] { "  E", "E  ", "E E", 'E', DNT.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.zirconium_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', ZR.ingot() });

		//Power armor
		CraftingManager.addRecipeAuto(IBatteryItem.emptyBattery(ModItems.t45_helmet), new Object[] { "PPC", "PBP", "IXI", 'P', ModItems.plate_armor_titanium, 'C', ModItems.circuit_targeting_tier3, 'I', ModItems.plate_polymer, 'X', ModItems.gas_mask_m65, 'B', ModItems.titanium_helmet });
		CraftingManager.addRecipeAuto(IBatteryItem.emptyBattery(ModItems.t45_plate), new Object[] { "MPM", "TBT", "PPP", 'M', ModItems.motor, 'P', ModItems.plate_armor_titanium, 'T', ModItems.gas_empty, 'B', ModItems.titanium_plate });
		CraftingManager.addRecipeAuto(IBatteryItem.emptyBattery(ModItems.t45_legs), new Object[] { "MPM", "PBP", "P P", 'M', ModItems.motor, 'P', ModItems.plate_armor_titanium, 'B', ModItems.titanium_legs });
		CraftingManager.addRecipeAuto(IBatteryItem.emptyBattery(ModItems.t45_boots), new Object[] { "P P", "PBP", 'P', ModItems.plate_armor_titanium, 'B', ModItems.titanium_boots });
		CraftingManager.addRecipeAuto(IBatteryItem.emptyBattery(ModItems.ajr_helmet), new Object[] { "PPC", "PBP", "IXI", 'P', ModItems.plate_armor_ajr, 'C', ModItems.circuit_targeting_tier4, 'I', POLYMER.ingot(), 'X', ModItems.gas_mask_m65, 'B', ModItems.alloy_helmet });
		CraftingManager.addRecipeAuto(IBatteryItem.emptyBattery(ModItems.ajr_plate), new Object[] { "MPM", "TBT", "PPP", 'M', ModItems.motor_desh, 'P', ModItems.plate_armor_ajr, 'T', ModItems.gas_empty, 'B', ModItems.alloy_plate });
		CraftingManager.addRecipeAuto(IBatteryItem.emptyBattery(ModItems.ajr_legs), new Object[] { "MPM", "PBP", "P P", 'M', ModItems.motor_desh, 'P', ModItems.plate_armor_ajr, 'B', ModItems.alloy_legs });
		CraftingManager.addRecipeAuto(IBatteryItem.emptyBattery(ModItems.ajr_boots), new Object[] { "P P", "PBP", 'P', ModItems.plate_armor_ajr, 'B', ModItems.alloy_boots });
		CraftingManager.addShapelessAuto(IBatteryItem.emptyBattery(ModItems.ajro_helmet), new Object[] { ModItems.ajr_helmet, KEY_RED, KEY_BLACK });
		CraftingManager.addShapelessAuto(IBatteryItem.emptyBattery(ModItems.ajro_plate), new Object[] { ModItems.ajr_plate, KEY_RED, KEY_BLACK });
		CraftingManager.addShapelessAuto(IBatteryItem.emptyBattery(ModItems.ajro_legs), new Object[] { ModItems.ajr_legs, KEY_RED, KEY_BLACK });
		CraftingManager.addShapelessAuto(IBatteryItem.emptyBattery(ModItems.ajro_boots), new Object[] { ModItems.ajr_boots, KEY_RED, KEY_BLACK });
		CraftingManager.addRecipeAuto(IBatteryItem.emptyBattery(ModItems.bj_helmet), new Object[] { "SBS", " C ", " I ", 'S', Items.string, 'B', new ItemStack(Blocks.wool, 1, 15), 'C', ModItems.circuit_targeting_tier4, 'I', STAR.ingot() });
		CraftingManager.addRecipeAuto(IBatteryItem.emptyBattery(ModItems.bj_plate), new Object[] { "N N", "MSM", "NCN", 'N', ModItems.plate_armor_lunar, 'M', ModItems.motor_desh, 'S', ModItems.starmetal_plate, 'C', ModItems.circuit_targeting_tier5 });
		CraftingManager.addRecipeAuto(IBatteryItem.emptyBattery(ModItems.bj_plate_jetpack), new Object[] { "NFN", "TPT", "ICI", 'N', ModItems.plate_armor_lunar, 'F', ModItems.fins_quad_titanium, 'T', new ItemStack(ModItems.fluid_tank_full, 1, Fluids.XENON.getID()), 'P', ModItems.bj_plate, 'I', ModItems.mp_thruster_10_xenon, 'C', ModItems.crystal_phosphorus });
		CraftingManager.addRecipeAuto(IBatteryItem.emptyBattery(ModItems.bj_legs), new Object[] { "MBM", "NSN", "N N", 'N', ModItems.plate_armor_lunar, 'M', ModItems.motor_desh, 'S', ModItems.starmetal_legs, 'B', ModBlocks.block_starmetal });
		CraftingManager.addRecipeAuto(IBatteryItem.emptyBattery(ModItems.bj_boots), new Object[] { "N N", "BSB", 'N', ModItems.plate_armor_lunar, 'S', ModItems.starmetal_boots, 'B', ModBlocks.block_starmetal });
		CraftingManager.addRecipeAuto(IBatteryItem.emptyBattery(ModItems.hev_helmet), new Object[] { "PPC", "PBP", "IFI", 'P', ModItems.plate_armor_hev, 'C', ModItems.circuit_targeting_tier4, 'B', ModItems.titanium_helmet, 'I', ModItems.plate_polymer, 'F', ModItems.gas_mask_filter });
		CraftingManager.addRecipeAuto(IBatteryItem.emptyBattery(ModItems.hev_plate), new Object[] { "MPM", "IBI", "PPP", 'P', ModItems.plate_armor_hev, 'B', ModItems.titanium_plate, 'I', POLYMER.ingot(), 'M', ModItems.motor_desh });
		CraftingManager.addRecipeAuto(IBatteryItem.emptyBattery(ModItems.hev_legs), new Object[] { "MPM", "IBI", "P P", 'P', ModItems.plate_armor_hev, 'B', ModItems.titanium_legs, 'I', POLYMER.ingot(), 'M', ModItems.motor_desh });
		CraftingManager.addRecipeAuto(IBatteryItem.emptyBattery(ModItems.hev_boots), new Object[] { "P P", "PBP", 'P', ModItems.plate_armor_hev, 'B', ModItems.titanium_boots });
		CraftingManager.addRecipeAuto(IBatteryItem.emptyBattery(ModItems.fau_helmet), new Object[] { "PWP", "PBP", "FSF", 'P', ModItems.plate_armor_fau, 'W', new ItemStack(Blocks.wool, 1, 14), 'B', ModItems.starmetal_helmet, 'F', ModItems.gas_mask_filter, 'S', ModItems.pipes_steel });
		CraftingManager.addRecipeAuto(IBatteryItem.emptyBattery(ModItems.fau_plate), new Object[] { "MCM", "PBP", "PSP", 'M', ModItems.motor_desh, 'C', ModItems.demon_core_closed, 'P', ModItems.plate_armor_fau, 'B', ModItems.starmetal_plate, 'S', ModBlocks.ancient_scrap });
		CraftingManager.addRecipeAuto(IBatteryItem.emptyBattery(ModItems.fau_legs), new Object[] { "MPM", "PBP", "PDP", 'M', ModItems.motor_desh, 'P', ModItems.plate_armor_fau, 'B', ModItems.starmetal_legs, 'D', ModItems.billet_polonium });
		CraftingManager.addRecipeAuto(IBatteryItem.emptyBattery(ModItems.fau_boots), new Object[] { "PDP", "PBP", 'P', ModItems.plate_armor_fau, 'D', ModItems.billet_polonium, 'B', ModItems.starmetal_boots });
		CraftingManager.addRecipeAuto(IBatteryItem.emptyBattery(ModItems.dns_helmet), new Object[] { "PCP", "PBP", "PSP", 'P', ModItems.plate_armor_dnt, 'S', ModItems.ingot_chainsteel, 'B', ModItems.bj_helmet, 'C', ModItems.circuit_targeting_tier6 });
		CraftingManager.addRecipeAuto(IBatteryItem.emptyBattery(ModItems.dns_plate), new Object[] { "PCP", "PBP", "PSP", 'P', ModItems.plate_armor_dnt, 'S', ModItems.ingot_chainsteel, 'B', ModItems.bj_plate_jetpack, 'C', ModItems.singularity_spark });
		CraftingManager.addRecipeAuto(IBatteryItem.emptyBattery(ModItems.dns_legs), new Object[] { "PCP", "PBP", "PSP", 'P', ModItems.plate_armor_dnt, 'S', ModItems.ingot_chainsteel, 'B', ModItems.bj_legs, 'C', ModItems.coin_worm });
		CraftingManager.addRecipeAuto(IBatteryItem.emptyBattery(ModItems.dns_boots), new Object[] { "PCP", "PBP", "PSP", 'P', ModItems.plate_armor_dnt, 'S', ModItems.ingot_chainsteel, 'B', ModItems.bj_boots, 'C', ModItems.demon_core_closed });
		CraftingManager.addRecipeAuto(IBatteryItem.emptyBattery(ModItems.rpa_helmet), new Object[] { "KPK", "PLP", " F ", 'L', DictFrame.fromOne(ModItems.parts_legendary, EnumLegendaryType.TIER2), 'K', ModItems.plate_kevlar, 'P', ModItems.plate_armor_ajr, 'F', ModItems.gas_mask_filter_combo });
		CraftingManager.addRecipeAuto(IBatteryItem.emptyBattery(ModItems.rpa_plate), new Object[] { "P P", "MLM", "PKP", 'L', DictFrame.fromOne(ModItems.parts_legendary, EnumLegendaryType.TIER2), 'K', ModItems.plate_kevlar, 'P', ModItems.plate_armor_ajr, 'M', ModItems.motor_desh });
		CraftingManager.addRecipeAuto(IBatteryItem.emptyBattery(ModItems.rpa_legs), new Object[] { "MPM", "KLK", "P P", 'L', DictFrame.fromOne(ModItems.parts_legendary, EnumLegendaryType.TIER2), 'K', ModItems.plate_kevlar, 'P', ModItems.plate_armor_ajr, 'M', ModItems.motor_desh });
		CraftingManager.addRecipeAuto(IBatteryItem.emptyBattery(ModItems.rpa_boots), new Object[] { "KLK", "P P", 'L', DictFrame.fromOne(ModItems.parts_legendary, EnumLegendaryType.TIER2), 'K', ModItems.plate_kevlar, 'P', ModItems.plate_armor_ajr });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.steamsuit_helmet, 1), new Object[] { "DCD", "CXC", " F ", 'D', DESH.ingot(), 'C', CU.plate(), 'X', ModItems.steel_helmet, 'F', ModItems.gas_mask_filter });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.steamsuit_plate, 1), new Object[] { "C C", "DXD", "CFC", 'D', DESH.ingot(), 'C', CU.plate(), 'X', ModItems.steel_plate, 'F', ModItems.tank_steel });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.steamsuit_legs, 1), new Object[] { "CCC", "DXD", "C C", 'D', DESH.ingot(), 'C', CU.plate(), 'X', ModItems.steel_legs });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.steamsuit_boots, 1), new Object[] { "C C", "DXD", 'D', DESH.ingot(), 'C', CU.plate(), 'X', ModItems.steel_boots });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.dieselsuit_helmet, 1), new Object[] { "W W", "W W", "SCS", 'W', new ItemStack(Blocks.wool, 1, 14), 'S', STEEL.ingot(), 'C', ModItems.circuit_targeting_tier3 });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.dieselsuit_plate, 1), new Object[] { "W W", "CDC", "SWS", 'W', new ItemStack(Blocks.wool, 1, 14), 'S', STEEL.ingot(), 'C', ModItems.circuit_targeting_tier3, 'D', ModBlocks.machine_diesel });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.dieselsuit_legs, 1), new Object[] { "M M", "S S", "W W", 'W', new ItemStack(Blocks.wool, 1, 14), 'S', STEEL.ingot(), 'M', ModItems.motor });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.dieselsuit_boots, 1), new Object[] { "W W", "S S", 'W', new ItemStack(Blocks.wool, 1, 14), 'S', STEEL.ingot() });

		//Bismuth fursui- I mean armor
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.bismuth_helmet, 1), new Object[] { "GPP", "P  ", "FPP", 'G', Items.gold_ingot, 'P', ModItems.plate_bismuth, 'F', ModItems.rag });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.bismuth_plate, 1), new Object[] { "RWR", "PCP", "SFS", 'R', ModItems.crystal_rare, 'W', ModItems.wire_gold, 'P', ModItems.plate_bismuth, 'C', ModItems.laser_crystal_bismuth, 'S', ModItems.ring_starmetal, 'F', ModItems.rag });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.bismuth_legs, 1), new Object[] { "FSF", "   ", "FSF", 'F', ModItems.rag, 'S', ModItems.ring_starmetal });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.bismuth_boots, 1), new Object[] { "W W", "P P", 'W', ModItems.wire_gold, 'P', ModItems.plate_bismuth });
		
		//Euphemium armor
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.euphemium_helmet, 1), new Object[] { "EEE", "E E", 'E', ModItems.plate_euphemium });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.euphemium_plate, 1), new Object[] { "EWE", "EEE", "EEE", 'E', ModItems.plate_euphemium, 'W', ModItems.watch });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.euphemium_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.plate_euphemium });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.euphemium_boots, 1), new Object[] { "E E", "E E", 'E', ModItems.plate_euphemium });
		
		//Jetpacks
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.jetpack_fly, 1), new Object[] { "ACA", "TLT", "D D", 'A', ModItems.cap_aluminium, 'C', ModItems.circuit_targeting_tier1, 'T', ModItems.tank_steel, 'L', Items.leather, 'D', ModItems.thruster_small });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.jetpack_break, 1), new Object[] { "ICI", "TJT", "I I", 'C', ModItems.circuit_targeting_tier2, 'T', ModItems.ingot_dura_steel, 'J', ModItems.jetpack_fly, 'I', ModItems.plate_polymer });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.jetpack_vector, 1), new Object[] { "TCT", "MJM", "B B", 'C', ModItems.circuit_targeting_tier3, 'T', ModItems.tank_steel, 'J', ModItems.jetpack_break, 'M', ModItems.motor, 'B', ModItems.bolt_dura_steel });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.jetpack_boost, 1), new Object[] { "PCP", "DJD", "PAP", 'C', ModItems.circuit_targeting_tier4, 'P', BIGMT.plate(), 'D', DESH.ingot(), 'J', ModItems.jetpack_vector, 'A', ModItems.board_copper });
		
		//Hazmat
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.hazmat_helmet, 1), new Object[] { "EEE", "EIE", " P ", 'E', ModItems.hazmat_cloth, 'I', KEY_ANYPANE, 'P', FE.plate() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.hazmat_plate, 1), new Object[] { "E E", "EEE", "EEE", 'E', ModItems.hazmat_cloth });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.hazmat_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.hazmat_cloth });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.hazmat_boots, 1), new Object[] { "E E", "E E", 'E', ModItems.hazmat_cloth });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.hazmat_helmet_red, 1), new Object[] { "EEE", "IEI", "EFE", 'E', ModItems.hazmat_cloth_red, 'I', KEY_ANYPANE, 'F', FE.plate() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.hazmat_plate_red, 1), new Object[] { "E E", "EEE", "EEE", 'E', ModItems.hazmat_cloth_red });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.hazmat_legs_red, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.hazmat_cloth_red });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.hazmat_boots_red, 1), new Object[] { "E E", "E E", 'E', ModItems.hazmat_cloth_red });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.hazmat_helmet_grey, 1), new Object[] { "EEE", "IEI", "EFE", 'E', ModItems.hazmat_cloth_grey, 'I', KEY_ANYPANE, 'F', FE.plate() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.hazmat_plate_grey, 1), new Object[] { "E E", "EEE", "EEE", 'E', ModItems.hazmat_cloth_grey });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.hazmat_legs_grey, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.hazmat_cloth_grey });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.hazmat_boots_grey, 1), new Object[] { "E E", "E E", 'E', ModItems.hazmat_cloth_grey });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.asbestos_helmet, 1), new Object[] { "EEE", "EIE", 'E', ModItems.asbestos_cloth, 'I', "plateGold" });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.asbestos_plate, 1), new Object[] { "E E", "EEE", "EEE", 'E', ModItems.asbestos_cloth });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.asbestos_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.asbestos_cloth });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.asbestos_boots, 1), new Object[] { "E E", "E E", 'E', ModItems.asbestos_cloth });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.hazmat_paa_helmet, 1), new Object[] { "EEE", "IEI", " P ", 'E', ModItems.plate_paa, 'I', KEY_ANYPANE, 'P', FE.plate() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.hazmat_paa_plate, 1), new Object[] { "E E", "EEE", "EEE", 'E', ModItems.plate_paa });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.hazmat_paa_legs, 1), new Object[] { "EEE", "E E", "E E", 'E', ModItems.plate_paa });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.hazmat_paa_boots, 1), new Object[] { "E E", "E E", 'E', ModItems.plate_paa });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.paa_plate, 1), new Object[] { "E E", "NEN", "ENE", 'E', ModItems.plate_paa, 'N', OreDictManager.getReflector() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.paa_legs, 1), new Object[] { "EEE", "N N", "E E", 'E', ModItems.plate_paa, 'N', OreDictManager.getReflector() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.paa_boots, 1), new Object[] { "E E", "N N", 'E', ModItems.plate_paa, 'N', OreDictManager.getReflector() });
		
		//Liquidator Suit
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.liquidator_helmet, 1), new Object[] { "III", "CBC", "III", 'I', ModItems.plate_polymer, 'C', ModItems.cladding_lead, 'B', ModItems.hazmat_helmet_grey });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.liquidator_plate, 1), new Object[] { "ICI", "TBT", "ICI", 'I', ModItems.plate_polymer, 'C', ModItems.cladding_lead, 'B', ModItems.hazmat_plate_grey, 'T', ModItems.gas_empty });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.liquidator_legs, 1), new Object[] { "III", "CBC", "I I", 'I', ModItems.plate_polymer, 'C', ModItems.cladding_lead, 'B', ModItems.hazmat_legs_grey });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.liquidator_boots, 1), new Object[] { "ICI", "IBI", 'I', ModItems.plate_polymer, 'C', ModItems.cladding_lead, 'B', ModItems.hazmat_boots_grey });

		//Masks
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.goggles, 1), new Object[] { "P P", "GPG", 'G', KEY_ANYPANE, 'P', STEEL.plate() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gas_mask, 1), new Object[] { "PPP", "GPG", " F ", 'G', KEY_ANYPANE, 'P', STEEL.plate(), 'F', FE.plate() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gas_mask_m65, 1), new Object[] { "PPP", "GPG", " F ", 'G', KEY_ANYPANE, 'P', ModItems.plate_polymer, 'F', FE.plate() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gas_mask_olde, 1), new Object[] { "PPP", "GPG", " F ", 'G', KEY_ANYPANE, 'P', Items.leather, 'F', FE.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gas_mask_mono, 1), new Object[] { " P ", "PPP", " F ", 'P', ModItems.plate_polymer, 'F', FE.plate() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.mask_of_infamy, 1), new Object[] { "III", "III", " I ", 'I', FE.plate() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ashglasses, 1), new Object[] { "I I", "GPG", 'I', ModItems.plate_polymer, 'G', ModBlocks.glass_ash, 'P', POLYMER.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.mask_rag, 1), new Object[] { "RRR", 'R', ModItems.rag_damp });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.mask_piss, 1), new Object[] { "RRR", 'R', ModItems.rag_piss });
		
		//Capes
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.cape_radiation, 1), new Object[] { "W W", "WIW", "WDW", 'W', new ItemStack(Blocks.wool, 1, 11), 'D', KEY_YELLOW, 'I', ModItems.nuclear_waste });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.cape_gasmask, 1), new Object[] { "W W", "WIW", "WDW", 'W', new ItemStack(Blocks.wool, 1, 4), 'D', KEY_BLACK, 'I', ModItems.gas_mask });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.cape_schrabidium, 1), new Object[] { "W W", "WIW", "WDW", 'W', SA326.ingot(), 'D', KEY_BLACK, 'I', ModItems.circuit_red_copper });
		
		//Configged
		if(GeneralConfig.enableLBSM && GeneralConfig.enableLBSMSimpleArmorRecipes) {
			addHelmet(	STAR.ingot(), ModItems.starmetal_helmet);
			addChest(	STAR.ingot(), ModItems.starmetal_plate);
			addLegs(	STAR.ingot(), ModItems.starmetal_legs);
			addBoots(	STAR.ingot(), ModItems.starmetal_boots);
			addHelmet(	SA326.ingot(), ModItems.schrabidium_helmet);
			addChest(	SA326.ingot(), ModItems.schrabidium_plate);
			addLegs(	SA326.ingot(), ModItems.schrabidium_legs);
			addBoots(	SA326.ingot(), ModItems.schrabidium_boots);
		} else {
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.starmetal_helmet, 1), new Object[] { "EEE", "ECE", 'E', STAR.ingot(), 'C', ModItems.cobalt_helmet });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.starmetal_plate, 1), new Object[] { "ECE", "EEE", "EEE", 'E', STAR.ingot(), 'C', ModItems.cobalt_plate });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.starmetal_legs, 1), new Object[] { "EEE", "ECE", "E E", 'E', STAR.ingot(), 'C', ModItems.cobalt_legs });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.starmetal_boots, 1), new Object[] { "E E", "ECE", 'E', STAR.ingot(), 'C', ModItems.cobalt_boots });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.schrabidium_helmet, 1), new Object[] { "EEE", "ESE", " P ", 'E', SA326.ingot(), 'S', ModItems.starmetal_helmet, 'P', ModItems.pellet_charged });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.schrabidium_plate, 1), new Object[] { "ESE", "EPE", "EEE", 'E', SA326.ingot(), 'S', ModItems.starmetal_plate, 'P', ModItems.pellet_charged });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.schrabidium_legs, 1), new Object[] { "EEE", "ESE", "EPE", 'E', SA326.ingot(), 'S', ModItems.starmetal_legs, 'P', ModItems.pellet_charged });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.schrabidium_boots, 1), new Object[] { "EPE", "ESE", 'E', SA326.ingot(), 'S', ModItems.starmetal_boots, 'P', ModItems.pellet_charged });
		}
	}
	
	public static void addHelmet(Object ingot, Item pick) {
		addArmor(ingot, pick, patternHelmet);
	}
	public static void addChest(Object ingot, Item axe) {
		addArmor(ingot, axe, patternChetplate);
	}
	public static void addLegs(Object ingot, Item shovel) {
		addArmor(ingot, shovel, patternLeggings);
	}
	public static void addBoots(Object ingot, Item hoe) {
		addArmor(ingot, hoe, patternBoots);
	}
	
	public static void addArmor(Object ingot, Item armor, String[] pattern) {
		CraftingManager.addRecipeAuto(new ItemStack(armor), new Object[] { pattern, 'X', ingot });
	}
	
	public static final String[] patternHelmet = new String[] {"XXX", "X X"};
	public static final String[] patternChetplate = new String[] {"X X", "XXX", "XXX"};
	public static final String[] patternLeggings = new String[] {"XXX", "X X", "X X"};
	public static final String[] patternBoots = new String[] {"X X", "X X"};
}

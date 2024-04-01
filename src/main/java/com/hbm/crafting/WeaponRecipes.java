package com.hbm.crafting;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;

import static com.hbm.inventory.OreDictManager.*;

import com.hbm.items.ItemAmmoEnums.*;
import com.hbm.items.food.ItemConserve.EnumFoodType;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.GunB92Cell;
import com.hbm.main.CraftingManager;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * For guns, ammo and the like
 * @author hbm
 */
public class WeaponRecipes {
	
	public static void register() {
		
		//Missiles
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.missile_taint, 1), new Object[] { ModItems.missile_assembly, ModItems.bucket_mud, ModItems.powder_spark_mix, ModItems.powder_magic });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.missile_micro, 1), new Object[] { ModItems.missile_assembly, ModItems.ducttape, ModItems.ammo_nuke.stackFromEnum(AmmoFatman.HIGH) });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.missile_bhole, 1), new Object[] { ModItems.missile_assembly, ModItems.ducttape, ModItems.grenade_black_hole });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.missile_schrabidium, 1), new Object[] { ModItems.missile_assembly, ModItems.ducttape, ModItems.cell_anti_schrabidium, ANY_HARDPLASTIC.ingot() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.missile_emp, 1), new Object[] { ModItems.missile_assembly, ModItems.ducttape, ModBlocks.emp_bomb });
		
		//Missile fins
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.mp_stability_10_flat, 1), new Object[] { "PSP", "P P", 'P', STEEL.plate(), 'S', ModBlocks.steel_scaffold });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.mp_stability_10_cruise, 1), new Object[] { "ASA", " S ", "PSP", 'A', TI.plate(), 'P', STEEL.plate(), 'S', ModBlocks.steel_scaffold });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.mp_stability_10_space, 1), new Object[] { "ASA", "PSP", 'A', AL.plate(), 'P', STEEL.ingot(), 'S', ModBlocks.steel_scaffold });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.mp_stability_15_flat, 1), new Object[] { "ASA", "PSP", 'A', AL.plate(), 'P', STEEL.plate(), 'S', ModBlocks.steel_scaffold });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.mp_stability_15_thin, 1), new Object[] { "A A", "PSP", "PSP", 'A', AL.plate(), 'P', STEEL.plate(), 'S', ModBlocks.steel_scaffold });

		//Missile thrusters
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.mp_thruster_15_balefire_large_rad, 1), new Object[] { "CCC", "CTC", "CCC", 'C', CU.plateCast(), 'T', ModItems.mp_thruster_15_balefire_large });
		
		//Missile fuselages
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.mp_fuselage_10_kerosene_insulation, 1), new Object[] { "CCC", "CTC", "CCC", 'C', ANY_RUBBER.ingot(), 'T', ModItems.mp_fuselage_10_kerosene });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.mp_fuselage_10_long_kerosene_insulation, 1), new Object[] { "CCC", "CTC", "CCC", 'C', ANY_RUBBER.ingot(), 'T', ModItems.mp_fuselage_10_long_kerosene });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.mp_fuselage_15_kerosene_insulation, 1), new Object[] { "CCC", "CTC", "CCC", 'C', ANY_RUBBER.ingot(), 'T', ModItems.mp_fuselage_15_kerosene });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.mp_fuselage_10_solid_insulation, 1), new Object[] { "CCC", "CTC", "CCC", 'C', ANY_RUBBER.ingot(), 'T', ModItems.mp_fuselage_10_solid });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.mp_fuselage_10_long_solid_insulation, 1), new Object[] { "CCC", "CTC", "CCC", 'C', ANY_RUBBER.ingot(), 'T', ModItems.mp_fuselage_10_long_solid });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.mp_fuselage_15_solid_insulation, 1), new Object[] { "CCC", "CTC", "CCC", 'C', ANY_RUBBER.ingot(), 'T', ModItems.mp_fuselage_15_solid });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.mp_fuselage_15_solid_desh, 1), new Object[] { "CCC", "CTC", "CCC", 'C', DESH.ingot(), 'T', ModItems.mp_fuselage_15_solid });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.mp_fuselage_10_kerosene_metal, 1), new Object[] { "ICI", "CTC", "ICI", 'C', STEEL.plate(), 'I', IRON.plate(), 'T', ModItems.mp_fuselage_10_kerosene });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.mp_fuselage_10_long_kerosene_metal, 1), new Object[] { "ICI", "CTC", "ICI", 'C', STEEL.plate(), 'I', IRON.plate(), 'T', ModItems.mp_fuselage_10_long_kerosene });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.mp_fuselage_15_kerosene_metal, 1), new Object[] { "ICI", "CTC", "ICI", 'C', STEEL.plate(), 'I', IRON.plate(), 'T', ModItems.mp_fuselage_15_kerosene });
		
		//Missile warheads
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.mp_warhead_15_boxcar, 1), new Object[] { "SNS", "CBC", "SFS", 'S', STAR.ingot(), 'N', ModBlocks.det_nuke, 'C', ModItems.circuit_targeting_tier4, 'B', ModBlocks.boxcar, 'F', ModItems.tritium_deuterium_cake });
		
		//Missile chips
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.mp_chip_1, 1), new Object[] { "P", "C", "S", 'P', ANY_RUBBER.ingot(), 'C', ModItems.circuit_targeting_tier1, 'S', ModBlocks.steel_scaffold });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.mp_chip_2, 1), new Object[] { "P", "C", "S", 'P', ANY_RUBBER.ingot(), 'C', ModItems.circuit_targeting_tier2, 'S', ModBlocks.steel_scaffold });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.mp_chip_3, 1), new Object[] { "P", "C", "S", 'P', ANY_RUBBER.ingot(), 'C', ModItems.circuit_targeting_tier3, 'S', ModBlocks.steel_scaffold });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.mp_chip_4, 1), new Object[] { "P", "C", "S", 'P', ANY_RUBBER.ingot(), 'C', ModItems.circuit_targeting_tier4, 'S', ModBlocks.steel_scaffold });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.mp_chip_5, 1), new Object[] { "P", "C", "S", 'P', ANY_RUBBER.ingot(), 'C', ModItems.circuit_targeting_tier5, 'S', ModBlocks.steel_scaffold });
		
		//Turrets
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.turret_sentry, 1), new Object[] { "PPL", " MD", " SC", 'P', STEEL.plate(), 'M', ModItems.motor, 'L', ModItems.mechanism_rifle_1, 'S', ModBlocks.steel_scaffold, 'C', ModItems.circuit_red_copper, 'D', ModItems.crt_display });
		
		//Guns
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_rpg, 1), new Object[] { "SSW", " MW", 'S', STEEL.shell(), 'W', IRON.plate(), 'M', ModItems.mechanism_launcher_1 });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_panzerschreck, 1), new Object[] { "SSS", " MW", 'S', STEEL.shell(), 'W', CU.plate(), 'M', ModItems.mechanism_launcher_1 });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_karl, 1), new Object[] { "SSW", " MW", 'S', STEEL.shell(), 'W', ALLOY.plate(), 'M', ModItems.mechanism_launcher_2 });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_quadro, 1), new Object[] { "SSS", "SSS", "CM ", 'S', STEEL.pipe(), 'C', ModItems.circuit_targeting_tier3, 'M', ModItems.mechanism_launcher_2 });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_hk69, 1), new Object[] { "SSI", " MB", 'S', STEEL.shell(), 'I', IRON.ingot(), 'M', ModItems.mechanism_launcher_1, 'B', STEEL.bolt() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_congolake, 1), new Object[] { "HHR", "WLW", 'H', AL.shell(), 'R', ModItems.mechanism_rifle_1, 'W', KEY_LOG, 'L', ModItems.mechanism_launcher_1 });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_stinger, 1), new Object[] { "SSW", "CMW", 'S', STEEL.plate(), 'W', TI.plate(), 'C', ModItems.circuit_red_copper, 'M', ModItems.mechanism_launcher_2 });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_stinger_rocket, 4), new Object[] { "SS ", "STI", " IR", 'S', STEEL.plate(), 'T', Item.getItemFromBlock(Blocks.tnt), 'I', AL.plate(), 'R', REDSTONE.dust() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_revolver, 1), new Object[] { "SSM", " RW", 'S', STEEL.plate(), 'W', KEY_PLANKS, 'R', ModItems.wire_aluminium, 'M', ModItems.mechanism_revolver_1 });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_revolver_saturnite, 1), new Object[] { "SSM", " RW", 'S', BIGMT.plate(), 'W', KEY_PLANKS, 'R', ModItems.wire_tungsten, 'M', ModItems.mechanism_revolver_2 });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_revolver_gold, 1), new Object[] { "SSM", " RW", 'S', GOLD.plate(), 'W', W.ingot(), 'R', ModItems.wire_gold, 'M', ModItems.mechanism_revolver_1 });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_revolver_schrabidium, 1), new Object[] { "SSM", " RW", 'S', SA326.block(), 'W', W.ingot(), 'R', ModItems.wire_schrabidium, 'M', ModItems.mechanism_special });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_revolver_pip, 1), new Object[] { " G ", "SSP", " TI", 'G', KEY_ANYPANE, 'S', STEEL.plate(), 'P', ModItems.mechanism_revolver_2, 'T', ModItems.wire_tungsten, 'I', ANY_PLASTIC.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_revolver_nopip, 1), new Object[] { "SSP", " TI", 'S', STEEL.plate(), 'P', ModItems.mechanism_revolver_2, 'T', ModItems.wire_tungsten, 'I', ANY_PLASTIC.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_revolver_blackjack, 1), new Object[] { "SSP", " TI", 'S', STEEL.plate(), 'P', ModItems.mechanism_revolver_2, 'T', ModItems.wire_tungsten, 'I', KEY_PLANKS });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_revolver_silver, 1), new Object[] { "SSP", " TI", 'S', AL.plate(), 'P', ModItems.mechanism_revolver_2, 'T', ModItems.wire_tungsten, 'I', KEY_PLANKS });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_revolver_red, 1), new Object[] { "R ", " B", 'R', ModItems.key_red, 'B', ModItems.gun_revolver_blackjack });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_deagle, 1), new Object[] { "PPM", " BI", 'P', STEEL.plate(), 'B', STEEL.bolt(), 'I', ANY_PLASTIC.ingot(), 'M', ModItems.mechanism_rifle_1 });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_uac_pistol, 1), new Object[] { "IIM", " BI", 'B', DURA.bolt(), 'I', ANY_PLASTIC.ingot(), 'M', ModItems.mechanism_rifle_1 });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_revolver_cursed, 1), new Object[] { "TTM", "SRI", 'S', STEEL.plate(), 'I', STEEL.ingot(), 'R', ModItems.wire_red_copper, 'T', TI.plate(), 'M', ModItems.mechanism_revolver_2 });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_revolver_nightmare, 1), new Object[] { "SEM", " RW", 'S', STEEL.plate(), 'W', KEY_PLANKS, 'R', ModItems.wire_aluminium, 'E', ModItems.powder_power, 'M', ModItems.mechanism_revolver_2 });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_revolver_nightmare2, 1), new Object[] { "SSM", "RRW", 'S', OreDictManager.getReflector(), 'W', W.ingot(), 'R', ModItems.wire_gold, 'M', ModItems.mechanism_special });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_bio_revolver, 1), new Object[] { "SSM", "BTW", 'S', STEEL.plate(), 'M', ModItems.mechanism_revolver_2, 'B', B.ingot(), 'T', W.bolt(), 'W', KEY_LOG });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_chemthrower, 1), new Object[] { "RWC", "HHT", "RLC", 'R', RUBBER.ingot(), 'W', ModItems.wrench, 'C', CU.plate(), 'H', STEEL.shell(), 'T', ModItems.tank_steel, 'L', ModItems.mechanism_launcher_2 });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_fatman, 1), new Object[] { "SSI", "IIM", "WPH", 'S', STEEL.plate(), 'I', STEEL.ingot(), 'W', ModItems.wire_aluminium, 'H', STEEL.shell(), 'P', Item.getItemFromBlock(Blocks.piston), 'M', ModItems.mechanism_launcher_2 });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_mirv, 1), new Object[] { "LLL", "WFW", "SSS", 'S', STEEL.plate(), 'L', PB.plate(), 'W', ModItems.wire_gold, 'F', ModItems.gun_fatman });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_proto, 1), new Object[] { "LLL", "WFW", "SSS", 'S', ANY_RUBBER.ingot(), 'L', ModItems.plate_desh, 'W', ModItems.wire_tungsten, 'F', ModItems.gun_fatman });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_nuke, 1, AmmoFatman.BALEFIRE.ordinal()), new Object[] { " S ", "EBE", " S ", 'S', STEEL.shell(), 'E', ModItems.powder_power, 'B', ModItems.egg_balefire_shard });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_mp40, 1), new Object[] { "IIM", " SW", " S ", 'S', STEEL.plate(), 'I', STEEL.ingot(), 'W', KEY_PLANKS, 'M', ModItems.mechanism_rifle_1 });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_thompson, 1), new Object[] { "IIM", " SW", " S ", 'S', IRON.plate(), 'I', STEEL.plate(), 'W', KEY_PLANKS, 'M', ModItems.mechanism_rifle_2 });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_flechette, 1), new Object[] { "PPM", "TIS", "G  ", 'P', STEEL.plate(), 'M', ModItems.mechanism_rifle_2, 'T', STEEL.shell(), 'I', STEEL.ingot(), 'S', ANY_PLASTIC.ingot(), 'G', ModItems.mechanism_launcher_1 });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_uboinik, 1), new Object[] { "IIM", "SPW", 'P', STEEL.plate(), 'I', STEEL.ingot(), 'W', KEY_PLANKS, 'S', KEY_STICK, 'M', ModItems.mechanism_revolver_2 });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_supershotgun, 1), new Object[] { "BBM", "BBM", "AWL", 'B', STEEL.pipe(), 'M', ModItems.mechanism_rifle_2, 'A', ModItems.plate_dalekanium, 'W', ModItems.wire_gold, 'L', KEY_LOG });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_ks23, 1), new Object[] { "PPM", "SWL", 'P', STEEL.plate(), 'M', ModItems.mechanism_rifle_1, 'S', KEY_STICK, 'W', ModItems.wire_tungsten, 'L', KEY_LOG });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.gun_sauer, 1), new Object[] { ModItems.ducttape, ModItems.gun_ks23, Blocks.lever, ModItems.gun_ks23 });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_xvl1456, 1), new Object[] { "PBB", "ACC", "PRY", 'P', STEEL.plate(), 'R', ModItems.redcoil_capacitor, 'A', ModItems.coil_advanced_alloy, 'B', ModItems.battery_generic, 'C', ModItems.coil_advanced_torus, 'Y', ModItems.mechanism_special });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_xvl1456_ammo, 16), new Object[] { " S ", "SRS", " S ", 'S', STEEL.plate(), 'R', ModItems.waste_natural_uranium });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_xvl1456_ammo, 16), new Object[] { " S ", "SRS", " S ", 'S', STEEL.plate(), 'R', ModItems.waste_uranium });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_xvl1456_ammo, 20), new Object[] { " S ", " R ", " S ", 'S', STEEL.plate(), 'R', ModItems.waste_plate_u235 });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_xvl1456_ammo, 16), new Object[] { "SRS", 'S', STEEL.plate(), 'R', ModItems.waste_u235 });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_xvl1456_ammo, 16), new Object[] { " S ", " R ", " S ", 'S', STEEL.plate(), 'R', U238.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_xvl1456_ammo, 16), new Object[] { "SRS", 'S', STEEL.plate(), 'R', U238.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_osipr, 1), new Object[] { "CCT", "WWI", "MCC", 'C', CMB.plate(), 'T', W.ingot(), 'W', ModItems.wire_magnetized_tungsten, 'I', ModItems.mechanism_rifle_2, 'M', ModItems.coil_magnetized_tungsten });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_immolator, 1), new Object[] { "WCC", "PMT", "WAA", 'W', ModItems.wire_gold, 'C', CU.plate(), 'P', ALLOY.plate(), 'M', ModItems.mechanism_launcher_1, 'T', ModItems.tank_steel, 'A', STEEL.plate() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_immolator_ammo, 16), new Object[] { "SPS", "PCP", "SPS", 'S', STEEL.plate(), 'C', COAL.dust(), 'P', P_RED.dust() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_immolator_ammo, 16), new Object[] { " F ", "SFS", " F ", 'S', STEEL.plate(), 'F', Fluids.DIESEL.getDict(1000) });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_immolator_ammo, 24), new Object[] { " F ", "SFS", " F ", 'S', STEEL.plate(), 'F', ModItems.canister_napalm });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_flamer, 1), new Object[] { "WSS", "SCT", "WMI", 'W', ModItems.wire_gold, 'S', STEEL.pipe(), 'C', ModItems.coil_tungsten, 'T', ModItems.tank_steel, 'M', ModItems.mechanism_launcher_1, 'I', STEEL.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_cryolator, 1), new Object[] { "SSS", "IWL", "LMI", 'S', STEEL.plate(), 'I', IRON.plate(), 'L', Items.leather, 'M', ModItems.mechanism_launcher_1, 'W', ModItems.wire_aluminium });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_cryolator_ammo, 16), new Object[] { "SPS", "PCP", "SPS", 'S', STEEL.plate(), 'C', KNO.dust(), 'P', Items.snowball });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_cryolator_ammo, 16), new Object[] { " F ", "SFS", " F ", 'S', STEEL.plate(), 'F', ModItems.powder_ice });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_mp, 1), new Object[] { "EEE", "SSM", "III", 'E', EUPH.ingot(), 'S', STEEL.plate(), 'I', STEEL.ingot(), 'M', ModItems.mechanism_rifle_2 });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_emp, 1), new Object[] { "CPG", "CMF", "CPI", 'C', ModItems.coil_copper, 'P', PB.plate(), 'G', ModItems.circuit_gold, 'M', ModItems.magnetron, 'I', W.ingot(), 'F', ModItems.mechanism_special });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_emp_ammo, 8), new Object[] { "IGI", "IPI", "IPI", 'G', GOLD.plate(), 'I', IRON.plate(), 'P', ModItems.powder_power });
		//CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_jack, 1), new Object[] { "WW ", "TSD", " TT", 'W', "ingotWeidanium", 'T', ModItems.toothpicks, 'S', ModItems.gun_uboinik, 'D', ModItems.ducttape });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.gun_jack_ammo, 1), new Object[] { ModItems.ammo_12gauge, ModItems.ammo_12gauge, ModItems.ammo_12gauge, ModItems.ammo_12gauge });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_euthanasia, 1), new Object[] { "TDT", "AAS", " T ", 'A', AUSTRALIUM.ingot(), 'T', ModItems.toothpicks, 'S', ModItems.gun_mp40, 'D', ModItems.ducttape });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_euthanasia_ammo, 12), new Object[] { "P", "S", "N", 'P', ModItems.powder_poison, 'N', KNO.dust(), 'S', ModItems.syringe_metal_empty });
		//CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_spark, 1), new Object[] { "TTD", "AAS", "  T", 'A', "ingotDaffergon", 'T', ModItems.toothpicks, 'S', ModItems.gun_rpg, 'D', ModItems.ducttape });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_spark_ammo, 4), new Object[] { "PCP", "DDD", "PCP", 'P', PB.plate(), 'C', ModItems.coil_gold, 'D', ModItems.powder_power });
		//CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_skystinger, 1), new Object[] { "TTT", "AAS", " D ", 'A', "ingotUnobtainium", 'T', ModItems.toothpicks, 'S', ModItems.gun_stinger, 'D', ModItems.ducttape });
		//CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_hp, 1), new Object[] { "TDT", "ASA", " T ", 'A', "ingotReiium", 'T', ModItems.toothpicks, 'S', ModItems.gun_xvl1456, 'D', ModItems.ducttape });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_hp_ammo, 8), new Object[] { " R ", "BSK", " Y ", 'S', STEEL.plate(), 'K', KEY_BLACK, 'R', KEY_RED, 'B', KEY_BLUE, 'Y', KEY_YELLOW });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_defabricator_ammo, 16), new Object[] { "PCP", "DDD", "PCP", 'P', STEEL.plate(), 'C', ModItems.coil_copper, 'D', LI.dust() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_lever_action, 1), new Object[] { "PPI", "SWD", 'P', IRON.plate(), 'I', ModItems.mechanism_rifle_1, 'S', KEY_STICK, 'D', KEY_PLANKS, 'W', ModItems.wire_aluminium });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_lever_action_dark, 1), new Object[] { "PPI", "SWD", 'P', STEEL.plate(), 'I', ModItems.mechanism_rifle_1, 'S', KEY_STICK, 'D', KEY_PLANKS, 'W', ModItems.wire_aluminium });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_bolt_action, 1), new Object[] { "PPI", "SWD", 'P', STEEL.plate(), 'I', ModItems.mechanism_rifle_1, 'S', KEY_STICK, 'D', KEY_PLANKS, 'W', ModItems.wire_copper });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_bolt_action_green, 1), new Object[] { "PPI", "SWD", 'P', IRON.plate(), 'I', ModItems.mechanism_rifle_1, 'S', KEY_STICK, 'D', KEY_PLANKS, 'W', ModItems.wire_copper });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_bolt_action_saturnite, 1), new Object[] { "PPI", "SWD", 'P', BIGMT.plate(), 'I', ModItems.mechanism_rifle_1, 'S', KEY_STICK, 'D', KEY_PLANKS, 'W', ModItems.wire_tungsten });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_b92), new Object[] { "DDD", "SSC", "  R", 'D', ModItems.plate_dineutronium, 'S', STAR.ingot(), 'C', ModItems.circuit_targeting_tier6, 'R', ModItems.gun_revolver_schrabidium });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_b93), new Object[] { "PCE", "SEB", "PCE", 'P', ModItems.plate_dineutronium, 'C', ModItems.weaponized_starblaster_cell, 'E', ModItems.component_emitter, 'B', ModItems.gun_b92, 'S', ModItems.singularity_spark });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_b92_ammo, 1), new Object[] { "PSP", "ESE", "PSP", 'P', STEEL.plate(), 'S', STAR.ingot(), 'E', ModItems.powder_spark_mix });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.weaponized_starblaster_cell, 1), new Object[] { new ItemStack(ModItems.fluid_tank_full, 1, Fluids.ACID.getID()), GunB92Cell.getFullCell(), ModItems.wire_copper });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_uzi, 1), new Object[] { "SMS", " PB", " P ", 'S', STEEL.ingot(), 'M', ModItems.mechanism_rifle_2, 'P', STEEL.plate(), 'B', DURA.bolt() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_uzi_silencer, 1), new Object[] { "P  ", " P ", "  U", 'P', ANY_PLASTIC.ingot(), 'U', ModItems.gun_uzi });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_uzi_saturnite, 1), new Object[] { "SMS", " PB", " P ", 'S', BIGMT.ingot(), 'M', ModItems.mechanism_rifle_2, 'P', BIGMT.plate(), 'B', W.bolt() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_uzi_saturnite_silencer, 1), new Object[] { "P  ", " P ", "  U", 'P', ANY_PLASTIC.ingot(), 'U', ModItems.gun_uzi_saturnite });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_bolter, 1), new Object[] { "SSM", "PIP", " I ", 'S', BIGMT.plate(), 'I', BIGMT.ingot(), 'M', ModItems.mechanism_special, 'P', ANY_PLASTIC.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_calamity, 1), new Object[] { " PI", "BBM", " PI", 'P', IRON.plate(), 'B', STEEL.pipe(), 'M', ModItems.mechanism_rifle_1, 'I', STEEL.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_m2, 1), new Object[] { " PI", "BBM", " PI", 'P', STEEL.plate(), 'B', STEEL.pipe(), 'M', ModItems.mechanism_rifle_2, 'I', STEEL.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_minigun, 1), new Object[] { "PIB", "PCM", "PIB", 'P', STEEL.pipe(), 'B', STEEL.block(), 'I', ANY_PLASTIC.ingot(), 'C', ModItems.mechanism_rifle_2, 'M', ModItems.motor });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_lacunae, 1), new Object[] { "TIT", "ILI", "PRP", 'T', ModItems.syringe_taint, 'I', STAR.ingot(), 'L', ModItems.gun_minigun, 'P', ModItems.pellet_rtg, 'R', ModBlocks.machine_rtg_grey });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_mymy, 1), new Object[] { "PP ", " WP", 'P', ANY_RUBBER.ingot(), 'W', ModItems.wire_aluminium });
		//CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_darter, 1), new Object[] { "SST", "  P", 'S', STEEL.plate(), 'T', ModItems.gas_empty, 'P', ANY_PLASTIC.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_fireext, 1), new Object[] { "HB", " T", 'H', STEEL.pipe(), 'B', STEEL.bolt(), 'T', ModItems.tank_steel });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_spas12, 1), new Object[] { "TPS", "HHR", "  L", 'T', STEEL.bolt(), 'P', STEEL.plate(), 'S', STEEL.ingot(), 'H', STEEL.pipe(), 'R', ModItems.mechanism_rifle_1, 'L', ANY_PLASTIC.ingot()});
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_glass_cannon, 1), new Object[] { "GGC", "GTM", 'G', Item.getItemFromBlock(ModBlocks.glass_quartz), 'C', ModItems.battery_lithium_cell, 'T', ModItems.crt_display, 'M', ModItems.mechanism_special });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_remington, 1), new Object[] { "PPM", "S L", 'P', STEEL.plate(), 'M', ModItems.mechanism_rifle_1, 'S', KEY_SLAB, 'L', KEY_LOG });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_benelli), new Object[] { "HHP", "SSM", "AAP", 'H', ModItems.ingot_dura_steel, 'S', STEEL.pipe(), 'A', AL.pipe(), 'P', ANY_PLASTIC.ingot(), 'M', ModItems.mechanism_rifle_2 });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_lunatic_marksman), new Object[] { " GN", "SSM", "  A", 'G', KEY_ANYPANE, 'N', ModItems.powder_nitan_mix, 'S', BIGMT.plate(), 'M', ModItems.mechanism_special, 'A', ANY_RESISTANTALLOY.plateCast() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_coilgun), new Object[] { "CCC", "SSM", "  P", 'C', ModBlocks.capacitor_copper, 'S', BIGMT.plate(), 'M', ModItems.mechanism_special, 'P', ANY_PLASTIC.ingot() });

		//Folly shells
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.folly_bullet, 1), new Object[] { " S ", "STS", "SMS", 'S', STAR.ingot(), 'T', ModItems.powder_magic, 'M', ModBlocks.block_meteor });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.folly_bullet_nuclear, 1), new Object[] { " N ", "UTU", "UTU", 'N', ModItems.ammo_nuke, 'U', IRON.ingot(), 'T', W.block() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.folly_bullet_du, 1), new Object[] { " U ", "UDU", "UTU", 'U', U238.block(), 'D', DESH.block(), 'T', W.block() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.folly_shell, 1), new Object[] { "IPI", "IPI", "IMI", 'I', IRON.ingot(), 'P', IRON.plate(), 'M', ANY_SMOKELESS.dust() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_folly, 1), new Object[] { " B ", "MEM", " S ", 'B', ModItems.folly_bullet, 'M', ModItems.powder_magic, 'E', ModItems.powder_power, 'S', ModItems.folly_shell });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_folly_nuclear, 1), new Object[] { " B ", "EEE", " S ", 'B', ModItems.folly_bullet_nuclear, 'E', ModBlocks.det_charge, 'S', ModItems.folly_shell });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_folly_du, 1), new Object[] { " B ", "EEE", " S ", 'B', ModItems.folly_bullet_du, 'E', ModBlocks.det_charge, 'S', ModItems.folly_shell });
		
		FluidType[] chainsawTypes = new FluidType[] {Fluids.DIESEL, Fluids.DIESEL_CRACK, Fluids.PETROIL, Fluids.PETROIL_LEADED, Fluids.GASOLINE, Fluids.GASOLINE_LEADED, Fluids.BIOFUEL};
		for(FluidType type : chainsawTypes) CraftingManager.addRecipeAuto(ModItems.ammo_rocket.stackFromEnum(2, AmmoRocket.RPC), new Object[] { "BP ", "CBH", " DR", 'B', ModItems.blades_steel, 'P', STEEL.plate(), 'C', type.getDict(1000), 'H', STEEL.shell(), 'D', ModItems.piston_selenium, 'R', ModItems.ammo_rocket });
		
		//240mm Shells
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_shell, 4), new Object[] { " T ", "GHG", "CCC", 'T', ModBlocks.tnt, 'G', Items.gunpowder, 'H', STEEL.shell(), 'C', CU.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_shell, 4), new Object[] { " T ", "GHG", "CCC", 'T', ModBlocks.tnt, 'G', ModItems.ballistite, 'H', STEEL.shell(), 'C', CU.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_shell, 6), new Object[] { " T ", "GHG", "CCC", 'T', ModBlocks.tnt, 'G', ModItems.cordite, 'H', STEEL.shell(), 'C', CU.ingot() });
		CraftingManager.addRecipeAuto(ModItems.ammo_shell.stackFromEnum(4, Ammo240Shell.EXPLOSIVE), new Object[] { " T ", "GHG", "CCC", 'T', ANY_PLASTICEXPLOSIVE.ingot(), 'G', Items.gunpowder, 'H', STEEL.shell(), 'C', CU.ingot() });
		CraftingManager.addRecipeAuto(ModItems.ammo_shell.stackFromEnum(4, Ammo240Shell.EXPLOSIVE), new Object[] { " T ", "GHG", "CCC", 'T', ANY_PLASTICEXPLOSIVE.ingot(), 'G', ModItems.ballistite, 'H', STEEL.shell(), 'C', CU.ingot() });
		CraftingManager.addRecipeAuto(ModItems.ammo_shell.stackFromEnum(6, Ammo240Shell.EXPLOSIVE), new Object[] { " T ", "GHG", "CCC", 'T', ANY_PLASTICEXPLOSIVE.ingot(), 'G', ModItems.cordite, 'H', STEEL.shell(), 'C', CU.ingot() });
		CraftingManager.addRecipeAuto(ModItems.ammo_shell.stackFromEnum(4, Ammo240Shell.APFSDS_T), new Object[] { " I ", "GIG", "CCC", 'I', W.ingot(), 'G', Items.gunpowder, 'C', CU.ingot() });
		CraftingManager.addRecipeAuto(ModItems.ammo_shell.stackFromEnum(4, Ammo240Shell.APFSDS_T), new Object[] { " I ", "GIG", "CCC", 'I', W.ingot(), 'G', ModItems.ballistite, 'C', CU.ingot() });
		CraftingManager.addRecipeAuto(ModItems.ammo_shell.stackFromEnum(6, Ammo240Shell.APFSDS_T), new Object[] { " I ", "GIG", "CCC", 'I', W.ingot(), 'G', ModItems.cordite, 'C', CU.ingot() });
		CraftingManager.addRecipeAuto(ModItems.ammo_shell.stackFromEnum(4, Ammo240Shell.APFSDS_DU), new Object[] { " I ", "GIG", "CCC", 'I', U238.ingot(), 'G', Items.gunpowder, 'C', CU.ingot() });
		CraftingManager.addRecipeAuto(ModItems.ammo_shell.stackFromEnum(4, Ammo240Shell.APFSDS_DU), new Object[] { " I ", "GIG", "CCC", 'I', U238.ingot(), 'G', ModItems.ballistite, 'C', CU.ingot() });
		CraftingManager.addRecipeAuto(ModItems.ammo_shell.stackFromEnum(6, Ammo240Shell.APFSDS_DU), new Object[] { " I ", "GIG", "CCC", 'I', U238.ingot(), 'G', ModItems.cordite, 'C', CU.ingot() });
		CraftingManager.addRecipeAuto(ModItems.ammo_shell.stackFromEnum(Ammo240Shell.W9), new Object[] { " P ", "NSN", " P ", 'P', PU239.nugget(), 'N', OreDictManager.getReflector(), 'S', ModItems.ammo_shell.stackFromEnum(Ammo240Shell.EXPLOSIVE) });
		
		//Artillery Shells
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_arty, 1, 0), new Object[] { "CIC", "CSC", "CCC", 'C', ModItems.cordite, 'I', IRON.block(), 'S', CU.shell() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_arty, 1, 1), new Object[] { " D ", "DSD", " D ", 'D', ModItems.ball_dynamite, 'S', new ItemStack(ModItems.ammo_arty, 1, 0) });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_arty, 1, 2), new Object[] { "TTT", "TST", "TTT", 'T', ModItems.ball_tnt, 'S', new ItemStack(ModItems.ammo_arty, 1, 0) });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_arty, 1, 5), new Object[] { "D", "S", "D", 'D', P_WHITE.ingot(), 'S', new ItemStack(ModItems.ammo_arty, 1, 0) });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_arty, 1, 7), new Object[] { "DSD", "SCS", "DSD", 'D', P_WHITE.ingot(), 'S', new ItemStack(ModItems.ammo_arty, 1, 5), 'C', ModBlocks.det_cord });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_arty, 1, 3), new Object[] { " P ", "NSN", " P ", 'P', PU239.nugget(), 'N', OreDictManager.getReflector(), 'S', new ItemStack(ModItems.ammo_arty, 1, 0) });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_arty, 1, 6), new Object[] { "DSD", "SCS", "DSD", 'D', OreDictManager.getReflector(), 'S', new ItemStack(ModItems.ammo_arty, 1, 3), 'C', ModBlocks.det_cord });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.ammo_arty, 1, 4), new Object[] { new ItemStack(ModItems.ammo_arty, 1, 2), ModItems.boy_bullet, ModItems.boy_target, ModItems.boy_shielding, ModItems.circuit_red_copper, ModItems.ducttape });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_arty, 1, 8), new Object[] { " I ", " S ", "CCC", 'C', ModItems.cordite, 'I', ModItems.sphere_steel, 'S', CU.shell() });

		//DGK Belts
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_dgk, 1), new Object[] { "LLL", "GGG", "CCC", 'L', PB.plate(), 'G', ModItems.ballistite, 'C', CU.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_dgk, 1), new Object[] { "LLL", "GGG", "CCC", 'L', PB.plate(), 'G', ModItems.cordite, 'C', CU.ingot() });

		//Fire Extingusisher Tanks
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_fireext, 1), new Object[] { " P ", "BDB", " P ", 'P', STEEL.plate(), 'B', STEEL.bolt(), 'D', new ItemStack(ModItems.fluid_tank_full, 1, Fluids.WATER.getID()) });
		CraftingManager.addRecipeAuto(ModItems.ammo_fireext.stackFromEnum(AmmoFireExt.FOAM), new Object[] { " N ", "NFN", " N ", 'N', KNO.dust(), 'F', ModItems.ammo_fireext });
		CraftingManager.addRecipeAuto(ModItems.ammo_fireext.stackFromEnum(AmmoFireExt.SAND), new Object[] { "NNN", "NFN", "NNN", 'N', ModBlocks.sand_boron, 'F', ModItems.ammo_fireext });
		
		//Blocks of explosives
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.dynamite, 1), new Object[] { "DDD", "DSD", "DDD", 'D', ModItems.stick_dynamite, 'S', ModItems.safety_fuse });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.tnt, 1), new Object[] { "DDD", "DSD", "DDD", 'D', ModItems.stick_tnt, 'S', ModItems.safety_fuse });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.semtex, 1), new Object[] { "DDD", "DSD", "DDD", 'D', ModItems.stick_semtex, 'S', ModItems.safety_fuse });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.c4, 1), new Object[] { "DDD", "DSD", "DDD", 'D', ModItems.stick_c4, 'S', ModItems.safety_fuse });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.fissure_bomb, 1), new Object[] { "SUS", "RPR", "SUS", 'S', ModBlocks.semtex, 'U', U238.block(), 'R', TA.ingot(), 'P', PU239.billet() });

		//Mines
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.mine_ap, 4), new Object[] { "C", "P", "T", 'C', ModItems.circuit_targeting_tier2, 'P', IRON.plate(), 'T', ANY_PLASTICEXPLOSIVE.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.mine_he, 1), new Object[] { " C ", "PTP", 'C', ModItems.circuit_targeting_tier2, 'P', STEEL.plate(), 'T', ANY_HIGHEXPLOSIVE.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.mine_shrap, 2), new Object[] { "LLL", " C ", "PTP", 'C', ModItems.circuit_targeting_tier2, 'P', STEEL.plate(), 'T', ModBlocks.det_cord, 'L', ModItems.pellet_buckshot });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.mine_fat, 1), new Object[] { "CDN", 'C', ModItems.circuit_targeting_tier2, 'D', ModItems.ducttape, 'N', ModItems.ammo_nuke });
		
		//Nuke parts
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.n2_charge, 1), new Object[] { " D ", "ERE", " D ", 'D', ModItems.ducttape, 'E', ModBlocks.det_charge, 'R', REDSTONE.block() });

		//Custom nuke rods
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.custom_tnt, 1), new Object[] { " C ", "TIT", "TIT", 'C', CU.plate(), 'I', IRON.plate(), 'T', ANY_HIGHEXPLOSIVE.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.custom_nuke, 1), new Object[] { " C ", "LUL", "LUL", 'C', CU.plate(), 'L', PB.plate(), 'U', U235.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.custom_hydro, 1), new Object[] { " C ", "LTL", "LIL", 'C', CU.plate(), 'L', PB.plate(), 'I', IRON.plate(), 'T', ModItems.cell_tritium });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.custom_amat, 1), new Object[] { " C ", "MMM", "AAA", 'C', CU.plate(), 'A', AL.plate(), 'M', ModItems.cell_antimatter });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.custom_dirty, 1), new Object[] { " C ", "WLW", "WLW", 'C', CU.plate(), 'L', PB.plate(), 'W', ModItems.nuclear_waste });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.custom_schrab, 1), new Object[] { " C ", "LUL", "LUL", 'C', CU.plate(), 'L', PB.plate(), 'U', SA326.ingot() });

		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.lamp_demon, 1), new Object[] { " D ", "S S", 'D', ModItems.demon_core_closed, 'S', STEEL.ingot() });

		CraftingManager.addRecipeAuto(new ItemStack(ModItems.crucible, 1, 3), new Object[] { "MEM", "YDY", "YCY", 'M', ModItems.ingot_meteorite_forged, 'E', EUPH.ingot(), 'Y', ModItems.billet_yharonite, 'D', ModItems.demon_core_closed, 'C', ModItems.ingot_chainsteel });
	}
}

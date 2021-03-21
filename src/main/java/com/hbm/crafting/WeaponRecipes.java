package com.hbm.crafting;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.GunB92Cell;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

/**
 * For guns, ammo and the like
 * @author hbm
 */
public class WeaponRecipes {
	
	public static void register() {
		
		//Missiles
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.missile_taint, 1), new Object[] { ModItems.missile_assembly, ModItems.bucket_mud, ModItems.powder_spark_mix, ModItems.powder_magic });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.missile_micro, 1), new Object[] { ModItems.missile_assembly, ModItems.ducttape, ModItems.ammo_nuke_high });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.missile_bhole, 1), new Object[] { ModItems.missile_assembly, ModItems.ducttape, ModItems.grenade_black_hole });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.missile_schrabidium, 1), new Object[] { ModItems.missile_assembly, ModItems.ducttape, ModItems.grenade_aschrab });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.missile_schrabidium, 1), new Object[] { ModItems.missile_assembly, ModItems.ducttape, ModItems.cell_sas3, ModItems.circuit_targeting_tier4 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.missile_emp, 1), new Object[] { ModItems.missile_assembly, ModItems.ducttape, ModBlocks.emp_bomb, ModItems.circuit_targeting_tier3 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.missile_anti_ballistic, 1), new Object[] { ModItems.missile_generic, ModItems.circuit_targeting_tier3 });
		
		//Missile fins
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.mp_stability_10_flat, 1), new Object[] { "PSP", "P P", 'P', "plateSteel", 'S', ModBlocks.steel_scaffold }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.mp_stability_10_cruise, 1), new Object[] { "ASA", " S ", "PSP", 'A', "plateTitanium", 'P', "plateSteel", 'S', ModBlocks.steel_scaffold }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.mp_stability_10_space, 1), new Object[] { "ASA", "PSP", 'A', "plateAluminum", 'P', "ingotSteel", 'S', ModBlocks.steel_scaffold }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.mp_stability_15_flat, 1), new Object[] { "ASA", "PSP", 'A', "plateAluminum", 'P', "plateSteel", 'S', ModBlocks.steel_scaffold }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.mp_stability_15_thin, 1), new Object[] { "A A", "PSP", "PSP", 'A', "plateAluminum", 'P', "plateSteel", 'S', ModBlocks.steel_scaffold }));

		//Missile thrusters
		GameRegistry.addRecipe(new ItemStack(ModItems.mp_thruster_15_balefire_large_rad, 1), new Object[] { "CCC", "CTC", "CCC", 'C', ModItems.board_copper, 'T', ModItems.mp_thruster_15_balefire_large });
		
		//Missile fuselages
		GameRegistry.addRecipe(new ItemStack(ModItems.mp_fuselage_10_kerosene_insulation, 1), new Object[] { "CCC", "CTC", "CCC", 'C', ModItems.plate_polymer, 'T', ModItems.mp_fuselage_10_kerosene });
		GameRegistry.addRecipe(new ItemStack(ModItems.mp_fuselage_10_long_kerosene_insulation, 1), new Object[] { "CCC", "CTC", "CCC", 'C', ModItems.plate_polymer, 'T', ModItems.mp_fuselage_10_long_kerosene });
		GameRegistry.addRecipe(new ItemStack(ModItems.mp_fuselage_15_kerosene_insulation, 1), new Object[] { "CCC", "CTC", "CCC", 'C', ModItems.plate_polymer, 'T', ModItems.mp_fuselage_15_kerosene });
		GameRegistry.addRecipe(new ItemStack(ModItems.mp_fuselage_10_solid_insulation, 1), new Object[] { "CCC", "CTC", "CCC", 'C', ModItems.plate_polymer, 'T', ModItems.mp_fuselage_10_solid });
		GameRegistry.addRecipe(new ItemStack(ModItems.mp_fuselage_10_long_solid_insulation, 1), new Object[] { "CCC", "CTC", "CCC", 'C', ModItems.plate_polymer, 'T', ModItems.mp_fuselage_10_long_solid });
		GameRegistry.addRecipe(new ItemStack(ModItems.mp_fuselage_15_solid_insulation, 1), new Object[] { "CCC", "CTC", "CCC", 'C', ModItems.plate_polymer, 'T', ModItems.mp_fuselage_15_solid });
		GameRegistry.addRecipe(new ItemStack(ModItems.mp_fuselage_15_solid_desh, 1), new Object[] { "CCC", "CTC", "CCC", 'C', ModItems.ingot_desh, 'T', ModItems.mp_fuselage_15_solid });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.mp_fuselage_10_kerosene_metal, 1), new Object[] { "ICI", "CTC", "ICI", 'C', "plateSteel", 'I', "plateIron", 'T', ModItems.mp_fuselage_10_kerosene }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.mp_fuselage_10_long_kerosene_metal, 1), new Object[] { "ICI", "CTC", "ICI", 'C', "plateSteel", 'I', "plateIron", 'T', ModItems.mp_fuselage_10_long_kerosene }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.mp_fuselage_15_kerosene_metal, 1), new Object[] { "ICI", "CTC", "ICI", 'C', "plateSteel", 'I', "plateIron", 'T', ModItems.mp_fuselage_15_kerosene }));
		
		//Missile warheads
		GameRegistry.addRecipe(new ItemStack(ModItems.mp_warhead_15_boxcar, 1), new Object[] { "SNS", "CBC", "SFS", 'S', ModItems.ingot_starmetal, 'N', ModBlocks.det_nuke, 'C', ModItems.circuit_targeting_tier4, 'B', ModBlocks.boxcar, 'F', ModItems.tritium_deuterium_cake });
		
		//Missile chips
		GameRegistry.addRecipe(new ItemStack(ModItems.mp_chip_1, 1), new Object[] { "P", "C", "S", 'P', ModItems.plate_polymer, 'C', ModItems.circuit_targeting_tier1, 'S', ModBlocks.steel_scaffold });
		GameRegistry.addRecipe(new ItemStack(ModItems.mp_chip_2, 1), new Object[] { "P", "C", "S", 'P', ModItems.plate_polymer, 'C', ModItems.circuit_targeting_tier2, 'S', ModBlocks.steel_scaffold });
		GameRegistry.addRecipe(new ItemStack(ModItems.mp_chip_3, 1), new Object[] { "P", "C", "S", 'P', ModItems.plate_polymer, 'C', ModItems.circuit_targeting_tier3, 'S', ModBlocks.steel_scaffold });
		GameRegistry.addRecipe(new ItemStack(ModItems.mp_chip_4, 1), new Object[] { "P", "C", "S", 'P', ModItems.plate_polymer, 'C', ModItems.circuit_targeting_tier4, 'S', ModBlocks.steel_scaffold });
		GameRegistry.addRecipe(new ItemStack(ModItems.mp_chip_5, 1), new Object[] { "P", "C", "S", 'P', ModItems.plate_polymer, 'C', ModItems.circuit_targeting_tier5, 'S', ModBlocks.steel_scaffold });
		
		//Guns
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_rpg, 1), new Object[] { "SSW", " MW", 'S', ModItems.hull_small_steel, 'W', "plateIron", 'M', ModItems.mechanism_launcher_1 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_panzerschreck, 1), new Object[] { "SSS", " MW", 'S', ModItems.hull_small_steel, 'W', "plateCopper", 'M', ModItems.mechanism_launcher_1 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_karl, 1), new Object[] { "SSW", " MW", 'S', ModItems.hull_small_steel, 'W', "plateAdvanced", 'M', ModItems.mechanism_launcher_2 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_quadro, 1), new Object[] { "SSS", "SSS", "CM ", 'S', ModItems.hull_small_steel, 'C', ModItems.circuit_targeting_tier3, 'M', ModItems.mechanism_launcher_2 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_hk69, 1), new Object[] { "SSI", " MB", 'S', ModItems.hull_small_steel, 'I', "ingotIron", 'M', ModItems.mechanism_launcher_1, 'B', ModItems.bolt_tungsten }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_stinger, 1), new Object[] { "SSW", "CMW", 'S', "plateSteel", 'W', "plateTitanium", 'C', ModItems.circuit_red_copper, 'M', ModItems.mechanism_launcher_2 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_stinger_ammo, 4), new Object[] { "SS ", "STI", " IR", 'S', "plateSteel", 'T', Item.getItemFromBlock(Blocks.tnt), 'I', "plateAluminum", 'R', "dustRedstone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_revolver, 1), new Object[] { "SSM", " RW", 'S', "plateSteel", 'W', "plankWood", 'R', ModItems.wire_aluminium, 'M', ModItems.mechanism_revolver_1 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_revolver_saturnite, 1), new Object[] { "SSM", " RW", 'S', "plateSaturnite", 'W', "plankWood", 'R', ModItems.wire_tungsten, 'M', ModItems.mechanism_revolver_2 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_revolver_iron, 1), new Object[] { "SSM", " RW", 'S', "plateIron", 'W', "plankWood", 'R', ModItems.wire_aluminium, 'M', ModItems.mechanism_revolver_1 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_revolver_gold, 1), new Object[] { "SSM", " RW", 'S', "plateGold", 'W', "ingotGold", 'R', ModItems.wire_gold, 'M', ModItems.mechanism_revolver_1 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_revolver_lead, 1), new Object[] { "SSM", " RW", 'S', "plateLead", 'W', "ingotTungsten", 'R', ModItems.wire_tungsten, 'M', ModItems.mechanism_revolver_2 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_revolver_schrabidium, 1), new Object[] { "SSM", " RW", 'S', ModBlocks.block_schrabidium, 'W', "ingotTungsten", 'R', ModItems.wire_schrabidium, 'M', ModItems.mechanism_special }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_revolver_pip, 1), new Object[] { " G ", "SSP", " TI", 'G', "paneGlass", 'S', "plateSteel", 'P', ModItems.mechanism_revolver_2, 'T', ModItems.wire_tungsten, 'I', "ingotPolymer" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_revolver_nopip, 1), new Object[] { "SSP", " TI", 'S', "plateSteel", 'P', ModItems.mechanism_revolver_2, 'T', ModItems.wire_tungsten, 'I', "ingotPolymer" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_revolver_blackjack, 1), new Object[] { "SSP", " TI", 'S', "plateSteel", 'P', ModItems.mechanism_revolver_2, 'T', ModItems.wire_tungsten, 'I', "plankWood" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_revolver_silver, 1), new Object[] { "SSP", " TI", 'S', "plateAluminum", 'P', ModItems.mechanism_revolver_2, 'T', ModItems.wire_tungsten, 'I', "plankWood" }));
		GameRegistry.addRecipe(new ItemStack(ModItems.gun_revolver_red, 1), new Object[] { "R ", " B", 'R', ModItems.key_red, 'B', ModItems.gun_revolver_blackjack });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_deagle, 1), new Object[] { "PPM", " BI", 'P', "plateSteel", 'B', ModItems.bolt_tungsten, 'I', "ingotPolymer", 'M', ModItems.mechanism_rifle_1 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_revolver_cursed, 1), new Object[] { "TTM", "SRI", 'S', "plateSteel", 'I', "ingotSteel", 'R', ModItems.wire_red_copper, 'T', "plateTitanium", 'M', ModItems.mechanism_revolver_2 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_revolver_nightmare, 1), new Object[] { "SEM", " RW", 'S', "plateSteel", 'W', "plankWood", 'R', ModItems.wire_aluminium, 'E', ModItems.powder_power, 'M', ModItems.mechanism_revolver_2 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_revolver_nightmare2, 1), new Object[] { "SSM", "RRW", 'S', "plateDenseLead", 'W', "ingotTungsten", 'R', ModItems.wire_gold, 'M', ModItems.mechanism_special }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_fatman, 1), new Object[] { "SSI", "IIM", "WPH", 'S', "plateSteel", 'I', "ingotSteel", 'W', ModItems.wire_aluminium, 'H', ModItems.hull_small_steel, 'P', Item.getItemFromBlock(Blocks.piston), 'M', ModItems.mechanism_launcher_2 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_mirv, 1), new Object[] { "LLL", "WFW", "SSS", 'S', "plateSteel", 'L', "plateLead", 'W', ModItems.wire_gold, 'F', ModItems.gun_fatman }));
		GameRegistry.addRecipe(new ItemStack(ModItems.gun_proto, 1), new Object[] { "LLL", "WFW", "SSS", 'S', ModItems.plate_polymer, 'L', ModItems.plate_desh, 'W', ModItems.wire_tungsten, 'F', ModItems.gun_fatman });
		GameRegistry.addRecipe(new ItemStack(ModItems.gun_bf_ammo, 1), new Object[] { " S ", "EBE", " S ", 'S', ModItems.hull_small_steel, 'E', ModItems.powder_power, 'B', ModItems.egg_balefire_shard });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_mp40, 1), new Object[] { "IIM", " SW", " S ", 'S', "plateSteel", 'I', "ingotSteel", 'W', "plankWood", 'M', ModItems.mechanism_rifle_2 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_thompson, 1), new Object[] { "IIM", " SW", " S ", 'S', "plateIron", 'I', "plateSteel", 'W', "plankWood", 'M', ModItems.mechanism_rifle_2 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_flechette, 1), new Object[] { "PPM", "TIS", "G  ", 'P', "plateSteel", 'M', ModItems.mechanism_rifle_2, 'T', ModItems.hull_small_steel, 'I', "ingotSteel", 'S', ModItems.ingot_polymer, 'G', ModItems.mechanism_launcher_1 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_uboinik, 1), new Object[] { "IIM", "SPW", 'P', "plateSteel", 'I', "ingotSteel", 'W', "plankWood", 'S', Items.stick, 'M', ModItems.mechanism_revolver_2 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_supershotgun, 1), new Object[] { "BBM", "BBM", "AWL", 'B', ModItems.hull_small_steel, 'M', ModItems.mechanism_rifle_2, 'A', ModItems.plate_dalekanium, 'W', ModItems.wire_gold, 'L', "logWood" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_ks23, 1), new Object[] { "PPM", "SWL", 'P', "plateSteel", 'M', ModItems.mechanism_rifle_1, 'S', Items.stick, 'W', ModItems.wire_tungsten, 'L', "logWood" }));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.gun_sauer, 1), new Object[] { ModItems.ducttape, ModItems.gun_ks23, Blocks.lever, ModItems.gun_ks23 });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_xvl1456, 1), new Object[] { "PBB", "ACC", "PRY", 'P', "plateSteel", 'R', ModItems.redcoil_capacitor, 'A', ModItems.coil_advanced_alloy, 'B', ModItems.battery_generic, 'C', ModItems.coil_advanced_torus, 'Y', ModItems.mechanism_special }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_xvl1456_ammo, 64), new Object[] { "SSS", "SRS", "SSS", 'S', "plateSteel", 'R', ModItems.rod_quad_uranium_fuel_depleted }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_xvl1456_ammo, 32), new Object[] { " S ", "SRS", " S ", 'S', "plateSteel", 'R', ModItems.rod_dual_uranium_fuel_depleted }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_xvl1456_ammo, 16), new Object[] { " S ", " R ", " S ", 'S', "plateSteel", 'R', ModItems.rod_uranium_fuel_depleted }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_xvl1456_ammo, 16), new Object[] { "SRS", 'S', "plateSteel", 'R', ModItems.rod_uranium_fuel_depleted }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_xvl1456_ammo, 16), new Object[] { " S ", " R ", " S ", 'S', "plateSteel", 'R', "U238" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_xvl1456_ammo, 16), new Object[] { "SRS", 'S', "plateSteel", 'R', "U238" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_osipr, 1), new Object[] { "CCT", "WWI", "MCC", 'C', ModItems.plate_combine_steel, 'T', "ingotTungsten", 'W', ModItems.wire_magnetized_tungsten, 'I', ModItems.mechanism_rifle_2, 'M', ModItems.coil_magnetized_tungsten }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_immolator, 1), new Object[] { "WCC", "PMT", "WAA", 'W', ModItems.wire_gold, 'C', "plateCopper", 'P', "plateAdvanced", 'M', ModItems.mechanism_launcher_1, 'T', ModItems.tank_steel, 'A', "plateSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_immolator_ammo, 16), new Object[] { "SPS", "PCP", "SPS", 'S', "plateSteel", 'C', "dustCoal", 'P', ModItems.powder_fire }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_immolator_ammo, 16), new Object[] { " F ", "SFS", " F ", 'S', "plateSteel", 'F', ModItems.canister_fuel }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_immolator_ammo, 24), new Object[] { " F ", "SFS", " F ", 'S', "plateSteel", 'F', ModItems.canister_napalm }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_flamer, 1), new Object[] { "WPP", "SCT", "WMI", 'W', ModItems.wire_gold, 'P', ModItems.pipes_steel, 'S', ModItems.hull_small_steel, 'C', ModItems.coil_tungsten, 'T', ModItems.tank_steel, 'M', ModItems.mechanism_launcher_1, 'I', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_cryolator, 1), new Object[] { "SSS", "IWL", "LMI", 'S', "plateSteel", 'I', "plateIron", 'L', Items.leather, 'M', ModItems.mechanism_launcher_1, 'W', ModItems.wire_aluminium }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_cryolator_ammo, 16), new Object[] { "SPS", "PCP", "SPS", 'S', "plateSteel", 'C', "dustSaltpeter", 'P', Items.snowball }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_cryolator_ammo, 16), new Object[] { " F ", "SFS", " F ", 'S', "plateSteel", 'F', ModItems.powder_ice }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_mp, 1), new Object[] { "EEE", "SSM", "III", 'E', ModItems.ingot_euphemium, 'S', "plateSteel", 'I', "ingotSteel", 'M', ModItems.mechanism_rifle_2 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_emp, 1), new Object[] { "CPG", "CMF", "CPI", 'C', ModItems.coil_copper, 'P', "plateLead", 'G', ModItems.circuit_gold, 'M', ModItems.magnetron, 'I', "ingotTungsten", 'F', ModItems.mechanism_special }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_emp_ammo, 8), new Object[] { "IGI", "IPI", "IPI", 'G', "plateGold", 'I', "plateIron", 'P', ModItems.powder_power }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_jack, 1), new Object[] { "WW ", "TSD", " TT", 'W', "ingotWeidanium", 'T', ModItems.toothpicks, 'S', ModItems.gun_uboinik, 'D', ModItems.ducttape }));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.gun_jack_ammo, 1), new Object[] { ModItems.ammo_12gauge, ModItems.ammo_12gauge, ModItems.ammo_12gauge, ModItems.ammo_12gauge });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_euthanasia, 1), new Object[] { "TDT", "AAS", " T ", 'A', "ingotAustralium", 'T', ModItems.toothpicks, 'S', ModItems.gun_mp40, 'D', ModItems.ducttape }));
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.gun_euthanasia_ammo, 12), new Object[] { "P", "S", "N", 'P', ModItems.powder_poison, 'N', ModItems.niter, 'S', ModItems.syringe_metal_empty });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_spark, 1), new Object[] { "TTD", "AAS", "  T", 'A', "ingotDaffergon", 'T', ModItems.toothpicks, 'S', ModItems.gun_rpg, 'D', ModItems.ducttape }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_spark_ammo, 4), new Object[] { "PCP", "DDD", "PCP", 'P', "plateLead", 'C', ModItems.coil_gold, 'D', ModItems.powder_power }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_skystinger, 1), new Object[] { "TTT", "AAS", " D ", 'A', "ingotUnobtainium", 'T', ModItems.toothpicks, 'S', ModItems.gun_stinger, 'D', ModItems.ducttape }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_hp, 1), new Object[] { "TDT", "ASA", " T ", 'A', "ingotReiium", 'T', ModItems.toothpicks, 'S', ModItems.gun_xvl1456, 'D', ModItems.ducttape }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_hp_ammo, 8), new Object[] { " R ", "BSK", " Y ", 'S', "plateSteel", 'K', new ItemStack(Items.dye, 1, 0), 'R', new ItemStack(Items.dye, 1, 1), 'B', new ItemStack(Items.dye, 1, 4), 'Y', new ItemStack(Items.dye, 1, 11) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_defabricator_ammo, 16), new Object[] { "PCP", "DDD", "PCP", 'P', "plateSteel", 'C', ModItems.coil_copper, 'D', "dustLithium" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_lever_action, 1), new Object[] { "PPI", "SWD", 'P', "plateIron", 'I', ModItems.mechanism_rifle_1, 'S', Items.stick, 'D', "plankWood", 'W', ModItems.wire_aluminium }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_lever_action_dark, 1), new Object[] { "PPI", "SWD", 'P', "plateSteel", 'I', ModItems.mechanism_rifle_1, 'S', Items.stick, 'D', "plankWood", 'W', ModItems.wire_aluminium }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_bolt_action, 1), new Object[] { "PPI", "SWD", 'P', "plateSteel", 'I', ModItems.mechanism_rifle_1, 'S', Items.stick, 'D', "plankWood", 'W', ModItems.wire_copper }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_bolt_action_green, 1), new Object[] { "PPI", "SWD", 'P', "plateIron", 'I', ModItems.mechanism_rifle_1, 'S', Items.stick, 'D', "plankWood", 'W', ModItems.wire_copper }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_bolt_action_saturnite, 1), new Object[] { "PPI", "SWD", 'P', "plateSaturnite", 'I', ModItems.mechanism_rifle_1, 'S', Items.stick, 'D', "plankWood", 'W', ModItems.wire_tungsten }));
		GameRegistry.addRecipe(new ItemStack(ModItems.gun_b92), new Object[] { "DDD", "SSC", "  R", 'D', ModItems.plate_dineutronium, 'S', ModItems.ingot_starmetal, 'S', ModItems.circuit_targeting_tier6, 'R', ModItems.gun_revolver_schrabidium });
		GameRegistry.addRecipe(new ItemStack(ModItems.gun_b93), new Object[] { "PCE", "SEB", "PCE", 'P', ModItems.plate_dineutronium, 'C', ModItems.weaponized_starblaster_cell, 'E', ModItems.component_emitter, 'B', ModItems.gun_b92, 'S', ModItems.singularity_spark });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_b92_ammo, 1), new Object[] { "PSP", "ESE", "PSP", 'P', "plateSteel", 'S', ModItems.ingot_starmetal, 'E', ModItems.powder_spark_mix }));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.weaponized_starblaster_cell, 1), new Object[] { new ItemStack(ModItems.fluid_tank_full, 1, FluidType.ACID.getID()), GunB92Cell.getFullCell(), ModItems.wire_copper });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_uzi, 1), new Object[] { "SMS", " PB", " P ", 'S', "ingotSteel", 'M', ModItems.mechanism_rifle_2, 'P', "plateSteel", 'B', ModItems.bolt_dura_steel }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_uzi_silencer, 1), new Object[] { "P  ", " P ", "  U", 'P', "ingotPolymer", 'U', ModItems.gun_uzi }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_uzi_saturnite, 1), new Object[] { "SMS", " PB", " P ", 'S', "ingotSaturnite", 'M', ModItems.mechanism_rifle_2, 'P', "plateSaturnite", 'B', ModItems.bolt_tungsten }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_uzi_saturnite_silencer, 1), new Object[] { "P  ", " P ", "  U", 'P', "ingotPolymer", 'U', ModItems.gun_uzi_saturnite }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_bolter, 1), new Object[] { "SSM", "PIP", " I ", 'S', ModItems.plate_saturnite, 'I', ModItems.ingot_saturnite, 'M', ModItems.mechanism_special, 'P', "ingotPolymer" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_calamity, 1), new Object[] { " PI", "BBM", " PI", 'P', "plateIron", 'B', ModItems.pipes_steel, 'M', ModItems.mechanism_rifle_1, 'I', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_calamity_dual, 1), new Object[] { "BBM", " PI", "BBM", 'P', "plateIron", 'B', ModItems.pipes_steel, 'M', ModItems.mechanism_rifle_1, 'I', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_minigun, 1), new Object[] { "PIB", "PCM", "PIB", 'P', ModItems.pipes_steel, 'B', "blockSteel", 'I', "ingotPolymer", 'C', ModItems.mechanism_rifle_2, 'M', ModItems.motor }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_avenger, 1), new Object[] { "PIB", "PCM", "PIB", 'P', ModItems.pipes_steel, 'B', "blockBeryllium", 'I', "ingotDesh", 'C', ModItems.mechanism_rifle_2, 'M', ModItems.motor }));
		GameRegistry.addRecipe(new ItemStack(ModItems.gun_lacunae, 1), new Object[] { "TIT", "ILI", "PRP", 'T', ModItems.syringe_taint, 'I', ModItems.ingot_starmetal, 'L', ModItems.gun_minigun, 'P', ModItems.pellet_rtg, 'R', ModBlocks.machine_rtg_grey });
		GameRegistry.addRecipe(new ItemStack(ModItems.gun_mymy, 1), new Object[] { "PP ", " WP", 'P', ModItems.plate_polymer, 'W', ModItems.wire_aluminium });
		//GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gun_darter, 1), new Object[] { "SST", "  P", 'S', "plateSteel", 'T', ModItems.gas_empty, 'P', ModItems.ingot_polymer }));
		
		//Legacy ammo recycling
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ammo_44, 1), new Object[] { ModItems.gun_revolver_nopip_ammo });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ammo_44_pip, 1), new Object[] { ModItems.gun_revolver_pip_ammo });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ammo_50bmg, 1), new Object[] { ModItems.gun_calamity_ammo });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ammo_5mm, 1), new Object[] { ModItems.gun_lacunae_ammo });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ammo_rocket, 1), new Object[] { ModItems.gun_rpg_ammo });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ammo_9mm, 1), new Object[] { ModItems.gun_mp40_ammo });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ammo_22lr, 1), new Object[] { ModItems.gun_uzi_ammo });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ammo_12gauge, 1), new Object[] { ModItems.gun_uboinik_ammo });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ammo_20gauge, 1), new Object[] { ModItems.gun_lever_action_ammo });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ammo_20gauge_slug, 1), new Object[] { ModItems.gun_bolt_action_ammo });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ammo_nuke_high, 1), new Object[] { ModItems.gun_fatman_ammo });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ammo_mirv_high, 1), new Object[] { ModItems.gun_mirv_ammo });
		
		//Ammo assemblies
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.pellet_flechette, 1), new Object[] { " L ", " L ", "LLL", 'L', "nuggetLead" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.pellet_claws, 1), new Object[] { " X ", "X X", " XX", 'X', "plateSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.assembly_iron, 24), new Object[] { " I", "GC", " P", 'I', "ingotIron", 'G', ModItems.cordite, 'C', ModItems.casing_357, 'P', ModItems.primer_357 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.assembly_iron, 24), new Object[] { " I", "GC", " P", 'I', "ingotIron", 'G', ModItems.ballistite, 'C', ModItems.casing_357, 'P', ModItems.primer_357 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.assembly_steel, 24), new Object[] { " I", "GC", " P", 'I', "ingotLead", 'G', ModItems.cordite, 'C', ModItems.casing_357, 'P', ModItems.primer_357 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.assembly_steel, 24), new Object[] { " I", "GC", " P", 'I', "ingotLead", 'G', ModItems.ballistite, 'C', ModItems.casing_357, 'P', ModItems.primer_357 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.assembly_lead, 24), new Object[] { " I", "GC", " P", 'I', ModItems.ingot_u235, 'G', ModItems.cordite, 'C', "paneGlassColorless", 'P', ModItems.primer_357 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.assembly_lead, 24), new Object[] { " I", "GC", " P", 'I', ModItems.ingot_pu239, 'G', ModItems.cordite, 'C', "paneGlassColorless", 'P', ModItems.primer_357 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.assembly_lead, 24), new Object[] { " I", "GC", " P", 'I', ModItems.trinitite, 'G', ModItems.cordite, 'C', "paneGlassColorless", 'P', ModItems.primer_357 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.assembly_lead, 24), new Object[] { " I", "GC", " P", 'I', ModItems.nuclear_waste_tiny, 'G', ModItems.cordite, 'C', "paneGlassColorless", 'P', ModItems.primer_357 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.assembly_gold, 24), new Object[] { " I", "GC", " P", 'I', "ingotGold", 'G', ModItems.cordite, 'C', ModItems.casing_357, 'P', ModItems.primer_357 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.assembly_schrabidium, 6), new Object[] { " I ", "GCN", " P ", 'I', "ingotSchrabidium", 'G', ModItems.cordite, 'C', ModItems.casing_357, 'P', ModItems.primer_357, 'N', Items.nether_star }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.assembly_nightmare, 24), new Object[] { " I", "GC", " P", 'I', "ingotTungsten", 'G', ModItems.cordite, 'C', ModItems.casing_357, 'P', ModItems.primer_357 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.assembly_desh, 24), new Object[] { " I", "GC", " P", 'I', "ingotDesh", 'G', ModItems.cordite, 'C', ModItems.casing_357, 'P', ModItems.primer_357 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.assembly_smg, 32), new Object[] { " I", "GC", " P", 'I', "ingotLead", 'G', ModItems.cordite, 'C', ModItems.casing_9, 'P', ModItems.primer_9 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.assembly_smg, 32), new Object[] { " I", "GC", " P", 'I', "ingotLead", 'G', ModItems.ballistite, 'C', ModItems.casing_9, 'P', ModItems.primer_9 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.assembly_556, 32), new Object[] { " I", "GC", " P", 'I', "ingotSteel", 'G', ModItems.cordite, 'C', ModItems.casing_9, 'P', ModItems.primer_9 }));
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_556_k, 32), new Object[] { "G", "C", "P", 'G', Items.gunpowder, 'C', ModItems.casing_9, 'P', ModItems.primer_9 });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_556_k, 32), new Object[] { "G", "C", "P", 'G', ModItems.ballistite, 'C', ModItems.casing_9, 'P', ModItems.primer_9 });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.assembly_uzi, 32), new Object[] { " I", "GC", " P", 'I', "ingotIron", 'G', ModItems.cordite, 'C', ModItems.casing_9, 'P', ModItems.primer_9 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.assembly_uzi, 32), new Object[] { " I", "GC", " P", 'I', "ingotIron", 'G', ModItems.ballistite, 'C', ModItems.casing_9, 'P', ModItems.primer_9 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.assembly_lacunae, 32), new Object[] { " I", "GC", " P", 'I', "ingotCopper", 'G', ModItems.cordite, 'C', ModItems.casing_9, 'P', ModItems.primer_9 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.assembly_nopip, 24), new Object[] { " I", "GC", " P", 'I', "ingotLead", 'G', ModItems.cordite, 'C', ModItems.casing_44, 'P', ModItems.primer_44 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.assembly_nopip, 24), new Object[] { " I", "GC", " P", 'I', "ingotLead", 'G', ModItems.ballistite, 'C', ModItems.casing_44, 'P', ModItems.primer_44 }));
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_12gauge, 12), new Object[] { " I ", "GCL", " P ", 'I', ModItems.pellet_buckshot, 'G', ModItems.cordite, 'C', ModItems.casing_buckshot, 'P', ModItems.primer_buckshot, 'L', ModItems.plate_polymer });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_12gauge, 12), new Object[] { " I ", "GCL", " P ", 'I', ModItems.pellet_buckshot, 'G', ModItems.ballistite, 'C', ModItems.casing_buckshot, 'P', ModItems.primer_buckshot, 'L', ModItems.plate_polymer });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_4gauge, 12), new Object[] { " I ", "GCL", " P ", 'I', ModItems.pellet_buckshot, 'G', ModItems.cordite, 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'L', ModItems.plate_polymer });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_4gauge, 12), new Object[] { " I ", "GCL", " P ", 'I', ModItems.pellet_buckshot, 'G', ModItems.ballistite, 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'L', ModItems.plate_polymer });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ammo_4gauge_slug, 12), new Object[] { " I ", "GCL", " P ", 'I', "ingotLead", 'G', ModItems.cordite, 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'L', ModItems.plate_polymer }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ammo_4gauge_slug, 12), new Object[] { " I ", "GCL", " P ", 'I', "ingotLead", 'G', ModItems.ballistite, 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'L', ModItems.plate_polymer }));
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_4gauge_flechette, 12), new Object[] { " I ", "GCL", " P ", 'I', ModItems.pellet_flechette, 'G', ModItems.cordite, 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'L', ModItems.plate_polymer });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_4gauge_flechette, 12), new Object[] { " I ", "GCL", " P ", 'I', ModItems.pellet_flechette, 'G', ModItems.ballistite, 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'L', ModItems.plate_polymer });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_4gauge_explosive, 4), new Object[] { " I ", "GCL", " P ", 'I', Blocks.tnt, 'G', ModItems.cordite, 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'L', ModItems.plate_polymer });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_4gauge_explosive, 4), new Object[] { " I ", "GCL", " P ", 'I', Blocks.tnt, 'G', ModItems.ballistite, 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'L', ModItems.plate_polymer });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_4gauge_explosive, 6), new Object[] { " I ", "GCL", " P ", 'I', ModItems.ingot_semtex, 'G', ModItems.cordite, 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'L', ModItems.plate_polymer });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_4gauge_explosive, 6), new Object[] { " I ", "GCL", " P ", 'I', ModItems.ingot_semtex, 'G', ModItems.ballistite, 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'L', ModItems.plate_polymer });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_4gauge_semtex, 4), new Object[] { " I ", "GCL", " P ", 'I', ModBlocks.det_miner, 'G', ModItems.cordite, 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'L', ModItems.plate_polymer });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_4gauge_semtex, 4), new Object[] { " I ", "GCL", " P ", 'I', ModBlocks.det_miner, 'G', ModItems.ballistite, 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'L', ModItems.plate_polymer });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ammo_20gauge, 12), new Object[] { " I ", "GCL", " P ", 'I', ModItems.pellet_buckshot, 'G', ModItems.cordite, 'C', ModItems.casing_buckshot, 'P', ModItems.primer_buckshot, 'L', "plateCopper" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ammo_20gauge, 12), new Object[] { " I ", "GCL", " P ", 'I', ModItems.pellet_buckshot, 'G', ModItems.ballistite, 'C', ModItems.casing_buckshot, 'P', ModItems.primer_buckshot, 'L', "plateCopper" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ammo_20gauge_slug, 12), new Object[] { " I ", "GCL", " P ", 'I', "ingotLead", 'G', ModItems.cordite, 'C', ModItems.casing_buckshot, 'P', ModItems.primer_buckshot, 'L', "plateCopper" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ammo_20gauge_slug, 12), new Object[] { " I ", "GCL", " P ", 'I', "ingotLead", 'G', ModItems.ballistite, 'C', ModItems.casing_buckshot, 'P', ModItems.primer_buckshot, 'L', "plateCopper" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ammo_20gauge_explosive, 12), new Object[] { " I ", "GCL", " P ", 'I', ModItems.pellet_cluster, 'G', ModItems.cordite, 'C', ModItems.casing_buckshot, 'P', ModItems.primer_buckshot, 'L', "plateCopper" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ammo_20gauge_explosive, 12), new Object[] { " I ", "GCL", " P ", 'I', ModItems.pellet_cluster, 'G', ModItems.ballistite, 'C', ModItems.casing_buckshot, 'P', ModItems.primer_buckshot, 'L', "plateCopper" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ammo_20gauge_flechette, 12), new Object[] { " I ", "GCL", " P ", 'I', ModItems.pellet_flechette, 'G', ModItems.cordite, 'C', ModItems.casing_buckshot, 'P', ModItems.primer_buckshot, 'L', "plateCopper" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ammo_20gauge_flechette, 12), new Object[] { " I ", "GCL", " P ", 'I', ModItems.pellet_flechette, 'G', ModItems.ballistite, 'C', ModItems.casing_buckshot, 'P', ModItems.primer_buckshot, 'L', "plateCopper" }));
		GameRegistry.addRecipe(new ItemStack(ModItems.gun_revolver_nightmare2_ammo, 6), new Object[] { "I", "C", "P", 'I', ModItems.powder_power, 'C', ModItems.casing_buckshot, 'P', ModItems.primer_buckshot });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.assembly_calamity, 12), new Object[] { " I ", "GCG", " P ", 'I', "ingotLead", 'G', ModItems.cordite, 'C', ModItems.casing_50, 'P', ModItems.primer_50 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.assembly_actionexpress, 12), new Object[] { " I", "GC", " P", 'I', "ingotLead", 'G', ModItems.cordite, 'C', ModItems.casing_50, 'P', ModItems.primer_50 }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.assembly_nuke, 1), new Object[] { " WP", "SEP", " WP", 'W', ModItems.wire_aluminium, 'P', "plateSteel", 'S', ModItems.hull_small_steel, 'E', ModItems.ingot_semtex }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ammo_dart, 16), new Object[] { "IPI", "ICI", "IPI", 'I', ModItems.plate_polymer, 'P', "plateIron", 'C', new ItemStack(ModItems.fluid_tank_full, 1, FluidType.WATZ.ordinal()) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ammo_dart_nerf, 16), new Object[] { "I", "I", 'I', ModItems.plate_polymer }));

		//Ammo types
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_12gauge_incendiary, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_12gauge, 'A', ModItems.powder_fire });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_12gauge_shrapnel, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_12gauge, 'A', ModBlocks.gravel_obsidian });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_12gauge_du, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_12gauge, 'A', ModItems.ingot_u238 });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_12gauge_sleek, 64), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_12gauge, 'A', ModItems.coin_maskman });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_20gauge_incendiary, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_20gauge, 'A', ModItems.powder_fire });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_20gauge_shrapnel, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_20gauge, 'A', ModBlocks.gravel_obsidian });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_20gauge_caustic, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_20gauge, 'A', ModItems.powder_poison });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ammo_20gauge_shock, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_20gauge, 'A', "dustDiamond" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ammo_20gauge_wither, 4), new Object[] { "BCB", "CAC", "BCB", 'B', ModItems.ammo_20gauge, 'A', Blocks.soul_sand, 'C', "dustCoal" }));
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_20gauge_sleek, 64), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_20gauge, 'A', ModItems.coin_maskman });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_4gauge_flechette_phosphorus, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_4gauge_flechette, 'A', ModItems.ingot_phosphorus });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_4gauge_balefire, 4), new Object[] { " B ", "BAB", " B ", 'B', ModItems.ammo_4gauge_explosive, 'A', ModItems.egg_balefire_shard });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_4gauge_kampf, 2), new Object[] { "G", "R", 'G', ModItems.ammo_rocket, 'R', ModItems.ammo_4gauge_explosive });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_4gauge_canister, 4), new Object[] {  " B ", "BAB", " B ", 'B', ModItems.ammo_4gauge_kampf, 'A', ModItems.pellet_canister });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_4gauge_claw, 4), new Object[] {  " B ", "BAB", " B ", 'B', ModItems.ammo_4gauge, 'A', ModItems.pellet_claws });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_4gauge_vampire, 4), new Object[] {  "ABA", "BAB", "ABA", 'B', ModItems.ammo_4gauge, 'A', ModItems.toothpicks });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_4gauge_void, 4), new Object[] {  " B ", "BAB", " B ", 'B', ModItems.ammo_4gauge, 'A', ModItems.pellet_charged });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_4gauge_sleek, 64), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_4gauge, 'A', ModItems.coin_maskman });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_44_ap, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_44, 'A', ModItems.ingot_dura_steel });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_44_du, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_44, 'A', ModItems.ingot_u238 });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_44_phosphorus, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_44, 'A', ModItems.ingot_phosphorus });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_44_star, 4), new Object[] { " B ", "BAB", " B ", 'B', ModItems.ammo_44_du, 'A', ModItems.ingot_starmetal });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_44_chlorophyte, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_44, 'A', ModItems.pellet_chlorophyte });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_5mm_explosive, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_5mm, 'A', ModItems.ingot_semtex });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_5mm_du, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_5mm, 'A', ModItems.ingot_u238 });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_5mm_star, 4), new Object[] { " B ", "BAB", " B ", 'B', ModItems.ammo_5mm_du, 'A', ModItems.ingot_starmetal });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_5mm_chlorophyte, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_5mm, 'A', ModItems.pellet_chlorophyte });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_9mm_ap, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_9mm, 'A', ModItems.ingot_dura_steel });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_9mm_du, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_9mm, 'A', ModItems.ingot_u238 });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_9mm_chlorophyte, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_9mm, 'A', ModItems.pellet_chlorophyte });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_22lr_ap, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_22lr, 'A', ModItems.ingot_dura_steel });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_22lr_chlorophyte, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_22lr, 'A', ModItems.pellet_chlorophyte });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_50bmg_incendiary, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_50bmg, 'A', ModItems.powder_fire });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_50bmg_phosphorus, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_50bmg, 'A', ModItems.ingot_phosphorus });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_50bmg_explosive, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_50bmg, 'A', ModItems.ingot_semtex });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_50bmg_ap, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_50bmg, 'A', ModItems.ingot_dura_steel });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_50bmg_du, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_50bmg, 'A', ModItems.ingot_u238 });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_50bmg_star, 4), new Object[] { " B ", "BAB", " B ", 'B', ModItems.ammo_50bmg_du, 'A', ModItems.ingot_starmetal });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_50bmg_chlorophyte, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_50bmg, 'A', ModItems.pellet_chlorophyte });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_50bmg_sleek, 64), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_50bmg, 'A', ModItems.coin_maskman });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_50ae_ap, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_50ae, 'A', ModItems.ingot_dura_steel });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_50ae_du, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_50ae, 'A', ModItems.ingot_u238 });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_50ae_star, 4), new Object[] { " B ", "BAB", " B ", 'B', ModItems.ammo_50ae_du, 'A', ModItems.ingot_starmetal });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_50ae_chlorophyte, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_50ae, 'A', ModItems.pellet_chlorophyte });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_556_phosphorus, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_556, 'A', ModItems.ingot_phosphorus });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_556_ap, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_556, 'A', ModItems.ingot_dura_steel });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_556_du, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_556, 'A', ModItems.ingot_u238 });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_556_star, 4), new Object[] { " B ", "BAB", " B ", 'B', ModItems.ammo_556_du, 'A', ModItems.ingot_starmetal });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_556_chlorophyte, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_556, 'A', ModItems.pellet_chlorophyte });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_556_sleek, 64), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_556, 'A', ModItems.coin_maskman });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_556_tracer, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_556, 'A', Items.redstone });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_556_flechette, 4), new Object[] { " B ", "BAB", " B ", 'B', ModItems.ammo_556, 'A', ModItems.pellet_flechette });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_556_flechette_incendiary, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_556_flechette, 'A', ModItems.powder_fire });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_556_flechette_phosphorus, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_556_flechette, 'A', ModItems.ingot_phosphorus });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_556_flechette_du, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_556_flechette, 'A', ModItems.ingot_u238 });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_556_flechette_sleek, 64), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_556_flechette, 'A', ModItems.coin_maskman });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_556_flechette_chlorophyte, 8), new Object[] { "BBB", "BAB", "BBB", 'B', ModItems.ammo_556_flechette, 'A', ModItems.pellet_chlorophyte });
		
		//Folly shells
		GameRegistry.addRecipe(new ItemStack(ModItems.folly_bullet, 1), new Object[] { " S ", "STS", "SMS", 'S', ModItems.ingot_starmetal, 'T', ModItems.powder_magic, 'M', ModBlocks.block_meteor });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.folly_bullet_nuclear, 1), new Object[] { " N ", "UTU", "UTU", 'N', ModItems.ammo_nuke, 'U', "ingotIron", 'T', "blockTungsten" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.folly_bullet_du, 1), new Object[] { " U ", "UDU", "UTU", 'U', "blockUranium238", 'D', "blockDesh", 'T', "blockTungsten" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.folly_shell, 1), new Object[] { "IPI", "IPI", "IMI", 'I', "ingotIron", 'P', "plateIron", 'M', ModItems.primer_50 }));
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_folly, 1), new Object[] { " B ", "MEM", " S ", 'B', ModItems.folly_bullet, 'M', ModItems.powder_magic, 'E', ModItems.powder_power, 'S', ModItems.folly_shell });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_folly_nuclear, 1), new Object[] { " B ", "EEE", " S ", 'B', ModItems.folly_bullet_nuclear, 'E', ModBlocks.det_charge, 'S', ModItems.folly_shell });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_folly_du, 1), new Object[] { " B ", "EEE", " S ", 'B', ModItems.folly_bullet_du, 'E', ModBlocks.det_charge, 'S', ModItems.folly_shell });

		//Rockets
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_rocket, 1), new Object[] { " T ", "GCG", " P ", 'T', Blocks.tnt, 'G', ModItems.rocket_fuel, 'C', ModItems.casing_50, 'P', ModItems.primer_50 });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_rocket, 2), new Object[] { " T ", "GCG", " P ", 'T', ModItems.ingot_semtex, 'G', ModItems.rocket_fuel, 'C', ModItems.casing_50, 'P', ModItems.primer_50 });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_rocket_he, 1), new Object[] { "G", "R", 'G', ModItems.ingot_semtex, 'R', ModItems.ammo_rocket });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_rocket_incendiary, 1), new Object[] { "G", "R", 'G', ModItems.powder_fire, 'R', ModItems.ammo_rocket });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_rocket_phosphorus, 1), new Object[] { "G", "R", 'G', ModItems.ingot_phosphorus, 'R', ModItems.ammo_rocket });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ammo_rocket_emp, 1), new Object[] { "G", "R", 'G', "dustDiamond", 'R', ModItems.ammo_rocket }));
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_rocket_shrapnel, 1), new Object[] { "G", "R", 'G', ModItems.pellet_buckshot, 'R', ModItems.ammo_rocket });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_rocket_glare, 1), new Object[] { "GGG", "GRG", "GGG", 'G', Items.redstone, 'R', ModItems.ammo_rocket });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_rocket_toxic, 1), new Object[] { "G", "R", 'G', ModItems.pellet_gas, 'R', ModItems.ammo_rocket });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_rocket_canister, 1), new Object[] { "G", "R", 'G', ModItems.pellet_canister, 'R', ModItems.ammo_rocket });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ammo_rocket_nuclear, 1), new Object[] { " P ", "NRN", " P ", 'P', ModItems.nugget_pu239, 'N', "plateDenseLead", 'R', ModItems.ammo_rocket }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ammo_rocket_rpc, 2), new Object[] { "BP ", "CBH", " DR", 'B', ModItems.blades_steel, 'P', "plateSteel", 'C', ModItems.canister_fuel, 'H', ModItems.hull_small_steel, 'D', ModItems.piston_selenium, 'R', ModItems.ammo_rocket }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ammo_rocket_rpc, 2), new Object[] { "BP ", "CBH", " DR", 'B', ModItems.blades_steel, 'P', "plateSteel", 'C', ModItems.canister_petroil, 'H', ModItems.hull_small_steel, 'D', ModItems.piston_selenium, 'R', ModItems.ammo_rocket }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ammo_rocket_rpc, 2), new Object[] { "BP ", "CBH", " DR", 'B', ModItems.blades_steel, 'P', "plateSteel", 'C', ModItems.canister_biofuel, 'H', ModItems.hull_small_steel, 'D', ModItems.piston_selenium, 'R', ModItems.ammo_rocket }));

		//40mm grenades
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ammo_grenade, 2), new Object[] { " T ", "GCI", " P ", 'T', Items.gunpowder, 'G', ModItems.cordite, 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'I', "plateIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ammo_grenade, 2), new Object[] { " T ", "GCI", " P ", 'T', Items.gunpowder, 'G', ModItems.ballistite, 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'I', "plateIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ammo_grenade_tracer, 2), new Object[] { " T ", "GCI", " P ", 'T', "dustLapis", 'G', ModItems.cordite, 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'I', "plateIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ammo_grenade_tracer, 2), new Object[] { " T ", "GCI", " P ", 'T', "dustLapis", 'G', ModItems.ballistite, 'C', ModItems.casing_50, 'P', ModItems.primer_50, 'I', "plateIron" }));
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_grenade_he, 2), new Object[] { "GIG", 'G', ModItems.ammo_grenade, 'I', ModItems.ingot_semtex });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_grenade_incendiary, 2), new Object[] { "GIG", 'G', ModItems.ammo_grenade, 'I', ModItems.powder_fire });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_grenade_phosphorus, 2), new Object[] { "GIG", 'G', ModItems.ammo_grenade, 'I', ModItems.ingot_phosphorus });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_grenade_toxic, 2), new Object[] { "GIG", 'G', ModItems.ammo_grenade, 'I', ModItems.powder_poison });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_grenade_concussion, 2), new Object[] { "GIG", 'G', ModItems.ammo_grenade, 'I', Items.glowstone_dust });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_grenade_nuclear, 2), new Object[] { " P ", "GIG", " P ", 'G', ModItems.ammo_grenade, 'I', ModItems.neutron_reflector, 'P', ModItems.nugget_pu239 });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_grenade_finned, 1), new Object[] { "G", "R", 'G', Items.feather, 'R', ModItems.ammo_grenade });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_grenade_kampf, 2), new Object[] { "G", "R", 'G', ModItems.ammo_rocket, 'R', ModItems.ammo_grenade });
		
		//240mm Shells
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ammo_shell, 4), new Object[] { " T ", "GHG", "CCC", 'T', Blocks.tnt, 'G', Items.gunpowder, 'H', ModItems.hull_small_steel, 'C', "ingotCopper" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ammo_shell, 4), new Object[] { " T ", "GHG", "CCC", 'T', Blocks.tnt, 'G', ModItems.ballistite, 'H', ModItems.hull_small_steel, 'C', "ingotCopper" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ammo_shell, 6), new Object[] { " T ", "GHG", "CCC", 'T', Blocks.tnt, 'G', ModItems.cordite, 'H', ModItems.hull_small_steel, 'C', "ingotCopper" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ammo_shell_explosive, 4), new Object[] { " T ", "GHG", "CCC", 'T', ModItems.ingot_semtex, 'G', Items.gunpowder, 'H', ModItems.hull_small_steel, 'C', "ingotCopper" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ammo_shell_explosive, 4), new Object[] { " T ", "GHG", "CCC", 'T', ModItems.ingot_semtex, 'G', ModItems.ballistite, 'H', ModItems.hull_small_steel, 'C', "ingotCopper" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ammo_shell_explosive, 6), new Object[] { " T ", "GHG", "CCC", 'T', ModItems.ingot_semtex, 'G', ModItems.cordite, 'H', ModItems.hull_small_steel, 'C', "ingotCopper" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ammo_shell_apfsds_t, 4), new Object[] { " I ", "GIG", "CCC", 'I', "ingotTungsten", 'G', Items.gunpowder, 'C', "ingotCopper" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ammo_shell_apfsds_t, 4), new Object[] { " I ", "GIG", "CCC", 'I', "ingotTungsten", 'G', ModItems.ballistite, 'C', "ingotCopper" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ammo_shell_apfsds_t, 6), new Object[] { " I ", "GIG", "CCC", 'I', "ingotTungsten", 'G', ModItems.cordite, 'C', "ingotCopper" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ammo_shell_apfsds_du, 4), new Object[] { " I ", "GIG", "CCC", 'I', "ingotUranium238", 'G', Items.gunpowder, 'C', "ingotCopper" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ammo_shell_apfsds_du, 4), new Object[] { " I ", "GIG", "CCC", 'I', "ingotUranium238", 'G', ModItems.ballistite, 'C', "ingotCopper" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ammo_shell_apfsds_du, 6), new Object[] { " I ", "GIG", "CCC", 'I', "ingotUranium238", 'G', ModItems.cordite, 'C', "ingotCopper" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ammo_shell_w9, 1), new Object[] { " P ", "NSN", " P ", 'P', "nuggetPlutonium239", 'N', "plateDenseLead", 'S', ModItems.ammo_shell_explosive }));
		
		//DGK Belts
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ammo_dgk, 1), new Object[] { "LLL", "GGG", "CCC", 'L', "plateLead", 'G', ModItems.ballistite, 'C', "ingotCopper" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ammo_dgk, 1), new Object[] { "LLL", "GGG", "CCC", 'L', "plateLead", 'G', ModItems.cordite, 'C', "ingotCopper" }));
		
		//Mini Nuke
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_nuke, 1), new Object[] { "P", "S", "P", 'P', ModItems.nugget_pu239, 'S', ModItems.assembly_nuke });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_nuke_low, 1), new Object[] { "P", "S", 'P', ModItems.nugget_pu239, 'S', ModItems.assembly_nuke });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_nuke_high, 1), new Object[] { "PPP", "PSP", "PPP", 'P', ModItems.nugget_pu239, 'S', ModItems.assembly_nuke });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_nuke_tots, 1), new Object[] { "PPP", "PIP", "PPP", 'P', ModItems.pellet_cluster, 'I', ModItems.ingot_pu239 });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_nuke_safe, 1), new Object[] { "G", "N", 'G', Items.glowstone_dust, 'N', ModItems.ammo_nuke_low });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_nuke_pumpkin, 1), new Object[] { " T ", "TST", " T ", 'T', Blocks.tnt, 'S', ModItems.assembly_nuke });
		
		//MIRV recycling
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ammo_nuke, 6), new Object[] { ModItems.ammo_mirv });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ammo_nuke_low, 6), new Object[] { ModItems.ammo_mirv_low });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ammo_nuke_high, 6), new Object[] { ModItems.ammo_mirv_high });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ammo_nuke_safe, 6), new Object[] { ModItems.ammo_mirv_safe });
		
		//MIRV
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_mirv, 1), new Object[] { "NNN", "CDS", "NNN", 'N', ModItems.ammo_nuke, 'C', ModItems.cap_aluminium, 'D', ModBlocks.det_cord, 'S', ModItems.hull_small_steel });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_mirv_low, 1), new Object[] { "NNN", "CDS", "NNN", 'N', ModItems.ammo_nuke_low, 'C', ModItems.cap_aluminium, 'D', ModBlocks.det_cord, 'S', ModItems.hull_small_steel });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_mirv_high, 1), new Object[] { "NNN", "CDS", "NNN", 'N', ModItems.ammo_nuke_high, 'C', ModItems.cap_aluminium, 'D', ModBlocks.det_cord, 'S', ModItems.hull_small_steel });
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_mirv_safe, 1), new Object[] { "NNN", "CDS", "NNN", 'N', ModItems.ammo_nuke_safe, 'C', ModItems.cap_aluminium, 'D', ModBlocks.det_cord, 'S', ModItems.hull_small_steel });
		//since the milk part of the recipe isn't realy present in the MIRV's effect, it might as well be replaced with something more sensible, i.e. duct tape
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_mirv_special, 1), new Object[] { "CBC", "MCM", "CBC", 'C', ModItems.canned_jizz, 'B', ModItems.gun_bf_ammo, 'M', ModItems.ammo_mirv });
		
		//Flamer fuel
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ammo_fuel, 1), new Object[] { " P ", "BDB", " P ", 'P', "plateSteel", 'B', ModItems.bolt_tungsten, 'D', ModItems.canister_fuel }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ammo_fuel_napalm, 1), new Object[] { " P ", "BDB", " P ", 'P', "plateSteel", 'B', ModItems.bolt_tungsten, 'D', ModItems.canister_napalm }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ammo_fuel_phosphorus, 1), new Object[] { "CPC", "CDC", "CPC", 'C', "dustCoal", 'P', ModItems.ingot_phosphorus, 'D', ModItems.ammo_fuel }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ammo_fuel_gas, 1), new Object[] { "PDP", "BDB", "PDP", 'P', "plateSteel", 'B', ModItems.bolt_tungsten, 'D', ModItems.pellet_gas }));
		GameRegistry.addRecipe(new ItemStack(ModItems.ammo_fuel_vaporizer, 1), new Object[] { "PSP", "SNS", "PSP", 'P', ModItems.ingot_phosphorus, 'S', ModItems.crystal_sulfur, 'N', ModItems.ammo_fuel_napalm });
		
		//Casings
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.casing_357, 1), new Object[] { " P ", "   ", "P P", 'P', "plateCopper" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.casing_44, 1), new Object[] { "P", " ", "P", 'P', "plateCopper" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.casing_9, 1), new Object[] { "P", "P", 'P', "plateCopper" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.casing_50, 1), new Object[] { " P ", " P ", "PPP", 'P', "plateCopper" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.casing_buckshot, 1), new Object[] { "P P", "PPP", 'P', "plateCopper" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.casing_357, 1), new Object[] { " P ", "   ", "P P", 'P', "plateIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.casing_44, 1), new Object[] { "P", " ", "P", 'P', "plateIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.casing_9, 1), new Object[] { "P", "P", 'P', "plateIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.casing_50, 1), new Object[] { " P ", " P ", "PPP", 'P', "plateIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.casing_buckshot, 1), new Object[] { "P P", "PPP", 'P', "plateIron" }));

		//Primers
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.primer_357, 1), new Object[] { "R", "P", 'P', "plateIron", 'R', "dustRedstone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.primer_44, 1), new Object[] { "P", "R", 'P', "plateIron", 'R', "dustRedstone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.primer_9, 1), new Object[] { "R", "P", 'P', "plateAluminum", 'R', "dustRedstone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.primer_50, 1), new Object[] { "P", "R", 'P', "plateAluminum", 'R', "dustRedstone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.primer_buckshot, 1), new Object[] { "R", "P", 'P', "plateCopper", 'R', "dustRedstone" }));

		//Turrets
		//GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.turret_light_ammo, 1), new Object[] { " L ", "IGI", "ICI", 'L', "plateLead", 'I', "plateIron", 'C', "plateCopper", 'G', Items.gunpowder }));
		//GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.turret_heavy_ammo, 1), new Object[] { "LGC", "LGC", "LGC", 'L', "plateLead", 'C', "plateCopper", 'G', Items.gunpowder }));
		//GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.turret_rocket_ammo, 1), new Object[] { "TS ", "SGS", " SR", 'T', Blocks.tnt, 'S', "plateSteel", 'G', Items.gunpowder, 'R', "dustRedstone" }));
		//GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.turret_flamer_ammo, 1), new Object[] { "FSF", "FPF", "FPF", 'F', ModItems.gun_immolator_ammo, 'S', ModItems.pipes_steel, 'P', "plateCopper" }));
		//GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.turret_tau_ammo, 1), new Object[] { "AAA", "AUA", "AAA", 'A', ModItems.gun_xvl1456_ammo, 'U', "blockUranium238" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.turret_spitfire_ammo, 1), new Object[] { "CP ", "PTP", " PR", 'P', "plateSteel", 'C', ModItems.circuit_copper, 'T', Blocks.tnt, 'R', "dustRedstone" }));
		//GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.turret_cwis_ammo, 1), new Object[] { "LLL", "GGG", "IGI", 'L', "plateLead", 'I', "plateIron", 'G', Items.gunpowder }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.turret_cheapo_ammo, 1), new Object[] { "ILI", "IGI", "ICI", 'L', "plateLead", 'I', "plateSteel", 'C', "plateCopper", 'G', Items.gunpowder }));

		//Grenades
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.grenade_generic, 4), new Object[] { "RS ", "ITI", " I ", 'I', "plateIron", 'R', ModItems.wire_red_copper, 'S', "plateSteel", 'T', Item.getItemFromBlock(Blocks.tnt) }));
		GameRegistry.addRecipe(new ItemStack(ModItems.grenade_strong, 2), new Object[] { " G ", "SGS", " S ", 'G', ModItems.grenade_generic, 'S', Items.gunpowder });
		GameRegistry.addRecipe(new ItemStack(ModItems.grenade_frag, 2), new Object[] { " G ", "WGW", " K ", 'G', ModItems.grenade_generic, 'W', Item.getItemFromBlock(Blocks.planks), 'K', Item.getItemFromBlock(Blocks.gravel) });
		GameRegistry.addRecipe(new ItemStack(ModItems.grenade_poison, 2), new Object[] { " G ", "PGP", " P ", 'G', ModItems.grenade_generic, 'P', ModItems.powder_poison });
		GameRegistry.addRecipe(new ItemStack(ModItems.grenade_gas, 2), new Object[] { " G ", "CGC", " C ", 'G', ModItems.grenade_generic, 'C', ModItems.pellet_gas });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.grenade_aschrab, 1), new Object[] {"RS ", "ITI", " S ", 'I', "paneGlassColorless", 'R', ModItems.wire_red_copper, 'S', "plateSteel", 'T', ModItems.cell_anti_schrabidium }));
		GameRegistry.addRecipe(new ItemStack(ModItems.grenade_mk2, 2), new Object[] { " G ", "SGS", " S ", 'G', ModItems.grenade_strong, 'S', Items.gunpowder });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.grenade_gascan, 1), new Object[] { ModItems.canister_fuel, Items.flint });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.grenade_gascan, 1), new Object[] { ModItems.canister_biofuel, Items.flint });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.grenade_gascan, 1), new Object[] { ModItems.canister_petroil, Items.flint });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.grenade_gascan, 1), new Object[] { ModItems.canister_kerosene, Items.flint });
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.grenade_lemon, 1), new Object[] { ModItems.lemon, ModItems.grenade_strong }));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.gun_moist_nugget, 12), new Object[] { Items.bread, Items.wheat, Items.cooked_chicken, Items.egg });
		GameRegistry.addRecipe(new ItemStack(ModItems.grenade_smart, 4), new Object[] { " A ", "ACA", " A ", 'A', ModItems.grenade_strong, 'C', ModItems.circuit_aluminium });
		GameRegistry.addRecipe(new ItemStack(ModItems.grenade_mirv, 1), new Object[] { "GGG", "GCG", "GGG", 'G', ModItems.grenade_smart, 'C', ModItems.grenade_generic });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.grenade_breach, 1), new Object[] { "G", "G", "P", 'G', ModItems.grenade_smart, 'P', "plateSaturnite" }));
		GameRegistry.addRecipe(new ItemStack(ModItems.grenade_burst, 1), new Object[] { "GGG", "GCG", "GGG", 'G', ModItems.grenade_breach, 'C', ModItems.grenade_generic });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.grenade_cloud), new Object[] { "SPS", "CAC", "SPS", 'S', "dustSulfur", 'P', ModItems.powder_poison, 'C', "dustCopper", 'A', new ItemStack(ModItems.fluid_tank_full, 1, FluidType.ACID.getID()) }));
		GameRegistry.addRecipe(new ItemStack(ModItems.grenade_pink_cloud), new Object[] { " S ", "ECE", " E ", 'S', ModItems.powder_spark_mix, 'E', ModItems.powder_magic, 'C', ModItems.grenade_cloud });
		GameRegistry.addRecipe(new ItemStack(ModItems.nuclear_waste_pearl), new Object[] { "WWW", "WFW", "WWW", 'W', ModItems.nuclear_waste_tiny, 'F', ModBlocks.block_fallout });
		//GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.grenade_nuke), new Object[] { "CGC", "CGC", "PAP", 'C', ModBlocks.det_charge, 'G', ModItems.grenade_mk2, 'P', "plateAdvanced", 'A', Blocks.anvil }));
		
		//IF Grenades
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.grenade_if_generic, 1), new Object[] { " C ", "PTP", " P ", 'C', ModItems.coil_tungsten, 'P', "plateSteel", 'T', Blocks.tnt }));
		GameRegistry.addRecipe(new ItemStack(ModItems.grenade_if_he, 1), new Object[] { "A", "G", "A", 'G', ModItems.grenade_if_generic, 'A', Items.gunpowder });
		GameRegistry.addRecipe(new ItemStack(ModItems.grenade_if_bouncy, 1), new Object[] { "G", "A", 'G', ModItems.grenade_if_generic, 'A', ModItems.plate_polymer });
		GameRegistry.addRecipe(new ItemStack(ModItems.grenade_if_sticky, 1), new Object[] { "G", "A", 'G', ModItems.grenade_if_generic, 'A', Items.slime_ball });
		GameRegistry.addRecipe(new ItemStack(ModItems.grenade_if_impact, 1), new Object[] { "G", "A", 'G', ModItems.grenade_if_generic, 'A', Items.redstone });
		GameRegistry.addRecipe(new ItemStack(ModItems.grenade_if_concussion, 1), new Object[] { "G", "A", 'G', ModItems.grenade_if_generic, 'A', Items.glowstone_dust });
		GameRegistry.addRecipe(new ItemStack(ModItems.grenade_if_toxic, 1), new Object[] { "G", "A", 'G', ModItems.grenade_if_generic, 'A', ModItems.powder_poison });
		GameRegistry.addRecipe(new ItemStack(ModItems.grenade_if_incendiary, 1), new Object[] { "G", "A", 'G', ModItems.grenade_if_generic, 'A', ModItems.powder_fire });
		GameRegistry.addRecipe(new ItemStack(ModItems.grenade_if_brimstone, 1), new Object[] { "R", "G", "A", 'G', ModItems.grenade_if_generic, 'R', Items.redstone, 'A', ModItems.sulfur });
		GameRegistry.addRecipe(new ItemStack(ModItems.grenade_if_mystery, 1), new Object[] { "A", "G", "A", 'G', ModItems.grenade_if_generic, 'A', ModItems.powder_magic });
		GameRegistry.addRecipe(new ItemStack(ModItems.grenade_if_spark, 1), new Object[] { " A ", "AGA", " A ", 'G', ModItems.grenade_if_generic, 'A', ModItems.powder_spark_mix });
		GameRegistry.addRecipe(new ItemStack(ModItems.grenade_if_hopwire, 1), new Object[] { " A ", "AGA", " A ", 'G', ModItems.grenade_if_generic, 'A', ModItems.powder_power });
		GameRegistry.addRecipe(new ItemStack(ModItems.grenade_if_null, 1), new Object[] { "BAB", "AGA", "BAB", 'G', ModItems.grenade_if_generic, 'A', Blocks.obsidian, 'B', ModItems.ingot_saturnite });

		//Mines
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.mine_ap, 4), new Object[] { "C", "P", "T", 'C', ModItems.circuit_targeting_tier2, 'P', "plateIron", 'T', ModItems.ingot_semtex }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.mine_he, 1), new Object[] { " C ", "PTP", 'C', ModItems.circuit_targeting_tier2, 'P', "plateSteel", 'T', ModItems.ingot_semtex }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.mine_shrap, 2), new Object[] { "LLL", " C ", "PTP", 'C', ModItems.circuit_targeting_tier2, 'P', "plateSteel", 'T', ModBlocks.det_cord, 'L', ModItems.pellet_buckshot }));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.mine_fat, 1), new Object[] { "CDN", 'C', ModItems.circuit_targeting_tier2, 'D', ModItems.ducttape, 'N', ModItems.ammo_nuke });
		
		//Nuke parts
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.gadget_explosive8, 1), new Object[] { "EEE", "EPE", "EEE", 'E', ModItems.gadget_explosive, 'P', "plateAluminum" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.man_explosive8, 1), new Object[] { "EEE", "ESE", "EEE", 'E', ModItems.man_explosive, 'S', "plateSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.n2_charge, 1), new Object[] { " D ", "ERE", " D ", 'D', ModItems.ducttape, 'E', ModBlocks.det_charge, 'R', "blockRedstone" }));

		//Custom nuke rods
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.custom_tnt, 1), new Object[] { " C ", "TIT", "TIT", 'C', "plateCopper", 'I', "plateIron", 'T', Blocks.tnt }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.custom_nuke, 1), new Object[] { " C ", "LUL", "LUL", 'C', "plateCopper", 'L', "plateLead", 'U', "ingotUranium235" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.custom_hydro, 1), new Object[] { " C ", "LTL", "LIL", 'C', "plateCopper", 'L', "plateLead", 'I', "plateIron", 'T', ModItems.cell_tritium }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.custom_amat, 1), new Object[] { " C ", "MMM", "AAA", 'C', "plateCopper", 'A', "plateAluminum", 'M', ModItems.cell_antimatter }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.custom_dirty, 1), new Object[] { " C ", "WLW", "WLW", 'C', "plateCopper", 'L', "plateLead", 'W', ModItems.nuclear_waste }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.custom_schrab, 1), new Object[] { " C ", "LUL", "LUL", 'C', "plateCopper", 'L', "plateLead", 'U', "ingotSchrabidium" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.demon_core_open, 1), new Object[] { "PRP", " CS", "PRP", 'P', "plateTitanium", 'R', "plateDenseLead", 'C', ModItems.man_core, 'S', ModItems.screwdriver }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.crucible, 1, 3), new Object[] { "MEM", "YDY", "YCY", 'M', ModItems.ingot_meteorite_forged, 'E', ModItems.ingot_euphemium, 'Y', ModItems.billet_yharonite, 'D', ModItems.demon_core_closed, 'C', ModItems.ingot_chainsteel }));
	}
}

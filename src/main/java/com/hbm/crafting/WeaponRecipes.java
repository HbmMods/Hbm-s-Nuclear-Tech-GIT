package com.hbm.crafting;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.material.Mats;

import static com.hbm.inventory.OreDictManager.*;

import com.hbm.items.ItemAmmoEnums.*;
import com.hbm.items.ItemEnums.EnumCasingType;
import com.hbm.items.machine.ItemCircuit.EnumCircuitType;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.GunB92Cell;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmo;
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

		//SEDNA Parts
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.part_stock, 1, Mats.MAT_WOOD.id), new Object[] { "WWW", "  W", 'W', KEY_PLANKS });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.part_grip, 1, Mats.MAT_WOOD.id), new Object[] { "W ", " W", " W", 'W', KEY_PLANKS });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.part_stock, 1, Mats.MAT_POLYMER.id), new Object[] { "WWW", "  W", 'W', POLYMER.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.part_grip, 1, Mats.MAT_POLYMER.id), new Object[] { "W ", " W", " W", 'W', POLYMER.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.part_stock, 1, Mats.MAT_BAKELITE.id), new Object[] { "WWW", "  W", 'W', BAKELITE.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.part_grip, 1, Mats.MAT_BAKELITE.id), new Object[] { "W ", " W", " W", 'W', BAKELITE.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.part_stock, 1, Mats.MAT_HARDPLASTIC.id), new Object[] { "WWW", "  W", 'W', PC.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.part_grip, 1, Mats.MAT_HARDPLASTIC.id), new Object[] { "W ", " W", " W", 'W', PC.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.part_stock, 1, Mats.MAT_PVC.id), new Object[] { "WWW", "  W", 'W', PVC.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.part_grip, 1, Mats.MAT_PVC.id), new Object[] { "W ", " W", " W", 'W', PVC.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.part_grip, 1, Mats.MAT_RUBBER.id), new Object[] { "W ", " W", " W", 'W', RUBBER.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.part_grip, 1, Mats.MAT_IVORY.id), new Object[] { "W ", " W", " W", 'W', Items.bone });

		CraftingManager.addRecipeAuto(DictFrame.fromOne(ModItems.casing, EnumCasingType.SHOTSHELL, 2), new Object[] { "P", "C", 'P', GUNMETAL.plate(), 'C', DictFrame.fromOne(ModItems.casing, EnumCasingType.LARGE) });
		CraftingManager.addRecipeAuto(DictFrame.fromOne(ModItems.casing, EnumCasingType.BUCKSHOT, 2), new Object[] { "P", "C", 'P', ANY_PLASTIC.ingot(), 'C', DictFrame.fromOne(ModItems.casing, EnumCasingType.LARGE) });
		CraftingManager.addRecipeAuto(DictFrame.fromOne(ModItems.casing, EnumCasingType.BUCKSHOT_ADVANCED, 2), new Object[] { "P", "C", 'P', ANY_PLASTIC.ingot(), 'C', DictFrame.fromOne(ModItems.casing, EnumCasingType.LARGE_STEEL) });
		
		//SEDNA Guns
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_pepperbox, 1), new Object[] { "IIW", "  C", 'I', IRON.ingot(), 'W', KEY_PLANKS, 'C', CU.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_light_revolver, 1), new Object[] { "BRM", "  G", 'B', STEEL.lightBarrel(), 'R', STEEL.lightReceiver(), 'M', GUNMETAL.mechanism(), 'G', WOOD.grip() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_light_revolver_atlas, 1), new Object[] { " M ", "MAM", " M ", 'M', WEAPONSTEEL.mechanism(), 'A', ModItems.gun_light_revolver });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_henry, 1), new Object[] { "BRP", "BMS", 'B', STEEL.lightBarrel(), 'R', GUNMETAL.lightReceiver(), 'M', GUNMETAL.mechanism(), 'S', WOOD.stock(), 'P', GUNMETAL.plate() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_greasegun, 1), new Object[] { "BRS", "SMG", 'B', STEEL.lightBarrel(), 'R', STEEL.lightReceiver(), 'S', STEEL.bolt(), 'M', GUNMETAL.mechanism(), 'G', STEEL.grip() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_maresleg, 1), new Object[] { "BRM", "BGS", 'B', STEEL.lightBarrel(), 'R', STEEL.lightReceiver(), 'M', GUNMETAL.mechanism(), 'G', STEEL.bolt(), 'S', WOOD.stock() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_maresleg_akimbo, 1), new Object[] { "SMS", 'S', ModItems.gun_maresleg, 'M', WEAPONSTEEL.mechanism() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_flaregun, 1), new Object[] { "BRM", "  G", 'B', STEEL.heavyBarrel(), 'R', STEEL.lightReceiver(), 'M', GUNMETAL.mechanism(), 'G', STEEL.grip() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_am180, 1), new Object[] { "BBR", "GMS", 'B', DURA.lightBarrel(), 'R', DURA.lightReceiver(), 'M', GUNMETAL.mechanism(), 'G', WOOD.grip(), 'S', WOOD.stock() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_liberator, 1), new Object[] { "BB ", "BBM", "G G", 'B', DURA.lightBarrel(), 'M', GUNMETAL.mechanism(), 'G', WOOD.grip() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_congolake, 1), new Object[] { "BM ", "BRS", "G  ", 'B', DURA.heavyBarrel(), 'M', GUNMETAL.mechanism(), 'R', DURA.lightReceiver(), 'S', WOOD.stock(), 'G', WOOD.grip() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_flamer, 1), new Object[] { " MG", "BBR", " GM", 'M', GUNMETAL.mechanism(), 'G', DURA.grip(), 'B', DURA.heavyBarrel(), 'R', DURA.heavyReceiver() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_flamer_topaz, 1), new Object[] { " M ", "MFM", " M ", 'M', WEAPONSTEEL.mechanism(), 'F', ModItems.gun_flamer });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_heavy_revolver, 1), new Object[] { "BRM", "  G", 'B', DESH.lightBarrel(), 'R', DESH.lightReceiver(), 'M', GUNMETAL.mechanism(), 'G', WOOD.grip() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_carbine, 1), new Object[] { "BRM", "G S", 'B', DESH.lightBarrel(), 'R',DESH.lightReceiver(), 'M', GUNMETAL.mechanism(), 'G', WOOD.grip(), 'S', WOOD.stock() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_uzi, 1), new Object[] { "BRS", " GM", 'B', DESH.lightBarrel(), 'R', DESH.lightReceiver(), 'S', ANY_PLASTIC.stock(), 'G', ANY_PLASTIC.grip(), 'M', GUNMETAL.mechanism() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_uzi_akimbo, 1), new Object[] { "UMU", 'U', ModItems.gun_uzi, 'M', WEAPONSTEEL.mechanism() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_spas12, 1), new Object[] { "BRM", "BGS", 'B', DESH.lightBarrel(), 'R', DESH.lightReceiver(), 'M', GUNMETAL.mechanism(), 'G', ANY_PLASTIC.grip(), 'S', DESH.stock() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_panzerschreck, 1), new Object[] { "BBB", "PGM", 'B', DESH.heavyBarrel(), 'P', STEEL.plateCast(), 'G', DESH.grip(), 'M', GUNMETAL.mechanism() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_g3, 1), new Object[] { "BRM", "WGS", 'B', WEAPONSTEEL.lightBarrel(), 'R', WEAPONSTEEL.lightReceiver(), 'M', WEAPONSTEEL.mechanism(), 'W', WOOD.grip(), 'G', RUBBER.grip(), 'S', WOOD.stock() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_stinger, 1), new Object[] { "BBB", "PGM", 'B', WEAPONSTEEL.heavyBarrel(), 'P', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), 'G', WEAPONSTEEL.grip(), 'M', WEAPONSTEEL.mechanism() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_chemthrower, 1), new Object[] { "MHW", "PSS", 'M', WEAPONSTEEL.mechanism(), 'H', RUBBER.pipe(), 'W', ModItems.wrench, 'P', WEAPONSTEEL.heavyBarrel(), 'S', WEAPONSTEEL.shell() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_m2, 1), new Object[] { "  G", "BRM", "  G", 'G', WOOD.grip(), 'B', FERRO.heavyBarrel(), 'R', FERRO.heavyReceiver(), 'M', WEAPONSTEEL.mechanism() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_autoshotgun, 1), new Object[] { "BRM", "G G", 'B', FERRO.heavyBarrel(), 'R', FERRO.heavyReceiver(), 'M', WEAPONSTEEL.mechanism(), 'G', ANY_PLASTIC.grip() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_quadro, 1), new Object[] { "BCB", "BMB", "GG ", 'B', FERRO.heavyBarrel(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), 'M', WEAPONSTEEL.mechanism(), 'G', ANY_PLASTIC.grip() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_lag, 1), new Object[] { "BRM", "  G", 'B', ANY_RESISTANTALLOY.lightBarrel(), 'R', ANY_RESISTANTALLOY.lightReceiver(), 'M', WEAPONSTEEL.mechanism(), 'G', ANY_PLASTIC.grip() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_minigun, 1), new Object[] { "BMG", "BRE", "BGM", 'B', ANY_RESISTANTALLOY.lightBarrel(), 'M', WEAPONSTEEL.mechanism(), 'G', ANY_PLASTIC.grip(), 'R', ANY_RESISTANTALLOY.heavyReceiver(), 'E', ModItems.motor_desh });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_missile_launcher, 1), new Object[] { " CM", "BBB", "G  ", 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), 'M', WEAPONSTEEL.mechanism(), 'B', ANY_RESISTANTALLOY.heavyBarrel(), 'G', ANY_PLASTIC.grip() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_tesla_cannon, 1), new Object[] { "CCC", "BRB", "MGE", 'C', ModItems.coil_advanced_alloy, 'B', ANY_RESISTANTALLOY.heavyBarrel(), 'R', ANY_RESISTANTALLOY.heavyReceiver(), 'M', WEAPONSTEEL.mechanism(), 'G', ANY_PLASTIC.grip(), 'E', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED) });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_stg77, 1), new Object[] { " D ", "BRS", "GM ", 'D', DIAMOND.gem(), 'B', BIGMT.lightBarrel(), 'R', BIGMT.lightReceiver(), 'S', ANY_HARDPLASTIC.stock(), 'G', ANY_HARDPLASTIC.grip(), 'M', BIGMT.mechanism() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_fatman, 1), new Object[] { "PPP", "BSR", "G M", 'P', BIGMT.plate(), 'B', BIGMT.heavyBarrel(), 'S', BIGMT.shell(), 'R', BIGMT.heavyReceiver(), 'G', ANY_HARDPLASTIC.grip(), 'M', BIGMT.mechanism() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_tau, 1), new Object[] { " RD", "CTT", "GMS", 'D', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BISMOID), 'C', CU.pipe(), 'T', ModItems.coil_advanced_torus, 'G', ANY_HARDPLASTIC.grip(), 'R', BIGMT.lightReceiver(), 'M', BIGMT.mechanism(), 'S', ANY_HARDPLASTIC.stock() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_lasrifle, 1), new Object[] { "LC ", "BRS", "MG ", 'L', ModItems.crystal_redstone, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BISMOID), 'B', ANY_BISMOIDBRONZE.lightBarrel(), 'R', ANY_BISMOIDBRONZE.lightReceiver(), 'S', ANY_HARDPLASTIC.stock(), 'M', BIGMT.mechanism(), 'G', ANY_HARDPLASTIC.grip() });
		
		//SEDNA Ammo
		CraftingManager.addRecipeAuto(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.STONE, 6), new Object[] { "C", "P", "G", 'C', KEY_COBBLESTONE, 'P', Items.paper, 'G', Items.gunpowder });
		CraftingManager.addRecipeAuto(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.STONE_AP, 6), new Object[] { "C", "P", "G", 'C', Items.flint, 'P', Items.paper, 'G', Items.gunpowder });
		CraftingManager.addRecipeAuto(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.STONE_SHOT, 6), new Object[] { "C", "P", "G", 'C', Blocks.gravel, 'P', Items.paper, 'G', Items.gunpowder });
		CraftingManager.addRecipeAuto(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.STONE_IRON, 6), new Object[] { "C", "P", "G", 'C', IRON.ingot(), 'P', Items.paper, 'G', Items.gunpowder });

		//Nitra!
		CraftingManager.addShapelessAuto(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.M357_SP, 6), new Object[] { DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.M357_SP), ModItems.nitra });
		CraftingManager.addShapelessAuto(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.M44_SP, 6), new Object[] { DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.M44_SP), ModItems.nitra });
		CraftingManager.addShapelessAuto(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.P9_SP, 12), new Object[] { DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.P9_SP), ModItems.nitra });
		CraftingManager.addShapelessAuto(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.P22_SP, 32), new Object[] { DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.P22_SP), ModItems.nitra });
		CraftingManager.addShapelessAuto(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.R556_SP, 8), new Object[] { DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.R556_SP), ModItems.nitra });
		CraftingManager.addShapelessAuto(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.R762_SP, 6), new Object[] { DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.R762_SP), ModItems.nitra });
		CraftingManager.addShapelessAuto(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.BMG50_SP, 4), new Object[] { DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.BMG50_SP), ModItems.nitra });
		CraftingManager.addShapelessAuto(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.G40_HE, 2), new Object[] { DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.G40_HE), ModItems.nitra });
		CraftingManager.addShapelessAuto(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.ROCKET_HE, 1), new Object[] { DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.ROCKET_HE), ModItems.nitra });
		
		//Missiles
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.missile_taint, 1), new Object[] { ModItems.missile_assembly, ModItems.bucket_mud, ModItems.powder_spark_mix, ModItems.powder_magic });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.missile_micro, 1), new Object[] { ModItems.missile_assembly, ModItems.ducttape, DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.NUKE_HIGH) });
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
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.mp_warhead_15_boxcar, 1), new Object[] { "SNS", "CBC", "SFS", 'S', STAR.ingot(), 'N', ModBlocks.det_nuke, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), 'B', ModBlocks.boxcar, 'F', ModItems.tritium_deuterium_cake });
		
		//Missile chips
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.mp_chip_1, 1), new Object[] { "P", "C", "S", 'P', ANY_RUBBER.ingot(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.VACUUM_TUBE), 'S', ModBlocks.steel_scaffold });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.mp_chip_2, 1), new Object[] { "P", "C", "S", 'P', ANY_RUBBER.ingot(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ANALOG), 'S', ModBlocks.steel_scaffold });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.mp_chip_3, 1), new Object[] { "P", "C", "S", 'P', ANY_RUBBER.ingot(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BASIC), 'S', ModBlocks.steel_scaffold });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.mp_chip_4, 1), new Object[] { "P", "C", "S", 'P', ANY_RUBBER.ingot(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), 'S', ModBlocks.steel_scaffold });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.mp_chip_5, 1), new Object[] { "P", "C", "S", 'P', ANY_RUBBER.ingot(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BISMOID), 'S', ModBlocks.steel_scaffold });
		
		//Turrets
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.turret_sentry, 1), new Object[] { "PPL", " MD", " SC", 'P', STEEL.plate(), 'M', ModItems.motor, 'L', ModItems.mechanism_rifle_1, 'S', ModBlocks.steel_scaffold, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BASIC), 'D', ModItems.crt_display });
		
		//Guns
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_b92), new Object[] { "DDD", "SSC", "  R", 'D', ModItems.plate_dineutronium, 'S', STAR.ingot(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BISMOID), 'R', ModItems.gun_lasrifle });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_b92_ammo, 1), new Object[] { "PSP", "ESE", "PSP", 'P', STEEL.plate(), 'S', STAR.ingot(), 'E', ModItems.powder_spark_mix });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.weaponized_starblaster_cell, 1), new Object[] { new ItemStack(ModItems.fluid_tank_full, 1, Fluids.PEROXIDE.getID()), GunB92Cell.getFullCell(), CU.wireFine() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_fireext, 1), new Object[] { "HB", " T", 'H', STEEL.pipe(), 'B', STEEL.bolt(), 'T', ModItems.tank_steel });

		
		//Ammo assemblies
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.pellet_flechette, 1), new Object[] { " L ", " L ", "LLL", 'L', PB.nugget() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.pellet_claws, 1), new Object[] { " X ", "X X", " XX", 'X', STEEL.plate() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.assembly_nuke, 1), new Object[] { " WP", "SEP", " WP", 'W', GOLD.wireFine(), 'P', STEEL.plate(), 'S', STEEL.shell(), 'E', ModItems.ball_tatb });
		
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
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.ammo_arty, 1, 4), new Object[] { new ItemStack(ModItems.ammo_arty, 1, 2), ModItems.boy_bullet, ModItems.boy_target, ModItems.boy_shielding, DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CONTROLLER), ModItems.ducttape });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_arty, 1, 8), new Object[] { " I ", " S ", "CCC", 'C', ModItems.cordite, 'I', ModItems.sphere_steel, 'S', CU.shell() });

		//DGK Belts
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_dgk, 1), new Object[] { "LLL", "GGG", "CCC", 'L', PB.plate(), 'G', ModItems.ballistite, 'C', CU.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_dgk, 1), new Object[] { "LLL", "GGG", "CCC", 'L', PB.plate(), 'G', ModItems.cordite, 'C', CU.ingot() });

		//Fire Extingusisher Tanks
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ammo_fireext, 1), new Object[] { " P ", "BDB", " P ", 'P', STEEL.plate(), 'B', STEEL.bolt(), 'D', new ItemStack(ModItems.fluid_tank_full, 1, Fluids.WATER.getID()) });
		CraftingManager.addRecipeAuto(ModItems.ammo_fireext.stackFromEnum(AmmoFireExt.FOAM), new Object[] { " N ", "NFN", " N ", 'N', KNO.dust(), 'F', ModItems.ammo_fireext });
		CraftingManager.addRecipeAuto(ModItems.ammo_fireext.stackFromEnum(AmmoFireExt.SAND), new Object[] { "NNN", "NFN", "NNN", 'N', ModBlocks.sand_boron, 'F', ModItems.ammo_fireext });

		//Grenades
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.grenade_generic, 4), new Object[] { "RS ", "ITI", " I ", 'I', IRON.plate(), 'R', MINGRADE.wireFine(), 'S', STEEL.plate(), 'T', Item.getItemFromBlock(Blocks.tnt) });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.grenade_strong, 2), new Object[] { " G ", "SGS", " S ", 'G', ModItems.grenade_generic, 'S', Items.gunpowder });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.grenade_frag, 2), new Object[] { " G ", "WGW", " K ", 'G', ModItems.grenade_generic, 'W', KEY_PLANKS, 'K', Item.getItemFromBlock(Blocks.gravel) });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.grenade_poison, 2), new Object[] { " G ", "PGP", " P ", 'G', ModItems.grenade_generic, 'P', ModItems.powder_poison });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.grenade_gas, 2), new Object[] { " G ", "CGC", " C ", 'G', ModItems.grenade_generic, 'C', ModItems.pellet_gas });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.grenade_aschrab, 1), new Object[] {"RS ", "ITI", " S ", 'I', KEY_CLEARGLASS, 'R', MINGRADE.wireFine(), 'S', STEEL.plate(), 'T', ModItems.cell_anti_schrabidium });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.grenade_mk2, 2), new Object[] { " G ", "SGS", " S ", 'G', ModItems.grenade_strong, 'S', Items.gunpowder });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.grenade_gascan, 1), new Object[] { Fluids.DIESEL.getDict(1000), Items.flint });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.grenade_gascan, 1), new Object[] { Fluids.DIESEL_CRACK.getDict(1000), Items.flint });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.grenade_gascan, 1), new Object[] { Fluids.PETROIL.getDict(1000), Items.flint });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.grenade_gascan, 1), new Object[] { Fluids.PETROIL_LEADED.getDict(1000), Items.flint });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.grenade_gascan, 1), new Object[] { Fluids.GASOLINE.getDict(1000), Items.flint });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.grenade_gascan, 1), new Object[] { Fluids.GASOLINE_LEADED.getDict(1000), Items.flint });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.grenade_gascan, 1), new Object[] { Fluids.BIOFUEL.getDict(1000), Items.flint });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.grenade_lemon, 1), new Object[] { ModItems.lemon, ModItems.grenade_strong });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.grenade_smart, 4), new Object[] { " A ", "ACA", " A ", 'A', ModItems.grenade_strong, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CHIP) });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.grenade_mirv, 1), new Object[] { "GGG", "GCG", "GGG", 'G', ModItems.grenade_smart, 'C', ModItems.grenade_generic });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.grenade_breach, 1), new Object[] { "G", "G", "P", 'G', ModItems.grenade_smart, 'P', BIGMT.plate() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.grenade_burst, 1), new Object[] { "GGG", "GCG", "GGG", 'G', ModItems.grenade_breach, 'C', ModItems.grenade_generic });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.grenade_cloud), new Object[] { "SPS", "CAC", "SPS", 'S', S.dust(), 'P', ModItems.powder_poison, 'C', CU.dust(), 'A', new ItemStack(ModItems.fluid_tank_full, 1, Fluids.PEROXIDE.getID()) });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.grenade_pink_cloud), new Object[] { " S ", "ECE", " E ", 'S', ModItems.powder_spark_mix, 'E', ModItems.powder_magic, 'C', ModItems.grenade_cloud });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.nuclear_waste_pearl), new Object[] { "WWW", "WFW", "WWW", 'W', ModItems.nuclear_waste_tiny, 'F', ModBlocks.block_fallout });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.grenade_kyiv), new Object[] { ModItems.canister_napalm, ModItems.bottle2_empty, ModItems.rag });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.disperser_canister_empty, 4), new Object[] { " P ", "PGP", " P ", 'P', ANY_HARDPLASTIC.ingot(), 'G', ModBlocks.glass_boron });

		//Sticks of explosives
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.stick_dynamite, 4), new Object[] { " S ", "PDP", "PDP", 'S', ModItems.safety_fuse, 'P', Items.paper, 'D', ModItems.ball_dynamite });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.stick_dynamite_fishing, 1), new Object[] { ModItems.stick_dynamite, ModItems.stick_dynamite, ModItems.stick_dynamite, Items.paper, ANY_TAR.any() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.stick_tnt, 4), new Object[] { " S ", "PDP", "PDP", 'S', ModBlocks.det_cord, 'P', Items.paper, 'D', ModItems.ball_tnt });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.stick_semtex, 4), new Object[] { " S ", "PDP", "PDP", 'S', ModBlocks.det_cord, 'P', Items.paper, 'D', ModItems.ingot_semtex });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.stick_c4, 4), new Object[] { " S ", "PDP", "PDP", 'S', ModBlocks.det_cord, 'P', Items.paper, 'D', ModItems.ingot_c4 });
		
		//Blocks of explosives
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.dynamite, 1), new Object[] { "DDD", "DSD", "DDD", 'D', ModItems.stick_dynamite, 'S', ModItems.safety_fuse });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.tnt, 1), new Object[] { "DDD", "DSD", "DDD", 'D', ModItems.stick_tnt, 'S', ModItems.safety_fuse });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.semtex, 1), new Object[] { "DDD", "DSD", "DDD", 'D', ModItems.stick_semtex, 'S', ModItems.safety_fuse });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.c4, 1), new Object[] { "DDD", "DSD", "DDD", 'D', ModItems.stick_c4, 'S', ModItems.safety_fuse });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.fissure_bomb, 1), new Object[] { "SUS", "RPR", "SUS", 'S', ModBlocks.semtex, 'U', U238.block(), 'R', TA.ingot(), 'P', PU239.billet() });

		
		//IF Grenades
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.grenade_if_generic, 1), new Object[] { " C ", "PTP", " P ", 'C', ModItems.coil_tungsten, 'P', STEEL.plate(), 'T', Blocks.tnt });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.grenade_if_he, 1), new Object[] { "A", "G", "A", 'G', ModItems.grenade_if_generic, 'A', Items.gunpowder });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.grenade_if_bouncy, 1), new Object[] { "G", "A", 'G', ModItems.grenade_if_generic, 'A', ANY_RUBBER.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.grenade_if_sticky, 1), new Object[] { "G", "A", 'G', ModItems.grenade_if_generic, 'A', KEY_SLIME });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.grenade_if_impact, 1), new Object[] { "G", "A", 'G', ModItems.grenade_if_generic, 'A', REDSTONE.dust() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.grenade_if_concussion, 1), new Object[] { "G", "A", 'G', ModItems.grenade_if_generic, 'A', Items.glowstone_dust });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.grenade_if_toxic, 1), new Object[] { "G", "A", 'G', ModItems.grenade_if_generic, 'A', ModItems.powder_poison });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.grenade_if_incendiary, 1), new Object[] { "G", "A", 'G', ModItems.grenade_if_generic, 'A', P_RED.dust() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.grenade_if_brimstone, 1), new Object[] { "R", "G", "A", 'G', ModItems.grenade_if_generic, 'R', REDSTONE.dust(), 'A', S.dust() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.grenade_if_mystery, 1), new Object[] { "A", "G", "A", 'G', ModItems.grenade_if_generic, 'A', ModItems.powder_magic });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.grenade_if_spark, 1), new Object[] { " A ", "AGA", " A ", 'G', ModItems.grenade_if_generic, 'A', ModItems.powder_spark_mix });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.grenade_if_hopwire, 1), new Object[] { " A ", "AGA", " A ", 'G', ModItems.grenade_if_generic, 'A', ModItems.powder_power });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.grenade_if_null, 1), new Object[] { "BAB", "AGA", "BAB", 'G', ModItems.grenade_if_generic, 'A', ModItems.undefined, 'B', BIGMT.ingot() });

		//Mines
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.mine_ap, 4), new Object[] { "I", "C", "S", 'I', ModItems.plate_polymer, 'C', ANY_SMOKELESS.dust(), 'S', STEEL.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.mine_shrap, 1), new Object[] { "L", "M", 'M', ModBlocks.mine_ap, 'L', ModItems.pellet_buckshot });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.mine_he, 1), new Object[] { " C ", "PTP", 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BASIC), 'P', STEEL.plate(), 'T', ANY_HIGHEXPLOSIVE.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.mine_fat, 1), new Object[] { "CDN", 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ANALOG), 'D', ModItems.ducttape, 'N', DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.NUKE_DEMO) });
		
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

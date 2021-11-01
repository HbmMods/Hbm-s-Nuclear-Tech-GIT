package com.hbm.lib;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemBattery;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.WeightedRandomChestContent;

public class HbmChestContents {

	static Random rand = new Random();

	private static WeightedRandomChestContent[] modGeneric = new WeightedRandomChestContent[] {
			new WeightedRandomChestContent(Items.bread, 0, 1, 5, 8),
			new WeightedRandomChestContent(ModItems.twinkie, 0, 1, 3, 6),
			new WeightedRandomChestContent(Items.iron_ingot, 0, 2, 6, 10),
			new WeightedRandomChestContent(ModItems.ingot_steel, 0, 2, 5, 7),
			new WeightedRandomChestContent(ModItems.ingot_beryllium, 0, 1, 2, 4),
			new WeightedRandomChestContent(ModItems.ingot_titanium, 0, 1, 1, 3),
			new WeightedRandomChestContent(ModItems.circuit_targeting_tier1, 0, 1, 1, 5),
			new WeightedRandomChestContent(ModItems.gun_revolver, 0, 1, 1, 3),
			new WeightedRandomChestContent(ModItems.gun_revolver_ammo, 0, 2, 6, 4),
			new WeightedRandomChestContent(ModItems.gun_kit_1, 0, 1, 3, 4),
			new WeightedRandomChestContent(ModItems.gun_lever_action, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.ammo_20gauge, 0, 2, 6, 3),
			new WeightedRandomChestContent(ModItems.casing_9, 0, 4, 10, 3),
			new WeightedRandomChestContent(ModItems.casing_50, 0, 4, 10, 3),
			new WeightedRandomChestContent(ModItems.primer_9, 0, 4, 10, 3),
			new WeightedRandomChestContent(ModItems.primer_50, 0, 4, 10, 3),
			new WeightedRandomChestContent(ModItems.cordite, 0, 4, 6, 5),
			new WeightedRandomChestContent(ModItems.battery_generic, 0, 1, 1, 4),
			new WeightedRandomChestContent(ModItems.battery_advanced, 0, 1, 1, 2),
			new WeightedRandomChestContent(ModItems.scrap, 0, 1, 3, 10),
			new WeightedRandomChestContent(ModItems.dust, 0, 2, 4, 9),
			new WeightedRandomChestContent(ModItems.bottle_opener, 0, 1, 1, 2),
			new WeightedRandomChestContent(ModItems.bottle_nuka, 0, 1, 3, 4),
			new WeightedRandomChestContent(ModItems.bottle_cherry, 0, 1, 1, 2),
			new WeightedRandomChestContent(ModItems.stealth_boy, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.cap_nuka, 0, 1, 15, 7),
			new WeightedRandomChestContent(ModItems.canister_fuel, 0, 1, 2, 2),
			new WeightedRandomChestContent(ModItems.canister_biofuel, 0, 1, 2, 3),
			new WeightedRandomChestContent(ModItems.gas_mask_m65, 60, 1, 1, 2),
			new WeightedRandomChestContent(ModItems.gas_mask_filter, 0, 1, 1, 3)  };

	private static WeightedRandomChestContent[] antenna = new WeightedRandomChestContent[] {
			new WeightedRandomChestContent(ModItems.twinkie, 0, 1, 3, 4),
			new WeightedRandomChestContent(ModItems.ingot_steel, 0, 1, 2, 7),
			new WeightedRandomChestContent(ModItems.ingot_red_copper, 0, 1, 1, 4),
			new WeightedRandomChestContent(ModItems.ingot_titanium, 0, 1, 3, 5),
			new WeightedRandomChestContent(ModItems.wire_red_copper, 0, 2, 3, 7),
			new WeightedRandomChestContent(ModItems.circuit_targeting_tier1, 0, 1, 1, 4),
			new WeightedRandomChestContent(ModItems.circuit_copper, 0, 1, 1, 4),
			new WeightedRandomChestContent(ModItems.battery_generic, 0, 1, 1, 4),
			new WeightedRandomChestContent(ModItems.battery_advanced, 0, 1, 1, 3),
			new WeightedRandomChestContent(ModItems.powder_iodine, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.powder_bromine, 0, 1, 1, 1),
			new WeightedRandomChestContent(Item.getItemFromBlock(ModBlocks.steel_poles), 0, 1, 4, 8),
			new WeightedRandomChestContent(Item.getItemFromBlock(ModBlocks.steel_scaffold), 0, 1, 3, 8),
			new WeightedRandomChestContent(Item.getItemFromBlock(ModBlocks.pole_top), 0, 1, 1, 4),
			new WeightedRandomChestContent(Item.getItemFromBlock(ModBlocks.pole_satellite_receiver), 0, 1, 1, 7),
			new WeightedRandomChestContent(ModItems.scrap, 0, 1, 3, 10),
			new WeightedRandomChestContent(ModItems.dust, 0, 2, 4, 9),
			new WeightedRandomChestContent(ModItems.bottle_opener, 0, 1, 1, 2),
			new WeightedRandomChestContent(ModItems.bottle_nuka, 0, 1, 3, 4),
			new WeightedRandomChestContent(ModItems.bottle_cherry, 0, 1, 1, 2),
			new WeightedRandomChestContent(ModItems.stealth_boy, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.cap_nuka, 0, 1, 15, 7),
			new WeightedRandomChestContent(ModItems.bomb_caller, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.gas_mask_filter, 0, 1, 1, 2) };

	private static WeightedRandomChestContent[] expensive = new WeightedRandomChestContent[] {
			new WeightedRandomChestContent(ModItems.nugget_schrabidium, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.chlorine_pinwheel, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.circuit_targeting_tier3, 0, 1, 1, 4),
			new WeightedRandomChestContent(ModItems.circuit_gold, 0, 1, 2, 3),
			new WeightedRandomChestContent(ModItems.circuit_targeting_tier4, 0, 1, 1, 2),
			new WeightedRandomChestContent(ModItems.gun_kit_1, 0, 1, 3, 6),
			new WeightedRandomChestContent(ModItems.gun_kit_2, 0, 1, 2, 3),
			new WeightedRandomChestContent(ModItems.gun_rpg, 0, 1, 1, 4),
			new WeightedRandomChestContent(ModItems.ammo_rocket, 0, 1, 4, 5),
			new WeightedRandomChestContent(ModItems.gun_fatman, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.ammo_nuke_safe, 0, 1, 2, 1),
			new WeightedRandomChestContent(ModItems.ammo_nuke_low, 0, 1, 2, 1),
			new WeightedRandomChestContent(ModItems.ammo_nuke_pumpkin, 0, 1, 2, 1),
			new WeightedRandomChestContent(ModItems.grenade_nuclear, 0, 1, 1, 2),
			new WeightedRandomChestContent(ModItems.grenade_smart, 0, 1, 3, 3),
			new WeightedRandomChestContent(ModItems.grenade_mirv, 0, 1, 1, 2),
			new WeightedRandomChestContent(ModItems.stealth_boy, 0, 1, 1, 2),
			new WeightedRandomChestContent(ModItems.battery_advanced, 0, 1, 1, 3),
			new WeightedRandomChestContent(ModItems.battery_advanced_cell, 0, 1, 1, 2),
			new WeightedRandomChestContent(ModItems.battery_schrabidium, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.syringe_awesome, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.crate_caller, 0, 1, 1, 3),
			new WeightedRandomChestContent(ModItems.fusion_core, 0, 1, 1, 4),
			new WeightedRandomChestContent(ModItems.bottle_nuka, 0, 1, 3, 6),
			new WeightedRandomChestContent(ModItems.bottle_quantum, 0, 1, 1, 3),
			new WeightedRandomChestContent(Item.getItemFromBlock(ModBlocks.red_barrel), 0, 1, 1, 6),
			new WeightedRandomChestContent(ModItems.canister_fuel, 0, 1, 2, 2),
			new WeightedRandomChestContent(ModItems.canister_biofuel, 0, 1, 2, 3),
			new WeightedRandomChestContent(ModItems.gas_mask_m65, 60, 1, 1, 5),
			new WeightedRandomChestContent(ModItems.bomb_caller, 0, 1, 1, 2),
			new WeightedRandomChestContent(ModItems.bomb_caller, 1, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.bomb_caller, 2, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.gas_mask_filter, 0, 1, 1, 4),
			new WeightedRandomChestContent(ModItems.journal_pip, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.journal_bj, 0, 1, 1, 1) };

	private static WeightedRandomChestContent[] nukeTrash = new WeightedRandomChestContent[] {
			new WeightedRandomChestContent(ModItems.nugget_u238, 0, 3, 12, 5),
			new WeightedRandomChestContent(ModItems.nugget_pu240, 0, 3, 8, 5),
			new WeightedRandomChestContent(ModItems.nugget_neptunium, 0, 1, 4, 3),
			new WeightedRandomChestContent(ModItems.rod_u238, 0, 1, 1, 3),
			new WeightedRandomChestContent(ModItems.rod_neptunium, 0, 1, 1, 4),
			new WeightedRandomChestContent(ModItems.rod_pu240, 0, 1, 1, 3),
			new WeightedRandomChestContent(ModItems.rod_dual_u238, 0, 1, 1, 3),
			new WeightedRandomChestContent(ModItems.rod_dual_pu240, 0, 1, 1, 3),
			new WeightedRandomChestContent(ModItems.rod_quad_u238, 0, 1, 1, 3),
			new WeightedRandomChestContent(ModItems.rod_quad_pu240, 0, 1, 1, 3),
			new WeightedRandomChestContent(ModItems.bottle_quantum, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.gas_mask_m65, 60, 1, 1, 5),
			new WeightedRandomChestContent(ModItems.hazmat_kit, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.gas_mask_filter, 0, 1, 1, 5),
			new WeightedRandomChestContent(Item.getItemFromBlock(ModBlocks.yellow_barrel), 0, 1, 1, 2) };

	private static WeightedRandomChestContent[] nuclear = new WeightedRandomChestContent[] {
			new WeightedRandomChestContent(ModItems.nugget_u235, 0, 3, 12, 5),
			new WeightedRandomChestContent(ModItems.nugget_pu238, 0, 3, 12, 5),
			new WeightedRandomChestContent(ModItems.nugget_pu239, 0, 3, 12, 5),
			new WeightedRandomChestContent(ModItems.rod_u235, 0, 1, 1, 3),
			new WeightedRandomChestContent(ModItems.rod_pu239, 0, 1, 1, 3),
			new WeightedRandomChestContent(ModItems.rod_dual_u235, 0, 1, 1, 3),
			new WeightedRandomChestContent(ModItems.rod_dual_pu239, 0, 1, 1, 3),
			new WeightedRandomChestContent(ModItems.rod_quad_u235, 0, 1, 1, 3),
			new WeightedRandomChestContent(ModItems.rod_quad_pu239, 0, 1, 1, 3),
			new WeightedRandomChestContent(ModItems.rod_uranium_fuel, 0, 1, 1, 4),
			new WeightedRandomChestContent(ModItems.rod_plutonium_fuel, 0, 1, 1, 4),
			new WeightedRandomChestContent(ModItems.rod_mox_fuel, 0, 1, 1, 4),
			new WeightedRandomChestContent(ModItems.rod_dual_uranium_fuel, 0, 1, 1, 3),
			new WeightedRandomChestContent(ModItems.rod_dual_plutonium_fuel, 0, 1, 1, 3),
			new WeightedRandomChestContent(ModItems.rod_dual_mox_fuel, 0, 1, 1, 3),
			new WeightedRandomChestContent(ModItems.rod_quad_uranium_fuel, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.rod_quad_plutonium_fuel, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.rod_quad_mox_fuel, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.pellet_rtg, 0, 1, 1, 3),
			new WeightedRandomChestContent(ModItems.powder_thorium, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.powder_neptunium, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.powder_strontium, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.powder_cobalt, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.bottle_quantum, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.gas_mask_m65, 60, 1, 1, 5),
			new WeightedRandomChestContent(ModItems.hazmat_kit, 0, 1, 1, 2),
			new WeightedRandomChestContent(ModItems.gas_mask_filter, 0, 1, 1, 5),
			new WeightedRandomChestContent(Item.getItemFromBlock(ModBlocks.yellow_barrel), 0, 1, 3, 3) };

	private static WeightedRandomChestContent[] vertibird = new WeightedRandomChestContent[] {
			new WeightedRandomChestContent(ModItems.t45_helmet, 0, 1, 1, 15),
			new WeightedRandomChestContent(ModItems.t45_plate, 0, 1, 1, 15),
			new WeightedRandomChestContent(ModItems.t45_legs, 0, 1, 1, 15),
			new WeightedRandomChestContent(ModItems.t45_boots, 0, 1, 1, 15),
			new WeightedRandomChestContent(ModItems.t45_kit, 0, 1, 1, 3),
			new WeightedRandomChestContent(ModItems.fusion_core, 0, 1, 1, 10),
			new WeightedRandomChestContent(ModItems.gun_revolver, 0, 1, 1, 4),
			new WeightedRandomChestContent(ModItems.gun_revolver_ammo, 0, 1, 24, 4),
			new WeightedRandomChestContent(ModItems.gun_kit_1, 0, 2, 3, 4),
			new WeightedRandomChestContent(ModItems.gun_rpg, 0, 1, 1, 3),
			new WeightedRandomChestContent(ModItems.ammo_rocket, 0, 1, 6, 3),
			new WeightedRandomChestContent(ModItems.rod_uranium_fuel, 0, 1, 1, 2),
			new WeightedRandomChestContent(ModItems.rod_dual_uranium_fuel, 0, 1, 1, 2),
			new WeightedRandomChestContent(ModItems.rod_quad_uranium_fuel, 0, 1, 1, 2),
			new WeightedRandomChestContent(ModItems.ammo_nuke_safe, 0, 1, 2, 1),
			new WeightedRandomChestContent(ModItems.gun_fatman, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.bottle_nuka, 0, 1, 3, 6),
			new WeightedRandomChestContent(ModItems.bottle_quantum, 0, 1, 1, 3),
			new WeightedRandomChestContent(ModItems.stealth_boy, 0, 1, 1, 7),
			new WeightedRandomChestContent(ModItems.crate_caller, 0, 1, 1, 3),
			new WeightedRandomChestContent(ModItems.gas_mask_m65, 0, 1, 1, 5),
			new WeightedRandomChestContent(ModItems.gas_mask_filter, 0, 1, 1, 5),
			new WeightedRandomChestContent(ModItems.grenade_nuclear, 0, 1, 2, 2),
			new WeightedRandomChestContent(ModItems.bomb_caller, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.bomb_caller, 1, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.bomb_caller, 2, 1, 1, 2) };

	private static WeightedRandomChestContent[] missile = new WeightedRandomChestContent[] {
			new WeightedRandomChestContent(ModItems.missile_generic, 0, 1, 1, 4),
			new WeightedRandomChestContent(ModItems.missile_incendiary, 0, 1, 1, 4),
			new WeightedRandomChestContent(ModItems.missile_cluster, 0, 1, 1, 4),
			new WeightedRandomChestContent(ModItems.missile_buster, 0, 1, 1, 4),
			new WeightedRandomChestContent(Item.getItemFromBlock(ModBlocks.launch_pad), 0, 1, 1, 5),
			new WeightedRandomChestContent(ModItems.gas_mask_m65, 0, 1, 1, 5),
			new WeightedRandomChestContent(ModItems.battery_advanced, 0, 1, 1, 5),
			new WeightedRandomChestContent(ModItems.designator, 0, 1, 1, 5),
			new WeightedRandomChestContent(ModItems.crate_caller, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.thruster_small, 0, 1, 1, 5),
			new WeightedRandomChestContent(ModItems.thruster_medium, 0, 1, 1, 4),
			new WeightedRandomChestContent(ModItems.thruster_large, 0, 1, 1, 2),
			new WeightedRandomChestContent(ModItems.fuel_tank_small, 0, 1, 1, 5),
			new WeightedRandomChestContent(ModItems.fuel_tank_medium, 0, 1, 1, 4),
			new WeightedRandomChestContent(ModItems.fuel_tank_small, 0, 1, 1, 2),
			new WeightedRandomChestContent(ModItems.warhead_mirvlet, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.warhead_nuclear, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.bomb_caller, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.bomb_caller, 3, 1, 1, 1) };

	private static WeightedRandomChestContent[] spaceship = new WeightedRandomChestContent[] {
			new WeightedRandomChestContent(ModItems.battery_advanced, 0, 1, 1, 5),
			new WeightedRandomChestContent(ModItems.ingot_advanced_alloy, 0, 2, 16, 5),
			new WeightedRandomChestContent(ModItems.wire_advanced_alloy, 0, 8, 32, 5),
			new WeightedRandomChestContent(ModItems.coil_advanced_alloy, 0, 2, 16, 5),
			new WeightedRandomChestContent(ModItems.cell_deuterium, 0, 1, 8, 5),
			new WeightedRandomChestContent(ModItems.cell_tritium, 0, 1, 8, 5),
			new WeightedRandomChestContent(ModItems.cell_antimatter, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.powder_neodymium, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.powder_niobium, 0, 1, 1, 1),
			new WeightedRandomChestContent(Item.getItemFromBlock(ModBlocks.fusion_conductor), 0, 2, 4, 5),
			new WeightedRandomChestContent(Item.getItemFromBlock(ModBlocks.fusion_heater), 0, 1, 3, 5),
			new WeightedRandomChestContent(Item.getItemFromBlock(ModBlocks.reactor_element), 0, 1, 2, 5),
			new WeightedRandomChestContent(Item.getItemFromBlock(ModBlocks.block_tungsten), 0, 3, 8, 5),
			new WeightedRandomChestContent(Item.getItemFromBlock(ModBlocks.red_wire_coated), 0, 4, 8, 5),
			new WeightedRandomChestContent(Item.getItemFromBlock(ModBlocks.red_cable), 0, 8, 16, 5) };

	private static WeightedRandomChestContent[] powder = new WeightedRandomChestContent[] {
			new WeightedRandomChestContent(ModItems.powder_neptunium, 0, 1, 32, 1),
			new WeightedRandomChestContent(ModItems.powder_iodine, 0, 1, 32, 1),
			new WeightedRandomChestContent(ModItems.powder_thorium, 0, 1, 32, 1),
			new WeightedRandomChestContent(ModItems.powder_astatine, 0, 1, 32, 1),
			new WeightedRandomChestContent(ModItems.powder_neodymium, 0, 1, 32, 1),
			new WeightedRandomChestContent(ModItems.powder_caesium, 0, 1, 32, 1),
			new WeightedRandomChestContent(ModItems.powder_strontium, 0, 1, 32, 1),
			new WeightedRandomChestContent(ModItems.powder_cobalt, 0, 1, 32, 1),
			new WeightedRandomChestContent(ModItems.powder_bromine, 0, 1, 32, 1),
			new WeightedRandomChestContent(ModItems.powder_niobium, 0, 1, 32, 1),
			new WeightedRandomChestContent(ModItems.powder_tennessine, 0, 1, 32, 1),
			new WeightedRandomChestContent(ModItems.powder_cerium, 0, 1, 32, 1) };

	private static WeightedRandomChestContent[] vault1 = new WeightedRandomChestContent[] {
			new WeightedRandomChestContent(Items.gold_ingot, 0, 3, 14, 1),
			new WeightedRandomChestContent(ModItems.pin, 0, 8, 8, 1),
			new WeightedRandomChestContent(ModItems.gun_calamity, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.bottle_quantum, 0, 1, 3, 1),
			new WeightedRandomChestContent(ModItems.ingot_advanced_alloy, 0, 4, 12, 1),
			new WeightedRandomChestContent(ModItems.ammo_50bmg, 0, 24, 48, 1),
			new WeightedRandomChestContent(ModItems.circuit_red_copper, 0, 6, 12, 1),
			new WeightedRandomChestContent(ModItems.gas_mask_m65, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.grenade_if_he, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.grenade_if_incendiary, 0, 1, 1, 1),
			new WeightedRandomChestContent(Items.diamond, 0, 1, 2, 1) };

	private static WeightedRandomChestContent[] vault2 = new WeightedRandomChestContent[] {
			new WeightedRandomChestContent(ModItems.ingot_desh, 0, 2, 6, 1),
			new WeightedRandomChestContent(ItemBattery.getFullBattery(ModItems.battery_advanced_cell_4), 1, 1, 1),
			new WeightedRandomChestContent(ModItems.powder_desh_mix, 0, 1, 5, 1),
			new WeightedRandomChestContent(Items.diamond, 0, 3, 6, 1),
			new WeightedRandomChestContent(ModItems.ammo_nuke, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.ammo_container, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.grenade_nuclear, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.grenade_smart, 0, 1, 6, 1),
			new WeightedRandomChestContent(ModItems.powder_yellowcake, 0, 16, 24, 1),
			new WeightedRandomChestContent(ModItems.gun_uzi, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.gun_uzi_silencer, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.clip_uzi, 0, 1, 3, 1),
			new WeightedRandomChestContent(ModItems.circuit_red_copper, 0, 12, 16, 1),
			new WeightedRandomChestContent(ModItems.circuit_gold, 0, 2, 6, 1) };

	private static WeightedRandomChestContent[] vault3 = new WeightedRandomChestContent[] {
			new WeightedRandomChestContent(ModItems.ingot_desh, 0, 6, 16, 1),
			new WeightedRandomChestContent(ItemBattery.getFullBattery(ModItems.battery_lithium), 1, 1, 1),
			new WeightedRandomChestContent(ModItems.powder_power, 0, 1, 5, 1),
			new WeightedRandomChestContent(ModItems.sat_chip, 0, 1, 1, 1),
			new WeightedRandomChestContent(Items.diamond, 0, 5, 9, 1),
			new WeightedRandomChestContent(ModItems.warhead_nuclear, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.ammo_nuke, 0, 1, 3, 1),
			new WeightedRandomChestContent(ModItems.ammo_container, 0, 1, 4, 1),
			new WeightedRandomChestContent(ModItems.grenade_nuclear, 0, 1, 2, 1),
			new WeightedRandomChestContent(ModItems.grenade_mirv, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.powder_yellowcake, 0, 26, 42, 1),
			new WeightedRandomChestContent(ModItems.ingot_u235, 0, 3, 6, 1),
			new WeightedRandomChestContent(ModItems.gun_fatman, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.gun_revolver_pip, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.clip_revolver_pip, 0, 2, 4, 1),
			new WeightedRandomChestContent(ModItems.circuit_red_copper, 0, 18, 32, 1),
			new WeightedRandomChestContent(ModItems.circuit_gold, 0, 6, 12, 1),
			new WeightedRandomChestContent(ModItems.nugget_schrabidium, 0, 6, 12, 1) };

	private static WeightedRandomChestContent[] vault4 = new WeightedRandomChestContent[] {
			new WeightedRandomChestContent(ModItems.ammo_container, 0, 3, 6, 1),
			new WeightedRandomChestContent(ModItems.clip_fatman, 0, 2, 3, 1),
			new WeightedRandomChestContent(ModItems.gun_mirv_ammo, 0, 2, 3, 1),
			new WeightedRandomChestContent(ModItems.gun_mirv, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.gun_fatman, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.gun_proto, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.gun_b92, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.ingot_combine_steel, 0, 16, 28, 1),
			new WeightedRandomChestContent(ModItems.nugget_schrabidium, 0, 8, 18, 1),
			new WeightedRandomChestContent(ModItems.man_core, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.boy_kit, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.nuke_starter_kit, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.weaponized_starblaster_cell, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.warhead_mirv, 0, 1, 1, 1),
			new WeightedRandomChestContent(ItemBattery.getFullBattery(ModItems.battery_schrabidium_cell), 1, 1, 1),
			new WeightedRandomChestContent(ModItems.powder_nitan_mix, 0, 16, 32, 1) };
	
	/**
	 * @param i
	 * @return WeightedRandomChestContent array with custom loot
	 * 
	 *         case 1: modGeneric loot (ingots, few blocks)
	 *         case 2: antenna loot (spare parts, electronics)
	 *         case 3: expensive loot (revolers, circuits, schrabidium nuggets)
	 *         case 4: nukeTrash loot (U238 and Pu240 nuggets and rods)
	 *         case 5: nuclear loot (U235 and Pu239 nuggets and rods, fuel rods)
	 *         case 6: vertibrid loot (T45 power armor, fusion cores, circuits, nuclear material)
	 *         case 7: missile loot (missiles, designators, missile parts)
	 *         case 8: spaceship loot (reactor elements, super conductors)
	 *         case 9: powder loot (secret chest with the five powders for NITAN)
	 **/

	public static WeightedRandomChestContent[] getLoot(int i) {
		switch (i) {
		case 1:
			return modGeneric;
		case 2:
			return antenna;
		case 3:
			return expensive;
		case 4:
			return nukeTrash;
		case 5:
			return nuclear;
		case 6:
			return vertibird;
		case 7:
			return missile;
		case 8:
			return spaceship;
		case 9:
			return powder;
		case 10:
			return vault1;
		case 11:
			return vault2;
		case 12:
			return vault3;
		case 13:
			return vault4;
		}

		return null;
	}
}

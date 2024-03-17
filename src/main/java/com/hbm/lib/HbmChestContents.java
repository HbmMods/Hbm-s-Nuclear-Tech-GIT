package com.hbm.lib;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.material.Mats;
import com.hbm.items.ItemAmmoEnums.Ammo357Magnum;
import com.hbm.items.ItemAmmoEnums.AmmoFatman;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemBreedingRod.BreedingRodType;
import com.hbm.items.machine.ItemZirnoxRod.EnumZirnoxType;
import com.hbm.items.special.ItemBookLore;
import com.hbm.items.tool.ItemBlowtorch;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;

public class HbmChestContents {

	public static WeightedRandomChestContent[] modGeneric = new WeightedRandomChestContent[] {
			weighted(Items.bread, 0, 1, 5, 8),
			weighted(ModItems.twinkie, 0, 1, 3, 6),
			weighted(Items.iron_ingot, 0, 2, 6, 10),
			weighted(ModItems.ingot_steel, 0, 2, 5, 7),
			weighted(ModItems.ingot_beryllium, 0, 1, 2, 4),
			weighted(ModItems.ingot_titanium, 0, 1, 1, 3),
			weighted(ModItems.circuit_targeting_tier1, 0, 1, 1, 5),
			weighted(ModItems.gun_revolver, 0, 1, 1, 3),
			weighted(ModItems.ammo_357, Ammo357Magnum.LEAD.ordinal(), 2, 6, 4),
			weighted(ModItems.gun_kit_1, 0, 1, 3, 4),
			weighted(ModItems.gun_lever_action, 0, 1, 1, 1),
			weighted(ModItems.ammo_20gauge, 0, 2, 6, 3),
			weighted(ModItems.casing_9, 0, 4, 10, 3),
			weighted(ModItems.casing_50, 0, 4, 10, 3),
			weighted(ModItems.cordite, 0, 4, 6, 5),
			weighted(ModItems.battery_generic, 0, 1, 1, 4),
			weighted(ModItems.battery_advanced, 0, 1, 1, 2),
			weighted(ModItems.scrap, 0, 1, 3, 10),
			weighted(ModItems.dust, 0, 2, 4, 9),
			weighted(ModItems.bottle_opener, 0, 1, 1, 2),
			weighted(ModItems.bottle_nuka, 0, 1, 3, 4),
			weighted(ModItems.bottle_cherry, 0, 1, 1, 2),
			weighted(ModItems.stealth_boy, 0, 1, 1, 1),
			weighted(ModItems.cap_nuka, 0, 1, 15, 7),
			weighted(ModItems.canister_full, Fluids.DIESEL.getID(), 1, 2, 2),
			weighted(ModItems.canister_full, Fluids.BIOFUEL.getID(), 1, 2, 3),
			weighted(ModItems.gas_mask_m65, 60, 1, 1, 2),
			weighted(ModItems.gas_mask_filter, 0, 1, 1, 3)  };
	
	public static WeightedRandomChestContent[] machineParts = new WeightedRandomChestContent[] {
			weighted(ModItems.plate_steel, 0, 1, 5, 5),
			weighted(ModItems.hull_big_steel, 0, 1, 2, 2),
			weighted(ModItems.hull_small_steel, 0, 1, 3, 3),
			weighted(ModItems.plate_polymer, 0, 1, 6, 5),
			weighted(ModItems.bolt, Mats.MAT_STEEL.id, 4, 16, 3),
			weighted(ModItems.bolt, Mats.MAT_TUNGSTEN.id, 4, 16, 3),
			weighted(ModItems.board_copper, 0, 1, 2, 4),
			weighted(ModItems.coil_tungsten, 0, 1, 2, 5),
			weighted(ModItems.motor, 0, 1, 2, 4),
			weighted(ModItems.tank_steel, 0, 1, 2, 3),
			weighted(ModItems.coil_copper, 0, 1, 3, 4),
			weighted(ModItems.coil_copper_torus, 0, 1, 2, 3),
			weighted(ModItems.wire_red_copper, 0, 1, 8, 5),
			weighted(ModItems.piston_selenium, 0, 1, 1, 3),
			weighted(ModItems.battery_advanced_cell, 0, 1, 1, 3),
			weighted(ModItems.circuit_raw, 0, 1, 3, 5),
			weighted(ModItems.circuit_aluminium, 0, 1, 2, 4),
			weighted(ModItems.circuit_copper, 0, 1, 1, 3),
			weighted(ModItems.circuit_red_copper, 0, 1, 1, 2),
			weighted(ModItems.blade_titanium, 0, 1, 8, 1)
	};

	public static WeightedRandomChestContent[] antenna = new WeightedRandomChestContent[] {
			weighted(ModItems.twinkie, 0, 1, 3, 4),
			weighted(ModItems.ingot_steel, 0, 1, 2, 7),
			weighted(ModItems.ingot_red_copper, 0, 1, 1, 4),
			weighted(ModItems.ingot_titanium, 0, 1, 3, 5),
			weighted(ModItems.wire_red_copper, 0, 2, 3, 7),
			weighted(ModItems.circuit_targeting_tier1, 0, 1, 1, 4),
			weighted(ModItems.circuit_copper, 0, 1, 1, 4),
			weighted(ModItems.battery_generic, 0, 1, 1, 4),
			weighted(ModItems.battery_advanced, 0, 1, 1, 3),
			weighted(ModItems.powder_iodine, 0, 1, 1, 1),
			weighted(ModItems.powder_bromine, 0, 1, 1, 1),
			weighted(Item.getItemFromBlock(ModBlocks.steel_poles), 0, 1, 4, 8),
			weighted(Item.getItemFromBlock(ModBlocks.steel_scaffold), 0, 1, 3, 8),
			weighted(Item.getItemFromBlock(ModBlocks.pole_top), 0, 1, 1, 4),
			weighted(Item.getItemFromBlock(ModBlocks.pole_satellite_receiver), 0, 1, 1, 7),
			weighted(ModItems.scrap, 0, 1, 3, 10),
			weighted(ModItems.dust, 0, 2, 4, 9),
			weighted(ModItems.bottle_opener, 0, 1, 1, 2),
			weighted(ModItems.bottle_nuka, 0, 1, 3, 4),
			weighted(ModItems.bottle_cherry, 0, 1, 1, 2),
			weighted(ModItems.stealth_boy, 0, 1, 1, 1),
			weighted(ModItems.cap_nuka, 0, 1, 15, 7),
			weighted(ModItems.bomb_caller, 0, 1, 1, 1),
			weighted(ModItems.gas_mask_filter, 0, 1, 1, 2) };

	public static WeightedRandomChestContent[] expensive = new WeightedRandomChestContent[] {
			weighted(ModItems.chlorine_pinwheel, 0, 1, 1, 1),
			weighted(ModItems.circuit_targeting_tier3, 0, 1, 1, 4),
			weighted(ModItems.circuit_gold, 0, 1, 2, 3),
			weighted(ModItems.circuit_targeting_tier4, 0, 1, 1, 2),
			weighted(ModItems.gun_kit_1, 0, 1, 3, 6),
			weighted(ModItems.gun_kit_2, 0, 1, 2, 3),
			weighted(ModItems.gun_rpg, 0, 1, 1, 4),
			weighted(ModItems.ammo_rocket, 0, 1, 4, 5),
			weighted(ModItems.gun_fatman, 0, 1, 1, 1),
			weighted(ModItems.ammo_nuke, AmmoFatman.SAFE.ordinal(), 1, 2, 1),
			weighted(ModItems.ammo_nuke, AmmoFatman.LOW.ordinal(), 1, 2, 1),
			weighted(ModItems.ammo_nuke, AmmoFatman.PUMPKIN.ordinal(), 1, 2, 1),
			weighted(ModItems.grenade_nuclear, 0, 1, 1, 2),
			weighted(ModItems.grenade_smart, 0, 1, 3, 3),
			weighted(ModItems.grenade_mirv, 0, 1, 1, 2),
			weighted(ModItems.stealth_boy, 0, 1, 1, 2),
			weighted(ModItems.battery_advanced, 0, 1, 1, 3),
			weighted(ModItems.battery_advanced_cell, 0, 1, 1, 2),
			weighted(ModItems.battery_schrabidium, 0, 1, 1, 1),
			weighted(ModItems.syringe_awesome, 0, 1, 1, 1),
			weighted(ModItems.crate_caller, 0, 1, 1, 3),
			weighted(ModItems.fusion_core, 0, 1, 1, 4),
			weighted(ModItems.bottle_nuka, 0, 1, 3, 6),
			weighted(ModItems.bottle_quantum, 0, 1, 1, 3),
			weighted(Item.getItemFromBlock(ModBlocks.red_barrel), 0, 1, 1, 6),
			weighted(ModItems.canister_full, Fluids.DIESEL.getID(), 1, 2, 2),
			weighted(ModItems.canister_full, Fluids.BIOFUEL.getID(), 1, 2, 3),
			weighted(ModItems.gas_mask_m65, 60, 1, 1, 5),
			weighted(ModItems.bomb_caller, 0, 1, 1, 2),
			weighted(ModItems.bomb_caller, 1, 1, 1, 1),
			weighted(ModItems.bomb_caller, 2, 1, 1, 1),
			weighted(ModItems.gas_mask_filter, 0, 1, 1, 4),
			weighted(ModItems.journal_pip, 0, 1, 1, 1),
			weighted(ModItems.journal_bj, 0, 1, 1, 1) };

	public static WeightedRandomChestContent[] nukeTrash = new WeightedRandomChestContent[] {
			weighted(ModItems.nugget_u238, 0, 3, 12, 5),
			weighted(ModItems.nugget_pu240, 0, 3, 8, 5),
			weighted(ModItems.nugget_neptunium, 0, 1, 4, 3),
			weighted(ModItems.rod, BreedingRodType.U238.ordinal(), 1, 1, 3),
			weighted(ModItems.rod_dual, BreedingRodType.U238.ordinal(), 1, 1, 3),
			weighted(ModItems.rod_quad, BreedingRodType.U238.ordinal(), 1, 1, 3),
			weighted(ModItems.bottle_quantum, 0, 1, 1, 1),
			weighted(ModItems.gas_mask_m65, 60, 1, 1, 5),
			weighted(ModItems.hazmat_kit, 0, 1, 1, 1),
			weighted(ModItems.gas_mask_filter, 0, 1, 1, 5),
			weighted(Item.getItemFromBlock(ModBlocks.yellow_barrel), 0, 1, 1, 2) };
	
	public static WeightedRandomChestContent[] nuclearFuel = new WeightedRandomChestContent[] {
			weighted(ModItems.billet_uranium, 0, 1, 4, 4),
			weighted(ModItems.billet_th232, 0, 1, 3, 3),
			weighted(ModItems.billet_uranium_fuel, 0, 1, 3, 5),
			weighted(ModItems.billet_mox_fuel, 0, 1, 3, 5),
			weighted(ModItems.billet_thorium_fuel, 0, 1, 3, 3),
			weighted(ModItems.billet_ra226be, 0, 1, 2, 2),
			weighted(ModItems.billet_beryllium, 0, 1, 1, 1),
			weighted(ModItems.nugget_u233, 0, 1, 1, 1),
			weighted(ModItems.nugget_uranium_fuel, 0, 1, 1, 1),
			weighted(ModItems.rod_zirnox_empty, 0, 1, 3, 3),
			weighted(ModItems.ingot_graphite, 0, 1, 4, 3),
			weighted(ModItems.pile_rod_uranium, 0, 2, 5, 3),
			weighted(ModItems.pile_rod_source, 0, 1, 2, 2),
			weighted(ModItems.reacher, 0, 1, 1, 3),
			weighted(ModItems.screwdriver, 0, 1, 1, 2),
	};

	public static WeightedRandomChestContent[] nuclear = new WeightedRandomChestContent[] {
			weighted(ModItems.nugget_u235, 0, 3, 12, 5),
			weighted(ModItems.nugget_pu238, 0, 3, 12, 5),
			weighted(ModItems.nugget_ra226, 0, 3, 6, 5),
			weighted(ModItems.rod, BreedingRodType.U235.ordinal(), 1, 1, 3),
			weighted(ModItems.rod_dual, BreedingRodType.U235.ordinal(), 1, 1, 3),
			weighted(ModItems.rod_quad, BreedingRodType.U235.ordinal(), 1, 1, 3),
			weighted(ModItems.rod_zirnox, EnumZirnoxType.URANIUM_FUEL.ordinal(), 1, 1, 4),
			weighted(ModItems.rod_zirnox, EnumZirnoxType.MOX_FUEL.ordinal(), 1, 1, 4),
			weighted(ModItems.rod_zirnox, EnumZirnoxType.LITHIUM.ordinal(), 1, 1, 3),
			weighted(ModItems.rod_zirnox, EnumZirnoxType.THORIUM_FUEL.ordinal(), 1, 1, 3),
			weighted(ModItems.rod_dual, BreedingRodType.THF.ordinal(), 1, 1, 3),
			weighted(ModItems.rod_zirnox_tritium, 0, 1, 1, 1),
			weighted(ModItems.rod_zirnox, EnumZirnoxType.U233_FUEL.ordinal(), 1, 1, 1),
			weighted(ModItems.rod_zirnox, EnumZirnoxType.U235_FUEL.ordinal(), 1, 1, 1),
			weighted(ModItems.pellet_rtg, 0, 1, 1, 3),
			weighted(ModItems.powder_thorium, 0, 1, 1, 1),
			weighted(ModItems.powder_neptunium, 0, 1, 1, 1),
			weighted(ModItems.powder_strontium, 0, 1, 1, 1),
			weighted(ModItems.powder_cobalt, 0, 1, 1, 1),
			weighted(ModItems.bottle_quantum, 0, 1, 1, 1),
			weighted(ModItems.gas_mask_m65, 60, 1, 1, 5),
			weighted(ModItems.hazmat_kit, 0, 1, 1, 2),
			weighted(ModItems.gas_mask_filter, 0, 1, 1, 5),
			weighted(Item.getItemFromBlock(ModBlocks.yellow_barrel), 0, 1, 3, 3) };

	public static WeightedRandomChestContent[] vertibird = new WeightedRandomChestContent[] {
			weighted(ModItems.t45_helmet, 0, 1, 1, 15),
			weighted(ModItems.t45_plate, 0, 1, 1, 15),
			weighted(ModItems.t45_legs, 0, 1, 1, 15),
			weighted(ModItems.t45_boots, 0, 1, 1, 15),
			weighted(ModItems.t45_kit, 0, 1, 1, 3),
			weighted(ModItems.fusion_core, 0, 1, 1, 10),
			weighted(ModItems.gun_revolver, 0, 1, 1, 4),
			weighted(ModItems.ammo_357, Ammo357Magnum.LEAD.ordinal(), 1, 24, 4),
			weighted(ModItems.gun_kit_1, 0, 2, 3, 4),
			weighted(ModItems.gun_rpg, 0, 1, 1, 3),
			weighted(ModItems.ammo_rocket, 0, 1, 6, 3),
			weighted(ModItems.rod, BreedingRodType.U235.ordinal(), 1, 1, 2),
			weighted(ModItems.billet_uranium_fuel, 0, 1, 1, 2),
			weighted(ModItems.ingot_uranium_fuel, 0, 1, 1, 2),
			weighted(ModItems.ammo_nuke, AmmoFatman.SAFE.ordinal(), 1, 2, 1),
			weighted(ModItems.gun_fatman, 0, 1, 1, 1),
			weighted(ModItems.bottle_nuka, 0, 1, 3, 6),
			weighted(ModItems.bottle_quantum, 0, 1, 1, 3),
			weighted(ModItems.stealth_boy, 0, 1, 1, 7),
			weighted(ModItems.crate_caller, 0, 1, 1, 3),
			weighted(ModItems.gas_mask_m65, 0, 1, 1, 5),
			weighted(ModItems.gas_mask_filter, 0, 1, 1, 5),
			weighted(ModItems.grenade_nuclear, 0, 1, 2, 2),
			weighted(ModItems.bomb_caller, 0, 1, 1, 1),
			weighted(ModItems.bomb_caller, 1, 1, 1, 1),
			weighted(ModItems.bomb_caller, 2, 1, 1, 2) };

	public static WeightedRandomChestContent[] missile = new WeightedRandomChestContent[] {
			weighted(ModItems.missile_generic, 0, 1, 1, 4),
			weighted(ModItems.missile_incendiary, 0, 1, 1, 4),
			weighted(ModItems.gas_mask_m65, 0, 1, 1, 5),
			weighted(ModItems.battery_advanced, 0, 1, 1, 5),
			weighted(ModItems.designator, 0, 1, 1, 5),
			weighted(ModItems.crate_caller, 0, 1, 1, 1),
			weighted(ModItems.thruster_small, 0, 1, 1, 5),
			weighted(ModItems.thruster_medium, 0, 1, 1, 4),
			weighted(ModItems.fuel_tank_small, 0, 1, 1, 5),
			weighted(ModItems.fuel_tank_medium, 0, 1, 1, 4),
			weighted(ModItems.bomb_caller, 0, 1, 1, 1),
			weighted(ModItems.bomb_caller, 3, 1, 1, 1),
			weighted(ModItems.bottle_nuka, 0, 1, 3, 10) };

	public static WeightedRandomChestContent[] spaceship = new WeightedRandomChestContent[] {
			weighted(ModItems.battery_advanced, 0, 1, 1, 5),
			weighted(ModItems.ingot_advanced_alloy, 0, 2, 16, 5),
			weighted(ModItems.wire_advanced_alloy, 0, 8, 32, 5),
			weighted(ModItems.coil_advanced_alloy, 0, 2, 16, 5),
			weighted(ModItems.cell_deuterium, 0, 1, 8, 5),
			weighted(ModItems.cell_tritium, 0, 1, 8, 5),
			weighted(ModItems.cell_antimatter, 0, 1, 1, 1),
			weighted(ModItems.powder_neodymium, 0, 1, 1, 1),
			weighted(ModItems.powder_niobium, 0, 1, 1, 1),
			weighted(Item.getItemFromBlock(ModBlocks.fusion_conductor), 0, 2, 4, 5),
			weighted(Item.getItemFromBlock(ModBlocks.fusion_heater), 0, 1, 3, 5),
			weighted(Item.getItemFromBlock(ModBlocks.pwr_fuel), 0, 1, 2, 5),
			weighted(Item.getItemFromBlock(ModBlocks.block_tungsten), 0, 3, 8, 5),
			weighted(Item.getItemFromBlock(ModBlocks.red_wire_coated), 0, 4, 8, 5),
			weighted(Item.getItemFromBlock(ModBlocks.red_cable), 0, 8, 16, 5) };

	public static WeightedRandomChestContent[] powder = new WeightedRandomChestContent[] {
			weighted(ModItems.powder_neptunium, 0, 1, 32, 1),
			weighted(ModItems.powder_iodine, 0, 1, 32, 1),
			weighted(ModItems.powder_thorium, 0, 1, 32, 1),
			weighted(ModItems.powder_astatine, 0, 1, 32, 1),
			weighted(ModItems.powder_neodymium, 0, 1, 32, 1),
			weighted(ModItems.powder_caesium, 0, 1, 32, 1),
			weighted(ModItems.powder_strontium, 0, 1, 32, 1),
			weighted(ModItems.powder_cobalt, 0, 1, 32, 1),
			weighted(ModItems.powder_bromine, 0, 1, 32, 1),
			weighted(ModItems.powder_niobium, 0, 1, 32, 1),
			weighted(ModItems.powder_tennessine, 0, 1, 32, 1),
			weighted(ModItems.powder_cerium, 0, 1, 32, 1) };

	public static WeightedRandomChestContent[] vault1 = new WeightedRandomChestContent[] {
			weighted(Items.gold_ingot, 0, 3, 14, 1),
			weighted(ModItems.gun_uac_pistol, 0, 1, 1, 2),
			weighted(ModItems.pin, 0, 8, 8, 1),
			weighted(ModItems.gun_calamity, 0, 1, 1, 1),
			weighted(ModItems.bottle_quantum, 0, 1, 3, 1),
			weighted(ModItems.ingot_advanced_alloy, 0, 4, 12, 1),
			weighted(ModItems.ammo_50bmg, 0, 24, 48, 1),
			weighted(ModItems.ammo_45, 0, 48, 64, 2),
			weighted(ModItems.circuit_red_copper, 0, 6, 12, 1),
			weighted(ModItems.gas_mask_m65, 0, 1, 1, 1),
			weighted(ModItems.grenade_if_he, 0, 1, 1, 1),
			weighted(ModItems.grenade_if_incendiary, 0, 1, 1, 1),
			weighted(Items.diamond, 0, 1, 2, 1) };

	public static WeightedRandomChestContent[] vault2 = new WeightedRandomChestContent[] {
			weighted(ModItems.ingot_desh, 0, 2, 6, 1),
			weighted(ModItems.battery_advanced_cell_4, 0, 1, 1, 1),
			weighted(ModItems.powder_desh_mix, 0, 1, 5, 1),
			weighted(Items.diamond, 0, 3, 6, 1),
			weighted(ModItems.ammo_nuke, 0, 1, 1, 1),
			weighted(ModItems.ammo_container, 0, 1, 1, 1),
			weighted(ModItems.grenade_nuclear, 0, 1, 1, 1),
			weighted(ModItems.grenade_smart, 0, 1, 6, 1),
			weighted(ModItems.powder_yellowcake, 0, 16, 24, 1),
			weighted(ModItems.gun_uzi, 0, 1, 1, 1),
			weighted(ModItems.gun_uzi_silencer, 0, 1, 1, 1),
			weighted(ModItems.clip_uzi, 0, 1, 3, 1),
			weighted(ModItems.circuit_red_copper, 0, 12, 16, 1),
			weighted(ModItems.circuit_gold, 0, 2, 6, 1) };

	public static WeightedRandomChestContent[] vault3 = new WeightedRandomChestContent[] {
			weighted(ModItems.ingot_desh, 0, 6, 16, 1),
			weighted(ModItems.battery_lithium, 0, 1, 1, 1),
			weighted(ModItems.powder_power, 0, 1, 5, 1),
			weighted(ModItems.sat_chip, 0, 1, 1, 1),
			weighted(Items.diamond, 0, 5, 9, 1),
			weighted(ModItems.warhead_nuclear, 0, 1, 1, 1),
			weighted(ModItems.ammo_nuke, 0, 1, 3, 1),
			weighted(ModItems.ammo_container, 0, 1, 4, 1),
			weighted(ModItems.grenade_nuclear, 0, 1, 2, 1),
			weighted(ModItems.grenade_mirv, 0, 1, 1, 1),
			weighted(ModItems.powder_yellowcake, 0, 26, 42, 1),
			weighted(ModItems.ingot_u235, 0, 3, 6, 1),
			weighted(ModItems.gun_fatman, 0, 1, 1, 1),
			weighted(ModItems.gun_revolver_pip, 0, 1, 1, 1),
			weighted(ModItems.clip_revolver_pip, 0, 2, 4, 1),
			weighted(ModItems.circuit_red_copper, 0, 18, 32, 1),
			weighted(ModItems.circuit_gold, 0, 6, 12, 1) };

	public static WeightedRandomChestContent[] vault4 = new WeightedRandomChestContent[] {
			weighted(ModItems.ammo_container, 0, 3, 6, 1),
			weighted(ModItems.clip_fatman, 0, 2, 3, 1),
			weighted(ModItems.ammo_nuke, AmmoFatman.MIRV.ordinal(), 2, 3, 1),
			weighted(ModItems.gun_mirv, 0, 1, 1, 1),
			weighted(ModItems.gun_fatman, 0, 1, 1, 1),
			weighted(ModItems.gun_proto, 0, 1, 1, 1),
			weighted(ModItems.gun_b92, 0, 1, 1, 1),
			weighted(ModItems.ingot_combine_steel, 0, 16, 28, 1),
			weighted(ModItems.man_core, 0, 1, 1, 1),
			weighted(ModItems.boy_kit, 0, 1, 1, 1),
			weighted(ModItems.nuke_starter_kit, 0, 1, 1, 1),
			weighted(ModItems.weaponized_starblaster_cell, 0, 1, 1, 1),
			weighted(ModItems.warhead_mirv, 0, 1, 1, 1),
			weighted(ModItems.battery_schrabidium_cell, 0, 1, 1, 1),
			weighted(ModItems.powder_nitan_mix, 0, 16, 32, 1) };	
	
	public static WeightedRandomChestContent[] officeTrash = new WeightedRandomChestContent[] {
			//Meta, Min amount, Max amount, Weight
			weighted(Items.paper, 0, 1, 12, 10),
			weighted(Items.book, 0, 1, 3, 4),
			weighted(ModItems.twinkie, 0, 1, 2, 6),
			weighted(ModItems.coffee, 0, 1, 1, 4),
			weighted(ModItems.flame_politics, 0, 1, 1, 2),
			weighted(ModItems.ring_pull, 0, 1, 1, 4),
			weighted(ModItems.can_empty, 0, 1, 1, 2),
			weighted(ModItems.can_creature, 0, 1, 2, 2),
			weighted(ModItems.can_smart, 0, 1, 3, 2),
			weighted(ModItems.can_mrsugar, 0, 1, 2, 2),
			weighted(ModItems.cap_nuka, 0, 1, 16, 2),
			weighted(ModItems.book_guide, 3, 1, 1, 1),
			weighted(Item.getItemFromBlock(ModBlocks.deco_computer), 0, 1, 1, 1)};
	
	public static WeightedRandomChestContent[] filingCabinet = new WeightedRandomChestContent[] {
			weighted(Items.paper, 0, 1, 12, 240),
			weighted(Items.book, 0, 1, 3, 90),
			weighted(Items.map, 0, 1, 1, 50),
			weighted(Items.writable_book, 0, 1, 1, 30),
			weighted(ModItems.cigarette, 0, 1, 16, 20),
			weighted(ModItems.toothpicks, 0, 1, 16, 10),
			weighted(ModItems.dust, 0, 1, 1, 40),
			weighted(ModItems.dust_tiny, 0, 1, 3, 75),
			weighted(ModItems.ink, 0, 1, 1, 1), //make that mf rare; 1:555 weight
	};
	
	public static WeightedRandomChestContent[] solidFuel = new WeightedRandomChestContent[] {
			weighted(ModItems.solid_fuel, 0, 1, 5, 1),
			weighted(ModItems.solid_fuel_presto, 0, 1, 2, 2),
			weighted(ModItems.ball_dynamite, 0, 1, 4, 2),
			weighted(Items.redstone, 0, 1, 3, 1),
			weighted(ModItems.niter, 0, 1, 3, 1)
	};
	
	public static WeightedRandomChestContent[] labVault = new WeightedRandomChestContent[] {
			weighted(ItemBlowtorch.getEmptyTool(ModItems.blowtorch), 1, 1, 4),
			weighted(ModItems.chemistry_set, 0, 1, 1, 15),
			weighted(ModItems.screwdriver, 0, 1, 1, 10),
			weighted(ModItems.nugget_mercury, 0, 1, 1, 3),
			weighted(ModItems.morning_glory, 0, 1, 1, 1),
			weighted(ModItems.filter_coal, 0, 1, 1, 5),
			weighted(ModItems.dust, 0, 1, 3, 25),
			weighted(Items.paper, 0, 1, 2, 15),
			weighted(ModItems.cell_empty, 0, 1, 1, 5),
			weighted(Items.glass_bottle, 0, 1, 1, 5),
			weighted(ModItems.powder_iodine, 0, 1, 1, 1),
			weighted(ModItems.powder_bromine, 0, 1, 1, 1),
			weighted(ModItems.powder_cobalt, 0, 1, 1, 1),
			weighted(ModItems.powder_neodymium, 0, 1, 1, 1),
			weighted(ModItems.powder_boron, 0, 1, 1, 1),
	};
	
	public static WeightedRandomChestContent[] lockersVault = new WeightedRandomChestContent[] {
			weighted(ModItems.robes_helmet, 0, 1, 1, 1),
			weighted(ModItems.robes_plate, 0, 1, 1, 1),
			weighted(ModItems.robes_legs, 0, 1, 1, 1),
			weighted(ModItems.robes_boots, 0, 1, 1, 1),
			weighted(ModItems.jackt, 0, 1, 1, 1),
			weighted(ModItems.jackt2, 0, 1, 1, 1),
			weighted(ModItems.gas_mask_m65, 0, 1, 1, 2),
			weighted(ModItems.gas_mask_mono, 0, 1, 1, 2),
			weighted(ModItems.goggles, 0, 1, 1, 2),
			weighted(ModItems.gas_mask_filter, 0, 1, 1, 4),
			weighted(ModItems.flame_opinion, 0, 1, 3, 5),
			weighted(ModItems.flame_conspiracy, 0, 1, 3, 5),
			weighted(ModItems.flame_politics, 0, 1, 3, 5),
			weighted(ModItems.cigarette, 0, 1, 8, 5),
			weighted(ModItems.armor_polish, 0, 1, 1, 3),
			weighted(ModItems.gun_kit_1, 0, 1, 1, 3),
			weighted(ModItems.rag, 0, 1, 3, 5),
			weighted(Items.paper, 0, 1, 6, 7),
			weighted(Items.clock, 0, 1, 1, 3),
			weighted(Items.book, 0, 1, 5, 10),
			weighted(Items.experience_bottle, 0, 1, 3, 1),
	};
	
	public static WeightedRandomChestContent weighted(Item item, int meta, int min, int max, int weight) { return new WeightedRandomChestContent(item, meta, min, max, weight); }
	public static WeightedRandomChestContent weighted(ItemStack item, int min, int max, int weight) { return new WeightedRandomChestContent(item, min, max, weight); }
	
	/** ITEMBOOKLORE SHIT */
	//one downside of all this huge flexibility, make a wrapper if it's too annoying
	public static ItemStack generateOfficeBook(Random rand) { //TODO rework this lore in general
		String key;
		int pages;
		switch(rand.nextInt(5)) {
		case 0:
			key = "resignation_note"; pages = 3; break;
		case 1:
			key = "memo_stocks"; pages = 1; break;
		case 2:
			key = "memo_schrab_gsa"; pages = 2; break;
		case 3:
			key = "memo_schrab_rd"; pages = 4; break;
		case 4:
			key = "memo_schrab_nuke"; pages = 3; break;
		default:
			return null;
		}
		
		return ItemBookLore.createBook(key, pages, 0x6BC8FF, 0x0A0A0A);
	}
	
	public static ItemStack generateLabBook(Random rand) {
		String key;
		int pages;
		
		switch(rand.nextInt(5)) {
		case 0:
			key = "bf_bomb_1"; pages = 4; break;
		case 1:
			key = "bf_bomb_2"; pages = 6; break;
		case 2:
			key = "bf_bomb_3"; pages = 6; break;
		case 3:
			key = "bf_bomb_4"; pages = 5; break;
		case 4:
			key = "bf_bomb_5"; pages = 9; break;
		default:
			return null;
		}
		
		return ItemBookLore.createBook(key, pages, 0x1E1E1E, 0x46EA44);
	}
}

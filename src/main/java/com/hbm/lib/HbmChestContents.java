package com.hbm.lib;

import java.util.HashMap;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemBreedingRod.*;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.WeightedRandomChestContent;

public class HbmChestContents {

	public static WeightedRandomChestContent[] modGeneric = new WeightedRandomChestContent[] {
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
			new WeightedRandomChestContent(ModItems.canister_full, Fluids.DIESEL.getID(), 1, 2, 2),
			new WeightedRandomChestContent(ModItems.canister_full, Fluids.BIOFUEL.getID(), 1, 2, 3),
			new WeightedRandomChestContent(ModItems.gas_mask_m65, 60, 1, 1, 2),
			new WeightedRandomChestContent(ModItems.gas_mask_filter, 0, 1, 1, 3)  };
	
	public static WeightedRandomChestContent[] machineParts = new WeightedRandomChestContent[] {
			new WeightedRandomChestContent(ModItems.plate_steel, 0, 1, 5, 5),
			new WeightedRandomChestContent(ModItems.hull_big_steel, 0, 1, 2, 2),
			new WeightedRandomChestContent(ModItems.hull_small_steel, 0, 1, 3, 3),
			new WeightedRandomChestContent(ModItems.plate_polymer, 0, 1, 6, 5),
			new WeightedRandomChestContent(ModItems.bolt_tungsten, 0, 1, 4, 3),
			new WeightedRandomChestContent(ModItems.board_copper, 0, 1, 2, 4),
			new WeightedRandomChestContent(ModItems.coil_tungsten, 0, 1, 2, 5),
			new WeightedRandomChestContent(ModItems.motor, 0, 1, 2, 4),
			new WeightedRandomChestContent(ModItems.tank_steel, 0, 1, 2, 3),
			new WeightedRandomChestContent(ModItems.coil_copper, 0, 1, 3, 4),
			new WeightedRandomChestContent(ModItems.coil_copper_torus, 0, 1, 2, 3),
			new WeightedRandomChestContent(ModItems.wire_red_copper, 0, 1, 8, 5),
			new WeightedRandomChestContent(ModItems.piston_selenium, 0, 1, 1, 3),
			new WeightedRandomChestContent(ModItems.battery_advanced_cell, 0, 1, 1, 3),
			new WeightedRandomChestContent(ModItems.circuit_raw, 0, 1, 3, 5),
			new WeightedRandomChestContent(ModItems.circuit_aluminium, 0, 1, 2, 4),
			new WeightedRandomChestContent(ModItems.circuit_copper, 0, 1, 1, 3),
			new WeightedRandomChestContent(ModItems.circuit_red_copper, 0, 1, 1, 2),
			new WeightedRandomChestContent(ModItems.blade_titanium, 0, 1, 8, 1)
	};

	public static WeightedRandomChestContent[] antenna = new WeightedRandomChestContent[] {
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

	public static WeightedRandomChestContent[] expensive = new WeightedRandomChestContent[] {
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
			new WeightedRandomChestContent(ModItems.canister_full, Fluids.DIESEL.getID(), 1, 2, 2),
			new WeightedRandomChestContent(ModItems.canister_full, Fluids.BIOFUEL.getID(), 1, 2, 3),
			new WeightedRandomChestContent(ModItems.gas_mask_m65, 60, 1, 1, 5),
			new WeightedRandomChestContent(ModItems.bomb_caller, 0, 1, 1, 2),
			new WeightedRandomChestContent(ModItems.bomb_caller, 1, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.bomb_caller, 2, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.gas_mask_filter, 0, 1, 1, 4),
			new WeightedRandomChestContent(ModItems.journal_pip, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.journal_bj, 0, 1, 1, 1) };

	public static WeightedRandomChestContent[] nukeTrash = new WeightedRandomChestContent[] {
			new WeightedRandomChestContent(ModItems.nugget_u238, 0, 3, 12, 5),
			new WeightedRandomChestContent(ModItems.nugget_pu240, 0, 3, 8, 5),
			new WeightedRandomChestContent(ModItems.nugget_neptunium, 0, 1, 4, 3),
			new WeightedRandomChestContent(ModItems.rod, BreedingRodType.U238.ordinal(), 1, 1, 3),
			new WeightedRandomChestContent(ModItems.rod, BreedingRodType.NP237.ordinal(), 1, 1, 4),
			new WeightedRandomChestContent(ModItems.rod, BreedingRodType.RGP.ordinal(), 1, 1, 3),
			new WeightedRandomChestContent(ModItems.rod_dual, BreedingRodType.U238.ordinal(), 1, 1, 3),
			new WeightedRandomChestContent(ModItems.rod_dual, BreedingRodType.RGP.ordinal(), 1, 1, 3),
			new WeightedRandomChestContent(ModItems.rod_quad, BreedingRodType.U238.ordinal(), 1, 1, 3),
			new WeightedRandomChestContent(ModItems.rod_quad, BreedingRodType.RGP.ordinal(), 1, 1, 3),
			new WeightedRandomChestContent(ModItems.bottle_quantum, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.gas_mask_m65, 60, 1, 1, 5),
			new WeightedRandomChestContent(ModItems.hazmat_kit, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.gas_mask_filter, 0, 1, 1, 5),
			new WeightedRandomChestContent(Item.getItemFromBlock(ModBlocks.yellow_barrel), 0, 1, 1, 2) };
	
	public static WeightedRandomChestContent[] nuclearFuel = new WeightedRandomChestContent[] {
			new WeightedRandomChestContent(ModItems.billet_uranium, 0, 1, 4, 4),
			new WeightedRandomChestContent(ModItems.billet_pu_mix, 0, 1, 2, 4),
			new WeightedRandomChestContent(ModItems.billet_th232, 0, 1, 3, 3),
			new WeightedRandomChestContent(ModItems.billet_uranium_fuel, 0, 1, 3, 5),
			new WeightedRandomChestContent(ModItems.billet_mox_fuel, 0, 1, 3, 5),
			new WeightedRandomChestContent(ModItems.billet_plutonium_fuel, 0, 1, 3, 4),
			new WeightedRandomChestContent(ModItems.billet_thorium_fuel, 0, 1, 3, 3),
			new WeightedRandomChestContent(ModItems.billet_ra226be, 0, 1, 2, 2),
			new WeightedRandomChestContent(ModItems.billet_beryllium, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.nugget_u233, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.nugget_u235, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.nugget_pu239, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.rod_zirnox_empty, 0, 1, 3, 3),
			new WeightedRandomChestContent(ModItems.ingot_graphite, 0, 1, 4, 3),
			new WeightedRandomChestContent(ModItems.pile_rod_uranium, 0, 2, 5, 3),
			new WeightedRandomChestContent(ModItems.pile_rod_source, 0, 1, 2, 2),
			new WeightedRandomChestContent(ModItems.reacher, 0, 1, 1, 3),
			new WeightedRandomChestContent(ModItems.screwdriver, 0, 1, 1, 2),
	};

	public static WeightedRandomChestContent[] nuclear = new WeightedRandomChestContent[] {
			new WeightedRandomChestContent(ModItems.nugget_u235, 0, 3, 12, 5),
			new WeightedRandomChestContent(ModItems.nugget_pu238, 0, 3, 12, 5),
			new WeightedRandomChestContent(ModItems.nugget_pu239, 0, 3, 12, 5),
			new WeightedRandomChestContent(ModItems.rod, BreedingRodType.U235.ordinal(), 1, 1, 3),
			new WeightedRandomChestContent(ModItems.rod, BreedingRodType.PU239.ordinal(), 1, 1, 3),
			new WeightedRandomChestContent(ModItems.rod_dual, BreedingRodType.U235.ordinal(), 1, 1, 3),
			new WeightedRandomChestContent(ModItems.rod_dual, BreedingRodType.PU239.ordinal(), 1, 1, 3),
			new WeightedRandomChestContent(ModItems.rod_quad, BreedingRodType.U235.ordinal(), 1, 1, 3),
			new WeightedRandomChestContent(ModItems.rod_quad, BreedingRodType.PU239.ordinal(), 1, 1, 3),
			new WeightedRandomChestContent(ModItems.rod_zirnox_uranium_fuel, 0, 1, 1, 4),
			new WeightedRandomChestContent(ModItems.rod_zirnox_plutonium_fuel, 0, 1, 1, 4),
			new WeightedRandomChestContent(ModItems.rod_zirnox_mox_fuel, 0, 1, 1, 4),
			new WeightedRandomChestContent(ModItems.rod_zirnox_lithium, 0, 1, 1, 3),
			new WeightedRandomChestContent(ModItems.rod_zirnox_thorium_fuel, 0, 1, 1, 3),
			new WeightedRandomChestContent(ModItems.rod_dual, BreedingRodType.THF.ordinal(), 1, 1, 3),
			new WeightedRandomChestContent(ModItems.rod_zirnox_tritium, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.rod_zirnox_u233_fuel, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.rod_zirnox_u235_fuel, 0, 1, 1, 1),
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

	public static WeightedRandomChestContent[] vertibird = new WeightedRandomChestContent[] {
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
			new WeightedRandomChestContent(ModItems.rod, BreedingRodType.U235.ordinal(), 1, 1, 2),
			new WeightedRandomChestContent(ModItems.billet_uranium_fuel, 0, 1, 1, 2),
			new WeightedRandomChestContent(ModItems.ingot_uranium_fuel, 0, 1, 1, 2),
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

	public static WeightedRandomChestContent[] missile = new WeightedRandomChestContent[] {
			new WeightedRandomChestContent(ModItems.missile_generic, 0, 1, 1, 4),
			new WeightedRandomChestContent(ModItems.missile_incendiary, 0, 1, 1, 4),
			new WeightedRandomChestContent(ModItems.gas_mask_m65, 0, 1, 1, 5),
			new WeightedRandomChestContent(ModItems.battery_advanced, 0, 1, 1, 5),
			new WeightedRandomChestContent(ModItems.designator, 0, 1, 1, 5),
			new WeightedRandomChestContent(ModItems.crate_caller, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.thruster_small, 0, 1, 1, 5),
			new WeightedRandomChestContent(ModItems.thruster_medium, 0, 1, 1, 4),
			new WeightedRandomChestContent(ModItems.fuel_tank_small, 0, 1, 1, 5),
			new WeightedRandomChestContent(ModItems.fuel_tank_medium, 0, 1, 1, 4),
			new WeightedRandomChestContent(ModItems.bomb_caller, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.bomb_caller, 3, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.bottle_nuka, 0, 1, 3, 10) };

	public static WeightedRandomChestContent[] spaceship = new WeightedRandomChestContent[] {
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

	public static WeightedRandomChestContent[] powder = new WeightedRandomChestContent[] {
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

	public static WeightedRandomChestContent[] vault1 = new WeightedRandomChestContent[] {
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

	public static WeightedRandomChestContent[] vault2 = new WeightedRandomChestContent[] {
			new WeightedRandomChestContent(ModItems.ingot_desh, 0, 2, 6, 1),
			new WeightedRandomChestContent(ModItems.battery_advanced_cell_4, 0, 1, 1, 1),
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

	public static WeightedRandomChestContent[] vault3 = new WeightedRandomChestContent[] {
			new WeightedRandomChestContent(ModItems.ingot_desh, 0, 6, 16, 1),
			new WeightedRandomChestContent(ModItems.battery_lithium, 0, 1, 1, 1),
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
			new WeightedRandomChestContent(ModItems.circuit_gold, 0, 6, 12, 1) };

	public static WeightedRandomChestContent[] vault4 = new WeightedRandomChestContent[] {
			new WeightedRandomChestContent(ModItems.ammo_container, 0, 3, 6, 1),
			new WeightedRandomChestContent(ModItems.clip_fatman, 0, 2, 3, 1),
			new WeightedRandomChestContent(ModItems.ammo_mirv, 0, 2, 3, 1),
			new WeightedRandomChestContent(ModItems.gun_mirv, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.gun_fatman, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.gun_proto, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.gun_b92, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.ingot_combine_steel, 0, 16, 28, 1),
			new WeightedRandomChestContent(ModItems.man_core, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.boy_kit, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.nuke_starter_kit, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.weaponized_starblaster_cell, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.warhead_mirv, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.battery_schrabidium_cell, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.powder_nitan_mix, 0, 16, 32, 1) };	
	
	public static WeightedRandomChestContent[] officeTrash = new WeightedRandomChestContent[] {
			//Meta, Min amount, Max amount, Weight
			new WeightedRandomChestContent(Items.paper, 0, 1, 12, 10),
			new WeightedRandomChestContent(Items.book, 0, 1, 3, 4),
			new WeightedRandomChestContent(ModItems.twinkie, 0, 1, 2, 6),
			new WeightedRandomChestContent(ModItems.coffee, 0, 1, 1, 4),
			new WeightedRandomChestContent(ModItems.flame_politics, 0, 1, 1, 2),
			new WeightedRandomChestContent(ModItems.ring_pull, 0, 1, 1, 4),
			new WeightedRandomChestContent(ModItems.can_empty, 0, 1, 1, 2),
			new WeightedRandomChestContent(ModItems.can_creature, 0, 1, 2, 2),
			new WeightedRandomChestContent(ModItems.can_smart, 0, 1, 3, 2),
			new WeightedRandomChestContent(ModItems.can_mrsugar, 0, 1, 2, 2),
			new WeightedRandomChestContent(ModItems.book_guide, 3, 1, 1, 1),
			new WeightedRandomChestContent(Item.getItemFromBlock(ModBlocks.deco_computer), 0, 1, 1, 1)};
	
	/** Nowhere else to put this and this seems like the most fitting place **/
	public static ItemStack genetateBook(String key) {
		
		String author = resolve("book.lore." + key + ".author");
		String title = resolve("book.lore." + key + ".title");
		
		ItemStack book = new ItemStack(Items.written_book);
		book.stackTagCompound = new NBTTagCompound();
		book.stackTagCompound.setString("author", author);
		book.stackTagCompound.setString("title", title);
		NBTTagList nbt = new NBTTagList();
		
		for(byte i = 1; i <= 50; i++) {
			String unloc = "book.lore." + key + ".page" + i;
			String page = resolve(unloc);
			
			if(page.equals(unloc))
				break;
			else
				nbt.appendTag(new NBTTagString(page));
		}
		
		book.stackTagCompound.setTag("pages", nbt);
		
		return book;
	}
	
	private static String resolve(String key) {
		String result = books.get(key);
		return result != null ? result : key;
	}
	
	private static HashMap<String, String> books = new HashMap();
	
	static {
		books.put("book.lore.office0.title", "Letter of Resignation");
		books.put("book.lore.office0.author", "Kosma");
		books.put("book.lore.office0.page1", "Management downsized our department again yesterday. Those idiots only have themselves to blame, I don't know what they were expecting after the Panay fiasco. Who the hell leaks that sort of information? We're losing millions and");
		books.put("book.lore.office0.page2", "it's ME who's the one out of a job now. I'M the one being asked to resign. I hope you asshats finally learn from your overabundance of mistakes and take that stick out of your ass.");
		books.put("book.lore.office0.page3", "I'm not coming back on Friday. Just send the paycheck.");
		books.put("book.lore.office1.title", "Note");
		books.put("book.lore.office1.author", "Jonas");
		books.put("book.lore.office1.page1", null);
		books.put("book.lore.office2.page2", null);
	}
}

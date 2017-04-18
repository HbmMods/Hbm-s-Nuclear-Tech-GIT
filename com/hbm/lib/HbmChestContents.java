package com.hbm.lib;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;

import net.minecraft.item.Item;
import net.minecraft.util.WeightedRandomChestContent;

public class HbmChestContents {

	static Random rand = new Random();

	private static WeightedRandomChestContent[] modGeneric = new WeightedRandomChestContent[] {
			new WeightedRandomChestContent(ModItems.ingot_steel, 0, 10, 15, 5),
			new WeightedRandomChestContent(ModItems.ingot_red_copper, 0, 5, 10, 3),
			new WeightedRandomChestContent(ModItems.ingot_tungsten, 0, 5, 15, 3),
			new WeightedRandomChestContent(ModItems.ingot_beryllium, 0, 1, 5, 2),
			new WeightedRandomChestContent(ModItems.ingot_titanium, 0, 7, 10, 4),
			new WeightedRandomChestContent(ModItems.gun_revolver, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.gun_revolver_ammo, 0, 2, 6, 2),
			new WeightedRandomChestContent(ModItems.battery_generic, 50, 1, 1, 4),
			new WeightedRandomChestContent(ModItems.battery_advanced, 200, 1, 1, 1),
			new WeightedRandomChestContent(Item.getItemFromBlock(ModBlocks.block_titanium), 0, 0, 3, 1) };

	private static WeightedRandomChestContent[] antenna = new WeightedRandomChestContent[] {
			new WeightedRandomChestContent(ModItems.ingot_steel, 0, 3, 7, 5),
			new WeightedRandomChestContent(ModItems.ingot_red_copper, 0, 3, 10, 3),
			new WeightedRandomChestContent(ModItems.ingot_titanium, 0, 3, 5, 4),
			new WeightedRandomChestContent(ModItems.wire_red_copper, 0, 3, 7, 4),
			new WeightedRandomChestContent(ModItems.circuit_aluminium, 0, 1, 5, 3),
			new WeightedRandomChestContent(ModItems.circuit_copper, 0, 1, 3, 2),
			new WeightedRandomChestContent(ModItems.circuit_red_copper, 0, 1, 2, 1),
			new WeightedRandomChestContent(ModItems.battery_generic, 50, 1, 1, 5),
			new WeightedRandomChestContent(ModItems.battery_advanced, 200, 1, 1, 3),
			new WeightedRandomChestContent(ModItems.powder_iodine, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.powder_bromine, 0, 1, 1, 1),
			new WeightedRandomChestContent(Item.getItemFromBlock(ModBlocks.block_titanium), 0, 1, 2, 2),
			new WeightedRandomChestContent(Item.getItemFromBlock(ModBlocks.steel_poles), 0, 4, 9, 5),
			new WeightedRandomChestContent(Item.getItemFromBlock(ModBlocks.steel_scaffold), 0, 4, 6, 3),
			new WeightedRandomChestContent(Item.getItemFromBlock(ModBlocks.pole_top), 0, 2, 4, 4),
			new WeightedRandomChestContent(Item.getItemFromBlock(ModBlocks.pole_satellite_receiver), 0, 3, 5, 3) };

	private static WeightedRandomChestContent[] expensive = new WeightedRandomChestContent[] {
			new WeightedRandomChestContent(ModItems.nugget_schrabidium, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.circuit_gold, 0, 1, 2, 3),
			new WeightedRandomChestContent(ModItems.circuit_schrabidium, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.nuke_starter_kit, 0, 1, 1, 2),
			new WeightedRandomChestContent(ModItems.nuke_commercially_kit, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.nuke_electric_kit, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.gun_revolver_gold, 0, 1, 1, 4),
			new WeightedRandomChestContent(ModItems.gun_revolver_gold_ammo, 0, 1, 6, 5),
			new WeightedRandomChestContent(ModItems.gun_revolver_lead, 0, 1, 1, 4),
			new WeightedRandomChestContent(ModItems.gun_revolver_lead_ammo, 0, 1, 6, 5),
			new WeightedRandomChestContent(ModItems.gun_rpg, 0, 1, 1, 4),
			new WeightedRandomChestContent(ModItems.gun_rpg_ammo, 0, 1, 32, 5),
			new WeightedRandomChestContent(ModItems.gun_fatman, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.gun_fatman_ammo, 0, 1, 8, 2),
			new WeightedRandomChestContent(ModItems.gun_xvl1456, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.gun_xvl1456_ammo, 0, 16, 64, 2),
			new WeightedRandomChestContent(ModItems.grenade_nuclear, 0, 1, 1, 2),
			new WeightedRandomChestContent(ModItems.stealth_boy, 0, 1, 1, 2),
			new WeightedRandomChestContent(ModItems.battery_advanced, 200, 1, 1, 3),
			new WeightedRandomChestContent(ModItems.battery_schrabidium, 10000, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.syringe_awesome, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.crate_caller, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.fusion_core, 0, 1, 1, 4),
			new WeightedRandomChestContent(Item.getItemFromBlock(ModBlocks.red_barrel), 0, 1, 3, 1) };

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
			new WeightedRandomChestContent(Item.getItemFromBlock(ModBlocks.yellow_barrel), 0, 1, 3, 3) };

	private static WeightedRandomChestContent[] vertibird = new WeightedRandomChestContent[] {
			new WeightedRandomChestContent(ModItems.t45_helmet, 0, 1, 1, 15),
			new WeightedRandomChestContent(ModItems.t45_plate, 0, 1, 1, 15),
			new WeightedRandomChestContent(ModItems.t45_legs, 0, 1, 1, 15),
			new WeightedRandomChestContent(ModItems.t45_boots, 0, 1, 1, 15),
			new WeightedRandomChestContent(ModItems.fusion_core, 0, 1, 1, 10),
			new WeightedRandomChestContent(ModItems.circuit_red_copper, 0, 1, 3, 3),
			new WeightedRandomChestContent(ModItems.circuit_gold, 0, 1, 3, 3),
			new WeightedRandomChestContent(ModItems.gun_revolver, 0, 1, 1, 4),
			new WeightedRandomChestContent(ModItems.gun_revolver_ammo, 0, 1, 24, 4),
			new WeightedRandomChestContent(ModItems.gun_rpg, 0, 1, 1, 3),
			new WeightedRandomChestContent(ModItems.gun_rpg_ammo, 0, 1, 6, 3),
			new WeightedRandomChestContent(ModItems.rod_uranium_fuel, 0, 1, 1, 2),
			new WeightedRandomChestContent(ModItems.rod_dual_uranium_fuel, 0, 1, 1, 2),
			new WeightedRandomChestContent(ModItems.rod_quad_uranium_fuel, 0, 1, 1, 2),
			new WeightedRandomChestContent(ModItems.gun_fatman_ammo, 0, 1, 2, 2),
			new WeightedRandomChestContent(ModItems.gun_fatman, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.crate_caller, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.grenade_nuclear, 0, 1, 2, 2) };

	private static WeightedRandomChestContent[] missile = new WeightedRandomChestContent[] {
			new WeightedRandomChestContent(ModItems.missile_generic, 0, 1, 1, 4),
			new WeightedRandomChestContent(ModItems.missile_incendiary, 0, 1, 1, 4),
			new WeightedRandomChestContent(ModItems.missile_cluster, 0, 1, 1, 4),
			new WeightedRandomChestContent(ModItems.missile_buster, 0, 1, 1, 4),
			new WeightedRandomChestContent(ModItems.missile_strong, 0, 1, 1, 3),
			new WeightedRandomChestContent(ModItems.missile_incendiary_strong, 0, 1, 1, 3),
			new WeightedRandomChestContent(ModItems.missile_cluster_strong, 0, 1, 1, 3),
			new WeightedRandomChestContent(ModItems.missile_buster_strong, 0, 1, 1, 3),
			new WeightedRandomChestContent(ModItems.missile_burst, 0, 1, 1, 2),
			new WeightedRandomChestContent(ModItems.missile_inferno, 0, 1, 1, 2),
			new WeightedRandomChestContent(ModItems.missile_rain, 0, 1, 1, 2),
			new WeightedRandomChestContent(ModItems.missile_drill, 0, 1, 1, 2),
			new WeightedRandomChestContent(ModItems.missile_nuclear, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.missile_nuclear_cluster, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.missile_endo, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.missile_exo, 0, 1, 1, 1),
			new WeightedRandomChestContent(Item.getItemFromBlock(ModBlocks.launch_pad), 0, 1, 1, 5),
			new WeightedRandomChestContent(ModItems.battery_advanced, 0, 1, 1, 5),
			new WeightedRandomChestContent(ModItems.designator, 0, 1, 1, 5),
			new WeightedRandomChestContent(ModItems.crate_caller, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.thruster_small, 0, 1, 1, 5),
			new WeightedRandomChestContent(ModItems.thruster_medium, 0, 1, 1, 5),
			new WeightedRandomChestContent(ModItems.thruster_large, 0, 1, 1, 5),
			new WeightedRandomChestContent(ModItems.fuel_tank_small, 0, 1, 1, 5),
			new WeightedRandomChestContent(ModItems.fuel_tank_medium, 0, 1, 1, 5),
			new WeightedRandomChestContent(ModItems.fuel_tank_small, 0, 1, 1, 5) };

	private static WeightedRandomChestContent[] spaceship = new WeightedRandomChestContent[] {
			new WeightedRandomChestContent(ModItems.battery_advanced, 0, 1, 1, 5),
			new WeightedRandomChestContent(ModItems.ingot_advanced_alloy, 0, 2, 16, 5),
			new WeightedRandomChestContent(ModItems.wire_advanced_alloy, 0, 8, 32, 5),
			new WeightedRandomChestContent(ModItems.coil_advanced_alloy, 0, 2, 16, 5),
			new WeightedRandomChestContent(ModItems.cell_deuterium, 0, 1, 8, 5),
			new WeightedRandomChestContent(ModItems.cell_tritium, 0, 1, 8, 5),
			new WeightedRandomChestContent(ModItems.cell_antimatter, 0, 1, 4, 5),
			new WeightedRandomChestContent(ModItems.cell_anti_schrabidium, 0, 1, 2, 5),
			new WeightedRandomChestContent(ModItems.powder_neodymium, 0, 1, 1, 1),
			new WeightedRandomChestContent(ModItems.powder_niobium, 0, 1, 1, 1),
			new WeightedRandomChestContent(Item.getItemFromBlock(ModBlocks.fusion_conductor), 0, 4, 8, 5),
			new WeightedRandomChestContent(Item.getItemFromBlock(ModBlocks.fusion_heater), 0, 1, 6, 5),
			new WeightedRandomChestContent(Item.getItemFromBlock(ModBlocks.reactor_element), 0, 1, 2, 5),
			new WeightedRandomChestContent(Item.getItemFromBlock(ModBlocks.block_tungsten), 0, 8, 32, 5),
			new WeightedRandomChestContent(Item.getItemFromBlock(ModBlocks.red_wire_coated), 0, 4, 16, 5),
			new WeightedRandomChestContent(Item.getItemFromBlock(ModBlocks.red_cable), 0, 8, 32, 5) };

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
		}

		return null;
	}
}

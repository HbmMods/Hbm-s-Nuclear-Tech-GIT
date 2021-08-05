package com.hbm.handler;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.gui.GUIScreenBobmazon.Offer;
import com.hbm.inventory.gui.GUIScreenBobmazon.Requirement;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemBattery;

import net.minecraft.item.ItemStack;

public class BobmazonOfferFactory {

	public static List<Offer> materials = new ArrayList();
	public static List<Offer> machines = new ArrayList();
	public static List<Offer> weapons = new ArrayList();
	public static List<Offer> tools = new ArrayList();
	public static List<Offer> special = new ArrayList();
	
	public static void init() {

		int inflation = 5;
		materials.add(new Offer(new ItemStack(ModItems.ingot_uranium), Requirement.NUCLEAR, 6 * inflation));
		materials.add(new Offer(new ItemStack(ModItems.ingot_u233), Requirement.NUCLEAR, 20 * inflation));
		materials.add(new Offer(new ItemStack(ModItems.ingot_u238), Requirement.NUCLEAR, 15 * inflation));
		materials.add(new Offer(new ItemStack(ModItems.ingot_th232), Requirement.NUCLEAR, 4 * inflation));
		materials.add(new Offer(new ItemStack(ModItems.ingot_plutonium), Requirement.NUCLEAR, 25 * inflation));
		materials.add(new Offer(new ItemStack(ModItems.ingot_titanium), Requirement.STEEL, 2 * inflation));
		materials.add(new Offer(new ItemStack(ModItems.ingot_copper), Requirement.STEEL, 2 * inflation));
		materials.add(new Offer(new ItemStack(ModItems.ingot_red_copper), Requirement.STEEL, 4 * inflation));
		materials.add(new Offer(new ItemStack(ModItems.ingot_tungsten), Requirement.STEEL, 3 * inflation));
		materials.add(new Offer(new ItemStack(ModItems.ingot_aluminium), Requirement.STEEL, 2 * inflation));
		materials.add(new Offer(new ItemStack(ModItems.ingot_steel), Requirement.STEEL, 4 * inflation));
		materials.add(new Offer(new ItemStack(ModItems.ingot_lead), Requirement.STEEL, 2 * inflation));
		materials.add(new Offer(new ItemStack(ModItems.ingot_polymer), Requirement.OIL, 8 * inflation));
		materials.add(new Offer(new ItemStack(ModItems.ingot_uranium_fuel), Requirement.NUCLEAR, 18 * inflation));
		materials.add(new Offer(new ItemStack(ModItems.ingot_thorium_fuel), Requirement.NUCLEAR, 16 * inflation));
		materials.add(new Offer(new ItemStack(ModItems.ingot_desh), Requirement.OIL, 16 * inflation));
		materials.add(new Offer(new ItemStack(ModItems.ingot_saturnite), Requirement.STEEL, 8 * inflation));
		materials.add(new Offer(new ItemStack(ModItems.lithium), Requirement.CHEMICS, 6 * inflation));
		materials.add(new Offer(new ItemStack(ModItems.solid_fuel), Requirement.OIL, 4 * inflation));
		materials.add(new Offer(new ItemStack(ModItems.lignite), Requirement.STEEL, 2 * inflation));
		materials.add(new Offer(new ItemStack(ModItems.canister_oil), Requirement.OIL, 4 * inflation));
		materials.add(new Offer(new ItemStack(ModItems.canister_fuel), Requirement.OIL, 16 * inflation));
		materials.add(new Offer(new ItemStack(ModItems.canister_petroil), Requirement.OIL, 12 * inflation));
		materials.add(new Offer(new ItemStack(ModItems.canister_kerosene), Requirement.OIL, 20 * inflation));
		materials.add(new Offer(new ItemStack(ModItems.canister_NITAN), Requirement.OIL, 100 * inflation));
		materials.add(new Offer(new ItemStack(ModItems.gas_petroleum), Requirement.OIL, 8 * inflation));
		materials.add(new Offer(new ItemStack(ModItems.motor), Requirement.ASSEMBLY, 12 * inflation));
		materials.add(new Offer(new ItemStack(ModItems.rtg_unit), Requirement.NUCLEAR, 25 * inflation));
		materials.add(new Offer(new ItemStack(ModItems.circuit_aluminium), Requirement.ASSEMBLY, 4 * inflation));
		materials.add(new Offer(new ItemStack(ModItems.circuit_copper), Requirement.ASSEMBLY, 6 * inflation));
		materials.add(new Offer(new ItemStack(ModItems.circuit_red_copper), Requirement.ASSEMBLY, 10 * inflation));
		materials.add(new Offer(new ItemStack(ModItems.circuit_gold), Requirement.CHEMICS, 16 * inflation));
		materials.add(new Offer(new ItemStack(ModItems.pellet_gas), Requirement.CHEMICS, 4 * inflation));
		materials.add(new Offer(new ItemStack(ModItems.magnetron), Requirement.ASSEMBLY, 10 * inflation));
		materials.add(new Offer(new ItemStack(ModItems.pellet_rtg), Requirement.NUCLEAR, 27 * inflation));
		materials.add(new Offer(new ItemStack(ModItems.piston_selenium), Requirement.ASSEMBLY, 12 * inflation));
		materials.add(new Offer(ItemBattery.getFullBattery(ModItems.battery_advanced), Requirement.ASSEMBLY, 15 * inflation));
		materials.add(new Offer(ItemBattery.getFullBattery(ModItems.battery_lithium), Requirement.CHEMICS, 30 * inflation));
		materials.add(new Offer(new ItemStack(ModItems.arc_electrode), Requirement.ASSEMBLY, 15 * inflation));
		materials.add(new Offer(new ItemStack(ModItems.fuse), Requirement.ASSEMBLY, 5 * inflation));

		machines.add(new Offer(new ItemStack(ModBlocks.concrete_smooth, 16), Requirement.CHEMICS, 32 * inflation));
		machines.add(new Offer(new ItemStack(ModBlocks.brick_compound, 8), Requirement.CHEMICS, 48 * inflation));
		machines.add(new Offer(new ItemStack(ModBlocks.barbed_wire, 16), Requirement.ASSEMBLY, 12 * inflation));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_siren), Requirement.ASSEMBLY, 12 * inflation));
		machines.add(new Offer(new ItemStack(ModBlocks.vault_door), Requirement.CHEMICS, 250 * inflation));
		machines.add(new Offer(new ItemStack(ModBlocks.blast_door), Requirement.CHEMICS, 120 * inflation));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_epress), Requirement.OIL, 60 * inflation));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_difurnace_off), Requirement.STEEL, 26 * inflation));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_gascent), Requirement.OIL, 100 * inflation));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_diesel), Requirement.CHEMICS, 65 * inflation));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_geo), Requirement.CHEMICS, 30 * inflation));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_battery), Requirement.ASSEMBLY, 30 * inflation));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_lithium_battery), Requirement.CHEMICS, 60 * inflation));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_assembler), Requirement.ASSEMBLY, 30 * inflation));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_chemplant), Requirement.CHEMICS, 50 * inflation));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_boiler_off), Requirement.CHEMICS, 25 * inflation));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_boiler_electric_off), Requirement.OIL, 60 * inflation));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_shredder), Requirement.ASSEMBLY, 45 * inflation));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_well), Requirement.OIL, 40 * inflation));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_refinery), Requirement.OIL, 80 * inflation));
		machines.add(new Offer(new ItemStack(ModBlocks.absorber), Requirement.CHEMICS, 10 * inflation));
		machines.add(new Offer(new ItemStack(ModBlocks.absorber_green), Requirement.OIL, 25 * inflation));
		machines.add(new Offer(new ItemStack(ModBlocks.decon), Requirement.CHEMICS, 15 * inflation));

		weapons.add(new Offer(new ItemStack(ModItems.loot_10), Requirement.OIL, 50 * inflation));
		weapons.add(new Offer(new ItemStack(ModItems.loot_15), Requirement.OIL, 65 * inflation));
		weapons.add(new Offer(new ItemStack(ModItems.loot_misc), Requirement.NUCLEAR, 65 * inflation));
		weapons.add(new Offer(new ItemStack(ModBlocks.launch_pad), Requirement.OIL, 95 * inflation));
		weapons.add(new Offer(new ItemStack(ModBlocks.machine_radar), Requirement.OIL, 90 * inflation));
		weapons.add(new Offer(new ItemStack(ModItems.designator), Requirement.CHEMICS, 35 * inflation));
		weapons.add(new Offer(new ItemStack(ModItems.designator_range), Requirement.CHEMICS, 50 * inflation));
		weapons.add(new Offer(new ItemStack(ModItems.sat_chip), Requirement.CHEMICS, 35 * inflation));
		weapons.add(new Offer(new ItemStack(ModBlocks.turret_cheapo), Requirement.CHEMICS, 70 * inflation));
		weapons.add(new Offer(new ItemStack(ModItems.turret_cheapo_ammo), Requirement.ASSEMBLY, 20 * inflation));
		weapons.add(new Offer(new ItemStack(ModItems.turret_control), Requirement.CHEMICS, 35 * inflation));
		weapons.add(new Offer(new ItemStack(ModItems.turret_chip), Requirement.CHEMICS, 80 * inflation));
		weapons.add(new Offer(new ItemStack(ModItems.turret_biometry), Requirement.CHEMICS, 15 * inflation));
		weapons.add(new Offer(new ItemStack(ModBlocks.mine_ap, 4), Requirement.ASSEMBLY, 25 * inflation));
		weapons.add(new Offer(new ItemStack(ModBlocks.emp_bomb), Requirement.CHEMICS, 90 * inflation));
		weapons.add(new Offer(new ItemStack(ModBlocks.det_cord, 16), Requirement.ASSEMBLY, 50 * inflation));
		weapons.add(new Offer(new ItemStack(ModBlocks.det_charge), Requirement.CHEMICS, 25 * inflation));
		weapons.add(new Offer(new ItemStack(ModItems.detonator), Requirement.ASSEMBLY, 15 * inflation));
		weapons.add(new Offer(new ItemStack(ModItems.detonator_laser), Requirement.CHEMICS, 60 * inflation));
		weapons.add(new Offer(new ItemStack(ModItems.defuser), Requirement.OIL, 5 * inflation));
		weapons.add(new Offer(new ItemStack(ModItems.gun_revolver), Requirement.ASSEMBLY, 15 * inflation));
		weapons.add(new Offer(new ItemStack(ModItems.gun_revolver_nopip), Requirement.ASSEMBLY, 20 * inflation));
		weapons.add(new Offer(new ItemStack(ModItems.gun_minigun), Requirement.OIL, 100 * inflation));
		weapons.add(new Offer(new ItemStack(ModItems.gun_panzerschreck), Requirement.ASSEMBLY, 95 * inflation));
		weapons.add(new Offer(new ItemStack(ModItems.gun_hk69), Requirement.ASSEMBLY, 60 * inflation));
		weapons.add(new Offer(new ItemStack(ModItems.gun_uzi), Requirement.OIL, 80 * inflation));
		weapons.add(new Offer(new ItemStack(ModItems.gun_lever_action), Requirement.ASSEMBLY, 60 * inflation));
		weapons.add(new Offer(new ItemStack(ModItems.gun_bolt_action), Requirement.ASSEMBLY, 35 * inflation));
		weapons.add(new Offer(new ItemStack(ModItems.gun_revolver_ammo, 6), Requirement.OIL, 12 * inflation));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_357_desh, 6), Requirement.OIL, 36 * inflation));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_44, 6), Requirement.OIL, 12 * inflation));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_44_ap, 6), Requirement.OIL, 18 * inflation));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_5mm, 50), Requirement.OIL, 50 * inflation));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_5mm_du, 50), Requirement.OIL, 75 * inflation));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_rocket), Requirement.OIL, 5 * inflation));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_rocket_incendiary), Requirement.OIL, 8 * inflation));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_rocket_sleek), Requirement.OIL, 12 * inflation));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_grenade), Requirement.OIL, 4 * inflation));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_grenade_incendiary), Requirement.OIL, 6 * inflation));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_grenade_sleek), Requirement.OIL, 10 * inflation));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_22lr, 32), Requirement.OIL, 24 * inflation));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_22lr_ap, 32), Requirement.OIL, 32 * inflation));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_20gauge, 6), Requirement.OIL, 18 * inflation));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_20gauge_slug, 6), Requirement.OIL, 20 * inflation));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_20gauge_flechette, 6), Requirement.OIL, 22 * inflation));
		weapons.add(new Offer(new ItemStack(ModItems.grenade_if_generic, 3), Requirement.CHEMICS, 15 * inflation));
		weapons.add(new Offer(new ItemStack(ModItems.grenade_if_he, 3), Requirement.CHEMICS, 25 * inflation));

		tools.add(new Offer(new ItemStack(ModBlocks.crate_can, 1), Requirement.STEEL, 20 * inflation));
		tools.add(new Offer(new ItemStack(ModBlocks.machine_keyforge), Requirement.STEEL, 10 * inflation));
		tools.add(new Offer(new ItemStack(ModBlocks.machine_telelinker), Requirement.CHEMICS, 35 * inflation));
		tools.add(new Offer(new ItemStack(ModBlocks.machine_satlinker), Requirement.CHEMICS, 50 * inflation));
		tools.add(new Offer(new ItemStack(ModItems.oil_detector), Requirement.CHEMICS, 45 * inflation));
		tools.add(new Offer(new ItemStack(ModItems.geiger_counter), Requirement.CHEMICS, 10 * inflation));
		tools.add(new Offer(new ItemStack(ModItems.key), Requirement.STEEL, 2 * inflation));
		tools.add(new Offer(new ItemStack(ModItems.padlock), Requirement.STEEL, 5 * inflation));
		tools.add(new Offer(new ItemStack(ModItems.padlock_reinforced), Requirement.OIL, 15 * inflation));
		tools.add(new Offer(new ItemStack(ModItems.syringe_antidote, 6), Requirement.STEEL, 10 * inflation));
		tools.add(new Offer(new ItemStack(ModItems.syringe_metal_stimpak, 4), Requirement.STEEL, 10 * inflation));
		tools.add(new Offer(new ItemStack(ModItems.syringe_metal_medx, 4), Requirement.STEEL, 10 * inflation));
		tools.add(new Offer(new ItemStack(ModItems.radaway, 6), Requirement.ASSEMBLY, 30 * inflation));
		tools.add(new Offer(new ItemStack(ModItems.radaway_strong, 3), Requirement.ASSEMBLY, 35 * inflation));
		tools.add(new Offer(new ItemStack(ModItems.radx, 4), Requirement.ASSEMBLY, 20 * inflation));
		tools.add(new Offer(new ItemStack(ModItems.pill_iodine, 6), Requirement.ASSEMBLY, 20 * inflation));
		tools.add(new Offer(new ItemStack(ModItems.gas_mask_filter, 1), Requirement.ASSEMBLY, 5 * inflation));
		tools.add(new Offer(new ItemStack(ModItems.gun_kit_1, 4), Requirement.OIL, 20 * inflation));
		tools.add(new Offer(new ItemStack(ModItems.gun_kit_2, 2), Requirement.OIL, 45 * inflation));
		tools.add(new Offer(new ItemStack(ModItems.hazmat_kit), Requirement.ASSEMBLY, 40 * inflation));
		tools.add(new Offer(new ItemStack(ModItems.hazmat_red_kit), Requirement.CHEMICS, 100 * inflation));
		tools.add(new Offer(new ItemStack(ModItems.hazmat_grey_kit), Requirement.OIL, 160 * inflation));

		special.add(new Offer(new ItemStack(ModItems.ingot_steel, 64), Requirement.STEEL, 1));
		special.add(new Offer(new ItemStack(ModItems.ingot_copper, 64), Requirement.STEEL, 1));
		special.add(new Offer(new ItemStack(ModItems.ingot_red_copper, 64), Requirement.STEEL, 1));
		special.add(new Offer(new ItemStack(ModItems.ingot_titanium, 64), Requirement.STEEL, 1));
		special.add(new Offer(new ItemStack(ModItems.ingot_tungsten, 64), Requirement.STEEL, 1));
		special.add(new Offer(new ItemStack(ModItems.ingot_cobalt, 64), Requirement.STEEL, 1));
		special.add(new Offer(new ItemStack(ModItems.ingot_tantalium, 64), Requirement.STEEL, 5));
		special.add(new Offer(new ItemStack(ModItems.ingot_bismuth, 16), Requirement.STEEL, 5));
		special.add(new Offer(new ItemStack(ModItems.ingot_schrabidium, 16), Requirement.STEEL, 5));
		special.add(new Offer(new ItemStack(ModItems.ingot_euphemium, 8), Requirement.STEEL, 16));
		special.add(new Offer(new ItemStack(ModItems.ingot_dineutronium, 1), Requirement.STEEL, 16));
		special.add(new Offer(new ItemStack(ModItems.ingot_starmetal, 16), Requirement.STEEL, 8));
		special.add(new Offer(new ItemStack(ModItems.ingot_semtex, 16), Requirement.STEEL, 1));
		special.add(new Offer(new ItemStack(ModItems.ingot_u235, 16), Requirement.STEEL, 1));
		special.add(new Offer(new ItemStack(ModItems.ingot_pu239, 16), Requirement.STEEL, 1));
		special.add(new Offer(new ItemStack(ModItems.ammo_container, 16), Requirement.STEEL, 5));
		special.add(new Offer(new ItemStack(ModItems.nuke_starter_kit), Requirement.STEEL, 5));
		special.add(new Offer(new ItemStack(ModItems.nuke_advanced_kit), Requirement.STEEL, 5));
		special.add(new Offer(new ItemStack(ModItems.boy_kit), Requirement.STEEL, 5));
		special.add(new Offer(new ItemStack(ModItems.prototype_kit), Requirement.STEEL, 10));
		special.add(new Offer(new ItemStack(ModItems.missile_kit), Requirement.STEEL, 5));
		special.add(new Offer(new ItemStack(ModItems.grenade_kit), Requirement.STEEL, 5));
		special.add(new Offer(new ItemStack(ModItems.jetpack_vector), Requirement.STEEL, 2));
		special.add(new Offer(new ItemStack(ModItems.jetpack_tank), Requirement.STEEL, 2));
		special.add(new Offer(new ItemStack(ModItems.gun_kit_1, 10), Requirement.STEEL, 1));
		special.add(new Offer(new ItemStack(ModItems.gun_kit_2, 5), Requirement.STEEL, 3));
		special.add(new Offer(new ItemStack(ModBlocks.struct_launcher_core, 1), Requirement.STEEL, 3));
		special.add(new Offer(new ItemStack(ModBlocks.struct_launcher_core_large, 1), Requirement.STEEL, 3));
		special.add(new Offer(new ItemStack(ModBlocks.struct_launcher, 40), Requirement.STEEL, 7));
		special.add(new Offer(new ItemStack(ModBlocks.struct_scaffold, 11), Requirement.STEEL, 7));
		special.add(new Offer(new ItemStack(ModItems.loot_10, 1), Requirement.STEEL, 2));
		special.add(new Offer(new ItemStack(ModItems.loot_15, 1), Requirement.STEEL, 2));
		special.add(new Offer(new ItemStack(ModItems.loot_misc, 1), Requirement.STEEL, 2));
		special.add(new Offer(new ItemStack(ModBlocks.crate_can, 1), Requirement.STEEL, 1));
		special.add(new Offer(new ItemStack(ModBlocks.crate_ammo, 1), Requirement.STEEL, 2));
		special.add(new Offer(new ItemStack(ModItems.crucible, 1, 3), Requirement.STEEL, 10));
		special.add(new Offer(new ItemStack(ModItems.spawn_chopper, 1), Requirement.STEEL, 10));
		special.add(new Offer(new ItemStack(ModItems.spawn_worm, 1), Requirement.STEEL, 10));
		special.add(new Offer(new ItemStack(ModItems.spawn_ufo, 1), Requirement.STEEL, 10));
		special.add(new Offer(new ItemStack(ModItems.sat_laser, 1), Requirement.HIDDEN, 8));
		special.add(new Offer(new ItemStack(ModItems.sat_gerald, 1), Requirement.HIDDEN, 32));
		special.add(new Offer(new ItemStack(ModItems.billet_yharonite, 4), Requirement.HIDDEN, 16));
		special.add(new Offer(new ItemStack(ModItems.ingot_electronium, 1), Requirement.HIDDEN, 16));
		special.add(new Offer(new ItemStack(ModItems.book_of_, 1), Requirement.HIDDEN, 16));
		special.add(new Offer(new ItemStack(ModItems.mysteryshovel, 1), Requirement.HIDDEN, 16));
		special.add(new Offer(new ItemStack(ModBlocks.ntm_dirt, 1), Requirement.HIDDEN, 16));
		special.add(new Offer(new ItemStack(ModItems.euphemium_kit, 1), Requirement.HIDDEN, 64));
	}
	
	public static List<Offer> getOffers(ItemStack stack) {
		
		if(stack != null) {

			if(stack.getItem() == ModItems.bobmazon_materials)
				return materials;
			if(stack.getItem() == ModItems.bobmazon_machines)
				return machines;
			if(stack.getItem() == ModItems.bobmazon_weapons)
				return weapons;
			if(stack.getItem() == ModItems.bobmazon_tools)
				return tools;
			if(stack.getItem() == ModItems.bobmazon_hidden)
				return special;
		}
		
		return null;
	}

}

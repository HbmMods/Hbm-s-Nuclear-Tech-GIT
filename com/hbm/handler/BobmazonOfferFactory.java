package com.hbm.handler;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.gui.GUIScreenBobmazon.Offer;
import com.hbm.inventory.gui.GUIScreenBobmazon.Requirement;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemBattery;

import net.minecraft.item.ItemStack;

public class BobmazonOfferFactory {

	public static List<Offer> materials = new ArrayList();
	public static List<Offer> machines = new ArrayList();
	public static List<Offer> weapons = new ArrayList();
	public static List<Offer> tools = new ArrayList();
	public static List<Offer> special = new ArrayList();
	
	public static void init() {

		materials.add(new Offer(new ItemStack(ModItems.ingot_uranium), Requirement.NUCLEAR, 6));
		materials.add(new Offer(new ItemStack(ModItems.ingot_u233), Requirement.NUCLEAR, 20));
		materials.add(new Offer(new ItemStack(ModItems.ingot_u238), Requirement.NUCLEAR, 15));
		materials.add(new Offer(new ItemStack(ModItems.ingot_th232), Requirement.NUCLEAR, 4));
		materials.add(new Offer(new ItemStack(ModItems.ingot_plutonium), Requirement.NUCLEAR, 25));
		materials.add(new Offer(new ItemStack(ModItems.ingot_titanium), Requirement.STEEL, 2));
		materials.add(new Offer(new ItemStack(ModItems.ingot_copper), Requirement.STEEL, 2));
		materials.add(new Offer(new ItemStack(ModItems.ingot_red_copper), Requirement.STEEL, 4));
		materials.add(new Offer(new ItemStack(ModItems.ingot_tungsten), Requirement.STEEL, 3));
		materials.add(new Offer(new ItemStack(ModItems.ingot_aluminium), Requirement.STEEL, 2));
		materials.add(new Offer(new ItemStack(ModItems.ingot_steel), Requirement.STEEL, 4));
		materials.add(new Offer(new ItemStack(ModItems.ingot_lead), Requirement.STEEL, 2));
		materials.add(new Offer(new ItemStack(ModItems.ingot_polymer), Requirement.OIL, 8));
		materials.add(new Offer(new ItemStack(ModItems.ingot_uranium_fuel), Requirement.NUCLEAR, 18));
		materials.add(new Offer(new ItemStack(ModItems.ingot_thorium_fuel), Requirement.NUCLEAR, 16));
		materials.add(new Offer(new ItemStack(ModItems.ingot_desh), Requirement.OIL, 16));
		materials.add(new Offer(new ItemStack(ModItems.ingot_saturnite), Requirement.STEEL, 8));
		materials.add(new Offer(new ItemStack(ModItems.lithium), Requirement.CHEMICS, 6));
		materials.add(new Offer(new ItemStack(ModItems.solid_fuel), Requirement.OIL, 4));
		materials.add(new Offer(new ItemStack(ModItems.lignite), Requirement.STEEL, 2));
		materials.add(new Offer(new ItemStack(ModItems.canister_oil), Requirement.OIL, 4));
		materials.add(new Offer(new ItemStack(ModItems.canister_fuel), Requirement.OIL, 16));
		materials.add(new Offer(new ItemStack(ModItems.canister_petroil), Requirement.OIL, 12));
		materials.add(new Offer(new ItemStack(ModItems.canister_kerosene), Requirement.OIL, 20));
		materials.add(new Offer(new ItemStack(ModItems.canister_NITAN), Requirement.OIL, 100));
		materials.add(new Offer(new ItemStack(ModItems.gas_petroleum), Requirement.OIL, 8));
		materials.add(new Offer(new ItemStack(ModItems.motor), Requirement.ASSEMBLY, 12));
		materials.add(new Offer(new ItemStack(ModItems.rtg_unit), Requirement.NUCLEAR, 25));
		materials.add(new Offer(new ItemStack(ModItems.circuit_aluminium), Requirement.ASSEMBLY, 4));
		materials.add(new Offer(new ItemStack(ModItems.circuit_copper), Requirement.ASSEMBLY, 6));
		materials.add(new Offer(new ItemStack(ModItems.circuit_red_copper), Requirement.ASSEMBLY, 10));
		materials.add(new Offer(new ItemStack(ModItems.circuit_gold), Requirement.CHEMICS, 16));
		materials.add(new Offer(new ItemStack(ModItems.pellet_gas), Requirement.CHEMICS, 4));
		materials.add(new Offer(new ItemStack(ModItems.magnetron), Requirement.ASSEMBLY, 10));
		materials.add(new Offer(new ItemStack(ModItems.pellet_rtg), Requirement.NUCLEAR, 27));
		materials.add(new Offer(new ItemStack(ModItems.piston_selenium), Requirement.ASSEMBLY, 12));
		materials.add(new Offer(ItemBattery.getFullBattery(ModItems.battery_advanced), Requirement.ASSEMBLY, 15));
		materials.add(new Offer(ItemBattery.getFullBattery(ModItems.battery_lithium), Requirement.CHEMICS, 30));
		materials.add(new Offer(new ItemStack(ModItems.arc_electrode), Requirement.ASSEMBLY, 15));
		materials.add(new Offer(new ItemStack(ModItems.fuse), Requirement.ASSEMBLY, 5));

		machines.add(new Offer(new ItemStack(ModBlocks.concrete_smooth, 16), Requirement.CHEMICS, 32));
		machines.add(new Offer(new ItemStack(ModBlocks.brick_compound, 8), Requirement.CHEMICS, 48));
		machines.add(new Offer(new ItemStack(ModBlocks.barbed_wire, 16), Requirement.ASSEMBLY, 12));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_siren), Requirement.ASSEMBLY, 12));
		machines.add(new Offer(new ItemStack(ModBlocks.vault_door), Requirement.CHEMICS, 250));
		machines.add(new Offer(new ItemStack(ModBlocks.blast_door), Requirement.CHEMICS, 120));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_epress), Requirement.OIL, 60));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_difurnace_off), Requirement.STEEL, 26));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_gascent), Requirement.OIL, 100));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_diesel), Requirement.CHEMICS, 65));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_geo), Requirement.CHEMICS, 30));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_battery), Requirement.ASSEMBLY, 30));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_lithium_battery), Requirement.CHEMICS, 60));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_assembler), Requirement.ASSEMBLY, 30));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_chemplant), Requirement.CHEMICS, 50));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_boiler_off), Requirement.CHEMICS, 25));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_boiler_electric_off), Requirement.OIL, 60));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_shredder), Requirement.ASSEMBLY, 45));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_well), Requirement.OIL, 40));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_refinery), Requirement.OIL, 80));
		machines.add(new Offer(new ItemStack(ModBlocks.absorber), Requirement.CHEMICS, 10));
		machines.add(new Offer(new ItemStack(ModBlocks.absorber_green), Requirement.OIL, 25));
		machines.add(new Offer(new ItemStack(ModBlocks.decon), Requirement.CHEMICS, 15));
		
		weapons.add(new Offer(new ItemStack(ModBlocks.launch_pad), Requirement.OIL, 95));
		weapons.add(new Offer(new ItemStack(ModBlocks.machine_radar), Requirement.OIL, 90));
		weapons.add(new Offer(new ItemStack(ModItems.designator), Requirement.CHEMICS, 35));
		weapons.add(new Offer(new ItemStack(ModItems.designator_range), Requirement.CHEMICS, 50));
		weapons.add(new Offer(new ItemStack(ModItems.sat_chip), Requirement.CHEMICS, 35));
		weapons.add(new Offer(new ItemStack(ModBlocks.turret_cheapo), Requirement.CHEMICS, 70));
		weapons.add(new Offer(new ItemStack(ModItems.turret_cheapo_ammo), Requirement.ASSEMBLY, 20));
		weapons.add(new Offer(new ItemStack(ModItems.turret_control), Requirement.CHEMICS, 35));
		weapons.add(new Offer(new ItemStack(ModItems.turret_chip), Requirement.CHEMICS, 80));
		weapons.add(new Offer(new ItemStack(ModItems.turret_biometry), Requirement.CHEMICS, 15));
		weapons.add(new Offer(new ItemStack(ModBlocks.mine_ap, 4), Requirement.ASSEMBLY, 25));
		weapons.add(new Offer(new ItemStack(ModBlocks.emp_bomb), Requirement.CHEMICS, 90));
		weapons.add(new Offer(new ItemStack(ModBlocks.det_cord, 16), Requirement.ASSEMBLY, 50));
		weapons.add(new Offer(new ItemStack(ModBlocks.det_charge), Requirement.CHEMICS, 25));
		weapons.add(new Offer(new ItemStack(ModItems.detonator), Requirement.ASSEMBLY, 15));
		weapons.add(new Offer(new ItemStack(ModItems.detonator_laser), Requirement.CHEMICS, 60));
		weapons.add(new Offer(new ItemStack(ModItems.defuser), Requirement.OIL, 5));
		weapons.add(new Offer(new ItemStack(ModItems.gun_revolver), Requirement.ASSEMBLY, 15));
		weapons.add(new Offer(new ItemStack(ModItems.gun_revolver_nopip), Requirement.ASSEMBLY, 20));
		weapons.add(new Offer(new ItemStack(ModItems.gun_minigun), Requirement.OIL, 100));
		weapons.add(new Offer(new ItemStack(ModItems.gun_panzerschreck), Requirement.ASSEMBLY, 95));
		weapons.add(new Offer(new ItemStack(ModItems.gun_hk69), Requirement.ASSEMBLY, 60));
		weapons.add(new Offer(new ItemStack(ModItems.gun_uzi), Requirement.OIL, 80));
		weapons.add(new Offer(new ItemStack(ModItems.gun_lever_action), Requirement.ASSEMBLY, 60));
		weapons.add(new Offer(new ItemStack(ModItems.gun_bolt_action), Requirement.ASSEMBLY, 35));
		weapons.add(new Offer(new ItemStack(ModItems.gun_revolver_ammo, 6), Requirement.OIL, 12));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_357_desh, 6), Requirement.OIL, 36));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_44, 6), Requirement.OIL, 12));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_44_ap, 6), Requirement.OIL, 18));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_5mm, 50), Requirement.OIL, 50));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_5mm_du, 50), Requirement.OIL, 75));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_rocket), Requirement.OIL, 5));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_rocket_incendiary), Requirement.OIL, 8));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_rocket_sleek), Requirement.OIL, 12));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_grenade), Requirement.OIL, 4));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_grenade_incendiary), Requirement.OIL, 6));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_grenade_sleek), Requirement.OIL, 10));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_22lr, 32), Requirement.OIL, 24));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_22lr_ap, 32), Requirement.OIL, 32));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_20gauge, 6), Requirement.OIL, 18));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_20gauge_slug, 6), Requirement.OIL, 20));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_20gauge_flechette, 6), Requirement.OIL, 22));
		weapons.add(new Offer(new ItemStack(ModItems.grenade_if_generic, 3), Requirement.CHEMICS, 15));
		weapons.add(new Offer(new ItemStack(ModItems.grenade_if_he, 3), Requirement.CHEMICS, 25));
		
		tools.add(new Offer(new ItemStack(ModBlocks.machine_keyforge), Requirement.STEEL, 10));
		tools.add(new Offer(new ItemStack(ModBlocks.machine_telelinker), Requirement.CHEMICS, 35));
		tools.add(new Offer(new ItemStack(ModBlocks.machine_satlinker), Requirement.CHEMICS, 50));
		tools.add(new Offer(new ItemStack(ModItems.oil_detector), Requirement.CHEMICS, 45));
		tools.add(new Offer(new ItemStack(ModItems.geiger_counter), Requirement.CHEMICS, 10));
		tools.add(new Offer(new ItemStack(ModItems.key), Requirement.STEEL, 2));
		tools.add(new Offer(new ItemStack(ModItems.padlock), Requirement.STEEL, 5));
		tools.add(new Offer(new ItemStack(ModItems.padlock_reinforced), Requirement.OIL, 15));
		tools.add(new Offer(new ItemStack(ModItems.syringe_antidote, 6), Requirement.STEEL, 10));
		tools.add(new Offer(new ItemStack(ModItems.syringe_metal_stimpak, 4), Requirement.STEEL, 10));
		tools.add(new Offer(new ItemStack(ModItems.syringe_metal_medx, 4), Requirement.STEEL, 10));
		tools.add(new Offer(new ItemStack(ModItems.radaway, 6), Requirement.ASSEMBLY, 30));
		tools.add(new Offer(new ItemStack(ModItems.radaway_strong, 3), Requirement.ASSEMBLY, 35));
		tools.add(new Offer(new ItemStack(ModItems.radx, 4), Requirement.ASSEMBLY, 20));
		tools.add(new Offer(new ItemStack(ModItems.pill_iodine, 6), Requirement.ASSEMBLY, 20));
		tools.add(new Offer(new ItemStack(ModItems.gas_mask_filter, 1), Requirement.ASSEMBLY, 5));
		tools.add(new Offer(new ItemStack(ModItems.gun_kit_1, 4), Requirement.OIL, 20));
		tools.add(new Offer(new ItemStack(ModItems.gun_kit_2, 2), Requirement.OIL, 45));
		tools.add(new Offer(new ItemStack(ModItems.hazmat_kit), Requirement.ASSEMBLY, 40));
		tools.add(new Offer(new ItemStack(ModItems.hazmat_red_kit), Requirement.CHEMICS, 100));
		tools.add(new Offer(new ItemStack(ModItems.hazmat_grey_kit), Requirement.OIL, 160));

		special.add(new Offer(new ItemStack(ModItems.nuke_starter_kit), Requirement.STEEL, 200));
		special.add(new Offer(new ItemStack(ModItems.nuke_advanced_kit), Requirement.STEEL, 300));
		special.add(new Offer(new ItemStack(ModItems.boy_kit), Requirement.STEEL, 350));
		special.add(new Offer(new ItemStack(ModItems.gun_lever_action), Requirement.STEEL, 20));
		special.add(new Offer(new ItemStack(ModItems.ammo_20gauge, 24), Requirement.STEEL, 10));
		special.add(new Offer(new ItemStack(ModItems.ammo_20gauge_incendiary, 24), Requirement.STEEL, 15));
		special.add(new Offer(new ItemStack(ModItems.gun_uzi_silencer, 1), Requirement.STEEL, 20));
		special.add(new Offer(new ItemStack(ModItems.ammo_22lr, 64), Requirement.STEEL, 10));
		special.add(new Offer(new ItemStack(ModItems.ammo_22lr_ap, 64), Requirement.STEEL, 15));
		special.add(new Offer(new ItemStack(ModItems.gun_hk69, 24), Requirement.STEEL, 30));
		special.add(new Offer(new ItemStack(ModItems.ammo_grenade, 6), Requirement.STEEL, 20));
		special.add(new Offer(new ItemStack(ModItems.ammo_grenade_sleek, 6), Requirement.STEEL, 40));
		special.add(new Offer(new ItemStack(ModItems.gun_kit_1, 10), Requirement.STEEL, 10));
		special.add(new Offer(new ItemStack(ModItems.gun_kit_2, 5), Requirement.STEEL, 15));
		special.add(new Offer(new ItemStack(ModBlocks.machine_epress, 2), Requirement.STEEL, 20));
		special.add(new Offer(new ItemStack(ModBlocks.machine_assembler, 1), Requirement.STEEL, 10));
		special.add(new Offer(new ItemStack(ModBlocks.machine_chemplant, 1), Requirement.STEEL, 10));
		special.add(new Offer(new ItemStack(ModBlocks.machine_boiler_off, 1), Requirement.STEEL, 10));
		special.add(new Offer(new ItemStack(ModBlocks.machine_well, 1), Requirement.STEEL, 10));
		special.add(new Offer(new ItemStack(ModBlocks.machine_refinery, 1), Requirement.STEEL, 25));
		special.add(new Offer(new ItemStack(ModBlocks.machine_fluidtank, 4), Requirement.STEEL, 10));
		special.add(new Offer(new ItemStack(ModItems.ingot_steel, 64), Requirement.STEEL, 20));
		special.add(new Offer(new ItemStack(ModItems.ingot_copper, 64), Requirement.STEEL, 20));
		special.add(new Offer(new ItemStack(ModItems.ingot_red_copper, 64), Requirement.STEEL, 20));
		special.add(new Offer(new ItemStack(ModItems.ingot_titanium, 64), Requirement.STEEL, 20));
		special.add(new Offer(new ItemStack(ModItems.ingot_tungsten, 64), Requirement.STEEL, 20));
		special.add(new Offer(new ItemStack(ModBlocks.struct_launcher_core, 1), Requirement.STEEL, 30));
		special.add(new Offer(new ItemStack(ModBlocks.struct_launcher_core_large, 1), Requirement.STEEL, 30));
		special.add(new Offer(new ItemStack(ModBlocks.struct_launcher, 40), Requirement.STEEL, 20));
		special.add(new Offer(new ItemStack(ModBlocks.struct_scaffold, 11), Requirement.STEEL, 5));
		special.add(new Offer(new ItemStack(ModItems.loot_10, 1), Requirement.STEEL, 5));
		special.add(new Offer(new ItemStack(ModItems.loot_15, 1), Requirement.STEEL, 5));
		special.add(new Offer(new ItemStack(ModItems.loot_misc, 1), Requirement.STEEL, 5));
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

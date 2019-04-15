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
	
	public static void init() {

		materials.add(new Offer(new ItemStack(ModItems.ingot_uranium), Requirement.NUCLEAR, 0));
		materials.add(new Offer(new ItemStack(ModItems.ingot_u233), Requirement.NUCLEAR, 0));
		materials.add(new Offer(new ItemStack(ModItems.ingot_u238), Requirement.NUCLEAR, 0));
		materials.add(new Offer(new ItemStack(ModItems.ingot_th232), Requirement.NUCLEAR, 0));
		materials.add(new Offer(new ItemStack(ModItems.ingot_plutonium), Requirement.NUCLEAR, 0));
		materials.add(new Offer(new ItemStack(ModItems.ingot_titanium), Requirement.STEEL, 0));
		materials.add(new Offer(new ItemStack(ModItems.ingot_copper), Requirement.STEEL, 0));
		materials.add(new Offer(new ItemStack(ModItems.ingot_red_copper), Requirement.STEEL, 0));
		materials.add(new Offer(new ItemStack(ModItems.ingot_tungsten), Requirement.STEEL, 0));
		materials.add(new Offer(new ItemStack(ModItems.ingot_aluminium), Requirement.STEEL, 0));
		materials.add(new Offer(new ItemStack(ModItems.ingot_steel), Requirement.STEEL, 0));
		materials.add(new Offer(new ItemStack(ModItems.ingot_lead), Requirement.STEEL, 0));
		materials.add(new Offer(new ItemStack(ModItems.ingot_polymer), Requirement.OIL, 0));
		materials.add(new Offer(new ItemStack(ModItems.ingot_uranium_fuel), Requirement.NUCLEAR, 0));
		materials.add(new Offer(new ItemStack(ModItems.ingot_thorium_fuel), Requirement.NUCLEAR, 0));
		materials.add(new Offer(new ItemStack(ModItems.ingot_desh), Requirement.OIL, 0));
		materials.add(new Offer(new ItemStack(ModItems.ingot_saturnite), Requirement.STEEL, 0));
		materials.add(new Offer(new ItemStack(ModItems.lithium), Requirement.CHEMICS, 0));
		materials.add(new Offer(new ItemStack(ModItems.solid_fuel), Requirement.OIL, 0));
		materials.add(new Offer(new ItemStack(ModItems.lignite), Requirement.STEEL, 0));
		materials.add(new Offer(new ItemStack(ModItems.canister_oil), Requirement.OIL, 0));
		materials.add(new Offer(new ItemStack(ModItems.canister_fuel), Requirement.OIL, 0));
		materials.add(new Offer(new ItemStack(ModItems.canister_petroil), Requirement.OIL, 0));
		materials.add(new Offer(new ItemStack(ModItems.canister_kerosene), Requirement.OIL, 0));
		materials.add(new Offer(new ItemStack(ModItems.canister_NITAN), Requirement.OIL, 0));
		materials.add(new Offer(new ItemStack(ModItems.gas_petroleum), Requirement.OIL, 0));
		materials.add(new Offer(new ItemStack(ModItems.motor), Requirement.ASSEMBLY, 0));
		materials.add(new Offer(new ItemStack(ModItems.rtg_unit), Requirement.NUCLEAR, 0));
		materials.add(new Offer(new ItemStack(ModItems.circuit_aluminium), Requirement.ASSEMBLY, 0));
		materials.add(new Offer(new ItemStack(ModItems.circuit_copper), Requirement.ASSEMBLY, 0));
		materials.add(new Offer(new ItemStack(ModItems.circuit_red_copper), Requirement.ASSEMBLY, 0));
		materials.add(new Offer(new ItemStack(ModItems.circuit_gold), Requirement.CHEMICS, 0));
		materials.add(new Offer(new ItemStack(ModItems.pellet_gas), Requirement.CHEMICS, 0));
		materials.add(new Offer(new ItemStack(ModItems.magnetron), Requirement.ASSEMBLY, 0));
		materials.add(new Offer(new ItemStack(ModItems.pellet_rtg), Requirement.NUCLEAR, 0));
		materials.add(new Offer(new ItemStack(ModItems.piston_selenium), Requirement.ASSEMBLY, 0));
		materials.add(new Offer(ItemBattery.getFullBattery(ModItems.battery_advanced), Requirement.ASSEMBLY, 0));
		materials.add(new Offer(ItemBattery.getFullBattery(ModItems.battery_lithium), Requirement.CHEMICS, 0));
		materials.add(new Offer(new ItemStack(ModItems.arc_electrode), Requirement.ASSEMBLY, 0));
		materials.add(new Offer(new ItemStack(ModItems.fuse), Requirement.ASSEMBLY, 0));

		machines.add(new Offer(new ItemStack(ModBlocks.concrete_smooth), Requirement.CHEMICS, 0));
		machines.add(new Offer(new ItemStack(ModBlocks.brick_compound), Requirement.CHEMICS, 0));
		machines.add(new Offer(new ItemStack(ModBlocks.barbed_wire), Requirement.ASSEMBLY, 0));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_siren), Requirement.ASSEMBLY, 0));
		machines.add(new Offer(new ItemStack(ModBlocks.vault_door), Requirement.CHEMICS, 0));
		machines.add(new Offer(new ItemStack(ModBlocks.blast_door), Requirement.CHEMICS, 0));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_epress), Requirement.OIL, 0));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_difurnace_off), Requirement.STEEL, 0));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_gascent), Requirement.OIL, 0));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_diesel), Requirement.CHEMICS, 0));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_geo), Requirement.CHEMICS, 0));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_battery), Requirement.ASSEMBLY, 0));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_lithium_battery), Requirement.CHEMICS, 0));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_assembler), Requirement.ASSEMBLY, 0));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_chemplant), Requirement.CHEMICS, 0));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_boiler_off), Requirement.CHEMICS, 0));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_boiler_electric_off), Requirement.OIL, 0));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_shredder), Requirement.ASSEMBLY, 0));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_well), Requirement.OIL, 0));
		machines.add(new Offer(new ItemStack(ModBlocks.machine_refinery), Requirement.OIL, 0));
		machines.add(new Offer(new ItemStack(ModBlocks.absorber), Requirement.CHEMICS, 0));
		machines.add(new Offer(new ItemStack(ModBlocks.absorber_green), Requirement.CHEMICS, 0));
		machines.add(new Offer(new ItemStack(ModBlocks.decon), Requirement.CHEMICS, 0));
		
		weapons.add(new Offer(new ItemStack(ModBlocks.launch_pad), Requirement.OIL, 0));
		weapons.add(new Offer(new ItemStack(ModBlocks.machine_radar), Requirement.OIL, 0));
		weapons.add(new Offer(new ItemStack(ModItems.designator), Requirement.CHEMICS, 0));
		weapons.add(new Offer(new ItemStack(ModItems.designator_range), Requirement.CHEMICS, 0));
		weapons.add(new Offer(new ItemStack(ModItems.sat_chip), Requirement.CHEMICS, 0));
		weapons.add(new Offer(new ItemStack(ModBlocks.turret_cheapo), Requirement.CHEMICS, 0));
		weapons.add(new Offer(new ItemStack(ModItems.turret_cheapo_ammo), Requirement.ASSEMBLY, 0));
		weapons.add(new Offer(new ItemStack(ModItems.turret_control), Requirement.CHEMICS, 0));
		weapons.add(new Offer(new ItemStack(ModItems.turret_chip), Requirement.CHEMICS, 0));
		weapons.add(new Offer(new ItemStack(ModItems.turret_biometry), Requirement.CHEMICS, 0));
		weapons.add(new Offer(new ItemStack(ModBlocks.mine_ap), Requirement.ASSEMBLY, 0));
		weapons.add(new Offer(new ItemStack(ModBlocks.emp_bomb), Requirement.CHEMICS, 0));
		weapons.add(new Offer(new ItemStack(ModBlocks.det_cord), Requirement.ASSEMBLY, 0));
		weapons.add(new Offer(new ItemStack(ModBlocks.det_charge), Requirement.CHEMICS, 0));
		weapons.add(new Offer(new ItemStack(ModItems.detonator), Requirement.ASSEMBLY, 0));
		weapons.add(new Offer(new ItemStack(ModItems.detonator_laser), Requirement.CHEMICS, 0));
		weapons.add(new Offer(new ItemStack(ModItems.defuser), Requirement.OIL, 0));
		weapons.add(new Offer(new ItemStack(ModItems.gun_revolver), Requirement.ASSEMBLY, 0));
		weapons.add(new Offer(new ItemStack(ModItems.gun_revolver_nopip), Requirement.ASSEMBLY, 0));
		weapons.add(new Offer(new ItemStack(ModItems.gun_minigun), Requirement.OIL, 0));
		weapons.add(new Offer(new ItemStack(ModItems.gun_panzerschreck), Requirement.ASSEMBLY, 0));
		weapons.add(new Offer(new ItemStack(ModItems.gun_hk69), Requirement.ASSEMBLY, 0));
		weapons.add(new Offer(new ItemStack(ModItems.gun_uzi), Requirement.OIL, 0));
		weapons.add(new Offer(new ItemStack(ModItems.gun_lever_action), Requirement.ASSEMBLY, 0));
		weapons.add(new Offer(new ItemStack(ModItems.gun_bolt_action), Requirement.ASSEMBLY, 0));
		weapons.add(new Offer(new ItemStack(ModItems.gun_revolver_ammo), Requirement.OIL, 0));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_357_desh), Requirement.OIL, 0));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_44), Requirement.OIL, 0));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_44_ap), Requirement.OIL, 0));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_5mm), Requirement.OIL, 0));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_5mm_du), Requirement.OIL, 0));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_rocket), Requirement.OIL, 0));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_rocket_incendiary), Requirement.OIL, 0));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_rocket_sleek), Requirement.OIL, 0));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_grenade), Requirement.OIL, 0));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_grenade_incendiary), Requirement.OIL, 0));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_grenade_sleek), Requirement.OIL, 0));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_22lr), Requirement.OIL, 0));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_22lr_ap), Requirement.OIL, 0));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_20gauge), Requirement.OIL, 0));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_20gauge_slug), Requirement.OIL, 0));
		weapons.add(new Offer(new ItemStack(ModItems.ammo_20gauge_flechette), Requirement.OIL, 0));
		weapons.add(new Offer(new ItemStack(ModItems.grenade_if_generic), Requirement.CHEMICS, 0));
		weapons.add(new Offer(new ItemStack(ModItems.grenade_if_he), Requirement.CHEMICS, 0));
		
		tools.add(new Offer(new ItemStack(ModBlocks.machine_keyforge), Requirement.STEEL, 0));
		tools.add(new Offer(new ItemStack(ModBlocks.machine_telelinker), Requirement.CHEMICS, 0));
		tools.add(new Offer(new ItemStack(ModBlocks.machine_satlinker), Requirement.CHEMICS, 0));
		tools.add(new Offer(new ItemStack(ModItems.oil_detector), Requirement.CHEMICS, 0));
		tools.add(new Offer(new ItemStack(ModItems.geiger_counter), Requirement.CHEMICS, 0));
		tools.add(new Offer(new ItemStack(ModItems.key), Requirement.STEEL, 0));
		tools.add(new Offer(new ItemStack(ModItems.padlock), Requirement.STEEL, 0));
		tools.add(new Offer(new ItemStack(ModItems.padlock_reinforced), Requirement.OIL, 0));
		tools.add(new Offer(new ItemStack(ModItems.syringe_antidote), Requirement.STEEL, 0));
		tools.add(new Offer(new ItemStack(ModItems.syringe_metal_stimpak), Requirement.STEEL, 0));
		tools.add(new Offer(new ItemStack(ModItems.syringe_metal_medx), Requirement.STEEL, 0));
		tools.add(new Offer(new ItemStack(ModItems.radaway), Requirement.ASSEMBLY, 0));
		tools.add(new Offer(new ItemStack(ModItems.radaway_strong), Requirement.ASSEMBLY, 0));
		tools.add(new Offer(new ItemStack(ModItems.radx), Requirement.ASSEMBLY, 0));
		tools.add(new Offer(new ItemStack(ModItems.pill_iodine), Requirement.ASSEMBLY, 0));
		tools.add(new Offer(new ItemStack(ModItems.gas_mask_filter), Requirement.ASSEMBLY, 0));
		tools.add(new Offer(new ItemStack(ModItems.gun_kit_1), Requirement.OIL, 0));
		tools.add(new Offer(new ItemStack(ModItems.gun_kit_2), Requirement.OIL, 0));
		tools.add(new Offer(new ItemStack(ModItems.hazmat_kit), Requirement.ASSEMBLY, 0));
		tools.add(new Offer(new ItemStack(ModItems.hazmat_red_kit), Requirement.CHEMICS, 0));
		tools.add(new Offer(new ItemStack(ModItems.hazmat_grey_kit), Requirement.OIL, 0));
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
		}
		
		return null;
	}

}

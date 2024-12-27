package com.hbm.itempool;

import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.material.Mats;
import com.hbm.items.ItemEnums.EnumCasingType;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemBreedingRod.BreedingRodType;
import com.hbm.items.machine.ItemCircuit.EnumCircuitType;
import com.hbm.items.machine.ItemZirnoxRod.EnumZirnoxType;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmo;

import static com.hbm.lib.HbmChestContents.*;

import com.hbm.blocks.ModBlocks;

import net.minecraft.init.Items;
import net.minecraft.util.WeightedRandomChestContent;

/**
 * Item pools for "legacy" structures, i.e. schematic2java ones
 * @author hbm
 *
 */
public class ItemPoolsLegacy {

	public static final String POOL_GENERIC = "POOL_GENERIC";
	public static final String POOL_ANTENNA = "POOL_ANTENNA";
	public static final String POOL_EXPENSIVE = "POOL_EXPENSIVE";
	public static final String POOL_NUKE_TRASH = "POOL_NUKE_TRASH";
	public static final String POOL_NUKE_MISC = "POOL_NUKE_MISC";
	public static final String POOL_VERTIBIRD = "POOL_VERTIBIRD";
	public static final String POOL_SPACESHIP = "POOL_SPACESHIP";
	
	public static void init() {

		//"generic" set, found commonly in chests in many structures
		new ItemPool(POOL_GENERIC) {{
			this.pool = new WeightedRandomChestContent[] {
					weighted(Items.bread, 0, 1, 5, 8),
					weighted(ModItems.twinkie, 0, 1, 3, 6),
					weighted(Items.iron_ingot, 0, 2, 6, 10),
					weighted(ModItems.ingot_steel, 0, 2, 5, 7),
					weighted(ModItems.ingot_beryllium, 0, 1, 2, 4),
					weighted(ModItems.ingot_titanium, 0, 1, 1, 3),
					weighted(ModItems.circuit, EnumCircuitType.VACUUM_TUBE.ordinal(), 1, 1, 5),
					weighted(ModItems.gun_light_revolver, 0, 1, 1, 3),
					weighted(ModItems.ammo_standard, EnumAmmo.M357_SP.ordinal(), 2, 6, 4),
					weighted(ModItems.ammo_standard, EnumAmmo.G12_BP.ordinal(), 3, 6, 3),
					weighted(ModItems.ammo_standard, EnumAmmo.G26_FLARE_SUPPLY.ordinal(), 1, 1, 1),
					weighted(ModItems.gun_kit_1, 0, 1, 3, 4),
					weighted(ModItems.gun_maresleg, 0, 1, 1, 1),
					weighted(ModItems.casing, EnumCasingType.SMALL.ordinal(), 4, 10, 3),
					weighted(ModItems.casing, EnumCasingType.SHOTSHELL.ordinal(), 4, 10, 3),
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
					weighted(ModItems.gas_mask_filter, 0, 1, 1, 3)
			};
		}};

		//"antenna" pool, found by antennas and in radio stations
		new ItemPool(POOL_ANTENNA) {{
			this.pool = new WeightedRandomChestContent[] {
					weighted(ModItems.twinkie, 0, 1, 3, 4),
					weighted(ModItems.ingot_steel, 0, 1, 2, 7),
					weighted(ModItems.ingot_red_copper, 0, 1, 1, 4),
					weighted(ModItems.ingot_titanium, 0, 1, 3, 5),
					weighted(ModItems.wire_fine, Mats.MAT_MINGRADE.id, 2, 3, 7),
					weighted(ModItems.circuit, EnumCircuitType.VACUUM_TUBE.ordinal(), 1, 1, 4),
					weighted(ModItems.circuit, EnumCircuitType.CAPACITOR.ordinal(), 1, 1, 2),
					weighted(ModItems.battery_generic, 0, 1, 1, 4),
					weighted(ModItems.battery_advanced, 0, 1, 1, 3),
					weighted(ModItems.powder_iodine, 0, 1, 1, 1),
					weighted(ModItems.powder_bromine, 0, 1, 1, 1),
					weighted(ModBlocks.steel_poles, 0, 1, 4, 8),
					weighted(ModBlocks.steel_scaffold, 0, 1, 3, 8),
					weighted(ModBlocks.pole_top, 0, 1, 1, 4),
					weighted(ModBlocks.pole_satellite_receiver, 0, 1, 1, 7),
					weighted(ModItems.scrap, 0, 1, 3, 10),
					weighted(ModItems.dust, 0, 2, 4, 9),
					weighted(ModItems.bottle_opener, 0, 1, 1, 2),
					weighted(ModItems.bottle_nuka, 0, 1, 3, 4),
					weighted(ModItems.bottle_cherry, 0, 1, 1, 2),
					weighted(ModItems.stealth_boy, 0, 1, 1, 1),
					weighted(ModItems.cap_nuka, 0, 1, 15, 7),
					weighted(ModItems.bomb_caller, 0, 1, 1, 1),
					weighted(ModItems.gas_mask_filter, 0, 1, 1, 2)
			};
		}};
		
		//"hidden" loot
		new ItemPool(POOL_EXPENSIVE) {{
			this.pool = new WeightedRandomChestContent[] {
					weighted(ModItems.chlorine_pinwheel, 0, 1, 1, 1),
					weighted(ModItems.circuit, EnumCircuitType.VACUUM_TUBE.ordinal(), 1, 1, 4),
					weighted(ModItems.circuit, EnumCircuitType.ANALOG.ordinal(), 1, 1, 3),
					weighted(ModItems.circuit, EnumCircuitType.CHIP.ordinal(), 1, 1, 2),
					weighted(ModItems.gun_kit_1, 0, 1, 3, 6),
					weighted(ModItems.gun_kit_2, 0, 1, 2, 3),
					weighted(ModItems.gun_panzerschreck, 0, 1, 1, 4),
					weighted(ModItems.ammo_standard, EnumAmmo.ROCKET_HE.ordinal(), 1, 4, 5),
					weighted(ModItems.ammo_standard, EnumAmmo.G26_FLARE_SUPPLY.ordinal(), 1, 1, 5),
					weighted(ModItems.ammo_standard, EnumAmmo.G26_FLARE_WEAPON.ordinal(), 1, 1, 3),
					weighted(ModItems.grenade_nuclear, 0, 1, 1, 2),
					weighted(ModItems.grenade_smart, 0, 1, 3, 3),
					weighted(ModItems.grenade_mirv, 0, 1, 1, 2),
					weighted(ModItems.stealth_boy, 0, 1, 1, 2),
					weighted(ModItems.battery_advanced, 0, 1, 1, 3),
					weighted(ModItems.battery_advanced_cell, 0, 1, 1, 2),
					weighted(ModItems.battery_schrabidium, 0, 1, 1, 1),
					weighted(ModItems.syringe_awesome, 0, 1, 1, 1),
					weighted(ModItems.fusion_core, 0, 1, 1, 4),
					weighted(ModItems.bottle_nuka, 0, 1, 3, 6),
					weighted(ModItems.bottle_quantum, 0, 1, 1, 3),
					weighted(ModBlocks.red_barrel, 0, 1, 1, 6),
					weighted(ModItems.canister_full, Fluids.DIESEL.getID(), 1, 2, 2),
					weighted(ModItems.canister_full, Fluids.BIOFUEL.getID(), 1, 2, 3),
					weighted(ModItems.gas_mask_m65, 60, 1, 1, 5),
					weighted(ModItems.bomb_caller, 0, 1, 1, 2),
					weighted(ModItems.bomb_caller, 1, 1, 1, 1),
					weighted(ModItems.bomb_caller, 2, 1, 1, 1),
					weighted(ModItems.gas_mask_filter, 0, 1, 1, 4),
					weighted(ModItems.journal_pip, 0, 1, 1, 1),
					weighted(ModItems.journal_bj, 0, 1, 1, 1),
					weighted(ModItems.launch_code_piece, 0, 1, 1, 1)
			};
		}};
		
		//nuclear waste products found in powerplants
		new ItemPool(POOL_NUKE_TRASH) {{
			this.pool = new WeightedRandomChestContent[] {
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
					weighted(ModBlocks.yellow_barrel, 0, 1, 1, 2)
			};
		}};
		
		//all sorts of nuclear related items, mostly fissile isotopes found in nuclear powerplants
		new ItemPool(POOL_NUKE_MISC) {{
			this.pool = new WeightedRandomChestContent[] {
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
					weighted(ModBlocks.yellow_barrel, 0, 1, 3, 3)
			};
		}};
		
		//loot found in vertibirds
		new ItemPool(POOL_VERTIBIRD) {{
			this.pool = new WeightedRandomChestContent[] {
					weighted(ModItems.t45_helmet, 0, 1, 1, 15),
					weighted(ModItems.t45_plate, 0, 1, 1, 15),
					weighted(ModItems.t45_legs, 0, 1, 1, 15),
					weighted(ModItems.t45_boots, 0, 1, 1, 15),
					weighted(ModItems.t45_kit, 0, 1, 1, 3),
					weighted(ModItems.fusion_core, 0, 1, 1, 10),
					weighted(ModItems.gun_light_revolver, 0, 1, 1, 4),
					weighted(ModItems.gun_kit_1, 0, 2, 3, 4),
					weighted(ModItems.ammo_standard, EnumAmmo.M357_FMJ.ordinal(), 1, 24, 4),
					weighted(ModItems.ammo_standard, EnumAmmo.G40_HE.ordinal(), 1, 6, 3),
					weighted(ModItems.ammo_standard, EnumAmmo.G26_FLARE_WEAPON.ordinal(), 1, 1, 5),
					weighted(ModItems.rod, BreedingRodType.U235.ordinal(), 1, 1, 2),
					weighted(ModItems.billet_uranium_fuel, 0, 1, 1, 2),
					weighted(ModItems.ingot_uranium_fuel, 0, 1, 1, 2),
					weighted(ModItems.bottle_nuka, 0, 1, 3, 6),
					weighted(ModItems.bottle_quantum, 0, 1, 1, 3),
					weighted(ModItems.stealth_boy, 0, 1, 1, 7),
					weighted(ModItems.gas_mask_m65, 0, 1, 1, 5),
					weighted(ModItems.gas_mask_filter, 0, 1, 1, 5),
					weighted(ModItems.grenade_nuclear, 0, 1, 2, 2),
					weighted(ModItems.bomb_caller, 0, 1, 1, 1),
					weighted(ModItems.bomb_caller, 1, 1, 1, 1),
					weighted(ModItems.bomb_caller, 2, 1, 1, 2)
			};
		}};
		
		//spaceship double chests
		new ItemPool(POOL_SPACESHIP) {{
			this.pool = new WeightedRandomChestContent[] {
					weighted(ModItems.battery_advanced, 0, 1, 1, 5),
					weighted(ModItems.ingot_advanced_alloy, 0, 2, 16, 5),
					weighted(ModItems.wire_fine, Mats.MAT_ALLOY.id, 8, 32, 5),
					weighted(ModItems.coil_advanced_alloy, 0, 2, 16, 5),
					weighted(ModItems.cell_deuterium, 0, 1, 8, 5),
					weighted(ModItems.cell_tritium, 0, 1, 8, 5),
					weighted(ModItems.cell_antimatter, 0, 1, 1, 1),
					weighted(ModItems.powder_neodymium, 0, 1, 1, 1),
					weighted(ModItems.powder_niobium, 0, 1, 1, 1),
					weighted(ModBlocks.fusion_conductor, 0, 2, 4, 5),
					weighted(ModBlocks.fusion_heater, 0, 1, 3, 5),
					weighted(ModBlocks.pwr_fuel, 0, 1, 2, 5),
					weighted(ModBlocks.block_tungsten, 0, 3, 8, 5),
					weighted(ModBlocks.red_wire_coated, 0, 4, 8, 5),
					weighted(ModBlocks.red_cable, 0, 8, 16, 5)
			};
		}};
	}
}

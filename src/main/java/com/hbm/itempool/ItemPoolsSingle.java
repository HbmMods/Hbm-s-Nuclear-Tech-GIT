package com.hbm.itempool;

import static com.hbm.lib.HbmChestContents.weighted;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemCircuit.EnumCircuitType;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmo;

import net.minecraft.init.Items;
import net.minecraft.util.WeightedRandomChestContent;

public class ItemPoolsSingle {

	public static final String POOL_POWDER = "POOL_POWDER";
	public static final String POOL_VAULT_RUSTY = "POOL_VAULT_RUSTY";
	public static final String POOL_VAULT_STANDARD = "POOL_VAULT_STANDARD";
	public static final String POOL_VAULT_REINFORCED = "POOL_VAULT_REINFORCED";
	public static final String POOL_VAULT_UNBREAKABLE = "POOL_VAULT_UNBREAKABLE";
	public static final String POOL_METEORITE_TREASURE = "POOL_METEORITE_TREASURE";
	
	public static void init() {

		//powder boxes
		new ItemPool(POOL_POWDER) {{
			this.pool = new WeightedRandomChestContent[] {
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
					weighted(ModItems.powder_cerium, 0, 1, 32, 1)
			};
		}};
		
		new ItemPool(POOL_VAULT_RUSTY) {{
			this.pool = new WeightedRandomChestContent[] {
					weighted(Items.gold_ingot, 0, 3, 14, 1),
					weighted(ModItems.gun_heavy_revolver, 0, 1, 1, 2),
					weighted(ModItems.pin, 0, 8, 8, 1),
					weighted(ModItems.gun_am180, 0, 1, 1, 1),
					weighted(ModItems.bottle_quantum, 0, 1, 3, 1),
					weighted(ModItems.ingot_advanced_alloy, 0, 4, 12, 1),
					weighted(ModItems.ammo_standard, EnumAmmo.BMG50_FMJ.ordinal(), 24, 48, 1),
					weighted(ModItems.ammo_standard, EnumAmmo.P9_JHP.ordinal(), 48, 64, 2),
					weighted(ModItems.circuit, EnumCircuitType.CHIP.ordinal(), 3, 6, 1),
					weighted(ModItems.gas_mask_m65, 0, 1, 1, 1),
					weighted(ModItems.grenade_if_he, 0, 1, 1, 1),
					weighted(ModItems.grenade_if_incendiary, 0, 1, 1, 1),
					weighted(Items.diamond, 0, 1, 2, 1)
			};
		}};
		
		new ItemPool(POOL_VAULT_STANDARD) {{
			this.pool = new WeightedRandomChestContent[] {
					weighted(ModItems.ingot_desh, 0, 2, 6, 1),
					weighted(ModItems.battery_advanced_cell_4, 0, 1, 1, 1),
					weighted(ModItems.powder_desh_mix, 0, 1, 5, 1),
					weighted(Items.diamond, 0, 3, 6, 1),
					weighted(ModItems.ammo_standard, EnumAmmo.NUKE_STANDARD.ordinal(), 1, 1, 1),
					weighted(ModItems.ammo_container, 0, 1, 1, 1),
					weighted(ModItems.grenade_nuclear, 0, 1, 1, 1),
					weighted(ModItems.grenade_smart, 0, 1, 6, 1),
					weighted(ModItems.powder_yellowcake, 0, 16, 24, 1),
					weighted(ModItems.gun_uzi, 0, 1, 1, 1),
					weighted(ModItems.circuit, EnumCircuitType.VACUUM_TUBE.ordinal(), 12, 16, 1),
					weighted(ModItems.circuit, EnumCircuitType.CHIP.ordinal(), 2, 6, 1)
			};
		}};
		
		new ItemPool(POOL_VAULT_REINFORCED) {{
			this.pool = new WeightedRandomChestContent[] {
					weighted(ModItems.ingot_desh, 0, 6, 16, 1),
					weighted(ModItems.battery_lithium, 0, 1, 1, 1),
					weighted(ModItems.powder_power, 0, 1, 5, 1),
					weighted(ModItems.sat_chip, 0, 1, 1, 1),
					weighted(Items.diamond, 0, 5, 9, 1),
					weighted(ModItems.warhead_nuclear, 0, 1, 1, 1),
					weighted(ModItems.ammo_standard, EnumAmmo.NUKE_STANDARD.ordinal(), 1, 3, 1),
					weighted(ModItems.ammo_container, 0, 1, 4, 1),
					weighted(ModItems.grenade_nuclear, 0, 1, 2, 1),
					weighted(ModItems.grenade_mirv, 0, 1, 1, 1),
					weighted(ModItems.powder_yellowcake, 0, 26, 42, 1),
					weighted(ModItems.ingot_u235, 0, 3, 6, 1),
					weighted(ModItems.gun_heavy_revolver, 0, 1, 1, 1),
					weighted(ModItems.circuit, EnumCircuitType.CHIP.ordinal(), 18, 32, 1),
					weighted(ModItems.circuit, EnumCircuitType.BASIC.ordinal(), 6, 12, 1)
			};
		}};
		
		new ItemPool(POOL_VAULT_UNBREAKABLE) {{
			this.pool = new WeightedRandomChestContent[] {
					weighted(ModItems.ammo_container, 0, 3, 6, 1),
					weighted(ModItems.ammo_standard, EnumAmmo.NUKE_DEMO.ordinal(), 2, 3, 1),
					weighted(ModItems.gun_carbine, 0, 1, 1, 1),
					weighted(ModItems.gun_congolake, 0, 1, 1, 1),
					weighted(ModItems.gun_b92, 0, 1, 1, 1),
					weighted(ModItems.ingot_combine_steel, 0, 16, 28, 1),
					weighted(ModItems.man_core, 0, 1, 1, 1),
					weighted(ModItems.boy_kit, 0, 1, 1, 1),
					weighted(ModItems.nuke_starter_kit, 0, 1, 1, 1),
					weighted(ModItems.weaponized_starblaster_cell, 0, 1, 1, 1),
					weighted(ModItems.warhead_mirv, 0, 1, 1, 1),
					weighted(ModItems.battery_schrabidium_cell, 0, 1, 1, 1),
					weighted(ModItems.powder_nitan_mix, 0, 16, 32, 1)
			};
		}};
		
		new ItemPool(POOL_METEORITE_TREASURE) {{
			this.pool = new WeightedRandomChestContent[] {
					weighted(ModItems.cobalt_pickaxe, 0, 1, 1, 10),
					weighted(ModItems.ingot_zirconium, 0, 1, 16, 10),
					weighted(ModItems.ingot_niobium, 0, 1, 16, 10),
					weighted(ModItems.ingot_cobalt, 0, 1, 16, 10),
					weighted(ModItems.ingot_boron, 0, 1, 16, 10),
					weighted(ModItems.ingot_starmetal, 0, 1, 1, 5),
					weighted(ModItems.crystal_gold, 0, 1, 4, 10),
					weighted(ModItems.circuit, EnumCircuitType.VACUUM_TUBE.ordinal(), 4, 8, 10),
					weighted(ModItems.circuit, EnumCircuitType.CHIP.ordinal(), 2, 4, 10),
					weighted(ModItems.definitelyfood, 0, 16, 32, 25),
					weighted(ModBlocks.crate_can, 0, 1, 3, 10),
					weighted(ModItems.pill_herbal, 0, 1, 2, 10),
					weighted(ModItems.serum, 0, 1, 1, 5),
					weighted(ModItems.heart_piece, 0, 1, 1, 5),
					weighted(ModItems.scrumpy, 0, 1, 1, 5),
					weighted(ModItems.launch_code_piece, 0, 1, 1, 5),
					weighted(ModItems.egg_glyphid, 0, 1, 1, 5),
					weighted(ModItems.gem_alexandrite, 0, 1, 1, 1),
			};
		}};
	}
}

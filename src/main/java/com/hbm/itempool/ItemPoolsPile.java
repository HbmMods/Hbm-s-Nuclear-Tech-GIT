package com.hbm.itempool;

import static com.hbm.lib.HbmChestContents.weighted;

import com.hbm.inventory.material.Mats;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmo;

import net.minecraft.init.Items;
import net.minecraft.util.WeightedRandomChestContent;

public class ItemPoolsPile {

	public static final String POOL_PILE_HIVE = "POOL_PILE_HIVE";
	public static final String POOL_PILE_BONES = "POOL_PILE_BONES";
	public static final String POOL_PILE_CAPS = "POOL_PILE_CAPS";
	public static final String POOL_PILE_MED_SYRINGE = "POOL_PILE_MED_SYRINGE";
	public static final String POOL_PILE_MED_PILLS = "POOL_PILE_MED_PILLS";
	public static final String POOL_PILE_MAKESHIFT_GUN = "POOL_PILE_MAKESHIFT_GUN";
	public static final String POOL_PILE_MAKESHIFT_WRENCH = "POOL_PILE_MAKESHIFT_WRENCH";
	public static final String POOL_PILE_MAKESHIFT_PLATES = "POOL_PILE_MAKESHIFT_PLATES";
	public static final String POOL_PILE_MAKESHIFT_WIRE = "POOL_PILE_MAKESHIFT_WIRE";
	public static final String POOL_PILE_NUKE_STORAGE = "POOL_PILE_NUKE_STORAGE";
	
	public static void init() {
		
		//items found in glyphid hives
		new ItemPool(POOL_PILE_HIVE) {{
			this.pool = new WeightedRandomChestContent[] {
					//Materials
					weighted(Items.iron_ingot, 0, 1, 3, 10),
					weighted(ModItems.ingot_steel, 0, 1, 2, 10),
					weighted(ModItems.ingot_aluminium, 0, 1, 2, 10),
					weighted(ModItems.scrap, 0, 3, 6, 10),
					//Armor
					weighted(ModItems.gas_mask_m65, 0, 1, 1, 10),
					weighted(ModItems.steel_plate, 0, 1, 1, 5),
					weighted(ModItems.steel_legs, 0, 1, 1, 5),
					//Gear
					weighted(ModItems.steel_pickaxe, 0, 1, 1, 5),
					weighted(ModItems.steel_shovel, 0, 1, 1, 5),
					//Weapons
					weighted(ModItems.gun_maresleg, 0, 1, 1, 5),
					weighted(ModItems.gun_light_revolver, 0, 1, 1, 1),
					weighted(ModItems.grenade_if_generic, 0, 1, 2, 5),
					weighted(ModItems.ammo_standard, EnumAmmo.G12.ordinal(), 4, 4, 10),
					weighted(ModItems.ammo_standard, EnumAmmo.M357_SP.ordinal(), 6, 12, 10),
					weighted(ModItems.ammo_standard, EnumAmmo.G40_HE.ordinal(), 1, 1, 2),
					//Consumables
					weighted(ModItems.bottle_nuka, 0, 1, 2, 20),
					weighted(ModItems.bottle_quantum, 0, 1, 2, 1),
					weighted(ModItems.definitelyfood, 0, 5, 12, 20),
					weighted(ModItems.egg_glyphid, 0, 1, 3, 30),
					weighted(ModItems.syringe_metal_stimpak, 0, 1, 1, 5),
					weighted(ModItems.iv_blood, 0, 1, 1, 10),
					weighted(Items.experience_bottle, 0, 1, 3, 5),
			};
		}};
		
		//items found in glyphid bone piles
		new ItemPool(POOL_PILE_BONES) {{
			this.pool = new WeightedRandomChestContent[] {
					weighted(Items.bone, 0, 1, 1, 10),
					weighted(Items.rotten_flesh, 0, 1, 1, 5),
					weighted(ModItems.biomass, 0, 1, 1, 2)
			};
		}};
		
		//bottlecap stashess
		new ItemPool(POOL_PILE_CAPS) {{
			this.pool = new WeightedRandomChestContent[] {
					weighted(ModItems.cap_nuka, 0, 4, 4, 20),
					weighted(ModItems.cap_quantum, 0, 4, 4, 3),
					weighted(ModItems.cap_sparkle, 0, 4, 4, 1),
			};
		}};
		
		//medicine stashes
		new ItemPool(POOL_PILE_MED_SYRINGE) {{
			this.pool = new WeightedRandomChestContent[] {
					weighted(ModItems.syringe_metal_stimpak, 0, 1, 1, 10),
					weighted(ModItems.syringe_metal_medx, 0, 1, 1, 5),
					weighted(ModItems.syringe_metal_psycho, 0, 1, 1, 5),
			};
		}};
		new ItemPool(POOL_PILE_MED_PILLS) {{
			this.pool = new WeightedRandomChestContent[] {
					weighted(ModItems.radaway, 0, 1, 1, 10),
					weighted(ModItems.radx, 0, 1, 1, 10),
					weighted(ModItems.iv_blood, 0, 1, 1, 15),
					weighted(ModItems.siox, 0, 1, 1, 5),
			};
		}};
		
		//makeshift gun
		new ItemPool(POOL_PILE_MAKESHIFT_GUN) {{ this.pool = new WeightedRandomChestContent[] { weighted(ModItems.gun_maresleg, 0, 1, 1, 10) }; }};
		new ItemPool(POOL_PILE_MAKESHIFT_WRENCH) {{ this.pool = new WeightedRandomChestContent[] { weighted(ModItems.wrench, 0, 1, 1, 10) }; }};
		new ItemPool(POOL_PILE_MAKESHIFT_PLATES) {{ this.pool = new WeightedRandomChestContent[] { weighted(ModItems.plate_steel, 0, 1, 1, 10) }; }};
		new ItemPool(POOL_PILE_MAKESHIFT_WIRE) {{ this.pool = new WeightedRandomChestContent[] { weighted(ModItems.wire_fine, Mats.MAT_ALUMINIUM.id, 1, 1, 10) }; }};
		
		new ItemPool(POOL_PILE_NUKE_STORAGE) {{
			this.pool = new WeightedRandomChestContent[] {
					weighted(ModItems.ammo_standard, EnumAmmo.NUKE_STANDARD.ordinal(), 1, 1, 50),
					weighted(ModItems.ammo_standard, EnumAmmo.NUKE_HIGH.ordinal(), 1, 1, 10),
					weighted(ModItems.ammo_standard, EnumAmmo.NUKE_TOTS.ordinal(), 1, 1, 10),

			};
		}};
	}
}

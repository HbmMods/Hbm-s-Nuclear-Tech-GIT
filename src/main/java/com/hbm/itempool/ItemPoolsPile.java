package com.hbm.itempool;

import static com.hbm.lib.HbmChestContents.weighted;

import com.hbm.items.ItemAmmoEnums.Ammo357Magnum;
import com.hbm.items.ItemAmmoEnums.AmmoFatman;
import com.hbm.items.ModItems;

import net.minecraft.init.Items;
import net.minecraft.util.WeightedRandomChestContent;

public class ItemPoolsPile {

	public static final String POOL_PILE_HIVE = "POOL_PILE_HIVE";
	public static final String POOL_PILE_BONES = "POOL_PILE_BONES";
	public static final String POOL_PILE_CAPS = "POOL_PILE_CAPS";
	
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
					weighted(ModItems.gun_lever_action, 0, 1, 1, 5),
					weighted(ModItems.gun_bio_revolver, 0, 1, 1, 1),
					weighted(ModItems.grenade_if_generic, 0, 1, 2, 5),
					weighted(ModItems.ammo_20gauge, 0, 8, 8, 10),
					weighted(ModItems.ammo_12gauge, 0, 4, 4, 10),
					weighted(ModItems.ammo_357, Ammo357Magnum.LEAD.ordinal(), 6, 12, 10),
					weighted(ModItems.ammo_grenade, 0, 1, 1, 2),
					weighted(ModItems.ammo_nuke, AmmoFatman.PUMPKIN.ordinal(), 1, 1, 1),
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
	}
}

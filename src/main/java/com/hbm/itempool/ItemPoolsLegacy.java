package com.hbm.itempool;

import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;
import com.hbm.items.ItemAmmoEnums.Ammo357Magnum;

import static com.hbm.lib.HbmChestContents.*;

import net.minecraft.init.Items;
import net.minecraft.util.WeightedRandomChestContent;

/**
 * Item pools for "legacy" structures, i.e. schematic2java ones
 * @author hbm
 *
 */
public class ItemPoolsLegacy {

	//"generic" set, found commonly in chests in many structures
	public static ItemPool poolGeneric = new ItemPool("POOL_GENERIC") {{
		this.pool = new WeightedRandomChestContent[] {
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
				weighted(ModItems.gas_mask_filter, 0, 1, 1, 3)
		};
	}};
}

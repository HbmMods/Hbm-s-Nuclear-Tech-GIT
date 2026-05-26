package com.hbm.itempool;

import static com.hbm.lib.HbmChestContents.weighted;

import com.hbm.items.ModItems;
import com.hbm.items.food.ItemConserve.EnumFoodType;

import net.minecraft.util.WeightedRandomChestContent;

public class ItemPoolsVendingMachine {

	public static final String POOL_SODA = "POOL_SODA";
	public static final String POOL_SNACKS = "POOL_SNACKS";

	
	public static void init() {

		new ItemPool(POOL_SODA) {{
			this.pool = new WeightedRandomChestContent[] {
					weighted(ModItems.bottle_nuka, 0, 1, 1, 10),
					weighted(ModItems.bottle_cherry, 0, 1, 1, 5),
					weighted(ModItems.bottle_quantum, 0, 1, 1, 1),
					weighted(ModItems.can_bepis, 0, 1, 1, 10),
					weighted(ModItems.can_luna, 0, 1, 1, 10),
					weighted(ModItems.can_mug, 0, 1, 1, 10),
					weighted(ModItems.can_breen, 0, 1, 1, 1),
			};
		}};

		new ItemPool(POOL_SNACKS) {{
			this.pool = new WeightedRandomChestContent[] {
					weighted(ModItems.definitelyfood, 0, 1, 1, 10),
					weighted(ModItems.canned_conserve, EnumFoodType.BEEF.ordinal(), 1, 1, 5),
					weighted(ModItems.canned_conserve, EnumFoodType.TUBE.ordinal(), 1, 1, 5),
					weighted(ModItems.twinkie, 0, 1, 1, 10),
					weighted(ModItems.chocolate, 0, 1, 1, 10),
			};
		}};
	}
}

package com.hbm.itempool;

import static com.hbm.lib.HbmChestContents.weighted;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;

import net.minecraft.util.WeightedRandomChestContent;

public class ItemPoolsC130 {

	public static final String POOL_SUPPLIES = "POOL_SUPPLIES";
	public static final String POOL_WEAPONS = "POOL_WEAPONS";

	
	public static void init() {

		new ItemPool(POOL_SUPPLIES) {{
			this.pool = new WeightedRandomChestContent[] {
					weighted(ModItems.definitelyfood, 0, 3, 10, 25),
					weighted(ModItems.syringe_metal_stimpak, 0, 1, 3, 10),
					weighted(ModItems.pill_iodine, 0, 1, 2, 2),
					weighted(ModItems.canister_full, Fluids.DIESEL.getID(), 1, 4, 5),
					weighted(ModBlocks.machine_diesel, 0, 1, 1, 1),
					weighted(ModItems.geiger_counter, 0, 1, 1, 2),
					weighted(ModItems.med_bag, 0, 1, 1, 3),
			};
		}};

		new ItemPool(POOL_WEAPONS) {{
			this.pool = new WeightedRandomChestContent[] {
					weighted(ModItems.gun_light_revolver, 0, 1, 1, 10),
					weighted(ModItems.gun_henry, 0, 1, 1, 10),
					weighted(ModItems.gun_maresleg, 0, 1, 1, 10),
					weighted(ModItems.gun_greasegun, 0, 1, 1, 10),
					weighted(ModItems.gun_carbine, 0, 1, 1, 5),
					weighted(ModItems.gun_heavy_revolver, 0, 1, 1, 5),
					weighted(ModItems.gun_panzerschreck, 0, 1, 1, 2),
			};
		}};
	}
}

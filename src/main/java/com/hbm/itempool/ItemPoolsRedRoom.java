package com.hbm.itempool;

import static com.hbm.lib.HbmChestContents.weighted;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ItemEnums.EnumSecretType;
import com.hbm.items.ModItems;

import net.minecraft.util.WeightedRandomChestContent;

public class ItemPoolsRedRoom {

	public static final String POOL_RED_PEDESTAL = "POOL_RED_PEDESTAL";
	public static final String POOL_BLACK_SLAB = "POOL_BLACK_SLAB";
	public static final String POOL_BLACK_PART = "POOL_BLACK_PART";
	
	public static void init() {
		
		//pedestal items
		new ItemPool(POOL_RED_PEDESTAL) {{
			this.pool = new WeightedRandomChestContent[] {
					weighted(ModItems.ballistic_gauntlet, 0, 1, 1, 10),
					weighted(ModItems.armor_polish, 0, 1, 1, 10),
					weighted(ModItems.bandaid, 0, 1, 1, 10),
					weighted(ModItems.serum, 0, 1, 1, 10),
					weighted(ModItems.quartz_plutonium, 0, 1, 1, 10),
					weighted(ModItems.morning_glory, 0, 1, 1, 10),
					weighted(ModItems.spider_milk, 0, 1, 1, 10),
					weighted(ModItems.ink, 0, 1, 1, 10),
					weighted(ModItems.heart_container, 0, 1, 1, 10),
					weighted(ModItems.black_diamond, 0, 1, 1, 10),
					weighted(ModItems.scrumpy, 0, 1, 1, 10),
					weighted(ModItems.wild_p, 0, 1, 1, 5),
					weighted(ModItems.card_aos, 0, 1, 1, 5),
					weighted(ModItems.card_qos, 0, 1, 1, 5),
					weighted(ModItems.starmetal_sword, 0, 1, 1, 5),
					weighted(ModItems.gem_alexandrite, 0, 1, 1, 5),
					weighted(ModItems.crackpipe, 0, 1, 1, 5),
					weighted(ModItems.flask_infusion, 0, 1, 1, 5),
					weighted(ModBlocks.boxcar, 0, 1, 1, 5),
					weighted(ModItems.book_of_, 0, 1, 1, 5),
			};
		}};
		
		//pedestal weapons
		new ItemPool(POOL_BLACK_SLAB) {{
			this.pool = new WeightedRandomChestContent[] {
					weighted(ModItems.clay_tablet, 0, 1, 1, 10)
			};
		}};
		
		//pedestal weapons
		new ItemPool(POOL_BLACK_PART) {{
			this.pool = new WeightedRandomChestContent[] {
					weighted(ModItems.item_secret, EnumSecretType.SELENIUM_STEEL.ordinal(), 4, 4, 10),
					weighted(ModItems.item_secret, EnumSecretType.CONTROLLER.ordinal(), 1, 1, 10),
					weighted(ModItems.item_secret, EnumSecretType.CANISTER.ordinal(), 1, 1, 10),
			};
		}};
	}
}

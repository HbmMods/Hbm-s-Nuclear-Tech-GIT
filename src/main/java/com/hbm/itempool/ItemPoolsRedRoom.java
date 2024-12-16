package com.hbm.itempool;

import static com.hbm.lib.HbmChestContents.weighted;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;

import net.minecraft.util.WeightedRandomChestContent;

public class ItemPoolsRedRoom {

	public static final String POOL_RED_PEDESTAL = "POOL_RED_PEDESTAL";
	public static final String POOL_RED_WEAPON = "POOL_RED_WEAPON";
	
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
					weighted(ModItems.gun_revolver_pip, 0, 1, 1, 5)
			};
		}};
		
		//pedestal weapons
		new ItemPool(POOL_RED_WEAPON) {{
			this.pool = new WeightedRandomChestContent[] {
					weighted(ModItems.gun_light_revolver_dani, 0, 1, 1, 10),
					weighted(ModItems.gun_maresleg_broken, 0, 1, 1, 10),
					weighted(ModItems.gun_heavy_revolver_lilmac, 0, 1, 1, 10),
					weighted(ModItems.gun_flamer_daybreaker, 0, 1, 1, 5),
					weighted(ModItems.gun_autoshotgun_sexy, 0, 1, 1, 5),
					weighted(ModItems.gun_minigun_lacunae, 0, 1, 1, 5),
					weighted(ModItems.gun_hangman, 0, 1, 1, 5),
					weighted(ModItems.gun_folly, 0, 1, 1, 1),
			};
		}};
	}
}

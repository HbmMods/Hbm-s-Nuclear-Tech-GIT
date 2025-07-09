package com.hbm.handler.microblocks;

import com.hbm.blocks.ModBlocks;
import cpw.mods.fml.common.event.FMLInterModComms;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class MicroBlocksCompatHandler {
	public static void preInit() {
		registerMicroBlockCompat(ModBlocks.basalt);
		registerMicroBlockCompat(ModBlocks.basalt_smooth);
		registerMicroBlockCompat(ModBlocks.basalt_brick);
		registerMicroBlockCompat(ModBlocks.basalt_polished);
		registerMicroBlockCompat(ModBlocks.basalt_tiles);

		registerMicroBlockCompat(ModBlocks.deco_titanium);
		registerMicroBlockCompat(ModBlocks.deco_red_copper);
		registerMicroBlockCompat(ModBlocks.deco_tungsten);
		registerMicroBlockCompat(ModBlocks.deco_aluminium);
		registerMicroBlockCompat(ModBlocks.deco_steel);
		registerMicroBlockCompat(ModBlocks.deco_rusty_steel);
		registerMicroBlockCompat(ModBlocks.deco_lead);
		registerMicroBlockCompat(ModBlocks.deco_beryllium);
		registerMicroBlockCompat(ModBlocks.deco_asbestos);
		registerMicroBlockCompat(ModBlocks.deco_rbmk);
		registerMicroBlockCompat(ModBlocks.deco_rbmk_smooth);

		registerMicroBlockCompat(ModBlocks.asphalt);
		registerMicroBlockCompat(ModBlocks.asphalt_light);

		registerMicroBlockCompat(ModBlocks.reinforced_brick);
		registerMicroBlockCompat(ModBlocks.reinforced_ducrete);
		registerMicroBlockCompat(ModBlocks.reinforced_sand);

		registerMicroBlockCompat(ModBlocks.reinforced_stone);
		registerMicroBlockCompat(ModBlocks.concrete_smooth);
		registerMicroBlockCompat(ModBlocks.concrete_colored);
		registerMicroBlockCompat(ModBlocks.concrete_colored_ext);
		registerMicroBlockCompat(ModBlocks.concrete);
		registerMicroBlockCompat(ModBlocks.concrete_asbestos);
		registerMicroBlockCompat(ModBlocks.concrete_super);
		registerMicroBlockCompat(ModBlocks.concrete_super_broken);
		registerMicroBlockCompat(ModBlocks.ducrete_smooth);
		registerMicroBlockCompat(ModBlocks.ducrete);
		registerMicroBlockCompat(ModBlocks.concrete_pillar);
		registerMicroBlockCompat(ModBlocks.brick_concrete);
		registerMicroBlockCompat(ModBlocks.brick_concrete_mossy);
		registerMicroBlockCompat(ModBlocks.brick_concrete_cracked);
		registerMicroBlockCompat(ModBlocks.brick_concrete_broken);
		registerMicroBlockCompat(ModBlocks.brick_concrete_marked);
		registerMicroBlockCompat(ModBlocks.brick_ducrete);
		registerMicroBlockCompat(ModBlocks.brick_obsidian);
		registerMicroBlockCompat(ModBlocks.brick_light);
		registerMicroBlockCompat(ModBlocks.brick_compound);
		registerMicroBlockCompat(ModBlocks.brick_asbestos);
		registerMicroBlockCompat(ModBlocks.brick_fire);
		registerMicroBlockCompat(ModBlocks.lightstone);

		registerMicroBlockCompat(ModBlocks.cmb_brick);
		registerMicroBlockCompat(ModBlocks.cmb_brick_reinforced);
		registerMicroBlockCompat(ModBlocks.vinyl_tile);

		registerMicroBlockCompat(ModBlocks.tile_lab);
		registerMicroBlockCompat(ModBlocks.tile_lab_cracked);
		registerMicroBlockCompat(ModBlocks.tile_lab_broken);
	}

	private static void registerMicroBlockCompat(Block block) {
		for(int meta = 0; meta < 15; meta++) {
			registerMicroBlockCompat(block, meta);
		}
	}

	private static void registerMicroBlockCompat(Block block, int meta) {
		FMLInterModComms.sendMessage("ForgeMicroblock", "microMaterial", new ItemStack(block, 1, meta));
	}
}

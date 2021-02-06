package com.hbm.world.dungeon;

import java.util.Arrays;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.RecipesCommon.MetaBlock;
import com.hbm.world.generator.DungeonToolbox;

import net.minecraft.world.World;

public class AncientTomb {

	
	public void build(World world, int x, int y, int z) {

		List<MetaBlock> concrete = Arrays.asList(new MetaBlock[] {
				new MetaBlock(ModBlocks.brick_concrete),
				new MetaBlock(ModBlocks.brick_concrete_broken),
				new MetaBlock(ModBlocks.brick_concrete_cracked)});
		
		int size = 5;
		int cladding = size - 1;
		int core = size -2;

		int dimOuter = size * 2 + 1;
		int dimInner = cladding * 2 + 1;
		int dimCore = core * 2 + 1;

		DungeonToolbox.generateBox(world, x - size, y - size, z - size, 1, dimOuter, dimOuter, concrete);
		DungeonToolbox.generateBox(world, x - size, y - size, z - size, dimOuter, 1, dimOuter, concrete);
		DungeonToolbox.generateBox(world, x - size, y - size, z - size, dimOuter, dimOuter, 1, concrete);
		DungeonToolbox.generateBox(world, x + size, y - size, z - size, 1, dimOuter, dimOuter, concrete);
		DungeonToolbox.generateBox(world, x - size, y + size, z - size, dimOuter, 1, dimOuter, concrete);
		DungeonToolbox.generateBox(world, x - size, y - size, z + size, dimOuter, dimOuter, 1, concrete);
		
		DungeonToolbox.generateBox(world, x - cladding, y - cladding, z - cladding, 1, dimInner, dimInner, ModBlocks.brick_obsidian);
		DungeonToolbox.generateBox(world, x - cladding, y - cladding, z - cladding, dimInner, 1, dimInner, ModBlocks.brick_obsidian);
		DungeonToolbox.generateBox(world, x - cladding, y - cladding, z - cladding, dimInner, dimInner, 1, ModBlocks.brick_obsidian);
		DungeonToolbox.generateBox(world, x + cladding, y - cladding, z - cladding, 1, dimInner, dimInner, ModBlocks.brick_obsidian);
		DungeonToolbox.generateBox(world, x - cladding, y + cladding, z - cladding, dimInner, 1, dimInner, ModBlocks.brick_obsidian);
		DungeonToolbox.generateBox(world, x - cladding, y - cladding, z + cladding, dimInner, dimInner, 1, ModBlocks.brick_obsidian);

		DungeonToolbox.generateBox(world, x - core, y - core, z - core, dimCore, dimCore, dimCore, ModBlocks.ancient_scrap);
	}
}

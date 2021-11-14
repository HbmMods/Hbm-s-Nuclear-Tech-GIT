package com.hbm.world.dungeon;

import java.util.Arrays;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockBobble.BobbleType;
import com.hbm.blocks.generic.BlockBobble.TileEntityBobble;
import com.hbm.config.GeneralConfig;
import com.hbm.inventory.RecipesCommon.MetaBlock;
import com.hbm.main.MainRegistry;
import com.hbm.world.generator.DungeonToolbox;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class ArcticVault {
	
	public void trySpawn(World world, int x, int y, int z) {
		
		y--;

		BiomeGenBase biome = world.getWorldChunkManager().getBiomeGenAt(x, z);
		
		if(biome.getFloatTemperature(x, y, z) < 0.2 && world.getBlock(x, y, z).getMaterial() == Material.rock) {
			build(world, x, y, z);
		}
		
	}
	
	private void build(World world, int x, int y, int z) {

		List<MetaBlock> brick = Arrays.asList(new MetaBlock[] {new MetaBlock(Blocks.stonebrick), new MetaBlock(Blocks.stonebrick, 2)});
		List<MetaBlock> web = Arrays.asList(new MetaBlock[] {new MetaBlock(Blocks.air), new MetaBlock(Blocks.air), new MetaBlock(Blocks.web)});
		List<MetaBlock> crates = Arrays.asList(new MetaBlock[] {new MetaBlock(ModBlocks.crate), new MetaBlock(ModBlocks.crate_metal), new MetaBlock(ModBlocks.crate_ammo), new MetaBlock(ModBlocks.crate_can), new MetaBlock(ModBlocks.crate_jungle)});

		DungeonToolbox.generateBox(world, x - 5, y, z - 5, 11, 1, 11, brick);
		DungeonToolbox.generateBox(world, x - 5, y + 6, z - 5, 11, 1, 11, brick);
		DungeonToolbox.generateBox(world, x - 5, y + 1, z - 5, 11, 5, 1, brick);
		DungeonToolbox.generateBox(world, x - 5, y + 1, z + 5, 11, 5, 1, brick);
		DungeonToolbox.generateBox(world, x - 5, y + 1, z - 5, 1, 5, 11, brick);
		DungeonToolbox.generateBox(world, x + 5, y + 1, z - 5, 1, 5, 11, brick);
		DungeonToolbox.generateBox(world, x - 4, y + 1, z - 4, 9, 3, 9, Blocks.air);
		DungeonToolbox.generateBox(world, x - 4, y + 1, z - 4, 9, 1, 9, new MetaBlock(Blocks.snow_layer));
		DungeonToolbox.generateBox(world, x - 2, y + 1, z - 2, 5, 2, 1, new MetaBlock(ModBlocks.tape_recorder, 3));
		DungeonToolbox.generateBox(world, x - 2, y + 3, z - 2, 5, 1, 1, new MetaBlock(Blocks.snow_layer));
		DungeonToolbox.generateBox(world, x - 2, y + 1, z + 2, 5, 2, 1, new MetaBlock(ModBlocks.tape_recorder, 2));
		DungeonToolbox.generateBox(world, x - 2, y + 3, z + 2, 5, 1, 1, new MetaBlock(Blocks.snow_layer));
		DungeonToolbox.generateBox(world, x - 4, y + 4, z - 4, 9, 2, 9, web);
		
		for(int i = 0; i < 15; i++) {
			int ix = x - 4 + world.rand.nextInt(10);
			int iz = z - 4 + world.rand.nextInt(10);
			
			if(world.getBlock(ix, y + 1, iz) == Blocks.snow_layer) {
				
				if(i == 0) {
					world.setBlock(ix, y + 1, iz, ModBlocks.bobblehead, world.rand.nextInt(16), 3);
					TileEntityBobble bobble = (TileEntityBobble) world.getTileEntity(ix, y + 1, iz);
					
					if(bobble != null) {
						bobble.type = BobbleType.values()[world.rand.nextInt(BobbleType.values().length - 1) + 1];
						bobble.markDirty();
					}
					
				} else {
					MetaBlock b = DungeonToolbox.getRandom(crates, world.rand);
					world.setBlock(ix, y + 1, iz, b.block, b.meta, 2);
					world.setBlock(ix, y + 2, iz, Blocks.snow_layer);
				}
			}
		}
		
		int iy = world.getHeightValue(x, z);

		if(world.getBlock(x, iy - 1, z).canPlaceTorchOnTop(world, x, iy - 1, z)) {
			world.setBlock(x, iy, z, ModBlocks.tape_recorder);
		}
		
		if(GeneralConfig.enableDebugMode)
			MainRegistry.logger.info("[Debug] Successfully spawned arctic code vault at " + x + " " + y + " " + z);
	}
}

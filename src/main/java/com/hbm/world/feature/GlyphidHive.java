package com.hbm.world.feature;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.util.LootGenerator;

import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.world.World;

public class GlyphidHive {

	public static final int[][][] schematicSmall = new int[][][] {
		{
			{0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,1,1,1,0,0,0,0},
			{0,0,0,0,1,1,1,0,0,0,0},
			{0,0,0,0,1,1,1,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0},
		},
		{
			{0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,1,1,1,0,0,0,0},
			{0,0,0,1,1,1,1,1,0,0,0},
			{0,0,1,1,1,1,1,1,1,0,0},
			{0,0,1,1,1,1,1,1,1,0,0},
			{0,0,1,1,1,1,1,1,1,0,0},
			{0,0,0,1,1,1,1,1,0,0,0},
			{0,0,0,0,1,1,1,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0},
		},
		{
			{0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,1,1,1,0,0,0,0},
			{0,0,1,1,1,1,1,1,1,0,0},
			{0,0,1,1,1,1,1,1,1,0,0},
			{0,1,1,1,3,3,3,1,1,1,0},
			{0,1,1,1,3,3,3,1,1,1,0},
			{0,1,1,1,3,3,3,1,1,1,0},
			{0,0,1,1,1,1,1,1,1,0,0},
			{0,0,1,1,1,1,1,1,1,0,0},
			{0,0,0,0,1,1,1,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0},
		},
		{
			{0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,1,1,1,0,0,0,0},
			{0,0,1,1,1,1,1,1,1,0,0},
			{0,0,1,1,2,2,2,1,1,0,0},
			{0,1,1,2,2,2,2,2,1,1,0},
			{0,1,1,2,2,2,2,2,1,1,0},
			{0,1,1,2,2,2,2,2,1,1,0},
			{0,0,1,1,2,2,2,1,1,0,0},
			{0,0,1,1,1,1,1,1,1,0,0},
			{0,0,0,0,1,1,1,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0},
		},
		{
			{0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,1,1,1,0,0,0,0},
			{0,0,1,1,1,1,1,1,1,0,0},
			{0,0,1,1,1,1,1,1,1,0,0},
			{0,1,1,1,1,1,1,1,1,1,0},
			{0,1,1,1,1,1,1,1,1,1,0},
			{0,1,1,1,1,1,1,1,1,1,0},
			{0,0,1,1,1,1,1,1,1,0,0},
			{0,0,1,1,1,1,1,1,1,0,0},
			{0,0,0,0,1,1,1,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0},
		}
	};
	public static void generateSmall(World world, int x, int y, int z, Random rand, boolean infected, boolean loot) {
		int overrideMeta = infected ? 1 : 0;
		
		for(int i = 0; i < 11; i++) {
			for(int j = 0; j < 5; j++) {
				for(int k = 0; k < 11; k++) {
					
					int block = schematicSmall[4 - j][i][k];
					int iX = x + i - 5;
					int iY = y + j - 2;
					int iZ = z + k - 5;
					
					switch(block) {
					case 1: world.setBlock(iX, iY, iZ, ModBlocks.glyphid_base, overrideMeta, 2); break;
					case 2: world.setBlock(iX, iY, iZ, rand.nextInt(3) == 0 ? ModBlocks.glyphid_spawner : ModBlocks.glyphid_base, overrideMeta, 2); break;
					case 3:
						int r = rand.nextInt(3);
						if(r == 0) {
							world.setBlock(iX, iY, iZ, Blocks.skull, 1, 3);
							TileEntitySkull skull = (TileEntitySkull) world.getTileEntity(iX, iY, iZ);
							if(skull != null) skull.func_145903_a(rand.nextInt(16));
						} else if(r == 1) {
							world.setBlock(iX, iY, z + k - 5, ModBlocks.deco_loot, 0, 2);
							LootGenerator.lootBones(world, iX, iY, iZ);
						} else if(r == 2) {
							if(loot) {
								world.setBlock(iX, iY, iZ, ModBlocks.deco_loot, 0, 2);
								LootGenerator.lootGlyphidHive(world, iX, iY, iZ);
							} else {
								world.setBlock(iX, iY, iZ, ModBlocks.glyphid_base, overrideMeta, 2);
							}
						}
						break;
					}
				}
			}
		}
	}
}

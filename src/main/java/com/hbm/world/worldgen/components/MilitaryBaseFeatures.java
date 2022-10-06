package com.hbm.world.worldgen.components;

import java.util.LinkedList;
import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;

//These structures are... kind of? low quality, but they test out new methods so whatev.
public class MilitaryBaseFeatures {
	
	//stop-gap methods until this entire mess can be organized into proper classes/structure groups
	public static void smallHelipad(LinkedList components, int chunkX, int posY, int chunkZ, Random rand) {
		BasicHelipad helipad = new BasicHelipad(rand, chunkX * 16 + 8, posY, chunkZ * 16 + 8);
		int[] chunkPos = getAdjacentChunk(chunkX, chunkZ, rand);
		
		RadioShack radio = new RadioShack(rand, chunkPos[0] * 16 + 8, posY, chunkPos[1] * 16 + 8);
		components.add(helipad);
		components.add(radio);
	}
	
	//ugh
	public static int[] getAdjacentChunk(int chunkX, int chunkZ, Random rand) {
		int[] chunkPos = new int[2];
		
		switch(rand.nextInt(4)) {
		case 0:
			chunkPos[0] = chunkX;
			chunkPos[1] = chunkZ + 1;
			break;
		case 1:
			chunkPos[0] = chunkX - 1;
			chunkPos[1] = chunkZ;
			break;
		case 2:
			chunkPos[0] = chunkX;
			chunkPos[1] = chunkZ - 1;
			break;
		case 3:
			chunkPos[0] = chunkX + 1;
			chunkPos[1] = chunkZ;
			break;
		}
		
		return chunkPos;
	}

	public static class BasicHelipad extends Feature {
		
		public BasicHelipad() { super(); }
		
		protected BasicHelipad(Random rand, int minX, int minY, int minZ) {
			super(rand, minX, minY, minZ, 12, 0, 12);
		}
		
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			if(!this.setAverageHeight(world, box, this.boundingBox.minY)) {
				return true;
			}
			
			this.boundingBox.offset(0, -1, 0);
			
			for(int i = 1; i < sizeX; i++) {
				for(int j = 1; j < sizeZ; j++) {
					clearCurrentPositionBlocksUpwards(world, i, 1, j, box);
				}
			}
			
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 1, 1, sizeX - 1, sizeZ - 1, -1, box);
			
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 0, 0, sizeX, 0, -1, box);
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 0, 1, 0, sizeZ, -1, box);
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, sizeX, 1, sizeX, sizeZ, -1, box);
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 1, sizeZ, sizeX - 1, sizeZ, -1, box);
			
			//Helipad
			fillWithBlocks(world, box, 1, 0, 1, 11, 0, 1, ModBlocks.concrete, Blocks.air, false); //this entire time, the second block was actually for anything not at min/max x's, y's, and z's. useful!
			fillWithBlocks(world, box, 11, 0, 2, 11, 0, 11, ModBlocks.concrete, Blocks.air, false);
			fillWithBlocks(world, box, 1, 0, 11, 10, 0, 11, ModBlocks.concrete, Blocks.air, false);
			fillWithBlocks(world, box, 1, 0, 2, 1, 0, 10, ModBlocks.concrete, Blocks.air, false);
			
			fillWithBlocks(world, box, 2, 0, 2, 10, 0, 10, ModBlocks.concrete_smooth, Blocks.air, false); //i'm not carefully carving out the white H lmao fuck that
			fillWithBlocks(world, box, 4, 0, 4, 4, 0, 8, ModBlocks.concrete_colored, Blocks.air, false); //white is 0
			fillWithBlocks(world, box, 8, 0, 4, 8, 0, 8, ModBlocks.concrete_colored, Blocks.air, false);
			fillWithBlocks(world, box, 5, 0, 6, 7, 0, 6, ModBlocks.concrete_colored, Blocks.air, false);
			
			//Surrounding Fences
			placeBlocksOnTop(world, box, ModBlocks.fence_metal, 0, 0, sizeX, 0, 1);
			placeBlocksOnTop(world, box, ModBlocks.fence_metal, sizeX, 1, sizeX, sizeZ, 1);
			placeBlocksOnTop(world, box, ModBlocks.fence_metal, 0, sizeZ, sizeX - 1, sizeZ, 1);
			placeBlocksOnTop(world, box, ModBlocks.fence_metal, 0, 1, 0, sizeZ - 1, 1);
			
			return false;
		}
		
	}
	
	public static class RadioShack extends Feature {
		
		private static LabTiles RandomLabTiles = new LabTiles();
		private static ConcreteBricks ConcreteBricks = new ConcreteBricks();
		
		public RadioShack() { super(); }
		
		protected RadioShack(Random rand, int minX, int minY, int minZ) {
			super(rand, minX, minY, minZ, 6, 4, 5);
		}

		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			if(!this.setAverageHeight(world, box, this.boundingBox.minY)) {
				return true;
			}
			
			this.boundingBox.offset(0, -1, 0);
			
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 1, 1, sizeX, sizeZ, -1, box);
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 0, 2, 0, 2, 0, box);
			
			//Floor & Foundation
			fillWithRandomizedBlocks(world, box, 2, 0, 1, 5, 0, 4, false, rand, RandomLabTiles);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_pillar, 0, 1, 0, 1, box);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_pillar, 0, sizeX, 0, 1, box);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_pillar, 0, 1, 0, sizeZ, box);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_pillar, 0, sizeX, 0, sizeZ, box);
			fillWithBlocks(world, box, 2, 0, 1, sizeX - 1, 0, 1, ModBlocks.concrete_smooth, Blocks.air, false);
			fillWithBlocks(world, box, 2, 0, 0, sizeX - 1, 0, 0, ModBlocks.concrete_smooth, Blocks.air, false);
			fillWithBlocks(world, box, sizeX, 0, 2, sizeX, 0, sizeZ - 1, ModBlocks.concrete_smooth, Blocks.air, false);
			fillWithBlocks(world, box, 2, 0, sizeZ, sizeX - 1, 0, sizeZ, ModBlocks.concrete_smooth, Blocks.air, false);
			fillWithBlocks(world, box, 1, 0, 2, 1, 0, sizeZ - 1, ModBlocks.concrete_smooth, Blocks.air, false);
			
			//Back Wall
			fillWithRandomizedBlocks(world, box, 1, 1, 1, 2, sizeY - 1, 1, false, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 2, 1, 0, 5, sizeY - 1, 0, false, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 5, 1, 1, sizeX, sizeY - 1, 1, false, rand, ConcreteBricks);
			//Front Wall
			fillWithRandomizedBlocks(world, box, 1, 1, sizeZ, 2, sizeY - 1, sizeZ, false, rand, ConcreteBricks);
			placeBlockAtCurrentPosition(world, ModBlocks.brick_concrete, 0, 3, sizeY - 1, sizeZ, box);
			fillWithRandomizedBlocks(world, box, 4, 1, sizeZ, sizeX, sizeY - 1, sizeZ, false, rand, ConcreteBricks);
			placeDoor(world, box, ModBlocks.door_metal, 3, 3, 1, sizeZ);
			//Left & Right Wall
			fillWithRandomizedBlocks(world, box, 1, 1, 2, 1, sizeY - 1, sizeZ - 1, false, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, sizeX, 1, 2, sizeX, sizeY - 1, sizeZ - 1, false, rand, ConcreteBricks);
			placeBlockAtCurrentPosition(world, ModBlocks.reinforced_glass, 0, 1, 2, 3, box);
			placeBlockAtCurrentPosition(world, ModBlocks.reinforced_glass, 0, sizeX, 2, 2, box);
			placeBlockAtCurrentPosition(world, ModBlocks.reinforced_glass, 0, sizeX, 2, 4, box);
			//Ceiling
			fillWithBlocks(world, box, 3, sizeY - 1, 1, 4, sizeY - 1, 1, ModBlocks.concrete_smooth, Blocks.air, false);
			fillWithBlocks(world, box, 2, sizeY - 1, 2, sizeX - 1, sizeY - 1, sizeZ - 1, ModBlocks.concrete_smooth, Blocks.air, false);
			
			fillWithAir(world, box, 2, 1, 2, sizeX - 1, 2, sizeZ - 1);
			
			//Decoration
			int southMeta = getDecoMeta(2);
			int northMeta = getDecoMeta(3); //all of these deco blocks are so inconsistent about what their directions actually are
			int eastMeta = getDecoMeta(4);
			fillWithMetadataBlocks(world, box, 2, 1, 2, 5, 1, 2, ModBlocks.steel_grate, 7, null, 0, false); //null should be okay here
			fillWithBlocks(world, box, 3, 1, 1, 4, 1, 1, ModBlocks.deco_tungsten, null, false);
			fillWithMetadataBlocks(world, box, 3, 2, 1, 4, 2, 1, ModBlocks.tape_recorder, northMeta, null, 0, false);
			placeBlockAtCurrentPosition(world, ModBlocks.radiorec, southMeta, 2, 2, 2, box);
			placeRandomBobble(world, box, rand, sizeX - 1, 2, 2);
			fillWithMetadataBlocks(world, box, sizeX - 1, 1, 3, sizeX - 1, 2, 3, ModBlocks.tape_recorder, eastMeta, null, 0, false);
			//OutsideDeco
			fillWithMetadataBlocks(world, box, 0, 1, 2, 0, 2, 2, ModBlocks.steel_poles, eastMeta, null, 0, false);
			placeBlockAtCurrentPosition(world, ModBlocks.pole_satellite_receiver, eastMeta, 0, sizeY - 1, 2, box);
			fillWithBlocks(world, box, 0, sizeY, 2, sizeX - 1, sizeY, 2, ModBlocks.steel_roof, null, false);
			placeBlockAtCurrentPosition(world, ModBlocks.steel_roof, 0, sizeX - 1, sizeY, 3, box);
			
			return false;
		}
		
	}
	
	
}

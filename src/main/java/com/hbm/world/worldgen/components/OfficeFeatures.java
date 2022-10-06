package com.hbm.world.worldgen.components;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.HbmChestContents;
import com.hbm.world.worldgen.components.Feature.ConcreteBricks;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;

//Oh my fucking god TM
public class OfficeFeatures {
	
	public static class LargeOffice extends Feature {
		
		private static ConcreteBricks ConcreteBricks = new ConcreteBricks();
		
		private boolean[] hasPlacedLoot = new boolean[2];
		
		public LargeOffice() {
			super();
		}
		
		public LargeOffice(Random rand, int minX, int minY, int minZ) {
			super(rand, minX, minY, minZ, 14, 5, 12);
			this.hasPlacedLoot[0] = false;
			this.hasPlacedLoot[1] = false;
		}
		
		@Override
		protected void func_143012_a(NBTTagCompound nbt) {
			super.func_143012_a(nbt);
			nbt.setBoolean("hasLoot1", this.hasPlacedLoot[0]);
			nbt.setBoolean("hasLoot2", this.hasPlacedLoot[1]);
		}
		
		@Override
		protected void func_143011_b(NBTTagCompound nbt) {
			super.func_143011_b(nbt);
			this.hasPlacedLoot[0] = nbt.getBoolean("hasLoot1");
			this.hasPlacedLoot[1] = nbt.getBoolean("hasLoot2");
		}
		
		//Holy shit I despise this method so goddamn much
		//TODO BOB: please i beg you make some sort of utility tool to simplify this
		//ideally we'd invent something like the structure blocks or even a more advanced
		//schematic to java tool
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			if(!this.setAverageHeight(world, box, this.boundingBox.minY)) {
				return true;
			}
			
			this.boundingBox.offset(0, -1, 0);
			
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 5, 0, sizeX, 1, -1, box);
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 0, 2, sizeX, 7, -1, box);
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 0, 8, 8, sizeZ, 0, box);
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 9, 8, sizeX, sizeZ, -1, box);
			
			fillWithAir(world, box, 1, 1, 3, 4, 3, 6);
			fillWithAir(world, box, 6, 1, 1, sizeX - 1, 3, 6);
			fillWithAir(world, box, 10, 1, 7, sizeX - 1, 3, sizeZ - 1);
			
			//Pillars
			//Back
			fillWithBlocks(world, box, 0, 0, 2, 0, 4, 2, ModBlocks.concrete_pillar);
			fillWithBlocks(world, box, 5, 0, 0, 5, 4, 0, ModBlocks.concrete_pillar);
			fillWithBlocks(world, box, sizeX, 0, 0, sizeX, 4, 0, ModBlocks.concrete_pillar);
			//Front
			fillWithBlocks(world, box, 0, 0, 7, 0, 3, 7, ModBlocks.concrete_pillar);
			fillWithBlocks(world, box, 0, 0, sizeZ, 0, 3, sizeZ, ModBlocks.concrete_pillar);
			fillWithBlocks(world, box, 3, 0, sizeZ, 3, 3, sizeZ, ModBlocks.concrete_pillar);
			fillWithBlocks(world, box, 6, 0, sizeZ, 6, 3, sizeZ, ModBlocks.concrete_pillar);
			fillWithBlocks(world, box, 9, 0, sizeZ, 9, 3, sizeZ, ModBlocks.concrete_pillar);
			fillWithBlocks(world, box, 9, 0, 7, 9, 3, 7, ModBlocks.concrete_pillar);
			fillWithBlocks(world, box, sizeX, 0, sizeZ, sizeX, 4, sizeZ, ModBlocks.concrete_pillar);
			
			//Walls
			//Back
			fillWithRandomizedBlocks(world, box, 1, 0, 2, 5, 4, 2, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 5, 0, 1, 5, 4, 1, rand, ConcreteBricks);
			
			fillWithRandomizedBlocks(world, box, 6, 0, 0, sizeX - 1, 1, 0, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 6, 2, 0, 6, 2, 0, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 9, 2, 0, 10, 2, 0, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, sizeX - 1, 2, 0, sizeX - 1, 2, 0, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 6, 3, 0, sizeX - 1, 4, 0, rand, ConcreteBricks);
			//Right
			fillWithRandomizedBlocks(world, box, sizeX, 0, 1, sizeX, 1, sizeZ - 1, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, sizeX, 2, 1, sizeX, 2, 2, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, sizeX, 2, 5, sizeX, 2, 7, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, sizeX, 2, sizeZ - 2, sizeX, 2, sizeZ - 1, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, sizeX, 3, 1, sizeX, 4, sizeZ - 1, rand, ConcreteBricks);
			//Front
			fillWithRandomizedBlocks(world, box, 0, 4, sizeZ, sizeX - 1, 4, sizeZ, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 10, 0, sizeZ, sizeX - 1, 1, sizeZ, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 10, 2, sizeZ, 10, 2, sizeZ, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, sizeX - 1, 2, sizeZ, sizeX - 1, 2, sizeZ, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 10, 3, sizeZ, sizeX - 1, 3, sizeZ, rand, ConcreteBricks);
			
			fillWithRandomizedBlocks(world, box, 9, 0, 8, 9, 3, sizeZ - 1, rand, ConcreteBricks);
			
			fillWithRandomizedBlocks(world, box, 1, 0, 7, 8, 0, 7, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 1, 1, 7, 1, 2, 7, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 4, 1, 7, 8, 3, 7, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 1, 3, 7, 3, 3, 7, rand, ConcreteBricks);
			//Left
			fillWithRandomizedBlocks(world, box, 0, 4, 3, 0, 4, sizeZ - 1, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 0, 0, 3, 0, 1, 6, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 0, 2, 3, 0, 3, 3, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 0, 2, 6, 0, 3, 6, rand, ConcreteBricks);
			//Interior
			fillWithRandomizedBlocks(world, box, 5, 1, 3, 5, 3, 5, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 5, 3, 6, 5, 3, 6, rand, ConcreteBricks);
			
			//Trim
			randomlyFillWithBlocks(world, box, rand, 0.85F, 0, sizeY, 2, 5, sizeY, 2, Blocks.stone_slab);
			randomlyFillWithBlocks(world, box, rand, 0.85F, 5, sizeY, 1, 5, sizeY, 1, Blocks.stone_slab);
			randomlyFillWithBlocks(world, box, rand, 0.85F, 5, sizeY, 0, sizeX, sizeY, 0, Blocks.stone_slab);
			randomlyFillWithBlocks(world, box, rand, 0.85F, sizeX, sizeY, 1, sizeX, sizeY, sizeZ, Blocks.stone_slab);
			randomlyFillWithBlocks(world, box, rand, 0.85F, 0, sizeY, sizeZ, sizeX - 1, sizeY, sizeZ, Blocks.stone_slab);
			randomlyFillWithBlocks(world, box, rand, 0.85F, 0, sizeY, 3, 0, sizeY, sizeZ - 1, Blocks.stone_slab);
			
			//Floor
			fillWithMetadataBlocks(world, box, 1, 0, 3, 4, 0, 6, Blocks.wool, 13); //Green Wool
			fillWithBlocks(world, box, 5, 0, 3, 5, 0, 6, ModBlocks.brick_light);
			fillWithBlocks(world, box, 6, 0, 1, sizeX - 1, 0, 6, ModBlocks.brick_light);
			fillWithBlocks(world, box, 10, 0, 7, sizeX - 1, 0, sizeZ - 1, ModBlocks.brick_light);
			//Ceiling
			fillWithBlocks(world, box, 6, 4, 1, sizeX - 1, 4, 2, ModBlocks.brick_light);
			fillWithBlocks(world, box, 1, 4, 3, sizeX - 1, 4, sizeZ - 1, ModBlocks.brick_light);
			
			//Decorations
			//Carpet
			fillWithMetadataBlocks(world, box, 9, 1, 3, 11, 1, 6, Blocks.carpet, 8); //Light gray
			//Windows
			randomlyFillWithBlocks(world, box, rand, 0.75F, 0, 2, 4, 0, 3, 5, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, 0.75F, 7, 2, 0, 8, 2, 0, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, 0.75F, sizeX - 3, 2, 0, sizeX - 2, 2, 0, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, 0.75F, sizeX, 2, 3, sizeX, 2, 4, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, 0.75F, sizeX, 2, 8, sizeX, 2, 9, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, 0.75F, sizeX - 3, 2, sizeZ, sizeX - 2, 2, sizeZ, Blocks.glass_pane);
			//Fuwnituwe >w<
			int stairMetaE = getStairMeta(1); //East
			int stairMetaN = getStairMeta(2); //*SHOULD* be north
			int stairMetaS = getStairMeta(3); //South :3
			int stairMetaWU = getStairMeta(0) | 4; //West, Upside-down
			int stairMetaEU = stairMetaE | 4; //East, Upside-down
			int stairMetaNU = stairMetaN | 4; //North, Upside-down uwu
			int stairMetaSU = stairMetaS | 4; //South, Upside-down 
			//Desk 1 :3
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairMetaEU, 1, 1, 4, box);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairMetaNU, 2, 1, 4, box);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairMetaWU, 3, 1, 4, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairMetaS, 2, 1, 3, box); //Chaiw :3
			placeBlockAtCurrentPosition(world, ModBlocks.deco_computer, getDecoModelMeta(0), 1, 2, 4, box); //Nowth-facing Computer :33
			//Desk 2 :3
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairMetaS, 7, 1, 3, box); //Chaiw :3
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairMetaEU, 6, 1, 4, box);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairMetaWU, 7, 1, 4, box);
			placeBlockAtCurrentPosition(world, Blocks.planks, 1, 8, 1, 4, box); //Spwuce Pwanks :3
			placeBlockAtCurrentPosition(world, ModBlocks.deco_computer, getDecoModelMeta(0), 7, 2, 4, box); //Nowth-facing Computer X3
			placeBlockAtCurrentPosition(world, Blocks.flower_pot, 0, 8, 2, 4, box);
			//Desk 3 :3
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairMetaEU, 10, 1, 1, box);
			fillWithMetadataBlocks(world, box, 11, 1, 1, sizeX - 1, 1, 1, Blocks.spruce_stairs, stairMetaSU);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairMetaNU, sizeX - 1, 1, 2, box);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairMetaSU, sizeX - 1, 1, 3, box);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairMetaWU, sizeX - 1, 1, 4, box);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairMetaNU, sizeX - 1, 1, 5, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairMetaN, 11, 1, 2, box); //Chaiw ;3
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairMetaE, sizeX - 2, 1, 4, box); //Chaiw :333
			placeBlockAtCurrentPosition(world, ModBlocks.deco_computer, getDecoModelMeta(1), sizeX - 3, 2, 1, box); //South-facing Computer :3
			placeBlockAtCurrentPosition(world, ModBlocks.deco_computer, getDecoModelMeta(2), sizeX - 1, 2, 5, box); //West-facing Computer ^w^
			placeBlockAtCurrentPosition(world, Blocks.flower_pot, 0, sizeX - 1, 2, 3, box);
			placeBlockAtCurrentPosition(world, ModBlocks.radiorec, getDecoMeta(5), sizeX - 1, 2, 2, box); //Wadio
			//Desk 4 DX
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairMetaEU, 10, 1, 8, box);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairMetaWU, 11, 1, 8, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairMetaN, 10, 1, 9, box); //Chaiw ;3
			placeBlockAtCurrentPosition(world, ModBlocks.deco_computer, getDecoModelMeta(1), 10, 2, 8, box); //South-facing Computer :33
			//Desk 5 :333
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairMetaSU, sizeX - 1, 1, sizeZ - 3, box);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairMetaWU, sizeX - 1, 1, sizeZ - 2, box);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairMetaNU, sizeX - 1, 1, sizeZ - 1, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairMetaE, sizeX - 3, 1, sizeZ - 1, box); //UwU... Chaiw!!!! :333 I wove chaiws XD :333 OwO what's this?? chaiw???? :333333333333333333
			placeBlockAtCurrentPosition(world, ModBlocks.deco_computer, getDecoModelMeta(2), sizeX - 1, 2, sizeZ - 1, box); //West-facing Computer >w<
			//Cobwebs pwobabwy
			//Maybe make a method for this eventually?
			//Something where the tops of ceilings + empty corners along walls get most cobwebs,
			//with no cobwebs hanging midair + it not being performance intensive
			randomlyFillWithBlocks(world, box, rand, 0.25F, 1, 3, 3, 4, 3, 6, Blocks.web);
			randomlyFillWithBlocks(world, box, rand, 0.25F, 6, 3, 1, sizeX - 1, 3, 6, Blocks.web);
			randomlyFillWithBlocks(world, box, rand, 0.25F, 10, 3, 7, sizeX - 1, 3, sizeZ - 1, Blocks.web);
			//Doors
			placeDoor(world, box, ModBlocks.door_office, 3, 2, 1, 7);
			placeDoor(world, box, ModBlocks.door_office, 3, 3, 1, 7);
			placeDoor(world, box, ModBlocks.door_office, 0, 5, 1, 6);
			
			//Woot
			if(!this.hasPlacedLoot[0])
				this.hasPlacedLoot[0] = generateInvContents(world, box, rand, Blocks.chest, sizeX - 4, 1, sizeZ - 1, HbmChestContents.officeTrash, 10);
			if(!this.hasPlacedLoot[1]) {
				this.hasPlacedLoot[1] = generateLockableContents(world, box, rand, ModBlocks.safe, 6, 1, 1, HbmChestContents.machineParts, 10, 0.5D);
				generateLoreBook(world, box, 6, 1, 1, 7, "office" + rand.nextInt(1));
			}
			
			//TODO: add book with funny lore to safe, add cobwebs too
			//0b00/0 West, 0b01/1 East, 0b10/2 North, 0b11/3 South, 0b100/4 West UD, 0b101 East UD, 0b110 North UD, 0b111 South UD
			return false;
		}
		
	}
	
}

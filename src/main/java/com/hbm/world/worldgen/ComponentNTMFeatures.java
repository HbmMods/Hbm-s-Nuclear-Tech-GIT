package com.hbm.world.worldgen;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.HbmChestContents;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

//Probably one of the more difficult parts.
/** Base component file. For structure generation under 32x32 blocks, as Minecraft generates 2x2 chunks for structures.
 * Larger non-procedural structures should be split up into several bounding boxes, which check if they intersect the chunk bounding box currently being loaded. Doing so will prevent
 * cascading world generation. See 
 * <a href="https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/modification-development/2895477-better-structure-generation">
 * TheMasterCaver's advice.</a> */
public class ComponentNTMFeatures {
	
	/** Register structures in MapGenStructureIO */
	public static void registerNTMFeatures() {
		MapGenStructureIO.func_143031_a(ComponentNTMFeatures.NTMHouse1.class, "NTMHouse1");
		MapGenStructureIO.func_143031_a(ComponentNTMFeatures.NTMLab1.class, "NTMLab1");
	}
	
	/** Sandstone Ruin 1 */
	public static class NTMHouse1 extends ComponentNTMFeatures.Feature {
		
		private boolean hasPlacedChest = false;
		
		private static ComponentNTMFeatures.Sandstone RandomSandstone = new ComponentNTMFeatures.Sandstone();
		
		/** Constructor for this feature; takes coordinates for bounding box */
		protected NTMHouse1(Random rand, int minX, int minY, int minZ) {
			super(rand, minX, minY, minZ, 9, 4, 6);
		}
		
		@Override
		protected void func_143012_a(NBTTagCompound nbt) {
			super.func_143012_a(nbt);
			nbt.setBoolean("hasChest", hasPlacedChest);
		}
		
		@Override
		protected void func_143011_b(NBTTagCompound nbt) {
			super.func_143011_b(nbt);
			this.hasPlacedChest = nbt.getBoolean("hasChest");
		}
		
		/**
		 * Generates structures.
		 */
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			/*
			 * Places block at current position. Dependent on coordinate mode, i.e. will allow for random rotation, so use this instead of setBlock!
			 * this.placeBlockAtCurrentPosition(world, block, minX, metadata, x, y, z, box);
			 * Fills an area with air, self-explanatory. Use to clear interiors of unwanted blocks.
			 * this.fillWithAir(world, box, minX, minY, minZ, maxX, maxY, maxZ);
			 * Fills an area with blocks, self-explanatory.
			 * this.fillWithBlocks(world, box, minX, minY, minZ, maxX, maxY, maxZ, blockToPlace, blockToReplace, alwaysReplace);
			 * Fills an area with metadata blocks, self-explanatory.
			 * this.fillWithMetadataBlocks(world, box, minX, minY, minZ, maxX, maxY, maxZ, blockToPlace, blockPlaceMeta, blockToReplace, replaceBlockMeta, alwaysReplace);
			 * Fills an area with randomized blocks, self-explanatory.
			 * this.fillWithRandomizedBlocks(world, box, minX, minY, minZ, maxX, maxY, maxZ, alwaysReplace, rand, StructureComponent.blockSelector);
			 * (BlockSelector is basically a list of blocks that can be randomly picked, except that it can actually be weighted)
			 * Replaces any air or water blocks with this block down. Useful for foundations
			 * this.func_151554_b(world, block, metadata, x, startAtY, z, box
			 * Fills an area with blocks randomly - look into randLimit?
			 * this.randomlyFillWithBlocks(world, box, rand, randLimit, minX, minY, minZ, maxX, maxY, maxZ, blockToPlace, blockToReplace, alwaysReplace);
			 */
			
			System.out.println("" + this.boundingBox.minX + ", " + this.boundingBox.minY + ", " + this.boundingBox.minZ);
			if(!this.func_74935_a(world, box, this.boundingBox.minY)) {
				return false;
			}
			//System.out.println("Hpos: " + this.hpos + "; minY:" + this.boundingBox.minY);
			
			for(byte i = 0; i < this.featureSizeX + 1; i++) {
				for(byte j = 0; j < this.featureSizeZ + 1; j++) {
					this.func_151554_b(world, Blocks.sandstone, 0, i, -1, j, box);
				}
			}
			
			//this.fillWithBlocks(world, box, 0, 0, 0, scatteredFeatureSizeX, 0, scatteredFeatureSizeZ, Blocks.sandstone, Blocks.air, false);
			
			System.out.print(this.coordBaseMode);
			//Walls
			this.fillWithRandomizedBlocks(world, box, 0, 0, 0, featureSizeX, 0, 0, false, rand, RandomSandstone); //Back Wall
			this.fillWithRandomizedBlocks(world, box, 0, 1, 0, 1, 1, 0, false, rand, RandomSandstone);
			this.placeBlockAtCurrentPosition(world, Blocks.fence, 0, 2, 1, 0, box);
			this.fillWithRandomizedBlocks(world, box, 3, 1, 0, 5, 1, 0, false, rand, RandomSandstone);
			this.placeBlockAtCurrentPosition(world, Blocks.fence, 0, 6, 1, 0, box);
			this.placeBlockAtCurrentPosition(world, Blocks.fence, 0, 7, 1, 0, box);
			this.fillWithRandomizedBlocks(world, box, featureSizeX - 1, 1, 0, featureSizeX, 1, 0, false, rand, RandomSandstone);
			this.fillWithRandomizedBlocks(world, box, 0, 2, 0, featureSizeX - 2, 2, 0, false, rand, RandomSandstone);
			this.fillWithRandomizedBlocks(world, box, 0, 0, 0, 0, 1, featureSizeZ, false, rand, RandomSandstone); //Left Wall
			this.placeBlockAtCurrentPosition(world, Blocks.stone_slab, 1, 0, 2, 1, box);
			this.fillWithMetadataBlocks(world, box, 0, 2, 3, 0, 2, featureSizeZ, Blocks.stone_slab, 1, Blocks.air, 0, false);
			this.fillWithRandomizedBlocks(world, box, 1, 0, featureSizeZ, 1, 1, featureSizeZ, false, rand, RandomSandstone); //Front Wall
			this.fillWithRandomizedBlocks(world, box, 3, 0, featureSizeZ, featureSizeX, 1, featureSizeZ, false, rand, RandomSandstone);
			this.fillWithRandomizedBlocks(world, box, 1, 2, featureSizeZ, 3, 2, featureSizeZ, false, rand, RandomSandstone);
			this.fillWithMetadataBlocks(world, box, 4, 2, featureSizeZ, 5, 2, featureSizeZ, Blocks.stone_slab, 1, Blocks.air, 0, false);
			this.placeBlockAtCurrentPosition(world, Blocks.stone_slab, 1, featureSizeX - 2, 2, featureSizeZ, box);
			this.fillWithRandomizedBlocks(world, box, featureSizeX, 0, 0, featureSizeX, 0, featureSizeZ, false, rand, RandomSandstone); //Right Wall
			this.randomlyFillWithBlocks(world, box, rand, 0.65F, featureSizeX, 1, 1, featureSizeX, 1, featureSizeZ - 1, Blocks.sand, Blocks.air, false);
			
			this.fillWithRandomizedBlocks(world, box, 4, 0, 1, 4, 1, 3, false, rand, RandomSandstone);
			this.placeBlockAtCurrentPosition(world, ModBlocks.reinforced_sand, 0, 4, 0, 4, box);
			
			//Loot/Sand
			this.placeBlockAtCurrentPosition(world, ModBlocks.crate_weapon, 0, 1, 0, 1, box);
			if(!this.hasPlacedChest)
				this.hasPlacedChest = this.generateStructureChestContents(world, box, rand, 3, 0, 1, HbmChestContents.getLoot(1), rand.nextInt(2) + 8); //Make sure to redo that class kek
			this.fillWithBlocks(world, box, 5, 0, 1, 6, 0, 1, ModBlocks.crate, Blocks.air, false);
			this.placeBlockAtCurrentPosition(world, Blocks.sand, 0, 7, 0, 1, box);
			if(rand.nextFloat() <= 0.1)
				 this.placeBlockAtCurrentPosition(world, ModBlocks.crate_metal, 0, featureSizeX - 1, 0, 1, box);
			this.randomlyFillWithBlocks(world, box, rand, 0.25F, 1, 0, 2, 3, 0, featureSizeZ - 1, Blocks.sand, Blocks.air, false);
			this.randomlyFillWithBlocks(world, box, rand, 0.25F, 5, 0, 2, featureSizeX - 1, 0, featureSizeZ - 1, Blocks.sand, Blocks.air, false);
			
			System.out.println("Successful spawn");
			
			return true;
		}
		
	}
	
	public static class NTMLab1 extends ComponentNTMFeatures.Feature {
		
		private static ComponentNTMFeatures.ConcreteBricks RandomConcreteBricks = new ComponentNTMFeatures.ConcreteBricks();
		private static ComponentNTMFeatures.LabTiles RandomLabTiles = new ComponentNTMFeatures.LabTiles();
		
		/** Constructor for this feature; takes coordinates for bounding box */
		protected NTMLab1(Random rand, int minX, int minY, int minZ) {
			super(rand, minX, minY, minZ, 9, 4, 7);
		}

		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			System.out.println("" + this.boundingBox.minX + ", " + this.boundingBox.minY + ", " + this.boundingBox.minZ);
			if(!this.func_74935_a(world, box, this.boundingBox.minY)) {
				return false;
			}
			
			for(byte i = 0; i < this.featureSizeX + 1; i++) {
				for(byte j = 0; j < this.featureSizeZ - 1; j++) {
					this.func_151554_b(world, Blocks.stonebrick, 0, i, -1, j, box);
				}
			}
			
			for(byte i = 3; i < this.featureSizeX + 1; i++) {
				for(byte j = 6; j < this.featureSizeZ + 1; j++) {
					this.func_151554_b(world, Blocks.stonebrick, 0, i, -1, j, box);
				}
			}
			
			int stairsMeta = this.getMetadataWithOffset(Blocks.stone_brick_stairs, 0);
			if(this.getBlockAtCurrentPosition(world, 2, 0, featureSizeZ - 1, box).getMaterial().isReplaceable() 
					|| this.getBlockAtCurrentPosition(world, 2, 0, featureSizeZ - 1, box) == Blocks.air) {
				this.func_151554_b(world, Blocks.stonebrick, 0, 2, -1, featureSizeZ - 1, box);
				this.placeBlockAtCurrentPosition(world, Blocks.stone_brick_stairs, stairsMeta, 2, 0, featureSizeZ - 1, box);
			}
			
			this.fillWithAir(world, box, 1, 0, 1, featureSizeX - 1, featureSizeY, 4);
			this.fillWithAir(world, box, 4, 0, 4, featureSizeX - 1, featureSizeY, featureSizeZ - 1);
			
			int northMeta = this.getMetadataForRotatable(8);
			
			//Pillars
			this.fillWithBlocks(world, box, 0, 0, 0, 0, 3, 0, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithBlocks(world, box, featureSizeX, 0, 0, featureSizeX, 3, 0, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithMetadataBlocks(world, box, 0, 0, 1, 0, 0, 4, ModBlocks.concrete_pillar, northMeta, Blocks.air, 0, false);
			this.fillWithMetadataBlocks(world, box, featureSizeX, 0, 1, featureSizeX, 0, featureSizeZ - 1, ModBlocks.concrete_pillar, northMeta, Blocks.air, 0, false);
			this.fillWithBlocks(world, box, 0, 0, featureSizeZ - 2, 0, 3, featureSizeZ - 2, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithBlocks(world, box, 3, 0, featureSizeZ - 2, 3, 3, featureSizeZ - 2, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithBlocks(world, box, 3, 0, featureSizeZ, 3, 3, featureSizeZ, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithBlocks(world, box, featureSizeX, 0, featureSizeZ, featureSizeX, 3, featureSizeZ, ModBlocks.concrete_pillar, Blocks.air, false);
			
			//Walls
			this.fillWithRandomizedBlocks(world, box, 1, 0, 0, featureSizeX - 1, featureSizeY - 1, 0, false, rand, RandomConcreteBricks); //Back Wall
			this.fillWithRandomizedBlocks(world, box, 0, featureSizeY, 0, featureSizeX, featureSizeY, 0, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 0, 1, 1, 0, featureSizeY - 1, 4, false, rand, RandomConcreteBricks); //Left Wall
			this.fillWithRandomizedBlocks(world, box, 0, featureSizeY, 0, 0, featureSizeY, featureSizeZ - 2, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 1, 0, featureSizeZ - 2, 2, featureSizeY, featureSizeZ - 2, false, rand, RandomConcreteBricks); //Front Wall Pt. 1
			this.placeBlockAtCurrentPosition(world, ModBlocks.brick_concrete_broken, 0, 3, featureSizeY, featureSizeZ - 2, box);
			this.fillWithRandomizedBlocks(world, box, 3, featureSizeY - 1, featureSizeZ - 1, 3, featureSizeY, featureSizeZ - 1, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 4, 0, featureSizeZ, featureSizeX - 1, 1, featureSizeZ, false, rand, RandomConcreteBricks); //Front Wall Pt. 2
			this.fillWithRandomizedBlocks(world, box, 4, 2, featureSizeZ, 4, 3, featureSizeZ, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, featureSizeX - 1, 2, featureSizeZ, featureSizeX - 1, 3, featureSizeZ, false, rand, RandomConcreteBricks);
			this.randomlyFillWithBlocks(world, box, rand, 0.75F, 5, 2, featureSizeZ, featureSizeX - 2, 3, featureSizeZ, Blocks.glass_pane, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 3, featureSizeY, featureSizeZ, featureSizeX, featureSizeY, featureSizeZ, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, featureSizeX, 1, 1, featureSizeX, featureSizeY, featureSizeZ - 1, false, rand, RandomConcreteBricks); //Right Wall
			
			//Floor & Ceiling
			this.fillWithRandomizedBlocks(world, box, 1, 0, 1, featureSizeX - 1, 0, 4, false, rand, RandomLabTiles); //Floor
			this.fillWithRandomizedBlocks(world, box, 4, 0, featureSizeZ - 2, featureSizeX - 1, 0, featureSizeZ - 1, false, rand, RandomLabTiles);
			this.placeBlockAtCurrentPosition(world, ModBlocks.tile_lab_cracked, 0, 3, 0, featureSizeZ - 1, box);
			
			this.fillWithBlocks(world, box, 1, featureSizeY - 1, 1, 1, featureSizeY, 4, ModBlocks.reinforced_glass, Blocks.air, false); //Ceiling
			this.fillWithBlocks(world, box, 2, featureSizeY, 1, featureSizeX - 1, featureSizeY, 4, ModBlocks.brick_light, Blocks.air, false);
			this.fillWithBlocks(world, box, 4, featureSizeY, featureSizeZ - 2, featureSizeX - 1, featureSizeY, featureSizeZ - 1, ModBlocks.brick_light, Blocks.air, false);
			
			return true;
		}
		
		
		
	}
	
	abstract static class Feature extends StructureComponent {
		/** The size of the bounding box for this feature in the X axis */
		protected int featureSizeX;
		/** The size of the bounding box for this feature in the Y axis */
		protected int featureSizeY;
		/** The size of the bounding box for this feature in the Z axis */
		protected int featureSizeZ;
		/** Average height? */
		protected int hpos = -1;
		
		
		protected Feature(Random rand, int minX, int minY, int minZ, int maxX, int maxY, int maxZ ) {
			super(0);
			this.featureSizeX = maxX;
			this.featureSizeY = maxY;
			this.featureSizeZ = maxZ;
			this.coordBaseMode = rand.nextInt(4);
			
			switch(this.coordBaseMode) {
			case 2:
				//North (2) and East (3) will result in mirrored structures. Not an issue, but keep in mind.
				this.boundingBox = new StructureBoundingBox(minX, minY, minZ, minX + maxZ - 1, minY + maxY - 1, minZ + maxX - 1);
				break;
			default:
				this.boundingBox = new StructureBoundingBox(minX, minY, minZ, minX + maxX - 1, minY + maxY - 1, minZ + maxZ - 1);
				
			}
		}
		
		/** Set to NBT */
		protected void func_143012_a(NBTTagCompound nbt) {
			nbt.setInteger("Width", this.featureSizeX);
			nbt.setInteger("Height", this.featureSizeY);
			nbt.setInteger("Depth", this.featureSizeZ);
			nbt.setInteger("HPos", this.hpos);
		}
		
		/** Get from NBT */
		protected void func_143011_b(NBTTagCompound nbt) {
			this.featureSizeX = nbt.getInteger("Width");
			this.featureSizeY = nbt.getInteger("Height");
			this.featureSizeZ = nbt.getInteger("Depth");
			this.hpos = nbt.getInteger("HPos");
		}
		
		protected boolean func_74935_a(World world, StructureBoundingBox box, int y) {
			//System.out.println("original: " + hpos);
			//System.out.println(y);
			
			int j = 0;
			int k = 0;
			
			for(int l = this.boundingBox.minZ; l <= this.boundingBox.maxZ; l++) {
				for(int i = this.boundingBox.minX; i <= this.boundingBox.maxX; i++) {
					if(box.isVecInside(i, y, l)) {
						j += Math.max(world.getTopSolidOrLiquidBlock(i, l), world.provider.getAverageGroundLevel());
						k++;
					}
				}
			}
			
			if(k == 0)
				return false;
			
			this.hpos = j / k;
			this.boundingBox.offset(0, this.hpos - this.boundingBox.minY, 0);
			//System.out.println("new: " + hpos);
			//System.out.println(y);
			return true;
		}
		
		/**
		 * Gets metadata for rotatable pillars.
		 * @param metadata (First two digits is equal to block metadata, other two are equal to orientation
		 * @return metadata adjusted for random orientation
		 */
		protected int getMetadataForRotatable(int metadata) {
			int blockMeta = metadata & 3;
			int rotationMeta = metadata & 12;
			
			if(rotationMeta == 0)
				return metadata;
			
			if(this.coordBaseMode % 2 == 0) { //North & South
				switch(rotationMeta) {
				case 1:
					rotationMeta = 4;
					break;
				case 2:
					rotationMeta = 8;
					break;
				}
			} else if(this.coordBaseMode != 0) { //East & West
				switch(rotationMeta) {
				case 1:
					rotationMeta = 8;
					break;
				case 2:
					rotationMeta = 4;
					break;
				}
			}
			
			return blockMeta | rotationMeta;
		}
	}
	
	//Block Selectors
	
	static class Sandstone extends StructureComponent.BlockSelector {
		
		Sandstone() { }
		
		/** Selects blocks */
		@Override
		public void selectBlocks(Random rand, int p_75062_2_, int p_75062_3_, int p_75062_4_, boolean p_75062_5_) {
			float chance = rand.nextFloat();
			
			if(chance > 0.6F) {
				this.field_151562_a = Blocks.sandstone;
			} else if (chance < 0.5F ) {
				this.field_151562_a = ModBlocks.reinforced_sand;
			} else {
				this.field_151562_a = Blocks.sand;
			}
		}
		
	}
	
	static class ConcreteBricks extends StructureComponent.BlockSelector {
		
		ConcreteBricks() { }
		
		/** Selects blocks */
		@Override
		public void selectBlocks(Random rand, int p_75062_2_, int p_75062_3_, int p_75062_4_, boolean p_75062_5_) {
			float chance = rand.nextFloat();
			
			if(chance < 0.2F) {
				this.field_151562_a = ModBlocks.brick_concrete;
			} else if (chance < 0.4F) {
				this.field_151562_a = ModBlocks.brick_concrete_mossy;
			} else if (chance < 0.8F) {
				this.field_151562_a = ModBlocks.brick_concrete_cracked;
			} else {
				this.field_151562_a = ModBlocks.brick_concrete_broken;
			}
		}
		
	}
	
	static class LabTiles extends StructureComponent.BlockSelector {
		
		LabTiles() { }
		
		/** Selects blocks */
		@Override
		public void selectBlocks(Random rand, int p_75062_2_, int p_75062_3_, int p_75062_4_, boolean p_75062_5_) {
			float chance = rand.nextFloat();
			
			if(chance < 0.5F) {
				this.field_151562_a = ModBlocks.tile_lab;
			} else if (chance < 0.8F) {
				this.field_151562_a = ModBlocks.tile_lab_cracked;
			} else {
				this.field_151562_a = ModBlocks.tile_lab_broken;
			}
		}
		
	}
}

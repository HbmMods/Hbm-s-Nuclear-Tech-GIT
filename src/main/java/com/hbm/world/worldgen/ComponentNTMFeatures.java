package com.hbm.world.worldgen;

import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

//Probably one of the more difficult parts.
/** Base component file. For structure generation under 32x32 blocks, as Minecraft generates 2x2 chunks for structures.
 * Larger non-procedural structures should be split up into several bounding boxes, which check if they intersect the chunk bounding box currently being loaded. Doing so will prevent
 * cascading world generation. See <a href="https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/modification-development/2895477-better-structure-generation">
 * TheMasterCaver's advice.</a> */
public class ComponentNTMFeatures {
	
	/** Register structures in MapGenStructureIO */
	public static void registerNTMFeatures() {
		MapGenStructureIO.func_143031_a(ComponentNTMFeatures.NTMHouse1.class, "NTMHouse1");
		
	}
	
	/** Sandstone Ruin 1 */
	public static class NTMHouse1 extends ComponentNTMFeatures.Feature {
		
		private static ComponentNTMFeatures.Sandstone RandomSandstone = new ComponentNTMFeatures.Sandstone();
		
		/** Constructor for this feature; takes coordinates for bounding box */
		protected NTMHouse1(Random rand, int minX, int minY, int minZ) {
			super(rand, minX, minY, minZ, 9, 4, 6);
		}
		
		@Override
		protected void func_143012_a(NBTTagCompound nbt) {
			super.func_143012_a(nbt);
		}
		
		@Override
		protected void func_143011_b(NBTTagCompound nbt) {
			super.func_143011_b(nbt);
		}
		
		/**
		 * Generates structures.
		 */
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			/*
			 * Places block at current position. Dependent on coordinate mode, i.e. will allow for random rotation
			 * this.placeBlockAtCurrentPosition(world, block, minX, metadata, x, y, z, box);
			 * Fills an area with air, self-explanatory.
			 * this.fillWithAir(world, box, minX, minY, minZ, maxX, maxY, maxZ);
			 * Fills an area with blocks, self-explanatory.
			 * this.fillWithBlocks(world, box, minX, minY, minZ, maxX, maxY, maxZ, blockToPlace, blockToReplace, alwaysReplace);
			 * Fills an area with metadata blocks, self-explanatory.
			 * this.fillWithMetadataBlocks(world, box, minX, minY, minZ, maxX, maxY, maxZ, blockToPlace, blockPlaceMeta, blockToReplace, replaceBlockMeta, alwaysReplace);
			 * Fills an area with randomized blocks, self-explanatory.
			 * this.fillWithRandomizedBlocks(world, box, minX, minY, minZ, maxX, maxY, maxZ, alwaysReplace, rand, StructureComponent.blockSelector);
			 * (BlockSelector is basically a list of blocks that can be randomly picked, except that it can actually be weighted)
			 * Replaces any air or water blocks with this block down to a certain y. Useful for foundations
			 * this.func_151554_b(world, block, metadata, x, fillDownToY, z, box
			 * Fills an area with blocks randomly - look into randLimit?
			 * this.randomlyFillWithBlocks(world, box, rand, randLimit, minX, minY, minZ, maxX, maxY, maxZ, blockToPlace, blockToReplace, alwaysReplace);
			 */
			
			//TODO: func_74935_a is suspect. It seems to be necessary to prevent the structure from spawning at y 0, but it also prevents all spawns.
			//System.out.println("" + this.boundingBox.minX + ", " + this.boundingBox.minY + ", " + this.boundingBox.minZ);
			if(!this.func_74935_a(world, box, this.boundingBox.minY)) {
				return false;
			}
			//System.out.println("Hpos: " + this.hpos + "; minY:" + this.boundingBox.minY);
			
			for(byte i = 0; i < scatteredFeatureSizeX; i++) {
				for(byte j = 0; j < scatteredFeatureSizeZ; j++) {
					this.func_151554_b(world, Blocks.sandstone, 0, i, -1, j, box);
				}
			}
			
			this.fillWithBlocks(world, box, 0, 0, 0, scatteredFeatureSizeX, 0, scatteredFeatureSizeZ, Blocks.sandstone, Blocks.air, false);
			
			this.fillWithRandomizedBlocks(world, box, 0, 1, 0, scatteredFeatureSizeX, 3, 0, false, rand, RandomSandstone);
			this.fillWithRandomizedBlocks(world, box, 0, 1, 0, 0, 2, scatteredFeatureSizeZ, false, rand, RandomSandstone);
			this.fillWithRandomizedBlocks(world, box, 0, 1, scatteredFeatureSizeZ, scatteredFeatureSizeX, 2, 0, false, rand, RandomSandstone);
			
			//System.out.println("Successful spawn");
			
			return true;
		}
		
	}
	
	static class Sandstone extends StructureComponent.BlockSelector {
		
		Sandstone() { }
		
		/** Selects blocks */
		@Override
		public void selectBlocks(Random rand, int p_75062_2_, int p_75062_3_, int p_75062_4_, boolean p_75062_5_) {
			if(rand.nextFloat() < 0.6F) {
				this.field_151562_a = Blocks.sandstone;
			} else if (rand.nextFloat() < 0.4F ) {
				this.field_151562_a = ModBlocks.reinforced_sand;
			}
			
			this.field_151562_a = Blocks.sand;
		}
		
	}
	
	abstract static class Feature extends StructureComponent {
		/** The size of the bounding box for this feature in the X axis */
		protected int scatteredFeatureSizeX;
		/** The size of the bounding box for this feature in the Y axis */
		protected int scatteredFeatureSizeY;
		/** The size of the bounding box for this feature in the Z axis */
		protected int scatteredFeatureSizeZ;
		/** Average height? */
		protected int hpos = -1;
		
		
		protected Feature(Random rand, int minX, int minY, int minZ, int maxX, int maxY, int maxZ ) {
			super(0);
			this.scatteredFeatureSizeX = maxX;
			this.scatteredFeatureSizeY = maxY;
			this.scatteredFeatureSizeZ = maxZ;
			this.coordBaseMode = rand.nextInt(4);
			
			switch(this.coordBaseMode) {
			case 0:
			case 2:
				this.boundingBox = new StructureBoundingBox(minX, minY, minZ, minX + maxX - 1, minY + maxY - 1, minZ + maxZ - 1);
				break;
			default:
				this.boundingBox = new StructureBoundingBox(minX, minY, minZ, minX + maxZ - 1, minY + maxY - 1, minZ + maxX - 1);
			}
		}
		
		/** Set to NBT */
		protected void func_143012_a(NBTTagCompound nbt) {
			nbt.setInteger("Width", this.scatteredFeatureSizeX);
			nbt.setInteger("Height", this.scatteredFeatureSizeY);
			nbt.setInteger("Depth", this.scatteredFeatureSizeZ);
			nbt.setInteger("HPos", this.hpos);
		}
		
		/** Get from NBT */
		protected void func_143011_b(NBTTagCompound nbt) {
			this.scatteredFeatureSizeX = nbt.getInteger("Width");
			this.scatteredFeatureSizeY = nbt.getInteger("Height");
			this.scatteredFeatureSizeZ = nbt.getInteger("Depth");
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
	}
	
}

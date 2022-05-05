package com.hbm.world.worldgen;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockBobble.BobbleType;
import com.hbm.blocks.generic.BlockBobble.TileEntityBobble;
import com.hbm.lib.HbmChestContents;
import com.hbm.tileentity.machine.storage.TileEntityCrateIron;
import com.hbm.util.LootGenerator;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemDoor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.WeightedRandomChestContent;
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
		MapGenStructureIO.func_143031_a(ComponentNTMFeatures.NTMHouse2.class, "NTMHouse2");
		MapGenStructureIO.func_143031_a(ComponentNTMFeatures.NTMLab1.class, "NTMLab1");
		MapGenStructureIO.func_143031_a(ComponentNTMFeatures.NTMLab2.class, "NTMLab2");
		MapGenStructureIO.func_143031_a(ComponentNTMFeatures.NTMWorkshop1.class, "NTMWorkshop1");
		MapGenStructureIO.func_143031_a(ComponentNTMFeatures.NTMRuin1.class, "NTMRuin1");
		MapGenStructureIO.func_143031_a(ComponentNTMFeatures.NTMRuin2.class, "NTMRuin2");
		MapGenStructureIO.func_143031_a(ComponentNTMFeatures.NTMRuin3.class, "NTMRuin3");
		MapGenStructureIO.func_143031_a(ComponentNTMFeatures.NTMRuin4.class, "NTMRuin4");
	}
	
	/** Sandstone Ruin 1 */
	public static class NTMHouse1 extends ComponentNTMFeatures.Feature {
		
		private boolean hasPlacedChest;
		
		private static ComponentNTMFeatures.Sandstone RandomSandstone = new ComponentNTMFeatures.Sandstone();
		
		public NTMHouse1() {
			super();
		}
		
		/** Constructor for this feature; takes coordinates for bounding box */
		protected NTMHouse1(Random rand, int minX, int minY, int minZ) {
			super(rand, minX, minY, minZ, 9, 4, 6);
			this.hasPlacedChest = false;
		}
		
		@Override
		protected void func_143012_a(NBTTagCompound nbt) {
			super.func_143012_a(nbt);
			nbt.setBoolean("hasChest", this.hasPlacedChest);
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
			
			//System.out.println(this.coordBaseMode);
			if(!this.func_74935_a(world, box, this.boundingBox.minY)) {
				return false;
			}
			//System.out.println("" + this.boundingBox.minX + ", " + this.boundingBox.minY + ", " + this.boundingBox.minZ);
			
			for(byte i = 0; i < this.featureSizeX + 1; i++) {
				for(byte j = 0; j < this.featureSizeZ + 1; j++) {
					this.func_151554_b(world, Blocks.sandstone, 0, i, -1, j, box);
				}
			}
			
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
				this.hasPlacedChest = this.generateStructureChestContents(world, box, rand, 3, 0, 1, HbmChestContents.modGeneric, rand.nextInt(2) + 8);
			this.fillWithBlocks(world, box, 5, 0, 1, 6, 0, 1, ModBlocks.crate, Blocks.air, false);
			this.placeBlockAtCurrentPosition(world, Blocks.sand, 0, 7, 0, 1, box);
			if(rand.nextFloat() <= 0.25)
				 this.placeBlockAtCurrentPosition(world, ModBlocks.crate_metal, 0, featureSizeX - 1, 0, 1, box);
			this.randomlyFillWithBlocks(world, box, rand, 0.25F, 1, 0, 2, 3, 0, featureSizeZ - 1, Blocks.sand, Blocks.air, false);
			this.randomlyFillWithBlocks(world, box, rand, 0.25F, 5, 0, 2, featureSizeX - 1, 0, featureSizeZ - 1, Blocks.sand, Blocks.air, false);
			
			return true;
		}
		
	}
	
	public static class NTMHouse2 extends ComponentNTMFeatures.Feature {
		
		private static ComponentNTMFeatures.Sandstone RandomSandstone = new ComponentNTMFeatures.Sandstone();
		
		private boolean[] hasPlacedLoot = new boolean[2];
		
		public NTMHouse2() {
			super();
		}
		
		protected NTMHouse2(Random rand, int minX, int minY, int minZ) {
			super(rand, minX, minY, minZ, 15, 5, 9);
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

		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			//System.out.print(this.coordBaseMode);
			if(!this.func_74935_a(world, box, this.boundingBox.minY)) {
				return false;
			}
			//System.out.println("" + this.boundingBox.minX + ", " + this.boundingBox.minY + ", " + this.boundingBox.minZ);
			
			for(byte i = 0; i < 7; i++) {
				for(byte j = 0; j < this.featureSizeZ + 1; j++) {
					this.func_151554_b(world, Blocks.sandstone, 0, i, -1, j, box);
				}
			}
			
			for(byte i = 9; i < this.featureSizeX + 1; i++) {
				for(byte j = 0; j < this.featureSizeZ + 1; j++) {
					this.func_151554_b(world, Blocks.sandstone, 0, i, -1, j, box);
				}
			}
			
			this.fillWithAir(world, box, 1, 0, 1, 5, featureSizeY, featureSizeZ - 1);
			
			//House 1
			this.fillWithRandomizedBlocks(world, box, 0, 0, 0, 6, 1, 0, false, rand, RandomSandstone); //Back Wall
			this.fillWithRandomizedBlocks(world, box, 0, 2, 0, 1, 2, 0, false, rand, RandomSandstone);
			this.placeBlockAtCurrentPosition(world, Blocks.fence, 0, 2, 2, 0, box);
			this.fillWithRandomizedBlocks(world, box, 3, 2, 0, 3, 2, 0, false, rand, RandomSandstone);
			this.placeBlockAtCurrentPosition(world, Blocks.fence, 0, 4, 2, 0, box);
			this.fillWithRandomizedBlocks(world, box, 5, 2, 0, 6, 2, 0, false, rand, RandomSandstone);
			this.fillWithRandomizedBlocks(world, box, 0, 3, 0, 6, 3, 0, false, rand, RandomSandstone);
			this.fillWithRandomizedBlocks(world, box, 0, 0, 1, 0, 3, featureSizeZ, false, rand, RandomSandstone); //Left Wall
			this.fillWithRandomizedBlocks(world, box, 1, 0, featureSizeZ, 6, 1, featureSizeZ, false, rand, RandomSandstone); //Front Wall
			this.fillWithRandomizedBlocks(world, box, 1, 2, featureSizeZ, 1, 2, featureSizeZ, false, rand, RandomSandstone);
			this.fillWithBlocks(world, box, 2, 2, featureSizeZ, 4, 2, featureSizeZ, Blocks.fence, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 5, 2, featureSizeZ, 6, 2, featureSizeZ, false, rand, RandomSandstone);
			this.fillWithRandomizedBlocks(world, box, 1, 3, featureSizeZ, 6, 3, featureSizeZ, false, rand, RandomSandstone);
			this.fillWithRandomizedBlocks(world, box, 6, 0, featureSizeZ - 1, 6, 3, featureSizeZ - 1, false, rand, RandomSandstone); //Right Wall
			this.fillWithRandomizedBlocks(world, box, 6, 0, featureSizeZ - 2, 6, 0, featureSizeZ - 2, false, rand, RandomSandstone);
			this.fillWithRandomizedBlocks(world, box, 6, 3, featureSizeZ - 2, 6, 3, featureSizeZ - 2, false, rand, RandomSandstone);
			this.fillWithRandomizedBlocks(world, box, 6, 0, 1, 6, 3, featureSizeZ - 3, false, rand, RandomSandstone);
			
			this.fillWithBlocks(world, box, 1, 0, 1, 5, 0, featureSizeZ - 1, Blocks.sandstone, Blocks.air, false); //Floor
			//this.fillWithRandomizedBlocks(world, box, 1, featureSizeY - 1, 0, 5, featureSizeY - 1, featureSizeZ, false, rand, RandomSandstone); //Ceiling
			this.fillWithBlocks(world, box, 1, featureSizeY - 1, 0, 5, featureSizeY - 1, featureSizeZ, Blocks.sandstone, Blocks.air, false);
			this.fillWithMetadataBlocks(world, box, 0, featureSizeY - 1, 0, 0, featureSizeY - 1, featureSizeZ, Blocks.stone_slab, 1, Blocks.air, 0, false); //Roof
			this.fillWithMetadataBlocks(world, box, 6, featureSizeY - 1, 0, 6, featureSizeY - 1, featureSizeZ, Blocks.stone_slab, 1, Blocks.air, 0, false);
			this.fillWithMetadataBlocks(world, box, 2, featureSizeY, 0, 4, featureSizeY, 0, Blocks.stone_slab, 1, Blocks.air, 0, false);
			this.fillWithMetadataBlocks(world, box, 3, featureSizeY, 1, 3, featureSizeY, 2, Blocks.stone_slab, 1, Blocks.air, 0, false);
			this.fillWithMetadataBlocks(world, box, 3, featureSizeY, 4, 3, featureSizeY, 6, Blocks.stone_slab, 1, Blocks.air, 0, false);
			this.placeBlockAtCurrentPosition(world, Blocks.stone_slab, 1, 3, featureSizeY, featureSizeZ - 1, box);
			this.fillWithMetadataBlocks(world, box, 2, featureSizeY, featureSizeZ, 4, featureSizeY, featureSizeZ, Blocks.stone_slab, 1, Blocks.air, 0, false);
			
			//House 2
			this.fillWithRandomizedBlocks(world, box, featureSizeX - 6, 0, 0, featureSizeX, 0, 0, false, rand, RandomSandstone); //Back Wall
			this.fillWithRandomizedBlocks(world, box, featureSizeX - 6, 1, 0, featureSizeX - 2, 1, 0, false, rand, RandomSandstone);
			this.fillWithRandomizedBlocks(world, box, featureSizeX - 6, 2, 0, featureSizeX - 6, 2, 0, false, rand, RandomSandstone);
			this.placeBlockAtCurrentPosition(world, Blocks.stone_slab, 1, featureSizeX - 6, 2, 0, box);
			this.placeBlockAtCurrentPosition(world, Blocks.stone_slab, 1, featureSizeX - 3, 2, 0, box);
			this.fillWithRandomizedBlocks(world, box, featureSizeX - 6, 0, 1, featureSizeX - 6, 3, 1, false, rand, RandomSandstone); //Left Wall
			this.fillWithRandomizedBlocks(world, box, featureSizeX - 6, 0, 2, featureSizeX - 6, 0, 2, false, rand, RandomSandstone);
			this.fillWithRandomizedBlocks(world, box, featureSizeX - 6, 3, 2, featureSizeX - 6, 3, featureSizeZ - 1, false, rand, RandomSandstone);
			this.placeBlockAtCurrentPosition(world, Blocks.stone_slab, 1, featureSizeX - 6, featureSizeY - 1, 2, box);
			this.fillWithMetadataBlocks(world, box, featureSizeX - 6, featureSizeY - 1, 4, featureSizeX - 6, featureSizeY - 1, featureSizeZ - 2, Blocks.stone_slab, 1, Blocks.air, 0, false);
			this.fillWithRandomizedBlocks(world, box, featureSizeX - 6, 0, 3, featureSizeX - 6, 1, featureSizeZ, false, rand, RandomSandstone);
			this.fillWithRandomizedBlocks(world, box, featureSizeX - 6, 0, 2, featureSizeX - 6, 0, 2, false, rand, RandomSandstone);
			this.fillWithRandomizedBlocks(world, box, featureSizeX - 6, 2, 3, featureSizeX - 6, 2, 3, false, rand, RandomSandstone);
			this.placeBlockAtCurrentPosition(world, Blocks.fence, 0, featureSizeX - 6, 2, 4, box);
			this.fillWithRandomizedBlocks(world, box, featureSizeX - 6, 2, 5, featureSizeX - 6, 2, 5, false, rand, RandomSandstone);
			this.fillWithBlocks(world, box, featureSizeX - 6, 2, featureSizeZ - 3, featureSizeX - 6, 2, featureSizeZ - 2, Blocks.fence, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, featureSizeX - 6, 2, featureSizeZ - 1, featureSizeX - 6, 2, featureSizeZ, false, rand, RandomSandstone);
			this.fillWithRandomizedBlocks(world, box, featureSizeX - 5, 0, featureSizeZ, featureSizeX, 1, featureSizeZ, false, rand, RandomSandstone); //Front Wall
			this.fillWithRandomizedBlocks(world, box, featureSizeX - 5, 2, featureSizeZ, featureSizeX - 5, 2, featureSizeZ, false, rand, RandomSandstone);
			this.fillWithRandomizedBlocks(world, box, featureSizeX - 1, 2, featureSizeZ, featureSizeX, 2, featureSizeZ, false, rand, RandomSandstone);
			this.fillWithRandomizedBlocks(world, box, featureSizeX, 0, 1, featureSizeX, 0, featureSizeZ - 1, false, rand, RandomSandstone); //Right Wall
			this.fillWithRandomizedBlocks(world, box, featureSizeX, 1, 3, featureSizeX, 1, 3, false, rand, RandomSandstone);
			this.fillWithMetadataBlocks(world, box, featureSizeX, 1, 4, featureSizeX, 1, 5, Blocks.stone_slab, 1, Blocks.air, 0, false);
			this.fillWithRandomizedBlocks(world, box, featureSizeX, 1, featureSizeZ - 1, featureSizeX, 1, featureSizeZ - 3, false, rand, RandomSandstone);
			this.placeBlockAtCurrentPosition(world, Blocks.stone_slab, 1, featureSizeX, 1, featureSizeZ - 1, box);
			
			this.fillWithBlocks(world, box, featureSizeX - 5, 0, 1, featureSizeX - 1, 0, featureSizeZ - 1, Blocks.sandstone, Blocks.air, false); //Floor
			
			//Loot & Decorations
			//House 1
			int eastMeta = this.getMetadataForRotatableDeco(4);
			this.placeBlockAtCurrentPosition(world, ModBlocks.machine_boiler_off, 4, 1, 1, 1, box);
			this.fillWithBlocks(world, box, 1, 2, 1, 1, 3, 1, ModBlocks.deco_pipe_quad_rusted, Blocks.air, false);
			this.placeBlockAtCurrentPosition(world, ModBlocks.deco_pipe_rim_rusted, 0, 1, featureSizeY, 1, box);
			this.placeBlockAtCurrentPosition(world, ModBlocks.crate, 0, 2, 1, 3, box);
			this.placeBlockAtCurrentPosition(world, ModBlocks.crate_can, 0, 1, 1, featureSizeZ - 4, box);
			if(!hasPlacedLoot[0]) {
				this.placeBlockAtCurrentPosition(world, Blocks.chest, this.getMetadataWithOffset(Blocks.chest, 3), 1, 1, featureSizeZ - 2, box);
				WeightedRandomChestContent.generateChestContents(rand, HbmChestContents.machineParts, (TileEntityChest)world.getTileEntity(this.getXWithOffset(1, featureSizeZ - 2), 
						this.getYWithOffset(1), this.getZWithOffset(1, featureSizeZ - 2)), 10);
				this.hasPlacedLoot[0] = true;
			}
			this.fillWithBlocks(world, box, 4, 1, featureSizeZ - 1, 5, 1, featureSizeZ - 1, ModBlocks.crate, Blocks.air, false);
			this.fillWithMetadataBlocks(world, box, 5, 1, 4, 5, 3, 4, ModBlocks.steel_scaffold, eastMeta, Blocks.air, 0, false);
			this.fillWithMetadataBlocks(world, box, 5, 1, 6, 5, 3, 6, ModBlocks.steel_scaffold, eastMeta, Blocks.air, 0, false);
			this.placeBlockAtCurrentPosition(world, ModBlocks.steel_grate, 7, 5, 1, 5, box);
			this.placeBlockAtCurrentPosition(world, ModBlocks.crate_weapon, 0, 5, 2, 5, box);
			
			//House 2
			if(!hasPlacedLoot[1]) {
				this.placeBlockAtCurrentPosition(world, Blocks.chest, this.getMetadataWithOffset(Blocks.chest, 3), featureSizeX - 5, 1, 1, box);
				WeightedRandomChestContent.generateChestContents(rand, HbmChestContents.antenna, (TileEntityChest)world.getTileEntity(this.getXWithOffset(featureSizeX - 5, 1), 
						this.getYWithOffset(1), this.getZWithOffset(featureSizeX - 5, 1)), 10);
				this.hasPlacedLoot[1] = true;
			}
			this.placeBlockAtCurrentPosition(world, ModBlocks.bobblehead, rand.nextInt(16), featureSizeX - 5, 1, 4, box);
			TileEntityBobble bobble = (TileEntityBobble) world.getTileEntity(this.getXWithOffset(featureSizeX - 5, 4), this.getYWithOffset(1), this.getZWithOffset(featureSizeX - 5, 4));
			
			if(bobble != null) {
				bobble.type = BobbleType.values()[rand.nextInt(BobbleType.values().length - 1) + 1];
				bobble.markDirty();
			}
			
			this.randomlyFillWithBlocks(world, box, rand, 0.25F, featureSizeX - 4, 1, 1, featureSizeX - 1, 1, featureSizeZ - 1, Blocks.sand, Blocks.air, false);
			
			return true;
		}
	}
	
	public static class NTMLab1 extends ComponentNTMFeatures.Feature {
		
		private static ComponentNTMFeatures.ConcreteBricks RandomConcreteBricks = new ComponentNTMFeatures.ConcreteBricks();
		private static ComponentNTMFeatures.LabTiles RandomLabTiles = new ComponentNTMFeatures.LabTiles();
		
		private boolean[] hasPlacedLoot = new boolean[2];
		
		public NTMLab1() {
			super();
		}
		
		/** Constructor for this feature; takes coordinates for bounding box */
		protected NTMLab1(Random rand, int minX, int minY, int minZ) {
			super(rand, minX, minY, minZ, 9, 4, 7);
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

		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			//System.out.println(this.coordBaseMode);
			if(!this.func_74935_a(world, box, this.boundingBox.minY)) {
				return false;
			}
			//System.out.println("" + this.boundingBox.minX + ", " + this.boundingBox.minY + ", " + this.boundingBox.minZ);
			
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
			this.fillWithAir(world, box, 3, 1, featureSizeZ - 1, 3, 2, featureSizeZ - 1);
			
			int pillarMeta = this.getMetadataForRotatablePillar(8);
			
			//Pillars
			this.fillWithBlocks(world, box, 0, 0, 0, 0, 3, 0, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithBlocks(world, box, featureSizeX, 0, 0, featureSizeX, 3, 0, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithMetadataBlocks(world, box, 0, 0, 1, 0, 0, 4, ModBlocks.concrete_pillar, pillarMeta, Blocks.air, 0, false);
			this.fillWithMetadataBlocks(world, box, featureSizeX, 0, 1, featureSizeX, 0, featureSizeZ - 1, ModBlocks.concrete_pillar, pillarMeta, Blocks.air, 0, false);
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
			
			//Decorations & Loot
			this.fillWithMetadataBlocks(world, box, 1, 1, 1, 1, 1, 4, Blocks.dirt, 2, Blocks.air, 0, false);
			int westDecoMeta = this.getMetadataForRotatableDeco(5);
			this.fillWithMetadataBlocks(world, box, 2, 1, 1, 2, 1, 4, ModBlocks.steel_wall, westDecoMeta, Blocks.air, 0, false);
			this.fillWithMetadataBlocks(world, box, 2, featureSizeY - 1, 1, 2, featureSizeY - 1, 4, ModBlocks.steel_wall, westDecoMeta, Blocks.air, 0, false);
			for(byte i = 0; i < 4; i++) {
				this.placeBlockAtCurrentPosition(world, ModBlocks.plant_flower, i, 1, 2, 1 + i, box);
			}
			
			int doorMeta = this.getMetadataWithOffset(Blocks.wooden_door, 2);
			this.placeBlockAtCurrentPosition(world, ModBlocks.door_office, doorMeta, 3, 1, featureSizeZ - 1, box);
			ItemDoor.placeDoorBlock(world, this.getXWithOffset(3, featureSizeZ - 1), this.getYWithOffset(1), this.getZWithOffset(3, featureSizeZ - 1), doorMeta, ModBlocks.door_office);
			
			int northDecoMeta = this.getMetadataForRotatableDeco(3);
			this.fillWithMetadataBlocks(world, box, 5, featureSizeY - 1, 1, featureSizeX - 1, featureSizeY - 1, 1, ModBlocks.steel_scaffold, westDecoMeta, Blocks.air, 0, false);
			this.fillWithMetadataBlocks(world, box, 5, featureSizeY - 1, 2, featureSizeX - 1, featureSizeY - 1, 2, ModBlocks.steel_wall, northDecoMeta, Blocks.air, 0, false);
			this.placeBlockAtCurrentPosition(world, ModBlocks.machine_electric_furnace_off, northDecoMeta, 5, 1, 1, box);
			this.placeBlockAtCurrentPosition(world, ModBlocks.machine_microwave, northDecoMeta, 5, 2, 1, box);
			this.placeBlockAtCurrentPosition(world, ModBlocks.deco_titanium, 0, 6, 1, 1, box);
			this.placeBlockAtCurrentPosition(world, ModBlocks.machine_shredder, 0, featureSizeX - 2, 1, 1, box);
			this.placeBlockAtCurrentPosition(world, ModBlocks.deco_titanium, 0, featureSizeX - 1, 1, 1, box);
			this.fillWithBlocks(world, box, 5, 1, 3, featureSizeX - 1, 1, 3, ModBlocks.deco_titanium, Blocks.air, false);
			if(!hasPlacedLoot[0]) {
				this.placeBlockAtCurrentPosition(world, ModBlocks.deco_loot, 0, 6, 2, 3, box);
				LootGenerator.lootMedicine(world, this.getXWithOffset(6, 3), this.getYWithOffset(2), this.getZWithOffset(6, 3));
				this.hasPlacedLoot[0] = true;
			}
			
			this.placeBlockAtCurrentPosition(world, ModBlocks.crate_can, 0, featureSizeX - 1, 1, featureSizeZ - 2, box);
			if(!hasPlacedLoot[1]) {
				this.hasPlacedLoot[1] = this.generateIronCrateContents(world, box, rand, featureSizeX - 1, 1, featureSizeZ - 1, HbmChestContents.modGeneric, 8);
			}
			
			return true;
		}
	}
	
	public static class NTMLab2 extends ComponentNTMFeatures.Feature {
		
		private static ComponentNTMFeatures.SuperConcrete RandomSuperConcrete = new ComponentNTMFeatures.SuperConcrete();
		private static ComponentNTMFeatures.ConcreteBricks RandomConcreteBricks = new ComponentNTMFeatures.ConcreteBricks();
		private static ComponentNTMFeatures.LabTiles RandomLabTiles = new ComponentNTMFeatures.LabTiles();
		
		private boolean[] hasPlacedLoot = new boolean[2];
		
		public NTMLab2() {
			super();
		}

		protected NTMLab2(Random rand, int minX, int minY, int minZ) {
			super(rand, minX, minY, minZ, 12, 11, 8);
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
		
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			//System.out.println(this.coordBaseMode);
			if(!this.func_74935_a(world, box, this.boundingBox.minY)) {
				return false;
			}
			this.boundingBox.offset(0, -7, 0);
			//System.out.println("" + this.boundingBox.minX + ", " + this.boundingBox.minY + ", " + this.boundingBox.minZ);
			
			for(byte i = 0; i < featureSizeX + 1; i++) {
				for(byte j = 0; j < featureSizeZ - 1; j++) {
					this.func_151554_b(world, Blocks.stonebrick, 0, i, 6, j, box);
				}
			}
			
			for(byte i = 0; i < 7; i++) {
				for(byte j = 7; j < featureSizeZ + 1; j++) {
					this.func_151554_b(world, Blocks.stonebrick, 0, i, 6, j, box);
				}
			}
			
			if(this.getBlockAtCurrentPosition(world, featureSizeX - 3, featureSizeY - 4, 7, box).getMaterial().isReplaceable() 
					|| this.getBlockAtCurrentPosition(world, featureSizeX - 3, featureSizeY - 4, 7, box) == Blocks.air) {
				int stairMeta = this.getMetadataWithOffset(Blocks.stone_brick_stairs, 2);
				this.func_151554_b(world, Blocks.stonebrick, 0, featureSizeX - 3, featureSizeY - 4, 7, box);
				this.func_151554_b(world, Blocks.stonebrick, 0, featureSizeX - 2, featureSizeY - 4, 7, box);
				this.fillWithMetadataBlocks(world, box, featureSizeX - 3, featureSizeY - 4, 7, featureSizeX - 2, featureSizeY - 4, 7, Blocks.stone_brick_stairs, stairMeta, Blocks.air, 0, false);
			}
			
			
			this.fillWithAir(world, box, 1, featureSizeY - 4, 1, featureSizeX - 1, featureSizeY, featureSizeZ - 3);
			this.fillWithAir(world, box, 1, featureSizeY - 4, featureSizeZ - 2, 5, featureSizeY, featureSizeZ - 1);
			this.fillWithAir(world, box, featureSizeX - 3, featureSizeY - 3, featureSizeZ - 2, featureSizeX - 2, featureSizeY - 2, featureSizeZ - 2);
			this.fillWithAir(world, box, 5, 5, 1, 6, 6, 2);
			this.fillWithAir(world, box, 2, 0, 2, featureSizeX - 2, 3, featureSizeZ - 2);		
			
			//Walls
			this.fillWithRandomizedBlocks(world, box, 0, featureSizeY - 4, 0, featureSizeX, featureSizeY, 0, false, rand, RandomSuperConcrete); //Back Wall
			this.fillWithRandomizedBlocks(world, box, 0, featureSizeY - 4, 0, 0, featureSizeY, featureSizeZ, false, rand, RandomSuperConcrete); //Left Wall
			this.fillWithRandomizedBlocks(world, box, 1, featureSizeY - 4, featureSizeZ, 5, featureSizeY - 4, featureSizeZ, false, rand, RandomSuperConcrete); //Front Wall pt. 1
			this.fillWithBlocks(world, box, 1, featureSizeY - 3, featureSizeZ, 1, featureSizeY - 1, featureSizeZ, ModBlocks.reinforced_glass, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 2, featureSizeY - 4, featureSizeZ, 2, featureSizeY - 1, featureSizeZ, false, rand, RandomSuperConcrete);
			this.fillWithBlocks(world, box, 3, featureSizeY - 3, featureSizeZ, 3, featureSizeY - 1, featureSizeZ, ModBlocks.reinforced_glass, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 4, featureSizeY - 4, featureSizeZ, 4, featureSizeY - 1, featureSizeZ, false, rand, RandomSuperConcrete);
			this.fillWithBlocks(world, box, 5, featureSizeY - 3, featureSizeZ, 5, featureSizeY - 1, featureSizeZ, ModBlocks.reinforced_glass, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 1, featureSizeY, featureSizeZ, 5, featureSizeY, featureSizeZ, false, rand, RandomSuperConcrete);
			this.fillWithRandomizedBlocks(world, box, 6, featureSizeY - 4, featureSizeZ - 1, 6, featureSizeY, featureSizeZ, false, rand, RandomSuperConcrete); //Front Wall pt. 2
			this.fillWithRandomizedBlocks(world, box, 6, featureSizeY - 4, featureSizeZ - 2, 7, featureSizeY - 2, featureSizeZ - 2, false, rand, RandomSuperConcrete); //Front Wall pt. 3
			this.fillWithBlocks(world, box, 6, featureSizeY - 1, featureSizeZ - 2, 7, featureSizeY - 1, featureSizeZ - 2, ModBlocks.concrete_super_broken, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, featureSizeX - 4, featureSizeY - 4, featureSizeZ - 2, featureSizeX, featureSizeY - 4, featureSizeZ - 2, false, rand, RandomSuperConcrete);
			this.fillWithRandomizedBlocks(world, box, featureSizeX - 4, featureSizeY - 3, featureSizeZ - 2, featureSizeX - 4, featureSizeY, featureSizeZ - 2, false, rand, RandomSuperConcrete);
			this.fillWithRandomizedBlocks(world, box, featureSizeX - 3, featureSizeY - 1, featureSizeZ - 2, featureSizeX - 2, featureSizeY, featureSizeZ - 2, false, rand, RandomSuperConcrete);
			this.fillWithRandomizedBlocks(world, box, featureSizeX - 1, featureSizeY - 4, featureSizeZ - 2, featureSizeX, featureSizeY, featureSizeZ - 2, false, rand, RandomSuperConcrete);
			this.fillWithRandomizedBlocks(world, box, featureSizeX, featureSizeY - 4, 1, featureSizeX, featureSizeY - 4, featureSizeZ - 3, false, rand, RandomSuperConcrete); //Right Wall
			this.fillWithBlocks(world, box, featureSizeX, featureSizeY - 3, featureSizeZ - 3, featureSizeX, featureSizeY - 1, featureSizeZ - 3, ModBlocks.reinforced_glass, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, featureSizeX, featureSizeY - 3, 4, featureSizeX, featureSizeY - 1, 4, false, rand, RandomSuperConcrete);
			this.fillWithBlocks(world, box, featureSizeX, featureSizeY - 3, 3, featureSizeX, featureSizeY - 1, 3, ModBlocks.reinforced_glass, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, featureSizeX, featureSizeY - 3, 2, featureSizeX, featureSizeY - 1, 2, false, rand, RandomSuperConcrete);
			this.fillWithBlocks(world, box, featureSizeX, featureSizeY - 3, 1, featureSizeX, featureSizeY - 1, 1, ModBlocks.reinforced_glass, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, featureSizeX, featureSizeY, 1, featureSizeX, featureSizeY, featureSizeZ - 3, false, rand, RandomSuperConcrete);
			
			this.fillWithBlocks(world, box, 1, 0, 1, featureSizeX - 1, 3, 1, ModBlocks.reinforced_stone, Blocks.air, false); //Back Wall
			this.fillWithBlocks(world, box, 1, 0, 2, 1, 3, featureSizeZ - 2, ModBlocks.reinforced_stone, Blocks.air, false); //Left Wall
			this.fillWithBlocks(world, box, 1, 0, featureSizeZ - 1, featureSizeX - 1, 3, featureSizeZ - 1, ModBlocks.reinforced_stone, Blocks.air, false); //Front Wall 
			this.fillWithBlocks(world, box, featureSizeX - 1, 0, 2, featureSizeX - 1, 3, featureSizeZ - 2, ModBlocks.reinforced_stone, Blocks.air, false); // Right Wall
			this.fillWithBlocks(world, box, 6, 0, 3, 6, 3, featureSizeZ - 2, ModBlocks.reinforced_stone, Blocks.air, false); //Internal Wall
			
			//Floors & Ceiling
			this.fillWithRandomizedBlocks(world, box, 1, featureSizeY - 4, 1, 3, featureSizeY - 4, featureSizeZ - 1, false, rand, RandomLabTiles); //Left Floor
			this.fillWithRandomizedBlocks(world, box, 4, featureSizeY - 4, featureSizeZ - 2, 5, featureSizeY - 4, featureSizeZ - 1, false, rand, RandomLabTiles);
			this.fillWithRandomizedBlocks(world, box, featureSizeX - 4, featureSizeY - 4, 1, featureSizeX - 1, featureSizeY - 4, featureSizeZ - 3, false, rand, RandomLabTiles); //Right Floor
			this.fillWithRandomizedBlocks(world, box, featureSizeX - 3, featureSizeY - 4, featureSizeZ - 2, featureSizeX - 2, featureSizeY - 4, featureSizeZ - 2, false, rand, RandomLabTiles);
			this.fillWithBlocks(world, box, 4, featureSizeY - 4, 1, 7, featureSizeY - 4, 1, ModBlocks.tile_lab_broken, Blocks.air, false); //Center Floor (Pain)
			this.placeBlockAtCurrentPosition(world, ModBlocks.tile_lab_broken, 0, 4, featureSizeY - 4, 2, box);
			this.fillWithBlocks(world, box, 4, featureSizeY - 4, 3, 4, featureSizeY - 4, 5, ModBlocks.tile_lab_cracked, Blocks.air, false);
			this.placeBlockAtCurrentPosition(world, ModBlocks.tile_lab_broken, 0, 5, featureSizeY - 4, 3, box);
			this.fillWithBlocks(world, box, 5, featureSizeY - 4, 4, 5, featureSizeY - 4, 5, ModBlocks.tile_lab_cracked, Blocks.air, false);
			this.placeBlockAtCurrentPosition(world, ModBlocks.tile_lab_broken, 0, 6, featureSizeY - 4, 4, box);
			this.placeBlockAtCurrentPosition(world, ModBlocks.tile_lab_cracked, 0, 6, featureSizeY - 4, 5, box);
			this.fillWithBlocks(world, box, 7, featureSizeY - 4, 2, 7, featureSizeY - 4, 3, ModBlocks.tile_lab_broken, Blocks.air, false);
			this.fillWithBlocks(world, box, 7, featureSizeY - 4, 4, 7, featureSizeY - 4, 5, ModBlocks.tile_lab_cracked, Blocks.air, false);
			
			this.fillWithBlocks(world, box, 1, featureSizeY, 1, 2, featureSizeY, featureSizeZ - 1, ModBlocks.brick_light, Blocks.air, false); //Left Ceiling
			this.fillWithBlocks(world, box, 3, featureSizeY, featureSizeZ - 2, 4, featureSizeY, featureSizeZ - 1, ModBlocks.brick_light, Blocks.air, false);
			this.fillWithBlocks(world, box, featureSizeX - 3, featureSizeY, 1, featureSizeX - 1, featureSizeY, featureSizeZ - 3, ModBlocks.brick_light, Blocks.air, false); //Right Ceiling
			this.fillWithBlocks(world, box, 3, featureSizeY, 1, 8, featureSizeY, 1, ModBlocks.waste_planks, Blocks.air, false); //Center Ceiling (Pain)
			this.fillWithBlocks(world, box, 3, featureSizeY, 2, 4, featureSizeY, 2, ModBlocks.waste_planks, Blocks.air, false);
			this.fillWithBlocks(world, box, 7, featureSizeY, 2, 8, featureSizeY, 2, ModBlocks.waste_planks, Blocks.air, false);
			this.fillWithBlocks(world, box, 3, featureSizeY, 3, 3, featureSizeY, 5, ModBlocks.waste_planks, Blocks.air, false);
			this.fillWithBlocks(world, box, 4, featureSizeY, 4, 4, featureSizeY, 5, ModBlocks.waste_planks, Blocks.air, false);
			this.fillWithBlocks(world, box, 5, featureSizeY, 6, 5, featureSizeY, featureSizeZ - 1, ModBlocks.waste_planks, Blocks.air, false);
			this.fillWithBlocks(world, box, 8, featureSizeY, 3, 8, featureSizeY, 5, ModBlocks.waste_planks, Blocks.air, false);
			
			this.fillWithRandomizedBlocks(world, box, 2, 0, 2, 5, 0, featureSizeZ - 2, false, rand, RandomLabTiles); //Floor
			this.fillWithRandomizedBlocks(world, box, 6, 0, 2, 6, 0, 3, false, rand, RandomLabTiles);
			this.fillWithRandomizedBlocks(world, box, 7, 0, 2, featureSizeX - 2, 0, featureSizeZ - 2, false, rand, RandomLabTiles);
			
			this.fillWithRandomizedBlocks(world, box, 1, 4, 1, featureSizeX - 1, 4, featureSizeZ - 1, false, rand, RandomConcreteBricks); //Ceiling
			
			//Decorations & Loot
			int eastMeta = this.getMetadataForRotatableDeco(4);
			int westMeta = this.getMetadataForRotatableDeco(5);
			int northMeta = this.getMetadataForRotatableDeco(3);
			int southMeta = this.getMetadataForRotatableDeco(2);
			this.placeBlockAtCurrentPosition(world, ModBlocks.crashed_balefire, southMeta, 6, featureSizeY - 2, 3, box);
			
			int doorMeta = this.getMetadataWithOffset(Blocks.wooden_door, 1);
			this.placeBlockAtCurrentPosition(world, ModBlocks.door_office, doorMeta, featureSizeX - 3, featureSizeY - 3, featureSizeZ - 2, box);
			ItemDoor.placeDoorBlock(world, this.getXWithOffset(featureSizeX - 3, featureSizeZ - 2), this.getYWithOffset(featureSizeY - 3), this.getZWithOffset(featureSizeX - 3, featureSizeZ - 2), 
					doorMeta, ModBlocks.door_office);
			this.placeBlockAtCurrentPosition(world, ModBlocks.door_office, doorMeta, featureSizeX - 2, featureSizeY - 3, featureSizeZ - 2, box);
			ItemDoor.placeDoorBlock(world, this.getXWithOffset(featureSizeX - 2, featureSizeZ - 2), this.getYWithOffset(featureSizeY - 3), this.getZWithOffset(featureSizeX - 2, featureSizeZ - 2), 
					doorMeta, ModBlocks.door_office);
			
			this.fillWithBlocks(world, box, 1, featureSizeY - 3, 1, 1, featureSizeY - 1, 1, ModBlocks.deco_steel, Blocks.air, false);
			this.fillWithMetadataBlocks(world, box, 1, featureSizeY - 3, 2, 1, featureSizeY - 2, 3, ModBlocks.steel_grate, 7, Blocks.air, 0, false);
			this.placeBlockAtCurrentPosition(world, ModBlocks.tape_recorder, westMeta, 1, featureSizeY - 1, 2, box);
			this.placeBlockAtCurrentPosition(world, ModBlocks.steel_beam, 0, 1, featureSizeY - 1, 3, box);
			this.fillWithBlocks(world, box, 1, featureSizeY - 3, 6, 1, featureSizeY - 1, 6, ModBlocks.deco_pipe_framed_rusted, Blocks.air, false);
			
			this.fillWithMetadataBlocks(world, box, featureSizeX - 4, featureSizeY - 3, 1, featureSizeX - 4, featureSizeY - 1, 1, ModBlocks.steel_wall, eastMeta, Blocks.air, 0, false);
			this.fillWithMetadataBlocks(world, box, featureSizeX - 3, featureSizeY - 1, 1, featureSizeX - 2, featureSizeY - 1, 1, ModBlocks.steel_grate, 0, Blocks.air, 0, false);
			this.fillWithMetadataBlocks(world, box, featureSizeX - 3, featureSizeY - 2, 1, featureSizeX - 2, featureSizeY - 2, 1, ModBlocks.tape_recorder, northMeta, Blocks.air, 0, false);
			this.fillWithBlocks(world, box, featureSizeX - 3, featureSizeY - 3, 1, featureSizeX - 2, featureSizeY - 3, 1, ModBlocks.deco_steel, Blocks.air, false);
			this.fillWithMetadataBlocks(world, box, featureSizeX - 1, featureSizeY - 3, 1, featureSizeX - 1, featureSizeY - 1, 1, ModBlocks.steel_wall, westMeta, Blocks.air, 0, false);
			
			this.fillWithMetadataBlocks(world, box, 2, 1, 2, 2, 1, featureSizeZ - 2, ModBlocks.steel_grate, 7, Blocks.air, 0, false);
			this.placeBlockAtCurrentPosition(world, ModBlocks.vitrified_barrel, 0, 2, 2, 2, box);
			this.fillWithMetadataBlocks(world, box, 3, 1, 2, 3, 3, 2, ModBlocks.steel_wall, westMeta, Blocks.air, 0, false);
			this.fillWithMetadataBlocks(world, box, 3, 1, 4, 3, 3, 4, ModBlocks.steel_wall, westMeta, Blocks.air, 0, false);
			this.fillWithMetadataBlocks(world, box, 3, 1, featureSizeZ - 2, 3, 3, featureSizeZ - 2, ModBlocks.steel_wall, westMeta, Blocks.air, 0, false);
			this.placeBlockAtCurrentPosition(world, ModBlocks.crate, 0, 4, 1, featureSizeZ - 2, box);
			this.placeBlockAtCurrentPosition(world, ModBlocks.crate_lead, 0, 4, 2, featureSizeZ - 2, box);
			if(!hasPlacedLoot[0]) {
				this.hasPlacedLoot[0] = this.generateIronCrateContents(world, box, rand, 5, 1, featureSizeZ - 2, HbmChestContents.nuclearFuel, 10);
			}
			this.fillWithBlocks(world, box, 4, 1, featureSizeZ - 3, 5, 1, featureSizeZ - 3, ModBlocks.crate_lead, Blocks.air, false);
			
			this.fillWithBlocks(world, box, featureSizeX - 5, 1, featureSizeZ - 2, featureSizeX - 5, 3, featureSizeZ - 2, ModBlocks.deco_steel, Blocks.air, false);;
			this.fillWithMetadataBlocks(world, box, featureSizeX - 4, 1, featureSizeZ - 2, featureSizeX - 2, 1, featureSizeZ - 2, ModBlocks.steel_grate, 7, Blocks.air, 0, false);
			this.fillWithMetadataBlocks(world, box, featureSizeX - 4, 2, featureSizeZ - 2, featureSizeX - 3, 2, featureSizeZ - 2, ModBlocks.tape_recorder, southMeta, Blocks.air, 0, false);
			this.placeBlockAtCurrentPosition(world, ModBlocks.steel_beam, 0, featureSizeX - 2, 2, featureSizeZ - 2, box);
			this.fillWithBlocks(world, box, featureSizeX - 4, 3, featureSizeZ - 2, featureSizeX - 2, 3, featureSizeZ - 2, ModBlocks.steel_roof, Blocks.air, false);
			if(!hasPlacedLoot[1]) {
				this.hasPlacedLoot[1] = this.generateIronCrateContents(world, box, rand, featureSizeX - 2, 1, 3, HbmChestContents.nukeTrash, 9);
			}
			
			return true;
		}
	}
	
	public static class NTMWorkshop1 extends ComponentNTMFeatures.Feature {
		
		private static ComponentNTMFeatures.SuperConcrete RandomSuperConcrete = new ComponentNTMFeatures.SuperConcrete();
		
		private boolean hasPlacedLoot;
		
		public NTMWorkshop1() {
			super();
		}
		
		protected NTMWorkshop1(Random rand, int minX, int minY, int minZ) {
			super(rand, minX, minY, minZ, 10, 6, 8);
			this.hasPlacedLoot = false;
		}
		
		@Override
		protected void func_143012_a(NBTTagCompound nbt) {
			super.func_143012_a(nbt);
			nbt.setBoolean("hasLoot", this.hasPlacedLoot);
		}
		
		@Override
		protected void func_143011_b(NBTTagCompound nbt) {
			super.func_143011_b(nbt);
			this.hasPlacedLoot = nbt.getBoolean("hasLoot");
		}

		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			////System.out.println(this.coordBaseMode);
			if(!this.func_74935_a(world, box, this.boundingBox.minY)) {
				return false;
			}
			//System.out.println("" + this.boundingBox.minX + ", " + this.boundingBox.minY + ", " + this.boundingBox.minZ);
			
			for(byte i = 1; i < featureSizeX - 2; i++) {
				for(byte j = 0; j < featureSizeZ + 1; j++) {
					this.func_151554_b(world, Blocks.stonebrick, 0, i, -1, j, box);
				}
			}
			
			for(byte i = 8; i < featureSizeX + 1; i++) {
				for(byte j = 1; j < 7; j++) {
					this.func_151554_b(world, Blocks.dirt, 0, i, -1, j, box);
				}
			}
			
			this.fillWithAir(world, box, 1, 0, 0, featureSizeX - 3, featureSizeY - 2, featureSizeZ);
			this.fillWithAir(world, box, featureSizeX - 2, 0, 2, featureSizeX - 1, 2, 5);
			
			if(this.getBlockAtCurrentPosition(world, 0, 0, 5, box).getMaterial().isReplaceable() 
					|| this.getBlockAtCurrentPosition(world, 0, 0, 5, box) == Blocks.air) {
				int stairMeta = this.getMetadataWithOffset(Blocks.stone_brick_stairs, 1);
				this.placeBlockAtCurrentPosition(world, Blocks.stone_brick_stairs, stairMeta, 0, 0, 5, box);
				
				for(byte i = 1; 1 < featureSizeZ; i++) {
					this.func_151554_b(world, Blocks.stonebrick, 0, 0, -1, i, box);
				}
				
				this.fillWithMetadataBlocks(world, box, 0, 0, 1, 0, 0, featureSizeZ - 1, Blocks.stone_slab, 5, Blocks.air, 0, false);
			}
			
			//Walls
			int pillarMetaWE = this.getMetadataForRotatablePillar(4);
			int pillarMetaNS = this.getMetadataForRotatablePillar(8);
			this.fillWithBlocks(world, box, 1, 0, 0, 1, 3, 0, ModBlocks.concrete_pillar, Blocks.air, false); //Back Wall
			this.placeBlockAtCurrentPosition(world, ModBlocks.concrete, 0, 1, 4, 0, box);
			this.fillWithMetadataBlocks(world, box, 2, 4, 0, featureSizeX - 4, 4, 0, ModBlocks.concrete_pillar, pillarMetaWE, Blocks.air, 0, false);
			this.placeBlockAtCurrentPosition(world, ModBlocks.concrete, 0, featureSizeX - 3, 4, 0, box);
			this.fillWithBlocks(world, box, featureSizeX - 3, 0, 0, featureSizeX - 3, 3, 0, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 2, 0, 0, featureSizeX - 4, 1, 0, false, rand, RandomSuperConcrete);
			this.fillWithRandomizedBlocks(world, box, 2, 2, 0, 2, 2, 0, false, rand, RandomSuperConcrete);
			this.fillWithBlocks(world, box, 3, 2, 0, 5, 2, 0, ModBlocks.reinforced_glass, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, featureSizeX - 4, 2, 0, featureSizeX - 4, 2, 0, false, rand, RandomSuperConcrete);
			this.fillWithRandomizedBlocks(world, box, 2, 3, 0, featureSizeX - 4, 3, 0, false, rand, RandomSuperConcrete);
			this.fillWithMetadataBlocks(world, box, 1, 4, 1, 1, 4, featureSizeZ - 1, ModBlocks.concrete_pillar, pillarMetaNS, Blocks.air, 0, false); //Left Wall
			this.placeBlockAtCurrentPosition(world, ModBlocks.concrete, 0, 1, 4, featureSizeZ, box);
			this.fillWithBlocks(world, box, 1, 0, featureSizeZ, 1, 3, featureSizeZ, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 1, 0, 1, 1, 1, 4, false, rand, RandomSuperConcrete);
			this.fillWithRandomizedBlocks(world, box, 1, 2, 1, 1, 2, 1, false, rand, RandomSuperConcrete);
			this.fillWithBlocks(world, box, 1, 2, 2, 1, 2, 3, ModBlocks.reinforced_glass, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 1, 2, 4, 1, 2, 4, false, rand, RandomSuperConcrete);
			this.fillWithRandomizedBlocks(world, box, 1, 3, 1, 1, 3, featureSizeZ - 1, false, rand, RandomSuperConcrete);
			this.fillWithRandomizedBlocks(world, box, 1, 0, featureSizeZ - 2, 1, 3, featureSizeZ - 1, false, rand, RandomSuperConcrete);
			this.fillWithMetadataBlocks(world, box, 2, 4, featureSizeZ, featureSizeX - 4, 4, featureSizeZ, ModBlocks.concrete_pillar, pillarMetaWE, Blocks.air, 0, false); //Front Wall
			this.placeBlockAtCurrentPosition(world, ModBlocks.concrete, 0, featureSizeX - 3, 4, featureSizeZ, box);
			this.fillWithBlocks(world, box, featureSizeX - 3, 0, featureSizeZ, featureSizeX - 3, 3, featureSizeZ, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 2, 0, featureSizeZ, featureSizeX - 4, 1, featureSizeZ, false, rand, RandomSuperConcrete);
			this.fillWithRandomizedBlocks(world, box, 2, 2, featureSizeZ, 2, 2, featureSizeZ, false, rand, RandomSuperConcrete);
			this.fillWithBlocks(world, box, 3, 2, featureSizeZ, 5, 2, featureSizeZ, ModBlocks.reinforced_glass, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, featureSizeX - 4, 2, featureSizeZ, featureSizeX - 4, 2, featureSizeZ, false, rand, RandomSuperConcrete);
			this.fillWithRandomizedBlocks(world, box, 2, 3, featureSizeZ, featureSizeX - 4, 3, featureSizeZ, false, rand, RandomSuperConcrete);
			this.fillWithMetadataBlocks(world, box, featureSizeX - 3, 4, 1, featureSizeX - 3, 4, featureSizeZ - 1, ModBlocks.concrete_pillar, pillarMetaNS, Blocks.air, 0, false); //Right Wall
			this.fillWithRandomizedBlocks(world, box, featureSizeX - 3, 0, 1, featureSizeX - 3, 3, featureSizeZ - 1, false, rand, RandomSuperConcrete);
			
			pillarMetaWE = this.getMetadataForRotatablePillar(5);
			pillarMetaNS = this.getMetadataForRotatablePillar(9);
			this.fillWithMetadataBlocks(world, box, featureSizeX - 2, 2, 1, featureSizeX - 1, 2, 1, Blocks.log, pillarMetaWE, Blocks.air, 0, false); //Back Wall
			this.fillWithMetadataBlocks(world, box, featureSizeX, 0, 1, featureSizeX, 2, 1, Blocks.log, 1, Blocks.air, 0, false);
			this.fillWithMetadataBlocks(world, box, featureSizeX - 2, 0, 1, featureSizeX - 1, 1, 1, Blocks.planks, 1, Blocks.air, 0, false);
			this.fillWithMetadataBlocks(world, box, featureSizeX, 2, 2, featureSizeX, 2, 5, Blocks.log, pillarMetaNS, Blocks.air, 0, false); //Right Wall
			this.fillWithMetadataBlocks(world, box, featureSizeX, 0, 6, featureSizeX, 2, 6, Blocks.log, 1, Blocks.air, 0, false);
			this.fillWithMetadataBlocks(world, box, featureSizeX, 0, 3, featureSizeX, 1, 5, Blocks.planks, 1, Blocks.air, 0, false);
			this.fillWithMetadataBlocks(world, box, featureSizeX - 2, 2, 6, featureSizeX - 1, 2, 6, Blocks.log, pillarMetaWE, Blocks.air, 0, false); //Front Wall
			this.fillWithMetadataBlocks(world, box, featureSizeX - 2, 0, 6, featureSizeX - 1, 1, 6, Blocks.planks, 1, Blocks.air, 0, false);
			
			//Floor & Ceiling
			this.fillWithBlocks(world, box, 2, 0, 1, 6, 0, featureSizeZ - 1, ModBlocks.brick_light, Blocks.air, false); //Floor
			this.placeBlockAtCurrentPosition(world, ModBlocks.brick_light, 0, 1, 0, 5, box);
			this.fillWithRandomizedBlocks(world, box, 2, 4, 1, 6, 4, 3, false, rand, RandomSuperConcrete); //Ceiling
			this.fillWithRandomizedBlocks(world, box, 2, 4, 4, 2, 4, 4, false, rand, RandomSuperConcrete);
			this.fillWithRandomizedBlocks(world, box, 5, 4, 4, 6, 4, 4, false, rand, RandomSuperConcrete);
			this.fillWithRandomizedBlocks(world, box, 2, 4, featureSizeZ - 3, 6, 4, featureSizeZ - 1, false, rand, RandomSuperConcrete);
			
			this.fillWithBlocks(world, box, featureSizeX - 2, 2, 2, featureSizeX - 1, 2, 5, ModBlocks.deco_steel, Blocks.air, false);
			
			//Loot & Decorations
			int southMeta = this.getMetadataForRotatableDeco(2);
			int eastMeta = this.getMetadataForRotatableDeco(5);
			this.placeBlockAtCurrentPosition(world, ModBlocks.pole_satellite_receiver, eastMeta, 2, featureSizeY - 1, 1, box);
			this.fillWithBlocks(world, box, 3, featureSizeY - 1, 1, 4, featureSizeY - 1, 1, ModBlocks.deco_steel, Blocks.air, false);
			this.fillWithBlocks(world, box, 2, featureSizeY - 1, 2, 4, featureSizeY - 1, 2, ModBlocks.deco_steel, Blocks.air, false);
			this.fillWithBlocks(world, box, 2, featureSizeY, 1, 4, featureSizeY, 2, ModBlocks.steel_roof, Blocks.air, false);
			this.fillWithBlocks(world, box, 2, 1, 1, 2, 3, 1, ModBlocks.deco_red_copper, Blocks.air, false);
			this.fillWithBlocks(world, box, 3, 1, 1, 3, 1, 2, ModBlocks.deco_beryllium, Blocks.air, false);
			this.placeBlockAtCurrentPosition(world, ModBlocks.machine_generator, 0, 4, 1, 1, box);
			this.placeBlockAtCurrentPosition(world, ModBlocks.machine_detector, 0, 4, 1, 2, box);
			this.fillWithBlocks(world, box, 5, 1, 1, 5, 1, 2, ModBlocks.deco_beryllium, Blocks.air, false);
			this.fillWithBlocks(world, box, 6, 1, 1, 6, 3, 1, ModBlocks.deco_red_copper, Blocks.air, false);
			this.fillWithBlocks(world, box, 3, 1, 4, 4, 1, 4, ModBlocks.concrete_super_broken, Blocks.air, false);
			this.fillWithMetadataBlocks(world, box, 6, 1, 4, 6, 3, 4, ModBlocks.steel_scaffold, eastMeta, Blocks.air, 0, false);
			this.fillWithMetadataBlocks(world, box, 6, 1, 5, 6, 1, 7, ModBlocks.steel_grate, 7, Blocks.air, 0, false);
			this.placeBlockAtCurrentPosition(world, ModBlocks.radiorec, eastMeta, 6, 2, featureSizeZ - 1, box);
			this.fillWithMetadataBlocks(world, box, 2, 1, featureSizeZ - 1, 3, 1, featureSizeZ - 1, ModBlocks.machine_electric_furnace_off, southMeta, Blocks.air, 0, false);
			if(!hasPlacedLoot) {
				this.hasPlacedLoot = this.generateIronCrateContents(world, box, rand, 4, 1, featureSizeZ - 1, HbmChestContents.machineParts, 11);
			}
			this.placeBlockAtCurrentPosition(world, Blocks.web, 0, 5, 3, 1, box);
			this.placeBlockAtCurrentPosition(world, Blocks.web, 0, 2, 1, 2, box);
			this.placeBlockAtCurrentPosition(world, Blocks.web, 0, 6, 1, 2, box);
			this.placeBlockAtCurrentPosition(world, Blocks.web, 0, 6, 2, 5, box);
			
			this.fillWithMetadataBlocks(world, box, featureSizeX - 2, 0, 5, featureSizeX - 1, 0, 5, ModBlocks.steel_grate, 7, Blocks.air, 0, false);
			this.placeBlockAtCurrentPosition(world, ModBlocks.tape_recorder, southMeta, featureSizeX - 2, 1, 5, box);
			this.placeBlockAtCurrentPosition(world, ModBlocks.bobblehead, rand.nextInt(16), featureSizeX - 1, 1, 5, box);
			TileEntityBobble bobble = (TileEntityBobble) world.getTileEntity(this.getXWithOffset(featureSizeX - 1, 5), this.getYWithOffset(1), this.getZWithOffset(featureSizeX - 1, 5));
			
			if(bobble != null) {
				bobble.type = BobbleType.values()[rand.nextInt(BobbleType.values().length - 1) + 1];
				bobble.markDirty();
			}
			this.fillWithMetadataBlocks(world, box, featureSizeX - 2, 0, 2, featureSizeX - 2, 0, 3, Blocks.log, pillarMetaWE, Blocks.air, 0, false);
			this.placeBlockAtCurrentPosition(world, Blocks.log, pillarMetaWE, featureSizeX - 2, 1, 2, box);
			this.placeBlockAtCurrentPosition(world, Blocks.web, 0, featureSizeX - 2, 1, 3, box);
			
			return true;
		}		
	}
	
	public static class NTMRuin1 extends ComponentNTMFeatures.Feature {
		
		private static ComponentNTMFeatures.ConcreteBricks RandomConcreteBricks = new ComponentNTMFeatures.ConcreteBricks();
		
		public NTMRuin1() {
			super();
		}
		
		protected NTMRuin1(Random rand, int minX, int minY, int minZ) {
			super(rand, minX, minY, minZ, 8, 6, 10);
		}

		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			//System.out.println(this.coordBaseMode);
			if(!this.func_74935_a(world, box, this.boundingBox.minY)) {
				return false;
			}
			//System.out.println("" + this.boundingBox.minX + ", " + this.boundingBox.minY + ", " + this.boundingBox.minZ);
			
			for(byte i = 0; i < featureSizeX + 1; i++) {
				for(byte j = 0; j < featureSizeZ + 1; j++) {
					this.func_151554_b(world, Blocks.stonebrick, 0, i, -1, j, box);
				}
			}
			
			int pillarMetaWE = this.getMetadataForRotatablePillar(4);
			int pillarMetaNS = this.getMetadataForRotatablePillar(8);
			
			this.fillWithBlocks(world, box, 0, 0, 0, 0, featureSizeY, 0, ModBlocks.concrete_pillar, Blocks.air, false); //Back Wall
			this.fillWithMetadataBlocks(world, box, 1, 3, 0, 3, 3, 0, ModBlocks.concrete_pillar, pillarMetaWE, Blocks.air, 0, false);
			this.fillWithBlocks(world, box, 4, 0, 0, 4, featureSizeY - 1, 0, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithMetadataBlocks(world, box, 5, 3, 0, featureSizeX - 1, 3, 0, ModBlocks.concrete_pillar, pillarMetaWE, Blocks.air, 0, false);
			this.fillWithBlocks(world, box, featureSizeX, 0, 0, featureSizeX, featureSizeY - 1, 0, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 1, 0, 0, 3, 0, 0, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 5, 0, 0, featureSizeX - 1, 0, 0, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 1, 1, 0, 1, 2, 0, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 3, 1, 0, 3, 2, 0, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 5, 1, 0, 5, 2, 0, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, featureSizeX - 1, 1, 0, featureSizeX - 1, 2, 0, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 1, 4, 0, 3, 4, 0, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 5, 4, 0, featureSizeX - 1, 4, 0, false, rand, RandomConcreteBricks);
			this.fillWithMetadataBlocks(world, box, 0, 3, 1, 0, 3, featureSizeZ - 1, ModBlocks.concrete_pillar, pillarMetaNS, Blocks.air, 0, false); //Left Wall
			this.fillWithBlocks(world, box, 0, 0, featureSizeZ, 0, featureSizeY - 1, featureSizeZ, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 0, 0, 1, 0, 0, featureSizeZ - 1, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 0, 1, 1, 0, 2, 2, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 0, 1, 4, 0, 2, 6, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 0, 1, featureSizeZ - 2, 0, 2, featureSizeZ - 1, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 0, 4, 1, 0, 4, 5, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 0, 5, 1, 0, 5, 2, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 0, 4, featureSizeZ - 2, 0, 4, featureSizeZ - 1, false, rand, RandomConcreteBricks);
			this.fillWithMetadataBlocks(world, box, 1, 3, featureSizeZ, 3, 3, featureSizeZ, ModBlocks.concrete_pillar, pillarMetaWE, Blocks.air, 0, false); //Front Wall
			this.fillWithBlocks(world, box, 4, 0, featureSizeZ, 4, featureSizeY - 2, featureSizeZ, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithMetadataBlocks(world, box, 5, 3, featureSizeZ, featureSizeX - 1, 3, featureSizeZ, ModBlocks.concrete_pillar, pillarMetaWE, Blocks.air, 0, false);
			this.fillWithBlocks(world, box, featureSizeX, 0, featureSizeZ, featureSizeX, featureSizeY - 2, featureSizeZ, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 1, 0, featureSizeZ, 3, 0, featureSizeZ, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 5, 0, featureSizeZ, featureSizeX - 1, 0, featureSizeZ, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 1, 1, featureSizeZ, 1, 2, featureSizeZ, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 3, 1, featureSizeZ, 3, 2, featureSizeZ, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 5, 1, featureSizeZ, 5, 2, featureSizeZ, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, featureSizeX - 1, 1, featureSizeZ, featureSizeX - 1, 2, featureSizeZ, false, rand, RandomConcreteBricks);
			this.fillWithMetadataBlocks(world, box, featureSizeX, 3, 1, featureSizeX, 3, 2, ModBlocks.concrete_pillar, pillarMetaNS, Blocks.air, 0, false); //Right Wall
			this.fillWithMetadataBlocks(world, box, featureSizeX, 3, featureSizeZ - 1, featureSizeX, 3, featureSizeZ - 1, ModBlocks.concrete_pillar, pillarMetaNS, Blocks.air, 0, false);
			this.fillWithRandomizedBlocks(world, box, featureSizeX, 0, 1, featureSizeX, 0, 4, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, featureSizeX, 1, 1, featureSizeX, 2, 2, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, featureSizeX, 0, 6, featureSizeX, 0, 6, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, featureSizeX, 0, featureSizeZ - 2, featureSizeX, 1, featureSizeZ - 1, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, featureSizeX, 2, featureSizeZ - 1, featureSizeX, 2, featureSizeZ - 1, false, rand, RandomConcreteBricks);
			
			this.randomlyFillWithBlocks(world, box, rand, 0.25F, 1, 0, 1, featureSizeX - 1, 0, featureSizeZ - 1, Blocks.gravel, Blocks.air, false);
			
			return true;
		}
	}
	
	public static class NTMRuin2 extends ComponentNTMFeatures.Feature {
		
		private static ComponentNTMFeatures.ConcreteBricks RandomConcreteBricks = new ComponentNTMFeatures.ConcreteBricks();
		
		public NTMRuin2() {
			super();
		}
		
		protected NTMRuin2(Random rand, int minX, int minY, int minZ) {
			super(rand, minX, minY, minZ, 7, 5, 10);
		}

		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			//System.out.println(this.coordBaseMode);
			if(!this.func_74935_a(world, box, this.boundingBox.minY)) {
				return false;
			}
			//System.out.println("" + this.boundingBox.minX + ", " + this.boundingBox.minY + ", " + this.boundingBox.minZ);
			
			for(byte i = 0; i < featureSizeX + 1; i++) {
				for(byte j = 0; j < featureSizeZ + 1; j++) {
					this.func_151554_b(world, Blocks.stonebrick, 0, i, -1, j, box);
				}
			}
			
			int pillarMetaWE = this.getMetadataForRotatablePillar(4);
			int pillarMetaNS = this.getMetadataForRotatablePillar(8);
			
			this.fillWithBlocks(world, box, 0, 0, 0, 0, 3, 0, ModBlocks.concrete_pillar, Blocks.air, false); //Back Wall
			this.fillWithMetadataBlocks(world, box, 1, 3, 0, featureSizeX - 1, 3, 0, ModBlocks.concrete_pillar, pillarMetaWE, Blocks.air, 0, false);
			this.fillWithBlocks(world, box, featureSizeX, 0, 0, featureSizeX, featureSizeY, 0, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 1, 0, 0, featureSizeX - 1, 0, 0, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 1, 1, 0, 1, 2, 0, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 3, 1, 0, 4, 2, 0, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, featureSizeX - 1, 1, 0, featureSizeX - 1, 2, 0, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 3, 4, 0, featureSizeX - 1, 4, 0, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, featureSizeX - 1, featureSizeY, 0, featureSizeX - 1, featureSizeY, 0, false, rand, RandomConcreteBricks);
			this.fillWithMetadataBlocks(world, box, 0, 3, 1, 0, 3, 4, ModBlocks.concrete_pillar, pillarMetaNS, Blocks.air, 0, false); //Left Wall
			this.fillWithBlocks(world, box, 0, 0, 5, 0, 0, 5, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithBlocks(world, box, 0, 0, featureSizeZ, 0, 2, featureSizeZ, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 0, 0, 1, 0, 2, 3, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 0, 0, featureSizeZ - 3, 0, 0, featureSizeZ - 1, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 0, 1, featureSizeZ - 1, 0, 1, featureSizeZ - 1, false, rand, RandomConcreteBricks);
			this.fillWithMetadataBlocks(world, box, featureSizeX - 1, 3, featureSizeZ, featureSizeX - 1, 3, featureSizeZ, ModBlocks.concrete_pillar, pillarMetaWE, Blocks.air, 0, false); //Front Wall
			this.fillWithBlocks(world, box, featureSizeX, 0, featureSizeZ, featureSizeX, 3, featureSizeZ, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 1, 0, featureSizeZ, featureSizeX - 1, 0, featureSizeZ, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 1, 1, featureSizeZ, 1, 2, featureSizeZ, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, featureSizeX - 1, 1, featureSizeZ, featureSizeX - 1, 2, featureSizeZ, false, rand, RandomConcreteBricks);
			this.fillWithMetadataBlocks(world, box, featureSizeX, 3, 1, featureSizeX, 3, 4, ModBlocks.concrete_pillar, pillarMetaNS, Blocks.air, 0, false); //Right Wall
			this.fillWithBlocks(world, box, featureSizeX, 0, 5, featureSizeX, 4, 5, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithMetadataBlocks(world, box, featureSizeX, 3, featureSizeZ - 2, featureSizeX, 3, featureSizeZ - 1, ModBlocks.concrete_pillar, pillarMetaNS, Blocks.air, 0, false);
			this.fillWithRandomizedBlocks(world, box, featureSizeX, 0, 1, featureSizeX, 0, 4, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, featureSizeX, 1, 1, featureSizeX, 2, 1, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, featureSizeX, 1, 3, featureSizeX, 2, 3, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, featureSizeX, 1, 4, featureSizeX, 1, 4, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, featureSizeX, 0, 6, featureSizeX, 0, featureSizeZ - 1, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, featureSizeX, 1, 6, featureSizeX, 1, 7, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, featureSizeX, 1, featureSizeZ - 1, featureSizeX, 2, featureSizeZ - 1, false, rand, RandomConcreteBricks);
			
			this.randomlyFillWithBlocks(world, box, rand, 0.25F, 1, 0, 1, featureSizeX - 1, 0, featureSizeZ - 1, Blocks.gravel, Blocks.air, false);
			
			return true;
		}
	}
	
	public static class NTMRuin3 extends ComponentNTMFeatures.Feature {
		
		private static ComponentNTMFeatures.ConcreteBricks RandomConcreteBricks = new ComponentNTMFeatures.ConcreteBricks();
		
		public NTMRuin3() {
			super();
		}
		
		protected NTMRuin3(Random rand, int minX, int minY, int minZ) {
			super(rand, minX, minY, minZ, 8, 3, 10);
		}

		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			//System.out.println(this.coordBaseMode);
			if(!this.func_74935_a(world, box, this.boundingBox.minY)) {
				return false;
			}
			//System.out.println("" + this.boundingBox.minX + ", " + this.boundingBox.minY + ", " + this.boundingBox.minZ);
			
			for(byte i = 0; i < featureSizeZ + 1; i++) {
					this.func_151554_b(world, Blocks.stonebrick, 0, 0, -1, i, box);
					this.func_151554_b(world, Blocks.stonebrick, 0, featureSizeX, -1, i, box);
			}
			
			for(byte i = 1; i < featureSizeX; i++) {
				this.func_151554_b(world, Blocks.stonebrick, 0, i, -1, 0, box);
				this.func_151554_b(world, Blocks.stonebrick, 0, i, -1, 4, box);
			}
			
			this.fillWithBlocks(world, box, 0, 0, 0, 0, featureSizeY, 0, ModBlocks.concrete_pillar, Blocks.air, false); //Back Wall
			this.fillWithBlocks(world, box, featureSizeX, 0, 0, featureSizeX, 1, 0, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 1, 0, 0, featureSizeX - 1, 0, 0, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 1, 1, 0, 1, 1, 0, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 4, 1, 0, 4, 1, 0, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, featureSizeX - 1, 1, 0, featureSizeX - 1, 1, 0, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 1, 2, 0, featureSizeX - 2, 2, 0, false, rand, RandomConcreteBricks);
			this.fillWithBlocks(world, box, 0, 0, 4, 0, 1, 4, ModBlocks.concrete_pillar, Blocks.air, false); //Left Wall
			this.placeBlockAtCurrentPosition(world, ModBlocks.concrete_pillar, 0, 0, 0, featureSizeZ, box);
			this.fillWithRandomizedBlocks(world, box, 0, 0, 1, 0, 0, 3, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 0, 0, 5, 0, 0, featureSizeZ - 1, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 0, 1, 5, 0, 1, 5, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 0, 1, 7, 0, 1, 7, false, rand, RandomConcreteBricks);
			this.fillWithBlocks(world, box, featureSizeX, 0, 4, featureSizeX, 1, 4, ModBlocks.concrete_pillar, Blocks.air, false); //Right Wall
			this.fillWithBlocks(world, box, featureSizeX, 0, featureSizeZ, featureSizeX, 1, featureSizeZ, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, featureSizeX, 0, 1, featureSizeX, 1, 3, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, featureSizeX, 0, 5, featureSizeX, 0, 6, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, featureSizeX, 0, featureSizeZ - 1, featureSizeX, 0, featureSizeZ - 1, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, featureSizeX - 1, 0, featureSizeZ, featureSizeX - 1, 0, featureSizeZ, false, rand, RandomConcreteBricks);
			this.fillWithBlocks(world, box, 4, 0, 4, 4, 2, 4, ModBlocks.concrete_pillar, Blocks.air, false); //Center Wall
			this.fillWithRandomizedBlocks(world, box, 3, 0, 4, 3, 1, 4, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 5, 0, 4, featureSizeX - 1, 1, 4, false, rand, RandomConcreteBricks);
			
			this.randomlyFillWithBlocks(world, box, rand, 0.05F, 1, 0, 1, featureSizeX - 1, 0, 3, Blocks.gravel, Blocks.air, false);
			this.randomlyFillWithBlocks(world, box, rand, 0.05F, 1, 0, 5, featureSizeX - 1, 0, featureSizeZ - 1, Blocks.gravel, Blocks.air, false);
			
			return true;
		}
	}
	
	public static class NTMRuin4 extends ComponentNTMFeatures.Feature {
		
		private static ComponentNTMFeatures.ConcreteBricks RandomConcreteBricks = new ComponentNTMFeatures.ConcreteBricks();
		
		public NTMRuin4() {
			super();
		}
		
		protected NTMRuin4(Random rand, int minX, int minY, int minZ) {
			super(rand, minX, minY, minZ, 10, 2, 11);
		}

		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			//System.out.println(this.coordBaseMode);
			if(!this.func_74935_a(world, box, this.boundingBox.minY)) {
				return false;
			}
			//System.out.println("" + this.boundingBox.minX + ", " + this.boundingBox.minY + ", " + this.boundingBox.minZ);
			
			for(byte i = 0; i < featureSizeZ + 1; i++) {
				this.func_151554_b(world, Blocks.stonebrick, 0, 0, -1, i, box);
				this.func_151554_b(world, Blocks.stonebrick, 0, i >= 5 ? featureSizeX : 5, -1, i, box); //elegant solution
			}
			
			for(byte i = 1; i < featureSizeX; i++) {
				this.func_151554_b(world, Blocks.stonebrick, 0, i, -1, featureSizeZ, box);
				this.func_151554_b(world, Blocks.stonebrick, 0, i, -1, i > 4 ? 5 : 0, box); //ternary operators my beloved
			}
			
			this.fillWithBlocks(world, box, 0, 0, 0, 0, 1, 0, ModBlocks.concrete_pillar, Blocks.air, false); //Back Wall Pt. 1
			this.fillWithBlocks(world, box, 5, 0, 0, 5, featureSizeY, 0, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 1, 0, 0, 4, 0, 0, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 4, 1, 0, 4, 1, 0, false, rand, RandomConcreteBricks);
			this.fillWithBlocks(world, box, 5, 0, 5, 5, featureSizeY, 5, ModBlocks.concrete_pillar, Blocks.air, false); //Right Wall Pt. 1
			this.fillWithRandomizedBlocks(world, box, 5, 0, 1, 5, 0, 4, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 5, 1, 1, 5, 1, 1, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 5, 1, 4, 5, 1, 4, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 5, 2, 1, 5, 2, 4, false, rand, RandomConcreteBricks);
			this.fillWithBlocks(world, box, featureSizeX, 0, 5, featureSizeX, 1, 5, ModBlocks.concrete_pillar, Blocks.air, false); //Back Wall Pt. 2
			this.fillWithRandomizedBlocks(world, box, 6, 0, 5, featureSizeX - 1, 0, 5, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 6, 1, 5, 6, 1, 5, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, featureSizeX - 1, 1, 5, featureSizeX + 1, 1, 5, false, rand, RandomConcreteBricks);
			this.fillWithBlocks(world, box, featureSizeX, 0, featureSizeZ, featureSizeX, 1, featureSizeZ, ModBlocks.concrete_pillar, Blocks.air, false); //Right Wall Pt. 2
			this.fillWithRandomizedBlocks(world, box, featureSizeX, 0, 6, featureSizeX, 0, featureSizeZ - 1, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, featureSizeX, 1, 6, featureSizeX, 1, featureSizeZ - 3, false, rand, RandomConcreteBricks);
			this.fillWithBlocks(world, box, 0, 0, featureSizeZ, 0, 0, featureSizeZ, ModBlocks.concrete_pillar, Blocks.air, false); //Front Wall
			this.fillWithRandomizedBlocks(world, box, 1, 0, featureSizeZ, 1, 0, featureSizeZ, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 6, 0, featureSizeZ, 7, 0, featureSizeZ, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, featureSizeX - 1, 0, featureSizeZ, featureSizeX - 1, 0, featureSizeZ, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 0, 0, 1, 0, 0, featureSizeZ - 1, false, rand, RandomConcreteBricks); //Left Wall
			this.fillWithRandomizedBlocks(world, box, 0, 1, 1, 0, 1, 1, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 0, 1, 4, 0, 1, 7, false, rand, RandomConcreteBricks);
			
			this.randomlyFillWithBlocks(world, box, rand, 0.05F, 1, 0, 1, 4, 0, 5, Blocks.gravel, Blocks.air, false);
			this.randomlyFillWithBlocks(world, box, rand, 0.05F, 1, 0, 6, featureSizeX - 1, 0, featureSizeZ - 1, Blocks.gravel, Blocks.air, false);
			
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
		
		
		protected Feature() {
			super(0);
		}
		
		protected Feature(Random rand, int minX, int minY, int minZ, int maxX, int maxY, int maxZ ) {
			super(0);
			this.featureSizeX = maxX;
			this.featureSizeY = maxY;
			this.featureSizeZ = maxZ;
			this.coordBaseMode = rand.nextInt(4);
			
			switch(this.coordBaseMode) {
			case 0:
				this.boundingBox = new StructureBoundingBox(minX, minY, minZ, minX + maxX, minY + maxY, minZ + maxZ);
				break;
			case 1:
				this.boundingBox = new StructureBoundingBox(minX, minY, minZ, minX + maxZ, minY + maxY, minZ + maxX);
				break;
			case 2:
				//North (2) and East (3) will result in mirrored structures. Not an issue, but keep in mind.
				this.boundingBox = new StructureBoundingBox(minX, minY, minZ, minX + maxX, minY + maxY, minZ + maxZ);
				break;
			case 3:
				this.boundingBox = new StructureBoundingBox(minX, minY, minZ, minX + maxX, minY + maxY, minZ + maxZ);
				break;
			default:
				this.boundingBox = new StructureBoundingBox(minX, minY, minZ, minX + maxX, minY + maxY, minZ + maxZ);
				
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
			return true;
		}
		
		/**
		 * Gets metadata for rotatable pillars.
		 * @param metadata (First two digits are equal to block metadata, other two are equal to orientation
		 * @return metadata adjusted for random orientation
		 */
		protected int getMetadataForRotatablePillar(int metadata) {
			int blockMeta = metadata & 3;
			int rotationMeta =  metadata >> 2;
			
			if(rotationMeta == 0)
				return metadata;
			
			if(this.coordBaseMode == 0 || this.coordBaseMode == 2) { //North & South
				switch(rotationMeta) {
				case 1:
					rotationMeta = 4;
					break;
				case 2:
					rotationMeta = 8;
					break;
				}
			} else if(this.coordBaseMode == 1 || this.coordBaseMode == 3) { //East & West
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
		
		/**
		 * Gets metadata for rotatable DecoBlock
		 * @param metadata (2 for facing South, 3 for facing North, 4 for facing East, 5 for facing West
		 * @return metadata adjusted for random orientation
		 */
		protected int getMetadataForRotatableDeco(int metadata) {
			switch(this.coordBaseMode) {
			case 0: //South
				switch(metadata) {
				case 2:
					return 2;
				case 3:
					return 3;
				case 4:
					return 4;
				case 5:
					return 5;
				}
			case 1: //West
				switch(metadata) {
				case 2:
					return 5;
				case 3:
					return 4;
				case 4:
					return 2;
				case 5:
					return 3;
				}
			case 2: //North
				switch(metadata) {
				case 2:
					return 3;
				case 3:
					return 2;
				case 4:
					return 4;
				case 5:
					return 5;
				}
			case 3: //East
				switch(metadata) {
				case 2:
					return 4;
				case 3:
					return 5;
				case 4:
					return 2;
				case 5:
					return 3;
				}
			}
			return 0;
		}
		
		/**
		 * it feels disgusting to make a method with this many parameters but fuck it, it's easier
		 * @return iron crate with generated content
		 */
		protected boolean generateIronCrateContents(World world, StructureBoundingBox box, Random rand, int featureX, int featureY, int featureZ, WeightedRandomChestContent[] content, int amount) {
			int posX = this.getXWithOffset(featureX, featureZ);
			int posY = this.getYWithOffset(featureY);
			int posZ = this.getZWithOffset(featureX, featureZ);
			
			this.placeBlockAtCurrentPosition(world, ModBlocks.crate_iron, 0, featureX, featureY, featureZ, box);
			TileEntityCrateIron crate = (TileEntityCrateIron)world.getTileEntity(posX, posY, posZ);
			
			if(crate != null) {
				WeightedRandomChestContent.generateChestContents(rand, content, crate, amount);
				return true;
			}
			
			return false;
		}
	}
	
	//Block Selectors
	
	static class Sandstone extends StructureComponent.BlockSelector {
		
		Sandstone() { }
		
		/** Selects blocks */
		@Override
		public void selectBlocks(Random rand, int posX, int posY, int posZ, boolean p_75062_5_) {
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
		public void selectBlocks(Random rand, int posX, int posY, int posZ, boolean p_75062_5_) {
			float chance = rand.nextFloat();
			
			if(chance < 0.2F) {
				this.field_151562_a = ModBlocks.brick_concrete;
			} else if (chance < 0.55F) {
				this.field_151562_a = ModBlocks.brick_concrete_mossy;
			} else if (chance < 0.75F) {
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
		public void selectBlocks(Random rand, int posX, int posY, int posZ, boolean p_75062_5_) {
			float chance = rand.nextFloat();
			
			if(chance < 0.5F) {
				this.field_151562_a = ModBlocks.tile_lab;
			} else if (chance < 0.9F) {
				this.field_151562_a = ModBlocks.tile_lab_cracked;
			} else {
				this.field_151562_a = ModBlocks.tile_lab_broken;
			}
		}
	}
	
	static class SuperConcrete extends StructureComponent.BlockSelector {
		
		SuperConcrete() {
			this.field_151562_a = ModBlocks.concrete_super;
		}
		
		/** Selects blocks */
		@Override
		public void selectBlocks(Random rand, int posX, int posY, int posZ, boolean p_75062_5_) {
			this.selectedBlockMetaData = rand.nextInt(6) + 10;
			
			
		}
	}
}

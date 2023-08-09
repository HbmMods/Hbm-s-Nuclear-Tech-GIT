package com.hbm.world.gen.component;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockBobble.BobbleType;
import com.hbm.blocks.generic.BlockBobble.TileEntityBobble;
import com.hbm.lib.HbmChestContents;
import com.hbm.util.LootGenerator;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemDoor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;

/* Described as "Civilian", as that's the overarching connection between all of these structures. Unlike the ruins, there's not enough to 
 * compartmentalize even further. Just in general many of the structures I consider lower-quality (except for the sandstone houses; those are actually pretty nice).
 */
public class CivilianFeatures {
	
	public static void registerComponents() {
		MapGenStructureIO.func_143031_a(NTMHouse1.class, "NTMHouse1"); 
		MapGenStructureIO.func_143031_a(NTMHouse2.class, "NTMHouse2"); 
		MapGenStructureIO.func_143031_a(NTMLab1.class, "NTMLab1"); 
		MapGenStructureIO.func_143031_a(NTMLab2.class, "NTMLab2"); 
		MapGenStructureIO.func_143031_a(NTMWorkshop1.class, "NTMWorkshop1"); 
	}
	
	/** Sandstone Ruin 1 */
	public static class NTMHouse1 extends Component {
		
		private boolean hasPlacedChest;
		
		private static Sandstone RandomSandstone = new Sandstone();
		
		public NTMHouse1() {
			super();
		}
		
		/** Constructor for this feature; takes coordinates for bounding box */
		public NTMHouse1(Random rand, int minX, int minY, int minZ) {
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
			if(!this.setAverageHeight(world, box, this.boundingBox.minY)) {
				return false;
			}
			//System.out.println("" + this.boundingBox.minX + ", " + this.boundingBox.minY + ", " + this.boundingBox.minZ);
			
			placeFoundationUnderneath(world, Blocks.sandstone, 0, 0, 0, 9, 6, -1, box);
			
			//Walls
			this.fillWithRandomizedBlocks(world, box, 0, 0, 0, 9, 0, 0, false, rand, RandomSandstone); //Back Wall
			this.fillWithRandomizedBlocks(world, box, 0, 1, 0, 1, 1, 0, false, rand, RandomSandstone);
			this.placeBlockAtCurrentPosition(world, Blocks.fence, 0, 2, 1, 0, box);
			this.fillWithRandomizedBlocks(world, box, 3, 1, 0, 5, 1, 0, false, rand, RandomSandstone);
			this.placeBlockAtCurrentPosition(world, Blocks.fence, 0, 6, 1, 0, box);
			this.placeBlockAtCurrentPosition(world, Blocks.fence, 0, 7, 1, 0, box);
			this.fillWithRandomizedBlocks(world, box, 9 - 1, 1, 0, 9, 1, 0, false, rand, RandomSandstone);
			this.fillWithRandomizedBlocks(world, box, 0, 2, 0, 9 - 2, 2, 0, false, rand, RandomSandstone);
			this.fillWithRandomizedBlocks(world, box, 0, 0, 0, 0, 1, 6, false, rand, RandomSandstone); //Left Wall
			this.placeBlockAtCurrentPosition(world, Blocks.stone_slab, 1, 0, 2, 1, box);
			this.fillWithMetadataBlocks(world, box, 0, 2, 3, 0, 2, 6, Blocks.stone_slab, 1, Blocks.air, 0, false);
			this.fillWithRandomizedBlocks(world, box, 1, 0, 6, 1, 1, 6, false, rand, RandomSandstone); //Front Wall
			this.fillWithRandomizedBlocks(world, box, 3, 0, 6, 9, 1, 6, false, rand, RandomSandstone);
			this.fillWithRandomizedBlocks(world, box, 1, 2, 6, 3, 2, 6, false, rand, RandomSandstone);
			this.fillWithMetadataBlocks(world, box, 4, 2, 6, 5, 2, 6, Blocks.stone_slab, 1, Blocks.air, 0, false);
			this.placeBlockAtCurrentPosition(world, Blocks.stone_slab, 1, 9 - 2, 2, 6, box);
			this.fillWithRandomizedBlocks(world, box, 9, 0, 0, 9, 0, 6, false, rand, RandomSandstone); //Right Wall
			this.randomlyFillWithBlocks(world, box, rand, 0.65F, 9, 1, 1, 9, 1, 6 - 1, Blocks.sand, Blocks.air, false);
			
			this.fillWithRandomizedBlocks(world, box, 4, 0, 1, 4, 1, 3, false, rand, RandomSandstone);
			this.placeBlockAtCurrentPosition(world, ModBlocks.reinforced_sand, 0, 4, 0, 4, box);
			
			//Loot/Sand
			this.placeBlockAtCurrentPosition(world, ModBlocks.crate_weapon, 0, 1, 0, 1, box);
			if(!this.hasPlacedChest)
				this.hasPlacedChest = this.generateStructureChestContents(world, box, rand, 3, 0, 1, HbmChestContents.modGeneric, rand.nextInt(2) + 8);
			this.fillWithBlocks(world, box, 5, 0, 1, 6, 0, 1, ModBlocks.crate, Blocks.air, false);
			this.placeBlockAtCurrentPosition(world, Blocks.sand, 0, 7, 0, 1, box);
			if(rand.nextFloat() <= 0.25)
				 this.placeBlockAtCurrentPosition(world, ModBlocks.crate_metal, 0, 9 - 1, 0, 1, box);
			this.randomlyFillWithBlocks(world, box, rand, 0.25F, 1, 0, 2, 3, 0, 6 - 1, Blocks.sand, Blocks.air, false);
			this.randomlyFillWithBlocks(world, box, rand, 0.25F, 5, 0, 2, 9 - 1, 0, 6 - 1, Blocks.sand, Blocks.air, false);
			
			return true;
		}
		
	}
	
	public static class NTMHouse2 extends Component {
		
		private static Sandstone RandomSandstone = new Sandstone();
		
		private boolean[] hasPlacedLoot = new boolean[2];
		
		public NTMHouse2() {
			super();
		}
		
		public NTMHouse2(Random rand, int minX, int minY, int minZ) {
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
			if(!this.setAverageHeight(world, box, this.boundingBox.minY)) {
				return false;
			}
			//System.out.println("" + this.boundingBox.minX + ", " + this.boundingBox.minY + ", " + this.boundingBox.minZ);
			
			placeFoundationUnderneath(world, Blocks.sandstone, 0, 0, 0, 6, 15, -1, box);
			placeFoundationUnderneath(world, Blocks.sandstone, 0, 9, 0, 15, 9, -1, box);
			
			this.fillWithAir(world, box, 1, 0, 1, 5, 5, 9 - 1);
			
			//House 1
			this.fillWithRandomizedBlocks(world, box, 0, 0, 0, 6, 1, 0, false, rand, RandomSandstone); //Back Wall
			this.fillWithRandomizedBlocks(world, box, 0, 2, 0, 1, 2, 0, false, rand, RandomSandstone);
			this.placeBlockAtCurrentPosition(world, Blocks.fence, 0, 2, 2, 0, box);
			this.fillWithRandomizedBlocks(world, box, 3, 2, 0, 3, 2, 0, false, rand, RandomSandstone);
			this.placeBlockAtCurrentPosition(world, Blocks.fence, 0, 4, 2, 0, box);
			this.fillWithRandomizedBlocks(world, box, 5, 2, 0, 6, 2, 0, false, rand, RandomSandstone);
			this.fillWithRandomizedBlocks(world, box, 0, 3, 0, 6, 3, 0, false, rand, RandomSandstone);
			this.fillWithRandomizedBlocks(world, box, 0, 0, 1, 0, 3, 9, false, rand, RandomSandstone); //Left Wall
			this.fillWithRandomizedBlocks(world, box, 1, 0, 9, 6, 1, 9, false, rand, RandomSandstone); //Front Wall
			this.fillWithRandomizedBlocks(world, box, 1, 2, 9, 1, 2, 9, false, rand, RandomSandstone);
			this.fillWithBlocks(world, box, 2, 2, 9, 4, 2, 9, Blocks.fence, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 5, 2, 9, 6, 2, 9, false, rand, RandomSandstone);
			this.fillWithRandomizedBlocks(world, box, 1, 3, 9, 6, 3, 9, false, rand, RandomSandstone);
			this.fillWithRandomizedBlocks(world, box, 6, 0, 9 - 1, 6, 3, 9 - 1, false, rand, RandomSandstone); //Right Wall
			this.fillWithRandomizedBlocks(world, box, 6, 0, 9 - 2, 6, 0, 9 - 2, false, rand, RandomSandstone);
			this.fillWithRandomizedBlocks(world, box, 6, 3, 9 - 2, 6, 3, 9 - 2, false, rand, RandomSandstone);
			this.fillWithRandomizedBlocks(world, box, 6, 0, 1, 6, 3, 9 - 3, false, rand, RandomSandstone);
			
			this.fillWithBlocks(world, box, 1, 0, 1, 5, 0, 9 - 1, Blocks.sandstone, Blocks.air, false); //Floor
			//this.fillWithRandomizedBlocks(world, box, 1, 5 - 1, 0, 5, 5 - 1, 9, false, rand, RandomSandstone); //Ceiling
			this.fillWithBlocks(world, box, 1, 5 - 1, 0, 5, 5 - 1, 9, Blocks.sandstone, Blocks.air, false);
			this.fillWithMetadataBlocks(world, box, 0, 5 - 1, 0, 0, 5 - 1, 9, Blocks.stone_slab, 1, Blocks.air, 0, false); //Roof
			this.fillWithMetadataBlocks(world, box, 6, 5 - 1, 0, 6, 5 - 1, 9, Blocks.stone_slab, 1, Blocks.air, 0, false);
			this.fillWithMetadataBlocks(world, box, 2, 5, 0, 4, 5, 0, Blocks.stone_slab, 1, Blocks.air, 0, false);
			this.fillWithMetadataBlocks(world, box, 3, 5, 1, 3, 5, 2, Blocks.stone_slab, 1, Blocks.air, 0, false);
			this.fillWithMetadataBlocks(world, box, 3, 5, 4, 3, 5, 6, Blocks.stone_slab, 1, Blocks.air, 0, false);
			this.placeBlockAtCurrentPosition(world, Blocks.stone_slab, 1, 3, 5, 9 - 1, box);
			this.fillWithMetadataBlocks(world, box, 2, 5, 9, 4, 5, 9, Blocks.stone_slab, 1, Blocks.air, 0, false);
			
			//House 2
			this.fillWithRandomizedBlocks(world, box, 15 - 6, 0, 0, 15, 0, 0, false, rand, RandomSandstone); //Back Wall
			this.fillWithRandomizedBlocks(world, box, 15 - 6, 1, 0, 15 - 2, 1, 0, false, rand, RandomSandstone);
			this.fillWithRandomizedBlocks(world, box, 15 - 6, 2, 0, 15 - 6, 2, 0, false, rand, RandomSandstone);
			this.placeBlockAtCurrentPosition(world, Blocks.stone_slab, 1, 15 - 6, 2, 0, box);
			this.placeBlockAtCurrentPosition(world, Blocks.stone_slab, 1, 15 - 3, 2, 0, box);
			this.fillWithRandomizedBlocks(world, box, 15 - 6, 0, 1, 15 - 6, 3, 1, false, rand, RandomSandstone); //Left Wall
			this.fillWithRandomizedBlocks(world, box, 15 - 6, 0, 2, 15 - 6, 0, 2, false, rand, RandomSandstone);
			this.fillWithRandomizedBlocks(world, box, 15 - 6, 3, 2, 15 - 6, 3, 9 - 1, false, rand, RandomSandstone);
			this.placeBlockAtCurrentPosition(world, Blocks.stone_slab, 1, 15 - 6, 5 - 1, 2, box);
			this.fillWithMetadataBlocks(world, box, 15 - 6, 5 - 1, 4, 15 - 6, 5 - 1, 9 - 2, Blocks.stone_slab, 1, Blocks.air, 0, false);
			this.fillWithRandomizedBlocks(world, box, 15 - 6, 0, 3, 15 - 6, 1, 9, false, rand, RandomSandstone);
			this.fillWithRandomizedBlocks(world, box, 15 - 6, 0, 2, 15 - 6, 0, 2, false, rand, RandomSandstone);
			this.fillWithRandomizedBlocks(world, box, 15 - 6, 2, 3, 15 - 6, 2, 3, false, rand, RandomSandstone);
			this.placeBlockAtCurrentPosition(world, Blocks.fence, 0, 15 - 6, 2, 4, box);
			this.fillWithRandomizedBlocks(world, box, 15 - 6, 2, 5, 15 - 6, 2, 5, false, rand, RandomSandstone);
			this.fillWithBlocks(world, box, 15 - 6, 2, 9 - 3, 15 - 6, 2, 9 - 2, Blocks.fence, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 15 - 6, 2, 9 - 1, 15 - 6, 2, 9, false, rand, RandomSandstone);
			this.fillWithRandomizedBlocks(world, box, 15 - 5, 0, 9, 15, 1, 9, false, rand, RandomSandstone); //Front Wall
			this.fillWithRandomizedBlocks(world, box, 15 - 5, 2, 9, 15 - 5, 2, 9, false, rand, RandomSandstone);
			this.fillWithRandomizedBlocks(world, box, 15 - 1, 2, 9, 15, 2, 9, false, rand, RandomSandstone);
			this.fillWithRandomizedBlocks(world, box, 15, 0, 1, 15, 0, 9 - 1, false, rand, RandomSandstone); //Right Wall
			this.fillWithRandomizedBlocks(world, box, 15, 1, 3, 15, 1, 3, false, rand, RandomSandstone);
			this.fillWithMetadataBlocks(world, box, 15, 1, 4, 15, 1, 5, Blocks.stone_slab, 1, Blocks.air, 0, false);
			this.fillWithRandomizedBlocks(world, box, 15, 1, 9 - 1, 15, 1, 9 - 3, false, rand, RandomSandstone);
			this.placeBlockAtCurrentPosition(world, Blocks.stone_slab, 1, 15, 1, 9 - 1, box);
			
			this.fillWithBlocks(world, box, 15 - 5, 0, 1, 15 - 1, 0, 9 - 1, Blocks.sandstone, Blocks.air, false); //Floor
			
			//Loot & Decorations
			//House 1
			int eastMeta = this.getDecoMeta(4);
			this.placeBlockAtCurrentPosition(world, ModBlocks.machine_boiler_off, 4, 1, 1, 1, box);
			this.fillWithBlocks(world, box, 1, 2, 1, 1, 3, 1, ModBlocks.deco_pipe_quad_rusted, Blocks.air, false);
			this.placeBlockAtCurrentPosition(world, ModBlocks.deco_pipe_rim_rusted, 0, 1, 5, 1, box);
			this.placeBlockAtCurrentPosition(world, ModBlocks.crate, 0, 2, 1, 3, box);
			this.placeBlockAtCurrentPosition(world, ModBlocks.crate_can, 0, 1, 1, 9 - 4, box);
			if(!hasPlacedLoot[0]) {
				this.placeBlockAtCurrentPosition(world, Blocks.chest, this.getMetadataWithOffset(Blocks.chest, 3), 1, 1, 9 - 2, box);
				WeightedRandomChestContent.generateChestContents(rand, HbmChestContents.machineParts, (TileEntityChest)world.getTileEntity(this.getXWithOffset(1, 9 - 2), 
						this.getYWithOffset(1), this.getZWithOffset(1, 9 - 2)), 10);
				this.hasPlacedLoot[0] = true;
			}
			this.fillWithBlocks(world, box, 4, 1, 9 - 1, 5, 1, 9 - 1, ModBlocks.crate, Blocks.air, false);
			this.fillWithMetadataBlocks(world, box, 5, 1, 4, 5, 3, 4, ModBlocks.steel_scaffold, eastMeta < 4 ? 0 : 8, Blocks.air, 0, false);
			this.fillWithMetadataBlocks(world, box, 5, 1, 6, 5, 3, 6, ModBlocks.steel_scaffold, eastMeta < 4 ? 0 : 8, Blocks.air, 0, false);
			this.placeBlockAtCurrentPosition(world, ModBlocks.steel_grate, 7, 5, 1, 5, box);
			this.placeBlockAtCurrentPosition(world, ModBlocks.crate_weapon, 0, 5, 2, 5, box);
			
			//House 2
			if(!hasPlacedLoot[1]) {
				this.placeBlockAtCurrentPosition(world, Blocks.chest, this.getMetadataWithOffset(Blocks.chest, 3), 15 - 5, 1, 1, box);
				WeightedRandomChestContent.generateChestContents(rand, HbmChestContents.antenna, (TileEntityChest)world.getTileEntity(this.getXWithOffset(15 - 5, 1), 
						this.getYWithOffset(1), this.getZWithOffset(15 - 5, 1)), 10);
				this.hasPlacedLoot[1] = true;
			}
			this.placeBlockAtCurrentPosition(world, ModBlocks.bobblehead, rand.nextInt(16), 15 - 5, 1, 4, box);
			TileEntityBobble bobble = (TileEntityBobble) world.getTileEntity(this.getXWithOffset(15 - 5, 4), this.getYWithOffset(1), this.getZWithOffset(15 - 5, 4));
			
			if(bobble != null) {
				bobble.type = BobbleType.values()[rand.nextInt(BobbleType.values().length - 1) + 1];
				bobble.markDirty();
			}
			
			this.randomlyFillWithBlocks(world, box, rand, 0.25F, 15 - 4, 1, 1, 15 - 1, 1, 9 - 1, Blocks.sand, Blocks.air, false);
			
			return true;
		}
	}
	
	public static class NTMLab1 extends Component {
		
		private static ConcreteBricks RandomConcreteBricks = new ConcreteBricks();
		private static LabTiles RandomLabTiles = new LabTiles();
		
		private boolean[] hasPlacedLoot = new boolean[2];
		
		public NTMLab1() {
			super();
		}
		
		/** Constructor for this feature; takes coordinates for bounding box */
		public NTMLab1(Random rand, int minX, int minY, int minZ) {
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
			if(!this.setAverageHeight(world, box, this.boundingBox.minY)) {
				return false;
			}
			//System.out.println("" + this.boundingBox.minX + ", " + this.boundingBox.minY + ", " + this.boundingBox.minZ);
			
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 0, 0, 9, 7 - 2, -1, box);
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 3, 6, 9, 7, -1, box);
			
			if(this.getBlockAtCurrentPosition(world, 2, 0, 7 - 1, box).getMaterial().isReplaceable() 
					|| this.getBlockAtCurrentPosition(world, 2, 0, 7 - 1, box) == Blocks.air) {
				placeFoundationUnderneath(world, Blocks.stonebrick, 0, 2, 7 - 1, 2, 7 - 1, -1, box);
				this.placeBlockAtCurrentPosition(world, Blocks.stone_brick_stairs, getStairMeta(0), 2, 0, 7 - 1, box);
			}
			
			this.fillWithAir(world, box, 1, 0, 1, 9 - 1, 4, 4);
			this.fillWithAir(world, box, 4, 0, 4, 9 - 1, 4, 7 - 1);
			this.fillWithAir(world, box, 3, 1, 7 - 1, 3, 2, 7 - 1);
			
			int pillarMeta = this.getPillarMeta(8);
			
			//Pillars
			this.fillWithBlocks(world, box, 0, 0, 0, 0, 3, 0, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithBlocks(world, box, 9, 0, 0, 9, 3, 0, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithMetadataBlocks(world, box, 0, 0, 1, 0, 0, 4, ModBlocks.concrete_pillar, pillarMeta, Blocks.air, 0, false);
			this.fillWithMetadataBlocks(world, box, 9, 0, 1, 9, 0, 7 - 1, ModBlocks.concrete_pillar, pillarMeta, Blocks.air, 0, false);
			this.fillWithBlocks(world, box, 0, 0, 7 - 2, 0, 3, 7 - 2, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithBlocks(world, box, 3, 0, 7 - 2, 3, 3, 7 - 2, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithBlocks(world, box, 3, 0, 7, 3, 3, 7, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithBlocks(world, box, 9, 0, 7, 9, 3, 7, ModBlocks.concrete_pillar, Blocks.air, false);
			
			//Walls
			this.fillWithRandomizedBlocks(world, box, 1, 0, 0, 9 - 1, 4 - 1, 0, false, rand, RandomConcreteBricks); //Back Wall
			this.fillWithRandomizedBlocks(world, box, 0, 4, 0, 9, 4, 0, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 0, 1, 1, 0, 4 - 1, 4, false, rand, RandomConcreteBricks); //Left Wall
			this.fillWithRandomizedBlocks(world, box, 0, 4, 0, 0, 4, 7 - 2, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 1, 0, 7 - 2, 2, 4, 7 - 2, false, rand, RandomConcreteBricks); //Front Wall Pt. 1
			this.placeBlockAtCurrentPosition(world, ModBlocks.brick_concrete_broken, 0, 3, 4, 7 - 2, box);
			this.fillWithRandomizedBlocks(world, box, 3, 4 - 1, 7 - 1, 3, 4, 7 - 1, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 4, 0, 7, 9 - 1, 1, 7, false, rand, RandomConcreteBricks); //Front Wall Pt. 2
			this.fillWithRandomizedBlocks(world, box, 4, 2, 7, 4, 3, 7, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 9 - 1, 2, 7, 9 - 1, 3, 7, false, rand, RandomConcreteBricks);
			this.randomlyFillWithBlocks(world, box, rand, 0.75F, 5, 2, 7, 9 - 2, 3, 7, Blocks.glass_pane, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 3, 4, 7, 9, 4, 7, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 9, 1, 1, 9, 4, 7 - 1, false, rand, RandomConcreteBricks); //Right Wall
			
			//Floor & Ceiling
			this.fillWithRandomizedBlocks(world, box, 1, 0, 1, 9 - 1, 0, 4, false, rand, RandomLabTiles); //Floor
			this.fillWithRandomizedBlocks(world, box, 4, 0, 7 - 2, 9 - 1, 0, 7 - 1, false, rand, RandomLabTiles);
			this.placeBlockAtCurrentPosition(world, ModBlocks.tile_lab_cracked, 0, 3, 0, 7 - 1, box);
			
			this.fillWithBlocks(world, box, 1, 4 - 1, 1, 1, 4, 4, ModBlocks.reinforced_glass, Blocks.air, false); //Ceiling
			this.fillWithBlocks(world, box, 2, 4, 1, 9 - 1, 4, 4, ModBlocks.brick_light, Blocks.air, false);
			this.fillWithBlocks(world, box, 4, 4, 7 - 2, 9 - 1, 4, 7 - 1, ModBlocks.brick_light, Blocks.air, false);
			
			//Decorations & Loot
			this.fillWithMetadataBlocks(world, box, 1, 1, 1, 1, 1, 4, Blocks.dirt, 2, Blocks.air, 0, false);
			int westDecoMeta = this.getDecoMeta(5);
			this.fillWithMetadataBlocks(world, box, 2, 1, 1, 2, 1, 4, ModBlocks.steel_wall, westDecoMeta, Blocks.air, 0, false);
			this.fillWithMetadataBlocks(world, box, 2, 4 - 1, 1, 2, 4 - 1, 4, ModBlocks.steel_wall, westDecoMeta, Blocks.air, 0, false);
			for(byte i = 0; i < 4; i++) {
				this.placeBlockAtCurrentPosition(world, ModBlocks.plant_flower, i, 1, 2, 1 + i, box);
			}
			
			int doorMeta = this.getMetadataWithOffset(Blocks.wooden_door, 2);
			this.placeBlockAtCurrentPosition(world, ModBlocks.door_office, doorMeta, 3, 1, 7 - 1, box);
			ItemDoor.placeDoorBlock(world, this.getXWithOffset(3, 7 - 1), this.getYWithOffset(1), this.getZWithOffset(3, 7 - 1), doorMeta, ModBlocks.door_office);
			
			int northDecoMeta = this.getDecoMeta(3);
			this.fillWithMetadataBlocks(world, box, 5, 4 - 1, 1, 9 - 1, 4 - 1, 1, ModBlocks.steel_scaffold, westDecoMeta < 4 ? 0 : 8, Blocks.air, 0, false);
			this.fillWithMetadataBlocks(world, box, 5, 4 - 1, 2, 9 - 1, 4 - 1, 2, ModBlocks.steel_wall, northDecoMeta, Blocks.air, 0, false);
			this.placeBlockAtCurrentPosition(world, ModBlocks.machine_electric_furnace_off, northDecoMeta, 5, 1, 1, box);
			this.placeBlockAtCurrentPosition(world, ModBlocks.machine_microwave, northDecoMeta, 5, 2, 1, box);
			this.placeBlockAtCurrentPosition(world, ModBlocks.deco_titanium, 0, 6, 1, 1, box);
			this.placeBlockAtCurrentPosition(world, ModBlocks.machine_shredder, 0, 9 - 2, 1, 1, box);
			this.placeBlockAtCurrentPosition(world, ModBlocks.deco_titanium, 0, 9 - 1, 1, 1, box);
			this.fillWithBlocks(world, box, 5, 1, 3, 9 - 1, 1, 3, ModBlocks.deco_titanium, Blocks.air, false);
			if(!hasPlacedLoot[0]) {
				this.placeBlockAtCurrentPosition(world, ModBlocks.deco_loot, 0, 6, 2, 3, box);
				LootGenerator.lootMedicine(world, this.getXWithOffset(6, 3), this.getYWithOffset(2), this.getZWithOffset(6, 3));
				this.hasPlacedLoot[0] = true;
			}
			
			this.placeBlockAtCurrentPosition(world, ModBlocks.crate_can, 0, 9 - 1, 1, 7 - 2, box);
			if(!hasPlacedLoot[1]) {
				this.hasPlacedLoot[1] = this.generateInvContents(world, box, rand, ModBlocks.crate_iron, 9 - 1, 1, 7 - 1, HbmChestContents.modGeneric, 8);
			}
			
			return true;
		}
	}
	
	public static class NTMLab2 extends Component {
		
		private static SuperConcrete RandomSuperConcrete = new SuperConcrete();
		private static ConcreteBricks RandomConcreteBricks = new ConcreteBricks();
		private static LabTiles RandomLabTiles = new LabTiles();
		
		private boolean[] hasPlacedLoot = new boolean[2];
		
		public NTMLab2() {
			super();
		}

		public NTMLab2(Random rand, int minX, int minY, int minZ) {
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
			if(!this.setAverageHeight(world, box, this.boundingBox.minY)) {
				return false;
			}
			this.boundingBox.offset(0, -7, 0);
			//System.out.println("" + this.boundingBox.minX + ", " + this.boundingBox.minY + ", " + this.boundingBox.minZ);
			
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 0, 0, 12, 8 - 2, 6, box);
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 0, 7, 6, 8, 6, box);
			
			if(this.getBlockAtCurrentPosition(world, 12 - 3, 11 - 4, 7, box).getMaterial().isReplaceable() 
					|| this.getBlockAtCurrentPosition(world, 12 - 3, 11 - 4, 7, box) == Blocks.air) {
				int stairMeta = this.getMetadataWithOffset(Blocks.stone_brick_stairs, 2);
				placeFoundationUnderneath(world, Blocks.stonebrick, 0, 12 - 3, 7, 12 - 2, 7, 11 - 4, box);
				this.fillWithMetadataBlocks(world, box, 12 - 3, 11 - 4, 7, 12 - 2, 11 - 4, 7, Blocks.stone_brick_stairs, stairMeta, Blocks.air, 0, false);
			}
			
			
			this.fillWithAir(world, box, 1, 11 - 4, 1, 12 - 1, 11, 8 - 3);
			this.fillWithAir(world, box, 1, 11 - 4, 8 - 2, 5, 11, 8 - 1);
			this.fillWithAir(world, box, 12 - 3, 11 - 3, 8 - 2, 12 - 2, 11 - 2, 8 - 2);
			this.fillWithAir(world, box, 5, 5, 1, 6, 6, 2);
			this.fillWithAir(world, box, 2, 0, 2, 12 - 2, 3, 8 - 2);		
			
			//Walls
			this.fillWithRandomizedBlocks(world, box, 0, 11 - 4, 0, 12, 11, 0, false, rand, RandomSuperConcrete); //Back Wall
			this.fillWithRandomizedBlocks(world, box, 0, 11 - 4, 0, 0, 11, 8, false, rand, RandomSuperConcrete); //Left Wall
			this.fillWithRandomizedBlocks(world, box, 1, 11 - 4, 8, 5, 11 - 4, 8, false, rand, RandomSuperConcrete); //Front Wall pt. 1
			this.fillWithBlocks(world, box, 1, 11 - 3, 8, 1, 11 - 1, 8, ModBlocks.reinforced_glass, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 2, 11 - 4, 8, 2, 11 - 1, 8, false, rand, RandomSuperConcrete);
			this.fillWithBlocks(world, box, 3, 11 - 3, 8, 3, 11 - 1, 8, ModBlocks.reinforced_glass, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 4, 11 - 4, 8, 4, 11 - 1, 8, false, rand, RandomSuperConcrete);
			this.fillWithBlocks(world, box, 5, 11 - 3, 8, 5, 11 - 1, 8, ModBlocks.reinforced_glass, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 1, 11, 8, 5, 11, 8, false, rand, RandomSuperConcrete);
			this.fillWithRandomizedBlocks(world, box, 6, 11 - 4, 8 - 1, 6, 11, 8, false, rand, RandomSuperConcrete); //Front Wall pt. 2
			this.fillWithRandomizedBlocks(world, box, 6, 11 - 4, 8 - 2, 7, 11 - 2, 8 - 2, false, rand, RandomSuperConcrete); //Front Wall pt. 3
			this.fillWithBlocks(world, box, 6, 11 - 1, 8 - 2, 7, 11 - 1, 8 - 2, ModBlocks.concrete_super_broken, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 12 - 4, 11 - 4, 8 - 2, 12, 11 - 4, 8 - 2, false, rand, RandomSuperConcrete);
			this.fillWithRandomizedBlocks(world, box, 12 - 4, 11 - 3, 8 - 2, 12 - 4, 11, 8 - 2, false, rand, RandomSuperConcrete);
			this.fillWithRandomizedBlocks(world, box, 12 - 3, 11 - 1, 8 - 2, 12 - 2, 11, 8 - 2, false, rand, RandomSuperConcrete);
			this.fillWithRandomizedBlocks(world, box, 12 - 1, 11 - 4, 8 - 2, 12, 11, 8 - 2, false, rand, RandomSuperConcrete);
			this.fillWithRandomizedBlocks(world, box, 12, 11 - 4, 1, 12, 11 - 4, 8 - 3, false, rand, RandomSuperConcrete); //Right Wall
			this.fillWithBlocks(world, box, 12, 11 - 3, 8 - 3, 12, 11 - 1, 8 - 3, ModBlocks.reinforced_glass, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 12, 11 - 3, 4, 12, 11 - 1, 4, false, rand, RandomSuperConcrete);
			this.fillWithBlocks(world, box, 12, 11 - 3, 3, 12, 11 - 1, 3, ModBlocks.reinforced_glass, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 12, 11 - 3, 2, 12, 11 - 1, 2, false, rand, RandomSuperConcrete);
			this.fillWithBlocks(world, box, 12, 11 - 3, 1, 12, 11 - 1, 1, ModBlocks.reinforced_glass, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 12, 11, 1, 12, 11, 8 - 3, false, rand, RandomSuperConcrete);
			
			this.fillWithBlocks(world, box, 1, 0, 1, 12 - 1, 3, 1, ModBlocks.reinforced_stone, Blocks.air, false); //Back Wall
			this.fillWithBlocks(world, box, 1, 0, 2, 1, 3, 8 - 2, ModBlocks.reinforced_stone, Blocks.air, false); //Left Wall
			this.fillWithBlocks(world, box, 1, 0, 8 - 1, 12 - 1, 3, 8 - 1, ModBlocks.reinforced_stone, Blocks.air, false); //Front Wall 
			this.fillWithBlocks(world, box, 12 - 1, 0, 2, 12 - 1, 3, 8 - 2, ModBlocks.reinforced_stone, Blocks.air, false); // Right Wall
			this.fillWithBlocks(world, box, 6, 0, 3, 6, 3, 8 - 2, ModBlocks.reinforced_stone, Blocks.air, false); //Internal Wall
			
			//Floors & Ceiling
			this.fillWithRandomizedBlocks(world, box, 1, 11 - 4, 1, 3, 11 - 4, 8 - 1, false, rand, RandomLabTiles); //Left Floor
			this.fillWithRandomizedBlocks(world, box, 4, 11 - 4, 8 - 2, 5, 11 - 4, 8 - 1, false, rand, RandomLabTiles);
			this.fillWithRandomizedBlocks(world, box, 12 - 4, 11 - 4, 1, 12 - 1, 11 - 4, 8 - 3, false, rand, RandomLabTiles); //Right Floor
			this.fillWithRandomizedBlocks(world, box, 12 - 3, 11 - 4, 8 - 2, 12 - 2, 11 - 4, 8 - 2, false, rand, RandomLabTiles);
			this.fillWithBlocks(world, box, 4, 11 - 4, 1, 7, 11 - 4, 1, ModBlocks.tile_lab_broken, Blocks.air, false); //Center Floor (Pain)
			this.placeBlockAtCurrentPosition(world, ModBlocks.tile_lab_broken, 0, 4, 11 - 4, 2, box);
			this.fillWithBlocks(world, box, 4, 11 - 4, 3, 4, 11 - 4, 5, ModBlocks.tile_lab_cracked, Blocks.air, false);
			this.placeBlockAtCurrentPosition(world, ModBlocks.tile_lab_broken, 0, 5, 11 - 4, 3, box);
			this.fillWithBlocks(world, box, 5, 11 - 4, 4, 5, 11 - 4, 5, ModBlocks.tile_lab_cracked, Blocks.air, false);
			this.placeBlockAtCurrentPosition(world, ModBlocks.tile_lab_broken, 0, 6, 11 - 4, 4, box);
			this.placeBlockAtCurrentPosition(world, ModBlocks.tile_lab_cracked, 0, 6, 11 - 4, 5, box);
			this.fillWithBlocks(world, box, 7, 11 - 4, 2, 7, 11 - 4, 3, ModBlocks.tile_lab_broken, Blocks.air, false);
			this.fillWithBlocks(world, box, 7, 11 - 4, 4, 7, 11 - 4, 5, ModBlocks.tile_lab_cracked, Blocks.air, false);
			
			this.fillWithBlocks(world, box, 1, 11, 1, 2, 11, 8 - 1, ModBlocks.brick_light, Blocks.air, false); //Left Ceiling
			this.fillWithBlocks(world, box, 3, 11, 8 - 2, 4, 11, 8 - 1, ModBlocks.brick_light, Blocks.air, false);
			this.fillWithBlocks(world, box, 12 - 3, 11, 1, 12 - 1, 11, 8 - 3, ModBlocks.brick_light, Blocks.air, false); //Right Ceiling
			this.fillWithBlocks(world, box, 3, 11, 1, 8, 11, 1, ModBlocks.waste_planks, Blocks.air, false); //Center Ceiling (Pain)
			this.fillWithBlocks(world, box, 3, 11, 2, 4, 11, 2, ModBlocks.waste_planks, Blocks.air, false);
			this.fillWithBlocks(world, box, 7, 11, 2, 8, 11, 2, ModBlocks.waste_planks, Blocks.air, false);
			this.fillWithBlocks(world, box, 3, 11, 3, 3, 11, 5, ModBlocks.waste_planks, Blocks.air, false);
			this.fillWithBlocks(world, box, 4, 11, 4, 4, 11, 5, ModBlocks.waste_planks, Blocks.air, false);
			this.fillWithBlocks(world, box, 5, 11, 6, 5, 11, 8 - 1, ModBlocks.waste_planks, Blocks.air, false);
			this.fillWithBlocks(world, box, 8, 11, 3, 8, 11, 5, ModBlocks.waste_planks, Blocks.air, false);
			
			this.fillWithRandomizedBlocks(world, box, 2, 0, 2, 5, 0, 8 - 2, false, rand, RandomLabTiles); //Floor
			this.fillWithRandomizedBlocks(world, box, 6, 0, 2, 6, 0, 3, false, rand, RandomLabTiles);
			this.fillWithRandomizedBlocks(world, box, 7, 0, 2, 12 - 2, 0, 8 - 2, false, rand, RandomLabTiles);
			
			this.fillWithRandomizedBlocks(world, box, 1, 4, 1, 12 - 1, 4, 8 - 1, false, rand, RandomConcreteBricks); //Ceiling
			
			//Decorations & Loot
			int eastMeta = this.getDecoMeta(4);
			int westMeta = this.getDecoMeta(5);
			int northMeta = this.getDecoMeta(3);
			int southMeta = this.getDecoMeta(2);
			this.placeBlockAtCurrentPosition(world, ModBlocks.crashed_balefire, southMeta, 6, 11 - 2, 3, box);
			
			int doorMeta = this.getMetadataWithOffset(Blocks.wooden_door, 1);
			this.placeBlockAtCurrentPosition(world, ModBlocks.door_office, doorMeta, 12 - 3, 11 - 3, 8 - 2, box);
			ItemDoor.placeDoorBlock(world, this.getXWithOffset(12 - 3, 8 - 2), this.getYWithOffset(11 - 3), this.getZWithOffset(12 - 3, 8 - 2), 
					doorMeta, ModBlocks.door_office);
			this.placeBlockAtCurrentPosition(world, ModBlocks.door_office, doorMeta, 12 - 2, 11 - 3, 8 - 2, box);
			ItemDoor.placeDoorBlock(world, this.getXWithOffset(12 - 2, 8 - 2), this.getYWithOffset(11 - 3), this.getZWithOffset(12 - 2, 8 - 2), 
					doorMeta, ModBlocks.door_office);
			
			this.fillWithBlocks(world, box, 1, 11 - 3, 1, 1, 11 - 1, 1, ModBlocks.deco_steel, Blocks.air, false);
			this.fillWithMetadataBlocks(world, box, 1, 11 - 3, 2, 1, 11 - 2, 3, ModBlocks.steel_grate, 7, Blocks.air, 0, false);
			this.placeBlockAtCurrentPosition(world, ModBlocks.tape_recorder, westMeta, 1, 11 - 1, 2, box);
			this.placeBlockAtCurrentPosition(world, ModBlocks.steel_beam, 0, 1, 11 - 1, 3, box);
			this.fillWithBlocks(world, box, 1, 11 - 3, 6, 1, 11 - 1, 6, ModBlocks.deco_pipe_framed_rusted, Blocks.air, false);
			
			this.fillWithMetadataBlocks(world, box, 12 - 4, 11 - 3, 1, 12 - 4, 11 - 1, 1, ModBlocks.steel_wall, eastMeta, Blocks.air, 0, false);
			this.fillWithMetadataBlocks(world, box, 12 - 3, 11 - 1, 1, 12 - 2, 11 - 1, 1, ModBlocks.steel_grate, 0, Blocks.air, 0, false);
			this.fillWithMetadataBlocks(world, box, 12 - 3, 11 - 2, 1, 12 - 2, 11 - 2, 1, ModBlocks.tape_recorder, northMeta, Blocks.air, 0, false);
			this.fillWithBlocks(world, box, 12 - 3, 11 - 3, 1, 12 - 2, 11 - 3, 1, ModBlocks.deco_steel, Blocks.air, false);
			this.fillWithMetadataBlocks(world, box, 12 - 1, 11 - 3, 1, 12 - 1, 11 - 1, 1, ModBlocks.steel_wall, westMeta, Blocks.air, 0, false);
			
			this.fillWithMetadataBlocks(world, box, 2, 1, 2, 2, 1, 8 - 2, ModBlocks.steel_grate, 7, Blocks.air, 0, false);
			this.placeBlockAtCurrentPosition(world, ModBlocks.vitrified_barrel, 0, 2, 2, 2, box);
			this.fillWithMetadataBlocks(world, box, 3, 1, 2, 3, 3, 2, ModBlocks.steel_wall, westMeta, Blocks.air, 0, false);
			this.fillWithMetadataBlocks(world, box, 3, 1, 4, 3, 3, 4, ModBlocks.steel_wall, westMeta, Blocks.air, 0, false);
			this.fillWithMetadataBlocks(world, box, 3, 1, 8 - 2, 3, 3, 8 - 2, ModBlocks.steel_wall, westMeta, Blocks.air, 0, false);
			this.placeBlockAtCurrentPosition(world, ModBlocks.crate, 0, 4, 1, 8 - 2, box);
			this.placeBlockAtCurrentPosition(world, ModBlocks.crate_lead, 0, 4, 2, 8 - 2, box);
			if(!hasPlacedLoot[0]) {
				this.hasPlacedLoot[0] = this.generateInvContents(world, box, rand, ModBlocks.crate_iron, 5, 1, 8 - 2, HbmChestContents.nuclearFuel, 10);
			}
			this.fillWithBlocks(world, box, 4, 1, 8 - 3, 5, 1, 8 - 3, ModBlocks.crate_lead, Blocks.air, false);
			
			this.fillWithBlocks(world, box, 12 - 5, 1, 8 - 2, 12 - 5, 3, 8 - 2, ModBlocks.deco_steel, Blocks.air, false);;
			this.fillWithMetadataBlocks(world, box, 12 - 4, 1, 8 - 2, 12 - 2, 1, 8 - 2, ModBlocks.steel_grate, 7, Blocks.air, 0, false);
			this.fillWithMetadataBlocks(world, box, 12 - 4, 2, 8 - 2, 12 - 3, 2, 8 - 2, ModBlocks.tape_recorder, southMeta, Blocks.air, 0, false);
			this.placeBlockAtCurrentPosition(world, ModBlocks.steel_beam, 0, 12 - 2, 2, 8 - 2, box);
			this.fillWithBlocks(world, box, 12 - 4, 3, 8 - 2, 12 - 2, 3, 8 - 2, ModBlocks.steel_roof, Blocks.air, false);
			if(!hasPlacedLoot[1]) {
				this.hasPlacedLoot[1] = this.generateInvContents(world, box, rand, ModBlocks.crate_iron, 12 - 2, 1, 3, HbmChestContents.nukeTrash, 9);
				if(rand.nextInt(2) == 0)
					generateLoreBook(world, box, 12 - 2, 1, 3, 1, HbmChestContents.generateOfficeBook(rand));
			}
			
			return true;
		}
	}
	
	public static class NTMWorkshop1 extends Component {
		
		private static SuperConcrete RandomSuperConcrete = new SuperConcrete();
		
		private boolean hasPlacedLoot;
		
		public NTMWorkshop1() {
			super();
		}
		
		public NTMWorkshop1(Random rand, int minX, int minY, int minZ) {
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
			if(!this.setAverageHeight(world, box, this.boundingBox.minY)) {
				return false;
			}
			//System.out.println("" + this.boundingBox.minX + ", " + this.boundingBox.minY + ", " + this.boundingBox.minZ);
			
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 1, 0, 8 - 3, 11, -1, box);
			placeFoundationUnderneath(world, Blocks.dirt, 0, 8, 1, 10, 6, -1, box);
			
			this.fillWithAir(world, box, 1, 0, 0, 10 - 3, 6 - 2, 8);
			this.fillWithAir(world, box, 10 - 2, 0, 2, 10 - 1, 2, 5);
			
			if(this.getBlockAtCurrentPosition(world, 0, 0, 5, box).getMaterial().isReplaceable() 
					|| this.getBlockAtCurrentPosition(world, 0, 0, 5, box) == Blocks.air) {
				int stairMeta = this.getMetadataWithOffset(Blocks.stone_brick_stairs, 1);
				this.placeBlockAtCurrentPosition(world, Blocks.stone_brick_stairs, stairMeta, 0, 0, 5, box);
				
				placeFoundationUnderneath(world, Blocks.stonebrick, 0, 0, 1, 0, 8 - 1, -1, box);
				
				this.fillWithMetadataBlocks(world, box, 0, 0, 1, 0, 0, 8 - 1, Blocks.stone_slab, 5, Blocks.air, 0, false);
			}
			
			//Walls
			int pillarMetaWE = this.getPillarMeta(4);
			int pillarMetaNS = this.getPillarMeta(8);
			this.fillWithBlocks(world, box, 1, 0, 0, 1, 3, 0, ModBlocks.concrete_pillar, Blocks.air, false); //Back Wall
			this.placeBlockAtCurrentPosition(world, ModBlocks.concrete, 0, 1, 4, 0, box);
			this.fillWithMetadataBlocks(world, box, 2, 4, 0, 10 - 4, 4, 0, ModBlocks.concrete_pillar, pillarMetaWE, Blocks.air, 0, false);
			this.placeBlockAtCurrentPosition(world, ModBlocks.concrete, 0, 10 - 3, 4, 0, box);
			this.fillWithBlocks(world, box, 10 - 3, 0, 0, 10 - 3, 3, 0, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 2, 0, 0, 10 - 4, 1, 0, false, rand, RandomSuperConcrete);
			this.fillWithRandomizedBlocks(world, box, 2, 2, 0, 2, 2, 0, false, rand, RandomSuperConcrete);
			this.fillWithBlocks(world, box, 3, 2, 0, 5, 2, 0, ModBlocks.reinforced_glass, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 10 - 4, 2, 0, 10 - 4, 2, 0, false, rand, RandomSuperConcrete);
			this.fillWithRandomizedBlocks(world, box, 2, 3, 0, 10 - 4, 3, 0, false, rand, RandomSuperConcrete);
			this.fillWithMetadataBlocks(world, box, 1, 4, 1, 1, 4, 8 - 1, ModBlocks.concrete_pillar, pillarMetaNS, Blocks.air, 0, false); //Left Wall
			this.placeBlockAtCurrentPosition(world, ModBlocks.concrete, 0, 1, 4, 8, box);
			this.fillWithBlocks(world, box, 1, 0, 8, 1, 3, 8, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 1, 0, 1, 1, 1, 4, false, rand, RandomSuperConcrete);
			this.fillWithRandomizedBlocks(world, box, 1, 2, 1, 1, 2, 1, false, rand, RandomSuperConcrete);
			this.fillWithBlocks(world, box, 1, 2, 2, 1, 2, 3, ModBlocks.reinforced_glass, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 1, 2, 4, 1, 2, 4, false, rand, RandomSuperConcrete);
			this.fillWithRandomizedBlocks(world, box, 1, 3, 1, 1, 3, 8 - 1, false, rand, RandomSuperConcrete);
			this.fillWithRandomizedBlocks(world, box, 1, 0, 8 - 2, 1, 3, 8 - 1, false, rand, RandomSuperConcrete);
			this.fillWithMetadataBlocks(world, box, 2, 4, 8, 10 - 4, 4, 8, ModBlocks.concrete_pillar, pillarMetaWE, Blocks.air, 0, false); //Front Wall
			this.placeBlockAtCurrentPosition(world, ModBlocks.concrete, 0, 10 - 3, 4, 8, box);
			this.fillWithBlocks(world, box, 10 - 3, 0, 8, 10 - 3, 3, 8, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 2, 0, 8, 10 - 4, 1, 8, false, rand, RandomSuperConcrete);
			this.fillWithRandomizedBlocks(world, box, 2, 2, 8, 2, 2, 8, false, rand, RandomSuperConcrete);
			this.fillWithBlocks(world, box, 3, 2, 8, 5, 2, 8, ModBlocks.reinforced_glass, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 10 - 4, 2, 8, 10 - 4, 2, 8, false, rand, RandomSuperConcrete);
			this.fillWithRandomizedBlocks(world, box, 2, 3, 8, 10 - 4, 3, 8, false, rand, RandomSuperConcrete);
			this.fillWithMetadataBlocks(world, box, 10 - 3, 4, 1, 10 - 3, 4, 8 - 1, ModBlocks.concrete_pillar, pillarMetaNS, Blocks.air, 0, false); //Right Wall
			this.fillWithRandomizedBlocks(world, box, 10 - 3, 0, 1, 10 - 3, 3, 8 - 1, false, rand, RandomSuperConcrete);
			
			pillarMetaWE = this.getPillarMeta(5);
			pillarMetaNS = this.getPillarMeta(9);
			this.fillWithMetadataBlocks(world, box, 10 - 2, 2, 1, 10 - 1, 2, 1, Blocks.log, pillarMetaWE, Blocks.air, 0, false); //Back Wall
			this.fillWithMetadataBlocks(world, box, 10, 0, 1, 10, 2, 1, Blocks.log, 1, Blocks.air, 0, false);
			this.fillWithMetadataBlocks(world, box, 10 - 2, 0, 1, 10 - 1, 1, 1, Blocks.planks, 1, Blocks.air, 0, false);
			this.fillWithMetadataBlocks(world, box, 10, 2, 2, 10, 2, 5, Blocks.log, pillarMetaNS, Blocks.air, 0, false); //Right Wall
			this.fillWithMetadataBlocks(world, box, 10, 0, 6, 10, 2, 6, Blocks.log, 1, Blocks.air, 0, false);
			this.fillWithMetadataBlocks(world, box, 10, 0, 3, 10, 1, 5, Blocks.planks, 1, Blocks.air, 0, false);
			this.fillWithMetadataBlocks(world, box, 10 - 2, 2, 6, 10 - 1, 2, 6, Blocks.log, pillarMetaWE, Blocks.air, 0, false); //Front Wall
			this.fillWithMetadataBlocks(world, box, 10 - 2, 0, 6, 10 - 1, 1, 6, Blocks.planks, 1, Blocks.air, 0, false);
			
			//Floor & Ceiling
			this.fillWithBlocks(world, box, 2, 0, 1, 6, 0, 8 - 1, ModBlocks.brick_light, Blocks.air, false); //Floor
			this.placeBlockAtCurrentPosition(world, ModBlocks.brick_light, 0, 1, 0, 5, box);
			this.fillWithRandomizedBlocks(world, box, 2, 4, 1, 6, 4, 3, false, rand, RandomSuperConcrete); //Ceiling
			this.fillWithRandomizedBlocks(world, box, 2, 4, 4, 2, 4, 4, false, rand, RandomSuperConcrete);
			this.fillWithRandomizedBlocks(world, box, 5, 4, 4, 6, 4, 4, false, rand, RandomSuperConcrete);
			this.fillWithRandomizedBlocks(world, box, 2, 4, 8 - 3, 6, 4, 8 - 1, false, rand, RandomSuperConcrete);
			
			this.fillWithBlocks(world, box, 10 - 2, 2, 2, 10 - 1, 2, 5, ModBlocks.deco_steel, Blocks.air, false);
			
			//Loot & Decorations
			int southMeta = this.getDecoMeta(2);
			int eastMeta = this.getDecoMeta(5);
			this.placeBlockAtCurrentPosition(world, ModBlocks.pole_satellite_receiver, eastMeta, 2, 6 - 1, 1, box);
			this.fillWithBlocks(world, box, 3, 6 - 1, 1, 4, 6 - 1, 1, ModBlocks.deco_steel, Blocks.air, false);
			this.fillWithBlocks(world, box, 2, 6 - 1, 2, 4, 6 - 1, 2, ModBlocks.deco_steel, Blocks.air, false);
			this.fillWithBlocks(world, box, 2, 6, 1, 4, 6, 2, ModBlocks.steel_roof, Blocks.air, false);
			this.fillWithBlocks(world, box, 2, 1, 1, 2, 3, 1, ModBlocks.deco_red_copper, Blocks.air, false);
			this.fillWithBlocks(world, box, 3, 1, 1, 3, 1, 2, ModBlocks.deco_beryllium, Blocks.air, false);
			this.placeBlockAtCurrentPosition(world, ModBlocks.machine_generator, 0, 4, 1, 1, box);
			this.placeBlockAtCurrentPosition(world, ModBlocks.machine_detector, 0, 4, 1, 2, box);
			this.fillWithBlocks(world, box, 5, 1, 1, 5, 1, 2, ModBlocks.deco_beryllium, Blocks.air, false);
			this.fillWithBlocks(world, box, 6, 1, 1, 6, 3, 1, ModBlocks.deco_red_copper, Blocks.air, false);
			this.fillWithBlocks(world, box, 3, 1, 4, 4, 1, 4, ModBlocks.concrete_super_broken, Blocks.air, false);
			this.fillWithMetadataBlocks(world, box, 6, 1, 4, 6, 3, 4, ModBlocks.steel_scaffold, eastMeta < 4 ? 0 : 8, Blocks.air, 0, false);
			this.fillWithMetadataBlocks(world, box, 6, 1, 5, 6, 1, 7, ModBlocks.steel_grate, 7, Blocks.air, 0, false);
			this.placeBlockAtCurrentPosition(world, ModBlocks.radiorec, eastMeta, 6, 2, 8 - 1, box);
			this.fillWithMetadataBlocks(world, box, 2, 1, 8 - 1, 3, 1, 8 - 1, ModBlocks.machine_electric_furnace_off, southMeta, Blocks.air, 0, false);
			if(!hasPlacedLoot) {
				this.hasPlacedLoot = this.generateInvContents(world, box, rand, ModBlocks.crate_iron, 4, 1, 8 - 1, HbmChestContents.machineParts, 11);
			}
			this.placeBlockAtCurrentPosition(world, Blocks.web, 0, 5, 3, 1, box);
			this.placeBlockAtCurrentPosition(world, Blocks.web, 0, 2, 1, 2, box);
			this.placeBlockAtCurrentPosition(world, Blocks.web, 0, 6, 1, 2, box);
			this.placeBlockAtCurrentPosition(world, Blocks.web, 0, 6, 2, 5, box);
			
			this.fillWithMetadataBlocks(world, box, 10 - 2, 0, 5, 10 - 1, 0, 5, ModBlocks.steel_grate, 7, Blocks.air, 0, false);
			this.placeBlockAtCurrentPosition(world, ModBlocks.tape_recorder, southMeta, 10 - 2, 1, 5, box);
			this.placeBlockAtCurrentPosition(world, ModBlocks.bobblehead, rand.nextInt(16), 10 - 1, 1, 5, box);
			TileEntityBobble bobble = (TileEntityBobble) world.getTileEntity(this.getXWithOffset(10 - 1, 5), this.getYWithOffset(1), this.getZWithOffset(10 - 1, 5));
			
			if(bobble != null) {
				bobble.type = BobbleType.values()[rand.nextInt(BobbleType.values().length - 1) + 1];
				bobble.markDirty();
			}
			this.fillWithMetadataBlocks(world, box, 10 - 2, 0, 2, 10 - 2, 0, 3, Blocks.log, pillarMetaWE, Blocks.air, 0, false);
			this.placeBlockAtCurrentPosition(world, Blocks.log, pillarMetaWE, 10 - 2, 1, 2, box);
			this.placeBlockAtCurrentPosition(world, Blocks.web, 0, 10 - 2, 1, 3, box);
			
			return true;
		}		
	}
	
}

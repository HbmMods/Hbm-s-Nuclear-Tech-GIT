package com.hbm.world.gen.component;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockBobble.BobbleType;
import com.hbm.blocks.generic.BlockBobble.TileEntityBobble;
import com.hbm.itempool.ItemPool;
import com.hbm.itempool.ItemPoolsComponent;
import com.hbm.itempool.ItemPoolsLegacy;
import com.hbm.lib.HbmChestContents;
import com.hbm.util.LootGenerator;

import net.minecraft.block.BlockStairs;
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
		MapGenStructureIO.func_143031_a(NTMLab1.class, "NTMLab1"); //i'll replace these shitty structures one day trust
		MapGenStructureIO.func_143031_a(NTMLab2.class, "NTMLab2");
		MapGenStructureIO.func_143031_a(RuralHouse1.class, "NTMRuralHouse1");
	}

	/** Sandstone Ruin 1 */
	public static class NTMHouse1 extends Component {

		private boolean hasPlacedChest;

		private static Sandstone RandomSandstone = new Sandstone();

		public NTMHouse1() {
			super();
		}

		/** Constructor for this feature; takes coordinates for bounding box */
		public NTMHouse1(Random rand, int minX, int minZ) {
			super(rand, minX, 64, minZ, 9, 4, 6);
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
				this.hasPlacedChest = this.generateStructureChestContents(world, box, rand, 3, 0, 1, ItemPool.getPool(ItemPoolsLegacy.POOL_GENERIC), rand.nextInt(2) + 8);
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

		public NTMHouse2(Random rand, int minX, int minZ) {
			super(rand, minX, 64, minZ, 15, 5, 9);
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
				WeightedRandomChestContent.generateChestContents(rand, ItemPool.getPool(ItemPoolsComponent.POOL_MACHINE_PARTS), (TileEntityChest)world.getTileEntity(this.getXWithOffset(1, 9 - 2),
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
				WeightedRandomChestContent.generateChestContents(rand, ItemPool.getPool(ItemPoolsLegacy.POOL_ANTENNA), (TileEntityChest)world.getTileEntity(this.getXWithOffset(15 - 5, 1),
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
		public NTMLab1(Random rand, int minX, int minZ) {
			super(rand, minX, 64, minZ, 9, 4, 7);
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
				this.hasPlacedLoot[1] = this.generateInvContents(world, box, rand, ModBlocks.crate_iron, 9 - 1, 1, 7 - 1, ItemPool.getPool(ItemPoolsLegacy.POOL_GENERIC), 8);
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

		public NTMLab2(Random rand, int minX, int minZ) {
			super(rand, minX, 64, minZ, 12, 11, 8);
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
				this.hasPlacedLoot[0] = this.generateInvContents(world, box, rand, ModBlocks.crate_iron, 5, 1, 8 - 2, ItemPool.getPool(ItemPoolsComponent.POOL_NUKE_FUEL), 10);
			}
			this.fillWithBlocks(world, box, 4, 1, 8 - 3, 5, 1, 8 - 3, ModBlocks.crate_lead, Blocks.air, false);

			this.fillWithBlocks(world, box, 12 - 5, 1, 8 - 2, 12 - 5, 3, 8 - 2, ModBlocks.deco_steel, Blocks.air, false);;
			this.fillWithMetadataBlocks(world, box, 12 - 4, 1, 8 - 2, 12 - 2, 1, 8 - 2, ModBlocks.steel_grate, 7, Blocks.air, 0, false);
			this.fillWithMetadataBlocks(world, box, 12 - 4, 2, 8 - 2, 12 - 3, 2, 8 - 2, ModBlocks.tape_recorder, southMeta, Blocks.air, 0, false);
			this.placeBlockAtCurrentPosition(world, ModBlocks.steel_beam, 0, 12 - 2, 2, 8 - 2, box);
			this.fillWithBlocks(world, box, 12 - 4, 3, 8 - 2, 12 - 2, 3, 8 - 2, ModBlocks.steel_roof, Blocks.air, false);
			if(!hasPlacedLoot[1]) {
				this.hasPlacedLoot[1] = this.generateInvContents(world, box, rand, ModBlocks.crate_iron, 12 - 2, 1, 3, ItemPool.getPool(ItemPoolsLegacy.POOL_NUKE_TRASH), 9);
				if(rand.nextInt(2) == 0)
					generateLoreBook(world, box, 12 - 2, 1, 3, 1, HbmChestContents.generateOfficeBook(rand));
			}

			return true;
		}
	}

	public static class RuralHouse1 extends Component {

		public RuralHouse1() {
			super();
		}

		public RuralHouse1(Random rand, int minX, int minZ) {
			super(rand, minX, 64, minZ, 14, 8, 14);
		}

		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {

			if(!this.setAverageHeight(world, box, this.boundingBox.minY)) {
				return false;
			}

			//FillWithAir
			fillWithAir(world, box, 9, 1, 3, 12, 4, 8);
			fillWithAir(world, box, 5, 1, 2, 8, 3, 8);
			fillWithAir(world, box, 2, 1, 5, 4, 3, 8);
			fillWithAir(world, box, 2, 1, 10, 7, 3, 12);

			//Foundations
			fillWithBlocks(world, box, 1, 0, 4, 4, 0, 4, ModBlocks.concrete_colored_ext);
			fillWithBlocks(world, box, 4, 0, 2, 4, 0, 3, ModBlocks.concrete_colored_ext);
			fillWithBlocks(world, box, 4, 0, 1, 9, 0, 1, ModBlocks.concrete_colored_ext);
			fillWithBlocks(world, box, 9, 0, 2, 10, 0, 2, ModBlocks.concrete_colored_ext);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_colored_ext, 0, 12, 0, 2, box);
			fillWithBlocks(world, box, 13, 0, 2, 13, 0, 9, ModBlocks.concrete_colored_ext);
			fillWithBlocks(world, box, 5, 0, 9, 12, 0, 9, ModBlocks.concrete_colored_ext);
			fillWithBlocks(world, box, 2, 0, 9, 3, 0, 9, ModBlocks.concrete_colored_ext);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_colored_ext, 0, 8, 0, 10, box);
			fillWithBlocks(world, box, 8, 0, 12, 8, 0, 13, ModBlocks.concrete_colored_ext);
			fillWithBlocks(world, box, 1, 0, 13, 7, 0, 13, ModBlocks.concrete_colored_ext);
			fillWithBlocks(world, box, 1, 0, 5, 1, 0, 12, ModBlocks.concrete_colored_ext);
			placeFoundationUnderneath(world, ModBlocks.concrete_colored_ext, 0, 1, 10, 8, 13, -1, box);
			placeFoundationUnderneath(world, ModBlocks.concrete_colored_ext, 0, 1, 4, 3, 9, -1, box);
			placeFoundationUnderneath(world, ModBlocks.concrete_colored_ext, 0, 4, 1, 13, 9, -1, box);

			placeFoundationUnderneath(world, Blocks.log, 0, 2, 3, 2, 3, 0, box);
			placeFoundationUnderneath(world, Blocks.log, 0, 3, 2, 3, 2, 0, box);
			placeFoundationUnderneath(world, Blocks.log, 0, 3, 0, 3, 0, -1, box);
			placeFoundationUnderneath(world, Blocks.log, 0, 5, 0, 5, 0, 0, box);
			placeFoundationUnderneath(world, Blocks.log, 0, 8, 0, 8, 0, 0, box);
			placeFoundationUnderneath(world, Blocks.log, 0, 10, 0, 10, 0, -1, box);
			placeFoundationUnderneath(world, Blocks.log, 0, 14, 1, 14, 1, -1, box);
			placeFoundationUnderneath(world, Blocks.log, 0, 14, 3, 14, 3, -1, box);
			placeFoundationUnderneath(world, Blocks.log, 0, 14, 5, 14, 6, 0, box);
			placeFoundationUnderneath(world, Blocks.log, 0, 14, 8, 14, 8, -1, box);
			placeFoundationUnderneath(world, Blocks.log, 0, 14, 10, 14, 10, -1, box);
			placeFoundationUnderneath(world, Blocks.log, 0, 9, 14, 9, 14, -1, box);
			placeFoundationUnderneath(world, Blocks.log, 0, 7, 14, 7, 14, -1, box);
			placeFoundationUnderneath(world, Blocks.log, 0, 4, 14, 5, 14, 0, box);
			placeFoundationUnderneath(world, Blocks.log, 0, 2, 14, 2, 14, -1, box);
			placeFoundationUnderneath(world, Blocks.log, 0, 0, 14, 0, 14, -1, box);
			placeFoundationUnderneath(world, Blocks.log, 0, 0, 13, 0, 13, 0, box);
			placeFoundationUnderneath(world, Blocks.log, 0, 0, 11, 0, 11, 0, box);
			placeFoundationUnderneath(world, Blocks.log, 0, 0, 9, 0, 9, -1, box);
			placeFoundationUnderneath(world, Blocks.log, 0, 0, 6, 0, 7, 0, box);
			placeFoundationUnderneath(world, Blocks.log, 0, 0, 4, 0, 4, 0, box);
			placeFoundationUnderneath(world, Blocks.log, 0, 0, 3, 0, 4, -1, box);

			//Walls
			//North/Front
			fillWithBlocks(world, box, 1, 1, 4, 4, 4, 4, Blocks.brick_block);
			fillWithBlocks(world, box, 2, 5, 4, 7, 5, 4, Blocks.brick_block);
			placeBlockAtCurrentPosition(world, Blocks.brick_block, 0, 3, 6, 4, box);
			placeBlockAtCurrentPosition(world, Blocks.brick_block, 0, 6, 6, 4, box);
			fillWithBlocks(world, box, 4, 7, 4, 5, 7, 4, Blocks.brick_block);
			fillWithBlocks(world, box, 4, 1, 1, 4, 4, 3, Blocks.brick_block);
			fillWithBlocks(world, box, 5, 1, 1, 8, 1, 1, Blocks.brick_block);
			fillWithBlocks(world, box, 5, 4, 1, 8, 4, 1, Blocks.brick_block);
			fillWithBlocks(world, box, 9, 1, 1, 9, 4, 2, Blocks.brick_block);
			fillWithBlocks(world, box, 10, 1, 2, 10, 3, 2, Blocks.brick_block);
			fillWithBlocks(world, box, 12, 1, 2, 13, 3, 2, Blocks.brick_block);
			fillWithBlocks(world, box, 10, 4, 2, 13, 4, 2, Blocks.brick_block);
			fillWithBlocks(world, box, 9, 5, 2, 12, 5, 2, Blocks.brick_block);
			fillWithBlocks(world, box, 10, 6, 2, 11, 6, 2, Blocks.brick_block);
			//East/Left
			fillWithBlocks(world, box, 13, 1, 3, 13, 1, 8, Blocks.brick_block);
			fillWithBlocks(world, box, 13, 3, 3, 13, 4, 8, Blocks.brick_block);
			//South/Back
			fillWithBlocks(world, box, 13, 1, 9, 13, 4, 9, Blocks.brick_block);
			fillWithBlocks(world, box, 9, 1, 9, 12, 1, 9, Blocks.brick_block);
			fillWithBlocks(world, box, 9, 4, 9, 12, 5, 9, Blocks.brick_block);
			fillWithBlocks(world, box, 10, 6, 9, 11, 6, 9, Blocks.brick_block);
			fillWithBlocks(world, box, 8, 1, 9, 8, 4, 10, Blocks.brick_block);
			fillWithBlocks(world, box, 8, 1, 12, 8, 3, 13, Blocks.brick_block);
			fillWithBlocks(world, box, 8, 4, 11, 8, 4, 13, Blocks.brick_block);
			fillWithBlocks(world, box, 7, 1, 13, 7, 3, 13, Blocks.brick_block);
			fillWithBlocks(world, box, 3, 1, 13, 6, 1, 13, Blocks.brick_block);
			fillWithBlocks(world, box, 2, 4, 13, 7, 5, 13, Blocks.brick_block);
			placeBlockAtCurrentPosition(world, Blocks.brick_block, 0, 6, 6, 13, box);
			placeBlockAtCurrentPosition(world, Blocks.brick_block, 0, 3, 6, 13, box);
			fillWithBlocks(world, box, 4, 7, 13, 5, 7, 13, Blocks.brick_block);
			fillWithBlocks(world, box, 2, 1, 13, 2, 3, 13, Blocks.brick_block);
			//West/Right
			fillWithBlocks(world, box, 1, 1, 13, 1, 4, 13, Blocks.brick_block);
			fillWithBlocks(world, box, 1, 1, 5, 1, 1, 12, Blocks.brick_block);
			placeBlockAtCurrentPosition(world, Blocks.brick_block, 0, 1, 2, 9, box);
			fillWithBlocks(world, box, 1, 3, 5, 1, 3, 12, Blocks.brick_block);
			//Inside
			fillWithBlocks(world, box, 2, 1, 9, 3, 3, 9, Blocks.brick_block);
			fillWithBlocks(world, box, 5, 1, 9, 7, 3, 9, Blocks.brick_block);
			//Wood Paneling
			fillWithMetadataBlocks(world, box, 5, 2, 1, 5, 3, 1, Blocks.planks, 1);
			fillWithMetadataBlocks(world, box, 8, 2, 1, 8, 3, 1, Blocks.planks, 1);
			placeBlockAtCurrentPosition(world, Blocks.planks, 1, 11, 3, 2, box);
			fillWithMetadataBlocks(world, box, 13, 2, 3, 13, 2, 4, Blocks.planks, 1);
			fillWithMetadataBlocks(world, box, 13, 2, 7, 13, 2, 8, Blocks.planks, 1);
			fillWithMetadataBlocks(world, box, 12, 2, 9, 12, 3, 9, Blocks.planks, 1);
			fillWithMetadataBlocks(world, box, 9, 2, 9, 9, 3, 9, Blocks.planks, 1);
			placeBlockAtCurrentPosition(world, Blocks.planks, 1, 8, 3, 11, box);
			fillWithMetadataBlocks(world, box, 6, 2, 13, 6, 3, 13, Blocks.planks, 1);
			fillWithMetadataBlocks(world, box, 3, 2, 13, 3, 3, 13, Blocks.planks, 1);
			placeBlockAtCurrentPosition(world, Blocks.planks, 1, 1, 2, 12, box);
			placeBlockAtCurrentPosition(world, Blocks.planks, 1, 1, 2, 10, box);
			placeBlockAtCurrentPosition(world, Blocks.planks, 1, 1, 2, 8, box);
			placeBlockAtCurrentPosition(world, Blocks.planks, 1, 1, 2, 5, box);
			placeBlockAtCurrentPosition(world, Blocks.planks, 1, 4, 3, 9, box);
			//Wood Framing
			//North/Front
			int logW = this.getPillarMeta(4);
			int logN = this.getPillarMeta(8);

			fillWithBlocks(world, box, 0, 0, 3, 0, 3, 3, Blocks.log);
			fillWithMetadataBlocks(world, box, 1, 4, 3, 3, 4, 3, Blocks.log, logW);
			fillWithMetadataBlocks(world, box, 3, 4, 1, 3, 4, 2, Blocks.log, logN);
			placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 9, 1, 3, 3, box);
			placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 9, 3, 3, 1, box);
			fillWithMetadataBlocks(world, box, 1, 1, 3, 2, 1, 3, Blocks.wooden_slab, 1);
			fillWithMetadataBlocks(world, box, 3, 1, 1, 3, 1, 3, Blocks.wooden_slab, 1);
			fillWithBlocks(world, box, 3, 0, 0, 3, 3, 0, Blocks.log);
			fillWithMetadataBlocks(world, box, 4, 1, 0, 9, 1, 0, Blocks.wooden_slab, 1);
			placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 9, 4, 3, 0, box);
			placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 9, 9, 3, 0, box);
			fillWithBlocks(world, box, 10, 0, 0, 10, 3, 0, Blocks.log);
			fillWithMetadataBlocks(world, box, 10, 4, 1, 13, 4, 1, Blocks.log, logW);
			fillWithBlocks(world, box, 14, 0, 1, 14, 3, 1, Blocks.log);
			//East/Left
			fillWithBlocks(world, box, 14, 0, 3, 14, 3, 3, Blocks.log);
			fillWithBlocks(world, box, 14, 0, 8, 14, 3, 8, Blocks.log);
			fillWithBlocks(world, box, 14, 0, 10, 14, 3, 10, Blocks.log);
			placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 1, 14, 1, 2, box);
			fillWithMetadataBlocks(world, box, 14, 1, 4, 14, 1, 7, Blocks.wooden_slab, 1);
			placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 1, 14, 1, 9, box);
			placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 9, 14, 3, 2, box);
			placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 9, 14, 3, 4, box);
			placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 9, 14, 3, 7, box);
			placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 9, 14, 3, 9, box);
			//South/Back
			fillWithMetadataBlocks(world, box, 9, 4, 10, 13, 4, 10, Blocks.log, logW);
			placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 9, 13, 3, 10, box);
			fillWithBlocks(world, box, 9, 0, 14, 9, 3, 14, Blocks.log);
			fillWithBlocks(world, box, 7, 0, 14, 7, 3, 14, Blocks.log);
			fillWithBlocks(world, box, 2, 0, 14, 2, 3, 14, Blocks.log);
			fillWithBlocks(world, box, 0, 0, 14, 0, 3, 14, Blocks.log);
			fillWithMetadataBlocks(world, box, 1, 4, 14, 8, 4, 14, Blocks.log, logW);
			placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 1, 8, 1, 14, box);
			fillWithMetadataBlocks(world, box, 3, 1, 14, 6, 1, 14, Blocks.wooden_slab, 1);
			placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 1, 1, 1, 14, box);
			placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 9, 8, 3, 14, box);
			placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 9, 1, 3, 14, box);
			//West/Right
			fillWithBlocks(world, box, 0, 0, 9, 0, 3, 9, Blocks.log);
			fillWithMetadataBlocks(world, box, 0, 1, 10, 0, 1, 13, Blocks.wooden_slab, 1);
			fillWithMetadataBlocks(world, box, 0, 1, 4, 0, 1, 8, Blocks.wooden_slab, 1);
			placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 9, 0, 3, 13, box);
			placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 9, 0, 3, 10, box);
			placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 9, 0, 3, 8, box);
			placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 9, 0, 3, 4, box);

			int stairW = this.getStairMeta(0);
			int stairE = this.getStairMeta(1);
			int stairN = this.getStairMeta(2);
			int stairS = this.getStairMeta(3);

			//Floor
			placeBlockAtCurrentPosition(world, Blocks.planks, 1, 11, 0, 2, box);
			fillWithMetadataBlocks(world, box, 9, 0, 3, 12, 0, 8, Blocks.planks, 1);
			fillWithMetadataBlocks(world, box, 5, 0, 2, 8, 0, 8, Blocks.planks, 1);
			fillWithMetadataBlocks(world, box, 2, 0, 5, 4, 0, 8, Blocks.planks, 1);
			placeBlockAtCurrentPosition(world, Blocks.planks, 1, 4, 0, 9, box);
			fillWithMetadataBlocks(world, box, 2, 0, 10, 7, 0, 12, Blocks.planks, 1);
			placeBlockAtCurrentPosition(world, Blocks.planks, 1, 8, 0, 11, box);
			fillWithBlocks(world, box, 13, 1, 0, 14, 1, 0, Blocks.fence);
			//Porches
			fillWithBlocks(world, box, 10, 0, 1, 13, 0, 1, Blocks.planks);
			fillWithMetadataBlocks(world, box, 11, 0, 0, 12, 0, 0, Blocks.spruce_stairs, stairN);
			fillWithMetadataBlocks(world, box, 13, 0, 0, 14, 0, 0, Blocks.planks, 1);
			fillWithBlocks(world, box, 12, 0, 10, 13, 0, 10, Blocks.planks);
			fillWithBlocks(world, box, 9, 0, 10, 11, 0, 11, Blocks.planks);
			fillWithBlocks(world, box, 9, 0, 12, 10, 0, 12, Blocks.planks);
			placeBlockAtCurrentPosition(world, Blocks.planks, 0, 9, 0, 13, box);
			for(int i = 0; i < 3; i++) {
				fillWithMetadataBlocks(world, box, 10 + i, 0, 13 - i, 11 + i, 0, 13 - i, Blocks.planks, 1);
				fillWithBlocks(world, box, 10 + i, 1, 13 - i, 11 + i, 1, 13 - i, Blocks.fence);
			}

			//Ceiling
			fillWithMetadataBlocks(world, box, 12, 4, 3, 12, 4, 8, Blocks.oak_stairs, stairW | 4);
			fillWithBlocks(world, box, 12, 5, 3, 12, 5, 8, Blocks.planks);
			fillWithBlocks(world, box, 10, 5, 3, 11, 6, 8, Blocks.planks);
			fillWithBlocks(world, box, 9, 5, 3, 9, 5, 8, Blocks.planks);
			fillWithMetadataBlocks(world, box, 9, 4, 3, 9, 4, 8, Blocks.oak_stairs, stairE | 4);
			fillWithBlocks(world, box, 8, 4, 5, 8, 4, 8, Blocks.planks);
			fillWithBlocks(world, box, 5, 4, 2, 8, 4, 4, Blocks.planks);
			fillWithBlocks(world, box, 1, 4, 5, 7, 4, 12, Blocks.planks);

			//Roofing
			//Framing
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairW, 1, 5, 3, box);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairW, 2, 6, 3, box);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairE | 4, 3, 6, 3, box);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairW, 3, 7, 3, box);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairE | 4, 4, 7, 3, box);
			fillWithMetadataBlocks(world, box, 4, 8, 3, 5, 8, 3, Blocks.wooden_slab, 1);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairW | 4, 5, 7, 3, box);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairE, 6, 7, 3, box);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairW | 4, 6, 6, 3, box);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairE, 7, 6, 3, box);
			fillWithMetadataBlocks(world, box, 2, 5, 3, 3, 5, 3, Blocks.planks, 1);
			placeBlockAtCurrentPosition(world, Blocks.planks, 1, 3, 5, 2, box);
			placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 1, 3, 5, 1, box);
			fillWithMetadataBlocks(world, box, 3, 4, 0, 14, 4, 0, Blocks.spruce_stairs, stairN);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairW, 8, 5, 1, box);
			placeBlockAtCurrentPosition(world, Blocks.planks, 1, 9, 5, 1, box);
			placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 1, 10, 5, 1, box);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairW, 9, 6, 1, box);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairE | 4, 10, 6, 1, box);
			fillWithMetadataBlocks(world, box, 10, 7, 1, 11, 7, 1, Blocks.wooden_slab, 1);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairW | 4, 11, 6, 1, box);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairE, 12, 6, 1, box);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairW | 4, 12, 5, 1, box);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairE, 13, 5, 1, box);
			fillWithMetadataBlocks(world, box, 14, 4, 1, 14, 4, 10, Blocks.spruce_stairs, stairE);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairE, 13, 5, 10, box);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairW | 4, 12, 5, 10, box);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairE, 12, 6, 10, box);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairW | 4, 11, 6, 10, box);
			fillWithMetadataBlocks(world, box, 10, 7, 10, 11, 7, 10, Blocks.wooden_slab, 1);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairE | 4, 10, 6, 10, box);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairW, 9, 6, 10, box);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairE | 4, 9, 5, 10, box);
			fillWithMetadataBlocks(world, box, 9, 4, 11, 9, 4, 14, Blocks.spruce_stairs, stairE);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairE, 8, 5, 14, box);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairW | 4, 7, 5, 14, box);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairE, 7, 6, 14, box);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairW | 4, 6, 6, 14, box);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairE, 6, 7, 14, box);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairW | 4, 5, 7, 14, box);
			fillWithMetadataBlocks(world, box, 4, 8, 14, 5, 8, 14, Blocks.wooden_slab, 1);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairE | 4, 4, 7, 14, box);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairW, 3, 7, 14, box);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairE | 4, 3, 6, 14, box);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairW, 2, 6, 14, box);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairE | 4, 2, 5, 14, box);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairW, 1, 5, 14, box);
			fillWithMetadataBlocks(world, box, 0, 4, 3, 0, 4, 14, Blocks.spruce_stairs, stairW);
			//Beams
			for(int z = 6; z <= 11; z += 5) {
				for(int i = 0; i < 3; i++) {
					placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairE | 4, 2 + i, 5 + i, z, box);
					placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairW | 4, 7 - i, 5 + i, z, box);
				}
			}

			//Main (LEFT)
			BrokenStairs roofStairs = new BrokenStairs();
			BrokenBlocks roofBlocks = new BrokenBlocks();

			roofStairs.setMetadata(stairW);
			fillWithBlocks(world, box, 4, 5, 1, 7, 5, 1, Blocks.wooden_slab);
			fillWithRandomizedBlocks(world, box, 4, 5, 2, 7, 5, 3, rand, roofBlocks); //TODO separate into stair/slab/block block selectors
			fillWithRandomizedBlocks(world, box, 8, 5, 2, 8, 5, 10, rand, roofBlocks);
			fillWithRandomizedBlocks(world, box, 9, 6, 2, 9, 6, 9, rand, roofStairs);
			randomlyFillWithBlocks(world, box, rand, 0.8F, 10, 7, 2, 11, 7, 9, Blocks.wooden_slab);
			roofStairs.setMetadata(stairE);
			fillWithRandomizedBlocks(world, box, 12, 6, 2, 12, 6, 9, rand, roofStairs); //i should redo like most of this shit
			fillWithRandomizedBlocks(world, box, 13, 5, 2, 13, 5, 9, rand, roofStairs);
			//Main (RIGHT)
			fillWithRandomizedBlocks(world, box, 8, 5, 11, 8, 5, 13, rand, roofStairs);
			fillWithRandomizedBlocks(world, box, 7, 6, 4, 7, 6, 13, rand, roofStairs);
			fillWithRandomizedBlocks(world, box, 6, 7, 4, 6, 7, 7, rand, roofStairs);
			fillWithRandomizedBlocks(world, box, 6, 7, 11, 6, 7, 13, rand, roofStairs);
			roofStairs.setMetadata(stairW);
			fillWithBlocks(world, box, 4, 8, 4, 5, 8, 5, Blocks.wooden_slab);
			placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 0, 5, 8, 6, box);
			placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 0, 4, 8, 11, box);
			fillWithBlocks(world, box, 4, 8, 12, 5, 8, 13, Blocks.wooden_slab);
			fillWithRandomizedBlocks(world, box, 3, 7, 4, 3, 7, 6, rand, roofStairs);
			fillWithRandomizedBlocks(world, box, 3, 7, 10, 3, 7, 13, rand, roofStairs);
			fillWithRandomizedBlocks(world, box, 2, 6, 4, 2, 6, 13, rand, roofStairs);
			fillWithRandomizedBlocks(world, box, 1, 5, 4, 1, 5, 13, rand, roofStairs);

			//Deco
			int metaN = getDecoMeta(3);
			int metaE = getDecoMeta(4);

			//Webs
			randomlyFillWithBlocks(world, box, rand, 0.05F, 12, 3, 3, 12, 3, 8, Blocks.web);
			randomlyFillWithBlocks(world, box, rand, 0.05F, 10, 4, 3, 11, 4, 8, Blocks.web);
			randomlyFillWithBlocks(world, box, rand, 0.05F, 5, 3, 2, 8, 3, 2, Blocks.web);
			randomlyFillWithBlocks(world, box, rand, 0.05F, 5, 3, 3, 9, 3, 8, Blocks.web);
			randomlyFillWithBlocks(world, box, rand, 0.05F, 2, 3, 5, 4, 3, 8, Blocks.web);
			randomlyFillWithBlocks(world, box, rand, 0.05F, 2, 3, 10, 7, 3, 12, Blocks.web);
			//Doors
			placeDoor(world, box, Blocks.wooden_door, 1, false, false, 11, 1, 2);
			placeDoor(world, box, Blocks.wooden_door, 1, false, rand.nextBoolean(), 4, 1, 9);
			placeDoor(world, box, Blocks.wooden_door, 2, false, rand.nextBoolean(), 8, 1, 11);
			//Windows
			randomlyFillWithBlocks(world, box, rand, 0.5F, 6, 2, 1, 7, 3, 1, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, 0.5F, 13, 2, 5, 13, 2, 6, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, 0.5F, 10, 2, 9, 11, 3, 9, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, 0.5F, 4, 2, 13, 5, 3, 13, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, 0.5F, 1, 2, 11, 1, 2, 11, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, 0.5F, 1, 2, 6, 1, 2, 7, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, 0.5F, 4, 6, 4, 5, 6, 4, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, 0.5F, 4, 6, 13, 5, 6, 13, Blocks.glass_pane);
			//Attic Access
			placeBlockAtCurrentPosition(world, Blocks.trapdoor, getDecoModelMeta(4) >> 2, 6, 4, 10, box);
			fillWithMetadataBlocks(world, box, 6, 2, 10, 6, 3, 10, Blocks.ladder, metaN);
			//Furniture
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairN | 4, 12, 1, 5, box); //tables
			placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 8, 12, 1, 6, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairS | 4, 12, 1, 7, box);
			fillWithMetadataBlocks(world, box, 9, 1, 4, 9, 1, 5, Blocks.dark_oak_stairs, stairE | 4);
			fillWithMetadataBlocks(world, box, 8, 1, 4, 8, 1, 5, Blocks.wooden_slab, 13);
			fillWithMetadataBlocks(world, box, 7, 1, 4, 7, 1, 5, Blocks.dark_oak_stairs, stairW | 4);
			placeBlockAtCurrentPosition(world, Blocks.dark_oak_stairs, stairS | 4, 8, 1, 2, box); //couch
			placeBlockAtCurrentPosition(world, Blocks.dark_oak_stairs, stairW, 7, 1, 2, box);
			placeBlockAtCurrentPosition(world, Blocks.dark_oak_stairs, stairS, 6, 1, 2, box);
			fillWithMetadataBlocks(world, box, 5, 1, 2, 5, 1, 3, Blocks.dark_oak_stairs, stairE);
			placeBlockAtCurrentPosition(world, Blocks.dark_oak_stairs, stairN, 5, 1, 4, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairW, 10, 1, 5, box); //chairs
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairN, 8, 1, 6, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairE, 9, 1, 8, box); //bookshelf
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairE | 4, 9, 2, 8, box);
			fillWithBlocks(world, box, 8, 1, 8, 8, 2, 8, Blocks.bookshelf);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairW, 7, 1, 8, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairW | 4, 7, 2, 8, box);
			fillWithMetadataBlocks(world, box, 7, 3, 8, 9, 3, 8, Blocks.wooden_slab, 1);
			placeBlockAtCurrentPosition(world, Blocks.double_stone_slab, 0, 4, 1, 5, box); //kitchen
			placeBlockAtCurrentPosition(world, rand.nextBoolean() ? ModBlocks.machine_electric_furnace_off : Blocks.furnace, metaN, 3, 1, 5, box); //idk why the meta is off between all these blocks and idc
			fillWithBlocks(world, box, 2, 1, 5, 2, 1, 6, Blocks.double_stone_slab);
			placeBlockAtCurrentPosition(world, Blocks.cauldron, 2, 2, 1, 7, box);
			placeBlockAtCurrentPosition(world, Blocks.double_stone_slab, 0, 2, 1, 8, box);
			placeBlockAtCurrentPosition(world, Blocks.double_stone_slab, 0, 4, 3, 5, box);
			placeBlockAtCurrentPosition(world, Blocks.redstone_lamp, 0, 3, 3, 5, box);
			placeBlockAtCurrentPosition(world, Blocks.double_stone_slab, 0, 2, 3, 5, box);
			placeBlockAtCurrentPosition(world, ModBlocks.steel_wall, metaN, 3, 3, 6, box);
			placeBlockAtCurrentPosition(world, ModBlocks.radiorec, getDecoMeta(2), 8, 2, 2, box);
			placeBlockAtCurrentPosition(world, Blocks.flower_pot, 0, 7, 2, 4, box);

			fillWithBlocks(world, box, 2, 1, 12, 3, 1, 12, Blocks.bookshelf); //bookshelf/desk
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairE | 4, 4, 1, 12, box);
			placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 8, 5, 1, 12, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairW | 4, 6, 1, 12, box);
			fillWithBlocks(world, box, 7, 1, 12, 7, 2, 12, Blocks.bookshelf);
			placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 5, 5, 1, 11, box); //seat
			placeBed(world, box, 1, 3, 1, 10);
			placeBlockAtCurrentPosition(world, Blocks.flower_pot, 0, 4, 2, 12, box);
			placeBlockAtCurrentPosition(world, ModBlocks.deco_computer, getDecoModelMeta(0), 5, 2, 12, box);

			fillWithMetadataBlocks(world, box, 4, 5, 5, 5, 5, 5, Blocks.dark_oak_stairs, stairS | 4); //seat and desk
			placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 1, 4, 5, 6, box);
			placeBlockAtCurrentPosition(world, ModBlocks.crate_can, 0, 7, 5, 7, box); //conserve crates
			placeBlockAtCurrentPosition(world, ModBlocks.crate_can, 0, 2, 5, 9, box);
			placeBlockAtCurrentPosition(world, ModBlocks.crate_can, 0, 3, 5, 11, box);
			if(rand.nextBoolean())
				placeBlockAtCurrentPosition(world, ModBlocks.machine_diesel, metaE, 7, 5, 9, box);
			placeBlockAtCurrentPosition(world, rand.nextBoolean() ? ModBlocks.crate_weapon : ModBlocks.crate, 0, 6, 5, 12, box);

			//inventories
			generateInvContents(world, box, rand, ModBlocks.filing_cabinet, getDecoModelMeta(2), 7, 1, 10, ItemPool.getPool(ItemPoolsComponent.POOL_OFFICE_TRASH), 4);
			generateInvContents(world, box, rand, Blocks.chest, metaE, 7, 5, 5, ItemPool.getPool(ItemPoolsLegacy.POOL_GENERIC), 8);
			//loot
			placeBlockAtCurrentPosition(world, ModBlocks.deco_loot, 0, 3, 2, 12, box);
			LootGenerator.lootBookLore(world, getXWithOffset(3, 12), getYWithOffset(2), getZWithOffset(3, 12), HbmChestContents.generateLabBook(rand)); //TODO write more lore
			placeBlockAtCurrentPosition(world, ModBlocks.deco_loot, 0, 5, 6, 5, box);
			LootGenerator.lootMakeshiftGun(world, getXWithOffset(5, 5), getYWithOffset(6), getZWithOffset(5, 5));
			placeRandomBobble(world, box, rand, 5, 5, 12);

			return true;
		}

		//i don't like this class
		public static class BrokenStairs extends BlockSelector {
			//man.
			public void setMetadata(int meta) {
				this.selectedBlockMetaData = meta;
			}
			//mannnnnnnn.
			@Override
			public int getSelectedBlockMetaData() {
				return this.field_151562_a instanceof BlockStairs ? this.selectedBlockMetaData : 0;
			}

			@Override
			public void selectBlocks(Random rand, int posX, int posY, int posZ, boolean notInterior) {
				float chance = rand.nextFloat();

				if(chance < 0.7)
					this.field_151562_a = Blocks.oak_stairs;
				else if(chance < 0.97)
					this.field_151562_a = Blocks.wooden_slab;
				else
					this.field_151562_a = Blocks.air;
			}
		}

		//this fucking sucks. i am racist against the blockselector class
		public static class BrokenBlocks extends BlockSelector {

			@Override
			public void selectBlocks(Random rand, int posX, int posY, int posZ, boolean notInterior) {
				float chance = rand.nextFloat();

				if(chance < 0.6) {
					this.field_151562_a = Blocks.planks;
					this.selectedBlockMetaData = 0;
				} else if(chance < 0.8) {
					this.field_151562_a = Blocks.oak_stairs;
					this.selectedBlockMetaData = rand.nextInt(4);
				} else {
					this.field_151562_a = Blocks.wooden_slab;
					this.selectedBlockMetaData = 0;
				}
			}
		}
	}
}

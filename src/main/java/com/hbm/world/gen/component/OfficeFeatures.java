package com.hbm.world.gen.component;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.itempool.ItemPool;
import com.hbm.itempool.ItemPoolsComponent;
import com.hbm.itempool.ItemPoolsLegacy;
import com.hbm.lib.HbmChestContents;
import com.hbm.util.LootGenerator;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;

//Oh my fucking god TM
public class OfficeFeatures {
	
	public static void registerComponents() {
		MapGenStructureIO.func_143031_a(LargeOffice.class, "NTMLargeOffice");
		MapGenStructureIO.func_143031_a(LargeOfficeCorner.class, "NTMLargeOfficeCorner");
	}
	
	public static class LargeOffice extends Component {
		
		private static ConcreteBricks ConcreteBricks = new ConcreteBricks();
		
		private boolean[] hasPlacedLoot = new boolean[2];
		
		public LargeOffice() {
			super();
		}
		
		public LargeOffice(Random rand, int minX, int minZ) {
			super(rand, minX, 64, minZ, 14, 5, 12);
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
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			if(!this.setAverageHeight(world, box, this.boundingBox.minY)) {
				return false;
			}
			
			this.boundingBox.offset(0, -1, 0);
			
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 5, 0, 14, 1, -1, box);
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 0, 2, 14, 7, -1, box);
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 0, 8, 8, 12, 0, box);
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 9, 8, 14, 12, -1, box);
			
			fillWithAir(world, box, 1, 1, 3, 4, 3, 6);
			fillWithAir(world, box, 6, 1, 1, 14 - 1, 3, 6);
			fillWithAir(world, box, 10, 1, 7, 14 - 1, 3, 12 - 1);
			
			//Pillars
			//Back
			fillWithBlocks(world, box, 0, 0, 2, 0, 4, 2, ModBlocks.concrete_pillar);
			fillWithBlocks(world, box, 5, 0, 0, 5, 4, 0, ModBlocks.concrete_pillar);
			fillWithBlocks(world, box, 14, 0, 0, 14, 4, 0, ModBlocks.concrete_pillar);
			//Front
			fillWithBlocks(world, box, 0, 0, 7, 0, 3, 7, ModBlocks.concrete_pillar);
			fillWithBlocks(world, box, 0, 0, 12, 0, 3, 12, ModBlocks.concrete_pillar);
			fillWithBlocks(world, box, 3, 0, 12, 3, 3, 12, ModBlocks.concrete_pillar);
			fillWithBlocks(world, box, 6, 0, 12, 6, 3, 12, ModBlocks.concrete_pillar);
			fillWithBlocks(world, box, 9, 0, 12, 9, 3, 12, ModBlocks.concrete_pillar);
			fillWithBlocks(world, box, 9, 0, 7, 9, 3, 7, ModBlocks.concrete_pillar);
			fillWithBlocks(world, box, 14, 0, 12, 14, 4, 12, ModBlocks.concrete_pillar);
			
			//Walls
			//Back
			fillWithRandomizedBlocks(world, box, 1, 0, 2, 5, 4, 2, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 5, 0, 1, 5, 4, 1, rand, ConcreteBricks);
			
			fillWithRandomizedBlocks(world, box, 6, 0, 0, 14 - 1, 1, 0, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 6, 2, 0, 6, 2, 0, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 9, 2, 0, 10, 2, 0, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 14 - 1, 2, 0, 14 - 1, 2, 0, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 6, 3, 0, 14 - 1, 4, 0, rand, ConcreteBricks);
			//Right
			fillWithRandomizedBlocks(world, box, 14, 0, 1, 14, 1, 12 - 1, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 14, 2, 1, 14, 2, 2, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 14, 2, 5, 14, 2, 7, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 14, 2, 12 - 2, 14, 2, 12 - 1, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 14, 3, 1, 14, 4, 12 - 1, rand, ConcreteBricks);
			//Front
			fillWithRandomizedBlocks(world, box, 0, 4, 12, 14 - 1, 4, 12, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 10, 0, 12, 14 - 1, 1, 12, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 10, 2, 12, 10, 2, 12, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 14 - 1, 2, 12, 14 - 1, 2, 12, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 10, 3, 12, 14 - 1, 3, 12, rand, ConcreteBricks);
			
			fillWithRandomizedBlocks(world, box, 9, 0, 8, 9, 3, 12 - 1, rand, ConcreteBricks);
			
			fillWithRandomizedBlocks(world, box, 1, 0, 7, 8, 0, 7, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 1, 1, 7, 1, 2, 7, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 4, 1, 7, 8, 3, 7, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 1, 3, 7, 3, 3, 7, rand, ConcreteBricks);
			//Left
			fillWithRandomizedBlocks(world, box, 0, 4, 3, 0, 4, 12 - 1, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 0, 0, 3, 0, 1, 6, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 0, 2, 3, 0, 3, 3, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 0, 2, 6, 0, 3, 6, rand, ConcreteBricks);
			//Interior
			fillWithRandomizedBlocks(world, box, 5, 1, 3, 5, 3, 5, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 5, 3, 6, 5, 3, 6, rand, ConcreteBricks);
			
			//Trim
			randomlyFillWithBlocks(world, box, rand, 0.85F, 0, 5, 2, 5, 5, 2, Blocks.stone_slab);
			randomlyFillWithBlocks(world, box, rand, 0.85F, 5, 5, 1, 5, 5, 1, Blocks.stone_slab);
			randomlyFillWithBlocks(world, box, rand, 0.85F, 5, 5, 0, 14, 5, 0, Blocks.stone_slab);
			randomlyFillWithBlocks(world, box, rand, 0.85F, 14, 5, 1, 14, 5, 12, Blocks.stone_slab);
			randomlyFillWithBlocks(world, box, rand, 0.85F, 0, 5, 12, 14 - 1, 5, 12, Blocks.stone_slab);
			randomlyFillWithBlocks(world, box, rand, 0.85F, 0, 5, 3, 0, 5, 12 - 1, Blocks.stone_slab);
			
			//Floor
			fillWithMetadataBlocks(world, box, 1, 0, 3, 4, 0, 6, Blocks.wool, 13); //Green Wool
			fillWithBlocks(world, box, 5, 0, 3, 5, 0, 6, ModBlocks.brick_light);
			fillWithBlocks(world, box, 6, 0, 1, 14 - 1, 0, 6, ModBlocks.brick_light);
			fillWithBlocks(world, box, 10, 0, 7, 14 - 1, 0, 12 - 1, ModBlocks.brick_light);
			//Ceiling
			fillWithBlocks(world, box, 6, 4, 1, 14 - 1, 4, 2, ModBlocks.brick_light);
			fillWithBlocks(world, box, 1, 4, 3, 14 - 1, 4, 12 - 1, ModBlocks.brick_light);
			
			//Decorations
			//Carpet
			fillWithMetadataBlocks(world, box, 9, 1, 3, 11, 1, 6, Blocks.carpet, 8); //Light gray
			//Windows
			randomlyFillWithBlocks(world, box, rand, 0.75F, 0, 2, 4, 0, 3, 5, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, 0.75F, 7, 2, 0, 8, 2, 0, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, 0.75F, 14 - 3, 2, 0, 14 - 2, 2, 0, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, 0.75F, 14, 2, 3, 14, 2, 4, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, 0.75F, 14, 2, 8, 14, 2, 9, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, 0.75F, 14 - 3, 2, 12, 14 - 2, 2, 12, Blocks.glass_pane);
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
			fillWithMetadataBlocks(world, box, 11, 1, 1, 14 - 1, 1, 1, Blocks.spruce_stairs, stairMetaSU);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairMetaNU, 14 - 1, 1, 2, box);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairMetaSU, 14 - 1, 1, 3, box);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairMetaWU, 14 - 1, 1, 4, box);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairMetaNU, 14 - 1, 1, 5, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairMetaN, 11, 1, 2, box); //Chaiw ;3
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairMetaE, 14 - 2, 1, 4, box); //Chaiw :333
			placeBlockAtCurrentPosition(world, ModBlocks.deco_computer, getDecoModelMeta(1), 14 - 3, 2, 1, box); //South-facing Computer :3
			placeBlockAtCurrentPosition(world, ModBlocks.deco_computer, getDecoModelMeta(2), 14 - 1, 2, 5, box); //West-facing Computer ^w^
			placeBlockAtCurrentPosition(world, Blocks.flower_pot, 0, 14 - 1, 2, 3, box);
			placeBlockAtCurrentPosition(world, ModBlocks.radiorec, getDecoMeta(5), 14 - 1, 2, 2, box); //Wadio
			//Desk 4 DX
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairMetaEU, 10, 1, 8, box);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairMetaWU, 11, 1, 8, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairMetaN, 10, 1, 9, box); //Chaiw ;3
			placeBlockAtCurrentPosition(world, ModBlocks.deco_computer, getDecoModelMeta(1), 10, 2, 8, box); //South-facing Computer :33
			//Desk 5 :333
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairMetaSU, 14 - 1, 1, 12 - 3, box);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairMetaWU, 14 - 1, 1, 12 - 2, box);
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairMetaNU, 14 - 1, 1, 12 - 1, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairMetaE, 14 - 3, 1, 12 - 1, box); //UwU... Chaiw!!!! :333 I wove chaiws XD :333 OwO what's this?? chaiw???? :333333333333333333
			placeBlockAtCurrentPosition(world, ModBlocks.deco_computer, getDecoModelMeta(2), 14 - 1, 2, 12 - 1, box); //West-facing Computer >w<
			//Cobwebs pwobabwy
			//Maybe make a method for this eventually?
			//Something where the tops of ceilings + empty corners along walls get most cobwebs,
			//with no cobwebs hanging midair + it not being performance intensive
			randomlyFillWithBlocks(world, box, rand, 0.25F, 1, 3, 3, 4, 3, 6, Blocks.web);
			randomlyFillWithBlocks(world, box, rand, 0.25F, 6, 3, 1, 14 - 1, 3, 6, Blocks.web);
			randomlyFillWithBlocks(world, box, rand, 0.25F, 10, 3, 7, 14 - 1, 3, 12 - 1, Blocks.web);
			//Doors
			placeDoor(world, box, ModBlocks.door_office, 3, false, rand.nextBoolean(), 2, 1, 7);
			placeDoor(world, box, ModBlocks.door_office, 3, true, rand.nextBoolean(), 3, 1, 7);
			placeDoor(world, box, ModBlocks.door_office, 0, false, rand.nextBoolean(), 5, 1, 6);
			
			//Woot
			if(!this.hasPlacedLoot[0])
				this.hasPlacedLoot[0] = generateInvContents(world, box, rand, ModBlocks.filing_cabinet, getDecoModelMeta(0), 14 - 4, 1, 12 - 1, ItemPool.getPool(ItemPoolsComponent.POOL_OFFICE_TRASH), 8);
			if(!this.hasPlacedLoot[1]) {
				this.hasPlacedLoot[1] = generateLockableContents(world, box, rand, ModBlocks.safe, getDecoMeta(3), 6, 1, 1, ItemPool.getPool(ItemPoolsComponent.POOL_MACHINE_PARTS), 10, 0.5D);
				if(rand.nextInt(2) == 0)
					generateLoreBook(world, box, 6, 1, 1, 7, HbmChestContents.generateOfficeBook(rand));
			}
			
			//0b00/0 West, 0b01/1 East, 0b10/2 North, 0b11/3 South, 0b100/4 West UD, 0b101 East UD, 0b110 North UD, 0b111 South UD
			return true;
		}
		
	}
	
	//bob i could kiss you
	public static class LargeOfficeCorner extends Component {
		
		private static ConcreteBricks ConcreteBricks = new ConcreteBricks();
		
		//no placed loot? it's because i would've had to do fucking 12 of them and i don't have an easy standardized system for that
		
		public LargeOfficeCorner() {
			super();
		}
		
		public LargeOfficeCorner(Random rand, int minX, int minZ) {
			super(rand, minX, 64, minZ, 11, 15, 14);
		}
		
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			if(!this.setAverageHeight(world, box, this.boundingBox.minY)) {
				return false;
			}
			
			this.boundingBox.offset(0, -1, 0);
			
			int pillarMetaWE = getPillarMeta(4);
			int pillarMetaNS = getPillarMeta(8);
			
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 4, 0, 11, 2, -1, box);
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 1, 3, 11, 13, -1, box);
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 0, 14, 4, 14, -1, box);
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 0, 10, 0, 13, -1, box);
			
			fillWithAir(world, box, 1, 1, 11, 3, 12, 13);
			fillWithAir(world, box, 4, 1, 4, 10, 12, 12);
			fillWithAir(world, box, 2, 1, 4, 3, 12, 10);
			fillWithAir(world, box, 5, 1, 1, 10, 12, 2);
			fillWithAir(world, box, 5, 13, 1, 8, 14, 2);
			
			//Back Wall
			fillWithBlocks(world, box, 1, 0, 3, 5, 0, 3, ModBlocks.concrete_pillar);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_pillar, pillarMetaWE, 6, 0, 3, box);
			fillWithBlocks(world, box, 7, 0, 3, 10, 0, 3, ModBlocks.concrete_pillar);
			fillWithBlocks(world, box, 4, 0, 0, 4, 0, 2, ModBlocks.concrete_pillar);
			fillWithBlocks(world, box, 5, 0, 0, 11, 0, 0, ModBlocks.concrete_pillar);
			fillWithBlocks(world, box, 11, 1, 0, 11, 12, 0, ModBlocks.concrete_pillar);
			fillWithBlocks(world, box, 1, 1, 3, 1, 12, 3, ModBlocks.concrete_pillar);
			fillWithRandomizedBlocks(world, box, 4, 1, 0, 10, 12, 0, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 4, 13, 0, 9, 13, 0, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 4, 14, 0, 8, 14, 0, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 4, 15, 0, 7, 15, 0, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 2, 1, 3, 5, 2, 3, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 7, 1, 3, 10, 2, 3, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 2, 3, 3, 10, 7, 3, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 2, 8, 3, 9, 10, 3, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 2, 11, 3, 10, 12, 3, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 4, 13, 3, 4, 14, 3, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 6, 13, 3, 9, 13, 3, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 6, 14, 3, 8, 14, 3, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 4, 15, 3, 7, 15, 3, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 4, 1, 1, 4, 15, 2, rand, ConcreteBricks);
			//Right Wall
			fillWithBlocks(world, box, 11, 0, 1, 11, 0, 12, ModBlocks.concrete_pillar);
			fillWithBlocks(world, box, 11, 0, 13, 11, 12, 13, ModBlocks.concrete_pillar);
			fillWithBlocks(world, box, 11, 1, 3, 11, 7, 3, ModBlocks.concrete_pillar);
			fillWithMetadataBlocks(world, box, 11, 4, 1, 11, 4, 2, ModBlocks.concrete_pillar, pillarMetaNS);
			fillWithMetadataBlocks(world, box, 11, 8, 1, 11, 8, 12, ModBlocks.concrete_pillar, pillarMetaNS);
			fillWithBlocks(world, box, 11, 9, 3, 11, 11, 3, ModBlocks.concrete_pillar);
			fillWithMetadataBlocks(world, box, 11, 12, 1, 11, 12, 12, ModBlocks.concrete_pillar, pillarMetaNS);
			fillWithRandomizedBlocks(world, box, 11, 9, 1, 11, 11, 2, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 11, 5, 1, 11, 7, 2, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 11, 1, 1, 11, 3, 2, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 11, 1, 4, 11, 7, 12, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 11, 9, 4, 11, 9, 12, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 11, 10, 4, 11, 10, 4, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 11, 10, 8, 11, 10, 8, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 11, 10, 12, 11, 10, 12, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 11, 11, 4, 11, 11, 12, rand, ConcreteBricks);
			//Front Wall
			fillWithBlocks(world, box, 4, 0, 13, 10, 0, 13, ModBlocks.concrete_pillar);
			fillWithBlocks(world, box, 4, 0, 14, 4, 12, 14, ModBlocks.concrete_pillar);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_pillar, 0, 3, 0, 14, box);
			fillWithMetadataBlocks(world, box, 1, 0, 14, 2, 0, 14, ModBlocks.concrete_pillar, pillarMetaWE);
			fillWithBlocks(world, box, 0, 0, 14, 0, 12, 14, ModBlocks.concrete_pillar);
			fillWithRandomizedBlocks(world, box, 4, 1, 13, 10, 1, 13, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 10, 2, 13, 10, 3, 13, rand, ConcreteBricks);
			fillWithBlocks(world, box, 9, 2, 13, 9, 3, 13, ModBlocks.concrete_pillar);
			fillWithBlocks(world, box, 6, 2, 13, 6, 3, 13, ModBlocks.concrete_pillar);
			fillWithRandomizedBlocks(world, box, 4, 2, 13, 5, 3, 13, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 4, 4, 13, 10, 5, 13, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 10, 6, 13, 10, 7, 13, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 4, 6, 13, 5, 7, 13, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 4, 8, 13, 10, 9, 13, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 10, 10, 13, 10, 11, 13, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 4, 10, 13, 5, 11, 13, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 4, 12, 13, 10, 12, 13, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 3, 1, 14, 3, 2, 14, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 1, 3, 14, 3, 5, 14, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 1, 8, 14, 3, 9, 14, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 1, 12, 14, 3, 12, 14, rand, ConcreteBricks);
			//Left Wall
			fillWithMetadataBlocks(world, box, 0, 0, 12, 0, 0, 13, ModBlocks.concrete_pillar, pillarMetaNS);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_pillar, 0, 0, 0, 11, box);
			fillWithBlocks(world, box, 0, 0, 10, 0, 12, 10, ModBlocks.concrete_pillar);
			fillWithBlocks(world, box, 1, 0, 4, 1, 0, 10, ModBlocks.concrete_pillar);
			fillWithBlocks(world, box, 1, 0, 3, 1, 12, 3, ModBlocks.concrete_pillar);
			fillWithRandomizedBlocks(world, box, 0, 1, 11, 0, 2, 11, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 0, 3, 11, 0, 5, 13, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 0, 8, 11, 0, 9, 13, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 0, 12, 11, 0, 12, 13, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 1, 1, 4, 1, 1, 10, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 1, 2, 9, 1, 3, 10, rand, ConcreteBricks);
			fillWithBlocks(world, box, 1, 2, 8, 1, 3, 8, ModBlocks.concrete_pillar);
			fillWithBlocks(world, box, 1, 2, 5, 1, 3, 5, ModBlocks.concrete_pillar);
			fillWithRandomizedBlocks(world, box, 1, 2, 4, 1, 3, 4, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 1, 4, 4, 1, 5, 10, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 1, 6, 9, 1, 7, 10, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 1, 6, 4, 1, 7, 4, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 1, 8, 4, 1, 9, 10, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 1, 10, 9, 1, 11, 10, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 1, 10, 4, 1, 11, 4, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 1, 12, 4, 1, 12, 10, rand, ConcreteBricks);
			
			//Floor 1
			fillWithMetadataBlocks(world, box, 5, 0, 1, 10, 0, 2, ModBlocks.concrete_pillar, pillarMetaWE);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_pillar, pillarMetaWE, 6, 0, 3, box);
			fillWithMetadataBlocks(world, box, 2, 0, 4, 10, 0, 10, Blocks.planks, 1);
			fillWithMetadataBlocks(world, box, 3, 0, 11, 10, 0, 11, Blocks.planks, 1);
			fillWithMetadataBlocks(world, box, 4, 0, 12, 10, 0, 12, Blocks.planks, 1);
			fillWithMetadataBlocks(world, box, 1, 0, 11, 2, 0, 13, Blocks.wool, 7); //Grey
			fillWithMetadataBlocks(world, box, 3, 0, 12, 3, 0, 13, Blocks.wool, 7);
			//Floor 2
			fillWithMetadataBlocks(world, box, 5, 4, 1, 5, 4, 3, ModBlocks.concrete_pillar, pillarMetaNS);
			fillWithMetadataBlocks(world, box, 2, 4, 4, 10, 4, 12, Blocks.planks, 1);
			fillWithMetadataBlocks(world, box, 1, 4, 11, 1, 4, 13, Blocks.planks, 1);
			fillWithMetadataBlocks(world, box, 2, 4, 13, 3, 4, 13, Blocks.planks, 1);
			//Floor 3
			fillWithMetadataBlocks(world, box, 10, 8, 1, 10, 8, 3, ModBlocks.concrete_pillar, pillarMetaNS);
			fillWithMetadataBlocks(world, box, 2, 8, 4, 10, 8, 12, Blocks.planks, 1);
			fillWithMetadataBlocks(world, box, 1, 8, 11, 1, 8, 13, Blocks.planks, 1);
			fillWithMetadataBlocks(world, box, 2, 8, 13, 3, 8, 13, Blocks.planks, 1);
			//Ceiling
			fillWithMetadataBlocks(world, box, 5, 12, 1, 5, 12, 2, ModBlocks.concrete_pillar, pillarMetaNS);
			fillWithBlocks(world, box, 10, 12, 1, 10, 12, 2, ModBlocks.asphalt);
			fillWithBlocks(world, box, 9, 13, 1, 9, 13, 2, ModBlocks.asphalt);
			fillWithBlocks(world, box, 8, 14, 1, 8, 14, 2, ModBlocks.asphalt);
			fillWithBlocks(world, box, 5, 15, 1, 7, 15, 2, ModBlocks.asphalt);
			fillWithBlocks(world, box, 2, 12, 4, 10, 12, 12, ModBlocks.brick_light);
			fillWithBlocks(world, box, 1, 12, 11, 1, 12, 13, ModBlocks.brick_light);
			fillWithBlocks(world, box, 2, 12, 13, 3, 12, 13, ModBlocks.brick_light);
			//Staircase
			int EastStairMeta = getStairMeta(1);
			int WestStairMeta = getStairMeta(0);
			int EastStairMetaUD = EastStairMeta | 4;
			int WestStairMetaUD = WestStairMeta | 4;
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, EastStairMeta, 9, 1, 1, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, WestStairMetaUD, 8, 1, 1, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, EastStairMeta, 8, 2, 1, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, WestStairMetaUD, 7, 2, 1, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, EastStairMeta, 7, 3, 1, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, WestStairMetaUD, 6, 3, 1, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, EastStairMeta, 6, 4, 1, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, EastStairMetaUD, 6, 4, 2, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, WestStairMeta, 6, 5, 2, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, EastStairMetaUD, 7, 5, 2, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, WestStairMeta, 7, 6, 2, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, EastStairMetaUD, 8, 6, 2, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, WestStairMeta, 8, 7, 2, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, EastStairMetaUD, 9, 7, 2, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, WestStairMeta, 9, 8, 2, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, WestStairMetaUD, 9, 8, 1, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, EastStairMeta, 9, 9, 1, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, WestStairMetaUD, 8, 9, 1, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, EastStairMeta, 8, 10, 1, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, WestStairMetaUD, 7, 10, 1, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, EastStairMeta, 7, 11, 1, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, WestStairMetaUD, 6, 11, 1, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, EastStairMeta, 6, 12, 1, box);
			
			//Decoration already? holy fuck it's been like 20 minutes
			//Windows
			randomlyFillWithBlocks(world, box, rand, 0.75F, 11, 10, 5, 11, 10, 7, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, 0.75F, 11, 10, 9, 11, 10, 11, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, 0.75F, 7, 2, 13, 8, 3, 13, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, 0.75F, 6, 6, 13, 9, 7, 13, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, 0.75F, 6, 10, 13, 9, 11, 13, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, 0.75F, 1, 6, 14, 3, 7, 14, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, 0.75F, 1, 10, 14, 3, 11, 14, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, 0.75F, 0, 6, 11, 0, 7, 13, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, 0.75F, 0, 10, 11, 0, 11, 13, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, 0.75F, 1, 2, 6, 1, 3, 7, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, 0.75F, 1, 6, 5, 1, 7, 8, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, 0.75F, 1, 10, 5, 1, 11, 8, Blocks.glass_pane);
			//Trim
			randomlyFillWithBlocks(world, box, rand, 0.85F, 10, 13, 0, 10, 13, 0, Blocks.stone_slab);
			randomlyFillWithBlocks(world, box, rand, 0.85F, 11, 13, 0, 11, 13, 13, Blocks.stone_slab);
			randomlyFillWithBlocks(world, box, rand, 0.85F, 4, 13, 13, 10, 13, 13, Blocks.stone_slab);
			randomlyFillWithBlocks(world, box, rand, 0.85F, 0, 13, 14, 4, 13, 14, Blocks.stone_slab);
			randomlyFillWithBlocks(world, box, rand, 0.85F, 0, 13, 10, 0, 13, 13, Blocks.stone_slab);
			randomlyFillWithBlocks(world, box, rand, 0.85F, 1, 13, 3, 1, 13, 10, Blocks.stone_slab);
			randomlyFillWithBlocks(world, box, rand, 0.85F, 2, 13, 3, 3, 13, 3, Blocks.stone_slab);
			//Interior Walls
			fillWithRandomizedBlocks(world, box, 4, 5, 12, 4, 6, 12, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 4, 5, 10, 4, 6, 10, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 4, 7, 10, 4, 7, 12, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 2, 5, 10, 3, 7, 10, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 4, 9, 10, 4, 11, 12, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 2, 11, 10, 3, 11, 10, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 2, 9, 10, 2, 10, 10, rand, ConcreteBricks);
			//Doors
			placeDoor(world, box, Blocks.wooden_door, 3, false, rand.nextBoolean(), 1, 1, 14);
			placeDoor(world, box, Blocks.wooden_door, 3, true, rand.nextBoolean(), 2, 1, 14);
			placeDoor(world, box, Blocks.wooden_door, 0, false, rand.nextBoolean(), 0, 1, 12);
			placeDoor(world, box, Blocks.wooden_door, 0, true, rand.nextBoolean(), 0, 1, 13);
			placeDoor(world, box, ModBlocks.door_office, 3, false, rand.nextBoolean(), 6, 1, 3);
			placeDoor(world, box, ModBlocks.door_office, 3, false, rand.nextBoolean(), 5, 5, 3);
			placeDoor(world, box, ModBlocks.door_office, 2, false, rand.nextBoolean(), 4, 5, 11);
			placeDoor(world, box, ModBlocks.door_office, 3, false, rand.nextBoolean(), 10, 9, 3);
			placeDoor(world, box, ModBlocks.door_office, 1, false, rand.nextBoolean(), 3, 9, 10);
			placeDoor(world, box, ModBlocks.door_metal, 3, false, rand.nextBoolean(), 5, 13, 3);
			//Furniture
			//Floor 1
			int NorthStairMeta = getStairMeta(2);
			int SouthStairMeta = getStairMeta(3);
			int NorthStairMetaUD = NorthStairMeta | 4;
			int SouthStairMetaUD = SouthStairMeta | 4;
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, EastStairMeta, 2, 1, 5, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, SouthStairMeta, 2, 1, 7, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, EastStairMeta, 2, 1, 8, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, NorthStairMeta, 2, 1, 9, box);
			placeBlockAtCurrentPosition(world, Blocks.dark_oak_stairs, SouthStairMetaUD, 8, 1, 4, box);
			fillWithMetadataBlocks(world, box, 8, 1, 5, 8, 1, 7, Blocks.wooden_slab, 13);
			placeBlockAtCurrentPosition(world, Blocks.dark_oak_stairs, NorthStairMetaUD, 8, 1, 8, box);
			placeBlockAtCurrentPosition(world, Blocks.dark_oak_stairs, WestStairMetaUD, 9, 1, 8, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, WestStairMeta, 9, 1, 5, box);
			placeBlockAtCurrentPosition(world, Blocks.flower_pot, 0, 9, 1, 4, box);
			placeBlockAtCurrentPosition(world, Blocks.flower_pot, 10, 8, 2, 7, box);
			
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, EastStairMeta, 6, 1, 12, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, NorthStairMeta, 7, 1, 12, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, WestStairMeta, 8, 1, 12, box);
			placeBlockAtCurrentPosition(world, Blocks.dark_oak_stairs, SouthStairMetaUD, 10, 1, 11, box);
			placeBlockAtCurrentPosition(world, Blocks.dark_oak_stairs, NorthStairMetaUD, 10, 1, 12, box);
			placeBlockAtCurrentPosition(world, Blocks.flower_pot, 0, 10, 2, 11, box);
			//Floor 2
			placeBlockAtCurrentPosition(world, Blocks.dark_oak_stairs, WestStairMetaUD, 4, 5, 4, box);
			placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 13, 3, 5, 4, box);
			placeBlockAtCurrentPosition(world, Blocks.dark_oak_stairs, EastStairMetaUD, 2, 5, 4, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, NorthStairMeta, 3, 5, 5, box);
			placeBlockAtCurrentPosition(world, ModBlocks.deco_computer, getDecoModelMeta(1), 3, 6, 4, box);
			placeBlockAtCurrentPosition(world, Blocks.dark_oak_stairs, EastStairMetaUD, 3, 5, 7, box);
			placeBlockAtCurrentPosition(world, Blocks.dark_oak_stairs, SouthStairMetaUD, 4, 5, 7, box);
			placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 13, 4, 5, 8, box);
			placeBlockAtCurrentPosition(world, Blocks.dark_oak_stairs, NorthStairMetaUD, 4, 5, 9, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, EastStairMeta, 2, 5, 9, box);
			placeBlockAtCurrentPosition(world, Blocks.dark_oak_stairs, SouthStairMetaUD, 10, 5, 4, box);
			placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 13, 10, 5, 5, box);
			placeBlockAtCurrentPosition(world, Blocks.dark_oak_stairs, NorthStairMetaUD, 10, 5, 6, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, NorthStairMeta, 8, 5, 6, box);
			placeBlockAtCurrentPosition(world, ModBlocks.deco_computer, getDecoModelMeta(2), 10, 6, 6, box);
			
			placeBlockAtCurrentPosition(world, Blocks.dark_oak_stairs, NorthStairMetaUD, 8, 5, 11, box);
			placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 13, 8, 5, 10, box);
			placeBlockAtCurrentPosition(world, Blocks.dark_oak_stairs, EastStairMetaUD, 8, 5, 9, box);
			placeBlockAtCurrentPosition(world, Blocks.dark_oak_stairs, SouthStairMetaUD, 9, 5, 9, box);
			placeBlockAtCurrentPosition(world, Blocks.dark_oak_stairs, WestStairMetaUD, 10, 5, 9, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, WestStairMeta, 10, 5, 10, box);
			placeBlockAtCurrentPosition(world, ModBlocks.deco_computer, getDecoModelMeta(1), 9, 6, 9, box);
			
			placeBlockAtCurrentPosition(world, Blocks.dark_oak_stairs, NorthStairMetaUD, 1, 5, 13, box);
			placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 13, 1, 5, 12, box);
			placeBlockAtCurrentPosition(world, Blocks.dark_oak_stairs, SouthStairMetaUD, 1, 5, 11, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, NorthStairMeta, 3, 5, 13, box);
			placeBlockAtCurrentPosition(world, Blocks.flower_pot, 0, 1, 6, 12, box);
			placeBlockAtCurrentPosition(world, ModBlocks.machine_microwave, getDecoMeta(5), 1, 6, 11, box);
			//Floor 3
			placeBlockAtCurrentPosition(world, Blocks.dark_oak_stairs, WestStairMetaUD, 8, 9, 4, box);
			placeBlockAtCurrentPosition(world, Blocks.dark_oak_stairs, EastStairMetaUD, 7, 9, 4, box);
			placeBlockAtCurrentPosition(world, Blocks.flower_pot, 0, 9, 9, 4, box);
			placeBlockAtCurrentPosition(world, Blocks.dark_oak_stairs, SouthStairMetaUD, 5, 9, 5, box);
			placeBlockAtCurrentPosition(world, Blocks.dark_oak_stairs, WestStairMetaUD, 5, 9, 6, box);
			fillWithMetadataBlocks(world, box, 3, 9, 6, 4, 9, 6, Blocks.wooden_slab, 13);
			placeBlockAtCurrentPosition(world, Blocks.dark_oak_stairs, EastStairMetaUD, 2, 9, 6, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, SouthStairMeta, 3, 9, 5, box);
			placeBlockAtCurrentPosition(world, ModBlocks.deco_computer, getDecoModelMeta(0), 3, 10, 6, box);
			
			placeBlockAtCurrentPosition(world, Blocks.dark_oak_stairs, WestStairMetaUD, 9, 9, 10, box);
			fillWithMetadataBlocks(world, box, 7, 9, 10, 8, 9, 10, Blocks.wooden_slab, 13);
			placeBlockAtCurrentPosition(world, Blocks.dark_oak_stairs, SouthStairMetaUD, 6, 9, 10, box);
			placeBlockAtCurrentPosition(world, Blocks.dark_oak_stairs, EastStairMetaUD, 5, 9, 10, box);
			placeBlockAtCurrentPosition(world, Blocks.dark_oak_stairs, EastStairMetaUD, 5, 9, 11, box);
			placeBlockAtCurrentPosition(world, Blocks.dark_oak_stairs, NorthStairMetaUD, 5, 9, 12, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, NorthStairMeta, 8, 9, 11, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, EastStairMeta, 7, 9, 8, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, SouthStairMeta, 9, 9, 8, box);
			placeBlockAtCurrentPosition(world, Blocks.flower_pot, 0, 6, 10, 10, box);
			placeBlockAtCurrentPosition(world, ModBlocks.deco_computer, getDecoModelMeta(1), 7, 10, 10, box);
			placeBlockAtCurrentPosition(world, ModBlocks.tape_recorder, getDecoMeta(5), 6, 9, 11, box);
			placeRandomBobble(world, box, rand, 5, 10, 11);
			placeBlockAtCurrentPosition(world, Blocks.dark_oak_stairs, WestStairMetaUD, 2, 9, 11, box);
			placeBlockAtCurrentPosition(world, Blocks.dark_oak_stairs, EastStairMetaUD, 1, 9, 11, box);
			placeBlockAtCurrentPosition(world, Blocks.flower_pot, 0, 2, 10, 11, box);
			
			//Roof
			placeBlockAtCurrentPosition(world, Blocks.flower_pot, 0, 5, 13, 9, box);
			placeBlockAtCurrentPosition(world, Blocks.flower_pot, 0, 7, 13, 11, box);
			
			generateInvContents(world, box, rand, ModBlocks.filing_cabinet, getDecoModelMeta(3), 9, 1, 7, ItemPool.getPool(ItemPoolsComponent.POOL_FILING_CABINET), 4);
			generateInvContents(world, box, rand, ModBlocks.filing_cabinet, getDecoModelMeta(1), 7, 5, 4, ItemPool.getPool(ItemPoolsComponent.POOL_FILING_CABINET), 4);
			generateInvContents(world, box, rand, ModBlocks.filing_cabinet, getDecoModelMeta(1), 7, 6, 4, ItemPool.getPool(ItemPoolsComponent.POOL_FILING_CABINET), 4);
			generateInvContents(world, box, rand, ModBlocks.filing_cabinet, getDecoModelMeta(2), 10, 5, 7, ItemPool.getPool(ItemPoolsComponent.POOL_FILING_CABINET), 4);
			generateInvContents(world, box, rand, ModBlocks.filing_cabinet, getDecoModelMeta(0), 10, 5, 12, ItemPool.getPool(ItemPoolsComponent.POOL_FILING_CABINET), 4);
			generateInvContents(world, box, rand, ModBlocks.filing_cabinet, getDecoModelMeta(0), 10, 6, 12, ItemPool.getPool(ItemPoolsComponent.POOL_FILING_CABINET), 4);
			generateInvContents(world, box, rand, ModBlocks.filing_cabinet, getDecoModelMeta(0), 2, 9, 5, ItemPool.getPool(ItemPoolsComponent.POOL_FILING_CABINET), 4);
			
			generateLockableContents(world, box, rand, ModBlocks.safe, getDecoMeta(2), 1, 9, 13, ItemPool.getPool(ItemPoolsComponent.POOL_OFFICE_TRASH), 10, 1.0D);
			if(rand.nextInt(2) == 0)
				generateLoreBook(world, box, 1, 9, 13, 7, HbmChestContents.generateOfficeBook(rand));
			
			generateInvContents(world, box, rand, ModBlocks.filing_cabinet, getDecoModelMeta(0), 2, 9, 13, ItemPool.getPool(ItemPoolsComponent.POOL_FILING_CABINET), 4);
			generateInvContents(world, box, rand, ModBlocks.filing_cabinet, getDecoModelMeta(0), 3, 9, 13, ItemPool.getPool(ItemPoolsComponent.POOL_FILING_CABINET), 4);
			generateLockableContents(world, box, rand, ModBlocks.filing_cabinet, getDecoModelMeta(0), 3, 10, 13, ItemPool.getPool(ItemPoolsLegacy.POOL_EXPENSIVE), 8, 0.1D);
			
			placeBlockAtCurrentPosition(world, ModBlocks.deco_loot, 0, 6, 13, 11, box);
			LootGenerator.lootCapStash(world, this.getXWithOffset(6, 11), this.getYWithOffset(13), this.getZWithOffset(6, 11));
			placeBlockAtCurrentPosition(world, ModBlocks.deco_loot, 0, 1, 10, 11, box);
			LootGenerator.lootMedicine(world, this.getXWithOffset(1, 11), this.getYWithOffset(10), this.getZWithOffset(1, 11));
			//this hurt my soul
			
			return true;
		}
	}
	
	
}

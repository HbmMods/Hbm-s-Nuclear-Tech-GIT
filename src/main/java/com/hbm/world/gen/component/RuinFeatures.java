package com.hbm.world.gen.component;

import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public class RuinFeatures {
	
	public static void registerComponents() {
		MapGenStructureIO.func_143031_a(NTMRuin1.class, "NTMRuin1");
		MapGenStructureIO.func_143031_a(NTMRuin2.class, "NTMRuin2");
		MapGenStructureIO.func_143031_a(NTMRuin3.class, "NTMRuin3");
		MapGenStructureIO.func_143031_a(NTMRuin4.class, "NTMRuin4");
	}
	
	public static class NTMRuin1 extends Component {
		
		private static ConcreteBricks RandomConcreteBricks = new ConcreteBricks();
		
		public NTMRuin1() {
			super();
		}
		
		public NTMRuin1(Random rand, int minX, int minZ) {
			super(rand, minX, 64, minZ, 8, 6, 10);
		}

		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			//System.out.println(this.coordBaseMode);
			if(!this.setAverageHeight(world, box, this.boundingBox.minY)) {
				return false;
			}
			//System.out.println("" + this.boundingBox.minX + ", " + this.boundingBox.minY + ", " + this.boundingBox.minZ);
			
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 0, 0, 8, 10, -1, box);
			
			int pillarMetaWE = this.getPillarMeta(4);
			int pillarMetaNS = this.getPillarMeta(8);
			
			this.fillWithBlocks(world, box, 0, 0, 0, 0, 6, 0, ModBlocks.concrete_pillar, Blocks.air, false); //Back Wall
			this.fillWithMetadataBlocks(world, box, 1, 3, 0, 3, 3, 0, ModBlocks.concrete_pillar, pillarMetaWE, Blocks.air, 0, false);
			this.fillWithBlocks(world, box, 4, 0, 0, 4, 6 - 1, 0, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithMetadataBlocks(world, box, 5, 3, 0, 8 - 1, 3, 0, ModBlocks.concrete_pillar, pillarMetaWE, Blocks.air, 0, false);
			this.fillWithBlocks(world, box, 8, 0, 0, 8, 6 - 1, 0, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 1, 0, 0, 3, 0, 0, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 5, 0, 0, 8 - 1, 0, 0, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 1, 1, 0, 1, 2, 0, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 3, 1, 0, 3, 2, 0, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 5, 1, 0, 5, 2, 0, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 8 - 1, 1, 0, 8 - 1, 2, 0, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 1, 4, 0, 3, 4, 0, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 5, 4, 0, 8 - 1, 4, 0, false, rand, RandomConcreteBricks);
			this.fillWithMetadataBlocks(world, box, 0, 3, 1, 0, 3, 10 - 1, ModBlocks.concrete_pillar, pillarMetaNS, Blocks.air, 0, false); //Left Wall
			this.fillWithBlocks(world, box, 0, 0, 10, 0, 6 - 1, 10, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 0, 0, 1, 0, 0, 10 - 1, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 0, 1, 1, 0, 2, 2, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 0, 1, 4, 0, 2, 6, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 0, 1, 10 - 2, 0, 2, 10 - 1, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 0, 4, 1, 0, 4, 5, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 0, 5, 1, 0, 5, 2, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 0, 4, 10 - 2, 0, 4, 10 - 1, false, rand, RandomConcreteBricks);
			this.fillWithMetadataBlocks(world, box, 1, 3, 10, 3, 3, 10, ModBlocks.concrete_pillar, pillarMetaWE, Blocks.air, 0, false); //Front Wall
			this.fillWithBlocks(world, box, 4, 0, 10, 4, 6 - 2, 10, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithMetadataBlocks(world, box, 5, 3, 10, 8 - 1, 3, 10, ModBlocks.concrete_pillar, pillarMetaWE, Blocks.air, 0, false);
			this.fillWithBlocks(world, box, 8, 0, 10, 8, 6 - 2, 10, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 1, 0, 10, 3, 0, 10, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 5, 0, 10, 8 - 1, 0, 10, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 1, 1, 10, 1, 2, 10, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 3, 1, 10, 3, 2, 10, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 5, 1, 10, 5, 2, 10, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 8 - 1, 1, 10, 8 - 1, 2, 10, false, rand, RandomConcreteBricks);
			this.fillWithMetadataBlocks(world, box, 8, 3, 1, 8, 3, 2, ModBlocks.concrete_pillar, pillarMetaNS, Blocks.air, 0, false); //Right Wall
			this.fillWithMetadataBlocks(world, box, 8, 3, 10 - 1, 8, 3, 10 - 1, ModBlocks.concrete_pillar, pillarMetaNS, Blocks.air, 0, false);
			this.fillWithRandomizedBlocks(world, box, 8, 0, 1, 8, 0, 4, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 8, 1, 1, 8, 2, 2, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 8, 0, 6, 8, 0, 6, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 8, 0, 10 - 2, 8, 1, 10 - 1, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 8, 2, 10 - 1, 8, 2, 10 - 1, false, rand, RandomConcreteBricks);
			
			this.randomlyFillWithBlocks(world, box, rand, 0.25F, 1, 0, 1, 8 - 1, 0, 10 - 1, Blocks.gravel, Blocks.air, false);
			
			return true;
		}
	}
	
	public static class NTMRuin2 extends Component {
		
		private static ConcreteBricks RandomConcreteBricks = new ConcreteBricks();
		
		public NTMRuin2() {
			super();
		}
		
		public NTMRuin2(Random rand, int minX, int minZ) {
			super(rand, minX, 64, minZ, 7, 5, 10);
		}

		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			//System.out.println(this.coordBaseMode);
			if(!this.setAverageHeight(world, box, this.boundingBox.minY)) {
				return false;
			}
			//System.out.println("" + this.boundingBox.minX + ", " + this.boundingBox.minY + ", " + this.boundingBox.minZ);
			
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 0, 0, 7, 10, -1, box);
			
			int pillarMetaWE = this.getPillarMeta(4);
			int pillarMetaNS = this.getPillarMeta(8);
			
			this.fillWithBlocks(world, box, 0, 0, 0, 0, 3, 0, ModBlocks.concrete_pillar, Blocks.air, false); //Back Wall
			this.fillWithMetadataBlocks(world, box, 1, 3, 0, 7 - 1, 3, 0, ModBlocks.concrete_pillar, pillarMetaWE, Blocks.air, 0, false);
			this.fillWithBlocks(world, box, 7, 0, 0, 7, 5, 0, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 1, 0, 0, 7 - 1, 0, 0, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 1, 1, 0, 1, 2, 0, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 3, 1, 0, 4, 2, 0, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 7 - 1, 1, 0, 7 - 1, 2, 0, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 3, 4, 0, 7 - 1, 4, 0, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 7 - 1, 5, 0, 7 - 1, 5, 0, false, rand, RandomConcreteBricks);
			this.fillWithMetadataBlocks(world, box, 0, 3, 1, 0, 3, 4, ModBlocks.concrete_pillar, pillarMetaNS, Blocks.air, 0, false); //Left Wall
			this.fillWithBlocks(world, box, 0, 0, 5, 0, 0, 5, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithBlocks(world, box, 0, 0, 10, 0, 2, 10, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 0, 0, 1, 0, 2, 3, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 0, 0, 10 - 3, 0, 0, 10 - 1, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 0, 1, 10 - 1, 0, 1, 10 - 1, false, rand, RandomConcreteBricks);
			this.fillWithMetadataBlocks(world, box, 7 - 1, 3, 10, 7 - 1, 3, 10, ModBlocks.concrete_pillar, pillarMetaWE, Blocks.air, 0, false); //Front Wall
			this.fillWithBlocks(world, box, 7, 0, 10, 7, 3, 10, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 1, 0, 10, 7 - 1, 0, 10, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 1, 1, 10, 1, 2, 10, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 7 - 1, 1, 10, 7 - 1, 2, 10, false, rand, RandomConcreteBricks);
			this.fillWithMetadataBlocks(world, box, 7, 3, 1, 7, 3, 4, ModBlocks.concrete_pillar, pillarMetaNS, Blocks.air, 0, false); //Right Wall
			this.fillWithBlocks(world, box, 7, 0, 5, 7, 4, 5, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithMetadataBlocks(world, box, 7, 3, 10 - 2, 7, 3, 10 - 1, ModBlocks.concrete_pillar, pillarMetaNS, Blocks.air, 0, false);
			this.fillWithRandomizedBlocks(world, box, 7, 0, 1, 7, 0, 4, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 7, 1, 1, 7, 2, 1, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 7, 1, 3, 7, 2, 3, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 7, 1, 4, 7, 1, 4, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 7, 0, 6, 7, 0, 10 - 1, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 7, 1, 6, 7, 1, 7, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 7, 1, 10 - 1, 7, 2, 10 - 1, false, rand, RandomConcreteBricks);
			
			this.randomlyFillWithBlocks(world, box, rand, 0.25F, 1, 0, 1, 7 - 1, 0, 10 - 1, Blocks.gravel, Blocks.air, false);
			
			return true;
		}
	}
	
	public static class NTMRuin3 extends Component {
		
		private static ConcreteBricks RandomConcreteBricks = new ConcreteBricks();
		
		public NTMRuin3() {
			super();
		}
		
		public NTMRuin3(Random rand, int minX, int minZ) {
			super(rand, minX, 64, minZ, 8, 3, 10);
		}

		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			//System.out.println(this.coordBaseMode);
			if(!this.setAverageHeight(world, box, this.boundingBox.minY)) {
				return false;
			}
			//System.out.println("" + this.boundingBox.minX + ", " + this.boundingBox.minY + ", " + this.boundingBox.minZ);
			
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 0, 0, 0, 10, -1, box);
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 8, 0, 8, 10, -1, box);
			
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 1, 0, 8, 0, -1, box);
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 1, 4, 8, 4, -1, box);
			
			this.fillWithBlocks(world, box, 0, 0, 0, 0, 3, 0, ModBlocks.concrete_pillar, Blocks.air, false); //Back Wall
			this.fillWithBlocks(world, box, 8, 0, 0, 8, 1, 0, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 1, 0, 0, 8 - 1, 0, 0, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 1, 1, 0, 1, 1, 0, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 4, 1, 0, 4, 1, 0, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 8 - 1, 1, 0, 8 - 1, 1, 0, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 1, 2, 0, 8 - 2, 2, 0, false, rand, RandomConcreteBricks);
			this.fillWithBlocks(world, box, 0, 0, 4, 0, 1, 4, ModBlocks.concrete_pillar, Blocks.air, false); //Left Wall
			this.placeBlockAtCurrentPosition(world, ModBlocks.concrete_pillar, 0, 0, 0, 10, box);
			this.fillWithRandomizedBlocks(world, box, 0, 0, 1, 0, 0, 3, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 0, 0, 5, 0, 0, 10 - 1, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 0, 1, 5, 0, 1, 5, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 0, 1, 7, 0, 1, 7, false, rand, RandomConcreteBricks);
			this.fillWithBlocks(world, box, 8, 0, 4, 8, 1, 4, ModBlocks.concrete_pillar, Blocks.air, false); //Right Wall
			this.fillWithBlocks(world, box, 8, 0, 10, 8, 1, 10, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 8, 0, 1, 8, 1, 3, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 8, 0, 5, 8, 0, 6, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 8, 0, 10 - 1, 8, 0, 10 - 1, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 8 - 1, 0, 10, 8 - 1, 0, 10, false, rand, RandomConcreteBricks);
			this.fillWithBlocks(world, box, 4, 0, 4, 4, 2, 4, ModBlocks.concrete_pillar, Blocks.air, false); //Center Wall
			this.fillWithRandomizedBlocks(world, box, 3, 0, 4, 3, 1, 4, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 5, 0, 4, 8 - 1, 1, 4, false, rand, RandomConcreteBricks);
			
			this.randomlyFillWithBlocks(world, box, rand, 0.05F, 1, 0, 1, 8 - 1, 0, 3, Blocks.gravel, Blocks.air, false);
			this.randomlyFillWithBlocks(world, box, rand, 0.05F, 1, 0, 5, 8 - 1, 0, 10 - 1, Blocks.gravel, Blocks.air, false);
			
			return true;
		}
	}
	
	public static class NTMRuin4 extends Component {
		
		private static ConcreteBricks RandomConcreteBricks = new ConcreteBricks();
		
		public NTMRuin4() {
			super();
		}
		
		public NTMRuin4(Random rand, int minX, int minZ) {
			super(rand, minX, 64, minZ, 10, 2, 11);
		}

		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			//System.out.println(this.coordBaseMode);
			if(!this.setAverageHeight(world, box, this.boundingBox.minY)) {
				return false;
			}
			//System.out.println("" + this.boundingBox.minX + ", " + this.boundingBox.minY + ", " + this.boundingBox.minZ);
			
			
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 0, 0, 0, 11, -1, box);
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 10, 5, 10, 11, -1, box);
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 5, 0, 5, 4, -1, box);
			
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 1, 11, 10 - 1, 11, -1, box);
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 1, 0, 4, 0, -1, box);
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 5, 5, 10 - 1, 5, -1, box);
			
			this.fillWithBlocks(world, box, 0, 0, 0, 0, 1, 0, ModBlocks.concrete_pillar, Blocks.air, false); //Back Wall Pt. 1
			this.fillWithBlocks(world, box, 5, 0, 0, 5, 2, 0, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 1, 0, 0, 4, 0, 0, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 4, 1, 0, 4, 1, 0, false, rand, RandomConcreteBricks);
			this.fillWithBlocks(world, box, 5, 0, 5, 5, 2, 5, ModBlocks.concrete_pillar, Blocks.air, false); //Right Wall Pt. 1
			this.fillWithRandomizedBlocks(world, box, 5, 0, 1, 5, 0, 4, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 5, 1, 1, 5, 1, 1, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 5, 1, 4, 5, 1, 4, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 5, 2, 1, 5, 2, 4, false, rand, RandomConcreteBricks);
			this.fillWithBlocks(world, box, 10, 0, 5, 10, 1, 5, ModBlocks.concrete_pillar, Blocks.air, false); //Back Wall Pt. 2
			this.fillWithRandomizedBlocks(world, box, 6, 0, 5, 10 - 1, 0, 5, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 6, 1, 5, 6, 1, 5, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 10 - 1, 1, 5, 10 - 1, 1, 5, false, rand, RandomConcreteBricks);
			this.fillWithBlocks(world, box, 10, 0, 11, 10, 1, 11, ModBlocks.concrete_pillar, Blocks.air, false); //Right Wall Pt. 2
			this.fillWithRandomizedBlocks(world, box, 10, 0, 6, 10, 0, 11 - 1, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 10, 1, 6, 10, 1, 11 - 3, false, rand, RandomConcreteBricks);
			this.fillWithBlocks(world, box, 0, 0, 11, 0, 0, 11, ModBlocks.concrete_pillar, Blocks.air, false); //Front Wall
			this.fillWithRandomizedBlocks(world, box, 1, 0, 11, 1, 0, 11, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 6, 0, 11, 7, 0, 11, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 10 - 1, 0, 11, 10 - 1, 0, 11, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 0, 0, 1, 0, 0, 11 - 1, false, rand, RandomConcreteBricks); //Left Wall
			this.fillWithRandomizedBlocks(world, box, 0, 1, 1, 0, 1, 1, false, rand, RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 0, 1, 4, 0, 1, 7, false, rand, RandomConcreteBricks);
			
			this.randomlyFillWithBlocks(world, box, rand, 0.05F, 1, 0, 1, 4, 0, 5, Blocks.gravel, Blocks.air, false);
			this.randomlyFillWithBlocks(world, box, rand, 0.05F, 1, 0, 6, 10 - 1, 0, 11 - 1, Blocks.gravel, Blocks.air, false);
			
			return true;
		}
	}
	
}

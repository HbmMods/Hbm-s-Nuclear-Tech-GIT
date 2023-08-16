package com.hbm.world.gen.component;

import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public class SpecialFeatures {
	
	public static void registerComponents() {
		MapGenStructureIO.func_143031_a(SpecialContainer.class, "NTMSpecialContainer");
	}
	
	public static class SpecialContainer extends Component {
		
		public SpecialContainer() {
			
		}
		
		public SpecialContainer(Random rand, int minX, int minY, int minZ) {
			super(rand, minX, minY, minZ, 3, 3, 9);
			
		}
		
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			if(hpos == -1 && !this.setAverageHeight(world, box, this.boundingBox.minY)) {
				return false;
			}
			
			fillWithAir(world, box, 1, 1, 1, 2, 2, 8);
			
			//floor
			fillWithBlocks(world, box, 0, 0, 0, 3, 0, 1, ModBlocks.deco_steel);
			fillWithBlocks(world, box, 1, 0, 2, 2, 0, 7, ModBlocks.deco_steel);
			fillWithBlocks(world, box, 0, 0, 8, 3, 0, 9, ModBlocks.deco_steel);
			fillWithBlocks(world, box, 1, 0, 1, 2, 0, 1, ModBlocks.cm_block);
			fillWithBlocks(world, box, 1, 0, 8, 2, 0, 8, ModBlocks.cm_block);
			//roof
			fillWithBlocks(world, box, 0, 3, 0, 3, 3, 9, ModBlocks.deco_steel);
			placeBlockAtCurrentPosition(world, ModBlocks.cm_block, 0, 0, 3, 2, box);
			placeBlockAtCurrentPosition(world, ModBlocks.cm_block, 0, 3, 3, 2, box);
			placeBlockAtCurrentPosition(world, ModBlocks.cm_block, 0, 0, 3, 7, box);
			placeBlockAtCurrentPosition(world, ModBlocks.cm_block, 0, 3, 3, 7, box);
			//walls
			fillWithBlocks(world, box, 0, 1, 0, 0, 2, 0, ModBlocks.deco_steel);
			fillWithBlocks(world, box, 0, 1, 1, 0, 2, 8, ModBlocks.cm_sheet);
			fillWithBlocks(world, box, 0, 1, 9, 0, 2, 9, ModBlocks.deco_steel);
			fillWithBlocks(world, box, 1, 1, 9, 2, 2, 9, ModBlocks.cm_sheet);
			fillWithBlocks(world, box, 3, 1, 9, 3, 2, 9, ModBlocks.deco_steel);
			fillWithBlocks(world, box, 3, 1, 1, 3, 2, 8, ModBlocks.cm_sheet);
			fillWithBlocks(world, box, 3, 1, 0, 3, 2, 0, ModBlocks.deco_steel);
			//doors
			placeDoor(world, box, ModBlocks.door_metal, 1, false, false, 2, 1, 0);
			placeDoor(world, box, ModBlocks.door_metal, 1, true, false, 1, 1, 0);
			
			return true;
		}
		
		@Override
		protected boolean setAverageHeight(World world, StructureBoundingBox box, int y) {
			
			int total = 0;
			int iterations = 0;
			
			for(int z = this.boundingBox.minZ; z <= this.boundingBox.maxZ; z++) {
				for(int x = this.boundingBox.minX; x <= this.boundingBox.maxX; x++) {
					if(box.isVecInside(x, y, z)) {
						total += Math.max(world.getTopSolidOrLiquidBlock(x, z), 1); // underwater :3c
						iterations++;
					}
				}
			}
			
			if(iterations == 0)
				return false;
			
			this.hpos = total / iterations; //finds mean of every block in bounding box
			this.boundingBox.offset(0, this.hpos - this.boundingBox.minY, 0);
			return true;
		}
	}
}

package com.hbm.world.gen.component;

import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;

//civilian features is fucking cringe :P
public class BrutalistFeatures {
	
	public static void registerComponents() {
		MapGenStructureIO.func_143031_a(ElevatedLab1.class, "NTMElevatedLab1");
	}
	
	//might be other than a lab, placeholder :P
	public static class ElevatedLab1 extends Component {
		
		public ElevatedLab1() { super(); }
		
		public ElevatedLab1(Random rand, int minX, int minY, int minZ) {
			super(rand, minX, minY, minZ, 11, 15, 14);
		}
		
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			if(!this.setAverageHeight(world, box, this.boundingBox.minY)) {
				return false;
			}
			
			final int stairMetaW = getStairMeta(0);
			final int stairMetaE = getStairMeta(1);
			final int stairMetaN = getStairMeta(2);
			final int stairMetaS = getStairMeta(3);
			
			//greater pillars
			for(int i = 2; i < 12; i+=5) {
				fillWithBlocks(world, box, i, 0, 2, i, 1, 4, ModBlocks.concrete_smooth);
				fillWithBlocks(world, box, i, 4, 2, i, 5, 4, ModBlocks.concrete_smooth);
				fillWithBlocks(world, box, i+1, 0, 2, i+1, 5, 4, ModBlocks.concrete_pillar);
				fillWithBlocks(world, box, i+2, 0, 2, i+2, 1, 4, ModBlocks.concrete_smooth);
				fillWithBlocks(world, box, i+2, 4, 2, i+2, 5, 4, ModBlocks.concrete_smooth);
				
				for(int j = 0; j <= 1; j++) {
					final int u = j * 4;
					placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, stairMetaN | u, i, 2 + j, 2, box);
					placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, stairMetaN | u, i+=2, 2 + j, 2, box);
					placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, stairMetaW | u, i, 2 + j, 3, box);
					placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, stairMetaE | u, i+=2, 2 + j, 3, box);
					placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, stairMetaS | u, i, 2 + j, 4, box);
					placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, stairMetaS | u, i+=2, 2 + j, 4, box);
				}
				
				placeFoundationUnderneath(world, ModBlocks.concrete_smooth, 0, i, 2, i, 4, -1, box);
				placeFoundationUnderneath(world, ModBlocks.concrete_pillar, 0, i+=1, 2, i+=1, 4, -1, box);
				placeFoundationUnderneath(world, ModBlocks.concrete_smooth, 0, i+=2, 2, i+=2, 4, -1, box);
			}
			
			//lesser pillars
			for(int i = 2; i < 12; i+=5) {
				fillWithBlocks(world, box, i, 0, 10, i, 1, 12, ModBlocks.concrete_smooth);
				fillWithBlocks(world, box, i, 3, 10, i, 3, 12, ModBlocks.concrete_smooth);
				fillWithBlocks(world, box, i+1, 0, 10, i+1, 3, 12, ModBlocks.concrete_pillar);
				fillWithBlocks(world, box, i+2, 0, 10, i+2, 1, 12, ModBlocks.concrete_smooth);
				fillWithBlocks(world, box, i+2, 3, 10, i+2, 3, 12, ModBlocks.concrete_smooth);
				
				placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, stairMetaN, i, 2, 2, box);
				placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, stairMetaN, i+=2, 2, 2, box);
				placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, stairMetaW, i, 2, 3, box);
				placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, stairMetaE, i+=2, 2, 3, box);
				placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, stairMetaS, i, 2, 4, box);
				placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, stairMetaS, i+=2, 2, 4, box);
				
				placeFoundationUnderneath(world, ModBlocks.concrete_smooth, 0, i, 10, i, 12, -1, box);
				placeFoundationUnderneath(world, ModBlocks.concrete_pillar, 0, i+=1, 10, i+=1, 12, -1, box);
				placeFoundationUnderneath(world, ModBlocks.concrete_smooth, 0, i+=2, 10, i+=2, 12, -1, box);
			}
			
			fillWithAir(world, box, 1, 5, 9, 15, 7, 13);
			fillWithAir(world, box, 13, 6, 8, 14, 6, 8);
			fillWithAir(world, box, 13, 7, 6, 14, 8, 8);
			fillWithAir(world, box, 1, 7, 1, 15, 9, 5);
			
			/* lower floor */
			//lower slabs
			fillWithMetadataBlocks(world, box, 0, 3, 9, 16, 3, 9, ModBlocks.concrete_slab, 8);
			fillWithMetadataBlocks(world, box, 1, 3, 10, 1, 3, 12, ModBlocks.concrete_slab, 8);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_slab, 8, 0, 3, 11, box);
			fillWithMetadataBlocks(world, box, 0, 3, 13, 16, 3, 13, ModBlocks.concrete_slab, 8);
			fillWithMetadataBlocks(world, box, 5, 3, 10, 6, 3, 12, ModBlocks.concrete_slab, 8);
			fillWithMetadataBlocks(world, box, 10, 3, 10, 11, 3, 12, ModBlocks.concrete_slab, 8);
			fillWithMetadataBlocks(world, box, 15, 3, 10, 15, 3, 12, ModBlocks.concrete_slab, 8);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_slab, 8, 16, 3, 11, box);
			//floor & lining
			fillWithBlocks(world, box, 1, 4, 9, 15, 4, 13, ModBlocks.deco_titanium);
			fillWithMetadataBlocks(world, box, 0, 4, 8, 16, 4, 8, ModBlocks.concrete_smooth_stairs, stairMetaN | 4);
			fillWithBlocks(world, box, 0, 4, 9, 0, 4, 13, ModBlocks.concrete_smooth);
			fillWithMetadataBlocks(world, box, 0, 4, 14, 16, 4, 14, ModBlocks.concrete_smooth_stairs, stairMetaS | 4);
			fillWithBlocks(world, box, 16, 4, 9, 16, 4, 13, ModBlocks.concrete_smooth);
			//walls
			fillWithBlocks(world, box, 0, 5, 8, 1, 6, 8, ModBlocks.concrete_colored_ext);
			fillWithBlocks(world, box, 4, 5, 8, 12, 6, 8, ModBlocks.concrete_colored_ext);
			fillWithBlocks(world, box, 0, 7, 8, 12, 7, 8, ModBlocks.concrete_smooth);
			fillWithBlocks(world, box, 15, 5, 8, 16, 5, 8, ModBlocks.concrete_colored_ext);
			fillWithBlocks(world, box, 15, 7, 8, 16, 7, 8, ModBlocks.concrete_smooth);
			
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_colored_ext, 0, 0, 5, 9, box);
			fillWithBlocks(world, box, 0, 5, 11, 0, 6, 11, ModBlocks.concrete_colored_ext);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_colored_ext, 0, 0, 5, 13, box);
			fillWithBlocks(world, box, 0, 5, 14, 0, 6, 14, ModBlocks.concrete_colored_ext);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth, 0, 0, 7, 9, box);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_colored_ext, 1, 0, 7, 11, box);
			fillWithBlocks(world, box, 0, 7, 13, 0, 7, 14, ModBlocks.concrete_smooth);
			
			fillWithBlocks(world, box, 4, 5, 14, 4, 6, 14, ModBlocks.concrete_colored_ext);
			fillWithBlocks(world, box, 6, 5, 14, 6, 6, 14, ModBlocks.concrete_colored_ext);
			fillWithBlocks(world, box, 8, 5, 14, 8, 6, 14, ModBlocks.concrete_colored_ext);
			fillWithBlocks(world, box, 10, 5, 14, 10, 6, 14, ModBlocks.concrete_colored_ext);
			fillWithBlocks(world, box, 12, 5, 14, 12, 6, 14, ModBlocks.concrete_colored_ext);
			fillWithBlocks(world, box, 16, 5, 14, 16, 6, 14, ModBlocks.concrete_colored_ext);
			fillWithMetadataBlocks(world, box, 1, 7, 14, 3, 7, 14, ModBlocks.concrete_smooth_stairs, 7);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth, 0, 4, 7, 14, box);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_colored_ext, 1, 6, 7, 14, box);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_colored_ext, 1, 8, 7, 14, box);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_colored_ext, 1, 10, 7, 14, box);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth, 0, 12, 7, 14, box);
			fillWithMetadataBlocks(world, box, 13, 7, 14, 15, 7, 14, ModBlocks.concrete_smooth_stairs, 7);
			
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_colored_ext, 0, 16, 5, 13, box);
			fillWithBlocks(world, box, 16, 5, 11, 16, 6, 11, ModBlocks.concrete_colored_ext);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_colored_ext, 0, 16, 5, 9, box);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth, 0, 16, 7, 13, box);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_colored_ext, 1, 16, 7, 11, box);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth, 0, 16, 7, 9, box);
			//windows
			final float limit = 0.9F;
			randomlyFillWithBlocks(world, box, rand, limit, 0, 6, 9, 0, 6, 9, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, limit, 0, 5, 10, 0, 7, 10, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, limit, 0, 5, 12, 0, 7, 12, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, limit, 0, 6, 13, 0, 6, 13, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, limit, 1, 5, 14, 3, 6, 14, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, limit, 5, 5, 14, 5, 7, 14, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, limit, 7, 5, 14, 7, 7, 14, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, limit, 9, 5, 14, 9, 7, 14, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, limit, 11, 5, 14, 11, 7, 14, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, limit, 13, 5, 14, 15, 6, 14, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, limit, 16, 6, 13, 16, 6, 13, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, limit, 16, 5, 12, 16, 7, 12, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, limit, 16, 5, 10, 16, 7, 10, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, limit, 16, 6, 9, 16, 6, 9, Blocks.glass_pane);
			//ceiling with liner
			fillWithMetadataBlocks(world, box, 1, 8, 9, 15, 8, 13, ModBlocks.vinyl_tile, 1);
			fillWithMetadataBlocks(world, box, 0, 8, 8, 11, 8, 8, ModBlocks.concrete_smooth_stairs, 2);
			fillWithBlocks(world, box, 12, 8, 8, 12, 8, 9, ModBlocks.concrete_smooth);
			fillWithMetadataBlocks(world, box, 13, 8, 9, 14, 8, 9, ModBlocks.concrete_smooth_stairs, 6);
			fillWithBlocks(world, box, 15, 8, 8, 15, 8, 9, ModBlocks.concrete_smooth);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, 2, 16, 8, 8, box);
			fillWithBlocks(world, box, 16, 8, 9, 16, 8, 13, ModBlocks.concrete_smooth);
			fillWithMetadataBlocks(world, box, 0, 8, 14, 16, 8, 14, ModBlocks.concrete_smooth_stairs, 3);
			fillWithBlocks(world, box, 0, 8, 9, 0, 8, 13, ModBlocks.concrete_smooth);
			//upper slabs
			fillWithBlocks(world, box, 0, 9, 9, 16, 9, 9, ModBlocks.concrete_slab);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_slab, 0, 0, 9, 11, box);
			fillWithBlocks(world, box, 0, 9, 13, 16, 9, 13, ModBlocks.concrete_slab);
			fillWithBlocks(world, box, 1, 9, 10, 15, 9, 12, ModBlocks.concrete_slab);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_slab, 0, 16, 9, 11, box);
			fillWithBlocks(world, box, 12, 9, 8, 15, 9, 8, ModBlocks.concrete_slab);
			
			/* middle stairs */
			//stairs
			fillWithMetadataBlocks(world, box, 13, 5, 8, 14, 5, 8, ModBlocks.concrete_smooth_stairs, 3);
			fillWithMetadataBlocks(world, box, 13, 6, 7, 14, 6, 7, ModBlocks.concrete_smooth_stairs, 3);
			fillWithMetadataBlocks(world, box, 12, 5, 7, 15, 5, 7, ModBlocks.concrete_smooth_stairs, 6);
			//walls
			fillWithBlocks(world, box, 12, 6, 7, 12, 8, 7, ModBlocks.concrete_smooth);
			fillWithBlocks(world, box, 15, 6, 7, 15, 8, 7, ModBlocks.concrete_smooth);
			//ceiling
			fillWithMetadataBlocks(world, box, 12, 9, 7, 15, 9, 7, ModBlocks.concrete_smooth_stairs, 3);
			fillWithMetadataBlocks(world, box, 13, 9, 6, 14, 9, 6, ModBlocks.concrete_slab, 8);
			
			/* upper floor */
			//lower slabs
			fillWithMetadataBlocks(world, box, 0, 5, 1, 16, 5, 1, ModBlocks.concrete_slab, 8);
			fillWithMetadataBlocks(world, box, 1, 5, 2, 1, 5, 4, ModBlocks.concrete_slab, 8);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_slab, 8, 0, 5, 3, box);
			fillWithMetadataBlocks(world, box, 0, 5, 5, 16, 5, 5, ModBlocks.concrete_slab, 8);
			fillWithMetadataBlocks(world, box, 12, 5, 6, 15, 5, 6, ModBlocks.concrete_slab, 8);
			fillWithMetadataBlocks(world, box, 5, 5, 2, 6, 5, 4, ModBlocks.concrete_slab, 8);
			fillWithMetadataBlocks(world, box, 10, 5, 2, 11, 5, 4, ModBlocks.concrete_slab, 8);
			fillWithMetadataBlocks(world, box, 15, 5, 2, 15, 5, 4, ModBlocks.concrete_slab, 8);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_slab, 8, 16, 5, 3, box);
			//floor and lining
			fillWithBlocks(world, box, 1, 6, 1, 15, 6, 5, ModBlocks.deco_titanium);
			fillWithBlocks(world, box, 13, 6, 6, 14, 6, 6, ModBlocks.deco_titanium);
			fillWithMetadataBlocks(world, box, 0, 6, 0, 16, 6, 0, ModBlocks.concrete_smooth_stairs, 6);
			fillWithBlocks(world, box, 0, 6, 1, 0, 6, 5, ModBlocks.concrete_smooth);
			fillWithMetadataBlocks(world, box, 0, 6, 6, 11, 6, 6, ModBlocks.concrete_smooth_stairs, 7);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth, 0, 12, 6, 6, box);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth, 0, 15, 6, 6, box);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, 7, 16, 6, 6, box);
			fillWithBlocks(world, box, 16, 6, 1, 16, 6, 5, ModBlocks.concrete_smooth);
			//walls
			fillWithBlocks(world, box, 0, 7, 0, 0, 8, 0, ModBlocks.concrete_colored_ext);
			fillWithBlocks(world, box, 4, 7, 0, 4, 8, 0, ModBlocks.concrete_colored_ext);
			fillWithBlocks(world, box, 6, 7, 0, 6, 8, 0, ModBlocks.concrete_colored_ext);
			fillWithBlocks(world, box, 10, 7, 0, 10, 8, 0, ModBlocks.concrete_colored_ext);
			fillWithBlocks(world, box, 12, 7, 0, 12, 8, 0, ModBlocks.concrete_colored_ext);
			fillWithBlocks(world, box, 16, 7, 0, 16, 8, 0, ModBlocks.concrete_colored_ext);
			fillWithMetadataBlocks(world, box, 1, 9, 0, 3, 9, 0, ModBlocks.concrete_smooth_stairs, 6);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth, 0, 4, 9, 0, box);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_colored_ext, 1, 6, 9, 0, box);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_colored_ext, 1, 10, 9, 0, box);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth, 0, 12, 9, 0, box);
			fillWithMetadataBlocks(world, box, 13, 9, 0, 15, 9, 0, ModBlocks.concrete_smooth_stairs, 6);
			
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_colored_ext, 0, 0, 7, 1, box);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_colored_ext, 0, 0, 7, 5, box);
			fillWithBlocks(world, box, 0, 9, 0, 0, 9, 1, ModBlocks.concrete_smooth);
			fillWithBlocks(world, box, 0, 9, 5, 0, 9, 6, ModBlocks.concrete_smooth);
			
			fillWithBlocks(world, box, 0, 7, 6, 12, 8, 6, ModBlocks.concrete_colored_ext);
			fillWithBlocks(world, box, 15, 7, 6, 16, 8, 6, ModBlocks.concrete_colored_ext);
			fillWithBlocks(world, box, 1, 9, 6, 12, 9, 6, ModBlocks.concrete_smooth);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth, 0, 15, 9, 6, box);
			
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_colored_ext, 0, 16, 7, 5, box);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_colored_ext, 0, 16, 7, 1, box);
			fillWithBlocks(world, box, 16, 9, 5, 16, 9, 6, ModBlocks.concrete_smooth);
			fillWithBlocks(world, box, 16, 9, 0, 16, 9, 1, ModBlocks.concrete_smooth);
			//windows
			randomlyFillWithBlocks(world, box, rand, limit, 1, 7, 0, 3, 8, 0, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, limit, 5, 7, 0, 5, 9, 0, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, limit, 7, 7, 0, 9, 9, 0, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, limit, 11, 7, 0, 11, 9, 0, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, limit, 13, 7, 0, 15, 8, 0, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, limit, 0, 8, 1, 0, 8, 1, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, limit, 0, 7, 2, 0, 9, 4, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, limit, 0, 8, 5, 0, 8, 5, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, limit, 16, 8, 5, 16, 8, 5, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, limit, 16, 7, 2, 16, 9, 4, Blocks.glass_pane);
			randomlyFillWithBlocks(world, box, rand, limit, 16, 8, 1, 16, 8, 1, Blocks.glass_pane);
			//ceiling with liner
			fillWithMetadataBlocks(world, box, 1, 10, 1, 15, 10, 5, ModBlocks.vinyl_tile, 1);
			fillWithMetadataBlocks(world, box, 0, 10, 0, 16, 10, 0, ModBlocks.concrete_smooth_stairs, 2);
			fillWithBlocks(world, box, 0, 10, 1, 0, 10, 5, ModBlocks.concrete_smooth);
			fillWithMetadataBlocks(world, box, 0, 10, 6, 16, 10, 6, ModBlocks.concrete_smooth_stairs, 3);
			fillWithBlocks(world, box, 16, 10, 1, 16, 10, 5, ModBlocks.concrete_smooth);
			//slabs
			fillWithBlocks(world, box, 0, 11, 1, 16, 11, 1, ModBlocks.concrete_slab);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_slab, 0, 0, 11, 3, box);
			fillWithBlocks(world, box, 1, 11, 2, 15, 11, 4, ModBlocks.concrete_slab);
			fillWithBlocks(world, box, 0, 11, 5, 16, 11, 5, ModBlocks.concrete_slab);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_slab, 0, 16, 11, 3, box);
			
			//stairs (probably wip)
			fillWithMetadataBlocks(world, box, 2, 4, 7, 3, 4, 7, ModBlocks.steel_grate, 7);
			placeBlockAtCurrentPosition(world, ModBlocks.steel_grate, 3, 4, 4, 7, box);
			placeBlockAtCurrentPosition(world, ModBlocks.steel_grate, 7, 5, 3, 7, box);
			placeBlockAtCurrentPosition(world, ModBlocks.steel_grate, 3, 6, 3, 7, box);
			placeBlockAtCurrentPosition(world, ModBlocks.steel_grate, 7, 7, 2, 7, box);
			placeBlockAtCurrentPosition(world, ModBlocks.steel_grate, 3, 8, 2, 7, box);
			placeBlockAtCurrentPosition(world, ModBlocks.steel_grate, 7, 9, 1, 7, box);
			placeBlockAtCurrentPosition(world, ModBlocks.steel_grate, 3, 10, 1, 7, box);
			placeBlockAtCurrentPosition(world, ModBlocks.steel_grate, 7, 11, 0, 7, box);
			placeBlockAtCurrentPosition(world, ModBlocks.steel_grate, 3, 12, 0, 7, box);
			placeBlockAtCurrentPosition(world, ModBlocks.steel_wall, 4, 1, 5, 7, box);
			final int decoMetaS = getDecoMeta(2);
			final int decoMetaN = getDecoMeta(3);
			fillWithMetadataBlocks(world, box, 2, 4, 6, 3, 5, 6, ModBlocks.steel_wall, decoMetaS);
			fillWithMetadataBlocks(world, box, 4, 4, 6, 5, 4, 6, ModBlocks.steel_wall, decoMetaS);
			fillWithMetadataBlocks(world, box, 5, 3, 6, 7, 3, 6, ModBlocks.steel_wall, decoMetaS);
			fillWithMetadataBlocks(world, box, 7, 2, 6, 9, 2, 6, ModBlocks.steel_wall, decoMetaS);
			fillWithMetadataBlocks(world, box, 9, 1, 6, 11, 1, 6, ModBlocks.steel_wall, decoMetaS);
			fillWithMetadataBlocks(world, box, 11, 0, 6, 12, 0, 6, ModBlocks.steel_wall, decoMetaS);
			fillWithMetadataBlocks(world, box, 5, 3, 8, 7, 3, 8, ModBlocks.steel_wall, decoMetaN);
			fillWithMetadataBlocks(world, box, 7, 2, 8, 9, 2, 8, ModBlocks.steel_wall, decoMetaN);
			fillWithMetadataBlocks(world, box, 9, 1, 8, 11, 1, 8, ModBlocks.steel_wall, decoMetaN);
			fillWithMetadataBlocks(world, box, 11, 0, 8, 12, 0, 8, ModBlocks.steel_wall, decoMetaN);
			
			
			
			
			return true;
		}
		
	}
}

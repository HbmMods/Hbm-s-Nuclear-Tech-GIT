package com.hbm.world.gen.component;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.HbmChestContents;
import com.hbm.world.gen.NTMWorldGenerator;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenShrub;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

//civilian features is fucking cringe :P
public class BrutalistFeatures {
	
	public static void registerComponents() {
		MapGenStructureIO.func_143031_a(ElevatedPrefab1.class, "NTMElevatedPrefab1");
	}
	
	//might be other than a lab, placeholder :P
	public static class ElevatedPrefab1 extends Component {
		
		protected int type;
		
		public ElevatedPrefab1() { super(); }
		
		public ElevatedPrefab1(Random rand, int minX, int minY, int minZ) {
			super(rand, minX, minY, minZ, 16, 11, 14);
			
			this.type = rand.nextInt(2);
		}
		
		/** Set to NBT */
		protected void func_143012_a(NBTTagCompound nbt) {
			super.func_143012_a(nbt);
			nbt.setInteger("type", type);
		}
		
		/** Get from NBT */
		protected void func_143011_b(NBTTagCompound nbt) {
			super.func_143011_b(nbt);
			this.type = nbt.getInteger("type");
		}
		
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			if(hpos == -1 && !this.setAverageHeight(world, box, this.boundingBox.minY)) {
				return false;
			}
			
			BiomeGenBase biome = world.getBiomeGenForCoords(this.boundingBox.minX, this.boundingBox.minZ);
			DirtyGlass glass = new DirtyGlass(biome, 0.1F, true);
			
			final int stairMetaW = getStairMeta(0);
			final int stairMetaE = getStairMeta(1);
			final int stairMetaN = getStairMeta(2);
			final int stairMetaS = getStairMeta(3);
			
			//greater pillars
			for(int i = 2; i <= 12; i+=5) {
				fillWithBlocks(world, box, i, 0, 2, i, 1, 4, ModBlocks.concrete_smooth);
				fillWithBlocks(world, box, i, 4, 2, i, 5, 4, ModBlocks.concrete_smooth);
				fillWithBlocks(world, box, i+1, 0, 2, i+1, 5, 4, ModBlocks.concrete_pillar);
				fillWithBlocks(world, box, i+2, 0, 2, i+2, 1, 4, ModBlocks.concrete_smooth);
				fillWithBlocks(world, box, i+2, 4, 2, i+2, 5, 4, ModBlocks.concrete_smooth);
				
				for(int j = 0; j <= 1; j++) {
					final int u = j * 4;
					placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, stairMetaN | u, i, 2 + j, 2, box);
					placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, stairMetaN | u, i+2, 2 + j, 2, box);
					placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, stairMetaW | u, i, 2 + j, 3, box);
					placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, stairMetaE | u, i+2, 2 + j, 3, box);
					placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, stairMetaS | u, i, 2 + j, 4, box);
					placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, stairMetaS | u, i+2, 2 + j, 4, box);
				}
				
				placeFoundationUnderneath(world, ModBlocks.concrete_smooth, 0, i, 2, i, 4, -1, box);
				placeFoundationUnderneath(world, ModBlocks.concrete_pillar, 0, i+1, 2, i+1, 4, -1, box);
				placeFoundationUnderneath(world, ModBlocks.concrete_smooth, 0, i+2, 2, i+2, 4, -1, box);
			}
			
			//lesser pillars
			for(int i = 2; i <= 12; i+=5) {
				fillWithBlocks(world, box, i, 0, 10, i, 1, 12, ModBlocks.concrete_smooth);
				fillWithBlocks(world, box, i, 3, 10, i, 3, 12, ModBlocks.concrete_smooth);
				fillWithBlocks(world, box, i+1, 0, 10, i+1, 3, 12, ModBlocks.concrete_pillar);
				fillWithBlocks(world, box, i+2, 0, 10, i+2, 1, 12, ModBlocks.concrete_smooth);
				fillWithBlocks(world, box, i+2, 3, 10, i+2, 3, 12, ModBlocks.concrete_smooth);
				
				placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, stairMetaN, i, 2, 10, box);
				placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, stairMetaN, i+2, 2, 10, box);
				placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, stairMetaW, i, 2, 11, box);
				placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, stairMetaE, i+2, 2, 11, box);
				placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, stairMetaS, i, 2, 12, box);
				placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, stairMetaS, i+2, 2, 12, box);
				
				placeFoundationUnderneath(world, ModBlocks.concrete_smooth, 0, i, 10, i, 12, -1, box);
				placeFoundationUnderneath(world, ModBlocks.concrete_pillar, 0, i+1, 10, i+1, 12, -1, box);
				placeFoundationUnderneath(world, ModBlocks.concrete_smooth, 0, i+2, 10, i+2, 12, -1, box);
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
			fillWithBlocks(world, box, 15, 5, 8, 16, 6, 8, ModBlocks.concrete_colored_ext);
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
			fillWithMetadataBlocks(world, box, 1, 7, 14, 3, 7, 14, ModBlocks.concrete_smooth_stairs, stairMetaS | 4);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth, 0, 4, 7, 14, box);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_colored_ext, 1, 6, 7, 14, box);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_colored_ext, 1, 8, 7, 14, box);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_colored_ext, 1, 10, 7, 14, box);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth, 0, 12, 7, 14, box);
			fillWithMetadataBlocks(world, box, 13, 7, 14, 15, 7, 14, ModBlocks.concrete_smooth_stairs, stairMetaS | 4);
			
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_colored_ext, 0, 16, 5, 13, box);
			fillWithBlocks(world, box, 16, 5, 11, 16, 6, 11, ModBlocks.concrete_colored_ext);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_colored_ext, 0, 16, 5, 9, box);
			fillWithBlocks(world, box, 16, 7, 13, 16, 7, 14, ModBlocks.concrete_smooth);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_colored_ext, 1, 16, 7, 11, box);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth, 0, 16, 7, 9, box);
			//windows
			fillWithRandomizedBlocks(world, box, 0, 6, 9, 0, 6, 9, rand, glass);
			fillWithRandomizedBlocks(world, box, 0, 5, 10, 0, 7, 10, rand, glass);
			fillWithRandomizedBlocks(world, box, 0, 5, 12, 0, 7, 12, rand, glass);
			fillWithRandomizedBlocks(world, box, 0, 6, 13, 0, 6, 13, rand, glass);
			fillWithRandomizedBlocks(world, box, 1, 5, 14, 3, 6, 14, rand, glass);
			fillWithRandomizedBlocks(world, box, 5, 5, 14, 5, 7, 14, rand, glass);
			fillWithRandomizedBlocks(world, box, 7, 5, 14, 7, 7, 14, rand, glass);
			fillWithRandomizedBlocks(world, box, 9, 5, 14, 9, 7, 14, rand, glass);
			fillWithRandomizedBlocks(world, box, 11, 5, 14, 11, 7, 14, rand, glass);
			fillWithRandomizedBlocks(world, box, 13, 5, 14, 15, 6, 14, rand, glass);
			fillWithRandomizedBlocks(world, box, 16, 6, 13, 16, 6, 13, rand, glass);
			fillWithRandomizedBlocks(world, box, 16, 5, 12, 16, 7, 12, rand, glass);
			fillWithRandomizedBlocks(world, box, 16, 5, 10, 16, 7, 10, rand, glass);
			fillWithRandomizedBlocks(world, box, 16, 6, 9, 16, 6, 9, rand, glass);
			//ceiling with liner
			fillWithMetadataBlocks(world, box, 1, 8, 9, 15, 8, 13, ModBlocks.vinyl_tile, 1);
			fillWithMetadataBlocks(world, box, 0, 8, 8, 11, 8, 8, ModBlocks.concrete_smooth_stairs, stairMetaN);
			fillWithBlocks(world, box, 12, 8, 8, 12, 8, 9, ModBlocks.concrete_smooth);
			fillWithMetadataBlocks(world, box, 13, 8, 9, 14, 8, 9, ModBlocks.concrete_smooth_stairs, stairMetaN | 4);
			fillWithBlocks(world, box, 15, 8, 8, 15, 8, 9, ModBlocks.concrete_smooth);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, stairMetaN, 16, 8, 8, box);
			fillWithBlocks(world, box, 16, 8, 9, 16, 8, 13, ModBlocks.concrete_smooth);
			fillWithMetadataBlocks(world, box, 0, 8, 14, 16, 8, 14, ModBlocks.concrete_smooth_stairs, stairMetaS);
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
			fillWithMetadataBlocks(world, box, 13, 5, 8, 14, 5, 8, ModBlocks.concrete_smooth_stairs, stairMetaS);
			fillWithMetadataBlocks(world, box, 13, 6, 7, 14, 6, 7, ModBlocks.concrete_smooth_stairs, stairMetaS);
			fillWithMetadataBlocks(world, box, 12, 5, 7, 15, 5, 7, ModBlocks.concrete_smooth_stairs, stairMetaN | 4);
			//walls
			fillWithBlocks(world, box, 12, 6, 7, 12, 8, 7, ModBlocks.concrete_smooth);
			fillWithBlocks(world, box, 15, 6, 7, 15, 8, 7, ModBlocks.concrete_smooth);
			//ceiling
			fillWithMetadataBlocks(world, box, 12, 9, 7, 15, 9, 7, ModBlocks.concrete_smooth_stairs, stairMetaS);
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
			fillWithMetadataBlocks(world, box, 0, 6, 0, 16, 6, 0, ModBlocks.concrete_smooth_stairs, stairMetaN | 4);
			fillWithBlocks(world, box, 0, 6, 1, 0, 6, 5, ModBlocks.concrete_smooth);
			fillWithMetadataBlocks(world, box, 0, 6, 6, 11, 6, 6, ModBlocks.concrete_smooth_stairs, stairMetaS | 4);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth, 0, 12, 6, 6, box);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth, 0, 15, 6, 6, box);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, stairMetaS | 4, 16, 6, 6, box);
			fillWithBlocks(world, box, 16, 6, 1, 16, 6, 5, ModBlocks.concrete_smooth);
			//walls
			fillWithBlocks(world, box, 0, 7, 0, 0, 8, 0, ModBlocks.concrete_colored_ext);
			fillWithBlocks(world, box, 4, 7, 0, 4, 8, 0, ModBlocks.concrete_colored_ext);
			fillWithBlocks(world, box, 6, 7, 0, 6, 8, 0, ModBlocks.concrete_colored_ext);
			fillWithBlocks(world, box, 10, 7, 0, 10, 8, 0, ModBlocks.concrete_colored_ext);
			fillWithBlocks(world, box, 12, 7, 0, 12, 8, 0, ModBlocks.concrete_colored_ext);
			fillWithBlocks(world, box, 16, 7, 0, 16, 8, 0, ModBlocks.concrete_colored_ext);
			fillWithMetadataBlocks(world, box, 1, 9, 0, 3, 9, 0, ModBlocks.concrete_smooth_stairs, stairMetaN | 4);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth, 0, 4, 9, 0, box);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_colored_ext, 1, 6, 9, 0, box);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_colored_ext, 1, 10, 9, 0, box);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth, 0, 12, 9, 0, box);
			fillWithMetadataBlocks(world, box, 13, 9, 0, 15, 9, 0, ModBlocks.concrete_smooth_stairs, stairMetaN | 4);
			
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
			fillWithRandomizedBlocks(world, box, 1, 7, 0, 3, 8, 0, rand, glass);
			fillWithRandomizedBlocks(world, box, 5, 7, 0, 5, 9, 0, rand, glass);
			fillWithRandomizedBlocks(world, box, 7, 7, 0, 9, 9, 0, rand, glass);
			fillWithRandomizedBlocks(world, box, 11, 7, 0, 11, 9, 0, rand, glass);
			fillWithRandomizedBlocks(world, box, 13, 7, 0, 15, 8, 0, rand, glass);
			fillWithRandomizedBlocks(world, box, 0, 8, 1, 0, 8, 1, rand, glass);
			fillWithRandomizedBlocks(world, box, 0, 7, 2, 0, 9, 4, rand, glass);
			fillWithRandomizedBlocks(world, box, 0, 8, 5, 0, 8, 5, rand, glass);
			fillWithRandomizedBlocks(world, box, 16, 8, 5, 16, 8, 5, rand, glass);
			fillWithRandomizedBlocks(world, box, 16, 7, 2, 16, 9, 4, rand, glass);
			fillWithRandomizedBlocks(world, box, 16, 8, 1, 16, 8, 1, rand, glass);
			//ceiling with liner
			fillWithMetadataBlocks(world, box, 1, 10, 1, 15, 10, 5, ModBlocks.vinyl_tile, 1);
			fillWithMetadataBlocks(world, box, 0, 10, 0, 16, 10, 0, ModBlocks.concrete_smooth_stairs, stairMetaN);
			fillWithBlocks(world, box, 0, 10, 1, 0, 10, 5, ModBlocks.concrete_smooth);
			fillWithMetadataBlocks(world, box, 0, 10, 6, 16, 10, 6, ModBlocks.concrete_smooth_stairs, stairMetaS);
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
			final int decoMetaS = getDecoMeta(2);
			final int decoMetaN = getDecoMeta(3);
			final int decoMetaE = getDecoMeta(4);
			placeBlockAtCurrentPosition(world, ModBlocks.steel_wall, decoMetaE, 1, 5, 7, box);
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
			
			/* Deco */
			//lights
			for(int j = 4; j <= 12; j+=4)
				placeBlockAtCurrentPosition(world, ModBlocks.reinforced_lamp_off, 0, j, 8, 11, box);
			for(int j = 4; j <= 12; j+=4)
				placeBlockAtCurrentPosition(world, ModBlocks.reinforced_lamp_off, 0, j, 10, 3, box);
			//doors
			placeDoor(world, box, ModBlocks.door_office, 1, false, false, 3, 5, 8);
			placeDoor(world, box, ModBlocks.door_office, 1, true, false, 2, 5, 8);
			
			final int decoModelMetaN = getDecoModelMeta(0);
			final int decoModelMetaS = getDecoModelMeta(1);
			final int decoModelMetaW = getDecoModelMeta(2);
			final int decoModelMetaE = getDecoModelMeta(3);
			final int decoMetaW = getDecoMeta(5);
			switch(this.type) {
			default: //machinery lab
				//lower floor
				placeBlockAtCurrentPosition(world, ModBlocks.deco_pipe_rim, 0, 1, 5, 9, box);
				placeBlockAtCurrentPosition(world, Blocks.fence, 0, 1, 5, 11, box);
				placeBlockAtCurrentPosition(world, Blocks.wooden_pressure_plate, 0, 1, 6, 11, box);
				placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairMetaE | 4, 1, 5, 13, box);
				placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairMetaW | 4, 2, 5, 13, box);
				placeBlockAtCurrentPosition(world, Blocks.flower_pot, 0, 1, 6, 13, box);
				placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairMetaS | 4, 5, 5, 9, box); //desk 1
				fillWithMetadataBlocks(world, box, 5, 5, 10, 5, 5, 11, Blocks.spruce_stairs, stairMetaE | 4);
				placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 9, 6, 5, 11, box);
				placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairMetaW | 4, 7, 5, 11, box);
				placeBlockAtCurrentPosition(world, Blocks.birch_stairs, stairMetaS, 7, 5, 9, box);
				placeBlockAtCurrentPosition(world, ModBlocks.deco_computer, decoModelMetaN, 6, 6, 11, box);
				placeBlockAtCurrentPosition(world, Blocks.dark_oak_stairs, stairMetaW | 4, 11, 5, 9, box); //desk 2
				fillWithMetadataBlocks(world, box, 9, 5, 9, 10, 5, 9, Blocks.dark_oak_stairs, stairMetaS | 4);
				placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 13, 9, 5, 10, box);
				placeBlockAtCurrentPosition(world, Blocks.dark_oak_stairs, stairMetaN | 4, 9, 5, 11, box);
				placeBlockAtCurrentPosition(world, Blocks.birch_stairs, stairMetaW, 11, 5, 10, box);
				placeBlockAtCurrentPosition(world, ModBlocks.deco_computer, decoModelMetaS, 10, 6, 9, box);
				placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairMetaE | 4, 13, 5, 13, box); //desk 3
				fillWithMetadataBlocks(world, box, 14, 5, 13, 15, 5, 13, Blocks.wooden_slab, 9);
				placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 9, 15, 5, 12, box);
				placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairMetaS | 4, 15, 5, 11, box);
				placeBlockAtCurrentPosition(world, Blocks.birch_stairs, stairMetaE, 14, 5, 12, box);
				placeBlockAtCurrentPosition(world, ModBlocks.deco_computer, decoModelMetaN, 14, 6, 13, box);
				placeBlockAtCurrentPosition(world, Blocks.flower_pot, 0, 15, 5, 10, box);
				placeBlockAtCurrentPosition(world, Blocks.fence, 0, 15, 5, 9, box);
				placeBlockAtCurrentPosition(world, Blocks.wooden_pressure_plate, 0, 15, 6, 9, box);
				//loot
				generateInvContents(world, box, rand, ModBlocks.filing_cabinet, decoModelMetaS, 8, 5, 9, HbmChestContents.officeTrash, 4);
				generateInvContents(world, box, rand, ModBlocks.filing_cabinet, decoModelMetaN, 12, 5, 13, HbmChestContents.filingCabinet, 6);
				//there were supposed to be paintings included, but i don't want to figure out how to
				//force the art type on both the server and clientside
				//upper floor
				placeBlockAtCurrentPosition(world, ModBlocks.deco_pipe_rim, 0, 15, 7, 5, box);
				placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairMetaW | 4, 15, 7, 1, box);
				placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairMetaE | 4, 14, 7, 1, box);
				placeBlockAtCurrentPosition(world, Blocks.flower_pot, 0, 14, 8, 1, box);
				fillWithBlocks(world, box, 8, 7, 3, 11, 7, 3, ModBlocks.tile_lab); //central table
				placeBlockAtCurrentPosition(world, ModBlocks.steel_wall, decoMetaW, 12, 7, 3, box);
				fillWithMetadataBlocks(world, box, 8, 7, 2, 11, 7, 2, ModBlocks.steel_wall, decoMetaS);
				placeBlockAtCurrentPosition(world, ModBlocks.steel_wall, decoMetaE, 7, 7, 3, box);
				fillWithMetadataBlocks(world, box, 8, 7, 4, 11, 7, 4, ModBlocks.steel_wall, decoMetaN);
				fillWithBlocks(world, box, 10, 7, 5, 12, 7, 5, ModBlocks.cm_sheet); //machine 1
				placeBlockAtCurrentPosition(world, ModBlocks.cm_block, 0, 12, 8, 5, box);
				placeBlockAtCurrentPosition(world, ModBlocks.tape_recorder, decoMetaS, 11, 8, 5, box);
				placeBlockAtCurrentPosition(world, ModBlocks.cm_block, 0, 10, 8, 5, box);
				placeBlockAtCurrentPosition(world, ModBlocks.cm_sheet, 0, 12, 9, 5, box);
				placeBlockAtCurrentPosition(world, ModBlocks.cm_port, 0, 11, 9, 5, box);
				placeBlockAtCurrentPosition(world, ModBlocks.cm_sheet, 0, 10, 9, 5, box);
				placeBlockAtCurrentPosition(world, Blocks.stone_button, getButtonMeta(4), 11, 9, 4, box);
				fillWithMetadataBlocks(world, box, 9, 7, 5, 9, 8, 5, ModBlocks.steel_wall, decoMetaW); //locker
				placeBlockAtCurrentPosition(world, ModBlocks.steel_roof, decoMetaN, 8, 9, 5, box);
				fillWithMetadataBlocks(world, box, 7, 7, 5, 7, 8, 5, ModBlocks.steel_wall, decoMetaE);
				fillWithBlocks(world, box, 4, 7, 5, 6, 7, 5, ModBlocks.cm_block); //machine 2
				placeBlockAtCurrentPosition(world, ModBlocks.tape_recorder, decoMetaS, 6, 8, 5, box);
				placeBlockAtCurrentPosition(world, ModBlocks.cm_circuit, 0, 5, 8, 5, box);
				placeBlockAtCurrentPosition(world, ModBlocks.tape_recorder, decoMetaS, 4, 8, 5, box);
				placeBlockAtCurrentPosition(world, ModBlocks.cm_engine, 0, 6, 9, 5, box);
				placeBlockAtCurrentPosition(world, ModBlocks.reinforced_lamp_off, 0, 5, 9, 5, box);
				placeBlockAtCurrentPosition(world, ModBlocks.cm_engine, 0, 4, 9, 5, box);
				placeLever(world, box, 4, false, 5, 8, 4);
				placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairMetaE | 4, 4, 7, 1, box); //table
				placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairMetaW | 4, 5, 7, 1, box);
				placeBlockAtCurrentPosition(world, ModBlocks.deco_pipe_rim, 0, 1, 7, 1, box);
				placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairMetaW | 4, 2, 7, 5, box); //desk
				placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairMetaE | 4, 1, 7, 5, box);
				placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 2, 2, 7, 4, box);
				placeBlockAtCurrentPosition(world, ModBlocks.deco_computer, decoModelMetaN, 2, 8, 5, box);
				//loot
				generateInvContents(world, box, rand, ModBlocks.filing_cabinet, decoModelMetaS, 13, 7, 1, HbmChestContents.filingCabinet, 4);
				if(rand.nextInt(2) == 0)
					generateLoreBook(world, box, 13, 7, 1, 4, HbmChestContents.generateLabBook(rand));
				generateInvContents(world, box, rand, Blocks.chest, decoMetaS, 8, 7, 5, HbmChestContents.labVault, 4);
				generateInvContents(world, box, rand, Blocks.chest, decoMetaS, 8, 8, 5, HbmChestContents.machineParts, 5);
				break;
			case 1: //hazmat 
				//lower floor
				placeBlockAtCurrentPosition(world, Blocks.birch_stairs, stairMetaS | 4, 1, 5, 9, box); //table
				placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 10, 1, 5, 10, box);
				placeBlockAtCurrentPosition(world, Blocks.birch_stairs, stairMetaN | 4, 1, 5, 11, box);
				placeBlockAtCurrentPosition(world, Blocks.flower_pot, 0, 1, 6, 10, box);
				placeBlockAtCurrentPosition(world, ModBlocks.deco_pipe_rim, 0, 1, 5, 13, box);
				fillWithMetadataBlocks(world, box, 5, 5, 9, 11, 5, 9, ModBlocks.concrete_asbestos_stairs, stairMetaS | 4); //con. desk
				fillWithMetadataBlocks(world, box, 5, 5, 10, 7, 5, 10, ModBlocks.concrete_slab, 10);
				placeBlockAtCurrentPosition(world, ModBlocks.concrete_asbestos_stairs, stairMetaN | 4, 7, 5, 11, box);
				placeBlockAtCurrentPosition(world, ModBlocks.concrete_slab, 10, 11, 5, 10, box);
				placeBlockAtCurrentPosition(world, ModBlocks.concrete_asbestos_stairs, stairMetaN | 4, 11, 5, 11, box);
				fillWithMetadataBlocks(world, box, 4, 6, 9, 4, 7, 9, ModBlocks.steel_wall, decoMetaE); //bank of tape recorders
				fillWithMetadataBlocks(world, box, 5, 6, 9, 5, 7, 9, ModBlocks.tape_recorder, decoMetaN);
				placeBlockAtCurrentPosition(world, ModBlocks.tape_recorder, decoMetaN, 6, 6, 9, box);
				placeBlockAtCurrentPosition(world, ModBlocks.cm_block, 0, 7, 6, 9, box);
				placeBlockAtCurrentPosition(world, ModBlocks.cm_block, 0, 6, 7, 9, box);
				placeBlockAtCurrentPosition(world, ModBlocks.tape_recorder, decoMetaN, 7, 7, 9, box);
				placeBlockAtCurrentPosition(world, Blocks.lever, 3, 6, 7, 10, box);
				placeBlockAtCurrentPosition(world, Blocks.stone_button, 3, 7, 6, 10, box);
				fillWithMetadataBlocks(world, box, 8, 6, 9, 8, 7, 9, ModBlocks.steel_corner, decoMetaW);
				placeBlockAtCurrentPosition(world, ModBlocks.tape_recorder, decoMetaN, 9, 6, 9, box);
				fillWithMetadataBlocks(world, box, 10, 6, 9, 10, 7, 9, ModBlocks.steel_corner, decoMetaN);
				placeBlockAtCurrentPosition(world, ModBlocks.cm_block, 0, 11, 6, 9, box);
				placeBlockAtCurrentPosition(world, ModBlocks.tape_recorder, decoMetaN, 11, 7, 9, box);
				fillWithMetadataBlocks(world, box, 12, 6, 9, 12, 7, 9, ModBlocks.steel_wall, decoMetaW);
				placeBlockAtCurrentPosition(world, Blocks.stone_button, 3, 11, 6, 10, box);
				placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairMetaN, 5, 5, 11, box); //chairs and computers
				placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairMetaN, 8, 5, 12, box);
				placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairMetaE, 10, 5, 11, box);
				placeBlockAtCurrentPosition(world, Blocks.flower_pot, 0, 6, 6, 10, box);
				placeBlockAtCurrentPosition(world, Blocks.flower_pot, 0, 7, 5, 12, box);
				placeBlockAtCurrentPosition(world, Blocks.flower_pot, 0, 10, 5, 10, box);
				placeBlockAtCurrentPosition(world, ModBlocks.deco_computer, decoModelMetaE, 7, 6, 11, box);
				placeBlockAtCurrentPosition(world, ModBlocks.deco_computer, decoModelMetaW, 11, 6, 11, box);
				placeBlockAtCurrentPosition(world, Blocks.birch_stairs, stairMetaE | 4, 14, 5, 13, box); //table
				placeBlockAtCurrentPosition(world, Blocks.birch_stairs, stairMetaW | 4, 15, 5, 13, box);
				placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairMetaS, 15, 5, 11, box);
				placeBlockAtCurrentPosition(world, ModBlocks.deco_pipe_rim, 0, 15, 5, 9, box);
				//loot
				generateInvContents(world, box, rand, ModBlocks.filing_cabinet, decoModelMetaS | 1, 9, 7, 9, HbmChestContents.filingCabinet, 3);
				generateInvContents(world, box, rand, Blocks.chest, decoMetaS, 14, 6, 13, HbmChestContents.modGeneric, 3);
				//upper floor
				placeBlockAtCurrentPosition(world, Blocks.birch_stairs, stairMetaW | 4, 15, 7, 5, box); //desks
				placeBlockAtCurrentPosition(world, Blocks.birch_stairs, stairMetaE | 4, 15, 7, 4, box);
				placeBlockAtCurrentPosition(world, Blocks.birch_stairs, stairMetaS | 4, 15, 7, 2, box);
				placeBlockAtCurrentPosition(world, Blocks.birch_stairs, stairMetaW | 4, 15, 7, 1, box);
				placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 0, 13, 7, 2, box);
				placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 0, 14, 7, 4, box);
				placeBlockAtCurrentPosition(world, ModBlocks.deco_computer, decoModelMetaW, 15, 8, 4, box);
				for(int i = 3; i <= 9; i+=6) { //hazmat tables
					fillWithMetadataBlocks(world, box, i, 7, 3, i, 7, 5, ModBlocks.concrete_asbestos_stairs, stairMetaW | 4);
					placeBlockAtCurrentPosition(world, ModBlocks.concrete_asbestos_stairs, stairMetaN | 4, i+1, 7, 3, box);
					fillWithMetadataBlocks(world, box, i+2, 7, 3, i+2, 7, 5, ModBlocks.concrete_asbestos_stairs, stairMetaE | 4);
					fillWithMetadataBlocks(world, box, i+1, 7, 4, i+1, 7, 5, ModBlocks.concrete_colored_ext, 5);
				}
				placeBlockAtCurrentPosition(world, ModBlocks.block_electrical_scrap, 0, 10, 8, 5, box);
				placeBlockAtCurrentPosition(world, ModBlocks.crate_lead, 0, 4, 8, 4, box);
				fillWithMetadataBlocks(world, box, 6, 7, 5, 8, 7, 5, ModBlocks.cm_block, 1); //machine
				placeBlockAtCurrentPosition(world, ModBlocks.cm_circuit, 2, 6, 8, 5, box);
				placeBlockAtCurrentPosition(world, ModBlocks.cm_port, 1, 7, 8, 5, box);
				placeBlockAtCurrentPosition(world, ModBlocks.cm_circuit, 2, 8, 8, 5, box);
				placeBlockAtCurrentPosition(world, ModBlocks.cm_sheet, 1, 6, 9, 5, box);
				placeBlockAtCurrentPosition(world, Blocks.redstone_lamp, 0, 7, 9, 5, box);
				placeBlockAtCurrentPosition(world, ModBlocks.cm_sheet, 1, 8, 9, 5, box);
				placeBlockAtCurrentPosition(world, Blocks.lever, 4, 7, 8, 4, box);
				placeBlockAtCurrentPosition(world, ModBlocks.geiger, decoMetaE, 6, 7, 4, box); //geiger
				if(rand.nextInt(2) == 0)
					placeBlockAtCurrentPosition(world, ModBlocks.crate_metal, 0, 6, 7, 1, box);
				else
					placeRandomBobble(world, box, rand, 6, 7, 1 );
				placeBlockAtCurrentPosition(world, Blocks.birch_stairs, stairMetaS | 4, 1, 7, 1, box); //desk
				placeBlockAtCurrentPosition(world, Blocks.birch_stairs, stairMetaE | 4, 2, 7, 1, box);
				placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 0, 1, 7, 3, box);
				placeBlockAtCurrentPosition(world, Blocks.flower_pot, 0, 2, 8, 1, box);
				placeBlockAtCurrentPosition(world, ModBlocks.crate_lead, 0, 1, 7, 5, box);
				//loot
				generateInvContents(world, box, rand, ModBlocks.crate_iron, 8, 7, 3, HbmChestContents.nuclearFuel, 8);
				generateInvContents(world, box, rand, ModBlocks.crate_iron, 2, 7, 5, HbmChestContents.nukeTrash, 6);
				//other crate
				placeBlockAtCurrentPosition(world, ModBlocks.crate_lead, 0, 2, 8, 5, box);
				
				break;
			}
			
			//webs
			randomlyFillWithBlocks(world, box, rand, 0.15F, 0, 6, 7, 10, 8, 7, Blocks.web);
			randomlyFillWithBlocks(world, box, rand, 0.4F, 11, 5, 7, 11, 9, 7, Blocks.web);
			randomlyFillWithBlocks(world, box, rand, 0.4F, 16, 5, 7, 16, 9, 7, Blocks.web);
			
			placeWebs(world, box, rand, 1, 5, 9, 15, 7, 13, 0.10F, 0.01F);
			placeWebs(world, box, rand, 13, 7, 6, 14, 8, 8, 0.20F, 0F);
			placeWebs(world, box, rand, 1, 7, 1, 15, 9, 5, 0.10F, 0.01F);
			
			//test
			//plan is: biome-dependent overgrowth, integrated neatly
			/*generateShrubs(world, box, rand, 0, 0, 16, 1, 4);
			generateShrubs(world, box, rand, 0, 13, 16, 14, 2);
			generateShrubs(world, box, rand, 0, 4, 16, 8, 2);*/
			
			
			return true;
		}
		
		protected void placeWebs(World world, StructureBoundingBox box, Random rand, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, float chance, float bigWebChance) {
			if(getYWithOffset(minY) < box.minY || getYWithOffset(maxY) > box.maxY)
				return;
			
			for(int x = minX; x <= maxX; x++) {
				
				for(int z = minZ; z <= maxZ; z++) {
					int posX = getXWithOffset(x, z);
					int posZ = getZWithOffset(x, z);
					
					if(posX >= box.minX && posX <= box.maxX && posZ >= box.minZ && posZ <= box.maxZ) {
						for(int y = minY; y <= maxY; y++) {
							int posY = getYWithOffset(y);
							
							if(world.getBlock(posX, posY, posZ) == Blocks.web) continue;
							
							boolean onWall = (x == minX || x == maxX || z == minZ || z == maxZ);
							boolean onCeiling = (y == minY || y == maxY); //floor or ceiling, really
							//this will go over the subchunk boundaries, but trees already do that all the time; it's only really an issue if it causes updates or
							//goes over the 2x2 chunk area entirely
							if(onWall && onCeiling && rand.nextFloat() <= bigWebChance) {
								
								for(int j = Math.max(minY, y - 1); j <= Math.min(maxY, y + 1); j++) { //on one hand, eugh. on the other, good-looking webs!
									int fac = 2 - Math.abs(j - y); //rounds out the edges with distance from starting block
									
									int maxI = Math.min(maxX, x + 1);
									for(int i = Math.max(minX, x - 1); i <= maxI; i++) {
										
										int maxK = Math.min(maxZ, z + 1);
										for(int k = Math.max(minZ, z - 1); k <= maxK; k++) {
											int posI = getXWithOffset(i, k);
											int posJ = getYWithOffset(j);
											int posK = getZWithOffset(i, k);
											
											if(world.isAirBlock(posI, posJ, posK) && (Math.abs(i - x) != fac || Math.abs(k - z) != fac || rand.nextInt(3) == 0))
												world.setBlock(posI, posJ, posK, Blocks.web, 0, 2);
												
										}
									}
								}
							} else if((onWall || onCeiling) && world.isAirBlock(posX, posY, posZ) && rand.nextFloat() <= chance)
								world.setBlock(posX, posY, posZ, Blocks.web, 0, 2);	
						}
					}
				}
			}
		}
		
		/*protected void generateShrubs(World world, StructureBoundingBox box, Random rand, int minX, int minZ, int maxX, int maxZ, int startY) {
			int startX = Math.min(this.getXWithOffset(minX, minZ), this.getXWithOffset(maxX, maxZ));
			int startZ = Math.min(this.getZWithOffset(minX, minZ), this.getZWithOffset(maxX, maxZ));
			int endX = Math.max(this.getXWithOffset(minX, minZ), this.getXWithOffset(maxX, maxZ)) - startX;
			int endZ = Math.max(this.getZWithOffset(minX, minZ), this.getZWithOffset(maxX, maxZ)) - startZ;
			
			int attempts = rand.nextInt(2);
			WorldGenShrub shrub = new WorldGenShrub(0, 0);
			
			for(int i = 0; i < attempts; i++) {
				int posX = startX + rand.nextInt(endX <= 0 ? 1 : endX);
				int posY = this.getYWithOffset(startY);
				int posZ = startZ + rand.nextInt(endZ <= 0 ? 1 : endZ);
				
				if(box.isVecInside(posX, posY, posZ)) {
					int brake = 0;
					
					do {
						Block block = world.getBlock(posX, posY, posZ);
						
						if(!((block.isAir(world, posX, posY, posZ) || block.isFoliage(world, posX, posY, posZ) || block.isLeaves(world, posX, posY, posZ)) && posY > 0)) {
							shrub.generate(world, rand, posX, posY, posZ);
							break;
						}
						
						posY--;
					} while(brake++ <= 15);
				}
			}
		}*/
	}
	
	//the block selector in general is kinda mid, i might just replace it entirely
	public static class DirtyGlass extends StructureComponent.BlockSelector {
		protected boolean webs;
		protected float chance;
		protected int meta; //argh
		
		public DirtyGlass(BiomeGenBase biome, float chance, boolean webs) {
			if(BiomeDictionary.isBiomeOfType(biome, Type.COLD))
				this.meta = 8; //fogged-up
			else if(NTMWorldGenerator.doesBiomeHaveTypes(biome, Type.WASTELAND, Type.JUNGLE, Type.SANDY, Type.SAVANNA, Type.SWAMP))
				this.meta = 12; //super dirty
			else
				this.meta = 7; //dirty
			
			this.field_151562_a = Blocks.stained_glass_pane;
			this.chance = chance;
			this.webs = webs;
		}
		
		@Override
		public void selectBlocks(Random rand, int posX, int posY, int posZ, boolean notInterior) {
			if(rand.nextFloat() <= chance) {
				this.selectedBlockMetaData = 0;
				if(webs && rand.nextInt(3) == 0)
					this.field_151562_a = Blocks.web;
				else
					this.field_151562_a = Blocks.air;
			} else {
				this.field_151562_a = Blocks.stained_glass_pane;
				this.selectedBlockMetaData = meta;
			}
		}
	}
}
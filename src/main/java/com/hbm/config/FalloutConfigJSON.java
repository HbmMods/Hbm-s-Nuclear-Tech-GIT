package com.hbm.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;
import com.hbm.inventory.RecipesCommon.MetaBlock;
import com.hbm.main.MainRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class FalloutConfigJSON {
	
	public static final List<FalloutEntry> entries = new ArrayList();
	public static Random rand = new Random();

	public static final Gson gson = new Gson();
	
	public static void initialize() {
		File folder = MainRegistry.configDir;
		
		File recFile = new File(folder.getAbsolutePath() + File.separatorChar + "hbmFallout.json");
	}
	
	public static class FalloutEntry {
		private Block matchesBlock = null;
		private int matchesMeta = -1;
		private Material matchesMaterial = null;

		private MetaBlock[] primaryBlocks = null;
		private MetaBlock[] secondaryBlocks = null;
		private float primaryChance = 1.0F;
		private double minDist = 0.0D;
		private double maxDist = 1.0D;
		
		private boolean isSolid = false;

		public FalloutEntry mB(Block block) { this.matchesBlock = block; return this; }
		public FalloutEntry mM(int meta) { this.matchesMeta = meta; return this; }
		public FalloutEntry mMa(Material mat) { this.matchesMaterial = mat; return this; }

		public FalloutEntry prim(MetaBlock... blocks) { this.primaryBlocks = blocks; return this; }
		public FalloutEntry sec(MetaBlock... blocks) { this.secondaryBlocks = blocks; return this; }
		public FalloutEntry c(float chance) { this.primaryChance = chance; return this; }
		public FalloutEntry min(double min) { this.minDist = min; return this; }
		public FalloutEntry max(double max) { this.maxDist = max; return this; }
		public FalloutEntry sol(boolean solid) { this.isSolid = solid; return this; }
		
		public boolean eval(World world, int x, int y, int z, Block b, double dist) {
			
			if(matchesBlock != null && b != matchesBlock) return false;
			if(matchesMaterial != null && b.getMaterial() != matchesMaterial) return false;
			if(matchesMeta != -1 && world.getBlockMetadata(x, y, z) != matchesMeta) return false;
			if(dist > maxDist || dist < minDist) return false;
			
			if(primaryChance < 1F && rand.nextFloat() > primaryChance) {
				
				if(primaryBlocks == null) return false;
				
				MetaBlock block = primaryBlocks[rand.nextInt(primaryBlocks.length)];
				world.setBlock(x, y, z, block.block, block.meta, 2);
				return true;
				
			} else {
				
				if(secondaryBlocks == null) return false;
				
				MetaBlock block = secondaryBlocks[rand.nextInt(secondaryBlocks.length)];
				world.setBlock(x, y, z, block.block, block.meta, 2);
				return true;
			}
		}
		
		public boolean isSolid() {
			return this.isSolid;
		}
	}
}

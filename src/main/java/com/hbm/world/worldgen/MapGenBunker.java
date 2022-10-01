package com.hbm.world.worldgen;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureStart;

public class MapGenBunker extends MapGenStructure {
	
	/** String ID for this MapGen */
	@Override
	public String func_143025_a() {
		return "NTMBunker";
	}

	@Override
	protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
		return false;
	}

	@Override
	protected StructureStart getStructureStart(int chunkX, int chunkZ) {
		return new Start(this.worldObj, this.rand, chunkX, chunkZ);
	}
	
	public static class Start extends StructureStart {
		
		public Start() { }
		
		public Start(World world, Random rand, int chunkX, int chunkZ) {
			super(chunkX, chunkZ);
			
			
			this.updateBoundingBox();
			this.markAvailableHeight(world, rand, 10);
		}
		
	}
}

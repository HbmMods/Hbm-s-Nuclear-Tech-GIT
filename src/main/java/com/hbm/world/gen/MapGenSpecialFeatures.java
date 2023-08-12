package com.hbm.world.gen;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.hbm.config.GeneralConfig;
import com.hbm.world.gen.component.BrutalistFeatures.ElevatedLab1;

import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;

//stuff like the exclusive n rare shipping containers.
//it's 'special' because it's not unique but also limited
public class MapGenSpecialFeatures extends MapGenStructure {
	//suuuuuper efficient for .contains()
	Set<ChunkCoordIntPair> locs = new HashSet<ChunkCoordIntPair>();
	
	/** String ID for this MapGen */
	@Override
	public String func_143025_a() {
		return "NTMSpecialFeatures";
	}

	@Override
	protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
		
		if(locs.isEmpty()) {
			
			Random rand = new Random(this.worldObj.getSeed());
			double theta = rand.nextDouble() * Math.PI * 2;
			int ringMax = 4; //each ring of structures has more (and is farther) than the last
			int ringDist = 1;
			final int total = 16; //for now
			//no biome checks necessary, underground caches can always be a backup
			for(int i = 1; i <= total; i++) {
				double dist = 312D * (1.25 * ringDist + rand.nextDouble() * 0.5); //5k blocks * random dist in the region of a ring
				int cX = (int)Math.round(Math.cos(theta) * dist);
				int cZ = (int)Math.round(Math.sin(theta) * dist);
				
				ChunkCoordIntPair pair = new ChunkCoordIntPair(cX, cZ);
				locs.add(pair);
				
				theta += Math.PI * 2 / ringMax;
				
				if(i == (ringDist - 1) * 4 + ringMax) {
					ringDist++;
					
					if(i + ringDist * 4 > total) //last ring may be sparser, but evenly spaced too
						ringMax = total - i;
					else
						ringMax = ringDist * 4;
				}
			}
		}
		
		return locs.contains(new ChunkCoordIntPair(chunkX, chunkZ));
	}

	@Override
	protected StructureStart getStructureStart(int chunkX, int chunkZ) {
		return new SpecialStart(this.worldObj, this.rand, chunkX, chunkZ);
	}
	
	public static class SpecialStart extends StructureStart {
		
		public SpecialStart() {}
		
		public SpecialStart(World world, Random rand, int chunkX, int chunkZ) {
			super(chunkX, chunkZ);
			
			//test
			ElevatedLab1 lab1 = new ElevatedLab1(rand, chunkX * 16 + 8, 64, chunkZ * 16 + 8);
			this.components.add(lab1);
			
			if(GeneralConfig.enableDebugMode) {
				System.out.print("[Debug] StructureStart at " + (chunkX * 16 + 8) + ", " + 64 + ", " + (chunkZ * 16 + 8) + "\n[Debug] Components: ");
				this.components.forEach((component) -> {
					System.out.print(MapGenStructureIO.func_143036_a((StructureComponent) component) + " ");
				});
				
				System.out.print("\n");
			}
			
			this.updateBoundingBox();
		}
	}
	
}

package com.hbm.world.gen;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.hbm.config.GeneralConfig;
import com.hbm.world.gen.component.SpecialFeatures.SpecialContainer;

import net.minecraft.util.Vec3;
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
	//do i even need chunkcoordintpairs? idk
	Set<ChunkCoordIntPair> locs = new HashSet<ChunkCoordIntPair>();
	//efficient enough for books n shit
	List<ChunkCoordIntPair> bookLocs = new ArrayList<ChunkCoordIntPair>();
	
	/** String ID for this MapGen */
	@Override
	public String func_143025_a() {
		return "NTMSpecialFeatures";
	}
	
	@Override
	protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
		if(locs.isEmpty())
			generatePositions();
		
		return locs.contains(new ChunkCoordIntPair(chunkX, chunkZ));
	}
	
	//i'll probs make a system to predict which locations are what in advance
	//seems like biomes can be cached/gen'd without creating the chunk, thankfully
	//vec3 will be the angle + distance from provided coords, given in chunk coords
	/*public Vec3 findClosestPosition(int chunkX, int chunkZ) {
		createBookList();
		
		long time = System.nanoTime();
		
		ChunkCoordIntPair pair = new ChunkCoordIntPair(0, 0);
		long dist = Long.MAX_VALUE;
		for(ChunkCoordIntPair loc : bookLocs) {
			int x = loc.chunkXPos - chunkX;
			int z = loc.chunkZPos - chunkZ;
			long cont = x * x + z * z;
			
			if(cont < dist) {
				pair = loc;
				dist = cont;
			}
		}
		
		System.out.print(System.nanoTime() - time);
		
		return Vec3.createVectorHelper(pair.chunkXPos - chunkX, 0, pair.chunkZPos - chunkZ);
	}
	
	protected void createBookList() {
		if(locs.isEmpty())
			generatePositions();
		
		if(!bookLocs.isEmpty()) return;
		
		long time = System.nanoTime();
		
		for(ChunkCoordIntPair loc : locs) {
			bookLocs.add(loc);
		}
		
		System.out.print(System.nanoTime() - time);
	}*/
	
	protected void generatePositions() {
		//for safety: maybe mandate interactions with these methods to an outside class/wrapper who say "fuck you"
		Random rand = new Random(this.worldObj.getSeed()); //TODO: worldObj is null until func_15139_a is called!! very bad!!!
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
			
			if(GeneralConfig.enableDebugMode)
				System.out.println("SpecialFeature: " + (pair.chunkXPos * 16 + 8) + ", Y, " + (pair.chunkZPos * 16 + 8));
			
			theta += Math.PI * 2 / ringMax;
			
			if(i == (ringDist - 1) * 4 + ringMax) {
				ringDist++;
				//maybe insert random theta each time?
				if(i + ringDist * 4 > total) //last ring may be sparser, but evenly spaced too
					ringMax = total - i;
				else
					ringMax = ringDist * 4;
			}
		}
	}

	@Override
	protected StructureStart getStructureStart(int chunkX, int chunkZ) {
		locs.remove(new ChunkCoordIntPair(chunkX, chunkZ));
		
		return new SpecialStart(this.worldObj, this.rand, chunkX, chunkZ);
	}
	
	public static class SpecialStart extends StructureStart {
		
		public SpecialStart() {}
		
		public SpecialStart(World world, Random rand, int chunkX, int chunkZ) {
			super(chunkX, chunkZ);
			
			//test
			SpecialContainer shipping = new SpecialContainer(rand, chunkX * 16 + 8, 64, chunkZ * 16 + 8);
			this.components.add(shipping);
			
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

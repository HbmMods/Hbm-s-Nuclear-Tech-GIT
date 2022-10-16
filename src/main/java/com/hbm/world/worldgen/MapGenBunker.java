package com.hbm.world.worldgen;

import java.util.List;
import java.util.Random;

import com.hbm.config.GeneralConfig;
import com.hbm.world.worldgen.components.BunkerComponents;
import com.hbm.world.worldgen.components.BunkerComponents.Atrium;
import com.hbm.world.worldgen.components.ProceduralComponents.ProceduralComponent;

import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureComponent;
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
			BunkerComponents.prepareComponents();
			Atrium atrium = new Atrium(0, rand, (chunkX << 4) + 8, (chunkZ << 4) + 8);
			this.components.add(atrium);
			atrium.buildComponent(atrium, components, rand);
			atrium.underwater = true;//rand.nextInt(2) == 0;
			
			List list = atrium.queuedComponents;
			while(!list.isEmpty()) {
				int k = rand.nextInt(list.size());
				ProceduralComponent component = (ProceduralComponent)list.remove(k);
				atrium.lastComponent = component;
				component.buildComponent(atrium, this.components, rand);
			}
			
			if(GeneralConfig.enableDebugMode) {
				System.out.print("[Debug] StructureStart at " + (chunkX * 16 + 8) + ", idfk lmao, " + (chunkZ * 16 + 8) + "\n[Debug] Components: ");
				this.components.forEach((component) -> {
					System.out.print(MapGenStructureIO.func_143036_a((StructureComponent) component) + " ");
				});
				
				System.out.print("\n");
			}
			
			this.updateBoundingBox();
			this.markAvailableHeight(world, rand, 10);
			
			System.out.print(this.boundingBox.minY);
		}
		
	}
}

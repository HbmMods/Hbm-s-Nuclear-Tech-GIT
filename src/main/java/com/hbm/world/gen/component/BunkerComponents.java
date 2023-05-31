package com.hbm.world.gen.component;

import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.HbmChestContents;
import com.hbm.world.gen.ProceduralStructureStart;
import com.hbm.world.gen.ProceduralStructureStart.ProceduralComponent;
import com.hbm.world.gen.component.Component.ConcreteBricks;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class BunkerComponents {
	
	public static class BunkerStart extends ProceduralStructureStart {
		
		public BunkerStart(World world, Random rand, int chunkX, int chunkZ) {
			super(chunkX, chunkZ);
			
			this.sizeLimit = 7 + rand.nextInt(6);
			this.distanceLimit = 40;
			
			final int x = chunkX * 16 + 8;
			final int z = chunkZ * 16 + 8;
			
			Weight[] weights = new Weight[] {
				new Weight(6, 3, Corridor::findValidPlacement),
				new Weight(7, 4, BedroomL::findValidPlacement),
				new Weight(10, 3, FunJunction::findValidPlacement),
				new Weight(5, 2, BathroomL::findValidPlacement),
			};
			
			StructureComponent starter = new StartingHub(rand, x, z);
			
			buildStart(world, rand, starter, weights);
			
			this.markAvailableHeight(world, rand, 20);
		}
		
	}
	
	public static void registerComponents() {
		MapGenStructureIO.func_143031_a(StartingHub.class, "NTMBStartingHub");
		MapGenStructureIO.func_143031_a(Corridor.class, "NTMBCorridor");
		MapGenStructureIO.func_143031_a(BedroomL.class, "NTMBBedroomL");
		MapGenStructureIO.func_143031_a(FunJunction.class, "NTMBFunJunction");
		MapGenStructureIO.func_143031_a(BathroomL.class, "NTMBBathroomL");
		//TODO more rooms for more variety
	}
	
	//why are we still doing this?
	private static ConcreteBricks ConcreteBricks = new ConcreteBricks();
	
	public static class StartingHub extends Component implements ProceduralComponent {
		
		private boolean[] paths = new boolean[3];
		
		public StartingHub() { }
		
		public StartingHub(Random rand, int x, int z) {
			super(rand, x, 64, z, 7, 5, 7);
		}
		
		public StartingHub(int componentType, StructureBoundingBox box, int coordMode) {
			super(componentType);
			this.boundingBox = box;
			this.coordBaseMode = coordMode;
		}
		
		/** write to nbt */
		@Override
		protected void func_143012_a(NBTTagCompound nbt) {
			super.func_143012_a(nbt);
			for(int i = 0; i < paths.length; i++)
				nbt.setBoolean("p" + i, paths[i]);
		}
		
		/** read from nbt */
		@Override
		protected void func_143011_b(NBTTagCompound nbt) {
			super.func_143011_b(nbt);
			for(int i = 0; i < paths.length; i++)
				paths[i] = nbt.getBoolean("p" + i);
		}
		
		@Override
		public void buildComponent(ProceduralStructureStart start, Random rand) {
			paths[0] = this.getNextComponentEast(start, this, coordBaseMode, rand, 5, 1) != null;
			paths[1] = this.getNextComponentAntiNormal(start, this, coordBaseMode, rand, 4, 1) != null;
			paths[2] = this.getNextComponentWest(start, this, coordBaseMode, rand, 3, 1) != null;
		}
		
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			fillWithAir(world, box, 1, 1, 1, 6, 3, 6);
			//floor
			fillWithMetadataBlocks(world, box, 1, 0, 1, 6, 0, 6, ModBlocks.vinyl_tile, 1);
			//ceiling
			fillWithBlocks(world, box, 1, 4, 1, 6, 4, 6, ModBlocks.vinyl_tile);
			//upper shield
			fillWithBlocks(world, box, 1, 4, 4, 3, 4, 6, ModBlocks.reinforced_stone);
			fillWithBlocks(world, box, 0, 5, 0, 7, 5, 7, ModBlocks.reinforced_stone);
			//walls
			fillWithRandomizedBlocks(world, box, 0, 0, 0, 0, 4, 7, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 1, 0, 7, 6, 4, 7, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 7, 0, 0, 7, 4, 7, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 1, 0, 0, 6, 4, 0, rand, ConcreteBricks);
			//meh, fix the area later
			final int hpos = Component.getAverageHeight(world, boundingBox, box, componentType - boundingBox.minY); 
			//top hatch
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_slab, 1, 0, hpos, 5, box);
			fillWithMetadataBlocks(world, box, 1, hpos, 4, 1, hpos, 6, ModBlocks.concrete_smooth_stairs, getStairMeta(0));
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_slab, 1, 2, hpos, 3, box);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, getStairMeta(2), 2, hpos, 4, box);
			placeBlockAtCurrentPosition(world, Blocks.trapdoor, getDecoModelMeta(8) >> 2, 2, hpos, 5, box);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, getStairMeta(3), 2, hpos, 6, box);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_slab, 1, 2, hpos, 7, box);
			fillWithMetadataBlocks(world, box, 3, hpos, 4, 3, hpos, 6, ModBlocks.concrete_smooth_stairs, getStairMeta(1));
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_slab, 1, 4, hpos, 5, box);
			//tunnel downwards
			fillWithBlocks(world, box, 1, 6, 4, 1, hpos - 1, 6, ModBlocks.reinforced_stone);
			fillWithBlocks(world, box, 2, 1, 6, 2, hpos - 1, 6, ModBlocks.reinforced_stone);
			fillWithBlocks(world, box, 3, 6, 4, 3, hpos - 1, 6, ModBlocks.reinforced_stone);
			fillWithBlocks(world, box, 2, 6, 4, 2, hpos - 1, 4, ModBlocks.reinforced_stone);
			fillWithMetadataBlocks(world, box, 2, 1, 5, 2, hpos - 1, 5, ModBlocks.ladder_sturdy, getDecoMeta(2)); //double check meta
			
			/* DECO */
			//lamps
			placeBlockAtCurrentPosition(world, ModBlocks.reinforced_lamp_off, 0, 2, 5, 2, box);
			placeBlockAtCurrentPosition(world, ModBlocks.reinforced_lamp_off, 0, 5, 5, 2, box);
			placeBlockAtCurrentPosition(world, ModBlocks.reinforced_lamp_off, 0, 5, 5, 5, box);
			placeBlockAtCurrentPosition(world, ModBlocks.fan, 0, 2, 4, 2, box);
			placeBlockAtCurrentPosition(world, ModBlocks.fan, 0, 5, 4, 2, box);
			placeBlockAtCurrentPosition(world, ModBlocks.fan, 0, 5, 4, 5, box);
			//machine
			placeBlockAtCurrentPosition(world, ModBlocks.deco_tungsten, 0, 3, 1, 6, box);
			generateInvContents(world, box, rand, Blocks.chest, getDecoMeta(3), 4, 1, 6, HbmChestContents.antenna/*TODO change */, 5);
			placeBlockAtCurrentPosition(world, ModBlocks.deco_tungsten, 0, 5, 1, 6, box);
			fillWithMetadataBlocks(world, box, 3, 2, 6, 5, 2, 6, ModBlocks.concrete_smooth_stairs, getStairMeta(2) | 4);
			fillWithMetadataBlocks(world, box, 3, 3, 6, 5, 3, 6, ModBlocks.tape_recorder, getDecoMeta(2));
			//desk
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, getStairMeta(1) | 4, 3, 1, 4, box);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, getStairMeta(3) | 4, 4, 1, 4, box);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, getStairMeta(0) | 4, 5, 1, 4, box);
			placeBlockAtCurrentPosition(world, ModBlocks.deco_computer, getDecoModelMeta(1), 4, 2, 4, box);
			//clear out entryways based on path
			if(paths[0]) fillWithAir(world, box, 7, 1, 2, 7, 2, 3);
			if(paths[1]) fillWithAir(world, box, 3, 1, 0, 4, 2, 0);
			if(paths[2]) fillWithAir(world, box, 0, 1, 2, 0, 2, 3);
			
			return true;
		}
	}
	
	public static class Corridor extends Component implements ProceduralComponent {
		
		private boolean path;
		private int[] decorations = new int[2];
		
		public Corridor() { }
		
		public Corridor(int componentType, StructureBoundingBox box, int coordMode, Random rand) {
			super(componentType);
			this.boundingBox = box;
			this.coordBaseMode = coordMode;
			
			decorations[0] = rand.nextInt(6);
			decorations[1] = rand.nextInt(6);
		}
		
		/** write to nbt */
		@Override
		protected void func_143012_a(NBTTagCompound nbt) {
			super.func_143012_a(nbt);
			nbt.setBoolean("p", path);
			nbt.setIntArray("d", decorations);
		}
		
		/** read from nbt */
		@Override
		protected void func_143011_b(NBTTagCompound nbt) {
			super.func_143011_b(nbt);
			path = nbt.getBoolean("p");
			decorations = nbt.getIntArray("d");
		}
		
		@Override
		public void buildComponent(ProceduralStructureStart start, Random rand) {
			path = this.getNextComponentNormal(start, this, coordBaseMode, rand, 3, 1) != null;
		}
		
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			fillWithAir(world, box, 1, 1, 1, 4, 3, 5);
			//floor
			fillWithMetadataBlocks(world, box, 1, 0, 1, 4, 0, 5, ModBlocks.vinyl_tile, 1);
			//ceiling
			fillWithBlocks(world, box, 1, 4, 1, 4, 4, 5, ModBlocks.vinyl_tile);
			//upper shield
			fillWithBlocks(world, box, 0, 5, 0, 5, 5, 6, ModBlocks.reinforced_stone);
			//walls
			fillWithRandomizedBlocks(world, box, 0, 0, 0, 0, 4, 6, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 1, 0, 6, 4, 4, 6, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 5, 0, 0, 5, 4, 6, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 1, 0, 0, 4, 4, 0, rand, ConcreteBricks);
			
			//TODO different deco types? maybe plants or vending machines?
			//save it to nbt either way
			/* DECO */
			//lamps
			fillWithBlocks(world, box, 2, 5, 3, 3, 5, 3, ModBlocks.reinforced_lamp_off);
			fillWithBlocks(world, box, 2, 4, 3, 3, 4, 3, ModBlocks.fan);
			//deco misc
			final int stairMetaW = getStairMeta(0);
			final int stairMetaE = getStairMeta(1);
			final int stairMetaN = getStairMeta(2);
			final int stairMetaS = getStairMeta(3);
			final int decoMetaE = getDecoMeta(4);
			final int decoMetaW = getDecoMeta(5);
			
			for(int i = 0; i <= 1; i++) {
				final int x = 1 + i * 3;
				switch (decorations[i]) {
					default: //table w/ chairs
						placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairMetaS, x, 1, 2, box);
						placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairMetaN, x, 1, 4, box);
						placeBlockAtCurrentPosition(world, Blocks.fence, 0, x, 1, 3, box);
						placeBlockAtCurrentPosition(world, Blocks.wooden_pressure_plate, 1, x, 2, 3, box);
						break;
					case 1://desk w/ computer
						placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, stairMetaS | 4, x, 1, 2, box);
						placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairMetaN, x, 1, 4, box);
						placeBlockAtCurrentPosition(world, ModBlocks.deco_computer, getDecoModelMeta(1), x, 2, 2, box);
						break;
					case 2: //couch
						placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairMetaS, x, 1, 2, box);
						placeBlockAtCurrentPosition(world, Blocks.oak_stairs, i < 1 ? stairMetaE : stairMetaW, x, 1, 3, box);
						placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairMetaN, x, 1, 4, box);
						break;
					case 3:
						placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, stairMetaS | 4, x, 1, 2, box);
						placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, (i < 1 ? stairMetaE : stairMetaW) | 4, x, 1, 3, box);
						placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, stairMetaN | 4, x, 1, 4, box);
						placeBlockAtCurrentPosition(world, Blocks.flower_pot, 0, x, 2, 2, box);
						break;
					case 4:
						fillWithBlocks(world, box, x, 1, 1, x, 3, 1, ModBlocks.deco_tungsten);
						placeBlockAtCurrentPosition(world, ModBlocks.deco_tungsten, 0, x, 1, 3, box);
						fillWithMetadataBlocks(world, box, x, 3, 2, x, 3, 4, ModBlocks.concrete_smooth_stairs, i < 1 ? stairMetaE : stairMetaW);
						fillWithBlocks(world, box, x, 1, 5, x, 3, 5, ModBlocks.deco_tungsten);
						fillWithMetadataBlocks(world, box, x, 1, 2, x, 2, 2, ModBlocks.tape_recorder, i < 1 ? decoMetaW : decoMetaE); //don't ask me
						fillWithMetadataBlocks(world, box, x, 1, 4, x, 2, 4, ModBlocks.tape_recorder, i < 1 ? decoMetaW : decoMetaE);
						placeBlockAtCurrentPosition(world, ModBlocks.deco_computer, i < 1 ? getDecoModelMeta(3) : getDecoModelMeta(2), x, 2, 3, box);
						break;
					case 5:
						placeBlockAtCurrentPosition(world, Blocks.fence, 0, x, 1, 1, box);
						placeBlockAtCurrentPosition(world, Blocks.wooden_pressure_plate, 0, x, 2, 1, box);
						placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, stairMetaS | 4, x, 1, 3, box);
						placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, stairMetaN | 4, x, 1, 4, box);
						placeBlockAtCurrentPosition(world, ModBlocks.radiorec, i < 1 ? decoMetaE : decoMetaW, x, 2, 3, box);
						break;
				}
			}
			//doors
			placeDoor(world, box, ModBlocks.door_bunker, 1, true, rand.nextBoolean(), 2, 1, 0);
			placeDoor(world, box, ModBlocks.door_bunker, 1, false, rand.nextBoolean(), 3, 1, 0);
			if(path) fillWithAir(world, box, 2, 1, 6, 3, 2, 6);
			
			return true;
		}
		
		public static StructureComponent findValidPlacement(List components, Random rand, int x, int y, int z, int coordMode, int type) {
			StructureBoundingBox box = ProceduralStructureStart.getComponentToAddBoundingBox(x, y, z, -3, -1, 0, 6, 6, 7, coordMode);
			return box.minY > 10 && StructureComponent.findIntersecting(components, box) == null ? new Corridor(type, box, coordMode, rand) : null;
		}
	}
	
	public static class BedroomL extends Component implements ProceduralComponent {
		
		private boolean path;
		
		public BedroomL() { }
		
		public BedroomL(int componentType, StructureBoundingBox box, int coordMode) {
			super(componentType);
			this.boundingBox = box;
			this.coordBaseMode = coordMode;
		}
		
		/** write to nbt */
		@Override
		protected void func_143012_a(NBTTagCompound nbt) {
			super.func_143012_a(nbt);
			nbt.setBoolean("p", path);
		}
		
		/** read from nbt */
		@Override
		protected void func_143011_b(NBTTagCompound nbt) {
			super.func_143011_b(nbt);
			path = nbt.getBoolean("p");
		}
		
		@Override
		public void buildComponent(ProceduralStructureStart start, Random rand) {
			path = this.getNextComponentWest(start, this, coordBaseMode, rand, 9, 1) != null;
		}
		
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			fillWithAir(world, box, 4, 1, 1, 8, 3, 4);
			fillWithAir(world, box, 1, 1, 5, 8, 3, 9);
			//floor
			fillWithMetadataBlocks(world, box, 4, 0, 1, 8, 0, 4, ModBlocks.vinyl_tile, 1);
			fillWithMetadataBlocks(world, box, 1, 0, 5, 8, 0, 9, ModBlocks.vinyl_tile, 1);
			//ceiling
			fillWithBlocks(world, box, 4, 4, 1, 8, 4, 4, ModBlocks.vinyl_tile);
			fillWithBlocks(world, box, 1, 4, 5, 8, 4, 9, ModBlocks.vinyl_tile);
			//upper shield
			fillWithBlocks(world, box, 3, 5, 0, 9, 5, 3, ModBlocks.reinforced_stone);
			fillWithBlocks(world, box, 0, 5, 4, 9, 5, 10, ModBlocks.reinforced_stone);
			//walls
			fillWithRandomizedBlocks(world, box, 0, 0, 4, 0, 4, 10, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 1, 0, 10, 8, 4, 10, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 9, 0, 0, 9, 4, 10, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 4, 0, 0, 8, 4, 0, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 3, 0, 0, 3, 4, 4, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 1, 0, 4, 2, 4, 4, rand, ConcreteBricks);
			
			/* DECO */
			//lamps
			placeBlockAtCurrentPosition(world, ModBlocks.reinforced_lamp_off, 0, 3, 5, 7, box);
			placeBlockAtCurrentPosition(world, ModBlocks.reinforced_lamp_off, 0, 6, 5, 7, box);
			placeBlockAtCurrentPosition(world, ModBlocks.reinforced_lamp_off, 0, 6, 5, 3, box);
			placeBlockAtCurrentPosition(world, ModBlocks.fan, 0, 3, 4, 7, box);
			placeBlockAtCurrentPosition(world, ModBlocks.fan, 0, 6, 4, 7, box);
			placeBlockAtCurrentPosition(world, ModBlocks.fan, 0, 6, 4, 3, box);
			//Beds w/ table
			final int stairMetaW = getStairMeta(0);
			final int stairMetaE = getStairMeta(1);
			final int stairMetaN = getStairMeta(2);
			final int stairMetaS = getStairMeta(3);
			placeBed(world, box, 1, 5, 1, 1);
			placeBed(world, box, 1, 5, 1, 3);
			placeBed(world, box, 2, 3, 1, 6);
			placeBed(world, box, 2, 1, 1, 6);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, stairMetaE | 4, 4, 1, 2, box);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, stairMetaE | 4, 4, 1, 4, box);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, stairMetaS | 4, 4, 1, 5, box);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, stairMetaS | 4, 2, 1, 5, box);
			placeBlockAtCurrentPosition(world, ModBlocks.radiorec, getDecoMeta(4), 4, 2, 4, box);
			//table w/ microwave
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, stairMetaS | 4, 8, 1, 3, box);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, stairMetaN | 4, 8, 1, 4, box);
			placeBlockAtCurrentPosition(world, Blocks.noteblock, 0, 8, 1, 5, box);
			placeBlockAtCurrentPosition(world, ModBlocks.machine_microwave, getDecoMeta(4), 8, 2, 4, box);
			//desk w/ computer
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, stairMetaW | 4, 6, 1, 9, box);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, stairMetaN | 4, 5, 1, 9, box);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, stairMetaE | 4, 4, 1, 9, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairMetaS, 5, 1, 8, box);
			placeBlockAtCurrentPosition(world, ModBlocks.deco_computer, getDecoModelMeta(0), 5, 2, 9, box);
			generateInvContents(world, box, rand, ModBlocks.filing_cabinet, getDecoModelMeta(0), 3, 1, 9, HbmChestContents.antenna/*TODO change */, 5);
			//lockers
			generateInvContents(world, box, rand, Blocks.chest, getDecoMeta(4), 8, 1, 7, HbmChestContents.antenna/*TODO change */, 3);
			generateInvContents(world, box, rand, Blocks.chest, getDecoMeta(4), 8, 2, 7, HbmChestContents.antenna/*TODO change */, 3);
			fillWithBlocks(world, box, 8, 1, 8, 8, 2, 8, ModBlocks.deco_tungsten);
			generateInvContents(world, box, rand, Blocks.chest, getDecoMeta(4), 8, 1, 9, HbmChestContents.antenna/*TODO change */, 3);
			generateInvContents(world, box, rand, Blocks.chest, getDecoMeta(4), 8, 2, 9, HbmChestContents.antenna/*TODO change */, 3);
			fillWithMetadataBlocks(world, box, 8, 3, 7, 8, 3, 9, Blocks.trapdoor, getDecoModelMeta(2) >> 2);
			//doors
			placeDoor(world, box, ModBlocks.door_bunker, 1, true, rand.nextBoolean(), 7, 1, 0);
			placeDoor(world, box, ModBlocks.door_bunker, 1, false, rand.nextBoolean(), 8, 1, 0);
			if(path) fillWithAir(world, box, 0, 1, 8, 0, 2, 9);
			
			return true;
		}
		
		public static StructureComponent findValidPlacement(List components, Random rand, int x, int y, int z, int coordMode, int type) {
			StructureBoundingBox box = ProceduralStructureStart.getComponentToAddBoundingBox(x, y, z, -8, -1, 0, 10, 6, 11, coordMode);
			return box.minY > 10 && StructureComponent.findIntersecting(components, box) == null ? new BedroomL(type, box, coordMode) : null;
		}
	}
	
	public static class FunJunction extends Component implements ProceduralComponent {
		
		private boolean[] paths = new boolean[2];
		
		public FunJunction() { }
		
		public FunJunction(int componentType, StructureBoundingBox box, int coordMode) {
			super(componentType);
			this.boundingBox = box;
			this.coordBaseMode = coordMode;
		}
		
		/** write to nbt */
		@Override
		protected void func_143012_a(NBTTagCompound nbt) {
			super.func_143012_a(nbt);
			for(int i = 0; i < paths.length; i++)
				nbt.setBoolean("p" + i, paths[i]);
		}
		
		/** read from nbt */
		@Override
		protected void func_143011_b(NBTTagCompound nbt) {
			super.func_143011_b(nbt);
			for(int i = 0; i < paths.length; i++)
				paths[i] = nbt.getBoolean("p" + i);
		}
		
		@Override
		public void buildComponent(ProceduralStructureStart start, Random rand) {
			paths[0] = this.getNextComponentEast(start, this, coordBaseMode, rand, 6, 1) != null;
			paths[1] = this.getNextComponentNormal(start, this, coordBaseMode, rand, 5, 1) != null;
		}
		
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			fillWithAir(world, box, 1, 1, 1, 6, 3, 10);
			//floor
			fillWithMetadataBlocks(world, box, 1, 0, 1, 6, 0, 10, ModBlocks.vinyl_tile, 1);
			//ceiling
			fillWithBlocks(world, box, 1, 4, 1, 6, 4, 10, ModBlocks.vinyl_tile);
			//upper shield
			fillWithBlocks(world, box, 0, 5, 0, 7, 5, 11, ModBlocks.reinforced_stone);
			//walls
			fillWithRandomizedBlocks(world, box, 0, 0, 0, 0, 4, 11, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 1, 0, 11, 6, 4, 11, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 7, 0, 0, 7, 4, 11, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 1, 0, 0, 6, 4, 0, rand, ConcreteBricks);
			
			/* DECO */
			//lamps
			placeBlockAtCurrentPosition(world, ModBlocks.reinforced_lamp_off, 0, 2, 5, 3, box);
			fillWithBlocks(world, box, 5, 5, 5, 5, 5, 6, ModBlocks.reinforced_lamp_off);
			placeBlockAtCurrentPosition(world, ModBlocks.reinforced_lamp_off, 0, 2, 5, 8, box);
			placeBlockAtCurrentPosition(world, ModBlocks.fan, 0, 2, 4, 3, box);
			fillWithBlocks(world, box, 5, 4, 5, 5, 4, 6, ModBlocks.fan);
			placeBlockAtCurrentPosition(world, ModBlocks.fan, 0, 2, 4, 8, box);
			//couches w/ tables
			final int stairMetaW = getStairMeta(0);
			final int stairMetaE = getStairMeta(1);
			final int stairMetaN = getStairMeta(2);
			final int stairMetaS = getStairMeta(3);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairMetaE, 1, 1, 1, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairMetaS, 2, 1, 1, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairMetaW, 3, 1, 1, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairMetaS, 1, 1, 4, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairMetaE, 1, 1, 5, box);
			fillWithMetadataBlocks(world, box, 1, 1, 6, 2, 1, 6, Blocks.oak_stairs, stairMetaN);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairMetaW, 3, 1, 6, box);
			placeBlockAtCurrentPosition(world, Blocks.fence, 0, 1, 1, 3, box);
			placeBlockAtCurrentPosition(world, Blocks.wooden_pressure_plate, 0, 1, 2, 3, box);
			placeBlockAtCurrentPosition(world, Blocks.fence, 0, 3, 1, 4, box);
			placeBlockAtCurrentPosition(world, Blocks.wooden_pressure_plate, 0, 3, 2, 4, box);
			//table & chest
			placeBlockAtCurrentPosition(world, Blocks.fence, 0, 6, 1, 2, box);
			placeBlockAtCurrentPosition(world, Blocks.wooden_pressure_plate, 0, 6, 2, 2, box);
			generateInvContents(world, box, rand, Blocks.chest, getDecoMeta(4), 6, 1, 3, HbmChestContents.antenna/*TODO change */, 7);
			//desk w/ computer + bobblehead
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, stairMetaS | 4, 1, 1, 8, box);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, stairMetaE | 4, 1, 1, 9, box);
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_smooth_stairs, stairMetaN | 4, 1, 1, 10, box);
			placeBlockAtCurrentPosition(world, Blocks.oak_stairs, stairMetaS, 2, 1, 8, box);
			placeBlockAtCurrentPosition(world, ModBlocks.deco_computer, getDecoModelMeta(3), 1, 2, 9, box);
			if(rand.nextBoolean()) placeRandomBobble(world, box, rand, 1, 2, 8);
			//jukebox
			fillWithBlocks(world, box, 6, 1, 8, 6, 2, 8, Blocks.noteblock);
			placeBlockAtCurrentPosition(world, ModBlocks.deco_tungsten, 0, 6, 1, 9, box);
			placeBlockAtCurrentPosition(world, ModBlocks.tape_recorder, getDecoMeta(4), 6, 2, 9, box);
			fillWithBlocks(world, box, 6, 3, 8, 6, 3, 9, ModBlocks.concrete_slab);
			placeBlockAtCurrentPosition(world, Blocks.lever, getDecoMeta(2), 5, 1, 9, box); //double-check meta
			//doors
			placeDoor(world, box, ModBlocks.door_bunker, 1, true, rand.nextBoolean(), 4, 1, 0);
			placeDoor(world, box, ModBlocks.door_bunker, 1, false, rand.nextBoolean(), 5, 1, 0);
			if(paths[0]) fillWithAir(world, box, 7, 1, 5, 7, 2, 6);
			if(paths[1]) fillWithAir(world, box, 4, 1, 11, 5, 2, 11);
			
			return true;
		}
		
		public static StructureComponent findValidPlacement(List components, Random rand, int x, int y, int z, int coordMode, int type) {
			StructureBoundingBox box = ProceduralStructureStart.getComponentToAddBoundingBox(x, y, z, -5, -1, 0, 8, 6, 12, coordMode);
			return box.minY > 10 && StructureComponent.findIntersecting(components, box) == null ? new FunJunction(type, box, coordMode) : null;
		}
	}
	
	public static class BathroomL extends Component implements ProceduralComponent {
		
		private boolean path;
		
		public BathroomL() { }
		
		public BathroomL(int componentType, StructureBoundingBox box, int coordMode) {
			super(componentType);
			this.boundingBox = box;
			this.coordBaseMode = coordMode;
		}
		
		/** write to nbt */
		@Override
		protected void func_143012_a(NBTTagCompound nbt) {
			super.func_143012_a(nbt);
			nbt.setBoolean("p", path);
		}
		
		/** read from nbt */
		@Override
		protected void func_143011_b(NBTTagCompound nbt) {
			super.func_143011_b(nbt);
			path = nbt.getBoolean("p");
		}
		
		@Override
		public void buildComponent(ProceduralStructureStart start, Random rand) {
			path = this.getNextComponentEast(start, this, coordBaseMode, rand, 3, 1) != null;
		}
		
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			fillWithAir(world, box, 1, 1, 1, 7, 3, 9);
			//floor
			fillWithMetadataBlocks(world, box, 1, 0, 1, 7, 0, 9, ModBlocks.vinyl_tile, 1);
			//ceiling
			fillWithBlocks(world, box, 1, 4, 1, 7, 4, 9, ModBlocks.vinyl_tile);
			//upper shield
			fillWithBlocks(world, box, 0, 5, 0, 8, 5, 10, ModBlocks.reinforced_stone);
			//walls
			fillWithRandomizedBlocks(world, box, 0, 0, 0, 0, 4, 10, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 1, 0, 10, 7, 4, 10, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 8, 0, 0, 8, 4, 10, rand, ConcreteBricks);
			fillWithRandomizedBlocks(world, box, 1, 0, 0, 7, 4, 0, rand, ConcreteBricks);
			
			/* DECO */
			//lamps
			placeBlockAtCurrentPosition(world, ModBlocks.reinforced_lamp_off, 0, 2, 5, 3, box);
			placeBlockAtCurrentPosition(world, ModBlocks.reinforced_lamp_off, 0, 2, 5, 7, box);
			placeBlockAtCurrentPosition(world, ModBlocks.reinforced_lamp_off, 0, 5, 5, 7, box);
			placeBlockAtCurrentPosition(world, ModBlocks.reinforced_lamp_off, 0, 5, 5, 3, box);
			placeBlockAtCurrentPosition(world, ModBlocks.fan, 0, 2, 4, 3, box);
			placeBlockAtCurrentPosition(world, ModBlocks.fan, 0, 2, 4, 7, box);
			placeBlockAtCurrentPosition(world, ModBlocks.fan, 0, 5, 4, 7, box);
			placeBlockAtCurrentPosition(world, ModBlocks.fan, 0, 5, 4, 3, box);
			//sinks
			for(int i = 2; i <= 8; i += 2) {
				placeBlockAtCurrentPosition(world, Blocks.cauldron, rand.nextInt(4), 1, 1, i, box);
				placeBlockAtCurrentPosition(world, ModBlocks.concrete_slab, 8, 1, 1, i + 1, box);
				placeBlockAtCurrentPosition(world, Blocks.tripwire_hook, getTripwireMeta(3), 1, 2, i, box);
			}
			//hand-dryers (industrial-strength)
			placeBlockAtCurrentPosition(world, ModBlocks.steel_beam, 3, 4, 1, 9, box);
			placeBlockAtCurrentPosition(world, ModBlocks.fan, getDecoMeta(2), 4, 2, 9, box);
			placeBlockAtCurrentPosition(world, Blocks.stone_button, 2, 3, 2, 9, box); //TODO button meta
			placeBlockAtCurrentPosition(world, ModBlocks.steel_beam, 3, 6, 1, 9, box);
			placeBlockAtCurrentPosition(world, ModBlocks.fan, getDecoMeta(2), 6, 2, 9, box);
			placeBlockAtCurrentPosition(world, Blocks.stone_button, 1, 7, 2, 9, box);
			//stalls w/ toilets
			for(int i = 1; i <= 5; i += 2) {
				placeBlockAtCurrentPosition(world, ModBlocks.door_metal, 0, 5, 1, i, box);
				placeDoor(world, box, ModBlocks.door_metal, 0, false, rand.nextBoolean(), 5, 1, i);
				fillWithMetadataBlocks(world, box, 5, 1, i + 1, 5, 2, i + 1, ModBlocks.steel_corner, getDecoMeta(2));
				fillWithMetadataBlocks(world, box, 6, 1, i + 1, 7, 2, i + 1, ModBlocks.steel_wall, getDecoMeta(2));
				placeBlockAtCurrentPosition(world, ModBlocks.deco_pipe_rim, 0, 7, 1, i, box);
				placeBlockAtCurrentPosition(world, Blocks.trapdoor, getDecoModelMeta(2) >> 2, 7, 2, i, box);
			}
			//doors
			placeDoor(world, box, ModBlocks.door_bunker, 1, true, rand.nextBoolean(), 2, 1, 0);
			placeDoor(world, box, ModBlocks.door_bunker, 1, false, rand.nextBoolean(), 3, 1, 0);
			if(path) fillWithAir(world, box, 8, 1, 7, 8, 2, 8);
			
			return true;
		}
		
		public static StructureComponent findValidPlacement(List components, Random rand, int x, int y, int z, int coordMode, int type) {
			StructureBoundingBox box = ProceduralStructureStart.getComponentToAddBoundingBox(x, y, z, -3, -1, 0, 9, 6, 11, coordMode);
			return box.minY > 10 && StructureComponent.findIntersecting(components, box) == null ? new BathroomL(type, box, coordMode) : null;
		}
	}
}

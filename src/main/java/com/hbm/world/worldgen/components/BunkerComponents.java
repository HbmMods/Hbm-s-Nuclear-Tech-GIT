package com.hbm.world.worldgen.components;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class BunkerComponents extends ProceduralComponents {
	
	protected static final Weight[] weightArray = new Weight[] {
			new Weight(1, 50, (list, rand, x, y, z, mode, type) -> { StructureBoundingBox box = ProceduralComponent.getComponentToAddBoundingBox(x, y, z, -1, -1, 0, 5, 6, 15, mode);
				return box.minY > 10 && StructureComponent.findIntersecting(list, box) == null ? new Corridor(type, rand, box, mode) : null; }),
			new Weight(2, -1, (list, rand, x, y, z, mode, type) -> { StructureBoundingBox box = ProceduralComponent.getComponentToAddBoundingBox(x, y, z, -1, -1, 0, 5, 6, 15, mode);
				return box.minY > 10 && StructureComponent.findIntersecting(list, box) == null ? new Corridor(type, rand, box, mode) : null; }),
			new Weight(1, -1, (list, rand, x, y, z, mode, type) -> { StructureBoundingBox box = ProceduralComponent.getComponentToAddBoundingBox(x, y, z, -1, -1, 0, 5, 6, 5, mode);
				return box.minY > 10 && StructureComponent.findIntersecting(list, box) == null ? new Intersection(type, rand, box, mode) : null; }),
			new Weight(8, -1, (list, rand, x, y, z, mode, type) -> { StructureBoundingBox box = ProceduralComponent.getComponentToAddBoundingBox(x, y, z, -3, -1, 0, 9, 6, 17, mode);
				return box.minY > 10 && StructureComponent.findIntersecting(list, box) == null ? new WideCorridor(type, rand, box, mode) : null; }),
			
	};
	
	public static void prepareComponents() {
		componentWeightList = new ArrayList();
		
		for(int i = 0; i < weightArray.length; i++) {
			weightArray[i].instancesSpawned = 0;
			componentWeightList.add(weightArray[i]);
		}
	}
	
	public static class Atrium extends ControlComponent {
		
		public Atrium() { }
		
		public Atrium(int componentType, Random rand, int posX, int posZ) { //TODO: change basically everything about this component
			super(componentType);
			this.coordBaseMode = rand.nextInt(4);
			this.boundingBox = new StructureBoundingBox(posX, 64, posZ, posX + 8, 68, posZ + 8);
		}
		
		@Override
		public void buildComponent(ControlComponent original, List components, Random rand) {
			getNextComponentNormal(original, components, rand, 3, 1);
			getNextComponentNX(original, components, rand, 3, 1);
			getNextComponentPX(original, components, rand, 3, 1);
		}
		
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			return true;
		}
	}
	
	public static class Corridor extends ProceduralComponent {
		
		boolean expandsNX = false;
		boolean expandsPX = false;
		boolean extendsPZ = true;
		
		public Corridor() { }
		
		public Corridor(int componentType, Random rand, StructureBoundingBox box, int coordBaseMode) {
			super(componentType);
			this.coordBaseMode = coordBaseMode;
			this.boundingBox = box;
			
		}
		
		protected void func_143012_a(NBTTagCompound data) {
			super.func_143012_a(data);
			data.setBoolean("expandsNX", expandsNX);
			data.setBoolean("expandsPX", expandsPX);
			data.setBoolean("extendsPZ", extendsPZ);
		}
		
		protected void func_143011_b(NBTTagCompound data) {
			super.func_143011_b(data);
			expandsNX = data.getBoolean("expandsNX");
			expandsPX = data.getBoolean("expandsPX");
			extendsPZ = data.getBoolean("extendsPZ");
		}
		
		@Override
		public void buildComponent(ControlComponent original, List components, Random rand) {
			StructureComponent component = getNextComponentNormal(original, components, rand, 1, 1);
			extendsPZ = component != null;
			
			if(rand.nextInt(3) == 0) {
				StructureComponent componentN = getNextComponentNX(original, components, rand, 6, 1);
				expandsNX = componentN != null;
			}
			
			if(rand.nextInt(3) == 0) {
				StructureComponent componentP = getNextComponentPX(original, components, rand, 6, 1);
				expandsPX = componentP != null;
			}
		}
		
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			if(isLiquidInStructureBoundingBox(world, boundingBox)) {
				return false;
			} else {
				int end = extendsPZ ? 14 : 13;
				
				fillWithAir(world, box, 1, 1, 0, 3, 3, end);				
				fillWithBlocks(world, box, 1, 0, 0, 3, 0, end, ModBlocks.deco_titanium);
				
				//Walls
				for(int x = 0; x <= 4; x += 4) {
					fillWithBlocks(world, box, x, 1, 0, x, 1, 4, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, x, 1, 10, x, 1, end, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, x, 2, 0, x, 2, 4, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, x, 2, 10, x, 2, end, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, x, 3, 10, x, 3, end, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, x, 3, 0, x, 3, 4, ModBlocks.reinforced_brick);
				}
				
				if(!extendsPZ) {
					fillWithBlocks(world, box, 1, 1, 14, 3, 1, 14, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 1, 2, 14, 3, 2, 14, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 1, 3, 14, 3, 3, 14, ModBlocks.reinforced_brick);
				}
				
				//ExpandsNX
				if(expandsNX) {
					fillWithBlocks(world, box, 0, 0, 6, 0, 0, 8, ModBlocks.deco_titanium); //Floor
					fillWithBlocks(world, box, 0, 1, 5, 0, 3, 5, ModBlocks.concrete_pillar); //Walls
					fillWithBlocks(world, box, 0, 1, 9, 0, 3, 9, ModBlocks.concrete_pillar);
					fillWithAir(world, box, 0, 1, 6, 0, 3, 8);
					fillWithBlocks(world, box, 0, 4, 6, 0, 4, 8, ModBlocks.reinforced_brick); //Ceiling
				} else {
					fillWithBlocks(world, box, 0, 1, 5, 0, 1, 9, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 0, 2, 5, 0, 2, 9, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 0, 3, 5, 0, 3, 9, ModBlocks.reinforced_brick);
				}
				
				//ExpandsPX
				if(expandsPX) {
					fillWithBlocks(world, box, 4, 0, 6, 4, 0, 8, ModBlocks.deco_titanium);
					fillWithBlocks(world, box, 4, 1, 5, 4, 3, 5, ModBlocks.concrete_pillar);
					fillWithBlocks(world, box, 4, 1, 9, 4, 3, 9, ModBlocks.concrete_pillar);
					fillWithAir(world, box, 4, 1, 6, 4, 3, 8);
					fillWithBlocks(world, box, 4, 4, 6, 4, 4, 8, ModBlocks.reinforced_brick);
				} else {
					fillWithBlocks(world, box, 4, 1, 5, 4, 1, 9, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 4, 2, 5, 4, 2, 9, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 4, 3, 5, 4, 3, 9, ModBlocks.reinforced_brick);
				}
				
				//Ceiling
				fillWithBlocks(world, box, 1, 4, 0, 1, 4, end, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 3, 4, 0, 3, 4, end, ModBlocks.reinforced_brick);
				int pillarMeta = getPillarMeta(8);
				for(int i = 0; i <= 12; i += 3) {
					placeBlockAtCurrentPosition(world, ModBlocks.concrete_pillar, pillarMeta, 2, 4, i, box);
					
					if(rand.nextInt(3) == 0) {
						placeBlockAtCurrentPosition(world, ModBlocks.reinforced_lamp_on, 0, 2, 4, i + 1, box);
						placeBlockAtCurrentPosition(world, Blocks.redstone_block, 0, 2, 5, i + 1, box);
					} else
						placeBlockAtCurrentPosition(world, ModBlocks.reinforced_lamp_off, 0, 2, 4, i + 1, box);
					
					placeBlockAtCurrentPosition(world, ModBlocks.concrete_pillar, pillarMeta, 2, 4, i + 2, box);
				}
				
				return true;
			}
		}
		
	}
	
	interface Wide { } //now you may ask yourself - where is that beautiful house? you may ask yourself - where does that highway go to?
	//you may ask yourself - am i right, am i wrong? you may say to yourself - my god, no multiple inheritance to be done!
	
	public static class WideCorridor extends Corridor implements Wide {
		
		boolean bulkheadNZ = true;
		boolean bulkheadPZ = true;
		
		public WideCorridor() { }
		
		public WideCorridor(int componentType, Random rand, StructureBoundingBox box, int coordBaseMode) {
			super(componentType, rand, box, coordBaseMode);
		}
		
		@Override
		public void buildComponent(ControlComponent original, List components, Random rand) {
			StructureComponent component = getNextComponentNormal(original, components, rand, 3, 1);
			extendsPZ = component != null;
			
			if(component instanceof Wide) {
				bulkheadPZ = false;
				
				if(component instanceof WideCorridor) {
					WideCorridor corridor = (WideCorridor) component;
					corridor.bulkheadNZ = rand.nextInt(4) == 0;
				}
			}
			
			if(rand.nextInt(3) == 0) {
				StructureComponent componentN = getNextComponentNX(original, components, rand, 7, 1);
				expandsNX = componentN != null;
			}
			
			if(rand.nextInt(3) == 0) {
				StructureComponent componentP = getNextComponentPX(original, components, rand, 7, 1);
				expandsPX = componentP != null;
			}
		}
		
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			if(isLiquidInStructureBoundingBox(world, boundingBox)) {
				return false;
			} else {
				int begin = bulkheadNZ ? 1 : 0;
				int end =  bulkheadPZ ? 15 : 16; //for the bulkhead
				int endExtend = !extendsPZ ? 15 : 16; //for parts that would be cut off if it doesn't extend further
				
				fillWithAir(world, box, 1, 1, begin, 7, 3, end);
				
				//Floor
				fillWithBlocks(world, box, 1, 0, begin, 1, 0, end, ModBlocks.deco_titanium);
				fillWithBlocks(world, box, 2, 0, begin, 2, 0, end, ModBlocks.tile_lab);
				fillWithBlocks(world, box, 3, 0, 0, 5, 0, endExtend, ModBlocks.deco_titanium);
				fillWithBlocks(world, box, 6, 0, begin, 6, 0, end, ModBlocks.tile_lab);
				fillWithBlocks(world, box, 7, 0, begin, 7, 0, end, ModBlocks.deco_titanium);
				
				int pillarMeta = getPillarMeta(8);
				//Walls
				if(expandsNX) {
					fillWithBlocks(world, box, 0, 1, begin, 0, 1, 5, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 0, 2, begin, 0, 2, 5, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 0, 3, begin, 0, 3, 5, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 0, 1, 11, 0, 1, end, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 0, 2, 11, 0, 2, end, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 0, 3, 11, 0, 3, end, ModBlocks.reinforced_brick);
					
					fillWithBlocks(world, box, 0, 0, 7, 0, 0, 9, ModBlocks.deco_titanium);
					fillWithBlocks(world, box, 0, 1, 6, 0, 3, 6, ModBlocks.concrete_pillar);
					fillWithBlocks(world, box, 0, 1, 10, 0, 3, 10, ModBlocks.concrete_pillar);
					fillWithMetadataBlocks(world, box, 0, 4, 7, 0, 4, 9, ModBlocks.concrete_pillar, pillarMeta);
					fillWithAir(world, box, 0, 1, 7, 0, 3, 9);
					
				} else {
					fillWithBlocks(world, box, 0, 1, begin, 0, 1, end, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 0, 2, begin, 0, 2, end, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 0, 3, begin, 0, 3, end, ModBlocks.reinforced_brick);
				}
				
				if(expandsPX) {
					fillWithBlocks(world, box, 8, 1, begin, 8, 1, 5, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 8, 2, begin, 8, 2, 5, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 8, 3, begin, 8, 3, 5, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 8, 1, 11, 8, 1, end, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 8, 2, 11, 8, 2, end, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 8, 3, 11, 8, 3, end, ModBlocks.reinforced_brick);
					
					fillWithBlocks(world, box, 8, 0, 7, 8, 0, 9, ModBlocks.deco_titanium);
					fillWithBlocks(world, box, 8, 1, 6, 8, 3, 6, ModBlocks.concrete_pillar);
					fillWithBlocks(world, box, 8, 1, 10, 8, 3, 10, ModBlocks.concrete_pillar);
					fillWithMetadataBlocks(world, box, 8, 4, 7, 8, 4, 9, ModBlocks.concrete_pillar, pillarMeta);
					fillWithAir(world, box, 8, 1, 7, 8, 3, 9);
					
				} else {
					fillWithBlocks(world, box, 8, 1, begin, 8, 1, end, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 8, 2, begin, 8, 2, end, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 8, 3, begin, 8, 3, end, ModBlocks.reinforced_brick);
				}
				
				if(bulkheadNZ) {
					fillWithBlocks(world, box, 1, 1, 0, 2, 1, 0, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 1, 2, 0, 2, 2, 0, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 1, 3, 0, 2, 3, 0, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 6, 1, 0, 7, 1, 0, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 6, 2, 0, 7, 2, 0, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 6, 3, 0, 7, 3, 0, ModBlocks.reinforced_brick);
					fillWithAir(world, box, 3, 1, 0, 5, 3, 0);
				}
				
				if(bulkheadPZ) {
					fillWithBlocks(world, box, 1, 1, 16, 2, 1, 16, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 1, 2, 16, 2, 2, 16, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 1, 3, 16, 2, 3, 16, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 6, 1, 16, 7, 1, 16, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 6, 2, 16, 7, 2, 16, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 6, 3, 16, 7, 3, 16, ModBlocks.reinforced_brick);
					
					if(!extendsPZ) {
						fillWithBlocks(world, box, 3, 1, 16, 5, 1, 16, ModBlocks.reinforced_brick);
						fillWithBlocks(world, box, 3, 2, 16, 5, 2, 16, ModBlocks.reinforced_stone);
						fillWithBlocks(world, box, 3, 3, 16, 5, 3, 16, ModBlocks.reinforced_brick);
					} else
						fillWithAir(world, box, 3, 1, 16, 5, 3, 16);
				}
				
				//Ceiling
				fillWithBlocks(world, box, 1, 4, begin, 1, 4, end, ModBlocks.reinforced_brick);
				fillWithMetadataBlocks(world, box, 2, 4, begin, 2, 4, end, ModBlocks.concrete_pillar, pillarMeta);
				fillWithBlocks(world, box, 3, 4, 0, 3, 4, endExtend, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 5, 4, 0, 5, 4, endExtend, ModBlocks.reinforced_brick);
				fillWithMetadataBlocks(world, box, 6, 4, begin, 6, 4, end, ModBlocks.concrete_pillar, pillarMeta);
				fillWithBlocks(world, box, 7, 4, begin, 7, 4, end, ModBlocks.reinforced_brick);
				
				for(int i = 0; i <= 12; i += 3) {
					fillWithMetadataBlocks(world, box, 4, 4, i, 4, 4, i + 1, ModBlocks.concrete_pillar, pillarMeta);
					
					if(rand.nextInt(3) == 0) {
						placeBlockAtCurrentPosition(world, ModBlocks.reinforced_lamp_on, 0, 4, 4, i + 2, box);
						placeBlockAtCurrentPosition(world, Blocks.redstone_block, 0, 4, 5, i + 2, box);
					} else
						placeBlockAtCurrentPosition(world, ModBlocks.reinforced_lamp_off, 0, 4, 4, i + 2, box);
				}
				
				fillWithMetadataBlocks(world, box, 4, 4, 15, 4, 4, endExtend, ModBlocks.concrete_pillar, pillarMeta);
				
				return true;
			}
		}
	}
	
	public static class Turn extends ProceduralComponent {
		
		public Turn() { }
		
		public Turn(int componentType, Random rand, StructureBoundingBox box, int coordBaseMode) {
			super(componentType);
			this.coordBaseMode = coordBaseMode;
			this.boundingBox = box;
			
		}
		
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			return true;
		}
	}
	
	public static class WideTurn extends Turn implements Wide {
		
		public WideTurn() { }
		
		public WideTurn(int componentType, Random rand, StructureBoundingBox box, int coordBaseMode) {
			super(componentType, rand, box, coordBaseMode);
		}
		
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			return true;
		}
	}
	
	public static class Intersection extends ProceduralComponent {
		
		boolean opensNX = false;
		boolean opensPX = false;
		boolean opensPZ = false;
		
		public Intersection() { }
		
		public Intersection(int componentType, Random rand, StructureBoundingBox box, int coordBaseMode) {
			super(componentType);
			this.coordBaseMode = coordBaseMode;
			this.boundingBox = box;
			
		}
		
		@Override
		public void buildComponent(ControlComponent original, List components, Random rand) {
			if(rand.nextInt(3) != 0) {
				StructureComponent component = getNextComponentNormal(original, components, rand, 1, 1);
				opensPZ = component != null;
			}
			
			StructureComponent componentN = getNextComponentNX(original, components, rand, 1, 1);
			opensNX = componentN != null;
			
			StructureComponent componentP = getNextComponentPX(original, components, rand, 1, 1);
			opensPX = componentP != null;
		}
		
		protected void func_143012_a(NBTTagCompound data) {
			super.func_143012_a(data);
			data.setBoolean("opensNX", opensNX);
			data.setBoolean("opensPX", opensPX);
			data.setBoolean("opensPZ", opensPZ);
		}
		
		protected void func_143011_b(NBTTagCompound data) {
			super.func_143011_b(data);
			opensNX = data.getBoolean("opensNX");
			opensPX = data.getBoolean("opensPX");
			opensPZ = data.getBoolean("opensPZ");
		}
		
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			if(isLiquidInStructureBoundingBox(world, boundingBox)) {
				return false;
			} else {
				
				fillWithAir(world, box, 1, 1, 0, 3, 3, 3);
				//Floor
				fillWithBlocks(world, box, 1, 0, 0, 3, 0, 3, ModBlocks.deco_titanium);
				//Ceiling
				int pillarMetaWE = getPillarMeta(4);
				int pillarMetaNS = getPillarMeta(8);
				
				fillWithBlocks(world, box, 3, 4, 0, 3, 4, 1, ModBlocks.reinforced_brick);
				fillWithMetadataBlocks(world, box, 2, 4, 0, 2, 4, 1, ModBlocks.concrete_pillar, pillarMetaNS);
				fillWithBlocks(world, box, 1, 4, 0, 1, 4, 1, ModBlocks.reinforced_brick);
				
				if(rand.nextInt(3) == 0) {
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_lamp_on, 0, 2, 4, 2, box);
					placeBlockAtCurrentPosition(world, Blocks.redstone_block, 0, 2, 5, 2, box);
				} else
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_lamp_off, 0, 2, 4, 2, box);
				
				if(opensPZ) {
					fillWithBlocks(world, box, 1, 0, 4, 3, 0, 4, ModBlocks.deco_titanium); //Floor
					fillWithBlocks(world, box, 1, 4, 3, 1, 4, 4, ModBlocks.reinforced_brick); //Ceiling
					fillWithMetadataBlocks(world, box, 2, 4, 3, 2, 4, 4, ModBlocks.concrete_pillar, pillarMetaNS);
					fillWithBlocks(world, box, 3, 4, 3, 3, 4, 4, ModBlocks.reinforced_brick);
					fillWithAir(world, box, 1, 1, 4, 3, 3, 4); //Opening
				} else {
					fillWithBlocks(world, box, 1, 4, 3, 3, 4, 3, ModBlocks.reinforced_brick); //Ceiling
					fillWithBlocks(world, box, 1, 1, 4, 3, 1, 4, ModBlocks.reinforced_brick); //Wall
					fillWithBlocks(world, box, 1, 2, 4, 3, 2, 4, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 1, 3, 4, 3, 3, 4, ModBlocks.reinforced_brick);
				}
				
				if(opensNX) {
					fillWithBlocks(world, box, 0, 0, 1, 0, 0, 3, ModBlocks.deco_titanium); //Floor
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 0, 4, 1, box); //Ceiling
					fillWithMetadataBlocks(world, box, 0, 4, 2, 1, 4, 2, ModBlocks.concrete_pillar, pillarMetaWE);
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 0, 4, 3, box);
					fillWithAir(world, box, 0, 1, 1, 0, 3, 3); //Opening
				} else {
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 1, 4, 2, box); //Ceiling
					fillWithBlocks(world, box, 0, 1, 1, 0, 1, 3, ModBlocks.reinforced_brick); //Wall
					fillWithBlocks(world, box, 0, 2, 1, 0, 2, 3, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 0, 3, 1, 0, 3, 3, ModBlocks.reinforced_brick);
				}
				
				if(opensPX) {
					fillWithBlocks(world, box, 4, 0, 1, 4, 0, 3, ModBlocks.deco_titanium); //Floor
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 4, 4, 1, box); //Ceiling
					fillWithMetadataBlocks(world, box, 3, 4, 2, 4, 4, 2, ModBlocks.concrete_pillar, pillarMetaWE);
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 4, 4, 3, box);
					fillWithAir(world, box, 4, 1, 1, 4, 3, 3); //Opening
				} else {
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 3, 4, 2, box);
					fillWithBlocks(world, box, 4, 1, 1, 4, 1, 3, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 4, 2, 1, 4, 2, 3, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 4, 3, 1, 4, 3, 3, ModBlocks.reinforced_brick);
				}
				
				//Pillars
				if(opensNX)
					fillWithBlocks(world, box, 0, 1, 0, 0, 3, 0, ModBlocks.concrete_pillar);
				else {
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 0, 1, 0, box);
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_stone, 0, 0, 2, 0, box);
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 0, 3, 0, box);
				}
				
				if(opensPX)
					fillWithBlocks(world, box, 4, 1, 0, 4, 3, 0, ModBlocks.concrete_pillar);
				else {
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 4, 1, 0, box);
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_stone, 0, 4, 2, 0, box);
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 4, 3, 0, box);
				}
				
				if(opensNX && opensPZ)
					fillWithBlocks(world, box, 0, 1, 4, 0, 3, 4, ModBlocks.concrete_pillar);
				else if(opensNX || opensPZ) {
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 0, 1, 4, box);
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_stone, 0, 0, 2, 4, box);
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 0, 3, 4, box);
				}
				
				if(opensPX && opensPZ)
					fillWithBlocks(world, box, 4, 1, 4, 4, 3, 4, ModBlocks.concrete_pillar);
				else if(opensPX || opensPZ) {
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 4, 1, 4, box);
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_stone, 0, 4, 2, 4, box);
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 4, 3, 4, box);
				}
				
				return true;
			}
		}
	}
	
	public static class WideIntersection extends Intersection implements Wide {
		
		public WideIntersection() { }
		
		public WideIntersection(int componentType, Random rand, StructureBoundingBox box, int coordBaseMode) {
			super(componentType, rand, box, coordBaseMode);
		}
		
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			return true;
		}
	}
	
	
}
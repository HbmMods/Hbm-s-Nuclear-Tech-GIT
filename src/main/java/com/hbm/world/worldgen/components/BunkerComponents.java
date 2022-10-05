package com.hbm.world.worldgen.components;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class BunkerComponents {
	
	private static final Weight[] weightArray = new Weight[0];
	
	private static List componentWeightList;
	static int totalWeight;
	
	public static void prepareComponents() {
		componentWeightList = new ArrayList();
		
		for(int i = 0; i < weightArray.length; i++) {
			weightArray[i].instancesSpawned = 0;
			componentWeightList.add(weightArray[i]);
		}
	}
	
	private static Bunker getWeightedComponent(StructureComponent original, List components, Random rand, int minX, int minY, int minZ, int coordMode, int componentType) {
		
		for(int i = 0; i < 5; i++) {
			int value = rand.nextInt(totalWeight);
			Iterator iterator = componentWeightList.iterator();
			
			while(iterator.hasNext()) {
				Weight weight = (Weight)iterator.next();
				value -= weight.weight;
				
				if(value < 0) {
					if(!weight.canSpawnStructure(componentType))
						break;
					
					Bunker component = weight.lambda.findValidPlacement(components, rand, minX, minY, minZ, coordMode, componentType);
					
					if(component != null) {
						weight.instancesSpawned++;
						
						if(!weight.canSpawnMoreStructures())
							componentWeightList.remove(weight);
							
						return component;
					}
					
				}
			}
		}
		
		return null;
	}
	
	private static StructureComponent getNextValidComponent(StructureComponent original, List components, Random rand, int minX, int minY, int minZ, int coordMode, int componentType) {
		
		if(components.size() > 50)
			return null;
		
		if(Math.abs(minX - original.getBoundingBox().minX) <= 112 && Math.abs(minZ - original.getBoundingBox().minX) <= 112) {
			
			StructureComponent structure = getWeightedComponent(original, components, rand, minX, minY, minZ, coordMode, componentType);
			
			if(structure != null) {
				components.add(structure); //Adds component to structure start list
				structure.buildComponent(original, components, rand); //either a) add it to a list in the original to be built or b) do it here. obviously the latter.
			}
			
			return structure;
		}
		
		return null;
	}
	
	static class Weight {
		
		public final instantiateStructure lambda; //i refuse to use some 🤓 ass method for getting the class from each PieceWeight
		//here, we overengineer shit like real developers
		
		public final int weight;
		public int instancesSpawned;
		public int instanceLimit;
		
		public Weight(instantiateStructure lambda, int weight, int limit) {
			this.lambda = lambda;
			this.weight = weight;
			this.instanceLimit = limit;
		}
		
		//Checks if another structure can be spawned based on input data
		public boolean canSpawnStructure(int componentAmount) {
			return this.instanceLimit == 0 || this.instanceLimit < this.instanceLimit;
		}
		
		//Checks if another structure can be spawned at all (used to flag for removal from the list)
		public boolean canSpawnMoreStructures() {
			return this.instanceLimit == 0 || this.instancesSpawned < this.instanceLimit;
		}
		
	}
	
	/** Returns a new instance of this structureComponent, or null if not able to be placed. */
	@FunctionalInterface
	interface instantiateStructure {
		Bunker findValidPlacement(List components, Random rand, int minX, int minY, int minZ, int coordMode, int componentType);
	}
	
	public abstract static class Bunker extends Feature {
		
		public Bunker() { }
		
		public Bunker(int componentType) {
			super(componentType); //important to carry over, as it allows for hard limits on the amount of components. increment once for each new component.
		}
		
		/** Gets next component in the direction this component is facing.<br>'original' refers to the initial starting component (hard distance limits), 'components' refers to the StructureStart list. */
		protected StructureComponent getNextComponentNormal(StructureComponent original, List components, Random rand, int offset, int offsetY) {
			switch(this.coordBaseMode) {
			case 0: //South
				return getNextValidComponent(original, components, rand, this.boundingBox.minX + offset, this.boundingBox.minY + offsetY, this.boundingBox.maxZ + 1, this.coordBaseMode, this.getComponentType());
			case 1: //West
				return getNextValidComponent(original, components, rand, this.boundingBox.minX - 1, this.boundingBox.minY + offsetY, this.boundingBox.minZ + offset, this.coordBaseMode, this.getComponentType());
			case 2: //North
				return getNextValidComponent(original, components, rand, this.boundingBox.maxX - offset, this.boundingBox.minY + offsetY, this.boundingBox.minZ - 1, this.coordBaseMode, this.getComponentType());
			case 3: //East
				return getNextValidComponent(original, components, rand, this.boundingBox.maxX + 1, this.boundingBox.minY + offsetY, this.boundingBox.maxZ - offset, this.coordBaseMode, this.getComponentType());
			default:
				return null;
			}
		}
		
		//Keep in mind for these methods: a given room would have its *actual entrance* opposite the side it is facing.
		/** Gets next component, to the West (-X) <i>relative to this component. */
		protected StructureComponent getNextComponentNX(StructureComponent original, List components, Random rand, int offset, int offsetY) {
			switch(this.coordBaseMode) {
			case 0: //South
				return getNextValidComponent(original, components, rand, this.boundingBox.minX - 1, this.boundingBox.minY + offsetY, this.boundingBox.minZ + offset, 1, this.getComponentType());
			case 1: //West
				return getNextValidComponent(original, components, rand, this.boundingBox.minX + offset, this.boundingBox.minY + offsetY, this.boundingBox.minZ - 1, 2, this.getComponentType());
			case 2: //North
				return getNextValidComponent(original, components, rand, this.boundingBox.maxX + 1, this.boundingBox.minY + offsetY, this.boundingBox.maxZ - offset, 3, this.getComponentType());
			case 3: //East
				return getNextValidComponent(original, components, rand, this.boundingBox.maxX - offset, this.boundingBox.minY + offsetY, this.boundingBox.maxZ + 1, 0, this.getComponentType());
			default:
				return null;
			}
		}
		
		/** Gets next component, to the East (+X) <i>relative to this component. */
		protected StructureComponent getNextComponentPX(StructureComponent original, List components, Random rand, int offset, int offsetY) {
			switch(this.coordBaseMode) {
			case 0: //South
				return getNextValidComponent(original, components, rand, this.boundingBox.maxX + 1, this.boundingBox.minY + offsetY, this.boundingBox.maxZ - offset, 1, this.getComponentType());
			case 1: //West
				return getNextValidComponent(original, components, rand, this.boundingBox.minZ + offset, this.boundingBox.minY + offsetY, this.boundingBox.maxZ + 1, 2, this.getComponentType());
			case 2: //North
				return getNextValidComponent(original, components, rand, this.boundingBox.minX - 1, this.boundingBox.minY + offsetY, this.boundingBox.minZ + offset, 3, this.getComponentType());
			case 3: //East
				return getNextValidComponent(original, components, rand, this.boundingBox.maxX - offset, this.boundingBox.minY + offsetY, this.boundingBox.minZ - 1, 0, this.getComponentType());
			default:
				return null;
			}
		}
		
		protected static StructureBoundingBox getComponentToAddBoundingBox(int posX, int posY, int posZ, int offsetX, int offsetY, int offsetZ, int maxX, int maxY, int maxZ, int coordMode) {
			switch(coordMode) {
			case 0:
				return new StructureBoundingBox(posX + offsetX, posY + offsetY, posZ + offsetZ, posX + maxX - 1 + offsetX, posY + maxY - 1 + offsetY, posZ + maxZ - 1 + offsetZ);
			case 1:
				return new StructureBoundingBox(posX - maxZ + 1 - offsetZ, posY + offsetY, posZ + offsetX, posX - offsetZ, posY + maxY - 1 + offsetY, posZ + maxX - 1 + offsetX);
			case 2:
				return new StructureBoundingBox(posX - maxX + 1 - offsetX, posY + offsetY, posZ - maxZ + 1 - offsetZ, posX - offsetX, posY + maxY - 1 + offsetY, posZ - offsetZ);
			case 3:
				return new StructureBoundingBox(posX + offsetZ, posY + offsetY, posZ - maxX + 1 - offsetX, posX + maxZ - 1 + offsetZ, posY + maxY - 1 + offsetY, posZ - offsetX);
			default:
				return new StructureBoundingBox(posX + offsetX, posY + offsetY, posZ + offsetZ, posX + maxX - 1 + offsetX, posY + maxY - 1 + offsetY, posZ + maxZ - 1 + offsetZ);
			}
		}
	}
	
	public static class Atrium extends Bunker {
		
		public Atrium() { }
		
		public Atrium(int componentType, Random rand, StructureBoundingBox box, int coordBaseMode) {
			super(componentType);
			this.coordBaseMode = coordBaseMode;
			this.boundingBox = box;
		}
		
		@Override
		public void buildComponent(StructureComponent original, List components, Random rand) {
			
		}
		
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			return true;
		}
	}
	
	public static class Corridor extends Bunker {
		
		boolean expandsNX = false;
		boolean expandsPX = false;
		
		public Corridor() { }
		
		public Corridor(int componentType, Random rand, StructureBoundingBox box, int coordBaseMode) {
			super(componentType);
			this.coordBaseMode = coordBaseMode;
			this.boundingBox = box;
			expandsNX = rand.nextInt(3) == 0;
			expandsPX = rand.nextInt(3) == 0;
			
		}
		
		protected void func_143012_a(NBTTagCompound data) {
			super.func_143012_a(data);
			data.setBoolean("expandsNX", expandsNX);
			data.setBoolean("expandsPX", expandsPX);
		}
		
		protected void func_143011_b(NBTTagCompound data) {
			super.func_143011_b(data);
			expandsNX = data.getBoolean("expandsNX");
			expandsPX = data.getBoolean("expandsPX");
		}
		
		@Override
		public void buildComponent(StructureComponent original, List components, Random rand) {
			getNextComponentNormal(original, components, rand, 1, 1);
			
			if(expandsNX)
				getNextComponentNX(original, components, rand, 6, 1);
			
			if(expandsPX)
				getNextComponentPX(original, components, rand, 6, 1);
		}
		
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			if(isLiquidInStructureBoundingBox(world, box)) {
				return false;
			} else {
				fillWithAir(world, box, 1, 1, 0, 3, 3, 14);				
				fillWithBlocks(world, box, 1, 0, 0, 3, 0, 14, ModBlocks.deco_titanium);
				
				//Walls
				for(int x = 0; x <= 4; x += 4) {
					fillWithBlocks(world, box, x, 1, 0, x, 1, 4, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, x, 1, 10, x, 1, 14, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, x, 2, 0, x, 2, 4, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, x, 2, 10, x, 2, 14, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, x, 3, 10, x, 3, 14, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, x, 3, 0, x, 3, 4, ModBlocks.reinforced_brick);
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
				fillWithBlocks(world, box, 1, 4, 0, 1, 4, 14, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 3, 4, 0, 3, 4, 14, ModBlocks.reinforced_brick);
				int pillarMeta = getPillarMeta(8);
				for(int i = 0; i <= 12; i += 3) {
					placeBlockAtCurrentPosition(world, ModBlocks.concrete_pillar, pillarMeta, 2, 4, i, box);
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_lamp_off, 0, 2, 4, i + 1, box);
					placeBlockAtCurrentPosition(world, ModBlocks.concrete_pillar, pillarMeta, 2, 4, i + 2, box);
				}
				
				return true;
			}
		}
		
	}
	
	public static class WideCorridor extends Corridor {
		
		boolean bulkheadNZ = false;
		boolean bulkheadPZ = true;
		
		public WideCorridor() { }
		
		public WideCorridor(int componentType, Random rand, StructureBoundingBox box, int coordBaseMode) {
			super(componentType, rand, box, coordBaseMode);
		}
		
		@Override
		public void buildComponent(StructureComponent original, List components, Random rand) {
			getNextComponentNormal(original, components, rand, 1, 1);
			
			if(expandsNX)
				getNextComponentNX(original, components, rand, 7, 1);
			
			if(expandsPX)
				getNextComponentPX(original, components, rand, 7, 1);
		}
		
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			if(isLiquidInStructureBoundingBox(world, box)) {
				return false;
			} else {
				int begin = bulkheadNZ ? 1 : 0;
				int end =  bulkheadPZ ? 15 : 16;
				
				fillWithAir(world, box, 1, 1, begin, 7, 3, end);
				
				//Floor
				fillWithBlocks(world, box, 1, 0, begin, 1, 0, end, ModBlocks.deco_titanium);
				fillWithBlocks(world, box, 2, 0, begin, 2, 0, end, ModBlocks.tile_lab);
				fillWithBlocks(world, box, 3, 0, 0, 5, 0, 14, ModBlocks.deco_titanium);
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
					fillWithBlocks(world, box, 0, 1, 0, 1, 1, 0, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 0, 2, 0, 1, 2, 0, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 0, 3, 0, 1, 3, 0, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 5, 1, 0, 6, 1, 0, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 5, 2, 0, 6, 2, 0, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 5, 3, 0, 6, 3, 0, ModBlocks.reinforced_brick);
				}
				
				if(bulkheadPZ) {
					fillWithBlocks(world, box, 0, 1, 16, 1, 1, 16, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 0, 2, 16, 1, 2, 16, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 0, 3, 16, 1, 3, 16, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 5, 1, 16, 6, 1, 16, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 5, 2, 16, 6, 2, 16, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 5, 3, 16, 6, 3, 16, ModBlocks.reinforced_brick);
				}
				
				//Ceiling
				fillWithBlocks(world, box, 1, 4, begin, 1, 4, end, ModBlocks.reinforced_brick);
				fillWithMetadataBlocks(world, box, 2, 4, begin, 2, 4, end, ModBlocks.concrete_pillar, pillarMeta);
				fillWithBlocks(world, box, 3, 4, 0, 3, 4, 16, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 5, 4, 0, 5, 4, 16, ModBlocks.reinforced_brick);
				fillWithMetadataBlocks(world, box, 6, 4, begin, 6, 4, end, ModBlocks.concrete_pillar, pillarMeta);
				fillWithBlocks(world, box, 7, 4, begin, 7, 4, end, ModBlocks.reinforced_brick);
				
				for(int i = 0; i <= 12; i += 3) {
					fillWithMetadataBlocks(world, box, 4, 4, i, 4, 4, i + 1, ModBlocks.concrete_pillar, pillarMeta);
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_lamp_off, 0, 4, 4, i + 2, box);
				}
				
				fillWithMetadataBlocks(world, box, 4, 4, 15, 4, 4, 16, ModBlocks.concrete_pillar, pillarMeta);
				
				return true;
			}
		}
	}
	
	public static class Turn extends Bunker {
		
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
	
	public static class WideTurn extends Turn {
		
		public WideTurn() { }
		
		public WideTurn(int componentType, Random rand, StructureBoundingBox box, int coordBaseMode) {
			super(componentType, rand, box, coordBaseMode);
		}
		
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			return true;
		}
	}
	
	public static class Intersection extends Bunker {
		
		public Intersection() { }
		
		public Intersection(int componentType, Random rand, StructureBoundingBox box, int coordBaseMode) {
			super(componentType);
			this.coordBaseMode = coordBaseMode;
			this.boundingBox = box;
			
		}
		
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			return true;
		}
	}
	
	public static class WideIntersection extends Intersection {
		
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
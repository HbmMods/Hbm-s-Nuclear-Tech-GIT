package com.hbm.world.worldgen.components;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

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
		
		
		
		public Corridor() { }
		
		public Corridor(int componentType, Random rand, StructureBoundingBox box, int coordBaseMode) {
			super(componentType);
			this.coordBaseMode = coordBaseMode;
			this.boundingBox = box;
			
		}
		
		protected void func_143012_a(NBTTagCompound data) {
			super.func_143012_a(data);
			
		}
		
		protected void func_143011_b(NBTTagCompound data) {
			super.func_143011_b(data);
			
		}
		
		@Override
		public void buildComponent(StructureComponent original, List components, Random rand) {
			
		}
		
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			if(isLiquidInStructureBoundingBox(world, box)) {
				return false;
			} else {
				
				
				return true;
			}
		}
		
	}
	
	public static class WideCorridor extends Corridor {
		
		public WideCorridor() { }
		
		public WideCorridor(int componentType, Random rand, StructureBoundingBox box, int coordBaseMode) {
			super(componentType, rand, box, coordBaseMode);
		}
		
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			return true;
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
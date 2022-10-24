package com.hbm.world.worldgen.components;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.hbm.world.worldgen.components.ProceduralComponents.ControlComponent;

import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public abstract class ProceduralComponents {
	
	protected List componentWeightList;
	
	protected static Weight[] weightArray = new Weight[] { };
	
	public void prepareComponents() {
		componentWeightList = new ArrayList();
		
		for(int i = 0; i < weightArray.length; i++) {
			weightArray[i].instancesSpawned = 0;
			componentWeightList.add(weightArray[i]);
		}
	}
	
	protected int getTotalWeight() {
		boolean flag = false;
		int totalWeight = 0;
		Weight weight;
		
		for(Iterator iterator = componentWeightList.iterator(); iterator.hasNext(); totalWeight += weight.weight) { //Iterates over the entire list to find the total weight
			weight = (Weight) iterator.next();
			
			if(weight.instanceLimit >= 0 && weight.instancesSpawned < weight.instanceLimit) //can more structure pieces be added, in general?
				flag = true;
		}
		
		return flag ? totalWeight : -1;
	}
	
	protected ProceduralComponent getWeightedComponent(ControlComponent original, List components, Random rand, int minX, int minY, int minZ, int coordMode, int componentType) {
		int totalWeight = getTotalWeight();
		
		if(totalWeight < 0)
			return null;
		
		for(int i = 0; i < 5; i++) {
			int value = rand.nextInt(totalWeight); //Pick a random value, based on how many parts there are already
			Iterator iterator = componentWeightList.iterator();
			
			while(iterator.hasNext()) {
				Weight weight = (Weight)iterator.next();
				value -= weight.weight; //Iterate over the list until the value is less than 0
				
				if(value < 0) {
					if(!weight.canSpawnStructure(componentType, coordMode, original.lastComponent)) //Additional checks based on game state info preventing spawn? start from beginning
						break;
					
					ProceduralComponent component = (ProceduralComponent) weight.lambda.findValidPlacement(components, rand, minX, minY, minZ, coordMode, componentType); //Construct the chosen component
					
					if(component != null) { //If it has been constructed, add it
						weight.instancesSpawned++;
						
						if(!weight.canSpawnMoreStructures()) //Structure can no longer be spawned regardless of game state? remove as an option
							componentWeightList.remove(weight);
							
						return component;
					}
					
				}
			}
		}
		
		return null;
	}
	
	protected int sizeLimit = 50;
	protected int distanceLimit = 64;
	
	protected ProceduralComponent getNextValidComponent(ControlComponent original, List components, Random rand, int minX, int minY, int minZ, int coordMode, int componentType) {
		
		if(components.size() > sizeLimit) //Hard limit on amount of components
			return null;
		
		if(Math.abs(minX - original.getBoundingBox().minX) <= distanceLimit && Math.abs(minZ - original.getBoundingBox().minZ) <= distanceLimit) { //Hard limit on spread of structure
			
			ProceduralComponent structure = getWeightedComponent(original, components, rand, minX, minY, minZ, coordMode, componentType + 1); //Returns null if all checks fail
			
			if(structure != null) {
				components.add(structure); //Adds component to structure start list
				original.queuedComponents.add(structure); //Add it to the list of queued components waiting to be built
			}
			
			return structure;
		}
		
		return null;
	}
	
	public static StructureBoundingBox getComponentToAddBoundingBox(int posX, int posY, int posZ, int offsetX, int offsetY, int offsetZ, int maxX, int maxY, int maxZ, int coordMode) {
		switch(coordMode) {
		case 0: //South
			return new StructureBoundingBox(posX + offsetX, posY + offsetY, posZ + offsetZ, posX + maxX - 1 + offsetX, posY + maxY - 1 + offsetY, posZ + maxZ - 1 + offsetZ);
		case 1: //West
			return new StructureBoundingBox(posX - maxZ + 1 - offsetZ, posY + offsetY, posZ + offsetX, posX - offsetZ, posY + maxY - 1 + offsetY, posZ + maxX - 1 + offsetX);
		case 2: //North
			return new StructureBoundingBox(posX - maxX + 1 - offsetX, posY + offsetY, posZ - maxZ + 1 - offsetZ, posX - offsetX, posY + maxY - 1 + offsetY, posZ + offsetZ);
		case 3: //East
			return new StructureBoundingBox(posX + offsetZ, posY + offsetY, posZ - maxX + 1 - offsetX, posX + maxZ - 1 + offsetZ, posY + maxY - 1 + offsetY, posZ - offsetX);
		default:
			return new StructureBoundingBox(posX + offsetX, posY + offsetY, posZ + offsetZ, posX + maxX - 1 + offsetX, posY + maxY - 1 + offsetY, posZ + maxZ - 1 + offsetZ);
		}
	}
	
	/** StructureComponent that supports procedural generation */
	public abstract static class ProceduralComponent extends Component {
		
		public ProceduralComponent() { }
		
		public ProceduralComponent(int componentType) {
			super(componentType); //Important to carry over.
		}
		
		public void buildComponent(ProceduralComponents instance, ControlComponent original, List components, Random rand) { }
		
		/** Gets next component in the direction this component is facing.<br>'original' refers to the initial starting component (hard distance limits), 'components' refers to the StructureStart list. */
		protected ProceduralComponent getNextComponentNormal(ProceduralComponents instance, ControlComponent original, List components, Random rand, int offset, int offsetY) {
			switch(this.coordBaseMode) {
			case 0: //South
				return instance.getNextValidComponent(original, components, rand, this.boundingBox.minX + offset, this.boundingBox.minY + offsetY, this.boundingBox.maxZ + 1, this.coordBaseMode, this.getComponentType());
			case 1: //West
				return instance.getNextValidComponent(original, components, rand, this.boundingBox.minX - 1, this.boundingBox.minY + offsetY, this.boundingBox.minZ + offset, this.coordBaseMode, this.getComponentType());
			case 2: //North
				return instance.getNextValidComponent(original, components, rand, this.boundingBox.maxX - offset, this.boundingBox.minY + offsetY, this.boundingBox.minZ - 1, this.coordBaseMode, this.getComponentType());
			case 3: //East
				return instance.getNextValidComponent(original, components, rand, this.boundingBox.maxX + 1, this.boundingBox.minY + offsetY, this.boundingBox.maxZ - offset, this.coordBaseMode, this.getComponentType());
			default:
				return null;
			}
		}
		
		/** Gets next component in the opposite direction this component is facing. */
		protected ProceduralComponent getNextComponentAntiNormal(ProceduralComponents instance, ControlComponent original, List components, Random rand, int offset, int offsetY) {
			switch(this.coordBaseMode) {
			case 0: //South
				return instance.getNextValidComponent(original, components, rand, this.boundingBox.maxX - offset, this.boundingBox.minY + offsetY, this.boundingBox.minZ - 1, 2, this.getComponentType());
			case 1: //West
				return instance.getNextValidComponent(original, components, rand, this.boundingBox.maxX + 1, this.boundingBox.minY + offsetY, this.boundingBox.maxZ - offset, 3, this.getComponentType());
			case 2: //North
				return instance.getNextValidComponent(original, components, rand, this.boundingBox.minX + offset, this.boundingBox.minY + offsetY, this.boundingBox.maxZ + 1, 0, this.getComponentType());
			case 3: //East
				return instance.getNextValidComponent(original, components, rand, this.boundingBox.minX - 1, this.boundingBox.minY + offsetY, this.boundingBox.minZ + offset, 1, this.getComponentType());
			default:
				return null;
			}
		}
		
		//Keep in mind for these methods: a given room would have its *actual entrance* opposite the side it is facing.
		/** Gets next component, to the West (-X) <i>relative to this component. */
		protected ProceduralComponent getNextComponentNX(ProceduralComponents instance, ControlComponent original, List components, Random rand, int offset, int offsetY) {
			switch(this.coordBaseMode) {
			case 0: //South
				return instance.getNextValidComponent(original, components, rand, this.boundingBox.minX - 1, this.boundingBox.minY + offsetY, this.boundingBox.minZ + offset, 1, this.getComponentType());
			case 1: //West
				return instance.getNextValidComponent(original, components, rand, this.boundingBox.maxX - offset, this.boundingBox.minY + offsetY, this.boundingBox.minZ - 1, 2, this.getComponentType());
			case 2: //North
				return instance.getNextValidComponent(original, components, rand, this.boundingBox.maxX + 1, this.boundingBox.minY + offsetY, this.boundingBox.maxZ - offset, 3, this.getComponentType());
			case 3: //East
				return instance.getNextValidComponent(original, components, rand, this.boundingBox.minX + offset, this.boundingBox.minY + offsetY, this.boundingBox.maxZ + 1, 0, this.getComponentType());
			default:
				return null;
			}
		}
		
		/** Gets next component, to the East (+X) <i>relative to this component. */
		protected ProceduralComponent getNextComponentPX(ProceduralComponents instance, ControlComponent original, List components, Random rand, int offset, int offsetY) {
			switch(this.coordBaseMode) {
			case 0: //South
				return instance.getNextValidComponent(original, components, rand, this.boundingBox.maxX + 1, this.boundingBox.minY + offsetY, this.boundingBox.maxZ - offset, 3, this.getComponentType() + 1);
			case 1: //West
				return instance.getNextValidComponent(original, components, rand, this.boundingBox.minX + offset, this.boundingBox.minY + offsetY, this.boundingBox.maxZ + 1, 0, this.getComponentType() + 1);
			case 2: //North
				return instance.getNextValidComponent(original, components, rand, this.boundingBox.minX - 1, this.boundingBox.minY + offsetY, this.boundingBox.minZ + offset, 1, this.getComponentType() + 1);
			case 3: //East
				return instance.getNextValidComponent(original, components, rand, this.boundingBox.maxX - offset, this.boundingBox.minY + offsetY, this.boundingBox.minZ - 1, 2, this.getComponentType() + 1);
			default:
				return null;
			}
		}
		
		/** Finds valid placement, using input information. Should be passed as a method reference to its respective Weight. */
		//Static so no override (cringe!)
		//public static ProceduralComponent findValidPlacement(List components, Random rand, int minX, int minY, int minZ, int coordMode, int componentType) { return null; }
	}
	
	/** ProceduralComponent that can serve as a master "control component" for procedural generation and building of components. */
	public abstract static class ControlComponent extends ProceduralComponent {
		
		public List queuedComponents = new ArrayList(); //List of all queued ProceduralComponents waiting to be built. Randomly iterated over until limits like component amt or dist are reached.
		public ProceduralComponent lastComponent = this; //Last component to be built. Used as input for the random selection's checks for specific components.
		
		public ControlComponent() { }
		
		public ControlComponent(int componentType) {
			super(componentType);
		}
	}
	
	/** Returns a new instance of this structureComponent, or null if not able to be placed.<br>Based on bounding box checks. */
	@FunctionalInterface
	interface instantiateStructure {
		ProceduralComponent findValidPlacement(List components, Random rand, int minX, int minY, int minZ, int coordMode, int componentType);
	}
	
	protected static class Weight {
		
		public final instantiateStructure lambda; //Read above
		
		public final int weight; //Weight of this component
		public int instancesSpawned; //How many components spawned?
		public int instanceLimit; //Limit on amount of components: -1 for no limit
		
		public Weight(int weight, int limit, instantiateStructure lambda) {
			this.weight = weight;
			this.instanceLimit = limit;
			this.lambda = lambda;
		}
		
		//Checks if another structure can be spawned based on input data
		public boolean canSpawnStructure(int componentAmount, int coordMode, ProceduralComponent component) {
			return this.instanceLimit < 0 || this.instancesSpawned < this.instanceLimit;
		}
		
		//Checks if another structure can be spawned at all (used to flag for removal from the list)
		public boolean canSpawnMoreStructures() {
			return this.instanceLimit < 0 || this.instancesSpawned < this.instanceLimit;
		}
		
	}
	
}

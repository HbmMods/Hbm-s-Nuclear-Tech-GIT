package com.hbm.world.worldgen.components;

import java.util.List;
import java.util.Random;

import net.minecraft.world.gen.structure.StructureComponent;

public class BunkerComponents {
	
	
	
	public abstract static class Bunker extends Feature {
		
		public Bunker() { }
		
		public Bunker(int componentType) {
			super(componentType); //important to carry over, as it allows for hard limits on the amount of components. increment once for each new component.
		}
		
		/** Gets next component in any direction.<br>'component' refers to the initial starting component (hard distance limits), 'components' refers to the StructureStart list. */
		protected StructureComponent getNextComponentNormal(StructureComponent component, List components, Random rand, int offset, int offsetY) {
			switch(this.coordBaseMode) {
			case 0:
				//TODO: getNextValidComponent()
			case 1:
			
			case 2:
				
			case 3:
				
			default:
				return null;
			}
		}
		
	}
}

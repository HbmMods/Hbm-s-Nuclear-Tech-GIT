package com.hbm.tileentity.machine.candu;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.hbm.items.machine.ItemCANDUBundle;
import com.hbm.tileentity.TileEntityMachineBase;

public class TileEntityCanduCore extends TileEntityMachineBase {
	
	BiMap<Integer, int[]> fuelRods = HashBiMap.create();

	public TileEntityCanduCore() {
		super(61);
		
	}

	@Override
	public String getName() {
		return "container.candu_core";
	}

	@Override
	public void updateEntity() {
		
		/*if(!worldObj.isRemote) {
			
			for(int i = 0; i <= 60; i++) {
				
				if(this.slots[i].getItem() instanceof ItemCANDUBundle) {
					ItemCANDUBundle fuelRod = (ItemCANDUBundle) this.slots[i].getItem();
					fuelRod.setCoreHeat(slots[i], 40D);
				}
			
			}
		}*/
	}
	
	public BiMap<Integer, int[]> getFuelMap() {
		return fuelRods;
	}
	
	public void createFuelMapEntry(Integer slotIndex, int[] coordinates) {
		fuelRods.put(slotIndex, coordinates);
	}
	
}

package com.hbm.tileentity.bomb;

import com.hbm.items.ModItems;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IBatteryItem;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityNukeBalefire extends TileEntityMachineBase {

	public boolean loaded;
	public boolean started;
	public int timer;

	public TileEntityNukeBalefire() {
		super(2);
	}

	@Override
	public String getName() {
		return "container.nukeFstbmb";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(started)
				timer--;
			
			if(timer <= 0)
				explode();
			
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("timer", timer);
			data.setBoolean("loaded", loaded);
			data.setBoolean("started", started);
			networkPack(data, 250);
		}
	}
	
	public void networkUnpack(NBTTagCompound data) {
		
		timer = data.getInteger("timer");
		started = data.getBoolean("started");
		loaded = data.getBoolean("loaded");
	}
	
	public boolean isLoaded() {
		
		return hasEgg() && hasBattery();
	}
	
	public boolean hasEgg() {
		
		if(slots[0] != null && slots[0].getItem() == ModItems.egg_balefire) {
			return true;
		}
		
		return false;
	}
	
	public boolean hasBattery() {
		
		if(slots[1] != null && slots[1].getItem() == ModItems.battery_spark &&
				((IBatteryItem)ModItems.battery_spark).getCharge(slots[1]) == ((IBatteryItem)ModItems.battery_spark).getMaxCharge()) {
			return true;
		}
		
		return false;
	}
	
	public void explode() {
		
	}
	
	public String getMinutes() {
		
		String mins = "" + (timer / 60);
		
		if(mins.length() == 1)
			mins = "0" + mins;
		
		return mins;
	}
	
	public String getSeconds() {
		
		String mins = "" + (timer % 60);
		
		if(mins.length() == 1)
			mins = "0" + mins;
		
		return mins;
	}

}

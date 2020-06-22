package com.hbm.saveddata.satellites;

import com.hbm.entity.logic.EntityDeathBlast;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class SatelliteLaser extends Satellite {
	
	public long lastOp;
	
	public SatelliteLaser() {
		this.ifaceAcs.add(InterfaceActions.HAS_MAP);
		this.ifaceAcs.add(InterfaceActions.SHOW_COORDS);
		this.ifaceAcs.add(InterfaceActions.CAN_CLICK);
		this.satIface = Interfaces.SAT_PANEL;
	}
	
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setLong("lastOp", lastOp);
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		lastOp = nbt.getLong("lastOp");
	}
	
	public void onClick(World world, int x, int z) {
		
		if(lastOp + 10000 < System.currentTimeMillis()) {
    		lastOp = System.currentTimeMillis();
    		
    		int y = world.getHeightValue(x, z);
    		
    		EntityDeathBlast blast = new EntityDeathBlast(world);
    		blast.posX = x;
    		blast.posY = y;
    		blast.posZ = z;
    		
    		world.spawnEntityInWorld(blast);
    	}
	}
}

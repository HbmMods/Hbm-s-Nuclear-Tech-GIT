package com.hbm.saveddata;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class SatelliteSavedData extends WorldSavedData {
	
	public int satCount;
	
	public List<SatelliteSaveStructure> satellites = new ArrayList();
	
    private World worldObj;

	public SatelliteSavedData(String p_i2141_1_) {
		super(p_i2141_1_);
	}

    public SatelliteSavedData(World p_i1678_1_)
    {
        super("satellites");
        this.worldObj = p_i1678_1_;
        this.markDirty();
    }
    
    public boolean isFreqTaken(int freq) {
    	
    	return getSatFromFreq(freq) != null;
    }
    
    public SatelliteSaveStructure getSatFromFreq(int freq) {
    	
    	for(SatelliteSaveStructure sat : satellites)
    		if(sat.satelliteID == freq)
    			return sat;
    	
    	return null;
    }

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		satCount = nbt.getInteger("satCount");
		
		for(int i = 0; i < satCount; i++) {
			SatelliteSaveStructure struct = new SatelliteSaveStructure();
			struct.readFromNBT(nbt, i);
			
			satellites.add(struct);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("satCount", satellites.size());
		
		for(int i = 0; i < satellites.size(); i++) {
			satellites.get(i).writeToNBT(nbt, i);
		}
	}

}

package com.hbm.saveddata;

import java.util.HashMap;
import java.util.Map.Entry;

import com.hbm.saveddata.satellites.Satellite;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class SatelliteSavedData extends WorldSavedData {
	
	public HashMap<Integer, Satellite> sats = new HashMap();
	
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
    
    public Satellite getSatFromFreq(int freq) {
    	
    	return sats.get(freq);
    }

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		int satCount = nbt.getInteger("satCount");
		
		for(int i = 0; i < satCount; i++) {
			
			Satellite sat = Satellite.create(nbt.getInteger("sat_id_" + i));
			sat.readFromNBT((NBTTagCompound) nbt.getTag("sat_data_" + i));
			
			int freq = nbt.getInteger("sat_freq_" + i);
			
			sats.put(freq, sat);
			
			System.out.println("Loaded sat" + i + " " + sat.getClass().getSimpleName());
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("satCount", sats.size());
		
		int i = 0;

    	for(Entry<Integer, Satellite> struct : sats.entrySet()) {

    		NBTTagCompound data = new NBTTagCompound();
    		struct.getValue().writeToNBT(data);
    		
    		nbt.setInteger("sat_id_" + i, struct.getValue().getID());
    		nbt.setTag("sat_data_" + i, data);
    		nbt.setInteger("sat_freq_" + i, struct.getKey());
			i++;
			
			System.out.println("Saved sat" + i + " " + struct.getValue().getClass().getSimpleName());
    	}
	}
	
	public static SatelliteSavedData getData(World worldObj) {

		SatelliteSavedData data = (SatelliteSavedData)worldObj.perWorldStorage.loadData(SatelliteSavedData.class, "satellites");
	    if(data == null) {
	        worldObj.perWorldStorage.setData("satellites", new SatelliteSavedData(worldObj));
	        
	        data = (SatelliteSavedData)worldObj.perWorldStorage.loadData(SatelliteSavedData.class, "satellites");
	    }
	    
	    data.worldObj = worldObj;
	    
	    return data;
	}

}

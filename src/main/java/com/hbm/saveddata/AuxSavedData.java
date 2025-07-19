package com.hbm.saveddata;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class AuxSavedData extends WorldSavedData {
	
	public List<DataPair> data = new ArrayList();

	public AuxSavedData(String p_i2141_1_) {
		super(p_i2141_1_);
	}

    public AuxSavedData()
    {
        super("hbmauxdata");
        this.markDirty();
    }
    
    static class DataPair {
    	
    	String key = "";
    	int value;
    	
    	public DataPair() { }
    	
    	public DataPair(String s, int i) {
    		key = s;
    		value = i;
    	}
    	
    	void readFromNBT(NBTTagCompound nbt, int i) {
    		this.key = nbt.getString("aux_key_" + i);
    		this.value = nbt.getInteger("aux_val_" + i);
    	}
    	
    	void writeToNBT(NBTTagCompound nbt, int i) {
    		nbt.setString("aux_key_" + i, key);
    		nbt.setInteger("aux_val_" + i, value);
    	}
    	
    }

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		
		int count = nbt.getInteger("dCount");
		
		for(int i = 0; i < count; i++) {
			DataPair struct = new DataPair();
			struct.readFromNBT(nbt, i);
			
			data.add(struct);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		
		nbt.setInteger("dCount", data.size());
		
		for(int i = 0; i < data.size(); i++) {
			data.get(i).writeToNBT(nbt, i);
		}
	}
	
	public static AuxSavedData getData(World worldObj) {

		AuxSavedData data = (AuxSavedData)worldObj.perWorldStorage.loadData(AuxSavedData.class, "hbmauxdata");
	    if(data == null) {
	        worldObj.perWorldStorage.setData("hbmauxdata", new AuxSavedData());
	        
	        data = (AuxSavedData)worldObj.perWorldStorage.loadData(AuxSavedData.class, "hbmauxdata");
	    }
	    
	    return data;
	}
	
	public static void setThunder(World world, int dura) {
		AuxSavedData data = getData(world);
		
		if(data.data == null) {
			data.data = new ArrayList();
			data.data.add(new DataPair("thunder", dura));
			
		} else {
			
			DataPair thunder = null;
			
			for(DataPair pair : data.data) {
				if(pair.key.equals("thunder")) {
					thunder = pair;
					break;
				}
			}
			
			if(thunder == null) {
				data.data.add(new DataPair("thunder", dura));
			} else {
				thunder.value = dura;
			}
		}
		
		data.markDirty();
	}

	public static int getThunder(World world) {

		AuxSavedData data = getData(world);
		
		if(data == null)
			return 0;

		for(DataPair pair : data.data) {
			if(pair.key.equals("thunder")) {
				return pair.value;
			}
		}

		return 0;
	}
}

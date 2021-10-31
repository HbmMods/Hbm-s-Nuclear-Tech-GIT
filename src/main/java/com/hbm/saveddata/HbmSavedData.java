/*package com.hbm.saveddata;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class HbmSavedData extends WorldSavedData
{
    private NBTTagCompound data = new NBTTagCompound();

    public HbmSavedData(String tag) {
        super(tag);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
   	 	data = nbt.getCompoundTag("detCount");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        nbt.setTag("detCount", data);
    }

    public HbmSavedData getData(World worldObj) {
    	HbmSavedData data = (HbmSavedData)worldObj.perWorldStorage.loadData(HbmSavedData.class, "hbmsaveddata");
	    if(data == null) {
	        worldObj.perWorldStorage.setData("hbmsaveddata", new AuxSavedData());
	        
	        data = (HbmSavedData) worldObj.perWorldStorage.loadData(HbmSavedData.class, "hbmsaveddata");
	    }
	    
	    return data;
	}
	
    
    public void setNukeDetCount(World world, int detCount) {
    	HbmSavedData worldData = this.getData(world);
    }
    
   // public int getNukeDetCount(World world)
}*/
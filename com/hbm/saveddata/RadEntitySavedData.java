package com.hbm.saveddata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.chunk.Chunk;

public class RadEntitySavedData extends WorldSavedData {
	
	public HashMap<Integer, Float> contaminated = new HashMap();
	
    private World worldObj;

	public RadEntitySavedData(String p_i2141_1_) {
		super(p_i2141_1_);
	}

    public RadEntitySavedData(World p_i1678_1_)
    {
        super("radentity");
        this.worldObj = p_i1678_1_;
        this.markDirty();
    }
    
    public float getRadFromEntity(Entity e) {
    	
    	Iterator it = contaminated.entrySet().iterator();
    	
    	while(it.hasNext()) {
    		
    		Map.Entry pair = (Map.Entry)it.next();
    		
    		if(((Integer)pair.getKey()).intValue() == e.getEntityId()) {
    			return (Float)pair.getValue();
    		}
    	}
    	
    	return 0F;
    }
    
    public void setRadForEntity(Entity e, float rad) {

    	Iterator it = contaminated.entrySet().iterator();
    	
    	while(it.hasNext()) {
    		
    		Map.Entry pair = (Map.Entry)it.next();
    		
    		if(((Integer)pair.getKey()).intValue() == e.getEntityId()) {
    			pair.setValue(rad);
    	    	
    	    	this.markDirty();
    			return;
    		}
    	}
    	
    	contaminated.put(e.getEntityId(), rad);
    	
    	this.markDirty();
    }

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		int count = nbt.getInteger("contCount");
		
		for(int i = 0; i < count; i++) {
			
			contaminated.put(nbt.getInteger("entID_" + i), nbt.getFloat("cont_" + i));
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("contCount", contaminated.entrySet().size());
		
		int i = 0;
		
		for (Map.Entry<Integer, Float> entry : contaminated.entrySet()) {

			nbt.setInteger("entID_" + i, entry.getKey());
			nbt.setFloat("cont_" + i, entry.getValue());
			
			i++;
		}
	}
	
	public static RadEntitySavedData getData(World worldObj) {

		RadEntitySavedData data = (RadEntitySavedData)worldObj.perWorldStorage.loadData(RadEntitySavedData.class, "radentity");
	    if(data == null) {
	        worldObj.perWorldStorage.setData("radentity", new RadEntitySavedData(worldObj));
	        
	        data = (RadEntitySavedData)worldObj.perWorldStorage.loadData(RadEntitySavedData.class, "radentity");
	    }
	    
	    return data;
	}

}

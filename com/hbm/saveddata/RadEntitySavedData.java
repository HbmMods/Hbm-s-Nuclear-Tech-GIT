package com.hbm.saveddata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.chunk.Chunk;

public class RadEntitySavedData extends WorldSavedData {
	
	public List<RadEntry> contaminated = new ArrayList();
	
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
    	
    	for(int i = 0; i < contaminated.size(); i++) {
    		if(contaminated.get(i).entID == e.getEntityId())
    			return contaminated.get(i).rad;
    	}
    	
    	return 0F;
    }
    
    public void setRadForEntity(Entity e, float rad) {
    	
    	if(!(e instanceof EntityLivingBase))
    		return;
    	
    	for(int i = 0; i < contaminated.size(); i++) {
    		if(contaminated.get(i).entID == e.getEntityId()) {
    			contaminated.get(i).rad = rad;
    			this.markDirty();
    			return;
    		}
    	}
    	
    	contaminated.add(new RadEntry(e.getEntityId(), rad));
    	
    	this.markDirty();
    }
    
    public void increaseRad(Entity e, float rad) {
    	
    	setRadForEntity(e, getRadFromEntity(e) + rad);
    }

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		int count = nbt.getInteger("contCount");
		
		for(int i = 0; i < count; i++) {
			
	    	contaminated.add(new RadEntry(nbt.getInteger("entID_" + i), nbt.getFloat("cont_" + i)));
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("contCount", contaminated.size());
		
		int i = 0;
		
		for (RadEntry entry : contaminated) {

			nbt.setInteger("entID_" + i, entry.entID);
			nbt.setFloat("cont_" + i, entry.rad);
			
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
	
	public class RadEntry {
	
		int entID;
		float rad;
		
		public RadEntry(int id, float rad) {
			this.entID = id;
			this.rad = rad;
		}
	}

}

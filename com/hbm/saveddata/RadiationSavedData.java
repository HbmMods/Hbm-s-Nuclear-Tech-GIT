package com.hbm.saveddata;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.particle.EntityFogFX;
import com.hbm.main.MainRegistry;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.chunk.Chunk;

public class RadiationSavedData extends WorldSavedData {
	
	public List<RadiationSaveStructure> contamination = new ArrayList();
	
	private static RadiationSavedData openInstance;
	
    public World worldObj;

	public RadiationSavedData(String p_i2141_1_) {
		super(p_i2141_1_);
	}

    public RadiationSavedData(World p_i1678_1_)
    {
        super("radiation");
        this.worldObj = p_i1678_1_;
        this.markDirty();
    }
    
    public boolean doesEntryExist(int x, int y) {
    	
    	return getRadFromCoord(x, y) != null;
    }
    
    public void createEntry(int x, int y, float rad) {
    	
    	contamination.add(new RadiationSaveStructure(x, y, rad));
        this.markDirty();
    }
    
    public void deleteEntry(RadiationSaveStructure struct) {
    	
    	contamination.remove(struct);
        this.markDirty();
    }
    
    public void jettisonData() {
    	
    	contamination.clear();
        this.markDirty();
    }
    
    public void setRadForCoord(int x, int y, float radiation) {
    	
    	RadiationSaveStructure entry = null;
    	
    	for(RadiationSaveStructure rad : contamination)
    		if(rad.chunkX == x && rad.chunkY == y) {
    			entry = rad;
    			break;
    		}
    	
    	if(entry == null) {

    		entry = new RadiationSaveStructure(x, y, radiation);
        	contamination.add(entry);
    	}
    	
    	entry.radiation = radiation;
        this.markDirty();
    	
    }
    
    public RadiationSaveStructure getRadFromCoord(int x, int y) {
    	
    	for(RadiationSaveStructure rad : contamination)
    		if(rad.chunkX == x && rad.chunkY == y)
    			return rad;
    	
    	return null;
    }
    
    public float getRadNumFromCoord(int x, int y) {
    	
    	for(RadiationSaveStructure rad : contamination)
    		if(rad.chunkX == x && rad.chunkY == y)
    			return rad.radiation;
    	
    	if(worldObj != null && worldObj.provider instanceof WorldProviderHell)
    		return MainRegistry.hellRad;
    	
    	return 0F;
    }
    
    public void updateSystem() {
    	
    	List<RadiationSaveStructure> tempList = new ArrayList(contamination);
    	
    	contamination.clear();
    	
    	for(RadiationSaveStructure struct : tempList) {
    		
    		if(struct.radiation != 0) {

				//struct.radiation *= 0.999F;
				struct.radiation *= 0.99F;
				struct.radiation -= 0.5F;
				
				if(struct.radiation <= 0) {
					struct.radiation = 0;
				}
				
				if(struct.radiation > MainRegistry.fogRad && worldObj != null && worldObj.rand.nextInt(MainRegistry.fogCh) == 0 && worldObj.getChunkFromChunkCoords(struct.chunkX, struct.chunkY).isChunkLoaded) {
					
					int x = struct.chunkX * 16 + worldObj.rand.nextInt(16);
					int z = struct.chunkY * 16 + worldObj.rand.nextInt(16);
					int y = worldObj.getHeightValue(x, z) + worldObj.rand.nextInt(5);
					
					EntityFogFX fog = new EntityFogFX(worldObj);
					fog.setPosition(x, y, z);
					//System.out.println(x + " " + y + " " + z);
					worldObj.spawnEntityInWorld(fog);
				}
    			
    			if(struct.radiation > 1) {
    				
    				float[] rads = new float[9];

    				rads[0] = getRadNumFromCoord(struct.chunkX + 1, struct.chunkY + 1);
    				rads[1] = getRadNumFromCoord(struct.chunkX, struct.chunkY + 1);
    				rads[2] = getRadNumFromCoord(struct.chunkX - 1, struct.chunkY + 1);
    				rads[3] = getRadNumFromCoord(struct.chunkX - 1, struct.chunkY);
    				rads[4] = getRadNumFromCoord(struct.chunkX - 1, struct.chunkY - 1);
    				rads[5] = getRadNumFromCoord(struct.chunkX, struct.chunkY - 1);
    				rads[6] = getRadNumFromCoord(struct.chunkX + 1, struct.chunkY - 1);
    				rads[7] = getRadNumFromCoord(struct.chunkX + 1, struct.chunkY);
    				rads[8] = getRadNumFromCoord(struct.chunkX, struct.chunkY);
    				
    				float main = 0.6F;
    				float side = 0.075F;
    				float corner = 0.025F;
    				
    				setRadForCoord(struct.chunkX + 1, struct.chunkY + 1, rads[0] + struct.radiation * corner);
    				setRadForCoord(struct.chunkX, struct.chunkY + 1, rads[1] + struct.radiation * side);
    				setRadForCoord(struct.chunkX - 1, struct.chunkY + 1, rads[2] + struct.radiation * corner);
    				setRadForCoord(struct.chunkX - 1, struct.chunkY, rads[3] + struct.radiation * side);
    				setRadForCoord(struct.chunkX - 1, struct.chunkY - 1, rads[4] + struct.radiation * corner);
    				setRadForCoord(struct.chunkX, struct.chunkY - 1, rads[5] + struct.radiation * side);
    				setRadForCoord(struct.chunkX + 1, struct.chunkY - 1, rads[6] + struct.radiation * corner);
    				setRadForCoord(struct.chunkX + 1, struct.chunkY, rads[7] + struct.radiation * side);
    				setRadForCoord(struct.chunkX, struct.chunkY, rads[8] + struct.radiation * main);
    				
    			} else {
    				
    				this.setRadForCoord(struct.chunkX, struct.chunkY, getRadNumFromCoord(struct.chunkX, struct.chunkY) + struct.radiation);
    			}
    		}
    	}
    	
        this.markDirty();
    }

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		int count = nbt.getInteger("radCount");
		
		for(int i = 0; i < count; i++) {
			RadiationSaveStructure struct = new RadiationSaveStructure();
			struct.readFromNBT(nbt, i);
			
			contamination.add(struct);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("radCount", contamination.size());
		
		for(int i = 0; i < contamination.size(); i++) {
			contamination.get(i).writeToNBT(nbt, i);
		}
	}
	
	public static RadiationSavedData getData(World worldObj) {
		
		if(openInstance != null && openInstance.worldObj == worldObj)
			return openInstance;

		RadiationSavedData data = (RadiationSavedData)worldObj.perWorldStorage.loadData(RadiationSavedData.class, "radiation");
	    if(data == null) {
	        worldObj.perWorldStorage.setData("radiation", new RadiationSavedData(worldObj));
	        
	        data = (RadiationSavedData)worldObj.perWorldStorage.loadData(RadiationSavedData.class, "radiation");
	    }
	    
	    data.worldObj = worldObj;
	    openInstance  = data;
	    
	    return openInstance;
	}
	
	public static void incrementRad(World worldObj, int x, int z, float rad, float maxRad) {
		
		RadiationSavedData data = getData(worldObj);
		
		Chunk chunk = worldObj.getChunkFromBlockCoords(x, z);
		
		float r = data.getRadNumFromCoord(chunk.xPosition, chunk.zPosition);
		
		if(r < maxRad) {
			
			data.setRadForCoord(chunk.xPosition, chunk.zPosition, r + rad);
		}
	}
	
	public static void decrementRad(World worldObj, int x, int z, float rad) {
		
		RadiationSavedData data = getData(worldObj);
		
		Chunk chunk = worldObj.getChunkFromBlockCoords(x, z);
		
		float r = data.getRadNumFromCoord(chunk.xPosition, chunk.zPosition);
		
		r -= rad;
		
		if(r > 0) {
			data.setRadForCoord(chunk.xPosition, chunk.zPosition, r);
		} else {
			data.setRadForCoord(chunk.xPosition, chunk.zPosition, 0);
		}
	}

}

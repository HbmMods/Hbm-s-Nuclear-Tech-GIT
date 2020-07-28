package com.hbm.saveddata;

import java.util.HashMap;
import java.util.Map.Entry;

import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxParticlePacket;
import com.hbm.packet.PacketDispatcher;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.chunk.Chunk;

public class RadiationSavedData extends WorldSavedData {

	public HashMap<ChunkCoordIntPair, Float> contamination = new HashMap();
	
	//in order to reduce read operations
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
    
    public void jettisonData() {
    	
    	contamination.clear();
        this.markDirty();
    }
    
    public void setRadForCoord(int x, int y, float radiation) {

    	ChunkCoordIntPair pair = new ChunkCoordIntPair(x, y);
    	contamination.put(pair, radiation);
        this.markDirty();
    	
    }
    
    public float getRadNumFromCoord(int x, int y) {
    	
    	ChunkCoordIntPair pair = new ChunkCoordIntPair(x, y);
    	Float rad = contamination.get(pair);
    	
    	return rad == null ? 0 : rad;
    }
    
    public void updateSystem() {
    	
    	HashMap<ChunkCoordIntPair, Float> tempList = new HashMap(contamination);
    	contamination.clear();
    	
    	for(Entry<ChunkCoordIntPair, Float> struct : tempList.entrySet()) {
    		
    		if(struct.getValue() != 0) {
    			
    			float rad = struct.getValue();

				//struct.radiation *= 0.999F;
    			rad *= 0.999F;
    			rad -= 0.05F;
				
				if(rad <= 0) {
					rad = 0;
				}
				
				if(rad > MainRegistry.fogRad && worldObj != null && worldObj.rand.nextInt(MainRegistry.fogCh) == 0 && worldObj.getChunkFromChunkCoords(struct.getKey().chunkXPos, struct.getKey().chunkZPos).isChunkLoaded) {
					
					int x = struct.getKey().chunkXPos * 16 + worldObj.rand.nextInt(16);
					int z = struct.getKey().chunkZPos * 16 + worldObj.rand.nextInt(16);
					int y = worldObj.getHeightValue(x, z) + worldObj.rand.nextInt(5);
					
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacket(x, y, z, 3), new TargetPoint(worldObj.provider.dimensionId, x, y, z, 100));
				}
    			
    			if(rad > 1) {
    				
    				float[] rads = new float[9];

    				rads[0] = getRadNumFromCoord(struct.getKey().chunkXPos + 1, struct.getKey().chunkZPos + 1);
    				rads[1] = getRadNumFromCoord(struct.getKey().chunkXPos, struct.getKey().chunkZPos + 1);
    				rads[2] = getRadNumFromCoord(struct.getKey().chunkXPos - 1, struct.getKey().chunkZPos + 1);
    				rads[3] = getRadNumFromCoord(struct.getKey().chunkXPos - 1, struct.getKey().chunkZPos);
    				rads[4] = getRadNumFromCoord(struct.getKey().chunkXPos - 1, struct.getKey().chunkZPos - 1);
    				rads[5] = getRadNumFromCoord(struct.getKey().chunkXPos, struct.getKey().chunkZPos - 1);
    				rads[6] = getRadNumFromCoord(struct.getKey().chunkXPos + 1, struct.getKey().chunkZPos - 1);
    				rads[7] = getRadNumFromCoord(struct.getKey().chunkXPos + 1, struct.getKey().chunkZPos);
    				rads[8] = getRadNumFromCoord(struct.getKey().chunkXPos, struct.getKey().chunkZPos);
    				
    				float main = 0.6F;
    				float side = 0.075F;
    				float corner = 0.025F;
    				
    				setRadForCoord(struct.getKey().chunkXPos + 1, struct.getKey().chunkZPos + 1, rads[0] + rad * corner);
    				setRadForCoord(struct.getKey().chunkXPos, struct.getKey().chunkZPos + 1, rads[1] + rad * side);
    				setRadForCoord(struct.getKey().chunkXPos - 1, struct.getKey().chunkZPos + 1, rads[2] + rad * corner);
    				setRadForCoord(struct.getKey().chunkXPos - 1, struct.getKey().chunkZPos, rads[3] + rad * side);
    				setRadForCoord(struct.getKey().chunkXPos - 1, struct.getKey().chunkZPos - 1, rads[4] + rad * corner);
    				setRadForCoord(struct.getKey().chunkXPos, struct.getKey().chunkZPos - 1, rads[5] + rad * side);
    				setRadForCoord(struct.getKey().chunkXPos + 1, struct.getKey().chunkZPos - 1, rads[6] + rad * corner);
    				setRadForCoord(struct.getKey().chunkXPos + 1, struct.getKey().chunkZPos, rads[7] + rad * side);
    				setRadForCoord(struct.getKey().chunkXPos, struct.getKey().chunkZPos, rads[8] + rad * main);
    				
    			} else {
    				
    				this.setRadForCoord(struct.getKey().chunkXPos, struct.getKey().chunkZPos, getRadNumFromCoord(struct.getKey().chunkXPos, struct.getKey().chunkZPos) + rad);
    			}
    		}
    	}
    	
        this.markDirty();
    }

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		int count = nbt.getInteger("radCount");
		
		for(int i = 0; i < count; i++) {

	    	ChunkCoordIntPair pair = new ChunkCoordIntPair(
	    			nbt.getInteger("cposX" + i),
	    			nbt.getInteger("cposZ" + i)
	    			
	    	);
			
			contamination.put(
					pair,
					nbt.getFloat("crad" + i)
			);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("radCount", contamination.size());
		
		int i = 0;

    	for(Entry<ChunkCoordIntPair, Float> struct : contamination.entrySet()) {
				nbt.setInteger("cposX" + i, struct.getKey().chunkXPos);
				nbt.setInteger("cposZ" + i, struct.getKey().chunkZPos);
				nbt.setFloat("crad" + i, struct.getValue());
				
				i++;
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

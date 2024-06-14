package com.hbm.dim;

import java.util.HashMap;
import java.util.Map.Entry;

import com.hbm.dim.trait.CelestialBodyTrait;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class CelestialBodyWorldSavedData extends WorldSavedData {

	private static final String DATA_NAME = "CelestialBodyData";

	public CelestialBodyWorldSavedData(String name) {
		super(name);
	}

    private HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait> traits;
    private long localTime;
    
	public static CelestialBodyWorldSavedData get(World world) {
		CelestialBodyWorldSavedData result = (CelestialBodyWorldSavedData) world.perWorldStorage.loadData(CelestialBodyWorldSavedData.class, DATA_NAME);
		
		if(result == null) {
			world.perWorldStorage.setData(DATA_NAME, new CelestialBodyWorldSavedData(DATA_NAME));
			result = (CelestialBodyWorldSavedData) world.perWorldStorage.loadData(CelestialBodyWorldSavedData.class, DATA_NAME);
		}
		
		return result;
	}

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        localTime = nbt.getLong("time");

        if(!nbt.hasKey("traits")) {
            traits = null;
            return;
        }

        NBTTagCompound data = nbt.getCompoundTag("traits");

        // iterate through traits, loading into `traits`
        traits = new HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait>();
        for(Entry<String, Class<? extends CelestialBodyTrait>> entry : CelestialBodyTrait.traitMap.entrySet()) {
            if(data.hasKey(entry.getKey())) {
                try {
                    CelestialBodyTrait trait = entry.getValue().newInstance();
                    trait.readFromNBT(data.getCompoundTag(entry.getKey()));
                    traits.put(trait.getClass(), trait);
                } catch (Exception ex) {
                }
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        nbt.setLong("time", localTime);

        if(traits == null) {
            nbt.removeTag("traits");
            return;
        }

        NBTTagCompound data = new NBTTagCompound();

        for(CelestialBodyTrait trait : traits.values()) {
            String name = CelestialBodyTrait.traitMap.inverse().get(trait.getClass());
            NBTTagCompound traitData = new NBTTagCompound();
            trait.writeToNBT(traitData);
            data.setTag(name, traitData);
        }

        nbt.setTag("traits", data);
    }

    public long getLocalTime() {
        return localTime;
    }

    public void setLocalTime(long time) {
        localTime = time;
        markDirty();
    }

    public void setTraits(CelestialBodyTrait... traits) {
        this.traits = new HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait>();
        for(CelestialBodyTrait trait : traits) {
            this.traits.put(trait.getClass(), trait);
        }
        markDirty();
    }

    public void clearTraits() {
        this.traits = null;
    }

    private static HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait> clientTraits;

    @SideOnly(Side.CLIENT)
    public static void updateClientTraits(HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait> traits) {
        clientTraits = traits;
    }

    public static HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait> getTraits(World world) {
        if(world == null) {
            // If we don't have a world, assume we're a client, and return the permasync data.
            // If we haven't yet received permasync data, don't fret, we just won't have overrides yet.
            // This will only break rendering for the client, they're not gonna suffocate.

            return clientTraits;
        }

        return get(world).traits;
    }

    public HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait> getTraits() {
        return traits;
    }
	
}

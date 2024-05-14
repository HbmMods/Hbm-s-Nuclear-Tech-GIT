package com.hbm.dim;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.logging.log4j.core.Logger;

import com.hbm.dim.trait.CelestialBodyTrait;
import com.hbm.main.MainRegistry;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraftforge.common.util.Constants;

public class CelestialBodyWorldSavedData extends WorldSavedData {

	private static final String DATA_NAME = "CelestialBodyData";

	public CelestialBodyWorldSavedData(String name) {
		super(name);
	}

    private HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait> traits;

	private static CelestialBodyWorldSavedData lastCachedUnsafe = null;

	public static CelestialBodyWorldSavedData get(World world) {
		if(world == null) {
			return lastCachedUnsafe;
		}

		CelestialBodyWorldSavedData result = (CelestialBodyWorldSavedData) world.perWorldStorage.loadData(CelestialBodyWorldSavedData.class, DATA_NAME);
		
		if(result == null) {
			world.perWorldStorage.setData(DATA_NAME, new CelestialBodyWorldSavedData(DATA_NAME));
			result = (CelestialBodyWorldSavedData) world.perWorldStorage.loadData(CelestialBodyWorldSavedData.class, DATA_NAME);
		}
		
		lastCachedUnsafe = result;
		return result;
	}

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
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
                	MainRegistry.logger.catching(ex);
                }
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        NBTTagCompound data = new NBTTagCompound();

        for(CelestialBodyTrait trait : traits.values()) {
            String name = CelestialBodyTrait.traitMap.inverse().get(trait.getClass());
            NBTTagCompound traitData = new NBTTagCompound();
            trait.writeToNBT(traitData);
            data.setTag(name, traitData);
        }

        nbt.setTag("traits", data);
    }

    public void setTraits(CelestialBodyTrait... traits) {
        this.traits = new HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait>();
        for(CelestialBodyTrait trait : traits) {
            this.traits.put(trait.getClass(), trait);
        }
        markDirty();
    }

    public HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait> getTraits() {
        return traits;
    }

	private NBTTagCompound writeTraitsToNBT(CelestialBodyTrait... traits) {
		NBTTagCompound nbt = new NBTTagCompound();
		for (CelestialBodyTrait trait : traits) {
			trait.writeToNBT(nbt);
		}
		return nbt;
	}


	
}

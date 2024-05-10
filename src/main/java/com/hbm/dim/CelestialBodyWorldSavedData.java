package com.hbm.dim;

import java.util.HashSet;
import java.util.Set;

import com.hbm.dim.trait.PlanetaryTrait;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class CelestialBodyWorldSavedData extends WorldSavedData {

    private static final String DATA_NAME = "CelestialBodyData";

    public CelestialBodyWorldSavedData(String name) {
        super(name);
    }

    private NBTTagCompound traits = new NBTTagCompound();

	private static CelestialBodyWorldSavedData lastCachedUnsafe = null;

	public static CelestialBodyWorldSavedData get(World world) {
		if(world == null) {
			return lastCachedUnsafe;
		}

		CelestialBodyWorldSavedData result = (CelestialBodyWorldSavedData) world.perWorldStorage.loadData(CelestialBodyWorldSavedData.class, "PlanetaryTraitsData");
		
        if(result == null) {
            world.perWorldStorage.setData(DATA_NAME, new CelestialBodyWorldSavedData(DATA_NAME));
            result = (CelestialBodyWorldSavedData) world.perWorldStorage.loadData(CelestialBodyWorldSavedData.class, "PlanetaryTraitsData");
        }
        
        lastCachedUnsafe = result;
        return result;
	}

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        traits = nbt.getCompoundTag("traits");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        nbt.setTag("traits", traits);
    }

    public void setTraits(int dimensionId, PlanetaryTrait... traits) {
        this.traits.setTag(Integer.toString(dimensionId), writeTraitsToNBT(traits));
        markDirty(); 
    }

    public Set<PlanetaryTrait> getTraits(int dimensionId) {
        return readTraitsFromNBT(traits.getCompoundTag(Integer.toString(dimensionId)));
    }

    private NBTTagCompound writeTraitsToNBT(PlanetaryTrait... traits) {
        NBTTagCompound nbt = new NBTTagCompound();
        for (PlanetaryTrait trait : traits) {
            trait.writeToNBT(nbt);
        }
        return nbt;
    }

    private Set<PlanetaryTrait> readTraitsFromNBT(NBTTagCompound nbt) {
        Set<PlanetaryTrait> traits = new HashSet<PlanetaryTrait>();
        // TODO: READ
        return traits;
    }
    
}

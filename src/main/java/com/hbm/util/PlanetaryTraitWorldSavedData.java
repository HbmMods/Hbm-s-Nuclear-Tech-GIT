package com.hbm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.Level;

import com.hbm.main.MainRegistry;
import com.hbm.saveddata.TomSaveData;
import com.hbm.util.PlanetaryTraitUtil.Hospitality;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.MapStorage;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.world.WorldEvent;

public class PlanetaryTraitWorldSavedData extends WorldSavedData {
    private static final String DATA_NAME = "PlanetaryTraitsData";

    public NBTTagCompound data = new NBTTagCompound();
	private static PlanetaryTraitWorldSavedData lastCachedUnsafe = null;
	public static PlanetaryTraitWorldSavedData result2;
    public PlanetaryTraitWorldSavedData(String name) {
        super(name);
    }


	public static PlanetaryTraitWorldSavedData get(World world) {
		if(world == null) {
			return lastCachedUnsafe;
		}
		PlanetaryTraitWorldSavedData result = (PlanetaryTraitWorldSavedData) world.perWorldStorage.loadData(PlanetaryTraitWorldSavedData.class, "PlanetaryTraitsData");
		
			if(result == null) {
				world.perWorldStorage.setData(DATA_NAME, new PlanetaryTraitWorldSavedData(DATA_NAME));
				result = (PlanetaryTraitWorldSavedData) world.perWorldStorage.loadData(PlanetaryTraitWorldSavedData.class, "PlanetaryTraitsData");
			}
			
			lastCachedUnsafe = result;
			return result;

	}
	public static PlanetaryTraitWorldSavedData getLastCachedOrNull() {
		return lastCachedUnsafe;
	}

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        data = nbt.getCompoundTag("Traits");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        nbt.setTag("Traits", data);
        System.out.println(data);
    }

    // Add methods to get and set traits as needed
    public void setTraits(int dimensionId, Set<Hospitality> traits) {
        data.setTag(Integer.toString(dimensionId), writeTraitsToNBT(traits));
        markDirty(); 
    }

    public Set<Hospitality> getTraits(int dimensionId) {
        return readTraitsFromNBT(data.getCompoundTag(Integer.toString(dimensionId)));
    }

    private NBTTagCompound writeTraitsToNBT(Set<Hospitality> traits) {
        NBTTagCompound nbt = new NBTTagCompound();
        for (Hospitality trait : traits) {
            nbt.setBoolean(trait.name(), true);
        }
        return nbt;
    }

    private Set<Hospitality> readTraitsFromNBT(NBTTagCompound nbt) {
        Set<Hospitality> traits = EnumSet.noneOf(Hospitality.class);
        for (Hospitality trait : Hospitality.values()) {
            if (nbt.hasKey(trait.name(), Constants.NBT.TAG_BYTE)) {
                traits.add(trait);
            }
        }
        return traits;
    }


	public static void resetLastCached() {
		lastCachedUnsafe = null;		
	}


}
    

    

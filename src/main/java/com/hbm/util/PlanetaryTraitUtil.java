package com.hbm.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.Level;

import com.hbm.config.SpaceConfig;
import com.hbm.config.WorldConfig;
import com.hbm.main.MainRegistry;
import com.hbm.saveddata.TomSaveData;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.channel.DefaultAddressedEnvelope;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.Constants;

public class PlanetaryTraitUtil {
	
	//rule: a planets trait list should NEVER be null
	public enum Hospitality{
		TOXIC, //this would be interesting, what if we made it so that if laythe was breatheable but toxic, we would need to have it so that you wear a gas mask at all times?
		OXYNEG,
		BREATHEABLE,
		HOT,
		PARENT,
		CLOSEST,
		MOON
	}
	
	public static Map<Integer, Float> dimGrav = new HashMap<>();
	
	static {
		dimGrav.put(DimensionManager.getProviderType(0), 9.8F);

	}
	
    public static float getGravityForDimension(int dimensionId) {
        return dimGrav.getOrDefault(dimensionId, 2F); // Default gravity value if not found
    }
    
	//TODO: actually somehow account for moons relative to a planets distance......
    // one thing we can do is give a "MOON" planet trait and a PARENT planet trait.
    // the game will then check for that and switch calculation from interplanetary to local orbit
    // any better ideas lmk or i will set something on fire :333333333333333333333333333333333333333333333
	public static Map<Integer, Float> dimau = new HashMap<>();
	
	static {
		dimau.put(DimensionManager.getProviderType(SpaceConfig.eveDimension), AstronomyUtil.EveAU);
		dimau.put(DimensionManager.getProviderType(SpaceConfig.dresDimension), AstronomyUtil.DresAU);
		dimau.put(DimensionManager.getProviderType(SpaceConfig.moonDimension), AstronomyUtil.MunKerbinAU);
		dimau.put(DimensionManager.getProviderType(SpaceConfig.ikeDimension), AstronomyUtil.IkeDunaKm);
		dimau.put(DimensionManager.getProviderType(SpaceConfig.mohoDimension), AstronomyUtil.MohoAU);
		dimau.put(DimensionManager.getProviderType(SpaceConfig.laytheDimension), AstronomyUtil.JoolAU);
		dimau.put(DimensionManager.getProviderType(SpaceConfig.minmusDimension), AstronomyUtil.MinmusKerbinAU);
		dimau.put(DimensionManager.getProviderType(SpaceConfig.dunaDimension), AstronomyUtil.DunaAU);
		dimau.put(DimensionManager.getProviderType(0), AstronomyUtil.KerbinAU);


	}
	
    public static float getDistanceForDimension(int dimensionId) {
        return dimau.getOrDefault(dimensionId, 1F); 
    }

    
	
    public static Map<Integer, Set<Hospitality>> idToDimensionMap = new HashMap<>();
    
    static {
        idToDimensionMap.put(DimensionManager.getProviderType(SpaceConfig.eveDimension), EnumSet.of(Hospitality.TOXIC, Hospitality.HOT));
        idToDimensionMap.put(DimensionManager.getProviderType(SpaceConfig.dresDimension), EnumSet.of(Hospitality.OXYNEG));
        idToDimensionMap.put(DimensionManager.getProviderType(SpaceConfig.moonDimension), EnumSet.of(Hospitality.OXYNEG));
        idToDimensionMap.put(DimensionManager.getProviderType(SpaceConfig.ikeDimension), EnumSet.of(Hospitality.OXYNEG));
        idToDimensionMap.put(DimensionManager.getProviderType(SpaceConfig.mohoDimension), EnumSet.of(Hospitality.OXYNEG, Hospitality.HOT));
        idToDimensionMap.put(DimensionManager.getProviderType(SpaceConfig.laytheDimension), EnumSet.of(Hospitality.BREATHEABLE));
        idToDimensionMap.put(DimensionManager.getProviderType(SpaceConfig.minmusDimension), EnumSet.of(Hospitality.OXYNEG));
        idToDimensionMap.put(DimensionManager.getProviderType(SpaceConfig.dunaDimension), EnumSet.of(Hospitality.OXYNEG, Hospitality.TOXIC));
    }

    @Deprecated
    public static boolean isDimensionWithTrait(World world, Hospitality trait) {
        int dimensionId = world.provider.dimensionId;
        Set<Hospitality> traits = idToDimensionMap.getOrDefault(dimensionId, Collections.emptySet());
        return traits.contains(trait);
        
    }

    public static boolean isDimensionWithTraitNT(World world, Hospitality trait) {
        int dimensionId = world.provider.dimensionId;
        world = DimensionManager.getWorld(dimensionId);
        Set<Hospitality> traits = idToDimensionMap.getOrDefault(dimensionId, Collections.emptySet());
        PlanetaryTraitWorldSavedData data = PlanetaryTraitWorldSavedData.getLastCachedOrNull();
        
  
        // Check if saved data exists and has traits for the dimension
        // you retard, you didnt even check if the data even existed in the FIRST PLACE
        if (data != null) {
            PlanetaryTraitWorldSavedData traitsData = PlanetaryTraitWorldSavedData.get(world);

            Set<Hospitality> savedTraits = traitsData.getTraits(dimensionId);
            
            // If saved traits exist, use them instead
            if (!savedTraits.isEmpty()) {
                traits = savedTraits;
            }
        }
        
        return traits.contains(trait);
    }
    public static void removeTraitsFromDimension(int dimensionId, Set<Hospitality> traits) {
        Set<Hospitality> existingTraits = idToDimensionMap.getOrDefault(dimensionId, new HashSet<>());
        existingTraits.removeAll(traits);
        World world = DimensionManager.getWorld(dimensionId);
        if (existingTraits.isEmpty()) {
            idToDimensionMap.remove(dimensionId);
        } else {
    	    PlanetaryTraitWorldSavedData traitsData = PlanetaryTraitWorldSavedData.get(world);
            idToDimensionMap.put(dimensionId, existingTraits);
    	    traitsData.setTraits(world.provider.dimensionId, existingTraits);
    	    traitsData.markDirty();
        }
        
    }
    
    public static void addTraitsToDimension(int dimensionId, Set<Hospitality> traits) {
        //int dimensionId = world.provider.dimensionId;
        Set<Hospitality> existingTraits = idToDimensionMap.getOrDefault(dimensionId, new HashSet<>());
        existingTraits.addAll(traits);
        idToDimensionMap.put(dimensionId, existingTraits);
        World world = DimensionManager.getWorld(dimensionId); // Fetch the world based on the dimension ID
	    PlanetaryTraitWorldSavedData traitsData = PlanetaryTraitWorldSavedData.get(world);
	    traitsData.setTraits(world.provider.dimensionId, existingTraits);
	    traitsData.markDirty();
    }
	public static World lastSyncWorld = null;
	public static NBTTagCompound tag;

	@SideOnly(Side.CLIENT)
	public static NBTTagCompound getTagsForClient(World world) {
		if(world != lastSyncWorld) return tag;
		return tag;
	}
	
	
}


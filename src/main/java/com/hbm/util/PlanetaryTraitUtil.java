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

import com.hbm.config.WorldConfig;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.Constants;

public class PlanetaryTraitUtil {
	
	public enum Hospitality{
		TOXIC,
		VACUUM,
		BREATHEABLE,
		HOT;
		
	}

    public static Map<Integer, Set<Hospitality>> idToDimensionMap = new HashMap<>();
    
    static {
        idToDimensionMap.put(DimensionManager.getProviderType(WorldConfig.eveDimension), EnumSet.of(Hospitality.TOXIC, Hospitality.HOT));
        idToDimensionMap.put(DimensionManager.getProviderType(WorldConfig.dresDimension), EnumSet.of(Hospitality.VACUUM));
        idToDimensionMap.put(DimensionManager.getProviderType(WorldConfig.moonDimension), EnumSet.of(Hospitality.VACUUM));
        idToDimensionMap.put(DimensionManager.getProviderType(WorldConfig.ikeDimension), EnumSet.of(Hospitality.VACUUM));
        idToDimensionMap.put(DimensionManager.getProviderType(WorldConfig.mohoDimension), EnumSet.of(Hospitality.VACUUM, Hospitality.HOT));
    }

    public static boolean isDimensionWithTrait(World world, Hospitality trait) {
        int dimensionId = world.provider.dimensionId;
        Set<Hospitality> traits = idToDimensionMap.getOrDefault(dimensionId, Collections.emptySet());
        return traits.contains(trait);
    }
    
    public static void removeTraitsFromDimension(int dimensionId, Set<Hospitality> traits) {
        Set<Hospitality> existingTraits = idToDimensionMap.getOrDefault(dimensionId, new HashSet<>());
        existingTraits.removeAll(traits);
        if (existingTraits.isEmpty()) {
            idToDimensionMap.remove(dimensionId);
        } else {
            idToDimensionMap.put(dimensionId, existingTraits);
        }
        
    }
    
    public static void addTraitsToDimension(int dimensionId, Set<Hospitality> traits) {
        //int dimensionId = world.provider.dimensionId;
        Set<Hospitality> existingTraits = idToDimensionMap.getOrDefault(dimensionId, new HashSet<>());
        existingTraits.addAll(traits);
        idToDimensionMap.put(dimensionId, existingTraits);
        World world = DimensionManager.getWorld(dimensionId); // Fetch the world based on the dimension ID
    }
}


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

import com.hbm.config.SpaceConfig;
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
		TOXIC, //this would be interesting, what if we made it so that if laythe was breatheable but toxic, we would need to have it so that you wear a gas mask at all times?
		OXYNEG,
		BREATHEABLE,
		HOT,
		
		
	}

    public static Map<Integer, Set<Hospitality>> idToDimensionMap = new HashMap<>();
    
    static {
        idToDimensionMap.put(DimensionManager.getProviderType(SpaceConfig.eveDimension), EnumSet.of(Hospitality.TOXIC, Hospitality.HOT));
        idToDimensionMap.put(DimensionManager.getProviderType(SpaceConfig.dresDimension), EnumSet.of(Hospitality.OXYNEG));
        idToDimensionMap.put(DimensionManager.getProviderType(SpaceConfig.moonDimension), EnumSet.of(Hospitality.OXYNEG));
        idToDimensionMap.put(DimensionManager.getProviderType(SpaceConfig.ikeDimension), EnumSet.of(Hospitality.OXYNEG));
        idToDimensionMap.put(DimensionManager.getProviderType(SpaceConfig.mohoDimension), EnumSet.of(Hospitality.OXYNEG, Hospitality.HOT));
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


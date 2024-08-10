package com.hbm.dim;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;

public abstract class BiomeGenBaseCelestial extends BiomeGenBase {

    protected ArrayList<SpawnListEntry> creatures = new ArrayList<SpawnListEntry>();
    protected ArrayList<SpawnListEntry> monsters = new ArrayList<SpawnListEntry>();
    protected ArrayList<SpawnListEntry> waterCreatures = new ArrayList<SpawnListEntry>();
    protected ArrayList<SpawnListEntry> caveCreatures = new ArrayList<SpawnListEntry>();

    public BiomeGenBaseCelestial(int id) {
        super(id);
    }

    // Returns a copy of the lists to prevent them being modified
    @SuppressWarnings("rawtypes")
    @Override
    public List getSpawnableList(EnumCreatureType type) {
        switch(type) {
        case monster: return (List)monsters.clone();
        case creature: return (List)creatures.clone();
        case waterCreature: return (List)waterCreatures.clone();
        case ambient: return (List)caveCreatures.clone();
        default: return new ArrayList<SpawnListEntry>();
        }
    }
    
}

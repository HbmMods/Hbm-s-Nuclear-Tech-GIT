package com.hbm.dim;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;

public abstract class BiomeGenBaseCelestial extends BiomeGenBase {

    protected List<SpawnListEntry> creatures = new ArrayList<SpawnListEntry>();
    protected List<SpawnListEntry> monsters = new ArrayList<SpawnListEntry>();
    protected List<SpawnListEntry> waterCreatures = new ArrayList<SpawnListEntry>();
    protected List<SpawnListEntry> caveCreatures = new ArrayList<SpawnListEntry>();

    public BiomeGenBaseCelestial(int id) {
        super(id);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List getSpawnableList(EnumCreatureType type) {
        switch(type) {
        case monster: return monsters;
        case creature: return creatures;
        case waterCreature: return waterCreatures;
        case ambient: return caveCreatures;
        default: return new ArrayList<SpawnListEntry>();
        }
    }
    
}

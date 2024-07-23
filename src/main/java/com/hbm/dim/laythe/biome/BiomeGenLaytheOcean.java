package com.hbm.dim.laythe.biome;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.mob.EntityScutterfish;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenLaytheOcean extends BiomeGenBase {
	
    public static final BiomeGenBase.Height height = new BiomeGenBase.Height(-0.6F, 0.01F);

    //TODO: avoid doing an extra planets and make each planet unique and cool.
	public BiomeGenLaytheOcean(int id) {
		super(id);
		this.setBiomeName("Sagan Sea");
		this.setDisableRain();
		this.waterColorMultiplier=0x5b009a;
		
        this.spawnableCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableWaterCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityScutterfish.class, 10, 4, 4));

        this.theBiomeDecorator.generateLakes = false;
        
        this.setHeight(height);
        
        this.topBlock = ModBlocks.laythe_silt;
        this.fillerBlock = ModBlocks.laythe_silt;
	}
}

	
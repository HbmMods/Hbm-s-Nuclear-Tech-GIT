package com.hbm.dim.minmus.biome;
import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenMinmusHills extends BiomeGenBase {
	
    public static final BiomeGenBase.Height height = new BiomeGenBase.Height(0.325F, 0.08F);

	public BiomeGenMinmusHills(int id) {
		super(id);
		this.setBiomeName("Minmus Hills");
		this.setDisableRain();
		
        this.spawnableCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        
        this.theBiomeDecorator.generateLakes = false;
        
        this.setHeight(height);
        
        this.topBlock = Blocks.snow;
        this.fillerBlock = Blocks.snow; //thiccer regolith due to uhhhhhh...................
	}
	
}
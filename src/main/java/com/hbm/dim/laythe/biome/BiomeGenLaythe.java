package com.hbm.dim.laythe.biome;
import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

public class BiomeGenLaythe extends BiomeGenBase {
	
    public static final BiomeGenBase.Height height = new BiomeGenBase.Height(0.125F, 0.05F);

    //TODO: avoid doing an extra planets and make each planet unique and cool.
	public BiomeGenLaythe(int id) {
		super(id);
		this.setBiomeName("Laythe Islands");
		//this.setDisableRain();
		this.waterColorMultiplier=0x5b009a;
		
        this.spawnableCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        
        this.theBiomeDecorator.generateLakes = false;
        
        this.setHeight(height);
        
        this.topBlock = ModBlocks.laythe_silt;
        this.fillerBlock = ModBlocks.laythe_silt;
       // BiomeDictionary.registerBiomeType(this, BiomeDictionary.Type.COLD, BiomeDictionary.Type.WET, BiomeDictionary.Type.DENSE, BiomeDictionary.Type.SPOOKY);
	}
}
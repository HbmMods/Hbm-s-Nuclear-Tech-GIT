/*******************************************************************************
 * Copyright 2015 SteveKunG - More Planets Mod
 *
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 ******************************************************************************/

package com.hbm.dim.duna;

import java.util.Random;

import com.hbm.config.WorldConfig;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenBaseDuna extends BiomeGenBase
{
    public static final BiomeGenBase dunaPlains = new BiomeGenDuna(WorldConfig.dunaBiome).setTemperatureRainfall(-1.0F, 0.0F);
    public static final BiomeGenBase dunaLowlands = new BiomeGenDunaLowlands(WorldConfig.dunaLowlandsBiome).setTemperatureRainfall(-1.0F, 0.0F);
    public static final BiomeGenBase dunaPolar = new BiomeGenDunaPolar(WorldConfig.dunaPolarBiome).setTemperatureRainfall(-1.0F, 0.0F);
    public static final BiomeGenBase dunaRiver = new BiomeGenDunaRiver(WorldConfig.dunaRiverBiome).setTemperatureRainfall(-1.0F, 0.0F);
    
    public BiomeGenBaseDuna(int id)
    {
        super(id);
//        this.spawnableMonsterList.add(new SpawnListEntry(EntityEvolvedZombie.class, 100, 4, 4));
//        this.spawnableMonsterList.add(new SpawnListEntry(EntityEvolvedSpider.class, 100, 4, 4));
//        this.spawnableMonsterList.add(new SpawnListEntry(EntityEvolvedSkeleton.class, 100, 4, 4));
//        this.spawnableMonsterList.add(new SpawnListEntry(EntityEvolvedCreeper.class, 100, 4, 4));
//        this.spawnableMonsterList.add(new SpawnListEntry(EntityEvolvedEnderman.class, 10, 1, 4));
//        this.spawnableMonsterList.add(new SpawnListEntry(EntityEvolvedWitch.class, 5, 1, 1));
    }

    /*@Override
    public void genTerrainBlocks(World world, Random rand, Block[] block, byte[] meta, int x, int z, double stoneNoise)
    {
        this.genPlutoBiomeTerrain(world, rand, block, meta, x, z, stoneNoise);
    }
    

    /*public void genPlutoBiomeTerrain(World world, Random rand, Block[] block, byte[] meta, int x, int z, double stoneNoise)
    {
        Block topBlock = this.topBlock;
        byte topMeta = this.topMeta;
        Block fillerBlock = this.fillerBlock;
        byte fillerMeta = this.fillerMeta;
        int currentFillerDepth = -1;
        int maxFillerDepth = (int)(stoneNoise / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
        int maskX = x & 15;
        int maskZ = z & 15;
        int worldHeight = block.length / 256;
        int seaLevel = 63;

        for (int y = 255; y >= 0; y--)
        {
            int index = (maskZ * 16 + maskX) * worldHeight + y;

            if (y <= 0 + rand.nextInt(5))
            {
                block[index] = Blocks.bedrock;
            }
            else
            {
                Block currentBlock = block[index];

                if (currentBlock != null && currentBlock.getMaterial() != Material.air)
                {
                    if (currentBlock == PlutoBlocks.pluto_block)
                    {
                        if (this.stoneBlock != null)
                        {
                            block[index] = this.stoneBlock;
                            meta[index] = this.stoneMeta;
                        }
                        if (currentFillerDepth == -1)
                        {
                            if (maxFillerDepth <= 0)
                            {
                                topBlock = null;
                                topMeta = 0;
                                fillerBlock = PlutoBlocks.pluto_block;
                                fillerMeta = 0;
                            }
                            else if (y >= seaLevel - 5 && y <= seaLevel)
                            {
                                topBlock = this.topBlock;
                                topMeta = this.topMeta;
                                fillerBlock = this.fillerBlock;
                                fillerMeta = 0;
                            }
                            if (y < seaLevel - 1 && (topBlock == null || topBlock.getMaterial() == Material.air))
                            {
                                if (this.getFloatTemperature(x, y, z) < 0.15F)
                                {
                                    topBlock = Blocks.ice;
                                    topMeta = 0;
                                }
                                else
                                {
                                    topBlock = Blocks.bedrock;
                                    topMeta = 0;
                                }
                            }

                            currentFillerDepth = maxFillerDepth;

                            if (y >= seaLevel - 2)
                            {
                                block[index] = topBlock;
                                meta[index] = topMeta;
                            }
                            else if (y < seaLevel - 8 - maxFillerDepth)
                            {
                                topBlock = null;
                                fillerBlock = PlutoBlocks.pluto_block;
                                fillerMeta = 0;
                                block[index] = PlutoBlocks.pluto_block;
                            }
                            else
                            {
                                block[index] = fillerBlock;
                                meta[index] = fillerMeta;
                            }
                        }
                        else if (currentFillerDepth > 0)
                        {
                            currentFillerDepth--;
                            block[index] = fillerBlock;
                            meta[index] = fillerMeta;

                            /*if (currentFillerDepth == 0 && fillerBlock == PlutoBlocks.pluto_block && fillerMeta == 11 && y < 64)
                            {
                                currentFillerDepth = rand.nextInt(8) + Math.max(0, y - (seaLevel - 1));
                                fillerBlock = PlutoBlocks.liquid_nitrogen;
                                fillerMeta = 0;
                            }*/
                      /*  }
                    }
                }
            }
        }
    }*/
}
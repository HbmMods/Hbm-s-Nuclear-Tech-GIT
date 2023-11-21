package com.hbm.dim;

import java.util.Random;

import com.hbm.world.test.MapGenTest;
import com.hbm.blocks.ModBlocks;
import com.hbm.config.SpaceConfig;
import com.hbm.dim.PerlinFestival;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCaves;

public class ExperimentalCaveGenerator extends MapGenBase {
	
	PerlinFestival perlinNoise;
 protected void generateLargeCave(long seed, int chunkX, int chunkZ, Block[] blocks, double x, double y, double z) {
     this.generateCaveNode(seed, chunkX, chunkZ, blocks, x, y, z, 1.0F + this.rand.nextFloat() * 6.0F, 0.0F, 0.0F, -1, -1, 0.5D);
     

 }

 protected void generateCaveNode(long seed, int chunkX, int chunkZ, Block[] blocks, double x, double y, double z, float caveSize, float yaw, float pitch, int currentStep, int totalSteps, double caveSizeIncrease) {
     double centerX = (double)(chunkX * 16 + 8);
     double centerZ = (double)(chunkZ * 16 + 8);
     float f3 = 0.0F;
     float f4 = 0.0F;
     Random random = new Random(seed);

     if (totalSteps <= 0) {
         int j1 = this.range * 16 - 16;
         totalSteps = j1 - random.nextInt(j1 / 4);
     }

     boolean flag2 = false;

     if (currentStep == -1) {
         currentStep = totalSteps / 2;
         flag2 = true;
     }

     int k1 = random.nextInt(totalSteps / 2) + totalSteps / 4;

     for (boolean flag = random.nextInt(6) == 0; currentStep < totalSteps; ++currentStep) {
    	    double roomWidth = 1.5D + (Math.sin(Math.PI / 2F) * caveSize); // Example conversion
    	    double roomHeight = roomWidth * caveSizeIncrease;
         double d6 = 1.5D + (double)(MathHelper.sin((float)currentStep * (float)Math.PI / (float)totalSteps) * caveSize * 1.0F);
         double d7 = d6 * caveSizeIncrease;
         float f5 = MathHelper.cos(pitch);
         float f6 = MathHelper.sin(pitch);
         x += (double)(MathHelper.cos(yaw) * f5);
         y += (double)f6;
         z += (double)(MathHelper.sin(yaw) * f5);
         final int depthThreshold = 30;
         this.perlinNoise = new PerlinFestival(seed);
         
         double noisefactor1 = perlinNoise.noise(x * d6, y * d7, z * d6);
         double noisefactor2 = perlinNoise.noise(x * d7 + 2, y * d7 + 2, z * d7 + 2);
         double dynamicScale = 3 + Math.sin(currentStep * 4);
         double noiseFactor = perlinNoise.noise(x * dynamicScale, y * dynamicScale, z * dynamicScale);
         if (y < depthThreshold) {
        	    double noiseFactorWidth = perlinNoise.noise(x * 0.1, y * 0.1, z * 0.1);
        	    double noiseFactorHeight = perlinNoise.noise(x * 0.1, y * 0.2, z * 0.1);

        	    d6 *= 3 + noiseFactorWidth;
        	    d7 *= 2 + noiseFactorHeight;
        	    pitch += noiseFactor * MathHelper.sin((float) (random.nextFloat() * Math.PI));
        	    yaw += noiseFactor * MathHelper.sin((float) (random.nextInt() * Math.PI));

        	}

         
         if (flag) {
             pitch *= 0.92F;
         } else {
             pitch *= 0.7F;
         }

         pitch += f4 * 0.1F;
         yaw += f3 * 0.1F;
         f4 *= 0.9F;
         f3 *= 0.75F;
         f4 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
         f3 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;

         if (!flag2 && currentStep == k1 && caveSize > 1.0F && totalSteps > 0) {
             this.generateCaveNode(random.nextLong(), chunkX, chunkZ, blocks, x, y, z, random.nextFloat() * 0.5F + 0.5F, yaw - ((float)Math.PI / 2F), pitch / 3.0F, currentStep, totalSteps, 1.0D);
             this.generateCaveNode(random.nextLong(), chunkX, chunkZ, blocks, x, y, z, random.nextFloat() * 0.5F + 0.5F, yaw + ((float)Math.PI / 2F), pitch / 3.0F, currentStep, totalSteps, 1.0D);
     	    return;             
         }


         if (flag2 || random.nextInt(4) != 0) {
             double d8 = x - centerX;
             double d9 = z - centerZ;
             double d10 = (double)(totalSteps - currentStep);
             double d11 = (double)(caveSize + 2.0F + 16.0F);

             if (d8 * d8 + d9 * d9 - d10 * d10 > d11 * d11) {
                 return;
             }

             if (x >= centerX - 16.0D - d6 * 2.0D && z >= centerZ - 16.0D - d6 * 2.0D && x <= centerX + 16.0D + d6 * 2.0D && z <= centerZ + 16.0D + d6 * 2.0D) {
                 int i4 = MathHelper.floor_double(x - d6) - chunkX * 16 - 1;
                 int l1 = MathHelper.floor_double(x + d6) - chunkX * 16 + 1;
                 int j4 = MathHelper.floor_double(y - d7) - 1;
                 int i2 = MathHelper.floor_double(y + d7) + 1;
                 int k4 = MathHelper.floor_double(z - d6) - chunkZ * 16 - 1;
                 int j2 = MathHelper.floor_double(z + d6) - chunkZ * 16 + 1;

                 if (i4 < 0) {
                     i4 = 0;
                 }

                 if (l1 > 16) {
                     l1 = 16;
                 }

                 if (j4 < 1) {
                     j4 = 1;
                 }

                 if (i2 > 248) {
                     i2 = 248;
                 }

                 if (k4 < 0) {
                     k4 = 0;
                 }

                 if (j2 > 16) {
                     j2 = 16;
                 }

                 boolean flag3 = false;
                 int k2;
                 int j3;

                 for (k2 = i4; !flag3 && k2 < l1; ++k2) {
                     for (int l2 = k4; !flag3 && l2 < j2; ++l2) {
                         for (int i3 = i2 + 1; !flag3 && i3 >= j4 - 1; --i3) {
                             j3 = (k2 * 16 + l2) * 256 + i3;

                             if (i3 >= 0 && i3 < 256) {
                                 Block block = blocks[j3];

                                 if (isOceanBlock(blocks, j3, k2, i3, l2, chunkX, chunkZ)) {
                                     flag3 = true;
                                 }

                                 if (i3 != j4 - 1 && k2 != i4 && k2 != l1 - 1 && l2 != k4 && l2 != j2 - 1) {
                                     i3 = j4;
                                 }
                             }
                         }
                     }
                 }

                 if (!flag3) {
                	 double noiseScaleX = 0.05; 
                	 double noiseScaleZ = 0.05; 
                	 double noiseMagnitude = 0.3;
                	 for (k2 = i4; k2 < l1; ++k2) {
                		    double noiseX = perlinNoise.noise((k2 + chunkX * 16) * noiseScaleX, y * noiseScaleX, z * noiseScaleX) * noiseMagnitude;
                		    double d13 = (((double)(k2 + chunkX * 16) + 0.5D - x) / d6) + noiseX;

                		    for (j3 = k4; j3 < j2; ++j3) {
                		        double noiseZ = perlinNoise.noise(x * noiseScaleZ, y * noiseScaleZ, (j3 + chunkZ * 16) * noiseScaleZ) * noiseMagnitude;
                		        double d14 = (((double)(j3 + chunkZ * 16) + 0.5D - z) / d6) + noiseZ;

                             int k3 = (k2 * 16 + j3) * 256 + i2;
                             boolean flag1 = false;

                             if (d13 * d13 + d14 * d14 < 1.0D) {
                                 for (int l3 = i2 - 1; l3 >= j4; --l3) {
                                     double d12 = ((double)l3 + 0.5D - y) / d7;
                                     
                                     if (d12 > -0.7D && (d13 * d13 + d12 * d12 + d14 * d14) * (1 + noiseFactor) < 1.0D) {
                                       
                                         Block block1 = blocks[k3];
                                         int carvingScale = 3;
                                         int carvingThreshold = 2;

                                         if (isTopBlock(blocks, k3, k2, l3, j3, chunkX, chunkZ)) {
                                             flag1 = true;
                                         }
                                       double carvingNoise = perlinNoise.noise(x * carvingScale, y * carvingScale, z * carvingScale);
                                       double noiseValue = perlinNoise.noise(x * caveSizeIncrease, y * caveSizeIncrease, z * caveSizeIncrease);
                                       if ((d13 * d13 + d12 * d12 + d14 * d14) * (1 + noiseValue) < 1.0D + carvingNoise) {

                                        	 if (Math.pow(d13, 2.5) + Math.pow(d14, 2.2) < 2) {
                                            digBlock(blocks, k3, k2, l3, j3, chunkX, chunkZ, flag1);
                                         }
                                        }
                                         digBlock(blocks, k3, k2, l3, j3, chunkX, chunkZ, flag1);
                                     
                                     }

                                     --k3;
                                 }
                             }
                         }
                     }

                     if (flag2) {
                         break;
                     }
                 }
             }
         }
     }
 }



	protected void func_151538_a(World p_151538_1_, int p_151538_2_, int p_151538_3_, int p_151538_4_, int p_151538_5_, Block[] p_151538_6_)
    {
        int i1 = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(15) + 1) + 1);

        if (this.rand.nextInt(7) != 0)
        {
            i1 = 0;
        }

        for (int j1 = 0; j1 < i1; ++j1)
        {
            double d0 = (double)(p_151538_2_ * 16 + this.rand.nextInt(16));
            double d1 = (double)this.rand.nextInt(this.rand.nextInt(120) + 8);
            double d2 = (double)(p_151538_3_ * 16 + this.rand.nextInt(16));
            int k1 = 1;

            if (this.rand.nextInt(4) == 0)
            {
                this.generateLargeCave(this.rand.nextLong(), p_151538_4_, p_151538_5_, p_151538_6_, d0, d1, d2);
                k1 += this.rand.nextInt(4);
            }

            for (int l1 = 0; l1 < k1; ++l1)
            {
                float f = this.rand.nextFloat() * (float)Math.PI * 2.0F;
                float f1 = (this.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
                float f2 = this.rand.nextFloat() * 2.0F + this.rand.nextFloat();

                if (this.rand.nextInt(10) == 0)
                {
                    f2 *= this.rand.nextFloat() * this.rand.nextFloat() * 3.0F + 1.0F;
                }

                this.generateCaveNode(this.rand.nextLong(), p_151538_4_, p_151538_5_, p_151538_6_, d0, d1, d2, f2, f, f1, 0, 0, 1.0D);
            }
        }
    }

    protected boolean isOceanBlock(Block[] data, int index, int x, int y, int z, int chunkX, int chunkZ)
    {
        return data[index] == Blocks.flowing_water || data[index] == Blocks.water;
    }

    //Exception biomes to make sure we generate like vanilla
    private boolean isExceptionBiome(BiomeGenBase biome)
    {
        if (biome == BiomeGenBase.mushroomIsland) return true;
        if (biome == BiomeGenBase.beach) return true;
        if (biome == BiomeGenBase.desert) return true;
        return false;
    }

    //Determine if the block at the specified location is the top block for the biome, we take into account
    //Vanilla bugs to make sure that we generate the map the same way vanilla does.
    private boolean isTopBlock(Block[] data, int index, int x, int y, int z, int chunkX, int chunkZ)
    {
        BiomeGenBase biome = worldObj.getBiomeGenForCoords(x + chunkX * 16, z + chunkZ * 16);
        return (isExceptionBiome(biome) ? data[index] == Blocks.grass : data[index] == biome.topBlock);
    }

    /**
     * Digs out the current block, default implementation removes stone, filler, and top block
     * Sets the block to lava if y is less then 10, and air other wise.
     * If setting to air, it also checks to see if we've broken the surface and if so 
     * tries to make the floor the biome's top block
     * 
     * @param data Block data array
     * @param index Pre-calculated index into block data
     * @param x local X position
     * @param y local Y position
     * @param z local Z position
     * @param chunkX Chunk X position
     * @param chunkZ Chunk Y position
     * @param foundTop True if we've encountered the biome's top block. Ideally if we've broken the surface.
     */
    protected void digBlock(Block[] data, int index, int x, int y, int z, int chunkX, int chunkZ, boolean foundTop)
    {
        BiomeGenBase biome = worldObj.getBiomeGenForCoords(x + chunkX * 16, z + chunkZ * 16);
        Block top    = (isExceptionBiome(biome) ? Blocks.grass : biome.topBlock);
        Block filler = (isExceptionBiome(biome) ? Blocks.dirt  : biome.fillerBlock);
        Block block  = data[index];

        if (block == Blocks.stone || block == filler || block == top)
        {
            if (y < 10)
            {
                data[index] = Blocks.lava;
            }
            else
            {
                data[index] = null;

                if (foundTop && data[index - 1] == filler)
                {
                    data[index - 1] = top;
                }
            }
        }
    }
	}
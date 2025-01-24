package com.hbm.world.feature;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenNTMMineable extends WorldGenerator {
    private Block ore;
    /** The number of blocks to generate. */
    private int numberOfBlocks;
    private Block blockToReplace;
    private int mineableBlockMeta;
    private int targetBlockMeta;

    public WorldGenNTMMineable(Block ore, int number)
    {
        this(ore, number, Blocks.stone);
    }

    public WorldGenNTMMineable(Block ore, int number, Block blockToReplace)
    {
        this.ore = ore;
        this.numberOfBlocks = number;
        this.blockToReplace = blockToReplace;
    }

    public WorldGenNTMMineable(Block block, int meta, int number, Block target)
    {
        this(block, number, target);
        this.mineableBlockMeta = meta;
    }

    /**Only change from the original WorldGenMinable class, support for the target block having meta**/
    public WorldGenNTMMineable(Block block, int meta, int number, Block target, int targetMeta)
    {
        this(block, number, target);
        this.mineableBlockMeta = meta;
        this.targetBlockMeta = targetMeta;
    }

    public boolean generate(World world, Random rand, int x, int y, int z)
    {
        float f = rand.nextFloat() * (float)Math.PI;
        double d0 = (float)(x + 8) + MathHelper.sin(f) * (float)this.numberOfBlocks / 8.0F;
        double d1 = (float)(x + 8) - MathHelper.sin(f) * (float)this.numberOfBlocks / 8.0F;
        double d2 = (float)(z + 8) + MathHelper.cos(f) * (float)this.numberOfBlocks / 8.0F;
        double d3 = (float)(z + 8) - MathHelper.cos(f) * (float)this.numberOfBlocks / 8.0F;
        double d4 = y + rand.nextInt(3) - 2;
        double d5 = y + rand.nextInt(3) - 2;

        for (int l = 0; l <= this.numberOfBlocks; ++l)
        {
            double d6 = d0 + (d1 - d0) * (double)l / (double)this.numberOfBlocks;
            double d7 = d4 + (d5 - d4) * (double)l / (double)this.numberOfBlocks;
            double d8 = d2 + (d3 - d2) * (double)l / (double)this.numberOfBlocks;
            double d9 = rand.nextDouble() * (double)this.numberOfBlocks / 16.0D;
            double d10 = (double)(MathHelper.sin((float)l * (float)Math.PI / (float)this.numberOfBlocks) + 1.0F) * d9 + 1.0D;
            double d11 = (double)(MathHelper.sin((float)l * (float)Math.PI / (float)this.numberOfBlocks) + 1.0F) * d9 + 1.0D;
            int i1 = MathHelper.floor_double(d6 - d10 / 2.0D);
            int j1 = MathHelper.floor_double(d7 - d11 / 2.0D);
            int k1 = MathHelper.floor_double(d8 - d10 / 2.0D);
            int l1 = MathHelper.floor_double(d6 + d10 / 2.0D);
            int i2 = MathHelper.floor_double(d7 + d11 / 2.0D);
            int j2 = MathHelper.floor_double(d8 + d10 / 2.0D);

            for (int k2 = i1; k2 <= l1; ++k2)
            {
                double d12 = ((double)k2 + 0.5D - d6) / (d10 / 2.0D);

                if (d12 * d12 < 1.0D)
                {
                    for (int l2 = j1; l2 <= i2; ++l2)
                    {
                        double d13 = ((double)l2 + 0.5D - d7) / (d11 / 2.0D);

                        if (d12 * d12 + d13 * d13 < 1.0D)
                        {
                            for (int i3 = k1; i3 <= j2; ++i3)
                            {
                                double d14 = ((double)i3 + 0.5D - d8) / (d10 / 2.0D);
                                boolean blockMatches = blockToReplace.getDamageValue(world,k2, l2, i3) == targetBlockMeta && world.getBlock(k2, l2, i3).isReplaceableOreGen(world, k2, l2, i3, blockToReplace);
                                if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D && blockMatches)
                                {
                                    world.setBlock(k2, l2, i3, this.ore, mineableBlockMeta, 2);
                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
    }
}

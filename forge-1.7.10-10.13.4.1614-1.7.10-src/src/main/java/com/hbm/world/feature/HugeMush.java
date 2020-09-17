package com.hbm.world.feature;

import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class HugeMush extends WorldGenerator
{
	Block Block0 = ModBlocks.mush_block;
	Block Block1 = ModBlocks.mush_block_stem;
	
    public HugeMush()
    {
        super(false);
    }

    @Override
	public boolean generate(World world, Random rand, int x, int y, int z)
    {
    	for(int i = -1; i < 2; i++)
    	{
        	for(int j = -1; j < 2; j++)
        	{
        		world.setBlock(x + i, y, z + j, Block0);
        	}
    	}
    	for(int i = -1; i < 2; i++)
    	{
        	for(int j = -1; j < 2; j++)
        	{
        		world.setBlock(x + i, y + 3, z + j, Block0);
        	}
    	}
    	for(int i = -2; i < 3; i++)
    	{
        	for(int j = -2; j < 3; j++)
        	{
        		world.setBlock(x + i, y + 5, z + j, Block0);
        	}
    	}
    	for(int i = -4; i < 5; i++)
    	{
        	for(int j = -4; j < 5; j++)
        	{
            	for(int k = 0; k < 3; k++)
            	{
            		world.setBlock(x + i, y + 6 + k, z + j, Block0);
            	}
        	}
    	}
    	for(int i = -3; i < 4; i++)
    	{
        	for(int j = -3; j < 4; j++)
        	{
        		world.setBlock(x + i, y + 9, z + j, Block0);
        	}
    	}
    	for(int i = -1; i < 2; i++)
    	{
        	for(int j = -1; j < 2; j++)
        	{
        		world.setBlock(x + i, y + 10, z + j, Block0);
        	}
    	}
    	for(int i = 0; i < 8; i++)
    	{
    		world.setBlock(x, y + i, z, Block1);
    	}
    	return true;
    }
}

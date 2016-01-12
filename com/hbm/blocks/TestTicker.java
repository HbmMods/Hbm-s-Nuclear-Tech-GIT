package com.hbm.blocks;

import java.util.Random;

import com.hbm.particles.NukeSmokeFX;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class TestTicker extends Block {

	protected TestTicker(Material p_i45394_1_) {
		super(p_i45394_1_);
        this.setTickRandomly(true);
	}

    public void updateTick(World world, int x, int y, int z, Random rand) {
    	
    	{
    		if(world.getBlock(x + 1, y, z) != ModBlocks.test_ticker) {
    			world.setBlock(x + 1, y, z, ModBlocks.test_ticker);
    		}
    	
    		if(world.getBlock(x, y + 1, z) != ModBlocks.test_ticker) {
    			world.setBlock(x, y + 1, z, ModBlocks.test_ticker);
    		}
    	
    		if(world.getBlock(x, y, z + 1) != ModBlocks.test_ticker) {
    			world.setBlock(x, y, z + 1, ModBlocks.test_ticker);
    		}
    	
    		if(world.getBlock(x - 1, y, z) != ModBlocks.test_ticker) {
    			world.setBlock(x - 1, y, z, ModBlocks.test_ticker);
    		}
    	
    		if(world.getBlock(x, y - 1, z) != ModBlocks.test_ticker) {
    			world.setBlock(x, y - 1, z, ModBlocks.test_ticker);
    		}
    	
    		if(world.getBlock(x, y, z - 1) != ModBlocks.test_ticker) {
    			world.setBlock(x, y, z - 1, ModBlocks.test_ticker);
    		}
    	}
    }

}

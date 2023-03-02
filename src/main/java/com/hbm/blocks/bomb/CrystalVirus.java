package com.hbm.blocks.bomb;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class CrystalVirus extends Block {
	
	public CrystalVirus(Material p_i45394_1_) {
		super(p_i45394_1_);
		this.setTickRandomly(true);
	}

    @Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
    	
    	if(GeneralConfig.enableVirus) {
    		if(world.getBlock(x + 1, y, z) != ModBlocks.crystal_virus && world.getBlock(x + 1, y, z) != Blocks.air && world.getBlock(x + 1, y, z) != ModBlocks.crystal_hardened && world.getBlock(x + 1, y, z) != ModBlocks.crystal_pulsar) {
    			world.setBlock(x + 1, y, z, ModBlocks.crystal_virus);
    		}
    	
    		if(world.getBlock(x, y + 1, z) != ModBlocks.crystal_virus && world.getBlock(x, y + 1, z) != Blocks.air && world.getBlock(x, y + 1, z) != ModBlocks.crystal_hardened && world.getBlock(x, y + 1, z) != ModBlocks.crystal_pulsar) {
    			world.setBlock(x, y + 1, z, ModBlocks.crystal_virus);
    		}
    	
    		if(world.getBlock(x, y, z + 1) != ModBlocks.crystal_virus && world.getBlock(x, y, z + 1) != Blocks.air && world.getBlock(x, y, z + 1) != ModBlocks.crystal_hardened && world.getBlock(x, y, z + 1) != ModBlocks.crystal_pulsar) {
    			world.setBlock(x, y, z + 1, ModBlocks.crystal_virus);
    		}
    	
    		if(world.getBlock(x - 1, y, z) != ModBlocks.crystal_virus && world.getBlock(x - 1, y, z) != Blocks.air && world.getBlock(x - 1, y, z) != ModBlocks.crystal_hardened && world.getBlock(x - 1, y, z) != ModBlocks.crystal_pulsar) {
    			world.setBlock(x - 1, y, z, ModBlocks.crystal_virus);
    		}
    	
    		if(world.getBlock(x, y - 1, z) != ModBlocks.crystal_virus && world.getBlock(x, y - 1, z) != Blocks.air && world.getBlock(x, y - 1, z) != ModBlocks.crystal_hardened && world.getBlock(x, y - 1, z) != ModBlocks.crystal_pulsar) {
    			world.setBlock(x, y - 1, z, ModBlocks.crystal_virus);
    		}
    	
    		if(world.getBlock(x, y, z - 1) != ModBlocks.crystal_virus && world.getBlock(x, y, z - 1) != Blocks.air && world.getBlock(x, y, z - 1) != ModBlocks.crystal_hardened && world.getBlock(x, y, z - 1) != ModBlocks.crystal_pulsar) {
    			world.setBlock(x, y, z - 1, ModBlocks.crystal_virus);
    		}
			world.setBlock(x, y, z, ModBlocks.crystal_hardened);
    	}
    }
    
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
    	
    	if((world.getBlock(x + 1, y, z) == Blocks.air || world.getBlock(x + 1, y, z) == ModBlocks.crystal_virus || world.getBlock(x + 1, y, z) == ModBlocks.crystal_hardened || world.getBlock(x + 1, y, z) == ModBlocks.crystal_pulsar) && 
    			(world.getBlock(x - 1, y, z) == Blocks.air || world.getBlock(x - 1, y, z) == ModBlocks.crystal_virus || world.getBlock(x - 1, y, z) == ModBlocks.crystal_hardened || world.getBlock(x - 1, y, z) == ModBlocks.crystal_pulsar) && 
    			(world.getBlock(x, y + 1, z) == Blocks.air || world.getBlock(x, y + 1, z) == ModBlocks.crystal_virus || world.getBlock(x, y + 1, z) == ModBlocks.crystal_hardened || world.getBlock(x, y + 1, z) == ModBlocks.crystal_pulsar) && 
    			(world.getBlock(x, y - 1, z) == Blocks.air || world.getBlock(x, y - 1, z) == ModBlocks.crystal_virus || world.getBlock(x, y - 1, z) == ModBlocks.crystal_hardened || world.getBlock(x, y - 1, z) == ModBlocks.crystal_pulsar) && 
    			(world.getBlock(x, y, z + 1) == Blocks.air || world.getBlock(x, y, z + 1) == ModBlocks.crystal_virus || world.getBlock(x, y, z + 1) == ModBlocks.crystal_hardened || world.getBlock(x, y, z + 1) == ModBlocks.crystal_pulsar) && 
    			(world.getBlock(x, y, z - 1) == Blocks.air || world.getBlock(x, y, z - 1) == ModBlocks.crystal_virus || world.getBlock(x, y, z - 1) == ModBlocks.crystal_hardened || world.getBlock(x, y, z - 1) == ModBlocks.crystal_pulsar) && 
    			!world.isRemote) {
			world.setBlock(x, y, z, ModBlocks.crystal_hardened);
    	}
    }

}

package com.hbm.blocks.bomb;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class CheaterVirusSeed extends Block {

	public CheaterVirusSeed(Material p_i45394_1_) {
		
		super(p_i45394_1_);
		this.setTickRandomly(true);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int i) {
		
		super.breakBlock(world, x, y, z, block, i);
		
		if(!GeneralConfig.enableVirus)
			return;

    	if((world.getBlock(x + 1, y, z) == Blocks.air || world.getBlock(x + 1, y, z) == ModBlocks.cheater_virus || world.getBlock(x + 1, y, z) == ModBlocks.cheater_virus_seed) && 
    			(world.getBlock(x - 1, y, z) == Blocks.air || world.getBlock(x - 1, y, z) == ModBlocks.cheater_virus || world.getBlock(x - 1, y, z) == ModBlocks.cheater_virus_seed) && 
    			(world.getBlock(x, y + 1, z) == Blocks.air || world.getBlock(x, y + 1, z) == ModBlocks.cheater_virus || world.getBlock(x, y + 1, z) == ModBlocks.cheater_virus_seed) && 
    			(world.getBlock(x, y - 1, z) == Blocks.air || world.getBlock(x, y - 1, z) == ModBlocks.cheater_virus || world.getBlock(x, y - 1, z) == ModBlocks.cheater_virus_seed) && 
    			(world.getBlock(x, y, z + 1) == Blocks.air || world.getBlock(x, y, z + 1) == ModBlocks.cheater_virus || world.getBlock(x, y, z + 1) == ModBlocks.cheater_virus_seed) && 
    			(world.getBlock(x, y, z - 1) == Blocks.air || world.getBlock(x, y, z - 1) == ModBlocks.cheater_virus || world.getBlock(x, y, z - 1) == ModBlocks.cheater_virus_seed) && 
    			!world.isRemote) {
			world.setBlock(x, y, z, Blocks.air);
    	} else {
    		world.setBlock(x, y, z, ModBlocks.cheater_virus);
    	}
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		
		if(!GeneralConfig.enableVirus)
			return;
    	
    	if((world.getBlock(x + 1, y, z) == Blocks.air || world.getBlock(x + 1, y, z) == ModBlocks.cheater_virus || world.getBlock(x + 1, y, z) == ModBlocks.cheater_virus_seed) && 
    			(world.getBlock(x - 1, y, z) == Blocks.air || world.getBlock(x - 1, y, z) == ModBlocks.cheater_virus || world.getBlock(x - 1, y, z) == ModBlocks.cheater_virus_seed) && 
    			(world.getBlock(x, y + 1, z) == Blocks.air || world.getBlock(x, y + 1, z) == ModBlocks.cheater_virus || world.getBlock(x, y + 1, z) == ModBlocks.cheater_virus_seed) && 
    			(world.getBlock(x, y - 1, z) == Blocks.air || world.getBlock(x, y - 1, z) == ModBlocks.cheater_virus || world.getBlock(x, y - 1, z) == ModBlocks.cheater_virus_seed) && 
    			(world.getBlock(x, y, z + 1) == Blocks.air || world.getBlock(x, y, z + 1) == ModBlocks.cheater_virus || world.getBlock(x, y, z + 1) == ModBlocks.cheater_virus_seed) && 
    			(world.getBlock(x, y, z - 1) == Blocks.air || world.getBlock(x, y, z - 1) == ModBlocks.cheater_virus || world.getBlock(x, y, z - 1) == ModBlocks.cheater_virus_seed) && 
    			!world.isRemote) {
			world.setBlock(x, y, z, Blocks.air);
    	} else {
    		world.setBlock(x, y, z, ModBlocks.cheater_virus);
    	}
	}
    
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
    	
    	if((world.getBlock(x + 1, y, z) == Blocks.air || world.getBlock(x + 1, y, z) == ModBlocks.cheater_virus || world.getBlock(x + 1, y, z) == ModBlocks.cheater_virus_seed) && 
    			(world.getBlock(x - 1, y, z) == Blocks.air || world.getBlock(x - 1, y, z) == ModBlocks.cheater_virus || world.getBlock(x - 1, y, z) == ModBlocks.cheater_virus_seed) && 
    			(world.getBlock(x, y + 1, z) == Blocks.air || world.getBlock(x, y + 1, z) == ModBlocks.cheater_virus || world.getBlock(x, y + 1, z) == ModBlocks.cheater_virus_seed) && 
    			(world.getBlock(x, y - 1, z) == Blocks.air || world.getBlock(x, y - 1, z) == ModBlocks.cheater_virus || world.getBlock(x, y - 1, z) == ModBlocks.cheater_virus_seed) && 
    			(world.getBlock(x, y, z + 1) == Blocks.air || world.getBlock(x, y, z + 1) == ModBlocks.cheater_virus || world.getBlock(x, y, z + 1) == ModBlocks.cheater_virus_seed) && 
    			(world.getBlock(x, y, z - 1) == Blocks.air || world.getBlock(x, y, z - 1) == ModBlocks.cheater_virus || world.getBlock(x, y, z - 1) == ModBlocks.cheater_virus_seed) && 
    			!world.isRemote) {
			world.setBlock(x, y, z, Blocks.air);
    	}
    }
}

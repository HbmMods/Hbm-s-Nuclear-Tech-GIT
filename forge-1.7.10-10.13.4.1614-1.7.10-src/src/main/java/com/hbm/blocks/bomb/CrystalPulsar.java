package com.hbm.blocks.bomb;

import com.hbm.blocks.ModBlocks;
import com.hbm.explosion.ExplosionChaos;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class CrystalPulsar extends Block {

	public CrystalPulsar(Material p_i45394_1_) {
		super(p_i45394_1_);
	}
    
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
    	
    	if(world.getBlock(x + 1, y, z) == ModBlocks.crystal_virus || 
    			world.getBlock(x - 1, y, z) == ModBlocks.crystal_virus || 
    			world.getBlock(x, y + 1, z) == ModBlocks.crystal_virus || 
    			world.getBlock(x, y - 1, z) == ModBlocks.crystal_virus || 
    			world.getBlock(x, y, z + 1) == ModBlocks.crystal_virus || 
    			world.getBlock(x, y, z - 1) == ModBlocks.crystal_virus || 
    			!world.isRemote) {
			ExplosionChaos.hardenVirus(world, x, y, z, 10);
    	}
    }

}

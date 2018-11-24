package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.saveddata.RadiationSavedData;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class BlockFallingRad extends BlockFalling {

	private float radIn = 0.0F;
	private float radMax = 0.0F;

	public BlockFallingRad(Material mat, float rad, float max) {
		super(mat);
	    this.setTickRandomly(true);
	    radIn = rad;
	    radMax = max;
	}
	
    @Override
	public void updateTick(World world, int x, int y, int z, Random rand)
    {
    	super.updateTick(world, x, y, z, rand);
        
        RadiationSavedData.incrementRad(world, x, z, radIn, radMax);

        world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
    }
    
    @Override
    public int tickRate(World world) {
    	
    	return 20;
    }
    
    public void onBlockAdded(World world, int x, int y, int z)
    {
        super.onBlockAdded(world, x, y, z);
        world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
    }

}

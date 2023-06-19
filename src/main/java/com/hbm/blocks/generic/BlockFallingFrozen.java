package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.handler.RogueWorldHandler;
import com.hbm.main.ModEventHandlerRogue;
import com.hbm.saveddata.RogueWorldSaveData;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockFire;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class BlockFallingFrozen extends BlockFalling {

	public BlockFallingFrozen(Material mat) {
		super(mat);
	    this.setTickRandomly(true);
	}
	
    @Override
	public void updateTick(World world, int x, int y, int z, Random rand)
    {
    	super.updateTick(world, x, y, z, rand);
    	RogueWorldSaveData data = RogueWorldSaveData.forWorld(world);
		float temp = ModEventHandlerRogue.getTemperatureAtDepth(y, world);
		if(temp <0)
		{
			for(int i = -1; i < 2; i++) {
				for(int j = -1; j < 2; j++) {
					for(int k = -1; k < 2; k++) {
						RogueWorldHandler.freeze(world, x+i, y+j, z+k, temp);
					}
				}
			}
		}
    }
		/*
		for(int i = -1; i < 2; i++) {
			for(int j = -1; j < 2; j++) {
				for(int k = -1; k < 2; k++) {
					Block b0 = world.getBlock(x + i, y + j, z + k);
					if(b0 instanceof BlockFire || b0.getMaterial()==Material.lava);
					{
						world.setBlock(x, y, z, Blocks.gravel);
					}
				}
			}
		}
    }
    */
    
    @Override
    public int tickRate(World world) {
    	
    	return 20;
    }
    
	@Override
	public Item getItemDropped(int i, Random rand, int j)
    {
		return Items.snowball;
    }
    
    public void onBlockAdded(World world, int x, int y, int z)
    {
        super.onBlockAdded(world, x, y, z);
        world.scheduleBlockUpdate(x, y, z, this, 1);
    }

}

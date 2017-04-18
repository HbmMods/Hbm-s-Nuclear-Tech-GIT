package com.hbm.blocks.test;

import com.hbm.entity.missile.EntityTestMissile;
import com.hbm.tileentity.TileEntityTestNuke;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class TestMissile extends Block {

	public TestMissile(Material p_i45394_1_) {
		super(p_i45394_1_);
	}
	
    @Override
	public void onNeighborBlockChange(World p_149695_1_, int x, int y, int z, Block p_149695_5_)
    {
    	TileEntityTestNuke entity = (TileEntityTestNuke) p_149695_1_.getTileEntity(x, y, z);
        if (p_149695_1_.isBlockIndirectlyGettingPowered(x, y, z))
        {
        	EntityTestMissile missile = new EntityTestMissile(p_149695_1_, x + 150, z + 150, x + 0.5F, y + 2F, z + 0.5F);
        	p_149695_1_.spawnEntityInWorld(missile);
        }
    }

}

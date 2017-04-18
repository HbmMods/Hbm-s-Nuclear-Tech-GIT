package com.hbm.blocks.bomb;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.interfaces.IBomb;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class DetCord extends Block implements IBomb {

	public DetCord(Material p_i45394_1_) {
		super(p_i45394_1_);
	}

    @Override
	public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion p_149723_5_)
    {
        this.explode(world, x, y, z);
    }

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block p_149695_5_)
    {
        if (world.isBlockIndirectlyGettingPowered(x, y, z))
        {
        	this.explode(world, x, y, z);
        }
    }

    @Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return null;
    }

	public void explode(World world, int x, int y, int z) {
		if(!world.isRemote) {
			
			world.setBlock(x, y, z, Blocks.air);
			if(this == ModBlocks.det_cord)
				world.createExplosion(null, x + 0.5, y + 0.5, z + 0.5, 1.5F, true);
			if(this == ModBlocks.det_charge)
				ExplosionLarge.explode(world, x, y, z, 15, true, false, false);
		}
	}

}

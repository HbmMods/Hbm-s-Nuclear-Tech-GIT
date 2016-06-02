package com.hbm.blocks;

import java.util.Random;

import com.hbm.explosion.ExplosionChaos;
import com.hbm.interfaces.IBomb;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class DetCord extends Block implements IBomb {

	protected DetCord(Material p_i45394_1_) {
		super(p_i45394_1_);
	}

    @Override
	public void onBlockDestroyedByExplosion(World p_149723_1_, int p_149723_2_, int p_149723_3_, int p_149723_4_, Explosion p_149723_5_)
    {
        if (!p_149723_1_.isRemote)
        {
        	p_149723_1_.createExplosion(null, p_149723_2_ + 0.5, p_149723_3_ + 0.5, p_149723_4_ + 0.5, 1.5F, true);
        }
    }

	@Override
	public void onNeighborBlockChange(World p_149695_1_, int x, int y, int z, Block p_149695_5_)
    {
        if (p_149695_1_.isBlockIndirectlyGettingPowered(x, y, z))
        {
        	p_149695_1_.createExplosion(null, x + 0.5, y + 0.5, z + 0.5, 1.5F, true);
        }
    }

    @Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return null;
    }

	public void explode(World world, int x, int y, int z) {
		world.createExplosion(null, x + 0.5, y + 0.5, z + 0.5, 1.5F, true);
	}

}

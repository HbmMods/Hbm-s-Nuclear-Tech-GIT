package com.hbm.blocks;

import com.hbm.explosion.ExplosionChaos;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class BombFlameWar extends Block {
	
    public World worldObj;
	
    protected BombFlameWar(Material p_i45394_1_) {
		super(p_i45394_1_);
	}

	public void onNeighborBlockChange(World p_149695_1_, int x, int y, int z, Block p_149695_5_)
    {
    	this.worldObj = p_149695_1_;
        if (p_149695_1_.isBlockIndirectlyGettingPowered(x, y, z))
        {
        	ExplosionChaos.explode(p_149695_1_, x, y, z, 15);
        	ExplosionChaos.spawnExplosion(p_149695_1_, x, y, z, 75);
        	ExplosionChaos.flameDeath(p_149695_1_, x, y, z, 100);
        }
    }

}

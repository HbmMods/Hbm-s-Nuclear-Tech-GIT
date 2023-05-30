package com.hbm.blocks.generic;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class BlockPlasma extends Block {

	public BlockPlasma(Material p_i45394_1_) {
		super(p_i45394_1_);
        this.setTickRandomly(true);
	}
	
    @Override
	public void updateTick(World world, int x, int y, int z, Random rand)
    {
        if (world.getGameRules().getGameRuleBooleanValue("doFireTick"))
        	world.setBlock(x, y, z, Blocks.air);
    }
    
    @Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        return null;
    }
    
    @Override
	public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_)
    {
    	p_149670_5_.attackEntityFrom(DamageSource.inFire, 40);
        p_149670_5_.setFire(10);
        p_149670_5_.setInWeb();
    }
    
    @Override
	public boolean isOpaqueCube()
    {
        return false;
    }

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
		return null;
    }
	
    @Override
	public boolean renderAsNormalBlock()
    {
        return false;
    }

}

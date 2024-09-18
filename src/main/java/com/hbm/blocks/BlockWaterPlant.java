package com.hbm.blocks;

import java.util.Random;

import com.hbm.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockWaterPlant extends Block {

	//once carbon tetrachloride exists, this will be rewritten. i just want my damn plants already ffs...
	protected BlockWaterPlant() {
		super(Material.water);
	}

	
	@Override
	public int getRenderType() {
		return 6;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return null;
	}
    @Override
    public Item getItemDropped(int parMetadata, Random parRand, int parFortune)  
    {
       return (ModItems.saltleaf);
    }
    @Override
    public int quantityDropped(int meta, int fortune, java.util.Random random) {
        return random.nextInt(4); 
    }
}

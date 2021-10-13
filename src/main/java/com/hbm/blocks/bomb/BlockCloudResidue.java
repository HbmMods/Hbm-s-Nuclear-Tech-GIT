package com.hbm.blocks.bomb;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockCloudResidue extends Block {
	
	public BlockCloudResidue(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

    public static int func_150032_b(int p_150032_0_)
    {
        return func_150031_c(p_150032_0_);
    }

    public static int func_150031_c(int p_150031_0_)
    {
        return p_150031_0_ & 15;
    }

    public MapColor getMapColor(int p_149728_1_)
    {
        return MapColor.redColor;
    }
	
	@Override
	public int getRenderType(){
		return ModBlocks.taint.getRenderType();
	}

    @Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return p_149650_2_.nextInt(25) == 0 ? ModItems.powder_cloud : null;
    }
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
    
    public void onNeighborBlockChange(World world, int x, int y, int z, Block b)
    {
    	if(!hasPosNeightbour(world, x, y, z) && !world.isRemote)
			world.setBlockToAir(x, y, z);
    }
    
    public static boolean hasPosNeightbour(World world, int x, int y, int z) {
    	Block b0 = world.getBlock(x + 1, y, z);
    	Block b1 = world.getBlock(x, y + 1, z);
    	Block b2 = world.getBlock(x, y, z + 1);
    	Block b3 = world.getBlock(x - 1, y, z);
    	Block b4 = world.getBlock(x, y - 1, z);
    	Block b5 = world.getBlock(x, y, z - 1);
    	boolean b = (b0.renderAsNormalBlock() && b0.getMaterial().isOpaque()) ||
    			(b1.renderAsNormalBlock() && b1.getMaterial().isOpaque()) ||
    			(b2.renderAsNormalBlock() && b2.getMaterial().isOpaque()) ||
    			(b3.renderAsNormalBlock() && b3.getMaterial().isOpaque()) ||
    			(b4.renderAsNormalBlock() && b4.getMaterial().isOpaque()) ||
    			(b5.renderAsNormalBlock() && b5.getMaterial().isOpaque());
    	return b;
    }
    
    @Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return null;
	}
	
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return AxisAlignedBB.getBoundingBox(par2, par3, par4, par2, par3, par4);
	}
}

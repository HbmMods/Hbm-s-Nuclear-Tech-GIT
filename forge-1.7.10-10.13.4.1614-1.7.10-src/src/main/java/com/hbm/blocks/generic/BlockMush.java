package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.main.MainRegistry;
import com.hbm.world.feature.HugeMush;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockMush extends Block implements IGrowable {

	public BlockMush(Material p_i45394_1_) {
		super(p_i45394_1_);
        float f = 0.2F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
		this.setTickRandomly(true);
	}
	
    protected boolean canPlaceBlockOn(Block p_149854_1_)
    {
        return p_149854_1_.func_149730_j();
    }
    
    @Override
	public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_)
    {
        return super.canPlaceBlockAt(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_) && this.canBlockStay(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_);
    }
    
    @Override
	public boolean canBlockStay(World p_149718_1_, int p_149718_2_, int p_149718_3_, int p_149718_4_)
    {
        if (p_149718_3_ >= 0 && p_149718_3_ < 256)
        {
            Block block = p_149718_1_.getBlock(p_149718_2_, p_149718_3_ - 1, p_149718_4_);
            return block == ModBlocks.waste_earth || block == ModBlocks.waste_mycelium;
        }
        else
        {
            return false;
        }
    }

    public boolean func_149884_c(World p_149884_1_, int p_149884_2_, int p_149884_3_, int p_149884_4_, Random p_149884_5_)
    {
        p_149884_1_.getBlockMetadata(p_149884_2_, p_149884_3_, p_149884_4_);
        p_149884_1_.setBlockToAir(p_149884_2_, p_149884_3_, p_149884_4_);
        HugeMush worldgenbigmushroom = null;

        worldgenbigmushroom = new HugeMush();
        worldgenbigmushroom.generate(p_149884_1_, p_149884_5_, p_149884_2_, p_149884_3_, p_149884_4_);
        
        return true;
    }

    @Override
	public boolean func_149851_a(World p_149851_1_, int p_149851_2_, int p_149851_3_, int p_149851_4_, boolean p_149851_5_)
    {
        return true;
    }

    @Override
	public boolean func_149852_a(World p_149852_1_, Random p_149852_2_, int p_149852_3_, int p_149852_4_, int p_149852_5_)
    {
        return p_149852_2_.nextFloat() < 0.4D;
    }
    @Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        return null;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    @Override
	public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    @Override
	public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * The type of render function that is called for this block
     */
    @Override
	public int getRenderType()
    {
        return 1;
    }
    @Override
	public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
    {
        super.onNeighborBlockChange(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_5_);
        this.checkAndDropBlock(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_);
    }

    /**
     * checks if the block can stay, if not drop as item
     */
    protected void checkAndDropBlock(World p_149855_1_, int p_149855_2_, int p_149855_3_, int p_149855_4_)
    {
        if (!this.canBlockStay(p_149855_1_, p_149855_2_, p_149855_3_, p_149855_4_))
        {
            this.dropBlockAsItem(p_149855_1_, p_149855_2_, p_149855_3_, p_149855_4_, p_149855_1_.getBlockMetadata(p_149855_2_, p_149855_3_, p_149855_4_), 0);
            p_149855_1_.setBlock(p_149855_2_, p_149855_3_, p_149855_4_, getBlockById(0), 0, 2);
        }
    }
    
    @Override
    public void updateTick(World world, int x, int y, int z, Random rand)
    {
        this.checkAndDropBlock(world, x, y, z);
    	if(GeneralConfig.enableMycelium && world.getBlock(x, y - 1, z) == ModBlocks.waste_earth && rand.nextInt(5) == 0)
    	{
    		world.setBlock(x, y - 1, z, ModBlocks.waste_mycelium);
    	}
    }

    @Override
	public void func_149853_b(World p_149853_1_, Random p_149853_2_, int p_149853_3_, int p_149853_4_, int p_149853_5_)
    {
        this.func_149884_c(p_149853_1_, p_149853_3_, p_149853_4_, p_149853_5_, p_149853_2_);
    }

}

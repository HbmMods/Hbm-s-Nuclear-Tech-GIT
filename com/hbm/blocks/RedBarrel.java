package com.hbm.blocks;

import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class RedBarrel extends BlockContainer {

	protected RedBarrel(Material p_i45394_1_) {
		super(p_i45394_1_);
	}

    public void onBlockDestroyedByExplosion(World p_149723_1_, int p_149723_2_, int p_149723_3_, int p_149723_4_, Explosion p_149723_5_)
    {
        if (!p_149723_1_.isRemote)
        {
        	explode(p_149723_1_, p_149723_2_, p_149723_3_, p_149723_4_);
        }
    }

	public void onNeighborBlockChange(World p_149695_1_, int x, int y, int z, Block p_149695_5_)
    {
        if (p_149695_1_.getBlock(x + 1, y, z) == Blocks.fire || p_149695_1_.getBlock(x - 1, y, z) == Blocks.fire || p_149695_1_.getBlock(x, y + 1, z) == Blocks.fire || p_149695_1_.getBlock(x, y - 1, z) == Blocks.fire || p_149695_1_.getBlock(x, y, z + 1) == Blocks.fire || p_149695_1_.getBlock(x, y, z - 1) == Blocks.fire)
        {
        	explode(p_149695_1_, x, y, z);
        }
    }
	
	public void explode(World p_149695_1_, int x, int y, int z) {

		p_149695_1_.newExplosion((Entity)null, (double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), 2.5F, true, true);
	}
	
	public int getRenderType(){
		return -1;
	}
	
	public boolean isOpaqueCube() {
		return false;
	}
	
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityRedBarrel();
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconregister) {
		this.blockIcon = iconregister.registerIcon(RefStrings.MODID + ":red_barrel");
	}
	
	public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
    {
        float f = 0.0625F;
        this.setBlockBounds(2*f, 0.0F, 2*f, 14*f, 1.0F, 14*f);
    }
	
    public boolean canDropFromExplosion(Explosion p_149659_1_)
    {
        return false;
    }

}

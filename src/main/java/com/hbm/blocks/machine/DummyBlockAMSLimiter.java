package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.EnumGUI;
import com.hbm.interfaces.IDummy;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityAMSLimiter;
import com.hbm.tileentity.machine.TileEntityDummy;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class DummyBlockAMSLimiter extends BlockContainer implements IDummy {
	
	public static boolean safeBreak = false;

	public DummyBlockAMSLimiter(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityDummy();
	}

    @Override
	public void breakBlock(World world, int x, int y, int z, Block block, int i)
    {
    	if(!safeBreak) {
    		TileEntity te = world.getTileEntity(x, y, z);
    		if(te != null && te instanceof TileEntityDummy) {
    			int a = ((TileEntityDummy)te).targetX;
    			int b = ((TileEntityDummy)te).targetY;
    			int c = ((TileEntityDummy)te).targetZ;
    		
    			//world.getBlock(a, b, c).breakBlock(world, a, b, c, block, i);
    			if(!world.isRemote)
    				world.func_147480_a(a, b, c, true);
    		}
    	}
    	world.removeTileEntity(x, y, z);
    }

	@Override
	public int getRenderType() {
		return -1;
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
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return null;
    }

    @Override
	@SideOnly(Side.CLIENT)
    public Item getItem(World world, int x, int y, int z)
    {
        return Item.getItemFromBlock(ModBlocks.ams_limiter);
    }
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
    		TileEntity te = world.getTileEntity(x, y, z);
    		if(te != null && te instanceof TileEntityDummy) {
    			int a = ((TileEntityDummy)te).targetX;
    			int b = ((TileEntityDummy)te).targetY;
    			int c = ((TileEntityDummy)te).targetZ;
    			
    			TileEntityAMSLimiter entity = (TileEntityAMSLimiter) world.getTileEntity(a, b, c);
    			if(entity != null)
    			{
    				FMLNetworkHandler.openGui(player, MainRegistry.instance, EnumGUI.AMS_LIMITER.ordinal(), world, a, b, c);
    			}
    		}
			return true;
		} else {
			return false;
		}
	}
}

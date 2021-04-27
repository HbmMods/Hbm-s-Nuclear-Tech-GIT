package com.hbm.blocks.test;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityMachineSingGen;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TestMachine extends BlockContainer
{
	public TestMachine(Material mat)
	{
		super(mat);
	}
	
	/*@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (world.isRemote)
		{
			return true;
		}
		else if (!player.isSneaking())
		{
			TileEntityMachineSingGen tileEntity = (TileEntityMachineSingGen) world.getTileEntity(x, y, z);
			if (tileEntity != null)
			{
				FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_test_machine, world, x, y, z);
			}
			return true;
		}
		else
		{
			return false;
		}
	}*/
	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
	{
		return new TileEntityMachineSingGen();
	}
	
	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
	{
		return Item.getItemFromBlock(ModBlocks.test_machine);
	}
}

package com.hbm.blocks.machine;

import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockBasicMachine extends BlockContainer
{
	private Class<? extends TileEntity> teClass = null;
	private int guiID;
	public BlockBasicMachine(Material mat, Class<? extends TileEntity> teClassIn, int gui)
	{
		super(mat);
		teClass = teClassIn;
		guiID = gui;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int p_149915_2_)
	{
		try
		{
			return teClass.newInstance();
		}
		catch (InstantiationException | IllegalAccessException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, int x, int y, int z,
			EntityPlayer player, int i, float fX, float fY, float fZ)
	{
		if (worldIn.isRemote)
			return true;
		else if (!player.isSneaking())
		{
			TileEntity te = worldIn.getTileEntity(x, y, z);
			if (te != null && te.getClass().isAssignableFrom(teClass))
				FMLNetworkHandler.openGui(player, MainRegistry.instance, guiID, worldIn, x, y, z);
			return true;
		}
		else
			return false;
	}
}

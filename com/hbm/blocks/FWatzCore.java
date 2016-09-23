package com.hbm.blocks;

import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityFWatzCore;
import com.hbm.tileentity.TileEntityMachineBattery;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class FWatzCore extends BlockContainer {

	protected FWatzCore(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityFWatzCore();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
			TileEntityFWatzCore entity = (TileEntityFWatzCore) world.getTileEntity(x, y, z);
			if(entity != null)
			{
				FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_fwatz_multiblock, world, x, y, z);
			}
			return true;
		} else {
			return false;
		}
	}

}

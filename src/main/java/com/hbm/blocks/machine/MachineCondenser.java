package com.hbm.blocks.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityCondenser;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MachineCondenser extends BlockContainer {

	public MachineCondenser(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCondenser();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
			TileEntityCondenser entity = (TileEntityCondenser) world.getTileEntity(x, y, z);
			if(entity != null)
			{
				switch(entity.tanks[0].getTankType()) {
				case SPENTSTEAM: entity.tanks[0].setTankType(FluidType.SPENTHEAVYSTEAM); entity.tanks[1].setTankType(FluidType.HEAVYWATER); break;
				case SPENTHEAVYSTEAM: entity.tanks[0].setTankType(FluidType.SPENTSTEAM); entity.tanks[1].setTankType(FluidType.WATER); break;
				}
			}
			return true;
		} else {
			return false;
		}
	}
}

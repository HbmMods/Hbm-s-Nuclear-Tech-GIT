package com.hbm.blocks.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityDummy;
import com.hbm.tileentity.TileEntityMachineFluidTank;
import com.hbm.tileentity.TileEntityMachineGasFlare;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MachineFluidTank extends BlockContainer {

	public MachineFluidTank(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityMachineFluidTank();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
    		TileEntityMachineFluidTank entity = (TileEntityMachineFluidTank) world.getTileEntity(x, y, z);
    		if(entity != null)
    		{
    			FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_machine_fluidtank, world, x, y, z);
    		}
			return true;
		} else {
			return false;
		}
	}

}

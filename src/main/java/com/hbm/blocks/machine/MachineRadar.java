package com.hbm.blocks.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.WeaponConfig;
import com.hbm.handler.EnumGUI;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityMachineRadar;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class MachineRadar extends BlockContainer {

	public MachineRadar(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityMachineRadar();
	}
	
	@Override
	public int getRenderType(){
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
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(y < WeaponConfig.radarAltitude) {
			if(world.isRemote)
				player.addChatMessage(new ChatComponentText("[Radar] Error: Radar altitude not sufficient."));
			return true;
		}
		
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
			TileEntityMachineRadar entity = (TileEntityMachineRadar) world.getTileEntity(x, y, z);
			if(entity != null)
			{
				FMLNetworkHandler.openGui(player, MainRegistry.instance, EnumGUI.RADAR.ordinal(), world, x, y, z);
			}
			return true;
		} else {
			return false;
		}
	}
	
    public boolean canProvidePower()
    {
        return true;
    }

    public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int m)
    {
		TileEntityMachineRadar entity = (TileEntityMachineRadar) world.getTileEntity(x, y, z);
        return entity.getRedPower();
    }

    public int isProvidingStrongPower(IBlockAccess world, int x, int y, int z, int m)
    {
        return isProvidingWeakPower(world, x, y, z, m);
    }
}

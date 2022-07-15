package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.EnumGUI;
import com.hbm.interfaces.IDummy;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityDummy;
import com.hbm.tileentity.machine.storage.TileEntityMachineFluidTank;

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

public class DummyBlockFluidTank extends DummyOldBase {

	public DummyBlockFluidTank(Material p_i45386_1_, boolean port) {
		super(p_i45386_1_, port);
	}

    @Override
	@SideOnly(Side.CLIENT)
    public Item getItem(World world, int x, int y, int z)
    {
        return Item.getItemFromBlock(ModBlocks.machine_fluidtank);
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
    			
    			TileEntityMachineFluidTank entity = (TileEntityMachineFluidTank) world.getTileEntity(a, b, c);
    			if(entity != null)
    			{
    				FMLNetworkHandler.openGui(player, MainRegistry.instance, EnumGUI.FLUID_TANK.ordinal(), world, a, b, c);
    			}
    		}
			return true;
		} else {
			return false;
		}
	}
}

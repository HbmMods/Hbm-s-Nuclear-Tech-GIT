package com.hbm.blocks.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityDummy;
import com.hbm.tileentity.machine.oil.TileEntityMachineRefinery;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class DummyBlockRefinery extends DummyOldBase {

	public DummyBlockRefinery(Material p_i45386_1_, boolean port) {
		super(p_i45386_1_, port);
	}

    @Override
	@SideOnly(Side.CLIENT)
    public Item getItem(World world, int x, int y, int z)
    {
        return Item.getItemFromBlock(ModBlocks.machine_refinery);
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

    			TileEntityMachineRefinery entity = (TileEntityMachineRefinery) world.getTileEntity(a, b, c);
    			if(entity != null)
    			{
    				FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, a, b, c);
    			}
    		}
			return true;
		} else {
			return false;
		}
	}
}

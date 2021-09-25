package com.hbm.blocks.test;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.BlockBasicMachine;
import com.hbm.interfaces.IBomb;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityMachineSingGen;
import com.hbm.tileentity.turret.TileEntityLunarOni;
import com.hbm.tileentity.turret.TileEntityTsukuyomi;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TestMachine extends BlockBasicMachine implements IBomb
{
	public TestMachine(Material mat, Class<? extends TileEntity> teClass, int guiID)
	{
		super(mat, teClass, guiID);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (Library.checkForHeld(player, ModItems.designator_range) || Library.checkForHeld(player, ModItems.detonator_laser))
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(player.getHeldItem().getTagCompound(), x, y, z));
		
		return super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
	}
	
	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
	{
		return Item.getItemFromBlock(ModBlocks.test_machine);
	}

	@Override
	public void explode(World world, int x, int y, int z)
	{
		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(x, y, z, 1, 0));
	}
}

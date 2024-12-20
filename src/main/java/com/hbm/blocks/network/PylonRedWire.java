package com.hbm.blocks.network;

import com.hbm.tileentity.network.TileEntityPylon;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import java.util.List;

public class PylonRedWire extends PylonBase {

	public PylonRedWire(Material material) {
		super(material);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityPylon();
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		list.add(EnumChatFormatting.GOLD + "Connection Type: " + EnumChatFormatting.YELLOW + "Single");
		list.add(EnumChatFormatting.GOLD + "Connection Range: " + EnumChatFormatting.YELLOW + "25m");
	}
}

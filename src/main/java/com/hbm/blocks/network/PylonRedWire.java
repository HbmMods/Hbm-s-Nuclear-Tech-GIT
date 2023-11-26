package com.hbm.blocks.network;

import java.util.List;

import com.hbm.tileentity.network.TileEntityPylon;

import com.hbm.util.I18nUtil;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

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
		for(String s : I18nUtil.resolveKeyArray("tile.red_pylon.desc"))
			list.add(EnumChatFormatting.GOLD + s);
	}
}

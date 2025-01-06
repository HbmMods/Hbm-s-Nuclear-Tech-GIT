package com.hbm.items.tool;

import java.util.List;

import com.hbm.handler.BobmazonOfferFactory;
import com.hbm.inventory.gui.GUIScreenBobmazon;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IGUIProvider;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemCatalog extends Item implements IGUIProvider {

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if(world.isRemote)
			player.openGui(MainRegistry.instance, 0, world, 0, 0, 0);
		
		return stack;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		
		if(this == ModItems.bobmazon_hidden) {
			list.add("For a guide on how to obtain this, visit https://bit.ly/2TPgcqT");
			list.add("No tricks this time, i promise.");
		}
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(BobmazonOfferFactory.getOffers(player.getHeldItem()) != null)
			return new GUIScreenBobmazon(player, BobmazonOfferFactory.getOffers(player.getHeldItem()));
		
		return null;
	}
}

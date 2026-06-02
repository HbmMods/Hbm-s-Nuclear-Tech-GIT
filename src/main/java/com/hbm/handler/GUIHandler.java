package com.hbm.handler;

import com.hbm.tileentity.IGUIProvider;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GUIHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		IGUIProvider provider = this.getGUIProvider(player, world, x, y, z);
		if(provider != null) return provider.provideContainer(ID, player, world, x, y, z);
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		IGUIProvider provider = this.getGUIProvider(player, world, x, y, z);
		if(provider != null) return provider.provideGUI(ID, player, world, x, y, z);
		return null;
	}

	public IGUIProvider getGUIProvider(EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if(tileEntity instanceof IGUIProvider) return ((IGUIProvider) tileEntity);
		
		Block block = world.getBlock(x, y, z);
		if(block instanceof IGUIProvider) return ((IGUIProvider) block);
		
		ItemStack item = player.getHeldItem();
		if(item != null && item.getItem() instanceof IGUIProvider) return ((IGUIProvider) item.getItem());
		
		Entity entity = player.worldObj.getEntityByID(x);
		if(entity != null && entity instanceof IGUIProvider) return ((IGUIProvider) entity);
		
		return null;
	}
}

package com.hbm.handler;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockBobble.TileEntityBobble;
import com.hbm.blocks.machine.NTMAnvil;
import com.hbm.entity.cart.EntityMinecartCrate;
import com.hbm.entity.cart.EntityMinecartDestroyer;
import com.hbm.interfaces.Spaghetti;
import com.hbm.inventory.container.*;
import com.hbm.inventory.gui.*;
import com.hbm.items.ModItems;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.bomb.*;
import com.hbm.tileentity.machine.*;
import com.hbm.tileentity.machine.oil.*;
import com.hbm.tileentity.machine.rbmk.*;
import com.hbm.tileentity.machine.storage.*;
import com.hbm.tileentity.turret.*;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

@Spaghetti("ew")
public class GUIHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		
		if(tileEntity instanceof IGUIProvider) {
			return ((IGUIProvider) tileEntity).provideContainer(ID, player, world, x, y, z);
		}
		
		Block block = world.getBlock(x, y, z);
		
		if(block instanceof IGUIProvider) {
			return ((IGUIProvider) block).provideContainer(ID, player, world, x, y, z);
		}
		
		ItemStack item = player.getHeldItem();
		
		if(item != null && item.getItem() instanceof IGUIProvider) {
			return ((IGUIProvider) item.getItem()).provideContainer(ID, player, world, x, y, z);
		}
		
		Entity entity = player.worldObj.getEntityByID(x);
		
		if(entity != null && entity instanceof IGUIProvider) {
			return ((IGUIProvider) entity).provideContainer(ID, player, world, x, y, z);
		}
		
		//notice: stop doing this, unless you absolutely have to :3
		
		//notice: stop doing this completely, period :P

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		
		if(tileEntity instanceof IGUIProvider) {
			return ((IGUIProvider) tileEntity).provideGUI(ID, player, world, x, y, z);
		}
		
		Block block = world.getBlock(x, y, z);
		
		if(block instanceof IGUIProvider) {
			return ((IGUIProvider) block).provideGUI(ID, player, world, x, y, z);
		}
		
		ItemStack item = player.getHeldItem();
		
		if(item != null && item.getItem() instanceof IGUIProvider) {
			return ((IGUIProvider) item.getItem()).provideGUI(ID, player, world, x, y, z);
		}
		
		Entity entity = player.worldObj.getEntityByID(x);
		
		if(entity != null && entity instanceof IGUIProvider) {
			return ((IGUIProvider) entity).provideGUI(ID, player, world, x, y, z);
		}
		
		//stop doing this unless you absolutely have to ;3
		
		//stop doing this, period >:3
		return null;
	}

}

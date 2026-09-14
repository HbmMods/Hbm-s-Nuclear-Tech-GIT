package com.hbm.items.tool;

import com.hbm.inventory.gui.GUIRTTYDesignator;
import com.hbm.items.IItemControlReceiver;

import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IGUIProvider;

import com.hbm.tileentity.network.RTTYSystem;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class ItemRTTYDesignator extends Item implements IItemControlReceiver, IGUIProvider {
	
	public static final String KEY_CHANNEL = "chan";

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(player.isSneaking() && world.isRemote) {
			player.openGui(MainRegistry.instance, 0, world, 0, 0, 0);
		} else if(!world.isRemote && !player.isSneaking()) {
			if(!stack.hasTagCompound() || !stack.stackTagCompound.hasKey(KEY_CHANNEL)) return stack;
			MovingObjectPosition pos = Library.rayTrace(player, 300, 1);

			ForgeDirection dir = ForgeDirection.getOrientation(pos.sideHit);
			int x = pos.blockX + dir.offsetX;
			int y = pos.blockY + dir.offsetY;
			int z = pos.blockZ + dir.offsetZ;
			
			String coords = x + ";" + y + ";" + z;
			
			String chanFreq = stack.stackTagCompound.getString(KEY_CHANNEL);
			RTTYSystem.broadcast(world, chanFreq, coords);
		}
		return stack;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		if(!stack.hasTagCompound() || !stack.stackTagCompound.hasKey(KEY_CHANNEL) || stack.stackTagCompound.getString(KEY_CHANNEL).isEmpty()) {
			list.add(EnumChatFormatting.RED + "No channel set!");
		} else {
			list.add(EnumChatFormatting.YELLOW + "Channel: " + stack.stackTagCompound.getString(KEY_CHANNEL));
		}
	}
	
	@Override
	public void receiveControl(EntityPlayer player, ItemStack stack, NBTTagCompound data) {
		if(!stack.hasTagCompound()) stack.stackTagCompound = new NBTTagCompound();
		stack.stackTagCompound.setString(KEY_CHANNEL, data.getString(KEY_CHANNEL));
	}
	
	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
	@Override public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIRTTYDesignator(player.getHeldItem());
	}
}

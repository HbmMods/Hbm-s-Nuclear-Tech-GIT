package com.hbm.items.tool;

import java.util.List;

import com.hbm.inventory.gui.GUIScreenPager;
import com.hbm.items.IItemControlReceiver;
import com.hbm.main.MainRegistry;
import com.hbm.main.ServerProxy;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.PlayerInformPacket;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.network.RTTYSystem;
import com.hbm.tileentity.network.RTTYSystem.RTTYChannel;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemRTTYPager extends Item implements IItemControlReceiver, IGUIProvider {
	
	public static final String KEY_CHANNEL = "chan";

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean held) {
		if(!stack.hasTagCompound() || !stack.stackTagCompound.hasKey(KEY_CHANNEL)) return;
		if(!(entity instanceof EntityPlayerMP) || world.isRemote) return;
		
		String channelFreq = stack.stackTagCompound.getString(KEY_CHANNEL);
		RTTYChannel chan = RTTYSystem.listen(world, channelFreq);
		
		if(chan != null && chan.timeStamp >= world.getTotalWorldTime() - 1) {
			int alive = entity.ticksExisted % 1000;
			String message = EnumChatFormatting.GOLD + "[ " + channelFreq + " (" + alive + ") ] " + EnumChatFormatting.YELLOW + chan.signal;
			PacketDispatcher.wrapper.sendTo(new PlayerInformPacket(message, ServerProxy.ID_PAGER_DYN + slot, 5_000), (EntityPlayerMP) entity);
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(world.isRemote) player.openGui(MainRegistry.instance, 0, world, 0, 0, 0);
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
	public void receiveControl(ItemStack stack, NBTTagCompound data) {
		if(data.hasKey("chan")) {
			if(!stack.hasTagCompound()) stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setString(KEY_CHANNEL, data.getString(KEY_CHANNEL));
		}
	}

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return null; }
	@Override public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return new GUIScreenPager(player.getHeldItem()); }
}

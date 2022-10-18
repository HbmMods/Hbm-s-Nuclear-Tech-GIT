package com.hbm.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.MouseEvent;

public interface ISyncButtons {

	public boolean canReceiveMouse(EntityPlayer player, ItemStack stack, MouseEvent event, int button, boolean buttonstate);
	public void receiveMouse(EntityPlayer player, ItemStack stack, int button, boolean buttonstate);
}

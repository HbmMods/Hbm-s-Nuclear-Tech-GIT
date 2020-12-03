package com.hbm.interfaces;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

public interface IItemHUD {
	
	public void renderHUD(RenderGameOverlayEvent.Pre event, ElementType type, EntityPlayer player, ItemStack stack);

}

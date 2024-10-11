package com.hbm.items.weapon.sedna.hud;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public interface IHUDComponent {

	public int getComponentHeight(EntityPlayer player, ItemStack stack);
	public void renderHUDComponent(Pre event, ElementType type, EntityPlayer player, ItemStack stack, int bottomOffset, int gunIndex);
}

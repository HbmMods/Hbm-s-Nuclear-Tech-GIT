package com.hbm.items;

import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.HbmAnimationPacket;
import com.hbm.render.anim.BusAnimation;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

public interface IAnimatedItem<T extends Enum<?>> {

	/** Fetch the animation for a given type */
	public BusAnimation getAnimation(T type, ItemStack stack);

	/** Should a player holding this item aim it like a gun/bow? */
	public boolean shouldPlayerModelAim(ItemStack stack);

	// Runtime erasure means we have to explicitly give the class a second time :(
	public Class<T> getEnum();

	// Run a specified animation
	public default void playAnimation(EntityPlayer player, T type) {
		if(player instanceof EntityPlayerMP) {
			PacketDispatcher.wrapper.sendTo(new HbmAnimationPacket(type.ordinal(), 0, 0), (EntityPlayerMP) player);
		}
	}

}

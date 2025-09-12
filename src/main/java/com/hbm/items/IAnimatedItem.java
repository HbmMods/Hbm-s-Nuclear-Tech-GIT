package com.hbm.items;

import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.GunAnimationPacket;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.HbmAnimations.AnimType;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

public interface IAnimatedItem {

	// Fetch the animation for a given type
	public BusAnimation getAnimation(AnimType type, ItemStack stack);

	// Run the swing animation
	public default void playAnimation(EntityPlayer player) {
		playAnimation(player, AnimType.CYCLE);
	}

	// Run a specified animation
	public default void playAnimation(EntityPlayer player, AnimType type) {
		if(player instanceof EntityPlayerMP) {
			PacketDispatcher.wrapper.sendTo(new GunAnimationPacket(type.ordinal(), 0, 0), (EntityPlayerMP) player);
		}
	}

}

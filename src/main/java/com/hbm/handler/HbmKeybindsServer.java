package com.hbm.handler;

import com.hbm.extprop.HbmPlayerProps;
import com.hbm.handler.HbmKeybinds.EnumKeybind;
import com.hbm.items.IKeybindReceiver;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class HbmKeybindsServer {
	
	/** Can't put this in HbmKeybinds because it's littered with clientonly stuff */
	public static void onPressedServer(EntityPlayer player, EnumKeybind key, boolean state) {
		
		// EXTPROP HANDLING
		HbmPlayerProps props = HbmPlayerProps.getData(player);
		props.setKeyPressed(key, state);
		
		// ITEM HANDLING
		ItemStack held = player.getHeldItem();
		if(held != null && held.getItem() instanceof IKeybindReceiver) {
			IKeybindReceiver rec = (IKeybindReceiver) held.getItem();
			if(rec.canHandleKeybind(player, held, key)) rec.handleKeybind(player, held, key, state);
		}
	}
}

package com.hbm.main;

import com.hbm.lib.RefStrings;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class ModEventHandler
{	
	public static boolean showMessage = true;
	
	@SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if(showMessage)
        {
        	event.player.addChatMessage(new ChatComponentText("Loaded world with Hbm's Nuclear Tech Mod " + RefStrings.VERSION + " for Minecraft 1.7.10!"));
        }
        
        showMessage = !showMessage;
	}
}

package com.hbm.main;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
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
	
	@SubscribeEvent
	public void itemSmelted(PlayerEvent.ItemSmeltedEvent e) {
		if(e.smelting.getItem().equals(ModItems.ingot_titanium)) {
			e.player.addStat(MainRegistry.achievementGetTitanium, 1);
		}
	}
	
	@SubscribeEvent
	public void itemCrafted(PlayerEvent.ItemCraftedEvent e) {
		
		Item item = e.crafting.getItem();

		if(item.equals(Item.getItemFromBlock(ModBlocks.machine_difurnace_off))) {
			e.player.addStat(MainRegistry.achievementCraftAlloyFurnace, 1);
		}
		if(item.equals(Item.getItemFromBlock(ModBlocks.machine_reactor))) {
			e.player.addStat(MainRegistry.achievementCraftBreedingReactor, 1);
		}
		if(item.equals(Item.getItemFromBlock(ModBlocks.machine_centrifuge))) {
			e.player.addStat(MainRegistry.achievementCraftCentrifuge, 1);
		}
	}
	
	@SubscribeEvent
	public void itemCollected(PlayerEvent.ItemPickupEvent e) {
		if(e.pickedUp.getEntityItem().equals(ModItems.nothing)) {
			//e.player.addStat(MainRegistry.achievementGetAmblygonite, 1);
		}
	}
}

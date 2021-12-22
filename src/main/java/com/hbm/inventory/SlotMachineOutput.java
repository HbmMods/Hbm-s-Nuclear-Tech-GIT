package com.hbm.inventory;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotMachineOutput extends Slot {

	public SlotMachineOutput(IInventory inventory, int i, int j, int k) {
		super(inventory, i, j, k);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return false;
	}
	
	//ugly but nothing to be done
	public static void checkAchievements(EntityPlayer player, ItemStack stack) {
		Item item = stack.getItem();
		
		if(item == Item.getItemFromBlock(ModBlocks.machine_chemplant))
			player.triggerAchievement(MainRegistry.achChemplant);
		if(item == Item.getItemFromBlock(ModBlocks.concrete) || item == Item.getItemFromBlock(ModBlocks.concrete_asbestos))
			player.triggerAchievement(MainRegistry.achConcrete);
		if(item == ModItems.ingot_polymer)
			player.triggerAchievement(MainRegistry.achPolymer);
		if(item == ModItems.ingot_desh)
			player.triggerAchievement(MainRegistry.achDesh);
		if(item == ModItems.gem_tantalium)
			player.triggerAchievement(MainRegistry.achTantalum);
		if(item == Item.getItemFromBlock(ModBlocks.machine_gascent))
			player.triggerAchievement(MainRegistry.achGasCent);
		if(item == Item.getItemFromBlock(ModBlocks.machine_centrifuge))
			player.triggerAchievement(MainRegistry.achCentrifuge);
		if(item == ModItems.ingot_schrabidium || item == ModItems.nugget_schrabidium)
			player.triggerAchievement(MainRegistry.achSchrab);
		if(item == Item.getItemFromBlock(ModBlocks.machine_crystallizer))
			player.triggerAchievement(MainRegistry.achAcidizer);
		if(item == Item.getItemFromBlock(ModBlocks.machine_silex))
			player.triggerAchievement(MainRegistry.achSILEX);
		if(item == ModItems.nugget_technetium)
			player.triggerAchievement(MainRegistry.achTechnetium);
		if(item == Item.getItemFromBlock(ModBlocks.watz_core))
			player.triggerAchievement(MainRegistry.achWatz);
		if(item == ModItems.nugget_bismuth)
			player.triggerAchievement(MainRegistry.achBismuth);
		if(item == ModItems.nugget_am241 || item == ModItems.nugget_am242)
			player.triggerAchievement(MainRegistry.achBreeding);
		if(item == ModItems.missile_nuclear || item == ModItems.missile_nuclear_cluster || item == ModItems.missile_doomsday || item == ModItems.mp_warhead_10_nuclear || item == ModItems.mp_warhead_10_nuclear_large || item == ModItems.mp_warhead_15_nuclear || item == ModItems.mp_warhead_15_nuclear_shark || item == ModItems.mp_warhead_15_boxcar)
			player.triggerAchievement(MainRegistry.achRedBalloons);
		if(item == Item.getItemFromBlock(ModBlocks.struct_iter_core))
			player.triggerAchievement(MainRegistry.achFusion);
	}
	
	@Override 
	public void onPickupFromSlot(EntityPlayer player, ItemStack stack) {
		checkAchievements(player, stack);
			
        this.onSlotChanged();
    }
}
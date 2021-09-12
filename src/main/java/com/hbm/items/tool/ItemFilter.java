package com.hbm.items.tool;

import com.hbm.handler.ArmorModHandler;
import com.hbm.main.MainRegistry;
import com.hbm.util.ArmorUtil;

import api.hbm.item.IGasMask;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemFilter extends Item {
	
	public ItemFilter() {
		this.setMaxDamage(20000);
		this.setCreativeTab(MainRegistry.consumableTab);
		this.setMaxStackSize(1);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		ItemStack helmet = player.inventory.armorItemInSlot(3);
		
		if(helmet == null)
			return stack;
		
		if(!(helmet.getItem() instanceof IGasMask)) {
			
			if(ArmorModHandler.hasMods(helmet)) {
				ItemStack[] mods = ArmorModHandler.pryMods(helmet);
				
				if(mods[ArmorModHandler.helmet_only] != null) {
					ItemStack mask = mods[ArmorModHandler.helmet_only];
					
					ItemStack ret = installFilterOn(mask, stack, world, player);
					ArmorModHandler.applyMod(helmet, mask);
					return ret;
				}
			}
		}
		
		return installFilterOn(helmet, stack, world, player);
	}
	
	private ItemStack installFilterOn(ItemStack helmet, ItemStack filter, World world, EntityPlayer player) {
		
		if(!(helmet.getItem() instanceof IGasMask)) {
			return filter;
		}
		
		IGasMask mask = (IGasMask) helmet.getItem();
		if(!mask.isFilterApplicable(helmet, player, filter))
			return filter;
		
		ItemStack copy = filter.copy();
		ItemStack current = ArmorUtil.getGasMaskFilter(helmet);
		
		if(current != null) {
			filter = current;
		} else {
			filter.stackSize = 0;
		}
		
		ArmorUtil.installGasMaskFilter(helmet, copy);
		
		world.playSoundAtEntity(player, "hbm:item.gasmaskScrew", 1.0F, 1.0F);
		
		return filter;
	}
}

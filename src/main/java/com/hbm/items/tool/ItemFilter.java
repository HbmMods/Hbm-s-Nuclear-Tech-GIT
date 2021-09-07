package com.hbm.items.tool;

import com.hbm.util.ArmorUtil;

import api.hbm.item.IGasMask;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemFilter extends Item {
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		ItemStack helmet = player.inventory.armorItemInSlot(3);
		
		if(helmet == null)
			return stack;
		
		if(!(helmet.getItem() instanceof IGasMask))
			return stack;
		
		IGasMask mask = (IGasMask) helmet.getItem();
		if(!mask.isFilterApplicable(helmet, player, stack))
			return stack;
		
		ItemStack copy = stack.copy();
		ItemStack current = ArmorUtil.getGasMaskFilter(helmet);
		
		if(current != null) {
			stack = current;
		} else {
			stack.stackSize = 0;
		}
		
		ArmorUtil.installGasMaskFilter(helmet, copy);
		
		world.playSoundAtEntity(player, "hbm:item.gasmaskScrew", 1.0F, 1.0F);
		
		return stack;
	}
}

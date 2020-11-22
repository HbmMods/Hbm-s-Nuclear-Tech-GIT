package com.hbm.items.tool;

import java.util.List;

import com.hbm.items.armor.ArmorFSB;
import com.hbm.items.armor.ArmorFSBPowered;

import api.hbm.energy.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemFusionCore extends Item {

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
    	if(ArmorFSB.hasFSBArmor(player) && player.inventory.armorInventory[3].getItem() instanceof ArmorFSBPowered) {
        	
        	for(ItemStack st : player.inventory.armorInventory) {
        		
        		if(st == null)
        			continue;
        		
        		if(st.getItem() instanceof IBatteryItem) {
        			
        			long maxcharge = ((IBatteryItem)st.getItem()).getMaxCharge();
        			long charge = ((IBatteryItem)st.getItem()).getCharge(st);
        			long newcharge = Math.min(charge + 2500000, maxcharge);
        			
        			((IBatteryItem)st.getItem()).setCharge(st, newcharge);
        		}
        	}
        	
        	stack.stackSize--;
        	
            world.playSoundAtEntity(player, "random.orb", 0.25F, 1.25F);
    	}
		
		return stack;
	}
    
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		
		list.add(EnumChatFormatting.YELLOW + "Charges all worn armor pieces by 2.5MHE");
		list.add("[Requires full electric set to be worn]");
	}
}

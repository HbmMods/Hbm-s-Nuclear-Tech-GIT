package com.hbm.items.food;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.items.armor.ArmorFSB;

import api.hbm.energymk2.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemPancake extends ItemFood {

	public ItemPancake(int p_i45339_1_, float p_i45339_2_, boolean p_i45339_3_) {
		super(p_i45339_1_, p_i45339_2_, p_i45339_3_);
		this.setAlwaysEdible();
	}


    @Override
	protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
    	
    	for(ItemStack st : player.inventory.armorInventory) {
    		
    		if(st == null)
    			continue;
    		
    		if(st.getItem() instanceof IBatteryItem) {
    			((IBatteryItem)st.getItem()).setCharge(st, ((IBatteryItem)st.getItem()).getMaxCharge(st));
    		}
    	}
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
    	
    	if(ArmorFSB.hasFSBArmorIgnoreCharge(player) && player.inventory.armorInventory[3].getItem() == ModItems.bj_helmet) {
        	return super.onItemRightClick(stack, world, player);
    	}
    	
    	if(!world.isRemote)
    		player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Your teeth are too soft to eat this."));
    	
    	return stack;
    }
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		list.add("Can be eaten to recharge lunar cybernetic armor");
		list.add("Not for people with weak molars");
		list.add("");
		list.add("Half burnt and smells horrible");
	}

}

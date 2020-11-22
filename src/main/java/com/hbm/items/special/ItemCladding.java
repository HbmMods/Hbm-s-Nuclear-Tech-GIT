package com.hbm.items.special;

import java.util.List;

import com.hbm.handler.HazmatRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemCladding extends Item {
	
	float rad;
	
	public ItemCladding(float rad) {
		this.rad = rad;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
			
		if(!world.isRemote) {

			boolean used = false;

			for(ItemStack armor : player.inventory.armorInventory) {

				if(armor != null && HazmatRegistry.getCladding(armor) < rad) {

					if(!armor.hasTagCompound())
						armor.stackTagCompound = new NBTTagCompound();

					armor.stackTagCompound.setFloat("hfr_cladding", rad);

					used = true;
				}
			}

			if(used) {
				world.playSoundAtEntity(player, "hbm:item.repair", 1.0F, 1.0F);
				stack.stackSize--;
			}
		}
		
		return stack;
	}
    
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		list.add(EnumChatFormatting.YELLOW + "Adds " + rad + " rad-resistance to all armor pieces.");
	}
}

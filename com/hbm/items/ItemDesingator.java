package com.hbm.items;

import java.util.List;

import com.hbm.blocks.LaunchPad;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemDesingator extends Item {

    @Override
	public void onCreated(ItemStack p_77622_1_, World p_77622_2_, EntityPlayer p_77622_3_) {
    	p_77622_1_.stackTagCompound = new NBTTagCompound();
    	p_77622_1_.stackTagCompound.setInteger("xCoord", 0);
    	p_77622_1_.stackTagCompound.setInteger("zCoord", 0);
    }
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		if(itemstack.stackTagCompound != null)
		{
			list.add("Target Coordinates:");
			list.add("X: " + String.valueOf(itemstack.stackTagCompound.getInteger("xCoord")));
			list.add("Z: " + String.valueOf(itemstack.stackTagCompound.getInteger("zCoord")));
		} else {
			list.add("Please select a target.");
		}
	}
	
	@Override
    public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
		if(!(p_77648_3_.getBlock(p_77648_4_, p_77648_5_, p_77648_6_) instanceof LaunchPad))
		{
			if(p_77648_1_.stackTagCompound != null)
			{
				p_77648_1_.stackTagCompound.setInteger("xCoord", p_77648_4_);
				p_77648_1_.stackTagCompound.setInteger("zCoord", p_77648_6_);
			} else {
				p_77648_1_.stackTagCompound = new NBTTagCompound();
				p_77648_1_.stackTagCompound.setInteger("xCoord", p_77648_4_);
				p_77648_1_.stackTagCompound.setInteger("zCoord", p_77648_6_);
			}
	        return true;
		}
    	
        return false;
    }
}

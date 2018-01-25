package com.hbm.items.tool;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemDesingatorManual extends Item {

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if(world.isRemote)
			player.openGui(MainRegistry.instance, ModItems.guiID_item_designator, world, 0, 0, 0);
		
		return stack;
	}

    @Override
	public void onCreated(ItemStack p_77622_1_, World p_77622_2_, EntityPlayer p_77622_3_) {
    	p_77622_1_.stackTagCompound = new NBTTagCompound();
    	p_77622_1_.stackTagCompound.setInteger("xCoord", 0);
    	p_77622_1_.stackTagCompound.setInteger("zCoord", 0);
    }
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{
		if(stack.stackTagCompound != null)
		{
			list.add("Target Coordinates:");
			list.add("X: " + String.valueOf(stack.stackTagCompound.getInteger("xCoord")));
			list.add("Z: " + String.valueOf(stack.stackTagCompound.getInteger("zCoord")));
		} else {
			list.add("Please select a target.");
		}
	}
}

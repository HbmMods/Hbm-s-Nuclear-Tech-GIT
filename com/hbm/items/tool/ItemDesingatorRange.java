package com.hbm.items.tool;

import java.util.List;

import com.hbm.blocks.bomb.LaunchPad;
import com.hbm.lib.Library;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemDesingatorRange extends Item {

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
	}@Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
		MovingObjectPosition pos = Library.rayTrace(player, 300, 1);
		int x = pos.blockX;
		int y = pos.blockY;
		int z = pos.blockZ;
		
		if(!(world.getBlock(x, y, z) instanceof LaunchPad))
		{
			if(stack.stackTagCompound == null)
				stack.stackTagCompound = new NBTTagCompound();
			
			stack.stackTagCompound.setInteger("xCoord", x);
			stack.stackTagCompound.setInteger("zCoord", z);
			
	        if(world.isRemote)
			{
	        	player.addChatMessage(new ChatComponentText("Position set to X:" + x + ", Z:" + z));
			}
	        
        	world.playSoundAtEntity(player, "hbm:item.techBleep", 1.0F, 1.0F);
        	
	        return stack;
		}
    	
        return stack;
    }
}

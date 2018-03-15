package com.hbm.items.tool;

import java.util.List;

import com.hbm.blocks.bomb.TurretBase;
import com.hbm.tileentity.bomb.TileEntityTurretBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemTurretBiometry extends Item {
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add("Channel set to " + getFreq(itemstack));
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {

		int i = 0;
		
		if(player.isSneaking()) {
			i = (getFreq(stack) - 1);
		} else {
			i = (getFreq(stack) + 1);
		}
		
		if(i == -1)
			i = 511;
		
		if(i == 512)
			i = 0;
		
		setFreq(stack, i);

        if(world.isRemote)
        	player.addChatMessage(new ChatComponentText("Channel set to " + i));

    	world.playSoundAtEntity(player, "hbm:item.techBleep", 1.0F, 1.0F);
		
		player.swingItem();
		
		return stack;
	}
	
	public static int getFreq(ItemStack stack) {
		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
			return 0;
		}
		return stack.stackTagCompound.getInteger("freq");
	}
	
	private static void setFreq(ItemStack stack, int i) {
		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
		}
		stack.stackTagCompound.setInteger("freq", i);
	}
}

package com.hbm.items.tool;

import java.util.Arrays;
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
		String[] names = getNames(itemstack);
		if(names != null)
			for(int i = 0; i < names.length; i++)
				list.add(names[i]);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {

		int i = 0;
		
		addName(stack, player.getUniqueID().toString());

        if(world.isRemote)
        	player.addChatMessage(new ChatComponentText("Added player data!"));

    	world.playSoundAtEntity(player, "hbm:item.techBleep", 1.0F, 1.0F);
		
		player.swingItem();
		
		return stack;
	}
	
	public static String[] getNames(ItemStack stack) {
		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
			return null;
		}
		
		String[] names = new String [stack.stackTagCompound.getInteger("playercount")];
		
		for(int i = 0; i < names.length; i++) {
			names[i] = stack.stackTagCompound.getString("player_" + i);
		}
		
		if(names.length == 0)
			return null;
		
		return names;
	}
	
	public static void addName(ItemStack stack, String s) {
		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
		}
		
		String[] names = getNames(stack);
		int count = 0;
		
		if(names != null && Arrays.asList(names).contains(s))
			return;
		
		if(names != null)
			count = names.length;
		
		stack.stackTagCompound.setInteger("playercount", count + 1);
		
		stack.stackTagCompound.setString("player_" + count, s);
	}
	
	public static void clearNames(ItemStack stack) {
		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
		}
		
		stack.stackTagCompound.setInteger("playercount", 0);
	}
}

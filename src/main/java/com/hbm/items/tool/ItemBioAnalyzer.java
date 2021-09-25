package com.hbm.items.tool;

import com.hbm.items.special.ItemCustomLore;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public abstract class ItemBioAnalyzer extends ItemCustomLore
{
	public ItemBioAnalyzer()
	{
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public abstract ItemStack onItemRightClick(ItemStack stack, World worldIn, EntityPlayer player);
	
	@Override
	public abstract boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity);
	
	/** The whole process was a little annoying so I simplified it slightly **/
	public void sendMessage(String message, EntityPlayer player)
	{
		player.addChatMessage(new ChatComponentText(message));
	}
}

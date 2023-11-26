package com.hbm.items.tool;

import java.util.List;
import java.util.Random;

import com.hbm.util.I18nUtil;
import com.hbm.world.machine.FWatz;
import com.hbm.world.machine.NuclearReactor;
import com.hbm.world.machine.Watz;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemWandS extends Item {

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		for(String s : I18nUtil.resolveKeyArray( "item.wand_s.desc"))
			list.add(s);
		if(itemstack.stackTagCompound != null)
		{
			switch(itemstack.stackTagCompound.getInteger("building"))
			{
			case 0:
				list.add("Structure: Nuclear Reactor");
				break;
			case 1:
				list.add("Structure: Watz Power Plant");
				break;
			case 2:
				list.add("Structure: Fusionary Watz Plant");
				break;
			}
		}
	}
	
	@Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
		if(stack.stackTagCompound == null)
		{
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setInteger("building", 0);
		}
		
		boolean up = player.rotationPitch <= 0.5F;
		
		if(!world.isRemote)
		{
			Random rand = new Random();
			
			switch(stack.stackTagCompound.getInteger("building"))
			{
			case 0:
				new NuclearReactor().generate(world, rand, x, up ? y : y - 4, z);
				break;
			case 1:
				new Watz().generate(world, rand, x, up ? y : y - 12, z);
				break;
			case 2:
				new FWatz().generateHull(world, rand, x, up ? y : y - 18, z);
				break;
			}
			
		}
		
		return true;
    }

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(player.isSneaking())
		{
			if(stack.stackTagCompound == null)
			{
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setInteger("building", 0);
				if(world.isRemote)
					player.addChatMessage(new ChatComponentText("Set Structure: Nuclear Reactor"));
			} else {
				int i = stack.stackTagCompound.getInteger("building");
				i++;
				stack.stackTagCompound.setInteger("building", i);
				if(i >= 3) {
					stack.stackTagCompound.setInteger("building", 0);
				}
				
				if(world.isRemote)
				{
				switch(i)
				{
					case 0:
						player.addChatMessage(new ChatComponentText("Set Structure: Nuclear Reactor"));
						break;
					case 1:
						player.addChatMessage(new ChatComponentText("Set Structure: Watz Power Plant"));
						break;
					case 2:
						player.addChatMessage(new ChatComponentText("Set Structure: Fusionary Watz Plant"));
						break;
					default:
						player.addChatMessage(new ChatComponentText("Set Structure: Nuclear Reactor"));
						break;
					}
				}
			}
		}
				
		return stack;
	}
}

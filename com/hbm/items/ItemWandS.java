package com.hbm.items;

import java.util.List;
import java.util.Random;

import com.hbm.world.FactoryAdvanced;
import com.hbm.world.FactoryTitanium;
import com.hbm.world.FusionReactor;
import com.hbm.world.NuclearReactor;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import scala.Int;

public class ItemWandS extends Item {

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add("Creative-only item");
		list.add("\"Instant structures for everyone!\"");
		list.add("(Cycle with shift-right click,");
		list.add("spawn structures with right click!)");
		if(itemstack.stackTagCompound != null)
		{
			switch(itemstack.stackTagCompound.getInteger("building"))
			{
			case 0:
				list.add("Structure: Titanium Factory");
				break;
			case 1:
				list.add("Structure: Advanced Factory");
				break;
			case 2:
				list.add("Structure: Nuclear Reactor");
				break;
			case 3:
				list.add("Structure: Fusion Reactor");
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
				new FactoryTitanium().generate(world, rand, x, up ? y : y - 2, z);
				break;
			case 1:
				new FactoryAdvanced().generate(world, rand, x, up ? y : y - 2, z);
				break;
			case 2:
				new NuclearReactor().generate(world, rand, x, up ? y : y - 4, z);
				break;
			case 3:
				new FusionReactor().generate(world, rand, x, up ? y : y - 4, z);
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
			} else {
				int i = stack.stackTagCompound.getInteger("building");
				i++;
				stack.stackTagCompound.setInteger("building", i);
				if(i >= 4) {
					stack.stackTagCompound.setInteger("building", 0);
				}
				
				if(world.isRemote)
				{
				switch(i)
				{
					case 0:
						player.addChatMessage(new ChatComponentText("Set Structure: Titanium Factory"));
						break;
					case 1:
						player.addChatMessage(new ChatComponentText("Set Structure: Advanced Factory"));
						break;
					case 2:
						player.addChatMessage(new ChatComponentText("Set Structure: Nuclear Reactor"));
						break;
					case 3:
						player.addChatMessage(new ChatComponentText("Set Structure: Fusion Reactor"));
						break;
					default:
						player.addChatMessage(new ChatComponentText("Set Structure: Titanium Factory"));
						break;
					}
				}
			}
		}
				
		return stack;
	}
}

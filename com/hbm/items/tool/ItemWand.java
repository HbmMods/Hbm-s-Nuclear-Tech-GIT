package com.hbm.items.tool;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemWand extends Item {
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add("Creative-only item");
		list.add("\"Destruction brings creation\"");
		list.add("(Set positions with right click,");
		list.add("set block with shift-right click!)");
		
		if(itemstack.stackTagCompound != null &&
				!(itemstack.stackTagCompound.getInteger("x") == 0 &&
						itemstack.stackTagCompound.getInteger("y") == 0 &&
								itemstack.stackTagCompound.getInteger("z") == 0))
		{
			list.add("Pos: " + itemstack.stackTagCompound.getInteger("x") + ", " + itemstack.stackTagCompound.getInteger("y") + ", " + itemstack.stackTagCompound.getInteger("z"));
		} else {
			list.add("Positions not set!");
		}
		if(itemstack.stackTagCompound != null)
			list.add("Block saved: " + Block.getBlockById(itemstack.stackTagCompound.getInteger("block")).getUnlocalizedName());
	}
	
	@Override
    public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
		if(p_77648_1_.stackTagCompound == null)
		{
			p_77648_1_.stackTagCompound = new NBTTagCompound();
		}
		
		if(p_77648_2_.isSneaking())
		{
			p_77648_1_.stackTagCompound.setInteger("block", Block.getIdFromBlock(p_77648_3_.getBlock(p_77648_4_, p_77648_5_, p_77648_6_)));
			p_77648_1_.stackTagCompound.setInteger("meta", p_77648_3_.getBlockMetadata(p_77648_4_, p_77648_5_, p_77648_6_));
			if(p_77648_3_.isRemote)
				p_77648_2_.addChatMessage(new ChatComponentText("Set block " + Block.getBlockById(p_77648_1_.stackTagCompound.getInteger("block")).getUnlocalizedName()));
		} else {
			if(p_77648_1_.stackTagCompound.getInteger("x") == 0 &&
					p_77648_1_.stackTagCompound.getInteger("y") == 0 &&
					p_77648_1_.stackTagCompound.getInteger("z") == 0)
			{
				p_77648_1_.stackTagCompound.setInteger("x", p_77648_4_);
				p_77648_1_.stackTagCompound.setInteger("y", p_77648_5_);
				p_77648_1_.stackTagCompound.setInteger("z", p_77648_6_);
				if(p_77648_3_.isRemote)
					p_77648_2_.addChatMessage(new ChatComponentText("Position set!"));
			} else {
				
				int x = p_77648_1_.stackTagCompound.getInteger("x");
				int y = p_77648_1_.stackTagCompound.getInteger("y");
				int z = p_77648_1_.stackTagCompound.getInteger("z");
				
				p_77648_1_.stackTagCompound.setInteger("x", 0);
				p_77648_1_.stackTagCompound.setInteger("y", 0);
				p_77648_1_.stackTagCompound.setInteger("z", 0);
				
				if(!p_77648_3_.isRemote)
				{
					for(int i = Math.min(x, p_77648_4_); i <= Math.max(x, p_77648_4_); i++)
					{
						for(int j = Math.min(y, p_77648_5_); j <= Math.max(y, p_77648_5_); j++)
						{
							for(int k = Math.min(z, p_77648_6_); k <= Math.max(z, p_77648_6_); k++)
							{
								p_77648_3_.setBlock(i, j, k, Block.getBlockById(p_77648_1_.stackTagCompound.getInteger("block")), p_77648_1_.stackTagCompound.getInteger("meta"), 3);
							}
						}
					}
				}
				if(p_77648_3_.isRemote)
					p_77648_2_.addChatMessage(new ChatComponentText("Selection filled!"));
			}
		}
    	
        return true;
    }

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(stack.stackTagCompound == null)
		{
			stack.stackTagCompound = new NBTTagCompound();
		}
		if(player.isSneaking())
		{
			stack.stackTagCompound.setInteger("block", 0);
			stack.stackTagCompound.setInteger("meta", 0);
			if(world.isRemote)
				player.addChatMessage(new ChatComponentText("Set block " + Block.getBlockById(stack.stackTagCompound.getInteger("block")).getUnlocalizedName()));
		}
				
		return stack;
	}

}

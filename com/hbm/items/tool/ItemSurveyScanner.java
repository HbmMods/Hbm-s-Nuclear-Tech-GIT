package com.hbm.items.tool;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IBomb;
import com.hbm.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class ItemSurveyScanner extends Item {

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{
		list.add("Right click to perform scan.");
		list.add("Shift click to change mode.");
		list.add("Current mode: " + (getMode(stack) == 0 ? "Resources" : "Structures"));
	}
	
	public int getMode(ItemStack stack) {
		if(!stack.hasTagCompound()) {
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setInteger("mode", 0);
			return 0;
		} else {
			return stack.stackTagCompound.getInteger("mode");
		}
	}
	
	public void setMode(ItemStack stack, int mode) {
		if(!stack.hasTagCompound()) {
			stack.stackTagCompound = new NBTTagCompound();
		}
		stack.stackTagCompound.setInteger("mode", mode);
	}
	
	public int getLevel(Block b, int meta, int i) {
		
		if(i == 0) {
			int[] ids = OreDictionary.getOreIDs(new ItemStack(b, 1, meta));
				
			for(int j = 0; j < ids.length; j++) {
					
				String s = OreDictionary.getOreName(ids[j]);
					
				if(s.length() > 3 && s.substring(0, 3).equals("ore"))
					return 1;
			}
		} else {
			if(b == Blocks.planks || b == Blocks.cobblestone || b == Blocks.glass || b == Blocks.stonebrick)
				return 1;
			if(b instanceof IBomb)
				return 100;
			if(b instanceof ITileEntityProvider)
				return 10;
			if(b == Blocks.nether_brick)
				return 5;
		}
		
		return 0;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {

		if(player.isSneaking()) {
			setMode(stack, (getMode(stack) == 1 ? 0 : 1));
	    	world.playSoundAtEntity(player, "hbm:item.techBoop", 1.0F, 1.0F);

			if(world.isRemote)
			{
				player.addChatMessage(new ChatComponentText("Mode switched."));
			}
			
		} else {

			int x = (int)player.posX;
			int y = (int)player.posY;
			int z = (int)player.posZ;
			
			int level = 0;
			int xOff = -25;

			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -25), world.getBlockMetadata(x + xOff, i, z + -25), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -20), world.getBlockMetadata(x + xOff, i, z + -20), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -15), world.getBlockMetadata(x + xOff, i, z + -15), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -10), world.getBlockMetadata(x + xOff, i, z + -10), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -5), world.getBlockMetadata(x + xOff, i, z + -5), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 0), world.getBlockMetadata(x + xOff, i, z + 0), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 5), world.getBlockMetadata(x + xOff, i, z + 5), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 10), world.getBlockMetadata(x + xOff, i, z + 10), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 15), world.getBlockMetadata(x + xOff, i, z + 15), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 20), world.getBlockMetadata(x + xOff, i, z + 20), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 25), world.getBlockMetadata(x + xOff, i, z + 25), getMode(stack));
			xOff += 5;
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -25), world.getBlockMetadata(x + xOff, i, z + -25), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -20), world.getBlockMetadata(x + xOff, i, z + -20), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -15), world.getBlockMetadata(x + xOff, i, z + -15), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -10), world.getBlockMetadata(x + xOff, i, z + -10), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -5), world.getBlockMetadata(x + xOff, i, z + -5), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 0), world.getBlockMetadata(x + xOff, i, z + 0), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 5), world.getBlockMetadata(x + xOff, i, z + 5), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 10), world.getBlockMetadata(x + xOff, i, z + 10), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 15), world.getBlockMetadata(x + xOff, i, z + 15), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 20), world.getBlockMetadata(x + xOff, i, z + 20), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 25), world.getBlockMetadata(x + xOff, i, z + 25), getMode(stack));
			xOff += 5;
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -25), world.getBlockMetadata(x + xOff, i, z + -25), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -20), world.getBlockMetadata(x + xOff, i, z + -20), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -15), world.getBlockMetadata(x + xOff, i, z + -15), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -10), world.getBlockMetadata(x + xOff, i, z + -10), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -5), world.getBlockMetadata(x + xOff, i, z + -5), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 0), world.getBlockMetadata(x + xOff, i, z + 0), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 5), world.getBlockMetadata(x + xOff, i, z + 5), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 10), world.getBlockMetadata(x + xOff, i, z + 10), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 15), world.getBlockMetadata(x + xOff, i, z + 15), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 20), world.getBlockMetadata(x + xOff, i, z + 20), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 25), world.getBlockMetadata(x + xOff, i, z + 25), getMode(stack));
			xOff += 5;
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -25), world.getBlockMetadata(x + xOff, i, z + -25), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -20), world.getBlockMetadata(x + xOff, i, z + -20), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -15), world.getBlockMetadata(x + xOff, i, z + -15), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -10), world.getBlockMetadata(x + xOff, i, z + -10), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -5), world.getBlockMetadata(x + xOff, i, z + -5), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 0), world.getBlockMetadata(x + xOff, i, z + 0), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 5), world.getBlockMetadata(x + xOff, i, z + 5), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 10), world.getBlockMetadata(x + xOff, i, z + 10), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 15), world.getBlockMetadata(x + xOff, i, z + 15), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 20), world.getBlockMetadata(x + xOff, i, z + 20), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 25), world.getBlockMetadata(x + xOff, i, z + 25), getMode(stack));
			xOff += 5;
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -25), world.getBlockMetadata(x + xOff, i, z + -25), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -20), world.getBlockMetadata(x + xOff, i, z + -20), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -15), world.getBlockMetadata(x + xOff, i, z + -15), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -10), world.getBlockMetadata(x + xOff, i, z + -10), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -5), world.getBlockMetadata(x + xOff, i, z + -5), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 0), world.getBlockMetadata(x + xOff, i, z + 0), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 5), world.getBlockMetadata(x + xOff, i, z + 5), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 10), world.getBlockMetadata(x + xOff, i, z + 10), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 15), world.getBlockMetadata(x + xOff, i, z + 15), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 20), world.getBlockMetadata(x + xOff, i, z + 20), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 25), world.getBlockMetadata(x + xOff, i, z + 25), getMode(stack));
			xOff += 5;
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -25), world.getBlockMetadata(x + xOff, i, z + -25), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -20), world.getBlockMetadata(x + xOff, i, z + -20), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -15), world.getBlockMetadata(x + xOff, i, z + -15), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -10), world.getBlockMetadata(x + xOff, i, z + -10), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -5), world.getBlockMetadata(x + xOff, i, z + -5), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 0), world.getBlockMetadata(x + xOff, i, z + 0), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 5), world.getBlockMetadata(x + xOff, i, z + 5), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 10), world.getBlockMetadata(x + xOff, i, z + 10), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 15), world.getBlockMetadata(x + xOff, i, z + 15), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 20), world.getBlockMetadata(x + xOff, i, z + 20), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 25), world.getBlockMetadata(x + xOff, i, z + 25), getMode(stack));
			xOff += 5;
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -25), world.getBlockMetadata(x + xOff, i, z + -25), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -20), world.getBlockMetadata(x + xOff, i, z + -20), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -15), world.getBlockMetadata(x + xOff, i, z + -15), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -10), world.getBlockMetadata(x + xOff, i, z + -10), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -5), world.getBlockMetadata(x + xOff, i, z + -5), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 0), world.getBlockMetadata(x + xOff, i, z + 0), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 5), world.getBlockMetadata(x + xOff, i, z + 5), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 10), world.getBlockMetadata(x + xOff, i, z + 10), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 15), world.getBlockMetadata(x + xOff, i, z + 15), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 20), world.getBlockMetadata(x + xOff, i, z + 20), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 25), world.getBlockMetadata(x + xOff, i, z + 25), getMode(stack));
			xOff += 5;
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -25), world.getBlockMetadata(x + xOff, i, z + -25), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -20), world.getBlockMetadata(x + xOff, i, z + -20), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -15), world.getBlockMetadata(x + xOff, i, z + -15), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -10), world.getBlockMetadata(x + xOff, i, z + -10), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -5), world.getBlockMetadata(x + xOff, i, z + -5), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 0), world.getBlockMetadata(x + xOff, i, z + 0), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 5), world.getBlockMetadata(x + xOff, i, z + 5), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 10), world.getBlockMetadata(x + xOff, i, z + 10), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 15), world.getBlockMetadata(x + xOff, i, z + 15), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 20), world.getBlockMetadata(x + xOff, i, z + 20), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 25), world.getBlockMetadata(x + xOff, i, z + 25), getMode(stack));
			xOff += 5;
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -25), world.getBlockMetadata(x + xOff, i, z + -25), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -20), world.getBlockMetadata(x + xOff, i, z + -20), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -15), world.getBlockMetadata(x + xOff, i, z + -15), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -10), world.getBlockMetadata(x + xOff, i, z + -10), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -5), world.getBlockMetadata(x + xOff, i, z + -5), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 0), world.getBlockMetadata(x + xOff, i, z + 0), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 5), world.getBlockMetadata(x + xOff, i, z + 5), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 10), world.getBlockMetadata(x + xOff, i, z + 10), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 15), world.getBlockMetadata(x + xOff, i, z + 15), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 20), world.getBlockMetadata(x + xOff, i, z + 20), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 25), world.getBlockMetadata(x + xOff, i, z + 25), getMode(stack));
			xOff += 5;
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -25), world.getBlockMetadata(x + xOff, i, z + -25), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -20), world.getBlockMetadata(x + xOff, i, z + -20), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -15), world.getBlockMetadata(x + xOff, i, z + -15), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -10), world.getBlockMetadata(x + xOff, i, z + -10), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -5), world.getBlockMetadata(x + xOff, i, z + -5), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 0), world.getBlockMetadata(x + xOff, i, z + 0), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 5), world.getBlockMetadata(x + xOff, i, z + 5), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 10), world.getBlockMetadata(x + xOff, i, z + 10), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 15), world.getBlockMetadata(x + xOff, i, z + 15), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 20), world.getBlockMetadata(x + xOff, i, z + 20), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 25), world.getBlockMetadata(x + xOff, i, z + 25), getMode(stack));
			xOff += 5;
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -25), world.getBlockMetadata(x + xOff, i, z + -25), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -20), world.getBlockMetadata(x + xOff, i, z + -20), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -15), world.getBlockMetadata(x + xOff, i, z + -15), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -10), world.getBlockMetadata(x + xOff, i, z + -10), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + -5), world.getBlockMetadata(x + xOff, i, z + -5), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 0), world.getBlockMetadata(x + xOff, i, z + 0), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 5), world.getBlockMetadata(x + xOff, i, z + 5), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 10), world.getBlockMetadata(x + xOff, i, z + 10), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 15), world.getBlockMetadata(x + xOff, i, z + 15), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 20), world.getBlockMetadata(x + xOff, i, z + 20), getMode(stack));
			for(int i =  y + 15; i > 5; i--)
				level += getLevel(world.getBlock(x + xOff, i, z + 25), world.getBlockMetadata(x + xOff, i, z + 25), getMode(stack));
			
	    	world.playSoundAtEntity(player, "hbm:item.techBleep", 1.0F, 1.0F);
	    	
			if(world.isRemote)
			{
				player.addChatMessage(new ChatComponentText("Scanned! Result: " + level));
			}
		}
		
		player.swingItem();
		
		return stack;
		
	}
	
    @Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int i, float f0, float f1, float f2)
    {
    	if(world.getBlock(x, y, z) == ModBlocks.block_beryllium && player.inventory.hasItem(ModItems.polaroid)) {
    		player.travelToDimension(1);
    		return true;
    	}
    	
    	return false;
    }
}
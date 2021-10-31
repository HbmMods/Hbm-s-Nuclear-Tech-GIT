package com.hbm.items.special;

import java.util.List;

import com.hbm.lib.RefStrings;
import com.hbm.main.ModEventHandler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemFilm extends Item {
	
	private IIcon filmUsed;
	
	public int contamLevel = 0;
	public int speckCount = 0;
	public int specks = 0;
	// i know this is convoluted as shit but i really fuckin can't tonight i apologise
	// one day i may look back upon my pheocode and i will weep
	
	// g?
	
	@Override
	public void onUpdate(ItemStack item, World world, Entity entity, int i, boolean b) {
		if(entity.isWet()) {
			if(item.stackTagCompound == null) {
				item.stackTagCompound = new NBTTagCompound();
				item.stackTagCompound.setInteger("speckCount", 0);
			}
			speckCount = item.stackTagCompound.getInteger("speckCount");
			contamLevel =  Math.round(ModEventHandler.nukeDetCount / 50);
			if(contamLevel > speckCount) {
				speckCount = contamLevel;
				item.stackTagCompound.setInteger("speckCount", speckCount);
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		int specks = (stack.stackTagCompound == null) ? 0 : stack.stackTagCompound.getInteger("speckCount");
		list.add(EnumChatFormatting.YELLOW + "Hold in water to detect");
		list.add(EnumChatFormatting.YELLOW + "global fallout contamination");
		if(specks > 0) {
			list.add("");
			if(specks == 1) {
				list.add("You look at the film and see 1 grey speck.");
			} else {
				list.add("You look at the film and see "+specks+" grey specks.");
			}
		}
				
	}
	
	/*@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack stack, int renderPass) {
		int specks = (stack.stackTagCompound == null) ? 0 : stack.stackTagCompound.getInteger("speckCount");
		return (specks > 0) ? this.filmUsed : this.itemIcon;
	}*/
}
package com.hbm.items.tool;

import java.util.List;

import com.hbm.blocks.bomb.LaunchPad;
import com.hbm.lib.HbmCollection;
import com.hbm.util.I18nUtil;

import api.hbm.item.IDesignatorItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemDesingator extends Item implements IDesignatorItem {

	@Override
<<<<<<< HEAD
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		if(itemstack.stackTagCompound != null)
		{
			list.add(I18nUtil.resolveKey(HbmCollection.tarCoord));
			list.add("X: " + String.valueOf(itemstack.stackTagCompound.getInteger("xCoord")));
			list.add("Z: " + String.valueOf(itemstack.stackTagCompound.getInteger("zCoord")));
=======
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		if(itemstack.stackTagCompound != null) {
			list.add("Target Coordinates:");
			list.add("X: " + itemstack.stackTagCompound.getInteger("xCoord"));
			list.add("Z: " + itemstack.stackTagCompound.getInteger("zCoord"));
>>>>>>> master
		} else {
//			list.add("Please select a target.");
			list.add(I18nUtil.resolveKey(HbmCollection.noPos));
		}
	}

	@Override
<<<<<<< HEAD
    public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
		if(!(p_77648_3_.getBlock(p_77648_4_, p_77648_5_, p_77648_6_) instanceof LaunchPad))
		{
			if(p_77648_1_.stackTagCompound != null)
			{
				p_77648_1_.stackTagCompound.setInteger("xCoord", p_77648_4_);
				p_77648_1_.stackTagCompound.setInteger("zCoord", p_77648_6_);
			} else {
				p_77648_1_.stackTagCompound = new NBTTagCompound();
				p_77648_1_.stackTagCompound.setInteger("xCoord", p_77648_4_);
				p_77648_1_.stackTagCompound.setInteger("zCoord", p_77648_6_);
			}
	        if(p_77648_3_.isRemote)
			{
	        	p_77648_2_.addChatMessage(new ChatComponentText(HbmCollection.posSet));
=======
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		
		if(!(world.getBlock(x, y, z) instanceof LaunchPad)) {

			if(stack.stackTagCompound == null)
				stack.stackTagCompound = new NBTTagCompound();
			
			stack.stackTagCompound.setInteger("xCoord", x);
			stack.stackTagCompound.setInteger("zCoord", z);
			
			if(world.isRemote) {
				player.addChatMessage(new ChatComponentText("Position set!"));
>>>>>>> master
			}

			world.playSoundAtEntity(player, "hbm:item.techBleep", 1.0F, 1.0F);

			return true;
		}

		return false;
	}

	@Override
	public boolean isReady(World world, ItemStack stack, int x, int y, int z) {
		return stack.hasTagCompound();
	}

	@Override
	public Vec3 getCoords(World world, ItemStack stack, int x, int y, int z) {
		return Vec3.createVectorHelper(stack.stackTagCompound.getInteger("xCoord"), 0, stack.stackTagCompound.getInteger("zCoord"));
	}
}

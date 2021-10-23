package com.hbm.items.tool;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.lib.HbmCollection;
import com.hbm.main.MainRegistry;
import com.hbm.util.I18nUtil;

import api.hbm.item.IDesignatorItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemDesingatorManual extends Item implements IDesignatorItem
{

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {

		if(world.isRemote)
			player.openGui(MainRegistry.instance, ModItems.guiID_item_designator, world, 0, 0, 0);

		return stack;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{
		if(stack.stackTagCompound != null)
		{
			list.add(I18nUtil.resolveKey(HbmCollection.tarSet));
			list.add("X: " + String.valueOf(stack.stackTagCompound.getInteger("xCoord")));
			list.add("Z: " + String.valueOf(stack.stackTagCompound.getInteger("zCoord")));
		}
		else 
			list.add(I18nUtil.resolveKey(HbmCollection.noPos));
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

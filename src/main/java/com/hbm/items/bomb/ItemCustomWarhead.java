package com.hbm.items.bomb;

import java.util.List;

import com.hbm.interfaces.ICustomWarhead;
import com.hbm.items.special.ItemWithSubtypes;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemCustomWarhead extends ItemWithSubtypes implements ICustomWarhead
{

	public ItemCustomWarhead()
	{
		super("warhead_gravimetric", "warhead_fusion", "warhead_chemical", "warhead_biological", "warhead_mirv");
		setMaxStackSize(1);
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		super.addInformation(itemstack, player, list, bool);
		addTooltip(itemstack, list);
	}
	// Why did I make this anyway?
	@Override
	public ICustomWarhead getInstance()
	{
		return this;
	}
	
	@Override
	public ItemStack constructNew()
	{
		ItemStack toOut = new ItemStack(this);
		toOut.stackTagCompound = new NBTTagCompound();
		NBTTagCompound data = new NBTTagCompound();
		toOut.stackTagCompound.setTag(NBT_GROUP, data);
		return toOut;
	}

	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List tabList)
	{
		final ItemStack[] warheads = {CustomWarheadWrapper.gravimetricBase.getStack(), CustomWarheadWrapper.pureFusionBase.getStack(), CustomWarheadWrapper.chemicalBase.getStack(), CustomWarheadWrapper.biologicalBase.getStack(), CustomWarheadWrapper.saltedBase.getStack()};
		for (ItemStack w : warheads)
			tabList.add(w);
	}
	
}

package com.hbm.items.bomb;

import java.util.List;

import javax.annotation.CheckForNull;
import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;

import com.google.common.annotations.Beta;
import com.hbm.interfaces.ICustomWarhead;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemHazard;
import com.hbm.lib.HbmCollection;
import com.hbm.util.I18nUtil;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
@Beta
public class ItemCustomCore extends ItemHazard implements ICustomWarhead
{
	public ItemCustomCore()
	{
		// TODO Auto-generated constructor stub
		addRadiation(5.0F);
		setMaxStackSize(1);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{
		super.addInformation(stack, player, list, bool);
//		list.add(I18nUtil.resolveKey(HbmCollection.lshift, "to see extended information"));
//		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
			addTooltip(stack, list);
	}
	
	@Override
	public float getYield() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ICustomWarhead getInstance()
	{
		return (ICustomWarhead) this;
	}
	
	@Override
	public EnumCustomWarhead getWarheadType(NBTTagCompound data)
	{
		return EnumCustomWarhead.NUCLEAR;
	}

	@Override
	public EnumWeaponType getWeaponType(NBTTagCompound data)
	{
		return EnumWeaponType.WMD;
	}

	@Override
	public EnumCustomWarheadTrait getWeaponTrait(NBTTagCompound data)
	{
		return EnumCustomWarheadTrait.RAD;
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
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		// TODO
		final ItemStack[] cores = {new CustomWarheadWrapper(item).addFuel(FissileFuel.U235, FissileFuel.U235.getIngotMass() * 4).getStack(), new CustomWarheadWrapper(item).addFuel(FissileFuel.Pu239, FissileFuel.Pu239.getIngotMass() * 4).getStack(), new CustomWarheadWrapper(item).addFuel(FissileFuel.U233, FissileFuel.U233.getIngotMass() * 3.6F).addFuel(FissileFuel.U235, FissileFuel.U235.getNuggetMass() * 3).getStack(), new CustomWarheadWrapper(item).addFuel(FissileFuel.Pu239, FissileFuel.Pu239.getIngotMass() * 2).addFuel(FissileFuel.Sa326, FissileFuel.Sa326.getNuggetMass()).getStack()};
		for (ItemStack core : cores)
			list.add(core);
	}
	
}

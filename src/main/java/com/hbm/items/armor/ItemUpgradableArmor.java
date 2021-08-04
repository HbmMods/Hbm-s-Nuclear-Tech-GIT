package com.hbm.items.armor;

import java.util.List;

import com.hbm.items.special.ItemCustomLore;
import com.hbm.items.special.ItemWithSubtypes;
import com.hbm.lib.RefStrings;
import com.hbm.util.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;

public class ItemUpgradableArmor extends ItemWithSubtypes
{
	public ItemUpgradableArmor(int size)
	{
		super(size);
		setHasSubtypes(true);
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		super.addInformation(itemstack, player, list, bool);
		list.add(I18nUtil.resolveKey(this.getUnlocalizedName() + ".desc.0", itemstack.getItemDamage() + 1));
	}
}

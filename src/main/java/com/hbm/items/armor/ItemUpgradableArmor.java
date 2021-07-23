package com.hbm.items.armor;

import java.util.List;

import com.hbm.items.special.ItemCustomLore;
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

public class ItemUpgradableArmor extends ItemCustomLore
{
	@SideOnly(Side.CLIENT)
	private IIcon[] itemIcon;
	private int setSize;
	public ItemUpgradableArmor(int size)
	{
		super();
		setSize = size;
		setHasSubtypes(true);
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		super.addInformation(itemstack, player, list, bool);
		list.add(I18nUtil.resolveKey(this.getUnlocalizedName() + ".desc.0", itemstack.getItemDamage() + 1));
	}
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister registry)
	{
		itemIcon = new IIcon[setSize];
		for (int i = 0; i < itemIcon.length; i++)
			itemIcon[i] = registry.registerIcon(String.format("%s:%s_%s", RefStrings.MODID, this.getUnlocalizedName().substring(5), i));
	}
	
	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List tabList)
	{
		for (int i = 0; i < setSize; i++)
			tabList.add(new ItemStack(itemIn, 1, i));
	}
	
	@Override
	public IIcon getIconFromDamage(int icon)
	{
		int i = MathHelper.clamp_int(icon, 0, setSize);
		return itemIcon[i];
	}
}

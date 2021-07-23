package com.hbm.items.special;

import java.util.List;

import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;

public class Orichalcum extends ItemCustomLore
{
	private static final int setSize = 5;
	@SideOnly(Side.CLIENT)
	private IIcon[] itemIcon;
	public Orichalcum()
	{
		super();
		setHasSubtypes(true);
	}
	
	@Override
	public void registerIcons(IIconRegister registry)
	{
		itemIcon = new IIcon[setSize];
		for (int i = 0; i < setSize; i++)
			itemIcon[i] = registry.registerIcon(String.format("%s:orichalcum_%s", RefStrings.MODID, i));
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
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		switch (stack.getItemDamage())
		{
		case 0:
			return "item.ingot_orichalcum";
		case 1:
			return "item.gem_orichalcum";
		case 2:
			return "item.ingot_orichalcum_small";
		case 3:
			return "item.powder_orichalcum_mix";
		case 4:
			return "item.ingot_orichalcum_small_irr";
		default:
			return "invalid meta tag";
		}
	}
}

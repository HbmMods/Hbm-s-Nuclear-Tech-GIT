package com.hbm.items;

import com.hbm.util.EnumUtil;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemEnumMultiSimple extends ItemEnumMulti
{

	public ItemEnumMultiSimple(Class<? extends Enum<?>> theEnum, boolean multiName, boolean multiTexture)
	{
		super(theEnum, multiName, multiTexture);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		if (multiName)
		{
			Enum<?> e = EnumUtil.grabEnumSafely(theEnum, stack.getItemDamage());
			return "item." + e.toString().toLowerCase();
		}
		else
			return super.getUnlocalizedName();
	}
	
	@Override
	public void registerIcons(IIconRegister reg)
	{
		if (multiTexture)
		{
			Enum<?>[] enums = theEnum.getEnumConstants();
			icons = new IIcon[enums.length];
			for (int i = 0; i < icons.length; i++)
			{
				Enum<?> num = enums[i];
				icons[i] = reg.registerIcon("hbm:" + num.toString().toLowerCase());
			}
		}
		else
			itemIcon = reg.registerIcon(getIconString());
	}
}

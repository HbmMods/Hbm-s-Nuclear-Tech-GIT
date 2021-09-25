package com.hbm.interfaces;

import java.util.List;

import com.hbm.items.special.ItemCustomLore;
import com.hbm.lib.Library;
import com.hbm.util.I18nUtil;

import net.minecraft.item.ItemStack;

public interface IHasLore 
{
	/**
	 * Automatically add tooltip info from the lang file
	 * @param stack - The itemstack the tooltip will be added to
	 * @param list - The tooltip list
	 */
	public default void standardLore(ItemStack stack, List list)
	{
		if (ItemCustomLore.getHasLore(stack))
		{
			String loc = ItemCustomLore.getLoc(stack);
			
			if(loc != null)
			{
				 String[] locs = loc.split("\\$");
				 for (String s : locs)
					 list.add(s);
			}
		}
	}
	
	public default void customLore(String key, List list, Object...args)
	{
		if (ItemCustomLore.getHasLore(key))
		{
			String[] locs = I18nUtil.resolveKeyArray(key, args);
			if (!Library.isArrayEmpty(locs))
				for (String s : locs)
					list.add(s);
		}
	}
}

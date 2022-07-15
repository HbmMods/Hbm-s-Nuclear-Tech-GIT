package com.hbm.interfaces;

import java.util.List;

import com.hbm.items.ItemCustomLore;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.util.I18nUtil;

import net.minecraft.item.ItemStack;
/**
 * For things that can't extend {@link#ItemCustomLore}
 * @author UFFR
 *
 */
public interface IHasLore 
{
	/**
	 * Automatically add tooltip info from the lang file
	 * @param stack - The itemstack the tooltip will be added to
	 * @param list - The tooltip list
	 */
	public default void standardLore(ItemStack stack, List<String> list)
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
	
	public default void customLore(String key, List<String> list, Object...args)
	{
		if (ItemCustomLore.getHasLore(key))
		{
			String[] locs = I18nUtil.resolveKeyArray(key, args);
			if (!Library.isArrayEmpty(locs))
				for (String s : locs)
					list.add(s);
		}
	}
	
	public static boolean getHasLore(String ulocIn)
	{
		String uloc = ulocIn.concat(MainRegistry.isPolaroid11() ? ".desc.11" : ".desc");
		String loc = I18nUtil.resolveKey(uloc);
		if (loc.equals(uloc))
			uloc = ulocIn.concat(".desc");
		else
			return true;
		loc = I18nUtil.resolveKey(uloc);
		return !uloc.equals(loc);
	}
	
	public static boolean keyExists(String key)
	{
		String loc = I18nUtil.resolveKey(key);
		return !(loc.equals(key));
	}
}

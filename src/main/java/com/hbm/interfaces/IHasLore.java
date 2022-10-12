package com.hbm.interfaces;

import java.util.Collections;
import java.util.List;

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
		if (getHasLore(stack))
		{
			final String loc = getLoc(stack);
			final String[] locs = loc.split("\\$");
			if (!Library.isArrayEmpty(locs))
				 Collections.addAll(list, locs);
		}
	}
	
	public default void customLore(String key, List<String> list, Object...args)
	{
		if (getHasLore(key))
		{
			final String[] locs = I18nUtil.resolveKeyArray(key, args);
			if (!Library.isArrayEmpty(locs))
				Collections.addAll(list, locs);
		}
	}
	
	public static String getLoc(ItemStack stack)
	{
		return getLoc(stack.getUnlocalizedName());
	}
	
	public static String getLoc(String ulocIn)
	{
		String uloc = ulocIn.concat(MainRegistry.isPolaroid11() ? ".desc.11" : ".desc"), loc = I18nUtil.resolveKey(uloc);
		if (loc.equals(uloc))
			uloc = ulocIn.concat(".desc");
		else
			return loc;
		loc = I18nUtil.resolveKey(uloc);
		return loc;
	}
	
	public static boolean getHasLore(ItemStack stack)
	{
		return getHasLore(stack.getUnlocalizedName());
	}
	
	public static boolean getHasLore(String ulocIn)
	{
		return !getLoc(ulocIn).equals(ulocIn + (MainRegistry.isPolaroid11() ? ".desc.11" : ".desc"));
	}
	
	public static boolean keyExists(String key)
	{
		String loc = I18nUtil.resolveKey(key);
		return !(loc.equals(key));
	}
}

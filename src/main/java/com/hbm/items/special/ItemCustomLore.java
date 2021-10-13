package com.hbm.items.special;

import java.util.List;
import java.util.Random;

import com.hbm.config.GeneralConfig;
import com.hbm.interfaces.IHasLore;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.util.ArmorUtil;
import com.hbm.util.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemCustomLore extends Item implements IHasLore
{
	EnumRarity rarity;
	boolean hasEffect = false;
	public String basicLore = new String();
	/** New item with custom lore, assumes that it is in the localization file
	 * Allows rarity and shimmer effect **/
	public ItemCustomLore() {}
	/** New item with manually inserted lore
	 * Allows rarity and shimmer effect
	 * @param lore - The tooltip to be added using the localization
	 */
	public ItemCustomLore(String lore)
	{
		this.basicLore = lore;
	}
	
	EnumRarity rarity;
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		if (!basicLore.isEmpty())
			list.add(I18nUtil.resolveKey(basicLore));
//		String unloc;
//		if (MainRegistry.isPolaroid11)
//			unloc = this.getUnlocalizedName(itemstack) + ".desc.11";
//		else
//			unloc = this.getUnlocalizedName(itemstack) + ".desc";
//		
//		String loc = I18nUtil.resolveKey(unloc);
//		if (unloc.equals(loc))
//		{
//			unloc = this.getUnlocalizedName(itemstack) + ".desc";
//			loc = I18nUtil.resolveKey(unloc);
//		}
//		if(!unloc.equals(loc)) {
//			
//			String[] locs = loc.split("\\$");
//			
//			for(String s : locs)
//				list.add(s);
//		}
		
		standardLore(itemstack, list);
		
		if(this == ModItems.pin)
		{
			if(ArmorUtil.checkArmorPiece(player, ModItems.jackt, 2) || ArmorUtil.checkArmorPiece(player, ModItems.jackt2, 2))
				list.add(I18nUtil.resolveKey(this.getUnlocalizedName() + "desc.100"));
			else
				list.add(I18nUtil.resolveKey(this.getUnlocalizedName() + "desc.10"));
		}
		if (this == ModItems.ingot_schraranium)
			if(GeneralConfig.enableBabyMode)
				list.add("shut up peer please for the love of god shut up i can't stand it any longer shut up shut up shut up shut up shut up");
				
	}
	/**
	 * Check if the item has a tooltip specified by the localization file
	 * @param item - The item in question
	 * @param special - If Polaroid ID #11 applies in this case
	 * @return Boolean of whether it does or not
	 */
	public static boolean getHasLore(Item item, boolean special)
	{
		String uloc = item.getUnlocalizedName() + ".desc";
		if (special)
			uloc += ".11";
		String loc = I18nUtil.resolveKey(uloc);
		return !uloc.equals(loc);
	}
	
	public static boolean getHasLore(Item item)
	{
		return getHasLore(item.getUnlocalizedName());
	}
	
	public static boolean getHasLore(ItemStack item)
	{
		return getHasLore(item.getUnlocalizedName());
	}
	
	public static boolean getHasLore(String ulocIn)
	{
		String uloc = ulocIn.concat(MainRegistry.isPolaroid11 ? ".desc.11" : ".desc");
		String loc = I18nUtil.resolveKey(uloc);
		if (loc.equals(uloc))
			uloc = ulocIn.concat(".desc");
		else
			return true;
		loc = I18nUtil.resolveKey(uloc);
		return !uloc.equals(loc);
	}
	
	public static String getLoc(ItemStack stack)
	{
		String uloc = stack.getUnlocalizedName();
		if (!getHasLore(uloc))
			return null;
		String testKey = uloc.concat(MainRegistry.isPolaroid11 ? ".desc.11" : ".desc");
		String loc = I18nUtil.resolveKey(testKey);
		if (!loc.equals(testKey))
			return loc;
		else
		{
			testKey = uloc.concat(".desc");
			loc = I18nUtil.resolveKey(testKey);
			return (loc.equals(testKey) ? null : loc);
		}
	}
	
	public static boolean keyExists(String key)
	{
		String loc = I18nUtil.resolveKey(key);
		return !(loc == key);
	}

    @Override
	public EnumRarity getRarity(ItemStack p_77613_1_)
    {	
		return this.rarity != null ? rarity : EnumRarity.common;
    }

    @Override
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack p_77636_1_)
    {    	
    	return hasEffect;
    }
    
    public ItemCustomLore setHasEffect()
    {
    	hasEffect = true;
    	return this;
    }
    
    public ItemCustomLore setRarity(EnumRarity rarity) {
    	this.rarity = rarity;
		return this;
    }
}

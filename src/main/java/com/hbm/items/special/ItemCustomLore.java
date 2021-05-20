package com.hbm.items.special;

import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import com.hbm.config.GeneralConfig;
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

public class ItemCustomLore extends Item {
	
	EnumRarity rarity;
	boolean hasEffect = false;
	public String basicLore = null;
	/** New item with custom lore, assumes that it is in the localization file
	 * Allows rarity and shimmer effect **/
	public ItemCustomLore() {}
	/** New item with manually inserted lore
	 * Allows rarity and shimmer effect
	 * @param lore - The tooltip to be added, single line only
	 */
	public ItemCustomLore(String lore)
	{
		this.basicLore = lore;
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		String unloc;
		if (MainRegistry.polaroidID == 11)
			unloc = this.getUnlocalizedName() + ".desc.11";
		else
			unloc = this.getUnlocalizedName() + ".desc";
		
		String loc = I18nUtil.resolveKey(unloc);
		if (unloc.equals(loc))
		{
			unloc = this.getUnlocalizedName() + ".desc";
			loc = I18nUtil.resolveKey(unloc);
		}
		if(!unloc.equals(loc)) {
			
			String[] locs = loc.split("\\$");
			
			for(String s : locs) {
				list.add(s);
			}
		}
		if (basicLore != null)
		{
			list.add(basicLore);
		}
		if(this == ModItems.pin)
		{
			if(ArmorUtil.checkArmorPiece(player, ModItems.jackt, 2) || ArmorUtil.checkArmorPiece(player, ModItems.jackt2, 2))
				list.add(I18nUtil.resolveKey(this.getUnlocalizedName() + "desc.100"));
			else
				list.add(I18nUtil.resolveKey(this.getUnlocalizedName() + "desc.10"));
		}
		if(this == ModItems.ingot_schraranium)
		{
			if(GeneralConfig.enableBabyMode)
				list.add("shut up peer please for the love of god shut up i can't stand it any longer shut up shut up shut up shut up shut up");
		}
				
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

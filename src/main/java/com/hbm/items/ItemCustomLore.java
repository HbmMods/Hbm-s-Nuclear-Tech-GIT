package com.hbm.items;

import java.util.List;
import java.util.Random;

import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.util.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemCustomLore extends Item {
	
	protected EnumRarity rarity;
	protected boolean hasEffect = false;
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		
		boolean p11 = !I18nUtil.resolveKey(this.getUnlocalizedName() + ".desc.P11").equals(this.getUnlocalizedName() + ".desc.P11");
		
		if(MainRegistry.polaroidID == 11 && p11) {
			String unlocP11 = this.getUnlocalizedName() + ".desc.P11";
			String locP11 = I18nUtil.resolveKey(unlocP11);
			
			if(!unlocP11.equals(locP11)) {
				String[] locsP11 = locP11.split("\\$");
				
				for(String s : locsP11) {
					list.add(s);
				}
			}
		} else {
			String unloc = this.getUnlocalizedName() + ".desc";
			String loc = I18nUtil.resolveKey(unloc);
			
			if(!unloc.equals(loc)) {
				String[] locs = loc.split("\\$");
				
				for(String s : locs) {
					list.add(s);
				}
			}
		}
		
		if(this == ModItems.undefined) {
			
			try {
				if(player.worldObj.rand.nextInt(10) == 0) {
					list.add(EnumChatFormatting.DARK_RED + "UNDEFINED");
				} else {
					Random rand = new Random(System.currentTimeMillis() / 500);
					
					if(setSize == 0)
						setSize = Item.itemRegistry.getKeys().size();
					
					int r = rand.nextInt(setSize);
					
					Item item = Item.getItemById(r);
					
					if(item != null) {
						list.add(new ItemStack(item).getDisplayName());
					} else {
						list.add(EnumChatFormatting.RED + "ERROR #" + r);
					}
				}
			} catch(Exception ex) {
				list.add(EnumChatFormatting.DARK_RED + "UNDEFINED");
			}
		}
	}
	
	static int setSize = 0;

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return this.rarity != null ? rarity : super.getRarity(stack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {
		return hasEffect;
	}

	public ItemCustomLore setRarity(EnumRarity rarity) {
		this.rarity = rarity;
		return this;
	}

	public ItemCustomLore setEffect() {
		this.hasEffect = true;
		return this;
	}
	
	@Override
	public Item setUnlocalizedName(String uloc) {
		setTextureName(RefStrings.MODID + ':' + uloc);
		return super.setUnlocalizedName(uloc);
	}
}

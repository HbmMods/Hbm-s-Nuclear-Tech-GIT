package com.hbm.items.special;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import com.hbm.util.I18nUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemAMSCore extends Item {

	long powerBase;
	int heatBase;
	int fuelBase;
	
	public ItemAMSCore(long powerBase, int heatBase, int fuelBase) {
		this.powerBase = powerBase;
		this.heatBase = heatBase;
		this.fuelBase = fuelBase;
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		if (this == ModItems.ams_core_sing) {
			for(String s : I18nUtil.resolveKeyArray("item.ams_core_sing.desc"))
				list.add(s);
		}

		if (this == ModItems.ams_core_wormhole) {
			for(String s : I18nUtil.resolveKeyArray("item.ams_core_wormhole.desc"))
				list.add(s);
		}

		if (this == ModItems.ams_core_eyeofharmony) {
			for(String s : I18nUtil.resolveKeyArray("item.ams_core_eyeofharmony.desc"))
				list.add(s);
		}

		if (this == ModItems.ams_core_thingy) {
			if(MainRegistry.polaroidID == 11) {
				list.add(I18nUtil.resolveKey("item.ams_core_thingy_hide.desc"));
			} else {
				for(String s : I18nUtil.resolveKeyArray("item.ams_core_thingy.desc"))
					list.add(s);
			}
		}
	}

    @Override
	public EnumRarity getRarity(ItemStack p_77613_1_) {

    	if(this == ModItems.ams_core_thingy)
    	{
    		return EnumRarity.epic;
    	}
    	
    	return EnumRarity.uncommon;
    }

    @Override
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack p_77636_1_)
    {
    	if(this == ModItems.ams_core_thingy && MainRegistry.polaroidID == 11)
    	{
    		return true;
    	}
    	
    	return false;
    }
    
    public static long getPowerBase(ItemStack stack) {
		if(stack == null || !(stack.getItem() instanceof ItemAMSCore))
			return 0;
		return ((ItemAMSCore)stack.getItem()).powerBase;
    }
    
    public static int getHeatBase(ItemStack stack) {
		if(stack == null || !(stack.getItem() instanceof ItemAMSCore))
			return 0;
		return ((ItemAMSCore)stack.getItem()).heatBase;
    }
    
    public static int getFuelBase(ItemStack stack) {
		if(stack == null || !(stack.getItem() instanceof ItemAMSCore))
			return 0;
		return ((ItemAMSCore)stack.getItem()).fuelBase;
    }
}

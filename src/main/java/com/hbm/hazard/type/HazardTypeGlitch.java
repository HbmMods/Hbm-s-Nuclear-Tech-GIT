package com.hbm.hazard.type;

import java.util.List;
import java.util.Random;


import com.hbm.config.RadiationConfig;
import com.hbm.hazard.modifier.HazardModifier;
import com.hbm.util.ArmorRegistry;
import com.hbm.util.I18nUtil;
import com.hbm.util.ArmorRegistry.HazardClass;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;

public class HazardTypeGlitch extends HazardTypeBase{
	
	@Override
	public void onUpdate(EntityLivingBase target, float level, ItemStack stack) {
		
		if(RadiationConfig.disableBlinding)
			return;

		if(!ArmorRegistry.hasProtection(target, 3, HazardClass.LIGHT)) {
			target.addPotionEffect(new PotionEffect(Potion.confusion.id, (int)Math.ceil(level), 0));
		}
	}

	@Override
	public void updateEntity(EntityItem item, float level) { }
	 
	@Override
	public void addHazardInformation(EntityPlayer player, List list, float level, ItemStack stack, List<HazardModifier> modifiers) {
		if(player.worldObj.rand.nextInt(10) == 0) {
			list.add(EnumChatFormatting.DARK_RED + "WORLD");
		} else {
			Random rand = new Random(System.currentTimeMillis() / 500);
			
			if(setSize == 0)
				setSize = Item.itemRegistry.getKeys().size();
			
			int r = rand.nextInt(setSize);
			
			Item item = Item.getItemById(r);
			
			if(item != null) {
				list.add(new ItemStack(item).getDisplayName());
			} else {
				list.add(EnumChatFormatting.RED + "STACKTRACE0x00" + r);
			}
		}
	}
	static int setSize = 0;
}
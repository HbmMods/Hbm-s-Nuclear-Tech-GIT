package com.hbm.items.special;

import java.util.List;

import com.hbm.main.MainRegistry;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemPolaroid extends Item {

    @Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean b) {
    	if(entity instanceof EntityPlayer)
    		if(((EntityPlayer)entity).getHealth() < 10F) {
    			((EntityPlayer) entity).addPotionEffect(new PotionEffect(Potion.resistance.id, 10, 2));
    		}
    }

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List tooltip, boolean advanced) {
		tooltip.add(I18nUtil.resolveKey("item.polaroid.desc."));
		tooltip.add("");

		String baseKey = this.getUnlocalizedName(stack) + ".desc";
		String[] extraLines = I18nUtil.resolveKeyArray(baseKey);

		for (String line : extraLines) {
			tooltip.add(line);
		}
	}
}

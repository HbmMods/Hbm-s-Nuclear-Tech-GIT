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
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add(I18nUtil.resolveKey("item.polaroid.desc."));
		list.add("");
		switch(MainRegistry.polaroidID) {
			case 1:
				list.add(I18nUtil.resolveKey("item.polaroid.desc.1"));
				break;
			case 2:
				list.add(I18nUtil.resolveKey("item.polaroid.desc.2"));
				break;
			case 3:
				list.add(I18nUtil.resolveKey("item.polaroid.desc.3"));
				break;
			case 4:
				list.add(I18nUtil.resolveKey("item.polaroid.desc.4"));
				break;
			case 5:
				list.add(I18nUtil.resolveKey("item.polaroid.desc.5"));
				break;
			case 6:
				list.add(I18nUtil.resolveKey("item.polaroid.desc.6"));
				break;
			case 7:
				list.add(I18nUtil.resolveKey("item.polaroid.desc.7"));
				break;
			case 8:
				list.add(I18nUtil.resolveKey("item.polaroid.desc.8"));
				break;
			case 9:
				list.add(I18nUtil.resolveKey("item.polaroid.desc.9"));
				break;
			case 10:
				list.add(I18nUtil.resolveKey("item.polaroid.desc.10"));
				break;
			case 11:
				list.add(I18nUtil.resolveKey("item.polaroid.desc.11"));
				break;
			case 12:
				list.add(I18nUtil.resolveKey("item.polaroid.desc.12"));
				break;
			case 13:
				list.add(I18nUtil.resolveKey("item.polaroid.desc.13"));
				break;
			case 14:
				list.add(I18nUtil.resolveKey("item.polaroid.desc.14"));
				break;
			case 15:
				list.add(I18nUtil.resolveKey("item.polaroid.desc.15"));
				break;
			case 16:
				list.add(I18nUtil.resolveKey("item.polaroid.desc.16"));
				break;
			case 17:
				list.add(I18nUtil.resolveKey("item.polaroid.desc.17"));
				break;
			case 18:
				String[] lines = I18nUtil.resolveKeyArray("item.polaroid.desc.18");
				for (String line : lines) {
					list.add(line);
				}
				break;
		}
	}
}

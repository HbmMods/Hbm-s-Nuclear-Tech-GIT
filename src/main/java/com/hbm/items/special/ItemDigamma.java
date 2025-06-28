package com.hbm.items.special;

import java.util.List;

import com.hbm.config.WeaponConfig;
import com.hbm.entity.effect.EntityQuasar;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemDigamma extends Item {

	int digamma;

	public ItemDigamma(int digamma) {
		super();
		
		//obacht! the particle's digamma value is "ticks until half life" while the superclass' interpretation is "simply add flat value"
		this.digamma = digamma;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean b) {
		super.onUpdate(stack, world, entity, i, b);

		if(entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			ContaminationUtil.applyDigammaData(player, 1F / ((float) digamma));
		}
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {

		list.add(EnumChatFormatting.GOLD + I18nUtil.resolveKey("trait.hlParticle", "1.67*10³⁴a"));
		list.add(EnumChatFormatting.RED + I18nUtil.resolveKey("trait.hlPlayer", (digamma / 20F) + "s"));

		list.add("");
		super.addInformation(stack, player, list, bool);

		float d = ((int) ((1000F / digamma) * 200F)) / 10F;

		list.add(EnumChatFormatting.RED + "[" + I18nUtil.resolveKey("trait.digamma") + "]");
		list.add(EnumChatFormatting.DARK_RED + "" + d + "mDRX/s");

		list.add(EnumChatFormatting.RED + "[" + I18nUtil.resolveKey("trait.drop") + "]");
	}

	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem) {
		if(entityItem != null) {

			if(entityItem.onGround && !entityItem.worldObj.isRemote) {

				if(WeaponConfig.dropSing) {
					EntityQuasar bl = new EntityQuasar(entityItem.worldObj, 5F);
					bl.posX = entityItem.posX;
					bl.posY = entityItem.posY;
					bl.posZ = entityItem.posZ;
					entityItem.worldObj.spawnEntityInWorld(bl);
				}

				entityItem.setDead();

				return true;
			}
		}

		return false;
	}
}

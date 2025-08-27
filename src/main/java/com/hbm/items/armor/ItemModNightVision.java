package com.hbm.items.armor;

import com.hbm.extprop.HbmPlayerProps;
import com.hbm.handler.ArmorModHandler;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public class ItemModNightVision extends ItemArmorMod {
	private static final String NIGHT_VISION_ACTIVE_NBT_KEY = "ITEM_MOD_NV_ACTIVE";

	public ItemModNightVision() {
		super(ArmorModHandler.helmet_only, true, false, false, false);
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		list.add(EnumChatFormatting.AQUA + I18nUtil.format("item.night_vision.description.item"));
		list.add("");
		super.addInformation(itemstack, player, list, bool);
	}

	@Override
	public void addDesc(List list, ItemStack stack, ItemStack armor) {
		list.add(EnumChatFormatting.YELLOW + I18nUtil.format("item.night_vision.description.in_armor", stack.getDisplayName()));
	}

	@Override
	public void modUpdate(EntityLivingBase entity, ItemStack armor) {
		if(!entity.worldObj.isRemote && entity instanceof EntityPlayer && armor.getItem() instanceof ArmorFSBPowered && ArmorFSBPowered.hasFSBArmor((EntityPlayer) entity)) {
			if(HbmPlayerProps.getData(((EntityPlayer) entity)).enableHUD) {
				// 15 seconds to make less flickering if the client lags
				entity.addPotionEffect(new PotionEffect(Potion.nightVision.id, 15 * 20, 0));
				if(!armor.hasTagCompound()) {
					armor.setTagCompound(new NBTTagCompound());
				}
				if(!armor.getTagCompound().hasKey(NIGHT_VISION_ACTIVE_NBT_KEY)) {
					armor.getTagCompound().setBoolean(NIGHT_VISION_ACTIVE_NBT_KEY, true); // Value does not matter, it's just a flag
				}

				if (entity.getRNG().nextInt(100) == 0) {
					armor.damageItem(1, entity);
				}
			} else if(armor.hasTagCompound() && armor.getTagCompound().hasKey(NIGHT_VISION_ACTIVE_NBT_KEY)) { // Disable night vision if it was the armor mod that applied it to avoid removing other night vision sources.
				entity.removePotionEffect(Potion.nightVision.id);
				armor.getTagCompound().removeTag(NIGHT_VISION_ACTIVE_NBT_KEY);
			}
		}
	}
}

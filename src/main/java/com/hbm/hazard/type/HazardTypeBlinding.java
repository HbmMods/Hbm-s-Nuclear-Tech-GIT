package com.hbm.hazard.type;

import java.util.List;

import com.hbm.hazard.modifier.HazardModifier;
import com.hbm.util.ArmorRegistry;
import com.hbm.util.I18nUtil;
import com.hbm.util.ArmorRegistry.HazardClass;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;

public class HazardTypeBlinding extends HazardTypeBase {

	@Override
	public void onUpdate(EntityLivingBase target, float level, ItemStack stack) {

		if(!ArmorRegistry.hasProtection(target, 3, HazardClass.LIGHT)) {
			target.addPotionEffect(new PotionEffect(Potion.blindness.id, (int)level, 0));
		}
	}

	@Override
	public void updateEntity(EntityItem item, float level)
	{
		final List<EntityLivingBase> entities = item.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(level, level, level, level, level, level));
		for (EntityLivingBase e : entities)
			e.addPotionEffect(new PotionEffect(Potion.blindness.id, (int) (level * (level / e.getDistanceToEntity(item)))));
	}

	@Override
	public void addHazardInformation(EntityPlayer player, List<String> list, float level, ItemStack stack, List<HazardModifier> modifiers) {
		list.add(EnumChatFormatting.DARK_AQUA + "[" + I18nUtil.resolveKey("trait.blinding") + "]");
	}

}

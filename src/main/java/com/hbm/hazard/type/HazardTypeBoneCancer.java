package com.hbm.hazard.type;

import java.util.List;

import com.hbm.extprop.HbmLivingProps;
import com.hbm.hazard.HazardModifier;
import com.hbm.util.ArmorRegistry;
import com.hbm.util.ArmorUtil;
import com.hbm.util.I18nUtil;
import com.hbm.util.ArmorRegistry.HazardClass;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class HazardTypeBoneCancer extends HazardTypeBase {

	@Override
	public void onUpdate(EntityLivingBase target, float level, ItemStack stack) {
		
		if(!ArmorRegistry.hasProtection(target, 3, HazardClass.PARTICLE_FINE))
			HbmLivingProps.incrementBoneCancer(target, (int) level);
		else
			ArmorUtil.damageGasMaskFilter(target, (int) level);
	}

	@Override
	public void updateEntity(EntityItem item, float level) { }

	@Override
	public void addHazardInformation(EntityPlayer player, List list, float level, ItemStack stack, List<HazardModifier> modifiers) {
		list.add(EnumChatFormatting.DARK_GREEN + "[" + I18nUtil.resolveKey("trait.boneCancer") + "]");
	}
}
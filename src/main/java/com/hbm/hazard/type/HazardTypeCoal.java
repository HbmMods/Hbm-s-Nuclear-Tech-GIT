package com.hbm.hazard.type;

import java.util.List;

import com.hbm.config.RadiationConfig;
import com.hbm.extprop.HbmLivingProps;
import com.hbm.hazard.modifier.HazardModifier;
import com.hbm.util.ArmorRegistry;
import com.hbm.util.ArmorUtil;
import com.hbm.util.ArmorRegistry.HazardClass;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class HazardTypeCoal extends HazardTypeBase {

	@Override
	public void onUpdate(EntityLivingBase target, float level, ItemStack stack) {
		
		if(RadiationConfig.disableCoal)
			return;
		
		if(!ArmorRegistry.hasProtection(target, 3, HazardClass.PARTICLE_COARSE)) {
			HbmLivingProps.incrementBlackLung(target, (int) Math.min(level * stack.stackSize, 10));
		} else {
			if(target.getRNG().nextInt(Math.max(65 - stack.stackSize, 1)) == 0) {
				ArmorUtil.damageGasMaskFilter(target, (int) level);
			}
		}
	}

	@Override
	public void updateEntity(EntityItem item, float level) { }

	@Override
	public void addHazardInformation(EntityPlayer player, List list, float level, ItemStack stack, List<HazardModifier> modifiers) {
		list.add(EnumChatFormatting.DARK_GRAY + "[" + I18nUtil.resolveKey("trait.coal") + "]");
	}

}

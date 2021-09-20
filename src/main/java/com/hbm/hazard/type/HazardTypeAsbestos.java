package com.hbm.hazard.type;

import java.util.List;

import com.hbm.extprop.HbmLivingProps;
import com.hbm.hazard.HazardModifier;
import com.hbm.util.ArmorRegistry;
import com.hbm.util.ArmorUtil;
import com.hbm.util.I18nUtil;
import com.hbm.util.ArmorRegistry.HazardClass;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class HazardTypeAsbestos extends HazardTypeBase {

	@Override
	public void onUpdate(EntityLivingBase target, float level) {
		
		if(!ArmorRegistry.hasProtection(target, 3, HazardClass.PARTICLE_FINE))
			HbmLivingProps.incrementAsbestos(target, (int) Math.min(level, 10));
		else
			ArmorUtil.damageGasMaskFilter(target, (int)level);
	}

	@Override
	public void addHazardInformation(EntityPlayer player, List list, float level, ItemStack stack, List<HazardModifier> modifiers) {
		list.add(EnumChatFormatting.WHITE + "[" + I18nUtil.resolveKey("trait.asbestos") + "]");
	}

}

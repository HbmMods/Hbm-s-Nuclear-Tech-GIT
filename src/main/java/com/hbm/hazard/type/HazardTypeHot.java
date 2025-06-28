package com.hbm.hazard.type;

import java.util.List;

import com.hbm.config.GeneralConfig;
import com.hbm.config.RadiationConfig;
import com.hbm.hazard.modifier.HazardModifier;
import com.hbm.items.ModItems;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class HazardTypeHot extends HazardTypeBase {

	@Override
	public void onUpdate(EntityLivingBase target, float level, ItemStack stack) {
		
		if(RadiationConfig.disableHot)
			return;
		
		boolean reacher = false;
		
		if(target instanceof EntityPlayer && !GeneralConfig.enable528)
			reacher = ((EntityPlayer) target).inventory.hasItem(ModItems.reacher);
		
		if(!reacher && !target.isWet() && level > 0)
			target.setFire((int) Math.ceil(level));
	}

	@Override
	public void updateEntity(EntityItem item, float level) { }

	@Override
	public void addHazardInformation(EntityPlayer player, List list, float level, ItemStack stack, List<HazardModifier> modifiers) {
		
		level = HazardModifier.evalAllModifiers(stack, player, level, modifiers);
		
		if(level > 0)
			list.add(EnumChatFormatting.GOLD + "[" + I18nUtil.resolveKey("trait.hot") + "]");
	}

}

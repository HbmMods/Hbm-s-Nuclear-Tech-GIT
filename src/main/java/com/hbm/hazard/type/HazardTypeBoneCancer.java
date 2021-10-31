package com.hbm.hazard.type;

import java.util.List;

import com.hbm.config.GeneralConfig;
import com.hbm.extprop.HbmLivingProps;
import com.hbm.hazard.HazardModifier;
import com.hbm.items.ModItems;
import com.hbm.util.ArmorRegistry;
import com.hbm.util.ArmorUtil;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.I18nUtil;
import com.hbm.util.ArmorRegistry.HazardClass;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class HazardTypeBoneCancer extends HazardTypeBase {

	@Override
	public void onUpdate(EntityLivingBase target, float level, ItemStack stack) {
		
		boolean reacher = false;
		
		if(target instanceof EntityPlayer && !GeneralConfig.enable528)
			reacher = ((EntityPlayer) target).inventory.hasItem(ModItems.reacher);
			
		if(level > 0) {
			level = level / 4F;
			
			if(reacher)
				level = (float) Math.min(Math.sqrt(level), level); 
			
			if(!ArmorRegistry.hasProtection(target, 3, HazardClass.PARTICLE_FINE))
				HbmLivingProps.incrementBoneCancer(target, (int) level);
			else
				ArmorUtil.damageGasMaskFilter(target, (int) level);
		}
		
	}

	@Override
	public void updateEntity(EntityItem item, float level) { }

	@Override
	public void addHazardInformation(EntityPlayer player, List list, float level, ItemStack stack, List<HazardModifier> modifiers) {
		list.add(EnumChatFormatting.DARK_GREEN + "[" + I18nUtil.resolveKey("trait.boneCancer") + "]");
	}
}
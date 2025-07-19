package com.hbm.hazard.type;

import java.util.List;

import com.hbm.config.RadiationConfig;
import com.hbm.hazard.modifier.HazardModifier;
import com.hbm.util.I18nUtil;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class HazardTypeHydroactive extends HazardTypeBase {

	@Override
	public void onUpdate(EntityLivingBase target, float level, ItemStack stack) {
		
		if(RadiationConfig.disableHydro)
			return;
		
		if(target.isWet() && stack.stackSize > 0) {
			stack.stackSize = 0;
			target.worldObj.newExplosion(null, target.posX, target.posY + target.getEyeHeight() - target.getYOffset(), target.posZ, level, false, true);
		}
	}

	@Override
	public void updateEntity(EntityItem item, float level) {
		
		if(RadiationConfig.disableHydro)
			return;
		
		if(item.isWet() || item.worldObj.getBlock((int) Math.floor(item.posX), (int) Math.floor(item.posY), (int) Math.floor(item.posZ)).getMaterial() == Material.water) {
			item.setDead();
			item.worldObj.newExplosion(null, item.posX, item.posY + item.height * 0.5, item.posZ, level, false, true);
		}
	}

	@Override
	public void addHazardInformation(EntityPlayer player, List list, float level, ItemStack stack, List<HazardModifier> modifiers) {
		list.add(EnumChatFormatting.RED + "[" + I18nUtil.resolveKey("trait.hydro") + "]");
	}
}
package com.hbm.hazard.type;

import java.util.List;

import com.hbm.config.GeneralConfig;
import com.hbm.handler.ArmorModHandler;
import com.hbm.hazard.modifier.HazardModifier;
import com.hbm.items.ModItems;
import com.hbm.items.armor.ItemModGloves;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.I18nUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class HazardTypeRadiation extends HazardTypeBase {

	@Override
	public void onUpdate(EntityLivingBase target, float level, ItemStack stack) {
		
		boolean reacher = false;
		
		if(target instanceof EntityPlayer) {
			ItemStack item = ((EntityPlayer) target).inventory.getCurrentItem();
			if(item != null)
				reacher = item.getItem() == ModItems.reacher;
		}
		
		level *= stack.stackSize;
		
		if(level > 0) {
			float rad = level / 20F;
			
			if(GeneralConfig.enable528 && reacher) {
				rad = (float) Math.sqrt(rad + 1F / ((rad + 2F) * (rad + 2F))) - 1F / (rad + 2F); //Reworked radiation function: sqrt(x+1/(x+2)^2)-1/(x+2)
			} else if(reacher) {
				rad = (float) (rad / Math.pow(7, 2));	//More realistic function for normal mode: x / distance^2
			}											//Distance is 7 here for balancing purposes, realistically it'd be longer and better
			
			ContaminationUtil.contaminate(target, HazardType.RADIATION, ContaminationType.CREATIVE, rad);
		}
	}

	@Override
	public void updateEntity(EntityItem item, float level) { }

	@Override
	@SideOnly(Side.CLIENT)
	public void addHazardInformation(EntityPlayer player, List list, float level, ItemStack stack, List<HazardModifier> modifiers) {
		
		level = HazardModifier.evalAllModifiers(stack, player, level, modifiers);
		
		list.add(EnumChatFormatting.GREEN + "[" + I18nUtil.resolveKey("trait.radioactive") + "]");
		String rad = "" + (Math.floor(level* 1000) / 1000);
		list.add(EnumChatFormatting.YELLOW + (rad + "RAD/s"));
		
		if(stack.stackSize > 1) {
			list.add(EnumChatFormatting.YELLOW + "Stack: " + ((Math.floor(level * 1000 * stack.stackSize) / 1000) + "RAD/s"));
		}
	}

}

package com.hbm.hazard.type;

import java.text.DecimalFormat;
import java.util.List;

import com.hbm.config.GeneralConfig;
import com.hbm.hazard.modifier.HazardModifier;
import com.hbm.items.machine.ItemRBMKRod;
import com.hbm.lib.Library;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.I18nUtil;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class HazardTypeDigamma extends HazardTypeBase {

	@Override
	public void onUpdate(EntityLivingBase target, float level, ItemStack stack) {
		ContaminationUtil.applyDigammaData(target, level / 20F);
	}

	@Override
	public void updateEntity(EntityItem item, float level) { }

	static final DecimalFormat basicFormatter = new DecimalFormat("0.###");
	static final DecimalFormat sciNotFormatter = new DecimalFormat("0.###E0");
	static
	{
		basicFormatter.setGroupingUsed(true);
		basicFormatter.setGroupingSize(3);
	}
	@Override
	public void addHazardInformation(EntityPlayer player, List<String> list, float level, ItemStack stack, List<HazardModifier> modifiers) {
		
		level = HazardModifier.evalAllModifiers(stack, player, level, modifiers);
		
//		float d = (float)(Math.floor(level * 10000F)) / 10F;
		list.add(EnumChatFormatting.RED + "[" + I18nUtil.resolveKey("trait.digamma") + "]");
		final boolean isRBMK = stack.getItem() instanceof ItemRBMKRod;
		final double drxValue = isRBMK ? level : Library.roundNumber(level, 4);
		String drx = GeneralConfig.enableRoundedValues ? (level < 0.001 ? sciNotFormatter.format(drxValue) : basicFormatter.format(drxValue)) : String.valueOf(Math.floor(level* 1000) / 1000);
		list.add(EnumChatFormatting.DARK_RED + "" + drx + "mDRX/s");
		
		if(stack.stackSize > 1) {
			list.add(EnumChatFormatting.DARK_RED + "Stack: " + (GeneralConfig.enableRoundedValues ? (drxValue * stack.stackSize < 0.001 ? sciNotFormatter.format(drxValue * stack.stackSize) : basicFormatter.format(drxValue * stack.stackSize)) : (Math.floor(level * 1000 * stack.stackSize) / 1000)) + "mDRX/s");
		}
	}

}

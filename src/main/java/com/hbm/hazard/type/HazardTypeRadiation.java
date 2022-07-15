package com.hbm.hazard.type;

import static com.hbm.lib.HbmCollection.alpha;
import static com.hbm.lib.HbmCollection.beta;
import static com.hbm.lib.HbmCollection.gamma;
import static com.hbm.lib.HbmCollection.eta;

import java.text.DecimalFormat;
import java.util.List;

import com.hbm.calc.EasyLocation;
import com.hbm.config.GeneralConfig;
import com.hbm.config.RadiationConfig;
import com.hbm.hazard.HazardEntry;
import com.hbm.hazard.HazardRegistry;
import com.hbm.hazard.HazardSystem;
import com.hbm.hazard.modifier.HazardModifier;
import com.hbm.inventory.OreDictManager;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemRBMKRod;
import com.hbm.lib.Library;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;
import com.hbm.util.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class HazardTypeRadiation extends HazardTypeBase
{
	static final char[] radTypeSymbols = {alpha, beta, gamma, eta};
	@Override
	public void onUpdate(EntityLivingBase target, float level, ItemStack stack) {
		
		boolean reacher = false;
		
		if(target instanceof EntityPlayer) {
			ItemStack item = ((EntityPlayer) target).inventory.getCurrentItem();
			if(item != null)
				reacher = item.getItem() == ModItems.reacher;
		}
		
		level *= stack.stackSize;
		
		if (RadiationConfig.realisticRads ? level > 0.001 : level > 0) {
			float rad = level / 20F;
			
			if(GeneralConfig.enable528 && reacher) {
				rad = (float) (rad / Math.pow(7, 2));	//More realistic function for 528: x / distance^2
			} else if(reacher) {
				rad = (float) Math.sqrt(rad + 1F / ((rad + 2F) * (rad + 2F))) - 1F / (rad + 2F); //Reworked radiation function: sqrt(x+1/(x+2)^2)-1/(x+2)
			}											
			
			ContaminationUtil.contaminate(target, HazardType.RADIATION, ContaminationType.CREATIVE, rad);
		}
	}

	@Override
	public void updateEntity(EntityItem item, float level)
	{
		Library.radiate(item.worldObj, new EasyLocation(item), HazardSystem.getHazardLevelFromStack(item.getEntityItem(), this), level * 10, HazardType.RADIATION, ContaminationType.HAZMAT);
	}

	
	static final DecimalFormat basicFormatter = new DecimalFormat("0.###");
	static final DecimalFormat sciNotFormatter = new DecimalFormat("0.###E0");
	static
	{
		basicFormatter.setGroupingUsed(true);
		basicFormatter.setGroupingSize(3);
	}
	
	static byte[] getTypes(ItemStack stack)
	{
		for (HazardEntry entry : HazardSystem.getHazardsFromStack(stack))
			if (entry.getType().getClass().equals(HazardRegistry.RADIATION_TYPES.getClass()))
				return HazardSystem.decompactRadTypes(entry.getBaseLevel());
		return HazardSystem.decompactRadTypes(HazardSystem.defaultRadTypes);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addHazardInformation(EntityPlayer player, List<String> list, float level, ItemStack stack, List<HazardModifier> modifiers) {
		
		level = HazardModifier.evalAllModifiers(stack, player, level, modifiers);
		
		if(level < 1e-5)
			return;
		
		list.add(EnumChatFormatting.GREEN + "[" + I18nUtil.resolveKey("trait.radioactive") + ']');
		final StringBuilder types = new StringBuilder();
		final byte[] radTypes = getTypes(stack);
		// Could be neater
		boolean flag = false;
		for (byte i = 0; i < 4; i++)
		{
			if (radTypes[i] > 0)
			{
				if (flag)
					types.append(',');
				types.append(radTypeSymbols[i]);
				flag = true;
			}
		}
		list.add(String.format("%s[%s emitter]", EnumChatFormatting.GREEN, types));
		final boolean isRBMK = stack.getItem() instanceof ItemRBMKRod;
		final double radValue = isRBMK ? level : Library.roundNumber(level, 4);
		String rad = GeneralConfig.enableRoundedValues ? (level < 0.001 ? sciNotFormatter.format(radValue) : basicFormatter.format(radValue)) : String.valueOf(Math.floor(level* 1000) / 1000);
		list.add(EnumChatFormatting.YELLOW + (rad + "RAD/s"));
		
		if(stack.stackSize > 1) {
			list.add(EnumChatFormatting.YELLOW + "Stack: " + (GeneralConfig.enableRoundedValues ? (radValue * stack.stackSize < 0.001 ? sciNotFormatter.format(radValue * stack.stackSize) : basicFormatter.format(radValue * stack.stackSize)) : (Math.floor(level * 1000 * stack.stackSize) / 1000)) + "RAD/s");
		}
	}

}

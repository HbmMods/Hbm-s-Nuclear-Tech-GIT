package com.hbm.hazard.type;

import java.util.List;

import com.hbm.config.GeneralConfig;
import com.hbm.config.RadiationConfig;
import com.hbm.hazard.modifier.HazardModifier;
import com.hbm.items.ModItems;
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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;

public class HazardTypeNeutron extends HazardTypeBase {

	public static final String NEUTRON_KEY = "ntmNeutron";

	@Override
	public void onUpdate(EntityLivingBase target, float level, ItemStack stack) {
		if(RadiationConfig.disableNeutron) return;

		boolean reacher = false;
		
		if(target instanceof EntityPlayer && !GeneralConfig.enable528)
			reacher = ((EntityPlayer) target).inventory.hasItem(ModItems.reacher);
		
		level *= stack.stackSize;
		
		if(level > 0) {
			float rad = (level / 20F)*ContaminationUtil.calculateRadiationMod(target);
			
			if(GeneralConfig.enable528 && reacher) {
				rad = (float) (rad / 49F);	//More realistic function for 528: x / distance^2
			} else if(reacher) {
				rad = (float) Math.sqrt(rad + 1F / ((rad + 2F) * (rad + 2F))) - 1F / (rad + 2F); //Reworked radiation function: sqrt(x+1/(x+2)^2)-1/(x+2)
			}

			if(target instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) target;
				for(int i = 0; i < player.inventory.mainInventory.length; i++) {
					apply(player.inventory.getStackInSlot(i), rad);
				}
				for(int i = 0; i < player.inventory.armorInventory.length; i++) {
					apply(player.inventory.armorItemInSlot(i), rad);
				}	
			}

			ContaminationUtil.contaminate(target, HazardType.NEUTRON, ContaminationType.CREATIVE, rad);
		}
	}

	@Override
	public void updateEntity(EntityItem item, float level) { }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addHazardInformation(EntityPlayer player, List list, float level, ItemStack stack, List<HazardModifier> modifiers) {
		
		level = HazardModifier.evalAllModifiers(stack, player, level, modifiers);
		
		if(level < 1e-5 || RadiationConfig.disableNeutron)
			return;
		
		list.add(EnumChatFormatting.BLUE + "[" + I18nUtil.resolveKey("trait.neutron") + "]");
		String neut = "" + (Math.floor(level* 1000) / 1000);
		list.add(EnumChatFormatting.LIGHT_PURPLE+ (neut + "RAD/s^2"));
		
		if(stack.stackSize > 1) {
			list.add(EnumChatFormatting.LIGHT_PURPLE + "Stack: " + ((Math.floor(level * 1000 * stack.stackSize) / 1000) + "RAD/s^2"));
		}
	}

	public static void apply(ItemStack stack, float rad) {
		if(stack == null) return;

		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();

		float activation = stack.stackTagCompound.getFloat(NEUTRON_KEY);
		stack.stackTagCompound.setFloat(NEUTRON_KEY, activation + (rad / stack.stackSize) / 10);
	}

	public static void decay(ItemStack stack, float factor) {
		if(stack == null) return;
		if(!stack.hasTagCompound()) return;
		
		float activation = stack.stackTagCompound.getFloat(NEUTRON_KEY);
		stack.stackTagCompound.setFloat(NEUTRON_KEY, activation * factor);

		if(activation < 1e-5)
			stack.stackTagCompound.removeTag(NEUTRON_KEY);

		if(stack.stackTagCompound.hasNoTags())
			stack.setTagCompound((NBTTagCompound) null);
	}

}

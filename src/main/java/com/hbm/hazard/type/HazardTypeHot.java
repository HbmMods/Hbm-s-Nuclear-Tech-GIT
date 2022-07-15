package com.hbm.hazard.type;

import java.util.List;

import com.hbm.config.GeneralConfig;
import com.hbm.config.RadiationConfig;
import com.hbm.handler.ArmorModHandler;
import com.hbm.hazard.modifier.HazardModifier;
import com.hbm.items.ModItems;
import com.hbm.items.armor.ItemModGloves;
import com.hbm.util.ArmorUtil;
import com.hbm.util.I18nUtil;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;

public class HazardTypeHot extends HazardTypeBase {

	@Override
	public void onUpdate(EntityLivingBase target, float level, ItemStack stack) {
		
		if(RadiationConfig.disableHot)
			return;
		
		boolean reacher = false;
		boolean gloves = false;
		
		//Why on earth did I add gloves again?
		if(target instanceof EntityPlayer) {
			ItemStack item = ((EntityPlayer) target).inventory.getCurrentItem();
			if(item != null)
				reacher = item.getItem() == ModItems.reacher;
			
			ItemStack armor = target.getEquipmentInSlot(3);
			if(armor != null) {
				gloves = armor.getItem() instanceof ItemModGloves || ArmorUtil.checkForHazmat(target);
				if(!gloves) {
					ItemStack mod = ArmorModHandler.pryMods(armor)[ArmorModHandler.legs_only];
					if(mod != null)
						gloves = mod.getItem() instanceof ItemModGloves;
				}
			}
		}
		
		if(!target.isWet() && level > 0) {
			if(GeneralConfig.enable528 && ((!reacher || !gloves) || level > 3))
				target.setFire((int) Math.ceil(level));
			if(((!reacher || !gloves) && level > 2) || (!reacher && !gloves))
				target.setFire((int) Math.ceil(level));
		}
	}

	@Override
	public void updateEntity(EntityItem item, float level)
	{
		final List<EntityLivingBase> entities = item.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(item.posX - 0.5, item.posY - 0.5, item.posZ - 0.5, item.posX + 0.5, item.posY + 0.5, item.posZ + 0.5).expand(level * 5, level * 5, level * 5));
		for (EntityLivingBase e : entities)
			e.setFire((int) (level * (level / e.getDistanceToEntity(item))));
	}

	@Override
	public void addHazardInformation(EntityPlayer player, List<String> list, float level, ItemStack stack, List<HazardModifier> modifiers) {
		
		level = HazardModifier.evalAllModifiers(stack, player, level, modifiers);
		
		if(level > 0)
			list.add(EnumChatFormatting.GOLD + "[" + I18nUtil.resolveKey("trait.hot") + "]");
	}

}

package com.hbm.items.armor;

import java.util.List;

import com.hbm.handler.ArmorModHandler;

import com.hbm.util.I18nUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Vec3;

public class ItemModLodestone extends ItemArmorMod {

	int range;
	
	public ItemModLodestone(int range) {
		super(ArmorModHandler.extra, true, true, true, true);
		this.range = range;
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		list.add(EnumChatFormatting.DARK_GRAY + I18nUtil.resolveKeyArray("armorMod.mod.Lodestone")[0]);
		list.add(EnumChatFormatting.DARK_GRAY + I18nUtil.resolveKeyArray("armorMod.mod.Lodestone",range)[1]);
		list.add("");
		super.addInformation(itemstack, player, list, bool);
	}

	@Override
	public void addDesc(List list, ItemStack stack, ItemStack armor) {
		list.add(EnumChatFormatting.DARK_GRAY + "  " + stack.getDisplayName() + I18nUtil.resolveKeyArray("armorMod.mod.Lodestone",range)[2] );
	}
	
	@Override
	public void modUpdate(EntityLivingBase entity, ItemStack armor) {
		
		List<EntityItem> items = entity.worldObj.getEntitiesWithinAABB(EntityItem.class, entity.boundingBox.expand(range, range, range));
		
		for(EntityItem item : items) {
			
			Vec3 vec = Vec3.createVectorHelper(entity.posX - item.posX, entity.posY - item.posY, entity.posZ - item.posZ);
			vec = vec.normalize();

			item.motionX += vec.xCoord * 0.05;
			item.motionY += vec.yCoord * 0.05;
			item.motionZ += vec.zCoord * 0.05;
			
			if(vec.yCoord > 0 && item.motionY < 0.04)
				item.motionY += 0.2;
		}
	}
}

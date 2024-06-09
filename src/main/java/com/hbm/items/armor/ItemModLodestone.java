package com.hbm.items.armor;

import java.util.List;

import com.hbm.handler.ArmorModHandler;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
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

		list.add(EnumChatFormatting.DARK_GRAY + "Attracts nearby items and Experience Orbs");
		list.add(EnumChatFormatting.DARK_GRAY + "Item attraction range: " + range);
		list.add(EnumChatFormatting.DARK_GRAY + "Experience Orb attraction range: " + range * 2);
		list.add("");
		super.addInformation(itemstack, player, list, bool);
	}

	@Override
	public void addDesc(List list, ItemStack stack, ItemStack armor) {
		list.add(EnumChatFormatting.DARK_GRAY + "  " + stack.getDisplayName() + " (Magnetic range: " + range + ")");
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

		List<EntityXPOrb> xpOrbs = entity.worldObj.getEntitiesWithinAABB(EntityXPOrb.class, entity.boundingBox.expand(range * 2, range * 2, range * 2));
		for(EntityXPOrb xpOrb : xpOrbs) {

			Vec3 vec = Vec3.createVectorHelper(entity.posX - xpOrb.posX, entity.posY - xpOrb.posY, entity.posZ - xpOrb.posZ);
			vec = vec.normalize();

			xpOrb.motionX += vec.xCoord * 0.05;
			xpOrb.motionY += vec.yCoord * 0.05;
			xpOrb.motionZ += vec.zCoord * 0.05;

			if(vec.yCoord > 0 && xpOrb.motionY < 0.04)
				xpOrb.motionY += 0.2;
		}
	}
}

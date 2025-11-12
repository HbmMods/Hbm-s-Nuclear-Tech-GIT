package com.hbm.items.armor;

import com.hbm.render.model.ModelArmorAJRO;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class ArmorAJRO extends ArmorFSBPowered {

	public ArmorAJRO(ArmorMaterial material, int slot, String texture, long maxPower, long chargeRate, long consumption, long drain) {
		super(material, slot, texture, maxPower, chargeRate, consumption, drain);
	}

	@SideOnly(Side.CLIENT)
	ModelArmorAJRO[] models;

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		
		if(models == null) {
			models = new ModelArmorAJRO[4];
			
			for(int i = 0; i < 4; i++)
				models[i] = new ModelArmorAJRO(i);
		}
		
		return models[armorSlot];
	}
}

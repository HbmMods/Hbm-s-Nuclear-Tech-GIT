package com.hbm.items.armor;

import com.hbm.render.model.ModelArmorDigamma;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class ArmorDigamma extends ArmorFSBPowered {

	public ArmorDigamma(ArmorMaterial material, int slot, String texture, long maxPower, long chargeRate, long consumption, long drain) {
		super(material, slot, texture, maxPower, chargeRate, consumption, drain);
	}

	@SideOnly(Side.CLIENT)
	ModelArmorDigamma[] models;

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		
		if(models == null) {
			models = new ModelArmorDigamma[4];
			
			for(int i = 0; i < 4; i++)
				models[i] = new ModelArmorDigamma(i);
		}
		
		return models[armorSlot];
	}
}

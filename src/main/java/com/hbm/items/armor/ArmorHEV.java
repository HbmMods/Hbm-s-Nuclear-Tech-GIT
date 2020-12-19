package com.hbm.items.armor;

import com.hbm.render.model.ModelArmorHEV;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class ArmorHEV extends ArmorFSBPowered {

	public ArmorHEV(ArmorMaterial material, int layer, int slot, String texture, long maxPower, long chargeRate, long consumption) {
		super(material, layer, slot, texture, maxPower, chargeRate, consumption);
	}

	@SideOnly(Side.CLIENT)
	ModelArmorHEV[] models;

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		
		if(models == null) {
			models = new ModelArmorHEV[4];
			
			for(int i = 0; i < 4; i++)
				models[i] = new ModelArmorHEV(i);
		}
		
		return models[armorSlot];
	}
}

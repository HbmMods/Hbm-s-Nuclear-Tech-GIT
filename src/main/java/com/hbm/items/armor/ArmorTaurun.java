package com.hbm.items.armor;

import com.hbm.render.model.ModelArmorTaurun;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class ArmorTaurun extends ArmorFSB {

	public ArmorTaurun(ArmorMaterial material, int slot, String texture) {
		super(material, slot, texture);
		this.setMaxDamage(0);
	}

	@SideOnly(Side.CLIENT)
	ModelArmorTaurun[] models;

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {

		if(models == null) {
			models = new ModelArmorTaurun[4];

			for(int i = 0; i < 4; i++)
				models[i] = new ModelArmorTaurun(i);
		}

		return models[armorSlot];
	}
}

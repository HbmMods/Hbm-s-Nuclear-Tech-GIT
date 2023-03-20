package com.hbm.items.armor;

import com.hbm.render.model.ModelGlasses;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ArmorAshGlasses extends ItemArmor {
	
	@SideOnly(Side.CLIENT)
	private ModelGlasses modelGoggles;

	public ArmorAshGlasses(ArmorMaterial armorMaterial, int armorType) {
		super(armorMaterial, 0, armorType);
	}

	@SideOnly(Side.CLIENT)
	ModelGlasses model;

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		
		if(model == null) {
			model = new ModelGlasses(0);
		}
		
		return model;
	}
}

package com.hbm.items.armor;

import com.hbm.render.model.ModelArmorBJ;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class ArmorBJEyepatch extends ArmorBJ{

	public ArmorBJEyepatch(ArmorMaterial material, int slot, String texture, long maxPower, long chargeRate, long consumption, long drain) { 
		super(material, slot, texture, maxPower, chargeRate, consumption, drain);
		}
	
	@SideOnly(Side.CLIENT)
	ModelArmorBJ model;
	
	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {

		if(model == null) {
			model = new ModelArmorBJ(6);
		}

		return model;
	}
	
	
	

}

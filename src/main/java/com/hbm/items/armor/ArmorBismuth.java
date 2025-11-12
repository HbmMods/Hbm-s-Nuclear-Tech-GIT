package com.hbm.items.armor;

import com.hbm.render.model.ModelArmorBismuth;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class ArmorBismuth extends ArmorFSB {
	
	public ArmorBismuth(ArmorMaterial material, int slot, String texture) {
		super(material, slot, texture);
	}

	@SideOnly(Side.CLIENT)
	ModelArmorBismuth[] models;

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		
		if(models == null) {
			models = new ModelArmorBismuth[4];
			
			for(int i = 0; i < 4; i++)
				models[i] = new ModelArmorBismuth(i);
		}
		
		return models[armorSlot];
	}

}

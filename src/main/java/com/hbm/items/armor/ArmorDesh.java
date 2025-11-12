package com.hbm.items.armor;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.hbm.handler.ArmorModHandler;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.render.model.ModelArmorDesh;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;

public class ArmorDesh extends ArmorFSBFueled {

	public ArmorDesh(ArmorMaterial material, int slot, String texture, FluidType fuelType, int maxFuel, int fillRate, int consumption, int drain) {
		super(material, slot, texture, fuelType, maxFuel, fillRate, consumption, drain);
	}
	
	@Override
	public Multimap getItemAttributeModifiers() {

		Multimap multimap = HashMultimap.create();
		multimap.put(SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(), new AttributeModifier(ArmorModHandler.fixedUUIDs[this.armorType], "Armor modifier", -0.025D, 1));
		return multimap;
	}

	@SideOnly(Side.CLIENT)
	ModelArmorDesh[] models;

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		
		if(models == null) {
			models = new ModelArmorDesh[4];
			
			for(int i = 0; i < 4; i++)
				models[i] = new ModelArmorDesh(i);
		}
		
		return models[armorSlot];
	}
}

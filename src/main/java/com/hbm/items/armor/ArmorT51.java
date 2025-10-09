package com.hbm.items.armor;

import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.render.model.ModelArmorT51;
import com.hbm.render.tileentity.IItemRendererProvider;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ArmorT51 extends ArmorFSBPowered implements IItemRendererProvider {

	public ArmorT51(ArmorMaterial material, int slot, String texture, long maxPower, long chargeRate, long consumption, long drain) {
		super(material, slot, texture, maxPower, chargeRate, consumption, drain);
	}

	@SideOnly(Side.CLIENT)
	ModelArmorT51[] models;

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		
		if(models == null) {
			models = new ModelArmorT51[4];
			
			for(int i = 0; i < 4; i++)
				models[i] = new ModelArmorT51(i);
		}
		
		return models[armorSlot];
	}

	@Override public Item getItemForRenderer() { return this; }

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase( ) {
			public void renderInventory() { setupRenderInv(); }
			public void renderNonInv() { setupRenderNonInv(); }
			public void renderCommon() {
				renderStandard(ResourceManager.armor_t51, armorType,
						ResourceManager.t51_helmet, ResourceManager.t51_chest, ResourceManager.t51_arm, ResourceManager.t51_leg,
						"Helmet", "Chest", "LeftArm", "RightArm", "LeftLeg", "RightLeg", "LeftBoot", "RightBoot");
			}};
	}
}

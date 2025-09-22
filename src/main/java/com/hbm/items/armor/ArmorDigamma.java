package com.hbm.items.armor;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.render.model.ModelArmorDigamma;
import com.hbm.render.tileentity.IItemRendererProvider;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ArmorDigamma extends ArmorFSBPowered implements IItemRendererProvider {

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

	@Override public Item getItemForRenderer() { return this; }

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase( ) {
			public void renderInventory() {
				if(armorType == 0) {
					GL11.glScaled(0.875, 0.875, 0.875);
					GL11.glTranslated(0, -2, 0);
				}
				setupRenderInv();
			}
			public void renderNonInv() { setupRenderNonInv(); }
			public void renderCommon() {
				renderStandard(ResourceManager.armor_fau, armorType,
						ResourceManager.fau_helmet, ResourceManager.fau_chest, ResourceManager.fau_arm, ResourceManager.fau_leg,
						"Head", "Body,Cassette", "LeftArm", "RightArm", "LeftLeg", "RightLeg", "LeftBoot", "RightBoot");
			}};
	}
}

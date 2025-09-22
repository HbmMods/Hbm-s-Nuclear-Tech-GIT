package com.hbm.items.armor;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.render.model.ModelArmorTaurun;
import com.hbm.render.tileentity.IItemRendererProvider;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ArmorTaurun extends ArmorFSB implements IItemRendererProvider {

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

	@Override public Item getItemForRenderer() { return this; }

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase( ) {
			public void renderInventory() { 
				if(armorType == 0) GL11.glTranslated(0, 1, 0);
				if(armorType == 1) GL11.glTranslated(0, 1.5, 0);
				setupRenderInv();
			}
			public void renderNonInv() { setupRenderNonInv(); }
			public void renderCommon() {
				renderStandard(ResourceManager.armor_taurun, armorType,
						ResourceManager.taurun_helmet, ResourceManager.taurun_chest, ResourceManager.taurun_arm, ResourceManager.taurun_leg,
						"Helmet", "Chest", "LeftArm", "RightArm", "LeftLeg", "RightLeg", "LeftBoot", "RightBoot");
			}};
	}
}

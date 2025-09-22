package com.hbm.items.armor;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.render.model.ModelArmorBismuth;
import com.hbm.render.tileentity.IItemRendererProvider;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ArmorBismuth extends ArmorFSB implements IItemRendererProvider {
	
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

	@Override public Item getItemForRenderer() { return this; }

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase( ) {
			public void renderInventory() { setupRenderInv(); }
			public void renderNonInv() { setupRenderNonInv(); }
			public void renderCommon() {
				if(armorType == 0) {
					GL11.glTranslated(0, -0.5, 0);
					GL11.glScaled(0.625, 0.625, 0.625);
				}
				if(armorType == 1) {
					GL11.glScaled(0.875, 0.875, 0.875);
				}
				GL11.glDisable(GL11.GL_CULL_FACE);
				renderStandard(ResourceManager.armor_bismuth, armorType,
						ResourceManager.armor_bismuth_tex, ResourceManager.armor_bismuth_tex, ResourceManager.armor_bismuth_tex, ResourceManager.armor_bismuth_tex,
						"Head", "Body", "LeftArm", "RightArm", "LeftLeg", "RightLeg", "LeftFoot", "RightFoot");
				GL11.glEnable(GL11.GL_CULL_FACE);
			}};
	}
}

package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.ItemGunBase;
import com.hbm.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ItemRenderDebug extends ItemRenderWeaponBase {

	@Override
	protected float getTurnMagnitude(ItemStack stack) { return ItemGunBase.getIsAiming(stack) ? 2.5F : -0.25F; }

	@Override
	protected void setupFirstPerson(ItemStack stack) {
		GL11.glTranslated(0, 0, 1);
		
		float offset = 0.8F;
		standardAimingTransform(stack,
				-1.0F * offset, -0.75F * offset, 1F * offset,
				0, -3.875 / 8D, 0);
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {
		
		double scale = 0.125D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(90, 0, 1, 0);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.debug_gun_tex);
		ResourceManager.lilmac.renderPart("Gun");
		ResourceManager.lilmac.renderPart("Cylinder");
		ResourceManager.lilmac.renderPart("Bullets");
		ResourceManager.lilmac.renderPart("Casings");
		ResourceManager.lilmac.renderPart("Pivot");
		ResourceManager.lilmac.renderPart("Hammer");
		GL11.glShadeModel(GL11.GL_FLAT);
	}

	@Override
	protected void setupInv(ItemStack stack) {
		super.setupInv(stack);
		double scale = 1.25D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(45, 0, 1, 0);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {

		GL11.glRotated(90, 0, 1, 0);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0F);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.debug_gun_tex);
		ResourceManager.lilmac.renderPart("Gun");
		ResourceManager.lilmac.renderPart("Cylinder");
		ResourceManager.lilmac.renderPart("Bullets");
		ResourceManager.lilmac.renderPart("Casings");
		ResourceManager.lilmac.renderPart("Pivot");
		ResourceManager.lilmac.renderPart("Hammer");
		GL11.glShadeModel(GL11.GL_FLAT);
	}
}

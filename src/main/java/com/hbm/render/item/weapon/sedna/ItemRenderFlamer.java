package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.mags.IMagazine;
import com.hbm.main.MainRegistry;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemRenderFlamer extends ItemRenderWeaponBase {
	
	public ResourceLocation texture;
	
	public ItemRenderFlamer(ResourceLocation texture) {
		this.texture = texture;
	}

	@Override
	protected float getTurnMagnitude(ItemStack stack) { return ItemGunBaseNT.getIsAiming(stack) ? 2.5F : -0.5F; }

	@Override
	public float getViewFOV(ItemStack stack, float fov) {
		float aimingProgress = ItemGunBaseNT.prevAimingProgress + (ItemGunBaseNT.aimingProgress - ItemGunBaseNT.prevAimingProgress) * interp;
		return  fov * (1 - aimingProgress * 0.33F);
	}

	@Override
	public void setupFirstPerson(ItemStack stack) {
		GL11.glTranslated(0, 0, 0.875);
		
		float offset = 0.8F;
		standardAimingTransform(stack,
				-1.5F * offset, -1.5F * offset, 2.75F * offset,
				0, -4.625 / 8D, 0.25);
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {
		
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		double scale = 0.375D;
		GL11.glScaled(scale, scale, scale);

		double[] equip = HbmAnimations.getRelevantTransformation("EQUIP");
		double[] rotate = HbmAnimations.getRelevantTransformation("ROTATE");
		
		GL11.glTranslated(0, 2, -6);
		GL11.glRotated(-equip[0], 1, 0, 0);
		GL11.glTranslated(0, -2, 6);

		GL11.glTranslated(0, 1, 0);
		GL11.glRotated(rotate[2], 0, 0, 1);
		GL11.glTranslated(0, -1, 0);

		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		GL11.glPushMatrix();
		HbmAnimations.applyRelevantTransformation("Gun");
		ResourceManager.flamethrower.renderPart("Gun");
		if(hasShield(stack)) ResourceManager.flamethrower.renderPart("HeatShield");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		HbmAnimations.applyRelevantTransformation("Tank");
		ResourceManager.flamethrower.renderPart("Tank");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		HbmAnimations.applyRelevantTransformation("Gauge");
		GL11.glTranslated(1.25, 1.25, 0);
		IMagazine mag = gun.getConfig(stack, 0).getReceivers(stack)[0].getMagazine(stack);
		GL11.glRotated(-135 + (mag.getAmount(stack, MainRegistry.proxy.me().inventory) * 270D / mag.getCapacity(stack)), 0, 0, 1);
		GL11.glTranslated(-1.25, -1.25, 0);
		ResourceManager.flamethrower.renderPart("Gauge");
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		double scale = 1.75D;
		GL11.glScaled(scale, scale, scale);
		GL11.glTranslated(0, -3, 4);

	}

	@Override
	public void setupInv(ItemStack stack) {
		super.setupInv(stack);
		double scale = 1.25D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(45, 0, 1, 0);
		GL11.glTranslated(-1, 1, 0);
	}

	@Override
	public void setupModTable(ItemStack stack) {
		double scale = -7.5D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glTranslated(0, 0, 1);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		ResourceManager.flamethrower.renderPart("Gun");
		ResourceManager.flamethrower.renderPart("Tank");
		ResourceManager.flamethrower.renderPart("Gauge");
		if(hasShield(stack)) ResourceManager.flamethrower.renderPart("HeatShield");
		GL11.glShadeModel(GL11.GL_FLAT);
	}
	
	public boolean hasShield(ItemStack stack) {
		return stack.getItem() == ModItems.gun_flamer_daybreaker;
	}
}

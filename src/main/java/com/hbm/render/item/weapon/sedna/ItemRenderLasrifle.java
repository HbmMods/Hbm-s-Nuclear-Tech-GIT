package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.mods.WeaponModManager;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ItemRenderLasrifle extends ItemRenderWeaponBase {

	@Override
	protected float getTurnMagnitude(ItemStack stack) { return ItemGunBaseNT.getIsAiming(stack) ? 2.5F : -0.25F; }

	@Override
	public float getViewFOV(ItemStack stack, float fov) {
		float aimingProgress = ItemGunBaseNT.prevAimingProgress + (ItemGunBaseNT.aimingProgress - ItemGunBaseNT.prevAimingProgress) * interp;
		return  fov * (1 - aimingProgress * (hasScope(stack) ? 0.75F : 0.66F));
	}

	@Override
	public void setupFirstPerson(ItemStack stack) {
		GL11.glTranslated(0, 0, 0.875);
		
		float offset = 0.8F;
		
		if(hasScope(stack)) {
			standardAimingTransform(stack,
					-1.5F * offset, -1.5F * offset, 2.5F * offset,
				0, -7.375 / 8D, 0.75);
		} else {
			standardAimingTransform(stack,
					-1.5F * offset, -1.5F * offset, 2.5F * offset,
				0, -5.25 / 8D, 1);
		}
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {

		if(hasScope(stack) && ItemGunBaseNT.prevAimingProgress == 1 && ItemGunBaseNT.aimingProgress == 1) return;
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.lasrifle_tex);
		double scale = 0.3125D;
		GL11.glScaled(scale, scale, scale);

		double[] equip = HbmAnimations.getRelevantTransformation("EQUIP");
		double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
		double[] lever = HbmAnimations.getRelevantTransformation("LEVER");
		double[] mag = HbmAnimations.getRelevantTransformation("MAG");
		
		GL11.glTranslated(0, -1, -6);
		GL11.glRotated(equip[0], 1, 0, 0);
		GL11.glTranslated(0, 1, 6);
		
		GL11.glTranslated(0, 0, recoil[2]);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);

		ResourceManager.lasrifle.renderPart("Gun");
		ResourceManager.lasrifle.renderPart("Stock");
		if(hasScope(stack)) ResourceManager.lasrifle.renderPart("Scope");
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, -0.375, 2.375);
		GL11.glRotated(lever[0], 1, 0, 0);
		GL11.glTranslated(0, 0.375, -2.375);
		ResourceManager.lasrifle.renderPart("Lever");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(mag[0], mag[1], mag[2]);
		ResourceManager.lasrifle.renderPart("Battery");
		GL11.glPopMatrix();
		
		if(!hasShotgun(stack)) ResourceManager.lasrifle.renderPart("Barrel");
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.lasrifle_mods_tex);
		if(hasShotgun(stack)) ResourceManager.lasrifle_mods.renderPart("BarrelShotgun");
		if(hasCapacitor(stack)) ResourceManager.lasrifle_mods.renderPart("UnderBarrel");
		
		GL11.glShadeModel(GL11.GL_FLAT);
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		double scale = 1.25D;
		GL11.glScaled(scale, scale, scale);
		GL11.glTranslated(0, 0, 4);

	}

	@Override
	public void setupInv(ItemStack stack) {
		super.setupInv(stack);
		double scale = 1.03125D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(45, 0, 1, 0);
		GL11.glTranslated(0.75, 0, 0);
	}

	@Override
	public void setupModTable(ItemStack stack) {
		double scale = -6.25D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glTranslated(0, -1, -1);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.lasrifle_tex);
		ResourceManager.lasrifle.renderPart("Gun");
		ResourceManager.lasrifle.renderPart("Stock");
		if(hasScope(stack)) ResourceManager.lasrifle.renderPart("Scope");
		ResourceManager.lasrifle.renderPart("Lever");
		ResourceManager.lasrifle.renderPart("Battery");
		if(!hasShotgun(stack)) ResourceManager.lasrifle.renderPart("Barrel");
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.lasrifle_mods_tex);
		if(hasShotgun(stack)) ResourceManager.lasrifle_mods.renderPart("BarrelShotgun");
		if(hasCapacitor(stack)) ResourceManager.lasrifle_mods.renderPart("UnderBarrel");
		GL11.glShadeModel(GL11.GL_FLAT);
	}
	
	public boolean hasScope(ItemStack stack) {
		return !WeaponModManager.hasUpgrade(stack, 0, WeaponModManager.ID_LAS_AUTO);
	}
	
	public boolean hasShotgun(ItemStack stack) {
		return WeaponModManager.hasUpgrade(stack, 0, WeaponModManager.ID_LAS_SHOTGUN);
	}
	
	public boolean hasCapacitor(ItemStack stack) {
		return WeaponModManager.hasUpgrade(stack, 0, WeaponModManager.ID_LAS_CAPACITOR);
	}
}

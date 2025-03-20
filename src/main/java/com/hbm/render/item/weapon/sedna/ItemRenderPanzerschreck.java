package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.mods.WeaponModManager;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ItemRenderPanzerschreck extends ItemRenderWeaponBase {

	@Override
	protected float getTurnMagnitude(ItemStack stack) { return ItemGunBaseNT.getIsAiming(stack) ? 2.5F : -0.25F; }

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
				-2.75F * offset, -2F * offset, 2.5F * offset,
			-0.9375, -9.25 / 8D, 0.25);
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {
		
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.panzerschreck_tex);
		double scale = 1.25D;
		GL11.glScaled(scale, scale, scale);

		double[] equip = HbmAnimations.getRelevantTransformation("EQUIP");
		double[] reload = HbmAnimations.getRelevantTransformation("RELOAD");
		double[] rocket = HbmAnimations.getRelevantTransformation("ROCKET");
		
		GL11.glTranslated(0, -1, -1);
		GL11.glRotated(equip[0], 1, 0, 0);
		GL11.glTranslated(0, 1, 1);
		
		GL11.glTranslated(0, -4, -3);
		GL11.glRotated(reload[0], 1, 0, 0);
		GL11.glTranslated(0, 4, 3);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		ResourceManager.panzerschreck.renderPart("Tube");
		if(hasShield(stack)) ResourceManager.panzerschreck.renderPart("Shield");
		
		GL11.glPushMatrix();
		GL11.glTranslated(rocket[0], rocket[1], rocket[2]);
		ResourceManager.panzerschreck.renderPart("Rocket");
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, 6.5);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glRotated(90 * gun.shotRand, 1, 0, 0);
		GL11.glScaled(0.75, 0.75, 0.75);
		this.renderMuzzleFlash(gun.lastShot[0], 150, 7.5);
		GL11.glPopMatrix();
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		double scale = 3D;
		GL11.glScaled(scale, scale, scale);
		GL11.glTranslated(0, 0.5, 1);

	}

	@Override
	public void setupInv(ItemStack stack) {
		super.setupInv(stack);
		double scale = 1.5D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(45, 0, 1, 0);
		GL11.glTranslated(-0.5, 0.5, 0);
	}

	@Override
	public void setupModTable(ItemStack stack) {
		double scale = -10D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(90, 0, 1, 0);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.panzerschreck_tex);
		ResourceManager.panzerschreck.renderPart("Tube");
		if(hasShield(stack)) ResourceManager.panzerschreck.renderPart("Shield");
		GL11.glShadeModel(GL11.GL_FLAT);
	}
	
	public boolean hasShield(ItemStack stack) {
		return !WeaponModManager.hasUpgrade(stack, 0, WeaponModManager.ID_NO_SHIELD);
	}
}

package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.mods.WeaponModManager;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemRenderAmat extends ItemRenderWeaponBase {
	
	public ResourceLocation texture;
	
	public ItemRenderAmat(ResourceLocation texture) {
		this.texture = texture;
	}

	@Override
	protected float getTurnMagnitude(ItemStack stack) { return ItemGunBaseNT.getIsAiming(stack) ? 2.5F : -0.5F; }

	@Override
	public float getViewFOV(ItemStack stack, float fov) {
		float aimingProgress = ItemGunBaseNT.prevAimingProgress + (ItemGunBaseNT.aimingProgress - ItemGunBaseNT.prevAimingProgress) * interp;
		return  fov * (1 - aimingProgress * (isScoped(stack) ? 0.8F : 0.33F));
	}

	@Override
	public void setupFirstPerson(ItemStack stack) {
		GL11.glTranslated(0, 0, 0.875);
		
		float offset = 0.8F;
		
		standardAimingTransform(stack,
			-1F * offset, -1F * offset, 3.25F * offset,
			0, -4.875 / 8D, 1.875);
	}
	
	@Override
	public void renderFirstPerson(ItemStack stack) {
		boolean isScoped = isScoped(stack);
		if(isScoped && ItemGunBaseNT.prevAimingProgress == 1 && ItemGunBaseNT.aimingProgress == 1) return;
		
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		double scale = 0.375D;
		GL11.glScaled(scale, scale, scale);

		boolean deployed = HbmAnimations.getRelevantAnim(0) == null || HbmAnimations.getRelevantAnim(0).animation.getBus("BIPOD") == null;
		double[] equip = HbmAnimations.getRelevantTransformation("EQUIP");
		double[] bipod = HbmAnimations.getRelevantTransformation("BIPOD");
		double[] lift = HbmAnimations.getRelevantTransformation("LIFT");
		double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
		double[] boltTurn = HbmAnimations.getRelevantTransformation("BOLT_TURN");
		double[] boltPull = HbmAnimations.getRelevantTransformation("BOLT_PULL");
		double[] mag = HbmAnimations.getRelevantTransformation("MAG");
		double[] scopeThrow = HbmAnimations.getRelevantTransformation("SCOPE_THROW");
		double[] scopeSpin = HbmAnimations.getRelevantTransformation("SCOPE_SPIN");

		GL11.glTranslated(0, 0, recoil[2]);
		
		GL11.glTranslated(0, -3, -8);
		GL11.glRotated(equip[0], 1, 0, 0);
		GL11.glRotated(lift[0], 1, 0, 0);
		GL11.glTranslated(0, 3, 8);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		ResourceManager.amat.renderPart("Gun");
		
		if(isScoped(stack)) {
			GL11.glPushMatrix();
			GL11.glTranslated(scopeThrow[0], scopeThrow[1], scopeThrow[2]);
			GL11.glTranslated(0, 1.5, -4.5);
			GL11.glRotated(scopeSpin[0], 1, 0, 0);
			GL11.glTranslated(0, -1.5, 4.5);
			ResourceManager.amat.renderPart("Scope");
			GL11.glPopMatrix();
		}
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0.625, 0);
		GL11.glRotated(boltTurn[2], 0, 0, 1);
		GL11.glTranslated(0, -0.625, 0);
		GL11.glTranslated(0, 0, boltPull[2]);
		ResourceManager.amat.renderPart("Bolt");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(mag[0], mag[1], mag[2]);
		ResourceManager.amat.renderPart("Magazine");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(0.3125, -0.625, -1);
		GL11.glRotated(deployed ? 25 : bipod[1], 0, 0, 1);
		GL11.glTranslated(-0.3125, 0.625, 1);
		ResourceManager.amat.renderPart("BipodHingeLeft");
		GL11.glTranslated(0.3125, -0.625, -1);
		GL11.glRotated(deployed ? 80 : bipod[0], 1, 0, 0);
		GL11.glTranslated(-0.3125, 0.625, 1);
		ResourceManager.amat.renderPart("BipodLeft");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(-0.3125, -0.625, -1);
		GL11.glRotated(deployed ? -25 : -bipod[1], 0, 0, 1);
		GL11.glTranslated(0.3125, 0.625, 1);
		ResourceManager.amat.renderPart("BipodHingeRight");
		GL11.glTranslated(-0.3125, -0.625, -1);
		GL11.glRotated(deployed ? 80 : bipod[0], 1, 0, 0);
		GL11.glTranslated(0.3125, 0.625, 1);
		ResourceManager.amat.renderPart("BipodRight");
		GL11.glPopMatrix();
		
		if(isSilenced(stack)) {
			GL11.glTranslated(0, 0.625, -4.3125);
			GL11.glScaled(1.25, 1.25, 1.25);
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.g3_attachments);
			ResourceManager.g3.renderPart("Silencer");
			
			GL11.glShadeModel(GL11.GL_FLAT);
		} else {
			ResourceManager.amat.renderPart("MuzzleBrake");

			double smokeScale = 0.5;
			
			GL11.glPushMatrix();
			GL11.glTranslated(0, 0.625, 12);
			GL11.glRotated(90, 0, 1, 0);
			GL11.glScaled(smokeScale, smokeScale, smokeScale);
			this.renderSmokeNodes(gun.getConfig(stack, 0).smokeNodes, 1D);
			GL11.glPopMatrix();
			
			GL11.glShadeModel(GL11.GL_FLAT);

			GL11.glPushMatrix();
			GL11.glTranslated(0, 0.5, 11);
			GL11.glRotated(90, 0, 1, 0);
			GL11.glScaled(0.75, 0.75, 0.75);
			this.renderGapFlash(gun.lastShot[0]);
			GL11.glPopMatrix();
		}
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		double scale = 1.25D;
		GL11.glScaled(scale, scale, scale);
		GL11.glTranslated(0, 0.5, 6.75);
	}

	@Override
	public void setupInv(ItemStack stack) {
		super.setupInv(stack);
		if(isSilenced(stack)) {
		double scale = 0.8175D;
			GL11.glScaled(scale, scale, scale);
			GL11.glRotated(25, 1, 0, 0);
			GL11.glRotated(45, 0, 1, 0);
			GL11.glTranslated(-0.5, 0.5, -1);
		} else {
			double scale = 0.9375D;
			GL11.glScaled(scale, scale, scale);
			GL11.glRotated(25, 1, 0, 0);
			GL11.glRotated(45, 0, 1, 0);
			GL11.glTranslated(-0.5, 0.5, 0);
		}
	}

	@Override
	public void setupModTable(ItemStack stack) {
		double scale = -5.75D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glTranslated(0, -0.25, -1.5);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		ResourceManager.amat.renderPart("Gun");
		ResourceManager.amat.renderPart("Bolt");
		ResourceManager.amat.renderPart("Magazine");
		ResourceManager.amat.renderPart("BipodLeft");
		ResourceManager.amat.renderPart("BipodHingeLeft");
		ResourceManager.amat.renderPart("BipodRight");
		ResourceManager.amat.renderPart("BipodHingeRight");
		if(isScoped(stack)) ResourceManager.amat.renderPart("Scope");
		if(isSilenced(stack)) {
			GL11.glTranslated(0, 0.625, -4.3125);
			GL11.glScaled(1.25, 1.25, 1.25);
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.g3_attachments);
			ResourceManager.g3.renderPart("Silencer");
		} else {
			ResourceManager.amat.renderPart("MuzzleBrake");
		}
		GL11.glShadeModel(GL11.GL_FLAT);
	}
	
	public boolean isScoped(ItemStack stack) {
		return true;
	}
	
	public boolean isSilenced(ItemStack stack) {
		return stack.getItem() == ModItems.gun_amat_penance || WeaponModManager.hasUpgrade(stack, 0, WeaponModManager.ID_SILENCER);
	}
}

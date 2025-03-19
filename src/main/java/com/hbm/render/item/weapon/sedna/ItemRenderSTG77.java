package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ItemRenderSTG77 extends ItemRenderWeaponBase {

	@Override
	protected float getTurnMagnitude(ItemStack stack) { return ItemGunBaseNT.getIsAiming(stack) ? 0.5F : -0.25F; }

	@Override
	public void setupFirstPerson(ItemStack stack) {
		GL11.glTranslated(0, 0, 0.875);
		
		float offset = 0.8F;
		standardAimingTransform(stack,
				-1.5F * offset, -1F * offset, 2.5F * offset,
			0, -5.75 / 8D, 2);
	}

	@Override
	public float getViewFOV(ItemStack stack, float fov) {
		float aimingProgress = ItemGunBaseNT.prevAimingProgress + (ItemGunBaseNT.aimingProgress - ItemGunBaseNT.prevAimingProgress) * interp;
		return  fov * (1 - aimingProgress * 0.66F);
	}

	@Override
	protected float getBaseFOV(ItemStack stack) {
		float aimingProgress = ItemGunBaseNT.prevAimingProgress + (ItemGunBaseNT.aimingProgress - ItemGunBaseNT.prevAimingProgress) * interp;
		return 70F - aimingProgress * 65;
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {
		if(ItemGunBaseNT.prevAimingProgress == 1 && ItemGunBaseNT.aimingProgress == 1) return;
		
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.stg77_tex);
		double scale = 0.5D;
		GL11.glScaled(scale, scale, scale);

		double[] equip = HbmAnimations.getRelevantTransformation("EQUIP");
		double[] lift = HbmAnimations.getRelevantTransformation("LIFT");
		double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
		double[] bolt = HbmAnimations.getRelevantTransformation("BOLT");
		double[] handle = HbmAnimations.getRelevantTransformation("HANDLE");
		double[] safety = HbmAnimations.getRelevantTransformation("SAFETY");

		double[] inspectGun = HbmAnimations.getRelevantTransformation("INSPECT_GUN");
		double[] inspectBarrel = HbmAnimations.getRelevantTransformation("INSPECT_BARREL");
		double[] inspectMove = HbmAnimations.getRelevantTransformation("INSPECT_MOVE");
		double[] inspectLever = HbmAnimations.getRelevantTransformation("INSPECT_LEVER");
		
		GL11.glTranslated(0, -1, -4);
		GL11.glRotated(equip[0], 1, 0, 0);
		GL11.glTranslated(0, 1, 4);
		
		GL11.glTranslated(0, 0, -4);
		GL11.glRotated(lift[0], 1, 0, 0);
		GL11.glTranslated(0, 0, 4);
		
		GL11.glTranslated(0, 0, recoil[2]);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		GL11.glPushMatrix();

		//GL11.glRotated(-70, 0, 0, 1);
		//GL11.glRotated(15, 1, 0, 0);
		GL11.glRotated(inspectGun[2], 0, 0, 1);
		GL11.glRotated(inspectGun[0], 1, 0, 0);
		
		HbmAnimations.applyRelevantTransformation("Gun");
		ResourceManager.stg77.renderPart("Gun");

		GL11.glPushMatrix();
		HbmAnimations.applyRelevantTransformation("Magazine");
		ResourceManager.stg77.renderPart("Magazine");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glRotated(inspectLever[2], 0, 0, 1);
		HbmAnimations.applyRelevantTransformation("Lever");
		ResourceManager.stg77.renderPart("Lever");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, bolt[2]);
		GL11.glPushMatrix();
		HbmAnimations.applyRelevantTransformation("Breech");
		ResourceManager.stg77.renderPart("Breech");
		GL11.glPopMatrix();
		GL11.glTranslated(0.125, 0, 0);
		GL11.glRotated(handle[2], 0, 0, 1);
		GL11.glTranslated(-0.125, 0, 0);
		HbmAnimations.applyRelevantTransformation("Handle");
		ResourceManager.stg77.renderPart("Handle");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(safety[0], 0, 0);
		HbmAnimations.applyRelevantTransformation("Safety");
		ResourceManager.stg77.renderPart("Safety");
		GL11.glPopMatrix();
		
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		//GL11.glTranslated(2, 0.75, 0);
		//GL11.glRotated(15, 1, 0, 0);
		//GL11.glRotated(0, 0, 0, 1);
		
		GL11.glTranslated(inspectMove[0], inspectMove[1], inspectMove[2]);
		GL11.glRotated(inspectBarrel[0], 1, 0, 0);
		GL11.glRotated(inspectBarrel[2], 0, 0, 1);
		HbmAnimations.applyRelevantTransformation("Gun");
		HbmAnimations.applyRelevantTransformation("Barrel");
		ResourceManager.stg77.renderPart("Barrel");
		GL11.glPopMatrix();

		double smokeScale = 0.75;
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, 8);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glScaled(smokeScale, smokeScale, smokeScale);
		this.renderSmokeNodes(gun.getConfig(stack, 0).smokeNodes, 0.5D);
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, 7.5);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glScaled(0.25, 0.25, 0.25);
		GL11.glRotated(-5 + gun.shotRand * 10, 1, 0, 0);
		this.renderGapFlash(gun.lastShot[0]);
		GL11.glPopMatrix();
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		double scale = 1.5D;
		GL11.glScaled(scale, scale, scale);
		GL11.glTranslated(0, 1, 2);

	}

	@Override
	public void setupInv(ItemStack stack) {
		super.setupInv(stack);
		double scale = 1.375D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(45, 0, 1, 0);
		GL11.glTranslated(-0.5, 0.5, 0);
	}

	@Override
	public void setupModTable(ItemStack stack) {
		double scale = -7.5D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(90, 0, 1, 0);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.stg77_tex);
		ResourceManager.stg77.renderPart("Gun");
		ResourceManager.stg77.renderPart("Barrel");
		ResourceManager.stg77.renderPart("Lever");
		ResourceManager.stg77.renderPart("Magazine");
		ResourceManager.stg77.renderPart("Safety");
		ResourceManager.stg77.renderPart("Handle");
		ResourceManager.stg77.renderPart("Breech");
		GL11.glShadeModel(GL11.GL_FLAT);
	}
}

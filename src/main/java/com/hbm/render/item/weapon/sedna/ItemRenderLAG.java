package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ItemRenderLAG extends ItemRenderWeaponBase {

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
				-1.5F * offset, -1F * offset, 1.5F * offset,
				0, -3.375 / 8D, 0.5);
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {
		
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.mike_hawk_tex);
		double scale = 0.25D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(90, 0, 1, 0);

		double[] equip = HbmAnimations.getRelevantTransformation("EQUIP");
		//double[] hammer = HbmAnimations.getRelevantTransformation("HAMMER");
		double[] addTrans = HbmAnimations.getRelevantTransformation("ADD_TRANS");
		double[] addRot = HbmAnimations.getRelevantTransformation("ADD_ROT");
		//Animation anim = HbmAnimations.getRelevantAnim(0);
		
		GL11.glTranslated(4, -4, 0);
		GL11.glRotated(-equip[0], 0, 0, 1);
		GL11.glTranslated(-4, 4, 0);

		GL11.glTranslated(addTrans[0], addTrans[1], addTrans[2]);
		GL11.glRotated(addRot[2], 0, 0, 1);
		GL11.glRotated(addRot[1], 0, 1, 0);

		GL11.glShadeModel(GL11.GL_SMOOTH);

		GL11.glPushMatrix();
		HbmAnimations.applyRelevantTransformation("Grip");
		ResourceManager.mike_hawk.renderPart("Grip");
		
		GL11.glPushMatrix();
		HbmAnimations.applyRelevantTransformation("Slide");
		
		/*if(anim != null) {
			BusAnimationSequence slideSeq = anim.animation.getBus("Hammer");
			if(slideSeq != null) GL11.glTranslated(0, 0.75, 0);
		}*/

		ResourceManager.mike_hawk.renderPart("Slide");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(3.125, 0.125, 0);
		GL11.glRotated(-25, 0, 0, 1);
		GL11.glTranslated(-3.125, -0.125, 0);
		HbmAnimations.applyRelevantTransformation("Hammer");
		ResourceManager.mike_hawk.renderPart("Hammer");
		GL11.glPopMatrix();
		
		if(gun.getConfig(stack, 0).getReceivers(stack)[0].getMagazine(stack).getAmount(stack, null) > 0) {
			GL11.glPushMatrix();
			HbmAnimations.applyRelevantTransformation("Bullet");
			ResourceManager.mike_hawk.renderPart("Bullet");
			GL11.glPopMatrix();
		}
		
		GL11.glPushMatrix();
		HbmAnimations.applyRelevantTransformation("Magazine");
		ResourceManager.mike_hawk.renderPart("Magazine");
		GL11.glPopMatrix();

		double smokeScale = 0.5;
		
		GL11.glPushMatrix();
		GL11.glTranslated(-10.25, 1, 0);
		GL11.glScaled(smokeScale, smokeScale, smokeScale);
		this.renderSmokeNodes(gun.getConfig(stack, 0).smokeNodes, 0.5D);
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPushMatrix();
		GL11.glTranslated(-10.25, 1, 0);
		GL11.glRotated(90 * gun.shotRand, 1, 0, 0);
		this.renderMuzzleFlash(gun.lastShot[0], 75, 7.5);
		GL11.glPopMatrix();
		
		GL11.glPopMatrix();
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		GL11.glTranslated(0, 1, 1);

	}

	@Override
	public void setupInv(ItemStack stack) {
		super.setupInv(stack);
		double scale = 1.5D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(45, 0, 1, 0);
		GL11.glTranslated(2.5, 1, 0);
	}

	@Override
	public void setupModTable(ItemStack stack) {
		double scale = -7.5D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glTranslated(0, 0.5, -2);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glRotated(90, 0, 1, 0);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.mike_hawk_tex);
		ResourceManager.mike_hawk.renderPart("Grip");
		ResourceManager.mike_hawk.renderPart("Slide");
		ResourceManager.mike_hawk.renderPart("Hammer");
		GL11.glShadeModel(GL11.GL_FLAT);
	}
}

package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ItemRenderMaresleg extends ItemRenderWeaponBase {

	@Override
	protected float getTurnMagnitude(ItemStack stack) { return ItemGunBaseNT.getIsAiming(stack) ? 2.5F : -0.5F; }

	@Override
	protected void setupFirstPerson(ItemStack stack) {
		GL11.glTranslated(0, 0, 0.875);
		
		float offset = 0.8F;
		standardAimingTransform(stack,
				-1.25F * offset, -1F * offset, 2F * offset,
				0, -3.875 / 8D, 1);
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {
		
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.maresleg_tex);
		double scale = 0.375D;
		GL11.glScaled(scale, scale, scale);

		double[] equip = HbmAnimations.getRelevantTransformation("EQUIP");
		double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
		double[] lever = HbmAnimations.getRelevantTransformation("LEVER");
		double[] turn = HbmAnimations.getRelevantTransformation("TURN");
		double[] lift = HbmAnimations.getRelevantTransformation("LIFT");
		double[] shell = HbmAnimations.getRelevantTransformation("SHELL");
		double[] flag = HbmAnimations.getRelevantTransformation("FLAG");

		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		GL11.glTranslated(recoil[0] * 2, recoil[1], recoil[2]);
		GL11.glRotated(recoil[2] * 5, 1, 0, 0);
		GL11.glRotated(turn[2], 0, 0, 1);

		GL11.glTranslated(0, 0, -4);
		GL11.glRotated(lift[0], 1, 0, 0);
		GL11.glTranslated(0, 0, 4);

		GL11.glTranslated(0, 0, -4);
		GL11.glRotated(equip[0], -1, 0, 0);
		GL11.glTranslated(0, 0, 4);
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 1, 8);
		GL11.glRotated(turn[2], 0, 0, -1);
		GL11.glRotated(90, 0, 1, 0);
		this.renderSmokeNodes(gun.smokeNodes, 0.25D);
		GL11.glPopMatrix();

		ResourceManager.maresleg.renderPart("Gun");
		ResourceManager.maresleg.renderPart("Stock");
		ResourceManager.maresleg.renderPart("Barrel");

		GL11.glPushMatrix();
		GL11.glTranslated(0, 0.125, -2.875);
		GL11.glRotated(lever[0], 1, 0, 0);
		GL11.glTranslated(0, -0.125, 2.875);
		ResourceManager.maresleg.renderPart("Lever");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(shell[0], shell[1] - 0.75, shell[2]);
		ResourceManager.maresleg.renderPart("Shell");
		GL11.glPopMatrix();
		
		if(flag[0] != 0) {
			GL11.glPushMatrix();
			GL11.glTranslated(0, -0.5, 0);
			ResourceManager.maresleg.renderPart("Shell");
			GL11.glPopMatrix();
		}
		
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPushMatrix();
		GL11.glTranslated(0, 1, 8);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glRotated(90 * gun.shotRand, 1, 0, 0);
		this.renderMuzzleFlash(gun.lastShot, 75, 5);
		GL11.glPopMatrix();
	}

	@Override
	protected void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		double scale = 1.75D;
		GL11.glScaled(scale, scale, scale);
		GL11.glTranslated(0, 0.25, 3);

	}

	@Override
	protected void setupInv(ItemStack stack) {
		super.setupInv(stack);
		double scale = 1.4375D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(45, 0, 1, 0);
		GL11.glTranslated(-0.5, 0.5, 0);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.maresleg_tex);
		ResourceManager.maresleg.renderPart("Gun");
		ResourceManager.maresleg.renderPart("Stock");
		ResourceManager.maresleg.renderPart("Barrel");
		ResourceManager.maresleg.renderPart("Lever");
		GL11.glShadeModel(GL11.GL_FLAT);
	}
}

package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ItemRenderMareslegAkimbo extends ItemRenderWeaponBase {
	
	@Override public boolean isAkimbo() { return true; }

	@Override
	protected float getTurnMagnitude(ItemStack stack) { return ItemGunBaseNT.getIsAiming(stack) ? 2.5F : -0.5F; }

	@Override
	public void setupFirstPerson(ItemStack stack) {
		GL11.glTranslated(0, 0, 0.875);
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {
		
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		
		float offset = 0.8F;
		
		for(int i = -1; i <= 1; i += 2) {
			
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.maresleg_tex);
			GL11.glPushMatrix();
			
			int index = i == -1 ? 0 : 1;
			
			standardAimingTransform(stack, -1.5F * offset * i, -1F * offset, 2F * offset, 0, -3.875 / 8D, 1);
			
			double scale = 0.375D;
			GL11.glScaled(scale, scale, scale);
	
			double[] equip = HbmAnimations.getRelevantTransformation("EQUIP", index);
			double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL", index);
			double[] lever = HbmAnimations.getRelevantTransformation("LEVER", index);
			double[] turn = HbmAnimations.getRelevantTransformation("TURN", index);
			double[] flip = HbmAnimations.getRelevantTransformation("FLIP", index);
			double[] lift = HbmAnimations.getRelevantTransformation("LIFT", index);
			double[] shell = HbmAnimations.getRelevantTransformation("SHELL", index);
			double[] flag = HbmAnimations.getRelevantTransformation("FLAG", index);
	
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
	
			GL11.glTranslated(0, 0, -2);
			GL11.glRotated(flip[0], -1, 0, 0);
			GL11.glTranslated(0, 0, 2);
			
			GL11.glPushMatrix();
			GL11.glTranslated(0, 1, 3.75);
			GL11.glRotated(turn[2], 0, 0, -1);
			GL11.glRotated(flip[0], 1, 0, 0);
			GL11.glRotated(90, 0, 1, 0);
			this.renderSmokeNodes(gun.getConfig(stack, index).smokeNodes, 0.25D);
			GL11.glPopMatrix();
	
			ResourceManager.maresleg.renderPart("Gun");
	
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
			GL11.glTranslated(0, 1, 3.75);
			GL11.glRotated(90, 0, 1, 0);
			GL11.glRotated(90 * gun.shotRand, 1, 0, 0);
			this.renderMuzzleFlash(gun.lastShot[index], 75, 5);
			GL11.glPopMatrix();
			
			GL11.glPopMatrix();
		}
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		double scale = 1.75D;
		GL11.glScaled(scale, scale, scale);
		GL11.glTranslated(0, 0.25, 3);
	}

	@Override
	public void setupThirdPersonAkimbo(ItemStack stack) {
		super.setupThirdPersonAkimbo(stack);
		double scale = 1.75D;
		GL11.glScaled(scale, scale, scale);
		GL11.glTranslated(0, 0.25, 3);
	}

	@Override
	public void setupInv(ItemStack stack) {
		GL11.glAlphaFunc(GL11.GL_GREATER, 0F);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glScaled(1, 1, -1);
		GL11.glTranslated(8, 8, 0);
		double scale = 2.5D;
		GL11.glScaled(scale, scale, scale);
	}

	@Override
	public void setupModTable(ItemStack stack) {
		double scale = -12.5D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glTranslated(0, -0.5, 1);
	}

	@Override
	public void renderInv(ItemStack stack) {
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.maresleg_tex);

		GL11.glPushMatrix();
		GL11.glRotated(225, 0, 0, 1);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(45, 0, 1, 0);
		GL11.glTranslated(-1, 0, 0);
		ResourceManager.maresleg.renderPart("Gun");
		ResourceManager.maresleg.renderPart("Lever");
		GL11.glPopMatrix();

		GL11.glTranslated(0, 0, 5);
		GL11.glPushMatrix();
		GL11.glRotated(225, 0, 0, 1);
		GL11.glRotated(-90, 0, 1, 0);
		GL11.glRotated(-90, 1, 0, 0);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(-45, 0, 1, 0);
		GL11.glTranslated(1, 0, 0);
		ResourceManager.maresleg.renderPart("Gun");
		ResourceManager.maresleg.renderPart("Lever");
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);
	}

	@Override
	public void renderModTable(ItemStack stack, int index) {
		renderOther(stack, ItemRenderType.INVENTORY);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {

		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.maresleg_tex);
		ResourceManager.maresleg.renderPart("Gun");
		ResourceManager.maresleg.renderPart("Lever");
		GL11.glShadeModel(GL11.GL_FLAT);
	}
}

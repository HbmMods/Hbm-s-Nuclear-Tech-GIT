package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ItemRenderDANI extends ItemRenderWeaponBase {
	
	@Override public boolean isAkimbo() { return true; }

	@Override
	protected float getTurnMagnitude(ItemStack stack) { return ItemGunBaseNT.getIsAiming(stack) ? 2.5F : -0.25F; }

	@Override
	public void setupFirstPerson(ItemStack stack) {
		GL11.glTranslated(0, 0, 0.875);
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {
		
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		float offset = 0.8F;
		
		for(int i = -1; i <= 1; i += 2) {
			
			int index = i == -1 ? 0 : 1;
			Minecraft.getMinecraft().renderEngine.bindTexture(index == 0 ? ResourceManager.dani_celestial_tex : ResourceManager.dani_lunar_tex);
			
			GL11.glPushMatrix();
			
			standardAimingTransform(stack,
					-1.5F * offset * i, -0.75F * offset, 1F * offset,
					0, -3.125 / 8D, 0.25);
			
			double scale = 0.125D;
			GL11.glScaled(scale, scale, scale);
	
			GL11.glShadeModel(GL11.GL_SMOOTH);
			double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL", index);
			double[] reloadMove = HbmAnimations.getRelevantTransformation("RELOAD_MOVE", index);
			double[] reloadRot = HbmAnimations.getRelevantTransformation("RELOAD_ROT", index);
			double[] equip = HbmAnimations.getRelevantTransformation("EQUIP", index);
			
			GL11.glTranslated(recoil[0], recoil[1], recoil[2]);
			GL11.glRotated(recoil[2] * 10, 1, 0, 0);
	
			GL11.glTranslated(0, -2, -2);
			GL11.glRotated(equip[0], -1, 0, 0);
			GL11.glTranslated(0, 2, 2);
			
			GL11.glPushMatrix();
			GL11.glTranslated(0, 1.5, 9.25);
			GL11.glRotated(-recoil[2] * 10, 1, 0, 0);
			GL11.glRotated(90, 0, 1, 0);
			this.renderSmokeNodes(gun.getConfig(stack, index).smokeNodes, 0.5D);
			GL11.glPopMatrix();
			
			GL11.glTranslated(reloadMove[0], reloadMove[1], reloadMove[2]);
	
			GL11.glRotated(reloadRot[0], 1, 0, 0);
			GL11.glRotated(reloadRot[2] * i, 0, 0, 1);
			GL11.glRotated(reloadRot[1] * i, 0, 1, 0);
			ResourceManager.bio_revolver.renderPart("Grip");
			
			GL11.glPushMatrix(); /// FRONT PUSH ///
			GL11.glRotated(HbmAnimations.getRelevantTransformation("FRONT", index)[2], 1, 0, 0);
			ResourceManager.bio_revolver.renderPart("Barrel");
			GL11.glPushMatrix(); /// LATCH PUSH ///
			GL11.glTranslated(0, 2.3125, -0.875);
			GL11.glRotated(HbmAnimations.getRelevantTransformation("LATCH", index)[2], 1, 0, 0);
			GL11.glTranslated(0, -2.3125, 0.875);
			ResourceManager.bio_revolver.renderPart("Latch");
			GL11.glPopMatrix(); /// LATCH POP ///
			
			GL11.glPushMatrix(); /// DRUM PUSH ///
			GL11.glTranslated(0, 1, 0);
			GL11.glRotated(HbmAnimations.getRelevantTransformation("DRUM", index)[2] * 60, 0, 0, 1);
			GL11.glTranslated(0, -1, 0);
			GL11.glTranslated(0, 0, HbmAnimations.getRelevantTransformation("DRUM_PUSH", index)[2]);
			ResourceManager.bio_revolver.renderPart("Drum");
			GL11.glPopMatrix(); /// DRUM POP ///
			
			GL11.glPopMatrix(); /// FRONT POP ///
			
			GL11.glPushMatrix(); /// HAMMER ///
			GL11.glTranslated(0, 0, -4.5);
			GL11.glRotated(-45 + 45 * HbmAnimations.getRelevantTransformation("HAMMER", index)[2], 1, 0, 0);
			GL11.glTranslated(0, 0, 4.5);
			ResourceManager.bio_revolver.renderPart("Hammer");
			GL11.glPopMatrix();
			
			GL11.glShadeModel(GL11.GL_FLAT);
	
			GL11.glPushMatrix();
			GL11.glTranslated(0, 1.5, 9.25);
			GL11.glRotated(90, 0, 1, 0);
			this.renderMuzzleFlash(gun.lastShot[index], 75, 7.5);
			GL11.glPopMatrix();

			GL11.glPopMatrix();
		}
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		GL11.glTranslated(0, 1, 3);
	}

	@Override
	public void setupThirdPersonAkimbo(ItemStack stack) {
		super.setupThirdPersonAkimbo(stack);
		GL11.glTranslated(0, 1, 3);
	}

	@Override
	public void setupInv(ItemStack stack) {
		GL11.glScaled(1, 1, -1);
		GL11.glTranslated(8, 6, 0);
		double scale = 1.125D;
		GL11.glScaled(scale, scale, scale);
	}

	@Override
	public void renderInv(ItemStack stack) {
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glShadeModel(GL11.GL_SMOOTH);

		GL11.glPushMatrix();
		GL11.glRotated(225, 0, 0, 1);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(45, 0, 1, 0);
		GL11.glTranslated(2, 0, 0);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.dani_celestial_tex);
		ResourceManager.bio_revolver.renderAll();
		GL11.glPopMatrix();

		GL11.glTranslated(0, 0, 5);
		GL11.glPushMatrix();
		GL11.glRotated(225, 0, 0, 1);
		GL11.glRotated(-90, 0, 1, 0);
		GL11.glRotated(-90, 1, 0, 0);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(-45, 0, 1, 0);
		GL11.glTranslated(-2, 0, 0);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.dani_lunar_tex);
		ResourceManager.bio_revolver.renderAll();
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);
	}

	@Override
	public void renderEquipped(ItemStack stack) {

		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.dani_lunar_tex);
		ResourceManager.bio_revolver.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);
	}

	@Override
	public void renderEquippedAkimbo(ItemStack stack) {

		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.dani_celestial_tex);
		ResourceManager.bio_revolver.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {

		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.dani_celestial_tex);
		ResourceManager.bio_revolver.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);
	}
}

package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;

public class ItemRenderEOTT extends ItemRenderWeaponBase {
	
	@Override public boolean isAkimbo() { return true; }

	@Override
	protected float getTurnMagnitude(ItemStack stack) { return ItemGunBaseNT.getIsAiming(stack) ? 2.5F : -0.25F; }

	@Override
	public float getViewFOV(ItemStack stack, float fov) {
		float aimingProgress = ItemGunBaseNT.prevAimingProgress + (ItemGunBaseNT.aimingProgress - ItemGunBaseNT.prevAimingProgress) * interp;
		return  fov * (1 - aimingProgress * 0.33F);
	}

	@Override
	public void setupFirstPerson(ItemStack stack) {
		GL11.glTranslated(0, 0, 1);
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		float offset = 0.8F;
		
		for(int i = -1; i <= 1; i += 2) {
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.eott_tex);
			int index = i == -1 ? 0 : 1;
			
			GL11.glPushMatrix();
			standardAimingTransform(stack,
					-1.0F * offset * i, -1.25F * offset, 1.25F * offset,
					0, -5.25 / 8D, 0.125);
			
			double scale = 0.25D;
			GL11.glScaled(scale, scale, scale);
	
			double[] equip = HbmAnimations.getRelevantTransformation("EQUIP", index);
			double[] rise = HbmAnimations.getRelevantTransformation("RISE", index);
			double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL", index);
			double[] slide = HbmAnimations.getRelevantTransformation("SLIDE", index);
			double[] bullet = HbmAnimations.getRelevantTransformation("BULLET", index);
			double[] hammer = HbmAnimations.getRelevantTransformation("HAMMER", index);
			double[] roll = HbmAnimations.getRelevantTransformation("ROLL", index);
			double[] mag = HbmAnimations.getRelevantTransformation("MAG", index);
			double[] magroll = HbmAnimations.getRelevantTransformation("MAGROLL", index);
			double[] sight = HbmAnimations.getRelevantTransformation("SIGHT", index);
	
			GL11.glTranslated(0, rise[1], 0);
			
			GL11.glTranslated(0, 1, -2.25);
			GL11.glRotated(equip[0], 1, 0, 0);
			GL11.glTranslated(0, -1, 2.25);
			
			GL11.glTranslated(0, -1, -4);
			GL11.glRotated(recoil[0], 1, 0, 0);
			GL11.glTranslated(0, 1, 4);
			
			GL11.glTranslated(0, 1, 0);
			GL11.glRotated(roll[2] * i, 0, 0, 1);
			GL11.glTranslated(0, -1, 0);
			
			GL11.glShadeModel(GL11.GL_SMOOTH);
			
			ResourceManager.aberrator.renderPart("Gun");
			
			GL11.glPushMatrix();
			GL11.glTranslated(0, 2.4375, -1.9375);
			GL11.glRotated(sight[0], 1, 0, 0);
			GL11.glTranslated(0, -2.4375, 1.9375);
			ResourceManager.aberrator.renderPart("Sight");
			GL11.glPopMatrix();
	
			GL11.glPushMatrix();
			GL11.glTranslated(mag[0] * i, mag[1], mag[2]);
			
			GL11.glTranslated(0, 1, 0);
			GL11.glRotated(magroll[2] * i, 0, 0, 1);
			GL11.glTranslated(0, -1, 0);
			
			ResourceManager.aberrator.renderPart("Magazine");
			GL11.glTranslated(bullet[0], bullet[1], bullet[2]);
			ResourceManager.aberrator.renderPart("Bullet");
			GL11.glPopMatrix();
	
			GL11.glPushMatrix();
			GL11.glTranslated(0, 0, slide[2]);
			ResourceManager.aberrator.renderPart("Slide");
			GL11.glPopMatrix();
			
			GL11.glPushMatrix();
			GL11.glTranslated(0, 1.25, -3.625);
			GL11.glRotated(-45 + hammer[0], 1, 0, 0);
			GL11.glTranslated(0, -1.25, 3.625);
			ResourceManager.aberrator.renderPart("Hammer");
			GL11.glPopMatrix();
	
			double smokeScale = 0.5;
			
			GL11.glPushMatrix();
			GL11.glTranslated(0, 2, 4);
			GL11.glRotated(recoil[0], -1, 0, 0);
			GL11.glRotated(roll[2] * i, 0, 0, -1);
			GL11.glRotated(90, 0, 1, 0);
			GL11.glScaled(smokeScale, smokeScale, smokeScale);
			this.renderSmokeNodes(gun.getConfig(stack, index).smokeNodes, 0.5D);
			GL11.glPopMatrix();
			
			GL11.glShadeModel(GL11.GL_FLAT);
	
			GL11.glPushMatrix();
			GL11.glTranslated(0, 2, 4);
			GL11.glRotated(90, 0, 1, 0);
			GL11.glRotated(90 * gun.shotRand, 1, 0, 0);
			GL11.glScaled(0.75, 0.75, 0.75);
			this.renderMuzzleFlash(gun.lastShot[index], 75, 7.5);
			GL11.glPopMatrix();
			
			GL11.glPushMatrix();
			GL11.glTranslated(0, 2, -1.5);
			GL11.glScaled(0.5, 0.5, 0.5);
			this.renderFireball(gun.lastShot[index]);
			GL11.glPopMatrix();

			GL11.glPopMatrix();
		}
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		GL11.glTranslated(0, -1, 4);
		double scale = 1.5D;
		GL11.glScaled(scale, scale, scale);
	}
	
	public void setupThirdPersonAkimbo(ItemStack stack) {
		super.setupThirdPersonAkimbo(stack);
		GL11.glTranslated(0, -1, 4);
		double scale = 1.5D;
		GL11.glScaled(scale, scale, scale);

	}

	@Override
	public void setupInv(ItemStack stack) {
		GL11.glScaled(1, 1, -1);
		GL11.glTranslated(8, 8, 0);
		double scale = 2.5D;
		GL11.glScaled(scale, scale, scale);
	}

	@Override
	public void renderInv(ItemStack stack) {
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glTranslated(0, 1, 0);

		GL11.glPushMatrix();
		GL11.glRotated(225, 0, 0, 1);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(45, 0, 1, 0);
		GL11.glTranslated(-1, 0, 0);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.eott_tex);
		ResourceManager.aberrator.renderPart("Gun");
		ResourceManager.aberrator.renderPart("Hammer");
		ResourceManager.aberrator.renderPart("Magazine");
		ResourceManager.aberrator.renderPart("Slide");
		ResourceManager.aberrator.renderPart("Sight");
		GL11.glPopMatrix();

		GL11.glTranslated(0, 0, 5);
		GL11.glPushMatrix();
		GL11.glRotated(225, 0, 0, 1);
		GL11.glRotated(-90, 0, 1, 0);
		GL11.glRotated(-90, 1, 0, 0);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(-45, 0, 1, 0);
		GL11.glTranslated(1, 0, 0);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.eott_tex);
		ResourceManager.aberrator.renderPart("Gun");
		ResourceManager.aberrator.renderPart("Hammer");
		ResourceManager.aberrator.renderPart("Magazine");
		ResourceManager.aberrator.renderPart("Slide");
		ResourceManager.aberrator.renderPart("Sight");
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);
	}

	@Override
	public void renderEquipped(ItemStack stack) {

		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.eott_tex);
		ResourceManager.aberrator.renderPart("Gun");
		ResourceManager.aberrator.renderPart("Hammer");
		ResourceManager.aberrator.renderPart("Magazine");
		ResourceManager.aberrator.renderPart("Slide");
		ResourceManager.aberrator.renderPart("Sight");
		GL11.glShadeModel(GL11.GL_FLAT);
	}

	@Override
	public void renderEquippedAkimbo(ItemStack stack) {

		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.eott_tex);
		ResourceManager.aberrator.renderPart("Gun");
		ResourceManager.aberrator.renderPart("Hammer");
		ResourceManager.aberrator.renderPart("Magazine");
		ResourceManager.aberrator.renderPart("Slide");
		ResourceManager.aberrator.renderPart("Sight");
		GL11.glShadeModel(GL11.GL_FLAT);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0F);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.eott_tex);
		ResourceManager.aberrator.renderPart("Gun");
		ResourceManager.aberrator.renderPart("Hammer");
		ResourceManager.aberrator.renderPart("Magazine");
		ResourceManager.aberrator.renderPart("Slide");
		ResourceManager.aberrator.renderPart("Sight");
		GL11.glShadeModel(GL11.GL_FLAT);
	}
	
	public static void renderFireball(long lastShot) {
		Tessellator tess = Tessellator.instance;
		
		int flash = 150;
		
		if(System.currentTimeMillis() - lastShot < flash) {
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			GL11.glPushMatrix();
			
			double fire = (System.currentTimeMillis() - lastShot) / (double) flash;
			double height = 5 * fire;
			double length = 10 * fire;
			double offset = 1 * fire;
			double lengthOffset = -1.125;
			Minecraft.getMinecraft().renderEngine.bindTexture(flash_plume);
			tess.startDrawingQuads();
			tess.setNormal(0F, 1F, 0F);
			tess.setColorRGBA_F(1F, 1F, 1F, 1F);

			tess.addVertexWithUV(height, -offset, 0, 0, 1);
			tess.addVertexWithUV(-height, -offset, 0, 1, 1);
			tess.addVertexWithUV(-height, -offset + length, -lengthOffset, 1, 0);
			tess.addVertexWithUV(height, -offset + length, -lengthOffset, 0 ,0);

			tess.addVertexWithUV(height, -offset, 0, 0, 1);
			tess.addVertexWithUV(-height, -offset, 0, 1, 1);
			tess.addVertexWithUV(-height, -offset + length, lengthOffset, 1, 0);
			tess.addVertexWithUV(height, -offset + length, lengthOffset, 0 ,0);
			
			tess.draw();
			GL11.glPopMatrix();
			GL11.glDisable(GL11.GL_BLEND);
		}
	}
}

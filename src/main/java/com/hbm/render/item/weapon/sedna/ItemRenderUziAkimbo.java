package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.mods.WeaponModManager;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ItemRenderUziAkimbo extends ItemRenderWeaponBase {
	
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
		GL11.glTranslated(0, 0, 0.875);
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {
		
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		
		float offset = 0.8F;
		
		for(int i = -1; i <= 1; i += 2) {
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.uzi_tex);

			GL11.glPushMatrix();
			int index = i == -1 ? 0 : 1;
			standardAimingTransform(stack, -2.25F * offset * i, -1.5F * offset, 2.5F * offset, 0, -4.375 / 8D, 1);
			
			double scale = 0.25D;
			GL11.glScaled(scale, scale, scale);
	
			double[] equip = HbmAnimations.getRelevantTransformation("EQUIP", index);
			double[] stockFront = HbmAnimations.getRelevantTransformation("STOCKFRONT", index);
			double[] stockBack = HbmAnimations.getRelevantTransformation("STOCKBACK", index);
			double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL", index);
			double[] lift = HbmAnimations.getRelevantTransformation("LIFT", index);
			double[] mag = HbmAnimations.getRelevantTransformation("MAG", index);
			double[] bullet = HbmAnimations.getRelevantTransformation("BULLET", index);
			double[] slide = HbmAnimations.getRelevantTransformation("SLIDE", index);
			double[] yeet = HbmAnimations.getRelevantTransformation("YEET", index);
			double[] speen = HbmAnimations.getRelevantTransformation("SPEEN", index);
	
			GL11.glTranslated(yeet[0], yeet[1], yeet[2]);
			GL11.glRotated(speen[0], 0, 0, i);
			
			GL11.glTranslated(0, -2, -4);
			GL11.glRotated(equip[0], 1, 0, 0);
			GL11.glTranslated(0, 2, 4);
			
			GL11.glTranslated(0, 0, -6);
			GL11.glRotated(lift[0], 1, 0, 0);
			GL11.glTranslated(0, 0, 6);
			
			GL11.glTranslated(0, 0, recoil[2]);
	
			GL11.glShadeModel(GL11.GL_SMOOTH);
			ResourceManager.uzi.renderPart(index == 0 ? "GunMirror" : "Gun");

			boolean silenced = hasSilencer(stack, index);
			if(silenced) ResourceManager.uzi.renderPart("Silencer");
			
			GL11.glPushMatrix();
			GL11.glTranslated(0, 0.3125D, -5.75);
			GL11.glRotated(180 - stockFront[0], 1, 0, 0);
			GL11.glTranslated(0, -0.3125D, 5.75);
			ResourceManager.uzi.renderPart("StockFront");
			
			GL11.glTranslated(0, -0.3125D, -3);
			GL11.glRotated(-200 - stockBack[0], 1, 0, 0);
			GL11.glTranslated(0, 0.3125D, 3);
			ResourceManager.uzi.renderPart("StockBack");
			GL11.glPopMatrix();
	
			GL11.glPushMatrix();
			GL11.glTranslated(0, 0, slide[2]);
			ResourceManager.uzi.renderPart("Slide");
			GL11.glPopMatrix();
			
			GL11.glPushMatrix();
			GL11.glTranslated(mag[0], mag[1], mag[2]);
			ResourceManager.uzi.renderPart("Magazine");
			if(bullet[0] == 1) ResourceManager.uzi.renderPart("Bullet");
			GL11.glPopMatrix();
			
			if(!silenced) {
				double smokeScale = 0.5;
				
				GL11.glPushMatrix();
				GL11.glTranslated(0, 0.75, 8.5);
				GL11.glRotated(90, 0, 1, 0);
				GL11.glScaled(smokeScale, smokeScale, smokeScale);
				this.renderSmokeNodes(gun.getConfig(stack, index).smokeNodes, 0.75D);
				GL11.glPopMatrix();
				
				GL11.glShadeModel(GL11.GL_FLAT);
		
				GL11.glPushMatrix();
				GL11.glTranslated(0, 0.75, 8.5);
				GL11.glRotated(90, 0, 1, 0);
				GL11.glRotated(90 * gun.shotRand, 1, 0, 0);
				this.renderMuzzleFlash(gun.lastShot[index], 75, 7.5);
				GL11.glPopMatrix();
			}
			
			GL11.glPopMatrix();
		}
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		GL11.glTranslated(0, 1, 1);
	}

	@Override
	public void setupThirdPersonAkimbo(ItemStack stack) {
		super.setupThirdPersonAkimbo(stack);
		GL11.glTranslated(0, 1, 1);
	}

	@Override
	public void setupInv(ItemStack stack) {
		GL11.glAlphaFunc(GL11.GL_GREATER, 0F);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glScaled(1, 1, -1);
		GL11.glTranslated(8, 8, 0);
		double scale = 1.5D;
		GL11.glScaled(scale, scale, scale);
	}

	@Override
	public void setupModTable(ItemStack stack) {
		double scale = -6.25D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glTranslated(0, 1, -4);
	}

	@Override
	public void renderEquipped(ItemStack stack) {

		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.uzi_tex);
		ResourceManager.uzi.renderPart("Gun");
		ResourceManager.uzi.renderPart("StockBack");
		ResourceManager.uzi.renderPart("StockFront");
		ResourceManager.uzi.renderPart("Slide");
		ResourceManager.uzi.renderPart("Magazine");
		if(hasSilencer(stack, 1)) ResourceManager.uzi.renderPart("Silencer");
		GL11.glShadeModel(GL11.GL_FLAT);
	}

	@Override
	public void renderEquippedAkimbo(ItemStack stack) {

		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.uzi_tex);
		ResourceManager.uzi.renderPart("GunMirror");
		ResourceManager.uzi.renderPart("StockBack");
		ResourceManager.uzi.renderPart("StockFront");
		ResourceManager.uzi.renderPart("Slide");
		ResourceManager.uzi.renderPart("Magazine");
		if(hasSilencer(stack, 0)) ResourceManager.uzi.renderPart("Silencer");
		GL11.glShadeModel(GL11.GL_FLAT);
	}

	@Override
	public void renderModTable(ItemStack stack, int index) {
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.uzi_tex);
		ResourceManager.uzi.renderPart(index == 0 ? "GunMirror" : "Gun");
		ResourceManager.uzi.renderPart("StockBack");
		ResourceManager.uzi.renderPart("StockFront");
		ResourceManager.uzi.renderPart("Slide");
		ResourceManager.uzi.renderPart("Magazine");
		if(hasSilencer(stack, index)) ResourceManager.uzi.renderPart("Silencer");
		GL11.glShadeModel(GL11.GL_FLAT);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.uzi_tex);

		boolean silencer0 = hasSilencer(stack, 1);
		boolean silencer1 = hasSilencer(stack, 0);
		boolean anySilenced = silencer0 || silencer1;
		
		GL11.glPushMatrix();
		GL11.glRotated(225, 0, 0, 1);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(45, 0, 1, 0);
		GL11.glTranslated(0, 1, 0);
		if(anySilenced) {
			double scale = 0.625D;
			GL11.glScaled(scale, scale, scale);
			GL11.glTranslated(0, 0, -4);
		}
		ResourceManager.uzi.renderPart("Gun");
		ResourceManager.uzi.renderPart("StockBack");
		ResourceManager.uzi.renderPart("StockFront");
		ResourceManager.uzi.renderPart("Slide");
		ResourceManager.uzi.renderPart("Magazine");
		if(silencer0) ResourceManager.uzi.renderPart("Silencer");
		GL11.glPopMatrix();
		
		GL11.glTranslated(0, 0, 5);
		
		GL11.glPushMatrix();
		GL11.glRotated(225, 0, 0, 1);
		GL11.glRotated(-90, 0, 1, 0);
		GL11.glRotated(-90, 1, 0, 0);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(-45, 0, 1, 0);
		GL11.glTranslated(0, 1, 0);
		if(anySilenced) {
			double scale = 0.625D;
			GL11.glScaled(scale, scale, scale);
			GL11.glTranslated(0, 0, -4);
		}
		ResourceManager.uzi.renderPart("GunMirror");
		ResourceManager.uzi.renderPart("StockBack");
		ResourceManager.uzi.renderPart("StockFront");
		ResourceManager.uzi.renderPart("Slide");
		ResourceManager.uzi.renderPart("Magazine");
		if(silencer1) ResourceManager.uzi.renderPart("Silencer");
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);
	}
	
	public boolean hasSilencer(ItemStack stack, int cfg) {
		return WeaponModManager.hasUpgrade(stack, cfg, WeaponModManager.ID_SILENCER);
	}
}

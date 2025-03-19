package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemRenderAtlas extends ItemRenderWeaponBase {
	
	public ResourceLocation texture;
	
	public ItemRenderAtlas(ResourceLocation texture) {
		this.texture = texture;
	}

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
				-1.0F * offset, -0.75F * offset, 1F * offset,
				0, -3.125 / 8D, 0.25);
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {
		
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		double scale = 0.125D;
		GL11.glScaled(scale, scale, scale);

		GL11.glShadeModel(GL11.GL_SMOOTH);
		double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
		double[] reloadMove = HbmAnimations.getRelevantTransformation("RELOAD_MOVE");
		double[] reloadRot = HbmAnimations.getRelevantTransformation("RELOAD_ROT");
		double[] equip = HbmAnimations.getRelevantTransformation("EQUIP");
		
		GL11.glTranslated(recoil[0], recoil[1], recoil[2]);
		GL11.glRotated(recoil[2] * 10, 1, 0, 0);

		GL11.glTranslated(0, 0, -7);
		GL11.glRotated(equip[0], -1, 0, 0);
		GL11.glTranslated(0, 0, 7);
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 1.5, 9.25);
		GL11.glRotated(-recoil[2] * 10, 1, 0, 0);
		GL11.glRotated(90, 0, 1, 0);
		this.renderSmokeNodes(gun.getConfig(stack, 0).smokeNodes, 0.5D);
		GL11.glPopMatrix();
		
		GL11.glTranslated(reloadMove[0], reloadMove[1], reloadMove[2]);

		GL11.glRotated(reloadRot[0], 1, 0, 0);
		GL11.glRotated(reloadRot[2], 0, 0, 1);
		GL11.glRotated(reloadRot[1], 0, 1, 0);
		ResourceManager.bio_revolver.renderPart("Grip");
		
		GL11.glPushMatrix(); /// FRONT PUSH ///
		GL11.glRotated(HbmAnimations.getRelevantTransformation("FRONT")[2], 1, 0, 0);
		ResourceManager.bio_revolver.renderPart("Barrel");
		GL11.glPushMatrix(); /// LATCH PUSH ///
		GL11.glTranslated(0, 2.3125, -0.875);
		GL11.glRotated(HbmAnimations.getRelevantTransformation("LATCH")[2], 1, 0, 0);
		GL11.glTranslated(0, -2.3125, 0.875);
		ResourceManager.bio_revolver.renderPart("Latch");
		GL11.glPopMatrix(); /// LATCH POP ///
		
		GL11.glPushMatrix(); /// DRUM PUSH ///
		GL11.glTranslated(0, 1, 0);
		GL11.glRotated(HbmAnimations.getRelevantTransformation("DRUM")[2] * 60, 0, 0, 1);
		GL11.glTranslated(0, -1, 0);
		GL11.glTranslated(0, 0, HbmAnimations.getRelevantTransformation("DRUM_PUSH")[2]);
		ResourceManager.bio_revolver.renderPart("Drum");
		GL11.glPopMatrix(); /// DRUM POP ///
		
		GL11.glPopMatrix(); /// FRONT POP ///
		
		GL11.glPushMatrix(); /// HAMMER ///
		GL11.glTranslated(0, 0, -4.5);
		GL11.glRotated(-45 + 45 * HbmAnimations.getRelevantTransformation("HAMMER")[2], 1, 0, 0);
		GL11.glTranslated(0, 0, 4.5);
		ResourceManager.bio_revolver.renderPart("Hammer");
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPushMatrix();
		GL11.glTranslated(0, 1.5, 9.25);
		GL11.glRotated(90, 0, 1, 0);
		this.renderMuzzleFlash(gun.lastShot[0], 75, 7.5);
		GL11.glPopMatrix();
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		double scale = 0.75D;
		GL11.glScaled(scale, scale, scale);
		GL11.glTranslated(0, 1, 3);
	}

	@Override
	public void setupInv(ItemStack stack) {
		super.setupInv(stack);
		double scale = 1.125D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(45, 0, 1, 0);
		GL11.glTranslated(-0.5, 1.5, 0);
	}

	@Override
	public void setupModTable(ItemStack stack) {
		double scale = -5D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glTranslated(0, 1.5, 0);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		ResourceManager.bio_revolver.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);
	}
}

package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.main.MainRegistry;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ItemRenderAm180 extends ItemRenderWeaponBase {

	@Override
	protected float getTurnMagnitude(ItemStack stack) { return ItemGunBaseNT.getIsAiming(stack) ? 2.5F : -0.5F; }

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
				-1F * offset, -1F * offset, 1F * offset,
				0, -4.1875 / 8D, 0.25);
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {
		
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.am180_tex);
		double scale = 0.1875D;
		GL11.glScaled(scale, scale, scale);

		double[] equip = HbmAnimations.getRelevantTransformation("EQUIP");
		double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
		double[] magazine = HbmAnimations.getRelevantTransformation("MAG");
		double[] magTurn = HbmAnimations.getRelevantTransformation("MAGTURN");
		double[] magSpin = HbmAnimations.getRelevantTransformation("MAGSPIN");
		double[] bolt = HbmAnimations.getRelevantTransformation("BOLT");
		double[] turn = HbmAnimations.getRelevantTransformation("TURN");
		
		GL11.glTranslated(0, -2, -6);
		GL11.glRotated(equip[0], 1, 0, 0);
		GL11.glTranslated(0, 2, 6);

		GL11.glRotated(turn[2], 0, 0, 1);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		GL11.glTranslated(0, 0, recoil[2]);

		HbmAnimations.applyRelevantTransformation("Gun");
		ResourceManager.am180.renderPart("Gun");
		ResourceManager.am180.renderPart("Silencer");

		GL11.glPushMatrix();
		HbmAnimations.applyRelevantTransformation("Trigger");
		ResourceManager.am180.renderPart("Trigger");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, bolt[2]);
		HbmAnimations.applyRelevantTransformation("Bolt");
		ResourceManager.am180.renderPart("Bolt");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(magazine[0], magazine[1], magazine[2]);

		GL11.glTranslated(0, 2.0625, 3.75);
		GL11.glRotated(magTurn[0], 1, 0, 0);
		GL11.glRotated(magTurn[2], 0, 0, 1);
		GL11.glTranslated(0, -2.0625, -3.75);

		GL11.glTranslated(0, 2.3125, 1.5);
		GL11.glRotated(magSpin[0], 1, 0, 0);
		GL11.glTranslated(0, -2.3125, -1.5);

		HbmAnimations.applyRelevantTransformation("Mag");
		
		GL11.glPushMatrix();
		int mag = gun.getConfig(stack, 0).getReceivers(stack)[0].getMagazine(stack).getAmount(stack, MainRegistry.proxy.me().inventory);
		GL11.glTranslated(0, 0, 1.5);
		GL11.glRotated(mag / 59D * 360D, 0, -1, 0);
		GL11.glTranslated(0, 0, -1.5);
		ResourceManager.am180.renderPart("Mag");
		GL11.glPopMatrix();
		
		ResourceManager.am180.renderPart("MagPlate");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 1.875, 17);
		GL11.glRotated(turn[2], 0, 0, -1);
		GL11.glRotated(90, 0, 1, 0);
		this.renderSmokeNodes(gun.getConfig(stack, 0).smokeNodes, 0.25D);
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPushMatrix();
		GL11.glTranslated(0, 1.875, 16.75);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glRotated(90 * gun.shotRand, 1, 0, 0);
		GL11.glScaled(0.5, 0.5, 0.5);
		this.renderMuzzleFlash(gun.lastShot[0], 75, 5);
		GL11.glPopMatrix();
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		double scale = 1D;
		GL11.glScaled(scale, scale, scale);
		GL11.glTranslated(0, -0.5, 3);

	}

	@Override
	public void setupInv(ItemStack stack) {
		super.setupInv(stack);
		double scale = 0.75D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(45, 0, 1, 0);
		GL11.glTranslated(1.5, 0, 0);
	}

	@Override
	public void setupModTable(ItemStack stack) {
		double scale = -5D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glTranslated(0, 0, -2);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.am180_tex);
		ResourceManager.am180.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);
	}
}

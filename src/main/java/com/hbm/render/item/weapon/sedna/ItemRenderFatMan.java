package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ItemRenderFatMan extends ItemRenderWeaponBase {

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
				-1.5F * offset, -1.25F * offset, 0.5F * offset,
				-1F * offset, -1.25F * offset, 0F * offset);
	}
	
	protected static String label = "AUTO";
	
	@Override
	public void renderFirstPerson(ItemStack stack) {
		
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.fatman_tex);
		double scale = 0.5D;
		GL11.glScaled(scale, scale, scale);
		
		boolean isLoaded = gun.getConfig(stack, 0).getReceivers(stack)[0].getMagazine(stack).getAmount(stack, null) > 0;

		double[] equip = HbmAnimations.getRelevantTransformation("EQUIP");
		double[] lid = HbmAnimations.getRelevantTransformation("LID");
		double[] nuke = HbmAnimations.getRelevantTransformation("NUKE");
		double[] piston = HbmAnimations.getRelevantTransformation("PISTON");
		double[] handle = HbmAnimations.getRelevantTransformation("HANDLE");
		double[] gauge = HbmAnimations.getRelevantTransformation("GAUGE");
		
		GL11.glTranslated(0, 1, -2);
		GL11.glRotated(equip[0], 1, 0, 0);
		GL11.glTranslated(0, -1, 2);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);

		ResourceManager.fatman.renderPart("Launcher");

		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, handle[2]);
		ResourceManager.fatman.renderPart("Handle");
		
		GL11.glTranslated(0.4375, -0.875, 0);
		GL11.glRotated(gauge[2], 0, 0, 1);
		GL11.glTranslated(-0.4375, 0.875, 0);
		ResourceManager.fatman.renderPart("Gauge");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(0.25, 0.125, 0);
		GL11.glRotated(lid[2], 0, 0, 1);
		GL11.glTranslated(-0.25, -0.125, 0);
		ResourceManager.fatman.renderPart("Lid");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, piston[2]);
		if(!isLoaded && piston[2] == 0) GL11.glTranslated(0, 0, 3);
		ResourceManager.fatman.renderPart("Piston");
		GL11.glPopMatrix();

		if(isLoaded || nuke[0] != 0 || nuke[1] != 0 || nuke[2] != 0) {
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.fatman_mininuke_tex);
			GL11.glPushMatrix();
			GL11.glTranslated(nuke[0], nuke[1], nuke[2]);
			ResourceManager.fatman.renderPart("MiniNuke");
			GL11.glPopMatrix();
		}
		
		GL11.glShadeModel(GL11.GL_FLAT);
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		double scale = 2.5D;
		GL11.glScaled(scale, scale, scale);
		GL11.glTranslated(-0.5, 0.5, -3);

	}

	@Override
	public void setupInv(ItemStack stack) {
		super.setupInv(stack);
		double scale = 1.375D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(45, 0, 1, 0);
		GL11.glTranslated(0, -0.5, 0);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {
		GL11.glEnable(GL11.GL_LIGHTING);

		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.fatman_tex);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		ResourceManager.fatman.renderPart("Launcher");
		ResourceManager.fatman.renderPart("Handle");
		ResourceManager.fatman.renderPart("Gauge");
		ResourceManager.fatman.renderPart("Lid");
		ResourceManager.fatman.renderPart("Piston");
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.fatman_mininuke_tex);
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		if(gun.getConfig(stack, 0).getReceivers(stack)[0].getMagazine(stack).getAmount(stack, null) > 0) ResourceManager.fatman.renderPart("MiniNuke");
		GL11.glShadeModel(GL11.GL_FLAT);
	}
}

package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ItemRenderPepperbox extends ItemRenderWeaponBase {

	@Override
	protected float getTurnMagnitude(ItemStack stack) { return ItemGunBaseNT.getIsAiming(stack) ? 2.5F : -0.5F; }

	@Override
	public float getViewFOV(ItemStack stack, float fov) {
		float aimingProgress = ItemGunBaseNT.prevAimingProgress + (ItemGunBaseNT.aimingProgress - ItemGunBaseNT.prevAimingProgress) * interp;
		return  fov * (1 - aimingProgress * 0.33F);
	}

	@Override
	public void setupFirstPerson(ItemStack stack) {
		GL11.glTranslated(0, 0, 1.5);
		
		float offset = 0.8F;
		standardAimingTransform(stack,
				-1.25F * offset, -0.75F * offset, 1F * offset,
				0, -2.5 / 8D, 0.5);
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {
		
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		
		double scale = 0.25D;
		GL11.glScaled(scale, scale, scale);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
		double[] cylinder = HbmAnimations.getRelevantTransformation("ROTATE");
		double[] hammer = HbmAnimations.getRelevantTransformation("HAMMER");
		double[] trigger = HbmAnimations.getRelevantTransformation("TRIGGER");
		double[] translate = HbmAnimations.getRelevantTransformation("TRANSLATE");
		double[] loader = HbmAnimations.getRelevantTransformation("LOADER");
		double[] shot = HbmAnimations.getRelevantTransformation("SHOT");

		GL11.glTranslated(translate[0], translate[1], translate[2]);
		
		GL11.glTranslated(0, 0, -5);
		GL11.glRotated(recoil[0], -1, 0, 0);
		GL11.glTranslated(0, 0, 5);
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0.5, 7);
		GL11.glRotated(90, 0, 1, 0);
		this.renderSmokeNodes(gun.getConfig(stack, 0).smokeNodes, 0.5D);
		GL11.glPopMatrix();
		
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.pepperbox_tex);
		
		if(loader[0] != 0 || loader[1] != 0 || loader[2] != 0) {
			GL11.glPushMatrix();
			GL11.glTranslated(loader[0], loader[1], loader[2]);
			ResourceManager.pepperbox.renderPart("Speedloader");
			if(shot[0] != 0) ResourceManager.pepperbox.renderPart("Shot");
			GL11.glPopMatrix();
		}
		
		ResourceManager.pepperbox.renderPart("Grip");
		
		GL11.glPushMatrix();
		GL11.glRotated(cylinder[0], 0, 0, 1);
		ResourceManager.pepperbox.renderPart("Cylinder");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(0, 0.375, -1.875);
		GL11.glRotated(hammer[0], 1, 0, 0);
		GL11.glTranslated(0, -0.375, 1.875);
		ResourceManager.pepperbox.renderPart("Hammer");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, -trigger[0] * 0.5);
		ResourceManager.pepperbox.renderPart("Trigger");
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPushMatrix();
		GL11.glTranslated(0, 0.5, 7);
		GL11.glScaled(0.5, 0.5, 0.5);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glRotated(90 * gun.shotRand, 1, 0, 0);
		this.renderMuzzleFlash(gun.lastShot[0]);
		GL11.glRotated(45, 1, 0, 0);
		this.renderMuzzleFlash(gun.lastShot[0]);
		GL11.glPopMatrix();
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		GL11.glTranslated(0, 1, 3);

	}

	@Override
	public void setupInv(ItemStack stack) {
		super.setupInv(stack);
		double scale = 1.5D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(45, 0, 1, 0);
		GL11.glTranslated(0.5, 0.5, 0);
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
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.pepperbox_tex);
		ResourceManager.pepperbox.renderPart("Grip");
		ResourceManager.pepperbox.renderPart("Cylinder");
		ResourceManager.pepperbox.renderPart("Hammer");
		ResourceManager.pepperbox.renderPart("Trigger");
		GL11.glShadeModel(GL11.GL_FLAT);
	}
}

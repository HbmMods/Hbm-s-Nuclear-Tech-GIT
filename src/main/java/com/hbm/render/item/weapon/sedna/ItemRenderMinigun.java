package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemRenderMinigun extends ItemRenderWeaponBase {
	
	protected ResourceLocation texture;
	
	public ItemRenderMinigun(ResourceLocation texture) {
		this.texture = texture;
	}

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
				-1.75F * offset, -1.75F * offset, 3.5F * offset,
			0, -6.25 / 8D, 1);
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {
		
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		double scale = 0.375D;
		GL11.glScaled(scale, scale, scale);

		double[] equip = HbmAnimations.getRelevantTransformation("EQUIP");
		double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
		double[] rotate = HbmAnimations.getRelevantTransformation("ROTATE");
		
		GL11.glTranslated(0, 3, -6);
		GL11.glRotated(equip[0], 1, 0, 0);
		GL11.glTranslated(0, -3, 6);
		
		GL11.glTranslated(0, 0, recoil[2]);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);

		ResourceManager.minigun.renderPart("Gun");
		
		GL11.glPushMatrix();
		GL11.glRotated(rotate[2], 0, 0, 1);
		ResourceManager.minigun.renderPart("Barrels");
		GL11.glPopMatrix();

		double smokeScale = 0.5;
		
		GL11.glPushMatrix();
		GL11.glTranslated(-2, 1.25, -3.5);
		GL11.glRotated(45, 0, 1, 0);
		GL11.glScaled(smokeScale, smokeScale, smokeScale);
		this.renderSmokeNodes(gun.getConfig(stack, 0).smokeNodes, 0.5D);
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, 12);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glRotated(gun.shotRand * 90, 1, 0, 0);
		GL11.glScaled(1.5, 1.5, 1.5);
		this.renderMuzzleFlash(gun.lastShot[0], 75, 5);
		GL11.glPopMatrix();
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		double scale = 1.75D;
		GL11.glScaled(scale, scale, scale);
		GL11.glTranslated(1, -3.5, 8);

	}

	@Override
	public void setupInv(ItemStack stack) {
		super.setupInv(stack);
		double scale = 0.875D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(45, 0, 1, 0);
		GL11.glTranslated(-0.25, 0.5, 0);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		ResourceManager.minigun.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);
	}
}

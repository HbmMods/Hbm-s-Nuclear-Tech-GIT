package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemRenderMaresleg extends ItemRenderWeaponBase {
	
	public ResourceLocation texture;
	
	public ItemRenderMaresleg(ResourceLocation texture) {
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
				-1.25F * offset, -1F * offset, 2F * offset,
				0, -3.875 / 8D, 1);
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {
		
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		double scale = 0.375D;
		GL11.glScaled(scale, scale, scale);
		
		boolean shortened = getShort(stack);

		double[] equip = HbmAnimations.getRelevantTransformation("EQUIP");
		double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
		double[] lever = HbmAnimations.getRelevantTransformation("LEVER");
		double[] turn = HbmAnimations.getRelevantTransformation("TURN");
		double[] flip = HbmAnimations.getRelevantTransformation("FLIP");
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
		
		GL11.glTranslated(0, 0, -2);
		GL11.glRotated(flip[0], -1, 0, 0);
		GL11.glTranslated(0, 0, 2);
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 1, shortened ? 3.75 : 8);
		GL11.glRotated(turn[2], 0, 0, -1);
		GL11.glRotated(flip[0], 1, 0, 0);
		GL11.glRotated(90, 0, 1, 0);
		this.renderSmokeNodes(gun.getConfig(stack, 0).smokeNodes, 0.25D);
		GL11.glPopMatrix();

		ResourceManager.maresleg.renderPart("Gun");
		if(!shortened) {
			ResourceManager.maresleg.renderPart("Stock");
			ResourceManager.maresleg.renderPart("Barrel");
		}

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
		GL11.glTranslated(0, 1, shortened ? 3.75 : 8);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glRotated(90 * gun.shotRand, 1, 0, 0);
		this.renderMuzzleFlash(gun.lastShot[0], 75, 5);
		GL11.glPopMatrix();
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		double scale = 1.75D;
		GL11.glScaled(scale, scale, scale);
		GL11.glTranslated(0, 0.25, 3);

	}

	@Override
	public void setupInv(ItemStack stack) {
		super.setupInv(stack);
		
		if(getShort(stack)) {
			double scale = 2.5D;
			GL11.glScaled(scale, scale, scale);
			GL11.glRotated(25, 1, 0, 0);
			GL11.glRotated(45, 0, 1, 0);
			GL11.glTranslated(-1, 0, 0);
		} else {
			double scale = 1.4375D;
			GL11.glScaled(scale, scale, scale);
			GL11.glRotated(25, 1, 0, 0);
			GL11.glRotated(45, 0, 1, 0);
			GL11.glTranslated(-0.5, 0.5, 0);
		}
	}

	@Override
	public void setupModTable(ItemStack stack) {
		double scale = -8.75D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(90, 0, 1, 0);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {
		GL11.glEnable(GL11.GL_LIGHTING);
		
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		ResourceManager.maresleg.renderPart("Gun");
		ResourceManager.maresleg.renderPart("Lever");
		if(!getShort(stack)) {
			ResourceManager.maresleg.renderPart("Stock");
			ResourceManager.maresleg.renderPart("Barrel");
		}
		GL11.glShadeModel(GL11.GL_FLAT);
	}
	
	public boolean getShort(ItemStack stack) {
		return stack.getItem() == ModItems.gun_maresleg_broken;
	}
}

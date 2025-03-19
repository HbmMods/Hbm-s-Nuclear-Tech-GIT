package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemRenderDoubleBarrel extends ItemRenderWeaponBase {
	
	protected ResourceLocation texture;
	
	public ItemRenderDoubleBarrel(ResourceLocation texture) {
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
				0, -2 / 8D, 1);
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {
		
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		double scale = 0.375D;
		GL11.glScaled(scale, scale, scale);

		double[] equip = HbmAnimations.getRelevantTransformation("EQUIP");
		double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
		double[] turn = HbmAnimations.getRelevantTransformation("TURN");
		double[] barrel = HbmAnimations.getRelevantTransformation("BARREL");
		double[] lift = HbmAnimations.getRelevantTransformation("LIFT");
		double[] shells = HbmAnimations.getRelevantTransformation("SHELLS");
		double[] shellFlip = HbmAnimations.getRelevantTransformation("SHELL_FLIP");
		double[] lever = HbmAnimations.getRelevantTransformation("LEVER");
		double[] buckle = HbmAnimations.getRelevantTransformation("BUCKLE");
		double[] no_ammo = HbmAnimations.getRelevantTransformation("NO_AMMO");

		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		GL11.glTranslated(recoil[0] * 3, recoil[1], recoil[2]);
		GL11.glRotated(recoil[2] * 10, 1, 0, 0);

		GL11.glTranslated(0, 0, -4);
		GL11.glRotated(equip[0], -1, 0, 0);
		GL11.glTranslated(0, 0, 4);

		GL11.glTranslated(0, 0, -4);
		GL11.glRotated(turn[1], 0, 1, 0);
		GL11.glTranslated(0, 0, 4);

		GL11.glTranslated(0, 0, -4);
		GL11.glRotated(lift[0], -1, 0, 0);
		GL11.glTranslated(0, 0, 4);

		ResourceManager.double_barrel.renderPart("Stock");
		
		GL11.glPushMatrix();
		
		GL11.glTranslated(0, -0.4375, -0.875);
		GL11.glRotated(barrel[0], 1, 0, 0);
		GL11.glTranslated(0, 0.4375, 0.875);
		
		ResourceManager.double_barrel.renderPart("BarrelShort");
		if(!isSawedOff(stack)) ResourceManager.double_barrel.renderPart("Barrel");

		GL11.glPushMatrix();
		GL11.glTranslated(0.75, 0, -0.6875);
		GL11.glRotated(buckle[1], 0, 1, 0);
		GL11.glTranslated(-0.75, 0, 0.6875);
		ResourceManager.double_barrel.renderPart("Buckle");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(-0.3125, 0.3125, 0);
		GL11.glRotated(lever[2], 0, 0, 1);
		GL11.glTranslated(0.3125, -0.3125, 0);
		ResourceManager.double_barrel.renderPart("Lever");
		GL11.glPopMatrix();

		if(no_ammo[0] == 0) {
			GL11.glPushMatrix();
			GL11.glTranslated(shells[0], shells[1], shells[2]);
			GL11.glTranslated(0, 0, -1);
			GL11.glRotated(shellFlip[0], 1, 0, 0);
			GL11.glTranslated(0, 0, 1);
			ResourceManager.double_barrel.renderPart("Shells");
			GL11.glPopMatrix();
		}
		
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, 8);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glRotated(90 * gun.shotRand, 1, 0, 0);
		GL11.glScaled(2, 2, 2);
		this.renderMuzzleFlash(gun.lastShot[0], 75, 5);
		GL11.glPopMatrix();
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		double scale = 1.75D;
		GL11.glScaled(scale, scale, scale);
		GL11.glTranslated(0, 1, 3);

	}

	@Override
	public void setupInv(ItemStack stack) {
		super.setupInv(stack);
		if(isSawedOff(stack)) {
			double scale = 2D;
			GL11.glScaled(scale, scale, scale);
			GL11.glRotated(25, 1, 0, 0);
			GL11.glRotated(45, 0, 1, 0);
			GL11.glTranslated(-2, 0.5, 0);
		} else {
			double scale = 1.375D;
			GL11.glScaled(scale, scale, scale);
			GL11.glRotated(25, 1, 0, 0);
			GL11.glRotated(45, 0, 1, 0);
			GL11.glTranslated(0, 0.5, 0);
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
		ResourceManager.double_barrel.renderPart("Stock");
		ResourceManager.double_barrel.renderPart("BarrelShort");
		if(!isSawedOff(stack)) ResourceManager.double_barrel.renderPart("Barrel");
		ResourceManager.double_barrel.renderPart("Buckle");
		ResourceManager.double_barrel.renderPart("Lever");
		ResourceManager.double_barrel.renderPart("Shells");
		GL11.glShadeModel(GL11.GL_FLAT);
	}
	
	public boolean isSawedOff(ItemStack stack) {
		return stack.getItem() == ModItems.gun_double_barrel_sacred_dragon;
	}
}

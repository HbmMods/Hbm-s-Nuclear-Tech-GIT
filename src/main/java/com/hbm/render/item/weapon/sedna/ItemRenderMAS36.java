package com.hbm.render.item.weapon.sedna;

import java.nio.DoubleBuffer;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.mods.WeaponModManager;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.item.ItemStack;

public class ItemRenderMAS36 extends ItemRenderWeaponBase {

	@Override
	protected float getTurnMagnitude(ItemStack stack) { return ItemGunBaseNT.getIsAiming(stack) ? 2.5F : -0.5F; }

	@Override
	public float getViewFOV(ItemStack stack, float fov) {
		float aimingProgress = ItemGunBaseNT.prevAimingProgress + (ItemGunBaseNT.aimingProgress - ItemGunBaseNT.prevAimingProgress) * interp;
		return  fov * (1 - aimingProgress * (isScoped(stack) ? 0.66F : 0.33F));
	}

	@Override
	public void setupFirstPerson(ItemStack stack) {
		GL11.glTranslated(0, 0, 0.875);
		
		float offset = 0.8F;
		
		if(isScoped(stack)) {
			standardAimingTransform(stack,
				-1.5F * offset, -1.25F * offset, 1.75F * offset,
				-0.2, -5.875 / 8D, 1.125);
		} else {
			standardAimingTransform(stack,
					-1.5F * offset, -1.25F * offset, 1.75F * offset,
					0, -4.6825 / 8D, 0.75);
		}
	}
	
	private static DoubleBuffer buf = null;

	@Override
	public void renderFirstPerson(ItemStack stack) {
		boolean isScoped = isScoped(stack);
		if(isScoped && ItemGunBaseNT.prevAimingProgress == 1 && ItemGunBaseNT.aimingProgress == 1) return;
		if(buf == null) buf = GLAllocation.createDirectByteBuffer(8*4).asDoubleBuffer();
		
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.mas36_tex);
		double scale = 0.375D;
		GL11.glScaled(scale, scale, scale);

		double[] equip = HbmAnimations.getRelevantTransformation("EQUIP");
		double[] lift = HbmAnimations.getRelevantTransformation("LIFT");
		double[] stock = HbmAnimations.getRelevantTransformation("STOCK");
		double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
		double[] boltTurn = HbmAnimations.getRelevantTransformation("BOLT_TURN");
		double[] boltPull = HbmAnimations.getRelevantTransformation("BOLT_PULL");
		double[] bullet = HbmAnimations.getRelevantTransformation("BULLET");
		double[] showClip = HbmAnimations.getRelevantTransformation("SHOW_CLIP");
		double[] clip = HbmAnimations.getRelevantTransformation("CLIP");
		double[] bullets = HbmAnimations.getRelevantTransformation("BULLETS");
		double[] stab = HbmAnimations.getRelevantTransformation("STAB");
		
		GL11.glTranslated(0, -3, -3);
		GL11.glRotated(equip[0], 1, 0, 0);
		GL11.glRotated(lift[0], 1, 0, 0);
		GL11.glTranslated(0, 3, 3);

		GL11.glTranslated(stab[0], stab[1], stab[2]);
		
		GL11.glTranslated(0, 0, recoil[2]);

		GL11.glShadeModel(GL11.GL_SMOOTH);
		ResourceManager.mas36.renderPart("Gun");
		if(hasBayonet(stack))  ResourceManager.mas36.renderPart("Bayonet");
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0.3125, -2.125);
		GL11.glRotated(stock[0], 1, 0, 0);
		GL11.glTranslated(0, -0.3125, 2.125);
		ResourceManager.mas36.renderPart("Stock");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(0, 0.0625 * 18.5, 0);
		GL11.glRotated(boltTurn[2], 0, 0, 1);
		GL11.glTranslated(0, 0.0625 * -18.5, 0);
		GL11.glTranslated(0, 0, boltPull[2]);
		ResourceManager.mas36.renderPart("Bolt");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(bullet[0], bullet[1], bullet[2]);
		ResourceManager.mas36.renderPart("Bullet");
		GL11.glPopMatrix();
		
		if(isScoped) ResourceManager.mas36.renderPart("Scope");

		if(showClip[0] != 0) {
			GL11.glPushMatrix();
			GL11.glTranslated(clip[0], clip[1], clip[2]);
			ResourceManager.mas36.renderPart("Clip");
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			if(bullets[0] == 0) GL11.glEnable(GL11.GL_CLIP_PLANE0);
			buf.put(new double[] { 0, 1, 0, -0.5} );
			buf.rewind();
			GL11.glClipPlane(GL11.GL_CLIP_PLANE0, buf);
			GL11.glTranslated(bullets[0], bullets[1], bullets[2]);
			ResourceManager.mas36.renderPart("Bullets");
			GL11.glDisable(GL11.GL_CLIP_PLANE0);
			GL11.glPopMatrix();
		}

		double smokeScale = 0.25;
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 1.125, 8);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glScaled(smokeScale, smokeScale, smokeScale);
		this.renderSmokeNodes(gun.getConfig(stack, 0).smokeNodes, 1D);
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPushMatrix();
		GL11.glTranslated(0, 1, 8);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glRotated(90 * gun.shotRand, 1, 0, 0);
		GL11.glScaled(0.5, 0.5, 0.5);
		this.renderMuzzleFlash(gun.lastShot[0], 75, 7.5);
		GL11.glPopMatrix();
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		double scale = 1.5D;
		GL11.glScaled(scale, scale, scale);
		GL11.glTranslated(0, 0.5, 3);
	}

	@Override
	public void setupInv(ItemStack stack) {
		super.setupInv(stack);
		double scale = 1.5D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(45, 0, 1, 0);
		GL11.glTranslated(-0.5, 0.5, 0);
	}

	@Override
	public void setupModTable(ItemStack stack) {
		double scale = -7.5D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glTranslated(0, -0.25, -2.5);
	}

	@Override
	public void renderModTable(ItemStack stack, int index) {
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.mas36_tex);
		ResourceManager.mas36.renderPart("Gun");
		ResourceManager.mas36.renderPart("Stock");
		ResourceManager.mas36.renderPart("Bolt");
		if(isScoped(stack)) ResourceManager.mas36.renderPart("Scope");
		if(hasBayonet(stack)) ResourceManager.mas36.renderPart("Bayonet");
		GL11.glShadeModel(GL11.GL_FLAT);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.mas36_tex);
		ResourceManager.mas36.renderPart("Gun");
		ResourceManager.mas36.renderPart("Stock");
		ResourceManager.mas36.renderPart("Bolt");
		if(isScoped(stack)) ResourceManager.mas36.renderPart("Scope");
		GL11.glTranslated(0, -1, -6);
		if(hasBayonet(stack)) ResourceManager.mas36.renderPart("Bayonet");
		GL11.glShadeModel(GL11.GL_FLAT);
	}
	
	public boolean isScoped(ItemStack stack) {
		return WeaponModManager.hasUpgrade(stack, 0, WeaponModManager.ID_SCOPE);
	}
	
	public boolean hasBayonet(ItemStack stack) {
		return WeaponModManager.hasUpgrade(stack, 0, WeaponModManager.ID_MAS_BAYONET);
	}
}

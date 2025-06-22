package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;
import com.hbm.util.BobMathUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ItemRenderSexy extends ItemRenderWeaponBase {

	@Override
	protected float getTurnMagnitude(ItemStack stack) { return ItemGunBaseNT.getIsAiming(stack) ? 2.5F : -0.25F; }

	@Override
	public float getViewFOV(ItemStack stack, float fov) {
		float aimingProgress = ItemGunBaseNT.prevAimingProgress + (ItemGunBaseNT.aimingProgress - ItemGunBaseNT.prevAimingProgress) * interp;
		return  fov * (1 - aimingProgress * 0.66F);
	}

	@Override
	public void setupFirstPerson(ItemStack stack) {
		GL11.glTranslated(0, 0, 0.875);
		
		float offset = 0.8F;

		/*standardAimingTransform(stack,
				-1.25F * offset, -0.75F * offset, 3.25F * offset,
			0, -5.25 / 8D, 1);*/
		
		standardAimingTransform(stack,
				-1F * offset, -0.75F * offset, 3F * offset,
				-0.5F, -0.5F, 2F);
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.sexy_tex);
		double scale = 0.375D;
		GL11.glScaled(scale, scale, scale);

		double[] equip = HbmAnimations.getRelevantTransformation("EQUIP");
		double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
		
		GL11.glTranslated(0, -1, -8);
		GL11.glRotated(equip[0], 1, 0, 0);
		GL11.glTranslated(0, 1, 8);
		
		GL11.glTranslated(0, 0, recoil[2]);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		ResourceManager.sexy.renderPart("Gun");
		
		GL11.glPushMatrix();
		//GL11.glTranslated(0, 0, -1);
		ResourceManager.sexy.renderPart("Barrel");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, 0.375);
		//GL11.glScaled(1, 1, 0.75);
		GL11.glTranslated(0, 0, -0.375);
		ResourceManager.sexy.renderPart("RecoilSpring");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0.4375, -2.875);
		//GL11.glRotated(60, 1, 0, 0);
		GL11.glTranslated(0, -0.4375, 2.875);
		ResourceManager.sexy.renderPart("Hood");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0.46875, -6.875);
		//GL11.glRotated(60, 1, 0, 0);
		GL11.glTranslated(0, -0.46875, 6.875);
		ResourceManager.sexy.renderPart("Lever");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, -6.75);
		//GL11.glScaled(1, 1, 0.75);
		GL11.glTranslated(0, 0, 6.75);
		ResourceManager.sexy.renderPart("LockSpring");
		GL11.glPopMatrix();
		
		ResourceManager.sexy.renderPart("Magazine");

		/*renderShell(0, -0.375, 90, false);
		renderShell(0.3125, -0.0625, 30, false);
		renderShell(0.75, -0.125, -30, false);
		renderShell(1.0625, -0.4375, -60, false);
		renderShell(1.0625, -0.875, -90, false);
		renderShell(1.0625, -1.3125, -90, false);*/
		
		double p = 0.0625D;
		
		renderShell(p *  0, p *  -6,  90, true);
		renderShell(p *  5, p *   1,  30, true);
		renderShell(p * 12, p *  -1, -30, true);
		renderShell(p * 17, p *  -6, -60, true);
		renderShell(p * 17, p * -13, -90, true);
		renderShell(p * 17, p * -20, -90, true);
		
		GL11.glShadeModel(GL11.GL_FLAT);
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		double scale = 1.25D;
		GL11.glScaled(scale, scale, scale);
		GL11.glTranslated(0, 0, 4);

	}

	@Override
	public void setupInv(ItemStack stack) {
		super.setupInv(stack);
		double scale = 1.375D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(45, 0, 1, 0);
		GL11.glTranslated(0, 0.5, 0.25);
	}

	@Override
	public void setupModTable(ItemStack stack) {
		double scale = -9.5D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glTranslated(0, 0, 0);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.sexy_tex);
		ResourceManager.sexy.renderPart("Gun");
		ResourceManager.sexy.renderPart("Barrel");
		ResourceManager.sexy.renderPart("RecoilSpring");
		ResourceManager.sexy.renderPart("Hood");
		ResourceManager.sexy.renderPart("Lever");
		ResourceManager.sexy.renderPart("LockSpring");
		ResourceManager.sexy.renderPart("Magazine");
		
		double p = 0.0625D;
		renderShell(p *  0, p *  -6,  90, true);
		renderShell(p *  5, p *   1,  30, true);
		renderShell(p * 12, p *  -1, -30, true);
		renderShell(p * 17, p *  -6, -60, true);
		renderShell(p * 17, p * -13, -90, true);
		renderShell(p * 17, p * -20, -90, true);
		
		GL11.glShadeModel(GL11.GL_FLAT);
	}
	
	public static void renderShell(double x0, double x1, double y0, double y1, double rot0, double rot1, boolean shell, double interp) {
		renderShell(BobMathUtil.interp(x0, x1, interp), BobMathUtil.interp(y0, y1, interp), BobMathUtil.interp(rot0, rot1, interp), shell);
	}
	
	public static void renderShell(double x, double y, double rot, boolean shell) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, 0.375 + y, 0);
		GL11.glRotated(rot, 0, 0, 1);
		GL11.glTranslated(0, -0.375, 0);
		ResourceManager.sexy.renderPart("Belt");
		if(shell) ResourceManager.sexy.renderPart("Shell");
		GL11.glPopMatrix();
	}
}

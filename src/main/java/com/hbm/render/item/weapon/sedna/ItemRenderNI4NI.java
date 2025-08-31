package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.impl.ItemGunNI4NI;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;
import com.hbm.render.tileentity.RenderArcFurnace;
import com.hbm.util.ColorUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ItemRenderNI4NI extends ItemRenderWeaponBase {

	@Override
	protected float getTurnMagnitude(ItemStack stack) { return ItemGunBaseNT.getIsAiming(stack) ? 2.5F : -0.25F; }

	@Override
	public float getViewFOV(ItemStack stack, float fov) {
		float aimingProgress = ItemGunBaseNT.prevAimingProgress + (ItemGunBaseNT.aimingProgress - ItemGunBaseNT.prevAimingProgress) * interp;
		return  fov * (1 - aimingProgress * 0.33F);
	}

	@Override
	public void setupFirstPerson(ItemStack stack) {
		GL11.glTranslated(0, 0, 1);
		
		float offset = 0.8F;
		standardAimingTransform(stack,
				-1.0F * offset, -1F * offset, 1F * offset,
				0, -5 / 8D, 0.125);
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {

		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		
		int[] color = ItemGunNI4NI.getColors(stack);
		int dark = 0xffffff;
		int light = 0xffffff;
		int grip = 0xffffff;
		if(color != null) {
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.n_i_4_n_i_greyscale_tex);
			dark = color[0];
			light = color[1];
			grip = color[2];
		} else {
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.n_i_4_n_i_tex);
		}
		
		double scale = 0.3125D;
		GL11.glScaled(scale, scale, scale);

		double[] equip = HbmAnimations.getRelevantTransformation("EQUIP");
		double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
		double[] drum = HbmAnimations.getRelevantTransformation("DRUM");
		
		GL11.glTranslated(0, 0, -2.25);
		GL11.glRotated(equip[0], 1, 0, 0);
		GL11.glTranslated(0, 0, 2.25);
		
		GL11.glTranslated(0, -1, -6);
		GL11.glRotated(recoil[0], 1, 0, 0);
		GL11.glTranslated(0, 1, 6);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		GL11.glPushMatrix();

		GL11.glColor3f(ColorUtil.fr(dark), ColorUtil.fg(dark), ColorUtil.fb(dark));
		ResourceManager.n_i_4_n_i.renderPart("FrameDark");
		
		GL11.glColor3f(ColorUtil.fr(grip), ColorUtil.fg(grip), ColorUtil.fb(grip));
		ResourceManager.n_i_4_n_i.renderPart("Grip");
		
		GL11.glColor3f(ColorUtil.fr(light), ColorUtil.fg(light), ColorUtil.fb(light));
		ResourceManager.n_i_4_n_i.renderPart("FrameLight");
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 1.1875D, 0);
		GL11.glRotated(drum[2], 0, 0, 1);
		GL11.glTranslated(0, -1.1875D, 0);
		ResourceManager.n_i_4_n_i.renderPart("Cylinder");
		RenderArcFurnace.fullbright(true);
		GL11.glColor3f(1F, 1F, 1F);
		ResourceManager.n_i_4_n_i.renderPart("CylinderHighlights");
		RenderArcFurnace.fullbright(false);
		GL11.glPopMatrix();
		
		RenderArcFurnace.fullbright(true);
		ResourceManager.n_i_4_n_i.renderPart("Barrel");
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor3f(0F, 1F, 0F);
		int coinCount = ItemGunNI4NI.getCoinCount(stack);
		if(coinCount > 3) ResourceManager.n_i_4_n_i.renderPart("Coin1");
		if(coinCount > 2) ResourceManager.n_i_4_n_i.renderPart("Coin2");
		if(coinCount > 1) ResourceManager.n_i_4_n_i.renderPart("Coin3");
		if(coinCount > 0) ResourceManager.n_i_4_n_i.renderPart("Coin4");
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		RenderArcFurnace.fullbright(false);
		
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPushMatrix();
		GL11.glTranslated(0, 0.75, 4);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glRotated(90 * gun.shotRand, 1, 0, 0);
		GL11.glScaled(0.125, 0.125, 0.125);
		this.renderLaserFlash(gun.lastShot[0], 75, 7.5, 0xFFFFFF);
		GL11.glPopMatrix();
		
		GL11.glPopMatrix();
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		GL11.glTranslated(0, 0.25, 3);
		double scale = 1.5D;
		GL11.glScaled(scale, scale, scale);
	}

	@Override
	public void setupInv(ItemStack stack) {
		super.setupInv(stack);
		double scale = 2.5D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(45, 0, 1, 0);
		GL11.glTranslated(0, 0, 0);
	}

	@Override
	public void setupModTable(ItemStack stack) {
		double scale = -15D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glTranslated(0, -0.5, 0);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0F);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		
		int[] color = ItemGunNI4NI.getColors(stack);
		int dark = 0xffffff;
		int light = 0xffffff;
		int grip = 0xffffff;
		if(color != null) {
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.n_i_4_n_i_greyscale_tex);
			dark = color[0];
			light = color[1];
			grip = color[2];
		} else {
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.n_i_4_n_i_tex);
		}
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glColor3f(ColorUtil.fr(light), ColorUtil.fg(light), ColorUtil.fb(light));
		ResourceManager.n_i_4_n_i.renderPart("FrameLight");
		ResourceManager.n_i_4_n_i.renderPart("Cylinder");
		GL11.glColor3f(ColorUtil.fr(grip), ColorUtil.fg(grip), ColorUtil.fb(grip));
		ResourceManager.n_i_4_n_i.renderPart("Grip");
		GL11.glColor3f(ColorUtil.fr(dark), ColorUtil.fg(dark), ColorUtil.fb(dark));
		ResourceManager.n_i_4_n_i.renderPart("FrameDark");
		GL11.glColor3f(1F, 1F, 1F);
		RenderArcFurnace.fullbright(true);
		ResourceManager.n_i_4_n_i.renderPart("CylinderHighlights");
		ResourceManager.n_i_4_n_i.renderPart("Barrel");
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor3f(0F, 1F, 0F);
		ResourceManager.n_i_4_n_i.renderPart("Coin1");
		ResourceManager.n_i_4_n_i.renderPart("Coin2");
		ResourceManager.n_i_4_n_i.renderPart("Coin3");
		ResourceManager.n_i_4_n_i.renderPart("Coin4");
		GL11.glColor3f(1F, 1F, 1F);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		RenderArcFurnace.fullbright(false);
		GL11.glShadeModel(GL11.GL_FLAT);
	}
}

package com.hbm.render.item.weapon.sedna;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.mags.IMagazine;
import com.hbm.main.MainRegistry;
import com.hbm.main.ResourceManager;
import com.hbm.particle.SpentCasing;
import com.hbm.render.anim.HbmAnimations;
import com.hbm.render.anim.HbmAnimations.AnimType;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ItemRenderCongoLake extends ItemRenderWeaponBase {

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
				-1.5F * offset, -2F * offset, 1.25F * offset,
				0, -10 / 8D, 0.25);
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {
		
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.congolake_tex);
		double scale = 0.5D;
		GL11.glScaled(scale, scale, scale);
		
		HbmAnimations.applyRelevantTransformation("Gun");
		ResourceManager.congolake.renderPart("Gun");


		GL11.glPushMatrix();
		{
			HbmAnimations.applyRelevantTransformation("Pump");
			ResourceManager.congolake.renderPart("Pump");
		}
		GL11.glPopMatrix();


		GL11.glPushMatrix();
		{
			float aimingProgress = ItemGunBaseNT.prevAimingProgress + (ItemGunBaseNT.aimingProgress - ItemGunBaseNT.prevAimingProgress) * interp;
			HbmAnimations.applyRelevantTransformation("Sight");
			GL11.glTranslated(0, 2.125, 3);
			GL11.glRotated(aimingProgress * -90, 1, 0, 0);
			GL11.glTranslated(0, -2.125, -3);
			ResourceManager.congolake.renderPart("Sight");
		}
		GL11.glPopMatrix();


		GL11.glPushMatrix();
		{
			HbmAnimations.applyRelevantTransformation("Loop");
			ResourceManager.congolake.renderPart("Loop");
		}
		GL11.glPopMatrix();


		GL11.glPushMatrix();
		{
			HbmAnimations.applyRelevantTransformation("GuardOuter");
			ResourceManager.congolake.renderPart("GuardOuter");

			GL11.glPushMatrix();
			{
				HbmAnimations.applyRelevantTransformation("GuardInner");
				ResourceManager.congolake.renderPart("GuardInner");
			}
			GL11.glPopMatrix();
		}
		GL11.glPopMatrix();


		GL11.glPushMatrix();
		{
			IMagazine mag = gun.getConfig(stack, 0).getReceivers(stack)[0].getMagazine(stack);
			if(gun.getLastAnim(stack, 0) != AnimType.INSPECT || mag.getAmount(stack, MainRegistry.proxy.me().inventory) > 0) { //omit when inspecting and no shell is loaded
				
				Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.casings_tex);
	
				HbmAnimations.applyRelevantTransformation("Shell");
	
				SpentCasing casing = mag.getCasing(stack, MainRegistry.proxy.me().inventory);
				int[] colors = casing != null ? casing.getColors() : new int[] { SpentCasing.COLOR_CASE_40MM };
	
				Color shellColor = new Color(colors[0]);
				GL11.glColor3f(shellColor.getRed() / 255F, shellColor.getGreen() / 255F, shellColor.getBlue() / 255F);
				ResourceManager.congolake.renderPart("Shell");
				
				Color shellForeColor = new Color(colors.length > 1 ? colors[1] : colors[0]);
				GL11.glColor3f(shellForeColor.getRed() / 255F, shellForeColor.getGreen() / 255F, shellForeColor.getBlue() / 255F);
				ResourceManager.congolake.renderPart("ShellFore");
	
				GL11.glColor3f(1F, 1F, 1F);
			}
		}
		GL11.glPopMatrix();

		double smokeScale = 0.25;
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glPushMatrix();
		GL11.glTranslated(0, 1.75, 4.25);
		double[] transform = HbmAnimations.getRelevantTransformation("Gun");
		GL11.glRotated(-transform[5], 0, 0, 1);
		GL11.glRotated(-transform[4], 0, 1, 0);
		GL11.glRotated(-transform[3], 1, 0, 0);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glScaled(smokeScale, smokeScale, smokeScale);
		this.renderSmokeNodes(gun.getConfig(stack, 0).smokeNodes, 1D);
		GL11.glPopMatrix();
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPushMatrix();
		GL11.glTranslated(0, 1.75, 4.25);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glRotated(90 * gun.shotRand, 1, 0, 0);
		GL11.glScaled(0.5, 0.5, 0.5);
		this.renderMuzzleFlash(gun.lastShot[0], 150, 7.5);
		GL11.glPopMatrix();
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		GL11.glTranslated(0, -2.5, 4);
		double scale = 2.5D;
		GL11.glScaled(scale, scale, scale);

	}

	@Override
	public void setupInv(ItemStack stack) {
		super.setupInv(stack);
		double scale = 2.5D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(45, 0, 1, 0);
		GL11.glTranslated(0, -1.25, 0);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.congolake_tex);
		ResourceManager.congolake.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);
	}
}

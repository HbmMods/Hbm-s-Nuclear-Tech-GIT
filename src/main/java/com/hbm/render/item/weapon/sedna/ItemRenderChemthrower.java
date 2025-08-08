package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.mags.IMagazine;
import com.hbm.main.MainRegistry;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ItemRenderChemthrower extends ItemRenderWeaponBase {

	@Override
	protected float getTurnMagnitude(ItemStack stack) { return ItemGunBaseNT.getIsAiming(stack) ? 2.5F : -0.25F; }

	@Override
	public void setupFirstPerson(ItemStack stack) {
		GL11.glTranslated(0, 0, 0.875);
		
		float offset = 0.8F;
		standardAimingTransform(stack,
				-2.5F * offset, -2.5F * offset, 2.5F * offset,
				0, -4.375 / 8D, 1);
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {
		
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.chemthrower_tex);
		double scale = 0.75D;
		GL11.glScaled(scale, scale, scale);

		double[] equip = HbmAnimations.getRelevantTransformation("EQUIP");
		
		GL11.glTranslated(0, -2, -4);
		GL11.glRotated(equip[0], -1, 0, 0);
		GL11.glTranslated(0, 2, 4);

		GL11.glShadeModel(GL11.GL_SMOOTH);

		GL11.glRotated(90, 0, 1, 0);
		ResourceManager.chemthrower.renderPart("Gun");
		ResourceManager.chemthrower.renderPart("Hose");
		ResourceManager.chemthrower.renderPart("Nozzle");
		
		GL11.glTranslated(0, 0.875, 1.75);
		IMagazine mag = gun.getConfig(stack, 0).getReceivers(stack)[0].getMagazine(stack);
		double d = (double) mag.getAmount(stack, MainRegistry.proxy.me().inventory) / (double) mag.getCapacity(stack);
		GL11.glRotated(135 - d * 270, 1, 0, 0);
		GL11.glTranslated(0, -0.875, -1.75);
		
		ResourceManager.chemthrower.renderPart("Gauge");
		
		GL11.glShadeModel(GL11.GL_FLAT);
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		double scale = 2D;
		GL11.glScaled(scale, scale, scale);
		GL11.glTranslated(0, -2.5, 0.5);

	}

	@Override
	public void setupInv(ItemStack stack) {
		super.setupInv(stack);
		double scale = 2D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(45, 0, 1, 0);
		GL11.glTranslated(0.875, 0, 0);
	}

	@Override
	public void setupModTable(ItemStack stack) {
		double scale = -10D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glTranslated(0, -0.5, -0.5);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {
		GL11.glEnable(GL11.GL_LIGHTING);

		GL11.glRotated(90, 0, 1, 0);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.chemthrower_tex);
		ResourceManager.chemthrower.renderPart("Gun");
		ResourceManager.chemthrower.renderPart("Hose");
		ResourceManager.chemthrower.renderPart("Nozzle");
		ResourceManager.chemthrower.renderPart("Gauge");
		GL11.glShadeModel(GL11.GL_FLAT);
	}
}

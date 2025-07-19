package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.mods.WeaponModManager;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ItemRenderMinigunDual extends ItemRenderWeaponBase {
	
	@Override public boolean isAkimbo() { return true; }

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
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {
		
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		
		float offset = 0.8F;
		
		for(int i = -1; i <= 1; i += 2) {
			int index = i == -1 ? 0 : 1;
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.minigun_dual_tex);

			GL11.glPushMatrix();
			standardAimingTransform(stack, -2.75F * offset * i, -1.75F * offset, 2.5F * offset, 0, 0, 0);
			
			double scale = 0.375D;
			GL11.glScaled(scale, scale, scale);

			double[] equip = HbmAnimations.getRelevantTransformation("EQUIP", index);
			double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL", index);
			double[] rotate = HbmAnimations.getRelevantTransformation("ROTATE", index);
			
			GL11.glTranslated(0, 3, -6);
			GL11.glRotated(equip[0], 1, 0, 0);
			GL11.glTranslated(0, -3, 6);
			
			GL11.glTranslated(0, 0, recoil[2]);
			
			GL11.glShadeModel(GL11.GL_SMOOTH);

			ResourceManager.minigun.renderPart(index == 0 ? "GunDual" : "Gun");
			
			GL11.glPushMatrix();
			GL11.glRotated(rotate[2] * i, 0, 0, 1);
			ResourceManager.minigun.renderPart("Barrels");
			GL11.glPopMatrix();

			GL11.glShadeModel(GL11.GL_FLAT);

			GL11.glPushMatrix();
			GL11.glTranslated(0, 0, 12);
			GL11.glRotated(90, 0, 1, 0);
			
			GL11.glRotated(gun.shotRand * 90, 1, 0, 0);
			GL11.glScaled(1.5, 1.5, 1.5);
			this.renderMuzzleFlash(gun.lastShot[index], 75, 5);
			GL11.glPopMatrix();
			
			GL11.glPopMatrix();
		}
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		double scale = 1.75D;
		GL11.glScaled(scale, scale, scale);
		GL11.glTranslated(-1, -3.5, 8);

	}

	@Override
	public void setupThirdPersonAkimbo(ItemStack stack) {
		super.setupThirdPerson(stack);
		double scale = 1.75D;
		GL11.glScaled(scale, scale, scale);
		GL11.glTranslated(2, -3.5, 8);
	}

	@Override
	public void setupInv(ItemStack stack) {
		GL11.glScaled(1, 1, -1);
		GL11.glTranslated(8, 8, 0);
		double scale = 0.875D;
		GL11.glScaled(scale, scale, scale);
	}

	@Override
	public void setupModTable(ItemStack stack) {
		double scale = -6.25D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(90, 0, 1, 0);
	}

	@Override
	public void renderEquipped(ItemStack stack) {

		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.minigun_dual_tex);
		ResourceManager.minigun.renderPart("Gun");
		ResourceManager.minigun.renderPart("Barrels");
		GL11.glShadeModel(GL11.GL_FLAT);
	}

	@Override
	public void renderEquippedAkimbo(ItemStack stack) {

		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.minigun_dual_tex);
		ResourceManager.minigun.renderPart("GunDual");
		ResourceManager.minigun.renderPart("Barrels");
		GL11.glShadeModel(GL11.GL_FLAT);
	}

	@Override
	public void renderModTable(ItemStack stack, int index) {
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.minigun_dual_tex);
		ResourceManager.minigun.renderPart(index == 0 ? "GunDual" : "Gun");
		ResourceManager.minigun.renderPart("Barrels");
		GL11.glShadeModel(GL11.GL_FLAT);
	}

	@Override
	public void renderInv(ItemStack stack) {
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.minigun_dual_tex);

		GL11.glPushMatrix();
		GL11.glRotated(225, 0, 0, 1);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glRotated(45, 0, 1, 0);
		ResourceManager.minigun.renderPart("GunDual");
		ResourceManager.minigun.renderPart("Barrels");
		GL11.glPopMatrix();

		GL11.glTranslated(0, 0, 8);
		GL11.glPushMatrix();
		GL11.glRotated(225, 0, 0, 1);
		GL11.glRotated(-90, 0, 1, 0);
		GL11.glRotated(-90, 1, 0, 0);
		GL11.glRotated(-45, 0, 1, 0);
		ResourceManager.minigun.renderPart("Gun");
		ResourceManager.minigun.renderPart("Barrels");
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.minigun_dual_tex);
		ResourceManager.minigun.renderPart("Gun");
		ResourceManager.minigun.renderPart("Barrels");
		GL11.glShadeModel(GL11.GL_FLAT);
	}
	
	public boolean hasSilencer(ItemStack stack, int cfg) {
		return WeaponModManager.hasUpgrade(stack, cfg, WeaponModManager.ID_SILENCER);
	}
	
	public boolean isSaturnite(ItemStack stack, int cfg) {
		return WeaponModManager.hasUpgrade(stack, cfg, WeaponModManager.ID_UZI_SATURN);
	}
}

package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.mods.XWeaponModManager;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class ItemRenderStarFAkimbo extends ItemRenderWeaponBase {
	
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
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.star_f_elite_tex);

			GL11.glPushMatrix();
			standardAimingTransform(stack, -2F * offset * i, -1.75F * offset, 2.5F * offset, 0, -7.625 / 8D, 1);

			double scale = 0.25D;
			GL11.glScaled(scale, scale, scale);

			double[] equip = HbmAnimations.getRelevantTransformation("EQUIP", index);
			double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL", index);
			double[] hammer = HbmAnimations.getRelevantTransformation("HAMMER", index);
			double[] tilt = HbmAnimations.getRelevantTransformation("TILT", index);
			double[] turn = HbmAnimations.getRelevantTransformation("TURN", index);
			double[] mag = HbmAnimations.getRelevantTransformation("MAG", index);
			double[] bullet = HbmAnimations.getRelevantTransformation("BULLET", index);
			double[] slide = HbmAnimations.getRelevantTransformation("SLIDE", index);

			GL11.glTranslated(0, -2, -8);
			GL11.glRotated(equip[0], 1, 0, 0);
			GL11.glTranslated(0, 2, 8);

			GL11.glTranslated(0, 1, -3);
			GL11.glRotated(turn[2] * i, 0, 0, 1);
			GL11.glRotated(tilt[0], 1, 0, 0);
			GL11.glTranslated(0, -1, 3);
			
			GL11.glTranslated(0, 0, recoil[2]);

			GL11.glShadeModel(GL11.GL_SMOOTH);
			ResourceManager.star_f.renderPart("Gun");

			GL11.glPushMatrix();
			GL11.glTranslated(0, 1.75, -4.25);
			GL11.glRotated(60 * (hammer[0] - 1), 1, 0, 0);
			GL11.glTranslated(0, -1.75, 4.25);
			ResourceManager.star_f.renderPart("Hammer");
			GL11.glPopMatrix();

			GL11.glPushMatrix();
			GL11.glTranslated(0, 0, slide[2] * 2.3125);
			ResourceManager.star_f.renderPart("Slide");
			GL11.glPopMatrix();
			
			GL11.glPushMatrix();
			GL11.glTranslated(mag[0], mag[1], mag[2]);
			ResourceManager.star_f.renderPart("Mag");
			GL11.glTranslated(bullet[0], bullet[1], bullet[2]);
			ResourceManager.star_f.renderPart("Bullet");
			GL11.glPopMatrix();
			
			if(hasSilencer(stack, index)) {
				GL11.glPushMatrix();
				GL11.glTranslated(0, 2.375, -0.25);
				Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.uzi_tex);
				ResourceManager.uzi.renderPart("Silencer");
				GL11.glPopMatrix();
				
			} else {
				double smokeScale = 0.5;
				
				GL11.glPushMatrix();
				GL11.glTranslated(0, 3, 6.125);
				GL11.glRotated(90, 0, 1, 0);
				GL11.glScaled(smokeScale, smokeScale, smokeScale);
				this.renderSmokeNodes(gun.getConfig(stack, index).smokeNodes, 0.75D);
				GL11.glPopMatrix();
				
				GL11.glShadeModel(GL11.GL_FLAT);
				
				renderMuzzleFlash(gun.shotRand, gun.lastShot[index]);
			}
			
			GL11.glPopMatrix();
		}
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		GL11.glTranslated(0, -0.25, 1.75);
		double scale = 0.75D;
		GL11.glScaled(scale, scale, scale);
	}

	@Override
	public void setupThirdPersonAkimbo(ItemStack stack) {
		super.setupThirdPersonAkimbo(stack);
		GL11.glTranslated(0, -0.25, 1.75);
		double scale = 0.75D;
		GL11.glScaled(scale, scale, scale);
	}

	@Override
	public void setupInv(ItemStack stack) {
		GL11.glAlphaFunc(GL11.GL_GREATER, 0F);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glScaled(1, 1, -1);
		GL11.glTranslated(8, 8, 0);
		double scale = 1.5D;
		GL11.glScaled(scale, scale, scale);
	}

	@Override
	public void setupModTable(ItemStack stack) {
		double scale = -6.25D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glTranslated(0, -0.25, -5);
	}

	@Override
	public void renderEquipped(ItemStack stack, Object... data) {
		renderStandardGun(stack, 1);
		if(!hasSilencer(stack, 1)) renderThirdPersonFlash((EntityLivingBase) data[1], stack, 1);
	}

	@Override
	public void renderEquippedAkimbo(ItemStack stack, EntityLivingBase ent) {
		renderStandardGun(stack, 0);
		if(!hasSilencer(stack, 0)) renderThirdPersonFlash(ent, stack, 0);
	}

	@Override
	public void renderModTable(ItemStack stack, int index) {
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.star_f_elite_tex);
		ResourceManager.star_f.renderPart("Gun");
		ResourceManager.star_f.renderPart("Slide");
		ResourceManager.star_f.renderPart("Mag");
		ResourceManager.star_f.renderPart("Hammer");
		if(hasSilencer(stack, index)) {
			GL11.glTranslated(0, 2.375, -0.25);
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.uzi_tex);
			ResourceManager.uzi.renderPart("Silencer");
		}
		GL11.glShadeModel(GL11.GL_FLAT);
	}
	
	@Override
	public void renderEntity(ItemStack stack) {
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glShadeModel(GL11.GL_SMOOTH);

		boolean anySilenced = hasSilencer(stack, 0) || hasSilencer(stack, 1);
		
		if(anySilenced) {
			GL11.glScaled(0.75, 0.75, 0.75);
		}
		
		GL11.glPushMatrix();
		GL11.glTranslated(-1, 1, 0);
		renderStandardGun(stack, 1);
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(1, 1, 0);
		renderStandardGun(stack, 0);
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type, Object... data) {
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glShadeModel(GL11.GL_SMOOTH);

		boolean anySilenced = hasSilencer(stack, 0) || hasSilencer(stack, 1);
		
		GL11.glPushMatrix();
		GL11.glRotated(225, 0, 0, 1);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(45, 0, 1, 0);
		GL11.glTranslated(0.5, 0, 0);
		if(anySilenced) {
			double scale = 0.625D;
			GL11.glScaled(scale, scale, scale);
			GL11.glTranslated(0, 0, -4);
		}
		renderStandardGun(stack, 1);
		GL11.glPopMatrix();
		
		GL11.glTranslated(0, 0, 5);
		
		GL11.glPushMatrix();
		GL11.glRotated(225, 0, 0, 1);
		GL11.glRotated(-90, 0, 1, 0);
		GL11.glRotated(-90, 1, 0, 0);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(-45, 0, 1, 0);
		GL11.glTranslated(-0.5, 0, 0);
		if(anySilenced) {
			double scale = 0.625D;
			GL11.glScaled(scale, scale, scale);
			GL11.glTranslated(0, 0, -4);
		}
		renderStandardGun(stack, 0);
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);
	}
	
	public boolean hasSilencer(ItemStack stack, int cfg) {
		return XWeaponModManager.hasUpgrade(stack, cfg, XWeaponModManager.ID_SILENCER);
	}
	
	public void renderStandardGun(ItemStack stack, int index) {

		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.star_f_elite_tex);
		ResourceManager.star_f.renderPart("Gun");
		ResourceManager.star_f.renderPart("Slide");
		ResourceManager.star_f.renderPart("Mag");
		ResourceManager.star_f.renderPart("Hammer");
		boolean silenced = hasSilencer(stack, index);
		if(silenced) {
			GL11.glTranslated(0, 2.375, -0.25);
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.uzi_tex);
			ResourceManager.uzi.renderPart("Silencer");
		}
		GL11.glShadeModel(GL11.GL_FLAT);
	}
	
	public void renderThirdPersonFlash(Entity ent, ItemStack stack, int config) {
		
		long shot;
		double shotRand = 0;
		if(ent == Minecraft.getMinecraft().thePlayer) {
			ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
			shot = gun.lastShot[config];
			shotRand = gun.shotRand;
		} else {
			shot = ItemRenderWeaponBase.flashMap.getOrDefault(ent, (long) -1);
			if(shot < 0) return;
		}
		
		renderMuzzleFlash(shotRand, shot);
	}
	
	public void renderMuzzleFlash(double shotRand, long shot) {
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 3, 6.125);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glRotated(90 * shotRand, 1, 0, 0);
		this.renderMuzzleFlash(shot, 75, 7.5);
		GL11.glPopMatrix();
	}
}

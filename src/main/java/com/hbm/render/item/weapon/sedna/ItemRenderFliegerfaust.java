package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class ItemRenderFliegerfaust extends ItemRenderWeaponBase {

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
				-2.75F * offset, -2F * offset, -1.5F * offset,
			-0.9375, -7.75 / 8D, -2.5);
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {
		
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.fliegerfaust_tex);
		float aimingProgress = ItemGunBaseNT.prevAimingProgress + (ItemGunBaseNT.aimingProgress - ItemGunBaseNT.prevAimingProgress) * interp;
		double scale = 1.25D;
		GL11.glScaled(scale, scale, scale);

		double[] equip = HbmAnimations.getRelevantTransformation("EQUIP");
		double[] reload = HbmAnimations.getRelevantTransformation("RELOAD");
		double[] drum = HbmAnimations.getRelevantTransformation("DRUM");
		double[] drumrot = HbmAnimations.getRelevantTransformation("DRUMROT");
		double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
		double[] unloading = HbmAnimations.getRelevantTransformation("UNLOADING");
		
		int rocketNum = unloading[0] > 0 ? gun.getConfig(stack, 0).getReceivers(stack)[0].getMagazine(stack).getAmount(stack, null) : 9;
		
		GL11.glRotated(aimingProgress * -45, 0, 0, 1);
		
		GL11.glTranslated(0, 0, 1);
		GL11.glRotated(equip[0], 1, 0, 0);
		GL11.glTranslated(0, 0, -1);
		
		GL11.glTranslated(0, -6, -1);
		GL11.glRotated(reload[0], 1, 0, 0);
		GL11.glTranslated(0, 6, 1);
		
		GL11.glTranslated(0, 0, recoil[2]);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);

		ResourceManager.fliegerfaust.renderPart("Launcher");
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, 2);
		GL11.glTranslated(drum[0], drum[1], drum[2]);
		GL11.glTranslated(0, 0, -8.5);
		GL11.glRotated(drumrot[0], 1, 0, 0);
		GL11.glTranslated(0, 0, 8.5);
		ResourceManager.fliegerfaust.renderPart("Drum");
		for(int i = 1; i <= rocketNum; i++) ResourceManager.fliegerfaust.renderPart("Rocket" + i);
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, 8);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glRotated(90 * gun.shotRand, 1, 0, 0);
		GL11.glScaled(0.75, 0.75, 0.75);
		this.renderMuzzleFlash(gun.lastShot[0], 75, 5);
		GL11.glPopMatrix();
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		double scale = 2D;
		GL11.glScaled(scale, scale, scale);
		GL11.glTranslated(0, 1, -2);

	}

	@Override
	public void setupInv(ItemStack stack) {
		super.setupInv(stack);
		double scale = 1.375D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(45, 0, 1, 0);
		GL11.glTranslated(0, 0.5, 0);
	}

	@Override
	public void setupModTable(ItemStack stack) {
		double scale = -8.5D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(90, 0, 1, 0);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type, Object... data) {
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.fliegerfaust_tex);

		ResourceManager.fliegerfaust.renderPart("Launcher");
		
		GL11.glTranslated(0, 0, 2);
		ResourceManager.fliegerfaust.renderPart("Drum");
		
		GL11.glShadeModel(GL11.GL_FLAT);
		
		if(type == ItemRenderType.EQUIPPED) {
			EntityLivingBase ent = (EntityLivingBase) data[1];
			long shot;
			double shotRand = 0;
			if(ent == Minecraft.getMinecraft().thePlayer) {
				ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
				shot = gun.lastShot[0];
				shotRand = gun.shotRand;
			} else {
				shot = ItemRenderWeaponBase.flashMap.getOrDefault(ent, (long) -1);
				if(shot < 0) return;
			}
			
			GL11.glPushMatrix();
			GL11.glTranslated(0, 0, 8);
			GL11.glRotated(90, 0, 1, 0);
			GL11.glRotated(90 * shotRand, 1, 0, 0);
			GL11.glScaled(0.75, 0.75, 0.75);
			this.renderMuzzleFlash(shot, 150, 7.5);
			GL11.glPopMatrix();
		}
	}
}

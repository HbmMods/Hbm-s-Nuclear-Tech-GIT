package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemRenderLaserPistol extends ItemRenderWeaponBase {
	
	public ResourceLocation texture;
	
	public ItemRenderLaserPistol(ResourceLocation texture) {
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
				-1.75F * offset, -2F * offset, 2.75F * offset,
				0, -10 / 8D, 1.25);
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {
		
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		double scale = 0.375D;
		GL11.glScaled(scale, scale, scale);
		
		double[] equip = HbmAnimations.getRelevantTransformation("EQUIP");
		double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
		double[] latch = HbmAnimations.getRelevantTransformation("LATCH");
		double[] lift = HbmAnimations.getRelevantTransformation("LIFT");
		double[] jolt = HbmAnimations.getRelevantTransformation("JOLT");
		double[] battery = HbmAnimations.getRelevantTransformation("BATTERY");
		double[] swirl = HbmAnimations.getRelevantTransformation("SWIRL");
		
		GL11.glTranslated(0, -1, -6);
		GL11.glRotated(equip[0], 1, 0, 0);
		GL11.glTranslated(0, 1, 6);
		
		GL11.glTranslated(0, 2, -2);
		GL11.glRotated(lift[0], 1, 0, 0);
		GL11.glTranslated(0, -2, 2);
		
		GL11.glTranslated(0, -1, -1);
		GL11.glRotated(swirl[0], 1, 0, 0);
		GL11.glTranslated(0, 1, 1);
		
		GL11.glTranslated(0, 0, recoil[2]);
		GL11.glTranslated(jolt[0], jolt[1], jolt[2]);

		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		ResourceManager.laser_pistol.renderPart("Gun");
		if(hasCapacitors(stack)) ResourceManager.laser_pistol.renderPart("Capacitors");
		if(hasTape(stack)) ResourceManager.laser_pistol.renderPart("Tape");
		
		GL11.glPushMatrix();
		GL11.glTranslated(1.125, 0, -1.9125);
		GL11.glRotated(latch[1], 0, 1, 0);
		GL11.glTranslated(-1.125, 0, 1.9125);
		ResourceManager.laser_pistol.renderPart("Latch");
		GL11.glTranslated(battery[0], battery[1], battery[2]);
		ResourceManager.laser_pistol.renderPart("Battery");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(0, 2, 4.75);
		GL11.glRotated(90, 0, 1, 0);
		renderLaserFlash(gun.lastShot[0], 150, 1.5D, hasEmerald(stack) ? 0x008000 : 0xff0000);
		GL11.glTranslated(0, 0, -0.25);
		renderLaserFlash(gun.lastShot[0], 150, 0.75D, hasEmerald(stack) ? 0x80ff00 : 0xff8000);
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		double scale = 1.25D;
		GL11.glScaled(scale, scale, scale);
		GL11.glTranslated(0, -0.5, 1);
	}

	@Override
	public void setupInv(ItemStack stack) {
		super.setupInv(stack);
		double scale = 1.75D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(45, 0, 1, 0);
		GL11.glTranslated(0, -0.5, 0);
	}

	@Override
	public void setupModTable(ItemStack stack) {
		double scale = -10D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glTranslated(0, -0.5, 0);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		ResourceManager.laser_pistol.renderPart("Gun");
		ResourceManager.laser_pistol.renderPart("Latch");
		if(hasCapacitors(stack)) ResourceManager.laser_pistol.renderPart("Capacitors");
		if(hasTape(stack)) ResourceManager.laser_pistol.renderPart("Tape");
		GL11.glShadeModel(GL11.GL_FLAT);
	}
	
	public boolean hasCapacitors(ItemStack stack) {
		return stack.getItem() == ModItems.gun_laser_pistol_pew_pew;
	}
	
	public boolean hasTape(ItemStack stack) {
		return stack.getItem() == ModItems.gun_laser_pistol_pew_pew;
	}
	
	public boolean hasEmerald(ItemStack stack) {
		return stack.getItem() == ModItems.gun_laser_pistol_morning_glory;
	}
}

package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.mods.WeaponModManager;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemRenderG3 extends ItemRenderWeaponBase {
	
	public ResourceLocation texture;
	
	public ItemRenderG3(ResourceLocation texture) {
		this.texture = texture;
	}

	@Override
	protected float getTurnMagnitude(ItemStack stack) { return ItemGunBaseNT.getIsAiming(stack) ? 2.5F : -0.25F; }

	@Override
	public float getViewFOV(ItemStack stack, float fov) {
		float aimingProgress = ItemGunBaseNT.prevAimingProgress + (ItemGunBaseNT.aimingProgress - ItemGunBaseNT.prevAimingProgress) * interp;
		return  fov * (1 - aimingProgress * (isScoped(stack) ? 0.66F : 0.33F));
	}

	@Override
	public void setupFirstPerson(ItemStack stack) {
		GL11.glTranslated(0, 0, 0.875);
		
		boolean isScoped = this.isScoped(stack);
		float offset = 0.8F;
		standardAimingTransform(stack,
				-1.25F * offset, -1F * offset, 2.75F * offset,
			0, isScoped ? (-5.53125 / 8D) : (-3.5625 / 8D), isScoped ? 1.46875 : 1.75);
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {
		
		boolean isScoped = this.isScoped(stack);
		if(isScoped && ItemGunBaseNT.prevAimingProgress == 1 && ItemGunBaseNT.aimingProgress == 1) return;
		
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		Minecraft.getMinecraft().renderEngine.bindTexture(getTexture(stack));
		double scale = 0.375D;
		GL11.glScaled(scale, scale, scale);

		double[] equip = HbmAnimations.getRelevantTransformation("EQUIP");
		double[] lift = HbmAnimations.getRelevantTransformation("LIFT");
		double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
		double[] mag = HbmAnimations.getRelevantTransformation("MAG");
		double[] speen = HbmAnimations.getRelevantTransformation("SPEEN");
		double[] bolt = HbmAnimations.getRelevantTransformation("BOLT");
		double[] plug = HbmAnimations.getRelevantTransformation("PLUG");
		double[] handle = HbmAnimations.getRelevantTransformation("HANDLE");
		double[] bullet = HbmAnimations.getRelevantTransformation("BULLET");
		
		GL11.glTranslated(0, -2, -6);
		GL11.glRotated(equip[0], 1, 0, 0);
		GL11.glTranslated(0, 2, 6);
		
		GL11.glTranslated(0, 0, -4);
		GL11.glRotated(lift[0], 1, 0, 0);
		GL11.glTranslated(0, 0, 4);
		
		GL11.glTranslated(0, 0, recoil[2]);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		ResourceManager.g3.renderPart("Rifle");
		if(hasStock(stack)) ResourceManager.g3.renderPart("Stock");
		boolean silenced = hasSilencer(stack);
		if(!silenced) ResourceManager.g3.renderPart("Flash_Hider");
		ResourceManager.g3.renderPart("Trigger");
		
		Minecraft.getMinecraft().renderEngine.bindTexture(getTexture(stack));
		GL11.glPushMatrix();
		GL11.glTranslated(mag[0], mag[1], mag[2]);
		GL11.glTranslated(0, -1.75, -0.5);
		GL11.glRotated(speen[2], 0, 0, 1);
		GL11.glRotated(speen[1], 0, 1, 0);
		GL11.glTranslated(0, 1.75, 0.5);
		ResourceManager.g3.renderPart("Magazine");
		if(bullet[0] == 0) ResourceManager.g3.renderPart("Bullet");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, bolt[2]);
		ResourceManager.g3.renderPart("Guide_And_Bolt");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0.625, plug[2]);
		GL11.glRotated(handle[2], 0, 0, 1);
		GL11.glTranslated(0, -0.625, 0);
		ResourceManager.g3.renderPart("Plug");
		
		GL11.glTranslated(0, 0.625, 5.25);
		GL11.glRotated(22.5, 0, 0, 1);
		GL11.glRotated(handle[1], 0, 1, 0);
		GL11.glRotated(-22.5, 0, 0, 1);
		GL11.glTranslated(0, -0.625, -5.25);
		ResourceManager.g3.renderPart("Handle");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, -0.875, -3.5);
		GL11.glRotated(-30 * (1 - ItemGunBaseNT.getMode(stack, 0)), 1, 0, 0);
		GL11.glTranslated(0, 0.875, 3.5);
		ResourceManager.g3.renderPart("Selector");
		GL11.glPopMatrix();
		
		if(silenced || isScoped) {
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.g3_attachments);
			if(silenced) ResourceManager.g3.renderPart("Silencer");
			if(isScoped) ResourceManager.g3.renderPart("Scope");
		}

		if(!silenced) {
			double smokeScale = 0.75;
			
			GL11.glPushMatrix();
			GL11.glTranslated(0, 0, 13);
			GL11.glRotated(90, 0, 1, 0);
			GL11.glScaled(smokeScale, smokeScale, smokeScale);
			this.renderSmokeNodes(gun.getConfig(stack, 0).smokeNodes, 0.5D);
			GL11.glPopMatrix();
			
			GL11.glShadeModel(GL11.GL_FLAT);
	
			GL11.glPushMatrix();
			GL11.glTranslated(0, 0, 12);
			GL11.glRotated(90, 0, 1, 0);
			GL11.glRotated(-25 + gun.shotRand * 10, 1, 0, 0);
			GL11.glScaled(0.75, 0.75, 0.75);
			this.renderMuzzleFlash(gun.lastShot[0], 75, 10);
			GL11.glPopMatrix();
		}
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		double scale = 1D;
		GL11.glScaled(scale, scale, scale);
		GL11.glTranslated(0, 2, 4);

	}

	@Override
	public void setupInv(ItemStack stack) {
		super.setupInv(stack);
		if(hasStock(stack)) {
			double scale = 0.875D;
			GL11.glScaled(scale, scale, scale);
			GL11.glRotated(25, 1, 0, 0);
			GL11.glRotated(hasSilencer(stack) ? 50 : 45, 0, 1, 0);
			GL11.glTranslated(hasSilencer(stack) ? 0.75 : -0.5, 0.5, 0);
		} else {
			double scale = 1.125D;
			GL11.glScaled(scale, scale, scale);
			GL11.glRotated(25, 1, 0, 0);
			GL11.glRotated(hasSilencer(stack) ? 55 : 45, 0, 1, 0); //preserves proportions whilst limiting size
			GL11.glTranslated(2.5, 0.5, 0);
		}
	}

	@Override
	public void setupModTable(ItemStack stack) {
		double scale = -5D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glTranslated(0, 0.5, -0.5);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {
		GL11.glEnable(GL11.GL_LIGHTING);
		
		boolean silenced = hasSilencer(stack);
		boolean isScoped = this.isScoped(stack);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(getTexture(stack));
		ResourceManager.g3.renderPart("Rifle");
		if(hasStock(stack)) ResourceManager.g3.renderPart("Stock");
		ResourceManager.g3.renderPart("Magazine");
		if(!silenced)ResourceManager.g3.renderPart("Flash_Hider");
		ResourceManager.g3.renderPart("Guide_And_Bolt");
		ResourceManager.g3.renderPart("Handle");
		ResourceManager.g3.renderPart("Trigger");
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, -0.875, -3.5);
		GL11.glRotated(-30, 1, 0, 0);
		GL11.glTranslated(0, 0.875, 3.5);
		ResourceManager.g3.renderPart("Selector");
		GL11.glPopMatrix();
		
		if(silenced || isScoped) {
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.g3_attachments);
			if(silenced) ResourceManager.g3.renderPart("Silencer");
			if(isScoped) ResourceManager.g3.renderPart("Scope");
		}
		GL11.glShadeModel(GL11.GL_FLAT);
	}
	
	public boolean hasStock(ItemStack stack) {
		return !WeaponModManager.hasUpgrade(stack, 0, WeaponModManager.ID_NO_STOCK);
	}
	
	public boolean hasSilencer(ItemStack stack) {
		return stack.getItem() == ModItems.gun_g3_zebra || WeaponModManager.hasUpgrade(stack, 0, WeaponModManager.ID_SILENCER);
	}
	
	public boolean isScoped(ItemStack stack) {
		return stack.getItem() == ModItems.gun_g3_zebra || WeaponModManager.hasUpgrade(stack, 0, WeaponModManager.ID_SCOPE);
	}
	
	public ResourceLocation getTexture(ItemStack stack) {
		if(WeaponModManager.hasUpgrade(stack, 0, WeaponModManager.ID_FURNITURE_GREEN)) return ResourceManager.g3_green_tex;
		if(WeaponModManager.hasUpgrade(stack, 0, WeaponModManager.ID_FURNITURE_BLACK)) return ResourceManager.g3_black_tex;
		return texture;
	}
}

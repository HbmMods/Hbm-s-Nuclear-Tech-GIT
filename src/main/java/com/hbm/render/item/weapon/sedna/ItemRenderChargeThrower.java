package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.factory.XFactoryTool;
import com.hbm.items.weapon.sedna.mags.MagazineFullReload;
import com.hbm.items.weapon.sedna.mods.WeaponModManager;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ItemRenderChargeThrower extends ItemRenderWeaponBase {

	@Override
	protected float getTurnMagnitude(ItemStack stack) { return ItemGunBaseNT.getIsAiming(stack) ? 0F : -0.5F; }

	@Override
	public float getViewFOV(ItemStack stack, float fov) {
		float aimingProgress = ItemGunBaseNT.prevAimingProgress + (ItemGunBaseNT.aimingProgress - ItemGunBaseNT.prevAimingProgress) * interp;
		return  fov * (1 - aimingProgress * (isScoped(stack) ? 0.66F : 0.33F));
	}

	@Override
	public void setupFirstPerson(ItemStack stack) {
		GL11.glTranslated(0, 0, 0.875);

		float offset = 0.8F;
		float zoom = 0.5F;
		
		if(isScoped(stack)) standardAimingTransform(stack,
				-1.5F * offset, -1.25F * offset, 3.5F * offset,
				-0.15625, -6.5 / 8D, 1.6875);
		else standardAimingTransform(stack,
				-1.5F * offset, -1.25F * offset, 3.5F * offset,
				-1.5F * zoom, -1.25F * zoom, 3.5F * zoom);
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		boolean usingScope = this.isScoped(stack) && gun.aimingProgress == 1 && gun.prevAimingProgress == 1;
		MagazineFullReload mag = (MagazineFullReload) gun.getConfig(stack, 0).getReceivers(stack)[0].getMagazine(stack);
		
		if(usingScope) {
			double scale = 3.5D;
			GL11.glScaled(scale, scale, scale);
			GL11.glTranslated(-0.5, -1.5, -4);
		} else {
			double scale = 0.5D;
			GL11.glScaled(scale, scale, scale);
		}

		boolean reloading = HbmAnimations.getRelevantAnim(0) != null && HbmAnimations.getRelevantAnim(0).animation.getBus("AMMO") != null;
		double[] equip = HbmAnimations.getRelevantTransformation("EQUIP");
		double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
		double[] raise = HbmAnimations.getRelevantTransformation("RAISE");
		double[] ammo = HbmAnimations.getRelevantTransformation("AMMO");
		double[] twist = HbmAnimations.getRelevantTransformation("TWIST");
		double[] turn = HbmAnimations.getRelevantTransformation("TURN");
		double[] roll = HbmAnimations.getRelevantTransformation("ROLL");

		GL11.glTranslated(0, 0, -7);
		GL11.glRotated(equip[0], -1, 0, 0);
		GL11.glTranslated(0, 0, 7);
		
		GL11.glTranslated(0, -7, 4);
		GL11.glRotated(raise[0], 1, 0, 0);
		GL11.glTranslated(0, 7, -4);

		GL11.glTranslated(recoil[0], recoil[1], recoil[2]);

		GL11.glTranslated(0, 0, -2);
		GL11.glRotated(turn[1], 0, 1, 0);
		GL11.glTranslated(0, 0, 2);
		GL11.glTranslated(0, -1, 0);
		GL11.glRotated(roll[2], 0, 0, 1);
		GL11.glTranslated(0, 1, 0);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.charge_thrower_tex);
		ResourceManager.charge_thrower.renderPart("Gun");
		if(isScoped(stack) && !usingScope) ResourceManager.charge_thrower.renderPart("Scope");
		
		if(mag.getAmount(stack, null) > 0 || reloading) {

			GL11.glTranslated(ammo[0], ammo[1], ammo[2]);
			GL11.glRotated(twist[2], 0, 0, 1);
			
			if(mag.getType(stack, null) == XFactoryTool.ct_hook) {
				Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.charge_thrower_hook_tex);
				ResourceManager.charge_thrower.renderPart("Hook");
			}
			if(mag.getType(stack, null) == XFactoryTool.ct_mortar) {
				Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.charge_thrower_mortar_tex);
				ResourceManager.charge_thrower.renderPart("Mortar");
			}
			if(mag.getType(stack, null) == XFactoryTool.ct_mortar_charge) {
				Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.charge_thrower_mortar_tex);
				ResourceManager.charge_thrower.renderPart("Mortar");
				ResourceManager.charge_thrower.renderPart("Oomph");
			}
			//Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.charge_thrower_rocket_tex);
			//ResourceManager.charge_thrower.renderPart("Rocket");
		}
		
		GL11.glShadeModel(GL11.GL_FLAT);
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		double scale = 1.5D;
		GL11.glScaled(scale, scale, scale);
		GL11.glTranslated(0.75, 1, 4);
	}

	@Override
	public void setupInv(ItemStack stack) {
		super.setupInv(stack);
		double scale = 1.25D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(45, 0, 1, 0);
		GL11.glTranslated(0, 0, -0.625);
	}

	@Override
	public void setupModTable(ItemStack stack) {
		double scale = -8.5D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glTranslated(0, 0, -1);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.charge_thrower_tex);
		ResourceManager.charge_thrower.renderPart("Gun");
		if(isScoped(stack)) ResourceManager.charge_thrower.renderPart("Scope");

		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		MagazineFullReload mag = (MagazineFullReload) gun.getConfig(stack, 0).getReceivers(stack)[0].getMagazine(stack);

		if(mag.getAmount(stack, null) > 0) {
			
			if(mag.getType(stack, null) == XFactoryTool.ct_hook) {
				Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.charge_thrower_hook_tex);
				ResourceManager.charge_thrower.renderPart("Hook");
			}
			if(mag.getType(stack, null) == XFactoryTool.ct_mortar) {
				Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.charge_thrower_mortar_tex);
				ResourceManager.charge_thrower.renderPart("Mortar");
			}
			if(mag.getType(stack, null) == XFactoryTool.ct_mortar_charge) {
				Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.charge_thrower_mortar_tex);
				ResourceManager.charge_thrower.renderPart("Mortar");
				ResourceManager.charge_thrower.renderPart("Oomph");
			}
		}
		
		GL11.glShadeModel(GL11.GL_FLAT);
	}
	
	public boolean isScoped(ItemStack stack) {
		return WeaponModManager.hasUpgrade(stack, 0, WeaponModManager.ID_SCOPE);
	}
}

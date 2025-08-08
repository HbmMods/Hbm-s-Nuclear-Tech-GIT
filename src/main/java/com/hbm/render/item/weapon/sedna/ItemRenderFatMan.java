package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.factory.XFactoryCatapult;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;
import com.hbm.render.util.RenderMiscEffects;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ItemRenderFatMan extends ItemRenderWeaponBase {

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
				-1.5F * offset, -1.25F * offset, 0.5F * offset,
				-1F * offset, -1.25F * offset, 0F * offset);
	}
	
	protected static String label = "AUTO";
	
	@Override
	public void renderFirstPerson(ItemStack stack) {

		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.fatman_tex);
		double scale = 0.5D;
		GL11.glScaled(scale, scale, scale);
		
		boolean isLoaded = gun.getConfig(stack, 0).getReceivers(stack)[0].getMagazine(stack).getAmount(stack, null) > 0;

		double[] equip = HbmAnimations.getRelevantTransformation("EQUIP");
		double[] lid = HbmAnimations.getRelevantTransformation("LID");
		double[] nuke = HbmAnimations.getRelevantTransformation("NUKE");
		double[] piston = HbmAnimations.getRelevantTransformation("PISTON");
		double[] handle = HbmAnimations.getRelevantTransformation("HANDLE");
		double[] gauge = HbmAnimations.getRelevantTransformation("GAUGE");
		
		GL11.glTranslated(0, 1, -2);
		GL11.glRotated(equip[0], 1, 0, 0);
		GL11.glTranslated(0, -1, 2);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);

		ResourceManager.fatman.renderPart("Launcher");

		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, handle[2]);
		ResourceManager.fatman.renderPart("Handle");
		
		GL11.glTranslated(0.4375, -0.875, 0);
		GL11.glRotated(gauge[2], 0, 0, 1);
		GL11.glTranslated(-0.4375, 0.875, 0);
		ResourceManager.fatman.renderPart("Gauge");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(0.25, 0.125, 0);
		GL11.glRotated(lid[2], 0, 0, 1);
		GL11.glTranslated(-0.25, -0.125, 0);
		ResourceManager.fatman.renderPart("Lid");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, piston[2]);
		if(!isLoaded && piston[2] == 0) GL11.glTranslated(0, 0, 3);
		ResourceManager.fatman.renderPart("Piston");
		GL11.glPopMatrix();

		if(isLoaded || nuke[0] != 0 || nuke[1] != 0 || nuke[2] != 0) {
			GL11.glPushMatrix();
			GL11.glTranslated(nuke[0], nuke[1], nuke[2]);
			renderNuke(gun.getConfig(stack, 0).getReceivers(stack)[0].getMagazine(stack).getType(stack, null));
			GL11.glPopMatrix();
		}
		
		GL11.glShadeModel(GL11.GL_FLAT);
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		double scale = 2.5D;
		GL11.glScaled(scale, scale, scale);
		GL11.glTranslated(-0.5, 0.5, -3);

	}

	@Override
	public void setupInv(ItemStack stack) {
		super.setupInv(stack);
		double scale = 1.375D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(45, 0, 1, 0);
		GL11.glTranslated(0, -0.5, 0);
	}

	@Override
	public void setupModTable(ItemStack stack) {
		double scale = -8.75D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(90, 0, 1, 0);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {
		GL11.glEnable(GL11.GL_LIGHTING);

		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		boolean isLoaded = gun.getConfig(stack, 0).getReceivers(stack)[0].getMagazine(stack).getAmount(stack, null) > 0;
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.fatman_tex);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		ResourceManager.fatman.renderPart("Launcher");
		ResourceManager.fatman.renderPart("Handle");
		ResourceManager.fatman.renderPart("Gauge");
		ResourceManager.fatman.renderPart("Lid");
		if(!isLoaded) GL11.glTranslated(0, 0, 3);
		ResourceManager.fatman.renderPart("Piston");
		if(isLoaded) renderNuke(gun.getConfig(stack, 0).getReceivers(stack)[0].getMagazine(stack).getType(stack, null));
		GL11.glShadeModel(GL11.GL_FLAT);
	}
	
	public void renderNuke(Object type) {
		if(type == XFactoryCatapult.nuke_balefire) {
			renderBalefire(interp);
		} else {
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.fatman_mininuke_tex);
			ResourceManager.fatman.renderPart("MiniNuke");
		}
	}
	
	public static void renderBalefire(float interp) {

		Minecraft mc = Minecraft.getMinecraft();
		mc.renderEngine.bindTexture(ResourceManager.fatman_balefire_tex);
		ResourceManager.fatman.renderPart("MiniNuke");
		mc.renderEngine.bindTexture(RenderMiscEffects.glintBF);
		mc.entityRenderer.disableLightmap(interp);

		float scale = 2F;
		float r = 0F;
		float g = 0.8F;
		float b = 0.15F;
		float speed = -6;
		float glintColor = 0.76F;
		int layers = 3;
		
		GL11.glPushMatrix();
		float offset = mc.thePlayer.ticksExisted + interp;
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		GL11.glDepthFunc(GL11.GL_EQUAL);
		GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
		GL11.glDepthMask(false);

		for(int k = 0; k < layers; ++k) {

			GL11.glColor4f(r * glintColor, g * glintColor, b * glintColor, 1.0F);
			GL11.glMatrixMode(GL11.GL_TEXTURE);
			GL11.glLoadIdentity();

			float movement = offset * (0.001F + (float) k * 0.003F) * speed;

			GL11.glScalef(scale, scale, scale);
			GL11.glRotatef(30.0F - k * 60.0F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(0F, movement, 0F);

			GL11.glMatrixMode(GL11.GL_MODELVIEW);

			ResourceManager.fatman.renderPart("MiniNuke");
		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glMatrixMode(GL11.GL_TEXTURE);
		GL11.glDepthMask(true);
		GL11.glLoadIdentity();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glPopMatrix();
		
		mc.entityRenderer.enableLightmap(interp);
	}
}

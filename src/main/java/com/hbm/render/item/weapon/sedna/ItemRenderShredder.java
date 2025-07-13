package com.hbm.render.item.weapon.sedna;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class ItemRenderShredder extends ItemRenderWeaponBase {
	
	protected ResourceLocation texture;
	
	public ItemRenderShredder(ResourceLocation texture) {
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
				-1.5F * offset, -1.25F * offset, 1.5F * offset,
			0, -6.25 / 8D, 0.5);
	}
	
	protected static String label = "[> <]";

	@Override
	public void renderFirstPerson(ItemStack stack) {
		
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		double scale = 0.25D;
		GL11.glScaled(scale, scale, scale);

		double[] equip = HbmAnimations.getRelevantTransformation("EQUIP");
		double[] lift = HbmAnimations.getRelevantTransformation("LIFT");
		double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
		double[] mag = HbmAnimations.getRelevantTransformation("MAG");
		double[] speen = HbmAnimations.getRelevantTransformation("SPEEN");
		double[] cycle = HbmAnimations.getRelevantTransformation("CYCLE");
		
		GL11.glTranslated(0, -2, -6);
		GL11.glRotated(equip[0], 1, 0, 0);
		GL11.glTranslated(0, 2, 6);
		
		GL11.glTranslated(0, 0, -4);
		GL11.glRotated(lift[0], 1, 0, 0);
		GL11.glTranslated(0, 0, 4);
		
		GL11.glTranslated(0, 0, recoil[2]);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		boolean sexy = stack.getItem() == ModItems.gun_autoshotgun_sexy;
		
		if(sexy || (gun.prevAimingProgress >= 1F && gun.aimingProgress >= 1F)) {

			GL11.glPushMatrix();
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_CULL_FACE);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
			FontRenderer font = Minecraft.getMinecraft().fontRenderer;
			float f3 = 0.04F;
			GL11.glTranslatef((font.getStringWidth(label) / 2) * f3, 3.25F, -1.75F);
			GL11.glScalef(f3, -f3, f3);
			GL11.glRotated(180D, 0, 1, 0);
			GL11.glNormal3f(0.0F, 0.0F, -1.0F * f3);
			float variance = 0.9F + player.getRNG().nextFloat() * 0.1F;
			font.drawString(label, 0, 0, new Color(sexy ? variance : 0F, sexy ? 0F : variance, 0F).getRGB());
			GL11.glColor3f(1F, 1F, 1F);

			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopAttrib();
			GL11.glPopMatrix();
			
			int brightness = player.worldObj.getLightBrightnessForSkyBlocks(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY), MathHelper.floor_double(player.posZ), 0);
			int j = brightness % 65536;
			int k = brightness / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j / 1.0F, (float) k / 1.0F);
		}
		
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);

		ResourceManager.shredder.renderPart("Gun");
		
		GL11.glPushMatrix();
		GL11.glTranslated(mag[0], mag[1], mag[2]);
		GL11.glTranslated(0, -1, -0.5);
		GL11.glRotated(speen[0], 1, 0, 0);
		GL11.glTranslated(0, 1, 0.5);
		ResourceManager.shredder.renderPart("Magazine");
		GL11.glTranslated(0, -1, -0.5);
		GL11.glRotated(cycle[2], 0, 0, 1);
		GL11.glTranslated(0, 1, 0.5);
		ResourceManager.shredder.renderPart("Shells");
		GL11.glPopMatrix();

		double smokeScale = 0.75;
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 1, 7.5);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glScaled(smokeScale, smokeScale, smokeScale);
		this.renderSmokeNodes(gun.getConfig(stack, 0).smokeNodes, 0.5D);
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPushMatrix();
		GL11.glTranslated(0, 1, 7.5);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glRotated(gun.shotRand * 90, 1, 0, 0);
		GL11.glScaled(0.75, 0.75, 0.75);
		this.renderMuzzleFlash(gun.lastShot[0], 75, 7.5);
		GL11.glPopMatrix();
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		double scale = 1.5D;
		GL11.glScaled(scale, scale, scale);
		GL11.glTranslated(0, 0.5, 4);

	}

	@Override
	public void setupInv(ItemStack stack) {
		super.setupInv(stack);
		double scale = 1.25D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glRotated(45, 0, 1, 0);
		GL11.glTranslated(-1.5, 0, 0);
	}

	@Override
	public void setupModTable(ItemStack stack) {
		double scale = -7.5D;
		GL11.glScaled(scale, scale, scale);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glTranslated(0, 0, 1.5);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		ResourceManager.shredder.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);
	}
}

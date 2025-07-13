package com.hbm.render.item.weapon.sedna;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.mags.IMagazine;
import com.hbm.main.MainRegistry;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;
import com.hbm.util.EntityDamageUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;

public class ItemRenderFolly extends ItemRenderWeaponBase {

	public static long timeAiming;
	public static boolean jingle = false;
	public static boolean wasAiming = false;

	@Override
	protected float getTurnMagnitude(ItemStack stack) { return ItemGunBaseNT.getIsAiming(stack) ? 2F : 2.5F; }

	@Override
	public float getViewFOV(ItemStack stack, float fov) {
		float aimingProgress = ItemGunBaseNT.prevAimingProgress + (ItemGunBaseNT.aimingProgress - ItemGunBaseNT.prevAimingProgress) * interp;
		return  fov * (1 - aimingProgress * 0.33F);
	}

	@Override
	public void setupFirstPerson(ItemStack stack) {
		GL11.glTranslated(0, 0, 0.875);

		float offset = 0.8F;
		float aim = 0.75F;
		standardAimingTransform(stack,
				-2.5F * offset, -1.5F * offset, 2.75F * offset,
				-2 * aim, -1 * aim, 2.25F * offset);
	}

	@Override
	public void renderFirstPerson(ItemStack stack) {

		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.folly_tex);
		double scale = 0.75D;
		GL11.glScaled(scale, scale, scale);

		double[] equip = HbmAnimations.getRelevantTransformation("EQUIP");
		double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
		double[] load = HbmAnimations.getRelevantTransformation("LOAD");
		double[] shell = HbmAnimations.getRelevantTransformation("SHELL");
		double[] screw = HbmAnimations.getRelevantTransformation("SCREW");
		double[] breech = HbmAnimations.getRelevantTransformation("BREECH");
		
		GL11.glTranslated(0, 1, -4);
		GL11.glRotated(-equip[0], 1, 0, 0);
		GL11.glTranslated(0, -1, 4);
		
		GL11.glTranslated(0, -2, -2);
		GL11.glRotated(load[0], 1, 0, 0);
		GL11.glTranslated(0, 2, 2);

		GL11.glShadeModel(GL11.GL_SMOOTH);

		ResourceManager.folly.renderPart("Cannon");
		
		GL11.glPushMatrix();
		GL11.glTranslated(recoil[0], recoil[1], recoil[2]);
		ResourceManager.folly.renderPart("Barrel");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(shell[0], shell[1], shell[2]);
		ResourceManager.folly.renderPart("Shell");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(breech[0], breech[1], breech[2]);
		ResourceManager.folly.renderPart("Breech");
		GL11.glTranslated(0, 1, 0);
		GL11.glRotated(screw[2], 0, 0, 1);
		GL11.glTranslated(0, -1, 0);
		ResourceManager.folly.renderPart("Cog");
		GL11.glPopMatrix();
		
		
		boolean isAiming = gun.prevAimingProgress >= 1F && gun.aimingProgress >= 1F;
		if(isAiming & !wasAiming) timeAiming = System.currentTimeMillis();
		
		if(isAiming) {
			
			String splash = getBootSplash();
			
			if(!jingle && !splash.isEmpty()) {
				MainRegistry.proxy.playSoundClient(player.posX, player.posY, player.posZ, "hbm:weapon.fire.vstar", 0.5F, 1F);
				jingle = true;
			}
	
			GL11.glPushMatrix();
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_CULL_FACE);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
			FontRenderer font = Minecraft.getMinecraft().fontRenderer;
			float variance = 0.85F + player.getRNG().nextFloat() * 0.15F;
			
			if(System.currentTimeMillis() - timeAiming > 5000 && load[0] == 0) {
				IMagazine mag = gun.getConfig(stack, 0).getReceivers(stack)[0].getMagazine(stack);
				String msg = mag.getAmount(stack, player.inventory) > 0 ? "+" : "No ammo";
				GL11.glPushMatrix();
				float crosshairSize = 0.01F;
				GL11.glTranslatef((font.getStringWidth(msg) / 2) * crosshairSize + 2, 1F + font.FONT_HEIGHT * crosshairSize / 2F, -2.75F);
				GL11.glScalef(crosshairSize, -crosshairSize, crosshairSize);
				GL11.glRotated(180D, 0, 1, 0);
				GL11.glNormal3f(0.0F, 0.0F, -1.0F * crosshairSize);
				font.drawString(msg, 0, 0, new Color(variance, variance * 0.5F, 0F).getRGB());
				GL11.glPopMatrix();
			}
			
			GL11.glPushMatrix();
			float splashSize = 0.02F;
			GL11.glTranslatef((font.getStringWidth(splash) / 2) * splashSize + 2, 1F + font.FONT_HEIGHT * splashSize / 2F, -2.75F);
			GL11.glScalef(splashSize, -splashSize, splashSize);
			GL11.glRotated(180D, 0, 1, 0);
			GL11.glNormal3f(0.0F, 0.0F, -1.0F * splashSize);
			font.drawString(splash, 0, 0, new Color(variance, variance * 0.5F, 0F).getRGB());
			GL11.glPopMatrix();
			
			List<String> tty = getTTY();
			if(!tty.isEmpty()) {
				GL11.glPushMatrix();
				float fontSize = 0.005F;
				GL11.glTranslatef(2.5F, 1.375F, -2.75F);
				GL11.glScalef(fontSize, -fontSize, fontSize);
				GL11.glRotated(180D, 0, 1, 0);
				GL11.glNormal3f(0.0F, 0.0F, -1.0F * fontSize);
				for(String line : tty) {
					font.drawString(line, 0, 0, new Color(variance, variance * 0.5F, 0F).getRGB());
					GL11.glTranslated(0, (font.FONT_HEIGHT + 2), 0);
				}
				GL11.glPopMatrix();
			}
			
			GL11.glColor3f(1F, 1F, 1F);
	
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glPopAttrib();
			GL11.glPopMatrix();
			
			int brightness = player.worldObj.getLightBrightnessForSkyBlocks(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY), MathHelper.floor_double(player.posZ), 0);
			int j = brightness % 65536;
			int k = brightness / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j / 1.0F, (float) k / 1.0F);
		} else {
			jingle = false;
		}
		
		wasAiming = isAiming;
		
		GL11.glShadeModel(GL11.GL_FLAT);
	}

	@Override
	public void setupThirdPerson(ItemStack stack) {
		super.setupThirdPerson(stack);
		double scale = 3D;
		GL11.glScaled(scale, scale, scale);
		GL11.glTranslated(-0.25, 0.5, 3);

	}

	@Override
	public void setupInv(ItemStack stack) {
		super.setupInv(stack);
		double scale = 1.25D;
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
		GL11.glTranslated(0, -1, 0);
	}

	@Override
	public void renderOther(ItemStack stack, ItemRenderType type) {
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.folly_tex);
		ResourceManager.folly.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);
	}
	
	public static String getBootSplash() {
		long now = System.currentTimeMillis();
		if(timeAiming + 5000 < now) return "";
		if(timeAiming + 3000 > now) return "";
		int splashIndex = (int)((now - timeAiming - 3000) * 35 / 2000) - 10;
		//use the StringBuilder this, can't eat the drywall that, this used to be a free country
		char[] letters = "VStarOS".toCharArray();
		String splash = "";
		for(int i = 0; i < letters.length; i++) {
			if(i < splashIndex - 1) splash += EnumChatFormatting.LIGHT_PURPLE;
			if(i == splashIndex - 1) splash += EnumChatFormatting.AQUA;
			if(i == splashIndex) splash += EnumChatFormatting.WHITE;
			if(i == splashIndex + 1) splash += EnumChatFormatting.AQUA;
			if(i == splashIndex + 2) splash += EnumChatFormatting.LIGHT_PURPLE;
			if(i > splashIndex + 2) splash += EnumChatFormatting.BLACK;
			splash += letters[i];
		}
		return splash;
	}
	
	public static List<String> getTTY() {
		List<String> tty = new ArrayList();
		long now = System.currentTimeMillis();
		int time = (int)((now - timeAiming));
		if(time < 3000) {
			if(time > 250) tty.add(EnumChatFormatting.GREEN + "POST successful - Code 0");
			if(time > 500) tty.add(EnumChatFormatting.GREEN + "8,388,608 bytes of RAM installed");
			if(time > 500) tty.add(EnumChatFormatting.GREEN + "5,187,427 bytes available");
			if(time > 750) tty.add(EnumChatFormatting.GREEN + "Reticulating splines...");
			if(time > 1500) tty.add(EnumChatFormatting.GREEN + "No keyboard found!");
			if(time > 2000) tty.add(EnumChatFormatting.GREEN + "Booting from /dev/sda1...");
		}
		if(time > 5000) {
			EntityPlayer player = MainRegistry.proxy.me();
			MovingObjectPosition mop = EntityDamageUtil.getMouseOver(player, 250);
			String target = EnumChatFormatting.GREEN + "Target: ";
			if(mop.typeOfHit == mop.typeOfHit.MISS) target += "N/A";
			if(mop.typeOfHit == mop.typeOfHit.BLOCK) target += mop.blockX + "/" + mop.blockY + "/" + mop.blockZ;
			if(mop.typeOfHit == mop.typeOfHit.ENTITY) target += mop.entityHit.getCommandSenderName();
			tty.add(target);
			tty.add(EnumChatFormatting.GREEN + "Angle: " + ((int)(-player.rotationPitch * 100) / 100D));
		}
		return tty;
	}
}

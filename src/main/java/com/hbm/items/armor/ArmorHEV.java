package com.hbm.items.armor;

import org.lwjgl.opengl.GL11;

import com.hbm.extprop.HbmLivingProps;
import com.hbm.items.ModItems;
import com.hbm.render.model.ModelArmorHEV;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

public class ArmorHEV extends ArmorFSBPowered {

	public ArmorHEV(ArmorMaterial material, int slot, String texture, long maxPower, long chargeRate, long consumption, long drain) {
		super(material, slot, texture, maxPower, chargeRate, consumption, drain);
	}

	@SideOnly(Side.CLIENT)
	ModelArmorHEV[] models;

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {

		if(models == null) {
			models = new ModelArmorHEV[4];

			for(int i = 0; i < 4; i++)
				models[i] = new ModelArmorHEV(i);
		}

		return models[armorSlot];
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleOverlay(RenderGameOverlayEvent.Pre event, EntityPlayer player) {

		if(this.hasFSBArmorIgnoreCharge(player) && player.inventory.armorInventory[2].getItem() == ModItems.hev_plate) {

			if(event.type == ElementType.ARMOR) {
				event.setCanceled(true);
				return;
			}

			if(event.type == ElementType.HEALTH) {
				event.setCanceled(true);
				renderOverlay(event, player);
				return;
			}
		}
	}

	private static long lastSurvey;
	private static float prevResult;
	private static float lastResult;

	private void renderOverlay(RenderGameOverlayEvent.Pre event, EntityPlayer player) {

		float in = HbmLivingProps.getRadiation(player);

		float radiation = 0;

		radiation = lastResult - prevResult;

		if(System.currentTimeMillis() >= lastSurvey + 1000) {
			lastSurvey = System.currentTimeMillis();
			prevResult = lastResult;
			lastResult = in;
		}

		GL11.glPushMatrix();

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_ALPHA_TEST);

		ScaledResolution res = event.resolution;

		double scale = 2D;

		GL11.glScaled(scale, scale, scale);

		int hX = (int) (8 / scale);
		int hY = (int) ((res.getScaledHeight() - 18 - 2) / scale);

		int healthColor = player.getHealth() * 5 > 15 ? 0xff8000 : 0xff0000;

		Minecraft.getMinecraft().fontRenderer.drawString("+" + (int) (player.getHealth() * 5), hX, hY, healthColor);

		double c = 0D;

		for(int i = 0; i < 4; i++) {

			ItemStack armor = player.inventory.armorInventory[i];
			ArmorFSBPowered item = ((ArmorFSBPowered) player.inventory.armorInventory[i].getItem());

			c += (double) item.getCharge(armor) / (double) item.getMaxCharge(armor);
		}

		int aX = (int) (70 / scale);
		int aY = (int) ((res.getScaledHeight() - 18 - 2) / scale);

		int armorColor = c * 25 > 15 ? 0xff8000 : 0xff0000;

		Minecraft.getMinecraft().fontRenderer.drawString("||" + (int) (c * 25), aX, aY, armorColor);

		String rad = "☢ [";

		for(int i = 0; i < 10; i++) {

			if(in / 100 > i) {

				int mid = (int) (in - i * 100);

				if(mid < 33)
					rad += "..";
				else if(mid < 67)
					rad += "|.";
				else
					rad += "||";
			} else {
				rad += " ";
			}
		}

		rad += "]";

		int rX = (int) (8 / scale);
		int rY = (int) ((res.getScaledHeight() - 40) / scale);

		int radColor = in < 800 ? 0xff8000 : 0xff0000;

		Minecraft.getMinecraft().fontRenderer.drawString(rad, rX, rY, radColor);

		GL11.glScaled(1 / scale, 1 / scale, 1 / scale);

		scale = 1D;

		GL11.glScaled(scale, scale, scale);

		if(radiation > 0) {

			int dX = (int) (32 / scale);
			int dY = (int) ((res.getScaledHeight() - 55) / scale);

			String delta = "" + Math.round(radiation);

			if(radiation > 1000)
				delta = ">1000";
			else if(radiation < 1)
				delta = "<1";

			Minecraft.getMinecraft().fontRenderer.drawString(delta + " RAD/s", dX, dY, 0xFF0000);
		}

		GL11.glColor4f(1F, 1F, 1F, 1F);

		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glPopMatrix();

		Minecraft.getMinecraft().renderEngine.bindTexture(Gui.icons);
	}
}

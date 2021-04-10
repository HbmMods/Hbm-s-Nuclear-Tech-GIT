package com.hbm.render.util;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.lib.RefStrings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderScreenOverlay {

	private static final ResourceLocation misc = new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_misc.png");
	private static final RenderItem itemRenderer = RenderItem.getInstance();
	
	private static long lastSurvey;
	private static float prevResult;
	private static float lastResult;
	
	public static void renderRadCounter(ScaledResolution resolution, float in, Gui gui) {
		GL11.glPushMatrix();

		GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        
        float radiation = 0;
        
        radiation = lastResult - prevResult;
        
        if(System.currentTimeMillis() >= lastSurvey + 1000) {
        	lastSurvey = System.currentTimeMillis();
        	prevResult = lastResult;
        	lastResult = in;
        }
		
		int length = 74;
		int maxRad = 1000;
		
		int bar = getScaled(in, maxRad, 74);
		
		//if(radiation >= 1 && radiation <= 999)
		//	bar -= (1 + Minecraft.getMinecraft().theWorld.rand.nextInt(3));
		
		int posX = 16;
		int posY = resolution.getScaledHeight() - 18 - 2;

		Minecraft.getMinecraft().renderEngine.bindTexture(misc);
        gui.drawTexturedModalRect(posX, posY, 0, 0, 94, 18);
        gui.drawTexturedModalRect(posX + 1, posY + 1, 1, 19, bar, 16);
        
        if(radiation >= 25) {
            gui.drawTexturedModalRect(posX + length + 2, posY - 18, 36, 36, 18, 18);
        	
        } else if(radiation >= 10) {
            gui.drawTexturedModalRect(posX + length + 2, posY - 18, 18, 36, 18, 18);
        	
        } else if(radiation >= 2.5) {
            gui.drawTexturedModalRect(posX + length + 2, posY - 18, 0, 36, 18, 18);
        	
        }
		
		if(radiation > 1000) {
			Minecraft.getMinecraft().fontRenderer.drawString(">1000 RAD/s", posX, posY - 8, 0xFF0000);
		} else if(radiation >= 1) {
			Minecraft.getMinecraft().fontRenderer.drawString(((int)Math.round(radiation)) + " RAD/s", posX, posY - 8, 0xFF0000);
		} else if(radiation > 0) {
			Minecraft.getMinecraft().fontRenderer.drawString("<1 RAD/s", posX, posY - 8, 0xFF0000);
		}

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glPopMatrix();
		Minecraft.getMinecraft().renderEngine.bindTexture(Gui.icons);
	}
	
	private static int getScaled(double cur, double max, double scale) {
		
		return (int) Math.min(cur / max * scale, scale);
	}

	
	public static void renderCustomCrosshairs(ScaledResolution resolution, Gui gui, Crosshair cross) {
		
		if(cross == Crosshair.NONE) {
			Minecraft.getMinecraft().renderEngine.bindTexture(Gui.icons);
			return;
		}

		int size = cross.size;

		GL11.glPushMatrix();
			Minecraft.getMinecraft().renderEngine.bindTexture(misc);
	        GL11.glEnable(GL11.GL_BLEND);
	        OpenGlHelper.glBlendFunc(GL11.GL_ONE_MINUS_DST_COLOR, GL11.GL_ONE_MINUS_SRC_COLOR, 1, 0);
	        gui.drawTexturedModalRect(resolution.getScaledWidth() / 2 - (size / 2), resolution.getScaledHeight() / 2 - (size / 2), cross.x, cross.y, size, size);
	        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
	        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
		Minecraft.getMinecraft().renderEngine.bindTexture(Gui.icons);
	}
	
	public static void renderAmmo(ScaledResolution resolution, Gui gui, Item ammo, int count, int max, int dura, boolean renderCount) {
		
		GL11.glPushMatrix();
        
		Minecraft mc = Minecraft.getMinecraft();
		
		int pX = resolution.getScaledWidth() / 2 + 62 + 36;
		int pZ = resolution.getScaledHeight() - 21;
		
		Minecraft.getMinecraft().renderEngine.bindTexture(misc);
        gui.drawTexturedModalRect(pX, pZ + 16, 94, 0, 52, 3);
        gui.drawTexturedModalRect(pX + 1, pZ + 16, 95, 3, 50 - dura, 3);
		
		String cap = max == -1 ? ("∞") : ("" + max);
		
		if(renderCount)
			Minecraft.getMinecraft().fontRenderer.drawString(count + " / " + cap, pX + 16, pZ + 6, 0xFFFFFF);

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.enableGUIStandardItemLighting();
        	itemRenderer.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), new ItemStack(ammo), pX, pZ);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        
        GL11.glPopMatrix();
		Minecraft.getMinecraft().renderEngine.bindTexture(Gui.icons);
	}
	
	public static void renderAmmoAlt(ScaledResolution resolution, Gui gui, Item ammo, int count) {
		
		GL11.glPushMatrix();
        
		Minecraft mc = Minecraft.getMinecraft();
		
		int pX = resolution.getScaledWidth() / 2 + 62 + 36 + 18;
		int pZ = resolution.getScaledHeight() - 21 - 16;
		
		Minecraft.getMinecraft().renderEngine.bindTexture(misc);
		
		Minecraft.getMinecraft().fontRenderer.drawString(count + "x", pX + 16, pZ + 6, 0xFFFFFF);

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.enableGUIStandardItemLighting();
        	itemRenderer.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), new ItemStack(ammo), pX, pZ);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        
        GL11.glPopMatrix();
		Minecraft.getMinecraft().renderEngine.bindTexture(Gui.icons);
	}
	
	public enum Crosshair {

		NONE(0, 0, 0),
		CROSS(1, 55, 16),
		CIRCLE(19, 55, 16),
		SEMI(37, 55, 16),
		KRUCK(55, 55, 16),
		DUAL(1, 73, 16),
		SPLIT(19, 73, 16),
		CLASSIC(37, 73, 16),
		BOX(55, 73, 16),
		L_CROSS(0, 90, 32),
		L_KRUCK(32, 90, 32),
		L_CLASSIC(64, 90, 32),
		L_CIRCLE(96, 90, 32),
		L_SPLIT(0, 122, 32),
		L_ARROWS(32, 122, 32),
		L_BOX(64, 122, 32),
		L_CIRCUMFLEX(96, 122, 32),
		L_RAD(0, 154, 32);
		
		public int x;
		public int y;
		public int size;
		
		private Crosshair(int x, int y, int size) {
			this.x = x;
			this.y = y;
			this.size = size;
		}
	}

}

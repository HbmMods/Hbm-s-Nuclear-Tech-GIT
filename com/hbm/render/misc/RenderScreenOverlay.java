package com.hbm.render.misc;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;

public class RenderScreenOverlay {

	private static final ResourceLocation misc = new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_misc.png");
	
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
	}
	
	private static int getScaled(double cur, double max, double scale) {
		
		return (int) Math.min(cur / max * scale, scale);
	}

}

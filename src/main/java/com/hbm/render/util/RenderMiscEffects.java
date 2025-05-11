package com.hbm.render.util;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.IModelCustom;

public class RenderMiscEffects {

	public static ResourceLocation glint = new ResourceLocation(RefStrings.MODID + ":textures/misc/glint.png");
	public static ResourceLocation glintBF = new ResourceLocation(RefStrings.MODID + ":textures/misc/glintBF.png");

	public static void renderClassicGlint(World world, float interpol, IModelCustom model, String part, float colorMod, float r, float g, float b, float speed, float scale) {

		GL11.glPushMatrix();
		float offset = Minecraft.getMinecraft().thePlayer.ticksExisted + interpol;
		GL11.glEnable(GL11.GL_BLEND);
		float color = colorMod;
		GL11.glColor4f(color, color, color, 1.0F);
		GL11.glDepthFunc(GL11.GL_EQUAL);
		GL11.glDepthMask(false);

		for(int k = 0; k < 2; ++k) {

			GL11.glDisable(GL11.GL_LIGHTING);

			float glintColor = 0.76F;

			GL11.glColor4f(r * glintColor, g * glintColor, b * glintColor, 1.0F);
			GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
			GL11.glMatrixMode(GL11.GL_TEXTURE);
			GL11.glLoadIdentity();

			float movement = offset * (0.001F + (float) k * 0.003F) * speed;

			GL11.glScalef(scale, scale, scale);
			GL11.glRotatef(30.0F - (float) k * 60.0F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(0.0F, movement, 0.0F);

			GL11.glMatrixMode(GL11.GL_MODELVIEW);

			if("all".equals(part))
				model.renderAll();
			else
				model.renderPart(part);
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
	}

	public static void renderClassicGlint(World world, float interpol, IModelCustom model, String part, float r, float g, float b, float speed, float scale) {
		renderClassicGlint(world, interpol, model, part, 0.5F, r, g, b, speed, scale);
	}

	public static void renderClassicGlint(World world, float interpol, IModelCustom model, String part) {
		renderClassicGlint(world, interpol, model, part, 0.5F, 0.25F, 0.8F, 20.0F, 1F / 3F);
	}
}

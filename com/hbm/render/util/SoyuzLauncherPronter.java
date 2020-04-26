package com.hbm.render.util;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;

public class SoyuzLauncherPronter {

	
	public static void prontLauncher(double rot) {

		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
		TextureManager tex = Minecraft.getMinecraft().getTextureManager();

		tex.bindTexture(ResourceManager.soyuz_launcher_legs_tex);
		ResourceManager.soyuz_launcher_legs.renderAll();

		tex.bindTexture(ResourceManager.soyuz_launcher_table_tex);
		ResourceManager.soyuz_launcher_table.renderAll();

		tex.bindTexture(ResourceManager.soyuz_launcher_tower_base_tex);
		ResourceManager.soyuz_launcher_tower_base.renderAll();

		GL11.glPushMatrix();
			tex.bindTexture(ResourceManager.soyuz_launcher_tower_tex);
			GL11.glTranslated(0, 5.5, 5.5);
			GL11.glRotated(rot, 1, 0, 0);
			GL11.glTranslated(0, -5.5, -5.5);
			ResourceManager.soyuz_launcher_tower.renderAll();
		GL11.glPopMatrix();

		tex.bindTexture(ResourceManager.soyuz_launcher_support_base_tex);
		ResourceManager.soyuz_launcher_support_base.renderAll();

		GL11.glPushMatrix();
			tex.bindTexture(ResourceManager.soyuz_launcher_support_tex);
			GL11.glTranslated(0, 5.5, -6.5);
			GL11.glRotated(rot, -1, 0, 0);
			GL11.glTranslated(0, -5.5, 6.5);
			ResourceManager.soyuz_launcher_support.renderAll();
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		GL11.glPopMatrix();
	}
}

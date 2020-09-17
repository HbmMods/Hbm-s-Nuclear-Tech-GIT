package com.hbm.render.util;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.loader.HmfController;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;

public class TomPronter {

	
	public static void prontTom() {
		GL11.glPushMatrix();

		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glScalef(100F, 100F, 100F);
		
		TextureManager tex = Minecraft.getMinecraft().getTextureManager();
		
		tex.bindTexture(ResourceManager.tom_main_tex);
		ResourceManager.tom_main.renderAll();
		
    	HmfController.setMod(50000D, 2500D);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GL11.glDisable(GL11.GL_ALPHA_TEST);

        float rot = -System.currentTimeMillis() / 10 % 360;
		//GL11.glScalef(1.2F, 2F, 1.2F);
		GL11.glScalef(0.8F, 5F, 0.8F);
		
		Random rand = new Random(0);
		
        for(int i = 0; i < 20/*10*/; i++) {
			tex.bindTexture(ResourceManager.tom_flame_tex);
			
			int r = rand.nextInt(90);
			
			GL11.glRotatef(rot + r, 0, 1, 0);
			
			ResourceManager.tom_flame.renderAll();
			
			GL11.glRotatef(rot, 0, -1, 0);
			
			GL11.glScalef(-1.015F, 0.9F, 1.015F);
        }
		
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);

		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_LIGHTING);
        HmfController.resetMod();
		
		GL11.glPopMatrix();
	}

}

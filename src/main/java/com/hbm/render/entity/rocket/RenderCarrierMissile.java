package com.hbm.render.entity.rocket;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderCarrierMissile extends Render {

	public RenderCarrierMissile() { }

	@Override
	public void doRender(Entity rocket, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_,
			float p_76986_9_) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) p_76986_2_, (float) p_76986_4_, (float) p_76986_6_);
		GL11.glScalef(1.0F, 1.0F, 1.0F);

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glScalef(2F, 2F, 2F);
		bindTexture(ResourceManager.missileCarrier_tex);
		ResourceManager.missileCarrier.renderAll();
		
		if(rocket.getDataWatcher().getWatchableObjectInt(8) == 1) {
	        GL11.glTranslated(0.0D, 0.5D, 0.0D);
	        GL11.glTranslated(1.25D, 0.0D, 0.0D);
			bindTexture(ResourceManager.missileBooster_tex);
			ResourceManager.missileBooster.renderAll();
	        GL11.glTranslated(-2.5D, 0.0D, 0.0D);
			ResourceManager.missileBooster.renderAll();
	        GL11.glTranslated(1.25D, 0.0D, 0.0D);
	        GL11.glTranslated(0.0D, 0.0D, 1.25D);
			ResourceManager.missileBooster.renderAll();
	        GL11.glTranslated(0.0D, 0.0D, -2.5D);
			ResourceManager.missileBooster.renderAll();
	        GL11.glTranslated(0.0D, 0.0D, 1.25D);
		}

        GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return ResourceManager.missileCarrier_tex;
	}
}

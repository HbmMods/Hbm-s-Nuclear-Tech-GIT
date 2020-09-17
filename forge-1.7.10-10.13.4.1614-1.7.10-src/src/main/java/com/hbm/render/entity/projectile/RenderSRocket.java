package com.hbm.render.entity.projectile;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.projectile.EntityRocketHoming;
import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelSRocket;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderSRocket extends Render {

	private ModelSRocket missile;

	public RenderSRocket() {
		missile = new ModelSRocket();
	}

	public void doRender(EntityRocketHoming rocket, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_,
			float p_76986_9_) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) p_76986_2_, (float) p_76986_4_, (float) p_76986_6_);
		GL11.glRotatef(rocket.prevRotationYaw + (rocket.rotationYaw - rocket.prevRotationYaw) * p_76986_9_ - 90.0F,
				0.0F, 1.0F, 0.0F);
		GL11.glRotatef(rocket.prevRotationPitch + (rocket.rotationPitch - rocket.prevRotationPitch) * p_76986_9_ + 180,
				0.0F, 0.0F, 1.0F);
		GL11.glScalef(1.5F, 1.5F, 1.5F);

		if(rocket.getIsCritical())
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/models/ModelSVTRocket.png"));
		else
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/models/ModelSRocket.png"));
		missile.renderAll(0.0625F);
		GL11.glPopMatrix();
	}
	
	@Override
	public void doRender(Entity rocket, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_,
			float p_76986_9_) {
		doRender((EntityRocketHoming)rocket, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return new ResourceLocation(RefStrings.MODID + ":textures/models/ModelSRocket.png");
	}
}

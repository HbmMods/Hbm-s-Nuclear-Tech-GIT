package com.hbm.render;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderHunterChopper extends Render {
	
	ProtoCopter mine;

	public RenderHunterChopper() {
		mine = new ProtoCopter();
	}

	@Override
	public void doRender(Entity rocket, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_,
			float p_76986_9_) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) p_76986_2_, (float) p_76986_4_, (float) p_76986_6_);
		GL11.glTranslatef(0.0625F * 0, 0.0625F * 32, 0.0625F * 0);
		GL11.glScalef(4F, 4F, 4F);
		GL11.glRotatef(180, 1, 0, 0);
        GL11.glRotatef(rocket.prevRotationYaw + (rocket.rotationYaw - rocket.prevRotationYaw) * p_76986_9_ - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(rocket.prevRotationPitch + (rocket.rotationPitch - rocket.prevRotationPitch) * p_76986_9_, 0.0F, 0.0F, 1.0F);
		
		bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/models/TheGadget3_.png"));
		
		mine.renderAll(0.0625F);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return new ResourceLocation(RefStrings.MODID + ":textures/models/TheGadget3_.png");
	}
}

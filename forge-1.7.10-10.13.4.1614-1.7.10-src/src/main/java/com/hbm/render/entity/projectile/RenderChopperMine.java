package com.hbm.render.entity.projectile;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelChopperMine;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderChopperMine extends Render {
	
	ModelChopperMine mine;

	public RenderChopperMine() {
		mine = new ModelChopperMine();
	}

	@Override
	public void doRender(Entity rocket, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_,
			float p_76986_9_) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) p_76986_2_, (float) p_76986_4_, (float) p_76986_6_);
		GL11.glScalef(1.5F, 1.5F, 1.5F);
		GL11.glRotatef(180, 1, 0, 0);
		
		bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/models/chopperBomb.png"));
		
		mine.renderAll(0.0625F);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return new ResourceLocation(RefStrings.MODID + ":textures/models/chopperBomb.png");
	}
}

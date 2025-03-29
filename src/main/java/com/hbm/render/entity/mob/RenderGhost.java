package com.hbm.render.entity.mob;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.mob.EntityGhost;
import com.hbm.lib.RefStrings;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderGhost extends RenderBiped {

	public RenderGhost() {
		super(new ModelBiped(0.0F), 0.5F, 1.0F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityLiving entity) {
		return this.getEntityTexture((EntityGhost) entity);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return this.getEntityTexture((EntityGhost) entity);
	}

	protected ResourceLocation getEntityTexture(EntityGhost entity) {
		return new ResourceLocation(RefStrings.MODID + ":textures/entity/ghost.png");
	}
	
	@Override
	public void doRender(EntityLiving p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		GL11.glDisable(GL11.GL_BLEND);
	}
}

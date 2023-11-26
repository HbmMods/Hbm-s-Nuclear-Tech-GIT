package com.hbm.render.entity.mob;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderPlasticBag extends Render {
	
	private static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/mobs/plasticbag.obj"));
	private static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID, "textures/entity/plasticbag.png");

	public RenderPlasticBag() {
		this.shadowOpaque = 0.0F;
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f0, float f1) {
		
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * f1 + 90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * f1 - 90, 0.0F, 0.0F, 1.0F);
		
		this.bindEntityTexture(entity);
		model.renderAll();
		
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return texture;
	}
}

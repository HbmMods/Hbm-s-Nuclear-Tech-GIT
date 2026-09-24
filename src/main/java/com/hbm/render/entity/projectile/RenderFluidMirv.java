package com.hbm.render.entity.projectile;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderFluidMirv extends Render {
	private static final ResourceLocation modelObj = new ResourceLocation(RefStrings.MODID, "models/bombletTheta.obj");
	private static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID, "textures/models/fluid_mirv.png");
	private final IModelCustom model;
	public RenderFluidMirv() {
		this.model = AdvancedModelLoader.loadModel(modelObj);
	}
	
	@Override
	public void doRender(Entity entity, double x, double y, double z, float yaw, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		
		GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * interp - 90f, 0f, 1f, 0f);
		GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * interp, 0f, 0f, 1f);
		
		GL11.glScalef(0.5f, 0.5f, 0.5f);
		
		bindTexture(texture);
		model.renderAll();
		
		GL11.glColor3f(1f, 1f, 1f);
		GL11.glPopMatrix();
	}
	
	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return texture;
	}
}

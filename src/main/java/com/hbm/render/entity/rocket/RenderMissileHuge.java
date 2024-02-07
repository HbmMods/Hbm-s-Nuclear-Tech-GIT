package com.hbm.render.entity.rocket;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.missile.EntityMissileTier3.*;
import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderMissileHuge extends Render {

	public RenderMissileHuge() {
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float interp) {

		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, (float) z);
		GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * interp - 90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * interp, 0.0F, 0.0F, 1.0F);
		GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * interp - 90.0F, 0.0F, -1.0F, 0.0F);

		if(entity instanceof EntityMissileBurst) bindTexture(ResourceManager.missileHuge_HE_tex);
		if(entity instanceof EntityMissileInferno) bindTexture(ResourceManager.missileHuge_IN_tex);
		if(entity instanceof EntityMissileRain) bindTexture(ResourceManager.missileHuge_CL_tex);
		if(entity instanceof EntityMissileDrill) bindTexture(ResourceManager.missileHuge_BU_tex);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		ResourceManager.missileHuge.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return ResourceManager.missileHuge_HE_tex;
	}
}

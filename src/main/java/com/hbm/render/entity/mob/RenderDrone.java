package com.hbm.render.entity.mob;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.mob.EntityFBIDrone;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderDrone extends Render {

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f0, float f1) {

		GL11.glPushMatrix();
		GL11.glTranslated(x, y + 0.25, z);
		
		this.bindTexture(getEntityTexture(entity));
		
		Random rand = new Random(entity.getEntityId());
		GL11.glRotated(rand.nextDouble() * 360D, 0, 1, 0);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glDisable(GL11.GL_CULL_FACE);
		ResourceManager.drone.renderAll();
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return this.getEntityTexture((EntityFBIDrone) entity);
	}

	protected ResourceLocation getEntityTexture(EntityFBIDrone entity) {
		return new ResourceLocation(RefStrings.MODID + ":textures/entity/quadcopter.png");
	}
}

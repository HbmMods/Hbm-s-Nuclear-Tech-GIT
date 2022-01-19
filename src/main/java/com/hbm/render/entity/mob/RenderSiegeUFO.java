package com.hbm.render.entity.mob;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.mob.siege.EntitySiegeUFO;
import com.hbm.entity.mob.siege.SiegeTier;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderSiegeUFO extends Render {

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f0, float f1) {

		GL11.glPushMatrix();
		GL11.glTranslated(x, y + 0.25, z);
		
		EntitySiegeUFO ufo = (EntitySiegeUFO) entity;
		
		this.bindTexture(getEntityTexture(entity));
		
		double rot = (entity.ticksExisted + f1) * 5 % 360D;
		GL11.glRotated(rot, 0, 1, 0);

		
		if(!ufo.isEntityAlive()) {
			float tilt = ufo.deathTime + f1;
			GL11.glRotatef(tilt * 5, 1, 0, 1);
		} else if(entity.hurtResistantTime > 0) {
			GL11.glRotated(Math.sin(System.currentTimeMillis() * 0.01D) * (entity.hurtResistantTime - f1), 1, 0, 0);
		}
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glDisable(GL11.GL_CULL_FACE);
		ResourceManager.mini_ufo.renderAll();
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return this.getEntityTexture((EntitySiegeUFO) entity);
	}

	protected ResourceLocation getEntityTexture(EntitySiegeUFO entity) {
		SiegeTier tier = entity.getTier();
		return new ResourceLocation(RefStrings.MODID + ":textures/entity/ufo_siege_" + tier.name + ".png");
	}
}

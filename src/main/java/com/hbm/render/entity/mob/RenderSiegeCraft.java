package com.hbm.render.entity.mob;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.mob.siege.EntitySiegeCraft;
import com.hbm.entity.mob.siege.SiegeTier;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.util.BeamPronter;
import com.hbm.render.util.RenderMiscEffects;
import com.hbm.render.util.BeamPronter.EnumBeamType;
import com.hbm.render.util.BeamPronter.EnumWaveType;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

public class RenderSiegeCraft extends Render {

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f0, float f1) {

		GL11.glPushMatrix();
		GL11.glTranslated(x, y + 0.5, z);
		GL11.glPushMatrix();

		EntitySiegeCraft ufo = (EntitySiegeCraft) entity;
		//BossStatus.setBossStatus(ufo, false);
		
		this.bindTexture(getEntityTexture(entity));
		
		double rot = (entity.ticksExisted + f1) * 5 % 360D;
		GL11.glRotated(rot, 0, 1, 0);

		
		if(!ufo.isEntityAlive()) {
			float tilt = ufo.deathTime + f1;
			GL11.glRotatef(tilt * 5, 1, 0, 1);
		}
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glDisable(GL11.GL_CULL_FACE);
		ResourceManager.siege_ufo.renderPart("UFO");
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
		GL11.glDisable(GL11.GL_LIGHTING);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);

		float health = ufo.getHealth() / ufo.getMaxHealth();
		GL11.glColor3f(1F - health, health, 0F);
		ResourceManager.siege_ufo.renderPart("Coils");
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopAttrib();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		if(ufo.getBeam()) {
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/misc/glintBF.png"));
			RenderMiscEffects.renderClassicGlint(ufo.worldObj, f1, ResourceManager.siege_ufo, "UFO", 0.5F, 1.0F, 1.0F, 5, 1F);
		}
		
		GL11.glColor3f(1F, 1F, 1F);
		
		Random rand = new Random(entity.ticksExisted / 4);

		GL11.glPushMatrix();
		for(int i = 0; i < 8; i++) {
			GL11.glRotated(45D, 0, 1, 0);
			if(rand.nextInt(5) == 0 || ufo.getBeam()) {
				GL11.glPushMatrix();
				GL11.glTranslated(4, 0, 0);
				BeamPronter.prontBeam(Vec3.createVectorHelper(-1.125, 0, 2.875), EnumWaveType.RANDOM, EnumBeamType.LINE, 0x80d0ff, 0xffffff, (int)(System.currentTimeMillis() % 1000) / 50, 15, 0.125F, 1, 0, 256);
				GL11.glPopMatrix();
			}
		}
		GL11.glPopMatrix();
		GL11.glPopMatrix();

		if(ufo.getBeam()) {
			GL11.glPushMatrix();
			Vec3 delta = ufo.getLockon().addVector(-ufo.posX, -ufo.posY, -ufo.posZ);
			double length = delta.lengthVector();
			double scale = 0.1D;
			BeamPronter.prontBeam(delta, EnumWaveType.RANDOM, EnumBeamType.SOLID, 0x101020, 0x101020, entity.ticksExisted / 6, (int)(length / 2 + 1), (float)scale * 1F, 4, 0.25F, 256);
			BeamPronter.prontBeam(delta, EnumWaveType.RANDOM, EnumBeamType.SOLID, 0x202060, 0x202060, entity.ticksExisted / 2, (int)(length / 2 + 1), (float)scale * 7F, 2, 0.0625F, 256);
			BeamPronter.prontBeam(delta, EnumWaveType.RANDOM, EnumBeamType.SOLID, 0x202060, 0x202060, entity.ticksExisted / 4, (int)(length / 2 + 1), (float)scale * 7F, 2, 0.0625F, 256);
			GL11.glPopMatrix();
		}
		
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return this.getEntityTexture((EntitySiegeCraft) entity);
	}

	protected ResourceLocation getEntityTexture(EntitySiegeCraft entity) {
		SiegeTier tier = entity.getTier();
		return new ResourceLocation(RefStrings.MODID + ":textures/entity/siege_craft_" + tier.name + ".png");
	}
}

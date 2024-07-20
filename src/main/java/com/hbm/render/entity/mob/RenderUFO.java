package com.hbm.render.entity.mob;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.mob.EntityUFO;
import com.hbm.main.ResourceManager;
import com.hbm.render.util.BeamPronter;
import com.hbm.render.util.BeamPronter.EnumBeamType;
import com.hbm.render.util.BeamPronter.EnumWaveType;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

public class RenderUFO extends Render {

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f0, float f1) {

		BossStatus.setBossStatus((IBossDisplayData)entity, false);
		
		GL11.glPushMatrix();
		GL11.glTranslated(x, y + 1, z);
		
		EntityUFO ufo = (EntityUFO)entity;
		
		if(!ufo.isEntityAlive()) {
			float tilt = ufo.deathTime + 30 + f1;
			GL11.glRotatef(tilt, 1, 0, 1);
		}
		
		double scale = 2D;
		
		this.bindTexture(getEntityTexture(entity));
		
		GL11.glPushMatrix();
		double rot = (entity.ticksExisted + f1) * 5 % 360D;
		GL11.glRotated(rot, 0, 1, 0);
		GL11.glScaled(scale, scale, scale);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		ResourceManager.ufo.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();

		if(ufo.getBeam()) {
			int ix = (int)Math.floor(entity.posX);
			int iz = (int)Math.floor(entity.posZ);
			int iy = 0;
			
			for(int i = (int)Math.ceil(entity.posY); i >= 0; i--) {
				
				if(entity.worldObj.getBlock(ix, i, iz) != Blocks.air) {
					iy = i;
					break;
				}
			}
			
			double length = entity.posY - iy;
			
			if(length > 0) {
				BeamPronter.prontBeam(Vec3.createVectorHelper(0, -length, 0), EnumWaveType.SPIRAL, EnumBeamType.SOLID, 0x101020, 0x101020, 0, (int)(length + 1), 0F, 6, (float)scale * 0.75F, 256);
				BeamPronter.prontBeam(Vec3.createVectorHelper(0, -length, 0), EnumWaveType.RANDOM, EnumBeamType.SOLID, 0x202060, 0x202060, entity.ticksExisted / 2, (int)(length / 2 + 1), (float)scale * 1.5F, 2, 0.0625F, 256);
				BeamPronter.prontBeam(Vec3.createVectorHelper(0, -length, 0), EnumWaveType.RANDOM, EnumBeamType.SOLID, 0x202060, 0x202060, entity.ticksExisted / 4, (int)(length / 2 + 1), (float)scale * 1.5F, 2, 0.0625F, 256);
			}
		}
		
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return ResourceManager.ufo_tex;
	}
}

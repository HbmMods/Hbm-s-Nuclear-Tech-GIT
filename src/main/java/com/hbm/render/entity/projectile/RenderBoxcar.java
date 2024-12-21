package com.hbm.render.entity.projectile;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.projectile.EntityBoxcar;
import com.hbm.entity.projectile.EntityBuilding;
import com.hbm.entity.projectile.EntityDuchessGambit;
import com.hbm.entity.projectile.EntityTorpedo;
import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderBoxcar extends Render {

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f0, float f1) {

		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_LIGHTING);

		if(entity instanceof EntityBoxcar) {
			GL11.glTranslatef(0, 0, -1.5F);
			GL11.glRotated(180, 0, 0, 1);
			GL11.glRotated(90, 1, 0, 0);

			bindTexture(ResourceManager.boxcar_tex);
			ResourceManager.boxcar.renderAll();
		}

		if(entity instanceof EntityDuchessGambit) {
			GL11.glTranslatef(0, 0, -1.0F);

			bindTexture(ResourceManager.duchessgambit_tex);
			ResourceManager.duchessgambit.renderAll();
		}

		if(entity instanceof EntityBuilding) {
			GL11.glDisable(GL11.GL_CULL_FACE);
			bindTexture(ResourceManager.building_tex);
			ResourceManager.building.renderAll();
			GL11.glEnable(GL11.GL_CULL_FACE);
		}

		if(entity instanceof EntityTorpedo) {
			float f = entity.ticksExisted + f1;
			GL11.glRotatef(Math.min(85, f * 3), 1, 0, 0);
			GL11.glShadeModel(GL11.GL_SMOOTH);
			bindTexture(ResourceManager.torpedo_tex);
			ResourceManager.torpedo.renderAll();
			GL11.glShadeModel(GL11.GL_FLAT);
		}

		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return ResourceManager.boxcar_tex;
	}
}

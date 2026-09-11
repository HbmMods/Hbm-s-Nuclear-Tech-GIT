package com.hbm.render.entity.rocket;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.factory.LegoClient;
import com.hbm.main.ResourceManager;
import com.hbm.render.util.SoyuzPronter;
import com.hbm.util.Vec3NT;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderSoyuz extends Render {

	@Override
	public void doRender(Entity entity, double x, double y, double z, float i, float j) {

		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glEnable(GL11.GL_CULL_FACE);

		int type = entity.getDataWatcher().getWatchableObjectInt(8);
		SoyuzPronter.prontSoyuz(type);
		
		Vec3NT vec = new Vec3NT(0, 0, 0);
		
		vec.setComponents(x, y, z).normalizeSelf().multiply(-1.5);
		GL11.glTranslated(vec.xCoord, vec.yCoord, vec.zCoord);
		
		double scale = 1.5D;

		GL11.glPushMatrix(); {
			GL11.glScaled(scale, scale, scale);
			LegoClient.renderFlare(entity, j, 1F, 0.75F, 0.5F);
		} GL11.glPopMatrix();
		GL11.glPushMatrix(); {
			GL11.glTranslated(2.5, 0, 0);
			GL11.glScaled(scale, scale, scale);
			LegoClient.renderFlare(entity, j, 1F, 0.75F, 0.5F);
		} GL11.glPopMatrix();
		GL11.glPushMatrix(); {
			GL11.glTranslated(-2.5, 0, 0);
			GL11.glScaled(scale, scale, scale);
			LegoClient.renderFlare(entity, j, 1F, 0.75F, 0.5F);
		} GL11.glPopMatrix();
		GL11.glPushMatrix(); {
			GL11.glTranslated(0, 0, 2.5);
			GL11.glScaled(scale, scale, scale);
			LegoClient.renderFlare(entity, j, 1F, 0.75F, 0.5F);
		} GL11.glPopMatrix();
		GL11.glPushMatrix(); {
			GL11.glTranslated(0, 0, -2.5);
			GL11.glScaled(scale, scale, scale);
			LegoClient.renderFlare(entity, j, 1F, 0.75F, 0.5F);
		} GL11.glPopMatrix();

		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		//just so if there's a mod that is trying to pull a funny
		return ResourceManager.soyuz_payload;
	}
}

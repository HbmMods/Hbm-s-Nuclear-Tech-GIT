package com.hbm.render.entity.rocket;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.util.SoyuzPronter;

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
		
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		//just so if there's a mod that is trying to pull a funny
		return ResourceManager.soyuz_payload;
	}

}

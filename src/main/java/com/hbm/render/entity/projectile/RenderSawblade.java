package com.hbm.render.entity.projectile;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderSawblade extends Render {

	@Override
	public void doRender(Entity cog, double x, double y, double z, float f0, float f1) {
		
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);

		int orientation = cog.getDataWatcher().getWatchableObjectInt(10);
		switch(orientation % 6) {
		case 3: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 2: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(270, 0F, 1F, 0F); break;
		}
		
		GL11.glTranslated(0, 0, -1);

		
		if(orientation < 6) {
			GL11.glRotated(System.currentTimeMillis() % (360 * 5) / 3D, 0.0D, 0.0D, -1.0D);
		}
		
		GL11.glTranslated(0, -1.375, 0);
		
		this.bindEntityTexture(cog);
		ResourceManager.sawmill.renderPart("Blade");
		
		GL11.glPopMatrix();

	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return ResourceManager.sawmill_tex;
	}
}

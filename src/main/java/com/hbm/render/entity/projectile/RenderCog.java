package com.hbm.render.entity.projectile;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderCog extends Render {

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
			GL11.glRotated(System.currentTimeMillis() % (360 * 3) / 3D, 0.0D, 0.0D, -1.0D);
		}
		
		GL11.glTranslated(0, -1.375, 0);
		
		this.bindEntityTexture(cog);
		ResourceManager.stirling.renderPart("Cog");
		
		GL11.glPopMatrix();

	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		int meta = entity.getDataWatcher().getWatchableObjectInt(11);
		
		if(meta == 0)
			return ResourceManager.stirling_tex;
		else
			return ResourceManager.stirling_steel_tex;
	}
}

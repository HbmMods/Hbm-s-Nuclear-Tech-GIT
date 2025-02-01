package com.hbm.render.entity.projectile;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.projectile.EntityBulletBaseMK4;
import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderBulletMK4 extends Render {

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f0, float interp) {
		EntityBulletBaseMK4 bullet = (EntityBulletBaseMK4) entity;
		if(bullet.config == null) bullet.config = bullet.getBulletConfig();
		if(bullet.config == null) return;
		
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, (float) z);
		
		if(bullet.config.renderRotations) {
			GL11.glRotatef(bullet.prevRotationYaw + (bullet.rotationYaw - bullet.prevRotationYaw) * interp - 90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(bullet.prevRotationPitch + (bullet.rotationPitch - bullet.prevRotationPitch) * interp + 180, 0.0F, 0.0F, 1.0F);
		}
		
		if(bullet.config.renderer != null) {
			bullet.config.renderer.accept(bullet, interp);
		}
		
		GL11.glPopMatrix();
	}

	@Override protected ResourceLocation getEntityTexture(Entity entity) { return ResourceManager.universal; }
}

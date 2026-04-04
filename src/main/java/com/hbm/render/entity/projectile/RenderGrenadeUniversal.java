package com.hbm.render.entity.projectile;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.entity.grenade.EntityGrenadeUniversal;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderGrenade;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderGrenadeUniversal extends Render {

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f0, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		
		EntityGrenadeUniversal grenade = (EntityGrenadeUniversal) entity;
		
		double scale = 0.0625D;
		GL11.glScaled(scale, scale, scale);
		double yaw = grenade.prevRotationYaw + (grenade.rotationYaw - grenade.prevRotationYaw) * interp;
		GL11.glRotated(yaw, 0, 1, 0);

		double spin = grenade.prevSpin + (grenade.spin - grenade.prevSpin) * interp;
		GL11.glRotated(spin, 1, 0, 0);
		
		if(grenade.getBounces() > 0) {
			GL11.glRotated(-80, 0, 0, 1);
		}
		
		ItemStack stack = grenade.getGrenadeItem();
		ItemRenderGrenade.renderGrenade(stack, null);
		
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return ResourceManager.grenade_frag_tex;
	}
}

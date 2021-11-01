package com.hbm.render.entity.projectile;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.grenade.EntityGrenadeASchrab;
import com.hbm.entity.grenade.EntityGrenadeMk2;
import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderGrenade extends Render {

	@Override
	public void doRender(Entity grenade, double x, double y, double z, float f0, float interp) {

		GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y + 0.125F, (float)z);
        GL11.glRotatef(grenade.prevRotationYaw + (grenade.rotationYaw - grenade.prevRotationYaw) * interp - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(grenade.prevRotationPitch + (grenade.rotationPitch - grenade.prevRotationPitch) * interp, 0.0F, 0.0F, 1.0F);
        
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glShadeModel(GL11.GL_SMOOTH);

		bindTexture(getEntityTexture(grenade));

        if(grenade instanceof EntityGrenadeMk2) {
	        GL11.glRotatef(90, 0F, 1F, 0F);
	        GL11.glScaled(0.125, 0.125, 0.125);
			ResourceManager.grenade_frag.renderAll();
        }
        if(grenade instanceof EntityGrenadeASchrab) {
	        GL11.glRotatef(90, 0F, 1F, 0F);
	        GL11.glScaled(0.125, 0.125, 0.125);
			ResourceManager.grenade_aschrab.renderAll();
        }
        
        GL11.glShadeModel(GL11.GL_FLAT);
        
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity grenade) {

        if(grenade instanceof EntityGrenadeMk2) {
    		return ResourceManager.grenade_mk2;
        }
        if(grenade instanceof EntityGrenadeASchrab) {
    		return ResourceManager.grenade_aschrab_tex;
        }
        
		return null;
	}
}

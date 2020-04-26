package com.hbm.render.entity.projectile;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.projectile.EntityBoxcar;
import com.hbm.entity.projectile.EntityBuilding;
import com.hbm.entity.projectile.EntityDuchessGambit;
import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderBoxcar extends Render {

	@Override
	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_,
			float p_76986_9_) {

		GL11.glPushMatrix();
        GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_LIGHTING);
        
        if(p_76986_1_ instanceof EntityBoxcar) {
            GL11.glTranslatef(0, 0, -1.5F);
            GL11.glRotated(180, 0, 0, 1);
            GL11.glRotated(90, 1, 0, 0);
            
	        bindTexture(ResourceManager.boxcar_tex);
	        ResourceManager.boxcar.renderAll();
        }
        
        if(p_76986_1_ instanceof EntityDuchessGambit) {
            GL11.glTranslatef(0, 0, -1.0F);
            
	        bindTexture(ResourceManager.duchessgambit_tex);
	        ResourceManager.duchessgambit.renderAll();
        }
        
        if(p_76986_1_ instanceof EntityBuilding) {
            GL11.glDisable(GL11.GL_CULL_FACE);
	        bindTexture(ResourceManager.building_tex);
	        ResourceManager.building.renderAll();
	        GL11.glEnable(GL11.GL_CULL_FACE);
        }
        
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return ResourceManager.boxcar_tex;
	}

}
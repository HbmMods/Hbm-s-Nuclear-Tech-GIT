package com.hbm.render.entity.rocket;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.missile.EntityMinerRocket;
import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderMinerRocket extends Render {

	@Override
	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_,
			float p_76986_9_) {

		GL11.glPushMatrix();
        GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
        //GL11.glRotated(180, 0, 0, 1);
        GL11.glDisable(GL11.GL_CULL_FACE);
        
        if(p_76986_1_ instanceof EntityMinerRocket) {
        	bindTexture(ResourceManager.minerRocket_tex);
        } else {
        	bindTexture(ResourceManager.bobmazon_tex);
        	GL11.glRotatef(180, 1, 0, 0);
            //GL11.glTranslatef(0, 2, 0);
        }
        
        ResourceManager.minerRocket.renderAll();
        
        GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return ResourceManager.minerRocket_tex;
	}
}

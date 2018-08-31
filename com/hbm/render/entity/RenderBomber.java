package com.hbm.render.entity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderBomber extends Render {
	
	public RenderBomber() { }

	@Override
	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {

		GL11.glPushMatrix();
        GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
        GL11.glRotatef(p_76986_1_.prevRotationYaw + (p_76986_1_.rotationYaw - p_76986_1_.prevRotationYaw) * p_76986_9_ - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(90, 0F, 0F, 1F);
        GL11.glRotatef(p_76986_1_.prevRotationPitch + (p_76986_1_.rotationPitch - p_76986_1_.prevRotationPitch) * p_76986_9_, 0.0F, 0.0F, 1.0F);

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
        
        
        int i = p_76986_1_.getDataWatcher().getWatchableObjectByte(16);
        
        switch(i) {
        case 0: bindTexture(ResourceManager.dornier_0_tex); break;
        case 1: bindTexture(ResourceManager.dornier_1_tex); break;
        case 2: bindTexture(ResourceManager.dornier_2_tex); break;
        case 3: bindTexture(ResourceManager.dornier_3_tex); break;
        case 4: bindTexture(ResourceManager.dornier_4_tex); break;
        case 5: bindTexture(ResourceManager.b29_0_tex); break;
        case 6: bindTexture(ResourceManager.b29_1_tex); break;
        case 7: bindTexture(ResourceManager.b29_2_tex); break;
        case 8: bindTexture(ResourceManager.b29_3_tex); break;
        default: bindTexture(ResourceManager.dornier_1_tex); break;
        }

        switch(i) {
        case 0:
        case 1:
        case 2:
        case 3:
        case 4: GL11.glScalef(5F, 5F, 5F); GL11.glRotatef(-90, 0F, 1F, 0F); ResourceManager.dornier.renderAll(); break;
        case 5:
        case 6:
        case 7:
        case 8: GL11.glScalef(30F/3.1F, 30F/3.1F, 30F/3.1F); GL11.glRotatef(180, 0F, 1F, 0F); ResourceManager.b29.renderAll(); break;
        default: ResourceManager.dornier.renderAll(); break;
        }
        

        GL11.glEnable(GL11.GL_CULL_FACE);
        
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return ResourceManager.dornier_1_tex;
	}
}

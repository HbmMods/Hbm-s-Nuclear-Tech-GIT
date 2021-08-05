package com.hbm.render.entity.effect;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.logic.EntityDeathBlast;
import com.hbm.lib.RefStrings;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderDeathBlast extends Render {
	
	private static final IModelCustom sphere = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/Sphere.obj"));

	@Override
	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float p_76986_9_) {
		
		GL11.glPushMatrix();
    	GL11.glTranslatef((float)x, (float)y, (float)z);
    	GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glDepthMask(false);

		GL11.glPushMatrix();
			//GL11.glRotatef((entity.ticksExisted % 360) * 10, 0, 1, 0);
	        GL11.glEnable(GL11.GL_BLEND);
	        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
	        //GL11.glDisable(GL11.GL_ALPHA_TEST);
	        
	        GL11.glColor3f(1.0F, 0, 0);
	        
	        Vec3 vector = Vec3.createVectorHelper(0.5D, 0, 0);
	
	        Tessellator tessellator = Tessellator.instance;
			RenderHelper.disableStandardItemLighting();
			
	        for(int i = 0; i < 8; i++) {
	            tessellator.startDrawing(6);
	            tessellator.addVertex(vector.xCoord, 250.0D, vector.zCoord);
	            tessellator.addVertex(vector.xCoord, 0.0D, vector.zCoord);
	        	vector.rotateAroundY(45);
	            tessellator.addVertex(vector.xCoord, 0.0D, vector.zCoord);
	            tessellator.addVertex(vector.xCoord, 250.0D, vector.zCoord);
	            tessellator.draw();
	        }

	        GL11.glColor3f(1.0F, 0, 1.0F);
	        
	        for(int i = 0; i < 8; i++) {
	            tessellator.startDrawing(6);
	            tessellator.addVertex(vector.xCoord / 2, 250.0D, vector.zCoord / 2);
	            tessellator.addVertex(vector.xCoord / 2, 0.0D, vector.zCoord / 2);
	        	vector.rotateAroundY(45);
	            tessellator.addVertex(vector.xCoord / 2, 0.0D, vector.zCoord / 2);
	            tessellator.addVertex(vector.xCoord / 2, 250.0D, vector.zCoord / 2);
	            tessellator.draw();
	        }
	    GL11.glPopMatrix();
        
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_FLAT);

        GL11.glPopMatrix();
        
        renderOrb(entity, x, y, z, p_76986_8_, p_76986_9_);
	}

	public void renderOrb(Entity entity, double x, double y, double z, float p_76986_8_, float p_76986_9_) {
		GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, (float)z);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glAlphaFunc(GL11.GL_GEQUAL, 0);
		
		double scale = 10 - 10D * (((double)entity.ticksExisted) / ((double)EntityDeathBlast.maxAge));
		double alpha = (((double)entity.ticksExisted) / ((double)EntityDeathBlast.maxAge));
		
		if(scale < 0)
			scale = 0;
		
        GL11.glColor4d(1.0, 0, 1.0, alpha);

		GL11.glEnable(GL11.GL_BLEND);
        GL11.glScaled(scale, scale, scale);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        sphere.renderAll();

        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GL11.glScaled(1.25, 1.25, 1.25);
        GL11.glColor4d(1.0, 0, 0, alpha * 0.125);
        
        for(int i = 0; i < 8; i++) {
        	sphere.renderAll();
            GL11.glScaled(1.05, 1.05, 1.05);
        }
        
		GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glAlphaFunc(GL11.GL_GEQUAL, 0.1F);
        GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
        
        GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return null;
	}

}

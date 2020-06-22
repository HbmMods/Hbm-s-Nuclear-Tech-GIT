package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;

public class RenderForceField extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f) {
		
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);

        bindTexture(ResourceManager.radar_body_tex);
        
        ResourceManager.radar_body.renderAll();

        GL11.glTranslated(0, 0.5D, 0);
        
        System.currentTimeMillis();

        generateSphere(8, 16, 10F, 0x0088FF);
        
        GL11.glPopMatrix();
	}
	
	private void generateSphere(int l, int s, float rad, int hex) {

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LIGHTING);
        
        float sRot = 360F / s;
        float lRot = (float)Math.PI / l;
        
        for(int k = 0; k < s; k++) {

    		GL11.glRotatef(sRot, 0F, 1F, 0F);
    		
	        Vec3 vec = Vec3.createVectorHelper(0, rad, 0);
	        
	        for(int i = 0; i < l; i++) {

	            Tessellator tessellator = Tessellator.instance;
	            
	            /*if((i < 2 || i > l - 2) && k % 10 == 0) {
		            tessellator.startDrawing(3);
		            tessellator.setColorRGBA_F(0F, 1F, 0F, 1.0F);
		            tessellator.addVertex(vec.xCoord, vec.yCoord, vec.zCoord);
		            tessellator.addVertex(0, 0, 0);
		            tessellator.draw();
	            }*/
	            
	            tessellator.startDrawing(3);
	            //tessellator.setColorRGBA_F(0F, 1F, 0F, 1.0F);
	            tessellator.setColorOpaque_I(hex);
	            tessellator.addVertex(vec.xCoord, vec.yCoord, vec.zCoord);
	        	vec.rotateAroundX(lRot);
	            tessellator.addVertex(vec.xCoord, vec.yCoord, vec.zCoord);
	            tessellator.draw();
	        }
        }

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glPopMatrix();
        
        generateSphere2(l, s, rad, hex);
	}
	
	private void generateSphere2(int l, int s, float rad, int hex) {

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LIGHTING);
        
        float sRot = (float)Math.PI * 2F / (float)(s);
        float lRot = (float)Math.PI / l;

        Vec3 vec2 = Vec3.createVectorHelper(0, rad, 0);
        
        for(int k = 0; k < l; k++) {
        	
        	vec2.rotateAroundZ(lRot);
        	
	        for(int i = 0; i < s; i++) {

	            Tessellator tessellator = Tessellator.instance;
	            tessellator.startDrawing(3);
	            //tessellator.setColorRGBA_F(0F, 1F, 0F, 1.0F);
	            tessellator.setColorOpaque_I(hex);
	            tessellator.addVertex(vec2.xCoord, vec2.yCoord, vec2.zCoord);
	        	vec2.rotateAroundY(sRot);
	            tessellator.addVertex(vec2.xCoord, vec2.yCoord, vec2.zCoord);
	            tessellator.draw();
	        }
        }

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glPopMatrix();
	}

}

package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderNukeN45 extends TileEntitySpecialRenderer {

    @Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f)
    {
    	boolean standing = tileEntity.getWorldObj().getBlock(tileEntity.xCoord, tileEntity.yCoord - 1, tileEntity.zCoord).isNormalCube();
    	
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
	    bindTexture(ResourceManager.universal);

        GL11.glPushMatrix();
        
        	//GL11.glScaled(2.0D, 2.0D, 2.0D);
        
        	if(standing) {
        		ResourceManager.n45_stand.renderAll();
        	}
        	
        	double d = /*0;*/ Math.sin((System.currentTimeMillis() % (1000* Math.PI)) / 500D) * 0.175 + 0.175;
        
            GL11.glTranslated(0, standing ? 1D : 0.5D, 0);

	        ResourceManager.n45_globe.renderAll();
	        
        	GL11.glRotated(90, 1, 0, 0);
        	
	        for(int i = 0; i < 8; i++) {
	            ResourceManager.n45_knob.renderAll();
	            GL11.glTranslated(0, -d, 0);
	            ResourceManager.n45_rod.renderAll();
	            GL11.glTranslated(0, d, 0);
	        	GL11.glRotated(45, 0, 0, 1);
	        }

        	GL11.glRotated(45, 0, 0, 1);
        	
	        for(int i = 0; i < 4; i++) {
	        	GL11.glRotated(-45, 1, 0, 0);
	            ResourceManager.n45_knob.renderAll();
	            GL11.glTranslated(0, -d, 0);
	            ResourceManager.n45_rod.renderAll();
	            GL11.glTranslated(0, d, 0);
	        	GL11.glRotated(45, 1, 0, 0);
	        	GL11.glRotated(90, 0, 0, 1);
	        }

        	GL11.glRotated(-90, 0, 0, 1);
        	
	        for(int i = 0; i < 4; i++) {
	        	GL11.glRotated(45, 1, 0, 0);
	            ResourceManager.n45_knob.renderAll();
	            GL11.glTranslated(0, -d, 0);
	            ResourceManager.n45_rod.renderAll();
	            GL11.glTranslated(0, d, 0);
	        	GL11.glRotated(-45, 1, 0, 0);
	        	GL11.glRotated(90, 0, 0, 1);
	        }

        	GL11.glRotated(45, 0, 0, 1);
        	GL11.glRotated(-90, 1, 0, 0);

            ResourceManager.n45_knob.renderAll();
            GL11.glTranslated(0, -d, 0);
            ResourceManager.n45_rod.renderAll();
            GL11.glTranslated(0, d, 0);
            
            if(!standing) {
            	int depth = 0;
            	
            	for(int i = 0; i < 51; i++) {
            		
            		if(!tileEntity.getWorldObj().getBlock(tileEntity.xCoord, tileEntity.yCoord - i - 1, tileEntity.zCoord).isNormalCube()) {
            			depth++;
            		} else {
            			break;
            		}
            	}
            	
            	if(depth != 0 && depth < 51) {

                    GL11.glTranslated(0, -1D, 0);
                    
                	for(int i = 0; i < depth + 1; i++) {

                        ResourceManager.n45_chain.renderAll();
                        GL11.glTranslated(0, -1, 0);
                	}
            	}
            }
	        
        GL11.glPopMatrix();
        
        GL11.glEnable(GL11.GL_CULL_FACE);

        GL11.glPopMatrix();
    }
}

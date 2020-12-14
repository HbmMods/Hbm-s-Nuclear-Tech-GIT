package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntitySolarMirror;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.EnumSkyBlock;

public class RenderSolarMirror extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float interp) {
		
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        
        TileEntitySolarMirror mirror = (TileEntitySolarMirror)te;

        bindTexture(ResourceManager.solar_mirror_tex);
        ResourceManager.solar_mirror.renderPart("Base");
        
        GL11.glTranslated(0, 1, 0);
        
    	int dx = mirror.tX - mirror.xCoord;
    	int dy = mirror.tY - mirror.yCoord;
    	int dz = mirror.tZ - mirror.zCoord;
    	
    	double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);
        
        if(mirror.tY >= mirror.yCoord) {

        	double pitch = Math.toDegrees(-Math.asin((dy + 0.5) / dist)) + 90;
        	double yaw = Math.toDegrees(-Math.atan2(dz, dx)) + 180;

        	GL11.glRotated(yaw, 0, 1, 0);
        	GL11.glRotated(pitch, 0, 0, 1);
        }
        
        GL11.glTranslated(0, -1, 0);
        ResourceManager.solar_mirror.renderPart("Mirror");
        
        if(mirror.isOn) {
	        
	        Tessellator tess = Tessellator.instance;
	        GL11.glDisable(GL11.GL_TEXTURE_2D);
	        GL11.glDisable(GL11.GL_LIGHTING);
	        GL11.glDisable(GL11.GL_CULL_FACE);
	        GL11.glEnable(GL11.GL_BLEND);
	        GL11.glShadeModel(GL11.GL_SMOOTH);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0);
	        GL11.glDepthMask(false);
	        
	        float min = 0.005F;
	        float max = 0.01F;
	        
	        tess.startDrawingQuads();
	        tess.setColorRGBA_F(1F, 1F, 1F, max);
	        tess.addVertex(0.5, 1.0625, 0.5);
	        tess.addVertex(0.5, 1.0625, -0.5);
	        tess.setColorRGBA_F(1F, 1F, 1F, min);
	        tess.addVertex(0.5, dist, -0.5);
	        tess.addVertex(0.5, dist, 0.5);
	
	        tess.setColorRGBA_F(1F, 1F, 1F, max);
	        tess.addVertex(-0.5, 1.0625, 0.5);
	        tess.addVertex(-0.5, 1.0625, -0.5);
	        tess.setColorRGBA_F(1F, 1F, 1F, min);
	        tess.addVertex(-0.5, dist, -0.5);
	        tess.addVertex(-0.5, dist, 0.5);
	
	        tess.setColorRGBA_F(1F, 1F, 1F, max);
	        tess.addVertex(0.5, 1.0625, 0.5);
	        tess.addVertex(-0.5, 1.0625, 0.5);
	        tess.setColorRGBA_F(1F, 1F, 1F, min);
	        tess.addVertex(-0.5, dist, 0.5);
	        tess.addVertex(0.5, dist, 0.5);
	
	        tess.setColorRGBA_F(1F, 1F, 1F, max);
	        tess.addVertex(0.5, 1.0625, -0.5);
	        tess.addVertex(-0.5, 1.0625, -0.5);
	        tess.setColorRGBA_F(1F, 1F, 1F, min);
	        tess.addVertex(-0.5, dist, -0.5);
	        tess.addVertex(0.5, dist, -0.5);
	        tess.draw();
	
	        GL11.glDepthMask(true);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
	        GL11.glShadeModel(GL11.GL_FLAT);
	        GL11.glDisable(GL11.GL_BLEND);
	        GL11.glEnable(GL11.GL_CULL_FACE);
	        GL11.glEnable(GL11.GL_LIGHTING);
	        GL11.glEnable(GL11.GL_TEXTURE_2D);
        }

        GL11.glPopMatrix();
	}

}

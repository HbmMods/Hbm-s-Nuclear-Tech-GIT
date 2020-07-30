package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.Library;
import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderRTG extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float inter) {
		
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);

        bindTexture(ResourceManager.rtg_tex);
        ResourceManager.rtg.renderPart("Gen");
        
        int ix = te.xCoord;
        int iy = te.yCoord;
        int iz = te.zCoord;

        if(Library.checkCableConnectables(te.getWorldObj(), ix + 1, iy, iz))
            ResourceManager.rtg.renderPart("Connector");
        
        if(Library.checkCableConnectables(te.getWorldObj(), ix - 1, iy, iz)) {
    		GL11.glRotatef(180, 0F, 1F, 0F);
            ResourceManager.rtg.renderPart("Connector");
    		GL11.glRotatef(-180, 0F, 1F, 0F);
        }
        
        if(Library.checkCableConnectables(te.getWorldObj(), ix, iy, iz - 1)) {
    		GL11.glRotatef(90, 0F, 1F, 0F);
            ResourceManager.rtg.renderPart("Connector");
    		GL11.glRotatef(-90, 0F, 1F, 0F);
        }
        
        if(Library.checkCableConnectables(te.getWorldObj(), ix, iy, iz + 1)) {
    		GL11.glRotatef(-90, 0F, 1F, 0F);
            ResourceManager.rtg.renderPart("Connector");
    		GL11.glRotatef(90, 0F, 1F, 0F);
        }

        GL11.glPopMatrix();
	}

}

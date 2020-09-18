package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderCyclotron extends TileEntitySpecialRenderer {

    @Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
    	
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glShadeModel(GL11.GL_SMOOTH);

        bindTexture(ResourceManager.cyclotron_tex);
        ResourceManager.cyclotron.renderPart("Body");
        
        GL11.glShadeModel(GL11.GL_FLAT);
        
        bindTexture(ResourceManager.cyclotron_ashes_filled);
        ResourceManager.cyclotron.renderPart("B1");
        bindTexture(ResourceManager.cyclotron_book_filled);
        ResourceManager.cyclotron.renderPart("B2");
        bindTexture(ResourceManager.cyclotron_gavel_filled);
        ResourceManager.cyclotron.renderPart("B3");
        bindTexture(ResourceManager.cyclotron_coin_filled);
        ResourceManager.cyclotron.renderPart("B4");
        
        GL11.glEnable(GL11.GL_CULL_FACE);

        GL11.glPopMatrix();
    }
}

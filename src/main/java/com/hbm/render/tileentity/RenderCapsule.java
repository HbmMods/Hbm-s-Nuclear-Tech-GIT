package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderCapsule extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float i) {
		
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5, y, z + 0.5);
        GL11.glEnable(GL11.GL_LIGHTING);

        GL11.glTranslatef(0.0F, -0.25F, 0.0F);
        GL11.glRotatef(-25, 0, 1, 0);
        GL11.glRotatef(15, 0, 0, 1);
        
        if(te.getBlockMetadata() == 3)
        	bindTexture(ResourceManager.soyuz_lander_rust_tex);
        else
        	bindTexture(ResourceManager.soyuz_lander_tex);
        
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        ResourceManager.soyuz_lander.renderPart("Capsule");
        GL11.glShadeModel(GL11.GL_FLAT);
        
        GL11.glPopMatrix();
	}

}

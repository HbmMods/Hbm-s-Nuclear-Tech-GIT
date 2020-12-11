package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderSolarBoiler extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float interp) {
		
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        
        GL11.glRotatef(90, 0F, 1F, 0F);
        
		switch(te.getBlockMetadata() - BlockDummyable.offset)
		{
		case 2: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(270, 0F, 1F, 0F); break;
		}

        bindTexture(ResourceManager.solar_tex);
        
        GL11.glShadeModel(GL11.GL_SMOOTH);
        ResourceManager.solar_boiler.renderPart("Base");
        GL11.glShadeModel(GL11.GL_FLAT);

        GL11.glPopMatrix();
	}

}

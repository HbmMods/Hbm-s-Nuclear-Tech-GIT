package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityMachineLargeTurbine;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderBigTurbine extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
		
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
        
        GL11.glRotatef(90, 0F, 1F, 0F);

		switch(tile.getBlockMetadata() - BlockDummyable.offset)
		{
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}
		
        GL11.glTranslated(0, 0, -1);

        bindTexture(ResourceManager.turbine_tex);
        
        GL11.glShadeModel(GL11.GL_SMOOTH);
        ResourceManager.turbine.renderPart("Body");
        GL11.glShadeModel(GL11.GL_FLAT);
        
        TileEntityMachineLargeTurbine turbine = (TileEntityMachineLargeTurbine) tile;

        GL11.glTranslated(0, 1, 0);
        GL11.glRotatef(turbine.lastRotor + (turbine.rotor - turbine.lastRotor) * f, 0, 0, 1);
        GL11.glTranslated(0, -1, 0);
        
        bindTexture(ResourceManager.universal_bright);
        ResourceManager.turbine.renderPart("Blades");
        
        GL11.glEnable(GL11.GL_CULL_FACE);

        GL11.glPopMatrix();

	}
}

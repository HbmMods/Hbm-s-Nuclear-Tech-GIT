package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityMachineCrystallizer;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderCrystallizer extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float inter) {
		
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
		
		switch(te.getBlockMetadata() - 10) {
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}
		
		TileEntityMachineCrystallizer crys = (TileEntityMachineCrystallizer)te;

		GL11.glShadeModel(GL11.GL_SMOOTH);
        bindTexture(ResourceManager.crystallizer_tex);
        ResourceManager.crystallizer.renderPart("Body");
        bindTexture(ResourceManager.crystallizer_window_tex);
        ResourceManager.crystallizer.renderPart("Windows");
        
        GL11.glPushMatrix();
        GL11.glRotatef(crys.prevAngle + (crys.angle - crys.prevAngle) * inter, 0, 1, 0);
        bindTexture(ResourceManager.crystallizer_spinner_tex);
        ResourceManager.crystallizer.renderPart("Spinner");
        GL11.glPopMatrix();
        
		GL11.glShadeModel(GL11.GL_FLAT);

        GL11.glPopMatrix();
	}

}

package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityMachineSeleniumEngine;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderSelenium extends TileEntitySpecialRenderer {

    @Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);
		GL11.glRotatef(-90, 0F, 1F, 0F);
		
		switch(tileEntity.getBlockMetadata())
		{
		case 2:
			GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4:
			GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3:
			GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5:
			GL11.glRotatef(0, 0F, 1F, 0F); break;
		}

        bindTexture(ResourceManager.selenium_body_tex);
        ResourceManager.selenium_body.renderAll();
        
        GL11.glTranslated(0.0D, 1.0D, 0.0D);
        
        int count = ((TileEntityMachineSeleniumEngine)tileEntity).pistonCount;
        
        float rot = 360F / count;

        bindTexture(ResourceManager.selenium_piston_tex);
        for(int i = 0; i < count; i++) {
            ResourceManager.selenium_piston.renderAll();
    		GL11.glRotatef(rot, 0, 0, 1);
        }

        bindTexture(ResourceManager.selenium_rotor_tex);
        ResourceManager.selenium_rotor.renderAll();

        GL11.glPopMatrix();
    }
}

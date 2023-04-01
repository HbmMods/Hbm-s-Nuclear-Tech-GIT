package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.bomb.TileEntityLaunchPad;
import com.hbm.tileentity.bomb.TileEntityLaunchPadPassenger;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderLaunchPadTier2 extends TileEntitySpecialRenderer {

    @Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
        
        bindTexture(ResourceManager.missile_pad_tex);
        ResourceManager.missile_pad.renderAll();

        GL11.glDisable(GL11.GL_CULL_FACE);
        int state = 0;
        
        if(tileEntity instanceof TileEntityLaunchPadPassenger)
        	state = ((TileEntityLaunchPadPassenger)tileEntity).state;
        
	        GL11.glTranslated(0, 1, 0);
	        
			if(state == 20)
			{
		        GL11.glScalef(2F, 2F, 2F);
				bindTexture(ResourceManager.missileCarrier_tex);
				ResourceManager.missileCarrier.renderAll();
		        GL11.glTranslated(0.0D, 0.5D, 0.0D);
		        GL11.glTranslated(1.25D, 0.0D, 0.0D);
				bindTexture(ResourceManager.missileBooster_tex);
				ResourceManager.missileBooster.renderAll();
		        GL11.glTranslated(-2.5D, 0.0D, 0.0D);
				ResourceManager.missileBooster.renderAll();
		        GL11.glTranslated(1.25D, 0.0D, 0.0D);
		        GL11.glTranslated(0.0D, 0.0D, 1.25D);
				ResourceManager.missileBooster.renderAll();
		        GL11.glTranslated(0.0D, 0.0D, -2.5D);
				ResourceManager.missileBooster.renderAll();
		        GL11.glTranslated(0.0D, 0.0D, 1.25D);
			}

			
	        GL11.glEnable(GL11.GL_CULL_FACE);

        GL11.glPopMatrix();
    }

}

package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.MainRegistry;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityMachineReactorSmall;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderVaultDoor extends TileEntitySpecialRenderer {

    @Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);

        bindTexture(ResourceManager.vault_frame_tex);
        ResourceManager.vault_frame.renderAll();
        GL11.glTranslated(-1.0D, 0.0D, 0.0D);
        bindTexture(ResourceManager.universal);
        ResourceManager.vault_teeth.renderAll();
        GL11.glTranslated(1.0D, 0.0D, 0.0D);


        GL11.glTranslated(0.0D, -2.5D, 0.0D);
        
        double[] timer = getAnimationFromSysTime(MainRegistry.time);

        GL11.glTranslated(-timer[0], 0, timer[1]);
        
        GL11.glTranslated(0.0D, 5D, 0.0D);
        GL11.glRotated(timer[2], 1, 0, 0);
        GL11.glTranslated(0.0D, -2.5D, 0.0D);
        
        bindTexture(ResourceManager.vault_cog_tex);
        ResourceManager.vault_cog.renderAll();

        GL11.glPopMatrix();
    }
    
    //x, z, roll
    private static double[] getAnimationFromSysTime(long time) {
    	
    	double pullOutDuration = 5000D;
    	double slideDuration = 5000D;
    	
    	double diameter = 4.5D;
    	double circumference = diameter * Math.PI;
    	
    	double x = (System.currentTimeMillis() - time) / pullOutDuration;
    	
    	if(x > 1)
    		x = 1;
    	
    	double z = (System.currentTimeMillis() - time - pullOutDuration) / slideDuration;
    	
    	if(System.currentTimeMillis() - time < pullOutDuration)
    		z = 0;
    	
    	if(z > 5)
    		z = 5;
    	
    	double roll = z / circumference * 360;
    	
    	return new double[] { x, z, roll };
    }
}

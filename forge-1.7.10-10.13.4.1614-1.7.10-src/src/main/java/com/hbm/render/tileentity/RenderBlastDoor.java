package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityBlastDoor;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderBlastDoor extends TileEntitySpecialRenderer {

    @Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f)
    {
    	renderDoor((TileEntityBlastDoor)tileEntity, x, y, z, f);
    }

	public void renderDoor(TileEntityBlastDoor tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);

        double timer;
        
        if(tileEntity.getBlockMetadata() == 2)
    		GL11.glRotatef(90, 0F, 1F, 0F);
        
        if(tileEntity.state == 0)
        	timer = getAnimationFromSysTime(5000);
        else if(tileEntity.state == 2)
        	timer = 0;
        else if(tileEntity.isOpening)
        	timer = getAnimationFromSysTime(tileEntity.sysTime + 5000 - System.currentTimeMillis());
        else
        	timer = getAnimationFromSysTime(System.currentTimeMillis() - tileEntity.sysTime);

        bindTexture(ResourceManager.blast_door_base_tex);
        ResourceManager.blast_door_base.renderAll();

        GL11.glTranslated(0, 3, 0);
        bindTexture(ResourceManager.blast_door_block_tex);
        ResourceManager.blast_door_block.renderAll();
        
        GL11.glTranslated(0, -timer, 0);

        GL11.glTranslated(0, 2, 0);
        bindTexture(ResourceManager.blast_door_tooth_tex);
        ResourceManager.blast_door_tooth.renderAll();

        if(timer > 1D) {
            bindTexture(ResourceManager.blast_door_slider_tex);
            ResourceManager.blast_door_slider.renderAll();
        }
        if(timer > 2D) {
            GL11.glTranslated(0, 1, 0);
            bindTexture(ResourceManager.blast_door_slider_tex);
            ResourceManager.blast_door_slider.renderAll();
        }
        if(timer > 3D) {
            GL11.glTranslated(0, 1, 0);
            bindTexture(ResourceManager.blast_door_slider_tex);
            ResourceManager.blast_door_slider.renderAll();
        }
        if(timer > 4D) {
            GL11.glTranslated(0, 1, 0);
            bindTexture(ResourceManager.blast_door_slider_tex);
            ResourceManager.blast_door_slider.renderAll();
        }

        GL11.glPopMatrix();
    }
	
    private static double getAnimationFromSysTime(long time) {
    	
    	double duration = 5000D;
    	double extend = 5.0D;
    	
    	return Math.max(Math.min(time, duration) / duration * extend, 0.0D);
    }
}

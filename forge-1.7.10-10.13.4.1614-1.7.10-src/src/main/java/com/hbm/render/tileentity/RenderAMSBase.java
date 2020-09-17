package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityAMSBase;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderAMSBase extends TileEntitySpecialRenderer {
	
	public RenderAMSBase() { }

    @Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);

        bindTexture(ResourceManager.ams_base_tex);
        
        ResourceManager.ams_base.renderAll();

        GL11.glPopMatrix();

		TileEntityAMSBase base = (TileEntityAMSBase)tileEntity;
		if(base.color > -1)
			renderTileEntityAt2(tileEntity, x, y, z, f);
    }

	public void renderTileEntityAt2(TileEntity tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y + 5.5, z + 0.5D);
        //GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);
		
		int rot = (int) ((System.nanoTime()/100000000)%360) * 3;
		
		TileEntityAMSBase base = (TileEntityAMSBase)tileEntity;
		
		double maxSize = 5;
		double minSize = 0.5;
		double scale = minSize;
		scale += ((((double)base.tanks[2].getFill()) / ((double)base.tanks[2].getMaxFill())) + (((double)base.tanks[3].getFill()) / ((double)base.tanks[3].getMaxFill()))) * ((maxSize - minSize) / 2);
		GL11.glScaled(scale, scale, scale);

		//bindTexture(new ResourceLocation(RefStrings.MODID, "textures/models/EMPBlast.png"));
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		GL11.glRotatef(rot, 0F, 1F, 0F);
		GL11.glScalef(1.1F, 1.1F, 1.1F);
		GL11.glColor3ub((byte)(0x20), (byte)(0x20), (byte)(0x40));
		ResourceManager.sphere_iuv.renderAll();
		GL11.glScalef(1/1.1F, 1/1.1F, 1/1.1F);

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glPopMatrix();
        
        renderTileEntityAt3(tileEntity, x, y, z, f);
    }

	public void renderTileEntityAt3(TileEntity tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y + 5.5, z + 0.5D);
        //GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);
		
		TileEntityAMSBase base = (TileEntityAMSBase)tileEntity;
		
		double maxSize = 5;
		double minSize = 0.5;
		double scale = minSize;
		scale += ((((double)base.tanks[2].getFill()) / ((double)base.tanks[2].getMaxFill())) + (((double)base.tanks[3].getFill()) / ((double)base.tanks[3].getMaxFill()))) * ((maxSize - minSize) / 2);
		GL11.glScaled(scale, scale, scale);
		
		GL11.glColor3ub((byte)((base.color & 0xFF0000) >> 16), (byte)((base.color & 0x00FF00) >> 8), (byte)((base.color & 0x0000FF) >> 0));
        
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		int rot = (int) ((System.nanoTime()/100000000)%360) * 3;

			GL11.glRotatef(rot, 0F, 1F, 0F);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			ResourceManager.sphere_ruv.renderAll();
			GL11.glScalef(1/0.5F, 1/0.5F, 1/0.5F);
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

			GL11.glRotatef(-rot * 2, 0F, 1F, 0F);
			GL11.glScalef(0.6F, 0.6F, 0.6F);
			ResourceManager.sphere_ruv.renderAll();
			GL11.glScalef(1/0.6F, 1/0.6F, 1/0.6F);
		
			GL11.glRotatef(rot * 2, 0F, 1F, 0F);
			GL11.glScalef(0.7F, 0.7F, 0.7F);
			ResourceManager.sphere_ruv.renderAll();
			GL11.glScalef(1/0.7F, 1/0.7F, 1/0.7F);
		
			GL11.glRotatef(-rot * 2, 0F, 1F, 0F);
			GL11.glScalef(0.8F, 0.8F, 0.8F);
			ResourceManager.sphere_ruv.renderAll();
			GL11.glScalef(1/0.8F, 1/0.8F, 1/0.8F);
		
			GL11.glRotatef(rot * 2, 0F, 1F, 0F);
			GL11.glScalef(0.9F, 0.9F, 0.9F);
			ResourceManager.sphere_ruv.renderAll();
			GL11.glScalef(1/0.9F, 1/0.9F, 1/0.9F);

			GL11.glRotatef(-rot * 2, 0F, 1F, 0F);
			ResourceManager.sphere_ruv.renderAll();
        
		GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        GL11.glPopMatrix();
    }
}

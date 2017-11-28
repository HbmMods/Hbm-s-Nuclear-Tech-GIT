package com.hbm.render.tileentity;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityAMSBase;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderAMSBase extends TileEntitySpecialRenderer {
	
	public RenderAMSBase() { }
	Random rand = new Random();

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
        
        renderTileEntityAt2(tileEntity, x, y, z, f);
    }

	public void renderTileEntityAt3(TileEntity tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y + 5.5, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);
		GL11.glScalef(2.5F, 2.5F, 2.5F);
		
		GL11.glColor3ub((byte)((((TileEntityAMSBase)tileEntity).color & 0xFF0000) >> 16), (byte)((((TileEntityAMSBase)tileEntity).color & 0x00FF00) >> 8), (byte)((((TileEntityAMSBase)tileEntity).color & 0x0000FF) >> 0));
        
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
		GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        GL11.glPopMatrix();
        
        renderTileEntityAt4(tileEntity, x, y, z, f);
    }

	public void renderTileEntityAt2(TileEntity tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y + 5.5, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);
		GL11.glScalef(2.5F, 2.5F, 2.5F);
		
		int rot = (int) ((System.nanoTime()/100000000)%360) * 3;

		bindTexture(new ResourceLocation(RefStrings.MODID, "textures/models/EMPBlast.png"));
		
		GL11.glRotatef(rot, 0F, 1F, 0F);
		GL11.glScalef(1.1F, 1.1F, 1.1F);
		ResourceManager.sphere_iuv.renderAll();
		GL11.glScalef(1/1.1F, 1/1.1F, 1/1.1F);

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glPopMatrix();
        
        renderTileEntityAt3(tileEntity, x, y, z, f);
    }

	public void renderTileEntityAt4(TileEntity tileEntity, double x, double y, double z, float f)
    {
		float radius = 0.04F;
		int distance = 1;
		int layers = 3;
		Tessellator tessellator = Tessellator.instance;

		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GL11.glTranslatef((float) x + 0.5F, (float) y + 2, (float) z + 0.5F);

		double lastPosX = 0;
		double lastPosZ = 0;

		for(int i = 7; i > 0; i -= distance) {
			
			double posX = rand.nextDouble() - 0.5;
			double posZ = rand.nextDouble() - 0.5;
			
			for(int j = 1; j <= layers; j++) {

				tessellator.startDrawingQuads();
				tessellator.setColorRGBA_F(1, 0.5F, 0, 1f);
				tessellator.addVertex(lastPosX + (radius * j), i, lastPosZ + (radius * j));
				tessellator.addVertex(lastPosX + (radius * j), i, lastPosZ - (radius * j));
				tessellator.addVertex(posX + (radius * j), i - distance, posZ - (radius * j));
				tessellator.addVertex(posX + (radius * j), i - distance, posZ + (radius * j));
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setColorRGBA_F(1, 0.5F, 0, 1f);
				tessellator.addVertex(lastPosX - (radius * j), i, lastPosZ + (radius * j));
				tessellator.addVertex(lastPosX - (radius * j), i, lastPosZ - (radius * j));
				tessellator.addVertex(posX - (radius * j), i - distance, posZ - (radius * j));
				tessellator.addVertex(posX - (radius * j), i - distance, posZ + (radius * j));
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setColorRGBA_F(1, 0.5F, 0, 1f);
				tessellator.addVertex(lastPosX + (radius * j), i, lastPosZ + (radius * j));
				tessellator.addVertex(lastPosX - (radius * j), i, lastPosZ + (radius * j));
				tessellator.addVertex(posX - (radius * j), i - distance, posZ + (radius * j));
				tessellator.addVertex(posX + (radius * j), i - distance, posZ + (radius * j));
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setColorRGBA_F(1, 0.5F, 0, 1f);
				tessellator.addVertex(lastPosX + (radius * j), i, lastPosZ - (radius * j));
				tessellator.addVertex(lastPosX - (radius * j), i, lastPosZ - (radius * j));
				tessellator.addVertex(posX - (radius * j), i - distance, posZ - (radius * j));
				tessellator.addVertex(posX + (radius * j), i - distance, posZ - (radius * j));
				tessellator.draw();
			}

			lastPosX = posX;
			lastPosZ = posZ;
		}
		
		for(int j = 1; j <= 2; j++) {

			tessellator.startDrawingQuads();
			tessellator.setColorRGBA_F(1, 1, 0, 1f);
			tessellator.addVertex(0 + (radius * j), 7, 0 + (radius * j));
			tessellator.addVertex(0 + (radius * j), 7, 0 - (radius * j));
			tessellator.addVertex(0 + (radius * j), 0, 0 - (radius * j));
			tessellator.addVertex(0 + (radius * j), 0, 0 + (radius * j));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setColorRGBA_F(1, 1, 0, 1f);
			tessellator.addVertex(0 - (radius * j), 7, 0 + (radius * j));
			tessellator.addVertex(0 - (radius * j), 7, 0 - (radius * j));
			tessellator.addVertex(0 - (radius * j), 0, 0 - (radius * j));
			tessellator.addVertex(0 - (radius * j), 0, 0 + (radius * j));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setColorRGBA_F(1, 1, 0, 1f);
			tessellator.addVertex(0 + (radius * j), 7, 0 + (radius * j));
			tessellator.addVertex(0 - (radius * j), 7, 0 + (radius * j));
			tessellator.addVertex(0 - (radius * j), 0, 0 + (radius * j));
			tessellator.addVertex(0 + (radius * j), 0, 0 + (radius * j));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setColorRGBA_F(1, 1, 0, 1f);
			tessellator.addVertex(0 + (radius * j), 7, 0 - (radius * j));
			tessellator.addVertex(0 - (radius * j), 7, 0 - (radius * j));
			tessellator.addVertex(0 - (radius * j), 0, 0 - (radius * j));
			tessellator.addVertex(0 + (radius * j), 0, 0 - (radius * j));
			tessellator.draw();
		}
		
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();
    }
}

package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityAMSBase;
import com.hbm.tileentity.machine.TileEntityAMSLimiter;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderAMSLimiter extends TileEntitySpecialRenderer {
	
	public RenderAMSLimiter() { }

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

        bindTexture(ResourceManager.ams_limiter_tex);
        
        if(((TileEntityAMSLimiter)tileEntity).locked)
            ResourceManager.ams_limiter_destroyed.renderAll();
        else
        	ResourceManager.ams_limiter.renderAll();

        GL11.glPopMatrix();
        renderTileEntityAt2(tileEntity, x, y, z, f);
    }

	public void renderTileEntityAt2(TileEntity tileEntity, double x, double y, double z, float f)
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

       // bindTexture(ResourceManager.universal);
        
        TileEntityAMSLimiter limiter = (TileEntityAMSLimiter)tileEntity;

        int meta = tileEntity.getBlockMetadata();
        boolean flag = false;
		double maxSize = 5;
		double minSize = 0.5;
        double rad = maxSize;
        if(meta == 2 && tileEntity.getWorldObj().getTileEntity(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord - 6) instanceof TileEntityAMSBase && !limiter.locked) {
        	flag = true;
        	TileEntityAMSBase base = (TileEntityAMSBase)tileEntity.getWorldObj().getTileEntity(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord - 6);
        	maxSize += ((((double)base.tanks[2].getFill()) / ((double)base.tanks[2].getMaxFill())) + (((double)base.tanks[3].getFill()) / ((double)base.tanks[3].getMaxFill()))) * ((maxSize - minSize) / 2);
        }
        if(meta == 3 && tileEntity.getWorldObj().getTileEntity(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord + 6) instanceof TileEntityAMSBase && !limiter.locked) {
        	flag = true;
        	TileEntityAMSBase base = (TileEntityAMSBase)tileEntity.getWorldObj().getTileEntity(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord + 6);
        	maxSize += ((((double)base.tanks[2].getFill()) / ((double)base.tanks[2].getMaxFill())) + (((double)base.tanks[3].getFill()) / ((double)base.tanks[3].getMaxFill()))) * ((maxSize - minSize) / 2);
        }
        if(meta == 4 && tileEntity.getWorldObj().getTileEntity(tileEntity.xCoord - 6, tileEntity.yCoord, tileEntity.zCoord) instanceof TileEntityAMSBase && !limiter.locked) {
        	flag = true;
        	TileEntityAMSBase base = (TileEntityAMSBase)tileEntity.getWorldObj().getTileEntity(tileEntity.xCoord - 6, tileEntity.yCoord, tileEntity.zCoord);
        	maxSize += ((((double)base.tanks[2].getFill()) / ((double)base.tanks[2].getMaxFill())) + (((double)base.tanks[3].getFill()) / ((double)base.tanks[3].getMaxFill()))) * ((maxSize - minSize) / 2);
        }
        if(meta == 5 && tileEntity.getWorldObj().getTileEntity(tileEntity.xCoord + 6, tileEntity.yCoord, tileEntity.zCoord) instanceof TileEntityAMSBase && !limiter.locked) {
        	flag = true;
        	TileEntityAMSBase base = (TileEntityAMSBase)tileEntity.getWorldObj().getTileEntity(tileEntity.xCoord + 6, tileEntity.yCoord, tileEntity.zCoord);
        	maxSize += ((((double)base.tanks[2].getFill()) / ((double)base.tanks[2].getMaxFill())) + (((double)base.tanks[3].getFill()) / ((double)base.tanks[3].getMaxFill()))) * ((maxSize - minSize) / 2);
        }
        
        if(flag) {
        	
			GL11.glRotatef(-90, 0F, 1F, 0F);
        	
        	double posX = 0;
        	double posY = 0;
        	double posZ = 0;
        	double length = 4;
        	double radius = 0.12;
            GL11.glTranslated(2.5, 5.5, 0);

    		RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glShadeModel(GL11.GL_SMOOTH);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
	        GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glDepthMask(false);
        	Tessellator tessellator = Tessellator.instance;
			tessellator.startDrawingQuads();
			tessellator.setColorRGBA_F(0, 0, 1F, 0f);
			tessellator.addVertex(posX + length, posY - radius, posZ - radius);
			tessellator.addVertex(posX + length, posY - radius, posZ + radius);
			tessellator.setColorRGBA_F(0, 0, 1F, 1f);
			tessellator.addVertex(posX, posY - radius, posZ + radius);
			tessellator.addVertex(posX, posY - radius, posZ - radius);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setColorRGBA_F(0, 0, 1F, 0f);
			tessellator.addVertex(posX + length, posY + radius, posZ + radius);
			tessellator.addVertex(posX + length, posY + radius, posZ - radius);
			tessellator.setColorRGBA_F(0, 0, 1F, 1f);
			tessellator.addVertex(posX, posY + radius, posZ - radius);
			tessellator.addVertex(posX, posY + radius, posZ + radius);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setColorRGBA_F(0, 0, 1F, 0f);
			tessellator.addVertex(posX + length, posY - radius, posZ - radius);
			tessellator.addVertex(posX + length, posY + radius, posZ - radius);
			tessellator.setColorRGBA_F(0, 0, 1F, 1f);
			tessellator.addVertex(posX, posY + radius, posZ - radius);
			tessellator.addVertex(posX, posY - radius, posZ - radius);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setColorRGBA_F(0, 0, 1F, 0f);
			tessellator.addVertex(posX + length, posY - radius, posZ + radius);
			tessellator.addVertex(posX + length, posY + radius, posZ + radius);
			tessellator.setColorRGBA_F(0, 0, 1F, 1f);
			tessellator.addVertex(posX, posY + radius, posZ + radius);
			tessellator.addVertex(posX, posY - radius, posZ + radius);
			tessellator.draw();
	        
	        if(limiter.power > 0) {

	        	radius *= 2;
	    		RenderHelper.disableStandardItemLighting();
	            GL11.glDisable(GL11.GL_TEXTURE_2D);
	            GL11.glShadeModel(GL11.GL_SMOOTH);
	            GL11.glEnable(GL11.GL_BLEND);
	            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
	            GL11.glDisable(GL11.GL_ALPHA_TEST);
		        GL11.glDisable(GL11.GL_CULL_FACE);
	            GL11.glDepthMask(false);
				tessellator.startDrawingQuads();
				tessellator.setColorRGBA_F(0, 0, 1F, 0f);
				tessellator.addVertex(posX + length, posY - radius, posZ - radius);
				tessellator.addVertex(posX + length, posY - radius, posZ + radius);
				tessellator.setColorRGBA_F(0, 0, 1F, 1f);
				tessellator.addVertex(posX, posY - radius, posZ + radius);
				tessellator.addVertex(posX, posY - radius, posZ - radius);
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setColorRGBA_F(0, 0, 1F, 0f);
				tessellator.addVertex(posX + length, posY + radius, posZ + radius);
				tessellator.addVertex(posX + length, posY + radius, posZ - radius);
				tessellator.setColorRGBA_F(0, 0, 1F, 1f);
				tessellator.addVertex(posX, posY + radius, posZ - radius);
				tessellator.addVertex(posX, posY + radius, posZ + radius);
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setColorRGBA_F(0, 0, 1F, 0f);
				tessellator.addVertex(posX + length, posY - radius, posZ - radius);
				tessellator.addVertex(posX + length, posY + radius, posZ - radius);
				tessellator.setColorRGBA_F(0, 0, 1F, 1f);
				tessellator.addVertex(posX, posY + radius, posZ - radius);
				tessellator.addVertex(posX, posY - radius, posZ - radius);
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setColorRGBA_F(0, 0, 1F, 0f);
				tessellator.addVertex(posX + length, posY - radius, posZ + radius);
				tessellator.addVertex(posX + length, posY + radius, posZ + radius);
				tessellator.setColorRGBA_F(0, 0, 1F, 1f);
				tessellator.addVertex(posX, posY + radius, posZ + radius);
				tessellator.addVertex(posX, posY - radius, posZ + radius);
				tessellator.draw();
				
	        	/*double iRadiusB = 0.3;
	        	double oRadiusB = 2;
	        	double iRadiusS = iRadiusB * 0.75;
	        	double oRadiusS = oRadiusB * 0.60;
	        	double bLength = rad;
	        	
	    		int rot = (int) ((System.nanoTime()/100000000)%360) * 3;

				GL11.glRotatef(rot, 1F, 0F, 0F);

				tessellator.startDrawingQuads();
				tessellator.setColorRGBA_F(0, 0, 1F, 0f);
				tessellator.addVertex(posX + bLength, posY - oRadiusB, posZ - oRadiusS);
				tessellator.addVertex(posX + bLength, posY - oRadiusB, posZ + oRadiusS);
				tessellator.setColorRGBA_F(0, 0, 1F, 1f);
				tessellator.addVertex(posX, posY - iRadiusB, posZ + iRadiusS);
				tessellator.addVertex(posX, posY - iRadiusB, posZ - iRadiusS);
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setColorRGBA_F(0, 0, 1F, 0f);
				tessellator.addVertex(posX + bLength, posY + oRadiusB, posZ - oRadiusS);
				tessellator.addVertex(posX + bLength, posY + oRadiusB, posZ + oRadiusS);
				tessellator.setColorRGBA_F(0, 0, 1F, 1f);
				tessellator.addVertex(posX, posY + iRadiusB, posZ + iRadiusS);
				tessellator.addVertex(posX, posY + iRadiusB, posZ - iRadiusS);
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setColorRGBA_F(0, 0, 1F, 0f);
				tessellator.addVertex(posX + bLength, posY - oRadiusS, posZ - oRadiusB);
				tessellator.addVertex(posX + bLength, posY + oRadiusS, posZ - oRadiusB);
				tessellator.setColorRGBA_F(0, 0, 1F, 1f);
				tessellator.addVertex(posX, posY + iRadiusS, posZ - iRadiusB);
				tessellator.addVertex(posX, posY - iRadiusS, posZ - iRadiusB);
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setColorRGBA_F(0, 0, 1F, 0f);
				tessellator.addVertex(posX + bLength, posY - oRadiusS, posZ + oRadiusB);
				tessellator.addVertex(posX + bLength, posY + oRadiusS, posZ + oRadiusB);
				tessellator.setColorRGBA_F(0, 0, 1F, 1f);
				tessellator.addVertex(posX, posY + iRadiusS, posZ + iRadiusB);
				tessellator.addVertex(posX, posY - iRadiusS, posZ + iRadiusB);
				tessellator.draw();
				
	        	iRadiusB *= 0.60;
	        	oRadiusB *= 0.60;
	        	iRadiusS = iRadiusB * 0.75;
	        	oRadiusS = oRadiusB * 0.60;
	        	bLength = 2.5;

				GL11.glRotatef(-2 * rot, 1F, 0F, 0F);

				tessellator.startDrawingQuads();
				tessellator.setColorRGBA_F(0.25F, 0.25F, 1F, 1f);
				tessellator.addVertex(posX + bLength, posY - oRadiusB, posZ - oRadiusS);
				tessellator.addVertex(posX + bLength, posY - oRadiusB, posZ + oRadiusS);
				tessellator.addVertex(posX, posY - iRadiusB, posZ + iRadiusS);
				tessellator.addVertex(posX, posY - iRadiusB, posZ - iRadiusS);
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setColorRGBA_F(0.25F, 0.25F, 1F, 1f);
				tessellator.addVertex(posX + bLength, posY + oRadiusB, posZ - oRadiusS);
				tessellator.addVertex(posX + bLength, posY + oRadiusB, posZ + oRadiusS);
				tessellator.addVertex(posX, posY + iRadiusB, posZ + iRadiusS);
				tessellator.addVertex(posX, posY + iRadiusB, posZ - iRadiusS);
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setColorRGBA_F(0.25F, 0.25F, 1F, 1f);
				tessellator.addVertex(posX + bLength, posY - oRadiusS, posZ - oRadiusB);
				tessellator.addVertex(posX + bLength, posY + oRadiusS, posZ - oRadiusB);
				tessellator.addVertex(posX, posY + iRadiusS, posZ - iRadiusB);
				tessellator.addVertex(posX, posY - iRadiusS, posZ - iRadiusB);
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setColorRGBA_F(0.25F, 0.25F, 1F, 1f);
				tessellator.addVertex(posX + bLength, posY - oRadiusS, posZ + oRadiusB);
				tessellator.addVertex(posX + bLength, posY + oRadiusS, posZ + oRadiusB);
				tessellator.addVertex(posX, posY + iRadiusS, posZ + iRadiusB);
				tessellator.addVertex(posX, posY - iRadiusS, posZ + iRadiusB);
				tessellator.draw();*/
	        }

			
	        GL11.glDisable(GL11.GL_BLEND);
	        GL11.glEnable(GL11.GL_ALPHA_TEST);
	        GL11.glShadeModel(GL11.GL_FLAT);
	        RenderHelper.enableStandardItemLighting();
	        GL11.glDepthMask(true);
            GL11.glEnable(GL11.GL_CULL_FACE);
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        GL11.glEnable(GL11.GL_TEXTURE_2D);
        }

        GL11.glPopMatrix();
    }
}

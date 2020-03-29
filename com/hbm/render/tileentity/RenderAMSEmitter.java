package com.hbm.render.tileentity;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityAMSBase;
import com.hbm.tileentity.machine.TileEntityAMSEmitter;
import com.hbm.tileentity.machine.TileEntityAMSLimiter;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderAMSEmitter extends TileEntitySpecialRenderer {
	
	public RenderAMSEmitter() { }
	Random rand = new Random();

    @Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);


        if(((TileEntityAMSEmitter)tileEntity).locked)
        	bindTexture(ResourceManager.ams_destroyed_tex);
        else
        	bindTexture(ResourceManager.ams_emitter_tex);

        if(((TileEntityAMSEmitter)tileEntity).locked)
            ResourceManager.ams_emitter_destroyed.renderAll();
        else
        	ResourceManager.ams_emitter.renderAll();

        GL11.glPopMatrix();
        renderTileEntityAt2(tileEntity, x, y, z, f);
    }

	public void renderTileEntityAt2(TileEntity tileEntity, double x, double y, double z, float f)
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
		GL11.glTranslatef((float) x + 0.5F, (float) y - 7, (float) z + 0.5F);

		TileEntityAMSEmitter emitter = (TileEntityAMSEmitter)tileEntity;
		
		if(emitter.getWorldObj().getTileEntity(emitter.xCoord, emitter.yCoord - 9, emitter.zCoord) instanceof TileEntityAMSBase && !emitter.locked || true) {
		
			if(emitter.efficiency > 0 || true) {
				
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
		}
		
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();
    }
}

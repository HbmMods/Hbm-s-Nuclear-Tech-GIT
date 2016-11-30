package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelSatelliteReceiver;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderPoleSatelliteReceiver extends TileEntitySpecialRenderer {

	private static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":" + "textures/models/PoleSatelliteReceiver.png");
	
	private ModelSatelliteReceiver model;
	
	public RenderPoleSatelliteReceiver() {
		this.model = new ModelSatelliteReceiver();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
			GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
			GL11.glRotatef(180, 0F, 0F, 1F);
			
			switch(tileentity.getBlockMetadata())
			{
			case 5:
				GL11.glRotatef(90, 0F, 1F, 0F); break;
			case 3:
				GL11.glRotatef(180, 0F, 1F, 0F); break;
			case 4:
				GL11.glRotatef(270, 0F, 1F, 0F); break;
			case 2:
				GL11.glRotatef(0, 0F, 1F, 0F); break;
			}
			
			this.bindTexture(texture);
			
			GL11.glPushMatrix();
				this.model.renderModel(0.0625F);
			GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

}

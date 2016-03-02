package com.hbm.render;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

public class RenderCable extends TileEntitySpecialRenderer {
	
	public ResourceLocation texture = new ResourceLocation(RefStrings.MODID, "textures/blocks/red_cable.png");
	float pixel = 1F/16F;
	float textureP = 1F / 32F;

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double offsetX, double offsetY, double offsetZ, float f) {
		GL11.glTranslated(offsetX, offsetY, offsetZ);
		GL11.glDisable(GL11.GL_LIGHTING);
		this.bindTexture(texture);
		drawCore(tileentity);
		drawConnection(ForgeDirection.UP);
		GL11.glTranslated(-offsetX, -offsetY, -offsetZ);
		GL11.glEnable(GL11.GL_LIGHTING);

	}
	
	public void drawCore(TileEntity tileentity) {
		Tessellator tesseract = Tessellator.instance;
		tesseract.startDrawingQuads();
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 11 * pixel / 2,  1 - 11 * pixel / 2, 5 * textureP, 5 * textureP);
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 5 * textureP, 0 * textureP);
			tesseract.addVertexWithUV(11 * pixel / 2, 1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 0 * textureP, 0 * textureP);
			tesseract.addVertexWithUV(11 * pixel / 2, 11 * pixel / 2, 1 - 11 * pixel / 2, 0 * textureP, 5 * textureP);

			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 11 * pixel / 2,  11 * pixel / 2, 5 * textureP, 5 * textureP);
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 11 * pixel / 2, 5 * textureP, 0 * textureP);
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 0 * textureP, 0 * textureP);
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 11 * pixel / 2, 1 - 11 * pixel / 2, 0 * textureP, 5 * textureP);

			tesseract.addVertexWithUV(11 * pixel / 2, 11 * pixel / 2, 11 * pixel / 2, 5 * textureP, 5 * textureP);
			tesseract.addVertexWithUV(11 * pixel / 2, 1 - 11 * pixel / 2, 11 * pixel / 2, 5 * textureP, 0 * textureP);
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 11 * pixel / 2, 0 * textureP, 0 * textureP);
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 11 * pixel / 2,  11 * pixel / 2, 0 * textureP, 5 * textureP);

			tesseract.addVertexWithUV(11 * pixel / 2, 11 * pixel / 2, 1 - 11 * pixel / 2, 5 * textureP, 5 * textureP);
			tesseract.addVertexWithUV(11 * pixel / 2, 1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 5 * textureP, 0 * textureP);
			tesseract.addVertexWithUV(11 * pixel / 2, 1 - 11 * pixel / 2, 11 * pixel / 2, 0 * textureP, 0 * textureP);
			tesseract.addVertexWithUV(11 * pixel / 2, 11 * pixel / 2,  11 * pixel / 2, 0 * textureP, 5 * textureP);

			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 5 * textureP, 5 * textureP);
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 11 * pixel / 2, 5 * textureP, 0 * textureP);
			tesseract.addVertexWithUV(11 * pixel / 2, 1 - 11 * pixel / 2, 11 * pixel / 2, 0 * textureP, 0 * textureP);
			tesseract.addVertexWithUV(11 * pixel / 2, 1 - 11 * pixel / 2,  1 - 11 * pixel / 2, 0 * textureP, 5 * textureP);

			tesseract.addVertexWithUV(11 * pixel / 2, 11 * pixel / 2,  1 - 11 * pixel / 2, 5 * textureP, 5 * textureP);
			tesseract.addVertexWithUV(11 * pixel / 2, 11 * pixel / 2, 11 * pixel / 2, 5 * textureP, 0 * textureP);
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 11 * pixel / 2, 11 * pixel / 2, 0 * textureP, 0 * textureP);
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 11 * pixel / 2, 1 - 11 * pixel / 2, 0 * textureP, 5 * textureP);
		tesseract.draw();
	}
	
	public void drawConnection(ForgeDirection direction)
	{
		if(direction.equals(ForgeDirection.UP))
		{
			
		}
		
		Tessellator tesseract = Tessellator.instance;
		tesseract.draw();
	}

}

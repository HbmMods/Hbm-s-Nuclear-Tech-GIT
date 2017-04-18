package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.tileentity.TileEntityCable;

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
		TileEntityCable cable = (TileEntityCable) tileentity;
		for(int i = 0; i < cable.connections.length; i++)
		{
			if(cable.connections[i] != null)
			{
				drawConnection(cable.connections[i]);
			}
		}
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
		
		// Muehsam muss ich hier im BSH meine genialen Mods schreiben, obwohl ich die Zeit eigentlich doch besser nutzen koennte.
		// Da mir das aber Spass macht, wird auch in Zukunft gutes Zeug von mir geben (und damit meine ich NICHT Drogen, etc.)
		// Danke.
		
		//I didn't write this, but I'm gonna leave it there.
	}
	
	public void drawConnection(ForgeDirection direction)
	{
		Tessellator tesseract = Tessellator.instance;
		tesseract.startDrawingQuads();
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			if(direction.equals(ForgeDirection.UP))
			{
		
			}
			if(direction.equals(ForgeDirection.DOWN))
			{
				GL11.glRotatef(180, 1, 0, 0);
			}
			if(direction.equals(ForgeDirection.NORTH))
			{
				GL11.glRotatef(270, 1, 0, 0);
			}
			if(direction.equals(ForgeDirection.SOUTH))
			{
				GL11.glRotatef(90, 1, 0, 0);
			}
			if(direction.equals(ForgeDirection.EAST))
			{
				GL11.glRotatef(270, 0, 0, 1);
			}
			if(direction.equals(ForgeDirection.WEST))
			{
				GL11.glRotatef(90, 0, 0, 1);
			}
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2,  1 - 11 * pixel / 2, 5 * textureP, 5 * textureP);
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 1, 1 - 11 * pixel / 2, 10 * textureP, 5 * textureP);
			tesseract.addVertexWithUV(11 * pixel / 2, 1, 1 - 11 * pixel / 2, 10 * textureP, 0 * textureP);
			tesseract.addVertexWithUV(11 * pixel / 2, 1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 5 * textureP, 0 * textureP);

			tesseract.addVertexWithUV(11 * pixel / 2, 1 - 11 * pixel / 2, 11 * pixel / 2, 5 * textureP, 5 * textureP);
			tesseract.addVertexWithUV(11 * pixel / 2, 1, 11 * pixel / 2, 10 * textureP, 5 * textureP);
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 1, 11 * pixel / 2, 10 * textureP, 0 * textureP);
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 11 * pixel / 2, 5 * textureP, 0 * textureP);

			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 11 * pixel / 2, 5 * textureP, 5 * textureP);
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 1, 11 * pixel / 2, 10 * textureP, 5 * textureP);
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 1, 1 - 11 * pixel / 2, 10 * textureP, 0 * textureP);
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 5 * textureP, 0 * textureP);

			tesseract.addVertexWithUV(11 * pixel / 2, 1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 5 * textureP, 5 * textureP);
			tesseract.addVertexWithUV(11 * pixel / 2, 1, 1 - 11 * pixel / 2, 10 * textureP, 5 * textureP);
			tesseract.addVertexWithUV(11 * pixel / 2, 1, 11 * pixel / 2, 10 * textureP, 0 * textureP);
			tesseract.addVertexWithUV(11 * pixel / 2, 1 - 11 * pixel / 2, 11 * pixel / 2, 5 * textureP, 0 * textureP);
		tesseract.draw();
		
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		if(direction.equals(ForgeDirection.UP))
		{
		
		}
		if(direction.equals(ForgeDirection.DOWN))
		{
			GL11.glRotatef(-180, 1, 0, 0);
		}
		if(direction.equals(ForgeDirection.NORTH))
		{
			GL11.glRotatef(-270, 1, 0, 0);
		}
		if(direction.equals(ForgeDirection.SOUTH))
		{
			GL11.glRotatef(-90, 1, 0, 0);
		}
		if(direction.equals(ForgeDirection.EAST))
		{
			GL11.glRotatef(-270, 0, 0, 1);
		}
		if(direction.equals(ForgeDirection.WEST))
		{
			GL11.glRotatef(-90, 0, 0, 1);
		}
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
	}

}

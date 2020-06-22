package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public class RenderSoyuzMultiblock extends TileEntitySpecialRenderer {
	
	float pixel = 1F/16F;

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f) {

		GL11.glPushMatrix();
		
		GL11.glTranslatef((float)x + 1, (float)y + 1, (float)z);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_CULL_FACE);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
		
		RenderBlocks rb = RenderBlocks.getInstance();

        IIcon icon;
		ResourceLocation loc;
		
        icon = rb.getBlockIconFromSide(ModBlocks.struct_launcher, 1);
		loc = new ResourceLocation(RefStrings.MODID + ":textures/blocks/" + icon.getIconName().substring(4, icon.getIconName().length()) + ".png");
		
		for(int i = -6; i <= 6; i++)
			for(int j = 3; j <= 4; j++)
				for(int k = -6; k <= 6; k++)
					renderSmolBlockAt(loc, i, j, k);
		
		for(int i = -1; i <= 1; i++)
			for(int j = 3; j <= 4; j++)
				for(int k = -8; k <= -7; k++)
					renderSmolBlockAt(loc, i, j, k);
		
		for(int i = -2; i <= 2; i++)
			for(int j = 3; j <= 4; j++)
				for(int k = 7; k <= 9; k++)
					renderSmolBlockAt(loc, i, j, k);
		
		for(int i = -2; i <= 2; i++)
			for(int k = 5; k <= 9; k++)
				renderSmolBlockAt(loc, i, 51, k);
		
		for(int i = -1; i <= 1; i++)
			for(int k = -8; k <= -6; k++)
				renderSmolBlockAt(loc, i, 38, k);
        
        icon = rb.getBlockIconFromSide(ModBlocks.concrete_smooth, 1);
		loc = new ResourceLocation(RefStrings.MODID + ":textures/blocks/" + icon.getIconName().substring(4, icon.getIconName().length()) + ".png");
		
		for(int i = 3; i <= 6; i++)
			for(int j = 0; j <= 2; j++)
				for(int k = 3; k <= 6; k++)
					renderSmolBlockAt(loc, i, j, k);
		
		for(int i = -6; i <= -3; i++)
			for(int j = 0; j <= 2; j++)
				for(int k = 3; k <= 6; k++)
					renderSmolBlockAt(loc, i, j, k);
		
		for(int i = -6; i <= -3; i++)
			for(int j = 0; j <= 2; j++)
				for(int k = -6; k <= -3; k++)
					renderSmolBlockAt(loc, i, j, k);
		
		for(int i = 3; i <= 6; i++)
			for(int j = 0; j <= 2; j++)
				for(int k = -6; k <= -3; k++)
					renderSmolBlockAt(loc, i, j, k);
		
		for(int i = -1; i <= 1; i++)
			for(int j = 0; j <= 2; j++)
				for(int k = -8; k <= -6; k++)
					renderSmolBlockAt(loc, i, j, k);
		
		for(int i = -2; i <= 2; i++)
			for(int j = 0; j <= 2; j++)
				for(int k = 5; k <= 9; k++)
					renderSmolBlockAt(loc, i, j, k);
        
        icon = rb.getBlockIconFromSide(ModBlocks.struct_scaffold, 1);
		loc = new ResourceLocation(RefStrings.MODID + ":textures/blocks/" + icon.getIconName().substring(4, icon.getIconName().length()) + ".png");
		
		for(int i = -1; i <= 1; i++)
			for(int j = 5; j <= 50; j++)
				for(int k = 6; k <= 8; k++)
					renderSmolBlockAt(loc, i, j, k);
		
		for(int j = 5; j <= 37; j++)
			renderSmolBlockAt(loc, 0, j, -7);

		GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
		
		GL11.glPopMatrix();
	}
	
	public void renderSmolBlockAt(ResourceLocation loc, int x, int y, int z) {
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, z);
		GL11.glRotatef(180, 0F, 0F, 1F);
		
		Tessellator tesseract = Tessellator.instance;
		tesseract.startDrawingQuads();
			this.bindTexture(loc);
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 1, 0);
			tesseract.addVertexWithUV(11 * pixel / 2, 1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 0, 0);
			tesseract.addVertexWithUV(11 * pixel / 2, 11 * pixel / 2, 1 - 11 * pixel / 2, 0, 1);
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 11 * pixel / 2,  1 - 11 * pixel / 2, 1, 1);
			tesseract.draw();

			tesseract.startDrawingQuads();
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 11 * pixel / 2, 1, 0);
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 0, 0);
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 11 * pixel / 2, 1 - 11 * pixel / 2, 0, 1);
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 11 * pixel / 2,  11 * pixel / 2, 1, 1);
			tesseract.draw();

			tesseract.startDrawingQuads();
			tesseract.addVertexWithUV(11 * pixel / 2, 1 - 11 * pixel / 2, 11 * pixel / 2, 1, 0);
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 11 * pixel / 2, 0, 0);
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 11 * pixel / 2,  11 * pixel / 2, 0, 1);
			tesseract.addVertexWithUV(11 * pixel / 2, 11 * pixel / 2, 11 * pixel / 2, 1, 1);
			tesseract.draw();

			tesseract.startDrawingQuads();
			tesseract.addVertexWithUV(11 * pixel / 2, 1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 1, 0);
			tesseract.addVertexWithUV(11 * pixel / 2, 1 - 11 * pixel / 2, 11 * pixel / 2, 0, 0);
			tesseract.addVertexWithUV(11 * pixel / 2, 11 * pixel / 2,  11 * pixel / 2, 0, 1);
			tesseract.addVertexWithUV(11 * pixel / 2, 11 * pixel / 2, 1 - 11 * pixel / 2, 1, 1);
			tesseract.draw();

			tesseract.startDrawingQuads();
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 11 * pixel / 2, 1, 0);
			tesseract.addVertexWithUV(11 * pixel / 2, 1 - 11 * pixel / 2, 11 * pixel / 2, 0, 0);
			tesseract.addVertexWithUV(11 * pixel / 2, 1 - 11 * pixel / 2,  1 - 11 * pixel / 2, 0, 1);
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 1, 1);
			tesseract.draw();

			tesseract.startDrawingQuads();
			tesseract.addVertexWithUV(11 * pixel / 2, 11 * pixel / 2, 11 * pixel / 2, 1, 0);
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 11 * pixel / 2, 11 * pixel / 2, 0, 0);
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 11 * pixel / 2, 1 - 11 * pixel / 2, 0, 1);
			tesseract.addVertexWithUV(11 * pixel / 2, 11 * pixel / 2,  1 - 11 * pixel / 2, 1, 1);
		tesseract.draw();
		GL11.glPopMatrix();
		
	}
}

package com.hbm.render.tileentity;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.IRenderFoundry;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderFoundry extends TileEntitySpecialRenderer {
	
	public static final ResourceLocation lava = new ResourceLocation(RefStrings.MODID, "textures/models/machines/lava_gray.png");

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		IRenderFoundry foundry = (IRenderFoundry) tile;
		
		if(!foundry.shouldRender()) return;
		
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		
		GL11.glDepthMask(false);
		Tessellator tess = Tessellator.instance;
		this.bindTexture(lava);
		
		int hex = foundry.getMat().moltenColor;
		Color color = new Color(hex);

		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);

		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);

		tess.startDrawingQuads();
		tess.setNormal(0F, 1F, 0F);
		tess.setColorRGBA_F(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, 1F);
		tess.addVertexWithUV(foundry.minX(), foundry.getLevel(), foundry.minZ(), foundry.minZ(), foundry.maxX());
		tess.addVertexWithUV(foundry.minX(), foundry.getLevel(), foundry.maxZ(), foundry.maxZ(), foundry.maxX());
		tess.addVertexWithUV(foundry.maxX(), foundry.getLevel(), foundry.maxZ(), foundry.maxZ(), foundry.minX());
		tess.addVertexWithUV(foundry.maxX(), foundry.getLevel(), foundry.minZ(), foundry.minZ(), foundry.minX());
		tess.draw();
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		tess.startDrawingQuads();
		tess.setNormal(0F, 1F, 0F);
		tess.setColorRGBA_F(1F, 1F, 1F, 0.3F);
		tess.addVertexWithUV(foundry.minX(), foundry.getLevel(), foundry.minZ(), foundry.minZ(), foundry.maxX());
		tess.addVertexWithUV(foundry.minX(), foundry.getLevel(), foundry.maxZ(), foundry.maxZ(), foundry.maxX());
		tess.addVertexWithUV(foundry.maxX(), foundry.getLevel(), foundry.maxZ(), foundry.maxZ(), foundry.minX());
		tess.addVertexWithUV(foundry.maxX(), foundry.getLevel(), foundry.minZ(), foundry.minZ(), foundry.minX());
		tess.draw();
		
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopAttrib();
		GL11.glPopMatrix();
		
		GL11.glDepthMask(true);
		
		if(foundry instanceof IInventory) {
			IInventory inv = (IInventory) foundry;
		}
		
		GL11.glPopMatrix();
	}
}

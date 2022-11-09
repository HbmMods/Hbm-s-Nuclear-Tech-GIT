package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.blocks.generic.BlockSnowglobe.TileEntitySnowglobe;
import com.hbm.lib.RefStrings;
import com.hbm.wiaj.WorldInAJar;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderSnowglobe extends TileEntitySpecialRenderer {
	
	public static final IModelCustom snowglobe = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/trinkets/snowglobe.obj"));
	public static final ResourceLocation socket = new ResourceLocation(RefStrings.MODID, "textures/models/trinkets/snowglobe.png");
	public static RenderBlocks renderer = new RenderBlocks();

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		
		GL11.glRotated(22.5D * tile.getBlockMetadata() + 90, 0, -1, 0);
		
		double scale = 0.0625D;
		GL11.glScaled(scale, scale, scale);
		
		this.bindTexture(socket);
		snowglobe.renderPart("Socket");
		
		TileEntitySnowglobe te = (TileEntitySnowglobe) tile;
		
		if(te.type.scene != null) {
			
			WorldInAJar world = te.type.scene;
			renderer.blockAccess = world;
			renderer.enableAO = true;
			
			double size = Math.max(world.sizeX, world.sizeZ);
			scale = 4D / size;
			GL11.glTranslated(0, 1, 0);
			GL11.glScaled(scale, scale, scale);
			
			GL11.glTranslated(world.sizeX * -0.5, 0, world.sizeZ * -0.5);

			RenderHelper.disableStandardItemLighting();
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_CULL_FACE);
			
			//i hope everyone involved in the creation of openGL has their nutsack explode

			bindTexture(TextureMap.locationBlocksTexture);
			GL11.glShadeModel(GL11.GL_SMOOTH);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			Tessellator.instance.startDrawingQuads();
			
			for(int a = 0; a < world.sizeX; a++) {
				for(int b = 0; b < world.sizeY; b++) {
					for(int c = 0; c < world.sizeZ; c++) {
						renderer.renderBlockByRenderType(world.getBlock(a, b, c), a, b, c);
					}
				}
			}
			
			Tessellator.instance.draw();
			GL11.glShadeModel(GL11.GL_FLAT);
		}

		GL11.glPopMatrix();
		RenderHelper.enableStandardItemLighting();
	}
}

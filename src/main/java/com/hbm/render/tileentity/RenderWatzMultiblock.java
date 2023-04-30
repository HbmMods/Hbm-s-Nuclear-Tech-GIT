package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.render.util.SmallBlockPronter;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderWatzMultiblock extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);

		bindTexture(TextureMap.locationBlocksTexture);
		SmallBlockPronter.startDrawing();
		
		SmallBlockPronter.drawSmolBlockAt(ModBlocks.watz_cooler, 0, 0F, 1F, 0F);
		SmallBlockPronter.drawSmolBlockAt(ModBlocks.watz_cooler, 0, 0F, 2F, 0F);
		
		for(int i = 0; i < 3; i++) {
			SmallBlockPronter.drawSmolBlockAt(ModBlocks.watz_element, 0, 1F, i, 0F);
			SmallBlockPronter.drawSmolBlockAt(ModBlocks.watz_element, 0, 2F, i, 0F);
			SmallBlockPronter.drawSmolBlockAt(ModBlocks.watz_element, 0, 0F, i, 1F);
			SmallBlockPronter.drawSmolBlockAt(ModBlocks.watz_element, 0, 0F, i, 2F);
			SmallBlockPronter.drawSmolBlockAt(ModBlocks.watz_element, 0, -1F, i, 0F);
			SmallBlockPronter.drawSmolBlockAt(ModBlocks.watz_element, 0, -2F, i, 0F);
			SmallBlockPronter.drawSmolBlockAt(ModBlocks.watz_element, 0, 0F, i, -1F);
			SmallBlockPronter.drawSmolBlockAt(ModBlocks.watz_element, 0, 0F, i, -2F);
			SmallBlockPronter.drawSmolBlockAt(ModBlocks.watz_element, 0, 1F, i, 1F);
			SmallBlockPronter.drawSmolBlockAt(ModBlocks.watz_element, 0, 1F, i, -1F);
			SmallBlockPronter.drawSmolBlockAt(ModBlocks.watz_element, 0, -1F, i, 1F);
			SmallBlockPronter.drawSmolBlockAt(ModBlocks.watz_element, 0, -1F, i, -1F);
			SmallBlockPronter.drawSmolBlockAt(ModBlocks.watz_cooler, 0, 2F, i, 1F);
			SmallBlockPronter.drawSmolBlockAt(ModBlocks.watz_cooler, 0, 2F, i, -1F);
			SmallBlockPronter.drawSmolBlockAt(ModBlocks.watz_cooler, 0, 1F, i, 2F);
			SmallBlockPronter.drawSmolBlockAt(ModBlocks.watz_cooler, 0, -1F, i, 2F);
			SmallBlockPronter.drawSmolBlockAt(ModBlocks.watz_cooler, 0, -2F, i, 1F);
			SmallBlockPronter.drawSmolBlockAt(ModBlocks.watz_cooler, 0, -2F, i, -1F);
			SmallBlockPronter.drawSmolBlockAt(ModBlocks.watz_cooler, 0, 1F, i, -2F);
			SmallBlockPronter.drawSmolBlockAt(ModBlocks.watz_cooler, 0, -1F, i, -2F);
			for(int j = -1; j < 2; j++) {
				SmallBlockPronter.drawSmolBlockAt(ModBlocks.watz_end, 1, 3F, i, j);
				SmallBlockPronter.drawSmolBlockAt(ModBlocks.watz_end, 1, j, i, 3F);
				SmallBlockPronter.drawSmolBlockAt(ModBlocks.watz_end, 1, -3F, i, j);
				SmallBlockPronter.drawSmolBlockAt(ModBlocks.watz_end, 1, j, i, -3F);
			}
			SmallBlockPronter.drawSmolBlockAt(ModBlocks.watz_end, 1, 2F, i, 2F);
			SmallBlockPronter.drawSmolBlockAt(ModBlocks.watz_end, 1, 2F, i, -2F);
			SmallBlockPronter.drawSmolBlockAt(ModBlocks.watz_end, 1, -2F, i, 2F);
			SmallBlockPronter.drawSmolBlockAt(ModBlocks.watz_end, 1, -2F, i, -2F);
		}
		
		SmallBlockPronter.draw();
		
		GL11.glPopMatrix();
	}
}

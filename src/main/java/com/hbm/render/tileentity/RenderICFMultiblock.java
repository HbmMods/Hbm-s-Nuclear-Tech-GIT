package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.render.util.SmallBlockPronter;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderICFMultiblock extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		
		switch(tile.getBlockMetadata()) {
		case 2: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(180, 0F, 1F, 0F); break;
		}
		
		GL11.glTranslated(-0.5, 0, -0.5);
		
		bindTexture(TextureMap.locationBlocksTexture);
		SmallBlockPronter.startDrawing();
		
		for(int i = -8; i <= 8; i++) {
			SmallBlockPronter.drawSmolBlockAt(ModBlocks.icf_component, 0, 1F, 0F, i);
			if(i != 0) SmallBlockPronter.drawSmolBlockAt(ModBlocks.icf_component, 0, 0F, 0F, i);
			SmallBlockPronter.drawSmolBlockAt(ModBlocks.icf_component, 0, -1F, 0F, i);
			SmallBlockPronter.drawSmolBlockAt(ModBlocks.icf_component, 2, 0F, 3F, i);
			for(int j = -1; j <= 1; j++) SmallBlockPronter.drawSmolBlockAt(ModBlocks.icf_component, Math.abs(i) <= 2 ? 2 : 4, j, 1F, i);
			for(int j = -2; j <= 2; j++) SmallBlockPronter.drawSmolBlockAt(ModBlocks.icf_component, Math.abs(i) <= 2 ? 2 : 4, j, 2F, i);
			for(int j = -2; j <= 2; j++) if(j != 0) SmallBlockPronter.drawSmolBlockAt(ModBlocks.icf_component, Math.abs(i) <= 2 ? 2 : 4, j, 3F, i);
			for(int j = -2; j <= 2; j++) SmallBlockPronter.drawSmolBlockAt(ModBlocks.icf_component, Math.abs(i) <= 2 ? 2 : 4, j, 4F, i);
			for(int j = -1; j <= 1; j++) SmallBlockPronter.drawSmolBlockAt(ModBlocks.icf_component, Math.abs(i) <= 2 ? 2 : 4, j, 5F, i);
		}
		
		SmallBlockPronter.draw();
		
		GL11.glPopMatrix();
	}

}

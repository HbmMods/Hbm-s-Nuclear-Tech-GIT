package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.render.util.SmallBlockPronter;
import com.hbm.tileentity.machine.TileEntityITERStruct;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderITERMultiblock extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f) {

		GL11.glPushMatrix();
		
		GL11.glTranslated(x, y, z);

		bindTexture(TextureMap.locationBlocksTexture);
		SmallBlockPronter.startDrawing();

		int[][][] layout = TileEntityITERStruct.layout;

		for(int iy = -2; iy <= 2; iy++) {
			int iny = 2 - Math.abs(iy);

			for(int ix = 0; ix < layout[0].length; ix++) {
				for(int iz = 0; iz < layout[0][0].length; iz++) {

					int block = layout[iny][ix][iz];

					switch(block) {
					case 0:
						continue;
					case 1: SmallBlockPronter.drawSmolBlockAt(ModBlocks.fusion_conductor, 1, ix - 7F, iy + 2, iz - 7F); break;
					case 2: SmallBlockPronter.drawSmolBlockAt(ModBlocks.fusion_center, 0, ix - 7F, iy + 2, iz - 7F); break;
					case 3: SmallBlockPronter.drawSmolBlockAt(ModBlocks.fusion_motor, 0, ix - 7F, iy + 2, iz - 7F); break;
					case 4: SmallBlockPronter.drawSmolBlockAt(ModBlocks.reinforced_glass, 0, ix - 7F, iy + 2, iz - 7F); break;
					}
				}
			}
		}
		
		SmallBlockPronter.draw();
		
		GL11.glPopMatrix();
	}

}

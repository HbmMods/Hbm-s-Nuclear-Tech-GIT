package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.fusion.MachineFusionTorus;
import com.hbm.render.util.SmallBlockPronter;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderFusionTorusMultiblock extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		
		bindTexture(TextureMap.locationBlocksTexture);
		SmallBlockPronter.startDrawing();
		
		for(int iy = 0; iy < 5; iy++) {
			for(int ix = 0; ix < MachineFusionTorus.layout[0].length; ix++) {
				for(int iz = 0; iz < MachineFusionTorus.layout[0][0].length; iz++) {
					
					int ly = iy > 2 ? 4 - iy : iy;
					int i = MachineFusionTorus.layout[ly][ix][iz];
					if(i == 0) continue;
					SmallBlockPronter.drawSmolBlockAt(ModBlocks.fusion_component, i, ix - 7, iy, iz - 7);
				}
			}
		}
		
		SmallBlockPronter.draw();
		
		GL11.glPopMatrix();
	}
}

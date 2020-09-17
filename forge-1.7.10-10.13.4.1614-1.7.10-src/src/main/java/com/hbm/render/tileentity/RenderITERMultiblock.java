package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.render.util.IconUtil;
import com.hbm.render.util.SmallBlockPronter;
import com.hbm.tileentity.machine.TileEntityITERStruct;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderITERMultiblock extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f) {

		GL11.glPushMatrix();
		
		GL11.glTranslatef((float)x, (float)y, (float)z);
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_CULL_FACE);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glDepthMask(false);
        
		ResourceLocation magnet = IconUtil.getTextureFromBlockAndSide(ModBlocks.fusion_conductor, 2);
		ResourceLocation solenoid = IconUtil.getTextureFromBlockAndSide(ModBlocks.fusion_center, 2);
		ResourceLocation motor = IconUtil.getTextureFromBlock(ModBlocks.fusion_motor);
		ResourceLocation glass = IconUtil.getTextureFromBlock(ModBlocks.reinforced_glass);
        
        int[][][] layout = TileEntityITERStruct.layout;
        
        for(int iy = -2; iy <= 2; iy ++) {
        	
        	int iny = 2 - Math.abs(iy);
        	
	        for(int ix = 0; ix < layout[0].length; ix++) {
	
	            for(int iz = 0; iz < layout[0][0].length; iz++) {
	            	
	            	int block = layout[iny][ix][iz];
	            	
	            	switch(block) {
	            	case 0: continue;
	            	case 1: bindTexture(magnet); break;
	            	case 2: bindTexture(solenoid); break;
	            	case 3: bindTexture(motor); break;
	            	case 4: bindTexture(glass); break;
	            	}
	            	
	            	SmallBlockPronter.renderSmolBlockAt(ix - 6F, iy + 3, iz - 7F);
	            }
	        }
        }

		GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glDepthMask(true);

        GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

}

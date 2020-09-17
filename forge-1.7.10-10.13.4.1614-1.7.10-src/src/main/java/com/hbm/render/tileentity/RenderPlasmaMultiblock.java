package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.render.util.IconUtil;
import com.hbm.render.util.SmallBlockPronter;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderPlasmaMultiblock extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f) {

		GL11.glPushMatrix();
		
		GL11.glTranslatef((float)x + 0.5F, (float)y, (float)z + 0.5F);
		
		switch(te.getBlockMetadata()) {
		case 2: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(180, 0F, 1F, 0F); break;
		}
		
		GL11.glTranslatef(-0.5F, 0, -0.5F);
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_CULL_FACE);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glDepthMask(false);
        
		ResourceLocation heater = IconUtil.getTextureFromBlockAndSide(ModBlocks.fusion_heater, 2);
		bindTexture(heater);
        
        for(int iy = 1; iy < 6; iy ++) {
        	
	        for(int ix = 0; ix < 10; ix++) {
	
	            for(int iz = -1; iz < 2; iz++) {
	            	
	            	if(iy == 5 && ix > 3)
	            		break;
	            	
	            	SmallBlockPronter.renderSmolBlockAt(ix, iy, iz);
	            }
	        }
        }

        for(int i = 10; i <= 11; i++)
            for(int j = 2; j <= 3; j++)
            	SmallBlockPronter.renderSmolBlockAt(i, j, 0);

		GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glDepthMask(true);

        GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
}

package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.ResourceManager;
import com.hbm.render.util.RenderSparks;
import com.hbm.tileentity.machine.TileEntityMachineReactorBreeding;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderBreeder extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
		
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
        
        GL11.glRotatef(90, 0F, 1F, 0F);

		switch(tile.getBlockMetadata() - BlockDummyable.offset)
		{
		case 2: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(270, 0F, 1F, 0F); break;
		}
		
		TileEntityMachineReactorBreeding breeder = (TileEntityMachineReactorBreeding) tile;
        
		if(breeder.progress > 0.0F)
			for(int i = 0; i < 3; i++) {
				GL11.glPushMatrix();
		        GL11.glRotatef((float) (Math.PI * i), 0F, 1F, 0F);
				RenderSparks.renderSpark((int) ((System.currentTimeMillis() % 10000) / 100 + i), 0, 1.5625, 0, 0.15F, 3, 4, 0x00ff00, 0xffffff);
				GL11.glPopMatrix();
			}
		
        bindTexture(ResourceManager.breeder_tex);
        
        GL11.glShadeModel(GL11.GL_SMOOTH);
        ResourceManager.breeder.renderAll();
        GL11.glShadeModel(GL11.GL_FLAT);
        
        GL11.glEnable(GL11.GL_CULL_FACE);

        GL11.glPopMatrix();
	}

}

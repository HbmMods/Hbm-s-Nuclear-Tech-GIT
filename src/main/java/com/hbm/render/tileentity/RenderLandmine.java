package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderLandmine extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);

		GL11.glRotatef(180, 0F, 1F, 0F);

		Block block = tileEntity.getWorldObj().getBlock(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);

		if(block == ModBlocks.mine_ap) {
			GL11.glTranslated(0, -0.075, 0);
			GL11.glScaled(1.5D, 1.5D, 1.5D);
			bindTexture(ResourceManager.mine_ap_tex);
			ResourceManager.mine_ap.renderAll();
		}
		if(block == ModBlocks.mine_he) {
			GL11.glRotatef(-90, 0F, 1F, 0F);
			GL11.glShadeModel(GL11.GL_SMOOTH);
			bindTexture(ResourceManager.mine_marelet_tex);
			ResourceManager.mine_marelet.renderAll();
			GL11.glShadeModel(GL11.GL_FLAT);
		}
		if(block == ModBlocks.mine_shrap) {
			bindTexture(ResourceManager.mine_shrap_tex);
			ResourceManager.mine_he.renderAll();
		}
		if(block == ModBlocks.mine_fat) {
			GL11.glScaled(0.25D, 0.25D, 0.25D);
			bindTexture(ResourceManager.mine_fat_tex);
			ResourceManager.mine_fat.renderAll();
		}

		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}

}

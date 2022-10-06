package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderFrackingTower extends TileEntitySpecialRenderer {
	
	public static final ResourceLocation pipe_tex = new ResourceLocation(RefStrings.MODID, "textures/blocks/pipe_silver.png");

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {

		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);

		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.fracking_tower_tex);
		ResourceManager.fracking_tower.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glTranslated(0, 0.5, 0);
		
		bindTexture(pipe_tex);
		ResourceManager.pipe_neo.renderPart("pX");
		ResourceManager.pipe_neo.renderPart("nX");
		ResourceManager.pipe_neo.renderPart("pZ");
		ResourceManager.pipe_neo.renderPart("nZ");
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}
}

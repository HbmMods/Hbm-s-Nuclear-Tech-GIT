package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityReactorResearch;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderSmallReactor extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);

		TileEntityReactorResearch reactor = (TileEntityReactorResearch) tileEntity;

		bindTexture(ResourceManager.reactor_small_base_tex);
		ResourceManager.reactor_small_base.renderAll();
		
		double level = (reactor.lastLevel + (reactor.level - reactor.lastLevel) * f);
		
		GL11.glPushMatrix();
		GL11.glTranslated(0.0D, level, 0.0D);

		bindTexture(ResourceManager.reactor_small_rods_tex);
		ResourceManager.reactor_small_rods.renderAll();

		GL11.glPopMatrix();

		if(reactor.totalFlux > 10 && reactor.isSubmerged()) {

			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			GL11.glDisable(GL11.GL_ALPHA_TEST);

			Tessellator tess = Tessellator.instance;

			for(double d = 0.285; d < 0.7; d += 0.025) {

				tess.startDrawingQuads();
				tess.setColorRGBA_F(0.4F, 0.9F, 1.0F, 0.025F + (float) (Math.random() * 0.015F) + (0.125F * reactor.totalFlux / 1000F));

				double top = 1.375;
				double bottom = 1.375;

				tess.addVertex(d, bottom - d, -d);
				tess.addVertex(d, top + d, -d);
				tess.addVertex(d, top + d, d);
				tess.addVertex(d, bottom - d, d);

				tess.addVertex(-d, bottom - d, -d);
				tess.addVertex(-d, top + d, -d);
				tess.addVertex(-d, top + d, d);
				tess.addVertex(-d, bottom - d, d);

				tess.addVertex(-d, bottom - d, d);
				tess.addVertex(-d, top + d, d);
				tess.addVertex(d, top + d, d);
				tess.addVertex(d, bottom - d, d);

				tess.addVertex(-d, bottom - d, -d);
				tess.addVertex(-d, top + d, -d);
				tess.addVertex(d, top + d, -d);
				tess.addVertex(d, bottom - d, -d);

				tess.addVertex(-d, top + d, -d);
				tess.addVertex(-d, top + d, d);
				tess.addVertex(d, top + d, d);
				tess.addVertex(d, top + d, -d);

				tess.addVertex(-d, bottom - d, -d);
				tess.addVertex(-d, bottom - d, d);
				tess.addVertex(d, bottom - d, d);
				tess.addVertex(d, bottom - d, -d);

				tess.draw();
			}

			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
		}

		GL11.glEnable(GL11.GL_CULL_FACE);

		GL11.glPopMatrix();
	}
}

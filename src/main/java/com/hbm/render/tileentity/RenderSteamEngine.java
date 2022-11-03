package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderSteamEngine extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		switch(tile.getBlockMetadata() - BlockDummyable.offset) {
		case 3: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 2: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}
		
		GL11.glTranslated(2, 0, 0);
		GL11.glShadeModel(GL11.GL_SMOOTH);

		double angle = System.currentTimeMillis() % 3600D;
		
		bindTexture(ResourceManager.steam_engine_tex);
		ResourceManager.steam_engine.renderPart("Base");
		
		GL11.glPushMatrix();
		GL11.glTranslated(2, 1.375, 0);
		GL11.glRotated(angle, 0, 0, -1);
		GL11.glTranslated(-2, -1.375, 0);
		ResourceManager.steam_engine.renderPart("Flywheel");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 1.375, -0.5);
		GL11.glRotated(angle * 2D, 1, 0, 0);
		GL11.glTranslated(0, -1.375, 0.5);
		ResourceManager.steam_engine.renderPart("Shaft");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		double sin = Math.sin(angle * Math.PI / 180D) * 0.25D - 0.25D;
		double cos = Math.cos(angle * Math.PI / 180D) * 0.25D;
		double ang = Math.acos(cos / 1.875D);
		GL11.glTranslated(sin, cos, 0);
		GL11.glTranslated(2.25, 1.375, 0);
		GL11.glRotated(ang * 180D / Math.PI - 90D, 0, 0, -1);
		GL11.glTranslated(-2.25, -1.375, 0);
		ResourceManager.steam_engine.renderPart("Transmission");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		double cath = Math.sqrt(3.515625D - (cos * cos));
		GL11.glTranslated(1.875 - cath + sin, 0, 0); //the difference that "1.875 - cath" makes is minuscule but very much noticeable
		ResourceManager.steam_engine.renderPart("Piston");
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}

}

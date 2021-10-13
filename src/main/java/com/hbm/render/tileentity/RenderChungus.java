package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityChungus;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderChungus extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {

		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);

		GL11.glRotatef(90, 0F, 1F, 0F);

		switch(tile.getBlockMetadata() - BlockDummyable.offset) {
		case 2:
			GL11.glRotatef(90, 0F, 1F, 0F);
			break;
		case 4:
			GL11.glRotatef(180, 0F, 1F, 0F);
			break;
		case 3:
			GL11.glRotatef(270, 0F, 1F, 0F);
			break;
		case 5:
			GL11.glRotatef(0, 0F, 1F, 0F);
			break;
		}

		TileEntityChungus turbine = (TileEntityChungus) tile;

		GL11.glTranslated(0, 0, -3);

		bindTexture(ResourceManager.chungus_tex);

		GL11.glShadeModel(GL11.GL_SMOOTH);
		ResourceManager.chungus.renderPart("Body");
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, 4.5);
		GL11.glRotatef(15 - (turbine.tanks[0].getTankType().ordinal() - 2) * 10, 1, 0, 0);
		GL11.glTranslated(0, 0, -4.5);
		ResourceManager.chungus.renderPart("Lever");
		GL11.glPopMatrix();

		GL11.glTranslated(0, 2.5, 0);
		GL11.glRotatef(turbine.lastRotor + (turbine.rotor - turbine.lastRotor) * f, 0, 0, -1);
		GL11.glTranslated(0, -2.5, 0);
		
		ResourceManager.chungus.renderPart("Blades");

		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPopMatrix();

	}
}

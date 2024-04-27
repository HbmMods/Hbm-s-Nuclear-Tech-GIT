package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityMachineIGenerator;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderIGenerator extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);

		switch(te.getBlockMetadata() - BlockDummyable.offset) {
		case 2: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(90, 0F, 1F, 0F); break;
		}
		
		GL11.glTranslated(0, 0, -1);

		TileEntityMachineIGenerator igen = (TileEntityMachineIGenerator) te;

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		
		GL11.glTranslated(0, 0, 1);
		GL11.glScaled(1D/6D, 1D/6D, 1D/6D);
		GL11.glTranslated(0, 0, -0.5);

		bindTexture(ResourceManager.igen_tex);
		ResourceManager.igen.renderPart("Body");
		
		float rot = igen.prevRotation + (igen.rotation - igen.prevRotation) * f;
		GL11.glTranslated(0, 1.5D, 0);
		GL11.glRotatef(-rot, 0, 0, 1);
		GL11.glTranslated(0, -1.5D, 0);
		ResourceManager.igen.renderPart("Rotor");
		
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);

		GL11.glPopMatrix();
	}
}

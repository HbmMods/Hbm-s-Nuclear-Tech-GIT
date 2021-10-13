package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderMachineShredder extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glRotatef(180, 0F, 1F, 0F);

		bindTexture(ResourceManager.shredder_tex);
		ResourceManager.shredder.renderPart("Base");
		
		double side = 0.3D;
		double height = 2.75D;
		float rot = (System.currentTimeMillis() / 5) % 360;

		GL11.glPushMatrix();
		GL11.glTranslated(side, height, 0);
		GL11.glRotatef(rot, 0F, 0F, 1F);
		ResourceManager.shredder.renderPart("Blades1");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(-side, height, 0);
		GL11.glRotatef(rot, 0F, 0F, -1F);
		ResourceManager.shredder.renderPart("Blades2");
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPopMatrix();
	}
}

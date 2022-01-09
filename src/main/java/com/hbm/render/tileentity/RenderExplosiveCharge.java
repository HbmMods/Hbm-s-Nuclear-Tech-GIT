package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.tileentity.bomb.TileEntityCharge;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderExplosiveCharge extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {

		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y + 0.5D, z + 0.5D);
		
		switch(tile.getBlockMetadata()) {
		case 0: GL11.glRotated(180, 0, 0, 1); break;
		case 1: break;
		case 2: GL11.glRotated(90, 0, 1, 0); GL11.glRotated(-90, 0, 0, 1); break;
		case 3: GL11.glRotated(-90, 0, 1, 0); GL11.glRotated(-90, 0, 0, 1); break;
		case 4: GL11.glRotated(180, 0, 1, 0); GL11.glRotated(-90, 0, 0, 1); break;
		case 5: GL11.glRotated(-90, 0, 0, 1); break;
		}
		
		TileEntityCharge charge = (TileEntityCharge) tile;
		String text = charge.getMinutes() + ":" + charge.getSeconds();
		FontRenderer font = Minecraft.getMinecraft().fontRenderer;

		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		float f3 = 0.0125F;
		GL11.glTranslatef(-0.05F, 0.315F - 0.5F, 0.15F);
		GL11.glScalef(f3, -f3, f3);
		GL11.glRotatef(90, 0, 1, 0);
		GL11.glRotatef(90, 1, 0, 0);
		GL11.glDepthMask(false);
		font.drawString(text, 0, 0, 0x00ff00);
		GL11.glDepthMask(true);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}
}

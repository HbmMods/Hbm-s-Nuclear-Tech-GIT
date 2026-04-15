package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKLever;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKLever.LeverUnit;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderRBMKLever extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float interp) {

		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_LIGHTING);
		
		switch(te.getBlockMetadata()) {
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}
		
		TileEntityRBMKLever lever = (TileEntityRBMKLever) te;
		
		for(int i = 0; i < 2; i++) {
			LeverUnit unit = lever.levers[i];
			if(!unit.active) continue;
			
			GL11.glPushMatrix(); {
				GL11.glTranslated(0.25, 0, i * -0.5 + 0.25);
	
				GL11.glColor3f(1F, 1F, 1F);
				this.bindTexture(ResourceManager.rbmk_lever_tex);
				ResourceManager.rbmk_lever.renderPart("Base");
				
				GL11.glPushMatrix(); {
					float progress = unit.prevFlipProgress + (unit.flipProgress - unit.prevFlipProgress) * interp;
					GL11.glTranslated(0.125, 0.5625, 0);
					GL11.glRotated(-180 * progress, 0, 0, 1);
					GL11.glTranslated(-0.125, -0.5625, 0);
					ResourceManager.rbmk_lever.renderPart("Lever");
				} GL11.glPopMatrix();
	
				FontRenderer font = Minecraft.getMinecraft().fontRenderer;
				int height = font.FONT_HEIGHT;
				if(unit.label != null && !unit.label.isEmpty()) {
	
					GL11.glTranslated(0.01, 0.0625, 0);
					int width = font.getStringWidth(unit.label);
					float f3 = Math.min(0.0125F, 0.4F / Math.max(width, 1));
					GL11.glScalef(f3, -f3, f3);
					GL11.glNormal3f(0.0F, 0.0F, -1.0F);
					GL11.glRotatef(90, 0, 1, 0);
	
					RenderArcFurnace.fullbright(true);
					font.drawString(unit.label, - width / 2, - height / 2, 0x00ff00);
					RenderArcFurnace.fullbright(false);
				}
			} GL11.glPopMatrix();
		}
		
		GL11.glPopMatrix();
	}
}

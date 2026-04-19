package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKIndicator;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKIndicator.IndicatorUnit;
import com.hbm.util.ColorUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderRBMKIndicator extends TileEntitySpecialRenderer {

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
		
		TileEntityRBMKIndicator indicator = (TileEntityRBMKIndicator) te;
		
		for(int i = 0; i < 6; i++) {
			IndicatorUnit unit = indicator.indicators[i];
			if(!unit.active) continue;
			
			GL11.glPushMatrix();
			GL11.glTranslated(0.25, (i / 2) * -0.3125 + 0.3125, (i % 2) * -0.5 + 0.25);

			GL11.glColor3f(1F, 1F, 1F);
			this.bindTexture(ResourceManager.rbmk_indicator_tex);
			ResourceManager.rbmk_indicator.renderPart("Base");

			float mult = unit.light ? 1F : 0.35F;
			GL11.glColor3f(ColorUtil.fr(unit.color) * mult, ColorUtil.fg(unit.color) * mult, ColorUtil.fb(unit.color) * mult);
			if(unit.light) RenderArcFurnace.fullbright(true);
			ResourceManager.rbmk_indicator.renderPart("Light");
			if(unit.light) RenderArcFurnace.fullbright(false);

			FontRenderer font = Minecraft.getMinecraft().fontRenderer;
			int height = font.FONT_HEIGHT;
			if(unit.label != null && !unit.label.isEmpty()) {

				GL11.glTranslated(0.0725, 0.5, 0);
				int width = font.getStringWidth(unit.label);
				float f3 = Math.min(0.0125F, 0.3F / Math.max(width, 1));
				GL11.glScalef(f3, -f3, f3);
				GL11.glNormal3f(0.0F, 0.0F, -1.0F);
				GL11.glRotatef(90, 0, 1, 0);

				font.drawString(unit.label, - width / 2, - height / 2, 0x000000);
			}
			
			GL11.glPopMatrix();
		}
		
		GL11.glPopMatrix();
	}
}

package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKGauge;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKGauge.GaugeUnit;
import com.hbm.util.BobMathUtil;
import com.hbm.util.ColorUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;

public class RenderRBMKGauge extends TileEntitySpecialRenderer {

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
		
		TileEntityRBMKGauge gauge = (TileEntityRBMKGauge) te;
		
		for(int i = 0; i < 4; i++) {
			GaugeUnit unit = gauge.gauges[i];
			if(!unit.active) continue;
			
			GL11.glPushMatrix();
			GL11.glTranslated(0.25, (i / 2) * -0.5 + 0.25, (i % 2) * -0.5 + 0.25);

			GL11.glColor3f(1F, 1F, 1F);
			this.bindTexture(ResourceManager.rbmk_gauge_tex);
			ResourceManager.rbmk_gauge.renderPart("Gauge");
			
			GL11.glPushMatrix();
			GL11.glColor3f(ColorUtil.fr(unit.color), ColorUtil.fg(unit.color), ColorUtil.fb(unit.color));

			double value = unit.lastRenderValue + (unit.renderValue - unit.lastRenderValue) * interp;
			long lower = Math.min(unit.min, unit.max);
			long upper = Math.max(unit.min, unit.max);
			if(lower == upper) upper += 1;
			long range = upper - lower;
			double angle = (double) (value - lower) / (double) range * 50D;
			if(unit.min > unit.max) angle = 50 - angle;
			
			angle = MathHelper.clamp_double(angle, 0, 80);
			
			GL11.glTranslated(0, 0.4375, -0.125);
			GL11.glRotated(angle - 85, -1, 0, 0);
			GL11.glTranslated(0, -0.4375, 0.125);
			
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			RenderArcFurnace.fullbright(true);
			GL11.glEnable(GL11.GL_LIGHTING);
			ResourceManager.rbmk_gauge.renderPart("Needle");
			RenderArcFurnace.fullbright(false);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			
			GL11.glPopMatrix();

			FontRenderer font = Minecraft.getMinecraft().fontRenderer;
			int height = font.FONT_HEIGHT;
			
			double lineScale = 0.0025D;
			String lineLower = unit.min <= 10_000 ? unit.min + "" : BobMathUtil.getShortNumber(unit.min);
			String lineUpper = unit.max <= 10_000 ? unit.max + "" : BobMathUtil.getShortNumber(unit.max);
			
			for(int j = 0; j < 2; j++) {
				GL11.glPushMatrix();
				GL11.glTranslated(0, 0.4375, -0.125);
				GL11.glRotated(10 + j * 50, -1, 0, 0);
				GL11.glTranslated(0, -0.4375, 0.125);
	
				GL11.glTranslated(0.032, 0.4375, 0.125);
				GL11.glScaled(lineScale, -lineScale, lineScale);
				GL11.glNormal3f(0.0F, 0.0F, -1.0F);
				GL11.glRotatef(90, 0, 1, 0);
				font.drawString(j == 0 ? lineLower : lineUpper, 0, -height / 2, 0x000000);
				GL11.glPopMatrix();
			}
			
			if(unit.label != null && !unit.label.isEmpty()) {

				GL11.glTranslated(0.01, 0.3125, 0);
				int width = font.getStringWidth(unit.label);
				float f3 = Math.min(0.0125F, 0.4F / Math.max(width, 1));
				GL11.glScalef(f3, -f3, f3);
				GL11.glNormal3f(0.0F, 0.0F, -1.0F);
				GL11.glRotatef(90, 0, 1, 0);

				RenderArcFurnace.fullbright(true);
				font.drawString(unit.label, - width / 2, - height / 2, 0x00ff00);
				RenderArcFurnace.fullbright(false);
			}
			GL11.glPopMatrix();
		}
		
		GL11.glPopMatrix();
	}

}

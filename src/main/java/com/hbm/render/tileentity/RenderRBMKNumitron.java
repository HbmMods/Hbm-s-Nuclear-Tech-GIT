package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKNumitron;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKNumitron.DisplayUnit;
import com.hbm.util.BobMathUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderRBMKNumitron extends TileEntitySpecialRenderer {

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
		
		TileEntityRBMKNumitron gauge = (TileEntityRBMKNumitron) te;
		
		for(int i = 0; i < 2; i++) {
			DisplayUnit unit = gauge.displays[i];
			if(!unit.active) continue;
			
			GL11.glPushMatrix();
			GL11.glTranslated(0.25, i * -0.5 + 0.25, 0);

			GL11.glColor3f(1F, 1F, 1F);
			this.bindTexture(ResourceManager.rbmk_numitron_tex);
			ResourceManager.rbmk_numitron.renderAll();
			
			GL11.glPushMatrix();
			
			RenderArcFurnace.fullbright(true);
			GL11.glEnable(GL11.GL_LIGHTING);
			
			this.bindTexture(ResourceManager.rbmk_numitron_lights_tex);

			double scale = 200D;
			double w = 8D / scale;
			double h = 13D / scale;
			double yOffset = 0.5625D;
			double line_height = 0.25D;
			
			String value = "";
			switch((int)unit.display_mode) {
				case 0:	// Normal
					value = BobMathUtil.getShortNumber(unit.value);
					break;
				case 1:	// Integer display
					// Overflow handling
					if (unit.value > 9999999) {
						value = "9999999";
						break;
					}
					// Underflow handling
					if (unit.value < -999999) {
						value = "-999999";
						break;
					}
					value = Long.toString(unit.value);
					break;
				case 2:	// Signed Hexadecimal display
					// Overflow handling
					if (unit.value > 0x7FFFFFF) {
						value = "7ffffff";
						break;
					}
					// Underflow handling
					// Theoretically this value is equal to 0x8000000 in a 28bit signed integer, but it's a long so we have to adapt
					if (unit.value < -134217728) {
						value = "8000000";
						break;
					}
					value = Long.toHexString(unit.value);
					long length = value.length();

					// A negative number will have many leading F's, so this needs to be accounted for
					if(length > 7) {
						value = value.substring((int)length-7);
					}
					break;
				case 3:	// Unsigned Hexadecimal display
					// Overflow handling
					if (unit.value > 0xFFFFFFF) {
						value = "fffffff";
						break;
					}
					// Underflow handling
					if (unit.value < 0) {
						value = "-0";
						break;
					}
					value = Long.toHexString(unit.value);
					break;
				default:
					value = BobMathUtil.getShortNumber(unit.value);
					break;
			}

			//** Fill up to 7 characters */
			//** For negative numbers when zeroes are added: put the zeroes between the '-' and the number */
			if ((value.length() < 7) && (value.charAt(0) == '-') && (unit.no_leading_zeroes == false)) {
				value = value.substring(1);
				while(value.length() < 6) value = "0" + value;
				value = "-" + value;
			} else {
				String fill_char = unit.no_leading_zeroes?" ":"0";
				while(value.length() < 7) value = fill_char + value;
			}
			
			Tessellator tess = Tessellator.instance;
			tess.startDrawingQuads();
			for(int j = 0; j < 7; j++) {

				//** Check if this digit is active */
				//** 0x40 is the bit for the left-most digit, with which this starts */
				if((unit.active_digits & (0x40L>>j)) == 0) continue;

				double zOffset = (j - 3) * 0.1D;
				char c = value.charAt(j);
				double u = -1;
				double v = 0;
				if(c == ' ') continue;
				if(c == '.') {u = 0.9; v = 1*line_height;}
				if(c == '-') {u = 0.8; v = 1*line_height;}
				else if(c == 'k') {u = 0.0; v = 1*line_height;}
				else if(c == 'M') {u = 0.1; v = 1*line_height;}
				else if(c == 'G') {u = 0.2; v = 1*line_height;}
				else if(c == 'T') {u = 0.3; v = 1*line_height;}
				else if(c == 'P') {u = 0.4; v = 1*line_height;}
				else if(c == 'E') {u = 0.5; v = 1*line_height;}
				else if(c == 'a') {u = 0.0; v = 2*line_height;}
				else if(c == 'b') {u = 0.1; v = 2*line_height;}
				else if(c == 'c') {u = 0.2; v = 2*line_height;}
				else if(c == 'd') {u = 0.3; v = 2*line_height;}
				else if(c == 'e') {u = 0.4; v = 2*line_height;}
				else if(c == 'f') {u = 0.5; v = 2*line_height;} // i would love to say this sucks, but this is actually surprisingly easy to read and probably the most performant way of doing it
				int charVal = c - '0'; // no string operations, no int parsing, no nothing, we just rawdog this shit
				if(charVal >= 0 && charVal <= 9) {u = 0.1 * charVal; v = 0.0;}
				if(u == -1) {u = 0.8; v = 1*line_height;}
				tess.setNormal(0F, 1F, 0F);
				tess.addVertexWithUV(0.03135, -h + yOffset, w - zOffset, u, v + line_height);
				tess.addVertexWithUV(0.03135, h + yOffset, w - zOffset, u, v);
				tess.addVertexWithUV(0.03135, h + yOffset, -w - zOffset, u + 0.1, v);
				tess.addVertexWithUV(0.03135, -h + yOffset, -w - zOffset, u + 0.1, v + line_height);
			}
			tess.draw();
			
			RenderArcFurnace.fullbright(false);
			
			GL11.glPopMatrix();

			FontRenderer font = Minecraft.getMinecraft().fontRenderer;
			int height = font.FONT_HEIGHT;
			
			if(unit.label != null && !unit.label.isEmpty()) {

				GL11.glTranslated(0.01, 0.3125, 0);
				int width = font.getStringWidth(unit.label);
				float f3 = Math.min(0.0125F, 0.75F / Math.max(width, 1));
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

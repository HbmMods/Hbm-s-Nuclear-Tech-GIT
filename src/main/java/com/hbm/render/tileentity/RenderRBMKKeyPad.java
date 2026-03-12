package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKKeyPad;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKKeyPad.KeyUnit;
import com.hbm.util.ColorUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderRBMKKeyPad extends TileEntitySpecialRenderer {

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
		
		TileEntityRBMKKeyPad keypad = (TileEntityRBMKKeyPad) te;
		
		for(int i = 0; i < 4; i++) {
			KeyUnit key = keypad.keys[i];
			if(!key.active) continue;
			
			boolean glow = key.isPressed;
			float mult = glow ? 1F : 0.65F;
			
			GL11.glPushMatrix();
			GL11.glTranslated(0.25, (i / 2) * -0.5 + 0.25, (i % 2) * -0.5 + 0.25);

			GL11.glColor3f(1F, 1F, 1F);
			this.bindTexture(ResourceManager.rbmk_keypad_tex);
			ResourceManager.rbmk_button.renderPart("Socket");
			
			GL11.glPushMatrix();
			GL11.glTranslated(key.isPressed ? -0.03125 : 0, 0, 0);
			GL11.glColor3f(ColorUtil.fr(key.color) * mult, ColorUtil.fg(key.color) * mult, ColorUtil.fb(key.color) * mult);
			
			if(glow) {
				RenderArcFurnace.fullbright(true);
				GL11.glEnable(GL11.GL_LIGHTING); // we want a glow, but normal lighting should still apply
			}
			ResourceManager.rbmk_button.renderPart("Button");
			if(glow) RenderArcFurnace.fullbright(false);
			
			GL11.glPopMatrix();

			FontRenderer font = Minecraft.getMinecraft().fontRenderer;
			int height = font.FONT_HEIGHT;
			if(key.label != null && !key.label.isEmpty()) {

				GL11.glTranslated(0.01, 0.3125, 0);
				int width = font.getStringWidth(key.label);
				float f3 = Math.min(0.0125F, 0.4F / Math.max(width, 1));
				GL11.glScalef(f3, -f3, f3);
				GL11.glNormal3f(0.0F, 0.0F, -1.0F);
				GL11.glRotatef(90, 0, 1, 0);

				RenderArcFurnace.fullbright(true);
				font.drawString(key.label, - width / 2, - height / 2, 0x00ff00);
				RenderArcFurnace.fullbright(false);
			}
			GL11.glPopMatrix();
		}
		
		GL11.glPopMatrix();
	}
}

package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.gui.GUIScreenRBMKTerminal;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKTerminal;
import com.hbm.util.BobMathUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderRBMKTerminal extends TileEntitySpecialRenderer {

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
		
		TileEntityRBMKTerminal terminal = (TileEntityRBMKTerminal) te;
		
		GL11.glTranslated(0.25, 0, 0);
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.rbmk_terminal_tex);
		ResourceManager.rbmk_terminal.renderAll();
		
		GL11.glTranslated(0.0635, 0.125, 0.0625 * 5.5);

		RenderArcFurnace.fullbright(true);
		FontRenderer font = Minecraft.getMinecraft().fontRenderer;
		int height = font.FONT_HEIGHT;
		float scale = 1F / 250F;
		String prefix = "> ";
		int prefixWidth = font.getStringWidth(prefix);
		int maxWidth = 172;
		int fontColor = terminal.doesRepeat ? 0xff8000 : 0x00ff00;
		
		String suffix = "";
		
		if(Minecraft.getMinecraft().currentScreen instanceof GUIScreenRBMKTerminal && BobMathUtil.getBlink()) {
			suffix = "_";
		}
		int suffixWidth = font.getStringWidth(suffix);
		
		for(int i = 0; i < 18; i++) {
			
			String label = i == 0 ? GUIScreenRBMKTerminal.getWorkingLine() : terminal.history[i - 1];
			if(label == null) label = "";
			
			StringBuilder builder = new StringBuilder(40);
			
			if(i == 0 || !label.isEmpty()) builder.append(prefix);

			int width = prefixWidth;
			for(int j = 0; j < label.length(); j++) {
				char c = label.charAt(j);
				width += font.getCharWidth(c);
				if(width <= maxWidth) {
					builder.append(c);
				} else {
					break;
				}
			}
			
			if(i == 0 && font.getStringWidth(builder.toString()) + suffixWidth <= maxWidth) {
				builder.append(suffix);
			}
			
			GL11.glTranslated(0, 10 * scale, 0);
			
			GL11.glPushMatrix();
			GL11.glScalef(scale, -scale, scale);
			GL11.glNormal3f(0.0F, 0.0F, -1.0F);
			GL11.glRotatef(90, 0, 1, 0);

			font.drawString(builder.toString(), 0, - height / 2, fontColor);
			GL11.glPopMatrix();
		}
		
		RenderArcFurnace.fullbright(false);
		
		GL11.glPopMatrix();
	}
}

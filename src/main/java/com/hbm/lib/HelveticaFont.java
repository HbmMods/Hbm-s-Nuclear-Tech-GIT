package com.hbm.lib;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;

public class HelveticaFont extends FontRenderer {
	
	public HelveticaFont(GameSettings gameSettingsIn, ResourceLocation location, TextureManager textureManagerIn, boolean unicode) {
		super(gameSettingsIn, location, textureManagerIn, unicode);
	}
	
	@Override
	protected float renderUnicodeChar(char ch, boolean italic)
	{
		int i = glyphWidth[ch] & 255;

		if (i == 0)
			return 0.0F;
		else
		{
			bindTexture(locationFontTexture);
			int k = i >>> 4;
			int l = i & 15;
			float f = k;
			float f1 = l + 1;
			float f2 = ch % 16 * 16 + f;
			float f3 = (ch & 255) / 16 * 16;
			float f4 = f1 - f - 0.02F;
			float f5 = italic ? 1.0F : 0.0F;
			GL11.glBegin(5);
			GL11.glTexCoord2f(f2 / 256.0F, f3 / 256.0F);
			GL11.glVertex3f(posX + f5, posY, 0.0F);
			GL11.glTexCoord2f(f2 / 256.0F, (f3 + 15.98F) / 256.0F);
			GL11.glVertex3f(posX - f5, posY + 7.99F, 0.0F);
			GL11.glTexCoord2f((f2 + f4) / 256.0F, f3 / 256.0F);
			GL11.glVertex3f(posX + f4 / 2.0F + f5, posY, 0.0F);
			GL11.glTexCoord2f((f2 + f4) / 256.0F, (f3 + 15.98F) / 256.0F);
			GL11.glVertex3f(posX + f4 / 2.0F - f5, posY + 7.99F, 0.0F);
			GL11.glEnd();
			return (f1 - f) / 2.0F + 1.0F;
		}
	}
	
	/*  thank you, shinoow, for being much smarter than me
	 * 
	 * 	i owe my life to you
	 * 
	 */

}

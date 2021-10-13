package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderCraneConsole extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float interp) {

		GL11.glPushMatrix();
		
		GL11.glTranslatef((float)x + 0.5F, (float)y, (float)z + 0.5F);
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_LIGHTING);
		
		switch(te.getBlockMetadata() - BlockDummyable.offset) {
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}
		
		GL11.glTranslated(0.5, 0, 0);

		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.rbmk_crane_console_tex);
		ResourceManager.rbmk_crane_console.renderPart("Console_Coonsole");
		ResourceManager.rbmk_crane_console.renderPart("JoyStick");
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 1.25, 0.75);
		GL11.glRotated(Math.sin(System.currentTimeMillis() * 0.01 % 360) * 180 / Math.PI * 0.125 + 45, 1, 0, 0);
		GL11.glTranslated(0, -1.25, -0.75);
		ResourceManager.rbmk_crane_console.renderPart("Meter1");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 1.25, 0.25);
		GL11.glRotated(System.currentTimeMillis() / 16 % 360, -1, 0, 0);
		GL11.glTranslated(0, -1.25, -0.25);
		ResourceManager.rbmk_crane_console.renderPart("Meter2");
		GL11.glPopMatrix();
		
		bindTexture(ResourceManager.ks23_tex);
		ResourceManager.rbmk_crane_console.renderPart("Shotgun");
		bindTexture(ResourceManager.mini_nuke_tex);
		ResourceManager.rbmk_crane_console.renderPart("MiniNuke");
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
		GL11.glDisable(GL11.GL_LIGHTING);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
		GL11.glColor3f(0F, 1F, 0F);
		ResourceManager.rbmk_crane_console.renderPart("Lamp1");
		GL11.glColor3f(1F, 1F, 0F);
		ResourceManager.rbmk_crane_console.renderPart("Lamp2");
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopAttrib();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}

}

package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityMachineRadGen;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;

public class RenderRadGen extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
		
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		TileEntityMachineRadGen radgen = (TileEntityMachineRadGen) tileEntity;
		
		switch(tileEntity.getBlockMetadata() - BlockDummyable.offset) {
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}

		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.radgen_tex);

		ResourceManager.radgen.renderPart("Base");
		
		GL11.glPushMatrix();
		if(radgen.isOn) {
			GL11.glTranslated(0, 1.5, 0);
			GL11.glRotatef((System.currentTimeMillis() % 3600) * -0.1F, 1F, 0F, 0F);
			GL11.glTranslated(0, -1.5, 0);
		}
		ResourceManager.radgen.renderPart("Rotor");
		GL11.glPopMatrix();

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
		GL11.glDisable(GL11.GL_LIGHTING);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
		if(radgen.isOn)
			GL11.glColor3f(0F, 1F, 0F);
		else
			GL11.glColor3f(0F, 0.1F, 0F);
		ResourceManager.radgen.renderPart("Light");
		GL11.glColor3f(1F, 1F, 1F);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopAttrib();
		
		int brightness = radgen.getWorldObj().getLightBrightnessForSkyBlocks(MathHelper.floor_double(radgen.xCoord), MathHelper.floor_double(radgen.yCoord), MathHelper.floor_double(radgen.zCoord), 0);
		int lX = brightness % 65536;
		int lY = brightness / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)lX / 1.0F, (float)lY / 1.0F);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDepthMask(false);

		GL11.glColor4f(0.5F, 0.75F, 1F, 0.3F);
		ResourceManager.radgen.renderPart("Glass");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		GL11.glDepthMask(true);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		GL11.glDisable(GL11.GL_BLEND);
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		ResourceManager.radgen.renderPart("Glass");
		
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPopMatrix();
	}
}

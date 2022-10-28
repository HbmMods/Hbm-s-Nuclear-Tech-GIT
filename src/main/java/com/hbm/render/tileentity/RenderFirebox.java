package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityHeaterFirebox;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderFirebox extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		switch(tile.getBlockMetadata() - BlockDummyable.offset) {
		case 3: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 2: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(270, 0F, 1F, 0F); break;
		}
		GL11.glRotatef(-90, 0F, 1F, 0F);
		
		TileEntityHeaterFirebox firebox = (TileEntityHeaterFirebox) tile;
		
		bindTexture(ResourceManager.heater_firebox_tex);
		ResourceManager.heater_firebox.renderPart("Main");
		
		GL11.glPushMatrix();
		float door = firebox.prevDoorAngle + (firebox.doorAngle - firebox.prevDoorAngle) * interp;
		GL11.glTranslated(1.375, 0, 0.375);
		GL11.glRotatef(door, 0F, -1F, 0F);
		GL11.glTranslated(-1.375, 0, -0.375);
		ResourceManager.heater_firebox.renderPart("Door");
		GL11.glPopMatrix();
		
		if(firebox.wasOn) {
			GL11.glPushMatrix();
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
			
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_CULL_FACE);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
			ResourceManager.heater_firebox.renderPart("InnerBurning");
			GL11.glEnable(GL11.GL_LIGHTING);
			
			GL11.glPopAttrib();
			GL11.glPopMatrix();
		} else {
			ResourceManager.heater_firebox.renderPart("InnerEmpty");
		}
		
		GL11.glPopMatrix();
	}
}

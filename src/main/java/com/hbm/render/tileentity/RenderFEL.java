package com.hbm.render.tileentity;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.items.machine.ItemFELCrystal.EnumWavelengths;
import com.hbm.main.ResourceManager;
import com.hbm.render.util.BeamPronter;
import com.hbm.render.util.BeamPronter.EnumBeamType;
import com.hbm.render.util.BeamPronter.EnumWaveType;
import com.hbm.tileentity.machine.TileEntityFEL;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;

public class RenderFEL extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
		
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		switch(tileEntity.getBlockMetadata() - BlockDummyable.offset) {
		case 4: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 2: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.fel_tex);
		ResourceManager.fel.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);
		
		TileEntityFEL fel = (TileEntityFEL) tileEntity;
		int color = 0xffffff;
		
		if(fel.mode.renderedBeamColor == 0) {
			color = Color.HSBtoRGB(fel.getWorldObj().getTotalWorldTime() / 50.0F, 0.5F, 0.1F) & 16777215;
		} else {
			color = fel.mode.renderedBeamColor;
		}
		int length = fel.distance - 3;
		GL11.glTranslated(0, 1.5, -1.5);
		if(fel.power > fel.powerReq * Math.pow(2, fel.mode.ordinal()) && fel.isOn && !(fel.mode == EnumWavelengths.NULL) && length > 0) {
			BeamPronter.prontBeamwithDepth(Vec3.createVectorHelper(0, 0, -length - 1), EnumWaveType.SPIRAL, EnumBeamType.SOLID, color, color, 0, 1, 0F, 2, 0.0625F);
			BeamPronter.prontBeamwithDepth(Vec3.createVectorHelper(0, 0, -length - 1), EnumWaveType.RANDOM, EnumBeamType.SOLID, color, color, (int)(tileEntity.getWorldObj().getTotalWorldTime() % 1000 / 2), (length / 2) + 1, 0.0625F, 2, 0.0625F);
		}
		
		GL11.glPopMatrix();
	}
}
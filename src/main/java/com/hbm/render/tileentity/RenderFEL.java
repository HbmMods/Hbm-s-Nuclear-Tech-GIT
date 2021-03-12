package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.ResourceManager;
import com.hbm.render.util.BeamPronter;
import com.hbm.render.util.BeamPronter.EnumBeamType;
import com.hbm.render.util.BeamPronter.EnumWaveType;

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

		GL11.glTranslated(0, 1.5, -2.5 + 0.0625);
		BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, -8), EnumWaveType.SPIRAL, EnumBeamType.SOLID, 0x400000, 0x400000, 0, 1, 0F, 2, 0.0625F);
		BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, -8), EnumWaveType.RANDOM, EnumBeamType.SOLID, 0x400000, 0x400000, (int)(tileEntity.getWorldObj().getTotalWorldTime() % 1000 / 2), 24, 0.0625F, 2, 0.0625F);

		GL11.glPopMatrix();
	}
}

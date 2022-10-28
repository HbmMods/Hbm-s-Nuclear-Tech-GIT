package com.hbm.render.tileentity;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.generic.BlockEmitter.TileEntityEmitter;
import com.hbm.render.util.BeamPronter;
import com.hbm.render.util.BeamPronter.EnumBeamType;
import com.hbm.render.util.BeamPronter.EnumWaveType;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;

public class RenderEmitter extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {

		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);

		GL11.glRotatef(90, 0F, 1F, 0F);

		switch(tileEntity.getBlockMetadata()) {
		case 0:
			GL11.glTranslated(0.0D, 0.5D, -0.5D);
			GL11.glRotatef(90, 1F, 0F, 0F); break;
		case 1:
			GL11.glTranslated(0.0D, 0.5D, 0.5D);
			GL11.glRotatef(90, -1F, 0F, 0F); break;
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}
		
		GL11.glTranslated(0, 0.5, 0.5);
		TileEntityEmitter emitter = (TileEntityEmitter) tileEntity;
		int range = emitter.beam - 1;
		int originalColor = emitter.color == 0 ? Color.HSBtoRGB(tileEntity.getWorldObj().getTotalWorldTime() / 50.0F, 0.5F, 0.25F) & 16777215 : emitter.color;
		float girth = emitter.girth;
		int r = (originalColor & 0xff0000) >> 16;
		int g = (originalColor & 0x00ff00) >> 8;
		int b = (originalColor & 0x0000ff);
		float innerMult = 0.85F;
		float outerMult = 0.1F;
		int colorInner = ((int)(r * innerMult) << 16) | ((int)(g * innerMult) << 8) | ((int)(b * innerMult));
		int colorOuter = ((int)(r * outerMult) << 16) | ((int)(g * outerMult) << 8) | ((int)(b * outerMult));

		if(range > 0) {
			
			int segments = (int)Math.max(Math.sqrt(girth * 50), 2);
			BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, range), EnumWaveType.SPIRAL, EnumBeamType.SOLID, colorOuter, colorInner, 0, 1, 0F, segments, girth);
			
			if(emitter.effect == 1) {
				BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, range), EnumWaveType.RANDOM, EnumBeamType.SOLID, colorOuter, colorInner, (int) tileEntity.getWorldObj().getTotalWorldTime() / 2, (int)Math.max(range / girth / 2, 1), girth * 2, 4, girth * 0.1F);
				BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, range), EnumWaveType.RANDOM, EnumBeamType.SOLID, colorOuter, colorInner, (int) tileEntity.getWorldObj().getTotalWorldTime() / 2 + 15, (int)Math.max(range / girth / 4, 1), girth * 2, 4, girth * 0.1F);		
			}

			if(emitter.effect == 2) {
				BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, range), EnumWaveType.SPIRAL, EnumBeamType.SOLID, colorOuter, colorInner, (int) (tileEntity.getWorldObj().getTotalWorldTime() + f) * -10 % 360, (int)Math.max(range / girth / 2, 1), girth * 2, 4, girth * 0.1F);
				BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, range), EnumWaveType.SPIRAL, EnumBeamType.SOLID, colorOuter, colorInner, (int) (tileEntity.getWorldObj().getTotalWorldTime() + f) * -10 % 360 + 180, (int)Math.max(range / girth / 2, 1), girth * 2, 4, girth * 0.1F);		
			}
			if(emitter.effect == 3) {
				BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, range), EnumWaveType.SPIRAL, EnumBeamType.SOLID, colorOuter, colorInner, (int) (tileEntity.getWorldObj().getTotalWorldTime() + f) * -10 % 360, (int)Math.max(range / girth / 2, 1), girth * 2, 4, girth * 0.1F);
				BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, range), EnumWaveType.SPIRAL, EnumBeamType.SOLID, colorOuter, colorInner, (int) (tileEntity.getWorldObj().getTotalWorldTime() + f) * -10 % 360 + 120, (int)Math.max(range / girth / 2, 1), girth * 2, 4, girth * 0.1F);		
				BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, range), EnumWaveType.SPIRAL, EnumBeamType.SOLID, colorOuter, colorInner, (int) (tileEntity.getWorldObj().getTotalWorldTime() + f) * -10 % 360 + 240, (int)Math.max(range / girth / 2, 1), girth * 2, 4, girth * 0.1F);		
			}
		}

		GL11.glEnable(GL11.GL_LIGHTING);

		GL11.glPopMatrix();
	}
}

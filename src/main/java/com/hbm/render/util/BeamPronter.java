package com.hbm.render.util;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class BeamPronter {

	public static enum EnumWaveType {
		RANDOM, SPIRAL
	}

	public static enum EnumBeamType {
		SOLID, LINE
	}

	public static void prontBeam(Vec3 skeleton, EnumWaveType wave, EnumBeamType beam, int outerColor, int innerColor, int start, int segments, float size, int layers, float thickness) {

		GL11.glPushMatrix();
		GL11.glDepthMask(false);

		float sYaw = (float) (Math.atan2(skeleton.xCoord, skeleton.zCoord) * 180F / Math.PI);
		float sqrt = MathHelper.sqrt_double(skeleton.xCoord * skeleton.xCoord + skeleton.zCoord * skeleton.zCoord);
		float sPitch = (float) (Math.atan2(skeleton.yCoord, (double) sqrt) * 180F / Math.PI);

		GL11.glRotatef(180, 0, 1F, 0);
		GL11.glRotatef(sYaw, 0, 1F, 0);
		GL11.glRotatef(sPitch - 90, 1F, 0, 0);

		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);

		if(beam == EnumBeamType.SOLID) {
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		}

		Tessellator tessellator = Tessellator.instance;

		Vec3 unit = Vec3.createVectorHelper(0, 1, 0);
		Random rand = new Random(start);
		double length = skeleton.lengthVector();
		double segLength = length / segments;
		double lastX = 0;
		double lastY = 0;
		double lastZ = 0;

		for(int i = 0; i <= segments; i++) {

			Vec3 spinner = Vec3.createVectorHelper(size, 0, 0);

			if(wave == EnumWaveType.SPIRAL) {
				spinner.rotateAroundY((float) Math.PI * (float) start / 180F);
				spinner.rotateAroundY((float) Math.PI * 45F / 180F * i);
			} else if(wave == EnumWaveType.RANDOM) {
				spinner.rotateAroundY((float) Math.PI * 4 * rand.nextFloat());
			}

			double pX = unit.xCoord * segLength * i + spinner.xCoord;
			double pY = unit.yCoord * segLength * i + spinner.yCoord;
			double pZ = unit.zCoord * segLength * i + spinner.zCoord;

			if(beam == EnumBeamType.LINE && i > 0) {

				tessellator.startDrawing(3);
				tessellator.setColorOpaque_I(outerColor);
				tessellator.addVertex(pX, pY, pZ);
				tessellator.addVertex(lastX, lastY, lastZ);
				tessellator.draw();
			}

			if(beam == EnumBeamType.SOLID && i > 0) {

				float radius = thickness / layers;

				for(int j = 1; j <= layers; j++) {

					float inter = (float) (j - 1) / (float) (layers - 1);
					int color = (int) (outerColor + (innerColor - outerColor) * inter);

					tessellator.startDrawingQuads();
					tessellator.setColorOpaque_I(color);
					tessellator.addVertex(lastX + (radius * j), lastY, lastZ + (radius * j));
					tessellator.addVertex(lastX + (radius * j), lastY, lastZ - (radius * j));
					tessellator.addVertex(pX + (radius * j), pY, pZ - (radius * j));
					tessellator.addVertex(pX + (radius * j), pY, pZ + (radius * j));
					tessellator.draw();
					tessellator.startDrawingQuads();
					tessellator.setColorOpaque_I(color);
					tessellator.addVertex(lastX - (radius * j), lastY, lastZ + (radius * j));
					tessellator.addVertex(lastX - (radius * j), lastY, lastZ - (radius * j));
					tessellator.addVertex(pX - (radius * j), pY, pZ - (radius * j));
					tessellator.addVertex(pX - (radius * j), pY, pZ + (radius * j));
					tessellator.draw();
					tessellator.startDrawingQuads();
					tessellator.setColorOpaque_I(color);
					tessellator.addVertex(lastX + (radius * j), lastY, lastZ + (radius * j));
					tessellator.addVertex(lastX - (radius * j), lastY, lastZ + (radius * j));
					tessellator.addVertex(pX - (radius * j), pY, pZ + (radius * j));
					tessellator.addVertex(pX + (radius * j), pY, pZ + (radius * j));
					tessellator.draw();
					tessellator.startDrawingQuads();
					tessellator.setColorOpaque_I(color);
					tessellator.addVertex(lastX + (radius * j), lastY, lastZ - (radius * j));
					tessellator.addVertex(lastX - (radius * j), lastY, lastZ - (radius * j));
					tessellator.addVertex(pX - (radius * j), pY, pZ - (radius * j));
					tessellator.addVertex(pX + (radius * j), pY, pZ - (radius * j));
					tessellator.draw();
				}
			}

			lastX = pX;
			lastY = pY;
			lastZ = pZ;
		}

		if(beam == EnumBeamType.LINE) {

			tessellator.startDrawing(3);
			tessellator.setColorOpaque_I(innerColor);
			tessellator.addVertex(0, 0, 0);
			tessellator.addVertex(0, skeleton.lengthVector(), 0);
			tessellator.draw();
		}

		if(beam == EnumBeamType.SOLID) {
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
		}

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();
		GL11.glDepthMask(true);

		GL11.glPopMatrix();
	}

}

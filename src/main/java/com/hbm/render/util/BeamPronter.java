package com.hbm.render.util;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class BeamPronter {
	
	public static Random rand = new Random();

	public static enum EnumWaveType {
		RANDOM, SPIRAL
	}

	public static enum EnumBeamType {
		SOLID, LINE
	}
	
	/* Beams drawn mid-frame get the wrong occlusion against tile entity / entity models drawn later in the frame (#2383),
	 * so beams pronted during world rendering are queued and replayed on RenderWorldLast, where the depth buffer is complete. */
	private static boolean inWorldRender = false;
	private static final List<QueuedBeam> deferredBeams = new ArrayList<QueuedBeam>();
	private static final FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

	private static class QueuedBeam {
		float[] matrix = new float[16];
		Vec3 skeleton;
		EnumWaveType wave;
		EnumBeamType beam;
		int outerColor, innerColor, start, segments, layers;
		float size, thickness;
	}

	public static void beginWorldRender() {
		inWorldRender = true;
	}

	public static void flushDeferred() {
		inWorldRender = false;
		if(deferredBeams.isEmpty()) return;

		GL11.glPushAttrib(GL11.GL_ENABLE_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_COLOR_BUFFER_BIT | GL11.GL_LIGHTING_BIT);
		GL11.glEnable(GL11.GL_DEPTH_TEST);

		for(QueuedBeam queued : deferredBeams) {
			GL11.glPushMatrix();
			matrixBuffer.rewind();
			matrixBuffer.put(queued.matrix);
			matrixBuffer.rewind();
			GL11.glLoadMatrix(matrixBuffer);
			// depth writes stay off: additive beams occlude nothing, the depth test alone handles occlusion correctly
			depthMask = false;
			prontBeam(queued.skeleton, queued.wave, queued.beam, queued.outerColor, queued.innerColor, queued.start, queued.segments, queued.size, queued.layers, queued.thickness);
			GL11.glPopMatrix();
		}

		deferredBeams.clear();
		GL11.glPopAttrib();
	}

	private static boolean depthMask = false;
	public static void prontBeamwithDepth(Vec3 skeleton, EnumWaveType wave, EnumBeamType beam, int outerColor, int innerColor, int start, int segments, float size, int layers, float thickness) {
		depthMask = true;
		prontBeam(skeleton, wave, beam, outerColor, innerColor, start, segments, size, layers, thickness);
		depthMask = false;
	}

	public static void prontBeam(Vec3 skeleton, EnumWaveType wave, EnumBeamType beam, int outerColor, int innerColor, int start, int segments, float size, int layers, float thickness) {

		if(inWorldRender) {
			QueuedBeam queued = new QueuedBeam();
			matrixBuffer.rewind();
			GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, matrixBuffer);
			matrixBuffer.get(queued.matrix);
			queued.skeleton = skeleton;
			queued.wave = wave;
			queued.beam = beam;
			queued.outerColor = outerColor;
			queued.innerColor = innerColor;
			queued.start = start;
			queued.segments = segments;
			queued.size = size;
			queued.layers = layers;
			queued.thickness = thickness;
			deferredBeams.add(queued);
			return;
		}

		GL11.glPushMatrix();
		GL11.glDepthMask(depthMask);

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
		rand.setSeed(start);
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
				spinner.rotateAroundY((float) Math.PI * 2 * rand.nextFloat());
				spinner.rotateAroundY((float) Math.PI * 2 * rand.nextFloat());
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

					int r1 = ((outerColor & 0xFF0000) >> 16);
					int g1 = ((outerColor & 0x00FF00) >> 8);
					int b1 = ((outerColor & 0x0000FF) >> 0);
					
					int r2 = ((innerColor & 0xFF0000) >> 16);
					int g2 = ((innerColor & 0x00FF00) >> 8);
					int b2 = ((innerColor & 0x0000FF) >> 0);

					int r = ((int)(r1 + (r2 - r1) * inter)) << 16;
					int g = ((int)(g1 + (g2 - g1) * inter)) << 8;
					int b = ((int)(b1 + (b2 - b1) * inter)) << 0;
					
					int color = r | g | b;

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

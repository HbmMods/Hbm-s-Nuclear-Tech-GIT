package com.hbm.render.util;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class BeamPronter {
	
	public static enum EnumWaveType {
		RANDOM,
		SPIRAL
	}
	
	public static enum EnumBeamType {
		SOLID,
		LINE
	}
	
	public static void prontHelix(Vec3 skeleton, double x, double y, double z, EnumWaveType wave, EnumBeamType beam, int outerColor, int innerColor, int start, int segments, float size) {

		GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LIGHTING);

		if(beam == EnumBeamType.SOLID) {
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		}
        
		Tessellator tessellator = Tessellator.instance;

		Vec3 unit = skeleton.normalize();
		Random rand = new Random(start);
		double length = skeleton.lengthVector();
		double segLength = length / segments;
		
		float sYaw = (float)(Math.atan2(skeleton.xCoord, skeleton.zCoord));
        float sqrt = MathHelper.sqrt_double(skeleton.xCoord * skeleton.xCoord + skeleton.zCoord * skeleton.zCoord);
		float sPitch = (float)(Math.atan2(skeleton.yCoord, (double)sqrt));
		
		double lastX = x;
		double lastY = y;
		double lastZ = z;
		
		for(int i = 0; i <= segments; i++) {
			
			Vec3 spinner = Vec3.createVectorHelper(size, 0, 0);
			
			if(wave == EnumWaveType.SPIRAL) {
				spinner.rotateAroundY((float)Math.PI * (float)start / 180F);
				spinner.rotateAroundY((float)Math.PI * 45F / 180F * i);
			} else if(wave == EnumWaveType.RANDOM) {
				spinner.rotateAroundY((float)Math.PI * 2 * rand.nextFloat());
			}
			
			spinner.rotateAroundX(sPitch + (float)Math.PI * 0.5F);
			spinner.rotateAroundY(sYaw);

			double pX = unit.xCoord * segLength * i + spinner.xCoord;
			double pY = unit.yCoord * segLength * i + spinner.yCoord;
			double pZ = unit.zCoord * segLength * i + spinner.zCoord;
			
			if(beam == EnumBeamType.LINE && i > 0) {

	            tessellator.startDrawing(3);
	            tessellator.setColorOpaque_I(outerColor);
	            tessellator.addVertex(pX + x, pY + y, pZ + z);
	            tessellator.addVertex(lastX + x, lastY + y, lastZ + z);
	            tessellator.draw();
			}
			
			if(beam == EnumBeamType.SOLID && i > 0) {
				
			}
			
			lastX = pX;
			lastY = pY;
			lastZ = pZ;
		}
		
		if(beam == EnumBeamType.LINE) {

            tessellator.startDrawing(3);
            tessellator.setColorOpaque_I(innerColor);
            tessellator.addVertex(x, y, z);
            tessellator.addVertex(x + skeleton.xCoord, y + skeleton.yCoord, z + skeleton.zCoord);
            tessellator.draw();
		}

		if(beam == EnumBeamType.SOLID) {
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
		}
		
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();
	}

}

package com.hbm.hrist;

import com.hbm.interfaces.NotableComments;
import com.hbm.util.Vec3NT;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;

@NotableComments
public class ConduitRenderUtil {

	 // public cum rags
	private static Vec3NT vec = new Vec3NT(0, 0, 0);
	private static double[][] v = new double[4][3];
	
	public static void renderSupport(Tessellator tess, IIcon icon, double x, double y, double z, double rotation) {
		double minU = icon.getMinU();
		double minV = icon.getMinV();
		double pu = (icon.getMaxU() - icon.getMinU()) / 32D;
		double pv = (icon.getMaxV() - icon.getMinV()) / 32D;
		
		// work smort, not hort
		vec.setTurn(01, 0, 00.1875, rotation).add(x, y, z).copyToArray(v[0]);
		vec.setTurn(01, 0, -0.1875, rotation).add(x, y, z).copyToArray(v[1]);
		vec.setTurn(-1, 0, -0.1875, rotation).add(x, y, z).copyToArray(v[2]);
		vec.setTurn(-1, 0, 00.1875, rotation).add(x, y, z).copyToArray(v[3]);

		setBrightnessFromNormals(tess, 0F, 1F, 0F);
		tess.addVertexWithUV(v[0][0], v[0][1] + 0.0625, v[0][2], minU + pu * 0, minV + pv * 32);
		tess.addVertexWithUV(v[1][0], v[1][1] + 0.0625, v[1][2], minU + pu * 6, minV + pv * 32);
		tess.addVertexWithUV(v[2][0], v[2][1] + 0.0625, v[2][2], minU + pu * 6, minV + pv * 00);
		tess.addVertexWithUV(v[3][0], v[3][1] + 0.0625, v[3][2], minU + pu * 0, minV + pv * 00);

		setBrightnessFromNormals(tess, 0F, -1F, 0F);
		tess.addVertexWithUV(v[1][0], v[1][1], v[1][2], minU + pu * 0, minV + pv * 32);
		tess.addVertexWithUV(v[0][0], v[0][1], v[0][2], minU + pu * 6, minV + pv * 32);
		tess.addVertexWithUV(v[3][0], v[3][1], v[3][2], minU + pu * 6, minV + pv * 00);
		tess.addVertexWithUV(v[2][0], v[2][1], v[2][2], minU + pu * 0, minV + pv * 00);
		
		vec.setTurn(1, 0, 0, rotation).normalizeSelf();
		setBrightnessFromNormals(tess, vec.xCoord, vec.yCoord, vec.zCoord);
		tess.addVertexWithUV(v[1][0], v[1][1] + 0.0625, v[1][2], minU + pu * 0, minV + pv * 0);
		tess.addVertexWithUV(v[0][0], v[0][1] + 0.0625, v[0][2], minU + pu * 6, minV + pv * 0);
		tess.addVertexWithUV(v[0][0], v[0][1] + 000000, v[0][2], minU + pu * 6, minV + pv * 1);
		tess.addVertexWithUV(v[1][0], v[1][1] + 000000, v[1][2], minU + pu * 0, minV + pv * 1);
		
		vec.setTurn(1, 0, 0, rotation + 180).normalizeSelf();
		setBrightnessFromNormals(tess, vec.xCoord, vec.yCoord, vec.zCoord);
		tess.addVertexWithUV(v[3][0], v[3][1] + 0.0625, v[3][2], minU + pu * 0, minV + pv * 0);
		tess.addVertexWithUV(v[2][0], v[2][1] + 0.0625, v[2][2], minU + pu * 6, minV + pv * 0);
		tess.addVertexWithUV(v[2][0], v[2][1] + 000000, v[2][2], minU + pu * 6, minV + pv * 1);
		tess.addVertexWithUV(v[3][0], v[3][1] + 000000, v[3][2], minU + pu * 0, minV + pv * 1);
		
		vec.setTurn(1, 0, 0, rotation + 90).normalizeSelf();
		setBrightnessFromNormals(tess, vec.xCoord, vec.yCoord, vec.zCoord);
		tess.addVertexWithUV(v[2][0], v[2][1] + 0.0625, v[2][2], minU + pu * 6, minV + pv * 00);
		tess.addVertexWithUV(v[1][0], v[1][1] + 0.0625, v[1][2], minU + pu * 6, minV + pv * 32);
		tess.addVertexWithUV(v[1][0], v[1][1] + 000000, v[1][2], minU + pu * 7, minV + pv * 32);
		tess.addVertexWithUV(v[2][0], v[2][1] + 000000, v[2][2], minU + pu * 7, minV + pv * 00);
		
		vec.setTurn(1, 0, 0, rotation + 270).normalizeSelf();
		setBrightnessFromNormals(tess, vec.xCoord, vec.yCoord, vec.zCoord);
		tess.addVertexWithUV(v[0][0], v[0][1] + 0.0625, v[0][2], minU + pu * 6, minV + pv * 00);
		tess.addVertexWithUV(v[3][0], v[3][1] + 0.0625, v[3][2], minU + pu * 6, minV + pv * 32);
		tess.addVertexWithUV(v[3][0], v[3][1] + 000000, v[3][2], minU + pu * 7, minV + pv * 32);
		tess.addVertexWithUV(v[0][0], v[0][1] + 000000, v[0][2], minU + pu * 7, minV + pv * 00);
		
		for(int i = 0; i < 2; i++) {
			vec.setTurn(0.9375 - i * 1.5625, 0, 00.0625, rotation).add(x, y, z).copyToArray(v[0]);
			vec.setTurn(0.9375 - i * 1.5625, 0, -0.0625, rotation).add(x, y, z).copyToArray(v[1]);
			vec.setTurn(0.6250 - i * 1.5625, 0, -0.0625, rotation).add(x, y, z).copyToArray(v[2]);
			vec.setTurn(0.6250 - i * 1.5625, 0, 00.0625, rotation).add(x, y, z).copyToArray(v[3]);
			
			setBrightnessFromNormals(tess, 0F, 1F, 0F);
			tess.addVertexWithUV(v[0][0], v[0][1] + 0.125, v[0][2], minU + pu * 19, minV + pv * 31);
			tess.addVertexWithUV(v[1][0], v[1][1] + 0.125, v[1][2], minU + pu * 19, minV + pv * 29);
			tess.addVertexWithUV(v[2][0], v[2][1] + 0.125, v[2][2], minU + pu * 14, minV + pv * 29);
			tess.addVertexWithUV(v[3][0], v[3][1] + 0.125, v[3][2], minU + pu * 14, minV + pv * 31);
			
			vec.setTurn(1, 0, 0, rotation).normalizeSelf();
			setBrightnessFromNormals(tess, vec.xCoord, vec.yCoord, vec.zCoord);
			tess.addVertexWithUV(v[1][0], v[1][1] + 0.1250, v[1][2], minU + pu * 14, minV + pv * 29);
			tess.addVertexWithUV(v[0][0], v[0][1] + 0.1250, v[0][2], minU + pu * 14, minV + pv * 31);
			tess.addVertexWithUV(v[0][0], v[0][1] + 0.0625, v[0][2], minU + pu * 13, minV + pv * 31);
			tess.addVertexWithUV(v[1][0], v[1][1] + 0.0625, v[1][2], minU + pu * 13, minV + pv * 29);
			
			vec.setTurn(1, 0, 0, rotation + 180).normalizeSelf();
			setBrightnessFromNormals(tess, vec.xCoord, vec.yCoord, vec.zCoord);
			tess.addVertexWithUV(v[3][0], v[3][1] + 0.1250, v[3][2], minU + pu * 14, minV + pv * 29);
			tess.addVertexWithUV(v[2][0], v[2][1] + 0.1250, v[2][2], minU + pu * 14, minV + pv * 31);
			tess.addVertexWithUV(v[2][0], v[2][1] + 0.0625, v[2][2], minU + pu * 13, minV + pv * 31);
			tess.addVertexWithUV(v[3][0], v[3][1] + 0.0625, v[3][2], minU + pu * 13, minV + pv * 29);
			
			vec.setTurn(1, 0, 0, rotation + 90).normalizeSelf();
			setBrightnessFromNormals(tess, vec.xCoord, vec.yCoord, vec.zCoord);
			tess.addVertexWithUV(v[2][0], v[2][1] + 0.1250, v[2][2], minU + pu * 14, minV + pv * 31);
			tess.addVertexWithUV(v[1][0], v[1][1] + 0.1250, v[1][2], minU + pu * 19, minV + pv * 31);
			tess.addVertexWithUV(v[1][0], v[1][1] + 0.0625, v[1][2], minU + pu * 19, minV + pv * 32);
			tess.addVertexWithUV(v[2][0], v[2][1] + 0.0625, v[2][2], minU + pu * 14, minV + pv * 32);
			
			vec.setTurn(1, 0, 0, rotation + 270).normalizeSelf();
			setBrightnessFromNormals(tess, vec.xCoord, vec.yCoord, vec.zCoord);
			tess.addVertexWithUV(v[0][0], v[0][1] + 0.1250, v[0][2], minU + pu * 14, minV + pv * 31);
			tess.addVertexWithUV(v[3][0], v[3][1] + 0.1250, v[3][2], minU + pu * 19, minV + pv * 31);
			tess.addVertexWithUV(v[3][0], v[3][1] + 0.0625, v[3][2], minU + pu * 19, minV + pv * 32);
			tess.addVertexWithUV(v[0][0], v[0][1] + 0.0625, v[0][2], minU + pu * 14, minV + pv * 32);
		}
	}
	
	public static void renderSteel(Tessellator tess, IIcon icon, double x0, double y0, double z0, double r0, double x1, double y1, double z1, double r1) {
		double minU = icon.getMinU();
		double minV = icon.getMinV();
		double pu = (icon.getMaxU() - icon.getMinU()) / 32D;
		double pv = (icon.getMaxV() - icon.getMinV()) / 32D;
		
		double avg = (r0 + r1) / 2;

		vec.setTurn(-0.03125, 0, 0, r0).add(x0, y0, z0).copyToArray(v[0]);
		vec.setTurn(00.03125, 0, 0, r0).add(x0, y0, z0).copyToArray(v[1]);
		vec.setTurn(00.03125, 0, 0, r1).add(x1, y1, z1).copyToArray(v[2]);
		vec.setTurn(-0.03125, 0, 0, r1).add(x1, y1, z1).copyToArray(v[3]);

		setBrightnessFromNormals(tess, 0F, 1F, 0F);
		tess.addVertexWithUV(v[0][0], v[0][1] + 0.1875, v[0][2], minU + pu * 10, minV + pv * 32);
		tess.addVertexWithUV(v[1][0], v[1][1] + 0.1875, v[1][2], minU + pu * 11, minV + pv * 32);
		tess.addVertexWithUV(v[2][0], v[2][1] + 0.1875, v[2][2], minU + pu * 11, minV + pv * 16);
		tess.addVertexWithUV(v[3][0], v[3][1] + 0.1875, v[3][2], minU + pu * 10, minV + pv * 16);

		setBrightnessFromNormals(tess, 0F, -1F, 0F);
		tess.addVertexWithUV(v[1][0], v[1][1] + 0.0625, v[1][2], minU + pu * 7, minV + pv * 32);
		tess.addVertexWithUV(v[0][0], v[0][1] + 0.0625, v[0][2], minU + pu * 8, minV + pv * 32);
		tess.addVertexWithUV(v[3][0], v[3][1] + 0.0625, v[3][2], minU + pu * 8, minV + pv * 16);
		tess.addVertexWithUV(v[2][0], v[2][1] + 0.0625, v[2][2], minU + pu * 7, minV + pv * 16);
		
		vec.setTurn(1, 0, 0, avg).normalizeSelf();
		setBrightnessFromNormals(tess, vec.xCoord, vec.yCoord, vec.zCoord);
		tess.addVertexWithUV(v[1][0], v[1][1] + 0.1875, v[1][2], minU + pu * 10, minV + pv * 16);
		tess.addVertexWithUV(v[0][0], v[0][1] + 0.1875, v[0][2], minU + pu * 11, minV + pv * 16);
		tess.addVertexWithUV(v[0][0], v[0][1] + 0.0625, v[0][2], minU + pu * 11, minV + pv * 14);
		tess.addVertexWithUV(v[1][0], v[1][1] + 0.0625, v[1][2], minU + pu * 10, minV + pv * 14);
		
		vec.setTurn(1, 0, 0, avg + 180).normalizeSelf();
		setBrightnessFromNormals(tess, vec.xCoord, vec.yCoord, vec.zCoord);
		tess.addVertexWithUV(v[3][0], v[3][1] + 0.1875, v[3][2], minU + pu * 10, minV + pv * 16);
		tess.addVertexWithUV(v[2][0], v[2][1] + 0.1875, v[2][2], minU + pu * 11, minV + pv * 16);
		tess.addVertexWithUV(v[2][0], v[2][1] + 0.0625, v[2][2], minU + pu * 11, minV + pv * 14);
		tess.addVertexWithUV(v[3][0], v[3][1] + 0.0625, v[3][2], minU + pu * 10, minV + pv * 14);
		
		vec.setTurn(1, 0, 0, avg + 90).normalizeSelf();
		setBrightnessFromNormals(tess, vec.xCoord, vec.yCoord, vec.zCoord);
		tess.addVertexWithUV(v[2][0], v[2][1] + 0.1875, v[2][2], minU + pu * 10, minV + pv * 16);
		tess.addVertexWithUV(v[1][0], v[1][1] + 0.1875, v[1][2], minU + pu * 10, minV + pv * 32);
		tess.addVertexWithUV(v[1][0], v[1][1] + 0.0625, v[1][2], minU + pu *  8, minV + pv * 32);
		tess.addVertexWithUV(v[2][0], v[2][1] + 0.0625, v[2][2], minU + pu *  8, minV + pv * 16);
		
		vec.setTurn(1, 0, 0, avg + 270).normalizeSelf();
		setBrightnessFromNormals(tess, vec.xCoord, vec.yCoord, vec.zCoord);
		tess.addVertexWithUV(v[0][0], v[0][1] + 0.1875, v[0][2], minU + pu * 11, minV + pv * 16);
		tess.addVertexWithUV(v[3][0], v[3][1] + 0.1875, v[3][2], minU + pu * 11, minV + pv * 32);
		tess.addVertexWithUV(v[3][0], v[3][1] + 0.0625, v[3][2], minU + pu * 13, minV + pv * 32);
		tess.addVertexWithUV(v[0][0], v[0][1] + 0.0625, v[0][2], minU + pu * 13, minV + pv * 16);
	}
	
	public static void setBrightnessFromNormals(Tessellator tess, double x, double y, double z) {
		
		float brightness = ((float) y + 0.7F) * 0.9F - (float) Math.abs(x) * 0.1F + (float) Math.abs(z) * 0.1F;
		if(brightness < 0.45F) brightness = 0.45F;
		
		tess.setNormal((float) x, (float) y, (float) z);
		tess.setColorOpaque_F(brightness, brightness, brightness);
	}
}

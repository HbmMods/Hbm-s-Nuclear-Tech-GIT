package com.hbm.render.block.ct;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;

public class RenderBlocksCT extends RenderBlocks {

	VertInfo tl;
	VertInfo tc;
	VertInfo tr;
	VertInfo cl;
	VertInfo cc;
	VertInfo cr;
	VertInfo bl;
	VertInfo bc;
	VertInfo br;
	
	Tessellator tess;
	
	public RenderBlocksCT() {
		super();
		this.tess = Tessellator.instance;
	}
	
	private void initSideInfo() {
		
		if(!this.enableAO)
			return;

		this.tl = new VertInfo(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft, this.brightnessTopLeft);
		this.tr = new VertInfo(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight, this.brightnessTopRight);
		this.bl = new VertInfo(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft, this.brightnessBottomLeft);
		this.br = new VertInfo(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight, this.brightnessBottomRight);

		this.tc = VertInfo.avg(tl, tr);
		this.bc = VertInfo.avg(bl, br);
		this.cl = VertInfo.avg(tl, bl);
		this.cr = VertInfo.avg(tr, br);
		
		this.cc = VertInfo.avg(tl, tr, bl, br);
	}
	
	private void drawFace(double[] ftl, double[] ftr, double[] fbl, double[] fbr) {
		
		double[] ftc = avgCoords(ftl, ftr);
		///TODO///
	}
	
	private void drawVert(double x, double y, double z, double u, double v, VertInfo info) {
		
		if(this.enableAO) {
			tess.setColorOpaque_F(info.red, info.blue, info.green);
			tess.setBrightness(info.brightness);
		}
		
		tess.addVertexWithUV(x, y, z, u, v);
	}
	
	private double[] avgCoords(double[] first, double[] second) {
		return new double[] {
				(first[0] + second[0]) / 2,
				(first[1] + second[1]) / 2,
				(first[2] + second[2]) / 2
		};
	}
	
	public static class VertInfo {
		float red;
		float green;
		float blue;
		int brightness;
		
		public VertInfo(float red, float green, float blue, int brightness) {
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.brightness = brightness;
		}
		
		public static VertInfo avg(VertInfo...infos) {
			float r = 0F;
			float g = 0F;
			float b = 0F;
			int l = 0;
			
			for(VertInfo vert : infos) {
				r += vert.red;
				g += vert.green;
				b += vert.blue;
				l += vert.brightness;
			}

			r /= infos.length;
			g /= infos.length;
			b /= infos.length;
			l /= infos.length;
			
			return new VertInfo(r, g, b, l);
		}
	}
}

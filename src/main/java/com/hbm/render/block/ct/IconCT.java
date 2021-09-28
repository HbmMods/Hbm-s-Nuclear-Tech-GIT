package com.hbm.render.block.ct;

import net.minecraft.util.IIcon;

public class IconCT implements IIcon {

	public static final int l = 0;	//left
	public static final int r = 1;	//right
	public static final int t = 0;	//top
	public static final int b = 2;	//bottom
	public static final int f = 0;	//full
	public static final int c = 4;	//connected
	public static final int j = 8;	//junction
	public static final int h = 12;	//horizontal
	public static final int v = 16;	//vertical
	
	public static final int ftl = 0;
	public static final int ftr = 1;
	public static final int fbl = 2;
	public static final int fbr = 3;
	public static final int ctl = 4;
	public static final int ctr = 5;
	public static final int cbl = 6;
	public static final int cbr = 7;
	public static final int jtl = 8;
	public static final int jtr = 9;
	public static final int jbl = 10;
	public static final int jbr = 11;
	public static final int htl = 12;
	public static final int htr = 13;
	public static final int hbl = 14;
	public static final int hbr = 15;
	public static final int vtl = 16;
	public static final int vtr = 17;
	public static final int vbl = 18;
	public static final int vbr = 19;
	
	private IIcon parent;
	private int type;
	private float minU;
	private float maxU;
	private float minV;
	private float maxV;
	
	/// none of this is going to work because that's just not how icon UV works! ///
	public IconCT(IIcon parent, int type) {
		this.parent = parent;
		this.type = type;
		
		int sub = ((type & f) != 0) ? 2 : 4;
		float len = 1F / sub;
		
		float du = 0F;
		float dv = 0F;
		
		//set pos to full block (coarse positioning)
		if((type & v) > 0 || (type & j) > 0) {
			du += len * 2;
		}
		if((type & h) > 0 || (type & j) > 0) {
			dv += len * 2;
		}
		
		//set pos to sub-block (fine positioning)
		if((type & r)  > 0) {
			du += len;
		}
		if((type & b) > 0) {
			dv += len;
		}

		minU = du;
		maxU = du + len;
		minV = dv;
		maxV = dv + len;
		//what moron wrote this
	}

	@Override
	public int getIconWidth() {
		return this.parent.getIconWidth();
	}

	@Override
	public int getIconHeight() {
		return this.parent.getIconHeight();
	}

	@Override
	public float getMinU() {
		return this.minU;
	}

	@Override
	public float getMaxU() {
		return this.maxU;
	}

	@Override
	public float getMinV() {
		return this.minV;
	}

	@Override
	public float getMaxV() {
		return this.maxV;
	}

	@Override
	public float getInterpolatedU(double interp) {
		return (float) (this.minU + (this.maxU - this.minU) * interp / 16D); //why 16 is involved here i do not know, but for some reason the interp range is [0;16]
	}

	@Override
	public float getInterpolatedV(double interp) {
		return (float) (this.minV + (this.maxV - this.minV) * interp / 16D);
	}

	@Override
	public String getIconName() {
		return this.parent.getIconName() + "_" + type;
	}

}

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
	
	
	public IconCT(IIcon parent, int type) {
		this.parent = parent;
		this.type = type;
		
		int sub = ((type & f) != 0) ? 2 : 4;
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
		return 0;
	}

	@Override
	public float getMaxU() {
		return 0;
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
		return 0;
	}

	@Override
	public float getInterpolatedV(double interp) {
		return 0;
	}

	@Override
	public String getIconName() {
		return this.parent.getIconName() + "_" + type;
	}

}

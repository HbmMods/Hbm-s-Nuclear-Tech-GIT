package com.hbm.render.block.ct;

import net.minecraft.util.IIcon;

public class IconCT implements IIcon {
	
	private IIcon parent;
	private int type;
	private float minU;
	private float maxU;
	private float minV;
	private float maxV;
	
	public IconCT(IIcon parent, int type) {
		this.parent = parent;
		this.type = type;
		
		int sub = (type < 4) ? 2 : 4;
		float lenU = (parent.getMaxU() - parent.getMinU()) / sub;
		float lenV = (parent.getMaxV() - parent.getMinV()) / sub;
		
		float du = parent.getMinU();
		float dv = parent.getMinV();
		
		//set pos to full block (coarse positioning)
		if(CT.isV(type) || CT.isJ(type)) {
			du += lenU * 2;
		}
		if(CT.isH(type) || CT.isJ(type)) {
			dv += lenV * 2;
		}
		
		//set pos to sub-block (fine positioning)
		if(CT.isR(type)) {
			du += lenU;
		}
		if(CT.isB(type)) {
			dv += lenV;
		}

		minU = du;
		maxU = du + lenU;
		minV = dv;
		maxV = dv + lenV;
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

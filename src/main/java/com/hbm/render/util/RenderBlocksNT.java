package com.hbm.render.util;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class RenderBlocksNT extends RenderBlocks {
	
	public static final RenderBlocksNT INSTANCE = new RenderBlocksNT();

	public RenderBlocksNT(IBlockAccess world) {
		super(world);
	}

	public RenderBlocksNT() {
		super();
	}
	
	public RenderBlocksNT setWorld(IBlockAccess world) {
		this.blockAccess = world;
		this.field_152631_f = false;
		this.flipTexture = false;
		return this;
	}
	
	@Override
	public void renderFaceZNeg(Block block, double x, double y, double z, IIcon icon) {
		Tessellator tessellator = Tessellator.instance;

		if(this.hasOverrideBlockTexture()) {
			icon = this.overrideBlockTexture;
		}

		double minU = (double) icon.getInterpolatedU(16.0D - this.renderMinX * 16.0D);	// **FIX**
		double maxU = (double) icon.getInterpolatedU(16.0D - this.renderMaxX * 16.0D);	// **FIX**

		if(this.field_152631_f) {
			minU = (double) icon.getInterpolatedU(this.renderMinX * 16.0D);	// **FIX**
			maxU = (double) icon.getInterpolatedU(this.renderMaxX * 16.0D);	// **FIX**
		}

		double maxV = (double) icon.getInterpolatedV(16.0D - this.renderMaxY * 16.0D);
		double minV = (double) icon.getInterpolatedV(16.0D - this.renderMinY * 16.0D);
		double minU2;

		if(this.flipTexture) {
			minU2 = maxU;
			maxU = minU;
			minU = minU2;
		}

		if(this.renderMinX < 0.0D || this.renderMaxX > 1.0D) {
			maxU = (double) icon.getMinU();
			minU = (double) icon.getMaxU();
		}

		if(this.renderMinY < 0.0D || this.renderMaxY > 1.0D) {
			maxV = (double) icon.getMinV();
			minV = (double) icon.getMaxV();
		}

		minU2 = minU;
		double maxU2 = maxU;
		double maxV2 = maxV;
		double minV2 = minV;

		if(this.uvRotateEast == 2) {
			maxU = (double) icon.getInterpolatedU(this.renderMinY * 16.0D);
			minU = (double) icon.getInterpolatedU(this.renderMaxY * 16.0D);
			maxV = (double) icon.getInterpolatedV(16.0D - this.renderMinX * 16.0D);
			minV = (double) icon.getInterpolatedV(16.0D - this.renderMaxX * 16.0D);
			maxV2 = maxV;
			minV2 = minV;
			minU2 = maxU;
			maxU2 = minU;
			maxV = minV;
			minV = maxV2;
		} else if(this.uvRotateEast == 1) {
			maxU = (double) icon.getInterpolatedU(16.0D - this.renderMaxY * 16.0D);
			minU = (double) icon.getInterpolatedU(16.0D - this.renderMinY * 16.0D);
			maxV = (double) icon.getInterpolatedV(this.renderMaxX * 16.0D);
			minV = (double) icon.getInterpolatedV(this.renderMinX * 16.0D);
			minU2 = minU;
			maxU2 = maxU;
			maxU = minU;
			minU = maxU2;
			maxV2 = minV;
			minV2 = maxV;
		} else if(this.uvRotateEast == 3) {
			maxU = (double) icon.getInterpolatedU(16.0D - this.renderMinX * 16.0D);
			minU = (double) icon.getInterpolatedU(16.0D - this.renderMaxX * 16.0D);
			maxV = (double) icon.getInterpolatedV(this.renderMaxY * 16.0D);
			minV = (double) icon.getInterpolatedV(this.renderMinY * 16.0D);
			minU2 = minU;
			maxU2 = maxU;
			maxV2 = maxV;
			minV2 = minV;
		}

		double minX = x + this.renderMinX;
		double maxX = x + this.renderMaxX;
		double minY = y + this.renderMinY;
		double maxY = y + this.renderMaxY;
		double minZ = z + this.renderMinZ;

		if(this.renderFromInside) {
			minX = x + this.renderMaxX;
			maxX = x + this.renderMinX;
		}

		if(this.enableAO) {
			tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
			tessellator.setBrightness(this.brightnessTopLeft);
			tessellator.addVertexWithUV(minX, maxY, minZ, minU2, maxV2);
			tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
			tessellator.setBrightness(this.brightnessBottomLeft);
			tessellator.addVertexWithUV(maxX, maxY, minZ, maxU, maxV);
			tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
			tessellator.setBrightness(this.brightnessBottomRight);
			tessellator.addVertexWithUV(maxX, minY, minZ, maxU2, minV2);
			tessellator.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
			tessellator.setBrightness(this.brightnessTopRight);
			tessellator.addVertexWithUV(minX, minY, minZ, minU, minV);
		} else {
			tessellator.addVertexWithUV(minX, maxY, minZ, minU2, maxV2);
			tessellator.addVertexWithUV(maxX, maxY, minZ, maxU, maxV);
			tessellator.addVertexWithUV(maxX, minY, minZ, maxU2, minV2);
			tessellator.addVertexWithUV(minX, minY, minZ, minU, minV);
		}
	}

	@Override
	public void renderFaceXPos(Block block, double x, double y, double z, IIcon icon) {
		Tessellator tessellator = Tessellator.instance;

		if(this.hasOverrideBlockTexture()) {
			icon = this.overrideBlockTexture;
		}

		double minU = (double) icon.getInterpolatedU(16D - this.renderMinZ * 16.0D);	// **FIX**
		double maxU = (double) icon.getInterpolatedU(16D - this.renderMaxZ * 16.0D);	// **FIX**

		if(this.field_152631_f) {
			minU = (double) icon.getInterpolatedU(this.renderMinZ * 16.0D);	// **FIX**
			maxU = (double) icon.getInterpolatedU(this.renderMaxZ * 16.0D);	// **FIX**
		}

		double maxV = (double) icon.getInterpolatedV(16.0D - this.renderMaxY * 16.0D);
		double minV = (double) icon.getInterpolatedV(16.0D - this.renderMinY * 16.0D);
		double minU2;

		if(this.flipTexture) {
			minU2 = maxU;
			maxU = minU;
			minU = minU2;
		}

		if(this.renderMinZ < 0.0D || this.renderMaxZ > 1.0D) {
			maxU = (double) icon.getMinU();
			minU = (double) icon.getMaxU();
		}

		if(this.renderMinY < 0.0D || this.renderMaxY > 1.0D) {
			maxV = (double) icon.getMinV();
			minV = (double) icon.getMaxV();
		}

		minU2 = minU;
		double maxU2 = maxU;
		double maxV2 = maxV;
		double minV2 = minV;

		if(this.uvRotateSouth == 2) {
			maxU = (double) icon.getInterpolatedU(this.renderMinY * 16.0D);
			maxV = (double) icon.getInterpolatedV(16.0D - this.renderMinZ * 16.0D);
			minU = (double) icon.getInterpolatedU(this.renderMaxY * 16.0D);
			minV = (double) icon.getInterpolatedV(16.0D - this.renderMaxZ * 16.0D);
			maxV2 = maxV;
			minV2 = minV;
			minU2 = maxU;
			maxU2 = minU;
			maxV = minV;
			minV = maxV2;
		} else if(this.uvRotateSouth == 1) {
			maxU = (double) icon.getInterpolatedU(16.0D - this.renderMaxY * 16.0D);
			maxV = (double) icon.getInterpolatedV(this.renderMaxZ * 16.0D);
			minU = (double) icon.getInterpolatedU(16.0D - this.renderMinY * 16.0D);
			minV = (double) icon.getInterpolatedV(this.renderMinZ * 16.0D);
			minU2 = minU;
			maxU2 = maxU;
			maxU = minU;
			minU = maxU2;
			maxV2 = minV;
			minV2 = maxV;
		} else if(this.uvRotateSouth == 3) {
			maxU = (double) icon.getInterpolatedU(16.0D - this.renderMinZ * 16.0D);
			minU = (double) icon.getInterpolatedU(16.0D - this.renderMaxZ * 16.0D);
			maxV = (double) icon.getInterpolatedV(this.renderMaxY * 16.0D);
			minV = (double) icon.getInterpolatedV(this.renderMinY * 16.0D);
			minU2 = minU;
			maxU2 = maxU;
			maxV2 = maxV;
			minV2 = minV;
		}

		double maxX = x + this.renderMaxX;
		double minY = y + this.renderMinY;
		double maxY = y + this.renderMaxY;
		double minZ = z + this.renderMinZ;
		double maxZ = z + this.renderMaxZ;

		if(this.renderFromInside) {
			minZ = z + this.renderMaxZ;
			maxZ = z + this.renderMinZ;
		}

		if(this.enableAO) {
			tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
			tessellator.setBrightness(this.brightnessTopLeft);
			tessellator.addVertexWithUV(maxX, minY, maxZ, maxU2, minV2);
			tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
			tessellator.setBrightness(this.brightnessBottomLeft);
			tessellator.addVertexWithUV(maxX, minY, minZ, minU, minV);
			tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
			tessellator.setBrightness(this.brightnessBottomRight);
			tessellator.addVertexWithUV(maxX, maxY, minZ, minU2, maxV2);
			tessellator.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
			tessellator.setBrightness(this.brightnessTopRight);
			tessellator.addVertexWithUV(maxX, maxY, maxZ, maxU, maxV);
		} else {
			tessellator.addVertexWithUV(maxX, minY, maxZ, maxU2, minV2);
			tessellator.addVertexWithUV(maxX, minY, minZ, minU, minV);
			tessellator.addVertexWithUV(maxX, maxY, minZ, minU2, maxV2);
			tessellator.addVertexWithUV(maxX, maxY, maxZ, maxU, maxV);
		}
	}
}

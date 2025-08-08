package com.hbm.render.block.ct;

import org.lwjgl.opengl.GL11;

import com.hbm.interfaces.NotableComments;
import com.hbm.main.MainRegistry;
import com.hbm.render.block.ct.CTContext.CTFace;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

@NotableComments
public class RenderBlocksCT extends RenderBlocks {
	
	public static RenderBlocksCT instance = new RenderBlocksCT();

	VertInfo tl;
	VertInfo tc;
	VertInfo tr;
	VertInfo cl;
	VertInfo cc;
	VertInfo cr;
	VertInfo bl;
	VertInfo bc;
	VertInfo br;
	
	public RenderBlocksCT() {
		super();
	}
	
	public void prepWorld(IBlockAccess acc) {
		this.blockAccess = acc;
	}
	
	private void initSideInfo(int side) {
		
		if(!this.enableAO)
			return;
		
		/*
		 * so what's the actual solution here? instantiating the VertInfos with TL red being 1 causes all faces to be red on the top left, so there's
		 * no translation issues here. perhaps i have to rotate the light and color info before instantiating the infos? afterwards is no good because
		 * of the avg calculations. either way forge is a fucking liar when saying "ayy lmao this is the color of the top left" no it fucking ain't,
		 * it's only the color in ONE PARTICULAR SIDE. well thanks for that i think that's rather poggers, lex.
		 */

		/*float red = (colorRedTopLeft + colorRedTopRight + colorRedBottomLeft + colorRedBottomRight) / 4F;
		float green = (colorGreenTopLeft + colorGreenTopRight + colorGreenBottomLeft + colorGreenBottomRight) / 4F;
		float blue = (colorBlueTopLeft + colorBlueTopRight + colorBlueBottomLeft + colorBlueBottomRight) / 4F;
		int light = (brightnessTopLeft + brightnessTopRight + brightnessBottomLeft + brightnessBottomRight) / 4;*/
		
		if(side == ForgeDirection.SOUTH.ordinal()) {
			this.tl = new VertInfo(colorRedTopLeft, colorGreenTopLeft, colorBlueTopLeft, brightnessTopLeft);
			this.tr = new VertInfo(colorRedTopRight, colorGreenTopRight, colorBlueTopRight, brightnessTopRight);
			this.bl = new VertInfo(colorRedBottomLeft, colorGreenBottomLeft, colorBlueBottomLeft, brightnessBottomLeft);
			this.br = new VertInfo(colorRedBottomRight, colorGreenBottomRight, colorBlueBottomRight, brightnessBottomRight);
		} else if(side == ForgeDirection.NORTH.ordinal()) {
			this.tr = new VertInfo(colorRedTopLeft, colorGreenTopLeft, colorBlueTopLeft, brightnessTopLeft);
			this.br = new VertInfo(colorRedTopRight, colorGreenTopRight, colorBlueTopRight, brightnessTopRight);
			this.tl = new VertInfo(colorRedBottomLeft, colorGreenBottomLeft, colorBlueBottomLeft, brightnessBottomLeft);
			this.bl = new VertInfo(colorRedBottomRight, colorGreenBottomRight, colorBlueBottomRight, brightnessBottomRight);
		} else if(side == ForgeDirection.EAST.ordinal()) {
			this.bl = new VertInfo(colorRedTopLeft, colorGreenTopLeft, colorBlueTopLeft, brightnessTopLeft);
			this.tl = new VertInfo(colorRedTopRight, colorGreenTopRight, colorBlueTopRight, brightnessTopRight);
			this.br = new VertInfo(colorRedBottomLeft, colorGreenBottomLeft, colorBlueBottomLeft, brightnessBottomLeft);
			this.tr = new VertInfo(colorRedBottomRight, colorGreenBottomRight, colorBlueBottomRight, brightnessBottomRight);
		} else if(side == ForgeDirection.WEST.ordinal()) {
			this.tr = new VertInfo(colorRedTopLeft, colorGreenTopLeft, colorBlueTopLeft, brightnessTopLeft);
			this.br = new VertInfo(colorRedTopRight, colorGreenTopRight, colorBlueTopRight, brightnessTopRight);
			this.tl = new VertInfo(colorRedBottomLeft, colorGreenBottomLeft, colorBlueBottomLeft, brightnessBottomLeft);
			this.bl = new VertInfo(colorRedBottomRight, colorGreenBottomRight, colorBlueBottomRight, brightnessBottomRight);
		} else if(side == ForgeDirection.UP.ordinal()) {
			this.br = new VertInfo(colorRedTopLeft, colorGreenTopLeft, colorBlueTopLeft, brightnessTopLeft);
			this.bl = new VertInfo(colorRedTopRight, colorGreenTopRight, colorBlueTopRight, brightnessTopRight);
			this.tr = new VertInfo(colorRedBottomLeft, colorGreenBottomLeft, colorBlueBottomLeft, brightnessBottomLeft);
			this.tl = new VertInfo(colorRedBottomRight, colorGreenBottomRight, colorBlueBottomRight, brightnessBottomRight);
		} else {
			this.tl = new VertInfo(colorRedTopLeft, colorGreenTopLeft, colorBlueTopLeft, brightnessTopLeft);
			this.tr = new VertInfo(colorRedTopRight, colorGreenTopRight, colorBlueTopRight, brightnessTopRight);
			this.bl = new VertInfo(colorRedBottomLeft, colorGreenBottomLeft, colorBlueBottomLeft, brightnessBottomLeft);
			this.br = new VertInfo(colorRedBottomRight, colorGreenBottomRight, colorBlueBottomRight, brightnessBottomRight);
		}

		this.tc = VertInfo.avg(tl, tr);
		this.bc = VertInfo.avg(bl, br);
		this.cl = VertInfo.avg(tl, bl);
		this.cr = VertInfo.avg(tr, br);
		
		this.cc = VertInfo.avg(tl, tr, bl, br);
	}

	@Override
	public boolean renderStandardBlock(Block block, int x, int y, int z) {
		
		if(this.blockAccess == null) {
			MainRegistry.logger.error("Tried to call RenderBlocksCT without setting up a world context!");
			return false;
		}
		
		CTContext.loadContext(blockAccess, x, y, z, block);
		
		return super.renderStandardBlock(block, x, y, z);
	}

	@Override
	public void renderFaceXPos(Block block, double x, double y, double z, IIcon icon) {
		initSideInfo(ForgeDirection.EAST.ordinal());
		CTFace face = CTContext.faces[ForgeDirection.EAST.ordinal()];

		/// ORDER: LEXICAL ///
		drawFace(
				new double[] {x + 1, y + 1, z + 1},
				new double[] {x + 1, y + 1, z + 0},
				new double[] {x + 1, y + 0, z + 1},
				new double[] {x + 1, y + 0, z + 0},
				face.getTopLeft(),
				face.getTopRight(),
				face.getBottomLeft(),
				face.getBottomRight());
	}

	@Override
	public void renderFaceXNeg(Block block, double x, double y, double z, IIcon icon) {
		initSideInfo(ForgeDirection.WEST.ordinal());
		CTFace face = CTContext.faces[ForgeDirection.WEST.ordinal()];

		/// ORDER: LEXICAL ///
		drawFace(
				new double[] {x + 0, y + 1, z + 0},
				new double[] {x + 0, y + 1, z + 1},
				new double[] {x + 0, y + 0, z + 0},
				new double[] {x + 0, y + 0, z + 1},
				face.getTopLeft(),
				face.getTopRight(),
				face.getBottomLeft(),
				face.getBottomRight());
	}

	@Override
	public void renderFaceYPos(Block block, double x, double y, double z, IIcon icon) {
		initSideInfo(ForgeDirection.UP.ordinal());
		CTFace face = CTContext.faces[ForgeDirection.UP.ordinal()];

		/// ORDER: LEXICAL ///
		drawFace(
				new double[] {x + 0, y + 1, z + 0},
				new double[] {x + 1, y + 1, z + 0},
				new double[] {x + 0, y + 1, z + 1},
				new double[] {x + 1, y + 1, z + 1},
				face.getTopLeft(),
				face.getTopRight(),
				face.getBottomLeft(),
				face.getBottomRight());
	}

	@Override
	public void renderFaceYNeg(Block block, double x, double y, double z, IIcon icon) {
		initSideInfo(ForgeDirection.DOWN.ordinal());
		CTFace face = CTContext.faces[ForgeDirection.DOWN.ordinal()];

		/// ORDER: LEXICAL ///
		drawFace(
				new double[] {x + 0, y + 0, z + 1},
				new double[] {x + 1, y + 0, z + 1},
				new double[] {x + 0, y + 0, z + 0},
				new double[] {x + 1, y + 0, z + 0},
				face.getTopLeft(),
				face.getTopRight(),
				face.getBottomLeft(),
				face.getBottomRight());
	}

	@Override
	public void renderFaceZPos(Block block, double x, double y, double z, IIcon icon) {
		initSideInfo(ForgeDirection.SOUTH.ordinal());
		CTFace face = CTContext.faces[ForgeDirection.SOUTH.ordinal()];

		/// ORDER: LEXICAL ///
		drawFace(
				new double[] {x + 0, y + 1, z + 1},
				new double[] {x + 1, y + 1, z + 1},
				new double[] {x + 0, y + 0, z + 1},
				new double[] {x + 1, y + 0, z + 1},
				face.getTopLeft(),
				face.getTopRight(),
				face.getBottomLeft(),
				face.getBottomRight());
	}

	@Override
	public void renderFaceZNeg(Block block, double x, double y, double z, IIcon icon) {
		initSideInfo(ForgeDirection.NORTH.ordinal());
		CTFace face = CTContext.faces[ForgeDirection.NORTH.ordinal()];

		/// ORDER: LEXICAL ///
		drawFace(
				new double[] {x + 1, y + 1, z + 0},
				new double[] {x + 0, y + 1, z + 0},
				new double[] {x + 1, y + 0, z + 0},
				new double[] {x + 0, y + 0, z + 0},
				face.getTopLeft(),
				face.getTopRight(),
				face.getBottomLeft(),
				face.getBottomRight());
	}

	/// ORDER: LEXICAL ///
	private void drawFace(double[] ftl, double[] ftr, double[] fbl, double[] fbr, IIcon itl, IIcon itr, IIcon ibl, IIcon ibr) {

		double[] ftc = avgCoords(ftl, ftr);
		double[] fbc = avgCoords(fbl, fbr);
		double[] fcl = avgCoords(ftl, fbl);
		double[] fcr = avgCoords(ftr, fbr);
		double[] fcc = avgCoords(ftc, fbc);

		/*IIcon atl = ModBlocks.block_steel.getIcon(0, 0);
		IIcon atr = ModBlocks.block_copper.getIcon(0, 0);
		IIcon abl = ModBlocks.block_tungsten.getIcon(0, 0);
		IIcon abr = ModBlocks.block_aluminium.getIcon(0, 0);*/
		
		/*drawSubFace(ftl, this.tl, ftc, this.tc, fcl, this.cl, fcc, this.cc, atl);
		drawSubFace(ftc, this.tc, ftr, this.tr, fcc, this.cc, fcr, this.cr, atr);
		drawSubFace(fcl, this.cl, fcc, this.cc, fbl, this.bl, fbc, this.bc, abl);
		drawSubFace(fcc, this.cc, fcr, this.cr, fbc, this.bc, fbr, this.br, abr);*/
		
		drawSubFace(ftl, this.tl, ftc, this.tc, fcl, this.cl, fcc, this.cc, itl);
		drawSubFace(ftc, this.tc, ftr, this.tr, fcc, this.cc, fcr, this.cr, itr);
		drawSubFace(fcl, this.cl, fcc, this.cc, fbl, this.bl, fbc, this.bc, ibl);
		drawSubFace(fcc, this.cc, fcr, this.cr, fbc, this.bc, fbr, this.br, ibr);
	}
	
	/// ORDER: LEXICAL ///
	private void drawSubFace(double[] ftl, VertInfo ntl, double[] ftr, VertInfo ntr, double[] fbl, VertInfo nbl, double[] fbr, VertInfo nbr, IIcon icon) {
		
		boolean debugColor = false;
		
		/// ORDER: ROTATIONAL ///
		if(debugColor) Tessellator.instance.setColorOpaque_F(1F, 1F, 0F);
		drawVert(ftr, icon.getMaxU(), icon.getMinV(), ntr);
		if(debugColor) Tessellator.instance.setColorOpaque_F(1F, 0F, 0F);
		drawVert(ftl, icon.getMinU(), icon.getMinV(), ntl);
		if(debugColor) Tessellator.instance.setColorOpaque_F(0F, 0F, 1F);
		drawVert(fbl, icon.getMinU(), icon.getMaxV(), nbl);
		if(debugColor) Tessellator.instance.setColorOpaque_F(0F, 1F, 0F);
		drawVert(fbr, icon.getMaxU(), icon.getMaxV(), nbr);
	}
	
	private void drawVert(double[] coord, double u, double v, VertInfo info) {
		drawVert(coord[0], coord[1], coord[2], u, v, info);
	}
	
	private void drawVert(double x, double y, double z, double u, double v, VertInfo info) {
		
		if(this.enableAO) {
			Tessellator.instance.setColorOpaque_F(info.red, info.green, info.blue);
			Tessellator.instance.setBrightness(info.brightness);
		}
		
		Tessellator.instance.addVertexWithUV(x, y, z, u, v);
	}
	
	private double[] avgCoords(double[] first, double[] second) {
		return new double[] {
				(first[0] + second[0]) / 2D,
				(first[1] + second[1]) / 2D,
				(first[2] + second[2]) / 2D
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

	@Override
	public void renderBlockAsItem(Block block, int meta, float mult) {

		block.setBlockBoundsForItemRender();
		this.setRenderBoundsFromBlock(block);
		GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		
		Tessellator.instance.startDrawingQuads();
		Tessellator.instance.setNormal(0.0F, -1.0F, 0.0F);
		super.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSideAndMetadata(block, 0, meta));
		Tessellator.instance.draw();
		Tessellator.instance.startDrawingQuads();
		Tessellator.instance.setNormal(0.0F, 1.0F, 0.0F);
		super.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSideAndMetadata(block, 1, meta));
		Tessellator.instance.draw();

		Tessellator.instance.startDrawingQuads();
		Tessellator.instance.setNormal(0.0F, 0.0F, -1.0F);
		super.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSideAndMetadata(block, 2, meta));
		Tessellator.instance.draw();
		Tessellator.instance.startDrawingQuads();
		Tessellator.instance.setNormal(0.0F, 0.0F, 1.0F);
		super.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSideAndMetadata(block, 3, meta));
		Tessellator.instance.draw();
		
		Tessellator.instance.startDrawingQuads();
		Tessellator.instance.setNormal(-1.0F, 0.0F, 0.0F);
		super.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSideAndMetadata(block, 4, meta));
		Tessellator.instance.draw();
		Tessellator.instance.startDrawingQuads();
		Tessellator.instance.setNormal(1.0F, 0.0F, 0.0F);
		super.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSideAndMetadata(block, 5, meta));
		Tessellator.instance.draw();
		
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}
}

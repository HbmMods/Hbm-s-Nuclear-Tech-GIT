package com.hbm.render.util;

import org.lwjgl.opengl.GL11;

import com.hbm.interfaces.Spaghetti;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;

public class SmallBlockPronter {
	
	static float pixel = 1F/16F;

	/**
	 * Bind the required texture yourself bruh     <- ding dong, 2023 bob speaking, fuck this guy
	 * @param loc
	 * @param x
	 * @param y
	 * @param z
	 */
	@Deprecated @Spaghetti("this is horseshit")
	public static void renderSmolBlockAt(float x, float y, float z) {
		GL11.glPushMatrix();
		GL11.glTranslatef(x - 1, y - 1, z);
		
		Tessellator tesseract = Tessellator.instance;
		tesseract.startDrawingQuads();
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 1, 0);
			tesseract.addVertexWithUV(11 * pixel / 2, 1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 0, 0);
			tesseract.addVertexWithUV(11 * pixel / 2, 11 * pixel / 2, 1 - 11 * pixel / 2, 0, 1);
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 11 * pixel / 2,  1 - 11 * pixel / 2, 1, 1);
			tesseract.draw();

			tesseract.startDrawingQuads();
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 11 * pixel / 2, 1, 0);
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 0, 0);
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 11 * pixel / 2, 1 - 11 * pixel / 2, 0, 1);
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 11 * pixel / 2,  11 * pixel / 2, 1, 1);
			tesseract.draw();

			tesseract.startDrawingQuads();
			tesseract.addVertexWithUV(11 * pixel / 2, 1 - 11 * pixel / 2, 11 * pixel / 2, 1, 0);
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 11 * pixel / 2, 0, 0);
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 11 * pixel / 2,  11 * pixel / 2, 0, 1);
			tesseract.addVertexWithUV(11 * pixel / 2, 11 * pixel / 2, 11 * pixel / 2, 1, 1);
			tesseract.draw();

			tesseract.startDrawingQuads();
			tesseract.addVertexWithUV(11 * pixel / 2, 1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 1, 0);
			tesseract.addVertexWithUV(11 * pixel / 2, 1 - 11 * pixel / 2, 11 * pixel / 2, 0, 0);
			tesseract.addVertexWithUV(11 * pixel / 2, 11 * pixel / 2,  11 * pixel / 2, 0, 1);
			tesseract.addVertexWithUV(11 * pixel / 2, 11 * pixel / 2, 1 - 11 * pixel / 2, 1, 1);
			tesseract.draw();

			tesseract.startDrawingQuads();
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 11 * pixel / 2, 1, 0);
			tesseract.addVertexWithUV(11 * pixel / 2, 1 - 11 * pixel / 2, 11 * pixel / 2, 0, 0);
			tesseract.addVertexWithUV(11 * pixel / 2, 1 - 11 * pixel / 2,  1 - 11 * pixel / 2, 0, 1);
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 1, 1);
			tesseract.draw();

			tesseract.startDrawingQuads();
			tesseract.addVertexWithUV(11 * pixel / 2, 11 * pixel / 2, 11 * pixel / 2, 1, 0);
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 11 * pixel / 2, 11 * pixel / 2, 0, 0);
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 11 * pixel / 2, 1 - 11 * pixel / 2, 0, 1);
			tesseract.addVertexWithUV(11 * pixel / 2, 11 * pixel / 2,  1 - 11 * pixel / 2, 1, 1);
		tesseract.draw();
		GL11.glPopMatrix();
		
	}
	
	public static void startDrawing() {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_CULL_FACE);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glDepthMask(false);
		Tessellator.instance.startDrawingQuads();
	}
	
	public static void draw() {
		Tessellator.instance.draw();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_LIGHTING);
	}
	
	public static void drawSmolBlockAt(Block b, int meta, float x, float y, float z) {

		Tessellator tesseract = Tessellator.instance;
		IIcon iconTop = b.getIcon(ForgeDirection.UP.ordinal(), meta);
		IIcon iconBottom = b.getIcon(ForgeDirection.DOWN.ordinal(), meta);
		IIcon iconNorth = b.getIcon(ForgeDirection.NORTH.ordinal(), meta);
		IIcon iconSouth = b.getIcon(ForgeDirection.SOUTH.ordinal(), meta);
		IIcon iconEast = b.getIcon(ForgeDirection.EAST.ordinal(), meta);
		IIcon iconWest = b.getIcon(ForgeDirection.WEST.ordinal(), meta);
		
		tesseract.setNormal(0F, 1F, 0F);
		
		tesseract.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 1 - 11 * pixel / 2, iconSouth.getMaxU(), iconSouth.getMinV());
		tesseract.addVertexWithUV(x + 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 1 - 11 * pixel / 2, iconSouth.getMinU(), iconSouth.getMinV());
		tesseract.addVertexWithUV(x + 11 * pixel / 2, y + 11 * pixel / 2, z + 1 - 11 * pixel / 2, iconSouth.getMinU(), iconSouth.getMaxV());
		tesseract.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 11 * pixel / 2,  z + 1 - 11 * pixel / 2, iconSouth.getMaxU(), iconSouth.getMaxV());

		tesseract.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 11 * pixel / 2, iconEast.getMaxU(), iconEast.getMinV());
		tesseract.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 1 - 11 * pixel / 2, iconEast.getMinU(), iconEast.getMinV());
		tesseract.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 11 * pixel / 2, z + 1 - 11 * pixel / 2, iconEast.getMinU(), iconEast.getMaxV());
		tesseract.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 11 * pixel / 2, z + 11 * pixel / 2, iconEast.getMaxU(), iconEast.getMaxV());

		tesseract.addVertexWithUV(x + 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 11 * pixel / 2, iconNorth.getMaxU(), iconNorth.getMinV());
		tesseract.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 11 * pixel / 2, iconNorth.getMinU(), iconNorth.getMinV());
		tesseract.addVertexWithUV(x + 1 - 11 * pixel / 2,y +  11 * pixel / 2, z + 11 * pixel / 2, iconNorth.getMinU(), iconNorth.getMaxV());
		tesseract.addVertexWithUV(x + 11 * pixel / 2, y + 11 * pixel / 2, z + 11 * pixel / 2, iconNorth.getMaxU(), iconNorth.getMaxV());

		tesseract.addVertexWithUV(x + 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 1 - 11 * pixel / 2, iconWest.getMaxU(), iconWest.getMinV());
		tesseract.addVertexWithUV(x + 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 11 * pixel / 2, iconWest.getMinU(), iconWest.getMinV());
		tesseract.addVertexWithUV(x + 11 * pixel / 2, y + 11 * pixel / 2, z + 11 * pixel / 2, iconWest.getMinU(), iconWest.getMaxV());
		tesseract.addVertexWithUV(x + 11 * pixel / 2, y + 11 * pixel / 2, z + 1 - 11 * pixel / 2, iconWest.getMaxU(), iconWest.getMaxV());

		tesseract.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 11 * pixel / 2, iconTop.getMaxU(), iconTop.getMinV());
		tesseract.addVertexWithUV(x + 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 11 * pixel / 2, iconTop.getMinU(), iconTop.getMinV());
		tesseract.addVertexWithUV(x + 11 * pixel / 2, y + 1 - 11 * pixel / 2,  z + 1 - 11 * pixel / 2, iconTop.getMinU(), iconTop.getMaxV());
		tesseract.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 1 - 11 * pixel / 2, z + 1 - 11 * pixel / 2, iconTop.getMaxU(), iconTop.getMaxV());

		tesseract.addVertexWithUV(x + 11 * pixel / 2, y + 11 * pixel / 2, z + 11 * pixel / 2, iconBottom.getMaxU(), iconBottom.getMinV());
		tesseract.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 11 * pixel / 2, z + 11 * pixel / 2, iconBottom.getMinU(), iconBottom.getMinV());
		tesseract.addVertexWithUV(x + 1 - 11 * pixel / 2, y + 11 * pixel / 2, z + 1 - 11 * pixel / 2, iconBottom.getMinU(), iconBottom.getMaxV());
		tesseract.addVertexWithUV(x + 11 * pixel / 2, y + 11 * pixel / 2, z + 1 - 11 * pixel / 2, iconBottom.getMaxU(), iconBottom.getMaxV());
	}
}

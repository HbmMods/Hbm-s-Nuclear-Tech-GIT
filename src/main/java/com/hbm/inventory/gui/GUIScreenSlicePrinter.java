package com.hbm.inventory.gui;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.machine.BlockPWR;
import com.hbm.blocks.machine.BlockPWR.TileEntityBlockPWR;

import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.util.ForgeDirection;

public class GUIScreenSlicePrinter extends GuiScreen {

	private final int x1, y1, z1;
	private final int x2, y2, z2;
	private final int sizeX, sizeY, sizeZ;
	private final ForgeDirection dir;

	private HashSet<Block> whitelist;

	private int yIndex;

	private RenderBlocks renderer;

	private String dirname;
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");

	public GUIScreenSlicePrinter(int x1, int y1, int z1, int x2, int y2, int z2, ForgeDirection dir) {
		this.x1 = Math.min(x1, x2);
		this.y1 = Math.min(y1, y2);
		this.z1 = Math.min(z1, z2);
		this.x2 = Math.max(x1, x2);
		this.y2 = Math.max(y1, y2);
		this.z2 = Math.max(z1, z2);

		this.dir = dir;

		this.sizeX = this.x2 - this.x1 + 1;
		this.sizeY = this.y2 - this.y1 + 1;
		this.sizeZ = this.z2 - this.z1 + 1;

		dirname = dateFormat.format(new Date()).toString();
	}

	public GUIScreenSlicePrinter(int x1, int y1, int z1, int x2, int y2, int z2, ForgeDirection dir, HashSet<Block> whitelist) {
		this(x1, y1, z1, x2, y2, z2, dir);
		this.whitelist = whitelist;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		if(renderer == null) {
			this.renderer = new RenderBlocks(mc.theWorld);
		}

		GuiScreen.drawRect(0, 0, width, height, 0xFFFF00FF);

		// Once we've reached the top slice, close the GUI
		if(yIndex >= sizeY) {
			mc.thePlayer.addChatMessage(new ChatComponentText("Slices saved to: .minecraft/printer/" + dirname));
			mc.thePlayer.closeScreen();
			return;
		}

		GL11.glPushMatrix();
		{

			setupRotation();

			mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
			GL11.glShadeModel(GL11.GL_SMOOTH);
			Tessellator.instance.startDrawingQuads();

			for(int x = 0; x < sizeX; x++) {
				for(int z = 0; z < sizeZ; z++) {
					Block block = mc.theWorld.getBlock(x1 + x, y1 + yIndex, z1 + z);
					if(whitelist != null && !whitelist.contains(block)) continue;

					// Revert PWR blocks to originals for slice rendering
					if(block instanceof BlockPWR) {
						TileEntity tile = mc.theWorld.getTileEntity(x1 + x, y1 + yIndex, z1 + z);
						if(tile instanceof TileEntityBlockPWR) {
							TileEntityBlockPWR pwr = (TileEntityBlockPWR) tile;
							if(pwr.block != null) {
								block = pwr.block;
							}
						}
					}

					int dx = x;
					int dz = z;

					// swizzle instead of rotating, so the PWR controller faces the correct rotation
					if(dir == ForgeDirection.WEST) {
						dx = sizeZ - 1 - z;
						dz = x;
					} else if(dir == ForgeDirection.SOUTH) {
						dx = sizeX - 1 - x;
						dz = sizeZ - 1 - z;
					} else if(dir == ForgeDirection.EAST) {
						dx = z;
						dz = sizeX - 1 - x;
					}

					renderer.renderBlockByRenderType(block, dx, 0, dz);
				}
			}

			Tessellator.instance.draw();
			GL11.glShadeModel(GL11.GL_FLAT);

		}
		GL11.glPopMatrix();

		File printerDir = new File(mc.mcDataDir, "printer");
		printerDir.mkdir();

		GUIScreenWikiRender.saveScreenshot(printerDir, dirname, "slice_" + yIndex + ".png", 0, 0, mc.displayWidth, mc.displayHeight, 0xFFFF00FF);

		yIndex++;
	}

	private void setupRotation() {
		double scale = -24;

		GL11.glTranslated(width / 2, height / 2 - 36, 400);
		GL11.glScaled(scale, scale, scale);
		GL11.glScaled(1, 1, 0.5); //incredible flattening power

		GL11.glRotated(-30, 1, 0, 0);
		GL11.glRotated(225, 0, 1, 0);

		if(dir == ForgeDirection.WEST || dir == ForgeDirection.EAST) {
			GL11.glTranslated(sizeX / -2D, -sizeY / 2D, sizeZ / -2D);
		} else {
			GL11.glTranslated(sizeZ / -2D, -sizeY / 2D, sizeX / -2D);
		}
	}

}

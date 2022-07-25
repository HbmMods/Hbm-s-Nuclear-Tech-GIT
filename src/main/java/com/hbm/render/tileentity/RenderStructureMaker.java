package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityStructureMarker;
import com.hbm.world.machine.FWatz;
import com.hbm.world.machine.NuclearReactor;
import com.hbm.world.machine.Watz;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderStructureMaker extends TileEntitySpecialRenderer {
	
	float pixel = 1F/16F;
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
			GL11.glTranslatef((float) x, (float) y, (float) z);
			GL11.glRotatef(180, 0F, 0F, 1F);

			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_BLEND);
			GL14.glBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			GL14.glBlendEquation(GL14.GL_FUNC_ADD);
			GL11.glColor4f(0.5f, 0.25f, 1.0f, 1f);
			this.renderBlocks((int)x, (int)y, (int)z, ((TileEntityStructureMarker)tileentity).type, tileentity.getWorldObj().getBlockMetadata(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord));
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}
	
	public void renderBlocks(int x, int y, int z, int type, int meta) {
		int offsetX = 0;
		int offsetZ = 0;
		if(type == 0) {

			if(meta == 6) {
				offsetZ = 3;
			}
			if(meta == 7) {
				offsetX = 3;
			}
			if(meta == 8) {
				offsetZ = -3;
			}
			if(meta == 9) {
				offsetX = -3;
			}
			
			GL11.glTranslatef(-2 + offsetX, -3, -2 + offsetZ);
			for(int a = 0; a < 5; a++) {
				for(int b = 0; b < 5; b++) {
					for(int c = 0; c < 5; c++) {
				
						Block block = Blocks.air;
						if(NuclearReactor.array2[b][a].substring(c, c + 1).equals("R"))
							block = ModBlocks.reactor_element;
						if(NuclearReactor.array2[b][a].substring(c, c + 1).equals("#"))
							block = ModBlocks.reactor_computer;
						if(NuclearReactor.array2[b][a].substring(c, c + 1).equals("C"))
							block = ModBlocks.reactor_control;
						if(NuclearReactor.array2[b][a].substring(c, c + 1).equals("A"))
							block = ModBlocks.reactor_hatch;
						if(NuclearReactor.array2[b][a].substring(c, c + 1).equals("I"))
							block = ModBlocks.reactor_conductor;
						//if(NuclearReactor.array2[b][a].substring(c, c + 1).equals("B"))
						//	block = ModBlocks.brick_concrete;
						if(block != Blocks.air) {
							RenderBlocks rb = RenderBlocks.getInstance();
							ResourceLocation loc1 = new ResourceLocation(RefStrings.MODID + ":textures/blocks/" + rb.getBlockIconFromSide(block, 1).getIconName().substring(4, rb.getBlockIconFromSide(block, 1).getIconName().length()) + ".png");
							ResourceLocation loc2 = new ResourceLocation(RefStrings.MODID + ":textures/blocks/" + rb.getBlockIconFromSide(block, 3).getIconName().substring(4, rb.getBlockIconFromSide(block, 3).getIconName().length()) + ".png");
							renderSmolBlockAt(loc1, loc2, a, b, c);
						}
					}
				}
			}
		}
		if(type == 1) {

			if(meta == 6) {
				offsetZ = 4;
			}
			if(meta == 7) {
				offsetX = 4;
			}
			if(meta == 8) {
				offsetZ = -4;
			}
			if(meta == 9) {
				offsetX = -4;
			}
			
			GL11.glTranslatef(-3 + offsetX, -12, -3 + offsetZ);
			for(int a = 0; a < 7; a++) {
				for(int b = 0; b < 13; b++) {
					for(int c = 0; c < 7; c++) {
				
						Block block = Blocks.air;
						if(Watz.array[b][a].substring(c, c + 1).equals("C"))
							block = ModBlocks.reinforced_brick;
						if(Watz.array[b][a].substring(c, c + 1).equals("A"))
							block = ModBlocks.watz_hatch;
						if(Watz.array[b][a].substring(c, c + 1).equals("R"))
							block = ModBlocks.watz_control;
						if(Watz.array[b][a].substring(c, c + 1).equals("S"))
							block = ModBlocks.watz_end;
						if(Watz.array[b][a].substring(c, c + 1).equals("I"))
							block = ModBlocks.watz_conductor;
						if(Watz.array[b][a].substring(c, c + 1).equals("#"))
							block = ModBlocks.watz_core;
						if(Watz.array[b][a].substring(c, c + 1).equals("K"))
							block = ModBlocks.watz_cooler;
						if(Watz.array[b][a].substring(c, c + 1).equals("W"))
							block = ModBlocks.watz_element;
						if(block != Blocks.air) {
							RenderBlocks rb = RenderBlocks.getInstance();
							ResourceLocation loc1 = new ResourceLocation(RefStrings.MODID + ":textures/blocks/" + rb.getBlockIconFromSide(block, 1).getIconName().substring(4, rb.getBlockIconFromSide(block, 1).getIconName().length()) + ".png");
							ResourceLocation loc2 = new ResourceLocation(RefStrings.MODID + ":textures/blocks/" + rb.getBlockIconFromSide(block, 3).getIconName().substring(4, rb.getBlockIconFromSide(block, 3).getIconName().length()) + ".png");
							renderSmolBlockAt(loc1, loc2, a, b, c);
						}
					}
				}
			}
		}
		if(type == 2) {

			if(meta == 6) {
				offsetZ = 10;
			}
			if(meta == 7) {
				offsetX = 10;
			}
			if(meta == 8) {
				offsetZ = -10;
			}
			if(meta == 9) {
				offsetX = -10;
			}
			
			GL11.glTranslatef(-9 + offsetX, -18, -9 + offsetZ);
			for(int a = 0; a < 19; a++) {
				for(int b = 0; b < 	19; b++) {
					for(int c = 0; c < 19; c++) {
				
						Block block = Blocks.air;
						if(FWatz.fwatz[18 - b][a].substring(c, c + 1).equals("X"))
							block = ModBlocks.fwatz_scaffold;
						if(FWatz.fwatz[18 - b][a].substring(c, c + 1).equals("H"))
							block = ModBlocks.fwatz_hatch;
						if(FWatz.fwatz[18 - b][a].substring(c, c + 1).equals("S"))
							block = ModBlocks.fwatz_cooler;
						if(FWatz.fwatz[18 - b][a].substring(c, c + 1).equals("T"))
							block = ModBlocks.fwatz_tank;
						if(FWatz.fwatz[18 - b][a].substring(c, c + 1).equals("M"))
							block = ModBlocks.fwatz_conductor;
						if(FWatz.fwatz[18 - b][a].substring(c, c + 1).equals("C"))
							block = ModBlocks.fwatz_computer;
						if(FWatz.fwatz[18 - b][a].substring(c, c + 1).equals("#"))
							block = ModBlocks.fwatz_core;
						if(block != Blocks.air) {
							RenderBlocks rb = RenderBlocks.getInstance();
							ResourceLocation loc1 = new ResourceLocation(RefStrings.MODID + ":textures/blocks/" + rb.getBlockIconFromSide(block, 1).getIconName().substring(4, rb.getBlockIconFromSide(block, 1).getIconName().length()) + ".png");
							ResourceLocation loc2 = new ResourceLocation(RefStrings.MODID + ":textures/blocks/" + rb.getBlockIconFromSide(block, 3).getIconName().substring(4, rb.getBlockIconFromSide(block, 3).getIconName().length()) + ".png");
							renderSmolBlockAt(loc1, loc2, a, b, c);
						}
					}
				}
			}
		}
	}
	
	public void renderSmolBlockAt(ResourceLocation loc1, ResourceLocation loc2, int x, int y, int z) {
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, z);
		GL11.glRotatef(180, 0F, 0F, 1F);
		Tessellator tesseract = Tessellator.instance;
		tesseract.startDrawingQuads();
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 1, 0);
			tesseract.addVertexWithUV(11 * pixel / 2, 1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 0, 0);
			tesseract.addVertexWithUV(11 * pixel / 2, 11 * pixel / 2, 1 - 11 * pixel / 2, 0, 1);
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 11 * pixel / 2,  1 - 11 * pixel / 2, 1, 1);
			this.bindTexture(loc2);
			tesseract.draw();

			tesseract.startDrawingQuads();
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 11 * pixel / 2, 1, 0);
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 0, 0);
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 11 * pixel / 2, 1 - 11 * pixel / 2, 0, 1);
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 11 * pixel / 2,  11 * pixel / 2, 1, 1);
			this.bindTexture(loc2);
			tesseract.draw();

			tesseract.startDrawingQuads();
			tesseract.addVertexWithUV(11 * pixel / 2, 1 - 11 * pixel / 2, 11 * pixel / 2, 1, 0);
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 11 * pixel / 2, 0, 0);
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 11 * pixel / 2,  11 * pixel / 2, 0, 1);
			tesseract.addVertexWithUV(11 * pixel / 2, 11 * pixel / 2, 11 * pixel / 2, 1, 1);
			this.bindTexture(loc2);
			tesseract.draw();

			tesseract.startDrawingQuads();
			tesseract.addVertexWithUV(11 * pixel / 2, 1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 1, 0);
			tesseract.addVertexWithUV(11 * pixel / 2, 1 - 11 * pixel / 2, 11 * pixel / 2, 0, 0);
			tesseract.addVertexWithUV(11 * pixel / 2, 11 * pixel / 2,  11 * pixel / 2, 0, 1);
			tesseract.addVertexWithUV(11 * pixel / 2, 11 * pixel / 2, 1 - 11 * pixel / 2, 1, 1);
			this.bindTexture(loc2);
			tesseract.draw();

			tesseract.startDrawingQuads();
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 11 * pixel / 2, 1, 0);
			tesseract.addVertexWithUV(11 * pixel / 2, 1 - 11 * pixel / 2, 11 * pixel / 2, 0, 0);
			tesseract.addVertexWithUV(11 * pixel / 2, 1 - 11 * pixel / 2,  1 - 11 * pixel / 2, 0, 1);
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 1 - 11 * pixel / 2, 1, 1);
			this.bindTexture(loc1);
			tesseract.draw();

			tesseract.startDrawingQuads();
			tesseract.addVertexWithUV(11 * pixel / 2, 11 * pixel / 2, 11 * pixel / 2, 1, 0);
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 11 * pixel / 2, 11 * pixel / 2, 0, 0);
			tesseract.addVertexWithUV(1 - 11 * pixel / 2, 11 * pixel / 2, 1 - 11 * pixel / 2, 0, 1);
			tesseract.addVertexWithUV(11 * pixel / 2, 11 * pixel / 2,  1 - 11 * pixel / 2, 1, 1);
			this.bindTexture(loc1);
			tesseract.draw();
		GL11.glPopMatrix();
		
	}

}

package com.hbm.wiaj;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;

//krass
public class GuiWorldInAJar extends GuiScreen {
	
	WorldInAJar world;
	RenderBlocks renderer;

	public GuiWorldInAJar() {
		super();
		world = new WorldInAJar(15, 15, 15);
		renderer = new RenderBlocks(world);
		renderer.enableAO = true;
		
		for(int x = 0; x < 15; x++) {
			for(int y = 0; y < 15; y++) {
				for(int z = 0; z < 15; z++) {
					
					if(y == 14) {
						world.setBlock(x, y, z, ModBlocks.glass_boron, 0);
						continue;
					}
					
					if(y > 0) {
						if(x == 0 || x == 14 || z == 0 || z == 14) {
							world.setBlock(x, y, z, ModBlocks.glass_boron, 0);
							continue;
						}
					}
					
					if(y == 0) {
						if(x == 0 || x == 14 || z == 0 || z == 14) {
							world.setBlock(x, y, z, ModBlocks.concrete_colored, 6);
						} else {
							world.setBlock(x, y, z, ModBlocks.concrete_smooth, 0);
						}
					}
				}
			}
		}

		world.setBlock(2, 1, 2, ModBlocks.fallout, 0);
		world.setBlock(4, 1, 4, ModBlocks.conveyor, 2);
		world.setBlock(4, 1, 5, ModBlocks.conveyor, 2);
		world.setBlock(4, 1, 6, ModBlocks.conveyor, 2);
		world.setBlock(4, 1, 7, ModBlocks.conveyor, 2);
		world.setBlock(4, 1, 8, ModBlocks.conveyor, 2);
		world.setBlock(4, 1, 9, ModBlocks.conveyor, 2);
		world.setBlock(4, 1, 10, ModBlocks.conveyor, 6);
		world.setBlock(5, 1, 10, ModBlocks.conveyor, 4);
		
		for(int x = 9; x < 12; x++) {
			for(int y = 1; y < 5; y++) {
				for(int z = 6; z < 9; z++) {
					world.setBlock(x, y, z, Blocks.brick_block, 0);
				}
			}
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		this.drawDefaultBackground();
		this.drawGuiContainerBackgroundLayer(f, mouseX, mouseY);
		GL11.glDisable(GL11.GL_LIGHTING);
		//this.drawGuiContainerForegroundLayer(mouseX, mouseY);
		GL11.glEnable(GL11.GL_LIGHTING);
	}

	private void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {

		GL11.glPushMatrix();
		double scale = -10;
		GL11.glTranslated(width / 2, height / 2 + 70, 100);
		GL11.glScaled(scale, scale, scale);
		GL11.glScaled(1, 1, 0.01); // increadible flattening power
		GL11.glRotated(30, -1, 0, 0);
		GL11.glRotated(45, 0, -1, 0);
		GL11.glTranslated(-7, 0 , -7);

		GL11.glTranslated(world.sizeX / 2D, 0 , world.sizeZ / 2D);
		GL11.glRotated(System.currentTimeMillis() % (360 * 20) / 20D, 0, -1, 0);
		GL11.glTranslated(world.sizeX / -2D, 0 , world.sizeZ / -2D);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Tessellator.instance.startDrawingQuads();
		
		for(int x = 0; x < world.sizeX; x++) {
			for(int y = 0; y < world.sizeY; y++) {
				for(int z = 0; z < world.sizeZ; z++) {
					renderer.renderBlockByRenderType(world.getBlock(x, y, z), x, y, z);
				}
			}
		}
		
		Tessellator.instance.draw();
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}

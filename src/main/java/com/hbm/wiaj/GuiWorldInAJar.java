package com.hbm.wiaj;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.wiaj.actions.ActionRotate;
import com.hbm.wiaj.actions.ActionSetBlock;
import com.hbm.wiaj.actions.ActionWait;

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
	
	JarScript testScript;

	public GuiWorldInAJar() {
		super();
		world = new WorldInAJar(15, 15, 15);
		renderer = new RenderBlocks(world);
		renderer.enableAO = true;
		
		testScript = new JarScript(world);
		JarScene startingScene = new JarScene(testScript);

		for(int y = 0; y < 15; y++) {
			for(int x = 0; x < 15; x++) {
				for(int z = 0; z < 15; z++) {
					
					if(y == 14) {
						startingScene.add(new ActionSetBlock(x, y, z, ModBlocks.glass_boron));
						continue;
					}
					
					if(y > 0) {
						if(x == 0 || x == 14 || z == 0 || z == 14) {
							startingScene.add(new ActionSetBlock(x, y, z, ModBlocks.glass_boron));
							continue;
						}
					}
					
					if(y == 0) {
						if(x == 0 || x == 14 || z == 0 || z == 14) {
							startingScene.add(new ActionSetBlock(x, y, z, ModBlocks.concrete_colored, 6));
						} else {
							startingScene.add(new ActionSetBlock(x, y, z, ModBlocks.concrete_colored, 0));
						}
					}
				}
			}
			startingScene.add(new ActionWait(1));
		}
		
		startingScene.add(new ActionWait(10));

		startingScene.add(new ActionSetBlock(2, 1, 2, ModBlocks.fallout, 0));
		
		startingScene.add(new ActionWait(10));
		
		startingScene.add(new ActionSetBlock(4, 1, 4, ModBlocks.conveyor, 2));
		startingScene.add(new ActionSetBlock(4, 1, 5, ModBlocks.conveyor, 2));
		startingScene.add(new ActionSetBlock(4, 1, 6, ModBlocks.conveyor, 2));
		startingScene.add(new ActionSetBlock(4, 1, 7, ModBlocks.conveyor, 2));
		startingScene.add(new ActionSetBlock(4, 1, 8, ModBlocks.conveyor, 2));
		startingScene.add(new ActionSetBlock(4, 1, 9, ModBlocks.conveyor, 2));
		startingScene.add(new ActionSetBlock(4, 1, 10, ModBlocks.conveyor, 6));
		startingScene.add(new ActionSetBlock(5, 1, 10, ModBlocks.conveyor, 4));
		
		startingScene.add(new ActionWait(10));
		
		startingScene.add(new ActionRotate(90, 0, 50));

		JarScene brickScene = new JarScene(testScript);

		for(int y = 1; y < 7; y++) {
			for(int x = 9; x < 12; x++) {
				for(int z = 6; z < 9; z++) {
					brickScene.add(new ActionSetBlock(x, y, z, Blocks.brick_block));
				}
			}
			brickScene.add(new ActionWait(2));
		}
		
		brickScene.add(new ActionRotate(-90, 0, 10));
		
		brickScene.add(new ActionWait(20));
		brickScene.add(new ActionRotate(45, 30, 10));
		brickScene.add(new ActionWait(40));
		brickScene.add(new ActionRotate(360, 0, 100));
		brickScene.add(new ActionWait(40));
		brickScene.add(new ActionRotate(-45, -30, 10));
		
		this.testScript.addScene(startingScene).addScene(brickScene);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		this.drawDefaultBackground();
		
		if(testScript != null) {
			testScript.run();
		}
		
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

		double pitch = testScript.lastRotationPitch + (testScript.rotationPitch - testScript.lastRotationPitch) * testScript.interp;
		double yaw = testScript.lastRotationYaw + (testScript.rotationYaw - testScript.lastRotationYaw) * testScript.interp;
		GL11.glRotated(pitch, 1, 0, 0);
		GL11.glRotated(yaw, 0, 1, 0);
		
		GL11.glTranslated(-7, 0 , -7);

		GL11.glTranslated(world.sizeX / 2D, 0 , world.sizeZ / 2D);
		//GL11.glRotated(System.currentTimeMillis() % (360 * 20) / 20D, 0, -1, 0);
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

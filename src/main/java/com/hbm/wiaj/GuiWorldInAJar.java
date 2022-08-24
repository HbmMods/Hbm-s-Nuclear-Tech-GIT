package com.hbm.wiaj;

import java.util.Map.Entry;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.blocks.ModBlocks;
import com.hbm.render.tileentity.RenderStirling;
import com.hbm.wiaj.actions.ActionCreateActor;
import com.hbm.wiaj.actions.ActionRotate;
import com.hbm.wiaj.actions.ActionSetActorData;
import com.hbm.wiaj.actions.ActionSetBlock;
import com.hbm.wiaj.actions.ActionUpdateActor;
import com.hbm.wiaj.actions.ActionWait;
import com.hbm.wiaj.actors.ActorTileEntity;
import com.hbm.wiaj.actors.ISpecialActor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;

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
		
		startingScene.add(new ActionWait(5));
		startingScene.add(new ActionRotate(90, 0, 10));

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
		brickScene.add(new ActionWait(20));
		brickScene.add(new ActionRotate(-45, -30, 10));
		brickScene.add(new ActionWait(20));
		
		brickScene.add(new ActionCreateActor(0, new ActorTileEntity(new RenderStirling())));
		NBTTagCompound stirling = new NBTTagCompound();
		stirling.setDouble("x", 7);
		stirling.setDouble("y", 1);
		stirling.setDouble("z", 7);
		stirling.setInteger("rotation", 2);
		stirling.setBoolean("hasCog", true);
		brickScene.add(new ActionSetActorData(0, stirling));
		brickScene.add(new ActionWait(20));
		brickScene.add(new ActionUpdateActor(0, "speed", 5F));
		brickScene.add(new ActionWait(20));
		brickScene.add(new ActionUpdateActor(0, "speed", 10F));
		brickScene.add(new ActionWait(20));
		brickScene.add(new ActionUpdateActor(0, "speed", 15F));
		brickScene.add(new ActionWait(20));
		brickScene.add(new ActionUpdateActor(0, "speed", 2F));
		brickScene.add(new ActionUpdateActor(0, "hasCog", false));
		brickScene.add(new ActionWait(20));
		brickScene.add(new ActionRotate(360, 0, 20));
		brickScene.add(new ActionWait(100));
		
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
		GL11.glScaled(1, 1, 0.01); //incredible flattening power

		double pitch = testScript.lastRotationPitch + (testScript.rotationPitch - testScript.lastRotationPitch) * testScript.interp;
		double yaw = testScript.lastRotationYaw + (testScript.rotationYaw - testScript.lastRotationYaw) * testScript.interp;
		GL11.glRotated(pitch, 1, 0, 0);
		GL11.glRotated(yaw, 0, 1, 0);
		
		GL11.glTranslated(-7, 0 , -7);

		GL11.glTranslated(world.sizeX / 2D, 0 , world.sizeZ / 2D);
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
		
		RenderHelper.enableStandardItemLighting();
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		GL11.glPushMatrix();
		
		GL11.glTranslated(width / 2, height / 2 + 70, 100);
		GL11.glScaled(scale, scale, scale);
		GL11.glScaled(1, 1, 0.01);
		GL11.glRotated(pitch, 1, 0, 0);
		GL11.glRotated(yaw, 0, 1, 0);
		GL11.glTranslated(-7, 0 , -7);
		GL11.glTranslated(world.sizeX / 2D, 0 , world.sizeZ / 2D);
		GL11.glTranslated(world.sizeX / -2D, 0 , world.sizeZ / -2D);
		
		for(Entry<Integer, ISpecialActor> actor : this.testScript.actors.entrySet()) {
			GL11.glPushMatrix();
			actor.getValue().draw(this.testScript.ticksElapsed, this.testScript.interp);
			GL11.glPopMatrix();
		}
		
		GL11.glPopMatrix();
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}

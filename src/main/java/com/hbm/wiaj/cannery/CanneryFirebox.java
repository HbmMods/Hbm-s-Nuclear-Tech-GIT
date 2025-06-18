package com.hbm.wiaj.cannery;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.main.ResourceManager;
import com.hbm.render.tileentity.RenderStirling;
import com.hbm.util.i18n.I18nUtil;
import com.hbm.wiaj.JarScene;
import com.hbm.wiaj.JarScript;
import com.hbm.wiaj.WorldInAJar;
import com.hbm.wiaj.actions.ActionCreateActor;
import com.hbm.wiaj.actions.ActionRemoveActor;
import com.hbm.wiaj.actions.ActionSetBlock;
import com.hbm.wiaj.actions.ActionSetTile;
import com.hbm.wiaj.actions.ActionSetZoom;
import com.hbm.wiaj.actions.ActionUpdateActor;
import com.hbm.wiaj.actions.ActionWait;
import com.hbm.wiaj.actors.ActorFancyPanel;
import com.hbm.wiaj.actors.ActorTileEntity;
import com.hbm.wiaj.actors.ITileActorRenderer;
import com.hbm.wiaj.actors.ActorFancyPanel.Orientation;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;

public class CanneryFirebox extends CanneryBase {

	@Override
	public ItemStack getIcon() {
		return new ItemStack(ModBlocks.heater_firebox);
	}

	@Override
	public String getName() {
		return "cannery.firebox";
	}

	public JarScript createScript() {

		WorldInAJar world = new WorldInAJar(5, 5, 5);
		JarScript script = new JarScript(world);
		
		JarScene scene0 = new JarScene(script);
		
		scene0.add(new ActionSetZoom(3, 0));
		
		for(int x = world.sizeX - 1; x >= 0 ; x--) {
			for(int z = 0; z < world.sizeZ; z++) {
				scene0.add(new ActionSetBlock(x, 0, z, Blocks.brick_block));
			}
			scene0.add(new ActionWait(2));
		}
		
		scene0.add(new ActionWait(8));
		
		NBTTagCompound firebox = new NBTTagCompound(); firebox.setDouble("x", 2); firebox.setDouble("y", 1); firebox.setDouble("z", 2); firebox.setInteger("rotation", 5);
		scene0.add(new ActionCreateActor(0, new ActorTileEntity(new ActorFirebox(), firebox)));
		
		scene0.add(new ActionWait(10));

		scene0.add(new ActionCreateActor(1, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 0, -10, new Object[][] {{I18nUtil.resolveKey("cannery.firebox.0")}}, 150)
				.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));
		
		scene0.add(new ActionWait(60));

		scene0.add(new ActionCreateActor(1, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 0, -10, new Object[][] {{I18nUtil.resolveKey("cannery.firebox.1")}}, 250)
				.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));
		
		scene0.add(new ActionWait(60));
		scene0.add(new ActionRemoveActor(1));
		scene0.add(new ActionWait(5));
		scene0.add(new ActionUpdateActor(0, "open", true));
		scene0.add(new ActionWait(30));
		
		scene0.add(new ActionUpdateActor(0, "isOn", true));

		scene0.add(new ActionCreateActor(1, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, -50, 40, new Object[][] {{new ItemStack(Items.coal)}}, 0)
				.setColors(colorCopper).setOrientation(Orientation.RIGHT)));
		scene0.add(new ActionWait(10));
		scene0.add(new ActionCreateActor(1, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, -50, 40, new Object[][] {{new ItemStack(ModItems.coke)}}, 0)
				.setColors(colorCopper).setOrientation(Orientation.RIGHT)));
		scene0.add(new ActionWait(10));
		scene0.add(new ActionCreateActor(1, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, -50, 40, new Object[][] {{new ItemStack(ModItems.solid_fuel)}}, 0)
				.setColors(colorCopper).setOrientation(Orientation.RIGHT)));
		scene0.add(new ActionWait(10));
		scene0.add(new ActionCreateActor(1, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, -50, 40, new Object[][] {{new ItemStack(ModItems.rocket_fuel)}}, 0)
				.setColors(colorCopper).setOrientation(Orientation.RIGHT)));
		scene0.add(new ActionWait(10));
		scene0.add(new ActionCreateActor(1, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, -50, 40, new Object[][] {{new ItemStack(ModItems.solid_fuel_bf)}}, 0)
				.setColors(colorCopper).setOrientation(Orientation.RIGHT)));
		scene0.add(new ActionWait(10));
		scene0.add(new ActionRemoveActor(1));
		scene0.add(new ActionWait(10));
		scene0.add(new ActionUpdateActor(0, "open", false));
		scene0.add(new ActionWait(30));

		scene0.add(new ActionCreateActor(1, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 0, -10, new Object[][] {{I18nUtil.resolveKey("cannery.firebox.2")}}, 250)
				.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));
		
		scene0.add(new ActionWait(80));

		scene0.add(new ActionCreateActor(1, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 0, -10, new Object[][] {{I18nUtil.resolveKey("cannery.firebox.3")}}, 250)
				.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));
		
		scene0.add(new ActionWait(60));
		scene0.add(new ActionRemoveActor(1));
		scene0.add(new ActionWait(10));
		
		JarScene scene1 = new JarScene(script);
		
		NBTTagCompound stirling = new NBTTagCompound();
		stirling.setDouble("x", 2);
		stirling.setDouble("y", 2);
		stirling.setDouble("z", 2);
		stirling.setInteger("rotation", 2);
		stirling.setBoolean("hasCog", true);
		scene1.add(new ActionCreateActor(1, new ActorTileEntity(new RenderStirling(), stirling)));
		scene1.add(new ActionUpdateActor(1, "speed", 0F));

		scene1.add(new ActionWait(10));
		scene1.add(new ActionCreateActor(2, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 0, -45, new Object[][] {{I18nUtil.resolveKey("cannery.firebox.4")}}, 250)
				.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));
		scene1.add(new ActionWait(60));
		scene1.add(new ActionRemoveActor(2));
		scene1.add(new ActionWait(10));
		
		for(int i = 0; i < 60; i++) {
			scene1.add(new ActionUpdateActor(1, "speed", i / 5F));
			scene1.add(new ActionWait(1));
		}
		
		scene1.add(new ActionSetTile(1, 2, 2, new Dummies.JarDummyConnector()));
		scene1.add(new ActionSetTile(0, 2, 2, new Dummies.JarDummyConnector()));
		scene1.add(new ActionSetTile(0, 1, 2, new Dummies.JarDummyConnector()));
		scene1.add(new ActionSetTile(0, 1, 3, new Dummies.JarDummyConnector()));
		scene1.add(new ActionSetBlock(0, 2, 2, ModBlocks.red_cable));
		scene1.add(new ActionSetBlock(0, 1, 2, ModBlocks.red_cable));
		scene1.add(new ActionSetBlock(0, 1, 3, ModBlocks.machine_detector, 0));
		scene1.add(new ActionWait(10));
		scene1.add(new ActionSetBlock(0, 1, 3, ModBlocks.machine_detector, 1));
		scene1.add(new ActionWait(100));
		
		script.addScene(scene0).addScene(scene1);
		return script;
	}
	
	public static class ActorFirebox implements ITileActorRenderer {

		@Override
		public void renderActor(WorldInAJar world, int ticks, float interp, NBTTagCompound data) {
			double x = data.getDouble("x");
			double y = data.getDouble("y");
			double z = data.getDouble("z");
			int rotation = data.getInteger("rotation");
			boolean isOn = data.getBoolean("isOn");
			float doorAngle = data.getFloat("angle");
			float prevDoorAngle = data.getFloat("lastAngle");
			
			GL11.glTranslated(x + 0.5D, y, z + 0.5D);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glShadeModel(GL11.GL_SMOOTH);
			GL11.glEnable(GL11.GL_CULL_FACE);
			
			switch(rotation) {
			case 3: GL11.glRotatef(0, 0F, 1F, 0F); break;
			case 5: GL11.glRotatef(90, 0F, 1F, 0F); break;
			case 2: GL11.glRotatef(180, 0F, 1F, 0F); break;
			case 4: GL11.glRotatef(270, 0F, 1F, 0F); break;
			}

			ITileActorRenderer.bindTexture(ResourceManager.heater_firebox_tex);
			ResourceManager.heater_firebox.renderPart("Main");
			
			GL11.glPushMatrix();
			
			float door = prevDoorAngle + (doorAngle - prevDoorAngle) * interp;
			GL11.glTranslated(1.375, 0, 0.375);
			GL11.glRotatef(door, 0F, -1F, 0F);
			GL11.glTranslated(-1.375, 0, -0.375);
			ResourceManager.heater_firebox.renderPart("Door");
			GL11.glPopMatrix();
			
			if(isOn) {
				GL11.glPushMatrix();
				GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
				
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_CULL_FACE);
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
				ResourceManager.heater_firebox.renderPart("InnerBurning");
				GL11.glEnable(GL11.GL_LIGHTING);
				
				GL11.glPopAttrib();
				GL11.glPopMatrix();
			} else {
				ResourceManager.heater_firebox.renderPart("InnerEmpty");
			}
		}

		@Override
		public void updateActor(int ticks, NBTTagCompound data) {
			
			boolean open = data.getBoolean("open");
			float doorAngle = data.getFloat("angle");
			data.setFloat("lastAngle", doorAngle);
			
			float swingSpeed = (doorAngle / 10F) + 3;
			
			if(open) {
				doorAngle += swingSpeed;
			} else {
				doorAngle -= swingSpeed;
			}
			
			doorAngle = MathHelper.clamp_float(doorAngle, 0F, 135F);
			data.setFloat("angle", doorAngle);
		}
	}
}

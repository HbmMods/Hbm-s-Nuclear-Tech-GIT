package com.hbm.wiaj.cannery;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
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
import com.hbm.wiaj.cannery.CanneryFirebox.ActorFirebox;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class CanneryStirling extends CanneryBase {

	@Override
	public ItemStack getIcon() {
		return new ItemStack(ModBlocks.machine_stirling);
	}

	@Override
	public String getName() {
		return "cannery.stirling";
	}
	public CanneryBase[] seeAlso() {
		return new CanneryBase[] {
				new CanneryFirebox()
		};
	}

	@Override
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
		
		NBTTagCompound stirling = new NBTTagCompound();
		stirling.setDouble("x", 2);
		stirling.setDouble("z", 2);
		stirling.setInteger("rotation", 2);
		scene0.add(new ActionCreateActor(1, new ActorTileEntity(new RenderStirling(), stirling)));
		/*
		 * When rewinding, the NBT tag persists. We have to manually set all variable values with UpdateActor.
		 */
		scene0.add(new ActionUpdateActor(1, "speed", 0F));
		scene0.add(new ActionUpdateActor(1, "y", 2D));
		scene0.add(new ActionUpdateActor(1, "type", 0));
		scene0.add(new ActionUpdateActor(1, "hasCog", true));
		scene0.add(new ActionUpdateActor(1, "spin", 0F));
		scene0.add(new ActionUpdateActor(1, "lastSpin", 0F));
		
		scene0.add(new ActionWait(10));
		
		scene0.add(new ActionCreateActor(2, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 0, -45, new Object[][] {{I18nUtil.resolveKey("cannery.stirling.0")}}, 250)
				.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));
		
		scene0.add(new ActionWait(60));
		
		scene0.add(new ActionCreateActor(2, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 0, 40, new Object[][] {{I18nUtil.resolveKey("cannery.stirling.1")}}, 250)
				.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));
		
		scene0.add(new ActionWait(60));
		scene0.add(new ActionRemoveActor(2));
		scene0.add(new ActionWait(5));
		scene0.add(new ActionUpdateActor(0, "open", true));
		scene0.add(new ActionWait(30));
		scene0.add(new ActionUpdateActor(0, "isOn", true));
		scene0.add(new ActionCreateActor(2, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, -50, 40, new Object[][] {{new ItemStack(Items.coal)}}, 0)
				.setColors(colorCopper).setOrientation(Orientation.RIGHT)));
		scene0.add(new ActionWait(20));
		scene0.add(new ActionRemoveActor(2));
		scene0.add(new ActionWait(10));
		scene0.add(new ActionUpdateActor(0, "open", false));
		scene0.add(new ActionWait(30));
		
		for(int i = 0; i < 60; i++) {
			scene0.add(new ActionUpdateActor(1, "speed", i / 5F));
			scene0.add(new ActionWait(1));
		}
		
		scene0.add(new ActionWait(20));
		
		scene0.add(new ActionSetTile(1, 2, 2, new Dummies.JarDummyConnector()));
		scene0.add(new ActionSetTile(0, 2, 2, new Dummies.JarDummyConnector()));
		scene0.add(new ActionSetTile(0, 1, 2, new Dummies.JarDummyConnector()));
		scene0.add(new ActionSetTile(0, 1, 3, new Dummies.JarDummyConnector()));
		scene0.add(new ActionSetBlock(0, 2, 2, ModBlocks.red_cable));
		scene0.add(new ActionSetBlock(0, 1, 2, ModBlocks.red_cable));
		scene0.add(new ActionSetBlock(0, 1, 3, ModBlocks.machine_detector, 0));
		scene0.add(new ActionWait(10));
		scene0.add(new ActionSetBlock(0, 1, 3, ModBlocks.machine_detector, 1));
		scene0.add(new ActionWait(40));
		
		JarScene scene1 = new JarScene(script);
		
		scene1.add(new ActionCreateActor(2, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 0, -45, new Object[][] {{I18nUtil.resolveKey("cannery.stirling.2")}}, 250)
				.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));
		scene1.add(new ActionWait(60));
		scene1.add(new ActionRemoveActor(2));
		
		NBTTagCompound burner = new NBTTagCompound(); burner.setDouble("x", 2); burner.setDouble("y", 1); burner.setDouble("z", 2); burner.setInteger("rotation", 5);
		scene1.add(new ActionCreateActor(0, new ActorTileEntity(new ActorBurner(), burner)));
		scene1.add(new ActionUpdateActor(1, "y", 3D));
		scene1.add(new ActionSetTile(1, 2, 2, null));
		scene1.add(new ActionSetTile(1, 3, 2, new Dummies.JarDummyConnector()));
		scene1.add(new ActionSetTile(0, 3, 2, new Dummies.JarDummyConnector()));
		scene1.add(new ActionSetBlock(0, 3, 2, ModBlocks.red_cable));
		
		for(int i = 0; i < 100; i++) {
			scene1.add(new ActionUpdateActor(1, "speed", (i + 60) / 5F));
			scene1.add(new ActionWait(1));
		}
		
		scene1.add(new ActionWait(20));
		scene1.add(new ActionUpdateActor(1, "hasCog", false));
		scene1.add(new ActionSetBlock(0, 1, 3, ModBlocks.machine_detector, 0));
		
		for(int i = 0; i < 160; i += 10) {
			scene1.add(new ActionUpdateActor(1, "speed", (160 - i) / 5F));
			scene1.add(new ActionWait(1));
		}
		scene1.add(new ActionUpdateActor(1, "speed", 0F));
		
		scene1.add(new ActionWait(20));
		
		scene1.add(new ActionCreateActor(2, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 0, -45, new Object[][] {{I18nUtil.resolveKey("cannery.stirling.3")}}, 250)
				.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));
		scene1.add(new ActionWait(60));
		scene1.add(new ActionRemoveActor(2));
		scene1.add(new ActionWait(20));
		scene1.add(new ActionUpdateActor(1, "hasCog", true));
		scene1.add(new ActionUpdateActor(1, "type", 1));
		scene1.add(new ActionWait(20));
		
		for(int i = 0; i < 60; i++) {
			scene1.add(new ActionUpdateActor(1, "speed", i / 5F));
			scene1.add(new ActionWait(1));
		}
		
		scene1.add(new ActionSetBlock(0, 1, 3, ModBlocks.machine_detector, 1));
		scene1.add(new ActionWait(100));
		
		script.addScene(scene0).addScene(scene1);
		return script;
	}
	
	public static class ActorBurner implements ITileActorRenderer {

		@Override
		public void renderActor(WorldInAJar world, int ticks, float interp, NBTTagCompound data) {
			double x = data.getDouble("x");
			double y = data.getDouble("y");
			double z = data.getDouble("z");
			
			GL11.glTranslated(x + 0.5D, y, z + 0.5D);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glShadeModel(GL11.GL_SMOOTH);
			GL11.glEnable(GL11.GL_CULL_FACE);

			ITileActorRenderer.bindTexture(ResourceManager.heater_oilburner_tex);
			ResourceManager.heater_oilburner.renderAll();
		}

		@Override
		public void updateActor(int ticks, NBTTagCompound data) { }
	}
}

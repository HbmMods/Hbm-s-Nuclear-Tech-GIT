package com.hbm.wiaj.cannery;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFELCrystal.EnumWavelengths;
import com.hbm.main.ResourceManager;
import com.hbm.render.util.BeamPronter;
import com.hbm.render.util.BeamPronter.EnumBeamType;
import com.hbm.render.util.BeamPronter.EnumWaveType;
import com.hbm.tileentity.network.TileEntityPipeBaseNT;
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
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;

public class CannerySILEX extends CanneryBase {

	@Override
	public ItemStack getIcon() {
		return new ItemStack(ModBlocks.machine_silex);
	}

	@Override
	public String getName() {
		return "cannery.silex";
	}

	public JarScript createScript() {
		WorldInAJar world = new WorldInAJar(17, 5, 5);
		JarScript script = new JarScript(world);
		
		JarScene scene0 = new JarScene(script);
		
		scene0.add(new ActionSetZoom(2, 0));
		
		for(int x = world.sizeX - 1; x >= 0 ; x--) {
			for(int z = 0; z < world.sizeZ; z++) {
				scene0.add(new ActionSetBlock(x, 0, z, Blocks.brick_block));
			}
			
			scene0.add(new ActionWait(1));
		}
		
		scene0.add(new ActionWait(9));
		
		NBTTagCompound fel = new NBTTagCompound();
		fel.setDouble("x", 11D);
		fel.setDouble("y", 1D);
		fel.setDouble("z", 2D);
		fel.setInteger("rotation", 5);
		fel.setInteger("length", 11);
		scene0.add(new ActionCreateActor(0, new ActorTileEntity(new ActorFEL(), fel)));

		scene0.add(new ActionCreateActor(3, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 15, -5, new Object[][] {{I18nUtil.resolveKey("cannery.silex.0")}}, 100)
				.setColors(colorCopper).setOrientation(Orientation.LEFT)));

		scene0.add(new ActionWait(80));
		scene0.add(new ActionRemoveActor(3));
		scene0.add(new ActionWait(10));

		scene0.add(new ActionCreateActor(3, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, -7, -15, new Object[][] {{new ItemStack(ModItems.laser_crystal_co2)}}, 0)
				.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));
		scene0.add(new ActionUpdateActor(0, "mode", 1));
		scene0.add(new ActionWait(20));
		scene0.add(new ActionCreateActor(3, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, -7, -15, new Object[][] {{new ItemStack(ModItems.laser_crystal_bismuth)}}, 0)
				.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));
		scene0.add(new ActionUpdateActor(0, "mode", 2));
		scene0.add(new ActionWait(20));
		scene0.add(new ActionCreateActor(3, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, -7, -15, new Object[][] {{new ItemStack(ModItems.laser_crystal_cmb)}}, 0)
				.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));
		scene0.add(new ActionUpdateActor(0, "mode", 3));
		scene0.add(new ActionWait(20));
		scene0.add(new ActionCreateActor(3, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, -7, -15, new Object[][] {{new ItemStack(ModItems.laser_crystal_dnt)}}, 0)
				.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));
		scene0.add(new ActionUpdateActor(0, "mode", 4));
		scene0.add(new ActionWait(20));
		scene0.add(new ActionCreateActor(3, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, -7, -15, new Object[][] {{new ItemStack(ModItems.laser_crystal_digamma)}}, 0)
				.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));
		scene0.add(new ActionUpdateActor(0, "mode", 5));
		scene0.add(new ActionWait(20));
		
		scene0.add(new ActionRemoveActor(3));
		scene0.add(new ActionUpdateActor(0, "mode", 0));
		scene0.add(new ActionWait(20));
		
		for(int y = 1; y < 4; y++) {
			for(int z = 1; z < 4; z++) {
				scene0.add(new ActionSetBlock(5, y, z, Blocks.stone));
			}
			scene0.add(new ActionWait(5));
		}
		
		scene0.add(new ActionCreateActor(3, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, -7, -15, new Object[][] {{I18nUtil.resolveKey("cannery.silex.1")}}, 150)
				.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));
		scene0.add(new ActionUpdateActor(0, "mode", 3));
		scene0.add(new ActionUpdateActor(0, "length", 4));
		
		scene0.add(new ActionWait(40));
		scene0.add(new ActionUpdateActor(0, "length", 11));
		scene0.add(new ActionSetBlock(5, 2, 2, Blocks.fire));
		scene0.add(new ActionWait(10));
		scene0.add(new ActionSetBlock(5, 2, 2, Blocks.air));
		scene0.add(new ActionWait(40));
		
		for(int y = 1; y < 4; y++) {
			for(int z = 1; z < 4; z++) {
				scene0.add(new ActionSetBlock(5, y, z, ModBlocks.brick_concrete));
			}
			
			if(y == 2) {
				scene0.add(new ActionUpdateActor(0, "length", 4));
			}
			
			scene0.add(new ActionWait(5));
		}
		
		scene0.add(new ActionCreateActor(3, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, -7, -15, new Object[][] {{I18nUtil.resolveKey("cannery.silex.2")}}, 150)
				.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));
		scene0.add(new ActionWait(40));
		scene0.add(new ActionRemoveActor(3));
		
		for(int y = 3; y > 0; y--) {
			for(int z = 1; z < 4; z++) {
				scene0.add(new ActionSetBlock(5, y, z, Blocks.air));
			}
			
			if(y == 2) {
				scene0.add(new ActionUpdateActor(0, "length", 11));
			}
			
			scene0.add(new ActionWait(5));
		}
		
		scene0.add(new ActionUpdateActor(0, "mode", 0));
		
		JarScene scene1 = new JarScene(script);
		
		NBTTagCompound silex0 = new NBTTagCompound();
		silex0.setDouble("x", 5D);
		silex0.setDouble("y", 1D);
		silex0.setDouble("z", 2D);
		silex0.setInteger("rotation", 2);
		scene1.add(new ActionCreateActor(1, new ActorTileEntity(new ActorSILEX(), silex0)));
		
		scene1.add(new ActionWait(20));
		
		scene1.add(new ActionCreateActor(3, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 42, -18, new Object[][] {{I18nUtil.resolveKey("cannery.silex.3")}}, 150)
				.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));
		
		scene1.add(new ActionWait(80));
		
		scene1.add(new ActionCreateActor(3, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 60, 32, new Object[][] {{I18nUtil.resolveKey("cannery.silex.4")}}, 150)
				.setColors(colorCopper).setOrientation(Orientation.RIGHT)));
		
		scene1.add(new ActionWait(60));
		
		scene1.add(new ActionCreateActor(3, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 12, 32, new Object[][] {{I18nUtil.resolveKey("cannery.silex.5")}}, 150)
				.setColors(colorCopper).setOrientation(Orientation.RIGHT)));
		
		scene1.add(new ActionWait(60));
		scene1.add(new ActionRemoveActor(3));
		scene1.add(new ActionWait(10));

		TileEntityPipeBaseNT duct = new TileEntityPipeBaseNT();
		duct.setType(Fluids.PEROXIDE);
		scene1.add(new ActionSetTile(5, 2, 0, duct));
		scene1.add(new ActionSetTile(5, 1, 0, duct));
		scene1.add(new ActionSetTile(6, 1, 0, duct));
		scene1.add(new ActionSetTile(7, 1, 0, new Dummies.JarDummyConnector()));
		scene1.add(new ActionSetTile(5, 2, 1, new Dummies.JarDummyConnector()));
		scene1.add(new ActionSetBlock(5, 2, 0, ModBlocks.fluid_duct_neo));
		scene1.add(new ActionSetBlock(5, 1, 0, ModBlocks.fluid_duct_neo));
		scene1.add(new ActionSetBlock(6, 1, 0, ModBlocks.fluid_duct_neo));
		scene1.add(new ActionSetBlock(7, 1, 0, ModBlocks.barrel_tcalloy));
		
		scene1.add(new ActionWait(20));
		scene1.add(new ActionUpdateActor(0, "mode", 3));
		scene1.add(new ActionWait(10));
		
		scene1.add(new ActionCreateActor(3, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 42, -18, new Object[][] {{I18nUtil.resolveKey("cannery.silex.6")}}, 150)
				.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));
		
		scene1.add(new ActionWait(80));
		
		scene1.add(new ActionCreateActor(3, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 42, -18, new Object[][] {{I18nUtil.resolveKey("cannery.silex.7")}}, 150)
				.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));
		
		scene1.add(new ActionWait(60));
		
		scene1.add(new ActionCreateActor(3, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, -7, -15, new Object[][] {{I18nUtil.resolveKey("cannery.silex.8")}}, 150)
				.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));
		
		scene1.add(new ActionWait(60));
		scene1.add(new ActionRemoveActor(3));
		scene1.add(new ActionWait(10));
		
		NBTTagCompound silex1 = new NBTTagCompound();
		silex1.setDouble("x", 1D);
		silex1.setDouble("y", 1D);
		silex1.setDouble("z", 2D);
		silex1.setInteger("rotation", 2);
		scene1.add(new ActionCreateActor(2, new ActorTileEntity(new ActorSILEX(), silex1)));
		scene1.add(new ActionWait(10));
		
		for(int i = 1; i < 5; i++) {
			scene1.add(new ActionSetTile(i, 1, 0, duct));
			scene1.add(new ActionSetBlock(i, 1, 0, ModBlocks.fluid_duct_neo));
		}
		scene1.add(new ActionSetTile(1, 2, 0, duct));
		scene1.add(new ActionSetBlock(1, 2, 0, ModBlocks.fluid_duct_neo));
		scene1.add(new ActionSetTile(1, 2, 1, new Dummies.JarDummyConnector()));
		
		scene1.add(new ActionWait(20));
		
		script.addScene(scene0).addScene(scene1);
		return script;
	}
	
	public static class ActorFEL implements ITileActorRenderer {

		@Override
		public void renderActor(WorldInAJar world, int ticks, float interp, NBTTagCompound data) {
			double x = data.getDouble("x");
			double y = data.getDouble("y");
			double z = data.getDouble("z");
			int rotation = data.getInteger("rotation");
			int mode = data.getInteger("mode");
			int length = data.getInteger("length");
			
			GL11.glTranslated(x + 0.5D, y, z + 0.5D);
			GL11.glEnable(GL11.GL_LIGHTING);
			
			switch(rotation) {
			case 3: GL11.glRotatef(0, 0F, 1F, 0F); break;
			case 5: GL11.glRotatef(90, 0F, 1F, 0F); break;
			case 2: GL11.glRotatef(180, 0F, 1F, 0F); break;
			case 4: GL11.glRotatef(270, 0F, 1F, 0F); break;
			}

			ITileActorRenderer.bindTexture(ResourceManager.fel_tex);
			ResourceManager.fel.renderAll();
			
			GL11.glTranslated(0, 1.5, -1.5);
			if(mode > 0 && mode < 6) {
				EnumWavelengths wave = EnumWavelengths.values()[mode];
				int color = wave.guiColor;
				if(color == 0)
					color = Color.HSBtoRGB(Minecraft.getMinecraft().theWorld.getTotalWorldTime() / 50.0F, 0.5F, 0.1F) & 16777215;
				
				BeamPronter.prontBeamwithDepth(Vec3.createVectorHelper(0, 0, -length - 1), EnumWaveType.SPIRAL, EnumBeamType.SOLID, color, color, 0, 1, 0F, 2, 0.0625F);
				BeamPronter.prontBeamwithDepth(Vec3.createVectorHelper(0, 0, -length - 1), EnumWaveType.RANDOM, EnumBeamType.SOLID, color, color, (int)(Minecraft.getMinecraft().theWorld.getTotalWorldTime() % 1000 / 2), (length / 2) + 1, 0.0625F, 2, 0.0625F);
			}
		}

		@Override
		public void updateActor(int ticks, NBTTagCompound data) { }
	}
	
	public static class ActorSILEX implements ITileActorRenderer {

		@Override
		public void renderActor(WorldInAJar world, int ticks, float interp, NBTTagCompound data) {
			double x = data.getDouble("x");
			double y = data.getDouble("y");
			double z = data.getDouble("z");
			int rotation = data.getInteger("rotation");
			
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

			ITileActorRenderer.bindTexture(ResourceManager.silex_tex);
			ResourceManager.silex.renderAll();
			GL11.glShadeModel(GL11.GL_FLAT);
		}

		@Override
		public void updateActor(int ticks, NBTTagCompound data) { }
	}
}

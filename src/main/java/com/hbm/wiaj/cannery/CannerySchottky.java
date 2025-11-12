package com.hbm.wiaj.cannery;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.tileentity.machine.TileEntityHadronDiode;
import com.hbm.util.i18n.I18nUtil;
import com.hbm.wiaj.JarScene;
import com.hbm.wiaj.JarScript;
import com.hbm.wiaj.WorldInAJar;
import com.hbm.wiaj.actions.ActionCreateActor;
import com.hbm.wiaj.actions.ActionRemoveActor;
import com.hbm.wiaj.actions.ActionRotateBy;
import com.hbm.wiaj.actions.ActionSetBlock;
import com.hbm.wiaj.actions.ActionSetTile;
import com.hbm.wiaj.actions.ActionSetZoom;
import com.hbm.wiaj.actions.ActionWait;
import com.hbm.wiaj.actors.ActorFancyPanel;
import com.hbm.wiaj.actors.ActorFancyPanel.Orientation;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class CannerySchottky extends CanneryBase {
	
	@Override
	public ItemStack getIcon() {
		return new ItemStack(ModBlocks.hadron_diode);
	}

	@Override
	public String getName() {
		return "cannery.schottky";
	}
	
	@Override
	public CanneryBase[] seeAlso() {
		return new CanneryBase[] {
			new CanneryHadron()
		};
	}

	@Override
	public JarScript createScript() {
		WorldInAJar world = new WorldInAJar(5, 5, 5);
		JarScript script = new JarScript(world);
		

		// FIRST SCENE: Show and explain the diode
		JarScene scene0 = new JarScene(script);
		scene0.add(new ActionSetZoom(4, 0));

		scene0.add(new ActionSetTile(2, 2, 2, new TileEntityHadronDiode()));
		scene0.add(new ActionSetBlock(2, 2, 2, ModBlocks.hadron_diode));
		
		scene0.add(new ActionCreateActor(1, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 0, -20, new Object[][] {{I18nUtil.resolveKey("cannery.schottky.0")}}, 200)
			.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));

		scene0.add(new ActionWait(100));
		scene0.add(new ActionRemoveActor(1));
		scene0.add(new ActionWait(5));
		
		scene0.add(new ActionCreateActor(1, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 0, -20, new Object[][] {{I18nUtil.resolveKey("cannery.schottky.1")}}, 200)
			.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));

		scene0.add(new ActionWait(80));
		scene0.add(new ActionRemoveActor(1));
		scene0.add(new ActionWait(10));

		scene0.add(new ActionCreateActor(2, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, -14, 8, new Object[][] {{new ItemStack(ModItems.screwdriver)}}, 0)
			.setColors(colorCopper).setOrientation(Orientation.RIGHT)));

		scene0.add(new ActionWait(20));
		scene0.add(new ActionRemoveActor(2));

		scene0.add(new ActionSetTile(2, 2, 2, new TileEntityHadronDiode() {{ sides[2] = DiodeConfig.IN; }}));

		scene0.add(new ActionWait(10));
		
		scene0.add(new ActionCreateActor(3, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, -14, 8, new Object[][] {{I18nUtil.resolveKey("cannery.schottky.2")}}, 100)
			.setColors(colorCopper).setOrientation(Orientation.RIGHT)));

		scene0.add(new ActionWait(60));
		scene0.add(new ActionRemoveActor(3));
		scene0.add(new ActionWait(10));

		scene0.add(new ActionCreateActor(4, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 14, 8, new Object[][] {{new ItemStack(ModItems.screwdriver)}}, 0)
			.setColors(colorCopper).setOrientation(Orientation.LEFT)));

		scene0.add(new ActionWait(10));
		scene0.add(new ActionRemoveActor(4));

		scene0.add(new ActionSetTile(2, 2, 2, new TileEntityHadronDiode() {{ sides[2] = DiodeConfig.IN; sides[4] = DiodeConfig.IN; }}));

		scene0.add(new ActionWait(5));

		scene0.add(new ActionCreateActor(5, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 14, 8, new Object[][] {{new ItemStack(ModItems.screwdriver)}}, 0)
			.setColors(colorCopper).setOrientation(Orientation.LEFT)));

		scene0.add(new ActionWait(10));
		scene0.add(new ActionRemoveActor(5));

		scene0.add(new ActionSetTile(2, 2, 2, new TileEntityHadronDiode() {{ sides[2] = DiodeConfig.IN; sides[4] = DiodeConfig.OUT; }}));

		scene0.add(new ActionWait(10));
		
		scene0.add(new ActionCreateActor(6, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 14, 8, new Object[][] {{I18nUtil.resolveKey("cannery.schottky.3")}}, 100)
			.setColors(colorCopper).setOrientation(Orientation.LEFT)));

		scene0.add(new ActionWait(60));
		scene0.add(new ActionRemoveActor(6));
		scene0.add(new ActionWait(10));
		

		// SECOND SCENE: Add another entrance and exit
		JarScene scene1 = new JarScene(script);
		scene1.add(new ActionSetZoom(4, 0));

		scene1.add(new ActionRotateBy(180, 0, 10));

		scene1.add(new ActionCreateActor(2, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, -14, 8, new Object[][] {{new ItemStack(ModItems.screwdriver)}}, 0)
			.setColors(colorCopper).setOrientation(Orientation.RIGHT)));

		scene1.add(new ActionWait(10));
		scene1.add(new ActionRemoveActor(2));

		scene1.add(new ActionSetTile(2, 2, 2, new TileEntityHadronDiode() {{ sides[2] = DiodeConfig.IN; sides[4] = DiodeConfig.OUT; sides[3] = DiodeConfig.IN; }}));

		scene1.add(new ActionWait(10));

		scene1.add(new ActionCreateActor(4, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 14, 8, new Object[][] {{new ItemStack(ModItems.screwdriver)}}, 0)
			.setColors(colorCopper).setOrientation(Orientation.LEFT)));

		scene1.add(new ActionWait(10));
		scene1.add(new ActionRemoveActor(4));

		scene1.add(new ActionSetTile(2, 2, 2, new TileEntityHadronDiode() {{ sides[2] = DiodeConfig.IN; sides[4] = DiodeConfig.OUT; sides[3] = DiodeConfig.IN; sides[5] = DiodeConfig.IN; }}));

		scene1.add(new ActionWait(5));

		scene1.add(new ActionCreateActor(5, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 14, 8, new Object[][] {{new ItemStack(ModItems.screwdriver)}}, 0)
			.setColors(colorCopper).setOrientation(Orientation.LEFT)));

		scene1.add(new ActionWait(10));
		scene1.add(new ActionRemoveActor(5));

		scene1.add(new ActionSetTile(2, 2, 2, new TileEntityHadronDiode() {{ sides[2] = DiodeConfig.IN; sides[4] = DiodeConfig.OUT; sides[3] = DiodeConfig.IN; sides[5] = DiodeConfig.OUT; }}));

		scene1.add(new ActionWait(10));

		scene1.add(new ActionRotateBy(-180, 0, 10));
		
		scene1.add(new ActionCreateActor(1, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 0, -20, new Object[][] {{I18nUtil.resolveKey("cannery.schottky.4")}}, 200)
			.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));

		scene1.add(new ActionWait(100));
		scene1.add(new ActionRemoveActor(1));
		scene1.add(new ActionWait(10));
		
		scene1.add(new ActionCreateActor(1, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 0, -20, new Object[][] {{I18nUtil.resolveKey("cannery.schottky.5")}}, 200)
			.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));

		scene1.add(new ActionWait(100));
		scene1.add(new ActionRemoveActor(1));
		scene1.add(new ActionWait(10));
		
		scene1.add(new ActionCreateActor(1, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 0, -20, new Object[][] {{I18nUtil.resolveKey("cannery.schottky.6")}}, 200)
			.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));

		scene1.add(new ActionWait(100));
		scene1.add(new ActionRemoveActor(1));
		scene1.add(new ActionWait(10));
		
		scene1.add(new ActionCreateActor(1, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 0, -20, new Object[][] {{I18nUtil.resolveKey("cannery.schottky.7")}}, 200)
			.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));

		scene1.add(new ActionWait(100));
		scene1.add(new ActionRemoveActor(1));
		scene1.add(new ActionWait(10));
		
		scene1.add(new ActionCreateActor(1, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 0, -20, new Object[][] {{I18nUtil.resolveKey("cannery.schottky.8")}}, 200)
			.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));

		scene1.add(new ActionWait(100));
		scene1.add(new ActionRemoveActor(1));
		scene1.add(new ActionWait(10));



		// THIRD SCENE: Correctly enclose the diode
		JarScene scene2 = new JarScene(script);
		scene2.add(new ActionSetZoom(4, 0));

		scene2.add(new ActionSetZoom(-2, 10));

		for(int x = 0; x < 5; x++) {
			for(int z = 0; z < 5; z++) {
				if((x == 0 || x == 4) && (z == 0 || z == 4)) continue;
				scene2.add(new ActionSetBlock(x, 0, z, ModBlocks.hadron_plating));
				scene2.add(new ActionWait(1));
			}
		}

		for(int x = 0; x < 5; x++) {
			for(int z = 0; z < 5; z++) {
				scene2.add(new ActionSetBlock(x, 1, z, (x == 0 || x == 4) && (z == 0 || z == 4) ? ModBlocks.hadron_plating : ModBlocks.hadron_coil_alloy));
				scene2.add(new ActionWait(1));
			}
		}

		for(int x = 0; x < 5; x++) {
			for(int z = 0; z < 5; z++) {
				if(x == 2 || z == 2) continue;
				scene2.add(new ActionSetBlock(x, 2, z, (x == 0 || x == 4) && (z == 0 || z == 4) ? ModBlocks.hadron_plating : ModBlocks.hadron_coil_alloy));
				scene2.add(new ActionWait(1));
			}
		}
		
		scene2.add(new ActionCreateActor(1, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 0, -20, new Object[][] {{I18nUtil.resolveKey("cannery.schottky.9")}}, 200)
			.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));


		scene2.add(new ActionRotateBy(360, 0, 100));
		scene2.add(new ActionRemoveActor(1));
		scene2.add(new ActionWait(10));

		for(int x = 0; x < 5; x++) {
			for(int z = 0; z < 5; z++) {
				scene2.add(new ActionSetBlock(x, 3, z, (x == 0 || x == 4) && (z == 0 || z == 4) ? ModBlocks.hadron_plating : ModBlocks.hadron_coil_alloy));
				scene2.add(new ActionWait(1));
			}
		}

		for(int x = 0; x < 5; x++) {
			for(int z = 0; z < 5; z++) {
				if((x == 0 || x == 4) && (z == 0 || z == 4)) continue;
				scene2.add(new ActionSetBlock(x, 4, z, ModBlocks.hadron_plating));
				scene2.add(new ActionWait(1));
			}
		}


		script
			.addScene(scene0)
			.addScene(scene1)
			.addScene(scene2);

		return script;
	}

}

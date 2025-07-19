package com.hbm.wiaj.cannery;

import com.hbm.blocks.ModBlocks;
import com.hbm.util.i18n.I18nUtil;
import com.hbm.wiaj.JarScene;
import com.hbm.wiaj.JarScript;
import com.hbm.wiaj.WorldInAJar;
import com.hbm.wiaj.actions.ActionCreateActor;
import com.hbm.wiaj.actions.ActionOffsetBy;
import com.hbm.wiaj.actions.ActionRemoveActor;
import com.hbm.wiaj.actions.ActionRotateBy;
import com.hbm.wiaj.actions.ActionSetBlock;
import com.hbm.wiaj.actions.ActionSetZoom;
import com.hbm.wiaj.actions.ActionWait;
import com.hbm.wiaj.actors.ActorFancyPanel;
import com.hbm.wiaj.actors.ActorFancyPanel.Orientation;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.util.ForgeDirection;

public class CanneryHadron extends CanneryBase {

	@Override
	public ItemStack getIcon() {
		return new ItemStack(ModBlocks.hadron_core);
	}

	@Override
	public String getName() {
		return "cannery.hadron";
	}
	
    @Override
	public CanneryBase[] seeAlso() {
		return new CanneryBase[] {
            new CannerySchottky()
		};
	}

	@Override
	public JarScript createScript() {
		WorldInAJar world = new WorldInAJar(25, 5, 25);
		JarScript script = new JarScript(world);
		
		

		// FIRST SCENE: Show and explain the core component
		JarScene scene0 = new JarScene(script);
		scene0.add(new ActionSetZoom(4, 0));
		
		scene0.add(new ActionSetBlock(12, 2, 12, ModBlocks.hadron_core, ForgeDirection.NORTH.ordinal()));
		
		scene0.add(new ActionCreateActor(1, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 0, -20, new Object[][] {{I18nUtil.resolveKey("cannery.hadron.0")}}, 200)
			.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));

		scene0.add(new ActionWait(100));
		scene0.add(new ActionRemoveActor(1));
		scene0.add(new ActionWait(5));

		scene0.add(new ActionCreateActor(2, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 0, -20, new Object[][] {{I18nUtil.resolveKey("cannery.hadron.1")}}, 200)
			.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));

		scene0.add(new ActionWait(100));
		scene0.add(new ActionRemoveActor(2));
		scene0.add(new ActionWait(10));

		scene0.add(new ActionCreateActor(3, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, -14, 4, new Object[][] {{I18nUtil.resolveKey("cannery.hadron.2")}}, 200)
			.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));

		scene0.add(new ActionWait(80));
		scene0.add(new ActionRemoveActor(3));
		scene0.add(new ActionWait(5));

		scene0.add(new ActionRotateBy(-90, 0, 10));

		scene0.add(new ActionCreateActor(4, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 14, 4, new Object[][] {{I18nUtil.resolveKey("cannery.hadron.3")}}, 200)
			.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));

		scene0.add(new ActionWait(80));
		scene0.add(new ActionRemoveActor(4));
		scene0.add(new ActionWait(5));

		scene0.add(new ActionRotateBy(90, 0, 10));



		// SECOND SCENE: Begin building a coil around the core component
		JarScene scene1 = new JarScene(script);

		scene1.add(new ActionSetZoom(4, 0));
		scene1.add(new ActionSetZoom(-2, 10));

		for(int i = 0; i < 8; i++) {
			double r = i * Math.PI / 4;
			scene1.add(new ActionSetBlock(12 + (int)(Math.cos(r) * 1.5F), 2 + (int)(Math.sin(r) * 1.5F), 12, ModBlocks.hadron_coil_alloy));
			scene1.add(new ActionWait(2));
		}

		scene1.add(new ActionWait(5));

		scene1.add(new ActionCreateActor(5, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 0, -25, new Object[][] {{I18nUtil.resolveKey("cannery.hadron.4")}}, 200)
			.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));

		scene1.add(new ActionWait(40));

		for(Block block : new Block[] {
			ModBlocks.hadron_coil_gold,
			ModBlocks.hadron_coil_neodymium,
			ModBlocks.hadron_coil_magtung,
			ModBlocks.hadron_coil_schrabidium,
			ModBlocks.hadron_coil_schrabidate,
			ModBlocks.hadron_coil_starmetal,
			ModBlocks.hadron_coil_chlorophyte,
			ModBlocks.hadron_coil_mese
		}) {
			for(int i = 0; i < 8; i++) {
				double r = i * Math.PI / 4;
				scene1.add(new ActionSetBlock(12 + (int)(Math.cos(r) * 1.5F), 2 + (int)(Math.sin(r) * 1.5F), 12, block));
				scene1.add(new ActionWait(1));
			}

			scene1.add(new ActionWait(4));
		}
		
		scene1.add(new ActionWait(20));
		scene1.add(new ActionRemoveActor(5));
		scene1.add(new ActionWait(5));

		for(int i = 0; i < 12; i++) {
			double r = i * Math.PI / 6;
			scene1.add(new ActionSetBlock(12 + (int)(Math.cos(r) * 2.75F), 2 + (int)(Math.sin(r) * 2.75F), 12, ModBlocks.hadron_plating));
			scene1.add(new ActionWait(2));
		}

		scene1.add(new ActionCreateActor(6, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 0, -40, new Object[][] {{I18nUtil.resolveKey("cannery.hadron.5")}}, 200)
			.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));
		
		scene1.add(new ActionWait(60));
		scene1.add(new ActionRemoveActor(6));
		scene1.add(new ActionWait(5));



		// THIRD SCENE: Add the Access Terminal and Power Plug
		JarScene scene2 = new JarScene(script);
		scene2.add(new ActionSetZoom(2, 0));

		scene2.add(new ActionWait(5));

		for(int i = 7; i >= 0; i--) {
			double r = i * Math.PI / 4;
			scene2.add(new ActionSetBlock(12 + (int)(Math.cos(r) * 1.5F), 2 + (int)(Math.sin(r) * 1.5F), 12, ModBlocks.hadron_coil_neodymium));
			scene2.add(new ActionWait(1));
		}

		scene2.add(new ActionWait(20));

		scene2.add(new ActionSetBlock(12 - 2, 2, 12, Blocks.air));
		scene2.add(new ActionWait(15));

		scene2.add(new ActionSetBlock(12 - 2, 2, 12, ModBlocks.hadron_access, ForgeDirection.EAST.ordinal()));
		scene2.add(new ActionWait(10));

		scene2.add(new ActionCreateActor(7, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 36, 18, new Object[][] {{I18nUtil.resolveKey("cannery.hadron.6")}}, 100)
			.setColors(colorCopper).setOrientation(Orientation.LEFT)));
		
		scene2.add(new ActionWait(80));
		scene2.add(new ActionRemoveActor(7));
		scene2.add(new ActionWait(20));

		scene2.add(new ActionSetBlock(12, 2 + 2, 12, Blocks.air));
		scene2.add(new ActionWait(15));

		scene2.add(new ActionSetBlock(12, 2 + 2, 12, ModBlocks.hadron_power));
		scene2.add(new ActionWait(10));

		scene2.add(new ActionCreateActor(8, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 0, -45, new Object[][] {{I18nUtil.resolveKey("cannery.hadron.7")}}, 200)
			.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));
		
		scene2.add(new ActionWait(80));
		scene2.add(new ActionRemoveActor(8));
		scene2.add(new ActionWait(20));



		// FOURTH SCENE: Add some coil segments and power them
		JarScene scene3 = new JarScene(script);
		scene3.add(new ActionSetZoom(2, 0));

		for(int i = 0; i < 8; i++) {
			double r = i * Math.PI / 4;
			scene3.add(new ActionSetBlock(12 + (int)(Math.cos(r) * 1.5F), 2 + (int)(Math.sin(r) * 1.5F), 11, ModBlocks.hadron_coil_neodymium));
			scene3.add(new ActionWait(2));
		}

		for(int i = 0; i < 12; i++) {
			double r = i * Math.PI / 6;
			scene3.add(new ActionSetBlock(12 + (int)(Math.cos(r) * 2.75F), 2 + (int)(Math.sin(r) * 2.75F), 11, ModBlocks.hadron_plating));
			scene3.add(new ActionWait(2));
		}

		scene3.add(new ActionWait(5));

		for(int i = 0; i < 8; i++) {
			double r = i * Math.PI / 4;
			scene3.add(new ActionSetBlock(12 + (int)(Math.cos(r) * 1.5F), 2 + (int)(Math.sin(r) * 1.5F), 10, ModBlocks.hadron_coil_neodymium));
			scene3.add(new ActionWait(2));
		}

		for(int i = 0; i < 12; i++) {
			double r = i * Math.PI / 6;
			scene3.add(new ActionSetBlock(12 + (int)(Math.cos(r) * 2.75F), 2 + (int)(Math.sin(r) * 2.75F), 10, ModBlocks.hadron_plating));
			scene3.add(new ActionWait(2));
		}

		scene3.add(new ActionWait(20));

		scene3.add(new ActionSetBlock(12, 2 + 2, 10, Blocks.air));
		scene3.add(new ActionWait(15));

		scene3.add(new ActionSetBlock(12, 2 + 2, 10, ModBlocks.hadron_power));
		scene3.add(new ActionWait(10));

		scene3.add(new ActionCreateActor(9, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, -28, -28, new Object[][] {{I18nUtil.resolveKey("cannery.hadron.8")}}, 200)
			.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));

		scene3.add(new ActionWait(40));

		scene3.add(new ActionCreateActor(10, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, -12, 28, new Object[][] {{I18nUtil.resolveKey("cannery.hadron.math.0")}, {I18nUtil.resolveKey("cannery.hadron.math.1")}}, 0)
			.setColors(colorCopper).setOrientation(Orientation.LEFT)));
		
		scene3.add(new ActionWait(40));
		scene3.add(new ActionRemoveActor(10));
		scene3.add(new ActionWait(5));

		for(int i = 0; i < 8; i++) {
			double r = i * Math.PI / 4;
			scene3.add(new ActionSetBlock(12 + (int)(Math.cos(r) * 1.5F), 2 + (int)(Math.sin(r) * 1.5F), 10, ModBlocks.hadron_coil_starmetal));
			scene3.add(new ActionWait(1));
		}

		scene3.add(new ActionCreateActor(13, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, -12, 28, new Object[][] {{I18nUtil.resolveKey("cannery.hadron.math.2")}, {I18nUtil.resolveKey("cannery.hadron.math.3")}}, 0)
			.setColors(colorCopper).setOrientation(Orientation.LEFT)));

		
		scene3.add(new ActionWait(80));
		scene3.add(new ActionRemoveActor(9));
		scene3.add(new ActionWait(10));

		scene3.add(new ActionCreateActor(11, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 0, -45, new Object[][] {{I18nUtil.resolveKey("cannery.hadron.9")}}, 200)
			.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));
		
		scene3.add(new ActionWait(80));
		scene3.add(new ActionRemoveActor(11));
		scene3.add(new ActionWait(5));

		scene3.add(new ActionCreateActor(12, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 0, -45, new Object[][] {{I18nUtil.resolveKey("cannery.hadron.10")}}, 200)
			.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));
		
		scene3.add(new ActionWait(80));
		scene3.add(new ActionRemoveActor(12));
		scene3.add(new ActionWait(10));

		scene3.add(new ActionRemoveActor(13));

		for(int i = 7; i >= 0; i--) {
			double r = i * Math.PI / 4;
			scene3.add(new ActionSetBlock(12 + (int)(Math.cos(r) * 1.5F), 2 + (int)(Math.sin(r) * 1.5F), 10, ModBlocks.hadron_coil_neodymium));
			scene3.add(new ActionWait(1));
		}



		// FIFTH SCENE: Add a bend to the coil
		JarScene scene4 = new JarScene(script);
		scene4.add(new ActionSetZoom(2, 0));

		scene4.add(new ActionOffsetBy(0, 0, 4, 10));
		scene4.add(new ActionRotateBy(90, 0, 10));


		// BEGIN CORNER SEGMENT
		for(int z = 9; z >= 7; z--) {
			for(int x = 11; x <= 14; x++) {
				if(z == 7 && x == 11) continue;
				scene4.add(new ActionSetBlock(x, 0, z, ModBlocks.hadron_plating));
				scene4.add(new ActionWait(2));
			}
		}

		for(int z = 9; z >= 6; z--) {
			for(int x = 10; x <= 14; x++) {
				if(z == 6 && x <= 11) continue;
				if(z <= 7 && x == 10) continue;
				scene4.add(new ActionSetBlock(x, 1, z, z == 6 || x == 10 || (z == 7 && x == 11) ? ModBlocks.hadron_plating : ModBlocks.hadron_coil_neodymium));
				scene4.add(new ActionWait(2));
			}
		}

		for(int z = 9; z >= 6; z--) {
			for(int x = 10; x <= 14; x++) {
				if(z == 6 && x <= 11) continue;
				if(z <= 7 && x == 10) continue;
				if(z == 9 && x == 12) continue;
				if(z == 8 && x == 12) continue;
				if(z == 8 && x == 13) continue;
				if(z == 8 && x == 14) continue;
				scene4.add(new ActionSetBlock(x, 2, z, z == 6 || x == 10 || (z == 7 && x == 11) ? ModBlocks.hadron_plating : ModBlocks.hadron_coil_neodymium));
				scene4.add(new ActionWait(2));
			}
		}
		// END CORNER SEGMENT


		scene4.add(new ActionWait(5));

		scene4.add(new ActionCreateActor(14, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, -8, -35, new Object[][] {{I18nUtil.resolveKey("cannery.hadron.11")}}, 200)
			.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));
		
		scene4.add(new ActionWait(80));
		scene4.add(new ActionRemoveActor(14));
		scene4.add(new ActionWait(5));

		scene4.add(new ActionCreateActor(15, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 24, -16, new Object[][] {{I18nUtil.resolveKey("cannery.hadron.12")}}, 200)
			.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));
		
		scene4.add(new ActionWait(80));
		scene4.add(new ActionRemoveActor(15));
		scene4.add(new ActionWait(10));

		for(int z = 9; z >= 6; z--) {
			for(int x = 10; x <= 14; x++) {
				if(z == 6 && x <= 11) continue;
				if(z <= 7 && x == 10) continue;
				scene4.add(new ActionSetBlock(x, 3, z, z == 6 || x == 10 || (z == 7 && x == 11) ? ModBlocks.hadron_plating : ModBlocks.hadron_coil_neodymium));
				scene4.add(new ActionWait(2));
			}
		}

		for(int z = 9; z >= 7; z--) {
			for(int x = 11; x <= 14; x++) {
				if(z == 7 && x == 11) continue;
				scene4.add(new ActionSetBlock(x, 4, z, ModBlocks.hadron_plating));
				scene4.add(new ActionWait(2));
			}
		}

		scene4.add(new ActionWait(10));

		scene4.add(new ActionSetBlock(14, 2 + 2, 8, Blocks.air));
		scene4.add(new ActionWait(10));

		scene4.add(new ActionSetBlock(14, 2 + 2, 8, ModBlocks.hadron_power));
		scene4.add(new ActionWait(10));




		// SIXTH SCENE: Reach the Analysis Chamber
		JarScene scene5 = new JarScene(script);
		scene5.add(new ActionSetZoom(2, 0));

		for(int i = 0; i < 8; i++) {
			double r = i * Math.PI / 4;
			scene5.add(new ActionSetBlock(15, 2 + (int)(Math.sin(r) * 1.5F), 8 + (int)(Math.cos(r) * 1.5F), ModBlocks.hadron_coil_neodymium));
			scene5.add(new ActionWait(1));
		}

		for(int i = 0; i < 12; i++) {
			double r = i * Math.PI / 6;
			scene5.add(new ActionSetBlock(15, 2 + (int)(Math.sin(r) * 2.75F), 8 + (int)(Math.cos(r) * 2.75F), ModBlocks.hadron_plating));
			scene5.add(new ActionWait(1));
		}

		for(int i = 0; i < 8; i++) {
			double r = i * Math.PI / 4;
			scene5.add(new ActionSetBlock(16, 2 + (int)(Math.sin(r) * 1.5F), 8 + (int)(Math.cos(r) * 1.5F), ModBlocks.hadron_coil_neodymium));
			scene5.add(new ActionWait(1));
		}

		for(int i = 0; i < 12; i++) {
			double r = i * Math.PI / 6;
			scene5.add(new ActionSetBlock(16, 2 + (int)(Math.sin(r) * 2.75F), 8 + (int)(Math.cos(r) * 2.75F), i == 3 ? ModBlocks.hadron_power : ModBlocks.hadron_plating));
			scene5.add(new ActionWait(1));
		}


		// BEGIN CORNER SEGMENT
		for(int x = 17; x <= 19; x++) {
			for(int z = 10; z >= 7; z--) {
				if(z == 7 && x == 19) continue;
				scene5.add(new ActionSetBlock(x, 0, z, ModBlocks.hadron_plating));
				scene5.add(new ActionWait(1));
			}
		}

		for(int x = 17; x <= 20; x++) {
			for(int z = 10; z >= 6; z--) {
				if(z == 6 && x >= 19) continue;
				if(z <= 7 && x == 20) continue;
				scene5.add(new ActionSetBlock(x, 1, z, z == 6 || x == 20 || (z == 7 && x == 19) ? ModBlocks.hadron_plating : ModBlocks.hadron_coil_neodymium));
				scene5.add(new ActionWait(1));
			}
		}

		for(int x = 17; x <= 20; x++) {
			for(int z = 10; z >= 6; z--) {
				if(z == 6 && x >= 19) continue;
				if(z <= 7 && x == 20) continue;
				if(z == 9 && x == 18) continue;
				if(z == 8 && x == 18) continue;
				if(z == 8 && x == 17) continue;
				if(z == 10 && x == 18) continue;
				scene5.add(new ActionSetBlock(x, 2, z, z == 6 || x == 20 || (z == 7 && x == 19) ? ModBlocks.hadron_plating : ModBlocks.hadron_coil_neodymium));
				scene5.add(new ActionWait(1));
			}
		}

		for(int x = 17; x <= 20; x++) {
			for(int z = 10; z >= 6; z--) {
				if(z == 6 && x >= 19) continue;
				if(z <= 7 && x == 20) continue;
				scene5.add(new ActionSetBlock(x, 3, z, z == 6 || x == 20 || (z == 7 && x == 19) ? ModBlocks.hadron_plating : ModBlocks.hadron_coil_neodymium));
				scene5.add(new ActionWait(1));
			}
		}

		for(int x = 17; x <= 19; x++) {
			for(int z = 10; z >= 7; z--) {
				if(z == 7 && x == 19) continue;
				scene5.add(new ActionSetBlock(x, 4, z, ModBlocks.hadron_plating));
				scene5.add(new ActionWait(1));
			}
		}
		// END CORNER SEGMENT


		scene5.add(new ActionRotateBy(-90, 0, 5));
		scene5.add(new ActionOffsetBy(0, 0, -8, 10));
		scene5.add(new ActionRotateBy(-90, 0, 10));
		scene5.add(new ActionSetZoom(-1, 10));

		for(int z = 11; z <= 20; z++) {
			for(int i = 0; i < 8; i++) {
				double r = i * Math.PI / 4;
				scene5.add(new ActionSetBlock(18 + (int)(Math.cos(r) * 1.5F), 2 + (int)(Math.sin(r) * 1.5F), z, ModBlocks.hadron_coil_neodymium));
				if(z == 11 || z == 20) scene5.add(new ActionWait(1));
			}
	
			for(int i = 0; i < 12; i++) {
				double r = i * Math.PI / 6;
				scene5.add(new ActionSetBlock(18 + (int)(Math.cos(r) * 2.75F), 2 + (int)(Math.sin(r) * 2.75F), z, i == 3 && z % 3 == 0 ? ModBlocks.hadron_power : ModBlocks.hadron_plating));
				if(z == 11 || z == 20) scene5.add(new ActionWait(1));
			}

			scene5.add(new ActionWait(z < 13 || z > 18 ? 2 : 1));
		}

		// SEVENTH SCENE: Actually build the Analysis Chamber
		JarScene scene6 = new JarScene(script);
		scene6.add(new ActionSetZoom(1, 0));

		scene6.add(new ActionSetZoom(1, 10));

		for(int i = 0; i < 8; i++) {
			double r = i * Math.PI / 4;
			scene6.add(new ActionSetBlock(12 + (int)(Math.cos(r) * 1.5F), 2 + (int)(Math.sin(r) * 1.5F), 13, ModBlocks.hadron_coil_neodymium));
			scene6.add(new ActionWait(1));
		}

		for(int i = 0; i < 12; i++) {
			double r = i * Math.PI / 6;
			scene6.add(new ActionSetBlock(12 + (int)(Math.cos(r) * 2.75F), 2 + (int)(Math.sin(r) * 2.75F), 13, ModBlocks.hadron_plating));
			scene6.add(new ActionWait(1));
		}
		
		for(int z = 14; z <= 16; z++) {
			for(int i = 0; i < 12; i++) {
				double r = i * Math.PI / 6;
				scene6.add(new ActionSetBlock(12 + (int)(Math.cos(r) * 2.75F), 2 + (int)(Math.sin(r) * 2.75F), z, i == 6 ? ModBlocks.hadron_analysis_glass : ModBlocks.hadron_analysis));
				scene6.add(new ActionWait(2));
			}
		}

		scene6.add(new ActionWait(10));

		scene6.add(new ActionCreateActor(16, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 0, 0, new Object[][] {{I18nUtil.resolveKey("cannery.hadron.13")}}, 200)
			.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));
		
		scene6.add(new ActionWait(100));
		scene6.add(new ActionRemoveActor(16));
		scene6.add(new ActionWait(10));

		for(int z = 17; z <= 20; z++) {
			for(int i = 0; i < 8; i++) {
				double r = i * Math.PI / 4;
				scene6.add(new ActionSetBlock(12 + (int)(Math.cos(r) * 1.5F), 2 + (int)(Math.sin(r) * 1.5F), z, ModBlocks.hadron_coil_neodymium));
				if(z == 17 || z == 20) scene6.add(new ActionWait(1));
			}
	
			for(int i = 0; i < 12; i++) {
				double r = i * Math.PI / 6;
				scene6.add(new ActionSetBlock(12 + (int)(Math.cos(r) * 2.75F), 2 + (int)(Math.sin(r) * 2.75F), z, i == 3 && (z == 18 || z == 20) ? ModBlocks.hadron_power : ModBlocks.hadron_plating));
				if(z == 17 || z == 20) scene6.add(new ActionWait(1));
			}

			scene6.add(new ActionWait(1));
		}

		scene6.add(new ActionSetZoom(-1, 10));

		// BEGIN CORNER SEGMENT
		for(int x = 17; x <= 19; x++) {
			for(int z = 23; z >= 21; z--) {
				if(z == 23 && x == 19) continue;
				scene6.add(new ActionSetBlock(x, 0, z, ModBlocks.hadron_plating));
				scene6.add(new ActionWait(1));
			}
		}

		for(int x = 17; x <= 20; x++) {
			for(int z = 24; z >= 21; z--) {
				if(z == 24 && x >= 19) continue;
				if(z >= 23 && x == 20) continue;
				scene6.add(new ActionSetBlock(x, 1, z, z == 24 || x == 20 || (z == 23 && x == 19) ? ModBlocks.hadron_plating : ModBlocks.hadron_coil_neodymium));
				scene6.add(new ActionWait(1));
			}
		}

		for(int x = 17; x <= 20; x++) {
			for(int z = 24; z >= 21; z--) {
				if(z == 24 && x >= 19) continue;
				if(z >= 23 && x == 20) continue;
				if(z == 21 && x == 18) continue;
				if(z == 22 && x == 18) continue;
				if(z == 22 && x == 17) continue;
				if(z == 20 && x == 18) continue;
				scene6.add(new ActionSetBlock(x, 2, z, z == 24 || x == 20 || (z == 23 && x == 19) ? ModBlocks.hadron_plating : ModBlocks.hadron_coil_neodymium));
				scene6.add(new ActionWait(1));
			}
		}

		for(int x = 17; x <= 20; x++) {
			for(int z = 24; z >= 21; z--) {
				if(z == 24 && x >= 19) continue;
				if(z >= 23 && x == 20) continue;
				scene6.add(new ActionSetBlock(x, 3, z, z == 24 || x == 20 || (z == 23 && x == 19) ? ModBlocks.hadron_plating : ModBlocks.hadron_coil_neodymium));
				scene6.add(new ActionWait(1));
			}
		}

		for(int x = 17; x <= 19; x++) {
			for(int z = 23; z >= 21; z--) {
				if(z == 23 && x == 19) continue;
				scene6.add(new ActionSetBlock(x, 4, z, ModBlocks.hadron_plating));
				scene6.add(new ActionWait(1));
			}
		}
		// END CORNER SEGMENT

		for(int i = 0; i < 8; i++) {
			double r = i * Math.PI / 4;
			scene6.add(new ActionSetBlock(16, 2 + (int)(Math.sin(r) * 1.5F), 22 + (int)(Math.cos(r) * 1.5F), ModBlocks.hadron_coil_neodymium));
			scene6.add(new ActionWait(1));
		}

		for(int i = 0; i < 12; i++) {
			double r = i * Math.PI / 6;
			scene6.add(new ActionSetBlock(16, 2 + (int)(Math.sin(r) * 2.75F), 22 + (int)(Math.cos(r) * 2.75F), i == 3 ? ModBlocks.hadron_power : ModBlocks.hadron_plating));
			scene6.add(new ActionWait(1));
		}

		for(int i = 0; i < 8; i++) {
			double r = i * Math.PI / 4;
			scene6.add(new ActionSetBlock(15, 2 + (int)(Math.sin(r) * 1.5F), 22 + (int)(Math.cos(r) * 1.5F), ModBlocks.hadron_coil_neodymium));
			scene6.add(new ActionWait(1));
		}

		for(int i = 0; i < 12; i++) {
			double r = i * Math.PI / 6;
			scene6.add(new ActionSetBlock(15, 2 + (int)(Math.sin(r) * 2.75F), 22 + (int)(Math.cos(r) * 2.75F), ModBlocks.hadron_plating));
			scene6.add(new ActionWait(1));
		}

		for(int i = 0; i < 8; i++) {
			double r = i * Math.PI / 4;
			scene6.add(new ActionSetBlock(14, 2 + (int)(Math.sin(r) * 1.5F), 22 + (int)(Math.cos(r) * 1.5F), ModBlocks.hadron_coil_neodymium));
			scene6.add(new ActionWait(1));
		}

		for(int i = 0; i < 12; i++) {
			double r = i * Math.PI / 6;
			scene6.add(new ActionSetBlock(14, 2 + (int)(Math.sin(r) * 2.75F), 22 + (int)(Math.cos(r) * 2.75F), i == 3 ? ModBlocks.hadron_power : ModBlocks.hadron_plating));
			scene6.add(new ActionWait(1));
		}

		// BEGIN CORNER SEGMENT
		for(int x = 11; x <= 13; x++) {
			for(int z = 23; z >= 21; z--) {
				if(z == 23 && x == 11) continue;
				scene6.add(new ActionSetBlock(x, 0, z, ModBlocks.hadron_plating));
				scene6.add(new ActionWait(1));
			}
		}

		for(int x = 10; x <= 13; x++) {
			for(int z = 24; z >= 21; z--) {
				if(z == 24 && x <= 11) continue;
				if(z >= 23 && x == 10) continue;
				scene6.add(new ActionSetBlock(x, 1, z, z == 24 || x == 10 || (z == 23 && x == 11) ? ModBlocks.hadron_plating : ModBlocks.hadron_coil_neodymium));
				scene6.add(new ActionWait(1));
			}
		}

		for(int x = 10; x <= 13; x++) {
			for(int z = 24; z >= 21; z--) {
				if(z == 24 && x <= 11) continue;
				if(z >= 23 && x == 10) continue;
				if(z == 21 && x == 12) continue;
				if(z == 22 && x == 12) continue;
				if(z == 22 && x == 13) continue;
				scene6.add(new ActionSetBlock(x, 2, z, z == 24 || x == 10 || (z == 23 && x == 11) ? ModBlocks.hadron_plating : ModBlocks.hadron_coil_neodymium));
				scene6.add(new ActionWait(1));
			}
		}

		for(int x = 10; x <= 13; x++) {
			for(int z = 24; z >= 21; z--) {
				if(z == 24 && x <= 11) continue;
				if(z >= 23 && x == 10) continue;
				scene6.add(new ActionSetBlock(x, 3, z, z == 24 || x == 10 || (z == 23 && x == 11) ? ModBlocks.hadron_plating : ModBlocks.hadron_coil_neodymium));
				scene6.add(new ActionWait(1));
			}
		}
		
		for(int x = 11; x <= 13; x++) {
			for(int z = 23; z >= 21; z--) {
				if(z == 23 && x == 11) continue;
				scene6.add(new ActionSetBlock(x, 4, z, ModBlocks.hadron_plating));
				scene6.add(new ActionWait(1));
			}
		}
		// END CORNER SEGMENT

		scene6.add(new ActionWait(10));

		scene6.add(new ActionCreateActor(17, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 0, -50, new Object[][] {{I18nUtil.resolveKey("cannery.hadron.14")}}, 200)
			.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));
		
		scene6.add(new ActionWait(100));
		scene6.add(new ActionRemoveActor(17));
		scene6.add(new ActionWait(10));




		// ADDENDUM SCENE: Schottky diodes



		// ADDENDUM SCENE: Cooling


		script
			.addScene(scene0)
			.addScene(scene1)
			.addScene(scene2)
			.addScene(scene3)
			.addScene(scene4)
			.addScene(scene5)
			.addScene(scene6);

		return script;
	}
	
}

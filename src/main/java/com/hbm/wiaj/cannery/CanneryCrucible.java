package com.hbm.wiaj.cannery;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemScraps;
import com.hbm.render.tileentity.RenderCrucible;
import com.hbm.render.tileentity.RenderFoundry;
import com.hbm.tileentity.machine.TileEntityCrucible;
import com.hbm.tileentity.machine.TileEntityFoundryBasin;
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
import com.hbm.wiaj.actions.ActionUpdateActor;
import com.hbm.wiaj.actions.ActionWait;
import com.hbm.wiaj.actors.ActorFancyPanel;
import com.hbm.wiaj.actors.ActorTileEntity;
import com.hbm.wiaj.actors.ActorFancyPanel.Orientation;
import com.hbm.wiaj.cannery.CanneryFirebox.ActorFirebox;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class CanneryCrucible extends CanneryBase {

	@Override
	public ItemStack getIcon() {
		return new ItemStack(ModBlocks.machine_crucible);
	}

	@Override
	public String getName() {
		return "cannery.crucible";
	}
	
	public CanneryBase[] seeAlso() {
		return new CanneryBase[] {
				new CanneryFoundryChannel()
		};
	}

	@Override
	public JarScript createScript() {
		WorldInAJar world = new WorldInAJar(5, 4, 5);
		JarScript script = new JarScript(world);
		
		JarScene scene0 = new JarScene(script);
		scene0.add(new ActionSetZoom(3, 0));
		
		for(int x = 0; x < 5 ; x++) {
			for(int z = 0; z < 5; z++) {
				scene0.add(new ActionSetBlock(x, 0, z, Blocks.brick_block));
			}
		}
		scene0.add(new ActionWait(5));
		for(int x = 1; x < 4 ; x++) {
			for(int z = 1; z < 4; z++) {
				scene0.add(new ActionSetBlock(x, 1, z, Blocks.brick_block));
			}
		}
		scene0.add(new ActionWait(5));

		scene0.add(new ActionSetTile(2, 2, 2, new TileEntityFauxCrucible() {{ meta = 14; }}));
		scene0.add(new ActionCreateActor(1, new ActorTileEntity(new RenderCrucible(), new NBTTagCompound() {{ setInteger("x", 2); setInteger("y", 2); setInteger("z", 2); }})));

		scene0.add(new ActionWait(10));
		scene0.add(new ActionCreateActor(0, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 0, -30, new Object[][] {{I18nUtil.resolveKey("cannery.crucible.0")}}, 200)
				.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));
		
		scene0.add(new ActionWait(60));
		scene0.add(new ActionRemoveActor(0));
		scene0.add(new ActionCreateActor(0, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 0, -30, new Object[][] {{I18nUtil.resolveKey("cannery.crucible.1")}}, 200)
				.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));
		
		scene0.add(new ActionWait(60));
		scene0.add(new ActionRemoveActor(0));
		scene0.add(new ActionWait(10));

		for(int x = 1; x < 4 ; x++) for(int z = 1; z < 4; z++) scene0.add(new ActionSetBlock(x, 1, z, Blocks.air)); 
		
		NBTTagCompound firebox = new NBTTagCompound(); firebox.setDouble("x", 2); firebox.setDouble("y", 1); firebox.setDouble("z", 2); firebox.setInteger("rotation", 5);
		scene0.add(new ActionCreateActor(2, new ActorTileEntity(new ActorFirebox(), firebox)));
		
		scene0.add(new ActionUpdateActor(2, "open", true));
		scene0.add(new ActionWait(30));
		scene0.add(new ActionUpdateActor(2, "isOn", true));

		scene0.add(new ActionCreateActor(0, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, -50, 25, new Object[][] {{new ItemStack(Items.coal)}}, 0)
				.setColors(colorCopper).setOrientation(Orientation.RIGHT)));
		
		scene0.add(new ActionWait(20));
		scene0.add(new ActionRemoveActor(0));
		scene0.add(new ActionWait(10));
		scene0.add(new ActionUpdateActor(2, "open", false));
		scene0.add(new ActionWait(30));
		
		JarScene scene1 = new JarScene(script);
		
		scene1.add(new ActionCreateActor(0, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 0, -30, new Object[][] {{I18nUtil.resolveKey("cannery.crucible.2")}}, 200)
				.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));
		scene1.add(new ActionWait(60));

		scene1.add(new ActionRemoveActor(0));
		scene1.add(new ActionRotateBy(45, -60, 20));
		scene1.add(new ActionWait(10));
		
		scene1.add(new ActionCreateActor(0, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 0, 0, new Object[][] {{I18nUtil.resolveKey("cannery.crucible.3")}}, 200)
				.setColors(colorCopper).setOrientation(Orientation.CENTER)));
		scene1.add(new ActionWait(40));

		scene1.add(new ActionRemoveActor(0));
		scene1.add(new ActionCreateActor(0, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, -30, 0, new Object[][] {{I18nUtil.resolveKey("cannery.crucible.4")}}, 200)
				.setColors(colorCopper).setOrientation(Orientation.LEFT)));
		scene1.add(new ActionWait(60));

		scene1.add(new ActionRemoveActor(0));
		scene1.add(new ActionCreateActor(0, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, -30, 0, new Object[][] {{I18nUtil.resolveKey("cannery.crucible.5")}}, 200)
				.setColors(colorCopper).setOrientation(Orientation.LEFT)));
		scene1.add(new ActionWait(60));

		scene1.add(new ActionRemoveActor(0));
		scene1.add(new ActionCreateActor(0, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, -30, 0, new Object[][] {{I18nUtil.resolveKey("cannery.crucible.6")}}, 200)
				.setColors(colorCopper).setOrientation(Orientation.LEFT)));
		scene1.add(new ActionWait(60));

		scene1.add(new ActionRemoveActor(0));
		scene1.add(new ActionWait(20));
		scene1.add(new ActionCreateActor(0, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 30, 0, new Object[][] {{I18nUtil.resolveKey("cannery.crucible.7")}}, 200)
				.setColors(colorCopper).setOrientation(Orientation.RIGHT)));
		scene1.add(new ActionWait(60));

		scene1.add(new ActionRemoveActor(0));
		scene1.add(new ActionCreateActor(0, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 30, 0, new Object[][] {{I18nUtil.resolveKey("cannery.crucible.8")}}, 200)
				.setColors(colorCopper).setOrientation(Orientation.RIGHT)));
		scene1.add(new ActionWait(60));

		scene1.add(new ActionRemoveActor(0));
		scene1.add(new ActionCreateActor(0, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 30, 0, new Object[][] {{I18nUtil.resolveKey("cannery.crucible.9")}}, 200)
				.setColors(colorCopper).setOrientation(Orientation.RIGHT)));
		scene1.add(new ActionWait(60));
		
		scene1.add(new ActionRemoveActor(0));
		scene1.add(new ActionRotateBy(-45, 60, 20));
		scene1.add(new ActionWait(10));
		
		JarScene scene2 = new JarScene(script);
		scene2.add(new ActionCreateActor(0, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 30, 0, new Object[][] {{I18nUtil.resolveKey("cannery.crucible.10")}}, 200)
				.setColors(colorCopper).setOrientation(Orientation.RIGHT)));
		scene2.add(new ActionWait(60));
		
		scene2.add(new ActionRemoveActor(0));
		scene2.add(new ActionWait(10));
		scene2.add(new ActionCreateActor(0, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 0, -40, new Object[][] {{new ItemStack(Items.gold_ingot, 11)}}, 0)
				.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));
		scene2.add(new ActionWait(20));
		
		scene2.add(new ActionRemoveActor(0));
		scene2.add(new ActionSetTile(2, 2, 2, new TileEntityFauxCrucible() {{ meta = 14; recipeStack.add(new MaterialStack(Mats.MAT_GOLD, MaterialShapes.BLOCK.q(16))); }}));
		scene2.add(new ActionWait(10));
		
		scene2.add(new ActionSetTile(0, 1, 2, new TileEntityFoundryBasin()));
		scene2.add(new ActionCreateActor(3, new ActorTileEntity(new RenderFoundry(), new NBTTagCompound() {{ setInteger("x", 0); setInteger("y", 1); setInteger("z", 2); }})));
		scene2.add(new ActionSetBlock(0, 1, 2, ModBlocks.foundry_basin));
		
		scene2.add(new ActionCreateActor(0, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 42, 25, new Object[][] {{new ItemStack(ModItems.mold, 1, 12)}}, 0)
				.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));
		scene2.add(new ActionWait(20));
		scene2.add(new ActionRemoveActor(0));
		scene2.add(new ActionSetTile(0, 1, 2, new TileEntityFoundryBasin() {{ slots[0] = new ItemStack(ModItems.mold, 0, 12); }}));
		
		scene2.add(new ActionWait(20));
		
		for(int i = 0; i < 60; i++) {
			final int j = i;
			scene2.add(new ActionSetTile(2, 2, 2, new TileEntityFauxCrucible() {{ meta = 14; recipeStack.add(new MaterialStack(Mats.MAT_GOLD, MaterialShapes.BLOCK.q((60 - j), 60) * 16)); }}));
			scene2.add(new ActionSetTile(0, 1, 2, new TileEntityFoundryBasin() {{ slots[0] = new ItemStack(ModItems.mold, 0, 12); type = Mats.MAT_GOLD; amount = MaterialShapes.BLOCK.q(j + 1, 60); }}));
			scene2.add(new ActionWait(1));
		}
		
		scene2.add(new ActionWait(40));
		scene2.add(new ActionSetTile(0, 1, 2, new TileEntityFoundryBasin() {{ slots[0] = new ItemStack(ModItems.mold, 0, 12); slots[1] = new ItemStack(Blocks.gold_block); }}));
		scene2.add(new ActionWait(20));
		scene2.add(new ActionSetTile(0, 1, 2, new TileEntityFoundryBasin() {{ slots[0] = new ItemStack(ModItems.mold, 0, 12); }}));
		scene2.add(new ActionCreateActor(0, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 42, 25, new Object[][] {{new ItemStack(Blocks.gold_block)}}, 0)
				.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));
		scene2.add(new ActionWait(20));
		scene2.add(new ActionRemoveActor(0));
		
		scene2.add(new ActionCreateActor(0, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 0, -30, new Object[][] {{I18nUtil.resolveKey("cannery.crucible.11")}}, 200)
				.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));
		scene2.add(new ActionWait(60));
		scene2.add(new ActionRemoveActor(0));
		scene2.add(new ActionCreateActor(0, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 0, -30, new Object[][] {{new ItemStack(Items.iron_shovel), " -> ", ItemScraps.create(new MaterialStack(Mats.MAT_GOLD, 1))}}, 200)
				.setColors(colorCopper).setOrientation(Orientation.BOTTOM)));
		scene2.add(new ActionSetTile(2, 2, 2, new TileEntityFauxCrucible() {{ meta = 14; }}));
		scene2.add(new ActionWait(20));
		scene2.add(new ActionRemoveActor(0));
		
		script.addScene(scene0).addScene(scene1).addScene(scene2);
		
		return script;
	}
	
	public static class TileEntityFauxCrucible extends TileEntityCrucible {
		
		public int meta = 0;
		
		@Override
		public int getBlockMetadata() {
			return meta;
		}
	}

}

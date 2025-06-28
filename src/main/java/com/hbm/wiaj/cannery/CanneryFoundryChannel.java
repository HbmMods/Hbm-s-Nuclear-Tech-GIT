package com.hbm.wiaj.cannery;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemScraps;
import com.hbm.render.tileentity.RenderFoundry;
import com.hbm.tileentity.machine.TileEntityFoundryBasin;
import com.hbm.tileentity.machine.TileEntityFoundryChannel;
import com.hbm.tileentity.machine.TileEntityFoundryMold;
import com.hbm.tileentity.machine.TileEntityFoundryOutlet;
import com.hbm.tileentity.machine.TileEntityFoundryTank;
import com.hbm.util.i18n.I18nUtil;
import com.hbm.wiaj.JarScene;
import com.hbm.wiaj.JarScript;
import com.hbm.wiaj.WorldInAJar;
import com.hbm.wiaj.actions.ActionCreateActor;
import com.hbm.wiaj.actions.ActionRemoveActor;
import com.hbm.wiaj.actions.ActionSetBlock;
import com.hbm.wiaj.actions.ActionSetTile;
import com.hbm.wiaj.actions.ActionSetZoom;
import com.hbm.wiaj.actions.ActionWait;
import com.hbm.wiaj.actors.ActorFancyPanel;
import com.hbm.wiaj.actors.ActorTileEntity;
import com.hbm.wiaj.actors.ActorFancyPanel.Orientation;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class CanneryFoundryChannel extends CanneryBase {

	@Override
	public ItemStack getIcon() {
		return new ItemStack(ModBlocks.foundry_channel);
	}

	@Override
	public String getName() {
		return "cannery.foundryChannel";
	}
	
	public CanneryBase[] seeAlso() {
		return new CanneryBase[] {
				new CanneryCrucible()
		};
	}

	@Override
	public JarScript createScript() {
		WorldInAJar world = new WorldInAJar(5, 4, 4);
		JarScript script = new JarScript(world);
		
		JarScene scene0 = new JarScene(script);
		
		/// SETUP ///
		scene0.add(new ActionSetZoom(3, 0));
		
		for(int x = world.sizeX - 1; x >= 0 ; x--) {
			for(int z = 0; z < world.sizeZ; z++) {
				scene0.add(new ActionSetBlock(x, 0, z, Blocks.brick_block));
			}
		}
		
		scene0.add(new ActionWait(5));
		scene0.add(new ActionSetBlock(1, 1, 2, Blocks.brick_block));
		scene0.add(new ActionSetBlock(2, 1, 2, Blocks.brick_block));
		scene0.add(new ActionSetBlock(3, 1, 2, Blocks.brick_block));
		scene0.add(new ActionSetBlock(3, 1, 3, Blocks.brick_block));

		scene0.add(new ActionSetTile(3, 1, 1, new TileEntityFoundryBasin() {{ slots[0] = new ItemStack(ModItems.mold, 0, 12); }}));
		scene0.add(new ActionCreateActor(1, new ActorTileEntity(new RenderFoundry(), new NBTTagCompound() {{ setInteger("x", 3); setInteger("y", 1); setInteger("z", 1); }})));
		scene0.add(new ActionSetBlock(3, 1, 1, ModBlocks.foundry_basin));
		scene0.add(new ActionSetTile(2, 1, 1, new TileEntityFoundryMold() {{ slots[0] = new ItemStack(ModItems.mold, 0, 2); }}));
		scene0.add(new ActionCreateActor(2, new ActorTileEntity(new RenderFoundry(), new NBTTagCompound() {{ setInteger("x", 2); setInteger("y", 1); setInteger("z", 1); }})));
		scene0.add(new ActionSetBlock(2, 1, 1, ModBlocks.foundry_mold));
		
		scene0.add(new ActionWait(5));
		scene0.add(new ActionSetBlock(3, 2, 3, Blocks.brick_block));
		scene0.add(new ActionSetTile(1, 2, 2, new TileEntityFoundryMold() {{ slots[0] = new ItemStack(ModItems.mold, 0, 2); }}));
		scene0.add(new ActionCreateActor(3, new ActorTileEntity(new RenderFoundry(), new NBTTagCompound() {{ setInteger("x", 1); setInteger("y", 2); setInteger("z", 2); }})));
		scene0.add(new ActionSetBlock(1, 2, 2, ModBlocks.foundry_mold));
		scene0.add(new ActionSetTile(2, 2, 2, new TileEntityFoundryChannel()));
		scene0.add(new ActionSetBlock(2, 2, 2, ModBlocks.foundry_channel));
		scene0.add(new ActionSetTile(3, 2, 2, new TileEntityFoundryChannel()));
		scene0.add(new ActionSetBlock(3, 2, 2, ModBlocks.foundry_channel));

		scene0.add(new ActionSetTile(2, 2, 1, new TileEntityFauxOutlet()));
		scene0.add(new ActionSetBlock(2, 2, 1, ModBlocks.foundry_outlet, 2));
		scene0.add(new ActionSetTile(3, 2, 1, new TileEntityFauxOutlet()));
		scene0.add(new ActionSetBlock(3, 2, 1, ModBlocks.foundry_outlet, 2));
		scene0.add(new ActionWait(5));
		scene0.add(new ActionSetTile(3, 3, 3, new TileEntityFoundryTank() {{ type = Mats.MAT_GOLD; amount = MaterialShapes.BLOCK.q(1); }}));
		scene0.add(new ActionSetBlock(3, 3, 3, ModBlocks.foundry_tank));
		scene0.add(new ActionSetTile(3, 3, 2, new TileEntityFauxOutlet()));
		scene0.add(new ActionSetBlock(3, 3, 2, ModBlocks.foundry_outlet, 2));
		
		scene0.add(new ActionWait(10));
		
		scene0.add(new ActionCreateActor(0, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 0, -25, new Object[][] {{I18nUtil.resolveKey("cannery.foundryChannel.0")}}, 200)
				.setColors(colorCopper).setOrientation(Orientation.LEFT)));
		
		scene0.add(new ActionWait(60));
		scene0.add(new ActionRemoveActor(0));
		scene0.add(new ActionCreateActor(0, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, -5, -40, new Object[][] {{I18nUtil.resolveKey("cannery.foundryChannel.1")}}, 200)
				.setColors(colorCopper).setOrientation(Orientation.TOP)));

		scene0.add(new ActionWait(60));
		scene0.add(new ActionRemoveActor(0));
		scene0.add(new ActionWait(10));
		scene0.add(new ActionSetTile(3, 2, 2, new TileEntityFoundryChannel() {{ type = Mats.MAT_GOLD; amount = MaterialShapes.INGOT.q(1); }}));
		
		scene0.add(new ActionWait(10));
		scene0.add(new ActionCreateActor(0, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 0, -25, new Object[][] {{I18nUtil.resolveKey("cannery.foundryChannel.2")}}, 200)
				.setColors(colorCopper).setOrientation(Orientation.LEFT)));
		
		scene0.add(new ActionWait(60));
		scene0.add(new ActionRemoveActor(0));
		
		for(int i = 0; i < 60; i++) {
			final int j = i;
			scene0.add(new ActionSetTile(3, 1, 1, new TileEntityFoundryBasin() {{ slots[0] = new ItemStack(ModItems.mold, 0, 12); type = Mats.MAT_GOLD; amount = MaterialShapes.BLOCK.q(j, 60); }}));
			scene0.add(new ActionWait(1));
		}
		
		JarScene scene1 = new JarScene(script);
		
		scene1.add(new ActionWait(10));
		scene1.add(new ActionCreateActor(0, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 0, -25, new Object[][] {{I18nUtil.resolveKey("cannery.foundryChannel.3")}}, 200)
				.setColors(colorCopper).setOrientation(Orientation.LEFT)));
		
		scene1.add(new ActionWait(60));
		scene1.add(new ActionRemoveActor(0));

		scene1.add(new ActionWait(10));
		scene1.add(new ActionSetTile(2, 2, 2, new TileEntityFoundryChannel() {{ type = Mats.MAT_GOLD; amount = MaterialShapes.INGOT.q(1); }}));
		scene1.add(new ActionWait(10));
		
		for(int i = 0; i < 60; i++) {
			final int j = i;
			scene1.add(new ActionSetTile(2, 1, 1, new TileEntityFoundryMold() {{ slots[0] = new ItemStack(ModItems.mold, 0, 2); type = Mats.MAT_GOLD; amount = MaterialShapes.INGOT.q(j, 60); }}));
			scene1.add(new ActionSetTile(1, 2, 2, new TileEntityFoundryMold() {{ slots[0] = new ItemStack(ModItems.mold, 0, 2); type = Mats.MAT_GOLD; amount = MaterialShapes.INGOT.q(j, 60); }}));
			scene1.add(new ActionWait(1));
		}
		
		scene1.add(new ActionWait(2));
		scene1.add(new ActionSetTile(3, 3, 3, new TileEntityFoundryTank()));
		scene1.add(new ActionWait(20));
		
		scene1.add(new ActionCreateActor(0, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 0, -25, new Object[][] {{I18nUtil.resolveKey("cannery.foundryChannel.4")}}, 200)
				.setColors(colorCopper).setOrientation(Orientation.LEFT)));
		scene1.add(new ActionWait(40));
		scene1.add(new ActionRemoveActor(0));
		scene1.add(new ActionWait(10));

		scene1.add(new ActionSetTile(2, 2, 2, new TileEntityFoundryChannel()));
		scene1.add(new ActionSetTile(3, 2, 2, new TileEntityFoundryChannel()));
		scene1.add(new ActionCreateActor(0, new ActorFancyPanel(Minecraft.getMinecraft().fontRenderer, 0, -25, new Object[][] {{new ItemStack(Items.iron_shovel), " -> ", ItemScraps.create(new MaterialStack(Mats.MAT_GOLD, 1))}}, 200)
				.setColors(colorCopper).setOrientation(Orientation.LEFT)));
		scene1.add(new ActionWait(40));
		scene1.add(new ActionRemoveActor(0));
		
		scene1.add(new ActionWait(60));
		scene1.add(new ActionSetTile(2, 1, 1, new TileEntityFoundryMold() {{ slots[1] = new ItemStack(Items.gold_ingot); }}));
		scene1.add(new ActionSetTile(1, 2, 2, new TileEntityFoundryMold() {{ slots[1] = new ItemStack(Items.gold_ingot); }}));
		scene1.add(new ActionSetTile(3, 1, 1, new TileEntityFoundryBasin() {{ slots[1] = new ItemStack(Blocks.gold_block); }}));

		script.addScene(scene0);
		script.addScene(scene1);
		
		return script;
	}
	
	public static class TileEntityFauxOutlet extends TileEntityFoundryOutlet {
		
		public boolean isClosed = false;
		
		@Override
		public boolean isClosed() { return isClosed; }
	}
}

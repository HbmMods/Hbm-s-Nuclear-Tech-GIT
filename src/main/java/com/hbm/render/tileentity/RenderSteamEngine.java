package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.TileEntitySteamEngine;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderSteamEngine extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		
		switch(tile.getBlockMetadata() - BlockDummyable.offset) {
		case 3: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 2: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}

		TileEntitySteamEngine engine = (TileEntitySteamEngine) tile;
		float angle = engine.lastRotor + (engine.rotor - engine.lastRotor) * interp;
		GL11.glTranslated(2, 0, 0);
		renderCommon(angle);
		
		GL11.glPopMatrix();
	}
	
	private void renderCommon(double rot) {
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		bindTexture(ResourceManager.steam_engine_tex);
		ResourceManager.steam_engine.renderPart("Base");
		
		GL11.glPushMatrix();
		GL11.glTranslated(2, 1.375, 0);
		GL11.glRotated(rot, 0, 0, -1);
		GL11.glTranslated(-2, -1.375, 0);
		ResourceManager.steam_engine.renderPart("Flywheel");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 1.375, -0.5);
		GL11.glRotated(rot * 2D, 1, 0, 0);
		GL11.glTranslated(0, -1.375, 0.5);
		ResourceManager.steam_engine.renderPart("Shaft");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		double sin = Math.sin(rot * Math.PI / 180D) * 0.25D - 0.25D;
		double cos = Math.cos(rot * Math.PI / 180D) * 0.25D;
		double ang = Math.acos(cos / 1.875D);
		GL11.glTranslated(sin, cos, 0);
		GL11.glTranslated(2.25, 1.375, 0);
		GL11.glRotated(ang * 180D / Math.PI - 90D, 0, 0, -1);
		GL11.glTranslated(-2.25, -1.375, 0);
		ResourceManager.steam_engine.renderPart("Transmission");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		double cath = Math.sqrt(3.515625D - (cos * cos) / 2);
		GL11.glTranslated(1.875 - cath + sin, 0, 0); //the difference that "1.875 - cath" makes is minuscule but very much noticeable
		ResourceManager.steam_engine.renderPart("Piston");
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glEnable(GL11.GL_CULL_FACE);
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_steam_engine);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase( ) {
			public void renderInventory() {
				GL11.glRotatef(90, 0F, -1F, 0F);
				GL11.glTranslated(0, -1.5, 0);
				double scale = 2D;
				GL11.glScaled(scale, scale, scale);
			}
			public void renderCommonWithStack(ItemStack item) {
				GL11.glRotatef(90, 0F, 1F, 0F);
				boolean cog = item.getItemDamage() != 1;
				RenderSteamEngine.this.renderCommon(cog ? System.currentTimeMillis() % 3600 * 0.1D : 0);
			}};
	}
}

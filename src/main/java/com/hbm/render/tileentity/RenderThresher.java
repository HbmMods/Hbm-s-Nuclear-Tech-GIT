package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.TileEntityMachineThresher;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderThresher extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		switch(tile.getBlockMetadata()) {
		case 3: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 2: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(90, 0F, 1F, 0F); break;
		}
		
		TileEntityMachineThresher thresher = (TileEntityMachineThresher) tile;

		double angle = thresher.prevAngle + (thresher.angle - thresher.prevAngle) * interp;
		double spin = thresher.lastSpin + (thresher.spin - thresher.lastSpin) * interp;
		double engine = thresher.isOn ? Math.sin(thresher.getWorldObj().getTotalWorldTime() * 2 % (Math.PI * 2) + interp) : 0;

		renderCommon(82.5 - angle, spin, engine);
		
		GL11.glPopMatrix();
	}
	
	private void renderCommon(double angle, double spin, double engine) {
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.thresher_tex);
		ResourceManager.thresher.renderPart("Base");
		
		GL11.glPushMatrix(); {
			GL11.glTranslated(0, engine * 0.01, 0);
			ResourceManager.thresher.renderPart("Engine");
		} GL11.glPopMatrix();

		GL11.glTranslated(0, 0.5, -1);
		GL11.glRotated(angle, 1, 0, 0);
		GL11.glTranslated(0, -0.5, 1);
		ResourceManager.thresher.renderPart("ArmUpper");

		GL11.glTranslated(0, 0.5, -5);
		GL11.glRotated(angle * -2, 1, 0, 0);
		GL11.glTranslated(0, -0.5, 5);
		GL11.glTranslated(-0.01, 0, 0);
		ResourceManager.thresher.renderPart("ArmLower");
		GL11.glTranslated(0.01, 0, 0);

		GL11.glTranslated(0, 0.5, -9);
		GL11.glRotated(angle, 1, 0, 0);
		GL11.glTranslated(0, -0.5, 9);
		ResourceManager.thresher.renderPart("Front");

		GL11.glTranslated(0, 0.5, -11);
		GL11.glRotated(-spin, 1, 0, 0);
		GL11.glTranslated(0, -0.5, 11);
		ResourceManager.thresher.renderPart("Wheel");
		
		GL11.glShadeModel(GL11.GL_FLAT);
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_thresher);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase( ) {
			public void renderInventory() {
				GL11.glTranslated(0, 4, -8);
				GL11.glScaled(4.5, 4.5, 4.5);
			}
			public void renderCommon() {
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glRotated(90, 0, -1, 0);
				RenderThresher.this.renderCommon(80D, System.currentTimeMillis() % 3600 * 0.25D, 0);
			}};
	}
}

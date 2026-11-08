package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderTrackingRadar extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glRotatef(90, 0F, 1F, 0F);

		float r = -15;
		float l = 45;

		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.tracking_radar_tex);
		ResourceManager.tracking_radar.renderPart("Base");
		GL11.glRotated(r, 0, 1, 0);
		ResourceManager.tracking_radar.renderPart("Mount");
		GL11.glTranslated(0, 9.375, 0);
		GL11.glRotated(l, 1, 0, 0);
		GL11.glTranslated(0, -9.375, 0);
		ResourceManager.tracking_radar.renderPart("Dish");
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_tracking_radar);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase( ) {
			public void renderInventory() {
				GL11.glTranslated(0, -5, 0);
				GL11.glScaled(2.25, 2.25, 2.25);
			}
			public void renderCommonWithStack(ItemStack item) {
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glRotatef(-90, 0F, 1F, 0F);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.tracking_radar_tex);
				ResourceManager.tracking_radar.renderPart("Base");
				GL11.glRotated(15, 0, 1, 0);
				ResourceManager.tracking_radar.renderPart("Mount");
				GL11.glTranslated(0, 9.375, 0);
				GL11.glRotated(45, 1, 0, 0);
				GL11.glTranslated(0, -9.375, 0);
				ResourceManager.tracking_radar.renderPart("Dish");
				GL11.glShadeModel(GL11.GL_FLAT);
			}};
	}
}

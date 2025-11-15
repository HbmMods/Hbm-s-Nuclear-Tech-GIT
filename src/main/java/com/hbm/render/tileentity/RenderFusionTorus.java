package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderFusionTorus extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.fusion_torus_tex);
		ResourceManager.fusion_torus.renderPart("Torus");
		
		GL11.glPushMatrix();
		double rot = (tile.getWorldObj().getTotalWorldTime() % 360) + interp;
		GL11.glRotated(rot * 10, 0, 1, 0);
		ResourceManager.fusion_torus.renderPart("Magnet");
		GL11.glPopMatrix();

		ResourceManager.fusion_torus.renderPart("Bolts1");
		ResourceManager.fusion_torus.renderPart("Bolts2");
		ResourceManager.fusion_torus.renderPart("Bolts3");
		ResourceManager.fusion_torus.renderPart("Bolts4");
		
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.fusion_torus);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase() {
			public void renderInventory() {
				GL11.glScaled(2, 2, 2);
			}
			public void renderCommon() {
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glRotatef(90, 0F, 1F, 0F);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.fusion_torus_tex);
				ResourceManager.fusion_torus.renderPart("Torus");
				GL11.glPushMatrix();
				double rot = (System.currentTimeMillis() / 5 % 360);
				GL11.glRotated(rot, 0, 1, 0);
				ResourceManager.fusion_torus.renderPart("Magnet");
				GL11.glPopMatrix();
				GL11.glShadeModel(GL11.GL_FLAT);
			}};
	}
}

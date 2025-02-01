package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderPADipole extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
		
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y - 1D, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.pa_dipole_tex);
		ResourceManager.pa_dipole.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPopMatrix();
	}
	
	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.pa_dipole);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -3, 0);
				double scale = 3.5;
				GL11.glScaled(scale, scale, scale);
			}
			public void renderCommon() {
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.pa_dipole_tex);
				ResourceManager.pa_dipole.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}};
	}
}

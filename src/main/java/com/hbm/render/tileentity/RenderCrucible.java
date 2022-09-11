package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderCrucible extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);

		bindTexture(ResourceManager.crucible_tex);
		ResourceManager.crucible_heat.renderAll();
		
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_crucible);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase( ) {
			public void renderInventory() {
				GL11.glTranslated(0, -1.5, 0);
				GL11.glScaled(3.25, 3.25, 3.25);
			}
			public void renderCommon() {
				bindTexture(ResourceManager.crucible_tex);
				ResourceManager.crucible_heat.renderAll();
			}};
	}
}

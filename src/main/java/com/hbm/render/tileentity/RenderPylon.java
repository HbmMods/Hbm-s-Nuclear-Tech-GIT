package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.network.TileEntityPylon;

import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderPylon extends RenderPylonBase implements IItemRendererProvider {
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		TileEntityPylon pyl = (TileEntityPylon)tile;
		
		bindTexture(ResourceManager.pylon_tex);
		ResourceManager.pylon.renderAll();
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		this.renderLinesGeneric(pyl, x, y, z);
		GL11.glPopMatrix();
	}
	
	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.red_pylon);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -5, 0);
				GL11.glScaled(2.9, 2.9, 2.9);
			}
			public void renderCommon() {
				GL11.glScaled(1, 1, 1);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.pylon_tex);
				ResourceManager.pylon.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}};
	}
}

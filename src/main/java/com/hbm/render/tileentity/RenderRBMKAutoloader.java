package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKAutoloader;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderRBMKAutoloader extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_CULL_FACE);

		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.rbmk_autoloader_tex);
		ResourceManager.rbmk_autoloader.renderPart("Base");

		TileEntityRBMKAutoloader press = (TileEntityRBMKAutoloader) tile;
		double p = (press.lastPiston + (press.renderPiston - press.lastPiston) * interp);
		GL11.glTranslated(0, p * -4D, 0);
		
		GL11.glTranslated(0, 4, 0);
		ResourceManager.rbmk_autoloader.renderPart("Piston");
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.rbmk_autoloader);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase( ) {
			public void renderInventory() {
				GL11.glTranslated(0, -6, 0);
				GL11.glScaled(1.75, 1.75, 1.75);
			}
			public void renderCommon() {
				GL11.glRotated(180, 0, 1, 0);
				GL11.glDisable(GL11.GL_CULL_FACE);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.rbmk_autoloader_tex);
				ResourceManager.rbmk_autoloader.renderPart("Base");
				ResourceManager.rbmk_autoloader.renderPart("Piston");
				GL11.glShadeModel(GL11.GL_FLAT);
				GL11.glEnable(GL11.GL_CULL_FACE);
			}};
	}

}

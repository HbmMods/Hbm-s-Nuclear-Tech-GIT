package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.TileEntityMachineRadarNT;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderRadarLarge extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);

		bindTexture(ResourceManager.radar_large_tex);
		ResourceManager.radar_large.renderPart("Radar");

		TileEntityMachineRadarNT radar = (TileEntityMachineRadarNT) tileEntity;
		GL11.glRotatef(radar.prevRotation + (radar.rotation - radar.prevRotation) * f, 0F, -1F, 0F);
		
		ResourceManager.radar_large.renderPart("Dish");

		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_radar_large);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase( ) {
			public void renderInventory() {
				GL11.glTranslated(0, -5, 0);
				GL11.glScaled(3, 3, 3);
			}
			public void renderCommonWithStack(ItemStack item) {
				GL11.glRotated(180, 0, 1, 0);
				GL11.glScaled(0.5, 0.5, 0.5);
				bindTexture(ResourceManager.radar_large_tex);
				ResourceManager.radar_large.renderPart("Radar");
				GL11.glRotated(System.currentTimeMillis() % 3600 * 0.1D, 0, -1, 0);
				ResourceManager.radar_large.renderPart("Dish");
			}};
	}
}

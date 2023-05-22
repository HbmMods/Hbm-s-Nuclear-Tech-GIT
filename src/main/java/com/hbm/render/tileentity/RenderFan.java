package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.MachineFan.TileEntityFan;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderFan extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		GL11.glTranslated(0D, 0.5, 0D); 

		switch(tile.getBlockMetadata()) {
		case 0: GL11.glRotatef(180, 1F, 0F, 0F); break;
		case 1: break;
		case 2: GL11.glRotatef(-90, 1F, 0F, 0F); break;
		case 4: GL11.glRotatef(90, 0F, 0F, 1F); break;
		case 3: GL11.glRotatef(90, 1F, 0F, 0F); break;
		case 5: GL11.glRotatef(-90, 0F, 0F, 1F); break;
		}
		
		GL11.glTranslated(0D, -0.5, 0D); 
		
		bindTexture(ResourceManager.fan_tex);
		ResourceManager.fan.renderPart("Frame");

		TileEntityFan fan = (TileEntityFan) tile;
		float rot = fan.prevSpin + (fan.spin - fan.prevSpin) * interp;
		GL11.glRotated(-rot, 0, 1, 0);
		ResourceManager.fan.renderPart("Blades");
		
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.fan);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -2.5, 0);
				double scale = 5;
				GL11.glScaled(scale, scale, scale);
			}
			public void renderCommon() {
				GL11.glScaled(2, 2, 2);
				bindTexture(ResourceManager.fan_tex);
				ResourceManager.fan.renderAll();
			}};
	}
}

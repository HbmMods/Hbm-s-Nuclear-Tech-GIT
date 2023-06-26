package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.TileEntityMachineCompressor;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderCompressor extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		
		switch(tile.getBlockMetadata() - BlockDummyable.offset) {
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		}

		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.compressor_tex);
		ResourceManager.compressor.renderPart("Compressor");
		
		TileEntityMachineCompressor compressor = (TileEntityMachineCompressor) tile;
		float lift = compressor.prevPiston + (compressor.piston - compressor.prevPiston) * interp;
		float fan = compressor.prevFanSpin + (compressor.fanSpin - compressor.prevFanSpin) * interp;
		
		GL11.glPushMatrix();
		GL11.glTranslatef(0, lift * 3 - 3, 0);
		ResourceManager.compressor.renderPart("Pump");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 1.5, 0);
		GL11.glRotatef(fan, 1, 0, 0);
		GL11.glTranslated(0, -1.5, 0);
		ResourceManager.compressor.renderPart("Fan");
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_compressor);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(3, 3, 3);
			}
			public void renderCommon() {
				GL11.glDisable(GL11.GL_CULL_FACE);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				
				GL11.glScaled(0.5, 0.5, 0.5);
				
				bindTexture(ResourceManager.compressor_tex);
				ResourceManager.compressor.renderPart("Compressor");
				
				double lift = (System.currentTimeMillis() * 0.005) % 9;
				
				if(lift > 3) lift = 3 - (lift - 3) / 2D;
				
				GL11.glPushMatrix();
				GL11.glTranslated(0, -lift, 0);
				ResourceManager.compressor.renderPart("Pump");
				GL11.glPopMatrix();

				GL11.glPushMatrix();
				GL11.glTranslated(0, 1.5, 0);
				GL11.glRotated((System.currentTimeMillis() * 0.25) % 360D, 1, 0, 0);
				GL11.glTranslated(0, -1.5, 0);
				ResourceManager.compressor.renderPart("Fan");
				GL11.glPopMatrix();
				
				GL11.glShadeModel(GL11.GL_FLAT);
				GL11.glEnable(GL11.GL_CULL_FACE);
			}};
	}
}

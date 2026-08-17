package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.TileEntityMachineTapeDrive;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderTapeDrive extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		switch(tile.getBlockMetadata()) {
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		}
		
		TileEntityMachineTapeDrive tapeDrive = (TileEntityMachineTapeDrive) tile;
		
		bindTexture(ResourceManager.tape_drive_tex);
		ResourceManager.tape_drive.renderPart("Frame");
		
		for(int i = 0; i < 12; i++) {
			byte tape = tapeDrive.tapes[i];
			
			GL11.glPushMatrix();
			
			//TBI
			
			GL11.glPopMatrix();
		}
		
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_tape_drive);
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
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.tape_drive_tex);
				ResourceManager.tape_drive.renderPart("Frame");
				GL11.glShadeModel(GL11.GL_FLAT);
			}};
	}
}

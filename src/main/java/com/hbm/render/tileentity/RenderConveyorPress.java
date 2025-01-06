package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.TileEntityConveyorPress;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderConveyorPress extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);

		switch(tile.getBlockMetadata() - BlockDummyable.offset) {
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}
		
		TileEntityConveyorPress press = (TileEntityConveyorPress) tile;
		
		bindTexture(ResourceManager.conveyor_press_tex);
		ResourceManager.conveyor_press.renderPart("Press");
		
		if(press.syncStack != null) {
			GL11.glPushMatrix();
			double piston = press.lastPress + (press.renderPress - press.lastPress) * interp;
			GL11.glTranslated(0, -piston * 0.75, 0);
			ResourceManager.conveyor_press.renderPart("Piston");
			GL11.glPopMatrix();
		}
		
		bindTexture(ResourceManager.conveyor_press_belt_tex);
		
		GL11.glMatrixMode(GL11.GL_TEXTURE);
		GL11.glLoadIdentity();
		int ticks = (int)(tile.getWorldObj().getTotalWorldTime() % 16) - 2;
		GL11.glTranslated(0, ticks / 16D, 0);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		ResourceManager.conveyor_press.renderPart("Belt");
		GL11.glMatrixMode(GL11.GL_TEXTURE);
		GL11.glLoadIdentity();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_conveyor_press);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase( ) {
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(4.5, 4.5, 4.5);
			}
			public void renderCommon() {
				bindTexture(ResourceManager.conveyor_press_tex);
				ResourceManager.conveyor_press.renderPart("Press");
				ResourceManager.conveyor_press.renderPart("Piston");
				bindTexture(ResourceManager.conveyor_press_belt_tex);
				ResourceManager.conveyor_press.renderPart("Belt");
			}};
	}
}

package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.TileEntitySolarBoiler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.client.IItemRenderer;

public class RenderSolarPanel extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float interp) {

		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);

		GL11.glPushMatrix();
		GL11.glRotatef(90, 0F, 1F, 0F);

		switch(te.getBlockMetadata() - BlockDummyable.offset) {
		case 2: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(270, 0F, 1F, 0F); break;
		}

		bindTexture(ResourceManager.solarp_tex);

		GL11.glShadeModel(GL11.GL_SMOOTH);
		ResourceManager.solarp.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();

		GL11.glPopMatrix();
	}
	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -3, 0);
				GL11.glScaled(2.75, 2.75, 2.75);
			}
			public void renderCommon() {
				GL11.glScaled(1, 1, 1);
				GL11.glDisable(GL11.GL_CULL_FACE);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.solarp_tex);
				ResourceManager.solarp.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
				GL11.glEnable(GL11.GL_CULL_FACE);
			}};
	}
	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_solar);
	}

}

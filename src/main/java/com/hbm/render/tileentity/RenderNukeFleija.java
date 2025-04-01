package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderNukeFleija extends TileEntitySpecialRenderer implements IItemRendererProvider {

    @Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
		switch(tileEntity.getBlockMetadata())
		{
		case 5:
			GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 2:
			GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 4:
			GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 3:
			GL11.glRotatef(0, 0F, 1F, 0F); break;
		}

		GL11.glShadeModel(GL11.GL_SMOOTH);
        bindTexture(ResourceManager.bomb_fleija_tex);
        ResourceManager.bomb_fleija.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);

        GL11.glPopMatrix();
    }
	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.nuke_fleija);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase() {
			public void renderInventory() {
				GL11.glScaled(6.8, 6.8, 6.8);
			}
			public void renderCommon() {
				GL11.glTranslated(0.125, 0, 0);
				GL11.glRotatef(90, 0F, 1F, 0F);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.bomb_fleija_tex);
				ResourceManager.bomb_fleija.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}};
	}

}

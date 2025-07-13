package com.hbm.render.tileentity;

import com.hbm.blocks.ModBlocks;
import com.hbm.render.item.ItemRenderBase;
import net.minecraft.item.Item;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderNukePrototype extends TileEntitySpecialRenderer implements IItemRendererProvider {

    @Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
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
        bindTexture(ResourceManager.bomb_prototype_tex);
        ResourceManager.bomb_prototype.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);

        GL11.glEnable(GL11.GL_CULL_FACE);

        GL11.glPopMatrix();
    }
	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.nuke_prototype);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, 0.125, 0);
				GL11.glScaled(3, 3, 3);
			}

			public void renderCommon() {
				GL11.glRotated(90, 0, 1, 0);
				GL11.glTranslated(0, 0.125, 0);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				GL11.glDisable(GL11.GL_CULL_FACE);
				bindTexture(ResourceManager.bomb_prototype_tex);
				ResourceManager.bomb_prototype.renderAll();
				GL11.glEnable(GL11.GL_CULL_FACE);
				GL11.glShadeModel(GL11.GL_FLAT);
			}
		};
	}

}

package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderRadiator extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		{

			GL11.glTranslated(x + 0.5D, y + 0.5D, z + 0.5D);
		
			switch(te.getBlockMetadata() - BlockDummyable.offset) {
			case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
			case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
			case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
			case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
			}

			GL11.glTranslatef(-0.5F, 0, 0);

			GL11.glRotatef(-90, 0, 0, 1);
			
			GL11.glShadeModel(GL11.GL_SMOOTH);
			bindTexture(ResourceManager.radiator_tex);
			ResourceManager.radiator.renderAll();
			GL11.glShadeModel(GL11.GL_FLAT);

		}
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_radiator);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase( ) {
			public void renderInventory() {
				GL11.glTranslated(0, -5, 0);
				GL11.glScaled(1.2, 1.2, 1.2);
			}
			public void renderCommon() {
				GL11.glScaled(0.75, 0.75, 0.75);
				// GL11.glRotatef(-90, 0, 0, 1);
				// GL11.glTranslated(0.5, 0, 0);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.radiator_tex);
				ResourceManager.radiator.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}
		};
	}

}
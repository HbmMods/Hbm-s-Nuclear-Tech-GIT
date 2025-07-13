package com.hbm.render.tileentity;

import java.awt.Color;
import java.nio.DoubleBuffer;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.TileEntityRefueler;

import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderRefueler extends TileEntitySpecialRenderer implements IItemRendererProvider {
	
	private static DoubleBuffer clip = null;

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		TileEntityRefueler refueler = (TileEntityRefueler) tile;

		GL11.glPushMatrix();
		{

			GL11.glTranslated(x + 0.5, y, z + 0.5);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glRotatef(90, 0F, 1F, 0F);
			switch(tile.getBlockMetadata()) {
			case 4: GL11.glRotatef(90, 0F, 1F, 0F); break;
			case 3: GL11.glRotatef(180, 0F, 1F, 0F); break;
			case 5: GL11.glRotatef(270, 0F, 1F, 0F); break;
			case 2: GL11.glRotatef(0, 0F, 1F, 0F); break;
			}
	
			GL11.glShadeModel(GL11.GL_SMOOTH);

			bindTexture(ResourceManager.refueler_tex);
			ResourceManager.refueler.renderPart("Fueler");


			if(clip == null) {
				clip = GLAllocation.createDirectByteBuffer(8*4).asDoubleBuffer();
				clip.put(new double[] {0, 1, 0, -0.125 });
				clip.rewind();
			}

			GL11.glEnable(GL11.GL_CLIP_PLANE0);
			GL11.glClipPlane(GL11.GL_CLIP_PLANE0, clip);

			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_TEXTURE_2D);

			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

			double fillLevel = refueler.prevFillLevel + (refueler.fillLevel - refueler.prevFillLevel) * interp;
			GL11.glTranslated(0, (1 - fillLevel) * -0.625, 0);
			
			Color color = new Color(refueler.tank.getTankType().getColor());
			GL11.glColor4f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, 0.75F);
			ResourceManager.refueler.renderPart("Fluid");
			GL11.glColor4f(1, 1, 1, 1);
			
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_BLEND);

			GL11.glDisable(GL11.GL_CLIP_PLANE0);

			GL11.glShadeModel(GL11.GL_FLAT);

		}
		GL11.glPopMatrix();
	}
	
	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.refueler);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -3, 0);
				GL11.glScaled(6, 6, 6);
			}
			public void renderCommon() {
				GL11.glScaled(2, 2, 2);
				GL11.glTranslated(0.5, 0, 0);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.refueler_tex);
				ResourceManager.refueler.renderPart("Fueler");
				GL11.glShadeModel(GL11.GL_FLAT);
			}
		};
	}
	
}
